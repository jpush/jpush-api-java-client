package cn.jpush.api.push.model.live_activity;

import cn.jpush.api.push.model.PushModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class LiveActivity implements PushModel {

    private final Boolean apnsProduction;

    private final String liveActivityId;

    private final String iOSEvent;
    private final JsonObject iOSContentState;
    private final Long iOSDismissalDate;

    public LiveActivity(Boolean apnsProduction, String liveActivityId, String iOSEvent, JsonObject iOSContentState, Long iOSDismissalDate) {
        this.apnsProduction = apnsProduction;
        this.liveActivityId = liveActivityId;
        this.iOSEvent = iOSEvent;
        this.iOSContentState = iOSContentState;
        this.iOSDismissalDate = iOSDismissalDate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Boolean apnsProduction;
        private String liveActivityId;
        private String iOSEvent;
        private JsonObject iOSContentState;
        private Long iOSDismissalDate;

        public Builder apnsProduction(Boolean apnsProduction) {
            this.apnsProduction = apnsProduction;
            return this;
        }

        public Builder liveActivityId(String liveActivityId) {
            this.liveActivityId = liveActivityId;
            return this;
        }

        public Builder iOSEvent(LiveActivityEvent iOSEvent) {
            if (iOSEvent != null) {
                this.iOSEvent = iOSEvent.getValue();
            }
            return this;
        }

        public Builder iOSContentState(String key, String value) {
            if (this.iOSContentState == null) {
                this.iOSContentState = new JsonObject();
            }
            this.iOSContentState.addProperty(key, value);
            return this;
        }

        public Builder iOSContentState(String key, Number value) {
            if (this.iOSContentState == null) {
                this.iOSContentState = new JsonObject();
            }
            this.iOSContentState.addProperty(key, value);
            return this;
        }

        public Builder iOSContentState(String key, Boolean value) {
            if (this.iOSContentState == null) {
                this.iOSContentState = new JsonObject();
            }
            this.iOSContentState.addProperty(key, value);
            return this;
        }

        public Builder iOSDismissalDate(Long iOSDismissalDate) {
            this.iOSDismissalDate = iOSDismissalDate;
            return this;
        }

        public LiveActivity build() {
            return new LiveActivity(apnsProduction, liveActivityId, iOSEvent, iOSContentState, iOSDismissalDate);
        }

    }

    @Override
    public JsonElement toJSON() {
        JsonObject jsonObject = new JsonObject();

        JsonArray platformJsonArray = new JsonArray();
        platformJsonArray.add(new JsonPrimitive("ios"));

        JsonObject audienceJsonObject = new JsonObject();
        if (liveActivityId != null) {
            audienceJsonObject.addProperty("live_activity_id", liveActivityId);
        }

        JsonObject optionsJsonObject = new JsonObject();
        if (apnsProduction != null) {
            optionsJsonObject.addProperty("apns_production", apnsProduction);
        }

        JsonObject liveActivityJsonObject = new JsonObject();
        JsonObject iOSJsonObject = new JsonObject();
        JsonObject alertJsonObject = new JsonObject();

        if (iOSEvent != null) {
            iOSJsonObject.addProperty("event", iOSEvent);
        }
        if (iOSContentState != null) {
            iOSJsonObject.add("content-state", iOSContentState);
        }
        if (!alertJsonObject.entrySet().isEmpty()) {
            iOSJsonObject.add("alert", alertJsonObject);
        }
        if (!alertJsonObject.entrySet().isEmpty()) {
            iOSJsonObject.addProperty("dismissal-date", iOSDismissalDate);
        }

        if (!iOSJsonObject.entrySet().isEmpty()) {
            liveActivityJsonObject.add("ios", iOSJsonObject);
        }

        jsonObject.add("platform", platformJsonArray);
        jsonObject.add("audience", audienceJsonObject);
        jsonObject.add("live_activity", liveActivityJsonObject);
        jsonObject.add("options", optionsJsonObject);
        return jsonObject;
    }

}
