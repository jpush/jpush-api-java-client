package cn.jpush.api.im.model;

import com.google.gson.annotations.Expose;

public class ImageMessage extends MediaMessage {
	@Expose private int width;
	@Expose private int height;
	@Expose private String imgLink;
	
	private ImageMessage(String mediaId, long mediaCrc32, String format,
			int width, int height, String imgLink) {
		
		super(MsgType.image, mediaId, mediaCrc32, format);
		
		this.width = width;
		this.height = height;
		this.imgLink = imgLink;
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}
	
	public static class Builder extends MediaMessage.Builder<ImageMessage, ImageMessage.Builder> {
		private int width;
		private int height;
		private String imgLink;
		
		@Override
		protected Builder getThis() {
			return this;
		}
		
		public Builder setWidthHeight(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}
		
		public Builder setImgLink(String imgLink) {
			this.imgLink = imgLink;
			return this;
		}
		
		
		@Override
		public ImageMessage build() {
			return new ImageMessage(mediaId, mediaCrc32, format, 
					width, height, imgLink);
		}
		
	}
	
	
}
