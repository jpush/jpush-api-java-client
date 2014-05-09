package cn.jpush.api.push;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

public class MessageTests {
    private static final String appKey ="dd1066407b044738b6479275";
    private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
    
    private static final String ALERT = "JPush Test - alert";
    private static final String MSG_CONTENT = "JPush Test - msgContent";
    private static final int SUCCEED_RESULT_CODE = 0;
	
    private JPushClient _client = null;
    
    @Before
    public void before() {
        _client = new JPushClient(masterSecret, appKey);
    }
	
	
    @Test
    public void sendMessageContentOnly() {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setMessage(Message.newBuilder().setMsgContent(MSG_CONTENT).build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendMessageContentAndTitle() {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setMessage(Message.newBuilder()
                        .setTitle("message title")
                        .setContentType("content type")
                        .setMsgContent(MSG_CONTENT).build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendMessageContentAndExtras() {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setMessage(Message.newBuilder()
                        .addExtra("key1", "value1")
                        .addExtra("key2", "value2")
                        .setMsgContent(MSG_CONTENT).build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    
}

