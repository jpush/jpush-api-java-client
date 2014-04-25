package cn.jpush.api.push.model;


import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.push.model.Options;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class OptionsTests {

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
        Options optional = Options.newBuilder().setSendno(111).build();
        Assert.assertEquals("", json, optional.toJSON());
    }

    @Test
    public void testApnsProduction() {
        JsonObject json = new JsonObject();
        json.add("apns_production", new JsonPrimitive(false));
        Options optional = Options.newBuilder().setApnsProduction(false).build();
        Assert.assertEquals("", json, optional.toJSON());
    }

}

