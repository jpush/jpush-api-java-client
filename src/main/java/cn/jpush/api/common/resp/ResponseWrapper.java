package cn.jpush.api.common.resp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class ResponseWrapper implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseWrapper.class);
    private static final int RESPONSE_CODE_NONE = -1;
    private static final long serialVersionUID = -4227962073448507865L;

    private static Gson _gson = new Gson();
    private static JsonParser jsonParser = new JsonParser();
    
    public int responseCode = RESPONSE_CODE_NONE;
    public String responseContent;
    
    public ErrorObject error;     // error for non-200 response, used by new API
    
    public int rateLimitQuota;
    public int rateLimitRemaining;
    public int rateLimitReset;
	
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
        error = new ErrorObject();
        error.error = new ErrorEntity();
        try {
            JsonElement element = jsonParser.parse(responseContent);
            JsonObject errorObj = null;
            if( element instanceof JsonArray) {
                JsonArray array = (JsonArray) element;
                for(int i = 0; i < array.size(); i++) {
                    if(array.get(i).getAsJsonObject().has("error")) {
                        errorObj = array.get(i).getAsJsonObject();
                        break;
                    }
                }
            } else if(element instanceof JsonObject) {
                errorObj = (JsonObject) element;
            } else {
                // nothing
            }
            if(null != errorObj) {
                JsonObject errorMsg = errorObj;
                if(errorObj.has("msg_id")) {
                    error.msg_id = errorObj.get("msg_id").getAsLong();
                }
                if (errorObj.has("error")) {
                    errorMsg = (JsonObject) errorObj.get("error");
                }
                if(errorMsg.has("code")) {
                    error.error.code = errorMsg.get("code").getAsInt();
                }
                if(errorMsg.has("message")) {
                    error.error.message = errorMsg.get("message").getAsString();
                }
            }
        } catch(JsonSyntaxException e) {
            LOG.error("Unexpected - responseContent:" + responseContent, e);
        } catch (Exception e) {
            LOG.error("Unexpected - responseContent:" + responseContent, e);
        }
    }
    
    public boolean isServerResponse() {
        if (responseCode / 100 == 2) return true;
        if (responseCode > 0 && null != error && error.error.code > 0) return true;
        return false;
    }
    
	@Override
	public String toString() {
		return _gson.toJson(this);
	}
	
	public static class ErrorObject {
	    public long msg_id;
        public ErrorEntity error;
	}
	
	public static class ErrorEntity {
	    public int code;
	    public String message;

	    @Override
        public String toString() {
            return _gson.toJson(this);
        }
	}
	
}
