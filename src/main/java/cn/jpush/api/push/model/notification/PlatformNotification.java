package cn.jpush.api.push.model.notification;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.push.model.PushModel;
import cn.jpush.api.utils.Preconditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public abstract class PlatformNotification implements PushModel {
    public static final String ALERT = "alert";
    private static final String EXTRAS = "extras";
    
    protected static final Logger LOG = LoggerFactory.getLogger(PlatformNotification.class);

    private String alert;
    private final Map<String, String> extras;
    private final Map<String, Number> numberExtras;
    private final Map<String, Boolean> booleanExtras;
    private final Map<String, JsonObject> jsonExtras;
    
    public PlatformNotification(String alert, Map<String, String> extras, 
    		Map<String, Number> numberExtras, 
    		Map<String, Boolean> booleanExtras, 
    		Map<String, JsonObject> jsonExtras) {
        this.alert = alert;
        this.extras = extras;
        this.numberExtras = numberExtras;
        this.booleanExtras = booleanExtras;
        this.jsonExtras = jsonExtras;
    }
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        
        if (null != alert) {
            json.add(ALERT, new JsonPrimitive(this.alert));
        }

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
    
    protected String getAlert() {
        return this.alert;
    }
    
    protected void setAlert(String alert) {
        this.alert = alert;
    }

    protected abstract String getPlatform();
    
    protected abstract static class Builder<T> {
        protected String alert;
        protected Map<String, String> extrasBuilder;
        protected Map<String, Number> numberExtrasBuilder;
        protected Map<String, Boolean> booleanExtrasBuilder;
        protected Map<String, JsonObject> jsonExtrasBuilder;
        
        public abstract Builder<T> setAlert(String alert);
                
        public Builder<T> addExtra(String key, String value) {
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

        public Builder<T> addExtras(Map<String, String> extras) {
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
        
        public Builder<T> addExtra(String key, Number value) {
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
        
        public Builder<T> addExtra(String key, Boolean value) {
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
        
        public Builder<T> addExtra(String key, JsonObject value) {
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
                
        public abstract T build();
    }

    
}
