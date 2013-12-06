package cn.jpush.receive.api;

import cn.jpush.api.ErrorCodeEnum;

import com.google.gson.Gson;

/**
 * @author zengzhiwu
 *
 */
public class ReceiveResult {
	
	private Integer android_received;
	
	private Integer ios_apns_sent;
	
	private String msg_id;
	
	private Integer response_status = 200;
	
	private String errmsg;

	public String getErrmsg() {
		return errmsg;
	}

	public int getResponse_status() {
		return response_status;
	}

	public void setResponse_status(int response_status) {
		this.response_status = response_status;
	}

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

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	@Override
	public String toString() {
		if(this.response_status == 200)
			this.response_status = null;
		return new Gson().toJson(this);
	}
}
