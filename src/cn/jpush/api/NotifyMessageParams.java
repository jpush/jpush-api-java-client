package cn.jpush.api;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

/*
 * 通知类型的消息内容
 */
public class NotifyMessageParams extends MessageParams {
	public class NotifyMsgContent extends MessageParams.MsgContent {
		//不填则默认为 0，使用 极光Push SDK 的默认通知样式。
		private int builderId = 0;
		
		public int getBuilderId() {
			return builderId;
		}
		public void setBuilderId(int builderId) {
			this.builderId = builderId;
		}
		
		@Override
		public String toString() {
			Map<String, String> params = new HashMap<String, String>();
			params.put("n_content", this.getMessage());
			params.put("n_builder_id", String.valueOf(this.getBuilderId()));
			params.put("n_title", this.getTitle());
			return new Gson().toJson(params);
		}
	}
	private NotifyMsgContent msgContent = new NotifyMsgContent();
	public NotifyMsgContent getMsgContent() {
		return this.msgContent;
	}
}
