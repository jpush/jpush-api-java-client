package cn.jpush.api.push.model.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.push.model.PushModel;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public abstract class PlatformNotification implements PushModel {
    public static final String ALERT = "alert";
    private static final String EXTRAS = "extras";
    
    protected static final Logger LOG = LoggerFactory.getLogger(PlatformNotification.class);

    private String alert;
    private final ImmutableMap<String, String> extras;
    private final ImmutableMap<String, Number> numberExtras;
    private final ImmutableMap<String, Boolean> booleanExtras;
    
    public PlatformNotification(String alert, ImmutableMap<String, String> extras, 
            ImmutableMap<String, Number> numberExtras, ImmutableMap<String, Boolean> booleanExtras) {
        this.alert = alert;
        this.extras = extras;
        this.numberExtras = numberExtras;
        this.booleanExtras = booleanExtras;
    }
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        
        if (null != alert) {
            json.add(ALERT, new JsonPrimitive(this.alert));
        }

        JsonObject extrasObject = null;
        if (null != extras || null != numberExtras || null != booleanExtras) {
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

        if (null != extras || null != numberExtras || null != booleanExtras) {
            json.add(EXTRAS, extrasObject);
        }
        
        return json;
    }
    
    protected String getAlert() {
        return this.alert;
    }
    
    protected void setAlert(String alert) {
        this.alert = alert;
    }

    protected abstract String getPlatform();
    
    protected abstract static class Builder<T> {
        protected String alert;
        protected ImmutableMap.Builder<String, String> extrasBuilder;
        protected ImmutableMap.Builder<String, Number> numberExtrasBuilder;
        protected ImmutableMap.Builder<String, Boolean> booleanExtrasBuilder;
        
        public abstract Builder<T> setAlert(String alert);
                
        public abstract Builder<T> addExtra(String key, String value);
        public abstract Builder<T> addExtra(String key, Number value);
        public abstract Builder<T> addExtra(String key, Boolean value);
        
        public abstract T build();
    }

    
}
