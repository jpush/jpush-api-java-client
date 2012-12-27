package cn.jpush.api;

import cn.jpush.api.utils.HttpPostClient;
import cn.jpush.api.utils.StringUtils;

/*
 *  API 简易接口
 */
public class SimpleJPushClient extends BaseClient{

	protected static HttpPostClient httpClient = new HttpPostClient();

	public SimpleJPushClient(String masterSecret, String appKey) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
	}
	public SimpleJPushClient(String masterSecret, String appKey,int timeToLive) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
		this.timeToLive = timeToLive;
	}
	
	
	public SimpleJPushClient(String masterSecret, String appKey, DeviceEnum device) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
		devices.add(device);
	}
	
	public SimpleJPushClient(String masterSecret, String appKey,DeviceEnum device, int timeToLive) {
	
		this.masterSecret = masterSecret;
		this.appKey = appKey;
		this.timeToLive = timeToLive;
		this.devices.add(device);
	}
	
	
	/* @description 发送带IMEI的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithImei(int sendNo, String imei,  String txt) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.IMEI);
		p.setReceiverValue(imei);
		return sendNotification(p, sendNo, txt);
	}
	
	
	
	 /* @description 发送带IMEI的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithImei(int sendNo, String imei, String txt) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.IMEI);
		p.setReceiverValue(imei);
		return sendCustomMessage(p, sendNo, txt);
	}
	
	
	 /*  @description 发送带TAG的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithTag(int sendNo, String tag, String txt) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendNotification(p, sendNo, txt);
	}
	
	
	
	 /*  @description 发送带TAG的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithTag(int sendNo, String tag, String txt) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendCustomMessage(p, sendNo, txt);
	}
	
	
	  /*  @description 发送带ALIAS的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAlias(int sendNo, String alias, String txt) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendNotification(p, sendNo,txt);
	}
	
	  /* @description 发送带ALIAS的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAlias(int sendNo, String alias, String txt) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendCustomMessage(p, sendNo, txt);
	}
	
	
	
	 /*  @description 发送带AppKey的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAppKey(int sendNo, String txt) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendNotification(p, sendNo, txt);
	}
	
	
	 /*  @description 发送带AppKey的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAppKey(int sendNo, String txt) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendCustomMessage(p, sendNo, txt);
	}
	

	protected MessageResult sendCustomMessage(CustomMessageParams p, int sendNo, String txt) {
		return sendMessage(p, sendNo,txt);
	}
	
	protected MessageResult sendNotification(NotifyMessageParams p, int sendNo, String txt) {

		return sendMessage(p, sendNo, txt);
	}
	
	protected MessageResult sendMessage(MessageParams p, int sendNo, String txt) {
		p.setSendNo(sendNo);
		p.setAppKey(this.getAppKey());
		p.setMasterSecret(this.masterSecret);
		p.setTimeToLive(this.timeToLive);
		p.setTxt(StringUtils.encodeParam(txt));
		
		if(p instanceof CustomMessageParams)
			p.setTargetPath(BaseURL.SIMPLE_CUSTOM_PATH);
		else if(p instanceof NotifyMessageParams)
			p.setTargetPath(BaseURL.SIMPLE_NOTIFICATION_PATH);
		
		for (DeviceEnum device : this.getDevices()) {
			p.addPlatform(device);
		}
		
		return sendMessage(p);
	}
	
	protected MessageResult sendMessage(MessageParams params) {
		return httpClient.simplePost(params.getTargetPath(),this.enableSSL,params);
	}
	

	
}
