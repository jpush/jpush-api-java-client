package cn.jpush.api.push.remote;

import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.SlowTests;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertTrue;

@Category(SlowTests.class)
public class NotificationTest extends BaseRemotePushTest {
    
    @Test
    public void sendNotification_alert_json() throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("key1", "value1");
        json.addProperty("key2", true);
        
        String alert = json.toString();
        System.out.println(alert);
        
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(alert)
                                .setTitle("title").build()).build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
	
    // --------------- Android
	
    @Test
    public void sendNotification_android_title() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(ALERT)
                                .setTitle("title").build()).build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    @Test
    public void sendNotification_android_buildId() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(ALERT)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setBuilderId(100)
                                .build()).build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    @Test
    public void sendNotification_android_extras() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(ALERT)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("key1", "value1")
                                .addExtra("key2", 222)
                                .build()).build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    
    // ------------------ ios
    
    @Test
    public void sendNotification_ios_badge() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.ios())
                .setNotification(Notification.ios_auto_badge())
                .build();
        try {
            PushResult result = _client.sendPush(payload);
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendNotification_ios_alert_jsonStr() throws Exception {
        JsonObject alert = new JsonObject();
        alert.add("title", new JsonPrimitive("Game Request"));
        alert.add("body", new JsonPrimitive("Bob wants to play poker"));
        alert.add("action-loc-key", new JsonPrimitive("PLAY"));
        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.ios())
                .setNotification(Notification.alert(alert.toString()))
                .build();
        try {
            PushResult result = _client.sendPush(payload);
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendNotification_ios_alert_jsonObj() throws Exception {
        IosAlert alert = IosAlert.newBuilder()
                .setTitleAndBody("ios title", "test subtitle", "test ios title")
                .build();

        PushPayload payload = PushPayload.newBuilder()
                .setAudience(Audience.all())
                .setPlatform(Platform.ios())
                .setNotification(Notification.alert(alert))
                .build();
        try {
            PushResult result = _client.sendPush(payload);
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

}

