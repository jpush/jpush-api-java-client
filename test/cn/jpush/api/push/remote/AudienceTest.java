package cn.jpush.api.push.remote;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
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
public class AudienceTest extends BaseRemoteTest {
    
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
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    @Test
    public void sendByRegistrationID() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(REGISTRATION_ID1))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
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
        assertTrue(result.isResultOK());
    }
    

    @Test
    public void sendByRegistrationIDMore() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(REGISTRATION_ID1, REGISTRATION_ID2))
                .setNotification(Notification.alert(ALERT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
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

