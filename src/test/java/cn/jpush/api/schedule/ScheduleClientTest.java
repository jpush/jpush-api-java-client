package cn.jpush.api.schedule;

import cn.jpush.api.BaseTest;
import cn.jpush.api.SlowTests;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * ScheduleClient Tester.
 *
 * @author liucy
 * @version 1.0
 */

@Category(SlowTests.class)
public class ScheduleClientTest extends BaseTest {


    private ScheduleClient client;

    @Override
    public void before() {
        client = new ScheduleClient(MASTER_SECRET, APP_KEY);
    }

    /**
     * Method: createSchedule(SchedulePayload payload)
     */
    @Test
    public void testCreateSchedule() {
//TODO: Test goes here... 
    }

    /**
     * Method: getScheduleList(int page)
     */
    @Test
    public void testGetScheduleList() {
        try {
            ScheduleListResult result = client.getScheduleList(1);
            Assert.assertEquals("", 1, result.page);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            Assert.assertTrue(e.getErrorMessage(), false);
        }
    }

    /**
     * Method: getSchedule(String scheduleId)
     */
    @Test
    public void testGetSchedule() {
//TODO: Test goes here... 
    }

    /**
     * Method: updateSchedule(String scheduleId, SchedulePayload payload)
     */
    @Test
    public void testUpdateSchedule() {
//TODO: Test goes here... 
    }

    /**
     * Method: deleteSchedule(String scheduleId)
     */
    @Test
    public void testDeleteSchedule() {
//TODO: Test goes here...
    }

} 
