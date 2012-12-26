package cn.jpush.example;

import cn.jpush.api.ErrorCodeEnum;

import cn.jpush.api.MessageResult;
import cn.jpush.api.SimpleJPushClient;

public class SimpleJpushClientExample {
	
	private static final String appKey = "57b9ef19d4be5de08df12aa0";//必填，例如466f7032ac604e02fb7bda89
	private static final String masterSecret = "9cc138f8dc04cbf16240daa92d8d50e2"; //必填，每个应用都对应一个masterSecret（1f0e3dad99908345f7439f8ffabdffc4)
	private static SimpleJPushClient  simpleJpush = null;
	
	public static void main(String[] args) {
		/*
		 * Example1: 初始化,默认发送给android和ios
		 * jpush = new JPushClient(masterSecret, appKey);
		 * 
		 * Example2: 只发送给android
		 * jpush = new JPushClient(masterSecret, appKey, DeviceEnum.Android);
		 * 
		 * Example3: 带有回调的初始化，并且只发送给android
		 * jpush = new JPushClient(masterSecret, appKey, DeviceEnum.Android);
		 */
		simpleJpush = new SimpleJPushClient(masterSecret,appKey);

		
		
		
		/* 是否启用ssl安全连接, 可选
		 * 参数：启用true， 禁用false，默认为非ssl连接
		 * 
		 * Example:
		 * jpush.setEnableSSL(true);
		 */
		
		
		//测试发送消息或者通知
		testSend();
	}
	
	private static void testSend() {
		//开发者自己维护sendNo，sendNo的作用请参考文档。
		/*1. + URL 中+号表示空格 %2B
		 2. 空格 URL中的空格可以用+号或者编码 %20
		 3. / 分隔目录和子目录 %2F
		 4. ? 分隔实际的 URL 和参数 %3F
		 5. % 指定特殊字符 %25
		 6. # 表示书签 %23
		 7. & URL 中指定的参数间的分隔符 %26
		 8. = URL 中指定参数的值 %3D */
		int sendNo = 103;
		String msgTitle = "标题+++";
		String txt = "/通#知?内&容%<可>;=====";
		String alias = "alias";
		String tag = "tag";

		MessageResult msgResult = simpleJpush.sendNotificationWithAppKey(sendNo, txt);
		
//		MessageResult msgResult = jpush.sendNotificationWithTag(sendNo, tag, msgTitle, msgContent);
		
//		MessageResult msgResult = jpush.sendNotificationWithAlias(sendNo, tag, msgTitle, msgContent);
		
		if (null != msgResult) {
			System.out.println("服务器返回数据: " + msgResult.toString());
			if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				System.out.println("发送成功， sendNo=" + msgResult.getSendno());
			} else {
				System.out.println("发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息=" + msgResult.getErrmsg());
			}
		} else {
			System.out.println("无法获取数据，检查网络是否超时");
		}
	}
}
