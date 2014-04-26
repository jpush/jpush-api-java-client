package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class NotificationTests {
    
    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        Notification.newBuilder().build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegal() {
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(
                        AndroidNotification.newBuilder().build())
                .build();
        Assert.assertEquals("", "", notification.toJSON());
    }
    
    @Test
    public void testAlertAndroid() {
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(AndroidNotification.alert("alert"))
                .build();
        JsonObject json = new JsonObject();
        JsonObject android = new JsonObject();
        android.add("alert", new JsonPrimitive("alert"));
        json.add("android", android);
        Assert.assertEquals("", json, notification.toJSON());
    }
    
    @Test
    public void testAlertIos() {
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(IosNotification.alert("alert"))
                .build();
        JsonObject json = new JsonObject();
        JsonObject ios = new JsonObject();
        ios.add("alert", new JsonPrimitive("alert"));
        ios.add("sound", new JsonPrimitive(""));
        json.add("ios", ios);
        Assert.assertEquals("", json, notification.toJSON());
    }
    
    @Test
    public void testAlertMpns() {
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(MpnsNotification.alert("alert"))
                .build();
        JsonObject json = new JsonObject();
        JsonObject mpns = new JsonObject();
        mpns.add("alert", new JsonPrimitive("alert"));
        json.add("mpns", mpns);
        Assert.assertEquals("", json, notification.toJSON());
    }
    
    @Test
    public void testAlertAll() {
        Notification notification = Notification.alert("alert");
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("alert"));
                
        Assert.assertEquals("", json, notification.toJSON());
    }

}
