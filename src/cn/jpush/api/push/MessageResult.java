package cn.jpush.api.push;

import cn.jpush.api.BaseResult;

/*
 * 发送消息立即返回的状态定义
 */
public class MessageResult extends BaseResult {
    public MessageResult() {
        super();
    }
	
	public MessageResult(int sendNo,int errcode,String errormsg){		
		this.sendno = sendNo;
		this.errcode = errcode;
		this.errmsg = errormsg;
	}
	
	//发送序号
	private int sendno = -1;
	//错误码，详见ErrorCodeEnum
	private int errcode = 10;
	

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
	
	public static MessageResult fromValue(String result) {	
		MessageResult messageResult = null;
		if ( (null != result) && (!"".equals(result)) ) {
			messageResult = _gson.fromJson(result, MessageResult.class);
		}
		return messageResult;
	}

	@Override
	public String toString() {
		return _gson.toJson(this);
	}
}
