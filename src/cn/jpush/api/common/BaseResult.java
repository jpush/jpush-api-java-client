package cn.jpush.api.common;

import cn.jpush.api.common.ResponseResult.ErrorObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseResult {
    public static final int ERROR_CODE_NONE = -1;
    public static final int ERROR_CODE_OK = 0;
    public static final String ERROR_MESSAGE_NONE = "None error message.";
    
    protected static final int RESPONSE_OK = 200;
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    public ResponseResult responseResult;
    
    protected ErrorObject getErrorObject() {
        if (null != responseResult) {
            return responseResult.error;
        }
        return null;
    }
    
    public int getErrorCode() {
        ErrorObject eo = getErrorObject();
        if (null != eo) {
            return eo.code;
        }
        return ERROR_CODE_OK;
    }
    
    public String getErrorMessage() {
        ErrorObject eo = getErrorObject();
        if (null != eo) {
            return eo.message;
        }
        return ERROR_MESSAGE_NONE;
    }
    
    public boolean isResultOK() {
        if (responseResult.responseCode == RESPONSE_OK) return true;
        return false;
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
    
    @Override
    public String toString() {
        return _gson.toJson(this);
    }

    
}
