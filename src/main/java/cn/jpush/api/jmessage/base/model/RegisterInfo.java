package cn.jpush.api.jmessage.base.model;

import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.utils.Preconditions;
import cn.jpush.api.utils.StringUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RegisterInfo implements IModel {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private String username;
    private String password;

    private RegisterInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {

        JsonObject json = new JsonObject();

        if ( null != username ) {
            json.addProperty(USERNAME, username);
        }

        if ( null != password ) {
            json.addProperty(PASSWORD, password);
        }

        return json;
    }

    public static class Builder{
        private String username;
        private String password;

        public Builder setUsername(String username) {
            this.username = username.trim();
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password.trim();
            return this;
        }

        public RegisterInfo build() {

            Preconditions.checkArgument( StringUtils.isNotEmpty(username),
                    "The username must not be empty.");

            Preconditions.checkArgument(StringUtils.isNotEmpty(username),
                    "The password must not be empty.");

            byte[] usernameByte = username.getBytes();
            byte[] passwordByte = password.getBytes();
            Preconditions.checkArgument( usernameByte.length >= 4 && usernameByte.length <=128,
                    "The length of username must between 4 and 128 bytes. Input is " + username);
            Preconditions.checkArgument( passwordByte.length >= 4 && passwordByte.length <=128,
                    "The length of password must between 4 and 128 bytes. Input is " + password);

            Preconditions.checkArgument(ServiceHelper.checkUsername(username),
                    "The parameter username contains illegal character," +
                            " a-zA-Z_0-9.、-,@。 is legally, and start with alphabet or number. Input is " + username);

            return new RegisterInfo(username, password);
        }

    }
}
