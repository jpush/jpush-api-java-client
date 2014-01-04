package cn.jpush.api.common;

import cn.jpush.api.common.ResponseResult.ErrorObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseResult {

    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    public ResponseResult responseResult;
    
    public ErrorObject getErrorObject() {
        if (null != responseResult) {
            return responseResult.error;
        }
        return null;
    }
    
    public int getRateLimitQuota() {
        if (null != responseResult) {
            return responseResult.rateLimitQuota;
        }
        return 0;
    }
    
    public int getRateLimitRemaining() {
        if (null != responseResult) {
            return responseResult.rateLimitRemaining;
        }
        return 0;
    }
    
    public int getRateLimitReset() {
        if (null != responseResult) {
            return responseResult.rateLimitReset;
        }
        return 0;
    }
    
}
