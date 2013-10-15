package cn.jpush.api;

import com.google.gson.Gson;

/*
 * 发送消息立即返回的状态定义
 */
public class MessageResult {
	public MessageResult() {}
	
	public MessageResult(int sendNo,int errcode,String errormsg){
		this.sendno = sendNo;
		this.errcode = errcode;
		this.errmsg = errormsg;
	}
	
	//发送序号
	private int sendno = -1;
	//错误码，详见ErrorCodeEnum
	private int errcode;
	//错误消息
	private String errmsg;
	//消息唯一ID，msgId从response中获取。
	private String msg_id;

	public String getMsgid() {
		return msg_id;
	}
	public void setMsgId(String msgid) {
		this.msg_id = msgid;
	}
	public int getSendno() {
		return sendno;
	}
	public void setSendno(int sendno) {
		this.sendno = sendno;
	}
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public static MessageResult fromValue(String result) {	
		MessageResult messageResult = null;
		if ( (null != result) && (!"".equals(result)) ) {
			messageResult = new Gson().fromJson(result, MessageResult.class);
		}
		return messageResult;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
