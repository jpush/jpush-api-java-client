package cn.jpush.api.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseResult {
    public static final int ERROR_CODE_NONE = -1;
    public static final int ERROR_CODE_OK = 0;
    public static final String ERROR_MESSAGE_NONE = "None error message.";
    
    protected static final int RESPONSE_OK = 200;
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    public ResponseResult responseResult;
    
    public abstract boolean isResultOK();
    
    public abstract int getErrorCode();
    
    public abstract String getErrorMessage();
    
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
