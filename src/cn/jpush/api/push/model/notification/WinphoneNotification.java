package cn.jpush.api.push.model.notification;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
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
            ImmutableMap<String, String> extras, 
            ImmutableMap<String, Number> numberExtras, 
            ImmutableMap<String, Boolean> booleanExtras) {
        super(alert, extras, numberExtras, booleanExtras);
        
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
        
        public WinphoneNotification build() {
            return new WinphoneNotification(alert, title, openPage, 
                    (null == extrasBuilder) ? null : extrasBuilder.build(), 
                    (null == numberExtrasBuilder) ? null : numberExtrasBuilder.build(),
                    (null == booleanExtrasBuilder) ? null : booleanExtrasBuilder.build());
        }
    }
}
