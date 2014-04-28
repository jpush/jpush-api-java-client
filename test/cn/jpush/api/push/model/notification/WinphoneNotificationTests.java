package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class WinphoneNotificationTests {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal() {
        WinphoneNotification winphone = WinphoneNotification.newBuilder().build();
        Assert.assertEquals("", "", winphone.toJSON());
    }
    
    @Test
    public void testQuickAlert() {
        WinphoneNotification winphone = WinphoneNotification.alert("aaa");
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("aaa"));
        Assert.assertEquals("", json, winphone.toJSON());
    }

    @Test
    public void testTitle() {
        WinphoneNotification winphone = WinphoneNotification.newBuilder().setTitle("title").build();
        JsonObject json = new JsonObject();
        json.add("title", new JsonPrimitive("title"));
        Assert.assertEquals("", json, winphone.toJSON());
    }
    
    @Test
    public void testExtra() {
        WinphoneNotification winphone = WinphoneNotification.newBuilder().addExtra("key", "value").build();
        JsonObject json = new JsonObject();
        JsonObject extra = new JsonObject();
        extra.add("key", new JsonPrimitive("value"));
        json.add("extras", extra);
        Assert.assertEquals("", json, winphone.toJSON());
    }
    

}


