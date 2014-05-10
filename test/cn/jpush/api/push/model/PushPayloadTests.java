package cn.jpush.api.push.model;

import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.common.ServiceHelper;
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
    public void testNotification() {
        int sendno = ServiceHelper.generateSendno();
        Notification notifcation = Notification.alert("alert");
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setOptions(Options.sendno(sendno))
                .setNotification(notifcation).build();
        
        JsonObject json = new JsonObject();
        json.add("audience", new JsonPrimitive("all"));
        json.add("platform", new JsonPrimitive("all"));
        
        JsonObject noti = new JsonObject();
        noti.add("alert", new JsonPrimitive("alert"));
        json.add("notification", noti);
        
        JsonObject options = new JsonObject();
        options.add("sendno", new JsonPrimitive(sendno));
        json.add("options", options);
        
        Assert.assertEquals("", json, payload.toJSON());
    }

    @Test
    public void testMessage() {
        int sendno = ServiceHelper.generateSendno();
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setOptions(Options.sendno(sendno))
                .setMessage(Message.content("message")).build();
        
        JsonObject json = new JsonObject();
        json.add("audience", new JsonPrimitive("all"));
        json.add("platform", new JsonPrimitive("all"));
        
        JsonObject msg = new JsonObject();
        msg.add("msg_content", new JsonPrimitive("message"));
        json.add("message", msg);
        
        JsonObject options = new JsonObject();
        options.add("sendno", new JsonPrimitive(sendno));
        json.add("options", options);
        
        Assert.assertEquals("", json, payload.toJSON());
    }

}


