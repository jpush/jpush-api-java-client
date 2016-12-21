package cn.jiguang.common.connection;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;


public class NettyHttpClient implements IHttpClient {

    private static Logger LOG = LoggerFactory.getLogger(NettyHttpClient.class);

    private String _authCode;
    private HttpProxy _proxy;
    private String _host;

    public NettyHttpClient(String authCode, HttpProxy proxy, ClientConfig config, String host) {

        String message = MessageFormat.format("Created instance with "
                        + "connectionTimeout {0}, readTimeout {1}, maxRetryTimes {2}, SSL Version {3}",
                config.getConnectionTimeout(), config.getReadTimeout(), config.getMaxRetryTimes(), config.getSSLVersion());
        LOG.info(message);
        //域名要去掉 https://
        _host = host.substring(8);

        _authCode = authCode;
        _proxy = proxy;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            ClientInitializer initializer = new ClientInitializer();
            b.handler(initializer);

            // Start the client.
            ChannelFuture f = b.connect(host, 443).sync(); // (5)
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public ResponseWrapper sendGet(String url) throws APIConnectionException, APIRequestException {
        return sendGet(url, null);
    }

    public ResponseWrapper sendGet(String url, String content) throws APIConnectionException, APIRequestException {
        //截取url中域名后面的路径，+8是因为 _host 中截取了 https://
        String path = url.substring(_host.length() + 8);
        ResponseWrapper wrapper = new ResponseWrapper();
        try {
            doRequest(path, HttpMethod.GET, content);
            handleResponse(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper sendPut(String url, String content) throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        try {
            doRequest(url, HttpMethod.PUT, content);
            handleResponse(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper sendPost(String url, String content) throws APIConnectionException, APIRequestException {
        String path = url.substring(_host.length() + 8);
        ResponseWrapper wrapper = new ResponseWrapper();
        try {
            doRequest(path, HttpMethod.POST, content);
            handleResponse(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper sendDelete(String url) throws APIConnectionException, APIRequestException {
        return sendDelete(url, null);
    }

    public ResponseWrapper sendDelete(String url, String content) throws APIConnectionException, APIRequestException {
        String path = url.substring(_host.length() + 8);
        ResponseWrapper wrapper = new ResponseWrapper();
        try {
            doRequest(path, HttpMethod.DELETE, content);
            handleResponse(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    public void handleResponse(ResponseWrapper wrapper) {
        List<String> responseList = responseMap.get(mKey);
        if (null != responseList) {
            int status = Integer.valueOf(responseList.get(0));
            wrapper.responseCode = status;
            if (responseList.size() > 1) {
                wrapper.responseContent = responseList.get(1);
            }
            if (status / 200 == 1) {
                LOG.debug("Succeed to get response OK - response body: " + wrapper.responseContent);
            } else if (status >= 300 && status < 400) {
                LOG.warn("Normal response but unexpected - responseCode:" + status + ", responseContent:" + wrapper.responseContent);
                wrapper.setErrorObject();
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
                        wrapper.setErrorObject();
                        break;
                    default:
                        LOG.error("Unexpected response.");
                        wrapper.setErrorObject();
                }
            }
        } else {
            LOG.error("Unexpected response.");
            wrapper.setErrorObject();
        }
    }

    public interface BaseCallback {
        public void onSucceed(ResponseWrapper wrapper);
    }
}
