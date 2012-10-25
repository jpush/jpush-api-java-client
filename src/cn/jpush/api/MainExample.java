package cn.jpush.api;

import cn.jpush.api.domain.MessageResult;

public class MainExample {
	//测试类
	public static void main(String []args) {
		String urlString = "http://api.jpush.cn:8800/sendmsg/sendmsg";
		
		MessagePusher pusher = new MessagePusher(urlString, "username", "password");
		MessageResult result = pusher.sendNotificationWithImei("imei_43124231", 
				"appkeyfdsafdjkl", "title", "content", 1, "desc");
		System.out.println("notifyMessageType errorcode: " + result.getErrcode());	//errorcode == 0 success
		
	}
}

