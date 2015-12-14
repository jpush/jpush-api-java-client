package cn.jpush.api;

import org.junit.Before;

public abstract class BaseTest {

	protected static final String APP_KEY ="dd1066407b044738b6479275";
    protected static final String MASTER_SECRET = "e8cc9a76d5b7a580859bcfa7";

    public static final String ALERT = "JPush Test - alert";
    public static final String MSG_CONTENT = "JPush Test - msgContent";
    
    public static final String REGISTRATION_ID1 = "0900e8d85ef";
    public static final String REGISTRATION_ID2 = "0a04ad7d8b4";

    
    protected JPushClient jpushClient = null;
    
    @Before
    public void before() {
        jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        
    }

}
