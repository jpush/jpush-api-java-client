package cn.jpush.api.common;

import org.junit.Assert;
import org.junit.Test;

public class ServiceHelperTest {

    @Test
    public void test_isValidBadge() {
        String badge = "+12432";
        Assert.assertTrue("", ServiceHelper.isValidBadgeValue(badge));
        
        badge = "+99999";
        Assert.assertTrue("", ServiceHelper.isValidBadgeValue(badge));
        
        badge = "+100000";
        Assert.assertFalse("", ServiceHelper.isValidBadgeValue(badge));
        
        badge = "-99999";
        Assert.assertTrue("", ServiceHelper.isValidBadgeValue(badge));
        
        badge = "99999";
        Assert.assertTrue("", ServiceHelper.isValidBadgeValue(badge));
        
        badge = "0";
        Assert.assertTrue("", ServiceHelper.isValidBadgeValue(badge));
        
        badge = "-1";
        Assert.assertTrue("", ServiceHelper.isValidBadgeValue(badge));
        
        badge = "++10000";
        Assert.assertFalse("", ServiceHelper.isValidBadgeValue(badge));
        
        badge = "100000";
        Assert.assertFalse("", ServiceHelper.isValidBadgeValue(badge));
        
    }

}
