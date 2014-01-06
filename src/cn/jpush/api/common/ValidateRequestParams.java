package cn.jpush.api.common;
import java.util.regex.Pattern;

import cn.jpush.api.push.MessageParams;
import cn.jpush.api.utils.StringUtils;

public class ValidateRequestParams {
	private final static Pattern PUSH_PATTERNS = Pattern.compile("[^a-zA-Z0-9]");
	private final static Pattern MSGID_PATTERNS = Pattern.compile("[^0-9, ]");
	
	public static void checkBasic(String appKey, String masterSecret) {
	    if (StringUtils.isEmpty(appKey)
	            || StringUtils.isEmpty(masterSecret)) {
	        throw new IllegalArgumentException("appKey and masterSecret are both required.");
	    }
	    if (appKey.length() != 24 
	            || masterSecret.length() != 24
	            || PUSH_PATTERNS.matcher(appKey).find()
	            || PUSH_PATTERNS.matcher(masterSecret).find()) {
	        throw new IllegalArgumentException("appKey and masterSecret format is incorrect. "
	                + "They should be 24 size, and be composed with alphabet and numbers. "
	                + "Please confirm that they are coming from JPush Web Portal.");
	    }
	    
	}
	
	public static void checkPushParams(MessageParams params) {
	    checkBasic(params.getAppKey(), params.getMasterSecret());
	    
	    
	}
	
	public static void checkReportParams(String appKey, String masterSecret, String msgIds) {
	    checkBasic(appKey, masterSecret);
	    
	    if (StringUtils.isTrimedEmpty(msgIds)) {
	        throw new IllegalArgumentException("msgIds param is required.");
	    }
	    
	    if (MSGID_PATTERNS.matcher(msgIds).find()) {
	        throw new IllegalArgumentException("msgIds param format is incorrect. "
	                + "It should be msg_id (number) which response from JPush Push API. "
	                + "If there are many, use ',' as interval. ");
	    }
	    
	    msgIds = msgIds.trim();
	    if (msgIds.endsWith(",")) {
	        msgIds = msgIds.substring(0, msgIds.length() - 1);
	    }
	    
	    String[] splits = msgIds.split(",");
	    try {
    	    for (String s : splits) {
    	        s = s.trim();
    	        if (!StringUtils.isEmpty(s)) {
                    Integer.parseInt(s);
    	        }
    	    }
	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("Every msg_id should be valid Integer number which splits by ','");
	    }
	}
}


