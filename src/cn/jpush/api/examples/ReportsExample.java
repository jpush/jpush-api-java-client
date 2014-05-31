package cn.jpush.api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.report.ReceivedsResult;

public class ReportsExample {
    protected static final Logger LOG = LoggerFactory.getLogger(ReportsExample.class);

    // demo App defined in resources/jpush-api.conf 
	private static final String appKey ="dd1066407b044738b6479275";
	private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";

	public static void main(String[] args) {
		testGetReport();
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

