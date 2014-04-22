package cn.jpush.api.push.model;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PushPayload implements PushModel {
    private final Platform platform;
    private final Audience audience;
    private final Notification notification;
    private final Message message;
    private final Optional optional;
    
    private PushPayload(Platform platform, Audience audience, 
            Notification notification, Message message, Optional optional) {
        this.platform = platform;
        this.audience = audience;
        this.notification = notification;
        this.message = message;
        this.optional = optional;
    }
    
    public static Builder newBuilder() {
        return new Builder();
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
            json.add(Optional.OPTIONAL, optional.toJSON());
        }
        return json;
    }
    
    public static class Builder {
        private Platform platform = Platform.all();
        private Audience audience = null;
        private Notification notification = null;
        private Message message = null;
        private Optional optional = null;
        
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
        
        public Builder setOptional(Optional optional) {
            this.optional = optional;
            return this;
        }
        
        public PushPayload build() {
            Preconditions.checkArgument(null != audience, "Audience should be set.");
            Preconditions.checkArgument(! (null == notification && null == message), "notification or message should be set at least one.");
            return new PushPayload(platform, audience, notification, message, optional);
        }
    }
}

