package cn.jpush.api.push.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.SocketPolicy;

import cn.jiguang.common.connection.IHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.model.PushPayload;

/**
 * Record: MockResponse.throttleBody will delay response body, but sometimes has no effect.
 * MockResponse.setBodyDelayTimeMs has no effect for delay test.
 * 
 */
public class ConnectionExceptionTest implements IMockTest {
    private PushClient _client = null;
    private MockWebServer _server = new MockWebServer();
    
    private static final String SIMPLE_CONTNET = PushPayload.alertAll("alert").toString();

    @Test
    public void test_read_timeout_disconnect_at_start() throws Exception {
        // disconnect at start. This will cause "Read timed out"
        _server.enqueue(new MockResponse()
            .setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        );
        connect_read_timeout();
    }
    
    @Test
    public void test_connect_timeout_and_retry() throws Exception {
        _server.enqueue(new MockResponse()
            .setBody(SIMPLE_CONTNET)
        );
        
        _server.play();
        _client = new PushClient(masterSecret, appKey);
        // connecting to a un-existed address will cause "connect timed out"
        _client.setBaseUrl("http://192.132.143.11" + ":81/");
        
        long start = System.currentTimeMillis();
        try {
            _client.sendPush(SIMPLE_CONTNET);
            fail("Should not go here for connection exception.");
        } catch (APIConnectionException e) {
            e.printStackTrace();
            assertFalse("connect timed out", e.isReadTimedout());
            assertEquals("Retried", IHttpClient.DEFAULT_MAX_RETRY_TIMES, e.getDoneRetriedTimes());
            
            long end = System.currentTimeMillis();
            assertTrue("Retried", (end - start) > IHttpClient.DEFAULT_CONNECTION_TIMEOUT 
                    * IHttpClient.DEFAULT_MAX_RETRY_TIMES);
            
        } catch (APIRequestException e) {
        }
    }
    

    
    
    public void connect_read_timeout() throws IOException {
        init();
        
        try {
            _client.sendPush(SIMPLE_CONTNET);
            fail("Should not go here for connection exception.");
        } catch (APIConnectionException e) {
            e.printStackTrace();
            assertTrue("Read timed out", e.isReadTimedout());
        } catch (APIRequestException e) {
        }
    }
    
    
    public void init() throws IOException {
        _server.play();
        
        URL mockUrl = _server.getUrl("/v3/push/");
        System.out.println("Server Url - " + mockUrl);
        
        _client = new PushClient(masterSecret, appKey);
        _client.setBaseUrl(mockUrl.toString());
    }
    
    @After
    public void after() throws IOException {
        _server.shutdown();
    }
    
    protected String getResponseOK(int msgid, int sendno) {
        JsonObject json = new JsonObject();
        json.add("msg_id", new JsonPrimitive(msgid));
        json.add("sendno", new JsonPrimitive(sendno));
        return json.toString();
    }

}
