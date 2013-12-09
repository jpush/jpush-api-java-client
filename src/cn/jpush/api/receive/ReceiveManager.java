package cn.jpush.api.receive;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cn.jpush.http.BaseURL;
import cn.jpush.http.HttpClient;
import cn.jpush.http.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ReceiveManager {
	protected static HttpClient httpClient = new HttpClient();
	private static Gson gson = new Gson();
	private static final Logger log = Logger.getLogger(ReceiveResult.class.getName());
	public String appKey = "";
	public String masterSecret = "";
	
	public ReceiveResult getReceived(String msgId) {
		String sendResult  = sendRequest(msgId);
		try{
			List<ReceiveResult> receiveResults =  gson.fromJson(sendResult,
					new TypeToken<ArrayList<ReceiveResult>>() {}.getType());
			if(receiveResults != null && receiveResults.size() >0)
				return receiveResults.get(0);
			
		}catch (Exception e) {
			log.log(Level.WARNING,String.format("[%s] get received is null." +
					"check it out the response message:[%s]",msgId,sendResult.toString()));
			
			ReceiveResult receiveResults = gson.fromJson(sendResult,ReceiveResult.class);
			return receiveResults;
		}
		return new ReceiveResult();
	}

	public List<ReceiveResult>  getReceiveds(String[] msgIds){
		String msgidStr = StringUtils.arrayToString(msgIds);
		String sendResult = sendRequest(msgidStr);

		try{
			if(sendResult != null && !"".equals(sendResult)){
				List<ReceiveResult> receiveResults =  gson.fromJson(sendResult,
						new TypeToken<ArrayList<ReceiveResult>>() {}.getType());
				return receiveResults;
			}
		}catch (Exception e) {
			log.log(Level.WARNING,"get receiveds is null." +
						"check it out the response message:"+sendResult.toString());
			return null;
		}
		return null;

	}

	protected String sendRequest(String msgId){
		String authCode = StringUtils.getAuthorizationBase64(this.appKey, this.masterSecret);
		return httpClient.sendReceived(BaseURL.RECEIVE_PATH, true, msgId,authCode);
	}
}
