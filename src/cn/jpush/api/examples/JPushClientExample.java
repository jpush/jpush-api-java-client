package cn.jpush.api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.DeviceEnum;
import cn.jpush.api.push.CustomMessageParams;
import cn.jpush.api.push.MessageResult;
import cn.jpush.api.push.ReceiverTypeEnum;
import cn.jpush.api.report.ReceivedsResult;

public class JPushClientExample {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushClientExample.class);

    // demo App defined in resources/jpush-api.conf 
	private static final String appKey ="dd1066407b044738b6479275";
	private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
	
    private static final String msgTitle = "Test from API example";
    private static final String msgContent = "Test Test";
    private static final String registrationID = "0900e8d85ef";

	private static JPushClient jpushClient = null;

	public static void main(String[] args) {
		jpushClient = new JPushClient(masterSecret, appKey, 0, DeviceEnum.Android, false);

		testSend();
	}

	private static void testSend() {
		CustomMessageParams params = new CustomMessageParams();
		params.setReceiverType(ReceiverTypeEnum.REGISTRATION_ID);
		params.setReceiverValue(registrationID);
		MessageResult msgResult = jpushClient.sendCustomMessage(msgTitle, msgContent, params, null);
		
		if (null != msgResult) {
		    LOG.info("responseContent - " + msgResult.responseResult.responseContent);
		    LOG.info("msgResult - " + msgResult);
		}
		
		ReceivedsResult rrr = jpushClient.getReportReceiveds("1708010723,1774452771");
        LOG.info("content - " + rrr.responseResult.responseContent);
		LOG.debug("Received - " + rrr);
	}

}

