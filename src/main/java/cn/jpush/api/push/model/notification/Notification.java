package cn.jpush.api.push.model.notification;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.push.model.PushModel;

public class Notification implements PushModel {    
    private final Object alert;
    private final Set<PlatformNotification> notifications;
    
    private Notification(Object alert, Set<PlatformNotification> notifications) {
        this.alert = alert;
        this.notifications = notifications;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    /**
     * Quick set all platform alert. 
     * Platform notification can override this alert. 
     * 
     * @param alert Notification alert
     * @return first level notification object
     */
    public static Notification alert(Object alert) {
        return newBuilder().setAlert(alert).build();
    }

    public static Notification android(String alert, String title, Map<String, String> extras) {
        return newBuilder()
                .addPlatformNotification(AndroidNotification.newBuilder()
                    .setAlert(alert)
                    .setTitle(title)
                    .addExtras(extras)
                    .build())
                .build();
    }

    public static Notification ios(Object alert, Map<String, String> extras) {
        return newBuilder()
                .addPlatformNotification(IosNotification.newBuilder()
                    .setAlert(alert)
                    .addExtras(extras)
                    .build())
                .build();
    }

    public static Notification ios_auto_badge() {
        return newBuilder()
                .addPlatformNotification(IosNotification.newBuilder()
                    .setAlert("")
                    .autoBadge()
                    .build())
                .build();
    }

    public static Notification ios_set_badge(int badge) {
        return newBuilder()
                .addPlatformNotification(IosNotification.newBuilder()
                    .setAlert("")
                    .setBadge(badge)
                    .build())
                .build();
    }

    public static Notification ios_incr_badge(int badge) {
        return newBuilder()
                .addPlatformNotification(IosNotification.newBuilder()
                    .setAlert("")
                    .incrBadge(badge)
                    .build())
                .build();
    }

    public static Notification winphone(String alert, Map<String, String> extras) {
        return newBuilder()
                .addPlatformNotification(WinphoneNotification.newBuilder()
                    .setAlert(alert)
                    .addExtras(extras)
                    .build())
                .build();
    }
    
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (null != alert) {
            if(alert instanceof JsonObject) {
                json.add(PlatformNotification.ALERT, (JsonObject) alert);
            } else if (alert instanceof IosAlert) {
                json.add(PlatformNotification.ALERT, ((IosAlert) alert).toJSON());
            } else {
                json.add(PlatformNotification.ALERT, new JsonPrimitive(alert.toString()));
            }
        }
        if (null != notifications) {
            for (PlatformNotification pn : notifications) {
                if (this.alert != null && pn.getAlert() == null) {
                    pn.setAlert(this.alert);
                }
                
                Preconditions.checkArgument(! (null == pn.getAlert()), 
                        "For any platform notification, alert field is needed. It can be empty string.");

                json.add(pn.getPlatform(), pn.toJSON());
            }
        }
        return json;
    }

    public static class Builder {
        private Object alert;
        private Set<PlatformNotification> builder;
        
        public Builder setAlert(Object alert) {
            this.alert = alert;
            return this;
        }
        
        public Builder addPlatformNotification(PlatformNotification notification) {
            if (null == builder) {
                builder = new HashSet<PlatformNotification>();
            }
            builder.add(notification);
            return this;
        }
        
        public Notification build() {
            Preconditions.checkArgument(! (null == builder && null == alert), 
                    "No notification payload is set.");
            return new Notification(alert, builder);
        }
    }
}

