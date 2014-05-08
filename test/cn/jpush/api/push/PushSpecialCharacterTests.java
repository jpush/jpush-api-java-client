package cn.jpush.api.push;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

/** 
 * 测试特殊字符
 */
public class PushSpecialCharacterTests {
	private String appKey = "7d431e42dfa6a6d693ac2d04";//必填，例如466f7032ac604e02fb7bda89
	private String masterSecret = "5e987ac6d2e04d95a9d8f0d1"; //必填，每个应用都对应一个masterSecret（1f0e3dad99908345f7439f8ffabdffc4)
	private JPushClient jpush = null;
	
	public static final char[] SPECIAL_CHARS = new char[] {'`', '~', '!', '@', '#', '$', '%', 
	    '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '{', '}', '[', ']', 
	    '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '.', '?', '/'};
	
	@Before
	public void before(){
		jpush = new JPushClient(masterSecret, appKey);
	}
	
	public int sendMessage(String content) {
	    Message message = Message.newBuilder().setTitle("title").build();
	    PushPayload payload = PushPayload.newBuilder()
	            .setAudience(Audience.tag("jpush_0986b893"))
	            .setMessage(message)
	            .build();
	    PushResult result = jpush.sendPush(payload);
	    return result.getErrorCode();
	}
	
	@Test
    public void testCharacters() {
		String prefix = "JPush Special Character tests - ";
		
		for (char c : SPECIAL_CHARS) {
		    String msgContent = prefix + c;
	        assertEquals(0, sendMessage(msgContent));
		}
	}
}


