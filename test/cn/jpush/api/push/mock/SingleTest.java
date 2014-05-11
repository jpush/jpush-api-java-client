package cn.jpush.api.push.mock;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.audience.AudienceType;
import cn.jpush.api.push.model.notification.Notification;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

public class SingleTest extends BaseMockTests {
	
    @Test
    public void sendByTagRegistrationID() throws Exception {
        _mockServer.enqueue(new MockResponse().setBody(getResponseOK(111, 222)));
        
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
        
        RecordedRequest request = _mockServer.takeRequest();
        assertEquals("", payload.toString(), request.getUtf8Body());
        assertNotNull("", request.getHeader("Authorization"));
    }

    
}

