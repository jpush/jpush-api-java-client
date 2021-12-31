package cn.jpush.api.image.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ImageFilePayload {
    private static final String IMAGE_TYPE = "image_type";
    private static final String OPPO_IMAGE_FILE = "oppo_file";
    private static final String XIAOMI_IMAGE_FILE = "xiaomi_file";
    private static final String HUAWEI_IMAGE_FILE = "huawei_file";
    private static final String FCM_IMAGE_FILE = "fcm_file";
    private static final String JIGUANG_IMAGE_FILE = "jiguang_file";

    private ImageType imageType;
    private String oppoFileName;
    private String xiaomiFileName;
    private String huaweiFileName;
    private String fcmFileName;
    private String jiguangFileName;

    public Map<String, String> toFileMap() {
        HashMap<String, String> fileMap = new HashMap<String, String>();
        if (null != oppoFileName) {
            fileMap.put(OPPO_IMAGE_FILE, oppoFileName);
        }
        if (null != xiaomiFileName) {
            fileMap.put(XIAOMI_IMAGE_FILE, xiaomiFileName);
        }
        if (null != huaweiFileName) {
            fileMap.put(HUAWEI_IMAGE_FILE, huaweiFileName);
        }
        if (null != fcmFileName) {
            fileMap.put(FCM_IMAGE_FILE, fcmFileName);
        }
        if (null != jiguangFileName) {
            fileMap.put(JIGUANG_IMAGE_FILE, jiguangFileName);
        }
        return fileMap;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private ImageType imageType;
        private String oppoFileName;
        private String xiaomiFileName;
        private String huaweiFileName;
        private String fcmFileName;
        private String jiguangFileName;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder setImageType(ImageType imageType) {
            this.imageType = imageType;
            return this;
        }

        public Builder setOppoFileName(String oppoFileName) {
            this.oppoFileName = oppoFileName;
            return this;
        }

        public Builder setXiaomiFileName(String xiaomiFileName) {
            this.xiaomiFileName = xiaomiFileName;
            return this;
        }

        public Builder setHuaweiFileName(String huaweiFileName) {
            this.huaweiFileName = huaweiFileName;
            return this;
        }

        public Builder setFcmFileName(String fcmFileName) {
            this.fcmFileName = fcmFileName;
            return this;
        }

        public Builder setJiguangFileName(String jiguangFileName) {
            this.jiguangFileName = jiguangFileName;
            return this;
        }

        public ImageFilePayload build() {
            ImageFilePayload imageFilePayload = new ImageFilePayload();
            imageFilePayload.setImageType(imageType);
            imageFilePayload.setOppoFileName(oppoFileName);
            imageFilePayload.setXiaomiFileName(xiaomiFileName);
            imageFilePayload.setHuaweiFileName(huaweiFileName);
            imageFilePayload.setFcmFileName(fcmFileName);
            imageFilePayload.setJiguangFileName(jiguangFileName);
            return imageFilePayload;
        }
    }

}