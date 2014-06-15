package cn.jpush.api.report;

import static org.junit.Assert.*;

import cn.jpush.api.common.TimeUnit;
import org.junit.Before;
import org.junit.Test;

import cn.jpush.api.JPushClient;

public class ReportFunctionTests {
    // use pushtalk to get static report
    private static final String appKey = "7d431e42dfa6a6d693ac2d04";
    private static final String masterSecret = "5e987ac6d2e04d95a9d8f0d1";
    
    private JPushClient jpushClient = null;
        
    @Before
    public void before() {
        jpushClient = new JPushClient(masterSecret, appKey);
    }
	
	@Test
	public void getReceivedsFixed() throws Exception {
	    ReceivedsResult result = jpushClient.getReportReceiveds("1613113584,1229760629,1174658841,1174658641");
	    assertTrue(result.isResultOK());
		assertTrue(result.received_list.size() > 0);
	}
	
    @Test
    public void getReceivedsFixed2() throws Exception {
        ReceivedsResult result = jpushClient.getReportReceiveds("1613113584,   ,1229760629,  ");
        assertTrue(result.isResultOK());
        assertTrue(result.received_list.size() > 0);
    }

    @Test
    public void getMessagesTest() throws Exception {
        MessagesResult result = jpushClient.getReportMessages("1613113584");
        assertTrue(result.isResultOK());
        assertTrue(result.messages.size() > 0);
    }

    @Test
    public void getMessagesTest2() throws Exception {
        MessagesResult result = jpushClient.getReportMessages("1613113584,   ,1229760629,  ");
        assertTrue(result.isResultOK());
        assertTrue(result.messages.size() > 0);
    }

    @Test
    public void getUsersTest() throws Exception {
        UsersResult result = jpushClient.getReportUsers(TimeUnit.MONTH, "2014-05", 1);
        assertTrue(result.isResultOK());
        assertTrue(result.items.size() > 0);
    }

    @Test
    public void getUserTest2() throws  Exception {
        UsersResult result = jpushClient.getReportUsers(TimeUnit.DAY, "2014-05-10", 5);
        assertTrue(result.isResultOK());
        assertTrue(result.items.size() > 0);
    }

    @Test
    public void getUserTest3() throws Exception {
        UsersResult result = jpushClient.getReportUsers(TimeUnit.HOUR, "2014-05-10 06", 10);
        assertTrue(result.isResultOK());
        assertTrue(result.items.size() > 0);
    }



}

