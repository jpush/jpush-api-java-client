package cn.jpush.api.examples;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.CIDResult;
import cn.jpush.api.push.GroupPushClient;
import cn.jpush.api.push.GroupPushResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.*;
import cn.jpush.api.report.GroupMessageDetailResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class PushExample {
    protected static final Logger LOG = LoggerFactory.getLogger(PushExample.class);

    /**
     * Change the app key and master secret to your own account
     * If you want to use push by group, please enter your own group push key and group master secret.
     */
    protected static final String APP_KEY = "8f02a4fa717a6235734d92de";
    protected static final String MASTER_SECRET = "cf6de29f9e66432ba4ac1c32";
    protected static final String GROUP_PUSH_KEY = "2c88a01e073a0fe4fc7b167c";
    protected static final String GROUP_MASTER_SECRET = "b11314807507e2bcfdeebe2e";

    public static final String TITLE = "Test from API example";
    public static final String ALERT = "Test from API Example - alert";
    public static final String MSG_CONTENT = "Test from API Example - msgContent";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
    public static long sendCount = 0;
    private static long sendTotalTime = 0;

    public static void main(String[] args) {

        // 回调参数可参考下面方法
//        testSendPushWithCustom();
//        testSendPushWithCustomField();
//        testBatchSend();
//        testSendPushWithCustomConfig();
//        testSendIosAlert();

        // 目前推荐这个方法进行测试
        testSendPush();
//        testSendGroupPush();


//        testGetCidList();
//        testSendPushes();
//        testSendPush_fromJSON();
//        testSendPushWithCallback();
//		  testSendPushWithCid();
//       testSendWithSMS();
    }

    // 使用 NettyHttpClient 异步接口发送请求
    public static void testSendPushWithCallback() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = buildPushObject_all_alias_alert();
            client.sendRequest(HttpMethod.POST, payload.toString(), uri, new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    LOG.info("Got result: " + responseWrapper.responseContent);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void testSendPush() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
//        String authCode = ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET);
        // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use NativeHttpClient.

//        ApacheHttpClient httpClient = new ApacheHttpClient(authCode, null, clientConfig);
//        NettyHttpClient httpClient =new NettyHttpClient(authCode, null, clientConfig);
//        jpushClient.getPushClient().setHttpClient(httpClient);

        // For push, all you need do is to build PushPayload object.
        final PushPayload payload = buildPushObject_android_and_ios();

//        PushPayload payload = buildPushObject_all_alias_alert();
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            System.out.println(result);
            // 如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
            // If uses NettyHttpClient, call close when finished sending request, otherwise process will not exit.
            // jpushClient.close();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }

    public static void testSendPushWithEncrypt() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        clientConfig.setEncryptType(EncryptKeys.ENCRYPT_SMS2_TYPE);
        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
//        String authCode = ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET);
        // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use NativeHttpClient.

//        ApacheHttpClient httpClient = new ApacheHttpClient(authCode, null, clientConfig);
//        NettyHttpClient httpClient =new NettyHttpClient(authCode, null, clientConfig);
//        jpushClient.getPushClient().setHttpClient(httpClient);
        final PushPayload payload = buildPushObject_android_and_ios();
//        // For push, all you need do is to build PushPayload object.
//        PushPayload payload = buildPushObject_all_alias_alert();
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            System.out.println(result);
            // 如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
            // If uses NettyHttpClient, call close when finished sending request, otherwise process will not exit.
            // jpushClient.close();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }

    //use String to build PushPayload instance
    public static void testSendPush_fromJSON() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlatformNotification.class, new InterfaceAdapter<PlatformNotification>())
                .create();
        // Since the type of DeviceType is enum, thus the value should be uppercase, same with the AudienceType.
        String payloadString = "{\"platform\":{\"all\":false,\"deviceTypes\":[\"IOS\"]},\"audience\":{\"all\":true,\"targets\":[{\"audienceType\":\"TAG_AND\",\"values\":[\"tag1\",\"tag_all\"]}]},\"notification\":{\"notifications\":[{\"soundDisabled\":false,\"badgeDisabled\":false,\"sound\":\"happy\",\"badge\":\"5\",\"contentAvailable\":false,\"alert\":\"Test from API Example - alert\",\"extras\":{\"from\":\"JPush\"},\"type\":\"cn.jpush.api.push.model.notification.IosNotification\"}]},\"message\":{\"msgContent\":\"Test from API Example - msgContent\"},\"options\":{\"sendno\":1429488213,\"overrideMsgId\":0,\"timeToLive\":-1,\"apnsProduction\":true,\"bigPushDuration\":0}}";
        PushPayload payload = gson.fromJson(payloadString, PushPayload.class);
        try {
            PushResult result = jpushClient.sendPush(payloadString);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            // LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            //LOG.error("Sendno: " + payload.getSendno());
        }
    }

    /**
     * 测试多线程发送 2000 条推送耗时
     */
    public static void testSendPushes() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET);
        // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use NativeHttpClient.
