package cn.jpush.api.push;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.*;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Base64;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;
import cn.jiguang.common.utils.sm2.SM2Util;
import cn.jpush.api.push.model.EncryptKeys;
import cn.jpush.api.push.model.EncryptPushPayload;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * Entrance for sending Push.
 * 
 * For the following parameters, you can set them by instance creation. 
 * This action will override setting in PushPayload Optional.
 * * apnsProduction If not present, the default is true.
 * * timeToLive If not present, the default is 86400(s) (one day).
 * 
 * Can be used directly.
 */
public class PushClient {

    private IHttpClient _httpClient;
    private String _baseUrl;
    private String _pushPath;
    private String _pushValidatePath;

    private JsonParser _jsonParser = new JsonParser();

    // If not present, true by default.
    private int _apnsProduction;
    
    // If not present, the default value is 86400(s) (one day)
    private long _timeToLive;

    // encrypt type, the default value is empty
    private String _encryptType;

    /**
     * Create a Push Client.
     * 
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     */
	public PushClient(String masterSecret, String appKey) {
	    this(masterSecret, appKey, null, ClientConfig.getInstance());
	}

    /**
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig} instead of this constructor.
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes The max retry times.
     */
    @Deprecated
	public PushClient(String masterSecret, String appKey, int maxRetryTimes) {
	    this(masterSecret, appKey, maxRetryTimes, null);
	}
	
	/**
	 * Create a Push Client with max retry times.
	 * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig} instead of this constructor.
     *
	 * @param masterSecret  API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 * @param maxRetryTimes max retry times
     * @param proxy The max retry times.
	 */
    @Deprecated
	public PushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        ServiceHelper.checkBasic(appKey, masterSecret);

        ClientConfig conf = ClientConfig.getInstance();
        conf.setMaxRetryTimes(maxRetryTimes);

        this._baseUrl = (String) conf.get(ClientConfig.PUSH_HOST_NAME);
        this._pushPath = (String) conf.get(ClientConfig.PUSH_PATH);
        this._pushValidatePath = (String) conf.get(ClientConfig.PUSH_VALIDATE_PATH);

