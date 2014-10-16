package cn.jpush.api.push.model.audience;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class AudienceTargetTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal() {
        AudienceTarget.newBuilder().setAudienceType(AudienceType.ALIAS).build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegal2() {
        AudienceTarget.newBuilder().addAudienceTargetValue("aaa").build();
    }
    
    @Test
    public void testAlias() {
        JsonArray arr = new JsonArray();
        arr.add(new JsonPrimitive("aaa"));
        
        AudienceTarget target = AudienceTarget.alias("aaa");
        Assert.assertEquals("", arr, target.toJSON());
    }

    

}
