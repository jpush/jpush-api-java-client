package cn.jpush.api.push.model.notification;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.utils.Preconditions;

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
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (null == value) {
                LOG.debug("Extra value is null, throw away it.");
                return this;
            }
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            extrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addExtras(Map<String, String> extras) {
            if (null == extras) {
                LOG.warn("Null extras param. Throw away it.");
                return this;
            }
            
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            for (String key : extras.keySet()) {
                extrasBuilder.put(key, extras.get(key));
            }
            return this;
        }
        
        public Builder addExtra(String key, Number value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (null == value) {
                LOG.debug("Extra value is null, throw away it.");
                return this;
            }
            if (null == numberExtrasBuilder) {
                numberExtrasBuilder = new HashMap<String, Number>();
            }
            numberExtrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addExtra(String key, Boolean value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (null == value) {
                LOG.debug("Extra value is null, throw away it.");
                return this;
            }
            if (null == booleanExtrasBuilder) {
                booleanExtrasBuilder = new HashMap<String, Boolean>();
            }
            booleanExtrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addExtra(String key, JsonObject value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (null == value) {
                LOG.debug("Extra value is null, throw away it.");
                return this;
            }
            if (null == jsonExtrasBuilder) {
            	jsonExtrasBuilder = new HashMap<String, JsonObject>();
            }
            jsonExtrasBuilder.put(key, value);
            return this;
        }
        
        
        public AndroidNotification build() {
            return new AndroidNotification(alert, title, builderId, 
            		extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder, jsonExtrasBuilder);
        }
    }
}
