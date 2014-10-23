package cn.jpush.api.push.model;

import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.utils.Preconditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Options implements PushModel {
    private static final String SENDNO = "sendno";
    private static final String OVERRIDE_MSG_ID = "override_msg_id";
    private static final String TIME_TO_LIVE = "time_to_live";
    private static final String APNS_PRODUCTION = "apns_production";
    private static final String BIG_PUSH_DURATION = "big_push_duration";
    
    private static final long NONE_TIME_TO_LIVE = -1;
    
    private final int sendno;
    private final long overrideMsgId;
    private long timeToLive;
    private boolean apnsProduction;
    private int bigPushDuration;	// minutes
    
    private Options(int sendno, long overrideMsgId, long timeToLive, boolean apnsProduction, 
    		int bigPushDuration) {
        this.sendno = sendno;
        this.overrideMsgId = overrideMsgId;
        this.timeToLive = timeToLive;
        this.apnsProduction = apnsProduction;
        this.bigPushDuration = bigPushDuration;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Options sendno() {
        return newBuilder().setSendno(ServiceHelper.generateSendno()).build();
    }
    
    public static Options sendno(int sendno) {
        return newBuilder().setSendno(sendno).build();
    }
    
    public void setApnsProduction(boolean apnsProduction) {
        this.apnsProduction = apnsProduction;
    }
    
    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }
    
    public void setBigPushDuration(int bigPushDuration) {
    	this.bigPushDuration = bigPushDuration;
    }
    
    public int getSendno() {
        return this.sendno;
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
        if (timeToLive >= 0) {
            json.add(TIME_TO_LIVE, new JsonPrimitive(timeToLive));
        }
        
        json.add(APNS_PRODUCTION, new JsonPrimitive(apnsProduction));
        
        if (bigPushDuration > 0) {
        	json.add(BIG_PUSH_DURATION,  new JsonPrimitive(bigPushDuration));
        }
        
        return json;
    }
    
    public static class Builder {
        private int sendno = 0;
        private long overrideMsgId = 0;
        private long timeToLive = NONE_TIME_TO_LIVE;
        private boolean apnsProduction = false;
        private int bigPushDuration = 0;
        
        public Builder setSendno(int sendno) {
            this.sendno = sendno;
            return this;
        }
        
        public Builder setOverrideMsgId(long overrideMsgId) {
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
        
        public Builder setBigPushDuration(int bigPushDuration) {
        	this.bigPushDuration = bigPushDuration;
        	return this;
        }
        
        public Options build() {
            Preconditions.checkArgument(sendno >= 0, "sendno should be greater than 0.");
            Preconditions.checkArgument(overrideMsgId >= 0, "override_msg_id should be greater than 0.");
            Preconditions.checkArgument(timeToLive >= NONE_TIME_TO_LIVE, "time_to_live should be greater than 0.");
            Preconditions.checkArgument(bigPushDuration >= 0, "bigPushDuration should be greater than 0.");
            if (sendno <= 0) {
                sendno = ServiceHelper.generateSendno();
            }
            
            return new Options(sendno, overrideMsgId, timeToLive, apnsProduction, bigPushDuration);
        }
    }

}
