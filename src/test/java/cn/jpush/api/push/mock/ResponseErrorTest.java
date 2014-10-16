package cn.jpush.api.push.mock;
import org.junit.Test;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ResponseErrorTest extends BaseMockTest {
    
//    @Test
//    public void appKeyNotExist() {
//        _mockServer.enqueue(new MockResponse()
//            .setResponseCode(400)
//            .setBody(getResponseError(111, 222, 1011, "appKey does not exist.")));
//        
//        String appKey = "dd1066407b044738b6479274";
//        JPushClient client = new JPushClient(masterSecret, appKey);
//        PushPayload payload = PushPayload.alertAll(ALERT);
//        PushResult result = client.sendPush(payload);
//        assertEquals(APPKEY_NOT_EXIST, result.getErrorCode());
//    }
//
//    @Test
//    public void authenticationFail() {
//        String masterSecret = "2b38ce69b1de2a7fa95706e2";
//        JPushClient client = new JPushClient(masterSecret, appKey);
//        PushPayload payload = PushPayload.alertAll(ALERT);
//        PushResult result = client.sendPush(payload);
//        assertEquals(AUTHENTICATION_FAIL, result.getErrorCode());
//    }

    @Test
    public void tooBig() {
        String msgContent = "深圳制造厂的朋友告诉我，过去的一年，他们服务了几十家小型创业公司，代工智能手表。不过，今年这些创业公司已经找不到了，庆幸的是，代工厂都是先付款再生产，也就没有损失。可穿戴设备、硬件创新，大潮初起，泥沙俱下，浪潮过后，却是遍地狼藉。国内的智能手环、手表们，如土曼、果壳，在 Jawbone、Google Glass 们引领下，纷纷推出“划时代”的产品，一时间，国内宣称要做可穿戴设备的公司，如过江之鲫。2013 年，不说句硬件创新，不戴款智能手环，都不好意思说自己是站在人文与科技的十字路口。2013 年，身边的朋友纷纷佩戴上了 Jawbone，幸运的人也会戴上传说中的智能手表。不过，现在越来越多的朋友开始放弃这些所谓的可穿戴式设备。";
        _currentPayload = PushPayload.messageAll(msgContent).toString();
        _expectedErrorCode = 1005;
    }

    // ---------------- invalid params
    
    @Test
    public void invalidParams_platform() throws Exception {
        JsonObject payload = new JsonObject();
        payload.add("platform", new JsonPrimitive("all_platform"));
        payload.add("audience", Audience.all().toJSON());
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1003;
    }

    @Test
    public void invalidParams_audience() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", new JsonPrimitive("all_audience"));
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1003;
    }

    @Test
    public void invalidParams_notification() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        payload.add("notification", new JsonPrimitive(ALERT));
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1003;
    }

    @Test
    public void invalidParams_notification_android() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        notification.add("android", new JsonPrimitive(ALERT));
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1003;
    }
    
    @Test
    public void invalidParams_notification_ios() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        notification.add("ios", new JsonPrimitive(ALERT));
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1003;
    }
    
    @Test
    public void invalidParams_notification_winphone() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        notification.add("winphone", new JsonPrimitive(ALERT));
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1003;
    }
    
    @Test
    public void invalidParams_notification_android_builderidNotNumber() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject android = new JsonObject();
        android.add("builder_id", new JsonPrimitive("builder_id_string"));
        
        notification.add("android", android);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1003;
    }
    
    
    // ------------------------ lack of params

    @Test
    public void lackOfParams_platform() {
        JsonObject payload = new JsonObject();
        payload.add("audience", Audience.all().toJSON());
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1002;
    }
    
    @Test
    public void lackOfParams_audience() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1002;
    }
    
    @Test
    public void lackOfParams_messageAndNotificaiton() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1002;
    }

    @Test
    public void lackOfParams_notification_android_empty() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject android = new JsonObject();
        
        notification.add("android", android);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1002;
    }
    
    @Test
    public void lackOfParams_notification_ios_empty() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject ios = new JsonObject();
        
        notification.add("ios", ios);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1002;
    }
    
    @Test
    public void lackOfParams_notification_winphone_empty() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject winphone = new JsonObject();
        
        notification.add("winphone", winphone);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1002;
    }
    
    @Test
    public void lackOfParams_notification_android_noalert() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject android = new JsonObject();
        android.add("title", new JsonPrimitive("title"));
        
        notification.add("android", android);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1002;
    }
    
    @Test
    public void lackOfParams_notification_ios_noalert() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject ios = new JsonObject();
        ios.add("badge", new JsonPrimitive(11));
        
        notification.add("ios", ios);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1002;
    }
    
    @Test
    public void lackOfParams_notification_winphone_noalert() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject winphone = new JsonObject();
        winphone.add("title", new JsonPrimitive("title"));
        
        notification.add("winphone", winphone);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        _currentPayload = payload.toString();
        _expectedErrorCode = 1002;
    }
    

	
}

