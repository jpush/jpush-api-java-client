package cn.jpush.example;
import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.IOSExtra;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

public class JpushClientExample {

	private static final String appKey = "47b9ef19d4be5de08df12aa0";//必填，例如466f7032ac604e02fb7bda89
	
	private static final String masterSecret ="8cc138f8dc04cbf16240daa92d8d50e2" ; //必填，每个应用都对应一个masterSecret（1f0e3dad99908345f7439f8ffabdffc4)
	
	
	private static JPushClient jpush = null;
	/*
	 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
	 * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
	 * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒）。	
	 */
	private static int timeToLive =  60 * 60 * 24;  

	public static void main(String[] args) {
		/*
		 * Example1: 初始化,默认发送给android和ios，同时设置离线消息存活时间
		 * jpush = new JPushClient(masterSecret, appKey,timeToLive);
		 * 
		 * Example2: 只发送给android
		 * jpush = new JPushClient(masterSecret, appKey, DeviceEnum.Android);
		 * 
		 * Example3: 只发送给IOS
		 * jpush = new JPushClient(masterSecret, appKey,  DeviceEnum.IOS);
		 * 
		 * Example4: 只发送给android,同时设置离线消息存活时间
		 * jpush = new JPushClient(masterSecret, appKey, DeviceEnum.Android);
		 */

		jpush = new JPushClient(masterSecret,appKey);

		/* 
		 * 是否启用ssl安全连接, 可选
		 * 参数：启用true， 禁用false，默认为非ssl连接
		 * 
		 * Example:
		 * jpush.setEnableSSL(true);
		 */
	
	
		//测试发送消息或者通知
	testSend();
	}

	private static void testSend() {

		int sendNo = 103;
		String msgTitle = "标题+++";
		String msgContent = "+/通#知?内&容%<可>;=====";

		/* String alias = "alias";
		String tag = "tag";
		HashMap hashMap = new HashMap();
		//IOS扩展参数，
		IOSExtra iosExtra = new IOSExtra(2);
		 */

		MessageResult msgResult =jpush.sendNotificationWithAppKey(sendNo,msgTitle,msgContent,0,null,new IOSExtra(20,"happy"));

	   //  jpush.sendNotificationWithAlias(sendNo, alias, msgTitle, msgContent,1,null,new IOSExtra(3));
		//		jpush.sendNotificationWithTag(sendNo, tag, msgTitle, msgContent);
		//		jpush.sendNotificationWithAlias(sendNo, tag, msgTitle, msgContent);

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
