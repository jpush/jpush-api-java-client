package cn.jpush.api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.TimeUnit;
import cn.jiguang.common.Week;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.schedule.ScheduleListResult;
import cn.jpush.api.schedule.ScheduleMsgIdsResult;
import cn.jpush.api.schedule.ScheduleResult;
import cn.jpush.api.schedule.model.SchedulePayload;
import cn.jpush.api.schedule.model.TriggerPayload;

public class ScheduleExample {

    protected static final Logger LOG = LoggerFactory.getLogger(ScheduleExample.class);

    private static final String appKey ="7b4b94cca0d185d611e53cca";
    private static final String masterSecret = "860803cf613ed54aa3b941a8";


    public static void main(String[] args) {
//    	testCreateSingleSchedule();
//    	testCreateDailySchedule();
//        testDeleteSchedule();
//        testGetScheduleList();
//        testUpdateSchedule();
//        testGetSchedule();
    	testGetScheduleMsgIds();
    }

    public static void testCreateSingleSchedule() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        String name = "test_schedule_example";
        String time = "2018-10-14 10:48:25";
        PushPayload push = PushPayload.alertAll("test schedule example.");
        try {
            ScheduleResult result = jpushClient.createSingleSchedule(name, time, push);
            LOG.info("schedule result is " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testCreateDailySchedule() {
        JPushClient jPushClient = new JPushClient(masterSecret, appKey);
        String name = "test_daily_schedule";
        String start = "2019-08-06 12:16:13";
        String end = "2020-08-06 12:16:13";
        String time = "14:00:00";
        PushPayload push = PushPayload.alertAll("test daily example.");
        try {
            ScheduleResult result = jPushClient.createDailySchedule(name, start, end, time, push);
            LOG.info("schedule result is " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testCreateWeeklySchedule() {
        JPushClient jPushClient = new JPushClient(masterSecret, appKey);
        String name = "test_weekly_schedule";
        String start = "2015-08-06 12:16:13";
        String end = "2115-08-06 12:16:13";
        String time = "14:00:00";
        Week[] days = {Week.MON, Week.FRI};
        PushPayload push = PushPayload.alertAll("test weekly example.");
        try {
            ScheduleResult result = jPushClient.createWeeklySchedule(name, start, end, time, days, push);
            LOG.info("schedule result is " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testCreateMonthlySchedule() {
        JPushClient jPushClient = new JPushClient(masterSecret, appKey);
        String name = "test_monthly_schedule";
        String start = "2015-08-06 12:16:13";
        String end = "2115-08-06 12:16:13";
        String time = "14:00:00";
        String[] points = {"01", "02"};
        PushPayload push = PushPayload.alertAll("test monthly example.");
        try {
            ScheduleResult result = jPushClient.createMonthlySchedule(name, start, end, time, points, push);
            LOG.info("schedule result is " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later.", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testDeleteSchedule() {
        String scheduleId = "95bbd066-3a88-11e5-8e62-0021f652c102";
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);

        try {
            jpushClient.deleteSchedule(scheduleId);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testGetScheduleList() {
        int page = 1;
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);

        try {
            ScheduleListResult list = jpushClient.getScheduleList(page);
            LOG.info("total " + list.getTotal_count());
            for(ScheduleResult s : list.getSchedules()) {
                LOG.info(s.toString());
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

    public static void testUpdateSchedule() {
        String scheduleId = "95bbd066-3a88-11e5-8e62-0021f652c102";
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        String[] points = {Week.MON.name(), Week.FRI.name()};
        TriggerPayload trigger = TriggerPayload.newBuilder()
                .setPeriodTime("2015-08-01 12:10:00", "2015-08-30 12:12:12", "15:00:00")
                .setTimeFrequency(TimeUnit.WEEK, 2, points)
                .buildPeriodical();
        SchedulePayload payload = SchedulePayload.newBuilder()
                .setName("test_update_schedule")
                .setEnabled(false)
                .setTrigger(trigger)
                .build();
        try {
            jpushClient.updateSchedule(scheduleId, payload);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testGetSchedule() {
        String scheduleId = "13573bfa-cf5a-11e8-bd7f-0021f6b55802";
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);

        try {
            ScheduleResult result = jpushClient.getSchedule(scheduleId);
            LOG.info("schedule " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }
    
    public static void testGetScheduleMsgIds() {
        String scheduleId = "63db10a0-cf5b-11e8-ac2f-0021f6b55802";
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);

        try {
        	ScheduleMsgIdsResult result = jpushClient.getScheduleMsgIds(scheduleId);
            LOG.info("scheduleMsgIds :" + result);
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
