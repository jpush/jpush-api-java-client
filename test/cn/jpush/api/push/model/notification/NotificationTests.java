package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;

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
        JsonObject android = new JsonObject();
        android.add("alert", new JsonPrimitive("alert"));
        json.add("alert", new JsonPrimitive("alert"));
        json.add("android", android);
        
        Assert.assertEquals("", json, notification.toJSON());

    }

}
