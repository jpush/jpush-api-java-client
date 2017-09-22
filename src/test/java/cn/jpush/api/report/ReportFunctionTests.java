package cn.jpush.api.report;

import static org.junit.Assert.assertTrue;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.report.model.CheckMessagePayload;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.jiguang.common.TimeUnit;
import cn.jpush.api.BaseTest;
import cn.jpush.api.SlowTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Category(SlowTests.class)
public class ReportFunctionTests extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(ReportFunctionTests.class);

	@Test
	public void getReceivedsFixed() throws Exception {
	    ReceivedsResult result = jpushClient.getReportReceiveds("1613113584,1229760629,1174658841,1174658641");
	    assertTrue(result.isResultOK());
		assertTrue(result.received_list.size() > 0);
	}
	
    @Test
    public void getReceivedsFixed2() throws Exception {
        ReceivedsResult result = jpushClient.getReportReceiveds("1613113584, 1229760629");
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

    @Test
    public void testGetMessageStatus() {
        CheckMessagePayload payload = CheckMessagePayload.newBuilder()
                .setMsgId(3993287034L)
                .addRegistrationIds(REGISTRATION_ID1, REGISTRATION_ID2, REGISTRATION_ID3)
                .setDate("2017-08-08")
                .build();
        try {
            Map<String, MessageStatus> map = jpushClient.getMessageStatus(payload);
            for (Map.Entry<String, MessageStatus> entry : map.entrySet()) {
                LOG.info("registrationId: " + entry.getKey() + " status: " + entry.getValue().getStatus());
            }
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

}

