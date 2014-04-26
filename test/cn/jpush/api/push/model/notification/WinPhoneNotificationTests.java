package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class WinPhoneNotificationTests {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal() {
        WinPhoneNotification mpns = WinPhoneNotification.newBuilder().build();
        Assert.assertEquals("", "", mpns.toJSON());
    }
    
    @Test
    public void testQuickAlert() {
        WinPhoneNotification mpns = WinPhoneNotification.alert("aaa");
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("aaa"));
        Assert.assertEquals("", json, mpns.toJSON());
    }

    @Test
    public void testTitle() {
        WinPhoneNotification mpns = WinPhoneNotification.newBuilder().setTitle("title").build();
        JsonObject json = new JsonObject();
        json.add("title", new JsonPrimitive("title"));
        Assert.assertEquals("", json, mpns.toJSON());
    }
    
    @Test
    public void testExtra() {
        WinPhoneNotification mpns = WinPhoneNotification.newBuilder().addExtra("key", "value").build();
        JsonObject json = new JsonObject();
        JsonObject extra = new JsonObject();
        extra.add("key", new JsonPrimitive("value"));
        json.add("extras", extra);
        Assert.assertEquals("", json, mpns.toJSON());
    }
    

}


