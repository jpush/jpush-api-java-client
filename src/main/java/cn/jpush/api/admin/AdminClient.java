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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Admin APIs
 * https://docs.jiguang.cn/jpush/server/push/rest_api_admin_api_v1/
 */
public class AdminClient {

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

//    public AppResult uploadCertificate(String appKey) throws APIConnectionException, APIRequestException {
//
//    }
}
