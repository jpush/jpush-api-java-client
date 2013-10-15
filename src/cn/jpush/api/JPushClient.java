package cn.jpush.api;

import java.util.Map;

/**
 * The entrance of JPush API library.
 *
 */
public class JPushClient extends BaseClient {

	protected static HttpPostClient httpClient = new HttpPostClient();

	public JPushClient(String masterSecret, String appKey) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
	}

	public JPushClient(String masterSecret, String appKey, long timeToLive) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
		this.timeToLive = timeToLive;
	}

	public JPushClient(String masterSecret, String appKey, DeviceEnum device) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
		devices.add(device);
	}

	public JPushClient(String masterSecret, String appKey, long timeToLive, DeviceEnum device) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
		this.timeToLive = timeToLive;
		this.devices.add(device);
	}
	
	/*
	 * @description 发送带TAG的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithTag(int sendNo, String tag, String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendNotification(p, sendNo, msgTitle, msgContent, 0, null);
	}

	/*
	 * @params builderId通知栏样式
	 * @description 发送带TAG的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithTag(int sendNo, String tag, String msgTitle, String msgContent, int builderId, Map<String, Object> extra) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendNotification(p, sendNo, msgTitle, msgContent, builderId, extra);
	}

	/*
	 * @params overrideMsgId 待覆盖的上一条消息的 ID
	 * @params builderId通知栏样式
	 * @description 发送带TAG的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithTag(int sendNo, String tag, String msgTitle, String msgContent, int builderId, Map<String, Object> extra,String overrideMsgId) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		p.setOverrideMsgId(overrideMsgId);

		return sendNotification(p, sendNo, msgTitle, msgContent, builderId, extra);
	}

	/*
	 * @description 发送带TAG的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithTag(int sendNo, String tag, String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, null, null);
	}

	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发送带TAG的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithTag(int sendNo, String tag, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, msgContentType, extra);
	}

	/*
	 * @params overrideMsgId 待覆盖的上一条消息的 ID
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发送带TAG的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithTag(int sendNo, String tag, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra,String overrideMsgId) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		p.setOverrideMsgId(overrideMsgId);

		return sendCustomMessage(p, sendNo, msgTitle, msgContent, msgContentType, extra);
	}

	/*
	 * @description 发送带ALIAS的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAlias(int sendNo, String alias, String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendNotification(p, sendNo, msgTitle, msgContent, 0, null);
	}

	/*
	 * @params builderId通知栏样式
	 * @description 发送带ALIAS的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAlias(int sendNo, String alias, String msgTitle, String msgContent, int builderId, Map<String, Object> extra) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendNotification(p, sendNo, msgTitle, msgContent, builderId, extra);
	}

	/*
	 * @params overrideMsgId 待覆盖的上一条消息的 ID
	 * @params builderId通知栏样式
	 * @description 发送带ALIAS的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAlias(int sendNo, String alias, String msgTitle, String msgContent, int builderId, Map<String, Object> extra,String overrideMsgId) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		p.setOverrideMsgId(overrideMsgId);

		return sendNotification(p, sendNo, msgTitle, msgContent, builderId, extra);
	}

	/*
	 * @description 发送带ALIAS的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAlias(int sendNo, String alias, String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, null, null);
	}

	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发送带ALIAS的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAlias(int sendNo, String alias, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, msgContentType, extra);
	}

	/*
	 * @params overrideMsgId 待覆盖的上一条消息的 ID
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发送带ALIAS的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAlias(int sendNo, String alias, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra,String overrideMsgId) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		p.setOverrideMsgId(overrideMsgId);

		return sendCustomMessage(p, sendNo, msgTitle, msgContent, msgContentType, extra);
	}


	/*
	 * @description 发送带AppKey的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAppKey(int sendNo, String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendNotification(p, sendNo, msgTitle, msgContent, 0, null);
	}

	/*
	 * @params builderId通知栏样式
	 * @description 发送带AppKey的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAppKey(int sendNo, String msgTitle, String msgContent, int builderId, Map<String, Object> extra) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendNotification(p, sendNo, msgTitle, msgContent, builderId, extra);
	}

	/*
	 * @params overrideMsgId 待覆盖的上一条消息的 ID
	 * @params builderId通知栏样式
	 * @description 发送带AppKey的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAppKey(int sendNo, String msgTitle, String msgContent, int builderId, Map<String, Object> extra,String overrideMsgId) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		p.setOverrideMsgId(overrideMsgId);

		return sendNotification(p, sendNo, msgTitle, msgContent, builderId, extra);
	}

	/*
	 * @description 发送带AppKey的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAppKey(int sendNo, String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, null, null);
	}

	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发送带AppKey的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAppKey(int sendNo, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendCustomMessage(p, sendNo, msgTitle, msgContent, msgContentType, extra);
	}

	/*
	 * @params overrideMsgId 待覆盖的上一条消息的 ID
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发送带AppKey的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAppKey(int sendNo, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra,String overrideMsgId) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		p.setOverrideMsgId(overrideMsgId);

		return sendCustomMessage(p, sendNo, msgTitle, msgContent, msgContentType, extra);
	}

	protected MessageResult sendCustomMessage(CustomMessageParams p, int sendNo, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		if (null != msgContentType) {
			p.getMsgContent().setContentType(msgContentType);
		}
		if (null != extra) {
			p.getMsgContent().setExtra(extra);
		}
		return sendMessage(p, sendNo, msgTitle, msgContent);
	}

	protected MessageResult sendNotification(NotifyMessageParams p, int sendNo, String msgTitle, String msgContent, int builderId, Map<String, Object> extra) {
		p.getMsgContent().setBuilderId(builderId);
		if (null != extra) {
			p.getMsgContent().setExtra(extra);
		}
		return sendMessage(p, sendNo, msgTitle, msgContent);
	}

	protected MessageResult sendMessage(MessageParams p, int sendNo, String msgTitle, String msgContent) {
		p.setSendNo(sendNo);
		p.setAppKey(this.getAppKey());
		p.setMasterSecret(this.masterSecret);
		p.setTimeToLive(this.timeToLive);
		p.setSendDescription(this.getSendDescription());
		for (DeviceEnum device : this.getDevices()) {
			p.addPlatform(device);
		}
		if (null != msgTitle) {
			p.getMsgContent().setTitle(msgTitle);
		}
		p.getMsgContent().setMessage(msgContent);
	
		return sendMessage(p);
	}

	protected MessageResult sendMessage(MessageParams params) {
		return httpClient.post(BaseURL.ALL_PATH, this.enableSSL, params);
	}

}
