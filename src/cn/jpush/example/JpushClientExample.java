package cn.jpush.example;

import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

public class JpushClientExample {
	private static final String username = "username";//必填,开发者注册帐号
	private static final String password = "password";//必填，开发者注册密码
	private static final String appKey = "";//必填
	private static final String callbackUrl = "";//没有的话，填写空字符串
	private static JPushClient client = null;
	
	public static void main(String[] args) {
		//初始化,默认发送给android和ios
		client = new JPushClient(username, password, appKey);
		/*
		 * 只发送给android
		 * client = new JPushClient(username, password, appKey, DeviceEnum.Android);
		 */
		
		/*
		 * 带有回调的初始化，并且只发送给android
		 * client = new JPushClient(username, password, appKey, callbackUrl, DeviceEnum.Android);
		 */
		
		//发送消息或者通知
		testSendNotificationWithAppKey();
		//testSendCustomMessageWithAppKey();
	}
	
	private static void testSendNotificationWithAppKey() {
		String msgTitle = "标题";
		String msgContent = "内容";
		MessageResult msgResult = client.sendNotificationWithImei("355302046376167", msgTitle, msgContent);
		System.out.println("服务器返回数据: " + msgResult.toString());
		if (null != msgResult) {
			if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				System.out.println("发送成功， sendNo=" + msgResult.getSendno());
			} else {
				System.out.println("发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息=" + msgResult.getErrmsg());
			}
		}
	}
	
	private static void testSendCustomMessageWithAppKey() {
		String msgTitle = "标题";
		String msgContent = "{\"key\":\"value\"}";
		MessageResult msgResult = client.sendCustomMessageWithTag("fruit", msgTitle, msgContent);
		System.out.println("服务器返回数据: " + msgResult.toString());
		if (null != msgResult) {
			if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				System.out.println("发送成功， sendNo=" + msgResult.getSendno());
			} else {
				System.out.println("发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息=" + msgResult.getErrmsg());
			}
		}
	}
}
