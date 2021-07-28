package cn.jpush.api.push.model;

import cn.jiguang.common.utils.Preconditions;

import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class InappMessage implements PushModel{

    private boolean inappMessage;

    private InappMessage(boolean inappMessage) {
        this.inappMessage = inappMessage;
    }

    public static Builder newBuilder() { return new Builder(); }

    public static InappMessage inappMessage(boolean inappMessage) {
        return newBuilder().setInappMessage(inappMessage).build();
    }


    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();

        json.addProperty("inapp_message", inappMessage);

        return json;
    }


    public static class Builder {
        private boolean inappMessage;

        public Builder setInappMessage(boolean inappMessage) {
            this.inappMessage = inappMessage;
            return this;
        }

        public InappMessage build() {
            return new InappMessage(inappMessage);
        }
    }
}
