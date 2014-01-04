package cn.jpush.api.push;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.utils.StringUtils;

import com.google.gson.Gson;

/*
 * 自定义类型的消息内容
 */
public class CustomMessageParams extends MessageParams {
	public class CustomMsgContent extends MessageParams.MsgContent {
		//message 里的内容类型
		private String contentType = "";
		//更多的附属信息
		private Map<String, Object> extras = new HashMap<String, Object>();
		
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		public Map<String, Object> getExtras() {
			return extras;
		}
		public void setExtras(Map<String, Object> extras) {
			this.extras = extras;
		}
		@Override
		public String toString() {
			Gson gson = new Gson();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("title", this.getTitle());
			params.put("message", this.getMessage());
			params.put("content_type", this.getContentType());
			params.put("extras", this.getExtras());
			
			return StringUtils.encodeParam(gson.toJson(params));
		}
	}
	
	private CustomMsgContent msgContent = new CustomMsgContent();
	public CustomMsgContent getMsgContent() {
		return msgContent;
	}
}
