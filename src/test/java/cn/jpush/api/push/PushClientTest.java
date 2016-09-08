package cn.jpush.api.push;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.Http2Request;
import cn.jiguang.common.connection.NettyHttp2Client;
import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.JPushClient;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.Test;

import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.BaseTest;
import cn.jpush.api.push.model.PushPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

public class PushClientTest extends BaseTest {

    protected static final Logger LOG = LoggerFactory.getLogger(PushClientTest.class);

    @Test
    public void testSendPush() {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, 3);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert();
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

    @Test
    public void testSendPushes() {
        // HttpProxy proxy = new HttpProxy("localhost", 3128);
        // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, 3);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert();

        long before = System.currentTimeMillis();
        for (int i=0; i<100; i++) {
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
        long after = System.currentTimeMillis();
        LOG.info("Cost: " + (after - before));
    }

    @Test
    public void testSendPushesReuse() {
        ClientConfig config = ClientConfig.getInstance();
        String host = (String) config.get(ClientConfig.PUSH_HOST_NAME);
        NettyHttp2Client client = new NettyHttp2Client(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, config, host);
        Queue<Http2Request> queue = new LinkedList<Http2Request>();
        String url = (String) config.get(ClientConfig.PUSH_PATH);
        PushPayload payload = buildPushObject_all_all_alert();
        for (int i=0; i<100; i++) {
            queue.offer(new Http2Request(url, payload.toString()));
        }
        try {
            long before = System.currentTimeMillis();
            LOG.info("before: " + before);
            client.setRequestQueue(HttpMethod.POST, queue).execute(new NettyHttp2Client.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper wrapper) {
                    PushResult result = BaseResult.fromResponse(wrapper, PushResult.class);
                    LOG.info("Got result - " + result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(ALERT);
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
