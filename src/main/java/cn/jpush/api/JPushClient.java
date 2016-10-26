package cn.jpush.api;

import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.TimeUnit;
import cn.jiguang.common.Week;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.BooleanResult;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.device.AliasDeviceListResult;
import cn.jpush.api.device.DeviceClient;
import cn.jpush.api.device.OnlineStatus;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.device.TagListResult;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.MessagesResult;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.ReportClient;
import cn.jpush.api.report.UsersResult;
import cn.jpush.api.schedule.ScheduleClient;
import cn.jpush.api.schedule.ScheduleListResult;
import cn.jpush.api.schedule.ScheduleResult;
import cn.jpush.api.schedule.model.SchedulePayload;
import cn.jpush.api.schedule.model.TriggerPayload;

/**
 * The global entrance of JPush API library.
 */
public class JPushClient {
    private final PushClient _pushClient;
	private final ReportClient _reportClient;
	private final DeviceClient _deviceClient;
    private final ScheduleClient _scheduleClient;
	
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
        _scheduleClient = new ScheduleClient(masterSecret, appKey);
	}

    /**
     * Create a JPush Client by custom Client configuration.
     *
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param conf The client configuration. Can use ClientConfig.getInstance() as default.
     */
    public JPushClient(String masterSecret, String appKey, HttpProxy proxy, ClientConfig conf) {
        _pushClient = new PushClient(masterSecret, appKey, proxy, conf);
        _reportClient = new ReportClient(masterSecret, appKey, proxy, conf);
        _deviceClient = new DeviceClient(masterSecret, appKey, proxy, conf);
        _scheduleClient = new ScheduleClient(masterSecret, appKey, proxy, conf);
    }

    /**
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes The max retry times.
     */
    @Deprecated
	public JPushClient(String masterSecret, String appKey, int maxRetryTimes) {
        _pushClient = new PushClient(masterSecret, appKey, maxRetryTimes);
        _reportClient = new ReportClient(masterSecret, appKey, maxRetryTimes);
        _deviceClient = new DeviceClient(masterSecret, appKey, maxRetryTimes);
        _scheduleClient = new ScheduleClient(masterSecret, appKey, maxRetryTimes);
	}

    /**
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes The max retry times.
     * @param proxy The proxy, if there is no proxy, should be null.
     */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        _pushClient = new PushClient(masterSecret, appKey, maxRetryTimes, proxy);
        _reportClient = new ReportClient(masterSecret, appKey, maxRetryTimes, proxy);
        _deviceClient = new DeviceClient(masterSecret, appKey, maxRetryTimes, proxy);
        _scheduleClient = new ScheduleClient(masterSecret, appKey, maxRetryTimes, proxy);
    }
    
    /**
     * Create a JPush Client by custom Client configuration.
     *
     * If you are using JPush privacy cloud, maybe this constructor is what you needed.
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     *
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes Client request retry times.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param conf The client configuration. Can use ClientConfig.getInstance() as default.
     */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy, ClientConfig conf) {
        conf.setMaxRetryTimes(maxRetryTimes);

        _pushClient = new PushClient(masterSecret, appKey, proxy, conf);
        _reportClient = new ReportClient(masterSecret, appKey, proxy, conf);
        _deviceClient = new DeviceClient(masterSecret, appKey, proxy, conf);
        _scheduleClient = new ScheduleClient(masterSecret, appKey, proxy, conf);

    }

    /**
     * Create a JPush Client by custom Client configuration with global settings.
     *
     * If you are using JPush privacy cloud, and you want different settings from default globally,
     * maybe this constructor is what you needed.
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setGlobalPushSetting} instead of this constructor.
     *
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes Client request retry times.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param conf The client configuration. Can use ClientConfig.getInstance() as default.
     * @param apnsProduction Global APNs environment setting. It will override PushPayload Options.
     * @param timeToLive Global time_to_live setting. It will override PushPayload Options.
     */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy, ClientConfig conf,
                       boolean apnsProduction, long timeToLive) {
        conf.setMaxRetryTimes(maxRetryTimes);
        conf.setApnsProduction(apnsProduction);
        conf.setTimeToLive(timeToLive);
        _pushClient = new PushClient(masterSecret, appKey, proxy, conf);
        _reportClient = new ReportClient(masterSecret, appKey, proxy, conf);
        _deviceClient = new DeviceClient(masterSecret, appKey, proxy, conf);
        _scheduleClient = new ScheduleClient(masterSecret, appKey, proxy, conf);
    }
    
	/**
	 * Create a JPush Client with global settings.
	 * 
	 * If you want different settings from default globally, this constructor is what you needed.
	 * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setGlobalPushSetting} instead of this constructor.
     *
	 * @param masterSecret API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 * @param apnsProduction Global APNs environment setting. It will override PushPayload Options.
	 * @param timeToLive Global time_to_live setting. It will override PushPayload Options.
	 */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, boolean apnsProduction, long timeToLive) {
        ClientConfig conf = ClientConfig.getInstance();
        conf.setApnsProduction(apnsProduction);
        conf.setTimeToLive(timeToLive);
        _pushClient = new PushClient(masterSecret, appKey);
        _reportClient = new ReportClient(masterSecret, appKey);
        _deviceClient = new DeviceClient(masterSecret, appKey);
        _scheduleClient = new ScheduleClient(masterSecret, appKey);
    }
    // ----------------------------- Push API

    /**
     * Send a push with PushPayload object.
     * 
     * @param pushPayload payload object of a push. 
     * @return PushResult The result object of a Push. Can be printed to a JSON.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
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
	 * @throws APIConnectionException if a remote or network exception occurs.
	 * @throws APIRequestException if a request exception occurs.
	 */
    public PushResult sendPush(String payloadString) throws APIConnectionException, APIRequestException {
        return _pushClient.sendPush(payloadString);
    }
    
    /**
     * Validate a push action, but do NOT send it actually.
     * 
     * @param payload payload of a push.
     * @return PushResult. Can be printed to a JSON.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendPushValidate(PushPayload payload) throws APIConnectionException, APIRequestException {
    	return _pushClient.sendPushValidate(payload);
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
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
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

    public PushResult sendNotificationAll(String alert) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.alertAll(alert);
        return _pushClient.sendPush(payload);
    }

    /**
     * Send a notification to all.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The notification content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @return push result
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendNotificationAll(String alert, SMS sms) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.alertAll(alert, sms);
        return _pushClient.sendPush(payload);
    }

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
     * Send a notification to Android with alias.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The notification title.
     * @param alert The notification content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra parameter.
     * @param alias The users' alias.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendAndroidNotificationWithAlias(String title, String alert, SMS sms,
                                                       Map<String, String> extras, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.android(alert, title, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

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
     * Send a notification to Android with RegistrationID.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The notification title.
     * @param alert The notification content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra parameter.
     * @param registrationID The registration id generated by JPush.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendAndroidNotificationWithRegistrationID(String title, String alert, SMS sms,
                                                                Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.android(alert, title, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

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
     * Send a notification to iOS with alias.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     * @param alert The notification content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra parameter.
     * @param alias The users' alias.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithAlias(String alert, SMS sms,
                                                   Map<String, String> extras, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with alias.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     *
     * @param alert The wrapper of APNs alert.
     * @param extras The extra params.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithAlias(IosAlert alert,
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
     * Send an iOS notification with alias.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The wrapper of APNs alert.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra params.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithAlias(IosAlert alert, SMS sms,
                                                   Map<String, String> extras, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with alias.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     *
     * @param alert The JSON object of APNs alert.
     * @param extras The extra params.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithAlias(JsonObject alert,
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
     * Send an iOS notification with alias.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The JSON object of APNs alert.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra params.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithAlias(JsonObject alert, SMS sms,
                                                   Map<String, String> extras, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

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

    /**
     * Send an iOS notification with registrationIds.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The notification content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra params.
     * @param registrationID The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithRegistrationID(String alert, SMS sms,
                                                            Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with registrationIds.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     *
     * @param alert The wrapper of APNs alert.
     * @param extras The extra params.
     * @param registrationID The registration ids.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithRegistrationID(IosAlert alert,
                                                            Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with registrationIds.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The wrapper of APNs alert.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra params.
     * @param registrationID The registration ids.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithRegistrationID(IosAlert alert, SMS sms,
                                                            Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with registrationIds.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     *
     * @param alert The wrapper of APNs alert.
     * @param extras The extra params.
     * @param registrationID The registration ids.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithRegistrationID(JsonObject alert,
                                                            Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with registrationIds.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The JSON object of APNs alert.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra params.
     * @param registrationID The registration ids.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithRegistrationID(JsonObject alert, SMS sms,
                                                            Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    
    // ---------------------- shortcuts - message

    public PushResult sendMessageAll(String msgContent) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.messageAll(msgContent);
        return _pushClient.sendPush(payload);
    }

    /**
     * Send a message to all
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendMessageAll(String msgContent, SMS sms) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.messageAll(msgContent, sms);
        return _pushClient.sendPush(payload);
    }

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
     * Send an Android message with alias.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The message title.
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendAndroidMessageWithAlias(String title, String msgContent, SMS sms, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

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
     * Send an Android message with registration id.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The message title.
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param registrationID The registration id list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendAndroidMessageWithRegistrationID(String title, String msgContent, SMS sms, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

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
     * Send an iOS message with alias.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The message title.
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosMessageWithAlias(String title, String msgContent, SMS sms, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

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
     * Send an iOS message with registration id.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The message title.
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param registrationID The registrationIds generated by JPush.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosMessageWithRegistrationID(String title, String msgContent, SMS sms, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

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

    /**
     * Send a message with registrationIds.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The message title.
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param registrationID The registrationIds generated by JPush.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendMessageWithRegistrationID(String title, String msgContent, SMS sms, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .setSMS(sms)
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

    public Map<String, OnlineStatus> getUserOnlineStatus(String... registrationIds)
            throws APIConnectionException, APIRequestException
    {
        return _deviceClient.getUserOnlineStatus(registrationIds);
    }

    public DefaultResult bindMobile(String registrationId, String mobile)
            throws APIConnectionException, APIRequestException
    {
        return _deviceClient.bindMobile(registrationId, mobile);
    }

    // ----------------------- Schedule

    /**
     * Create a single schedule.
     * @param name The schedule name.
     * @param time The push time, format is 'yyyy-MM-dd HH:mm:ss'
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult createSingleSchedule(String name, String time, PushPayload push)
            throws APIConnectionException, APIRequestException {
        TriggerPayload trigger = TriggerPayload.newBuilder()
                .setSingleTime(time)
                .buildSingle();
        SchedulePayload payload = SchedulePayload.newBuilder()
                .setName(name)
                .setEnabled(true)
                .setTrigger(trigger)
                .setPush(push)
                .build();

        return _scheduleClient.createSchedule(payload);
    }

    /**
     * Create a daily schedule push everyday.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult createDailySchedule(String name, String start, String end, String time, PushPayload push)
            throws APIConnectionException, APIRequestException {
        return createPeriodicalSchedule(name, start, end, time, TimeUnit.DAY, 1, null, push);
    }

    /**
     * Create a daily schedule push with a custom frequency.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'
     * @param frequency The custom frequency.
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult createDailySchedule(String name, String start, String end, String time, int frequency, PushPayload push)
            throws APIConnectionException, APIRequestException {
        return createPeriodicalSchedule(name, start, end, time, TimeUnit.DAY, frequency, null, push);
    }

    /**
     * Create a weekly schedule push every week at the appointed days.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'
     * @param days The appointed days.
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult createWeeklySchedule(String name, String start, String end, String time, Week[] days, PushPayload push)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != days && days.length > 0, "The days must not be empty.");

        String[] points = new String[days.length];
        for(int i = 0 ; i < days.length; i++) {
            points[i] = days[i].name();
        }
        return createPeriodicalSchedule(name, start, end, time, TimeUnit.WEEK, 1, points, push);
    }

    /**
     * Create a weekly schedule push with a custom frequency at the appointed days.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'.
     * @param frequency The custom frequency.
     * @param days The appointed days.
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult createWeeklySchedule(String name, String start, String end, String time, int frequency, Week[] days, PushPayload push)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != days && days.length > 0, "The days must not be empty.");

        String[] points = new String[days.length];
        for(int i = 0 ; i < days.length; i++) {
            points[i] = days[i].name();
        }
        return createPeriodicalSchedule(name, start, end, time, TimeUnit.WEEK, frequency, points, push);
    }

    /**
     * Create a monthly schedule push every month at the appointed days.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'.
     * @param points The appointed days.
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult createMonthlySchedule(String name, String start, String end, String time, String[] points, PushPayload push)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != points && points.length > 0, "The points must not be empty.");
        return createPeriodicalSchedule(name, start, end, time, TimeUnit.MONTH, 1, points, push);
    }

    /**
     * Create a monthly schedule push with a custom frequency at the appointed days.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'.
     * @param frequency The custom frequency.
     * @param points The appointed days.
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult createMonthlySchedule(String name, String start, String end, String time, int frequency, String[] points, PushPayload push)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != points && points.length > 0, "The points must not be empty.");
        return createPeriodicalSchedule(name, start, end, time, TimeUnit.MONTH, frequency, points, push);
    }

    /**
     * Get the schedule information by the schedule id.
     * @param scheduleId The schedule id.
     * @return The schedule information.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult getSchedule(String scheduleId)
            throws APIConnectionException, APIRequestException {
        return  _scheduleClient.getSchedule(scheduleId);
    }

    /**
     * Get the schedule list size and the first page.
     * @return The schedule list size and the first page.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleListResult getScheduleList()
            throws APIConnectionException, APIRequestException {
        return _scheduleClient.getScheduleList(1);
    }

    /**
     * Get the schedule list by the page.
     * @param page The page to search.
     * @return The schedule list of the appointed page.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleListResult getScheduleList(int page)
            throws APIConnectionException, APIRequestException {
        return _scheduleClient.getScheduleList(page);
    }

    /**
     * Update the schedule name
     * @param scheduleId The schedule id.
     * @param name The new name.
     * @return The schedule information after updated.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult updateScheduleName(String scheduleId, String name)
            throws APIConnectionException, APIRequestException {
        SchedulePayload payload = SchedulePayload.newBuilder()
                .setName(name)
                .build();

        return updateSchedule(scheduleId, payload);
    }

    /**
     * Enable the schedule.
     * @param scheduleId The schedule id.
     * @return The schedule information after updated.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult enableSchedule(String scheduleId)
            throws APIConnectionException, APIRequestException {
        SchedulePayload payload = SchedulePayload.newBuilder()
                .setEnabled(true)
                .build();

        return updateSchedule(scheduleId, payload);
    }

    /**
     * Disable the schedule.
     * @param scheduleId The schedule id.
     * @return The schedule information after updated.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult disableSchedule(String scheduleId)
            throws APIConnectionException, APIRequestException {
        SchedulePayload payload = SchedulePayload.newBuilder()
                .setEnabled(false)
                .build();
        return updateSchedule(scheduleId, payload);
    }

    /**
     * Update the trigger of the schedule.
     * @param scheduleId The schedule id.
     * @param trigger The new trigger.
     * @return The schedule information after updated.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult updateScheduleTrigger(String scheduleId, TriggerPayload trigger)
            throws APIConnectionException, APIRequestException {
        SchedulePayload payload = SchedulePayload.newBuilder()
                .setTrigger(trigger)
                .build();

        return updateSchedule(scheduleId, payload);
    }

    /**
     * Update the push content of the schedule.
     * @param scheduleId The schedule id.
     * @param push The new push payload.
     * @return The schedule information after updated.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult updateSchedulePush(String scheduleId, PushPayload push)
            throws APIConnectionException, APIRequestException {
        SchedulePayload payload = SchedulePayload.newBuilder()
                .setPush(push)
                .build();

        return updateSchedule(scheduleId, payload);
    }

    /**
     * Update a schedule by the id.
     * @param scheduleId The schedule id to update.
     * @param payload The new schedule payload.
     * @return The new schedule information.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public ScheduleResult updateSchedule(String scheduleId, SchedulePayload payload)
            throws APIConnectionException, APIRequestException {
        return _scheduleClient.updateSchedule(scheduleId, payload);
    }

    /**
     * Delete a schedule by id.
     * @param scheduleId The schedule id.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public void deleteSchedule(String scheduleId)
            throws APIConnectionException, APIRequestException {
        _scheduleClient.deleteSchedule(scheduleId);
    }

    private ScheduleResult createPeriodicalSchedule(String name, String start, String end, String time,
                                                    TimeUnit timeUnit, int frequency, String[] point, PushPayload push)
            throws APIConnectionException, APIRequestException {
        TriggerPayload trigger = TriggerPayload.newBuilder()
                .setPeriodTime(start, end, time)
                .setTimeFrequency(timeUnit, frequency, point )
                .buildPeriodical();
        SchedulePayload payload = SchedulePayload.newBuilder()
                .setName(name)
                .setEnabled(true)
                .setTrigger(trigger)
                .setPush(push)
                .build();

        return _scheduleClient.createSchedule(payload);
    }

}

