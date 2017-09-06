package cn.jpush.api.push.remote;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.SlowTests;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

@Category(SlowTests.class)
public class ExceptionTest extends BaseRemotePushTest {
    
    @Test
    public void appKeyNotExist() {
        String appKey = "dd1066407b044738b6479274";
        JPushClient client = new JPushClient(MASTER_SECRET, appKey);
        PushPayload payload = PushPayload.alertAll(ALERT);
        
        try {
            client.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(AUTHENTICATION_FAIL, e.getErrorCode());
        }
    }

    @Test
    public void authenticationFail() {
        String masterSecret = "2b38ce69b1de2a7fa95706e2";
        JPushClient client = new JPushClient(masterSecret, APP_KEY);
        PushPayload payload = PushPayload.alertAll(ALERT);
        try {
            client.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(AUTHENTICATION_FAIL, e.getErrorCode());
        }
    }

    @Test
    public void tooBig() {
        String msgContent = "深圳制造厂的朋友告诉我，过去的一年，他们服务了几十家小型创业公司，代工智能手表。不过，今年这些创业公司已经找不到了，庆幸的是，代工厂都是先付款再生产，也就没有损失。可穿戴设备、硬件创新，大潮初起，泥沙俱下，浪潮过后，却是遍地狼藉。国内的智能手环、手表们，如土曼、果壳，在 Jawbone、Google Glass 们引领下，纷纷推出“划时代”的产品，一时间，国内宣称要做可穿戴设备的公司，如过江之鲫。2013 年，不说句硬件创新，不戴款智能手环，都不好意思说自己是站在人文与科技的十字路口。2013 年，身边的朋友纷纷佩戴上了 Jawbone，幸运的人也会戴上传说中的智能手表。不过，现在越来越多的朋友开始放弃这些所谓的可穿戴式设备。";
        PushPayload payload = PushPayload.messageAll(msgContent);
        String content = payload.toString();
        
        byte[] bytes = content.getBytes();
        System.out.println("len: " + bytes.length);
        try {
            _client.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(TOO_BIG, e.getErrorCode());
        }
    }

    // ---------------- invalid params
    
    @Test
    public void invalidParams_platform() {
        JsonObject payload = new JsonObject();
        payload.add("platform", new JsonPrimitive("all_platform"));
        payload.add("audience", Audience.all().toJSON());
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
    }

    @Test
    public void invalidParams_audience() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", new JsonPrimitive("all_audience"));
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
    }

    @Test
    public void invalidParams_notification() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        payload.add("notification", new JsonPrimitive(ALERT));
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
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
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
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
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
    }
    /*
    @Test
    public void invalidParams_notification_winphone() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        notification.add("winphone", new JsonPrimitive(ALERT));
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
    }
    */
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
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
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
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
    }
    
    @Test
    public void invalidParams_notification_ios_empty() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject ios = new JsonObject();
        
        notification.add("ios", ios);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
    }
    
    @Test
    public void invalidParams_notification_winphone_empty() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject winphone = new JsonObject();
        
        notification.add("winphone", winphone);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(LACK_OF_PARAMS, e.getErrorCode());
        }
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
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
    }
    
    @Test
    public void invalidParams_notification_ios_noalert() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject ios = new JsonObject();
        ios.add("badge", new JsonPrimitive(11));
        
        notification.add("ios", ios);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
    }
    
    @Test
    public void invalidParams_notification_winphone_noalert() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject notification = new JsonObject();
        JsonObject winphone = new JsonObject();
        winphone.add("title", new JsonPrimitive("title"));
        
        notification.add("winphone", winphone);
        payload.add("notification", notification);
        
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(INVALID_PARAMS, e.getErrorCode());
        }
    }
    
    
    // ------------------------ lack of params

    @Test
    public void lackOfParams_platform() {
        JsonObject payload = new JsonObject();
        payload.add("audience", Audience.all().toJSON());
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(LACK_OF_PARAMS, e.getErrorCode());
        }
    }
    
    @Test
    public void lackOfParams_audience() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("notification", Notification.alert(ALERT).toJSON());
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(LACK_OF_PARAMS, e.getErrorCode());
        }
    }
    
    @Test
    public void lackOfParams_messageAndNotificaiton() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(LACK_OF_PARAMS, e.getErrorCode());
        }
    }


    @Test
    public void lackOfParams_message_noMsgContent() {
        JsonObject payload = new JsonObject();
        payload.add("platform", Platform.all().toJSON());
        payload.add("audience", Audience.all().toJSON());
        
        JsonObject message = new JsonObject();
        message.add("title", new JsonPrimitive("title"));
        
        payload.add("message", message);
        
        System.out.println("json string: " + payload.toString());
        
        try {
            _client.sendPush(payload.toString());
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            assertEquals(LACK_OF_PARAMS, e.getErrorCode());
        }
    }
    

	
}

