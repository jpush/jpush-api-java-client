package cn.jpush.api;

import java.util.Map;
import java.util.Set;

import cn.jpush.api.common.TimeUnit;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.BooleanResult;
import cn.jpush.api.common.resp.DefaultResult;
import cn.jpush.api.device.AliasDeviceListResult;
import cn.jpush.api.device.DeviceClient;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.device.TagListResult;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
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
	private final DeviceClient _deviceClient;
	
	/**
	 * Create a JPush Client.
	 * 
	 * @param masterSecret API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 */
	public JPushClient(String masterSecret, String appKey) {
	    _pushClient = new PushClient(masterSecret, appKey);
	    _reportClient = new ReportClient(masterSecret, appKey);
	    _deviceClient = new DeviceClient(masterSecret, appKey);
	}
	
	public JPushClient(String masterSecret, String appKey, int maxRetryTimes) {
        _pushClient = new PushClient(masterSecret, appKey, maxRetryTimes);
        _reportClient = new ReportClient(masterSecret, appKey, maxRetryTimes);
        _deviceClient = new DeviceClient(masterSecret, appKey, maxRetryTimes);
	}
	
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        _pushClient = new PushClient(masterSecret, appKey, maxRetryTimes, proxy);
        _reportClient = new ReportClient(masterSecret, appKey, maxRetryTimes, proxy);
        _deviceClient = new DeviceClient(masterSecret, appKey, maxRetryTimes, proxy);
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
        _deviceClient = new DeviceClient(masterSecret, appKey);
    }
    
    
    // ----------------------------- Push API

    /**
     * Send a push with PushPayload object.
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
     * @param  payloadString payload of a push.
     * @return PushResult. Can be printed to a JSON.
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
    public PushResult sendPush(String payloadString) throws APIConnectionException, APIRequestException {
        return _pushClient.sendPush(payloadString);
    }
    
    /**
     * Validate a push action, but do NOT send it actually.
     * 
     * @param paylaod
     * @return
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    public PushResult sendPushValidate(PushPayload paylaod) throws APIConnectionException, APIRequestException {
    	return _pushClient.sendPushValidate(paylaod);
    }

    public PushResult sendPushValidate(String payloadString) throws APIConnectionException, APIRequestException {
    	return _pushClient.sendPushValidate(payloadString);
    }

    
    // ------------------------------- Report API

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
    
    
    // ------------------------------ Shortcuts - notification
    
    /**
     * Shortcut
     */
    public PushResult sendNotificationAll(String alert) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.alertAll(alert);
        return _pushClient.sendPush(payload);
    }
    
    /**
     * Shortcut
     */
    public PushResult sendAndroidNotificationWithAlias(String title, String alert, 
            Map<String, String> extras, String... alias) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.android(alert, title, extras))
                .build();
        return _pushClient.sendPush(payload);
    }
    
    /**
     * Shortcut
     */
    public PushResult sendAndroidNotificationWithRegistrationID(String title, String alert, 
            Map<String, String> extras, String... registrationID) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.android(alert, title, extras))
                .build();
        return _pushClient.sendPush(payload);
    }
    
    /**
     * Shortcut
     */
    public PushResult sendIosNotificationWithAlias(String alert, 
            Map<String, String> extras, String... alias) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.ios(alert, extras))
                .build();
        return _pushClient.sendPush(payload);
    }
    
    /**
     * Shortcut
     */
    public PushResult sendIosNotificationWithRegistrationID(String alert, 
            Map<String, String> extras, String... registrationID) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    
    // ---------------------- shortcuts - message
    
    /**
     * Shortcut
     */
    public PushResult sendMessageAll(String msgContent) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.messageAll(msgContent);
        return _pushClient.sendPush(payload);
    }
    
    /**
     * Shortcut
     */
    public PushResult sendAndroidMessageWithAlias(String title, String msgContent, String... alias) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .build();
        return _pushClient.sendPush(payload);
    }
    
    /**
     * Shortcut
     */
    public PushResult sendAndroidMessageWithRegistrationID(String title, String msgContent, String... registrationID) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .build();
        return _pushClient.sendPush(payload);
    }
    
    /**
     * Shortcut
     */
    public PushResult sendIosMessageWithAlias(String title, String msgContent, String... alias) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .build();
        return _pushClient.sendPush(payload);
    }
    
    /**
     * Shortcut
     */
    public PushResult sendIosMessageWithRegistrationID(String title, String msgContent, String... registrationID) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Shortcut
     */
    public PushResult sendMessageWithRegistrationID(String title, String msgContent, String... registrationID) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .build();
        return _pushClient.sendPush(payload);
    }


    
    // ----------------------- Device
    
    public TagAliasResult getDeviceTagAlias(String registrationId) 
    		throws APIConnectionException, APIRequestException {
    	return _deviceClient.getDeviceTagAlias(registrationId);
    }

    public DefaultResult updateDeviceTagAlias(String registrationId, boolean clearAlias, boolean clearTag)
    		throws APIConnectionException, APIRequestException {
    	return _deviceClient.updateDeviceTagAlias(registrationId, clearAlias, clearTag);
    }
    
    public DefaultResult updateDeviceTagAlias(String registrationId, String alias,  
            	Set<String> tagsToAdd, Set<String> tagsToRemove)
            throws APIConnectionException, APIRequestException {
    	return _deviceClient.updateDeviceTagAlias(registrationId, alias, tagsToAdd, tagsToRemove);
    }

	public TagListResult getTagList()
			throws APIConnectionException, APIRequestException {
		return _deviceClient.getTagList();
	}

	public BooleanResult isDeviceInTag(String theTag, String registrationID)
			throws APIConnectionException, APIRequestException {
		return _deviceClient.isDeviceInTag(theTag, registrationID);
	}

	public DefaultResult addRemoveDevicesFromTag(String theTag,
				Set<String> toAddUsers, Set<String> toRemoveUsers)
			throws APIConnectionException, APIRequestException {
		return _deviceClient.addRemoveDevicesFromTag(theTag, toAddUsers,
				toRemoveUsers);
	}

	public DefaultResult deleteTag(String theTag, String platform)
			throws APIConnectionException, APIRequestException {
		return _deviceClient.deleteTag(theTag, platform);
	}

	public AliasDeviceListResult getAliasDeviceList(String alias,
			String platform) throws APIConnectionException, APIRequestException {
		return _deviceClient.getAliasDeviceList(alias, platform);
	}

	public DefaultResult deleteAlias(String alias, String platform)
			throws APIConnectionException, APIRequestException {
		return _deviceClient.deleteAlias(alias, platform);
	}
    
    
}

