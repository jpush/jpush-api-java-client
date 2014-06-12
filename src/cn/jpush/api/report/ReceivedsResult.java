package cn.jpush.api.report;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.api.common.BaseResult;
import cn.jpush.api.common.ResponseWrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

public class ReceivedsResult extends BaseResult {
    private static final Type RECEIVED_TYPE = new TypeToken<List<Received>>(){}.getType();

    @Expose public List<Received> received_list = new ArrayList<Received>();

    
	public static class Received {
	    @Expose public long msg_id;
	    @Expose public int android_received;
	    @Expose public int ios_apns_sent;
	}
	
	public static ReceivedsResult fromResponse(ResponseWrapper responseWrapper) {
        ReceivedsResult receivedsResult = new ReceivedsResult();
        if (responseWrapper.isServerResponse()) {
            receivedsResult.received_list = _gson.fromJson(responseWrapper.responseContent, RECEIVED_TYPE);
        }
        
        receivedsResult.setResponseWrapper(responseWrapper);
        return receivedsResult;
	}
	
}
