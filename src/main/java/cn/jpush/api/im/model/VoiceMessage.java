package cn.jpush.api.im.model;


public class VoiceMessage extends MediaMessage {
	protected int duration;	//s
	
	private VoiceMessage(String targetType, String targetId, String targetName,
			String fromType, String fromId, String fromName,
			String mediaId, long mediaCrc32, String format, 
			String extras, int duration) {
		
		super(targetType, targetId, targetName, 
				fromType, fromId, fromName, 
				MsgType.voice, extras, 
				mediaId, mediaCrc32, format);
		
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
			
			return new VoiceMessage(targetType, targetId, targetName, 
					fromType, fromId, fromName, 
					mediaId, mediaCrc32, format, 
					extras, duration);
		}
		
	}
	
}
