package cn.jpush.api.im.model;

public class TextMessage extends ImMessage {
	protected String text;
	
	private TextMessage(String targetType, String targetId, String targetName,
			String fromType, String fromId, String fromName,
			String extras, String text) {
		
		super(targetType, targetId, targetName, 
				fromType, fromId, fromName, 
				MsgType.text, extras);
		
		this.text = text;
	}
	

	public static Builder newBuilder() {
		return new Builder();
	}
	
	
	public static class Builder extends ImMessage.Builder<TextMessage, TextMessage.Builder> {
		private String text;
		
		public Builder setText(String text) {
			this.text = text;
			return this;
		}
		
		protected Builder getThis() {
			return this;
		}
		
		public TextMessage build() {
			return new TextMessage(
					targetType, targetId, targetName, 
					fromType, fromId, fromName, 
					extras, text);
		}
	}
	
	
}
