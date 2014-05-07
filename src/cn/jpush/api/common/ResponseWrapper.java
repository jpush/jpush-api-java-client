package cn.jpush.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class ResponseWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseWrapper.class);
    private static final int RESPONSE_CODE_NONE = -1;
    
    private static Gson _gson = new Gson();
    
    public int responseCode = RESPONSE_CODE_NONE;
    public String responseContent;
    
    public ErrorContent error;     // error for non-200 response, used by new API
    
    public int rateLimitQuota;
    public int rateLimitRemaining;
    public int rateLimitReset;
    
    public String exceptionString;

	public ResponseWrapper() {
	}
	
    public void setRateLimit(String quota, String remaining, String reset) {
        if (null == quota) return;
        
        try {
            rateLimitQuota = Integer.parseInt(quota);
            rateLimitRemaining = Integer.parseInt(remaining);
            rateLimitReset = Integer.parseInt(reset);
            
            LOG.debug("JPush API Rate Limiting params - quota:" + quota + ", remaining:" + remaining + ", reset:" + reset);
        } catch (NumberFormatException e) {
            LOG.debug("Unexpected - parse rate limiting headers error.");
        }
    }
    
    public void setErrorObject() {
        error = _gson.fromJson(responseContent, ErrorContent.class);
    }

	@Override
	public String toString() {
		return _gson.toJson(this);
	}
	
	public class ErrorContent {
	    public Error error;
	}
	
	public class Error {
	    public int code;
	    public String message;
	    
	    @Override
	    public String toString() {
	        return _gson.toJson(this);
	    }
	}
	
}
