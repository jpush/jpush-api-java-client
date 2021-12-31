package cn.jpush.api.image;

import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.BaseTest;
import cn.jpush.api.image.model.ImageFilePayload;
import cn.jpush.api.image.model.ImageType;
import cn.jpush.api.image.model.ImageUploadResult;
import cn.jpush.api.image.model.ImageUrlPayload;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ImageClientTest extends BaseTest {

    @Test
    public void testUploadImageByUrl() throws NoSuchFieldException, APIConnectionException, APIRequestException {
        ResponseWrapper mockResponseWrapper = new ResponseWrapper();
        mockResponseWrapper.responseCode = 200;
        mockResponseWrapper.responseContent = "{\n" +
                "    \"media_id\": \"jgmedia-2-14b23451-0001-41ce-89d9-987b465122da\",  \n" +
                "    \"oppo_image_url\": \"3653918_5f92b5739ae676f5745bcbf4\",   \n" +
                "    \"xiaomi_image_url\": \"http://f6.market.xiaomi.com/download/MiPass/01fff50f50ba193f94074ec/d1671936d468383f.png\"     \n" +
                "}";

        NativeHttpClient mockIHttpClient = mock(NativeHttpClient.class);
        when(mockIHttpClient.sendPost(anyString(), anyString())).thenReturn(mockResponseWrapper);

        ImageClient client = new ImageClient(MASTER_SECRET, APP_KEY);
        new FieldSetter(client, client.getClass().getDeclaredField("_httpClient")).set(mockIHttpClient);
        ImageClient spyClient = Mockito.spy(client);
        ImageUrlPayload payload = ImageUrlPayload.newBuilder()
                .setImageUrl("xxx")
                .setImageType(ImageType.BIG_PICTURE)
                .setFcmImageUrl("xxx")
                .setHuaweiImageUrl("xxx")
                .build();
        ImageUploadResult imageUploadResult = spyClient.uploadImage(payload);
        verify(mockIHttpClient).sendPost("https://api.jpush.cn/v3/images/byurls", "{\"image_type\":1,\"image_url\":\"xxx\",\"huawei_image_url\":\"xxx\",\"fcm_image_url\":\"xxx\"}");

        assertThat(imageUploadResult.getMediaId(), equalTo("jgmedia-2-14b23451-0001-41ce-89d9-987b465122da"));
        assertThat(imageUploadResult.getOppoImageUrl(), equalTo("3653918_5f92b5739ae676f5745bcbf4"));
        assertThat(imageUploadResult.getXiaomiImageUrl(), equalTo("http://f6.market.xiaomi.com/download/MiPass/01fff50f50ba193f94074ec/d1671936d468383f.png"));
        assertThat(imageUploadResult.getHuaweiImageUrl(), nullValue());
    }

    @Test
    public void testUploadImageByFile() throws NoSuchFieldException {
        String content = "{\n" +
                "    \"media_id\": \"jgmedia-2-14b23451-0001-41ce-89d9-987b465122da\",  \n" +
                "    \"oppo_image_url\": \"3653918_5f92b5739ae676f5745bcbf4\",   \n" +
                "    \"xiaomi_image_url\": \"http://f6.market.xiaomi.com/download/MiPass/01fff50f50ba193f94074ec/d1671936d468383f.png\"     \n" +
                "}";

        NativeHttpClient mockIHttpClient = mock(NativeHttpClient.class);
        when(mockIHttpClient.formUploadByPost(anyString(), anyMap(), anyMap(), anyString())).thenReturn(content);

        ImageClient client = new ImageClient(MASTER_SECRET, APP_KEY);
        new FieldSetter(client, client.getClass().getDeclaredField("_httpClient")).set(mockIHttpClient);
        ImageClient spyClient = Mockito.spy(client);
        ImageFilePayload payload = ImageFilePayload.newBuilder()
                .setImageType(ImageType.LARGE_ICON)
                .setOppoFileName("oppoXX.jpg")
                .setXiaomiFileName("dir/xiaomiXX.jpg")
                .build();
        ImageUploadResult imageUploadResult = spyClient.uploadImage(payload);
        HashMap<String, String> textMap = new HashMap<String, String>();
        textMap.put("image_type", "2");
        HashMap<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("oppo_file", "oppoXX.jpg");
        fileMap.put("xiaomi_file", "dir/xiaomiXX.jpg");
        verify(mockIHttpClient).formUploadByPost("https://api.jpush.cn/v3/images/byfiles", textMap, fileMap, null);

        assertThat(imageUploadResult.getMediaId(), equalTo("jgmedia-2-14b23451-0001-41ce-89d9-987b465122da"));
        assertThat(imageUploadResult.getOppoImageUrl(), equalTo("3653918_5f92b5739ae676f5745bcbf4"));
        assertThat(imageUploadResult.getXiaomiImageUrl(), equalTo("http://f6.market.xiaomi.com/download/MiPass/01fff50f50ba193f94074ec/d1671936d468383f.png"));
        assertThat(imageUploadResult.getHuaweiImageUrl(), nullValue());
    }

    @Test
    public void testModifyImageByUrl() throws NoSuchFieldException, APIConnectionException, APIRequestException {
        ResponseWrapper mockResponseWrapper = new ResponseWrapper();
        mockResponseWrapper.responseCode = 200;
        mockResponseWrapper.responseContent = "{\n" +
                "    \"media_id\": \"jgmedia-2-14b23451-0001-41ce-89d9-987b465122da\",  \n" +
                "    \"oppo_image_url\": \"3653918_5f92b5739ae676f5745bcbf4\",   \n" +
                "    \"xiaomi_image_url\": \"http://f6.market.xiaomi.com/download/MiPass/01fff50f50ba193f94074ec/d1671936d468383f.png\"     \n" +
                "}";
        String mediaId = "jgmedia-2-14b23451-0001-41ce-89d9-987b465122da";
        NativeHttpClient mockIHttpClient = mock(NativeHttpClient.class);
        when(mockIHttpClient.sendPut(anyString(), anyString())).thenReturn(mockResponseWrapper);

        ImageClient client = new ImageClient(MASTER_SECRET, APP_KEY);
        new FieldSetter(client, client.getClass().getDeclaredField("_httpClient")).set(mockIHttpClient);
        ImageClient spyClient = Mockito.spy(client);
        ImageUrlPayload payload = ImageUrlPayload.newBuilder()
                .setImageUrl("xxx.jpg")
                .setFcmImageUrl("xxx.png")
                .setHuaweiImageUrl("xxx.jpeg")
                .build();
        ImageUploadResult imageUploadResult = spyClient.modifyImage(mediaId, payload);
        verify(mockIHttpClient).sendPut("https://api.jpush.cn/v3/images/byurls/" + mediaId, "{\"image_url\":\"xxx.jpg\",\"huawei_image_url\":\"xxx.jpeg\",\"fcm_image_url\":\"xxx.png\"}");

        assertThat(imageUploadResult.getMediaId(), equalTo("jgmedia-2-14b23451-0001-41ce-89d9-987b465122da"));
        assertThat(imageUploadResult.getOppoImageUrl(), equalTo("3653918_5f92b5739ae676f5745bcbf4"));
        assertThat(imageUploadResult.getXiaomiImageUrl(), equalTo("http://f6.market.xiaomi.com/download/MiPass/01fff50f50ba193f94074ec/d1671936d468383f.png"));
        assertThat(imageUploadResult.getHuaweiImageUrl(), nullValue());
    }

    @Test
    public void testModifyImageByFile() throws NoSuchFieldException {
        String content = "{\n" +
                "    \"media_id\": \"jgmedia-2-14b23451-0001-41ce-89d9-987b465122da\",  \n" +
                "    \"oppo_image_url\": \"3653918_5f92b5739ae676f5745bcbf4\",   \n" +
                "    \"xiaomi_image_url\": \"http://f6.market.xiaomi.com/download/MiPass/01fff50f50ba193f94074ec/d1671936d468383f.png\"     \n" +
                "}";
        String mediaId = "jgmedia-2-14b23451-0001-41ce-89d9-987b465122da";

        NativeHttpClient mockIHttpClient = mock(NativeHttpClient.class);
        when(mockIHttpClient.formUploadByPut(anyString(), anyMap(), anyMap(), anyString())).thenReturn(content);

        ImageClient client = new ImageClient(MASTER_SECRET, APP_KEY);
        new FieldSetter(client, client.getClass().getDeclaredField("_httpClient")).set(mockIHttpClient);
        ImageClient spyClient = Mockito.spy(client);
        ImageFilePayload payload = ImageFilePayload.newBuilder()
                .setOppoFileName("oppoXX.jpg")
                .setXiaomiFileName("dir/xiaomiXX.jpg")
                .build();
        ImageUploadResult imageUploadResult = spyClient.modifyImage(mediaId, payload);
        HashMap<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("oppo_file", "oppoXX.jpg");
        fileMap.put("xiaomi_file", "dir/xiaomiXX.jpg");
        verify(mockIHttpClient).formUploadByPut("https://api.jpush.cn/v3/images/byfiles/" + mediaId, null, fileMap, null);

        assertThat(imageUploadResult.getMediaId(), equalTo("jgmedia-2-14b23451-0001-41ce-89d9-987b465122da"));
        assertThat(imageUploadResult.getOppoImageUrl(), equalTo("3653918_5f92b5739ae676f5745bcbf4"));
        assertThat(imageUploadResult.getXiaomiImageUrl(), equalTo("http://f6.market.xiaomi.com/download/MiPass/01fff50f50ba193f94074ec/d1671936d468383f.png"));
        assertThat(imageUploadResult.getHuaweiImageUrl(), nullValue());
    }

    @Test
    public void testUploadImageByUrl2() throws APIConnectionException, APIRequestException {

        ImageClient client = new ImageClient(MASTER_SECRET, APP_KEY);
        ImageUrlPayload payload = ImageUrlPayload.newBuilder()
                .setImageType(ImageType.LARGE_ICON)
                .setImageUrl("http://img.aiimg.com/uploads/allimg/151009/280082-151009232P5.jpg")
                .setOppoImageUrl("http://img.aiimg.com/uploads/allimg/151009/280082-151009232P5.jpg")
                .setHuaweiImageUrl("http://img.aiimg.com/uploads/allimg/151009/280082-151009232P5.jpg")
                .build();
        ImageUploadResult imageUploadResult = client.uploadImage(payload);
        assertThat(imageUploadResult, notNullValue());
        String mediaId = imageUploadResult.getMediaId();
        assertThat(mediaId, notNullValue());
        ImageUrlPayload payload2 = ImageUrlPayload.newBuilder()
                .setImageUrl("http://img.aiimg.com/uploads/allimg/151009/280082-151009225435.jpg")
                .setFcmImageUrl("http://img.aiimg.com/uploads/allimg/151009/280082-151009225435.jpg")
                .setHuaweiImageUrl("http://img.aiimg.com/uploads/allimg/151009/280082-151009225435.jpg")
                .build();
        ImageUploadResult imageUploadResult2 = client.modifyImage(mediaId, payload2);
        assertThat(imageUploadResult2, notNullValue());
        assertThat(imageUploadResult2.getHuaweiImageUrl(), notNullValue());
    }

    @Test
    public void testUploadImageByFile2() {
        ImageClient client = new ImageClient(MASTER_SECRET, APP_KEY);
        ImageFilePayload payload = ImageFilePayload.newBuilder()
                .setImageType(ImageType.BIG_PICTURE)
                .setOppoFileName("/Users/yongxing/Downloads/Xnip2020-12-11_14-24-28.jpg")
                .setXiaomiFileName("/Users/yongxing/Downloads/Xnip2020-12-11_14-24-28.jpg")
                .build();
        ImageUploadResult imageUploadResult = client.uploadImage(payload);
        assertThat(imageUploadResult, notNullValue());
        assertThat(imageUploadResult.getError(), nullValue());
        String mediaId = imageUploadResult.getMediaId();
        assertThat(mediaId, notNullValue());
        ImageFilePayload payload2 = ImageFilePayload.newBuilder()
                .setOppoFileName("/Users/yongxing/Downloads/IMG_2778.jpeg")
                .setXiaomiFileName("/Users/yongxing/Downloads/IMG_2778.jpeg")
                .build();
        imageUploadResult = client.modifyImage(mediaId, payload2);
        assertThat(imageUploadResult, notNullValue());
        assertThat(imageUploadResult.getOppoImageUrl(), notNullValue());
    }
}