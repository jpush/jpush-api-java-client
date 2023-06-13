package cn.jpush.api.push.model.live_activity;

import cn.jpush.api.push.model.PushModel;
import com.google.gson.*;

public class LiveActivity implements PushModel {

    private final Boolean apnsProduction;

    private final String liveActivityId;

    private final String iOSEvent;
    private final JsonObject iOSContentState;
    private final Long iOSDismissalDate;
    private final JsonObject iOSAlert;

    public LiveActivity(Boolean apnsProduction, String liveActivityId, String iOSEvent, JsonObject iOSContentState, Long iOSDismissalDate, JsonObject iOSAlert) {
        this.apnsProduction = apnsProduction;
        this.liveActivityId = liveActivityId;
        this.iOSEvent = iOSEvent;
        this.iOSContentState = iOSContentState;
        this.iOSDismissalDate = iOSDismissalDate;
        this.iOSAlert = iOSAlert;
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
        private JsonObject iOSAlert;

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

        public Builder iOSAlertTitle(String iosAlertTitle) {
            if (this.iOSAlert == null) {
                this.iOSAlert = new JsonObject();
            }
            this.iOSAlert.addProperty("title", iosAlertTitle);
            return this;
        }

        public Builder iOSAlertAlternateTitle(String iosAlertAlternateTitle) {
            if (this.iOSAlert == null) {
                this.iOSAlert = new JsonObject();
            }
            this.iOSAlert.addProperty("alternate_title", iosAlertAlternateTitle);
            return this;
        }

        public Builder iOSAlertBody(String iosAlertBody) {
            if (this.iOSAlert == null) {
                this.iOSAlert = new JsonObject();
            }
            this.iOSAlert.addProperty("body", iosAlertBody);
            return this;
        }

        public Builder iOSAlertAlternateBody(String iosAlertAlternateBody) {
            if (this.iOSAlert == null) {
                this.iOSAlert = new JsonObject();
            }
            this.iOSAlert.addProperty("alternate_body", iosAlertAlternateBody);
            return this;
        }

        public Builder iOSAlertSound(String iosAlertSound) {
            if (this.iOSAlert == null) {
                this.iOSAlert = new JsonObject();
            }
            this.iOSAlert.addProperty("sound", iosAlertSound);
            return this;
        }

        public LiveActivity build() {
            return new LiveActivity(apnsProduction, liveActivityId, iOSEvent, iOSContentState, iOSDismissalDate, iOSAlert);
        }

    }

    @Override
    public JsonElement toJSON() {
        JsonObject pushJsonObject = new JsonObject();

        JsonArray platformJsonArray = new JsonArray();
        platformJsonArray.add(new JsonPrimitive("ios"));

        JsonObject audienceJsonObject = new JsonObject();
        if (liveActivityId != null) {
            audienceJsonObject.addProperty("live_activity_id", liveActivityId);
        }

        JsonObject liveActivityJsonObject = new JsonObject();
        JsonObject iOSJsonObject = new JsonObject();

        if (iOSEvent != null) {
            iOSJsonObject.addProperty("event", iOSEvent);
        }

        if (iOSContentState != null) {
            iOSJsonObject.add("content-state", iOSContentState);
        }

        if (iOSDismissalDate != null) {
            iOSJsonObject.addProperty("dismissal-date", iOSDismissalDate);
        }

        if (iOSAlert != null) {
            iOSJsonObject.add("alert", iOSAlert);
        }

        if (!iOSJsonObject.entrySet().isEmpty()) {
            liveActivityJsonObject.add("ios", iOSJsonObject);
        }

        JsonObject optionsJsonObject = new JsonObject();
        if (apnsProduction != null) {
            optionsJsonObject.addProperty("apns_production", apnsProduction);
        }
        if (iOSAlert != null) {
            optionsJsonObject.addProperty("alternate_set", true);
        }

        pushJsonObject.add("platform", platformJsonArray);
        pushJsonObject.add("audience", audienceJsonObject);
        pushJsonObject.add("live_activity", liveActivityJsonObject);
        pushJsonObject.add("options", optionsJsonObject);
        return pushJsonObject;
    }

}
