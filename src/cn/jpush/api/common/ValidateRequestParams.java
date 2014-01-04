package cn.jpush.api.common;
import java.util.regex.Pattern;

import cn.jpush.api.push.MessageParams;
import cn.jpush.api.utils.StringUtils;

public class ValidateRequestParams {
	private final static int sendNo = -1;
	private final static Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
	
	public static void checkBasic(String appkey, String masterSecret) {
	    if (StringUtils.isEmpty(appkey)
	            || StringUtils.isEmpty(masterSecret)) {
	        throw new IllegalArgumentException("appKey and masterSecret are both required.");
	    }
	    if (appkey.length() != 24 
	            || masterSecret.length() != 24
	            || pattern.matcher(appkey).find()
	            || pattern.matcher(masterSecret).find()) {
	        throw new IllegalArgumentException("appKey and masterSecret format is incorrect.");
	    }
	    
	}
	
	public static void checkPushParams(MessageParams params) {
	    checkBasic(params.getAppKey(), params.getMasterSecret());
	    
	}
	
	public static void checkReportParams() {
	    
	}
}


