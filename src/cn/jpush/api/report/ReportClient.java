package cn.jpush.api.report;

import java.net.URLEncoder;
import java.util.regex.Pattern;

import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.common.IHttpClient;
import cn.jpush.api.common.NativeHttpClient;
import cn.jpush.api.common.ResponseWrapper;
import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.common.TimeUnit;
import cn.jpush.api.utils.StringUtils;

public class ReportClient {    
    private static final String REPORT_HOST_NAME = "https://report.jpush.cn";
    private static final String REPORT_RECEIVE_PATH = "/v3/received";
    private static final String REPORT_USER_PATH = "/v3/users";
    private static final String REPORT_MESSAGE_PATH = "/v3/messages";

    private final NativeHttpClient _httpClient;
    
    private String _masterSecret;
	private String _appKey;
	
	public ReportClient(String masterSecret, String appKey, int maxRetryTimes) {
        this._masterSecret = masterSecret;
        this._appKey = appKey;
        
        ServiceHelper.checkBasic(appKey, masterSecret);
        
        _httpClient = new NativeHttpClient(maxRetryTimes);
	}
	
    public ReportClient(String masterSecret, String appKey) {
        this(masterSecret, appKey, IHttpClient.DEFAULT_MAX_RETRY_TIMES);
    }
    
	
    public ReceivedsResult getReceiveds(String[] msgIdArray) throws APIConnectionException, APIRequestException {
        return getReceiveds(StringUtils.arrayToString(msgIdArray));
    }
	
    public ReceivedsResult getReceiveds(String msgIds) throws APIConnectionException, APIRequestException {
        checkMsgids(msgIds);
        String authCode = ServiceHelper.getAuthorizationBase64(_appKey, _masterSecret);
        
        String url = REPORT_HOST_NAME + REPORT_RECEIVE_PATH + "?msg_ids=" + msgIds;
        ResponseWrapper response = _httpClient.sendGet(url, null, authCode);
        
        return ReceivedsResult.fromResponse(response);
	}
	
    public MessagesResult getMessages(String msgIds) throws APIConnectionException, APIRequestException {
        checkMsgids(msgIds);
        String authCode = ServiceHelper.getAuthorizationBase64(_appKey, _masterSecret);
        
        String url = REPORT_HOST_NAME + REPORT_MESSAGE_PATH + "?msg_ids=" + msgIds;
        ResponseWrapper response = _httpClient.sendGet(url, null, authCode);
        
        return MessagesResult.fromResponse(response);
    }
    
    public UsersResult getUsers(TimeUnit timeUnit, String start, int duration) throws APIConnectionException, APIRequestException {
        String authCode = ServiceHelper.getAuthorizationBase64(_appKey, _masterSecret);
        
        String startEncoded = null;
        try {
            startEncoded = URLEncoder.encode(start, "utf-8");
        } catch (Exception e) {
        }
        
        String url = REPORT_HOST_NAME + REPORT_USER_PATH
                + "?time_unit=" + timeUnit.toString()
                + "&start=" + startEncoded + "&duration=" + duration;
        ResponseWrapper response = _httpClient.sendGet(url, null, authCode);
        
        return UsersResult.fromResponse(response);
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


