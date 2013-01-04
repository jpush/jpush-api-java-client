package cn.jpush.api;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/*
 * 简易接口测试，正常功能点
 */
public class SimpleClientFucntionTests {
	private String appKey = "57b9ef19d4be5de08df12aa0";//必填，例如466f7032ac604e02fb7bda89
	private String masterSecret = "9cc138f8dc04cbf16240daa92d8d50e2"; //必填，每个应用都对应一个masterSecret（1f0e3dad99908345f7439f8ffabdffc4)
	private  SimpleJPushClient jpush = null;
	private int sendNo = 11111;
	private int resultCode = 0;
	private String txt = "jpush-content";
	private String alias = "alias";
	private String tag = "tag";
	private String imei = "358794047517434";
	
	/*
	 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
	 * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
	 * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒）。	
	 */
	private static int timeToLive =  60 * 60 * 24;
	
	@Before
	public void before(){
		jpush = new SimpleJPushClient(masterSecret, appKey);
		//jpush = new SimpleJPushClient(masterSecret, appKey,timeToLive);
	}
	
	
	/*
	 * 发送App可以自定义消息
	 */
	@Test
	public void sendNotificationWithAppKey(){
		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, txt);
		assertEquals(resultCode, result.getErrcode());
		
	}
	
	/*
	 * 发送Alias可以自定义消息
	 */
	@Test
	public void sendNotificationWithAlias(){
		MessageResult result = jpush.sendNotificationWithAlias(sendNo, alias, txt);
		assertEquals(resultCode, result.getErrcode());
	}
	
	
	/*
	 * 发送Tag可以自定义消息
	 */
	@Test
	public void sendNotificationWithTag(){
		MessageResult result = jpush.sendNotificationWithTag(sendNo, tag, txt);
		assertEquals(resultCode, result.getErrcode());
	}
	
	
	/*
	 * 发送Tag可以自定义消息
	 */
	@Test
	public void sendNotificationWithImei(){
		MessageResult result = jpush.sendNotificationWithImei(sendNo, imei, txt);
		assertEquals(resultCode, result.getErrcode());
	}
	
	
	/*
	 * =============================================自定义消息==========================
	 */
	
	/*
	 * 发送appkey可以自定消息
	 */
	@Test
	public void sendCustomMessageWithAppKey(){
		MessageResult result = jpush.sendCustomMessageWithAppKey(sendNo, txt);
		assertEquals(resultCode, result.getErrcode());
	}
	
	/*
	 * 发送Tag可以自定消息
	 */
	@Test
	public void sendCustomMessageWithTag(){
		MessageResult result = jpush.sendCustomMessageWithTag(sendNo, tag, txt);
		assertEquals(resultCode, result.getErrcode());
	}
	
	/*
	 * 发送Alias可以自定消息
	 */
	@Test
	public void sendCustomMessageWithAlias(){
		MessageResult result = jpush.sendCustomMessageWithAlias(sendNo, alias, txt);
		assertEquals(resultCode, result.getErrcode());
	}
	
	/*
	 * 发送Imei可以自定消息
	 */
	@Test
	public void sendCustomMessageWitImei(){
		MessageResult result = jpush.sendCustomMessageWithImei(sendNo, imei, txt);
		assertEquals(resultCode, result.getErrcode());
	}
	
	
	
}
