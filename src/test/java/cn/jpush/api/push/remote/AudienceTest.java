package cn.jpush.api.push.remote;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.SlowTests;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.audience.AudienceType;
import cn.jpush.api.push.model.notification.Notification;

/**
 * Device1: 0900e8d85ef
 * Device2: 
 *
 * tag1: Device1
 * tag2: Device2
 * tag_all: Device1, Device2
 * tag_no: no Device
 *
 * alias1: Device1
 * alias2: Device2
 * alias_no: no Device
 *
 */
@Category(SlowTests.class)
public class AudienceTest extends BaseRemotePushTest {
    public static final String TAG1 = "audience_tag1";
    public static final String TAG2 = "audience_tag2";
    public static final String TAG_ALL = "audience_tag_all";
    public static final String TAG_NO = "audience_tag_no";
    public static final String ALIAS1 = "audience_alias1";
    public static final String ALIAS2 = "audience_alias2";
    public static final String ALIAS_NO = "audience_alias_no";

    @BeforeClass
    public static void setAudiences() throws Exception {
        Set<String> tags1 = new HashSet<String>();
        tags1.add(TAG1);
        tags1.add(TAG_ALL);

        Set<String> tags2 = new HashSet<String>();
        tags1.add(TAG2);
        tags1.add(TAG_ALL);

        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        DefaultResult result = jpushClient.updateDeviceTagAlias(REGISTRATION_ID3, ALIAS1, tags1, null);
        assertThat(result.isResultOK(), is(true));

        result = jpushClient.updateDeviceTagAlias(REGISTRATION_ID3, ALIAS2, tags2, null);
        assertThat(result.isResultOK(), is(true));
    }

    // one --------

    @Test
    public void sendByTag() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(TAG1))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }

    @Test
    public void sendByTagAnd() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag_and(TAG1))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }

    @Test
    public void sendByAlias() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(ALIAS1))
                .setNotification(Notification.alert(ALERT))
                .build();
        try {
            PushResult result = _client.sendPush(payload);
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendByRegistrationID() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(REGISTRATION_ID3))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
    }

    // one more -------------------------

    @Test
    public void sendByTagMore() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(TAG1, TAG2))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }

    @Test
    public void sendByTagAndMore() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag_and(TAG1, TAG_ALL))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }

    @Test
    public void sendByTagAndMore_fail() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag_and(TAG1, TAG2))
                .setNotification(Notification.alert(ALERT))
                .build();

        try {
            _client.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(NO_TARGET, e.getErrorCode());
        }
    }

    @Test
    public void sendByAliasMore() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(ALIAS1, ALIAS2))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue("Should be OK", result.isResultOK());
    }


    @Test
    public void sendByRegistrationIDMore() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(REGISTRATION_ID1, REGISTRATION_ID2))
                .setNotification(Notification.alert(ALERT))
                .build();
        try {
            PushResult result = _client.sendPush(payload);
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }



    // composite ok -------------------------

    @Test
    public void sendByTagAlias() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.newBuilder()
                                .setAudienceType(AudienceType.ALIAS)
                                .addAudienceTargetValue(ALIAS1).build())
                        .addAudienceTarget(AudienceTarget.newBuilder()
                                .setAudienceType(AudienceType.TAG)
                                .addAudienceTargetValue(TAG_ALL).build())
                        .build())
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }

    @Test
    public void sendByTagRegistrationID() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.newBuilder()
                                .setAudienceType(AudienceType.REGISTRATION_ID)
                                .addAudienceTargetValue(REGISTRATION_ID1).build())
                        .addAudienceTarget(AudienceTarget.newBuilder()
                                .setAudienceType(AudienceType.TAG)
                                .addAudienceTargetValue(TAG_ALL).build())
                        .build())
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }

    @Test
    public void sendByTagRegistrationID_0() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.newBuilder()
                                .setAudienceType(AudienceType.REGISTRATION_ID)
                                .addAudienceTargetValue(REGISTRATION_ID1).build())
                        .addAudienceTarget(AudienceTarget.newBuilder()
                                .setAudienceType(AudienceType.TAG)
                                .addAudienceTargetValue(TAG_NO).build())
                        .build())
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }

    @Test
    public void sendByTagAlias_0() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.newBuilder()
                                .setAudienceType(AudienceType.ALIAS)
                                .addAudienceTargetValue(ALIAS1).build())
                        .addAudienceTarget(AudienceTarget.newBuilder()
                                .setAudienceType(AudienceType.TAG)
                                .addAudienceTargetValue(TAG2).build())
                        .build())
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }

    @Test
    public void sendByTagAlias_0_2() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.newBuilder()
                                .setAudienceType(AudienceType.ALIAS)
                                .addAudienceTargetValue(ALIAS_NO).build())
                        .addAudienceTarget(AudienceTarget.newBuilder()
                                .setAudienceType(AudienceType.TAG)
                                .addAudienceTargetValue(TAG_ALL).build())
                        .build())
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }


}

