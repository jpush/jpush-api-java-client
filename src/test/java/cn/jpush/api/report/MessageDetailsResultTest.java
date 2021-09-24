package cn.jpush.api.report;

import cn.jpush.api.BaseTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MessageDetailsResultTest extends BaseTest {

    @Test
    public void getMessageDetailsResultTest() throws Exception{
        MessageDetailResult result = jpushClient.getMessagesDetail("38280845540235675");
        assertTrue(result.isResultOK());
        System.out.println(result.received_list.get(0).details);
        assertTrue(result.received_list.size() > 0);
    }
}
