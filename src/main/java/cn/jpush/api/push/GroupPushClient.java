package cn.jpush.api.push;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.connection.IHttpClient;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;
import cn.jpush.api.push.model.PushPayload;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * Created by caiyaoguan on 2017/7/4.
 */
public class GroupPushClient {

    private IHttpClient _httpClient;
    private String _baseUrl;
    private String _groupPushPath;
    private Gson _gson = new Gson();

    public GroupPushClient(String groupMasterSecret, String groupKey) {
        this(groupMasterSecret, groupKey, null, ClientConfig.getInstance());
    }

    public GroupPushClient(String groupMasterSecret, String groupKey, HttpProxy proxy, ClientConfig conf) {
        ServiceHelper.checkBasic(groupKey, groupMasterSecret);
        this._baseUrl = (String) conf.get(ClientConfig.PUSH_HOST_NAME);
        this._groupPushPath = (String) conf.get(ClientConfig.GROUP_PUSH_PATH);
        String authCode = ServiceHelper.getBasicAuthorization("group-" + groupKey, groupMasterSecret);
        this._httpClient = new NativeHttpClient(authCode, proxy, conf);
    }

    public Map<String, PushResult> sendGroupPush(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(! (null == pushPayload), "pushPayload should not be null");

        ResponseWrapper response = _httpClient.sendPost(_baseUrl + _groupPushPath, pushPayload.toString());
        return  _gson.fromJson(response.responseContent,
                new TypeToken<Map<String, PushResult>>(){}.getType());
    }

}
