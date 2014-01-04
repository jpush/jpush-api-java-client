package cn.jpush.api;

import java.util.Map;

import cn.jpush.api.common.DeviceEnum;
import cn.jpush.api.push.CustomMessageParams;
import cn.jpush.api.push.MessageParams;
import cn.jpush.api.push.MessageResult;
import cn.jpush.api.push.NotificationParams;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.ReceiverTypeEnum;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.ReportClient;

/**
 * The overall entrance of JPush API library.
 * 
 */
public class JPushClient {
    private PushClient _pushClient;
	private ReportClient _reportClient;
	
	public JPushClient(String masterSecret, String appKey) {
	    this(masterSecret, appKey, MessageParams.NO_TIME_TO_LIVE, null, false);
	}
	
	public JPushClient(String masterSecret, String appKey, long timeToLive, DeviceEnum device, boolean apnsProduction) {
	    _pushClient = new PushClient(masterSecret, appKey, timeToLive, device, apnsProduction);
	    _reportClient = new ReportClient(masterSecret, appKey);
	}
	
	public MessageResult sendNotification(String notificationContent, NotificationParams params, Map<String, Object> extras) {
	    return _pushClient.sendNotification(notificationContent, params, extras);
	}

	public MessageResult sendCustomMessage(String msgTitle, String msgContent, CustomMessageParams params, Map<String, Object> extras) {
	    return _pushClient.sendCustomMessage(msgTitle, msgContent, params, extras);
	}
	
	
	public MessageResult sendCustomMessageAll(String msgTitle, String msgContent) {
	    CustomMessageParams params = new CustomMessageParams();
        params.setReceiverType(ReceiverTypeEnum.APP_KEY);
        //params.setTimeToLive(MessageParams.DEFAULT_TIME_TO_LIVE);
        //params.setSendNo(1);
        //params.setOverrideMsgId("");
	    return _pushClient.sendCustomMessage(msgTitle, msgContent, params, null);
	}
	
	public MessageResult sendNotificationAll(String notificationContent) {
	    NotificationParams params = new NotificationParams();
	    params.setReceiverType(ReceiverTypeEnum.APP_KEY);
        //params.setTimeToLive(MessageParams.DEFAULT_TIME_TO_LIVE);
	    //params.setSendNo(1);
        //params.setAndroidNotificationTitle("");
        //params.setAndroidBuilderId(0);
        //params.setOverrideMsgId("");
		return _pushClient.sendNotification(notificationContent, params, null);
	}
	
	
    public ReceivedsResult getReportReceiveds(String msgIds) {
	    return _reportClient.getReceiveds(msgIds);
	}
	
}

