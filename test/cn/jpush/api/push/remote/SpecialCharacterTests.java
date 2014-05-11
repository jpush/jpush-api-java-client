package cn.jpush.api.push.remote;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

public class SpecialCharacterTests extends BaseRemoteTests {
	
	public static final char[] SPECIAL_CHARS = new char[] {'`', '~', '!', '@', '#', '$', '%', 
	    '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '{', '}', '[', ']', 
	    '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '.', '?', '/'};
	
	public int sendMessage(String content) {
	    Message message = Message.newBuilder().setTitle("title").build();
	    PushPayload payload = PushPayload.newBuilder()
	            .setAudience(Audience.tag("jpush_0986b893"))
	            .setMessage(message)
	            .build();
	    PushResult result = _client.sendPush(payload);
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


