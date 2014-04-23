package cn.jpush.api.push.model;


import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.push.model.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class OptionalTests {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalSendno() {
        Optional.newBuilder().setSendno(-1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalOverrideMsgId() {
        Optional.newBuilder().setOverrideMsgId(-1).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalTimeToLive() {
        Optional.newBuilder().setTimeToLive(-1).build();
    }

    @Test
    public void testSendno() {
        JsonObject json = new JsonObject();
        json.add("sendno", new JsonPrimitive(111));
        Optional optional = Optional.newBuilder().setSendno(111).build();
        Assert.assertEquals("", json, optional.toJSON());
    }

    @Test
    public void testApnsProduction() {
        JsonObject json = new JsonObject();
        json.add("apns_production", new JsonPrimitive(false));
        Optional optional = Optional.newBuilder().setApnsProduction(false).build();
        Assert.assertEquals("", json, optional.toJSON());
    }

}

