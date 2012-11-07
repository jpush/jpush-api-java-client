package cn.jpush.example;

import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

public class JpushClientExample {
	private static final String username = "username";//必填,开发者注册帐号
	private static final String password = "password";//必填，开发者注册密码
	private static final String appKey = "";//必填，例如466f7032ac604e02fb7bda89
	private static final String callbackUrl = "";//没有的话，填写空字符串
	private static JPushClient jpush = null;
	
	public static void main(String[] args) {
		/*
		 * Example1: 初始化,默认发送给android和ios
		 * jpush = new JPushClient(username, password, appKey);
		 * 
		 * Example2: 只发送给android
		 * jpush = new JPushClient(username, password, appKey, DeviceEnum.Android);
		 * 
		 * Example3: 带有回调的初始化，并且只发送给android
		 * jpush = new JPushClient(username, password, appKey, callbackUrl, DeviceEnum.Android);
		 */
		jpush = new JPushClient(username, password, appKey);
		
		
		/* 是否启用ssl安全连接; 优点:安全，缺点:速度慢，资源消耗多
		 * 参数：启用true， 禁用false，默认为非ssl连接
		 * 
		 * Example:
		 * jpush.setEnableSSL(true);
		 */
		//jpush.setEnableSSL(true);
		
		
		//测试发送消息或者通知
		testSendNotification();
	}
	
	private static void testSendNotification() {
		String msgTitle = "标题";
		String msgContent = "消息内容";
		MessageResult msgResult = jpush.sendNotificationWithAppKey(msgTitle, msgContent);
		if (null != msgResult) {
			System.out.println("服务器返回数据: " + msgResult.toString());
			if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				System.out.println("发送成功， sendNo=" + msgResult.getSendno());
			} else {
				System.out.println("发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息=" + msgResult.getErrmsg());
			}
		}
	}
}
