package cn.jpush.api.push.model.audience;

public enum AudienceType {
	TAG("tag"),
	TAG_AND("tag_and"),
	TAG_NOT("tag_not"),
	ALIAS("alias"),
	SEGMENT("segment"),
	ABTEST("abtest"),
	REGISTRATION_ID("registration_id");
	
	private final String value;
	private AudienceType(final String value) {
		this.value = value;
	}
	public String value() {
		return this.value;
	}
	
	
}
