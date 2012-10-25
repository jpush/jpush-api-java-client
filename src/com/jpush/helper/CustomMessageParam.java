package com.jpush.helper;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/*
 * 自定义类型的消息内容
 */
public class CustomMessageParam extends MessageParam {
	public CustomMessageParam() {
		
	}
	
	public CustomMessageParam(String username, String password) {
		super(username, password);
	}
	
	public class CustomMsgContent extends MessageParam.MsgContent {
		//自定义消息的内容。
		private String message = "";
		
		//message 里的内容类型
		private String contentType = "";
		
		//消息标题
		private String title = "";
		
		//更多的附属信息
		private Map<String, Object> extra;
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
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
				buffer.append("\"message\":\"" + this.message + "\"");
				if (null != this.contentType) {
					buffer.append(",\"content_type\":\"" + this.contentType + "\"");
				}
				if (null != this.title) {
					buffer.append(",\"title\":\"" + this.title + "\"");
				}
				if (null != this.extra) {
					buffer.append(",\"extra\":\"" + new ObjectMapper().writeValueAsString(this.extra) + "\"");
				}
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
