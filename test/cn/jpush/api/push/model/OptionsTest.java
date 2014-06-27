package cn.jpush.api.push.model;

import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.common.ServiceHelper;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class OptionsTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalSendno() {
        Options.newBuilder().setSendno(-1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalOverrideMsgId() {
        Options.newBuilder().setOverrideMsgId(-1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalTimeToLive() {
        Options.newBuilder().setTimeToLive(-1).build();
    }

    @Test
    public void testSendno() {
        JsonObject json = new JsonObject();
        json.add("sendno", new JsonPrimitive(111));
        json.add("apns_production", new JsonPrimitive(false));
        Options options = Options.newBuilder().setSendno(111).build();
        Assert.assertEquals("", json, options.toJSON());
    }

    @Test
    public void testApnsProduction_defaultFalse() {
        int sendno = ServiceHelper.generateSendno();

        JsonObject json = new JsonObject();
        json.add("apns_production", new JsonPrimitive(false));
        json.add("sendno", new JsonPrimitive(sendno));
        
        Options options = Options.newBuilder()
                .setSendno(sendno)
                .build();
        
        Assert.assertEquals("", json, options.toJSON());
    }
    
    @Test
    public void testApnsProduction_True() {
        int sendno = ServiceHelper.generateSendno();

        JsonObject json = new JsonObject();
        json.add("apns_production", new JsonPrimitive(true));
        json.add("sendno", new JsonPrimitive(sendno));
        
        Options options = Options.newBuilder()
                .setSendno(sendno)
                .setApnsProduction(true)
                .build();
        
        Assert.assertEquals("", json, options.toJSON());
    }


}

