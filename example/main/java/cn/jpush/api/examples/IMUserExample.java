package cn.jpush.api.examples;

import cn.jpush.api.jmessage.JMessageClient;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.jmessage.base.connection.APIRequestException;
import cn.jpush.api.jmessage.base.model.RegisterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class IMUserExample {

    protected static final Logger LOG = LoggerFactory.getLogger(IMUserExample.class);

    private static final String appkey = "242780bfdd7315dc1989fe2b";
    private static final String masterSecret = "2f5ced2bef64167950e63d13";

    public static void testRegisterUsers() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {

            List<RegisterInfo> users = new ArrayList<RegisterInfo>();

            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername("test_user")
                    .setPassword("test_pass")
                    .build();

            RegisterInfo user1 = RegisterInfo.newBuilder()
                    .setUsername("test_user1")
                    .setPassword("test_pass1")
                    .build();

            users.add(user);
            users.add(user1);

            RegisterInfo[] regUsers = new RegisterInfo[users.size()];

            String res = client.registerUsers(users.toArray(regUsers));
            System.out.println(res);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testGetUserInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            String res = client.getUserInfo("test_user");
            System.out.println(res);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testUpdatePassword() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.updateUserPassword("test_user", "test_new_pass");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testUpdateUserInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.updateUserInfo("test_user", "test_nick", "2000-01-12", "help me!", 1, "shenzhen", "nanshan", null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testGetUsers() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            String res = client.getUserList(0, 30);
            System.out.println(res);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testGetGroupsByUser() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            String res = client.getGroupListByUser("test_user");
            System.out.println(res);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void testDeleteUser() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.deleteUser("test_user_119");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        testGetGroupsByUser();
    }

}

