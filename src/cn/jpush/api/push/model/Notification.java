package cn.jpush.api.push.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Notification implements PushModel {
    public static final String NOTIFICATION = "notification";
    
    private final String alert;
    private final ImmutableSet<PlatformNotification> notifications;
    
    private Notification(String alert, ImmutableSet<PlatformNotification> notifications) {
        this.alert = alert;
        this.notifications = notifications;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    /**
     * Quick set all platform alert
     * 
     * @param alert Notification alert
     * @return first level notification object
     */
    public static Notification alert(String alert) {
        return newBuilder()
                .addPlatformNotification(AndroidNotification.alert(alert))
                .setAlert(alert).build();
    }
    
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (null != alert) {
            json.add(PlatformNotification.ALERT, new JsonPrimitive(alert));
        }
        for (PlatformNotification pn : notifications) {
            if (this.alert != null && pn.getAlert() == null) {
                pn.setAlert(this.alert);
            }
            json.add(pn.getPlatform(), pn.toJSON());
        }
        return json;
    }
    
    public static class Builder {
        private String alert;
        private ImmutableSet.Builder<PlatformNotification> builder;
        
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }
        
        public Builder addPlatformNotification(PlatformNotification notification) {
            if (null == builder) {
                builder = ImmutableSet.builder();
            }
            builder.add(notification);
            return this;
        }
        
        public Notification build() {
            Preconditions.checkArgument(! (null == builder), "Should set at least one platform notification");
            return new Notification(alert, builder.build());
        }
    }
}

