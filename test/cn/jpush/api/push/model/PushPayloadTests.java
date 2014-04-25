package cn.jpush.api.push.model;

import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class PushPayloadTests {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal_OnlyAudience() {
        Audience audience = Audience.all();
        PushPayload.newBuilder().setAudience(audience).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal_OnlyPlatform() {
        Platform platform = Platform.all();
        PushPayload.newBuilder().setPlatform(platform).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal_PlatformAudience() {
        Platform platform = Platform.all();
        Audience audience = Audience.all();
        PushPayload.newBuilder().setPlatform(platform).setAudience(audience).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal_NoAudience() {
        Platform platform = Platform.all();
        Notification notifcation = Notification.alert("alert");
        PushPayload.newBuilder().setPlatform(platform).setNotification(notifcation).build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegal_NoPlatform() {
        Audience audience = Audience.all();
        Notification notifcation = Notification.alert("alert");
        PushPayload.newBuilder().setAudience(audience).setNotification(notifcation).build();
    }
    
    public void testNormal() {
        Platform platform = Platform.all();
        Audience audience = Audience.all();
        Notification notifcation = Notification.alert("alert");
        PushPayload payload = PushPayload.newBuilder().setAudience(audience).setNotification(notifcation).build();
        
        Assert.assertEquals("", getNotificationAlert("alert"), payload.toJSON());
    }

    public JsonObject getNotificationAlert(String alert) {
        JsonObject json = new JsonObject();
        JsonObject android = new JsonObject();
        android.add("alert", new JsonPrimitive("alert"));
        json.add("alert", new JsonPrimitive("alert"));
        json.add("android", android);
        return json;
    }
}


