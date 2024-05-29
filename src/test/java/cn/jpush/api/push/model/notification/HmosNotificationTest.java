package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.jpush.api.FastTests;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Category(FastTests.class)
public class HmosNotificationTest {

    @Test
    public void testEmpty() {
        HmosNotification hmos = HmosNotification.newBuilder().build();
        Assert.assertEquals("", new JsonObject(), hmos.toJSON());
    }
    
    @Test
    public void testQuickAlert() {
        HmosNotification hmos = HmosNotification.alert("aaa");
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("aaa"));
        Assert.assertEquals("", json, hmos.toJSON());
    }

    @Test
    public void testTitle() {
        HmosNotification hmos = HmosNotification.newBuilder().setTitle("title").build();
        JsonObject json = new JsonObject();
        json.add("title", new JsonPrimitive("title"));
        Assert.assertEquals("", json, hmos.toJSON());
    }
    
    @Test
    public void testExtra() {
        HmosNotification hmos = HmosNotification.newBuilder()
                .addExtra("key2", 222)
                .addExtra("key", "value").build();
        JsonObject json = new JsonObject();
        JsonObject extra = new JsonObject();
        extra.add("key", new JsonPrimitive("value"));
        extra.add("key2", new JsonPrimitive(222));
        json.add("extras", extra);
        Assert.assertEquals("", json, hmos.toJSON());
    }
    

}


