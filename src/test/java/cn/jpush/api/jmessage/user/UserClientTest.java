package cn.jpush.api.jmessage.user;


import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.jmessage.base.BaseTest;
import cn.jpush.api.jmessage.base.connection.APIRequestException;
import cn.jpush.api.jmessage.base.connection.ResponseWrapper;
import cn.jpush.api.jmessage.base.model.RegisterInfo;
import cn.jpush.api.jmessage.base.model.RegisterPayload;
import cn.jpush.api.jmessage.base.model.UserPayload;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UserClientTest extends BaseTest {


    private UserClient userClient = null;

    @Before
    public void before() throws Exception {
        userClient = new UserClient(APP_KEY, MASTER_SECRET);
    }

    /**
     * Method: registerUsers(RegisterPayload payload)
     */
    @Test
    public void testRegisterUsers() {
        try {
            List<RegisterInfo> users = new ArrayList<RegisterInfo>();
            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername("junit_test_user")
                    .setPassword("junit_test_pass")
                    .build();

            JsonObject obj = new JsonObject();
            obj.addProperty("username", "junit_test_user");
            obj.addProperty("password", "junit_test_pass");

            Assert.assertEquals("", obj, user.toJSON());

            users.add(user);
            RegisterInfo[] regUsers = new RegisterInfo[users.size()];

            RegisterPayload payload = RegisterPayload.newBuilder()
                    .addUsers(users.toArray(regUsers)).build();

            JsonArray arr = new JsonArray();
            arr.add(obj);

            Assert.assertEquals("", arr, payload.toJSON());

            ResponseWrapper res = userClient.registerUsers(payload);
            assertEquals(201, res.responseCode);

            ResponseWrapper res1 = userClient.deleteUser("junit_test_user");
            assertEquals(204, res1.responseCode);

        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
            assertTrue(false);
        }
    }

    @Test
    public void testRegisterUsers_exist() {
        try {
            List<RegisterInfo> users = new ArrayList<RegisterInfo>();
            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername(JUNIT_USER1)
                    .setPassword(JUNIT_USER1)
                    .build();

            users.add(user);
            RegisterInfo[] regUsers = new RegisterInfo[users.size()];

            RegisterPayload payload = RegisterPayload.newBuilder()
                    .addUsers(users.toArray(regUsers)).build();

            ResponseWrapper res = userClient.registerUsers(payload);
            assertEquals(201, res.responseCode);

        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (APIRequestException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getErrorMessage().contains("exit"));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterUserNull() {
        try {
            userClient.registerUsers(null);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method: registerAdmins(RegisterPayload payload)
     */
    @Test
    public void testRegisterAdmins() {
        try {
            List<RegisterInfo> users = new ArrayList<RegisterInfo>();
            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername("junit_test_admin")
                    .setPassword("junit_test_pass")
                    .build();

            JsonObject obj = new JsonObject();
            obj.addProperty("username", "junit_test_admin");
            obj.addProperty("password", "junit_test_pass");

            Assert.assertEquals("", obj, user.toJSON());

            users.add(user);
            RegisterInfo[] regUsers = new RegisterInfo[users.size()];

            RegisterPayload payload = RegisterPayload.newBuilder()
                    .addUsers(users.toArray(regUsers)).build();

            JsonArray arr = new JsonArray();
            arr.add(obj);

            Assert.assertEquals("", arr, payload.toJSON());

            ResponseWrapper res = userClient.registerAdmins(payload);
            assertEquals(201, res.responseCode);

            userClient.deleteUser("junit_test_admin");

        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterAdminNull() {
        try {
            userClient.registerAdmins(null);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }


    /**
     * Method: getUserInfo(String username)
     */
    @Test
    public void testGetUserInfo() {
        try {

            ResponseWrapper res = userClient.getUserInfo(JUNIT_USER);
            assertEquals(200, res.responseCode);
            assertTrue(res.responseContent.contains(JUNIT_USER));
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserInfoNull() {
        try {
            userClient.getUserInfo(null);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserInfoEmpty() {
        try {
            userClient.getUserInfo("");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserInfoBlank() {
        try {
            userClient.getUserInfo("  ");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    /**
     * Method: updatePassword(String username, String password)
     */
    @Test
    public void testUpdatePassword() {
        try {
            ResponseWrapper res = userClient.updatePassword(JUNIT_USER, "junit_new_password");
            System.out.println(res.responseContent);
            assertEquals(204, res.responseCode);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_UsernameNull() {
        try {
            userClient.updatePassword(null, "password");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_UsernameBlank() {
        try {
            userClient.updatePassword(" ", "password");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_PasswordNull() {
        try {
            userClient.updatePassword("test_user", null);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_PasswordBlank() {
        try {
            userClient.updatePassword("test_user", " ");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_PasswordLengthNotEnough() {
        try {
            userClient.updatePassword("test_user", "123");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdatePassword_PasswordOverLength() {
        try {
            userClient.updatePassword("test_user", MORE_THAN_128);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    /**
     * Method: updateUserInfo(String username, UserPayload payload)
     */
    @Test
    public void testUpdateUserInfo() {
        try {

            UserPayload payload = UserPayload.newBuilder()
                    .setRegion("shenzhen")
                    .setBirthday("2015-04-01")
                    .build();

            JsonObject obj = new JsonObject();
            obj.addProperty("region", "shenzhen");
            obj.addProperty("birthday", "2015-04-01" );

            Assert.assertEquals("", obj, payload.toJSON());

            ResponseWrapper res = userClient.updateUserInfo(JUNIT_USER, payload);
            assertEquals(204, res.responseCode);

        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserInfo_UsernameNull() {
        try {
            userClient.updateUserInfo(null, UserPayload.newBuilder().build());
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserInfo_UsernameBlank() {
        try {
            userClient.updateUserInfo(" ", UserPayload.newBuilder().build());
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserInfo_PayloadNull() {
        try {
            userClient.updateUserInfo("test_user", null);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    /**
     * Method: getUserList(int start, int count)
     */
    @Test
    public void testGetUserList() {
        try {

            ResponseWrapper res = userClient.getUserList(0, 5);
            assertEquals(200, res.responseCode);

            try {
                JsonObject obj = parser.parse(res.responseContent).getAsJsonObject();
                assertEquals(5, obj.get("count").getAsInt());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                assertTrue(false);
            }

        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetUserList_StartNegative() {
        try {
            userClient.getUserList(-1, 3);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserList_CountNegative() {
        try {
            userClient.getUserList(0, -1);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserList_CountMoreThan500() {
        try {
            userClient.getUserList(0, 501);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    /**
     * Method: getGroupList(String username)
     */
    @Test
    public void testGetGroupList() {
        try {

            ResponseWrapper res = userClient.getGroupList(JUNIT_USER);
            assertEquals(200, res.responseCode);

        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupList_UsernameNull() {
        try {
            userClient.getGroupList(null);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupList_UsernameBlank() {
        try {
            userClient.getGroupList(" ");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    /**
     * Method: deleteUser(String username)
     */
    @Test
    public void testDeleteUser() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUser_UsernameNull() {
        try {
            userClient.deleteUser(null);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUser_UsernameBlank() {
        try {
            userClient.deleteUser(" ");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

}
