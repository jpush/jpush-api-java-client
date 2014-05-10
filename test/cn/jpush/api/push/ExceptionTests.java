package cn.jpush.api.push;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ExceptionTests {
    private static final String appKey ="dd1066407b044738b6479275";
    private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
    
    private static final int LACK_OF_PARAMS = 1002;
    private static final int INVALID_PARAMS = 1003;
    private static final int AUTHENTICATION_FAIL = 1004;
    private static final int TOO_BIG = 1005;
    private static final int APPKEY_NOT_EXIST = 1008;

    private static final String ALERT = "JPush Test - alert";
    private static final String MSG_CONTENT = "JPush Test - msgContent";
    private static final String TAG = "tag_api";
    private static final String ALIAS = "alias_api";
    
    private JPushClient _client = null;
    
    @Before
    public void before() {
        _client = new JPushClient(masterSecret, appKey);
    }
    
    @Test
    public void appKeyNotExist() {
        String appKey = "dd1066407b044738b6479274";
        JPushClient client = new JPushClient(masterSecret, appKey);
        PushPayload payload = PushPayload.notificationAlertAll(ALERT);
        PushResult result = client.sendPush(payload);
        assertEquals(APPKEY_NOT_EXIST, result.getErrorCode());
    }

    @Test
    public void authenticationFail() {
        String masterSecret = "2b38ce69b1de2a7fa95706e2";
        JPushClient client = new JPushClient(masterSecret, appKey);
        PushPayload payload = PushPayload.notificationAlertAll(ALERT);
        PushResult result = client.sendPush(payload);
        assertEquals(AUTHENTICATION_FAIL, result.getErrorCode());
    }

    @Test
    public void tooBig() {
        String msgContent = "深圳制造厂的朋友告诉我，过去的一年，他们服务了几十家小型创业公司，代工智能手表。不过，今年这些创业公司已经找不到了，庆幸的是，代工厂都是先付款再生产，也就没有损失。可穿戴设备、硬件创新，大潮初起，泥沙俱下，浪潮过后，却是遍地狼藉。国内的智能手环、手表们，如土曼、果壳，在 Jawbone、Google Glass 们引领下，纷纷推出“划时代”的产品，一时间，国内宣称要做可穿戴设备的公司，如过江之鲫。2013 年，不说句硬件创新，不戴款智能手环，都不好意思说自己是站在人文与科技的十字路口。2013 年，身边的朋友纷纷佩戴上了 Jawbone，幸运的人也会戴上传说中的智能手表。不过，现在越来越多的朋友开始放弃这些所谓的可穿戴式设备。";
        PushPayload payload = PushPayload.simpleMessageAll(msgContent);
        String content = payload.toString();
        
        byte[] bytes = content.getBytes();
        System.out.println("len: " + bytes.length);
        PushResult result = _client.sendPush(payload);
        assertEquals(TOO_BIG, result.getErrorCode());
    }

    // ---------------- invalid params
    
    @Test
    public void invalidParams_platform() {
        JsonObject payload = new JsonObject();
        payload.add("platform", new JsonPrimitive("all_platform"));
        payload.add("audience", Audience.all().toJSON());
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        PushResult result = _client.sendPush(payload.toString());
        assertEquals(INVALID_PARAMS, result.getErrorCode());
    }

    @Test
    public void invalidParams_audience() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", new JsonPrimitive("all_audience"));
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        PushResult result = _client.sendPush(payload.toString());
        assertEquals(INVALID_PARAMS, result.getErrorCode());
    }

    @Test
    public void invalidParams_notification() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        payload.add("notification", new JsonPrimitive(ALERT));
        System.out.println("json string: " + payload.toString());
        
        PushResult result = _client.sendPush(payload.toString());
        assertEquals(INVALID_PARAMS, result.getErrorCode());
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
        
        PushResult result = _client.sendPush(payload.toString());
        assertEquals(INVALID_PARAMS, result.getErrorCode());
    }
    
    @Test
    public void invalidParams_notification_android_empty() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject android = new JsonObject();
        
        notification.add("android", android);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        PushResult result = _client.sendPush(payload.toString());
        assertEquals(INVALID_PARAMS, result.getErrorCode());
    }
    
    @Test
    public void invalidParams_notification_android_noalert() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject android = new JsonObject();
        android.add("title", new JsonPrimitive("title"));
        
        notification.add("android", android);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        PushResult result = _client.sendPush(payload.toString());
        assertEquals(INVALID_PARAMS, result.getErrorCode());
    }
    
    @Test
    public void invalidParams_notification_android_builderidNotNumber() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject android = new JsonObject();
        android.add("alert", new JsonPrimitive(11111));
        
        notification.add("android", android);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        PushResult result = _client.sendPush(payload.toString());
        assertEquals(INVALID_PARAMS, result.getErrorCode());
    }
    
    
    // ------------------------ lack of params

    @Test
    public void lackOfParams_platform() {
        JsonObject payload = new JsonObject();
        payload.add("audience", Audience.all().toJSON());
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        PushResult result = _client.sendPush(payload.toString());
        assertEquals(LACK_OF_PARAMS, result.getErrorCode());
    }
    
    @Test
    public void lackOfParams_audience() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        PushResult result = _client.sendPush(payload.toString());
        assertEquals(LACK_OF_PARAMS, result.getErrorCode());
    }
    
    @Test
    public void lackOfParams_messageAndNotificaiton() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        System.out.println("json string: " + payload.toString());
        
        PushResult result = _client.sendPush(payload.toString());
        assertEquals(LACK_OF_PARAMS, result.getErrorCode());
    }

	
}

