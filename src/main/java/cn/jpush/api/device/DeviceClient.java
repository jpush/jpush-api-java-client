package cn.jpush.api.device;

import java.util.Set;

import cn.jpush.api.common.ServiceHelper;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.connection.IHttpClient;
import cn.jpush.api.common.connection.NativeHttpClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.BaseResult;
import cn.jpush.api.common.resp.BooleanResult;
import cn.jpush.api.common.resp.DefaultResult;
import cn.jpush.api.common.resp.ResponseWrapper;
import cn.jpush.api.utils.Preconditions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class DeviceClient {
    public static final String HOST_NAME_SSL = "https://device.jpush.cn";
    public static final String DEVICES_PATH = "/v3/devices";
    public static final String TAGS_PATH = "/v3/tags";
    public static final String ALIASES_PATH = "/v3/aliases";
    
    private final NativeHttpClient _httpClient;

    public DeviceClient(String masterSecret, String appKey) {
        this(masterSecret, appKey, IHttpClient.DEFAULT_MAX_RETRY_TIMES);
    }
    
    public DeviceClient(String masterSecret, String appKey, int maxRetryTimes) {
        this(masterSecret, appKey, maxRetryTimes, null);
    }
    
    public DeviceClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        ServiceHelper.checkBasic(appKey, masterSecret);
        
        String authCode = ServiceHelper.getBasicAuthorization(appKey, masterSecret);
        this._httpClient = new NativeHttpClient(authCode, maxRetryTimes, proxy);
    }

    
    // -------------- device 
    
    public TagAliasResult getDeviceTagAlias(String registrationId) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + DEVICES_PATH + "/" + registrationId;
        
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return BaseResult.fromResponse(response, TagAliasResult.class);
    }
    
    public DefaultResult updateDeviceTagAlias(String registrationId, boolean clearAlias, boolean clearTag) throws APIConnectionException, APIRequestException {
    	Preconditions.checkArgument(clearAlias || clearTag, "It is not meaningful to do nothing.");
    	
        String url = HOST_NAME_SSL + DEVICES_PATH + "/" + registrationId;
        
        JsonObject top = new JsonObject();
        if (clearAlias) {
            top.addProperty("alias", "");
        }
        if (clearTag) {
            top.addProperty("tags", "");
        }
        
        ResponseWrapper response = _httpClient.sendPost(url, top.toString());
        
        return DefaultResult.fromResponse(response);
    }    
    
    public DefaultResult updateDeviceTagAlias(String registrationId, String alias,  
            Set<String> tagsToAdd, Set<String> tagsToRemove) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + DEVICES_PATH + "/" + registrationId;
        
        JsonObject top = new JsonObject();
        if (null != alias) {
            top.addProperty("alias", alias);
        }
        
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
            top.add("tags", tagObject);
        }
        
        ResponseWrapper response = _httpClient.sendPost(url, top.toString());
        
        return DefaultResult.fromResponse(response);
    }

    // ------------- tags

    public TagListResult getTagList() throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + TAGS_PATH + "/";
        
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return TagListResult.fromResponse(response, TagListResult.class);
    }
    
    public BooleanResult isDeviceInTag(String theTag, String registrationID) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + TAGS_PATH + "/" + theTag + "/registration_ids/" + registrationID;
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return BaseResult.fromResponse(response, BooleanResult.class);        
    }
    
    public DefaultResult addRemoveDevicesFromTag(String theTag, Set<String> toAddUsers, Set<String> toRemoveUsers) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + TAGS_PATH + "/" + theTag;
        
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
        
        return DefaultResult.fromResponse(response);
    }
    
    public DefaultResult deleteTag(String theTag, String platform) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + TAGS_PATH + "/" + theTag;
        if (null != platform) {
        	url += "?platform=" + platform; 
        }
        
        ResponseWrapper response = _httpClient.sendDelete(url);
        
        return DefaultResult.fromResponse(response);        
    }
    
    
    // ------------- alias
    
    public AliasDeviceListResult getAliasDeviceList(String alias, String platform) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + ALIASES_PATH + "/" + alias;
        if (null != platform) {
        	url += "?platform=" + platform; 
        }
        
        ResponseWrapper response = _httpClient.sendGet(url);
        
        return BaseResult.fromResponse(response, AliasDeviceListResult.class);
    }
    
    public DefaultResult deleteAlias(String alias, String platform) throws APIConnectionException, APIRequestException {
        String url = HOST_NAME_SSL + ALIASES_PATH + "/" + alias;
        if (null != platform) {
        	url += "?platform=" + platform; 
        }
        
        ResponseWrapper response = _httpClient.sendDelete(url);
        
        return DefaultResult.fromResponse(response);
    }
        
}




