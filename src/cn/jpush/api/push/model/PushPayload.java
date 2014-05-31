package cn.jpush.api.push.model;

import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The object you should to build for sending a push.
 * 
 * Basically start with newBuilder() method to build a PushPayload object.
 * alertAll() is a shortcut for quickly build payload of alert to all platform and all audience;
 * mesageAll() is a shortcut for quickly build payload of message to all platform and all audience.
 * 
 */
public class PushPayload implements PushModel {
    private static final String PLATFORM = "platform";
    private static final String AUDIENCE = "audience";
    private static final String NOTIFICATION = "notification";
    private static final String MESSAGE = "message";
    private static final String OPTIONS = "options";

    private static Gson _gson = new Gson();
    
    private final Platform platform;
    private final Audience audience;
    private final Notification notification;
    private final Message message;
    private Options options;
    
    
    private PushPayload(Platform platform, Audience audience, 
            Notification notification, Message message, Options options) {
        this.platform = platform;
        this.audience = audience;
        this.notification = notification;
        this.message = message;
        this.options = options;
    }
    
    /**
     * The entrance for building a PushPayload object.
     */
    public static Builder newBuilder() {
        return new Builder();
    }
    
    /**
     * The shortcut of building a simple alert notification object to all platforms and all audiences
     */
    public static PushPayload alertAll(String alert) {
        return new Builder()
            .setPlatform(Platform.all())
            .setAudience(Audience.all())
            .setNotification(Notification.alert(alert)).build();
    }
    
    /**
     * The shortcut of building a simple message object to all platforms and all audiences
     */
    public static PushPayload messageAll(String msgContent) {
        return new Builder()
            .setPlatform(Platform.all())
            .setAudience(Audience.all())
            .setMessage(Message.content(msgContent)).build();
    }
    
    public static PushPayload fromJSON(String payloadString) {
        return _gson.fromJson(payloadString, PushPayload.class);
    }
    
    public void resetOptionsApnsProduction(boolean apnsProduction) {
        if (null == options) {
            options = Options.newBuilder().setApnsProduction(apnsProduction).build();
        } else {
            options.setApnsProduction(apnsProduction);
        }
    }
    
    public void resetOptionsTimeToLive(long timeToLive) {
        if (null == options) {
            options = Options.newBuilder().setTimeToLive(timeToLive).build();
        } else {
            options.setTimeToLive(timeToLive);
        }
    }
    
    public int getSendno() {
        if (null != options) {
            return options.getSendno();
        }
        return 0;
    }
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (null != platform) {
            json.add(PLATFORM, platform.toJSON());
        }
        if (null != audience) {
            json.add(AUDIENCE, audience.toJSON());
        }
        if (null != notification) {
            json.add(NOTIFICATION, notification.toJSON());
        }
        if (null != message) {
            json.add(MESSAGE, message.toJSON());
        }
        if (null != options) {
            json.add(OPTIONS, options.toJSON());
        }
        return json;
    }
    
    @Override
    public String toString() {
        return _gson.toJson(toJSON());
    }
    
    public static class Builder {
        private Platform platform = null;
        private Audience audience = null;
        private Notification notification = null;
        private Message message = null;
        private Options options = null;
        
        public Builder setPlatform(Platform platform) {
            this.platform = platform;
            return this;
        }
        
        public Builder setAudience(Audience audience) {
            this.audience = audience;
            return this;
        }
        
        public Builder setNotification(Notification notification) {
            this.notification = notification;
            return this;
        }
        
        public Builder setMessage(Message message) {
            this.message = message;
            return this;
        }
        
        public Builder setOptions(Options options) {
            this.options = options;
            return this;
        }
        
        public PushPayload build() {
            Preconditions.checkArgument(! (null == audience || null == platform), "Audience/Platform should be set.");
            Preconditions.checkArgument(! (null == notification && null == message), "notification or message should be set at least one.");
            if (null == options) {
                options = Options.sendno();
            }
            
            return new PushPayload(platform, audience, notification, message, options);
        }
    }
}

