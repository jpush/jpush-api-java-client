package cn.jpush.api.push.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.ServiceHelper;
import cn.jpush.api.FastTests;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

@Category(FastTests.class)
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
    	System.out.println("Size: " + LONG_TEXT_2.getBytes().length);
        Assert.assertFalse("Shouldn't exceed - " + LONG_TEXT_2.getBytes().length, 
        		payload.isGlobalExceedLength());
    }
    
    @Test
    public void testGlobalExeed2() {
    	PushPayload payload = PushPayload.newBuilder()
    			.setPlatform(Platform.all())
    			.setAudience(Audience.all())
    			.setMessage(Message.content(LONG_TEXT_3))
    			.build();
    	System.out.println("Size: " + LONG_TEXT_3.getBytes().length);
    	Assert.assertTrue("Should exeed - " + LONG_TEXT_3.getBytes().length,
    			payload.isGlobalExceedLength());
    }
    
    @Test
    public void testIosExceed() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(LONG_TEXT_1))
                .build();
    	System.out.println("Size: " + LONG_TEXT_1.getBytes().length);
        Assert.assertFalse("Shouldn't exceed - " + LONG_TEXT_1.getBytes().length, 
        		payload.isIosExceedLength());
    }

    @Test
    public void testIosExceed2() {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder().addPlatformNotification(
                        IosNotification.alert(LONG_TEXT_3)).build())
                .build();
    	System.out.println("Size: " + LONG_TEXT_3.getBytes().length);
        Assert.assertTrue("Should exceed - " + LONG_TEXT_3.getBytes().length, 
        		payload.isIosExceedLength());
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
    private static final String LONG_TEXT_3 = ""
    		+ "这是 Push API 最近的版本。"
    		+ "相比于 API v2 版本，v3 版本的改进为："
    		+ "完全基于 https，不再提供 http 访问；"
    		+ "使用 HTTP Basic Authentication 的方式做访问授权。这样整个 API 请求可以使用常见的 HTTP 工具来完成，比如：curl，浏览器插件等；"
    		+ "推送内容完全使用 JSON 的格式；"
    		+ "支持的功能有所改进：支持多 tag 的与或操作；可单独发送通知或者自定义消息，也可同时推送通知与自定义消息；windows phone 目前只有通知。"
    		+ "向某单个设备或者某设备列表推送一条通知、或者消息。"
    		+ "推送的内容只能是 JSON 表示的一个推送对象。"
    		+ "调用地址：POST https://api.jpush.cn/v3/push"
    		+ "别名与标签使用教程 为什么需要别名与标签"
    		+ "推送消息时，要指定推送的对象：全部，某一个人，或者某一群人。"
    		+ "全部很好办，针对某应用“群发”就好了。Portal与API都支持向指定的 appKey 群发消息。"
    		+ "要指定向某一个特定的人，或者某一群特定的人，则相对复杂。"
    		+ "因为对于 JPush 来说，某一个人就是一个注册ID，这个注册ID与开发者App没有任何关系，或者说对开发者App是没有意义的。"
    		+ "如果要对开发者App有意义的某个特定的用户推送消息，则需要：把 JPush 注册用户与开发者App 用户绑定起来。"
    		+ "这个绑定有两个基本思路："
    		+ "把绑定关系保存到 JPush 服务器端"
    		+ "把绑定关系保存到开发者应用服务器中"
    		+ "前者，就是这里要说到的：别名与标签的功能。这个机制简单易用，适用于大多数开发者。"
    		+ "后者，则是 JPush 提供的另外一套 RegistrationID 机制。这套机制开发者需要有应用服务器来维护绑定关系，不适用于普通开发者。"
    		+ "Android SDK r1.6.0 版本开始支持。"
    		+ "别名与标签的机制，其工作方式是："
    		+ "客户端开发者App调用 setAliasAndTags API 来设置关系"
    		+ "JPush SDK 把该关系设置保存到 JPush Server 上"
    		+ "在服务器端推送消息时，指定向之前设置过的别名或者标签推送"
    		+ "SDK 支持的 setAliasAndTags 请参考相应的文档：别名与标签 API"
    		+ "使用过程中有几个点做特别说明："
    		+ "App 调用 SDK setAliasAndTags API 时，r1.5.0 版本提供了 Callback 来返回设置状态。如果返回 6002 （超时）则建议重试"
    		+ "老版本没有提供 Callback 无设置状态返回，从而没有机制确定一定成功。建议升级到新版本"
    		+ "Portal 上推送或者 API 调用向别名或者标签推送时，可能会报错：不存在推送目标用户。"
    		+ "该报错表明，JPush Server 上还没有针对你所推送的别名或者标签的用户绑定关系，所以没有推送目标。"
    		+ "这时请开发者检查确认，开发者App是否正确地调用了 setAliasAndTags API，以及调用时是否网络不好，JPush SDK 暂时未能保存成功。"
    		+ "使用别名 用于给某特定用户推送消息。别名，可以近似地被认为，是用户帐号里的昵称。"
    		+ "使用标签 用于给某一群人推送消息。"
    		+ "标签类似于博客里为文章打上 tag ，即为某资源分类。"
    		+ "动态标签 JPush 提供的设置标签的 API 是在客户端的。"
    		+ "开发者如何做到在自己的服务器端动态去设置分组呢？ 比如一个企业OA系统，经常需要去变更部门人员分组。以下是大概的思路："
    		+ "设计一种自定义消息格式（业务协议），App解析后可以调用 JPush SDK setAliasAndTags API 来重新设置标签（分组）"
    		+ "例：{\"action\":\"resetTags\", \"newTags\":[\"dep_level_1\":\"A公司\", \"dep_level_2\":\"技术部\", \"dep_level_3\""
    		+ ":\"Android开发组\", \"address\":\"深圳\", \"lang\":\"zh\"]}"
    		+ "要动态设置分组时，推送这条自定义消息给指定的用户"
    		+ "使用别名的机制，推送到指定的用户。"
    		+ "客户端App 调用 JPush SDK API 来设置新的标签"
    		+ "通过极光推送服务，主动、及时地向您的用户发起交互，向其推送聊天消息、日程提醒、活动预告、进度提示、动态更新等。"
            + "精准的目标用户和有价值的推送内容可以提升用户忠诚度，提高留存率与收入。"
            + "客户端 SDK 采用自定义的协议保持长连接，电量、流量消耗都很少。 "
            + "服务端先进技术架构，高并发可扩展性的云服务，经受过几亿用户的考验，"
            + "完全省去应用开发者自己维护长连接的设备和人力的成本投入。"
            + "简单的SDK集成方式，使开发商可以快速部署，更专注主营业务。灵活的推送入接入，"
            + "同时支持网站上直接推送，也提供 消息推送和送达统计的 API调用。 ";
}


