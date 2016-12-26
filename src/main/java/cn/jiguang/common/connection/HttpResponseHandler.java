package cn.jiguang.common.connection;

import cn.jiguang.common.resp.ResponseWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;


public class HttpResponseHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger LOG = LoggerFactory.getLogger(HttpResponseHandler.class);
    private int status;
    private NettyHttpClient.BaseCallback _callback;
    private CountDownLatch _latch;
    private ResponseWrapper _wrapper = new ResponseWrapper();

    public HttpResponseHandler(NettyHttpClient.BaseCallback callback, CountDownLatch latch) {
        this._callback = callback;
        this._latch = latch;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            status = response.status().code();
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            LOG.info(content.content().toString());
            if (content instanceof LastHttpContent) {
                LOG.info("closing connection");
                ctx.close();
            } else {
                String responseContent = content.content().toString(CharsetUtil.UTF_8);
                _wrapper.responseCode = status;
                _wrapper.responseContent = responseContent;
                LOG.info("Got Response code: " + status + " content: " + responseContent);
                System.err.println("Got Response code: " + status + " content: " + responseContent);
                System.err.flush();
                if (null != _callback) {
                    _callback.onSucceed(_wrapper);
                }
                if (null != _latch) {
                    _latch.countDown();
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.error("error:", cause);
        try {
            ctx.close();
        }catch (Exception ex) {
            LOG.error("close error:", ex);
        }
    }

    public void resetLatch(CountDownLatch latch) {
        this._latch = latch;
    }

    public ResponseWrapper getResponse() {
        return _wrapper;
    }
}
