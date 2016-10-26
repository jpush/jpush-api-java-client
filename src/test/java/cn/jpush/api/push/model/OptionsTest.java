package cn.jpush.api.push.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.ServiceHelper;
import cn.jpush.api.FastTests;

@Category(FastTests.class)
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
        Options.newBuilder().setTimeToLive(-2).build();
    }

    @Test
    public void testSendno() {
        JsonObject json = new JsonObject();
        json.add("sendno", new JsonPrimitive(111));
        json.add("apns_production", new JsonPrimitive(false));
        Options options = Options.newBuilder().setSendno(111).build();
        
        assertThat(options.toJSON(), is((JsonElement) json));
    }

    @Test
    public void testTimeToLive_int() {
        JsonObject json = new JsonObject();
        json.add("sendno", new JsonPrimitive(111));
        json.add("apns_production", new JsonPrimitive(false));
        json.add("time_to_live", new JsonPrimitive(640));
        
        Options options = Options.newBuilder()
                .setSendno(111)
                .setTimeToLive(640).build();
        
        assertThat(options.toJSON(), is((JsonElement) json));
    }

    @Test
    public void testTimeToLive_0() {
        JsonObject json = new JsonObject();
        json.add("sendno", new JsonPrimitive(111));
        json.add("apns_production", new JsonPrimitive(false));
        json.add("time_to_live", new JsonPrimitive(0));
        
        Options options = Options.newBuilder()
                .setSendno(111)
                .setTimeToLive(0).build();
        
        assertThat(options.toJSON(), is((JsonElement) json));
    }
    
    @Test
    public void testTimeToLive_default() {
        JsonObject json = new JsonObject();
        json.add("sendno", new JsonPrimitive(111));
        json.add("apns_production", new JsonPrimitive(false));
        
        Options options = Options.newBuilder()
                .setSendno(111)
                .setTimeToLive(-1).build();
        
        assertThat(options.toJSON(), is((JsonElement) json));
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
        
        assertThat(options.toJSON(), is((JsonElement) json));
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
        
        assertThat(options.toJSON(), is((JsonElement) json));
    }
    
    @Test
    public void testBigPushDuration() {
        int sendno = ServiceHelper.generateSendno();
        Options options = Options.newBuilder()
        		.setSendno(sendno)
                .setBigPushDuration(10)
                .build();
        
        JsonObject json = new JsonObject();
        json.add("sendno", new JsonPrimitive(sendno));
        json.add("big_push_duration", new JsonPrimitive(10));
        json.add("apns_production", new JsonPrimitive(false));
        
        assertThat(options.toJSON(), is((JsonElement) json));
    }
}

