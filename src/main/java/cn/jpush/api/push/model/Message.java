package cn.jpush.api.push.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.Preconditions;

/**
 * Android 1.6.2 及以下版本 接收 notification 与 message 并存（即本次 api 调用同时推送通知和消息）的离线推送， 只能收到通知部分，message 部分没有透传给 App。
 *
 * Android 1.6.3 及以上 SDK 版本已做相应调整，能正常接收同时推送通知和消息的离线记录。
 *
 * iOS 1.7.3 及以上的版本才能正确解析 v3 的 message，但是无法解析 v2 推送通知同时下发的应用内消息。
 */
public class Message implements PushModel {
    private static final String MSG_CONTENT = "msg_content";
    private static final String TITLE = "title";
    private static final String CONTENT_TYPE = "content_type";
    private static final String TEST_MESSAGE = "test_message";
    private static final String EXTRAS = "extras";

    private final String msgContent;
    private final String title;
    private final String contentType;
    private final Boolean testMessage;
    private final Map<String, String> extras;
    private final Map<String, Number> numberExtras;
    private final Map<String, Boolean> booleanExtras;
    private final Map<String, JsonObject> jsonExtras;
    private final Map<String, JsonPrimitive> customData;

    private Message(String title, String msgContent, String contentType, Boolean testMessage,
                    Map<String, String> extras,
                    Map<String, Number> numberExtras,
                    Map<String, Boolean> booleanExtras,
                    Map<String, JsonObject> jsonExtras,
                    Map<String, JsonPrimitive> customData) {
        this.title = title;
        this.msgContent = msgContent;
        this.contentType = contentType;
        this.testMessage = testMessage;
        this.extras = extras;
        this.numberExtras = numberExtras;
        this.booleanExtras = booleanExtras;
        this.jsonExtras = jsonExtras;
        this.customData = customData;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Message content(String msgContent) {
        return new Builder().setMsgContent(msgContent).build();
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (null != title) {
            json.add(TITLE, new JsonPrimitive(title));
        }
        if (null != msgContent) {
            json.add(MSG_CONTENT, new JsonPrimitive(msgContent));
        }
        if (null != contentType) {
            json.add(CONTENT_TYPE, new JsonPrimitive(contentType));
        }
        if (null != testMessage) {
            json.add(TEST_MESSAGE, new JsonPrimitive(testMessage));
        }

        JsonObject extrasObject = null;
        if (null != extras || null != numberExtras || null != booleanExtras || null != jsonExtras) {
            extrasObject = new JsonObject();
        }

        if (null != extras) {
            for (String key : extras.keySet()) {
                if (extras.get(key) != null) {
                    extrasObject.add(key, new JsonPrimitive(extras.get(key)));
                } else {
                    extrasObject.add(key, JsonNull.INSTANCE);
                }
            }
        }
        if (null != numberExtras) {
            for (String key : numberExtras.keySet()) {
                extrasObject.add(key, new JsonPrimitive(numberExtras.get(key)));
            }
        }
        if (null != booleanExtras) {
            for (String key : booleanExtras.keySet()) {
                extrasObject.add(key, new JsonPrimitive(booleanExtras.get(key)));
            }
        }
        if (null != jsonExtras) {
            for (String key : jsonExtras.keySet()) {
                extrasObject.add(key, jsonExtras.get(key));
            }
        }

        if (null != extras || null != numberExtras || null != booleanExtras || null != jsonExtras) {
            json.add(EXTRAS, extrasObject);
        }

        if (null != customData) {
            for (Map.Entry<String, JsonPrimitive> entry : customData.entrySet()) {
                json.add(entry.getKey(), entry.getValue());
            }
        }

        return json;
    }


    public static class Builder {
        private String title;
        private String msgContent;
        private String contentType;
        private Boolean testMessage;
        private Map<String, String> extrasBuilder;
        private Map<String, Number> numberExtrasBuilder;
        private Map<String, Boolean> booleanExtrasBuilder;
        protected Map<String, JsonObject> jsonExtrasBuilder;
        private Map<String, JsonPrimitive> customData;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMsgContent(String msgContent) {
            this.msgContent = msgContent;
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder setTestMessage(Boolean testMessage) {
            this.testMessage = testMessage;
            return this;
        }

        public Builder addExtra(String key, String value) {
            Preconditions.checkArgument(!(null == key || null == value), "Key/Value should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            extrasBuilder.put(key, value);
            return this;
        }

        public Builder addExtras(Map<String, String> extras) {
            Preconditions.checkArgument(!(null == extras), "extras should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            for (String key : extras.keySet()) {
                extrasBuilder.put(key, extras.get(key));
            }
            return this;
        }

        public Builder addExtra(String key, Number value) {
            Preconditions.checkArgument(!(null == key || null == value), "Key/Value should not be null.");
            if (null == numberExtrasBuilder) {
                numberExtrasBuilder = new HashMap<String, Number>();
            }
            numberExtrasBuilder.put(key, value);
            return this;
        }

        public Builder addExtra(String key, Boolean value) {
            Preconditions.checkArgument(!(null == key || null == value), "Key/Value should not be null.");
            if (null == booleanExtrasBuilder) {
                booleanExtrasBuilder = new HashMap<String, Boolean>();
            }
            booleanExtrasBuilder.put(key, value);
            return this;
        }

        public Builder addExtra(String key, JsonObject value) {
            Preconditions.checkArgument(!(null == key || null == value), "Key/Value should not be null.");
            if (null == jsonExtrasBuilder) {
                jsonExtrasBuilder = new HashMap<String, JsonObject>();
            }
            jsonExtrasBuilder.put(key, value);
            return this;
        }

        public Builder addCustom(Map<String, String> extras) {
            if (customData == null) {
                customData = new LinkedHashMap<String, JsonPrimitive>();
            }
            for (Map.Entry<String, String> entry : extras.entrySet()) {
                customData.put(entry.getKey(), new JsonPrimitive(entry.getValue()));
            }
            return this;
        }

        public Builder addCustom(String key, Number value) {
            Preconditions.checkArgument(!(null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<String, JsonPrimitive>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }

        public Builder addCustom(String key, String value) {
            Preconditions.checkArgument(!(null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<String, JsonPrimitive>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }

        public Builder addCustom(String key, Boolean value) {
            Preconditions.checkArgument(!(null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<String, JsonPrimitive>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }

        public Message build() {
            Preconditions.checkArgument(!(null == msgContent),
                    "msgContent should be set");
            return new Message(title, msgContent, contentType, testMessage,
                    extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder, jsonExtrasBuilder, customData);
        }
    }
}
