package cn.jpush.api.im.model;

public class ImageMessage extends MediaMessage {
	protected int width;
	protected int height;
	protected String imgLink;
	
	
	private ImageMessage(String targetType, String targetId, String targetName,
			String fromType, String fromId, String fromName,
			String mediaId, long mediaCrc32, String format,
			String extras, int width, int height, String imgLink) {
		
		super(targetType, targetId, targetName, 
				fromType, fromId, fromName, 
				MsgType.image, extras, 
				mediaId, mediaCrc32, format);
		
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
			
			return new ImageMessage(targetType, targetId, targetName, 
					fromType, fromId, fromName, 
					mediaId, mediaCrc32, format, 
					extras, width, height, imgLink);
		}
		
	}
	
	
}
