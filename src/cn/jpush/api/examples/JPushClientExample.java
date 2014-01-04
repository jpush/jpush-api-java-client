package cn.jpush.api.examples;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.CustomMessageParams;
import cn.jpush.api.push.IOSExtra;
import cn.jpush.api.push.MessageResult;
import cn.jpush.api.push.ReceiverTypeEnum;
import cn.jpush.api.report.ReceivedsResult;

public class JPushClientExample {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushClientExample.class);

    // demo App defined in resources/jpush-api.conf 
	private static final String appKey ="dd1066407b044738b6479275";
	private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
	
	private static JPushClient _client = null;

	/**
	 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
	 * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
	 * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒)。
	 */
	private static long timeToLive =  60 * 60 * 24;  

	public static void main(String[] args) {
		/*
		 * Example1: 初始化,默认发送给android和ios，同时设置离线消息存活时间
		 * jpush = new JPushClient(masterSecret, appKey, timeToLive);
		 */

		/*		
		 * Example2: 只发送给android
		 * jpush = new JPushClient(masterSecret, appKey, DeviceEnum.Android);
		 */

		/*
		 * Example3: 只发送给IOS
		 * jpush = new JPushClient(masterSecret, appKey, DeviceEnum.IOS);
		 */

		/*
		 * Example4: 只发送给android,同时设置离线消息存活时间
		 * jpush = new JPushClient(masterSecret, appKey, timeToLive, DeviceEnum.Android);
		 */


		_client = new JPushClient(masterSecret, appKey);

		/*
		 * 是否启用ssl安全连接, 可选
		 * 参数：启用true， 禁用false，默认为非ssl连接
		 */
		//jpush.setEnableSSL(true);


		//测试发送消息或者通知
		testSend();
	}

	private static void testSend() {
		// 在实际业务中，建议 sendNo 是一个你自己的业务可以处理的一个自增数字。
		int sendNo = getRandomSendNo();
		String msgTitle = "Test from javen";
		String msgContent = "Test Test";

		/*
		 * IOS设备扩展参数,
		 * 设置badge，设置声音
		 */
		Map<String, Object> extra = new HashMap<String, Object>();
		IOSExtra iosExtra = new IOSExtra(10, "WindowsLogonSound.wav");
		extra.put("ios", iosExtra);
		
		/*
		 * 通知、消息  两者区别。请参考：http://docs.jpush.cn/pages/viewpage.action?pageId=3309701
		 */

		String regID = "050ffb64f67";
		
		CustomMessageParams params = new CustomMessageParams();
		params.setReceiverType(ReceiverTypeEnum.TAG);
		params.setReceiverValue("nexus7_0986b893");
		MessageResult msgResult = _client.sendCustomMessage(msgTitle, msgContent, params, null);
		
		//对所有用户发送消息。
		//MessageResult msgResult = jpush.sendCustomMessageWithAppKey(sendNo,msgTitle, msgContent);
		
		
		//覆盖指定msgId的消息,msgId可以从msgResult.getMsgid()获取。
		//MessageResult msgResult = jpush.sendNotificationWithAppKey(sendNo, msgTitle, msgContent, 0, extra,msgResult.getMsgid());


		if (null != msgResult) {
		    LOG.info("content - " + msgResult.responseResult.responseContent);
			System.out.println("服务器返回数据: " + msgResult.toString());
		} else {
			System.out.println("无法获取数据");
		}

		ReceivedsResult rrr = _client.getReportReceiveds("1708010723,1774452771");
        LOG.info("content - " + rrr.responseResult.responseContent);
		LOG.debug("Received - " + rrr);
	}

	public static final int MAX = Integer.MAX_VALUE;
	public static final int MIN = (int) MAX/2;

	/**
	 * 保持 sendNo 的唯一性是有必要的
	 * It is very important to keep sendNo unique.
	 * @return sendNo
	 */
	public static int getRandomSendNo() {
		return (int) (MIN + Math.random() * (MAX - MIN));
	}

}
