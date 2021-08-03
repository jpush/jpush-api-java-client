package cn.jpush.api.push.model.notification;

import java.util.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.push.model.PushModel;

public class Notification implements PushModel {
    private static final String AI_OPPORTUNITY = "ai_opportunity";
    private static final String VOIP = "voip";

    // default is false
    private boolean aiOpportunity;
    private final Object alert;
    private final Set<PlatformNotification> notifications;
    private final Map<String, String> voip;
    private final Map<String, Number> numberVoip;
    private final Map<String, Boolean> booleanVoip;
    private final Map<String, JsonObject> jsonVoip;
    
    private Notification(boolean aiOpportunity, Object alert, Set<PlatformNotification> notifications,
                         Map<String, String> voip, Map<String, Number> numberVoip, Map<String, Boolean> booleanVoip,
                         Map<String, JsonObject> jsonVoip) {
        this.aiOpportunity = aiOpportunity;
        this.alert = alert;
        this.notifications = notifications;
        this.voip = voip;
        this.numberVoip = numberVoip;
        this.booleanVoip = booleanVoip;
        this.jsonVoip = jsonVoip;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }

    public static Notification aiOpportunity(boolean ai_opportunity) {
        return newBuilder().setAiOpportunity(ai_opportunity).build();
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

        json.addProperty(AI_OPPORTUNITY, aiOpportunity);

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


        JsonObject extrasObject = null;
        if (null != voip || null != numberVoip || null != booleanVoip || null != jsonVoip) {
            extrasObject = new JsonObject();
        }

        if (null != voip) {
            String value = null;
            for (String key : voip.keySet()) {
                value = voip.get(key);
                if (null != value) {
                    extrasObject.add(key, new JsonPrimitive(value));
                }
            }
        }
        if (null != numberVoip) {
            Number value = null;
            for (String key : numberVoip.keySet()) {
                value = numberVoip.get(key);
                if (null != value) {
                    extrasObject.add(key, new JsonPrimitive(value));
                }
            }
        }
        if (null != booleanVoip) {
            Boolean value = null;
            for (String key : booleanVoip.keySet()) {
                value = booleanVoip.get(key);
                if (null != value) {
                    extrasObject.add(key, new JsonPrimitive(value));
                }
            }
        }
        if (null != jsonVoip) {
            JsonObject value = null;
            for (String key : jsonVoip.keySet()) {
                value = jsonVoip.get(key);
                if (null != value) {
                    extrasObject.add(key, value);
                }
            }
        }

        if (null != voip || null != numberVoip || null != booleanVoip || null != jsonVoip) {
            json.add(VOIP, extrasObject);
        }

        return json;
    }

    public static class Builder {
        private boolean aiOpportunity;
        private Object alert;
        private Set<PlatformNotification> builder;
        protected Map<String, String> voipBuilder;
        protected  Map<String, Number> numberVoipBuilder;
        protected  Map<String, Boolean> booleanVoipBuilder;
        protected  Map<String, JsonObject> jsonVoipBuilder;



        public Builder setAiOpportunity(boolean aiOpportunity) {
            this.aiOpportunity = aiOpportunity;
            return this;
        }

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

        public Builder addVoip(String key, String value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == voipBuilder) {
                voipBuilder = new HashMap<String, String>();
            }
            voipBuilder.put(key, value);
            return this;
        }

        public Builder addVoip(Map<String, String> voip) {
            Preconditions.checkArgument(! (null == voip), "extras should not be null.");
            if (null == voipBuilder) {
                voipBuilder = new HashMap<String, String>();
            }
            for (String key : voip.keySet()) {
                voipBuilder.put(key, voip.get(key));
            }
            return this;
        }

        public Builder addVoip(String key, Number value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == numberVoipBuilder) {
                numberVoipBuilder = new HashMap<String, Number>();
            }
            numberVoipBuilder.put(key, value);
            return this;
        }

        public Builder addVoip(String key, Boolean value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == booleanVoipBuilder) {
                booleanVoipBuilder = new HashMap<String, Boolean>();
            }
            booleanVoipBuilder.put(key, value);
            return this;
        }

        public Builder addVoip(String key, JsonObject value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == jsonVoipBuilder) {
                jsonVoipBuilder = new HashMap<String, JsonObject>();
            }
            jsonVoipBuilder.put(key, value);
            return this;
        }
        
        public Notification build() {
            Preconditions.checkArgument(! (null == builder && null == alert),
                    "No notification payload is set.");
            return new Notification(aiOpportunity, alert, builder, voipBuilder, numberVoipBuilder, booleanVoipBuilder
            , jsonVoipBuilder);
        }
    }
}

