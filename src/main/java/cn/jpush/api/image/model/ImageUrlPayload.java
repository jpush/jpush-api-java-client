package cn.jpush.api.image.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class ImageUrlPayload {
    private static final String IMAGE_TYPE = "image_type";
    private static final String IMAGE_URL = "image_url";
    private static final String OPPO_IMAGE_URL = "oppo_image_url";
    private static final String XIAOMI_IMAGE_URL = "xiaomi_image_url";
    private static final String HUAWEI_IMAGE_URL = "huawei_image_url";
    private static final String FCM_IMAGE_URL = "fcm_image_url";
    private static final String JIGUANG_IMAGE_URL = "jiguang_image_url";

    private ImageType imageType;
    private String imageUrl;
    private String oppoImageUrl;
    private String xiaomiImageUrl;
    private String huaweiImageUrl;
    private String fcmImageUrl;
    private String jiguangImageUrl;

    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (null != imageType) {
            json.addProperty(IMAGE_TYPE, imageType.value());
        }
        if (null != imageUrl) {
            json.addProperty(IMAGE_URL, imageUrl);
        }
        if (null != oppoImageUrl) {
            json.addProperty(OPPO_IMAGE_URL, oppoImageUrl);
        }
        if (null != xiaomiImageUrl) {
            json.addProperty(XIAOMI_IMAGE_URL, xiaomiImageUrl);
        }
        if (null != huaweiImageUrl) {
            json.addProperty(HUAWEI_IMAGE_URL, huaweiImageUrl);
        }
        if (null != fcmImageUrl) {
            json.addProperty(FCM_IMAGE_URL, fcmImageUrl);
        }
        if (null != jiguangImageUrl) {
            json.addProperty(JIGUANG_IMAGE_URL, jiguangImageUrl);
        }
        return json;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private ImageType imageType;
        private String imageUrl;
        private String oppoImageUrl;
        private String xiaomiImageUrl;
        private String huaweiImageUrl;
        private String fcmImageUrl;
        private String jiguangImageUrl;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder setImageType(ImageType imageType) {
            this.imageType = imageType;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setOppoImageUrl(String oppoImageUrl) {
            this.oppoImageUrl = oppoImageUrl;
            return this;
        }

        public Builder setXiaomiImageUrl(String xiaomiImageUrl) {
            this.xiaomiImageUrl = xiaomiImageUrl;
            return this;
        }

        public Builder setHuaweiImageUrl(String huaweiImageUrl) {
            this.huaweiImageUrl = huaweiImageUrl;
            return this;
        }

        public Builder setFcmImageUrl(String fcmImageUrl) {
            this.fcmImageUrl = fcmImageUrl;
            return this;
        }

        public Builder setJiguangImageUrl(String jiguangImageUrl) {
            this.jiguangImageUrl = jiguangImageUrl;
            return this;
        }

        public ImageUrlPayload build() {
            ImageUrlPayload imageUrlPayload = new ImageUrlPayload();
            imageUrlPayload.setImageType(imageType);
            imageUrlPayload.setImageUrl(imageUrl);
            imageUrlPayload.setOppoImageUrl(oppoImageUrl);
            imageUrlPayload.setXiaomiImageUrl(xiaomiImageUrl);
            imageUrlPayload.setHuaweiImageUrl(huaweiImageUrl);
            imageUrlPayload.setFcmImageUrl(fcmImageUrl);
            imageUrlPayload.setJiguangImageUrl(jiguangImageUrl);
            return imageUrlPayload;
        }
    }

}