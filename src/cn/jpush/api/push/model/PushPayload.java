package cn.jpush.api.push.model;

import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PushPayload implements PushModel {
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
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static PushPayload notificationAlertAll(String alert) {
        return new Builder()
            .setAudience(Audience.all())
            .setNotification(Notification.alert(alert)).build();
    }
    
    public static PushPayload simpleMessageAll(String content) {
        return new Builder()
            .setAudience(Audience.all())
            .setMessage(Message.content(content)).build();
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
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        json.add(Platform.PLATFORM, platform.toJSON());
        json.add(Audience.AUDIENCE, audience.toJSON());
        if (null != notification) {
            json.add(Notification.NOTIFICATION, notification.toJSON());
        }
        if (null != message) {
            json.add(Message.MESSAGE, message.toJSON());
        }
        if (null != message) {
            json.add(Options.OPTIONAL, options.toJSON());
        }
        return json;
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
            Preconditions.checkArgument(! (null == audience || null == platform), "Audience should be set.");
            Preconditions.checkArgument(! (null == notification && null == message), "notification or message should be set at least one.");
            return new PushPayload(platform, audience, notification, message, options);
        }
    }
}

