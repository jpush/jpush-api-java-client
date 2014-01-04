package cn.jpush.api.push;

public enum ReceiverTypeEnum {
	IMEI(1),
	TAG(2),
	ALIAS(3),
	APP_KEY(4),
	REGISTRATION_ID(5);
	
	private final int value;
	private ReceiverTypeEnum(final int value) {
		this.value = value;
	}
	public int value() {
		return this.value;
	}
}
