package cn.jpush.api.push.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.jpush.api.FastTests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@Category(FastTests.class)
public class MessageTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegal() {
        Message.newBuilder().build();
    }
    
    @Test
    public void testMsgContent() {
        Message message = Message.content("msg content");
        
        JsonObject json = new JsonObject();
        json.add("msg_content", new JsonPrimitive("msg content"));
        
        assertThat(message.toJSON(), is((JsonElement) json));
    }

    @Test
    public void testMsgContentAndExtras() {
        Message message = Message.newBuilder()
                .setMsgContent("msgContent")
                .addExtra("key1", "value1")
                .addExtra("key2", 222)
                .addExtra("key3", Boolean.FALSE).build();
        
        JsonObject json = new JsonObject();
        json.add("msg_content", new JsonPrimitive("msgContent"));
        
        JsonObject extras = new JsonObject();
        extras.add("key1", new JsonPrimitive("value1"));
        extras.add("key2", new JsonPrimitive(222));
        extras.add("key3", new JsonPrimitive(Boolean.FALSE));
        
        json.add("extras", extras);
        
        assertThat(message.toJSON(), is((JsonElement) json));
    }
    
    @Test
    public void testMsgContentAndExtrasMap() {
        Map<String, String> extrasMap = new HashMap<String, String>();
        extrasMap.put("key1", "value1");
        extrasMap.put("key2", "value2");
        
        Message message = Message.newBuilder()
                .setMsgContent("msgContent")
                .addExtras(extrasMap).build();
        
        JsonObject json = new JsonObject();
        json.add("msg_content", new JsonPrimitive("msgContent"));
        
        JsonObject extras = new JsonObject();
        extras.add("key1", new JsonPrimitive("value1"));
        extras.add("key2", new JsonPrimitive("value2"));
        json.add("extras", extras);
        
        assertThat(message.toJSON(), is((JsonElement) json));
    }


}
