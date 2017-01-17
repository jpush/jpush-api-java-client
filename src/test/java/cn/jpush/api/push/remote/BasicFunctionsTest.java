package cn.jpush.api.push.remote;

import static org.junit.Assert.assertTrue;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.jiguang.common.DeviceType;
import cn.jpush.api.SlowTests;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.WinphoneNotification;

@Category(SlowTests.class)
public class BasicFunctionsTest extends BaseRemotePushTest {
	
	@Test
    public void sendSimpleNotification_Pall_Ndefault() throws Exception {
	    PushPayload payload = PushPayload.alertAll("Pall Nall default alert");
		PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
	}
	
    @Test
    public void sendSimpleNotification_Pandroid_Nandroid() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.alert("Pandroid Nandroid alert"))
                        .build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    @Test
    public void sendSimpleNotification_Pall_Nandroid() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.alert("Pall Nandroid alert"))
                        .build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    @Test
    public void sendSimpleNotification_Pios_Nios() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.alert("Pios Nios alert"))
                        .build())
                .build();
        try {
            PushResult result = _client.sendPush(payload);
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendSimpleNotification_Pall_Nios() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.alert("Pall Nios alert"))
                        .build())
                .build();
        try {
            PushResult result = _client.sendPush(payload);
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendSimpleNotification_Pwp_Nwp() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.winphone())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(WinphoneNotification.alert("Pwp Nwp alert"))
                        .build())
                .build();
        try {
            PushResult result = _client.sendPush(payload);
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendSimpleNotification_Pall_Nwp() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(WinphoneNotification.alert("Pall Nwp alert"))
                        .build())
                .build();
        try {
            PushResult result = _client.sendPush(payload);
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void sendSimpleNotification_Pall_Nall() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.newBuilder()
                        .addDeviceType(DeviceType.IOS)
                        .addDeviceType(DeviceType.WinPhone)
                        .addDeviceType(DeviceType.Android).build())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(WinphoneNotification.alert("Pall Nall wp alert"))
                        .addPlatformNotification(IosNotification.alert("Pall Nall ios alert"))
                        .addPlatformNotification(AndroidNotification.alert("Pall Nall android alert"))
                        .build())
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    @Test
    public void sendSimpleMessage_default() throws Exception {
        PushPayload payload = PushPayload.messageAll("Pall msg");
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    @Test
    public void sendSimpleMessage_Pandroid() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setMessage(Message.content("Pandroid msg"))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
    @Test
    public void sendSimpleMessage_Pios() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.all())
                .setMessage(Message.content("Pios msg"))
                .build();
        try {
            PushResult result = _client.sendPush(payload);
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }
    
    //@Test
    public void sendSimpleMessage_Pwinphone() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.winphone())
                .setAudience(Audience.all())
                .setMessage(Message.content("Pwp msg"))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    
	
    @Test
    public void sendSimpleMessageAndNotification_Pall() throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert("Pall Nall Mall alert"))
                .setMessage(Message.content("Pall Nall Mall msg"))
                .build();
        PushResult result = _client.sendPush(payload);
        assertTrue(result.isResultOK());
    }
    

    
}

