package cn.jpush.api.jmessage.group;


import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.jmessage.base.BaseClient;
import cn.jpush.api.jmessage.base.ServiceConstant;
import cn.jpush.api.jmessage.base.connection.APIRequestException;
import cn.jpush.api.jmessage.base.connection.ResponseWrapper;
import cn.jpush.api.jmessage.base.model.GroupPayload;
import cn.jpush.api.jmessage.base.model.Members;
import cn.jpush.api.utils.Preconditions;
import cn.jpush.api.utils.StringUtils;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GroupClient extends BaseClient {

    private static final Logger LOG = LoggerFactory.getLogger(GroupClient.class);

    public GroupClient(String appkey, String masterSecret) {
        super(appkey, masterSecret);
    }

    public GroupClient(String appkey, String masterSecret, int maxRetryTimes) {
        super(appkey, masterSecret, maxRetryTimes);
    }

    public GroupClient(String appkey, String masterSecret, int maxRetryTimes, HttpProxy proxy) {
        super(appkey, masterSecret, maxRetryTimes, proxy);
    }

    public ResponseWrapper getGroupInfo( long gid )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument(gid > 0, "gid should more than 0.");

        return _httpClient.sendGet(_baseUrl + ServiceConstant.GROUP_PATH + "/" + gid);
    }

    public ResponseWrapper getGroupMembers( long gid )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument(gid > 0, "gid should more than 0.");

        return _httpClient.sendGet(_baseUrl + ServiceConstant.GROUP_PATH + "/" + gid + "/members");
    }

    public ResponseWrapper getGroupListByAppkey( int start, int count )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkPositionIndex(start, count);
        Preconditions.checkArgument(count <= 500, "count should not more than 500.");

        return _httpClient.sendGet(_baseUrl + ServiceConstant.GROUP_PATH + "?start=" + start + "&count=" + count);
    }

    public ResponseWrapper createGroup(GroupPayload payload)
            throws APIConnectionException, APIRequestException
    {

        Preconditions.checkArgument(! (null == payload), "group payload should not be null");

        return _httpClient.sendPost(_baseUrl + ServiceConstant.GROUP_PATH, payload.toString());
    }

    public ResponseWrapper addOrRemoveMembers( long gid, Members add, Members remove)
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument(gid > 0, "gid should more than 0.");

        if ( null == add && null == remove ) {
            Preconditions.checkArgument(false, "add list or remove list should not be null at the same time.");
        }

        JsonObject json = new JsonObject();

        if ( null != add ) {
            json.add("add", add.toJSON());
        }

        if ( null != remove ) {
            json.add("remove", remove.toJSON());
        }

        return _httpClient.sendPost(_baseUrl + ServiceConstant.GROUP_PATH + "/" + gid + "/members", json.toString());

    }

    public ResponseWrapper deleteGroup( long gid )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument(gid > 0, "gid should more than 0.");
        return _httpClient.sendDelete(_baseUrl + ServiceConstant.GROUP_PATH + "/" + gid);
    }

    public ResponseWrapper updateGroupInfo( long gid, String groupName, String groupDesc )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument(gid > 0, "gid should more than 0.");

        if ( StringUtils.isTrimedEmpty(groupName) && StringUtils.isTrimedEmpty(groupDesc)) {
            Preconditions.checkArgument(false, "group name or group description should not be null at the same time.");
        }

        JsonObject json = new JsonObject();
        if (StringUtils.isNotEmpty(groupName)) {
            groupName = groupName.trim();
            Preconditions.checkArgument( groupName.getBytes().length <= 64,
                    "The length of group name must not more than 64 bytes.");
            Preconditions.checkArgument( !StringUtils.isLineBroken(groupName),
                    "The group name must not contain line feed character.");
            json.addProperty(GroupPayload.GROUP_NAME, groupName);
        }

        if (StringUtils.isNotEmpty(groupDesc)) {
            groupDesc = groupDesc.trim();
            Preconditions.checkArgument(groupDesc.getBytes().length <= 250,
                    "The length of group description must not more than 250 bytes.");
            json.addProperty(GroupPayload.DESC, groupDesc);
        }

        return _httpClient.sendPut(_baseUrl + ServiceConstant.GROUP_PATH + "/" + gid, json.toString());
    }

}
