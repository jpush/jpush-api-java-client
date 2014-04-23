package cn.jpush.api.push.model.audience;

import java.util.Collection;

import cn.jpush.api.push.model.PushModel;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class AudienceTarget implements PushModel {
    private final AudienceType audienceType;
    private final ImmutableSet<String> values;
    
    private AudienceTarget(AudienceType audienceType, ImmutableSet<String> values) {
        this.audienceType = audienceType;
        this.values = values;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public AudienceType getAudienceType() {
        return this.audienceType;
    }
    
    public String getAudienceTypeValue() {
        return this.audienceType.value();
    }
    
    public JsonElement toJSON() {
        JsonArray array = new JsonArray();
        boolean numberValue = false;
        if (audienceType == AudienceType.REGISTRATION_ID 
                || audienceType == AudienceType.SEGMENT) {
            numberValue = true;
        }
        
        try {
            for (String value : values) {
                int number = 0;
                if (numberValue) {
                    number = Integer.parseInt(value);
                    array.add(new JsonPrimitive(number));
                } else {
                    array.add(new JsonPrimitive(value));
                }
            }
        } catch (NumberFormatException e) {
            Preconditions.checkArgument(true, "Value of registration_id/segment should be int.");
        }
        return array;
    }
    
    
    public static class Builder {
        private AudienceType audienceType = null;
        private ImmutableSet.Builder<String> valueBuilder = null;
        
        public Builder setAudienceType(AudienceType audienceType) {
            this.audienceType = audienceType;
            return this;
        }
        
        public Builder addAudienceTargetValue(String value) {
            if (null == valueBuilder) {
                valueBuilder = ImmutableSet.builder();
            }
            valueBuilder.add(value);
            return this;
        }
        
        public Builder addAudienceTargetValues(Collection<String> values) {
            if (null == valueBuilder) {
                valueBuilder = ImmutableSet.builder();
            }
            for (String value : values) {
                valueBuilder.add(value);
            }
            return this;
        }
        
        public Builder addAudienceTargetValues(String... values) {
            if (null == valueBuilder) {
                valueBuilder = ImmutableSet.builder();
            }
            for (String value : values) {
                valueBuilder.add(value);
            }
            return this;
        }
        
        public AudienceTarget build() {
            Preconditions.checkArgument(null != audienceType, "AudienceType should be set.");
            Preconditions.checkArgument(null != valueBuilder, "Target values should be set one at least.");
            ImmutableSet<String> values = valueBuilder.build();
            return new AudienceTarget(audienceType, values);
        }
    }
}