//        ApacheHttpClient httpClient = new ApacheHttpClient(authCode, null, clientConfig);
        jpushClient.getPushClient().setHttpClient(httpClient);
        final PushPayload payload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread() {
                public void run() {
                    for (int j = 0; j < 200; j++) {
                        long start = System.currentTimeMillis();
                        try {
                            PushResult result = jpushClient.sendPush(payload);
                            LOG.info("Got result - " + result);

                        } catch (APIConnectionException e) {
                            LOG.error("Connection error. Should retry later. ", e);
                            LOG.error("Sendno: " + payload.getSendno());

                        } catch (APIRequestException e) {
                            LOG.error("Error response from JPush server. Should review and fix it. ", e);
                            LOG.info("HTTP Status: " + e.getStatus());
                            LOG.info("Error Code: " + e.getErrorCode());
                            LOG.info("Error Message: " + e.getErrorMessage());
                            LOG.info("Msg ID: " + e.getMsgId());
                            LOG.error("Sendno: " + payload.getSendno());
                        }

                        System.out.println("耗时" + (System.currentTimeMillis() - start) + "毫秒 sendCount:" + (++sendCount));
                    }
                }
            };
            thread.start();
        }
    }

    public static void testSendGroupPush() {
        GroupPushClient groupPushClient = new GroupPushClient(GROUP_MASTER_SECRET, GROUP_PUSH_KEY);
        final PushPayload payload = buildPushObject_android_and_ios();
        try {
            GroupPushResult groupPushResult = groupPushClient.sendGroupPush(payload);
            Map<String, PushResult> result = groupPushResult.getAppResultMap();
            for (Map.Entry<String, PushResult> entry : result.entrySet()) {
                PushResult pushResult = entry.getValue();
                PushResult.Error error = pushResult.error;
                if (error != null) {
                    LOG.info("Group_msgid: " + groupPushResult.getGroupMsgId() + " AppKey: " + entry.getKey() + " error code : " + error.getCode() + " error message: " + error.getMessage());
                } else {
                    LOG.info("Group_msgid: " + groupPushResult.getGroupMsgId() + " AppKey: " + entry.getKey() + " sendno: " + pushResult.sendno + " msg_id:" + pushResult.msg_id);
                }

            }
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }

    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(ALERT);
    }

    public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias("alias1"))
                .setNotification(Notification.alert(ALERT))
                .build();
    }

    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }

    /**
     * Could modify the contents for pushing
     * The comments are showing how to use it
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios() {
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("test", "https://community.jiguang.cn/push");
        // you can set anything you want in this builder, read the document to avoid collision.
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
//                .setMessage(Message.newBuilder()
//                        .setMsgContent("Hi, JPush")
//                        .build())
                .setNotification(Notification.newBuilder()
                        .setAlert("testing alert content")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("Android Title")
                                .addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())
//                .setSMS(SMS.newBuilder()
//                        .setDelayTime(1000)
//                        .setTempID(2000)
//                        .addPara("Test", 1)
//                        .setActiveFilter(true)
//                        .build())
//                .setNotification3rd(Notification3rd.newBuilder()
//                        .setContent("Hi, JPush")
//                        .setTitle("msg testing")
//                        .setChannelId("channel1001")
//                        .setUriActivity("cn.jpush.android.ui.OpenClickActivity")
//                        .setUriAction("cn.jpush.android.intent.CONNECTION")
//                        .setBadgeAddNum(1)
//                        .setBadgeClass("com.test.badge.MainActivity")
//                        .setSound("sound")
//                        .addExtra("news_id", 124)
//                        .addExtra("my_key", "a value")
//                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)
                        .setTimeToLive(43200)
                        .build())
                .build();
    }

    public static void buildPushObject_with_extra() {

        JsonObject jsonExtra = new JsonObject();
        jsonExtra.addProperty("extra1", 1);
        jsonExtra.addProperty("extra2", false);

        Map<String, String> extras = new HashMap<String, String>();
        extras.put("extra_1", "val1");
        extras.put("extra_2", "val2");

        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                        .setAlert("alert content")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("Android Title")
                                .addExtras(extras)
                                .addExtra("booleanExtra", false)
                                .addExtra("numberExtra", 1)
                                .addExtra("jsonExtra", jsonExtra)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())
                .build();

        System.out.println(payload.toJSON());
    }

    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        JsonObject sound = new JsonObject();
        sound.add("critical", new JsonPrimitive(1));
        sound.add("name", new JsonPrimitive("default"));
        sound.add("volume", new JsonPrimitive(0.2));
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setMutableContent(false)
//                                .setSound("happy")
                                .setSound(sound)
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                .setMessage(Message.content(MSG_CONTENT))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_android_newly_support() {

        JsonObject inbox = new JsonObject();
        inbox.add("line1", new JsonPrimitive("line1 string"));
        inbox.add("line2", new JsonPrimitive("line2 string"));
        inbox.add("contentTitle", new JsonPrimitive("title string"));
        inbox.add("summaryText", new JsonPrimitive("+3 more"));

        JsonObject intent = new JsonObject();
        intent.add("url", new JsonPrimitive("intent:#Intent;component=com.jiguang.push/com.example.jpushdemo.SettingActivity;end"));

        Notification notification = Notification.newBuilder()
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .setAlert(ALERT)
                        .setBigPicPath("path to big picture")
                        .setBigText("long text")
                        .setBuilderId(1)
                        .setCategory("CATEGORY_SOCIAL")
                        .setInbox(inbox)
                        .setStyle(1)
                        .setTitle("Alert test")
                        .setPriority(1)
                        .setLargeIcon("http://www.jiguang.cn/largeIcon.jpg")
                        .setIntent(intent)
                        .build())
                .build();
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(notification)
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .setSendno(ServiceHelper.generateSendno())
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(MSG_CONTENT)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_all_tag_not() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag_not("abc", "123"))
                .setNotification(Notification.alert(ALERT))
                .build();
    }

    public static PushPayload buildPushObject_android_cid() {
        Collection<String> list = new LinkedList<String>();
        list.add("1507bfd3f79558957de");
        list.add("1507bfd3f79554957de");
        list.add("1507bfd3f79555957de");
        list.add("1507bfd3f79556957de");
        list.add("1507ffd3f79545957de");
        list.add("1507ffd3f79457957de");
        list.add("1507ffd3f79456757de");
        list.add("zzzzzzzz");


        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
//                .setAudience(Audience.registrationId("1507bfd3f79558957de"))
                .setAudience(Audience.registrationId(list))
                .setNotification(Notification.alert(ALERT))
                .setCid("cid")
                .build();
    }

    public static void testSendPushWithCustomConfig() {
        ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
        config.setPushHostName("https://api.jpush.cn");

        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, config);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert();

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    public static void testSendIosAlert() {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);

        IosAlert alert = IosAlert.newBuilder()
                .setTitleAndBody("test alert", "subtitle", "test ios alert json")
                .setActionLocKey("PLAY")
                .build();
        try {
            PushResult result = jpushClient.sendIosNotificationWithAlias(alert, new HashMap<String, String>(), "alias1");
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testSendWithSMS() {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
//            SMS sms = SMS.content(1, 10);
            SMS sms = SMS.newBuilder()
                    .setDelayTime(1000)
                    .setTempID(2000)
                    .addPara("Test", 1)
                    .setActiveFilter(false)
                    .build();
            PushResult result = jpushClient.sendAndroidMessageWithAlias("Test SMS", "test sms", sms, "alias1");
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testGetCidList() {
        JPushClient jPushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            CIDResult result = jPushClient.getCidList(3, "push");
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testSendPushWithCid() {
        JPushClient jPushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        PushPayload pushPayload = buildPushObject_android_cid();
        try {
            PushResult result = jPushClient.sendPush(pushPayload);
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    /**
     * 批量单推接口
     * https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#vip
     */
    public static void testBatchSend() {

        JPushClient jPushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            {
                List<PushPayload> pushPayloadList = new ArrayList<>();
                PushPayload.Builder builder1 = PushPayload.newBuilder();
                builder1.setMessage(Message.content("content1 by alias"))
                        .setNotification(Notification.alert(ALERT))
                        .setPlatform(Platform.all())
                        .setAudience(Audience.all())
                        .setOptions(Options.sendno())
                        .setTarget("1507ffd3f79456757de");
                pushPayloadList.add(builder1.build());

                PushPayload.Builder builder2 = PushPayload.newBuilder();
                builder2.setMessage(Message.content("content2 by alias"))
                        .setNotification(Notification.alert(ALERT))
                        .setPlatform(Platform.android())
                        .setAudience(Audience.all())
                        .setOptions(Options.sendno())
                        .setTarget("1507ffd3f79456757de");
                pushPayloadList.add(builder2.build());

                BatchPushResult result = jPushClient.batchSendPushByAlias(pushPayloadList);
                LOG.info("batchSendPushByAlias param: {}, result: {}", pushPayloadList, new Gson().toJson(result.getBatchPushResult()));
            }

//            {
//                List<PushPayload> pushPayloadList = new ArrayList<>();
//                PushPayload.Builder builder1 = PushPayload.newBuilder();
//                builder1.setMessage(Message.content("content1 by regId"))
//                        .setNotification(Notification.alert(ALERT))
//                        .setPlatform(Platform.android())
//                        .setAudience(Audience.all())
//                        .setOptions(Options.sendno())
//                        .setTarget("1507ffd3f79456757de");
//                pushPayloadList.add(builder1.build());
//
//                PushPayload.Builder builder2 = PushPayload.newBuilder();
//                builder2.setMessage(Message.content("content2 by regId"))
//                        .setNotification(Notification.alert(ALERT))
//                        .setAudience(Audience.all())
//                        .setPlatform(Platform.ios())
//                        .setOptions(Options.sendno())
//                        .setTarget("1507ffd3f79456757de");
//                pushPayloadList.add(builder2.build());
//
//                BatchPushResult result = jPushClient.batchSendPushByRegId(pushPayloadList);
//                LOG.info("batchSendPushByRegId param: {}, result: {}", pushPayloadList, new Gson().toJson(result.getBatchPushResult()));
//            }

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    /**
     * 自定义发送参数名称, 华为客户可参考该方法
     */
    public static void testSendPushWithCustomField() {

        ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
        config.setPushHostName("https://api.jpush.cn");

        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, config);

        Notification notification = Notification.newBuilder()
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .setAlert(ALERT)
                        .setTitle("Alert test")
                        .setLargeIcon("http://www.jiguang.cn/largeIcon.jpg")
                        .addCustom("uri_activity", "uri_activity")
                        .addCustom("uri_flag", "uri_flag")
                        .addCustom("uri_action", "uri_action")
                        .build())
                .build();

        PushPayload.Builder payloadBuilder = new PushPayload.Builder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(notification);

        try {
            PushResult result = jpushClient.sendPush(payloadBuilder.build());
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    /**
     * 回调参数示例
     */
    public static void testSendPushWithCustom() {

        ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
        config.setPushHostName("https://api.jpush.cn");

        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, config);

        Notification notification = Notification.newBuilder()
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .setAlert(ALERT)
                        .setTitle("Alert test")
                        .build())
                .build();

        JsonObject callback = new JsonObject();
        callback.addProperty("url", "https://www.jiguagn.cn/callback");
        JsonObject params = new JsonObject();
        params.addProperty("name", "joe");
        params.addProperty("age", 26);
        callback.add("params", params);
        callback.addProperty("type", 3);

        PushPayload.Builder payloadBuilder = new PushPayload.Builder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(notification)
                .addCustom("callback", callback);

        try {
            PushResult result = jpushClient.sendPush(payloadBuilder.build());
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

}

