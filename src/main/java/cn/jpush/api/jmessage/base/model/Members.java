package cn.jpush.api.jmessage.base.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;


public class Members implements IModel {

    private JsonArray members;

    private Members(JsonArray members) {
        this.members = members;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        return members;
    }

    public static class Builder {

        private JsonArray members = new JsonArray();

        public Builder addMember(String... usernames) {

            if( null == usernames ) {
                return this;
            }

            for (String username : usernames) {
                JsonPrimitive member = new JsonPrimitive(username);
                this.members.add(member);
            }
            return this;
        }

        public Members build() {
            return new Members(members);
        }
    }
}
