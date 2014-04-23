package cn.jpush.api.push.model.notification;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class AndroidNotification extends PlatformNotification {
    public static final String NOTIFICATION_ANDROID = "android";
    
    public static final String TITLE = "title";
    public static final String BUILDER_ID = "builder_id";
    public static final String EXTRAS = "extras";
    
    private final String title;
    private final int builderId;
    private final ImmutableMap<String, String> extras;
        
    private AndroidNotification(String alert, String title, int builderId, 
            ImmutableMap<String, String> extras) {
        super(alert);
        this.title = title;
        this.builderId = builderId;
        this.extras = extras;
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
        JsonObject json = new JsonObject();
        if (null != alert) {
            json.add(ALERT, new JsonPrimitive(this.alert));
        }
        if (builderId > 0) {
            json.add(BUILDER_ID, new JsonPrimitive(this.builderId));
        }
        if (null != title) {
            json.add(TITLE, new JsonPrimitive(title));
        }
        if (null != extras) {
            JsonObject extrasObject = new JsonObject();
            for (String key : extras.keySet()) {
                extrasObject.add(key, new JsonPrimitive(extras.get(key)));
            }
            json.add(EXTRAS, extrasObject);
        }
        
        Preconditions.checkArgument(
                ! (null == alert && null == title && 0 == builderId && null == extras)
                , "No any notification params are set.");

        return json;
    }
    
    
    public static class Builder {
        private String alert;
        private String title;
        private int builderId;
        private ImmutableMap.Builder<String, String> extrasBuilder;
        
        public Builder setAlert(String alert) {
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
        
        public Builder addExtra(Map<String, String> extra) {
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            extrasBuilder.putAll(extra);
            return this;
        }
        
        public Builder addExtra(String key, String value) {
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            extrasBuilder.put(key, value);
            return this;
        }
        
        public AndroidNotification build() {
            return new AndroidNotification(alert, title, builderId, 
                    (null == extrasBuilder) ? null : extrasBuilder.build());
        }
    }
}
