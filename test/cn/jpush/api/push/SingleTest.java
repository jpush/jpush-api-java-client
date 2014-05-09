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

public class SingleTest {
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
    
    private static final String ALERT = "JPush Test - alert";
    private static final String MSG_CONTENT = "JPush Test - msgContent";
    private static final int SUCCEED_RESULT_CODE = 0;
	
    private JPushClient _client = null;
    
    @Before
    public void before() {
        _client = new JPushClient(masterSecret, appKey);
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

    
}

