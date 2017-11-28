package cn.jpush.api.push;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.BaseTest;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GroupPushClientTest extends BaseTest {

    protected static final Logger LOG = LoggerFactory.getLogger(GroupPushClientTest.class);

    @Test
    public void testSendGroupPush() {
        GroupPushClient groupPushClient = new GroupPushClient(GROUP_MASTER_SECRET, GROUP_PUSH_KEY);
        final PushPayload payload = buildPushObject_android();
        try {
            Map<String, PushResult> result = groupPushClient.sendGroupPush(payload);
            for (Map.Entry<String, PushResult> entry : result.entrySet()) {
                PushResult pushResult = entry.getValue();
                PushResult.Error error = pushResult.error;
                if (error != null) {
                    LOG.info("AppKey: " + entry.getKey() + " error code : " + error.getCode() + " error message: " + error.getMessage());
                } else {
                    LOG.info("AppKey: " + entry.getKey() + " sendno: " + pushResult.sendno + " msg_id:" + pushResult.msg_id);
                }

            }
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }

    public static PushPayload buildPushObject_android() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(REGISTRATION_ID3))
                .setNotification(Notification.newBuilder()
                        .setAlert("alert content")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("Android Title").build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())
                .build();
    }
}
