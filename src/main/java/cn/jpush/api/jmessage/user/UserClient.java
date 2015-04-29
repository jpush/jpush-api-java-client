package cn.jpush.api.jmessage.user;


import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.jmessage.base.BaseClient;
import cn.jpush.api.jmessage.base.ServiceConstant;
import cn.jpush.api.jmessage.base.connection.APIRequestException;
import cn.jpush.api.jmessage.base.connection.ResponseWrapper;
import cn.jpush.api.jmessage.base.model.RegisterPayload;
import cn.jpush.api.jmessage.base.model.UserPayload;
import cn.jpush.api.utils.Preconditions;
import cn.jpush.api.utils.StringUtils;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserClient extends BaseClient {

    private static final Logger LOG = LoggerFactory.getLogger(UserClient.class);

    public UserClient(String appkey, String masterSecret) {
        super(appkey, masterSecret);
    }

    public UserClient(String appkey, String masterSecret, int maxRetryTimes) {
        super(appkey, masterSecret, maxRetryTimes);
    }

    public UserClient(String appkey, String masterSecret, int maxRetryTimes, HttpProxy proxy) {
        super(appkey, masterSecret, maxRetryTimes, proxy);
    }

    public ResponseWrapper registerUsers(RegisterPayload payload)
            throws APIConnectionException, APIRequestException
    {

        Preconditions.checkArgument(!(null == payload), "payload should not be null");

        return _httpClient.sendPost(_baseUrl + ServiceConstant.USER_PATH, payload.toString());
    }

    public ResponseWrapper registerAdmins(RegisterPayload payload)
            throws APIConnectionException, APIRequestException
    {

        Preconditions.checkArgument( !(null == payload), "payload should not be null");

        return _httpClient.sendPost(_baseUrl + ServiceConstant.ADMIN_PATH, payload.toString());

    }

    public ResponseWrapper getUserInfo( String username )
            throws APIConnectionException, APIRequestException
    {

        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");

        return _httpClient.sendGet(_baseUrl + ServiceConstant.USER_PATH + "/" + username);
    }

    public ResponseWrapper updatePassword( String username, String password )
            throws APIConnectionException, APIRequestException
    {

        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(password), "password should not be empty");

        Preconditions.checkArgument( password.getBytes().length >= 4 && password.getBytes().length <=128,
                "The length of password must between 4 and 128 bytes. Input is " + password);

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("new_password", password);

        return _httpClient.sendPut(_baseUrl + ServiceConstant.USER_PATH + "/" + username + "/password",
                jsonObj.toString());

    }

    public ResponseWrapper updateUserInfo( String username, UserPayload payload )
            throws APIConnectionException,APIRequestException
    {
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");
        Preconditions.checkArgument( !(null == payload), "payload should not be null");

        return _httpClient.sendPut(_baseUrl + ServiceConstant.USER_PATH + "/" + username, payload.toString());
    }

    public ResponseWrapper getUserList( int start, int count )
            throws APIConnectionException, APIRequestException
    {

        Preconditions.checkPositionIndex(start, count);
        Preconditions.checkArgument(count <= 500, "count must not more than 500.");

        return _httpClient.sendGet(_baseUrl + ServiceConstant.USER_PATH + "?start=" + start + "&count=" + count);

    }

    public ResponseWrapper getGroupList( String username )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");

        return _httpClient.sendGet(_baseUrl + ServiceConstant.USER_PATH + "/" + username + "/groups");

    }

    public ResponseWrapper deleteUser( String username )
            throws APIConnectionException, APIRequestException
    {
        Preconditions.checkArgument( !StringUtils.isTrimedEmpty(username), "username should not be empty");

        return _httpClient.sendDelete(_baseUrl + ServiceConstant.USER_PATH + "/" + username);
    }
}
