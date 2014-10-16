package cn.jpush.api.push.remote;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import cn.jpush.api.BaseTest;
import cn.jpush.api.push.PushClient;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public abstract class BaseRemotePushTest extends BaseTest {
    
    public static final String CONTENT_TYPE_JSON = "application/json";
    
    public static final int SUCCEED_RESULT_CODE = 0;
    public static final int LACK_OF_PARAMS = 1002;
    public static final int INVALID_PARAMS = 1003;
    public static final int AUTHENTICATION_FAIL = 1004;
    public static final int TOO_BIG = 1005;
    public static final int APPKEY_NOT_EXIST = 1008;
    public static final int NO_TARGET = 1011;

    protected static PushClient _client = null;
    
    @After
    public void after() {
        
    }
    
    @BeforeClass
    public static void beforeClass() throws IOException {
        _client = new PushClient(MASTER_SECRET, APP_KEY);
    }
    
    @AfterClass
    public static void afterClass() throws IOException {
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

