package cn.jpush.api.admin;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.connection.IHttpClient;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.JPushConfig;
import cn.jpush.api.file.FileClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Admin APIs
 * https://docs.jiguang.cn/jpush/server/push/rest_api_admin_api_v1/
 */
public class AdminClient {

    protected static final Logger LOG = LoggerFactory.getLogger(FileClient.class);


    private IHttpClient mHttpClient;
    private String mBasePath;
    private String mV1AppPath;
    private Gson mGson = new Gson();

    /**
     * Create a Push Client.
     *
     * @param appKey The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     */
    public AdminClient(String appKey, String masterSecret) {
        this(appKey, masterSecret, null, JPushConfig.getInstance());
    }

    public AdminClient(String appKey, String masterSecret, HttpProxy proxy) {
        this(appKey, masterSecret, proxy, JPushConfig.getInstance());
    }


    public AdminClient(String appKey, String masterSecret, HttpProxy proxy, JPushConfig conf) {
        ServiceHelper.checkBasic(appKey, masterSecret);
        mBasePath = (String) conf.get(JPushConfig.ADMIN_HOST_NAME);
        mV1AppPath = (String) conf.get(JPushConfig.V1_APP_PATH);
        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this.mHttpClient = new NativeHttpClient(authCode, proxy, conf.getClientConfig());
    }

    public void setHttpClient(IHttpClient client) {
        this.mHttpClient = client;
    }

    /**
     * Create an app under developer account
     * @param appName app name
     * @param packageName android package name
     * @param groupName developer app group name
     * @return {@link CreateAppResult}
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public CreateAppResult createApp(String appName, String packageName, String groupName)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != appName, "app name should not be null");
        Preconditions.checkArgument(null != packageName, "package name should not be null");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("app_name", appName);
        jsonObject.addProperty("android_package", packageName);
        if (null != groupName) {
            jsonObject.addProperty("group_name", groupName);
        }
        ResponseWrapper responseWrapper = mHttpClient.sendPost(mBasePath + mV1AppPath, mGson.toJson(jsonObject));
        return CreateAppResult.fromResponse(responseWrapper, CreateAppResult.class);
    }

    /**
     * Delete app by app key
     * @param appKey app key
     * @return {@link AppResult}
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public AppResult deleteApp(String appKey) throws APIConnectionException, APIRequestException {
        ResponseWrapper responseWrapper = mHttpClient.sendDelete(mBasePath + mV1AppPath + "/" + appKey + "/delete");
        return DefaultResult.fromResponse(responseWrapper, AppResult.class);
    }

    /**
     * Upload certificate
     * @param appKey app key
     * @param devCertificatePassword dev certificate password
     * @param proCertificatePassword pro certificate password
     * @param devCertificateFile dev certificate file
     * @param proCertificateFile pro certificate file
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public void uploadCertificate(String appKey, String devCertificateFile, String devCertificatePassword,
                                  String proCertificateFile, String proCertificatePassword)
            throws APIConnectionException, APIRequestException {

        Preconditions.checkArgument(devCertificateFile != null || proCertificateFile != null,
                "dev certificate file or pro certificate file should not be null");

        NativeHttpClient client = (NativeHttpClient) mHttpClient;
        String url = mBasePath + mV1AppPath + "/" + appKey + "/certificate";

        Map<String, String> fileMap = new HashMap<String, String>();
        Map<String, String> textMap = new HashMap<String, String>();

        if(devCertificateFile != null) {
            textMap.put("devCertificatePassword", devCertificatePassword);
            fileMap.put("devCertificateFile", devCertificateFile);
        }

        if(proCertificateFile != null) {
            textMap.put("proCertificatePassword", proCertificatePassword);
            fileMap.put("proCertificateFile", proCertificateFile);
        }

        String response = client.formUploadByPost(url, textMap, fileMap, null);
        LOG.info("uploadFile:{}", response);
    }
}
