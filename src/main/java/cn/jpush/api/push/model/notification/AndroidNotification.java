package cn.jpush.api.push.model.notification;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

public class AndroidNotification extends PlatformNotification {
    public static final String NOTIFICATION_ANDROID = "android";
    
    private static final String TITLE = "title";
    private static final String BUILDER_ID = "builder_id";
    
    private final String title;
    private final int builderId;
    
    private AndroidNotification(Object alert, String title, int builderId,
            Map<String, String> extras, 
            Map<String, Number> numberExtras, 
            Map<String, Boolean> booleanExtras, 
            Map<String, JsonObject> jsonExtras) {
        super(alert, extras, numberExtras, booleanExtras, jsonExtras);
        
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
    
    
    public static class Builder extends PlatformNotification.Builder<AndroidNotification, Builder> {
        private String title;
        private int builderId;
        
        protected Builder getThis() {
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
        
        public Builder setAlert(Object alert) {
            this.alert = alert;
            return this;
        }
        
        
        public AndroidNotification build() {
            return new AndroidNotification(alert, title, builderId, 
            		extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder, jsonExtrasBuilder);
        }
    }
}
