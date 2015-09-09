package cn.jpush.api.push.model.notification;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

public class WinphoneNotification extends PlatformNotification {
    private static final String NOTIFICATION_WINPHONE = "winphone";
    
    private static final String TITLE = "title";
    private static final String _OPEN_PAGE = "_open_page";
    
    private final String title;
    private final String openPage;
    
    private WinphoneNotification(Object alert, String title, String openPage,
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
    
    
    public static class Builder extends PlatformNotification.Builder<WinphoneNotification, Builder> {
        private String title;
        private String openPage;
        
        protected Builder getThis() {
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
        
        public Builder setAlert(Object alert) {
            this.alert = alert;
            return this;
        }

        
        public WinphoneNotification build() {
            return new WinphoneNotification(alert, title, openPage, 
            		extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder, jsonExtrasBuilder);
        }
    }
}
