package cn.jpush.api.im.model;

import com.google.gson.annotations.Expose;


public abstract class MediaMessage extends BaseMessage {
	@Expose protected String mediaId;
	@Expose protected long mediaCrc32;
	@Expose protected String format;
	
	protected MediaMessage(MsgType msgType, 
			String mediaId, long mediaCrc32, String format) {
		super(msgType);
		
		this.mediaId = mediaId;
		this.mediaCrc32 = mediaCrc32;
		this.format = format;
	}
	
	
	protected static abstract class Builder<T extends MediaMessage, B extends MediaMessage.Builder<T, B>> {
		private B theBuilder;
		
		protected String mediaId;
		protected long mediaCrc32;
		protected String format;
		
		protected abstract B getThis();
		
		protected abstract T build();
		
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
