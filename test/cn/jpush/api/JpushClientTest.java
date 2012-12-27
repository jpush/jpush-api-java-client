package cn.jpush.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class JpushClientTest {

	private static  String appKey = "57b9ef19d4be5de08df12aa0";//必填，例如466f7032ac604e02fb7bda89
	private static  String masterSecret = "9cc138f8dc04cbf16240daa92d8d50e2"; //必填，每个应用都对应一个masterSecret（1f0e3dad99908345f7439f8ffabdffc4)
	private static JPushClient jpush = null;

	public JpushClientTest(){
		jpush = new JPushClient(masterSecret, appKey);
	}
	/*
	 * 参数值不合法 1003
	 */
	@Test
	public void testSendNotificationWithAppKeyParamError(){
		appKey = "9cc138f8dc04cbf16240daa92d8d50e21";
		jpush = new JPushClient(masterSecret, appKey);
		int erroCode = 1003;
		int sendNo = 110;
		String msgTitle = "kktalk";
		String msgContent = "kktalk-士大夫";
				
		System.out.println(msgContent.getBytes().length);
		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());

	}
	/*
	 * 消息体太大 1005
	 */
	@Test
	public void testSendNotificationWithAppKeyBigMessage(){
		int erroCode = 1005;
		int sendNo = 110;
		String msgTitle = "kktalk";
		String msgContent = "kktalk-士大夫jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"sdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
				"sddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
				"sdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddcontent";
		System.out.println(msgContent.getBytes().length);
		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());

	}
	
/*
 * 1004 verification_code 验证失败
 */
	@Test
	public void testSendNotificationWithAppKeyverificationCode (){
		masterSecret = "9cc138f8dc04cbf16240daa92d8d50e21";
		jpush = new JPushClient(masterSecret, appKey);
		int erroCode = 1004;
		int sendNo = 110;
		String msgTitle = "kktalk";
		String msgContent = "kktalk-士大夫";
				
		System.out.println(msgContent.getBytes().length);
		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());

	}
}
