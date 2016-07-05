package cn.jpush.api.push.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushClient;

public class BaseMockTest implements IMockTest {
    
    static {
        SUPPORT_PLATFORM.add("android");
        SUPPORT_PLATFORM.add("ios");
        SUPPORT_PLATFORM.add("winphone");
        
        SUPPORT_AUDIENCE.add("tag");
        SUPPORT_AUDIENCE.add("tag_and");
        SUPPORT_AUDIENCE.add("alias");
        SUPPORT_AUDIENCE.add("registration_id");
        SUPPORT_AUDIENCE.add("segment");
    }
    
    
    protected static PushClient _client = null;
    protected static MockWebServer _mockServer = null;
    protected static URL _mockUrl = null;
    protected String _currentPayload = null;
    protected int _expectedErrorCode = 0;
    
    @Before
    public void before() {
    }
    
    private void basicRequestCheck() {
        RecordedRequest request;
        try {
            request = _mockServer.takeRequest();
            assertNotNull("", request.getHeader("Authorization"));
            assertEquals("", CONTENT_TYPE_JSON, request.getHeader("Content-Type"));
            assertEquals("", "keep-alive", request.getHeader("Connection"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @After
    public void after() {
        int sendno = 0;
        int responseCode = 200;
        int errorCode = 0;
        String errorMessage = null;
        
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(_currentPayload).getAsJsonObject();
        
        if (!json.has("platform") || !json.has("audience")
                || (!json.has("notification") && !json.has("message"))) {
            responseCode = 400;
            errorCode = 1002;
            errorMessage = "Lack of params.";
            
        } else {
        
            JsonElement platform = json.get("platform");
            if (platform.isJsonPrimitive() && !"all".equals(platform.getAsString())) {
                responseCode = 400;
                errorCode = 1003;
                errorMessage = "Invalid param - platform string should only be 'all'";
            }
            
            if (platform.isJsonArray()) {
                JsonArray platformArray = platform.getAsJsonArray();
                for (int i = 0; i < platformArray.size(); i++) {
                    String onePlatform = platformArray.get(i).getAsString();
                    if (!SUPPORT_PLATFORM.contains(onePlatform)) {
                        responseCode = 400;
                        errorCode = 1003;
                        errorMessage = "Invalid param - platform is invalid - " + onePlatform;
                        break;
                    }
                }
            }
            
            JsonElement audience = json.get("audience");
            if (audience.isJsonPrimitive() && !"all".equals(audience.getAsString())) {
                responseCode = 400;
                errorCode = 1003;
                errorMessage = "Invalid param - audience string should only be 'all'";
            }
            
            if (audience.isJsonObject()) {
                JsonObject audienceObject = audience.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : audienceObject.entrySet()) {
                    if (!SUPPORT_AUDIENCE.contains(entry.getKey())) {
                        responseCode = 400;
                        errorCode = 1003;
                        errorMessage = "Invalid param - audience is invalid - " + entry.getKey();
                    }
                }
            }
            
            if (json.has("notification")) {
                JsonElement notification = json.get("notification");
                if (!notification.isJsonObject()) {
                    responseCode = 400;
                    errorCode = 1003;
                    errorMessage = "Invalid param - notification is invalid - should not be string value. ";
                } else {
                    
                }
                
            } else if (json.has("message")) {
                JsonElement message = json.get("message");
                if (!message.isJsonObject()) {
                    responseCode = 400;
                    errorCode = 1003;
                    errorMessage = "Invalid param - message is invalid - should not be string value. ";
                }
            }
        
        }
        
        if (responseCode == 200) {
            _mockServer.enqueue(new MockResponse()
                .setBody(getResponseOK(111, sendno)));
        } else {
            _mockServer.enqueue(new MockResponse()
                .setResponseCode(responseCode)
                .setBody(getResponseError(111, sendno, errorCode, errorMessage)));
        }
        
        try {
            _client.sendPush(_currentPayload);
            
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            // TODO Auto-generated catch block
            assertEquals("", _expectedErrorCode, e.getErrorCode());
        }
        
        basicRequestCheck();
    }
    
    @BeforeClass
    public static void beforeClass() throws IOException {
        _mockServer = new MockWebServer();
        
        _mockServer.play();
        _mockUrl = _mockServer.getUrl("/v3/push/");
        
        _client = new PushClient(masterSecret, appKey);
        _client.setBaseUrl(_mockUrl.toString());
    }
    
    @AfterClass
    public static void afterClass() throws IOException {
        _mockServer.shutdown();
    }
    
    
    protected String getResponseOK(int msgid, int sendno) {
        JsonObject json = new JsonObject();
        json.add("msg_id", new JsonPrimitive(msgid));
        json.add("sendno", new JsonPrimitive(sendno));
        return json.toString();
    }
    
    protected String getResponseError(int msgid, int sendno, int errorCode, String errorMessage) {
        JsonObject json = new JsonObject();
        json.add("msg_id", new JsonPrimitive(msgid));
        json.add("sendno", new JsonPrimitive(sendno));
        
        JsonObject error = new JsonObject();
        error.add("code", new JsonPrimitive(errorCode));
        error.add("message", new JsonPrimitive(errorMessage));
        
        json.add("error", error);
        return json.toString();
    }


    
}

