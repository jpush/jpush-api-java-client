package cn.jpush.api;

import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.common.TimeUnit;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.report.MessagesResult;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.ReportClient;
import cn.jpush.api.report.UsersResult;

/**
 * The global entrance of JPush API library.
 */
public class JPushClient {
    private final PushClient _pushClient;
	private final ReportClient _reportClient;
	
	/**
	 * Create a JPush Client.
	 * 
	 * @param masterSecret API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 */
	public JPushClient(String masterSecret, String appKey) {
	    _pushClient = new PushClient(masterSecret, appKey);
	    _reportClient = new ReportClient(masterSecret, appKey);
	}
	
	public JPushClient(String masterSecret, String appKey, int maxRetryTimes) {
        _pushClient = new PushClient(masterSecret, appKey, maxRetryTimes);
        _reportClient = new ReportClient(masterSecret, appKey, maxRetryTimes);	    
	}
	
	/**
	 * Create a JPush Client with global settings.
	 * 
	 * If you want different settings from default globally, this constructor is what you needed.
	 * 
	 * @param masterSecret API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 * @param apnsProduction Global APNs environment setting. It will override PushPayload Options.
	 * @param timeToLive Global time_to_live setting. It will override PushPayload Options.
	 */
    public JPushClient(String masterSecret, String appKey, boolean apnsProduction, long timeToLive) {
        _pushClient = new PushClient(masterSecret, appKey, apnsProduction, timeToLive);
        _reportClient = new ReportClient(masterSecret, appKey);
    }

    /**
     * Send a push with object.
     * 
     * @param pushPayload payload object of a push. 
     * @return PushResult The result object of a Push. Can be printed to a JSON.
     * @throws APIConnectionException
     * @throws APIRequestException
     */
	public PushResult sendPush(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
	    return _pushClient.sendPush(pushPayload);
	}
	
	/**
	 * Send a push with JSON string.
	 * 
	 * You can send a push JSON string directly with this method.
	 * 
	 * Attention: globally settings cannot be affect this type of Push.
     * 
     * @param pushPayload payload of a push. 
     * @return PushResult. Can be printed to a JSON.
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
    public PushResult sendPush(String payloadString) throws APIConnectionException, APIRequestException {
        return _pushClient.sendPush(payloadString);
    }

    /**
     * Get received report. 
     * 
     * @param msgIds 100 msgids to batch getting is supported.
     * @return ReceivedResult. Can be printed to JSON.
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    public ReceivedsResult getReportReceiveds(String msgIds) throws APIConnectionException, APIRequestException {
	    return _reportClient.getReceiveds(msgIds);
	}
    
    public UsersResult getReportUsers(TimeUnit timeUnit, String start, int duration) throws APIConnectionException, APIRequestException {
        return _reportClient.getUsers(timeUnit, start, duration);
    }
    
    public MessagesResult getReportMessages(String msgIds) throws APIConnectionException, APIRequestException {
        return _reportClient.getMessages(msgIds);
    }
    

}

