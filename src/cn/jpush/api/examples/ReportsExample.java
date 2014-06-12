package cn.jpush.api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.TimeUnit;
import cn.jpush.api.report.MessagesResult;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.UsersResult;

public class ReportsExample {
    protected static final Logger LOG = LoggerFactory.getLogger(ReportsExample.class);

    // demo App defined in resources/jpush-api.conf 
	private static final String appKey = "dd1066407b044738b6479275";
	private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";

	public static void main(String[] args) {
		testGetReport();
		testGetMessages();
		testGetUsers();
	}
	
    
	public static void testGetReport() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		ReceivedsResult receivedsResult = jpushClient.getReportReceiveds("1942377665");
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

    public static void testGetUsers() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        UsersResult result = jpushClient.getReportUsers(TimeUnit.DAY, "2014-06-10", 3);
        
        if (result.isResultOK()) {
            LOG.info("Users Count - " + result);
        } else {
            if (result.getErrorCode() > 0) {
                // 业务异常
                LOG.warn("Service error - ErrorCode: "
                        + result.getErrorCode() + ", ErrorMessage: "
                        + result.getErrorMessage());
            } else {
                // 未到达 JPush
                LOG.error("Other excepitons - "
                        + result.getExceptionString());
            }
        }
    }

    public static void testGetMessages() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        MessagesResult result = jpushClient.getReportMessages("269978303");
        
        if (result.isResultOK()) {
            LOG.info("Messages Count - " + result);
        } else {
            if (result.getErrorCode() > 0) {
                // 业务异常
                LOG.warn("Service error - ErrorCode: "
                        + result.getErrorCode() + ", ErrorMessage: "
                        + result.getErrorMessage());
            } else {
                // 未到达 JPush
                LOG.error("Other excepitons - "
                        + result.getExceptionString());
            }
        }
    }

}

