package cn.jpush.api.push.model;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class MessageTests {
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegal() {
        Message.newBuilder().build();
    }
    
    @Test
    public void testMsgContent() {
        Message message = Message.content("msg content");
        
        JsonObject json = new JsonObject();
        json.add("msg_content", new JsonPrimitive("msg content"));
        
        Assert.assertEquals("", json, message.toJSON());

    }

}
