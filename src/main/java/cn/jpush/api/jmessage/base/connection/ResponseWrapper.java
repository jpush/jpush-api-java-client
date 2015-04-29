package cn.jpush.api.jmessage.base.connection;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseWrapper {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseWrapper.class);
    private static final int RESPONSE_CODE_NONE = -1;
    private static Gson _gson = new Gson();
    
    public int responseCode = RESPONSE_CODE_NONE;
    public String responseContent;

    public boolean isServerResponse() {
        if (responseCode == 200) return true;
        return false;
    }
    
	@Override
	public String toString() {
		return _gson.toJson(this);
	}
	
}
