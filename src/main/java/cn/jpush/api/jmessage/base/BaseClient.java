package cn.jpush.api.jmessage.base;

import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.jmessage.base.connection.IHttpClient;
import cn.jpush.api.jmessage.base.connection.NativeHttpClient;
import com.google.gson.JsonParser;

public class BaseClient {

    protected final NativeHttpClient _httpClient;
    protected JsonParser _jsonParser = new JsonParser();
    protected String _baseUrl;

    /**
     * Create a JMessage Client with default retry times, default is 3.
     *
     * @param appkey The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     */
    public BaseClient(String appkey, String masterSecret) {
        this(appkey, masterSecret, IHttpClient.DEFAULT_MAX_RETRY_TIMES);
    }

    /**
     * Create a JMessage Client with max retry times.
     *
     * @param appkey The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param maxRetryTimes max retry times
     */
    public BaseClient(String appkey, String masterSecret, int maxRetryTimes) {
        this(appkey, masterSecret, maxRetryTimes, null);
    }

    /**
     * Create a JMessage Client with max retry times.
     *
     * @param appkey The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param maxRetryTimes max retry times
     */
    public BaseClient(String appkey, String masterSecret, int maxRetryTimes, HttpProxy proxy) {
        ServiceHelper.checkBasic(appkey, masterSecret);
        String authCode = ServiceHelper.getBasicAuthorization(appkey, masterSecret);
        this._baseUrl = ServiceConstant.HOST_NAME_SSL;
        this._httpClient = new NativeHttpClient(authCode, maxRetryTimes, proxy);
    }

    public void setBaseUrl(String baseUrl) {
        this._baseUrl = baseUrl;
    }

}
