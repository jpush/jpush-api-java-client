package cn.jpush.api.report;

import java.lang.reflect.Type;
import java.util.List;

import cn.jpush.api.common.BaseHttpClient;
import cn.jpush.api.common.ResponseResult;
import cn.jpush.api.report.ReceivedsResult.Received;
import cn.jpush.api.utils.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ReportClient extends BaseHttpClient {    
	private static Gson _gson = new Gson();
	
    public String _appKey;
    public String _masterSecret;
	
	public ReportClient(String masterSecret, String appKey) {
        this._masterSecret = masterSecret;
	    this._appKey = appKey;
	}
	
	
	public ReceivedsResult getReceived(String msgId) {
	    return getReceiveds(new String[] { msgId });
	}
	
    public ReceivedsResult getReceiveds(String[] msgIdArray) {
        String msgIds = StringUtils.arrayToString(msgIdArray);
        return getReceiveds(msgIdArray);
    }
	
    public ReceivedsResult getReceiveds(String msgIds) {
        String authCode = getAuthorizationBase64(_appKey, _masterSecret);
        return getResportReceived(msgIds, authCode);
	}
	
    public static String REPORT_HOST_NAME = "https://report.jpush.cn";
    public static String REPORT_RECEIVE_PATH = "/v2/received";

    public ReceivedsResult getResportReceived(String msgIds, String authCode) {
        String url = REPORT_HOST_NAME + REPORT_RECEIVE_PATH + "?msg_ids=" + msgIds;
        ResponseResult result = sendGet(url, true, null, authCode);
        
        ReceivedsResult rrr = new ReceivedsResult();
        if (result.responseCode == 200) {
            Type listType = new TypeToken<List<Received>>(){}.getType();
            rrr.receivedList = _gson.fromJson(result.responseContent, listType);
        }
        
        rrr.responseResult = result;
        return rrr;
    }

}



