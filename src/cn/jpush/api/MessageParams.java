package cn.jpush.api;

import java.util.HashSet;
import java.util.Set;

public class MessageParams {
	/*
	 * 发送编号。由开发者自己维护，标识一次发送请求
	 */
	private int sendNo = 0;
	
	/*
	 *  (appKey)，只能填写一个。
	 * 如果不填，则会向所有的应用发送。
	 */
	private String appKey = "";
	
	/*
	 * 枚举类定义 ReceiverTypeEnum
	 */
	private ReceiverTypeEnum receiverType;
	
	/*
	 * 发送范围值，与 receiverType 相对应。
	 * receiverType = 4 不用设置
	 */
	private String receiverValue = "";
	
	/*
	 * 从消息推送时起，保存离线的时长。秒为单位。
	 * 不设置默认保存一天
	 */
	private int timeToLive; 
	
	/*
	 * 每个应用对应一个masterSecret，用来校验
	 */
	private String masterSecret;
	
	/*
	 * 描述此次发送调用。
	 * 不会发到用户。
	 */
	private String sendDescription = "";
	
	/*
	 * 目标用户中断手机的平台类型，如：android, ios
	 */
	private Set<DeviceEnum> platform = new HashSet<DeviceEnum>();
	
	/*
	 * 推送介意接口消息内容 
	 */
	private String txt;
	private String targetPath;
	
	public String getTargetPath() {
		return targetPath;
	}
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	/*
	 * 发送消息的内容。
	 * 与 msg_type 相对应的值。
	 */
	private MsgContent msgContent = new MsgContent();
	
	public class MsgContent {
		private String title = "";
		private String message = "";
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		@Override
		public String toString() {
			return "";
		}
	}
	
	public MsgContent getMsgContent() {
		return this.msgContent;
	}
	public int getSendNo() {
		return sendNo;
	}
	public void setSendNo(int sendNo) {
		this.sendNo = sendNo;
	}
	public String getAppKey() {
		return this.appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public int getTimeToLive() {
		return timeToLive;
	}
	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}
	public String getMasterSecret() {
		return masterSecret;
	}
	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}
	public ReceiverTypeEnum getReceiverType() {
		return this.receiverType;
	}
	public void setReceiverType(ReceiverTypeEnum receiverType) {
		this.receiverType = receiverType;
	}
	public String getReceiverValue() {
		return this.receiverValue;
	}
	public void setReceiverValue(String receiverValue) {
		this.receiverValue = receiverValue;
	}
	public String getSendDescription() {
		return sendDescription;
	}
	public void setSendDescription(String sendDescription) {
		this.sendDescription = sendDescription;
	}
	public String getPlatform() {
		String keys = "";
		for (DeviceEnum key : this.platform) {
			keys += (key.value() + ",");
		}
		return keys.length() > 0 ? keys.substring(0, keys.length()-1) : "";
	}
	public void addPlatform(DeviceEnum platform) {
		this.platform.add(platform);
	}
	
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
}
