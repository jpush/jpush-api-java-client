package cn.jpush.api.push.model.audience;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.push.model.PushModel;

public class Audience implements PushModel {
    private static final String ALL = "all";
    
    private final boolean all;
    private final Set<AudienceTarget> targets;
    
    private Audience(boolean all, Set<AudienceTarget> targets) {
        this.all = all;
        this.targets = targets;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Audience all() {
        return newBuilder().setAll(true).build();
    }
    
    public static Audience tag(String... tagValue) {
        AudienceTarget target = AudienceTarget.tag(tagValue);
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience tag(Collection<String> tagValues) {
        AudienceTarget target = AudienceTarget.tag(tagValues);
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience tag_and(String... tagValue) {
        AudienceTarget target = AudienceTarget.tag_and(tagValue);
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience tag_and(Collection<String> tagValues) {
        AudienceTarget target = AudienceTarget.tag_and(tagValues);
        return newBuilder().addAudienceTarget(target).build();
    }

    public static Audience tag_not(String...tagValue) {
        AudienceTarget target = AudienceTarget.tag_not(tagValue);
        return newBuilder().addAudienceTarget(target).build();
    }

    public static Audience tag_not(Collection<String> tagValues) {
        AudienceTarget target = AudienceTarget.tag_not(tagValues);
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience alias(String... alias) {
        AudienceTarget target = AudienceTarget.alias(alias);
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience alias(Collection<String> aliases) {
        AudienceTarget target = AudienceTarget.alias(aliases);
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience segment(String... segment) {
        AudienceTarget target = AudienceTarget.segment(segment);
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience segment(Collection<String> segments) {
        AudienceTarget target = AudienceTarget.segment(segments);
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience registrationId(String... registrationId) {
        AudienceTarget target = AudienceTarget.registrationId(registrationId);
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience registrationId(Collection<String> registrationIds) {
        AudienceTarget target = AudienceTarget.registrationId(registrationIds);
        return newBuilder().addAudienceTarget(target).build();
    }

    public static Audience abTest(String... abTestId) {
        AudienceTarget target = AudienceTarget.abTest(abTestId);
        return newBuilder().addAudienceTarget(target).build();
    }

    public static Audience abTest(Collection<String> abTestIds) {
        AudienceTarget target = AudienceTarget.abTest(abTestIds);
        return newBuilder().addAudienceTarget(target).build();
    }
    
    
    public boolean isAll() {
        return this.all;
    }
    
    public JsonElement toJSON() {
        if (all) {
            return new JsonPrimitive(ALL);
        }
        
        // if not all, there will be target be set.
        JsonObject json = new JsonObject();
        if (null != targets) {
	        for (AudienceTarget target : targets) {
	            json.add(target.getAudienceTypeValue(), target.toJSON());
	        }
        }
        return json;
    }

    public static class Builder {
        private boolean all = false;
        private Set<AudienceTarget> audienceBuilder = null;
        
        public Builder setAll(boolean all) {
            this.all = all;
            return this;
        }
        
        public Builder addAudienceTarget(AudienceTarget target) {
            if (null == audienceBuilder) {
                audienceBuilder = new HashSet<AudienceTarget>();
            }
            audienceBuilder.add(target);
            return this;
        }
        
        public Audience build() {
            Preconditions.checkArgument(! (all && null != audienceBuilder), "If audience is all, no any other audience may be set.");
            Preconditions.checkArgument(! (all == false && null == audienceBuilder), "No any audience target is set.");
            return new Audience(all, audienceBuilder);
        }
    }
    
}


