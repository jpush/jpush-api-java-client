package cn.jpush.api.push.model.notification;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class IosNotification extends PlatformNotification {
    public static final String NOTIFICATION_IOS = "ios";
    
    public static final String DEFAULT_SOUND = "";
    public static final String APS = "aps";
    
    public static final String BADGE = "badge";
    public static final String SOUND = "sound";
    public static final String CONTENT_AVAILABLE = "content-available";
    public static final String EXTRAS = "extras";
    
    private final boolean soundDisabled;
    private final String sound;
    private final int badge;
    private final boolean contentAvailable;
    private final ImmutableMap<String, String> extras;
    
    private IosNotification(String alert, String sound, int badge, 
            boolean contentAvailable, boolean soundDisabled,   
            ImmutableMap<String, String> extras) {
        super(alert);
        this.sound = sound;
        this.badge = badge;
        this.contentAvailable = contentAvailable;
        this.soundDisabled = soundDisabled;
        this.extras = extras;
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
        JsonObject json = new JsonObject();
        if (null != alert) {
            json.add(ALERT, new JsonPrimitive(this.alert));
        }
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
        
        if (null != extras) {
            JsonObject extrasObject = new JsonObject();
            for (String key : extras.keySet()) {
                extrasObject.add(key, new JsonPrimitive(extras.get(key)));
            }
            json.add(EXTRAS, extrasObject);
        }
        
        Preconditions.checkArgument(
                ! (null == alert && null == sound && badge < 0 && !contentAvailable && null == extras)
                , "No any notification params are set.");

        return json;
    }
    
    
    public static class Builder {
        private String alert;
        private String sound;
        private int badge = -1;
        private boolean contentAvailable = false;
        private boolean soundDisabled = false;
        private ImmutableMap.Builder<String, String> extrasBuilder;
        
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }
        
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
        
        public Builder addExtras(Map<String, String> extras) {
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            extrasBuilder.putAll(extras);
            return this;
        }
        
        public Builder addExtra(String key, String value) {
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            extrasBuilder.put(key, value);
            return this;
        }
        
        public IosNotification build() {
            return new IosNotification(alert, sound, badge, contentAvailable, soundDisabled,  
                    (null == extrasBuilder) ? null : extrasBuilder.build());
        }
    }
}
