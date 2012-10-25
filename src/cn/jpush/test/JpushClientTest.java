package cn.jpush.test;

import org.testng.Assert;
import org.testng.annotations.*;
import cn.jpush.api.DeviceEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

public class JpushClientTest {
	
	private static final String username = "username";
	private static final String password = "password";
	private static final String callbackUrl = "http://dev.kktalk.cn/apps/callback.jsp";
	private static JPushClient client = new JPushClient(username, password, callbackUrl);
	
	public static void main(String []args) {
		testSendCustomMessageWithImei();
	}
	
	@Test //带IMEI通知
	public static void testSendNotificationWithImei() {
		String imei = "860949003474563";
		String appKey = "3b5fcf388d207c8f448e4d48";
		int sendNo = 2244;
		String sendDescription = "kktalk";
		String msgTitle = "kktalk";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendNotificationWithImei(imei, 
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test //带IMEI自定义消息
	public static void testSendCustomMessageWithImei() {
		String imei = "860949003474563";
		String appKey = "3b5fcf388d207c8f448e4d48";
		int sendNo = 2244;
		String sendDescription = "kktalk";
		String msgTitle = "kktalk";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendCustomMessageWithImei(imei, 
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test //带IMEI通知
	public static void testSendCustomMessageWithAppKey() {
		String appKey = "3b5fcf388d207c8f448e4d48";
		int sendNo = 2244;
		String sendDescription = "kktalk";
		String msgTitle = "kktalk";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendCustomMessageWithAppKey(
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android);
		Assert.assertEquals(msgResult.getErrcode(), 0);
		
	}
}
