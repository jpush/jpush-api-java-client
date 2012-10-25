package com.jpush.helper;

import java.util.HashSet;
import java.util.Set;

public class MessageParam {
	/*
	 * 开发者注册帐号
	 */
	private String userName = "";
	
	/*
	 * 开发者注册密码
	 */
	private String password = "";
	
	/*
	 * 发送编号。由开发者自己维护，标识一次发送请求
	 */
	private int sendNo = 0;
	
	/*
	 * 待发送的应用程序列表 (appKey)，多个使用逗号隔开。
	 * 如果不填，则会向所有的应用发送。
	 */
	private Set<String> appKeys = new HashSet<String>();
	
	/*
	 * 接收者类型。都支持多个。
	 * 枚举类定义 ReceiverTypeEnum
	 */
	private Set<Integer> receiverType = new HashSet<Integer>();
	
	/*
	 * 发送范围值，与 receiver_type 相对应。
	 * receiver_type = 4 无该字段
	 */
	private Set<String> receiverValue = new HashSet<String>();
	
	/*
	 * 描述此次发送调用。
	 * 不会发到用户。
	 */
	private String sendDescription = "";
	
	/*
	 * 回调 HTTP URL。
	 */
	private String callbackUrl = "";
	
	/*
	 * 目标用户中断手机的平台类型，如：android, ios
	 */
	private Set<String> platform = new HashSet<String>();
	
	/*
	 * 发送消息的内容。
	 * 与 msg_type 相对应的值。
	 */
	private MsgContent msgContent = new MsgContent();
	
	public class MsgContent {
		@Override
		public String toString() {
			return "";
		}
	}
	
	public MessageParam() {
		
	}
	
	public MessageParam(String username, String password) {
		this.userName  = username;
		this.password = password;
	}
	
	public MsgContent getMsgContent() {
		return this.msgContent;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSendNo() {
		return sendNo;
	}
	public void setSendNo(int sendNo) {
		this.sendNo = sendNo;
	}
	public String getAppKeys() {
		return fromStringSet(this.appKeys);
	}
	public void addAppKeys(String appKey) {
		this.appKeys.add(appKey);
	}
	public String getReceiverType() {
		return fromIntegerSet(this.receiverType);
	}
	public void addReceiverType(int receiverType) {
		this.receiverType.add(receiverType);
	}
	public String getReceiverValue() {
		return fromStringSet(this.receiverValue);
	}
	public void addReceiverValue(String receiverValue) {
		this.receiverValue.add(receiverValue);
	}
	public String getSendDescription() {
		return sendDescription;
	}
	public void setSendDescription(String sendDescription) {
		this.sendDescription = sendDescription;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getPlatform() {
		return fromStringSet(this.platform);
	}
	public void addPlatform(String platform) {
		this.platform.add(platform);
	}
	
	private String fromIntegerSet(Set<Integer> sets) {
		String keys = "";
		for (Object key : sets) {
			keys += (key + ",");
		}
		return keys.length() > 0
				? keys.substring(0, keys.length()-1)
				: "";
	}
	private String fromStringSet(Set<String> sets) {
		String keys = "";
		for (Object key : sets) {
			keys += (key + ",");
		}
		return keys.length() > 0
				? keys.substring(0, keys.length()-1)
				: "";
	}
}
