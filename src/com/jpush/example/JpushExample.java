package com.jpush.example;

import java.util.HashMap;

import com.jpush.helper.CustomMessageParam;
import com.jpush.helper.MessageHelper;
import com.jpush.helper.MessageResult;
import com.jpush.helper.NotifyMessageParam;
import com.jpush.helper.ReceiverTypeEnum;

public class JpushExample {
	//测试类
	public static void main(String []args) {
		String urlString = "http://api.jpush.cn:8800/sendmsg/sendmsg";
		
		/*
		 * 通知类型消息
		 */
		NotifyMessageParam notifyMessage = new NotifyMessageParam("username", "password");
		notifyMessage.setSendNo(0);
		
		notifyMessage.addReceiverType(ReceiverTypeEnum.IMEI.value());//接收者类型
		notifyMessage.addReceiverValue("receiver imei value");//需要和接收者类型对应
		//支持多个receiver_type的写法,但是type和value一定要同时添加
		//notifyMessage.addReceiverType(ReceiverTypeEnum.TAG.value());
		//notifyMessage.addReceiverValue("receiver tag value");
		
		notifyMessage.setCallbackUrl("push callback url");
		notifyMessage.setSendDescription("send description");
		notifyMessage.addAppKeys("your app keys");
		notifyMessage.addPlatform("android");
		notifyMessage.addPlatform("ios");
		notifyMessage.getMsgContent().setTitle("");
		notifyMessage.getMsgContent().setBuilderId("0");
		notifyMessage.getMsgContent().setContent("_content_");
		MessageResult result = MessageHelper.sendMessage(urlString, MessageHelper.convertMessage(notifyMessage));
		System.out.println("notifyMessageType errorcode: " + result.getErrcode());//errorcode == 0 success
		
		/*
		 * 自定义类型消息
		 */
		CustomMessageParam customMessage = new CustomMessageParam("username", "password");
		customMessage.setSendNo(0);
		
		customMessage.addReceiverType(ReceiverTypeEnum.TAG.value());
		customMessage.addReceiverValue("receiver tag value");
		
		customMessage.setCallbackUrl("your callback");
		customMessage.setSendDescription("send description");
		customMessage.addAppKeys("your appkeys");
		customMessage.addPlatform("android");
		customMessage.getMsgContent().setMessage("");
		customMessage.getMsgContent().setContentType("");
		customMessage.getMsgContent().setTitle("_title_");
		customMessage.getMsgContent().setExtra(new HashMap<String, Object>());
		result = MessageHelper.sendMessage(urlString, MessageHelper.convertMessage(customMessage));
		System.out.println("customMessageType errorcode: " + result.getErrcode());
	}
}
