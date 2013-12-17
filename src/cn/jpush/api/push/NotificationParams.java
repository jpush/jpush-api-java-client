package cn.jpush.api.push;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.utils.StringUtils;

/*
 * 通知内容
 */
public class NotificationParams extends MessageParams {
    
	public class NotificationContent extends MessageParams.MsgContent {
		//不填则默认为 0，使用 极光Push SDK 的默认通知样式。
		private int builderId = 0;
		
		private Map<String, Object> extra = new HashMap<String, Object>();
		
		public int getBuilderId() {
			return builderId;
		}
		public void setBuilderId(int builderId) {
			this.builderId = builderId;
		}
		public Map<String, Object> getExtra() {
			return extra;
		}
		public void setExtra(Map<String, Object> extra) {
			this.extra = extra;
		}
		
		@Override
		public String toString() {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("n_content", this.getMessage());
			params.put("n_builder_id", String.valueOf(this.getBuilderId()));
			params.put("n_title", this.getTitle());
			params.put("n_extras", this.getExtra());
			return StringUtils.encodeParam(_gson.toJson(params));
		}
	}
	
	private NotificationContent msgContent = new NotificationContent();
	public NotificationContent getMsgContent() {
		return this.msgContent;
	}
}
