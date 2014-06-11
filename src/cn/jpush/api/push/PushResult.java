package cn.jpush.api.push;

import cn.jpush.api.common.BaseResult;
import cn.jpush.api.common.ResponseWrapper;

import com.google.gson.annotations.Expose;

public class PushResult extends BaseResult {
    
    @Expose public long msg_id;
    @Expose public int sendno;
    
    public static PushResult fromResponse(ResponseWrapper responseWrapper) {
        PushResult pushResult = null;
        
        if (responseWrapper.isServerResponse()) {
            pushResult = _gson.fromJson(responseWrapper.responseContent, PushResult.class);
        } else {
            pushResult = new PushResult();
        }
        
        pushResult.setResponseWrapper(responseWrapper);
        
        return pushResult;
    }
}

