package cn.jpush.api.push.model.notification;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;


/**
 * <p><b>QuickApp 通知类</b></p>
 * <br>
 * 具体使用方法请参考官方文档 https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#platform
 * 支持 QuickApp Notification 的参数：
 * <ul>
 * <li>alert: 继承自父类 PlatformNotification 的 alert 属性；本类设置则覆盖。</li>
 * <li>title: 支持 setTitle(string) 方法来设置；可替换快应用推送通知的标题。 </li>
 * <li>page: 支持 setPage(String) 方法来设置；可设置快应用跳转页面</li>
 * <li>extras: 继承自父类 PlatformNotification 的 extras 属性；支持通过 addExtra(key, value) 来添加自定义字段，具体看代码。 </li>
 * </ul>
 * <br>
 */
public class QuickappNotification extends PlatformNotification {
    private static final String NOTIFICATION_QUICKAPP = "quickapp";

    private static final String TITLE = "title";
    private static final String PAGE = "page";

    private final String title;
    private final String page;

    private QuickappNotification(Object alert, String title, String page,
                                 Map<String, String> extras,
                                 Map<String, Number> numberExtras,
                                 Map<String, Boolean> booleanExtras,
                                 Map<String, JsonObject> jsonExtras,
                                 Map<String, JsonPrimitive> customData) {
        super(alert, extras, numberExtras, booleanExtras, jsonExtras, customData);

        this.title = title;
        this.page = page;
    }

    public static Builder newBuilder() {
        return new QuickappNotification.Builder();
    }

    public static QuickappNotification alert(String alert) {
        return newBuilder().setAlert(alert).build();
    }

    @Override
    public String getPlatform() {
        return NOTIFICATION_QUICKAPP;
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = super.toJSON().getAsJsonObject();

        if (null != title) {
            json.add(TITLE, new JsonPrimitive(title));
        }
        if (null != page) {
            json.add(PAGE, new JsonPrimitive(page));
        }

        return json;
    }

    public static class Builder extends PlatformNotification.Builder<QuickappNotification, Builder> {
        private String title;
        private String page;

        @Override
        protected Builder getThis() {
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPage(String page) {
            this.page = page;
            return this;
        }

        @Override
        public Builder setAlert(Object alert) {
            this.alert = alert;
            return this;
        }


        public QuickappNotification build() {
            return new QuickappNotification(alert, title, page,
                    extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder, jsonExtrasBuilder, super.customData);
        }
    }
}
