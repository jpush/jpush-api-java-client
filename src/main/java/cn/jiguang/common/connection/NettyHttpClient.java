package cn.jiguang.common.connection;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.concurrent.CountDownLatch;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


public class NettyHttpClient implements IHttpClient {

    private static Logger LOG = LoggerFactory.getLogger(NettyHttpClient.class);

    private String _authCode;
    private int _maxRetryTimes;
    private int _readTimeout;
    private Channel _channel;
    private Bootstrap b;
    private EventLoopGroup _workerGroup;
    private SslContext _sslCtx;


    public NettyHttpClient(String authCode, HttpProxy proxy, ClientConfig config) {
        _maxRetryTimes = config.getMaxRetryTimes();
        _readTimeout = config.getReadTimeout();
        String message = MessageFormat.format("Created instance with "
                        + "connectionTimeout {0}, readTimeout {1}, maxRetryTimes {2}, SSL Version {3}",
                config.getConnectionTimeout(), _readTimeout, _maxRetryTimes, config.getSSLVersion());
        LOG.info(message);
        _authCode = authCode;

        try {
            _sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            _workerGroup = new NioEventLoopGroup();
            b = new Bootstrap(); // (1)
            b.group(_workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
        } catch (SSLException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(HttpMethod method, String content, URI uri, BaseCallback callback) {
        FullHttpRequest request;
        b = new Bootstrap();
        if (b.group() == null) {
            b.group(_workerGroup);
        }
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new NettyClientInitializer(_sslCtx, callback, null));
        String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }
        _channel = b.connect(uri.getHost(), port).syncUninterruptibly().channel();
        if (null != content) {
            ByteBuf byteBuf = Unpooled.copiedBuffer(content.getBytes(CharsetUtil.UTF_8));
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri.getRawPath(), byteBuf);
            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, (long) byteBuf.readableBytes());
        } else {
            request = new DefaultFullHttpRequest(HTTP_1_1, method, uri.getRawPath());
        }
        request.headers().set(HttpHeaderNames.HOST, uri.getHost());
        request.headers().set(HttpHeaderNames.AUTHORIZATION, _authCode);
        request.headers().set("Content-Type","application/json;charset=utf-8");

        LOG.info("Sending request. " + request);
        LOG.info("Send body: " + content);
        _channel.writeAndFlush(request);
        try {
            _channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ResponseWrapper sendGet(String url) throws APIConnectionException, APIRequestException {
        return sendGet(url, null);
    }

    public ResponseWrapper sendGet(String url, String content) throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        try {
            return sendHttpRequest(HttpMethod.GET, url, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper sendPut(String url, String content) throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        try {
            return sendHttpRequest(HttpMethod.PUT, url, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    @Override
    public ResponseWrapper sendPost(String url, String content) throws APIConnectionException, APIRequestException {
        ResponseWrapper wrapper = new ResponseWrapper();
        try {
            return sendHttpRequest(HttpMethod.POST, url, content);
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
        ResponseWrapper wrapper = new ResponseWrapper();
        try {
            return sendHttpRequest(HttpMethod.DELETE, url, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrapper;
    }



    private ResponseWrapper sendHttpRequest(HttpMethod method, String url, String body) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        NettyClientInitializer initializer = new NettyClientInitializer(_sslCtx, null, latch);
        b.handler(initializer);
        ResponseWrapper wrapper = new ResponseWrapper();
        URI uri = new URI(url);
        String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
        String host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        try {
            ChannelFuture connect = b.connect(host, port);
            _channel = connect.sync().channel();
            FullHttpRequest request;
            if (null != body) {
                ByteBuf byteBuf = Unpooled.copiedBuffer(body.getBytes(CharsetUtil.UTF_8));
                request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri.getRawPath(), byteBuf);
                request.headers().set(HttpHeaderNames.CONTENT_LENGTH, (long) byteBuf.readableBytes());
            } else {
                request = new DefaultFullHttpRequest(HTTP_1_1, method, uri.getRawPath());
            }
            request.headers().set(HttpHeaderNames.HOST, uri.getHost());
            request.headers().set(HttpHeaderNames.AUTHORIZATION, _authCode);
            request.headers().set("Content-Type","application/json;charset=utf-8");
            connect.awaitUninterruptibly();
            LOG.info("Sending request. " + request);
            LOG.info("Send body: " + body);
            _channel.writeAndFlush(request);
            latch.await();
            wrapper = initializer.getResponse();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    public void send(ByteBuf body, HttpMethod method, URI uri) {
        String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
        String host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }
        _channel = b.connect(host, port).syncUninterruptibly().channel();
        HttpRequest request;
        if (null != body) {
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri.getRawPath(), body);
            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, (long) body.readableBytes());
        } else {
            request = new DefaultFullHttpRequest(HTTP_1_1, method, uri.getRawPath());
        }
        request.headers().set(HttpHeaderNames.HOST, uri.getHost());
        request.headers().set(HttpHeaderNames.AUTHORIZATION, _authCode);
        request.headers().set("Content-Type","application/json;charset=utf-8");
        LOG.info("Sending request. " + request);
        LOG.info("Send body: " + body);
        _channel.writeAndFlush(request);
    }


    public void close() {
        if (null != _channel) {
            _channel.closeFuture().syncUninterruptibly();
            _workerGroup.shutdownGracefully();
            _channel = null;
            _workerGroup = null;
        }
        System.out.println("Finished request(s)");
    }

    public interface BaseCallback {
        public void onSucceed(ResponseWrapper wrapper);
    }
}
