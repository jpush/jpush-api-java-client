package cn.jpush.api;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/*
 * 自定义类型的消息内容
 */
public class CustomMessageParams extends MessageParams {
	public class CustomMsgContent extends MessageParams.MsgContent {
		//message 里的内容类型
		private String contentType = "";
		//更多的附属信息
		private Map<String, Object> extra;
		
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		public Map<String, Object> getExtra() {
			return extra;
		}
		public void setExtra(Map<String, Object> extra) {
			this.extra = extra;
		}
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			try {
				buffer.append("{");
				buffer.append("\"message\":\"" + this.getMessage() + "\"");
				if (null != this.getContentType()) {
					buffer.append(",\"content_type\":\"" + this.getContentType() + "\"");
				}
				if (null != this.getTitle()) {
					buffer.append(",\"title\":\"" + this.getTitle() + "\"");
				}
				String extraJson = new ObjectMapper().writeValueAsString(this.extra);
				buffer.append(",\"extra\":\"" + ((null != extraJson) ? "{}":"") + "\"");
				buffer.append("}");
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return buffer.toString();
		}
	}
	
	private CustomMsgContent msgContent = new CustomMsgContent();
	public CustomMsgContent getMsgContent() {
		return msgContent;
	}
}
