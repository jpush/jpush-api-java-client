package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class IosNotificationTests {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal() {
        IosNotification ios = IosNotification.newBuilder().build();
        Assert.assertEquals("", "", ios.toJSON());
    }
    
    @Test
    public void testQuickAlert() {
        IosNotification ios = IosNotification.alert("aaa");
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("aaa"));
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testBadge_0() {
        IosNotification ios = IosNotification.newBuilder().setBadge(0).build();
        JsonObject json = new JsonObject();
        json.add("badge", new JsonPrimitive(0));
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testBadge_1() {
        IosNotification ios = IosNotification.newBuilder().setBadge(1).build();
        JsonObject json = new JsonObject();
        json.add("badge", new JsonPrimitive(1));
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testSound() {
        IosNotification ios = IosNotification.newBuilder().setSound("sound").build();
        JsonObject json = new JsonObject();
        json.add("sound", new JsonPrimitive("sound"));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testSoundDisabled() {
        IosNotification ios = IosNotification.newBuilder()
                .setAlert("alert")
                .setSound("sound").disableSound().build();
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("alert"));
        Assert.assertEquals("", json, ios.toJSON());
    }

    
    @Test
    public void testExtra() {
        IosNotification ios = IosNotification.newBuilder().addExtra("key", "value").build();
        JsonObject json = new JsonObject();
        JsonObject extra = new JsonObject();
        extra.add("key", new JsonPrimitive("value"));
        json.add("extras", extra);
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }
    

}


