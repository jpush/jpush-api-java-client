package cn.jpush.api.push;

import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.connection.IHttpClient;
import cn.jpush.api.common.connection.NativeHttpClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.BaseResult;
import cn.jpush.api.common.resp.ResponseWrapper;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.utils.Preconditions;
import cn.jpush.api.utils.StringUtils;

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
    public static final String HOST_NAME_SSL = "https://api.jpush.cn";
    public static final String PUSH_PATH = "/v3/push";
    public static final String PUSH_VALIDATE_PATH = "/v3/push/validate";
    
    private final NativeHttpClient _httpClient;
    private JsonParser _jsonParser = new JsonParser();

    // If not present, true by default.
    private boolean _apnsProduction = true;
    
    // If not present, the default value is 86400(s) (one day)
    private long _timeToLive = 60 * 60 * 24;
    
    private boolean _globalSettingEnabled = false;
    
    private String _baseUrl;
    
    /**
     * Create a Push Client.
     * 
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     */
	public PushClient(String masterSecret, String appKey) {
	    this(masterSecret, appKey, IHttpClient.DEFAULT_MAX_RETRY_TIMES);
	}
	
	public PushClient(String masterSecret, String appKey, int maxRetryTimes) {
	    this(masterSecret, appKey, maxRetryTimes, null);
	}
	
	/**
	 * Create a Push Client with max retry times.
	 * 
	 * @param masterSecret  API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 * @param maxRetryTimes max retry times
	 */
	public PushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        ServiceHelper.checkBasic(appKey, masterSecret);
        
        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._baseUrl = HOST_NAME_SSL;
        this._httpClient = new NativeHttpClient(authCode, maxRetryTimes, proxy);
	}

	/**
     * Create a Push Client with global settings.
     * 
     * If you want different settings from default globally, this constructor is what you needed.
     * 
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param apnsProduction Global APNs environment setting. It will override PushPayload Options.
     * @param timeToLive Global time_to_live setting. It will override PushPayload Options.
     */
    public PushClient(String masterSecret, String appKey, boolean apnsProduction, long timeToLive) {
        this(masterSecret, appKey);
        
        this._apnsProduction = apnsProduction;
        this._timeToLive = timeToLive;
        this._globalSettingEnabled = true;
    }
    
    public void setDefaults(boolean apnsProduction, long timeToLive) {
        this._apnsProduction = apnsProduction;
        this._timeToLive = timeToLive;
        this._globalSettingEnabled = true;
    }
    
    public void setBaseUrl(String baseUrl) {
        this._baseUrl = baseUrl;
    }
    
    public PushResult sendPush(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(! (null == pushPayload), "pushPayload should not be null");
        
        if (_globalSettingEnabled) {
            pushPayload.resetOptionsTimeToLive(_timeToLive);
            pushPayload.resetOptionsApnsProduction(_apnsProduction);
        }
        
        ResponseWrapper response = _httpClient.sendPost(_baseUrl + PUSH_PATH, pushPayload.toString());
        
        return BaseResult.fromResponse(response, PushResult.class);
    }
    
    public PushResult sendPushValidate(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(! (null == pushPayload), "pushPayload should not be null");
        
        if (_globalSettingEnabled) {
            pushPayload.resetOptionsTimeToLive(_timeToLive);
            pushPayload.resetOptionsApnsProduction(_apnsProduction);
        }
        
        ResponseWrapper response = _httpClient.sendPost(_baseUrl + PUSH_VALIDATE_PATH, pushPayload.toString());
        
        return BaseResult.fromResponse(response, PushResult.class);
    }
    
    public PushResult sendPush(String payloadString) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(payloadString), "pushPayload should not be empty");
        
        try {
            _jsonParser.parse(payloadString);
        } catch (JsonParseException e) {
            Preconditions.checkArgument(false, "payloadString should be a valid JSON string.");
        }
        
        ResponseWrapper response = _httpClient.sendPost(_baseUrl + PUSH_PATH, payloadString);
        
        return BaseResult.fromResponse(response, PushResult.class);
    }
    
    public PushResult sendPushValidate(String payloadString) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(payloadString), "pushPayload should not be empty");
        
        try {
            _jsonParser.parse(payloadString);
        } catch (JsonParseException e) {
            Preconditions.checkArgument(false, "payloadString should be a valid JSON string.");
        }
        
        ResponseWrapper response = _httpClient.sendPost(_baseUrl + PUSH_VALIDATE_PATH, payloadString);
        
        return BaseResult.fromResponse(response, PushResult.class);
    }


}


