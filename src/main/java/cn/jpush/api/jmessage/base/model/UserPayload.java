package cn.jpush.api.jmessage.base.model;

import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.utils.Preconditions;
import cn.jpush.api.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class UserPayload implements IModel {

    public static final String NICKNAME = "nickname";
    public static final String BIRTHDAY = "birthday";
    public static final String SIGNATURE = "signature";
    public static final String GENDER = "gender";
    public static final String REGION = "region";
    public static final String ADDRESS = "address";
    public static final String AVATAR = "avatar";

    private static Gson _gson = new Gson();

    private String nickname;
    private String birthday;
    private String signature;
    private int gender;
    private String region;
    private String address;
    private String avatar;

    private UserPayload(String nickname, String birthday, String signature, int gender, String region, String address, String avatar) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.signature = signature;
        this.gender = gender;
        this.region = region;
        this.address = address;
        this.avatar = avatar;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();

        if ( null != nickname ) {
            json.addProperty(NICKNAME, nickname);
        }

        if ( null != birthday ) {
            json.addProperty(BIRTHDAY, birthday);
        }

        if ( -1 != gender ) {
            json.addProperty(GENDER, gender);
        }

        if ( null != signature ) {
            json.addProperty(SIGNATURE, signature);
        }

        if ( null != region ) {
            json.addProperty(REGION, region);
        }

        if ( null != address ) {
            json.addProperty(ADDRESS, address);
        }

        if ( null != avatar ) {
            json.addProperty(AVATAR, avatar);
        }

        return json;
    }

    @Override
    public String toString() {
        return _gson.toJson(toJSON());
    }

    public static class Builder {

        private String nickname;
        private String birthday;
        private String signature;
        private int gender = -1;
        private String region;
        private String address;
        private String avatar;

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder setSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public Builder setGender(int gender) {
            this.gender = gender;
            return this;
        }

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public UserPayload build() {

            if ( null != nickname ) {
                Preconditions.checkArgument(nickname.getBytes().length <= 64,
                        "The length of nickname must less than 64 bytes.");
                Preconditions.checkArgument(StringUtils.isLineBroken(nickname),
                        "The nickname must not contain line feed character.");
            }

            if ( null != birthday) {
                Preconditions.checkArgument( ServiceHelper.isValidBirthday(birthday),
                        "Invalid birthday.");
            }

            if (null != signature) {
                Preconditions.checkArgument(signature.getBytes().length <= 250,
                        "The length of signature must not more than 250 bytes.");
            }

            Preconditions.checkArgument( gender >= -1 && gender <= 2,
                    "Invalid gender. 0 for unknown , 1 for male and 2 for female." );

            if ( null != region ) {
                Preconditions.checkArgument(region.getBytes().length <= 250,
                       "The length of region must not more than 250 bytes." );
            }

            if ( null != address ) {
                Preconditions.checkArgument(address.getBytes().length <= 250,
                        "The length of address must not more than 250 bytes." );
            }

            return new UserPayload(nickname, birthday, signature, gender, region, address, avatar);
        }

    }
}
