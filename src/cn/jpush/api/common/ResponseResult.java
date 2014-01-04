package cn.jpush.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class ResponseResult {
    protected static final Logger LOG = LoggerFactory.getLogger(ResponseResult.class);
    protected static Gson _gson = new Gson();
    
    public int responseCode;
    public String responseContent;
    
    public Error error;     // error for non-200 response, used by new API
    
    public int rateLimitQuota;
    public int rateLimitRemaining;
    public int rateLimitReset;
    
    public String exceptionString;

	public ResponseResult() {
	}
	
    public void setRateLimit(String quota, String remaining, String reset) {
        try {
            rateLimitQuota = Integer.parseInt(quota);
            rateLimitRemaining = Integer.parseInt(remaining);
            rateLimitReset = Integer.parseInt(reset);
        } catch (NumberFormatException e) {
            LOG.error("Unexpected - parse rate limiting headers error.");
        }
    }
    
    public void setErrorObject() {
        error = _gson.fromJson(responseContent, Error.class);
    }

	@Override
	public String toString() {
		return _gson.toJson(this);
	}

	public class Error {
	    public int code;
	    public String message;
	}
	
}
