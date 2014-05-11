package cn.jpush.api.push.remote;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.jpush.api.common.DeviceType;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.WinphoneNotification;

public class BasicFunctionsTests extends BaseRemoteTests {
	
	@Test
    public void sendSimpleNotification_Pall_Ndefault() {
	    PushPayload payload = PushPayload.alertAll(ALERT);
		PushResult result = _client.sendPush(payload);
		assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
	}
	
    @Test
    public void sendSimpleNotification_Pandroid_Nandroid() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.alert("alert"))
                        .build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendSimpleNotification_Pall_Nandroid() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.alert("alert"))
                        .build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendSimpleNotification_Pios_Nios() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.alert("alert"))
                        .build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendSimpleNotification_Pall_Nios() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.alert("alert"))
                        .build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendSimpleNotification_Pwp_Nwp() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.winphone())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(WinphoneNotification.alert("alert"))
                        .build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendSimpleNotification_wp() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(WinphoneNotification.alert("alert"))
                        .build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    
    @Test
    public void sendSimpleNotification_Pall_Nall() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.newBuilder()
                        .addDeviceType(DeviceType.IOS)
                        .addDeviceType(DeviceType.WinPhone)
                        .addDeviceType(DeviceType.Android).build())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(WinphoneNotification.alert("alert"))
                        .addPlatformNotification(IosNotification.alert("alert"))
                        .addPlatformNotification(AndroidNotification.alert("alert"))
                        .build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendSimpleMessage_default() {
        PushPayload payload = PushPayload.messageAll(MSG_CONTENT);
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendSimpleMessage_Pandroid() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setMessage(Message.content(MSG_CONTENT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendSimpleMessage_Pios() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setMessage(Message.content(MSG_CONTENT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
    @Test
    public void sendSimpleMessage_Pwinphone() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.winphone())
                .setAudience(Audience.all())
                .setMessage(Message.content(MSG_CONTENT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    
	
    @Test
    public void sendSimpleMessageAndNotification_Pall() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(ALERT))
                .setMessage(Message.content(MSG_CONTENT))
                .build();
        PushResult result = _client.sendPush(payload);
        assertEquals(SUCCEED_RESULT_CODE, result.getErrorCode());
    }
    

    
}

