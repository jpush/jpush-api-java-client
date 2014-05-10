package cn.jpush.api.push.model.notification;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class IosNotification extends PlatformNotification {
    private static final String NOTIFICATION_IOS = "ios";
    
    private static final String DEFAULT_SOUND = "";
    
    private static final String BADGE = "badge";
    private static final String SOUND = "sound";
    private static final String CONTENT_AVAILABLE = "content-available";
    
    private final boolean soundDisabled;
    private final String sound;
    private final int badge;
    private final boolean contentAvailable;
    
    private IosNotification(String alert, String sound, int badge, 
            boolean contentAvailable, boolean soundDisabled,   
            ImmutableMap<String, String> extras, 
            ImmutableMap<String, Number> numberExtras, 
            ImmutableMap<String, Boolean> booleanExtras) {
        super(alert, extras, numberExtras, booleanExtras);
        
        this.sound = sound;
        this.badge = badge;
        this.contentAvailable = contentAvailable;
        this.soundDisabled = soundDisabled;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static IosNotification alert(String alert) {
        return newBuilder().setAlert(alert).build();
    }
    
    
    @Override
    public String getPlatform() {
        return NOTIFICATION_IOS;
    }
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = super.toJSON().getAsJsonObject();
        
        if (badge >= 0) {
            json.add(BADGE, new JsonPrimitive(this.badge));
        }
        if (!soundDisabled) {
            if (null != sound) {
                json.add(SOUND, new JsonPrimitive(sound));
            } else {
                json.add(SOUND, new JsonPrimitive(DEFAULT_SOUND));
            }
        }
        if (contentAvailable) {
            json.add(CONTENT_AVAILABLE, new JsonPrimitive(1));
        }
        
        return json;
    }
    
    
    public static class Builder extends PlatformNotification.Builder<IosNotification> {
        private String sound;
        private int badge = -1;
        private boolean contentAvailable = false;
        private boolean soundDisabled = false;
        
        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }
        
        public Builder disableSound() {
            this.soundDisabled = true;
            return this;
        }
        
        public Builder setBadge(int badge) {
            this.badge = badge;
            return this;
        }
        
        public Builder setContentAvailable(boolean contentAvailable) {
            this.contentAvailable = contentAvailable;
            return this;
        }
        
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }
        
        public Builder addExtra(String key, String value) {
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            extrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addExtra(String key, Number value) {
            if (null == numberExtrasBuilder) {
                numberExtrasBuilder = ImmutableMap.builder();
            }
            numberExtrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addExtra(String key, Boolean value) {
            if (null == booleanExtrasBuilder) {
                booleanExtrasBuilder = ImmutableMap.builder();
            }
            booleanExtrasBuilder.put(key, value);
            return this;
        }
        
        public IosNotification build() {
            return new IosNotification(alert, sound, badge, contentAvailable, soundDisabled,  
                    (null == extrasBuilder) ? null : extrasBuilder.build(), 
                    (null == numberExtrasBuilder) ? null : numberExtrasBuilder.build(),
                    (null == booleanExtrasBuilder) ? null : booleanExtrasBuilder.build());
        }
    }
}
