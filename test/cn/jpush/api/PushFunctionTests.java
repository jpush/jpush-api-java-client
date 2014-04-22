package cn.jpush.api;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import cn.jpush.api.common.DeviceType;
import cn.jpush.api.push.IosExtras;
import cn.jpush.api.push.MessageResult;
import cn.jpush.api.push.NotificationParams;
import cn.jpush.api.push.model.AudienceType;

public class PushFunctionTests {
    private static final String appKey ="dd1066407b044738b6479275";
    private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
    
    private static final String TAG = "tag_api";
    private static final String ALIAS = "alias_api";
    private static final String MSG_CONTENT = "JPush Test - error tests";
    private static final int SUCCEED_RESULT_CODE = 0;
	
    private JPushClient jpushAndroid = null;
    private JPushClient jpushIos = null;
    
    
    @Before
    public void before() {
        jpushAndroid = new JPushClient(masterSecret, appKey, 0, DeviceType.Android, false);
        jpushIos = new JPushClient(masterSecret, appKey, 0, DeviceType.IOS, false);
    }
	
	
	@Test
	public void sendNotificationAll_android(){
		MessageResult result = jpushAndroid.sendNotificationAll(MSG_CONTENT);
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
		
		MessageResult result = jpushIos.sendNotification(MSG_CONTENT, params, extra);
		assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
	}
	
	@Test
    public void sendNotificationWithAlias() {
        NotificationParams params = new NotificationParams();
        params.setReceiverType(AudienceType.ALIAS);
        params.setReceiverValue(ALIAS);
        
        MessageResult result = jpushAndroid.sendNotification(MSG_CONTENT, params, null);
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
        
        MessageResult result = jpushIos.sendNotification(MSG_CONTENT, params, extra);
		assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
	}
	
	@Test
	public void sendNotificationWithTag(){
        NotificationParams params = new NotificationParams();
        params.setReceiverType(AudienceType.TAG);
        params.setReceiverValue(TAG);
        
        MessageResult result = jpushAndroid.sendNotification(MSG_CONTENT, params, null);
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
		
        MessageResult result = jpushAndroid.sendNotification(MSG_CONTENT, params, null);
		assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
	}
	
}


