package cn.jpush.api.push.remote;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.jpush.api.SlowTests;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

@Category(SlowTests.class)
public class MessageTest extends BaseRemotePushTest {
	
    @Test
    public void sendMessageContentOnly() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setMessage(Message.newBuilder().setMsgContent(MSG_CONTENT).build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    @Test
    public void sendMessageContentAndTitle() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setMessage(Message.newBuilder()
                        .setTitle("message title")
                        .setContentType("content type")
                        .setMsgContent(MSG_CONTENT).build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    @Test
    public void sendMessageContentAndExtras() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setMessage(Message.newBuilder()
                        .addExtra("key1", "value1")
                        .addExtra("key2", 222)
                        .addExtra("key3", Boolean.FALSE)
                        .setMsgContent(MSG_CONTENT).build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    
}

