package cn.jpush.api.examples;

import cn.jiguang.common.TimeUnit;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.report.*;
import cn.jpush.api.report.model.CheckMessagePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ReportsExample {
    protected static final Logger LOG = LoggerFactory.getLogger(ReportsExample.class);


    /**
     * Change the app key and master secret to your own account
     * If you want to use push by group, please enter your own group push key and group master secret.
     */
    protected static final String appKey = "8f02a4fa717a6235734d92de";
    protected static final String masterSecret = "cf6de29f9e66432ba4ac1c32";
    protected static final String GROUP_PUSH_KEY = "2c88a01e073a0fe4fc7b167c";
    protected static final String GROUP_MASTER_SECRET = "b11314807507e2bcfdeebe2e";


    public static final String REGISTRATION_ID1 = "0900e8d85ef";
    public static final String REGISTRATION_ID2 = "0a04ad7d8b4";
    public static final String REGISTRATION_ID3 = "18071adc030dcba91c0";

	public static void main(String[] args) {

//        testGetReceivedDetail();
//      testGetMessagesDetail();
//		testGetReport();
//		testGetMessages();
//		testGetUsers();
//		testGetMessageStatus();
        testGetGroupMessagesDetail();
//        testGetGroupUsers();
	}
	
    
	public static void testGetReport() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		try {
            ReceivedsResult result = jpushClient.getReportReceiveds("2252035206045707");
            LOG.info("Got result - " + result);
            
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
	}

    /**
     * 用户统计
     * https://docs.jiguang.cn/jpush/server/push/rest_api_v3_report/#vip_2
     */
    public static void testGetUsers() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        try {
            UsersResult result = jpushClient.getReportUsers(TimeUnit.DAY, "2014-06-10", 3);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testGetMessages() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        try {
            MessagesResult result = jpushClient.getReportMessages("269978303");
            LOG.debug("Got result - " + result);
            
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testGetMessageStatus() {
        JPushClient jPushClient = new JPushClient(masterSecret, appKey);
        CheckMessagePayload payload = CheckMessagePayload.newBuilder()
                .setMsgId(3993287034L)
                .addRegistrationIds(REGISTRATION_ID1, REGISTRATION_ID2, REGISTRATION_ID3)
                .setDate("2017-08-08")
                .build();
        try {
            Map<String, MessageStatus> map = jPushClient.getMessageStatus(payload);
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

    /**
     * 消息统计
     * https://docs.jiguang.cn/jpush/server/push/rest_api_v3_report/#vip
     */
    public static void testGetMessagesDetail() {

        JPushClient jPushClient = new JPushClient(masterSecret, appKey);
//        String msgIds = "3993287034,3993287035,3993287036";
        String msgIds = "38280839685161019";
        try {
            MessageDetailResult result = jPushClient.getMessagesDetail(msgIds);
            LOG.info("msgIds: {}, MessageDetail: {}", msgIds, result.received_list);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }

    /**
     * 送达详情
     * https://docs.jiguang.cn/jpush/server/push/rest_api_v3_report/#_7
     */
    public static void testGetReceivedDetail() {

        JPushClient jPushClient = new JPushClient(masterSecret, appKey);
        String msgIds = "3993287034,3993287035,3993287036";
        try {
            ReceivedsResult result = jPushClient.getReceivedsDetail(msgIds);
            LOG.info("msgIds: {}, ReceivedsDetail: {}", msgIds, result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    /**
     * 分组统计-消息统计
     * https://docs.jiguang.cn/jpush/server/push/rest_api_v3_report/#-vip
     */
    public static void testGetGroupMessagesDetail() {

        JPushClient jPushClient = new JPushClient(GROUP_MASTER_SECRET, GROUP_PUSH_KEY);
        String groupMsgIds = "c4hmbne09s6bbaca91vg";
        try {
            GroupMessageDetailResult result = jPushClient.getGroupMessagesDetail(groupMsgIds);
            LOG.info("group_msgIds: {}, MessageDetail: {}", groupMsgIds, result.received_list);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }

    /**
     * 分组统计-用户统计
     * https://docs.jiguang.cn/jpush/server/push/rest_api_v3_report/#-vip_1
     */
    public static void testGetGroupUsers() {
        JPushClient jpushClient = new JPushClient(GROUP_MASTER_SECRET, GROUP_PUSH_KEY);
        try {
            GroupUsersResult result = jpushClient.getGroupReportUsers(TimeUnit.DAY, "2021-08-21", 3);
            LOG.debug("Got result - " + result);

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

