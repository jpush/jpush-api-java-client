package cn.jpush.api.push.model.notification;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

/**
 * <p><b>Android 通知类</b></p>
 * <br>
 * 具体使用方法请参考官方文档
 * 支持 Android Notification 的参数：
 * <ul>
 * <li>alert: 继承自父类 PlatformNotification 的 alert 属性；本类设置则覆盖。</li>
 * <li>title: 支持 setTitle(string) 方法来设置；可替换展示App名称的地方。 </li>
 * <li>builder_id: 支持 setBuilderId(int) 方法来设置。android 8.0 开始建议采用NotificationChannel配置。 </li>
 * <li>channel_id: 支持 setChannelId(string) 方法来设置；不超过1000字节。</li>
 * <li>priority: 支持 setPriority(int) 方法来设置。可改变通知栏展示优先级</li>
 * <li>category: 支持 setCategory(string) 方法来设置。</li>
 * <li>style: 支持 setStyle(int) 方法来设置；可改变通知栏样式类型。</li>
 * <li>alert_type: 支持 setAlertType(int) 方法来设置；可改变通知方式。</li>
 * <li>big_text: 支持 setBigText(string) 方法来设置；可改变大文本通知栏样式。 </li>
 * <li>inbox: 支持 setInbox(JSONObject) 方法来设置；可改变文本条目通知栏样式。</li>
 * <li>big_pic_path: 支持 setBigPicPath(string) 方法来设置；可改变大图片通知栏样式。 </li>
 * <li>extras: 继承自父类 PlatformNotification 的 extras 属性；支持通过 addExtra(key, value) 来添加自定义字段，具体看代码。 </li>
 * <li>large_icon: 支持 setLargeIcon(string) 方法来设置；可设置通知栏大图标。</li>
 * <li>small_icon_uri: 支持 setSmallIconUri(string) 方法来设置；可设置通知栏小图标。</li>
 * <li>intent: 支持 setIntent(JSONObject) 方法来设置； 可指定跳转页面。</li>
 * <li>uri_activity: 支持 setUriActivity(string) 方法来设置; 可指定跳转页面。</li>
 * <li>uri_action: 支持 setUriAction(string) 方法来设置；可指定跳转页面。</li>
 * <li>badge_add_num: 支持 setBadgeAddNum(int) 方法来设置；可指定角标数字增加</li>
 * <li>badge_class: 支持 setBadgeClass(string) 方法来设置；配合badge_add_num使用，二者需要共存，缺一不可。</li>
 * <li>sound: 支持 setSound(string) 方法来设置声音文件；填写文件名称即可，无需文件名后缀。</li>
 * <li>show_begin_time: 支持 setShowBeginTime(string) 方法来设置；可定时展示开始时间。</li>
 * <li>show_end_time: 支持 setShowEndTime(string) 方法来设置；可定时展示结束时间。</li>
 * <li>display_foreground: 支持 setDisplayForeground(string) 方法来设置； 可设置app在前台，通知是否展示。</li>
 ** </ul>
 * <br>
 */
public class AndroidNotification extends PlatformNotification {
    public static final String NOTIFICATION_ANDROID = "android";

    private static final String TITLE = "title";
    private static final String BUILDER_ID = "builder_id";
    private static final String CHANNEL_ID = "channel_id";
    private static final String PRIORITY = "priority";
    private static final String CATEGORY = "category";
    private static final String STYLE = "style";
    private static final String ALERT_TYPE = "alert_type";
    private static final String BIG_TEXT = "big_text";
    private static final String INBOX = "inbox";
    private static final String BIG_PIC_PATH = "big_pic_path";
    private static final String LARGE_ICON = "large_icon";
    private static final String SMALL_ICON_URI = "small_icon_uri";
    private static final String INTENT = "intent";
    private static final String URI_ACTIVITY = "uri_activity";
    private static final String URI_ACTION = "uri_action";
    private static final String BADGE_ADD_NUM = "badge_add_num";
    private static final String BADGE_CLASS = "badge_class";
    private static final String SOUND = "sound";
    private static final String SHOW_BEGIN_TIME = "show_begin_time";
    private static final String SHOW_END_TIME = "show_end_time";
    private static final String DISPLAY_FOREGROUND = "display_foreground";

