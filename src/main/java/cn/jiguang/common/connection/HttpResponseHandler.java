package cn.jiguang.common.connection;

import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;


@ChannelHandler.Sharable
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
                            _wrapper.setErrorObject();
                            break;
                        case 401:
                            LOG.error("Authentication failed! Please check authentication params according to docs.");
                            _wrapper.setErrorObject();
                            break;
                        case 403:
                            LOG.error("Request is forbidden! Maybe your appkey is listed in blacklist or your params is invalid.");
                            _wrapper.setErrorObject();
                            break;
                        case 404:
                            LOG.error("Request page is not found! Maybe your params is invalid.");
                            _wrapper.setErrorObject();
                            break;
                        case 410:
                            LOG.error("Request resource is no longer in service. Please according to notice on official website.");
                            _wrapper.setErrorObject();
                        case 429:
                            LOG.error("Too many requests! Please review your appkey's request quota.");
                            _wrapper.setErrorObject();
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
                if (null != _latch) {
                    _latch.countDown();
                }
                if (null != _callback) {
                    _callback.onSucceed(_wrapper);
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
