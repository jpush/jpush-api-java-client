package cn.jiguang.common.connection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class ClientInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new HttpResponseHandler());
    }
}
