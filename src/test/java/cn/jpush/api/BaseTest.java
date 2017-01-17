package cn.jpush.api;

import org.junit.Before;

public abstract class BaseTest {

	protected static final String APP_KEY = "d4ee2375846bc30fa51334f5";
    protected static final String MASTER_SECRET = "2bf52ee46fdeaadb8718fc15";

    public static final String ALERT = "JPush Test - alert";
    public static final String MSG_CONTENT = "JPush Test - msgContent";
    
    public static final String REGISTRATION_ID1 = "0900e8d85ef";
    public static final String REGISTRATION_ID2 = "0a04ad7d8b4";
    public static final String REGISTRATION_ID3 = "18071adc030dcba91c0";

    
    protected JPushClient jpushClient = null;
    
    @Before
    public void before() {
        jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        
    }

}
