package cn.jpush.api.push.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.Preconditions;

public class Message implements PushModel {    
    private static final String TITLE = "title";
    private static final String MSG_CONTENT = "msg_content";
    private static final String CONTENT_TYPE = "content_type";
    private static final String EXTRAS = "extras";
    
    private final String title;
    private final String msgContent;
    private final String contentType;
    private final Map<String, String> extras;
    private final Map<String, Number> numberExtras;
    private final Map<String, Boolean> booleanExtras;
    private final Map<String, JsonObject> jsonExtras;
    private final Map<String, JsonPrimitive> customData;
    private final Map<String, JsonArray> jsonArrayExtras;

    private Message(String title, String msgContent, String contentType, 
    		Map<String, String> extras, 
    		Map<String, Number> numberExtras,
    		Map<String, Boolean> booleanExtras,
    		Map<String, JsonObject> jsonExtras,
            Map<String, JsonPrimitive> customData,
            Map<String, JsonArray> jsonArrayExtras) {
        this.title = title;
        this.msgContent = msgContent;
        this.contentType = contentType;
        this.extras = extras;
        this.numberExtras = numberExtras;
        this.booleanExtras = booleanExtras;
        this.jsonExtras = jsonExtras;
        this.customData = customData;
        this.jsonArrayExtras = jsonArrayExtras;
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
        
        JsonObject extrasObject = null;
        if (null != extras || null != numberExtras || null != booleanExtras || null != jsonExtras || null != jsonArrayExtras){
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

        if (null != jsonArrayExtras) {
            for (String key : jsonArrayExtras.keySet()) {
                extrasObject.add(key, jsonArrayExtras.get(key));
            }
        }
        
        if (null != extras || null != numberExtras || null != booleanExtras) {
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
        private Map<String, String> extrasBuilder;
        private Map<String, Number> numberExtrasBuilder;
        private Map<String, Boolean> booleanExtrasBuilder;
        protected Map<String, JsonObject> jsonExtrasBuilder;
        private Map<String, JsonPrimitive> customData;
        protected Map<String, JsonArray> jsonArrayExtrasBuilder;
        
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
        
        public Builder addExtra(String key, String value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            extrasBuilder.put(key, value);
            return this;
        }
        
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

        public Builder addExtra(String key, JsonArray value) {
        	Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == jsonArrayExtrasBuilder) {
            	jsonArrayExtrasBuilder = new HashMap<String, JsonArray>();
            }
            jsonArrayExtrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addCustom(Map<String, String> extras) {
            if (customData == null) {
                customData = new LinkedHashMap<>();
            }
            for (Map.Entry<String, String> entry : extras.entrySet()) {
                customData.put(entry.getKey(), new JsonPrimitive(entry.getValue()));
            }
            return this;
        }

        public Builder addCustom(String key, Number value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }

        public Builder addCustom(String key, String value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }

        public Builder addCustom(String key, Boolean value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }
        
        public Message build() {
            Preconditions.checkArgument(! (null == msgContent), 
                    "msgContent should be set");
            return new Message(title, msgContent, contentType, 
            		extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder,jsonExtrasBuilder, customData, jsonArrayExtrasBuilder);
        }
    }
}
