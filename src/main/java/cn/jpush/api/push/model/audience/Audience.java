package cn.jpush.api.push.model.audience;

import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.push.model.PushModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Audience implements PushModel {
    private static final String ALL = "all";
    
    private final boolean all;
    private final boolean file;
    private final Set<AudienceTarget> targets;
    
    private Audience(boolean all, boolean file, Set<AudienceTarget> targets) {
        this.all = all;
        this.file = file;
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

    public static Audience file(String fileId) {
        AudienceTarget target = AudienceTarget.file(fileId);
        return newBuilder().setFile(Boolean.TRUE).addAudienceTarget(target).build();
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
        if (file) {
            for (AudienceTarget target : targets) {
                if (AudienceType.FILE == target.getAudienceType()) {
                    json.add(target.getAudienceTypeValue(), target.toFileJSON());
                }
            }
            return json;
        }
        if (null != targets) {
	        for (AudienceTarget target : targets) {
	            json.add(target.getAudienceTypeValue(), target.toJSON());
	        }
        }
        return json;
    }

    public static Audience fromJsonElement(JsonElement jsonElement) {
        if (jsonElement == null) {
            return null;
        }
        boolean all = !jsonElement.isJsonObject();
        if (all) {
            return new Audience(true, false,null);
        }
        boolean file = false;
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Set<AudienceTarget> audienceTargetSet = new HashSet<AudienceTarget>();
        for (AudienceType type : AudienceType.values()) {
            JsonArray jsonArray = jsonObject.getAsJsonArray(type.value());
            if (jsonArray == null) {
                continue;
            }
            if (AudienceType.FILE == type) {
                file = true;
            }
            audienceTargetSet.add(AudienceTarget.fromJsonElement(jsonArray, type));
        }
        return new Audience(false, file, audienceTargetSet);
    }

    public static class Builder {
        private boolean all = false;
        private boolean file = false;
        private Set<AudienceTarget> audienceBuilder = null;

        public Builder setAll(boolean all) {
            this.all = all;
            return this;
        }

        public Builder setFile(boolean file) {
            this.file = file;
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
            return new Audience(all, file, audienceBuilder);
        }
    }

}


