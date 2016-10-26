package cn.jpush.api.schedule.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.jiguang.common.utils.StringUtils;
import cn.jpush.api.push.model.PushPayload;

public class SchedulePayload implements IModel {

    private static Gson gson = new Gson();

    private String name;
    private Boolean enabled;
    private TriggerPayload trigger;
    private PushPayload push;

    private SchedulePayload(String name, Boolean enabled, TriggerPayload trigger, PushPayload push) {
        this.name = name;
        this.enabled = enabled;
        this.trigger = trigger;
        this.push = push;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if ( StringUtils.isNotEmpty(name) ) {
            json.addProperty("name", name);
        }
        if ( null != enabled ) {
            json.addProperty("enabled", enabled);
        }
        if ( null != trigger ) {
            json.add("trigger", trigger.toJSON());
        }
        if ( null != push ) {
            json.add("push", push.toJSON());
        }
        return json;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    public void resetPushApnsProduction(boolean apnsProduction) {
        if(null != push) {
            push.resetOptionsApnsProduction(apnsProduction);
        }
    }

    public void resetPushTimeToLive(long timeToLive) {
        if(null != push) {
            push.resetOptionsTimeToLive(timeToLive);
        }
    }

    public static class Builder{
        private String name;
        private Boolean enabled;
        private TriggerPayload trigger;
        private PushPayload push;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder setTrigger(TriggerPayload trigger) {
            this.trigger = trigger;
            return this;
        }

        public Builder setPush(PushPayload push) {
            this.push = push;
            return this;
        }

        public SchedulePayload build() {

            return new SchedulePayload(name, enabled, trigger, push);
        }
    }
}
