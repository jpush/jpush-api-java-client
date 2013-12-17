package cn.jpush.api;

import com.google.gson.Gson;

public class BaseResult {
    protected static Gson _gson = new Gson();
	
	public BaseResult() {
	}
	
    public BaseResult(String error, Integer responseStatus) {
		this.errmsg = error;
		this.response_status = responseStatus;
	}
	
	protected Long msg_id;
	protected Integer response_status = 200;
	protected String errmsg;

	public Long getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(Long msg_id) {
		this.msg_id = msg_id;
	}

	public Integer getResponse_status() {
		return response_status;
	}

	public void setResponse_status(Integer response_status) {
		this.response_status = response_status;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	@Override
	public String toString() {
		return _gson.toJson(this);
	}

}
