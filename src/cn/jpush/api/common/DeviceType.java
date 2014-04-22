package cn.jpush.api.common;

public enum DeviceType {
    
	Android("android"),
	IOS("ios"),
	MPNs("mpns");
	
	private final String value;
	
	private DeviceType(final String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
	
}
