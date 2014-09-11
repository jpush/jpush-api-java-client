package cn.jpush.api;

import org.junit.Before;

public abstract class BaseTest {

	protected static final String APP_KEY ="dd1066407b044738b6479275";
    protected static final String MASTER_SECRET = "2b38ce69b1de2a7fa95706ea";

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

    
    protected JPushClient jpushClient = null;
    
    @Before
    public void before() {
        jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        
    }

}
