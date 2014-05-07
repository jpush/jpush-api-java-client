package cn.jpush.api.push;

import cn.jpush.api.common.BaseHttpClient;
import cn.jpush.api.common.ResponseWrapper;
import cn.jpush.api.push.model.PushPayload;

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
public class PushClient extends BaseHttpClient {
    private static String HOST_NAME_SSL = "https://api.jpush.cn";
    private static final String PUSH_PATH = "/v3/push";
    
    // The API secret of the appKey. Please get it from JPush Web Portal
    private final String _masterSecret;
    
    // The KEY of the Application created on JPush. Please get it from JPush Web Portal
    private final String _appKey;
    
    // If not present, true by default.
    private boolean _apnsProduction = true;
    
    // If not present, the default value is 86400(s) (one day)
    private long _timeToLive = 60 * 60 * 24;
    
    
    private boolean _overallSettingEnabled = false;
    
    // Generated HTTP Basic authorization string.
    private final String _authCode;
    
    
	public PushClient(String masterSecret, String appKey) {
        this._masterSecret = masterSecret;
        this._appKey = appKey;
        this._authCode = getAuthorizationBase64(_appKey, _masterSecret);
        checkBasic(appKey, masterSecret);
	}
	
    public PushClient(String masterSecret, String appKey, boolean apnsProduction, long timeToLive) {
        this(masterSecret, appKey);
        this._apnsProduction = apnsProduction;
        this._timeToLive = timeToLive;
        this._overallSettingEnabled = true;
    }
    
    public PushResult sendPush(PushPayload pushPayload) {
        if (_overallSettingEnabled) {
            pushPayload.resetOptionsTimeToLive(_timeToLive);
            pushPayload.resetOptionsApnsProduction(_apnsProduction);
        }
        
        String url = HOST_NAME_SSL + PUSH_PATH;
        ResponseWrapper response = sendPost(url, pushPayload.toString(), _authCode);
        
        return PushResult.fromResponse(response);
    }
    
}


