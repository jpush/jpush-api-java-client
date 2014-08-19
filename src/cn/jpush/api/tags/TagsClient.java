package cn.jpush.api.tags;

import java.util.Set;

import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.common.BaseResult;
import cn.jpush.api.common.HttpProxy;
import cn.jpush.api.common.IHttpClient;
import cn.jpush.api.common.NativeHttpClient;
import cn.jpush.api.common.NormalResult;
import cn.jpush.api.common.ResponseWrapper;
import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.utils.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class TagsClient {
    public static final String HOST_NAME_SSL = "https://devices.jpush.cn";
    public static final String DEVICE_PATH = "/v3/device";
    public static final String TAG_PATH = "/v3/tag";
    public static final String ALIAS_PATH = "/v3/alias";
    
    private final NativeHttpClient _httpClient;

    public TagsClient(String masterSecret, String appKey) {
        this(masterSecret, appKey, IHttpClient.DEFAULT_MAX_RETRY_TIMES);
    }
    
    public TagsClient(String masterSecret, String appKey, int maxRetryTimes) {
        this(masterSecret, appKey, maxRetryTimes, null);
    }
    
    public TagsClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        ServiceHelper.checkBasic(appKey, masterSecret);
        
        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, maxRetryTimes, proxy);
    }

    
    // -------------- device 
    
    public TagAliasResult getDeviceTagAlias(String registrationId) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + DEVICE_PATH + "/" + registrationId;
        
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return BaseResult.fromResponse(response, TagAliasResult.class);
    }
    
    public NormalResult updateDeviceTagAlias(String registrationId, String alias, boolean clearTag, 
            Set<String> tagsToAdd, Set<String> tagsToRemove) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + DEVICE_PATH + "/" + registrationId;
        
        JsonObject top = new JsonObject();
        if (null != alias) {
            top.addProperty("alias", alias);
        }
        if (clearTag) {
            top.addProperty("tag", "");
        } else {
            JsonObject tagObject = new JsonObject();
            JsonArray tagsAdd = ServiceHelper.fromSet(tagsToAdd);
            if (tagsAdd.size() > 0) {
                tagObject.add("add", tagsAdd);
            }
            
            JsonArray tagsRemove = ServiceHelper.fromSet(tagsToRemove);
            if (tagsRemove.size() > 0) {
                tagObject.add("remove", tagsRemove);
            }
            
            if (tagObject.entrySet().size() > 0) {
                top.add("tag", tagObject);
            }
        }
        
        ResponseWrapper response = _httpClient.sendPost(url, top.toString());
        
        return BaseResult.fromResponse(response, NormalResult.class);
    }
    
    
    // ------------- tags

    public TagListResult getTagList(String platform) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + TAG_PATH + "/list";
        if (!StringUtils.isEmpty(platform)) {
            url += "?platform=" + platform;
        }
        
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return BaseResult.fromResponse(response, TagListResult.class);
    }
    
    public NormalResult addRemoveDevicesFromTag(String theTag, Set<String> toAddUsers, Set<String> toRemoveUsers) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + TAG_PATH + "/" + theTag;
        
        JsonObject top = new JsonObject();
        JsonObject registrationIds = new JsonObject();
        
        if (null != toAddUsers && toAddUsers.size() > 0) {
            JsonArray array = new JsonArray();
            for (String user : toAddUsers) {
                array.add(new JsonPrimitive(user));
            }
            registrationIds.add("add", array);
        }
        if (null != toRemoveUsers && toRemoveUsers.size() > 0) {
            JsonArray array = new JsonArray();
            for (String user : toRemoveUsers) {
                array.add(new JsonPrimitive(user));
            }
            registrationIds.add("remove", array);
        }
        
        top.add("registration_ids", registrationIds);
        
        ResponseWrapper response = _httpClient.sendPost(url, top.toString());
        
        return BaseResult.fromResponse(response, NormalResult.class);
    }
    
    public NormalResult deleteTag(String theTag) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + TAG_PATH + "/" + theTag;
        
        ResponseWrapper response = _httpClient.sendDelete(url);
        
        return BaseResult.fromResponse(response, NormalResult.class);        
    }
    
    public NormalResult isDeviceInTag(String theTag, String registrationID) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + TAG_PATH + "/" + theTag + "/exist";
        
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return BaseResult.fromResponse(response, NormalResult.class);        
    }
    
    
    // ------------- alias
    
    public AliasDeviceListResult getAliasDeviceList(String alias) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + ALIAS_PATH + "/" + alias;
        
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return BaseResult.fromResponse(response, AliasDeviceListResult.class);
    }
    
    public NormalResult deleteAlias(String alias) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + ALIAS_PATH + "/" + alias;
        
        ResponseWrapper response = _httpClient.sendDelete(url);
        
        return BaseResult.fromResponse(response, NormalResult.class);
    }
        
}
