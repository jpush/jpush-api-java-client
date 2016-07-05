package cn.jpush.api.push;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.BaseTest;
import cn.jpush.api.push.model.PushPayload;

public class PushClientTest extends BaseTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_json() {
        PushClient pushClient = new PushClient(MASTER_SECRET, APP_KEY);
        
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
        PushClient pushClient = new PushClient(MASTER_SECRET, APP_KEY);
        
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
    	PushClient pushClient = new PushClient(MASTER_SECRET, APP_KEY);
    	
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
