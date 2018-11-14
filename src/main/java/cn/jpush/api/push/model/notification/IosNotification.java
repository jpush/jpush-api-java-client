package cn.jpush.api.push.model.notification;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.ServiceHelper;

/**
 * <p><b>APNs 通知类</b></p>
 * <br>
 * 支持 APNs 默认的几个参数：
 * <ul>
 * <li>alert: 继承自父类 PlatformNotification 的 alert 属性；本类设置则覆盖。</li>
 * <li>badge: 支持 setBadge(int) 方法来设置；支持 incrBadge(int) 方法来增加。</li>
 * <li>sound: 支持 setSound(string) 方法来设置声音文件。或者 setSound(JSON object) 对应官方payload结构 </li>
 * <li>content-available: 用来支持后台推送。如果该值赋值为 1，表示开启后台推送。</li>
 * <li>extras: JSON object. 支持更多的自定义字段信息。</li>
 * </ul>
 * <br>
 * 需要特别留意的是，JPush SDK 会对以下几个值有特别的默认设置考虑：
 * <ul>
 * <li>badge: 默认为 "+1"。如果需要取消 badge 值，需要显式地调用 disableBadge()。</li>
 * <li>sound: 默认为 ""，即默认的声音提示。如果需要取消 sound 值，即不要声音，需要显式地调用 disableSound()。</li>
 * </ul>
 */
public class IosNotification extends PlatformNotification {
    public static final String NOTIFICATION_IOS = "ios";
        
    private static final String DEFAULT_SOUND = "";
    private static final String DEFAULT_BADGE = "+1";
    
    private static final String BADGE = "badge";
    private static final String SOUND = "sound";
    private static final String CONTENT_AVAILABLE = "content-available";
    private static final String MUTABLE_CONTENT = "mutable-content";
    private static final String CATEGORY = "category";
    
    private static final String ALERT_VALID_BADGE = "Badge number should be 0~99999, "
            + "and can be prefixed with + to add, - to minus";

    
    private final boolean soundDisabled;
    private final boolean badgeDisabled;
    private final Object sound;
    private final String badge;
    private final boolean contentAvailable;
    private final String category;
    private final boolean mutableContent;

    
    private IosNotification(Object alert, Object sound, String badge,
            boolean contentAvailable, boolean soundDisabled, boolean badgeDisabled, 
            String category, boolean mutableContent,
            Map<String, String> extras, 
            Map<String, Number> numberExtras, 
            Map<String, Boolean> booleanExtras, 
            Map<String, JsonObject> jsonExtras) {
        super(alert, extras, numberExtras, booleanExtras, jsonExtras);
        
        this.sound = sound;
        this.badge = badge;
        this.contentAvailable = contentAvailable;
        this.soundDisabled = soundDisabled;
        this.badgeDisabled = badgeDisabled;
        this.category = category;
        this.mutableContent = mutableContent;
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
            if (null != badge) {
                json.add(BADGE, new JsonPrimitive(this.badge));
            } else {
                json.add(BADGE, new JsonPrimitive(DEFAULT_BADGE));
            }
        }
        if (!soundDisabled) {
            if (null != sound) {
            	if(sound instanceof String){
            		json.add(SOUND, new JsonPrimitive((String)sound));
            	}else if (sound instanceof JsonObject) {
					json.add(SOUND,  (JsonObject) sound);
				}
                
            } else {
                json.add(SOUND, new JsonPrimitive(DEFAULT_SOUND));
            }
        }
        if (contentAvailable) {
            json.add(CONTENT_AVAILABLE, new JsonPrimitive(true));
        }
        if (null != category) {
        	json.add(CATEGORY, new JsonPrimitive(category));
        }
        if (mutableContent) {
            json.add(MUTABLE_CONTENT, new JsonPrimitive(true));
        }

        return json;
    }
    
    
    public static class Builder extends PlatformNotification.Builder<IosNotification, Builder> {
        private Object sound;
        private String badge;
        private boolean contentAvailable = false;
        private boolean soundDisabled = false;
        private boolean badgeDisabled = false;
        private String category;
        private boolean mutableContent;

        protected Builder getThis() {
        	return this;
        }
        
        public Builder setSound(Object sound) {
        	if (null == sound) {
                LOG.warn("Null sound. Throw away it.");
                return this;
            }
            this.sound = sound;
            return this;
        }
        
        public Builder disableSound() {
            this.soundDisabled = true;
            return this;
        }
        
        public Builder incrBadge(int badge) {
            if (!ServiceHelper.isValidIntBadge(Math.abs(badge))) {
                LOG.warn(ALERT_VALID_BADGE);
                return this;
            }
            
            if (badge >= 0) {
                this.badge = "+" + badge;
            } else {
                this.badge = "" + badge;
            }
            return this;
        }
        
        public Builder setBadge(int badge) {
            if (!ServiceHelper.isValidIntBadge(badge)) {
                LOG.warn(ALERT_VALID_BADGE);
                return this;
            }
            this.badge = "" + badge;
            return this;
        }
        
        /**
         * equals to: +1
         * @return IosNotification builder
         */
        public Builder autoBadge() {
            return incrBadge(1);
        }
        
        public Builder disableBadge() {
            this.badgeDisabled = true;
            return this;
        }
        
        public Builder setContentAvailable(boolean contentAvailable) {
            this.contentAvailable = contentAvailable;
            return this;
        }
        
        public Builder setCategory(String category) {
        	this.category = category;
        	return this;
        }
        
        public Builder setAlert(Object alert) {
            this.alert = alert;
            return this;
        }

        public Builder setMutableContent(boolean mutableContent) {
            this.mutableContent = mutableContent;
            return this;
        }


        public IosNotification build() {
            return new IosNotification(alert, sound, badge, contentAvailable, 
                    soundDisabled, badgeDisabled, category, mutableContent,
            		extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder, jsonExtrasBuilder);
        }
    }
}