    private final String title;
    private final int builderId;
    private String channelId;
    // range from [-2 ~ 2], default is 0
    private int priority;
    private String category;
    // range from [0 ~ 3], default is 0. bigText=1, Inbox=2, bigPicture=3.
    private int style = 0;
    // range from [-1 ~ 7], default is -1
    private int alert_type;
    private String big_text;
    private Object inbox;
    private String big_pic_path;
    private String large_icon;
    private String small_icon_uri;
    private JsonObject intent;
    private String uri_activity;
    private String uri_action;
    // range from [1 ~ 99], suggest set to 1
    private int badge_add_num;
    private String badge_class;
    private String sound;
    private String show_begin_time;
    private String show_end_time;
    private String display_foreground;

    private AndroidNotification(Object alert,
                                String title,
                                int builderId,
                                String channelId,
                                int priority,
                                String category,
                                int style,
                                int alertType,
                                String bigText,
                                Object inbox,
                                String bigPicPath,
                                String large_icon,
                                String small_icon_uri,
                                JsonObject intent,
                                String uri_activity,
                                String uri_action,
                                int badge_add_num,
                                String badge_class,
                                String sound,
                                String show_begin_time,
                                String show_end_time,
                                String display_foreground,
                                Map<String, String> extras,
                                Map<String, Number> numberExtras,
                                Map<String, Boolean> booleanExtras,
                                Map<String, JsonObject> jsonExtras,
                                Map<String, JsonPrimitive> customData) {
        super(alert, extras, numberExtras, booleanExtras, jsonExtras, customData);

        this.title = title;
        this.builderId = builderId;
        this.channelId = channelId;
        this.priority = priority;
        this.category = category;
        this.style = style;
        this.alert_type = alertType;
        this.big_text = bigText;
        this.inbox = inbox;
        this.big_pic_path = bigPicPath;
        this.large_icon = large_icon;
        this.small_icon_uri = small_icon_uri;
        this.intent = intent;
        this.uri_activity = uri_activity;
        this.uri_action = uri_action;
        this.badge_add_num = badge_add_num;
        this.badge_class = badge_class;
        this.sound = sound;
        this.show_begin_time = show_begin_time;
        this.show_end_time = show_end_time;
        this.display_foreground = display_foreground;

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static AndroidNotification alert(String alert) {
        return newBuilder().setAlert(alert).build();
    }

    @Override
    public String getPlatform() {
        return NOTIFICATION_ANDROID;
    }

    protected Object getInbox() {
        return this.inbox;
    }

    protected void setInbox(Object inbox) {
        this.inbox = inbox;
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = super.toJSON().getAsJsonObject();

        if (null != title) {
            json.add(TITLE, new JsonPrimitive(title));
        }

        if (builderId > 0) {
            json.add(BUILDER_ID, new JsonPrimitive(this.builderId));
        }

        if (null != channelId) {
            json.add(CHANNEL_ID, new JsonPrimitive(channelId));
        }

        // 默认为 0
        if (0 != priority) {
            json.add(PRIORITY, new JsonPrimitive(priority));
        }

        if (null != category) {
            json.add(CATEGORY, new JsonPrimitive(category));
        }

        // 默认是 0
        if (0 != style) {
            json.add(STYLE, new JsonPrimitive(this.style));
        }

        if (-1 != alert_type && alert_type <= 7) {
            json.add(ALERT_TYPE, new JsonPrimitive(this.alert_type));
        }

        if (null != big_text) {
            json.add(BIG_TEXT, new JsonPrimitive(this.big_text));
        }

        if (null != inbox) {
            if (inbox instanceof JsonObject) {
                json.add(INBOX, (JsonObject) inbox);
            }
        }

        if (null != big_pic_path) {
            json.add(BIG_PIC_PATH, new JsonPrimitive(this.big_pic_path));
        }

        if (null != large_icon) {
            json.add(LARGE_ICON, new JsonPrimitive(this.large_icon));
        }

        if (null != small_icon_uri) {
            json.add(SMALL_ICON_URI, new JsonPrimitive(this.small_icon_uri));
        }

        if (null != intent) {
            json.add(INTENT, intent);
        }

        if (null != uri_activity) {
            json.add(URI_ACTIVITY, new JsonPrimitive(this.uri_activity));
        }

        if (null != uri_action) {
            json.add(URI_ACTION, new JsonPrimitive(this.uri_action));
        }

        // 如果不填写，表示不改变角标数字
        if (0 != badge_add_num) {
            json.add(BADGE_ADD_NUM, new JsonPrimitive(this.badge_add_num));
        }

        if (null != badge_class) {
            json.add(BADGE_CLASS, new JsonPrimitive(this.badge_class));
        }

        if (null != sound) {
            json.add(SOUND, new JsonPrimitive(this.sound));
        }

        if (null != show_begin_time) {
            json.add(SHOW_BEGIN_TIME, new JsonPrimitive(this.show_begin_time));
        }

        if (null != show_end_time) {
            json.add(SHOW_END_TIME, new JsonPrimitive(this.show_end_time));
        }

        if (null != display_foreground) {
            json.add(DISPLAY_FOREGROUND, new JsonPrimitive(this.display_foreground));
        }


        return json;
    }

    public static class Builder extends PlatformNotification.Builder<AndroidNotification, Builder> {
        private String title;
        private int builderId;
        private String channelId;
        private int priority;
        private String category;
        private int style = 0;
        private int alert_type = -1;
        private String big_text;
        private Object inbox;
        private String big_pic_path;
        private String large_icon;
        private String small_icon_uri;
        private JsonObject intent;
        private String uri_activity;
        private String uri_action;
        private int badge_add_num;
        private String badge_class;
        private String sound;
        private String show_begin_time;
        private String show_end_time;
        private String display_foreground;

        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public Builder setAlert(Object alert) {
            this.alert = alert;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBuilderId(int builderId) {
            this.builderId = builderId;
            return this;
        }

        public String getChannelId() {
            return channelId;
        }

        public Builder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setStyle(int style) {
            this.style = style;
            return this;
        }

        public Builder setAlertType(int alertType) {
            this.alert_type = alertType;
            return this;
        }

        public Builder setBigText(String bigText) {
            this.big_text = bigText;
            return this;
        }

        public Builder setInbox(Object inbox) {
            if (null == inbox) {
                LOG.warn("Null inbox. Throw away it.");
                return this;
            }
            this.inbox = inbox;
            return this;
        }

        public Builder setBigPicPath(String bigPicPath) {
            this.big_pic_path = bigPicPath;
            return this;
        }

        public Builder setLargeIcon(String largeIcon) {
            this.large_icon = largeIcon;
            return this;
        }

        public Builder setSmallIconUri(String smallIconUri) {
            this.small_icon_uri = smallIconUri;
            return this;
        }

        public Builder setIntent(JsonObject intent) {
            if (null == intent) {
                LOG.warn("Null intent. Throw away it.");
                return this;
            }
            this.intent = intent;
            return this;
        }

        public Builder setUriActivity(String uriActivity) {
            this.uri_activity = uriActivity;
            return this;
        }

        public Builder setUriAction(String uriAction) {
            this.uri_action = uriAction;
            return this;
        }

        public Builder setBadgeAddNum(int badgeAddNum) {
            this.badge_add_num = badgeAddNum;
            return this;
        }

        public Builder setBadgeClass(String badgeClass) {
            this.badge_class = badgeClass;
            return this;
        }

        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        public Builder setShowBeginTime(String showBeginTime) {
            this.show_begin_time = showBeginTime;
            return this;
        }

        public Builder setShowEndTime(String showEndTime) {
            this.show_end_time = showEndTime;
            return this;
        }

        public Builder setDisplayForeground(String displayForeground) {
            this.display_foreground = displayForeground;
            return this;
        }


        @Override
        public AndroidNotification build() {
            return new AndroidNotification(
                    alert,
                    title,
                    builderId,
                    channelId,
                    priority,
                    category,
                    style,
                    alert_type,
                    big_text,
                    inbox,
                    big_pic_path,
                    large_icon,
                    small_icon_uri,
                    intent,
                    uri_activity,
                    uri_action,
                    badge_add_num,
                    badge_class,
                    sound,
                    show_begin_time,
                    show_end_time,
                    display_foreground,
                    extrasBuilder,
                    numberExtrasBuilder,
                    booleanExtrasBuilder,
                    jsonExtrasBuilder,
                    super.customData
            );
        }
    }
}
