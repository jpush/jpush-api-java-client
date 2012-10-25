package cn.jpush.api.domain;

public enum MsgTypeEnum {
	//通知类型消息
	Notification(1),
	
	//用户自定义类型消息
	Custom(2);
	
	private final int value;
	private MsgTypeEnum(final int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
	
}
