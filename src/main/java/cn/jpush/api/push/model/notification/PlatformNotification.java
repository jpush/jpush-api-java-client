package cn.jpush.api.push.model.notification;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.push.model.PushModel;

public abstract class PlatformNotification implements PushModel {
    public static final String ALERT = "alert";
    private static final String EXTRAS = "extras";
    
    protected static final Logger LOG = LoggerFactory.getLogger(PlatformNotification.class);

    private Object alert;
    private final Map<String, String> extras;
    private final Map<String, Number> numberExtras;
    private final Map<String, Boolean> booleanExtras;
    private final Map<String, JsonObject> jsonExtras;
    private final Map<String, JsonPrimitive> customData;
    private final Map<String, JsonArray> jsonArrayExtras;
    
    public PlatformNotification(Object alert, Map<String, String> extras,
    		Map<String, Number> numberExtras, 
    		Map<String, Boolean> booleanExtras, 
    		Map<String, JsonObject> jsonExtras,
    		Map<String, JsonArray> jsonArrayExtras) {
        this.alert = alert;
        this.extras = extras;
        this.numberExtras = numberExtras;
        this.booleanExtras = booleanExtras;
        this.jsonExtras = jsonExtras;
        this.jsonArrayExtras = jsonArrayExtras;
        customData = new LinkedHashMap<>();
    }

    public PlatformNotification(Object alert, Map<String, String> extras,
                                Map<String, Number> numberExtras,
                                Map<String, Boolean> booleanExtras,
                                Map<String, JsonObject> jsonExtras,
                                Map<String, JsonPrimitive> customData,
                                Map<String, JsonArray> jsonArrayExtras) {
        this.alert = alert;
        this.extras = extras;
        this.numberExtras = numberExtras;
        this.booleanExtras = booleanExtras;
        this.jsonExtras = jsonExtras;
        this.jsonArrayExtras = jsonArrayExtras;
        this.customData = customData;
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        
        if (null != alert) {
            if ( alert instanceof JsonObject) {
                json.add(ALERT, (JsonObject) alert);
            } else if (alert instanceof IosAlert) {
                json.add(ALERT, ((IosAlert) alert).toJSON());
            } else {
                json.add(ALERT, new JsonPrimitive(alert.toString()));
            }
        }

        JsonObject extrasObject = null;
        if (null != extras || null != numberExtras || null != booleanExtras || null != jsonExtras || null != jsonArrayExtras) {
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
        if (null != jsonArrayExtras) {
        	JsonArray value = null;
            for (String key : jsonArrayExtras.keySet()) {
                value = jsonArrayExtras.get(key);
                if (null != value) {
                    extrasObject.add(key, value);
                }
            }
        }
        
        if (null != extras || null != numberExtras || null != booleanExtras || null != jsonExtras || null != jsonArrayExtras) {
            json.add(EXTRAS, extrasObject);
        }

        if (null != customData) {
            for (Map.Entry<String, JsonPrimitive> entry : customData.entrySet()) {
                json.add(entry.getKey(), entry.getValue());
            }
        }
        
        return json;
    }
    
    protected Object getAlert() {
        return this.alert;
    }
    
    protected void setAlert(Object alert) {
        this.alert = alert;
    }

    protected abstract String getPlatform();
    
    protected abstract static class Builder<T extends PlatformNotification, B extends Builder<T, B>> {
    	private B theBuilder;
    	
        protected Object alert;
        protected Map<String, String> extrasBuilder;
        protected Map<String, Number> numberExtrasBuilder;
        protected Map<String, Boolean> booleanExtrasBuilder;
        protected Map<String, JsonObject> jsonExtrasBuilder;
        protected Map<String, JsonPrimitive> customData;
        protected Map<String, JsonArray> jsonArrayExtrasBuilder;
        
        public Builder () {
            customData = new LinkedHashMap<>();
        	theBuilder = getThis();
        }

        protected abstract B getThis();
        
        public abstract B setAlert(Object alert);
                
        public B addExtra(String key, String value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (null == value) {
                LOG.debug("Extra value is null, throw away it.");
                return theBuilder;
            }
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            extrasBuilder.put(key, value);
            return theBuilder;
        }

        public B addExtras(Map<String, String> extras) {
            if (null == extras) {
                LOG.warn("Null extras param. Throw away it.");
                return theBuilder;
            }
            
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            for (String key : extras.keySet()) {
                extrasBuilder.put(key, extras.get(key));
            }
            return theBuilder;
        }
        
        public B addExtra(String key, Number value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (null == value) {
                LOG.debug("Extra value is null, throw away it.");
                return theBuilder;
            }
            if (null == numberExtrasBuilder) {
                numberExtrasBuilder = new HashMap<String, Number>();
            }
            numberExtrasBuilder.put(key, value);
            return theBuilder;
        }
        
        public B addExtra(String key, Boolean value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (null == value) {
                LOG.debug("Extra value is null, throw away it.");
                return theBuilder;
            }
            if (null == booleanExtrasBuilder) {
                booleanExtrasBuilder = new HashMap<String, Boolean>();
            }
            booleanExtrasBuilder.put(key, value);
            return theBuilder;
        }
        
        public B addExtra(String key, JsonObject value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (null == value) {
                LOG.debug("Extra value is null, throw away it.");
                return theBuilder;
            }
            if (null == jsonExtrasBuilder) {
            	jsonExtrasBuilder = new HashMap<String, JsonObject>();
            }
            jsonExtrasBuilder.put(key, value);
            return theBuilder;
        }

        public B addExtra(String key, JsonArray value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (null == value) {
                LOG.debug("Extra value is null, throw away it.");
                return theBuilder;
            }
            if (null == jsonArrayExtrasBuilder) {
            	jsonArrayExtrasBuilder = new HashMap<String, JsonArray>();
            }
            jsonArrayExtrasBuilder.put(key, value);
            return theBuilder;
        }
        
        public B addCustom(Map<String, String> extras) {
            for (Map.Entry<String, String> entry : extras.entrySet()) {
                customData.put(entry.getKey(), new JsonPrimitive(entry.getValue()));
            }
            return theBuilder;
        }

        public B addCustom(String key, Number value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            customData.put(key, new JsonPrimitive(value));
            return theBuilder;
        }

        public B addCustom(String key, String value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            customData.put(key, new JsonPrimitive(value));
            return theBuilder;
        }

        public B addCustom(String key, Boolean value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            customData.put(key, new JsonPrimitive(value));
            return theBuilder;
        }

        public abstract T build();
    }

    
}
