package cn.jpush.api.report;

import java.net.URLEncoder;
import java.util.regex.Pattern;

import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.common.TimeUnit;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.connection.IHttpClient;
import cn.jpush.api.common.connection.NativeHttpClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.BaseResult;
import cn.jpush.api.common.resp.ResponseWrapper;
import cn.jpush.api.utils.StringUtils;

public class ReportClient {    
    private static final String REPORT_HOST_NAME = "https://report.jpush.cn";
    private static final String REPORT_RECEIVE_PATH = "/v3/received";
    private static final String REPORT_USER_PATH = "/v3/users";
    private static final String REPORT_MESSAGE_PATH = "/v3/messages";

    private final NativeHttpClient _httpClient;

    public ReportClient(String masterSecret, String appKey) {
        this(masterSecret, appKey, IHttpClient.DEFAULT_MAX_RETRY_TIMES, null);
    }
    
	public ReportClient(String masterSecret, String appKey, int maxRetryTimes) {
	    this(masterSecret, appKey, maxRetryTimes, null);
	}
	
	public ReportClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        ServiceHelper.checkBasic(appKey, masterSecret);
        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        
        _httpClient = new NativeHttpClient(authCode, maxRetryTimes, proxy);
	}
	
	
    public ReceivedsResult getReceiveds(String[] msgIdArray) 
            throws APIConnectionException, APIRequestException {
        return getReceiveds(StringUtils.arrayToString(msgIdArray));
    }
	
    public ReceivedsResult getReceiveds(String msgIds) 
            throws APIConnectionException, APIRequestException {
        checkMsgids(msgIds);
        
        String url = REPORT_HOST_NAME + REPORT_RECEIVE_PATH + "?msg_ids=" + msgIds;
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return ReceivedsResult.fromResponse(response);
	}
	
    public MessagesResult getMessages(String msgIds) 
            throws APIConnectionException, APIRequestException {
        checkMsgids(msgIds);
        
        String url = REPORT_HOST_NAME + REPORT_MESSAGE_PATH + "?msg_ids=" + msgIds;
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return MessagesResult.fromResponse(response);
    }
    
    public UsersResult getUsers(TimeUnit timeUnit, String start, int duration) 
            throws APIConnectionException, APIRequestException {        
        String startEncoded = null;
        try {
            startEncoded = URLEncoder.encode(start, "utf-8");
        } catch (Exception e) {
        }
        
        String url = REPORT_HOST_NAME + REPORT_USER_PATH
                + "?time_unit=" + timeUnit.toString()
                + "&start=" + startEncoded + "&duration=" + duration;
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return BaseResult.fromResponse(response, UsersResult.class);
    }
    
    
    private final static Pattern MSGID_PATTERNS = Pattern.compile("[^0-9, ]");

    public static void checkMsgids(String msgIds) {
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


