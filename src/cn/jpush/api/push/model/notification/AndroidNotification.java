package cn.jpush.api.push.model.notification;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class AndroidNotification extends PlatformNotification {
    public static final String NOTIFICATION_ANDROID = "android";
    
    private static final String TITLE = "title";
    private static final String BUILDER_ID = "builder_id";
    
    private final String title;
    private final int builderId;
    
    private AndroidNotification(String alert, String title, int builderId, 
            ImmutableMap<String, String> extras, 
            ImmutableMap<String, Number> numberExtras, 
            ImmutableMap<String, Boolean> booleanExtras) {
        super(alert, extras, numberExtras, booleanExtras);
        
        this.title = title;
        this.builderId = builderId;
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
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = super.toJSON().getAsJsonObject();
        
        if (builderId > 0) {
            json.add(BUILDER_ID, new JsonPrimitive(this.builderId));
        }
        if (null != title) {
            json.add(TITLE, new JsonPrimitive(title));
        }
        
        return json;
    }
    
    
    public static class Builder extends PlatformNotification.Builder<AndroidNotification> {
        private String title;
        private int builderId;
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder setBuilderId(int builderId) {
            this.builderId = builderId;
            return this;
        }
        
        public Builder setAlert(String alert) {
            this.alert = alert;
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
        
        public Builder addExtras(Map<String, String> extras) {
            Preconditions.checkArgument(! (null == extras), "extras should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            for (String key : extras.keySet()) {
                extrasBuilder.put(key, extras.get(key));
            }
            return this;
        }
        
        public Builder addExtra(String key, Number value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == numberExtrasBuilder) {
                numberExtrasBuilder = ImmutableMap.builder();
            }
            numberExtrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addExtra(String key, Boolean value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == booleanExtrasBuilder) {
                booleanExtrasBuilder = ImmutableMap.builder();
            }
            booleanExtrasBuilder.put(key, value);
            return this;
        }
        
        public AndroidNotification build() {
            return new AndroidNotification(alert, title, builderId, 
                    (null == extrasBuilder) ? null : extrasBuilder.build(), 
                    (null == numberExtrasBuilder) ? null : numberExtrasBuilder.build(),
                    (null == booleanExtrasBuilder) ? null : booleanExtrasBuilder.build());
        }
    }
}
