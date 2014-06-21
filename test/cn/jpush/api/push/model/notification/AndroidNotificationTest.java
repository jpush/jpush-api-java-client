package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class AndroidNotificationTest {

    @Test
    public void testNoParams() {
        AndroidNotification an = AndroidNotification.newBuilder().build();
        Assert.assertEquals("", new JsonObject(), an.toJSON());
    }
    
    @Test
    public void testQuickAlert() {
        AndroidNotification an = AndroidNotification.alert("alert");
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("alert"));
        Assert.assertEquals("", json, an.toJSON());
    }

    @Test
    public void testTitle() {
        AndroidNotification an = AndroidNotification.newBuilder().setTitle("title").build();
        JsonObject json = new JsonObject();
        json.add("title", new JsonPrimitive("title"));
        Assert.assertEquals("", json, an.toJSON());
    }
    
    @Test
    public void testExtra() {
        AndroidNotification an = AndroidNotification.newBuilder()
                .addExtra("key2", 222)
                .addExtra("key1", "value1").build();
        JsonObject json = new JsonObject();
        JsonObject extra = new JsonObject();
        extra.add("key1", new JsonPrimitive("value1"));
        extra.add("key2", new JsonPrimitive(222));
        json.add("extras", extra);
        Assert.assertEquals("", json, an.toJSON());
    }
    

}


