package cn.jpush.api.push.mock;

import java.util.ArrayList;
import java.util.List;

public interface IMockTest {
    public static final String REMOTE_PATH = "/v3/push";
    
    public static final String appKey ="dd1066407b044738b6479275";
    public static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
    
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final List<String> SUPPORT_PLATFORM = new ArrayList<String>();
    public static final List<String> SUPPORT_AUDIENCE = new ArrayList<String>();
    
    public static final int SUCCEED_RESULT_CODE = 0;
    public static final int LACK_OF_PARAMS = 1002;
    public static final int INVALID_PARAMS = 1003;
    public static final int AUTHENTICATION_FAIL = 1004;
    public static final int TOO_BIG = 1005;
    public static final int APPKEY_NOT_EXIST = 1008;

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
}
