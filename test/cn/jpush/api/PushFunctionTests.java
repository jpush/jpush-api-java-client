package cn.jpush.api;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.audience.AudienceType;

public class PushFunctionTests {
    private static final String appKey ="dd1066407b044738b6479275";
    private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
    
    private static final String TAG = "tag_api";
    private static final String ALIAS = "alias_api";
    private static final String MSG_CONTENT = "JPush Test - error tests";
    private static final int SUCCEED_RESULT_CODE = 0;
	
    private JPushClient jpushClient = null;
    
    
    @Before
    public void before() {
        jpushClient = new JPushClient(masterSecret, appKey);
    }
	
	
	@Test
	public void sendNotificationAll_android(){
		PushResult result = jpushClient.sendNotificationAll(MSG_CONTENT);
		assertEquals(0, result.getErrorCode());
	}
	
	@Test
	public void sendNotificationAll_ios(){
        HashMap<String, Object> extra = new HashMap<String, Object>();
		extra.put("jpush-key","jpush-value");
		IosExtras iosExtra = new IosExtras(1,"test.mp3");
		extra.put("ios", iosExtra);
		
		NotificationParams params = new NotificationParams();
		params.setReceiverType(AudienceType.APP_KEY);
		
		PushResult result = jpushIos.sendNotification(MSG_CONTENT, params, extra);
		assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
	}
	
	@Test
    public void sendNotificationWithAlias() {
        NotificationParams params = new NotificationParams();
        params.setReceiverType(AudienceType.ALIAS);
        params.setReceiverValue(ALIAS);
        
        PushResult result = jpushClient.sendNotification(MSG_CONTENT, params, null);
		assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
	}
	
	@Test
	public void sendNotificationWithAlias_ios(){
        HashMap<String, Object> extra = new HashMap<String, Object>();
        extra.put("jpush-key","jpush-value");
        IosExtras iosExtra = new IosExtras(1,"test.mp3");
        extra.put("ios", iosExtra);
		
        NotificationParams params = new NotificationParams();
        params.setReceiverType(AudienceType.ALIAS);
        params.setReceiverValue(ALIAS);
        
        PushResult result = jpushIos.sendNotification(MSG_CONTENT, params, extra);
		assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
	}
	
	@Test
	public void sendNotificationWithTag(){
        NotificationParams params = new NotificationParams();
        params.setReceiverType(AudienceType.TAG);
        params.setReceiverValue(TAG);
        
        PushResult result = jpushClient.sendNotification(MSG_CONTENT, params, null);
		assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
	}
	
	@Test
	public void sendNotificationWithTagByExtra(){
        HashMap<String, Object> extra = new HashMap<String, Object>();
        extra.put("jpush-key","jpush-value");
        IosExtras iosExtra = new IosExtras(1,"test.mp3");
        extra.put("ios", iosExtra);
        
        NotificationParams params = new NotificationParams();
        params.setReceiverType(AudienceType.ALIAS);
        params.setReceiverValue(ALIAS);
		
        PushResult result = jpushClient.sendNotification(MSG_CONTENT, params, null);
		assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
	}
	
}


