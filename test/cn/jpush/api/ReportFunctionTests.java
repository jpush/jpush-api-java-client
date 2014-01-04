package cn.jpush.api;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cn.jpush.api.report.ReceivedsResult;

public class ReportFunctionTests {
    private static final String appKey = "7d431e42dfa6a6d693ac2d04";    // use pushtalk to get static report
    private static final String masterSecret = "5e987ac6d2e04d95a9d8f0d1";
    
    private JPushClient jpushClient = null;
        
    @Before
    public void before() {
        jpushClient = new JPushClient(masterSecret, appKey);
    }
	
	@Test
	public void sendNotificationAll_android(){
	    ReceivedsResult rrr = jpushClient.getReportReceiveds("1613113584,1229760629,1174658841,1174658641");
		assertTrue(rrr.receivedList.size() > 0);
	}	
}

