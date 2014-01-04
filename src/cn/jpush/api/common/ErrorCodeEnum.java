package cn.jpush.api.common;

public enum ErrorCodeEnum {
	//发送成功
	NOERROR(0),
	
	//系统内部错误
	SystemError(10),
	
	//不支持GET请求
	NotSupportGetMethod(1001),
	
	//缺少必须参数
	MissingRequiredParameters(1002),
	
	//参数值不合法
	InvalidParameter(1003),
	
	//验证失败
	VerificationFailed(1004),
	
	//消息体太大
	DataTooBig(1005),
	
	//IMEI不合法
	InvalidIMEI(1007),
	
	//appkey不合法
	InvalidAppKey(1008),
	
	//msg_content不合法
	InvalidMsgContent(1010),
	
	//没有满足条件的推送目标
	NoTarget(1011),
	
	//IOS不支持自定义消息
	CustomMessgaeNotSupportIOS(1012),
	
	//connection timeout
	CONNECTIONTIMEOUT(12),

	//Connection refused
	CONNECTIONREFUSED(13),
	
	UnknownHostException(14),
	
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
