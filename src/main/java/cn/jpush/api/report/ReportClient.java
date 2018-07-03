package cn.jpush.api.report;


import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Pattern;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.TimeUnit;
import cn.jiguang.common.utils.StringUtils;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.report.model.CheckMessagePayload;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ReportClient {    

    private final NativeHttpClient _httpClient;
    private String _hostName;
    private String _receivePath;
    private String _userPath;
    private String _messagePath;
    private String _statusPath;

    public ReportClient(String masterSecret, String appKey) {
        this(masterSecret, appKey, null, ClientConfig.getInstance());
    }

    /**
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     * @param masterSecret  API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes max retry times.
     *
     */
    @Deprecated
	public ReportClient(String masterSecret, String appKey, int maxRetryTimes) {
	    this(masterSecret, appKey, maxRetryTimes, null);
	}

    /**
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     * @param masterSecret  API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes max retry times
     * @param proxy The max retry times.
     *
     */
    @Deprecated
	public ReportClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        ServiceHelper.checkBasic(appKey, masterSecret);

        ClientConfig conf = ClientConfig.getInstance();
        conf.setMaxRetryTimes(maxRetryTimes);

        _hostName = (String) conf.get(ClientConfig.REPORT_HOST_NAME);
        _receivePath = (String) conf.get(ClientConfig.REPORT_RECEIVE_PATH);
        _userPath = (String) conf.get(ClientConfig.REPORT_USER_PATH);
        _messagePath = (String) conf.get(ClientConfig.REPORT_MESSAGE_PATH);
        _statusPath = (String) conf.get(ClientConfig.REPORT_STATUS_PATH);
        
        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        _httpClient = new NativeHttpClient(authCode, proxy, conf);
	}

    public ReportClient(String masterSecret, String appKey, HttpProxy proxy, ClientConfig conf) {
        ServiceHelper.checkBasic(appKey, masterSecret);

        _hostName = (String) conf.get(ClientConfig.REPORT_HOST_NAME);
        _receivePath = (String) conf.get(ClientConfig.REPORT_RECEIVE_PATH);
        _userPath = (String) conf.get(ClientConfig.REPORT_USER_PATH);
        _messagePath = (String) conf.get(ClientConfig.REPORT_MESSAGE_PATH);
        _statusPath = (String) conf.get(ClientConfig.REPORT_STATUS_PATH);

        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        _httpClient = new NativeHttpClient(authCode, proxy, conf);
    }
	
	
    public ReceivedsResult getReceiveds(String[] msgIdArray) 
            throws APIConnectionException, APIRequestException {
        return getReceiveds(StringUtils.arrayToString(msgIdArray));
    }
	
    public ReceivedsResult getReceiveds(String msgIds) 
            throws APIConnectionException, APIRequestException {
        checkMsgids(msgIds);
        
        String url = _hostName + _receivePath + "?msg_ids=" + msgIds;
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return ReceivedsResult.fromResponse(response);
	}
	
    public MessagesResult getMessages(String msgIds) 
            throws APIConnectionException, APIRequestException {
        checkMsgids(msgIds);
        
        String url = _hostName + _messagePath + "?msg_ids=" + msgIds;
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return MessagesResult.fromResponse(response);
    }

    public Map<String, MessageStatus> getMessagesStatus(CheckMessagePayload payload)
            throws APIConnectionException, APIRequestException {
        String url = _hostName + (_statusPath.endsWith("/message")?_statusPath:(_statusPath+"/message"));
        ResponseWrapper result = _httpClient.sendPost(url, payload.toString());
        Type type = new TypeToken<Map<String, MessageStatus>>(){}.getType();
        return new Gson().fromJson(result.responseContent, type);
    }
    
    public UsersResult getUsers(TimeUnit timeUnit, String start, int duration) 
            throws APIConnectionException, APIRequestException {        
        String startEncoded = null;
        try {
            startEncoded = URLEncoder.encode(start, "utf-8");
        } catch (Exception e) {
        }
        
        String url = _hostName + _userPath
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
                    Long.parseLong(s);
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Every msg_id should be valid Long number which splits by ','");
        }
    }

}