        this._apnsProduction = (Integer) conf.get(ClientConfig.APNS_PRODUCTION);
        this._timeToLive = (Long) conf.get(ClientConfig.TIME_TO_LIVE);

        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf);
	}

    public PushClient(String masterSecret, String appKey, HttpProxy proxy, ClientConfig conf) {
        ServiceHelper.checkBasic(appKey, masterSecret);

        this._baseUrl = (String) conf.get(ClientConfig.PUSH_HOST_NAME);
        this._pushPath = (String) conf.get(ClientConfig.PUSH_PATH);
        this._pushValidatePath = (String) conf.get(ClientConfig.PUSH_VALIDATE_PATH);

        this._apnsProduction = (Integer) conf.get(ClientConfig.APNS_PRODUCTION);
        this._timeToLive = (Long) conf.get(ClientConfig.TIME_TO_LIVE);
        this._encryptType = (String) conf.get(ClientConfig.ENCRYPT_TYPE);

        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf);

    }

	/**
     * Create a Push Client with global settings.
     * 
     * If you want different settings from default globally, this constructor is what you needed.
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig} instead of this constructor.
     *
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param apnsProduction Global APNs environment setting. It will override PushPayload Options.
     * @param timeToLive Global time_to_live setting. It will override PushPayload Options.
     */
    @Deprecated
    public PushClient(String masterSecret, String appKey, boolean apnsProduction, long timeToLive) {
        this(masterSecret, appKey);
        if(apnsProduction) {
            _apnsProduction = 1;
        } else {
            _apnsProduction = 0;
        }
        this._timeToLive = timeToLive;
    }

    /**
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setGlobalPushSetting} instead of this method.
     *
     * @param apnsProduction Global APNs environment setting. It will override PushPayload Options.
     * @param timeToLive Global time_to_live setting. It will override PushPayload Options.
     */
    @Deprecated
    public void setDefaults(boolean apnsProduction, long timeToLive) {
        if(apnsProduction) {
            _apnsProduction = 1;
        } else {
            _apnsProduction = 0;
        }
        this._timeToLive = timeToLive;
    }
    
    public void setBaseUrl(String baseUrl) {
        this._baseUrl = baseUrl;
    }
    
    public PushResult sendPush(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(! (null == pushPayload), "pushPayload should not be null");
        
        if (_apnsProduction > 0) {
            pushPayload.resetOptionsApnsProduction(true);
        } else if(_apnsProduction == 0) {
            pushPayload.resetOptionsApnsProduction(false);
        }

        if (_timeToLive >= 0) {
            pushPayload.resetOptionsTimeToLive(_timeToLive);
        }

        ResponseWrapper response = _httpClient.sendPost(_baseUrl + _pushPath, getEncryptData(pushPayload));
        
        return BaseResult.fromResponse(response, PushResult.class);
    }
    
    public PushResult sendPushValidate(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(! (null == pushPayload), "pushPayload should not be null");
        
        if (_apnsProduction > 0) {
            pushPayload.resetOptionsApnsProduction(true);
        } else if(_apnsProduction == 0) {
            pushPayload.resetOptionsApnsProduction(false);
        }

        if (_timeToLive >= 0) {
            pushPayload.resetOptionsTimeToLive(_timeToLive);
        }
        
        ResponseWrapper response = _httpClient.sendPost(_baseUrl + _pushValidatePath, getEncryptData(pushPayload));
        
        return BaseResult.fromResponse(response, PushResult.class);
    }
    
    public PushResult sendPush(String payloadString) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(payloadString), "pushPayload should not be empty");
        
        try {
            _jsonParser.parse(payloadString);
        } catch (JsonParseException e) {
            Preconditions.checkArgument(false, "payloadString should be a valid JSON string.");
        }
        
        ResponseWrapper response = _httpClient.sendPost(_baseUrl + _pushPath, getEncryptData(payloadString));
        
        return BaseResult.fromResponse(response, PushResult.class);
    }
    
    public PushResult sendPushValidate(String payloadString) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(payloadString), "pushPayload should not be empty");
        
        try {
            _jsonParser.parse(payloadString);
        } catch (JsonParseException e) {
            Preconditions.checkArgument(false, "payloadString should be a valid JSON string.");
        }
        
        ResponseWrapper response = _httpClient.sendPost(_baseUrl + _pushValidatePath, getEncryptData(payloadString));
        
        return BaseResult.fromResponse(response, PushResult.class);
    }

    /**
     * Get cid list, the data form of cid is appKey-uuid.
     * @param count the count of cid list, from 1 to 1000. default is 1.
     * @param type default is "push", option: "schedule"
     * @return CIDResult, an array of cid
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public CIDResult getCidList(int count, String type) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(count >= 1 && count <= 1000, "count should not less than 1 or larger than 1000");
        Preconditions.checkArgument(type == null || type.equals("push") || type.equals("schedule"), "type should be \"push\" or \"schedule\"");
        ResponseWrapper responseWrapper;
        if (type != null) {
            responseWrapper = _httpClient.sendGet(_baseUrl + _pushPath + "/cid?count=" + count + "&type=" + type);
        } else {
            responseWrapper = _httpClient.sendGet(_baseUrl + _pushPath + "/cid?count=" + count);
        }
        return  BaseResult.fromResponse(responseWrapper, CIDResult.class);
    }

    public void setHttpClient(IHttpClient client) {
        this._httpClient = client;
    }

    // 如果使用 NettyHttpClient，在发送请求后需要手动调用 close 方法
    public void close() {
        if (_httpClient != null && _httpClient instanceof NettyHttpClient) {
            ((NettyHttpClient) _httpClient).close();
        } else if (_httpClient != null && _httpClient instanceof ApacheHttpClient) {
            ((ApacheHttpClient) _httpClient).close();
        }
    }

    /**
     * 获取加密的payload数据
     * @param payloadData
     * @return
     */
    private String getEncryptData(String payloadData) {
        JsonElement payloadElement = _jsonParser.parse(payloadData);
        JsonObject jsonObject = payloadElement.getAsJsonObject();
        Audience audience = Audience.fromJsonElement(jsonObject.get("audience"));
        return getEncryptData(payloadData, audience);
    }

    /**
     * 获取加密的payload数据
     * @param pushPayload
     * @return
     */
    private String getEncryptData(PushPayload pushPayload) {
        if (_encryptType.isEmpty()) {
            return pushPayload.toString();
        }
        if (EncryptKeys.ENCRYPT_SMS2_TYPE.equals(_encryptType)) {
            EncryptPushPayload encryptPushPayload = new EncryptPushPayload();
            try {
                encryptPushPayload.setPayload(String.valueOf(Base64.encode(SM2Util.encrypt(pushPayload.toString(), EncryptKeys.DEFAULT_SM2_ENCRYPT_KEY))));
            } catch (Exception e) {
                throw new RuntimeException("encrypt word exception", e);
            }
            encryptPushPayload.setAudience(pushPayload.getAudience());
            return encryptPushPayload.toString();
        }
        // 不支持的加密默认不加密
        return pushPayload.toString();
    }

    /**
     * 获取加密的payload数据
     * @param pushPayload
     * @return
     */
    private String getEncryptData(String pushPayload, Audience audience) {
        if (_encryptType.isEmpty()) {
            return pushPayload;
        }
        if (EncryptKeys.ENCRYPT_SMS2_TYPE.equals(_encryptType)) {
            EncryptPushPayload encryptPushPayload = new EncryptPushPayload();
            try {
                encryptPushPayload.setPayload(String.valueOf(Base64.encode(SM2Util.encrypt(pushPayload, EncryptKeys.DEFAULT_SM2_ENCRYPT_KEY))));
            } catch (Exception e) {
                throw new RuntimeException("encrypt word exception", e);
            }
            encryptPushPayload.setAudience(audience);
            return encryptPushPayload.toString();
        }
        // 不支持的加密默认不加密
        return pushPayload;
    }

}


