package cn.jpush.api.report;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.jiguang.common.TimeUnit;
import cn.jpush.api.BaseTest;
import cn.jpush.api.SlowTests;

@Category(SlowTests.class)
public class ReportFunctionTests extends BaseTest {

	@Test
	public void getReceivedsFixed() throws Exception {
	    ReceivedsResult result = jpushClient.getReportReceiveds("1613113584,1229760629,1174658841,1174658641");
	    assertTrue(result.isResultOK());
		assertTrue(result.received_list.size() > 0);
	}
	
    @Test
    public void getReceivedsFixed2() throws Exception {
        ReceivedsResult result = jpushClient.getReportReceiveds("1613113584, 1229760629,  ");
        assertTrue(result.isResultOK());
        assertTrue(result.received_list.size() > 0);
    }

    public void getMessagesTest() throws Exception {
        MessagesResult result = jpushClient.getReportMessages("1613113584");
        assertTrue(result.isResultOK());
        assertTrue(result.messages.size() > 0);
    }

    public void getMessagesTest2() throws Exception {
        MessagesResult result = jpushClient.getReportMessages("1613113584,   ,1229760629,  ");
        assertTrue(result.isResultOK());
        assertTrue(result.messages.size() > 0);
    }

    public void getUsersTest() throws Exception {
        UsersResult result = jpushClient.getReportUsers(TimeUnit.MONTH, "2014-05", 1);
        assertTrue(result.isResultOK());
        assertTrue(result.items.size() > 0);
    }

    public void getUserTest2() throws  Exception {
        UsersResult result = jpushClient.getReportUsers(TimeUnit.DAY, "2014-05-10", 5);
        assertTrue(result.isResultOK());
        assertTrue(result.items.size() > 0);
    }

    public void getUserTest3() throws Exception {
        UsersResult result = jpushClient.getReportUsers(TimeUnit.HOUR, "2014-05-10 06", 10);
        assertTrue(result.isResultOK());
        assertTrue(result.items.size() > 0);
    }



}

