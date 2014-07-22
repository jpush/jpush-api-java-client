package cn.jpush.api.app;

import cn.jpush.api.common.BaseResult;
import cn.jpush.api.common.ResponseWrapper;

import com.google.gson.annotations.Expose;

public class AppCreateResult extends BaseResult {
    @Expose public String app_key;
    @Expose public String android_package;
    @Expose public boolean is_new_created;
    @Expose public String master_secret;
    
    public static AppCreateResult fromResponse(ResponseWrapper responseWrapper) {
        AppCreateResult result = null;
        
        if (responseWrapper.isServerResonse()) {
            result = _gson.fromJson(responseWrapper.responseContent, AppCreateResult.class);
        } else {
            result = new AppCreateResult();
        }
        
        result.setResponseWrapper(responseWrapper);
        
        return result;
    }

}
