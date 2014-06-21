package cn.jpush.api.push.remote;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import cn.jpush.api.push.PushClient;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class BaseRemoteTest {
    public static final String appKey ="dd1066407b044738b6479275";
    public static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
    
    public static final String CONTENT_TYPE_JSON = "application/json";
    
    public static final int SUCCEED_RESULT_CODE = 0;
    public static final int LACK_OF_PARAMS = 1002;
    public static final int INVALID_PARAMS = 1003;
    public static final int AUTHENTICATION_FAIL = 1004;
    public static final int TOO_BIG = 1005;
    public static final int APPKEY_NOT_EXIST = 1008;
    public static final int NO_TARGET = 1011;

    public static final String ALERT = "JPush Test - alert";
    public static final String MSG_CONTENT = "JPush Test - msgContent";
    
    public static final String TAG1 = "tag1";
    public static final String TAG2 = "tag2";
    public static final String TAG_ALL = "tag_all";
    public static final String TAG_NO = "tag_no";
    public static final String ALIAS1 = "alias1";
    public static final String ALIAS2 = "alias2";
    public static final String ALIAS_NO = "alias_no";
    public static final String REGISTRATION_ID1 = "0900e8d85ef";
    public static final String REGISTRATION_ID2 = "0a04ad7d8b4";
        
    protected static PushClient _client = null;
    
    @After
    public void after() {
        
    }
    
    @BeforeClass
    public static void beforeClass() throws IOException {
        _client = new PushClient(masterSecret, appKey);
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

