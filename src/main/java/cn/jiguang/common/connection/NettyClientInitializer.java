package cn.jiguang.common.connection;

import cn.jiguang.common.resp.ResponseWrapper;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.ssl.SslContext;

import java.util.concurrent.CountDownLatch;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    private SslContext _sslCtx;
    private NettyHttpClient.BaseCallback _callback;
    private CountDownLatch _latch;
    private HttpResponseHandler _handler;

    public NettyClientInitializer(SslContext sslContext, NettyHttpClient.BaseCallback callback, CountDownLatch latch) {
        this._sslCtx = sslContext;
        this._callback = callback;
        this._latch = latch;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        this._handler = new HttpResponseHandler(_callback, _latch);
        socketChannel.pipeline().addLast(_sslCtx.newHandler(socketChannel.alloc()), new HttpClientCodec(), _handler);
    }

    public void resetLatch(CountDownLatch latch) {
        _handler.resetLatch(latch);
    }

    public ResponseWrapper getResponse() {
        return _handler.getResponse();
    }
}
