package cn.jpush.api.push.model;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Message implements PushModel {    
    private static final String TITLE = "title";
    private static final String MSG_CONTENT = "msg_content";
    private static final String CONTENT_TYPE = "content_type";
    private static final String EXTRAS = "extras";
    
    private final String title;
    private final String msgContent;
    private final String contentType;
    private final ImmutableMap<String, String> extras;
    
    private Message(String title, String msgContent, String contentType, ImmutableMap<String, String> extras) {
        this.title = title;
        this.msgContent = msgContent;
        this.contentType = contentType;
        this.extras = extras;
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
        if (null != extras) {
            Gson gson = new Gson();
            JsonElement extrasObject = gson.toJsonTree(extras);
            json.add(EXTRAS, extrasObject);
        }
        return json;
    }
    
    public static class Builder {
        private String title;
        private String msgContent;
        private String contentType;
        private ImmutableMap.Builder<String, String> extrasBuilder;
        
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
        
        public Builder addExtras(Map<String, String> extras) {
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            extrasBuilder.putAll(extras);
            return this;
        }
        
        public Builder addExtra(String key, String value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            extrasBuilder.put(key, value);
            return this;
        }
        
        public Message build() {
            Preconditions.checkArgument(! (null == msgContent), 
                    "msgConent should be set");
            return new Message(title, msgContent, contentType, 
                    (null == extrasBuilder) ? null : extrasBuilder.build());
        }
    }
}
