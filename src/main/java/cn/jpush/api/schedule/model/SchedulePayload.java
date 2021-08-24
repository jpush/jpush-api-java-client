package cn.jpush.api.schedule.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.jiguang.common.utils.StringUtils;
import cn.jpush.api.push.model.PushPayload;

public class SchedulePayload implements IModel {

    private static final String CID = "cid";
    private static final String NAME = "name";
    private static final String ENABLED = "enabled";
    private static final String TRIGGER = "trigger";
    private static final String PUSH = "push";

    private static Gson gson = new Gson();

    private String cid;
    private String name;
    private Boolean enabled;
    private TriggerPayload trigger;
    private PushPayload push;

    private SchedulePayload(String cid, String name, Boolean enabled, TriggerPayload trigger, PushPayload push) {
        this.cid = cid;
        this.name = name;
        this.enabled = enabled;
        this.trigger = trigger;
        this.push = push;
    }

    public SchedulePayload setCid(String cid) {
        this.cid = cid;
        return this;
    }

    public String getCid() {
        return cid;
    }

    /**
     *
     * The entrance for building a SchedulePayload object.
     * @return SchedulePayload builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if ( null != cid ) {
            json.addProperty(CID, cid);
        }
        if ( StringUtils.isNotEmpty(name) ) {
            json.addProperty(NAME, name);
        }
        if ( null != enabled ) {
            json.addProperty(ENABLED, enabled);
        }
        if ( null != trigger ) {
            json.add(TRIGGER, trigger.toJSON());
        }
        if ( null != push ) {
            json.add(PUSH, push.toJSON());
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
        private String cid;
        private String name;
        private Boolean enabled;
        private TriggerPayload trigger;
        private PushPayload push;

        public Builder setCid(String cid) {
            this.cid = cid;
            return this;
        }

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

            return new SchedulePayload(cid, name, enabled, trigger, push);
        }
    }
}
