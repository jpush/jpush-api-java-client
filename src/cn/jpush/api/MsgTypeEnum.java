package cn.jpush.api;

public enum MsgTypeEnum {
	//通知类型消息
	NOTIFY(1),
	
	//用户自定义类型消息
	CUSTOM(2);
	
	private final int value;
	private MsgTypeEnum(final int value) {
		this.value = value;
	}
	public int value() {
		return this.value;
	}
}