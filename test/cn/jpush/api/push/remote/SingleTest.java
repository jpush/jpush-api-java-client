package cn.jpush.api.push.remote;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class SingleTest extends BaseRemotePushTest {
    
    @Test
    public void sendSimpleMessageAndNotification_Pall() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert("Pall Nall Mall alert"))
                .setMessage(Message.content("Pall Nall Mall msg"))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
}

