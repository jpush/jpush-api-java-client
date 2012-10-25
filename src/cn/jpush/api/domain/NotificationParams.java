package cn.jpush.api.domain;

/*
 * 通知类型的消息内容
 */
public class NotificationParams extends BaseMessageParams {

	public NotificationParams() {
	}
	
	public class NotificationContent extends BaseMessageParams.MsgContent {
		//不填则默认为 0，使用 极光Push SDK 的默认通知样式。
		private String builderId = "";
		
		//通知标题。不填则默认使用该应用的名称。
		private String title = "";
		
		//通知内容。
		private String content = "";
	
		public String getBuilderId() {
			return builderId;
		}
		public void setBuilderId(String builderId) {
			this.builderId = builderId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("{");
			buffer.append("\"n_content\":\"" + this.getContent() + "\"");
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
	private NotificationContent msgContent = new NotificationContent();
	public NotificationContent getMsgContent() {
		return this.msgContent;
	}
}
