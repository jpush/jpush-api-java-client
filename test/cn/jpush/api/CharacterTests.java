package cn.jpush.api;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/** 
 * 测试特殊字符
 */
public class CharacterTests {

	private String appKey = "57b9ef19d4be5de08df12aa0";//必填，例如466f7032ac604e02fb7bda89
	private String masterSecret = "13ac09b17715bd117163d8a1"; //必填，每个应用都对应一个masterSecret（1f0e3dad99908345f7439f8ffabdffc4)
	private  JPushClient jpush = null;
	private int sendNo = 11111;

	/*
	 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
	 * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
	 * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒）。	
	 */
	private static int timeToLive =  60 * 60 * 24;  
	
	@Before
	public void before(){
		jpush = new JPushClient(masterSecret, appKey);
		//jpush = new JPushClient(masterSecret, appKey,timeToLive);
	}

	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush+";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter1(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush|";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}


	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter2(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush@";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter3(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush!";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter4(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush~";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter6(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush#";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter8(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush$";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter7(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush%";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter9(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush^";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}
	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter11(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush&";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}
	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter12(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush-";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}
	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter13(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush*";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}
	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter14(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpusch()";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}
	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter15(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush_";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter16(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush?";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}
	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter17(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush<>";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter18(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpsush;";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}
	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter19(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush[]";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter20(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush.";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter21(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush{}";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}
	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter22(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush;";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}

	
	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter23(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush+";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}
	

	@Test
	public void testSendNotificationWithAppKeyWithSpecialCharacter24(){

		int erroCode = ErrorCodeEnum.NOERROR.value();
		String msgTitle = "jpush";
		String msgContent = "jpush\"\"";

		MessageResult result = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent);
		assertEquals(erroCode, result.getErrcode());
	}



}
