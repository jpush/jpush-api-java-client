package cn.jpush.api.push.model.notification;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class IosNotification extends PlatformNotification {
    public static final String NOTIFICATION_IOS = "ios";
        
    private static final String DEFAULT_SOUND = "";
    private static final int BADGE_UNDEFINED = -1;
    private static final int DEFAULT_BADGE = 1;
    
    private static final String BADGE = "badge";
    private static final String SOUND = "sound";
    private static final String CONTENT_AVAILABLE = "content-available";
    
    private final boolean soundDisabled;
    private final boolean badgeDisabled;
    private final String sound;
    private final int badge;
    private final boolean contentAvailable;
    
    private IosNotification(String alert, String sound, int badge, 
            boolean contentAvailable, boolean soundDisabled, boolean badgeDisabled, 
            ImmutableMap<String, String> extras, 
            ImmutableMap<String, Number> numberExtras, 
            ImmutableMap<String, Boolean> booleanExtras) {
        super(alert, extras, numberExtras, booleanExtras);
        
        this.sound = sound;
        this.badge = badge;
        this.contentAvailable = contentAvailable;
        this.soundDisabled = soundDisabled;
        this.badgeDisabled = badgeDisabled;
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
        
        if (!badgeDisabled) {
            if (badge >= 0) {
                json.add(BADGE, new JsonPrimitive(this.badge));
            } else if (badge == BADGE_UNDEFINED) {
                json.add(BADGE, new JsonPrimitive(DEFAULT_BADGE));
            }
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
        private int badge = BADGE_UNDEFINED;
        private boolean contentAvailable = false;
        private boolean soundDisabled = false;
        private boolean badgeDisabled = false;
        
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
        
        public Builder disableBadge() {
            this.badgeDisabled = true;
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
        
        public IosNotification build() {
            return new IosNotification(alert, sound, badge, contentAvailable, 
                    soundDisabled, badgeDisabled,   
                    (null == extrasBuilder) ? null : extrasBuilder.build(), 
                    (null == numberExtrasBuilder) ? null : numberExtrasBuilder.build(),
                    (null == booleanExtrasBuilder) ? null : booleanExtrasBuilder.build());
        }
    }
}
