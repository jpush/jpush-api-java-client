package cn.jpush.api.examples;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.DeviceEnum;
import cn.jpush.api.push.CustomMessageParams;
import cn.jpush.api.push.MessageResult;
import cn.jpush.api.push.NotificationParams;
import cn.jpush.api.push.ReceiverTypeEnum;
import cn.jpush.api.report.ReceivedsResult;

public class JPushClientExample {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushClientExample.class);

    // demo App defined in resources/jpush-api.conf 
	private static final String appKey = "82d5658a74e76241f402d4a4";   //"dd1066407b044738b6479275";
	private static final String masterSecret = "451f2e305b6a9f523b3d015c";     //"2b38ce69b1de2a7fa95706ea";
	
	public static final String msgTitle = "Test from API example";
    public static final String msgContent = "Test Test";
    public static final String registrationID = "092923191f1";
    public static final String tag = "tag_api";

	public static void main(String[] args) {
//		testSend();
		testSendMpnsNotificaiton();
//		testGetReport();
	}
	
	private static void testSendMpnsNotificaiton() {
	    JPushClient jpushClient = new JPushClient(masterSecret, appKey, 0, DeviceEnum.WinPhone, false);
	    NotificationParams params = new NotificationParams();
	    params.setReceiverType(ReceiverTypeEnum.REGISTRATION_ID);
	    params.setReceiverValue(registrationID);
	    params.setMpnsNotificationTitle(msgTitle);
	    
	    Map<String, Object> extras = new HashMap<String, Object>();
	    extras.put(NotificationParams.MPNS_EXTRA_OPEN_PAGE, "/MainPage.xaml");
	    extras.put("key1", "value1");
	    extras.put("key2", "value2");
	    
	    MessageResult msgResult = jpushClient.sendNotification(msgContent, params, extras);
        if (msgResult.isResultOK()) {
            LOG.info("msgResult - " + msgResult);
            LOG.info("messageId - " + msgResult.getMessageId());
        } else {
            if (msgResult.getErrorCode() > 0) {
                // 业务异常
                LOG.warn("Service error - ErrorCode: "
                        + msgResult.getErrorCode() + ", ErrorMessage: "
                        + msgResult.getErrorMessage());
            } else {
                // 未到达 JPush 
                LOG.error("Other excepitons - "
                        + msgResult.responseResult.exceptionString);
            }
        }
	}
	
	private static void testSend() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 0, DeviceEnum.Android, false);
		CustomMessageParams params = new CustomMessageParams();
		//params.setReceiverType(ReceiverTypeEnum.REGISTRATION_ID);
		//params.setReceiverValue(registrationID);
		params.setReceiverType(ReceiverTypeEnum.TAG);
		params.setReceiverValue(tag);
		
		MessageResult msgResult = jpushClient.sendCustomMessage(msgTitle, msgContent, params, null);
        LOG.debug("responseContent - " + msgResult.responseResult.responseContent);
		if (msgResult.isResultOK()) {
	        LOG.info("msgResult - " + msgResult);
	        LOG.info("messageId - " + msgResult.getMessageId());
		} else {
		    if (msgResult.getErrorCode() > 0) {
		        // 业务异常
		        LOG.warn("Service error - ErrorCode: "
		                + msgResult.getErrorCode() + ", ErrorMessage: "
		                + msgResult.getErrorMessage());
		    } else {
		        // 未到达 JPush 
		        LOG.error("Other excepitons - "
		                + msgResult.responseResult.exceptionString);
		    }
		}
	}
	
	public static void testGetReport() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		ReceivedsResult receivedsResult = jpushClient.getReportReceiveds("1708010723,1774452771");
        LOG.debug("responseContent - " + receivedsResult.responseResult.responseContent);
		if (receivedsResult.isResultOK()) {
		    LOG.info("Receiveds - " + receivedsResult);
		} else {
            if (receivedsResult.getErrorCode() > 0) {
                // 业务异常
                LOG.warn("Service error - ErrorCode: "
                        + receivedsResult.getErrorCode() + ", ErrorMessage: "
                        + receivedsResult.getErrorMessage());
            } else {
                // 未到达 JPush
                LOG.error("Other excepitons - "
                        + receivedsResult.responseResult.exceptionString);
            }
		}
	}

}

