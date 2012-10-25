package cn.jpush.api;

/*
 * 通知类型的消息内容
 */
public class NotifyMessageParams extends MessageParams {
	public class NotifyMsgContent extends MessageParams.MsgContent {
		//不填则默认为 0，使用 极光Push SDK 的默认通知样式。
		private String builderId = "";
		
		public String getBuilderId() {
			return builderId;
		}
		public void setBuilderId(String builderId) {
			this.builderId = builderId;
		}
		
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("{");
			buffer.append("\"n_content\":\"" + this.getMessage() + "\"");
			if (null != this.getBuilderId()) {
				buffer.append(",\"n_builder_id\":\"" + this.getBuilderId() + "\"");
			}
			if (null != this.getTitle()) {
				buffer.append(",\"n_title\":\"" + this.getTitle() + "\"");
			}
			buffer.append("}");
			return buffer.toString();
		}
	}
	private NotifyMsgContent msgContent = new NotifyMsgContent();
	public NotifyMsgContent getMsgContent() {
		return this.msgContent;
	}
}
