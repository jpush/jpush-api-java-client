package cn.jpush.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class ResponseResult {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseResult.class);
    private static final int RESPONSE_CODE_NONE = -1;
    
    private static Gson _gson = new Gson();
    
    public int responseCode = RESPONSE_CODE_NONE;
    public String responseContent;
    
    public ErrorObject error;     // error for non-200 response, used by new API
    
    public int rateLimitQuota;
    public int rateLimitRemaining;
    public int rateLimitReset;
    
    public String exceptionString;

	public ResponseResult() {
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
        error = _gson.fromJson(responseContent, ErrorObject.class);
    }

	@Override
	public String toString() {
		return _gson.toJson(this);
	}

	public class ErrorObject {
	    public int code;
	    public String message;
	}
	
}
