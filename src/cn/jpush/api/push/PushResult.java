package cn.jpush.api.push;

import cn.jpush.api.common.BaseResult;
import cn.jpush.api.common.ResponseWrapper;

import com.google.gson.annotations.Expose;

public class PushResult extends BaseResult {
    
    @Expose public long msg_id;
    @Expose public int sendno;
    
    public static PushResult fromResponse(ResponseWrapper response) {
        PushResult pushResult = null;
        if (response.responseCode == RESPONSE_OK) {
            pushResult = _gson.fromJson(response.responseContent, PushResult.class);
        } else {
            pushResult = new PushResult();
        }
        pushResult.responseResult = response;
        return pushResult;
    }
    
}

