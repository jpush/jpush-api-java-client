package cn.jpush.api.report;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cn.jpush.api.HttpClient;
import cn.jpush.api.http.BaseURL;
import cn.jpush.api.utils.Base64;
import cn.jpush.api.utils.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JPushReportClient {
    private static final Logger log = Logger.getLogger(ReportReceivedResult.class.getName());
    
    private static HttpClient httpClient = new HttpClient();
	private static Gson gson = new Gson();
	
	public String _appKey;
	public String _masterSecret;
	
	public JPushReportClient(String appKey, String masterSecret) {
	    this._appKey = appKey;
	    this._masterSecret = masterSecret;
	}
	
	
	public ReportReceivedResult getReceived(String msgId) {
		List<ReportReceivedResult> list = getReceiveds(new String[] { msgId });
		if (null != list) return list.get(0);
		return null;
	}
	
	public List<ReportReceivedResult> getReceiveds(String[] msgIds){
		String msgidStr = StringUtils.arrayToString(msgIds);
		String sendResult = sendRequest(msgidStr);

		try{
			if(sendResult != null && !"".equals(sendResult)){
				List<ReportReceivedResult> receiveResults =  gson.fromJson(sendResult,
						new TypeToken<ArrayList<ReportReceivedResult>>() {}.getType());
				return receiveResults;
			}
		}catch (Exception e) {
			log.log(Level.WARNING, "get receiveds is null." +
						"check it out the response message:" + sendResult.toString());
		}
		return null;
	}
	
	protected String sendRequest(String msgIds){
		String authCode = getAuthorizationBase64(this._appKey, this._masterSecret);
		return httpClient.sendReceived(BaseURL.RECEIVE_PATH, true, msgIds,authCode);
	}
	
	// Authorization base64 code for receive api
    public static String getAuthorizationBase64(String appKey,String  masterSecret){
        String encodeKey = appKey +":"+masterSecret;
        return String.valueOf(Base64.encode(encodeKey.getBytes())); 
    }
    
}



