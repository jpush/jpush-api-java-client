package cn.jpush.api.report;

import java.util.regex.Pattern;

import cn.jpush.api.common.NativeHttpClient;
import cn.jpush.api.common.ResponseWrapper;
import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.common.TimeUnit;
import cn.jpush.api.utils.StringUtils;

public class ReportClient {    
    private static final String REPORT_HOST_NAME = "http://183.232.25.237:9900";   //"https://report.jpush.cn";
    private static final String REPORT_RECEIVE_PATH = "/v2/received";
    private static final String REPORT_USER_PATH = "/v3/user";
    private static final String REPORT_MESSAGE_PATH = "/v3/message";

    private NativeHttpClient _httpClient = new NativeHttpClient();;
    
    private String _masterSecret;
	private String _appKey;
	
	public ReportClient(String masterSecret, String appKey) {
        this._masterSecret = masterSecret;
        this._appKey = appKey;
        ServiceHelper.checkBasic(appKey, masterSecret);
	}
	
	
    public ReceivedsResult getReceiveds(String[] msgIdArray) {
        return getReceiveds(StringUtils.arrayToString(msgIdArray));
    }
	
    public ReceivedsResult getReceiveds(String msgIds) {
        checkMsgids(msgIds);
        String authCode = ServiceHelper.getAuthorizationBase64(_appKey, _masterSecret);
        
        String url = REPORT_HOST_NAME + REPORT_RECEIVE_PATH + "?msg_ids=" + msgIds;
        ResponseWrapper response = _httpClient.sendGet(url, null, authCode);
        
        return ReceivedsResult.fromResponse(response);
	}
	
    public MessagesResult getMessagesCount(String msgIds) {
        checkMsgids(msgIds);
        String authCode = ServiceHelper.getAuthorizationBase64(_appKey, _masterSecret);
        
        String url = REPORT_HOST_NAME + REPORT_MESSAGE_PATH + "?msg_ids=" + msgIds;
        ResponseWrapper response = _httpClient.sendGet(url, null, authCode);
        
        return MessagesResult.fromResponse(response);
    }
    
    public UsersResult getUsersCount(TimeUnit timeUnit, String start, int step) {
        String authCode = ServiceHelper.getAuthorizationBase64(_appKey, _masterSecret);
        
        String url = REPORT_HOST_NAME + REPORT_USER_PATH
                + "?time_unit=" + timeUnit.toString()
                + "&start=" + start + "&step=" + step;
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


