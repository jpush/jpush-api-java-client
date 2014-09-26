package cn.jpush.api.push;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.common.HttpProxy;
import cn.jpush.api.push.model.PushPayload;

public class PushClientTest {
    private static final String appKey ="dd1066407b044738b6479275";
    private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";

    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_json() {
        PushClient pushClient = new PushClient(masterSecret, appKey);
        
        try {
            pushClient.sendPush("{aaa:'a}");
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void test_empty_string() {
        PushClient pushClient = new PushClient(masterSecret, appKey);
        
        try {
            pushClient.sendPush("");
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_empty_password() {
        new HttpProxy("127.0.0.1", 8080, "", null);
    }    

    @Test
    public void test_validate() {
    	PushClient pushClient = new PushClient(masterSecret, appKey);
    	
    	try {
    		PushResult result = pushClient.sendPushValidate(PushPayload.alertAll("alert"));
    		assertTrue("", result.isResultOK());
    	} catch (APIRequestException e) {
    		fail("Should not fail");
    	} catch (APIConnectionException e) {
    		e.printStackTrace();
    	}
    }
    
    

}
