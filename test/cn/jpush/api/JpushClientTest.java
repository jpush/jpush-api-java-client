package cn.jpush.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class JpushClientTest {

	private static final String appKey = "57b9ef19d4be5de08df12aa0";//必填，例如466f7032ac604e02fb7bda89
	private static final String masterSecret = "9cc138f8dc04cbf16240daa92d8d50e2"; //必填，每个应用都对应一个masterSecret（1f0e3dad99908345f7439f8ffabdffc4)
	private static JPushClient jpush = null;

	public JpushClientTest(){
		jpush = new JPushClient(masterSecret, appKey);
	}
	@Test
	public void testSendNotificationWithAppKey(){
		int erroCode = 0;
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
}
