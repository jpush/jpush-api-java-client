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
        PushPayload.newBuilder()
            .setAudience(audience)
            .setNotification(notifcation).build();
    }
    
    @Test
    public void testQuickNotification() {
        PushPayload payload = PushPayload.notificationAlertAll("alert");
        JsonObject json = new JsonObject();
        JsonObject noti = new JsonObject();
        noti.add("alert", new JsonPrimitive("alert"));
        json.add("notification", noti);
        json.add("audience", new JsonPrimitive("all"));
        json.add("platform", new JsonPrimitive("all"));
        Assert.assertEquals("", json, payload.toJSON());
    }
    
    @Test
    public void testQuickMessage() {
        PushPayload payload = PushPayload.simpleMessageAll("message");
        JsonObject json = new JsonObject();
        JsonObject msg = new JsonObject();
        msg.add("content", new JsonPrimitive("message"));
        json.add("message", msg);
        json.add("audience", new JsonPrimitive("all"));
        json.add("platform", new JsonPrimitive("all"));
        Assert.assertEquals("", json, payload.toJSON());
    }
    
    @Test
    public void testNotification() {
        Notification notifcation = Notification.alert("alert");
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(notifcation).build();
        
        JsonObject json = new JsonObject();
        JsonObject noti = new JsonObject();
        noti.add("alert", new JsonPrimitive("alert"));
        json.add("notification", noti);
        json.add("audience", new JsonPrimitive("all"));
        json.add("platform", new JsonPrimitive("all"));
        Assert.assertEquals("", json, payload.toJSON());
    }

    @Test
    public void testMessage() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setMessage(Message.content("message")).build();
        
        JsonObject json = new JsonObject();
        JsonObject msg = new JsonObject();
        msg.add("content", new JsonPrimitive("message"));
        json.add("message", msg);
        json.add("audience", new JsonPrimitive("all"));
        json.add("platform", new JsonPrimitive("all"));
        Assert.assertEquals("", json, payload.toJSON());
    }

}


