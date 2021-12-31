package cn.jpush.api.image;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.connection.IHttpClient;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;
import cn.jpush.api.image.model.ImageFilePayload;
import cn.jpush.api.image.model.ImageSource;
import cn.jpush.api.image.model.ImageUploadResult;
import cn.jpush.api.image.model.ImageUrlPayload;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Provide the ability to upload images to the Jiguang server. Only images in JPG, JPEG and PNG format are supported.
 *
 * @author fuyx
 * @version 2020-12-14
 */
public class ImageClient {

    protected static final Logger LOG = LoggerFactory.getLogger(ImageClient.class);

    private IHttpClient _httpClient;
    private String _baseUrl;
    private String _imagesPath;
    private Gson _gson = new Gson();

    public ImageClient(String masterSecret, String appKey) {
        this(masterSecret, appKey, null, ClientConfig.getInstance());
    }

    public ImageClient(String masterSecret, String appKey, HttpProxy proxy, ClientConfig conf) {
        _baseUrl = (String) conf.get(ClientConfig.PUSH_HOST_NAME);
        _imagesPath = (String) conf.get(ClientConfig.V3_IMAGES_PATH);
        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf);
    }


    /**
     * Upload image by url. Require at least one non-null url.
     * @param imageUrlPayload image url payload
     * @return {@link ImageUploadResult}
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public ImageUploadResult uploadImage(ImageUrlPayload imageUrlPayload)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(imageUrlPayload.getImageType() != null, "Image type should not be null");
        checkImageUrlPayload(imageUrlPayload);
        NativeHttpClient client = (NativeHttpClient) _httpClient;
        String url = _baseUrl + _imagesPath + "/" + ImageSource.URL.value();
        JsonElement jsonElement = imageUrlPayload.toJSON();
        String content = _gson.toJson(jsonElement);
        ResponseWrapper responseWrapper = client.sendPost(url, content);
        if (responseWrapper.responseCode != 200) {
            LOG.error("upload image failed: {}", responseWrapper);
        }
        ImageUploadResult imageUploadResult = _gson.fromJson(responseWrapper.responseContent, ImageUploadResult.class);

        LOG.info("upload image result:{}", imageUploadResult);
        return imageUploadResult;
    }

    /**
     * Upload image by file. Require at least 1 non-null fileName. Currently only support Xiaomi and OPPO
     * @param imageFilePayload image file payload
     * @return {@link ImageUploadResult}
     */
    public ImageUploadResult uploadImage(ImageFilePayload imageFilePayload) {
        Preconditions.checkArgument(imageFilePayload.getImageType() != null, "Image type should not be null");
        checkImageFilePayload(imageFilePayload);
        NativeHttpClient client = (NativeHttpClient) _httpClient;
        String url = _baseUrl + _imagesPath + "/" + ImageSource.FILE.value();

        Map<String, String> textMap = new HashMap<String, String>();
        textMap.put("image_type", String.valueOf(imageFilePayload.getImageType().value()));

        Map<String, String> fileMap = imageFilePayload.toFileMap();
        LOG.debug("upload fileMap: {}", fileMap);
        String response = client.formUploadByPost(url, textMap, fileMap, null);
        LOG.debug("upload image result: {}", response);
        ImageUploadResult imageUploadResult;
        try {
            imageUploadResult = _gson.fromJson(response, ImageUploadResult.class);
        } catch (JsonSyntaxException e) {
            LOG.error("could not parse response: {}", response);
            throw new IllegalStateException("could not parse response", e);
        }
        LOG.info("upload image result:{}", imageUploadResult);
        return imageUploadResult;
    }

    /**
     * Modify image by url. Require at least one non-null url.
     * @param mediaId media id
     * @param imageUrlPayload image url payload
     * @return {@link ImageUploadResult}
     * @throws APIConnectionException connection exception
     * @throws APIRequestException request exception
     */
    public ImageUploadResult modifyImage(String mediaId, ImageUrlPayload imageUrlPayload)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(mediaId), "mediaId should not be empty");
        checkImageUrlPayload(imageUrlPayload);
        NativeHttpClient client = (NativeHttpClient) _httpClient;
        String url = _baseUrl + _imagesPath + "/" + ImageSource.URL.value() + "/" + mediaId;
        JsonElement jsonElement = imageUrlPayload.toJSON();
        String content = _gson.toJson(jsonElement);
        ResponseWrapper responseWrapper = client.sendPut(url, content);
        if (responseWrapper.responseCode != 200) {
            LOG.error("upload image failed: {}", responseWrapper);
        }
        ImageUploadResult imageUploadResult = _gson.fromJson(responseWrapper.responseContent, ImageUploadResult.class);

        LOG.info("upload image result:{}", imageUploadResult);
        return imageUploadResult;
    }


    /**
     * Modify image by file. Require at least 1 non-null fileName. Currently only support Xiaomi and OPPO
     * @param mediaId media id
     * @param imageFilePayload image file payload
     * @return {@link ImageUploadResult}
     */
    public ImageUploadResult modifyImage(String mediaId, ImageFilePayload imageFilePayload) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(mediaId), "mediaId should not be empty");
        checkImageFilePayload(imageFilePayload);
        NativeHttpClient client = (NativeHttpClient) _httpClient;
        String url = _baseUrl + _imagesPath + "/" + ImageSource.FILE.value() + "/" + mediaId;

        Map<String, String> fileMap = imageFilePayload.toFileMap();
        LOG.debug("upload image fileMap: {}", fileMap);
        String response = client.formUploadByPut(url, null, fileMap, null);
        LOG.debug("upload image result: {}", response);
        ImageUploadResult imageUploadResult;
        try {
            imageUploadResult = _gson.fromJson(response, ImageUploadResult.class);
        } catch (JsonSyntaxException e) {
            LOG.error("could not parse response: {}", response);
            throw new IllegalStateException("could not parse response", e);
        }
        LOG.info("upload image result:{}", imageUploadResult);
        return imageUploadResult;
    }

    private void checkImageUrlPayload(ImageUrlPayload imageUrlPayload) {
        boolean anyUrlNotEmpty = StringUtils.isNotEmpty(imageUrlPayload.getImageUrl())
                || StringUtils.isNotEmpty(imageUrlPayload.getFcmImageUrl())
                || StringUtils.isNotEmpty(imageUrlPayload.getHuaweiImageUrl())
                || StringUtils.isNotEmpty(imageUrlPayload.getOppoImageUrl())
                || StringUtils.isNotEmpty(imageUrlPayload.getXiaomiImageUrl())
                || StringUtils.isNotEmpty(imageUrlPayload.getJiguangImageUrl()) ;
        Preconditions.checkArgument(anyUrlNotEmpty, "Require at least 1 non-empty url");
    }

    private void checkImageFilePayload(ImageFilePayload imageFilePayload) {
        boolean anyFileNotEmpty = StringUtils.isNotEmpty(imageFilePayload.getOppoFileName() )
                || StringUtils.isNotEmpty(imageFilePayload.getXiaomiFileName() );
        Preconditions.checkArgument(anyFileNotEmpty, "Require at least 1 non-empty fileName. Currently only support Xiaomi and OPPO");
    }


}