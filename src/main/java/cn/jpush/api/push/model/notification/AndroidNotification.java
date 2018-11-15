package cn.jpush.api.push.model.notification;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

public class AndroidNotification extends PlatformNotification {
    public static final String NOTIFICATION_ANDROID = "android";
    
    private static final String TITLE = "title";
    private static final String BUILDER_ID = "builder_id";
    private static final String INBOX = "inbox";
    private static final String STYLE = "style";
    private static final String ALERT_TYPE = "alert_type";
    private static final String BIG_TEXT = "big_text";
    private static final String BIG_PIC_PATH = "big_pic_path";
    private static final String PRIORITY = "priority";
    private static final String CATEGORY = "category";
    private static final String LARGE_ICON = "large_icon";
    private static final String INTENT = "intent";
    
    private final String title;
    private final int builderId;
    // 0 ~ 4
    private int style = 0;
    // -1 ~ 7
    private int alert_type;
    private String big_text;
    private Object inbox;
    private String big_pic_path;
    private int priority;
    private String category;
    private String large_icon;
    private JsonObject intent;
    
    private AndroidNotification(Object alert, String title, int builderId, int style, int alertType, String bigText,
                                Object inbox, String bigPicPath, int priority, String category,String large_icon,JsonObject intent, 
            Map<String, String> extras, 
            Map<String, Number> numberExtras, 
            Map<String, Boolean> booleanExtras, 
            Map<String, JsonObject> jsonExtras) {
        super(alert, extras, numberExtras, booleanExtras, jsonExtras);
        
        this.title = title;
        this.builderId = builderId;
        this.style = style;
        this.alert_type = alertType;
        this.big_text = bigText;
        this.inbox = inbox;
        this.big_pic_path = bigPicPath;
        this.priority = priority;
        this.category = category;
        this.large_icon = large_icon;
        this.intent = intent;
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

    protected Object getInbox() {
        return this.inbox;
    }

    protected void setInbox(Object inbox) {
        this.inbox = inbox;
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

        // 默认是 0
        if (0 != style) {
            json.add(STYLE, new JsonPrimitive(this.style));
        }

        if (-1 != alert_type) {
            json.add(ALERT_TYPE, new JsonPrimitive(this.alert_type));
        }

        if (null != big_text) {
            json.add(BIG_TEXT, new JsonPrimitive(this.big_text));
        }

        if (null != inbox) {
            if (inbox instanceof JsonObject) {
                json.add(INBOX, (JsonObject) inbox);
            }
        }

        if (null != big_pic_path) {
            json.add(BIG_PIC_PATH, new JsonPrimitive(big_pic_path));
        }

        // 默认为 0
        if (0 != priority) {
            json.add(PRIORITY, new JsonPrimitive(priority));
        }

        if (null != category) {
            json.add(CATEGORY, new JsonPrimitive(category));
        }
        
        if (null != large_icon) {
            json.add(LARGE_ICON, new JsonPrimitive(large_icon));
        }

        if (null != intent) {
            json.add(INTENT, intent);
        }
        
        return json;
    }
    
    
    public static class Builder extends PlatformNotification.Builder<AndroidNotification, Builder> {
        private String title;
        private int builderId;
        private int style = 0;
        private int alert_type = -1;
        private String big_text;
        private Object inbox;
        private String big_pic_path;
        private int priority;
        private String category;
        private String large_icon;
        private JsonObject intent;
        
        protected Builder getThis() {
        	return this;
        }
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder setBuilderId(int builderId) {
            this.builderId = builderId;
            return this;
        }
        
        public Builder setAlert(Object alert) {
            this.alert = alert;
            return this;
        }

        public Builder setStyle(int style) {
            this.style = style;
            return this;
        }

        public Builder setAlertType(int alertType) {
            this.alert_type = alertType;
            return this;
        }

        public Builder setBigText(String bigText) {
            this.big_text = bigText;
            return this;
        }

        public Builder setBigPicPath(String bigPicPath) {
            this.big_pic_path = bigPicPath;
            return this;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setInbox(Object inbox) {
            if (null == inbox) {
                LOG.warn("Null inbox. Throw away it.");
                return this;
            }
            this.inbox = inbox;
            return this;
        }
        
        public Builder setLargeIcon(String largeIcon) {
            this.large_icon = largeIcon;
            return this;
        }
        
        public Builder setIntent(JsonObject intent) {
        	if (null == inbox) {
                LOG.warn("Null intent. Throw away it.");
                return this;
            }
            this.intent = intent;
            return this;
        }
        
        
        public AndroidNotification build() {
            return new AndroidNotification(alert, title, builderId, style, alert_type, big_text, inbox, big_pic_path, priority,
                    category, large_icon, intent,extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder, jsonExtrasBuilder);
        }
    }
}
