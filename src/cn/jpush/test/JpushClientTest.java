package cn.jpush.test;

import org.testng.Assert;
import org.testng.annotations.*;

import cn.jpush.api.CustomMessageParams;
import cn.jpush.api.DeviceEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;
import cn.jpush.api.NotifyMessageParams;
import cn.jpush.api.ReceiverTypeEnum;

public class JpushClientTest {
	
	private final String username = "username";
	private final String password = "password";
	private final String callbackUrl = "callbackUrl";
	private JPushClient client = null;
	
	@BeforeTest
	public void init() {
		client = new JPushClient(username, password, callbackUrl);
	}
	
	@AfterTest
	public void shutdown() {
		client.shutdown();
	}
	
	@Test
	public void testSendNotificationWithImei() {
		String imei = "860949003474563";
		String appKey = "466f7032ac604e02fb7bda89";
		int sendNo = 1;
		String sendDescription = "title";
		String msgTitle = "title";
		String msgContent = "write something...";
		MessageResult msgResult = client.sendNotificationWithImei(imei,
									appKey, sendNo, sendDescription,
									msgTitle, msgContent,
									DeviceEnum.Android,
									DeviceEnum.IOS);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test
	public void testSendCustomMessageWithImei() {
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
	public void testSendNotifyMessageWithAppKey() {
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
	public void testSendCustomMessageWithAppKey() {
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
	public void testSendNotifyMessageWithTag() {
		String tag = "fruit";
		String appKey = "fa1fa9091f2e5c786a87ecae";
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
	public void testSendCustomMessageWithTag() {
		String tag = "fruit";
		String appKey = "fa1fa9091f2e5c786a87ecae";
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
	public void testSendNotifyMessageWithAlias() {
		String alias = "alias1";
		String appKey = "fa1fa9091f2e5c786a87ecae";
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
	public void testSendCustomMessageWithAlias() {
		String alias = "alias1";
		String appKey = "fa1fa9091f2e5c786a87ecae";
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
	
	@Test
	public void testSendNotifyMessage() {
		NotifyMessageParams params = new NotifyMessageParams();
		params.setAppKeys("466f7032ac604e02fb7bda89");
		params.setSendNo(1);
		params.setSendDescription("description");
		params.addReceiverType(ReceiverTypeEnum.IMEI);
		params.addReceiverValue("860949003474563");
		params.addPlatform(DeviceEnum.Android);
		params.addPlatform(DeviceEnum.IOS);
		params.getMsgContent().setTitle("notification title");
		params.getMsgContent().setMessage("notification message");
		params.getMsgContent().setBuilderId("0");
		
		MessageResult msgResult = client.sendNotification(params);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
	
	@Test
	public void testSendCustomMessage() {
		CustomMessageParams params = new CustomMessageParams();
		params.setAppKeys("466f7032ac604e02fb7bda89");
		params.setSendNo(1);
		params.setSendDescription("description");
		params.addReceiverType(ReceiverTypeEnum.IMEI);
		params.addReceiverValue("860949003474563");
		params.addPlatform(DeviceEnum.Android);
		params.addPlatform(DeviceEnum.IOS);
		params.getMsgContent().setTitle("notification title");
		params.getMsgContent().setMessage("notification message");
		params.getMsgContent().setContentType("0");
		params.getMsgContent().setExtra(new java.util.HashMap<String, Object>());
		
		MessageResult msgResult = client.sendCustomMessage(params);
		Assert.assertEquals(msgResult.getErrcode(), 0);
	}
}
