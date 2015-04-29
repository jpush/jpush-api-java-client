package cn.jpush.api.jmessage.base.model;

import cn.jpush.api.utils.Preconditions;
import cn.jpush.api.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class GroupPayload implements IModel {

    public static final String OWNER = "owner_username";
    public static final String GROUP_NAME = "group_name";
    public static final String MEMBERS = "members_username";
    public static final String DESC = "group_desc";

    private static Gson gson = new Gson();

    private String owner;
    private String name;
    private Members members;
    private String desc;

    private GroupPayload(String owner, String name, Members members, String desc) {
        this.owner = owner;
        this.name = name;
        this.members = members;
        this.desc = desc;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {

        JsonObject json = new JsonObject();

        if ( null != owner ) {
            json.addProperty(OWNER, owner);
        }

        if ( null != name ) {
            json.addProperty(GROUP_NAME, name);
        }

        if ( null != members ) {
            json.add(MEMBERS, members.toJSON());
        }

        if ( null != desc ) {
            json.addProperty(DESC, desc);
        }

        return json;

    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    public static class Builder{

        private String owner;
        private String name;
        private Members members;
        private String desc;

        public Builder setOwner(String owner) {
            this.owner = owner.trim();
            return this;
        }

        public Builder setName(String name) {
            this.name = name.trim();
            return this;
        }

        public Builder setMembers(Members members) {
            this.members = members;
            return this;
        }

        public Builder setDesc(String desc) {
            this.desc = desc.trim();
            return this;
        }

        public GroupPayload build() {

            Preconditions.checkArgument(StringUtils.isNotEmpty(owner), "The owner must not be empty.");
            Preconditions.checkArgument(StringUtils.isNotEmpty(name), "The group name must not be empty.");
            Preconditions.checkArgument(name.getBytes().length <= 64,
                    "The length of group name must not more than 250 bytes.");
            Preconditions.checkArgument( !StringUtils.isLineBroken(name),
                    "The group name must not contain line feed character.");

            if ( null != desc ) {
                Preconditions.checkArgument( desc.getBytes().length <= 250,
                        "The length of group description must not more than 250 bytes.");
            }

            return new GroupPayload(owner, name, members, desc);
        }

    }
}
