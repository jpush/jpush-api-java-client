package cn.jpush.test;

import org.testng.Assert;
import org.testng.annotations.*;
import cn.jpush.api.DeviceEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

public class JpushClientTest {
	
	private static final String username = "username";
	private static final String password = "password";
	private static final String callbackUrl = "callback";
	private static JPushClient client = null;
	
	static {
		client = new JPushClient(username, password, callbackUrl);
	}
	
	public static void main(String []args) {
		//client.shutdown();
	}
	
	@Test
	public static void testSendNotificationWithImei() {
		String imei = "860949003474563";
		String appKey = "466f7032ac604e02fb7bda89";
		int sendNo = 1;
		String sendDescription = "title";
		String msgTitle = "title";
		String msgContent = "write 中文...";
		MessageResult msgResult = client.sendNotificationWithImei(imei,
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android,
											DeviceEnum.IOS);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test
	public static void testSendCustomMessageWithImei() {
		String imei = "860949003474563";
		String appKey = "466f7032ac604e02fb7bda89";
		int sendNo = 1;
		String sendDescription = "title";
		String msgTitle = "title";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendCustomMessageWithImei(imei, 
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test
	public static void testSendNotifyMessageWithAppKey() {
		String appKey = "466f7032ac604e02fb7bda89";
		int sendNo = 1;
		String sendDescription = "title";
		String msgTitle = "title";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendNotificationWithAppKey(
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test
	public static void testSendCustomMessageWithAppKey() {
		String appKey = "466f7032ac604e02fb7bda89";
		int sendNo = 1;
		String sendDescription = "title";
		String msgTitle = "title";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendCustomMessageWithAppKey(
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test
	public static void testSendNotifyMessageWithTag() {
		String tag = "tag";
		String appKey = "466f7032ac604e02fb7bda89";
		int sendNo = 1;
		String sendDescription = "title";
		String msgTitle = "title";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendNotificationWithTag(tag,
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test
	public static void testSendCustomMessageWithTag() {
		String tag = "tag";
		String appKey = "466f7032ac604e02fb7bda89";
		int sendNo = 1;
		String sendDescription = "title";
		String msgTitle = "title";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendCustomMessageWithTag(tag,
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test
	public static void testSendNotifyMessageWithAlias() {
		String alias = "alias";
		String appKey = "466f7032ac604e02fb7bda89";
		int sendNo = 1;
		String sendDescription = "title";
		String msgTitle = "title";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendNotificationWithAlias(alias,
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test
	public static void testSendCustomMessageWithAlias() {
		String alias = "alias";
		String appKey = "466f7032ac604e02fb7bda89";
		int sendNo = 1;
		String sendDescription = "title";
		String msgTitle = "title";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendCustomMessageWithAlias(alias,
											appKey, sendNo, sendDescription,
											msgTitle, msgContent,
											DeviceEnum.Android);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
}
