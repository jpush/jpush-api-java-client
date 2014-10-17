package cn.jpush.api.push.model.audience;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.api.push.model.PushModel;
import cn.jpush.api.utils.Preconditions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class AudienceTarget implements PushModel {
    private final AudienceType audienceType;
    private final Set<String> values;
    
    private AudienceTarget(AudienceType audienceType, Set<String> values) {
        this.audienceType = audienceType;
        this.values = values;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static AudienceTarget tag(String... tag) {
        return newBuilder().setAudienceType(AudienceType.TAG).addAudienceTargetValues(tag).build();
    }
    
    public static AudienceTarget tag(Collection<String> tags) {
        return newBuilder().setAudienceType(AudienceType.TAG).addAudienceTargetValues(tags).build();
    }
    
    public static AudienceTarget tag_and(String... tag) {
        return newBuilder().setAudienceType(AudienceType.TAG_AND).addAudienceTargetValues(tag).build();
    }
    
    public static AudienceTarget tag_and(Collection<String> tags) {
        return newBuilder().setAudienceType(AudienceType.TAG_AND).addAudienceTargetValues(tags).build();
    }
    
    public static AudienceTarget alias(String... alias) {
        return newBuilder().setAudienceType(AudienceType.ALIAS).addAudienceTargetValues(alias).build();
    }
    
    public static AudienceTarget alias(Collection<String> aliases) {
        return newBuilder().setAudienceType(AudienceType.ALIAS).addAudienceTargetValues(aliases).build();
    }

    public static AudienceTarget registrationId(String... registrationId) {
        return newBuilder().setAudienceType(AudienceType.REGISTRATION_ID).addAudienceTargetValues(registrationId).build();
    }
    
    public static AudienceTarget registrationId(Collection<String> registrationIds) {
        return newBuilder().setAudienceType(AudienceType.REGISTRATION_ID).addAudienceTargetValues(registrationIds).build();
    }
    
    
    public AudienceType getAudienceType() {
        return this.audienceType;
    }
    
    public String getAudienceTypeValue() {
        return this.audienceType.value();
    }
    
    public JsonElement toJSON() {
        JsonArray array = new JsonArray();
		if (null != values) {
			for (String value : values) {
				array.add(new JsonPrimitive(value));
			}
		}
        return array;
    }
    
    
    public static class Builder {
        private AudienceType audienceType = null;
        private Set<String> valueBuilder = null;
        
        public Builder setAudienceType(AudienceType audienceType) {
            this.audienceType = audienceType;
            return this;
        }
        
        public Builder addAudienceTargetValue(String value) {
            if (null == valueBuilder) {
                valueBuilder = new HashSet<String>();
            }
            valueBuilder.add(value);
            return this;
        }
        
        public Builder addAudienceTargetValues(Collection<String> values) {
            if (null == valueBuilder) {
                valueBuilder = new HashSet<String>();
            }
            for (String value : values) {
                valueBuilder.add(value);
            }
            return this;
        }
        
        public Builder addAudienceTargetValues(String... values) {
            if (null == valueBuilder) {
                valueBuilder = new HashSet<String>();
            }
            for (String value : values) {
                valueBuilder.add(value);
            }
            return this;
        }
        
        public AudienceTarget build() {
            Preconditions.checkArgument(null != audienceType, "AudienceType should be set.");
            Preconditions.checkArgument(null != valueBuilder, "Target values should be set one at least.");
            return new AudienceTarget(audienceType, valueBuilder);
        }
    }
}
