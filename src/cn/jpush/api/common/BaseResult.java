package cn.jpush.api.common;

import cn.jpush.api.common.ResponseWrapper.ErrorObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseResult {
    public static final int ERROR_CODE_NONE = -1;
    public static final int ERROR_CODE_OK = 0;
    public static final String ERROR_MESSAGE_NONE = "None error message.";
    
    protected static final int RESPONSE_OK = 200;
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    private ResponseWrapper responseWrapper;
    
    public void setResponseWrapper(ResponseWrapper responseWrapper) {
        this.responseWrapper = responseWrapper;
    }
        
    protected ErrorObject getErrorObject() {
        if (null != responseWrapper) {
            return responseWrapper.error;
        }
        return null;
    }
    
    public int getErrorCode() {
        ErrorObject eo = getErrorObject();
        if (null != eo) {
            return eo.error.code;
        }
        if (null != responseWrapper) {
            if (responseWrapper.responseCode == RESPONSE_OK) {
                return ERROR_CODE_OK;
            }
        }
        return ERROR_CODE_NONE;
    }
    
    public String getErrorMessage() {
        ErrorObject eo = getErrorObject();
        if (null != eo) {
            return eo.error.message;
        }
        return ERROR_MESSAGE_NONE;
    }
    
    public String getOriginalContent() {
        if (null != responseWrapper) {
            return responseWrapper.responseContent;
        }
        return null;
    }
    
    public String getExceptionString() {
        if (null != responseWrapper) {
            return responseWrapper.exceptionString;
        }
        return null;
    }
    
    public boolean isResultOK() {
        if (responseWrapper.responseCode == RESPONSE_OK) return true;
        return false;
    }

    public int getRateLimitQuota() {
        if (null != responseWrapper) {
            return responseWrapper.rateLimitQuota;
        }
        return 0;
    }
    
    public int getRateLimitRemaining() {
        if (null != responseWrapper) {
            return responseWrapper.rateLimitRemaining;
        }
        return 0;
    }
    
    public int getRateLimitReset() {
        if (null != responseWrapper) {
            return responseWrapper.rateLimitReset;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return _gson.toJson(this);
    }

    
}
