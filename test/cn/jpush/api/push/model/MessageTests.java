package cn.jpush.api.push.model;

import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.push.model.Message;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class MessageTests {
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegal() {
        Message.newBuilder().build();
    }
    
    @Test
    public void testContent() {
        Message message = Message.content("content");
        
        JsonObject json = new JsonObject();
        json.add("content", new JsonPrimitive("content"));
        
        Assert.assertEquals("", json, message.toJSON());

    }

}
