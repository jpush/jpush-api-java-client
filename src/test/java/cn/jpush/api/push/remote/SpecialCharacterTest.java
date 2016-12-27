package cn.jpush.api.push.remote;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.SlowTests;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

@Category(SlowTests.class)
public class SpecialCharacterTest extends BaseRemotePushTest {
	
	public static final char[] SPECIAL_CHARS = new char[] {'`', '~', '!', '@', '#', '$', '%', 
	    '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '{', '}', '[', ']', 
	    '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '.', '?', '/'};
	
	public int sendMessage(String content) {
	    Message message = Message.newBuilder()
	            .setMsgContent(content)
	            .setTitle("title").build();
	    
	    PushPayload payload = PushPayload.newBuilder()
	            .setPlatform(Platform.all())
	            .setAudience(Audience.alias("special_c"))
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
                .setAudience(Audience.alias("special_c"))
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
    
    
    @BeforeClass
    public static void prepareAudience() throws Exception {
    	JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
    	DefaultResult result = jpushClient.updateDeviceTagAlias(REGISTRATION_ID3, "special_c", null, null);
    	assertThat(result.isResultOK(), is(true));
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


