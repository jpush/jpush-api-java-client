package cn.jpush.api.push;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cn.jiguang.commom.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.examples.PushExample;
import cn.jpush.api.push.model.Platform;
import org.junit.Test;

import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.BaseTest;
import cn.jpush.api.push.model.PushPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushClientTest extends BaseTest {

    protected static final Logger LOG = LoggerFactory.getLogger(PushClientTest.class);

    @Test
    public void testSendPush() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);

//        String payloadString = "{\"platform\":{\"all\":\"true\"},\"audience\":{\"all\":\"true\"},\"notification\":{\"alert\":\"Test\",\"ios\":{\"alert\":\"第 1 条\",\"extras\":{\"extra_key\":\"extra_value\"},\"badge\":\"+1\",\"sound\":\"\"},\"android\":{\"alert\":\"第 1 条\",\"extras\":{\"android_key\":\"android_value\"}}},\"options\":{\"sendno\":182145298,\"apns_production\":false}}";
        String payloadString = "{\"platform\":{\"all\":\"false\",\"deviceTypes\":[\"android\",\"ios\"]},\"audience\":{\"all\":\"true\"},\"notification\":{\"alert\":\"Test\",\"ios\":{\"alert\":\"第 1 条\",\"extras\":{\"extra_key\":\"extra_value\"},\"badge\":\"+1\",\"sound\":\"\"},\"android\":{\"alert\":\"第 1 条\",\"extras\":{\"android_key\":\"android_value\"}}},\"options\":{\"sendno\":182145298,\"apns_production\":false}}";
        PushPayload payload = PushPayload.fromJSON(payloadString);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

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
