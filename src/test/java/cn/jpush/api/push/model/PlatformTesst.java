package cn.jpush.api.push.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import cn.jiguang.common.DeviceType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import cn.jpush.api.FastTests;

@Category(FastTests.class)
public class PlatformTesst {

    @Test
    public void testAll() {
        Platform all = Platform.all();
        Assert.assertEquals("test", new JsonPrimitive("all"), all.toJSON());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotAll() {
        Platform all = Platform.newBuilder().setAll(false).build();
        
        assertThat(all.toJSON(), is((JsonElement) new JsonPrimitive("all")));
    }

    @Test
    public void testAndroid() {
        Platform android = Platform.newBuilder().addDeviceType(DeviceType.Android).build();
        JsonArray array = new JsonArray();
        array.add(new JsonPrimitive("android"));
        
        assertThat(android.toJSON(), is((JsonElement) array));
    }
    
}



