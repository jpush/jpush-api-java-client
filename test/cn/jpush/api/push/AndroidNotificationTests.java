package cn.jpush.api.push;

import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.push.model.AndroidNotification;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class AndroidNotificationTests {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal() {
        AndroidNotification an = AndroidNotification.newBuilder().build();
        Assert.assertEquals("", "", an.toJSON());
    }
    
    @Test
    public void testQuickAlert() {
        AndroidNotification an = AndroidNotification.alert("aaa");
        JsonObject json = new JsonObject();
        json.add("alert", new JsonPrimitive("aaa"));
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
        AndroidNotification an = AndroidNotification.newBuilder().addExtra("key", "value").build();
        JsonObject json = new JsonObject();
        JsonObject extra = new JsonObject();
        extra.add("key", new JsonPrimitive("value"));
        json.add("extras", extra);
        Assert.assertEquals("", json, an.toJSON());
    }
    

}


