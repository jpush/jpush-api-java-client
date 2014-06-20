package cn.jpush.api.push.model.audience;

import java.util.Collection;

import cn.jpush.api.push.model.PushModel;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Audience implements PushModel {
    private static final String ALL = "all";
    
    private final boolean all;
    private final ImmutableSet<AudienceTarget> targets;
    
    private Audience(boolean all, ImmutableSet<AudienceTarget> targets) {
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
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.TAG)
                .addAudienceTargetValues(tagValue).build();
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience tag(Collection<String> tagValues) {
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.TAG)
                .addAudienceTargetValues(tagValues).build();
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience tag_and(String... tagValue) {
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.TAG_AND)
                .addAudienceTargetValues(tagValue).build();
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience tag_and(Collection<String> tagValues) {
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.TAG_AND)
                .addAudienceTargetValues(tagValues).build();
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience alias(String... alias) {
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.ALIAS)
                .addAudienceTargetValues(alias).build();
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience alias(Collection<String> aliases) {
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.ALIAS)
                .addAudienceTargetValues(aliases).build();
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience segment(String... segment) {
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.SEGMENT)
                .addAudienceTargetValues(segment).build();
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience segment(Collection<String> segments) {
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.ALIAS)
                .addAudienceTargetValues(segments).build();
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience registrationId(String... registrationId) {
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.REGISTRATION_ID)
                .addAudienceTargetValues(registrationId).build();
        return newBuilder().addAudienceTarget(target).build();
    }
    
    public static Audience registrationId(Collection<String> registrationIds) {
        AudienceTarget target = AudienceTarget.newBuilder()
                .setAudienceType(AudienceType.ALIAS)
                .addAudienceTargetValues(registrationIds).build();
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
        for (AudienceTarget target : targets) {
            json.add(target.getAudienceTypeValue(), target.toJSON());
        }
        return json;
    }
    
    public static class Builder {
        private boolean all = false;
        private ImmutableSet.Builder<AudienceTarget> audienceBuilder = null;
        
        public Builder setAll(boolean all) {
            this.all = all;
            return this;
        }
        
        public Builder addAudienceTarget(AudienceTarget target) {
            if (null == audienceBuilder) {
                audienceBuilder = ImmutableSet.builder();
            }
            audienceBuilder.add(target);
            return this;
        }
        
        public Audience build() {
            Preconditions.checkArgument(! (all && null != audienceBuilder), "If audience is all, no any other audience may be set.");
            Preconditions.checkArgument(! (all == false && null == audienceBuilder), "No any audience target is set.");
            return new Audience(all, (null == audienceBuilder) ? null : audienceBuilder.build());
        }
    }
    
}


