package cn.jpush.api.push.model.notification;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.utils.Preconditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class WinphoneNotification extends PlatformNotification {
    private static final String NOTIFICATION_WINPHONE = "winphone";
    
    private static final String TITLE = "title";
    private static final String _OPEN_PAGE = "_open_page";
    
    private final String title;
    private final String openPage;
    
    private WinphoneNotification(String alert, String title, String openPage, 
    		Map<String, String> extras, 
    		Map<String, Number> numberExtras, 
    		Map<String, Boolean> booleanExtras,
    		Map<String, JsonObject> jsonExtras) {
    	super(alert, extras, numberExtras, booleanExtras, jsonExtras);
        
        this.title = title;
        this.openPage = openPage;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static WinphoneNotification alert(String alert) {
        return newBuilder().setAlert(alert).build();
    }
    
    
    @Override
    public String getPlatform() {
        return NOTIFICATION_WINPHONE;
    }
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = super.toJSON().getAsJsonObject();
        
        if (null != title) {
            json.add(TITLE, new JsonPrimitive(title));
        }
        if (null != openPage) {
            json.add(_OPEN_PAGE, new JsonPrimitive(openPage));
        }
        
        return json;
    }
    
    
    public static class Builder extends PlatformNotification.Builder<WinphoneNotification> {
        private String title;
        private String openPage;
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder setOpenPage(String openPage) {
            this.openPage = openPage;
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
        

        
        public WinphoneNotification build() {
            return new WinphoneNotification(alert, title, openPage, 
            		extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder, jsonExtrasBuilder);
        }
    }
}
