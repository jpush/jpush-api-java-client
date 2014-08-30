package cn.jpush.api.report;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.api.common.resp.BaseResult;
import cn.jpush.api.common.resp.ResponseWrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

public class MessagesResult extends BaseResult {
    private static final Type MESSAGE_TYPE = new TypeToken<List<Message>>(){}.getType();

    @Expose public List<Message> messages = new ArrayList<Message>();
    
	public static class Message {
	    @Expose public long msg_id;
	    @Expose public Android android;
	    @Expose public Ios ios;
	}
	
    public static class Android {
        @Expose public int received;
        @Expose public int target;
        @Expose public int online_push;
        @Expose public int click;
    }
    
    public static class Ios {
        @Expose public int apns_sent;
        @Expose public int apns_target;
        @Expose public int click;
    }
    	
	static MessagesResult fromResponse(ResponseWrapper responseWrapper) {
        MessagesResult result = new MessagesResult();
        if (responseWrapper.isServerResponse()) {
            result.messages = _gson.fromJson(responseWrapper.responseContent, MESSAGE_TYPE);
        }
        
        result.setResponseWrapper(responseWrapper);
        return result;
	}
	
}
