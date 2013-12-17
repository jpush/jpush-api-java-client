package cn.jpush.api.report;

import cn.jpush.api.BaseResult;

public class ReportReceivedResult extends BaseResult {
	private Integer android_received;
	private Integer ios_apns_sent;
	
	public Integer getAndroid_received() {
		return android_received;
	}
	
	public void setAndroid_received(Integer android_received) {
		this.android_received = android_received;
	}

	public Integer getIos_apns_sent() {
		return ios_apns_sent;
	}

	public void setIos_apns_sent(Integer ios_apns_sent) {
		this.ios_apns_sent = ios_apns_sent;
	}
	
	@Override
	public String toString() {
		if(this.response_status == 200)
			this.response_status = null;
		return _gson.toJson(this);
	}
}

