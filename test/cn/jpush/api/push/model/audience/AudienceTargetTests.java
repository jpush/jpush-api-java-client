package cn.jpush.api.push.model.audience;

import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.audience.AudienceType;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class AudienceTargetTests {

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
        
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.ALIAS)
                .addAudienceTargetValue("aaa").build();
        Assert.assertEquals("", arr, target.toJSON());
    }

    

}
