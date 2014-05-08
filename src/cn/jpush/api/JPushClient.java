package cn.jpush.api;

import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.ReportClient;

/**
 * The overall entrance of JPush API library.
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
	
	/**
	 * Create a JPush Client with overall settings.
	 * 
	 * @param masterSecret API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 * @param apnsProduction Overall APNs environment setting. It will override PushPayload Optional.
	 * @param timeToLive Overall time_to_live setting. It will override PushPayload Optional.
	 */
    public JPushClient(String masterSecret, String appKey, boolean apnsProduction, long timeToLive) {
        _pushClient = new PushClient(masterSecret, appKey, apnsProduction, timeToLive);
        _reportClient = new ReportClient(masterSecret, appKey);
    }
    
    /**
     * Send a push
     * 
     * @param pushPayload payload of a push. 
     * @return PushResult. Can be printed to a JSON.
     */
	public PushResult sendPush(PushPayload pushPayload) {
	    return _pushClient.sendPush(pushPayload);
	}
	
	/**
	 * Get received report. 
	 * 
	 * @param msgIds 100 msgids to batch getting is supported.
	 * @return ReceivedResult. Can be printed to JSON.
	 */
    public ReceivedsResult getReportReceiveds(String msgIds) {
	    return _reportClient.getReceiveds(msgIds);
	}
	
}

