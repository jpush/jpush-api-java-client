package cn.jpush.api.common.resp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseResult implements IRateLimiting {
    public static final int ERROR_CODE_NONE = -1;
    public static final int ERROR_CODE_OK = 0;
    public static final String ERROR_MESSAGE_NONE = "None error message.";
    
    protected static final int RESPONSE_OK = 200;
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    
    private ResponseWrapper responseWrapper;
    
    public void setResponseWrapper(ResponseWrapper responseWrapper) {
        this.responseWrapper = responseWrapper;
    }
    
    public String getOriginalContent() {
        if (null != responseWrapper) {
            return responseWrapper.responseContent;
        }
        return null;
    }
    
    public boolean isResultOK() {
        return RESPONSE_OK == responseWrapper.responseCode;
    }
    
    public static <T extends BaseResult> T fromResponse(
            ResponseWrapper responseWrapper, Class<T> clazz) {
        T result = null;
        
        if (responseWrapper.isServerResponse()) {
            result = _gson.fromJson(responseWrapper.responseContent, clazz);
        } else {
            try {
                result = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        result.setResponseWrapper(responseWrapper);
        
        return result;
    }

    
    @Override
    public int getRateLimitQuota() {
        if (null != responseWrapper) {
            return responseWrapper.rateLimitQuota;
        }
        return 0;
    }
    
    @Override
    public int getRateLimitRemaining() {
        if (null != responseWrapper) {
            return responseWrapper.rateLimitRemaining;
        }
        return 0;
    }
    
    @Override
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
