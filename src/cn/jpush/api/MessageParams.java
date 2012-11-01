package cn.jpush.api;

import java.util.HashSet;
import java.util.Set;

public class MessageParams {
	/*
	 * 发送编号。由开发者自己维护，标识一次发送请求
	 */
	private int sendNo = 0;
	
	/*
	 * 待发送的应用程序列表 (appKey)，多个使用逗号隔开。
	 * 如果不填，则会向所有的应用发送。
	 */
	private String appKeys = "";
	
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
	 * 描述此次发送调用。
	 * 不会发到用户。
	 */
	private String sendDescription = "";
	
	/*
	 * 目标用户中断手机的平台类型，如：android, ios
	 */
	private Set<DeviceEnum> platform = new HashSet<DeviceEnum>();
	
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
	public String getAppKeys() {
		return this.appKeys;
	}
	public void setAppKeys(String appKeys) {
		this.appKeys = appKeys;
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
}
