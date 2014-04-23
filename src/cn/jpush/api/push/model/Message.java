package cn.jpush.api.push.model;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Message implements PushModel {
    public static final String MESSAGE = "message";
    
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String EXTRAS = "extras";
    
    private final String title;
    private final String content;
    private final ImmutableMap<String, String> extras;
    
    private Message(String title, String content, ImmutableMap<String, String> extras) {
        this.title = title;
        this.content = content;
        this.extras = extras;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Message content(String content) {
        return new Builder().setContent(content).build();
    }
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (null != title) {
            json.add(TITLE, new JsonPrimitive(title));
        }
        if (null != content) {
            json.add(CONTENT, new JsonPrimitive(content));
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
        private String content;
        private ImmutableMap.Builder<String, String> extrasBuilder;
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder setContent(String content) {
            this.content = content;
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
            Preconditions.checkArgument(null == key || null == value, "Key/Value should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            extrasBuilder.put(key, value);
            return this;
        }
        
        public Message build() {
            Preconditions.checkArgument(! 
                    (null == title && null == content && null == extrasBuilder), 
                    "No any param is set.");
            return new Message(title, content, (null == extrasBuilder) ? null : extrasBuilder.build());
        }
    }
}
