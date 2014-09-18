package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class IosNotificationTest {

    @Test
    public void testEmpty() {
        IosNotification ios = IosNotification.newBuilder().build();
        JsonObject json = new JsonObject();
        json.add("sound", new JsonPrimitive(""));
        json.add("badge", new JsonPrimitive("+1"));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testQuickAlert() {
        IosNotification ios = IosNotification.alert("aaa");
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("aaa"));
        json.add("sound", new JsonPrimitive(""));
        json.add("badge", new JsonPrimitive("+1"));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testBadge_0() {
        IosNotification ios = IosNotification.newBuilder().setBadge(0).build();
        JsonObject json = new JsonObject();
        json.add("badge", new JsonPrimitive("0"));
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testBadge_2() {
        IosNotification ios = IosNotification.newBuilder().setBadge(2).build();
        JsonObject json = new JsonObject();
        json.add("badge", new JsonPrimitive("2"));
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testBadge_auto() {
        IosNotification ios = IosNotification.newBuilder().autoBadge().build();
        JsonObject json = new JsonObject();
        json.add("badge", new JsonPrimitive("+1"));
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testBadge_plus_2() {
        IosNotification ios = IosNotification.newBuilder().incrBadge(2).build();
        JsonObject json = new JsonObject();
        json.add("badge", new JsonPrimitive("+2"));
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testBadge_plus_0() {
        IosNotification ios = IosNotification.newBuilder().incrBadge(0).build();
        JsonObject json = new JsonObject();
        json.add("badge", new JsonPrimitive("+0"));
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testBadge_minus_2() {
        IosNotification ios = IosNotification.newBuilder().incrBadge(-2).build();
        JsonObject json = new JsonObject();
        json.add("badge", new JsonPrimitive("-2"));
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testSound() {
        IosNotification ios = IosNotification.newBuilder().setSound("sound").build();
        JsonObject json = new JsonObject();
        json.add("sound", new JsonPrimitive("sound"));
        json.add("badge", new JsonPrimitive("+1"));
        Assert.assertEquals("", json, ios.toJSON());
    }
    
    @Test
    public void testSoundDisabled() {
        IosNotification ios = IosNotification.newBuilder()
                .setSound("sound")
                .disableSound()
                .setAlert("alert")
                .build();
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("alert"));
        json.add("badge", new JsonPrimitive("+1"));
        Assert.assertEquals("", json, ios.toJSON());
    }

    @Test
    public void testBadgeDisabled() {
        IosNotification ios = IosNotification.newBuilder()
                .disableBadge()
                .setAlert("alert")
                .build();
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("alert"));
        json.add("sound", new JsonPrimitive(""));
        Assert.assertEquals("", json, ios.toJSON());
    }

    
    @Test
    public void testExtra() {
        IosNotification ios = IosNotification.newBuilder()
                .addExtra("key2", Boolean.TRUE)
                .addExtra("key", "value").build();
        JsonObject json = new JsonObject();
        JsonObject extra = new JsonObject();
        extra.add("key", new JsonPrimitive("value"));
        extra.add("key2", new JsonPrimitive(Boolean.TRUE));
        json.add("extras", extra);
        json.add("sound", new JsonPrimitive(""));
        json.add("badge", new JsonPrimitive("+1"));
        Assert.assertEquals("", json, ios.toJSON());
    }
    

}


