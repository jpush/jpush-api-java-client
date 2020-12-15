package cn.jpush.api.image.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ImageUploadResult {

    @SerializedName("media_id")
    private String mediaId;
    @SerializedName("oppo_image_url")
    private String oppoImageUrl;
    @SerializedName("xiaomi_image_url")
    private String xiaomiImageUrl;
    @SerializedName("huawei_image_url")
    private String huaweiImageUrl;
    @SerializedName("fcm_image_url")
    private String fcmImageUrl;
    @SerializedName("jiguang_image_url")
    private String jiguangImageUrl;
    @SerializedName("error")
    private Error error;

    @Data
    public static class Error {
        @SerializedName("message")
        private String message;
        @SerializedName("code")
        private int code;
    }
}