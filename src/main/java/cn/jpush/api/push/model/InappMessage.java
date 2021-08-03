package cn.jpush.api.push.model;

import cn.jiguang.common.utils.Preconditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * inapp_message 此功能生效需Android push SDK≥V3.9.0、iOS push SDK≥V3.4.0，若低于此版本按照原流程执行。
 *
 * 默认值为false
 */
public class InappMessage implements PushModel{

    private boolean inappMessage;

    private InappMessage(boolean inappMessage) {
        this.inappMessage = inappMessage;
    }

    /**
     * the entrance for building a inappMessage object
     * @return inappMessage builder
     */
    public static Builder newBuilder() { return new Builder(); }

    public boolean getInappMessage() { return inappMessage; }


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
