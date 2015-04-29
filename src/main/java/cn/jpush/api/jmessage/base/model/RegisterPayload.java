package cn.jpush.api.jmessage.base.model;

import cn.jpush.api.utils.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class RegisterPayload implements IModel {


    private static Gson gson = new Gson();

    private JsonArray array ;

    private RegisterPayload(JsonArray array) {
        this.array = array;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        return array;
    }

    public static class Builder {

        private JsonArray array = new JsonArray();

        public Builder addUsers(RegisterInfo... users) {

            if( null == users ) {
                return this;
            }

            for ( RegisterInfo user : users) {

                array.add(user.toJSON());
            }

            return this;
        }

        public RegisterPayload build() {

            Preconditions.checkArgument(0 != array.size(), "The user list must not be empty.");

            return new RegisterPayload(array);
        }
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
