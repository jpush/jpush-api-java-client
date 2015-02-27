package cn.jpush.api.im.model;

import com.google.gson.annotations.Expose;

public class TextMessage extends BaseMessage {
	@Expose private String text;
	
	private TextMessage(String text) {
		super(MsgType.text);
		
		this.text = text;
	}
	

	public static Builder newBuilder() {
		return new Builder();
	}
	
	
	public static class Builder {
		private String text;
		
		public Builder setText(String text) {
			this.text = text;
			return this;
		}
		
		protected Builder getThis() {
			return this;
		}
		
		public TextMessage build() {
			return new TextMessage(text);
		}
	}
	
	
}
