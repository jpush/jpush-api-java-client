package cn.jpush.api.jmessage;


import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.jmessage.base.connection.APIRequestException;
import cn.jpush.api.jmessage.base.model.*;
import cn.jpush.api.jmessage.group.GroupClient;
import cn.jpush.api.jmessage.user.UserClient;

public class JMessageClient {

    private final UserClient _userClient;

    private final GroupClient _groupClient;


    public JMessageClient(String appkey, String masterSecret) {
        _userClient = new UserClient(appkey, masterSecret);
        _groupClient = new GroupClient(appkey, masterSecret);
    }

    public JMessageClient(String appkey, String masterSecret, int maxRetryTimes) {
        _userClient = new UserClient(appkey, masterSecret, maxRetryTimes);
        _groupClient = new GroupClient(appkey, masterSecret, maxRetryTimes);
    }

    public JMessageClient(String appkey, String masterSecret, int maxRetryTimes, HttpProxy proxy) {
        _userClient = new UserClient(appkey, masterSecret, maxRetryTimes, proxy);
        _groupClient = new GroupClient(appkey, masterSecret, maxRetryTimes, proxy);
    }

    public String registerUsers(RegisterInfo[] users)
            throws APIConnectionException, APIRequestException
    {
        RegisterPayload payload = RegisterPayload.newBuilder()
                .addUsers(users)
                .build();

        return _userClient.registerUsers(payload).responseContent;
    }

    public String registerAdmins(RegisterInfo[] admins)
            throws APIConnectionException, APIRequestException
    {
        RegisterPayload payload = RegisterPayload.newBuilder()
                .addUsers(admins)
                .build();

        return _userClient.registerAdmins(payload).responseContent;
    }

    public String getUserInfo(String username)
            throws APIConnectionException, APIRequestException
    {
        return _userClient.getUserInfo(username).responseContent;
    }

    public void updateUserPassword(String username, String password)
            throws APIConnectionException, APIRequestException
    {
        _userClient.updatePassword(username, password);
    }

    public void updateUserInfo(String username, String nickname, String birthday, String signature, int gender,
                               String region, String address, String avatar)
            throws APIConnectionException, APIRequestException
    {
        UserPayload payload = UserPayload.newBuilder()
                .setNickname(nickname)
                .setBirthday(birthday)
                .setSignature(signature)
                .setGender(gender)
                .setRegion(region)
                .setAddress(address)
                .setAvatar(avatar)
                .build();

        _userClient.updateUserInfo(username, payload);
    }

    public String getUserList(int start, int count)
            throws APIConnectionException, APIRequestException
    {
        return _userClient.getUserList(start, count).responseContent;
    }

    public String getGroupListByUser(String username)
            throws APIConnectionException, APIRequestException
    {
        return _userClient.getGroupList(username).responseContent;
    }

    public void deleteUser(String username)
            throws APIConnectionException, APIRequestException
    {
        _userClient.deleteUser(username);
    }


    public String getGroupInfo(long gid)
            throws APIConnectionException, APIRequestException
    {
        return _groupClient.getGroupInfo(gid).responseContent;
    }

    public String getGroupMembers(long gid)
            throws APIConnectionException, APIRequestException
    {
        return _groupClient.getGroupMembers(gid).responseContent;
    }

    public String getGroupListByAppkey(int start, int count)
            throws APIConnectionException, APIRequestException
    {
        return _groupClient.getGroupListByAppkey(start, count).responseContent;
    }

    public String createGroup(String owner, String gname, String desc, String... userlist)
            throws APIConnectionException, APIRequestException
    {
        Members members = Members.newBuilder().addMember(userlist).build();

        GroupPayload payload = GroupPayload.newBuilder()
                .setOwner(owner)
                .setName(gname)
                .setDesc(desc)
                .setMembers(members)
                .build();

        return _groupClient.createGroup(payload).responseContent;
    }

    public void addOrRemoveMembers(long gid, String[] addList, String[] removeList)
            throws APIConnectionException, APIRequestException
    {
        Members add = Members.newBuilder()
                .addMember(addList)
                .build();

        Members remove = Members.newBuilder()
                .addMember(removeList)
                .build();

        _groupClient.addOrRemoveMembers(gid, add, remove);
    }

    public void deleteGroup(long gid)
            throws APIConnectionException, APIRequestException
    {
        _groupClient.deleteGroup(gid);
    }

    public void updateGroupInfo(long gid, String groupName, String groupDesc)
            throws APIConnectionException, APIRequestException
    {
        _groupClient.updateGroupInfo(gid, groupName, groupDesc);
    }

}
