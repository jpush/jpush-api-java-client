package cn.jpush.api.push.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.Preconditions;

/**
 * 使用说明
 * notification_3rd 只针对开通了厂商通道的用户生效；
 * notification 和 notification_3rd 不能同时有内容，如果这两块同时有内容，则会返回错误提示；
 * notification_3rd 的内容对 iOS 和 WinPhone 平台无效，只针对 Android 平台生效；
 * notification_3rd 是用作补发厂商通知的内容，只有当 message 部分有内容，才允许传递此字段，且要两者都不为空时，才会对离线的厂商设备转发厂商通道的通知。
 */
public class Notification3rd implements PushModel{
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String CHANNEL_ID = "channel_id";
    private static final String URI_ACTIVITY = "uri_activity";
    private static final String URI_ACTION = "uri_action";
    private static final String BADGE_ADD_NUM = "badge_add_num";
    private static final String BADGE_SET_NUM = "badge_set_num";
    private static final String BADGE_CLASS = "badge_class";
    private static final String SOUND = "sound";
    private static final String EXTRAS = "extras";


    private final String title;
    private final String content;
    private final String channel_id;
    private final String uri_activity;
    private final String uri_action;
    private final int badge_add_num;
    private final int badge_set_num;
    private final String badge_class;
    private final String sound;
    private final Map<String, String> extras;
    private final Map<String, Number> numberExtras;
    private final Map<String, Boolean> booleanExtras;
    private final Map<String, JsonObject> jsonExtras;

    private Notification3rd(String title, String content, String channel_id,
                            String uri_activity, String uri_action, int badge_add_num,int badge_set_num,
                            String badge_class, String sound,
                            Map<String, String> extras,
                            Map<String, Number> numberExtras,
                            Map<String, Boolean> booleanExtras,
                            Map<String, JsonObject> jsonExtras) {
        this.title = title;
        this.content = content;
        this.channel_id = channel_id;
        this.uri_activity = uri_activity;
        this.uri_action = uri_action;
        this.badge_add_num = badge_add_num;
        this.badge_set_num = badge_set_num;
        this.badge_class = badge_class;
        this.sound = sound;
        this.extras = extras;
        this.numberExtras = numberExtras;
        this.booleanExtras = booleanExtras;
        this.jsonExtras = jsonExtras;
    }


    /**
     * The entrance for building a Notification3rd object.
     * @return Notification3rd builder
     */
    public static Builder newBuilder() { return new Builder(); }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();

        if (null != title) {
            json.addProperty(TITLE, title);
        }

        // 必填
        json.addProperty(CONTENT, content);

        if (null != channel_id) {
            json.addProperty(CHANNEL_ID, channel_id);
        }

        if (null != uri_activity) {
            json.addProperty(URI_ACTIVITY, uri_activity);
        }

        if (null != uri_action) {
            json.addProperty(URI_ACTION, uri_action);
        }

        if (0 != badge_add_num) {
            json.addProperty(BADGE_ADD_NUM, badge_add_num);
        }

        if (0 != badge_set_num) {
            json.addProperty(BADGE_SET_NUM, badge_set_num);
        }

        if (null != badge_class) {
            json.addProperty(BADGE_CLASS, badge_class);
        }

        if (null != sound) {
            json.addProperty(SOUND, sound);
        }

        /**
         * for adding extras into json
         */
        JsonObject extrasObject = null;
        if (null != extras || null != numberExtras || null != booleanExtras || null != jsonExtras) {
            extrasObject = new JsonObject();
        }

        if (null != extras) {
            String value = null;
            for (String key : extras.keySet()) {
                value = extras.get(key);
                if (null != value) {
                    extrasObject.add(key, new JsonPrimitive(value));
                }
            }
        }
        if (null != numberExtras) {
            Number value = null;
            for (String key : numberExtras.keySet()) {
                value = numberExtras.get(key);
                if (null != value) {
                    extrasObject.add(key, new JsonPrimitive(value));
                }
            }
        }
        if (null != booleanExtras) {
            Boolean value = null;
            for (String key : booleanExtras.keySet()) {
                value = booleanExtras.get(key);
                if (null != value) {
                    extrasObject.add(key, new JsonPrimitive(value));
                }
            }
        }
        if (null != jsonExtras) {
            JsonObject value = null;
            for (String key : jsonExtras.keySet()) {
                value = jsonExtras.get(key);
                if (null != value) {
                    extrasObject.add(key, value);
                }
            }
        }

        if (null != extras || null != numberExtras || null != booleanExtras || null != jsonExtras) {
            json.add(EXTRAS, extrasObject);
        }

        return json;
    }

    public static class Builder{
        private String title;
        private String content;
        private String channel_id;
        private String uri_activity;
        private String uri_action;
        private int badge_add_num;
        private int badge_set_num;
        private String badge_class;
        private String sound;
        protected Map<String, String> extrasBuilder;
        protected Map<String, Number> numberExtrasBuilder;
        protected Map<String, Boolean> booleanExtrasBuilder;
        protected Map<String, JsonObject> jsonExtrasBuilder;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setChannelId(String channel_id) {
            this.channel_id = channel_id;
            return this;
        }

        public Builder setUriActivity(String uri_activity) {
            this.uri_activity = uri_activity;
            return this;
        }

        public Builder setUriAction(String uri_action) {
            this.uri_action = uri_action;
            return this;
        }

        public Builder setBadgeAddNum(int badge_add_num) {
            this.badge_add_num = badge_add_num;
            return this;
        }

        public Builder setBadgeSetNum(int badge_set_num) {
            this.badge_set_num = badge_set_num;
            return this;
        }

        public Builder setBadgeClass(String badge_class) {
            this.badge_class = badge_class;
            return this;
        }

        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        // addExtra 一次加入一对
        public Builder addExtra(String key, String value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            extrasBuilder.put(key, value);
            return this;
        }

        // addExtras 可以一次加入多对
        public Builder addExtras(Map<String, String> extras) {
            Preconditions.checkArgument(! (null == extras), "extras should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            for (String key : extras.keySet()) {
                extrasBuilder.put(key, extras.get(key));
            }
            return this;
        }

        public Builder addExtra(String key, Number value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == numberExtrasBuilder) {
                numberExtrasBuilder = new HashMap<String, Number>();
            }
            numberExtrasBuilder.put(key, value);
            return this;
        }

        public Builder addExtra(String key, Boolean value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == booleanExtrasBuilder) {
                booleanExtrasBuilder = new HashMap<String, Boolean>();
            }
            booleanExtrasBuilder.put(key, value);
            return this;
        }

        public Builder addExtra(String key, JsonObject value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == jsonExtrasBuilder) {
                jsonExtrasBuilder = new HashMap<String, JsonObject>();
            }
            jsonExtrasBuilder.put(key, value);
            return this;
        }

        public Notification3rd build() {
            Preconditions.checkArgument(content != null && content != "", "content should not be null or empty");

            return new Notification3rd(title, content, channel_id, uri_activity, uri_action, badge_add_num, badge_set_num,
                    badge_class, sound, extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder, jsonExtrasBuilder);
        }
    }
}
