package cn.jpush.api.push;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cn.jpush.api.JPushClient;
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
public class AudienceTests {
    private static final String appKey ="dd1066407b044738b6479275";
    private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";

    private static final String TAG1 = "tag1";
    private static final String TAG2 = "tag2";
    private static final String TAG_ALL = "tag_all";
    private static final String TAG_NO = "tag_no";
    private static final String ALIAS1 = "alias1";
    private static final String ALIAS2 = "alias2";
    private static final String ALIAS_NO = "alias_no";
    private static final String REGISTRATION_ID1 = "0900e8d85ef";
    private static final String REGISTRATION_ID2 = "0a04ad7d8b4";

    private static final int SUCCEED_RESULT_CODE = 0;
    private static final int NO_TARGET = 1011;
    private static final String ALERT = "JPush Test - alert";
    private static final String MSG_CONTENT = "JPush Test - msgContent";
    
    private JPushClient _client = null;
    
    @Before
    public void before() {
        _client = new JPushClient(masterSecret, appKey);
    }
    
    // one --------
    
    @Test
    public void sendByTag() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(TAG1))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendByTagAnd() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag_and(TAG1))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendByAlias() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(ALIAS1))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendByRegistrationID() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(REGISTRATION_ID1))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    // one more -------------------------
    
    @Test
    public void sendByTagMore() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(TAG1, TAG2))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendByTagAndMore() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag_and(TAG1, TAG_ALL))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendByTagAndMore_fail() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag_and(TAG1, TAG2))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(NO_TARGET, result.getErrorCode());
    }
    
    @Test
    public void sendByAliasMore() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(ALIAS1, ALIAS2))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    

    @Test
    public void sendByRegistrationIDMore() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(REGISTRATION_ID1, REGISTRATION_ID2))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    

    
    // composite ok -------------------------
    
    @Test
    public void sendByTagAlias() {
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
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendByTagRegistrationID() {
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
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    // composite fail ------------------------
    
    @Test
    public void sendByTagAlias_fail() {
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
        assertEquals(NO_TARGET, result.getErrorCode());
    }

    @Test
    public void sendByTagAlias_fail2() {
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
        assertEquals(NO_TARGET, result.getErrorCode());
    }
    
    @Test
    public void sendByTagRegistrationID_fail() {
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
        assertEquals(NO_TARGET, result.getErrorCode());
    }


}

