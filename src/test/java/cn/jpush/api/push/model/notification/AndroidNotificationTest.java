package cn.jpush.api.push.model.notification;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.jpush.api.FastTests;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Map;

@Category(FastTests.class)
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
    
    @Test
    public void testExtra_nullvalue() {
        String value2 = "value2";
        value2 = null;
        AndroidNotification an = AndroidNotification.newBuilder()
                .addExtra("key2", value2)
                .addExtra("key1", "value1").build();
        JsonObject json = new JsonObject();
        JsonObject extra = new JsonObject();
        extra.add("key1", new JsonPrimitive("value1"));
        json.add("extras", extra);
        Assert.assertEquals("", json, an.toJSON());
    }

    @Test
    public void testCustomParam() {

        Map<String, String> customParams = new HashMap<String, String>();
        customParams.put("custom_field1", "field1");
        customParams.put("custom_field2", "field2");
        customParams.put("custom_field3", "field3");

        AndroidNotification an = AndroidNotification.newBuilder()
                .addCustom("custom_field_num", 1)
                .addCustom("custom_field_string", "string")
                .addCustom("custom_field_boolean", true)
                .addCustom(customParams)
                .build();
        Assert.assertEquals("{\"custom_field_num\":1,\"custom_field_string\":\"string\",\"custom_field_boolean\":true,\"custom_field1\":\"field1\",\"custom_field3\":\"field3\",\"custom_field2\":\"field2\"}", an.toJSON().toString());
    }
    

}


