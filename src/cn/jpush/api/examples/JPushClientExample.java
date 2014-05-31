package cn.jpush.api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.ReceivedsResult;

public class JPushClientExample {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushClientExample.class);

    // demo App defined in resources/jpush-api.conf 
	private static final String appKey ="dd1066407b044738b6479275";
	private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
	
	public static final String TITLE = "Test from API example";
    public static final String CONTENT = "Test from Javen";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";

	public static void main(String[] args) {
	    testSendPush();
		testGetReport();
	}
	
	private static void testSendPush() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = buildPushObject_all_all_alert();
        LOG.info("Paylaod JSON - " + payload.toString());
        
        PushResult result = jpushClient.sendPush(payload);
        if (result.isResultOK()) {
            LOG.debug(result.toString());
        } else {
            if (result.getErrorCode() > 0) {
                LOG.warn(result.getOriginalContent());
            } else {
                LOG.debug("Maybe connect error. Retry laster. ");
            }
        }
	}
	
	public static PushPayload buildPushObject_all_all_alert() {
	    return PushPayload.alertAll(CONTENT);
	}
	
    public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias("alias1"))
                .setNotification(Notification.alert(CONTENT))
                .build();
    }
    
    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(CONTENT)
                                .setTitle(TITLE)
                                .build())
                        .build())
                .build();
    }
    
    
	public static void testGetReport() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		ReceivedsResult receivedsResult = jpushClient.getReportReceiveds("1708010723,1774452771");
        LOG.debug("responseContent - " + receivedsResult.getOriginalContent());
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
                        + receivedsResult.getExceptionString());
            }
		}
	}

}

