package cn.jiguang.common.connection;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * Apache HttpClient 实现的版本，提供了连接池来实现高并发网络请求。
 */
public class ApacheHttpClient implements IHttpClient {

    private static Logger LOG = LoggerFactory.getLogger(ApacheHttpClient.class);

    private static CloseableHttpClient _httpClient = null;
    private final static Object syncLock = new Object();
    private final int _connectionTimeout;
    private final int _connectionRequestTimeout;
    private final int _socketTimeout;
    private final int _maxRetryTimes;
    private String _authCode;
    private HttpHost _proxy;

    public ApacheHttpClient(String authCode, HttpProxy proxy, ClientConfig config) {
        _maxRetryTimes = config.getMaxRetryTimes();
        _connectionTimeout = config.getConnectionTimeout();
        _connectionRequestTimeout = config.getConnectionRequestTimeout();
        _socketTimeout = config.getSocketTimeout();
        _authCode = authCode;
        if (proxy != null) {
            _proxy = new HttpHost(proxy.getHost(), proxy.getPort());
        }
    }

    private void configHttpRequest(HttpRequestBase httpRequestBase) {
        RequestConfig requestConfig;
        if (_proxy != null) {
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(_connectionRequestTimeout)
                    .setConnectTimeout(_connectionTimeout)
                    .setSocketTimeout(_socketTimeout)
                    .setProxy(_proxy)
                    .build();
        } else {
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(_connectionRequestTimeout)
                    .setConnectTimeout(_connectionTimeout)
                    .setSocketTimeout(_socketTimeout)
                    .build();
        }

        httpRequestBase.setConfig(requestConfig);
    }

    public CloseableHttpClient getHttpClient(String url) {
        String hostname = url.split("/")[2];
        int port = 80;
        if (hostname.contains(":")) {
            String[] arr = hostname.split(":");
            hostname = arr[0];
            port = Integer.parseInt(arr[1]);
        }
        if (_httpClient == null) {
            synchronized (syncLock) {
                if (_httpClient == null) {
                    _httpClient = createHttpClient(200, 40, 100, hostname, port);
                }
            }
        }
        return _httpClient;

    }

    public CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, int maxRoute,
                                                       String hostname, int port) {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory
                .getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory
                .getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory> create().register("http", plainsf)
                .register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                registry);
        // 将最大连接数增加
        cm.setMaxTotal(maxTotal);
        // 将每个路由基础的连接增加
        cm.setDefaultMaxPerRoute(maxPerRoute);
        HttpHost httpHost = new HttpHost(hostname, port);
        // 将目标主机的最大连接数增加
        cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= _maxRetryTimes) {
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// SSL握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext
                        .adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler).build();

        return httpClient;

    }


    @Override
    public ResponseWrapper sendGet(String url) throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, _authCode);
            configHttpRequest(httpGet);
            response = getHttpClient(url).execute(httpGet, HttpClientContext.create());
            processResponse(response, wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wrapper;

    }

    public ResponseWrapper sendGet(String url, String content)
            throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, _authCode);
            configHttpRequest(httpGet);
            response = getHttpClient(url).execute(httpGet, HttpClientContext.create());
            processResponse(response, wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper sendDelete(String url) throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        CloseableHttpResponse response = null;
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.setHeader(HttpHeaders.AUTHORIZATION, _authCode);
            configHttpRequest(httpDelete);
            response = getHttpClient(url).execute(httpDelete, HttpClientContext.create());
            processResponse(response, wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wrapper;
    }

    public ResponseWrapper sendDelete(String url, String content)
            throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        CloseableHttpResponse response = null;
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.setHeader(HttpHeaders.AUTHORIZATION, _authCode);
            configHttpRequest(httpDelete);
            response = getHttpClient(url).execute(httpDelete, HttpClientContext.create());
            processResponse(response, wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper sendPost(String url, String content) throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, _authCode);
            configHttpRequest(httpPost);
            StringEntity params = new StringEntity(content, CHARSET);
            httpPost.setEntity(params);
            response = getHttpClient(url).execute(httpPost, HttpClientContext.create());
            processResponse(response, wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper sendPut(String url, String content) throws APIConnectionException, APIRequestException {
//        return doRequest(url, content, RequestMethod.PUT);
        ResponseWrapper wrapper = new ResponseWrapper();
        CloseableHttpResponse response = null;
        try {
            HttpPut httpPut = new HttpPut(url);
            httpPut.setHeader(HttpHeaders.AUTHORIZATION, _authCode);
            configHttpRequest(httpPut);
            StringEntity params = new StringEntity(content, CHARSET);
            httpPut.setEntity(params);
            response = getHttpClient(url).execute(httpPut, HttpClientContext.create());
            processResponse(response, wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wrapper;
    }

    public void processResponse(CloseableHttpResponse response, ResponseWrapper wrapper)
            throws APIConnectionException, APIRequestException, IOException {
        HttpEntity entity = response.getEntity();
        LOG.debug("Response", response.toString());
        int status = response.getStatusLine().getStatusCode();
        String responseContent = EntityUtils.toString(entity, "utf-8");
        wrapper.responseCode = status;
        wrapper.responseContent = responseContent;
        LOG.debug(wrapper.responseContent);
        EntityUtils.consume(entity);
        if (status >= 200 && status < 300) {
            LOG.debug("Succeed to get response OK - responseCode:" + status);
            LOG.debug("Response Content - " + responseContent);

        } else if (status >= 300 && status < 400) {
            LOG.warn("Normal response but unexpected - responseCode:" + status + ", responseContent:" + responseContent);

        } else {
            LOG.warn("Got error response - responseCode:" + status + ", responseContent:" + responseContent);

            switch (status) {
                case 400:
                    LOG.error("Your request params is invalid. Please check them according to error message.");
                    wrapper.setErrorObject();
                    break;
                case 401:
                    LOG.error("Authentication failed! Please check authentication params according to docs.");
                    wrapper.setErrorObject();
                    break;
                case 403:
                    LOG.error("Request is forbidden! Maybe your appkey is listed in blacklist or your params is invalid.");
                    wrapper.setErrorObject();
                    break;
                case 404:
                    LOG.error("Request page is not found! Maybe your params is invalid.");
                    wrapper.setErrorObject();
                    break;
                case 410:
                    LOG.error("Request resource is no longer in service. Please according to notice on official website.");
                    wrapper.setErrorObject();
                case 429:
                    LOG.error("Too many requests! Please review your appkey's request quota.");
                    wrapper.setErrorObject();
                    break;
                case 500:
                case 502:
                case 503:
                case 504:
                    LOG.error("Seems encountered server error. Maybe JPush is in maintenance? Please retry later.");
                    break;
                default:
                    LOG.error("Unexpected response.");
            }

            throw new APIRequestException(wrapper);
        }
    }
}
