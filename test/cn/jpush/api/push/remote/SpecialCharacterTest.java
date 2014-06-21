package cn.jpush.api.push.remote;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class SpecialCharacterTest extends BaseRemoteTest {
	
	public static final char[] SPECIAL_CHARS = new char[] {'`', '~', '!', '@', '#', '$', '%', 
	    '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '{', '}', '[', ']', 
	    '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '.', '?', '/'};
	
	public int sendMessage(String content) {
	    Message message = Message.newBuilder()
	            .setMsgContent(content)
	            .setTitle("title").build();
	    
	    PushPayload payload = PushPayload.newBuilder()
	            .setPlatform(Platform.all())
	            .setAudience(Audience.tag(TAG1))
	            .setMessage(message)
	            .build();
	    try {
            _client.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            return e.getErrorCode();
        }
	    return 0;
	}
	
    public int sendNotification(String alert) {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(TAG1))
                .setNotification(Notification.alert(alert))
                .build();
        try {
            _client.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            return e.getErrorCode();
        }
        return 0;
    }
    
	@Test
    public void testCharacters() {
		String prefix = "JPush Special Character tests - ";
		
		for (char c : SPECIAL_CHARS) {
		    String msgContent = prefix + c;
	        assertEquals(0, sendNotification(msgContent));
		}
		
        for (char c : SPECIAL_CHARS) {
            String msgContent = prefix + c;
            assertEquals(0, sendMessage(msgContent));
        }
        
		
	}
}


