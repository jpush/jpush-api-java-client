package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.jpush.api.FastTests;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Category(FastTests.class)
public class NotificationTest {
    
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
    
    @Test(expected = IllegalArgumentException.class)
    public void testNoAlert() {
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(
                        AndroidNotification.newBuilder().setTitle("title").build())
                .build();
        Assert.assertEquals("", "", notification.toJSON());
    }
    
    @Test
    public void testAlert_android() {
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
    public void testAlert_ios() {
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(IosNotification.alert("alert"))
                .build();
        JsonObject json = new JsonObject();
        JsonObject ios = new JsonObject();
        ios.add("alert", new JsonPrimitive("alert"));
        ios.add("sound", new JsonPrimitive(""));
        ios.add("badge", new JsonPrimitive("+1"));
        json.add("ios", ios);
        Assert.assertEquals("", json, notification.toJSON());
    }
    
    @Test
    public void testAlert_winphone() {
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(WinphoneNotification.alert("alert"))
                .build();
        JsonObject json = new JsonObject();
        JsonObject winphone = new JsonObject();
        winphone.add("alert", new JsonPrimitive("alert"));
        json.add("winphone", winphone);
        Assert.assertEquals("", json, notification.toJSON());
    }
    
    @Test
    public void testAlert_all() {
        Notification notification = Notification.alert("alert");
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("alert"));
        
        Assert.assertEquals("", json, notification.toJSON());
    }

    @Test
    public void testExtras_jsonValue() {
    	JsonObject extraValue = new JsonObject();
    	extraValue.add("v_key", new JsonPrimitive("v_value"));
    	
        Notification notification = Notification
        		.newBuilder()
        		.addPlatformNotification(
        				AndroidNotification
        					.newBuilder()
        					.setAlert("alert")
        					.addExtra("key", extraValue)
        					.build())
        		.build();
        
        JsonObject json = new JsonObject();
        JsonObject android = new JsonObject();
        android.add("alert", new JsonPrimitive("alert"));
        
        JsonObject extra = new JsonObject();
        extra.add("key", extraValue);
        android.add("extras", extra);
        
        json.add("android", android);
        
        Assert.assertEquals("", json, notification.toJSON());
    }


    
    
    @Test
    public void testShortcut_android() {
        Notification notification = Notification.android("alert", "title", null);
        JsonObject json = new JsonObject();
        JsonObject android = new JsonObject();
        android.add("alert", new JsonPrimitive("alert"));
        android.add("title", new JsonPrimitive("title"));
        json.add("android", android);
                
        Assert.assertEquals("", json, notification.toJSON());
    }

    
    
}
