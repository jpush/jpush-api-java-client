package cn.jpush.api.report;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Pattern;

import cn.jpush.api.common.BaseHttpClient;
import cn.jpush.api.common.ResponseResult;
import cn.jpush.api.common.ValidateRequestParams;
import cn.jpush.api.report.ReceivedsResult.Received;
import cn.jpush.api.utils.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ReportClient extends BaseHttpClient {    
    private static final String REPORT_HOST_NAME = "https://report.jpush.cn";
    private static final String REPORT_RECEIVE_PATH = "/v2/received";

	private static Gson _gson = new Gson();
	
    private String _masterSecret;
	private String _appKey;
	
	public ReportClient(String masterSecret, String appKey) {
        this._masterSecret = masterSecret;
        this._appKey = appKey;
        checkBasic(appKey, masterSecret);
	}
	
	
    public ReceivedsResult getReceiveds(String[] msgIdArray) {
        return getReceiveds(StringUtils.arrayToString(msgIdArray));
    }
	
    public ReceivedsResult getReceiveds(String msgIds) {
        String authCode = getAuthorizationBase64(_appKey, _masterSecret);
        return getResportReceived(msgIds, authCode);
	}
	
    public ReceivedsResult getResportReceived(String msgIds, String authCode) {
        ValidateRequestParams.checkReportParams(_appKey, _masterSecret, msgIds);
        
        String url = REPORT_HOST_NAME + REPORT_RECEIVE_PATH + "?msg_ids=" + msgIds;
        ResponseResult result = sendGet(url, null, authCode);
        
        ReceivedsResult receivedsResult = new ReceivedsResult();
        if (result.responseCode == RESPONSE_OK) {
            Type listType = new TypeToken<List<Received>>(){}.getType();
            receivedsResult.receivedList = _gson.fromJson(result.responseContent, listType);
        }
        
        receivedsResult.responseResult = result;
        return receivedsResult;
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


