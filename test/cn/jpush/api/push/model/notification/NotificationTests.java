package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class NotificationTests {
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegal() {
        Notification.newBuilder().setAlert("alert").build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegal2() {
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(
                        AndroidNotification.newBuilder().build())
                .build();
        Assert.assertEquals("", "", notification.toJSON());
    }
    
    @Test
    public void testAlertAll() {
        Notification notification = Notification.alert("alert");
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("alert"));
        
        JsonObject android = new JsonObject();
        android.add("alert", new JsonPrimitive("alert"));
        
        JsonObject ios = new JsonObject();
        ios.add("alert", new JsonPrimitive("alert"));
        ios.add("sound", new JsonPrimitive(""));

        JsonObject mpns = new JsonObject();
        mpns.add("alert", new JsonPrimitive("alert"));

        json.add("android", android);
        json.add("ios", ios);
        json.add("mpns", mpns);
        
        Assert.assertEquals("", json, notification.toJSON());

    }

}
