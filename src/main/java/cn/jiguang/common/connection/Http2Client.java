package cn.jiguang.common.connection;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;


public class Http2Client implements IHttpClient {

    private static final Logger LOG = LoggerFactory.getLogger(Http2Client.class);
    private static final String KEYWORDS_CONNECT_TIMED_OUT = "connect timed out";
    private static final String KEYWORDS_READ_TIMED_OUT = "Read timed out";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final int _connectionTimeout;
    private final int _readTimeout;
    private final int _maxRetryTimes;
    private final String _sslVer;

    private String _authCode;
    private HttpProxy _proxy;

    public Http2Client(String authCode, HttpProxy proxy, ClientConfig config) {
        _maxRetryTimes = config.getMaxRetryTimes();
        _connectionTimeout = config.getConnectionTimeout();
        _readTimeout = config.getReadTimeout();
        _sslVer = config.getSSLVersion();

        _authCode = authCode;
        _proxy = proxy;

        String message = MessageFormat.format("Created instance with "
                        + "connectionTimeout {0}, readTimeout {1}, maxRetryTimes {2}, SSL Version {3}",
                _connectionTimeout, _readTimeout, _maxRetryTimes, _sslVer);
        LOG.info(message);

        if (null != _proxy && _proxy.isAuthenticationNeeded()) {
            Authenticator.setDefault(new NativeHttpClient.SimpleProxyAuthenticator(
                    _proxy.getUsername(), _proxy.getPassword()));
        }
    }

    @Override
    public ResponseWrapper sendGet(String url) throws APIConnectionException, APIRequestException {
        return sendGet(url, null);
    }

    public ResponseWrapper sendGet(String url, String content) throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        LOG.debug("Send request - Get" + " " + url);
        if (null != content) {
            LOG.debug("Request Content - " + content);
        }

        try {
            Request request = new Request.Builder().url(url)
                    .header("User-Agent", JPUSH_USER_AGENT)
                    .addHeader("Accept-Charset", CHARSET)
                    .addHeader("Charset", CHARSET)
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("Authorization", _authCode)
                    .addHeader("Content-Type", CONTENT_TYPE_JSON)
                    .build();
            if (null != content) {
                byte[] data = content.getBytes(CHARSET);
                request.newBuilder().header("Content-Length", String.valueOf(data.length));
            }
            handleResponse(wrapper, request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    public void handleResponse(ResponseWrapper wrapper, Request request) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(_connectionTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(_readTimeout, TimeUnit.MILLISECONDS)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            wrapper.responseCode = 200;
            wrapper.responseContent = response.body().string();
            LOG.debug("Succeed to get response OK - response body: " + wrapper.responseContent);
//            InputStream in = response.body().byteStream();
//            StringBuffer sb = new StringBuffer();
//            if (null != in) {
//                InputStreamReader reader = new InputStreamReader(in, CHARSET);
//                char[] buff = new char[1024];
//                int len;
//                while ((len = reader.read(buff)) > 0) {
//                    sb.append(buff, 0, len);
//                }
//            }
        } else {
            int status = response.code();
            wrapper.responseCode = status;
            wrapper.responseContent = response.body().string();
            if (status >= 300 && status < 400) {
                LOG.warn("Normal response but unexpected - responseCode:" + status + ", responseContent:" + wrapper.responseContent);

            } else {
                LOG.warn("Got error response - responseCode:" + status + ", responseContent:" + wrapper.responseContent);

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
            }
            LOG.warn("Got error response - response: " + response.body().string());
            wrapper.setErrorObject();
        }
    }

    @Override
    public ResponseWrapper sendDelete(String url) throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        LOG.debug("Send request - Delete url:" + " " + url);
        Request request;
        try {
            request = new Request.Builder().url(url)
                    .header("User-Agent", JPUSH_USER_AGENT)
                    .addHeader("Accept-Charset", CHARSET)
                    .addHeader("Charset", CHARSET)
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("Authorization", _authCode)
                    .addHeader("Content-Type", CONTENT_TYPE_JSON)
                    .delete().build();
            handleResponse(wrapper, request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper sendPost(String url, String content) throws APIConnectionException, APIRequestException {
        LOG.debug("Send request - Post url:" + " " + url + " content: " + content);
        ResponseWrapper wrapper = new ResponseWrapper();
        try {
            RequestBody body = RequestBody.create(JSON, content);
            Request request = new Request.Builder().url(url)
                    .header("User-Agent", JPUSH_USER_AGENT)
                    .addHeader("Accept-Charset", CHARSET)
                    .addHeader("Charset", CHARSET)
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("Authorization", _authCode)
                    .addHeader("Content-Type", CONTENT_TYPE_JSON)
                    .post(body).build();
            handleResponse(wrapper, request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper sendPut(String url, String content) throws APIConnectionException, APIRequestException {
        LOG.debug("Send request - Put url:" + " " + url + " content: " + content);
        ResponseWrapper wrapper = new ResponseWrapper();
        try {
            RequestBody body = RequestBody.create(JSON, content);
            Request request = new Request.Builder().url(url)
                    .header("User-Agent", JPUSH_USER_AGENT)
                    .addHeader("Accept-Charset", CHARSET)
                    .addHeader("Charset", CHARSET)
                    .addHeader("Connection", "Keep-Alive")
                    .addHeader("Authorization", _authCode)
                    .addHeader("Content-Type", CONTENT_TYPE_JSON)
                    .put(body).build();
            handleResponse(wrapper, request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wrapper;
    }
}
