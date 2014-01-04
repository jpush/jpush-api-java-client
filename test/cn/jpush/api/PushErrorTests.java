package cn.jpush.api;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cn.jpush.api.common.DeviceEnum;
import cn.jpush.api.common.ErrorCodeEnum;
import cn.jpush.api.push.MessageResult;
import cn.jpush.api.push.NotificationParams;
import cn.jpush.api.push.ReceiverTypeEnum;

public class PushErrorTests {
    private static final String appKey ="dd1066407b044738b6479275";
    private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
    private JPushClient jpushAndroid = null;
    private JPushClient jpushIos = null;
    
    private static final String MSG_CONTENT = "JPush Test - error tests";
    private static final String TAG = "tag_api";
    private static final String ALIAS = "alias_api";
    
    @Before
    public void before() {
        jpushAndroid = new JPushClient(masterSecret, appKey, 0, DeviceEnum.Android, false);
        jpushIos = new JPushClient(masterSecret, appKey, 0, DeviceEnum.IOS, false);
    }
    
    public int sendNotification(String content) {
        NotificationParams params = new NotificationParams();
        params.setReceiverType(ReceiverTypeEnum.TAG);
        params.setReceiverValue(TAG);
        MessageResult result = jpushAndroid.sendNotification(content, params, null);
        return result.errcode;
    }
    
	@Test
	public void testSendNotificationWithInvalidAppkey(){
		String appKey = "7d431e42dfa6a6d693ac2d05";  // invalid app_key
		jpushAndroid = new JPushClient(masterSecret, appKey);
		
		assertEquals(ErrorCodeEnum.InvalidAppKey.value(), sendNotification(MSG_CONTENT));
	}
	
	@Test
	public void testSendNotificationWithBigMessage(){
		String msgContent = "jpushjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj" +
		"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
		"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
		"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
		"sddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeee" +
		"sdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeee" +
		"sdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddcontenteeeeeeeeeeee";

		assertEquals(ErrorCodeEnum.InvalidParameter.value(), sendNotification(msgContent));
	}
	
	@Test
	public void testSendNotificationWithAppKeyValidateFailed (){
		String masterSecret = "5e987ac6d2e04d95a9d8f0d2"; // invalid masterSeret
		jpushAndroid = new JPushClient(masterSecret, appKey);
		
		assertEquals(ErrorCodeEnum.VerificationFailed.value(), sendNotification(MSG_CONTENT));
	}

	@Test
    public void testSendNotificationWithInvalidTag() {
		String invalid_tag = "invalid_tag_1_1_";
        NotificationParams params = new NotificationParams();
        params.setReceiverType(ReceiverTypeEnum.TAG);
        params.setReceiverValue(invalid_tag);
        MessageResult result = jpushAndroid.sendNotification(MSG_CONTENT, params, null);

		assertEquals(ErrorCodeEnum.NoTarget.value(), result.errcode);
	}
	
	@Test
	public void testSendNotificationWithAppKeyInvalidPushByAlgin(){
        String invalid_alias = "invalid_alias_1_1_";
        NotificationParams params = new NotificationParams();
        params.setReceiverType(ReceiverTypeEnum.ALIAS);
        params.setReceiverValue(invalid_alias);
        MessageResult result = jpushAndroid.sendNotification(MSG_CONTENT, params, null);
        
        assertEquals(ErrorCodeEnum.NoTarget.value(), result.errcode);
	}
	
}

