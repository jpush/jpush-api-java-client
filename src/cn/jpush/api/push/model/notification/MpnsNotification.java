package cn.jpush.api.push.model.notification;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class MpnsNotification extends PlatformNotification {
    public static final String NOTIFICATION_MPNS = "mpns";
    
    public static final String TITLE = "title";
    public static final String EXTRAS = "extras";
    public static final String _OPEN_PAGE = "_open_page";
    
    private final String title;
    private final String openPage;
    private final ImmutableMap<String, String> extras;
    
    private MpnsNotification(String alert, String title, String openPage, 
            ImmutableMap<String, String> extras) {
        super(alert);
        this.title = title;
        this.openPage = openPage;
        this.extras = extras;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static MpnsNotification alert(String alert) {
        return newBuilder().setAlert(alert).build();
    }
    
    
    @Override
    public String getPlatform() {
        return NOTIFICATION_MPNS;
    }
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (null != alert) {
            json.add(ALERT, new JsonPrimitive(this.alert));
        }
        if (null != title) {
            json.add(TITLE, new JsonPrimitive(title));
        }
        if (null != openPage) {
            json.add(_OPEN_PAGE, new JsonPrimitive(openPage));
        }
        if (null != extras) {
            JsonObject extrasObject = new JsonObject();
            for (String key : extras.keySet()) {
                extrasObject.add(key, new JsonPrimitive(extras.get(key)));
            }
            json.add(EXTRAS, extrasObject);
        }
        
        Preconditions.checkArgument(
                ! (null == alert && null == title && null == extras)
                , "No any notification params are set.");

        return json;
    }
    
    
    public static class Builder {
        private String alert;
        private String title;
        private String openPage;
        private ImmutableMap.Builder<String, String> extrasBuilder;
        
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder setOpenPage(String openPage) {
            this.openPage = openPage;
            return this;
        }
        
        public Builder addExtra(Map<String, String> extra) {
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            extrasBuilder.putAll(extra);
            return this;
        }
        
        public Builder addExtra(String key, String value) {
            if (null == extrasBuilder) {
                extrasBuilder = ImmutableMap.builder();
            }
            extrasBuilder.put(key, value);
            return this;
        }
        
        public MpnsNotification build() {
            return new MpnsNotification(alert, title, openPage, 
                    (null == extrasBuilder) ? null : extrasBuilder.build());
        }
    }
}
