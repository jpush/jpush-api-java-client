package cn.jpush.api.push;

import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.push.model.Audience;
import cn.jpush.api.push.model.Notification;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;

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
    
    @Test
    public void testIllegal_NoPlatform() {
        Audience audience = Audience.all();
        Notification notifcation = Notification.alert("alert");
        PushPayload push = PushPayload.newBuilder().setAudience(audience).setNotification(notifcation).build();
        
        JsonObject json = new JsonObject();
        json.add("audience", new JsonPrimitive("all"));
        json.add("platform", new JsonPrimitive("all"));
        json.add("notification", getNotificationAlert("alert"));
        Assert.assertEquals("", json, push.toJSON());
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


