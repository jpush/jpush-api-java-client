package cn.jpush.api.im.model;

import com.google.gson.annotations.Expose;

public class VoiceMessage extends MediaMessage {
	@Expose private int duration;	//s
	
	private VoiceMessage(String mediaId, long mediaCrc32, String format, 
			int duration) {
		
		super(MsgType.voice, mediaId, mediaCrc32, format);
		
		this.duration = duration;
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}

	
	public static class Builder extends MediaMessage.Builder<VoiceMessage, VoiceMessage.Builder> {
		private int duration;
		
		protected Builder getThis() {
			return this;
		}
		
		public Builder setDuration(int duration) {
			this.duration = duration;
			return this;
		}
		
		@Override
		public VoiceMessage build() {
			return new VoiceMessage(mediaId, mediaCrc32, format, duration);
		}
		
	}
	
}
