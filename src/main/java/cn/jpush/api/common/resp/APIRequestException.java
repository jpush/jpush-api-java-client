package cn.jpush.api.common.resp;

import cn.jpush.api.common.resp.ResponseWrapper.ErrorObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class APIRequestException extends Exception implements IRateLimiting {
    private static final long serialVersionUID = -3921022835186996212L;
    
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    private final ResponseWrapper responseWrapper;
    
    public APIRequestException(ResponseWrapper responseWrapper) {
        super(responseWrapper.responseContent);
        this.responseWrapper = responseWrapper;
    }
    
    public int getStatus() {
        return this.responseWrapper.responseCode;
    }
    
    public long getMsgId() {
        ErrorObject eo = getErrorObject();
        if (null != eo) {
            return eo.msg_id;
        }
        return 0;
    }
    
    public int getErrorCode() {
        ErrorObject eo = getErrorObject();
        if (null != eo) {
            return eo.error.code;
        }
        return -1;
    }
    
    public String getErrorMessage() {
        ErrorObject eo = getErrorObject();
        if (null != eo) {
            return eo.error.message;
        }
        return null;
    }
    
    @Override
    public String toString() {
        return _gson.toJson(this);
    }
    
    private ErrorObject getErrorObject() {
        return this.responseWrapper.error;
    }
    

    @Override
    public int getRateLimitQuota() {
        return responseWrapper.rateLimitQuota;
    }

    @Override
    public int getRateLimitRemaining() {
        return responseWrapper.rateLimitRemaining;
    }

    @Override
    public int getRateLimitReset() {
        return responseWrapper.rateLimitReset;
    }
        
}

