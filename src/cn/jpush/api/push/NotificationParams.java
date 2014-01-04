package cn.jpush.api.push;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.utils.StringUtils;

/*
 * 通知内容
 */
public class NotificationParams extends MessageParams {
    public static final int DEFAULT_N_BUILDER_ID = 0;
    
	public class NotificationContent extends MessageParams.MsgContent {
	    
		// Android notification builder id - default is 0
		private int builderId = 0;
		
		private Map<String, Object> extras = new HashMap<String, Object>();
		
		public int getBuilderId() {
			return builderId;
		}
		public void setBuilderId(int builderId) {
			this.builderId = builderId;
		}
		
		public Map<String, Object> getExtras() {
			return extras;
		}
		public void setExtras(Map<String, Object> extras) {
			this.extras = extras;
		}
		
		@Override
		public String toString() {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("n_content", this.getMessage());
			params.put("n_builder_id", String.valueOf(this.getBuilderId()));
			params.put("n_title", this.getTitle());
			params.put("n_extras", this.getExtras());
			return StringUtils.encodeParam(_gson.toJson(params));
		}
	}
	
	private NotificationContent msgContent = new NotificationContent();
	public NotificationContent getMsgContent() {
		return this.msgContent;
	}
	
	public void setAndroidBuilderId(int builderId) {
	    this.getMsgContent().setBuilderId(builderId);
	}
	
	public void setAndroidNotificationTitle(String title) {
	    this.getMsgContent().setTitle(title);
	}
	
}

