package cn.jpush.api.push.model;

import org.junit.Assert;
import org.junit.Test;

import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class PushPayloadTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal_OnlyAudience() {
        Audience audience = Audience.all();
        PushPayload.newBuilder().setAudience(audience).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal_OnlyPlatform() {
        Platform platform = Platform.all();
        PushPayload.newBuilder().setPlatform(platform).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal_PlatformAudience() {
        Platform platform = Platform.all();
        Audience audience = Audience.all();
        PushPayload.newBuilder().setPlatform(platform).setAudience(audience).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegal_NoAudience() {
        Platform platform = Platform.all();
        Notification notifcation = Notification.alert("alert");
        PushPayload.newBuilder().setPlatform(platform).setNotification(notifcation).build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIllegal_NoPlatform() {
        Audience audience = Audience.all();
        Notification notifcation = Notification.alert("alert");
        PushPayload.newBuilder()
            .setAudience(audience)
            .setNotification(notifcation).build();
    }
            
    @Test
    public void testNotification() {
        int sendno = ServiceHelper.generateSendno();
        Notification notifcation = Notification.alert("alert");
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setOptions(Options.sendno(sendno))
                .setNotification(notifcation).build();
        
        JsonObject json = new JsonObject();
        json.add("audience", new JsonPrimitive("all"));
        json.add("platform", new JsonPrimitive("all"));
        
        JsonObject noti = new JsonObject();
        noti.add("alert", new JsonPrimitive("alert"));
        json.add("notification", noti);
        
        JsonObject options = new JsonObject();
        options.add("sendno", new JsonPrimitive(sendno));
        options.add("apns_production", new JsonPrimitive(false));
        json.add("options", options);
        
        Assert.assertEquals("", json, payload.toJSON());
    }

    @Test
    public void testMessage() {
        int sendno = ServiceHelper.generateSendno();
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setOptions(Options.sendno(sendno))
                .setMessage(Message.content("message")).build();
        
        JsonObject json = new JsonObject();
        json.add("audience", new JsonPrimitive("all"));
        json.add("platform", new JsonPrimitive("all"));
        
        JsonObject msg = new JsonObject();
        msg.add("msg_content", new JsonPrimitive("message"));
        json.add("message", msg);
        
        JsonObject options = new JsonObject();
        options.add("sendno", new JsonPrimitive(sendno));
        options.add("apns_production", new JsonPrimitive(false));
        json.add("options", options);
        
        Assert.assertEquals("", json, payload.toJSON());
    }
    
    @Test
    public void testGlobalExceed() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setMessage(Message.content(LONG_TEXT_2))
                .build();
        Assert.assertTrue("Should exceed", payload.isGlobalExceedLength());
    }

    @Test
    public void testIosExceed() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(LONG_TEXT_1))
                .build();
        Assert.assertTrue("Should exceed", payload.isIosExceedLength());
    }

    @Test
    public void testIosExceed2() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder().addPlatformNotification(
                        IosNotification.alert(LONG_TEXT_1)).build())
                .build();
        Assert.assertTrue("Should exceed", payload.isIosExceedLength());
    }

    
    
    
    private static final String LONG_TEXT_1 = ""
            + "极光推送，使得开发者可以即时地向其应用程序的用户推送通知或者消息，"
            + "与用户保持互动，从而有效地提高留存率，提升用户体验。平台提供整合了Android推送、iOS推送的统一推送服务。";
    private static final String LONG_TEXT_2 = ""
            + "通过极光推送服务，主动、及时地向您的用户发起交互，向其推送聊天消息、日程提醒、活动预告、进度提示、动态更新等。"
            + "精准的目标用户和有价值的推送内容可以提升用户忠诚度，提高留存率与收入。"
            + "客户端 SDK 采用自定义的协议保持长连接，电量、流量消耗都很少。 "
            + "服务端先进技术架构，高并发可扩展性的云服务，经受过几亿用户的考验，"
            + "完全省去应用开发者自己维护长连接的设备和人力的成本投入。"
            + "简单的SDK集成方式，使开发商可以快速部署，更专注主营业务。灵活的推送入接入，"
            + "同时支持网站上直接推送，也提供 消息推送和送达统计的 API调用。 "
            + "清晰的统计图表，直观的跟踪推送带来的效果。"
            + "下载并集成 SDK 接入极光推送服务。极光推送提供了 Android，iOS，Windows Phone以及 PhoneGap 的客户端 SDK。"
            + "同时也开放多种语言实现的服务端 SDK，方便开发者调用 API 进行推送。"
            + "本 Wiki 是极光推送 (JPush) 产品的开发者文档网站。"
            + "极光推送所有技术文档都在本 Wiki 里，没有别的提供渠道。同时，我们也在不断地补充、完善文档。"
            + "这些文档包括这样几种类型：常见问题、入门指南、API定义、教程等。";
}


