package cn.jpush.api.push.model;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Options implements PushModel {
    public static final String OPTIONAL = "optional";
    
    public static final String SENDNO = "sendno";
    public static final String OVERRIDE_MSG_ID = "override_msg_id";
    public static final String TIME_TO_LIVE = "time_to_live";
    public static final String APNS_PRODUCTION = "apns_production";
    
    private final int sendno;
    private final int overrideMsgId;
    private long timeToLive;
    private boolean apnsProduction;
    
    private Options(int sendno, int overrideMsgId, long timeToLive, boolean apnsProduction) {
        this.sendno = sendno;
        this.overrideMsgId = overrideMsgId;
        this.timeToLive = timeToLive;
        this.apnsProduction = apnsProduction;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public void setApnsProduction(boolean apnsProduction) {
        this.apnsProduction = apnsProduction;
    }
    
    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }
    
    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (sendno > 0) {
            json.add(SENDNO, new JsonPrimitive(sendno));
        }
        if (overrideMsgId > 0) {
            json.add(OVERRIDE_MSG_ID, new JsonPrimitive(overrideMsgId));
        }
        if (timeToLive > 0) {
            json.add(TIME_TO_LIVE, new JsonPrimitive(timeToLive));
        }
        if (!apnsProduction) {
            json.add(APNS_PRODUCTION, new JsonPrimitive(apnsProduction));
        }
        return json;
    }
    
    public static class Builder {
        private int sendno = 0;
        private int overrideMsgId = 0;
        private long timeToLive = 0;
        private boolean apnsProduction = true;
        
        public Builder setSendno(int sendno) {
            this.sendno = sendno;
            return this;
        }
        
        public Builder setOverrideMsgId(int overrideMsgId) {
            this.overrideMsgId = overrideMsgId;
            return this;
        }
        
        public Builder setTimeToLive(long timeToLive) {
            this.timeToLive = timeToLive;
            return this;
        }
        
        public Builder setApnsProduction(boolean apnsProduction) {
            this.apnsProduction = apnsProduction;
            return this;
        }
        
        public Options build() {
            Preconditions.checkArgument(sendno >= 0, "sendno should be greater than 0.");
            Preconditions.checkArgument(overrideMsgId >= 0, "override_msg_id should be greater than 0.");
            Preconditions.checkArgument(timeToLive >= 0, "time_to_live should be greater than 0.");
            return new Options(sendno, overrideMsgId, timeToLive, apnsProduction);
        }
    }

}
