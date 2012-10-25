package cn.jpush.api;

public enum ErrorCodeEnum {
	//系统内部错误
	SystemError(10),
	
	//不支持GET请求
	NotSupportGetMethod(1001),
	
	//缺少必须参数
	MissingRequiredParameters(1002),
	
	//参数值不合法
	InvalidParameter(1003),
	
	//验证失败
	ValidateFailed(1004),
	
	//用户名或者密码错误
	InvalidUsernameOrPassword(1005),
	
	//消息体太大
	DataTooBig(1006),
	
	//IMEI不合法
	InvalidIMEI(1007),
	
	//receiver_type=1时，必须填写appkeys
	MissingAppKeyValue(1008),
	
	//appkeys不合法
	InvalidAppKey(1009),
	
	//msg_content不合法
	InvalidMsgContentValue(1010);
	
	private final int value;
	private ErrorCodeEnum(final int value) {
		this.value = value;
	}
	public int value() {
		return this.value;
	}
}
