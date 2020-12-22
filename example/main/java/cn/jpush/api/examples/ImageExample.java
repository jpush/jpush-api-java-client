package cn.jpush.api.image;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.image.model.ImageFilePayload;
import cn.jpush.api.image.model.ImageType;
import cn.jpush.api.image.model.ImageUploadResult;
import cn.jpush.api.image.model.ImageUrlPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageExample {
    protected static final Logger LOG = LoggerFactory.getLogger(ImageExample.class);

    // demo App defined in resources/jpush-api.conf 
    protected static final String APP_KEY = "e4ceeaf7a53ad745dd4728f2";
    protected static final String MASTER_SECRET = "1582b986adeaf48ceec1e354";
    protected static final String GROUP_PUSH_KEY = "2c88a01e073a0fe4fc7b167c";
    protected static final String GROUP_MASTER_SECRET = "b11314807507e2bcfdeebe2e";

    public static final String TITLE = "Test from API example";
    public static final String ALERT = "Test from API Example - alert";
    public static final String MSG_CONTENT = "Test from API Example - msgContent";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
    public static long sendCount = 0;
    private static long sendTotalTime = 0;

    public static void main(String[] args) throws APIConnectionException, APIRequestException {
        testUploadImageByFile();
        testUploadImageByUrl();
    }

    public static void testUploadImageByUrl() throws APIConnectionException, APIRequestException {
        ImageClient client = new ImageClient(MASTER_SECRET, APP_KEY);
        ImageUrlPayload payload = ImageUrlPayload.newBuilder()
                .setImageType(ImageType.LARGE_ICON)
                .setImageUrl("http://xxx.com/image/a.jpg")
                .build();
        ImageUploadResult imageUploadResult = client.uploadImage(payload);
        String mediaId = imageUploadResult.getMediaId();
    }

    public static void testUploadImageByFile() {
        ImageClient client = new ImageClient(MASTER_SECRET, APP_KEY);
        ImageFilePayload payload = ImageFilePayload.newBuilder()
                .setImageType(ImageType.BIG_PICTURE)
                // 本地文件路径
                .setOppoFileName("/MyDir/a.jpg")
                .setXiaomiFileName("/MyDir/a.jpg")
                .build();
        ImageUploadResult imageUploadResult = client.uploadImage(payload);
        String mediaId = imageUploadResult.getMediaId();
    }
}

