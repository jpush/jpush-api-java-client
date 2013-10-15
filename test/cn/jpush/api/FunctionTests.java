package cn.jpush.api;

import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试正常的功能点
 */
public class FunctionTests {

	private String appKey = "57b9ef19d4be5de08df12aa0";//必填，例如466f7032ac604e02fb7bda89
	private String masterSecret = "13ac09b17715bd117163d8a1"; //必填，每个应用都对应一个masterSecret（1f0e3dad99908345f7439f8ffabdffc4)
	private  JPushClient jpush = null;
	private int sendNo = 11111;
	private int resultCode = 0;
	private String msgTitle = "jpush-title";
	private String msgContent = "jpush-content";
	private String alias = "aaa";
	private String tag = "tag";
	private String imei = "358794047517434";
	/*
	 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
	 * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
	 * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒）。	
	 */
	private static long timeToLive =  60 * 60 * 24;  

	@Before
	public void before(){
		jpush = new JPushClient(masterSecret, appKey);
		//jpush = new JPushClient(masterSecret, appKey,timeToLive);
	}
	
	
	
	//发送带AppKey的自定义通知
	@Test
	public void sendNotificationWithAppKey(){
		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(resultCode, result.getErrcode());
	}
	
	//发送 自定义参数的通知
	@Test
	public void sendNotificationWithAppKeyVByExtra(){
		HashMap<String,Object> extra = new HashMap<String,Object>();
		extra.put("jpush-key","jpush-value");
		IOSExtra iosExtra = new IOSExtra(1,"test.mp3");
		extra.put("ios", iosExtra);
		
		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent, 0, extra);
		assertEquals(resultCode, result.getErrcode());
	}

	//发送带ALIAS的通知
	@Test
	public void sendNotificationWithAlias(){
	
		MessageResult result = jpush.sendNotificationWithAlias(sendNo, alias, msgTitle, msgContent);
		assertEquals(resultCode, result.getErrcode());
	}
	
	//发送带ALIAS、自定义参数的通知
	@Test
	public void sendNotificationWithAliasByExtra(){
		
		HashMap extra = new HashMap();
		extra.put("jpush-key","jpush-value");
		IOSExtra iosExtra = new IOSExtra(1,"test.mp3");
		extra.put("ios", iosExtra);
		
		MessageResult result = jpush.sendNotificationWithAlias(sendNo, alias, msgTitle, msgContent, 0, extra);
		assertEquals(resultCode, result.getErrcode());
	}
	
	//发送带tag的通知
	@Test
	public void sendNotificationWithTag(){
	
		MessageResult result = jpush.sendNotificationWithTag(sendNo, tag, msgTitle, msgContent);
		assertEquals(resultCode, result.getErrcode());
	}
	
	//发送带tag、自定义参数的通知

	@Test
	public void sendNotificationWithTagByExtra(){
		
		HashMap extra = new HashMap();
		extra.put("jpush-key","jpush-value");
		IOSExtra iosExtra = new IOSExtra(1,"test.mp3");
		extra.put("ios", iosExtra);
		
		MessageResult result = jpush.sendNotificationWithTag(sendNo, tag, msgTitle, msgContent, 0, extra);
		assertEquals(resultCode, result.getErrcode());
	}
	

/*	//发送带IMEI、自定义参数的通知
	@Test
	public void sendNotificationWithIMEI(){
		
		MessageResult result = jpush.sendNotificationWithImei(sendNo, imei, msgTitle, msgContent);
		assertEquals(resultCode, result.getErrcode());
	}
	

	//发送带IMEI、自定义参数的通知
	@Test
	public void sendNotificationWithIMEIByExtra(){
		
		MessageResult result = jpush.sendNotificationWithImei(sendNo, imei, msgTitle, msgContent);
		assertEquals(resultCode, result.getErrcode());
	}
	*/
	
	
	/*
	 * =======================================自定义消息===========================================
	 * 
	 */
	
	//发送带AppKey的自定义消息
	@Test
	public void sendCustomMessageWithAppKey(){
		MessageResult result = jpush.sendCustomMessageWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(resultCode, result.getErrcode());

	}
	
	//发送带AppKey/自定义参数 的自定义消息
	@Test
	public void sendCustomMessageWithAppKeyByExtra(){
		MessageResult result = jpush.sendCustomMessageWithAppKey(sendNo, msgTitle, msgContent,"json",null);
		assertEquals(resultCode, result.getErrcode());

		
		
	}

	//发送带Alias的自定义消息
	@Test
	public void sendCustomMessageWithAlias(){
		MessageResult result = jpush.sendCustomMessageWithAlias(sendNo, alias, msgTitle, msgContent);
		assertEquals(resultCode, result.getErrcode());

	}
	
	//发送带Alias的自定义消息
	@Test
	public void sendCustomMessageWithAliasByExtra(){
		MessageResult result = jpush.sendCustomMessageWithAlias(sendNo, alias, msgTitle, msgContent,"xml",null);
		assertEquals(resultCode, result.getErrcode());

	}
	
/*	//发送带imei的自定义消息
	@Test
	public void sendCustomMessageWithImei(){
		MessageResult result = jpush.sendCustomMessageWithImei(sendNo, imei, msgTitle, msgContent);
		assertEquals(resultCode, result.getErrcode());

	}
	
	//发送带imei/自定义内容 的自定义消息
	@Test
	public void sendCustomMessageWithImeiByExtra(){
		HashMap extra = new HashMap();
		extra.put("jpush-key","jpush-value");
		
		MessageResult result = jpush.sendCustomMessageWithImei(sendNo, imei, msgTitle, msgContent,"json",extra);
		assertEquals(resultCode, result.getErrcode());
	

	}
	
	*/

}

