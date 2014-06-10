package cn.jpush.api.app;

import cn.jpush.api.app.model.AppModel;
import cn.jpush.api.common.NativeHttpClient;
import cn.jpush.api.common.ResponseWrapper;
import cn.jpush.api.common.ServiceHelper;

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
public class AppClient {
    private static final String HOST_NAME_SSL = "https://admin.jpush.cn";
    private static final String REQUEST_PATH = "/v1/app";
    
    private NativeHttpClient _httpClient = new NativeHttpClient();;
    
    // The API secret of the appKey. Please get it from JPush Web Portal
    private final String _devSecret;
    
    // The KEY of the Application created on JPush. Please get it from JPush Web Portal
    private final String _devKey;
        
    // Generated HTTP Basic authorization string.
    private final String _authCode;
    private String _baseUrl;
    
    /**
     * Create a Push Client.
     * 
     * @param devSecret API access secret of the appKey.
     * @param devKey The KEY of one application on JPush.
     */
	public AppClient(String devKey, String devSecret) {
        this._devKey = devKey;
        this._devSecret = devSecret;
        
        this._authCode = ServiceHelper.getAuthorizationBase64(_devKey, _devSecret);
        this._baseUrl = HOST_NAME_SSL + REQUEST_PATH;
        //ServiceHelper.checkBasic(devKey, devSecret);
	}
	
    public void setBaseUrl(String baseUrl) {
        this._baseUrl = baseUrl;
    }
    
    public AppCreateResult create(AppModel app) {
        ResponseWrapper response = _httpClient.sendPost(_baseUrl, app.toString(), _authCode);
        
        return AppCreateResult.fromResponse(response);
    }
    
}


