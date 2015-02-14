package cn.jpush.api.im.model;


public abstract class MediaMessage extends ImMessage {
	protected String mediaId;
	protected long mediaCrc32;
	protected String format;
	
	protected MediaMessage(String targetType, String targetId, String targetName,
			String fromType, String fromId, String fromName,
			MsgType msgType, String extras, 
			String mediaId, long mediaCrc32, String format) {
		
		super(targetType, targetId, targetName, 
				fromType, fromId, fromName, 
				msgType, extras);
		
		this.mediaId = mediaId;
		this.mediaCrc32 = mediaCrc32;
		this.format = format;
	}
	
	
	protected static abstract class Builder<T extends MediaMessage, B extends MediaMessage.Builder<T, B>>
			extends ImMessage.Builder<T, B> {
		private B theBuilder;
		
		protected String mediaId;
		protected long mediaCrc32;
		protected String format;
		
		public Builder() {
			this.theBuilder = getThis();
		}
		
		public B setMedia(String mediaId, long crc32, String format) {
			this.mediaId = mediaId;
			this.mediaCrc32 = crc32;
			this.format = format;
			return theBuilder;
		}
		
	}
	
}
