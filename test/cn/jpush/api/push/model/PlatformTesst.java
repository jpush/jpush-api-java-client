package cn.jpush.api.push.model;

import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.common.DeviceType;
import cn.jpush.api.push.model.Platform;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class PlatformTesst {

    @Test
    public void testAll() {
        Platform all = Platform.all();
        Assert.assertEquals("test", new JsonPrimitive("all"), all.toJSON());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotAll() {
        Platform all = Platform.newBuilder().setAll(false).build();
        Assert.assertNotEquals("test", new JsonPrimitive("all"), all.toJSON());
    }

    @Test
    public void testAndroid() {
        Platform android = Platform.newBuilder().addDeviceType(DeviceType.Android).build();
        JsonArray array = new JsonArray();
        array.add(new JsonPrimitive("android"));
        Assert.assertEquals("test", array, android.toJSON());
    }
    
}



