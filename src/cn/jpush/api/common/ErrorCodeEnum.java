package cn.jpush.api.common;

public enum ErrorCodeEnum {
	
	//参数值不合法
	InvalidParameter(1003),
	
	//验证失败
	VerificationFailed(1004),
	
	//appkey不合法
	InvalidAppKey(1008),
	
	//msg_content不合法
	InvalidMsgContent(1010),
	
	//没有满足条件的推送目标
	NoTarget(1011),
		
	//Unknown exception
	UnknownException(-1);

	
	private final int value;
	private ErrorCodeEnum(final int value) {
		this.value = value;
	}
	public int value() {
		return this.value;
	}
	
	public static String errorMsg(final int value){
		String errMsg = null;
		switch (value) {
		case 400:
			errMsg = "msg_ids  request param is required.";
			break;
		case 401:
			errMsg = "Basic authentication failed";
			break;
		case 500:
			errMsg = "server internal errror";
		default:
			break;
		}
		return errMsg;
	}
}
