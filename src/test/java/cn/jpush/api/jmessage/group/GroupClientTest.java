package cn.jpush.api.jmessage.group;


import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.jmessage.base.BaseTest;
import cn.jpush.api.jmessage.base.connection.APIRequestException;
import cn.jpush.api.jmessage.base.connection.ResponseWrapper;
import cn.jpush.api.jmessage.base.model.GroupPayload;
import cn.jpush.api.jmessage.base.model.Members;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GroupClientTest extends BaseTest {

    private static Logger LOG = LoggerFactory.getLogger(GroupClientTest.class);

    private GroupClient groupClient = null;

    @Before
    public void before() throws Exception {
        groupClient = new GroupClient(APP_KEY, MASTER_SECRET);
    }


    /**
     * Method: getGroupInfo(long gid)
     */
    @Test
    public void testGetGroupInfo() {
        try {
            ResponseWrapper res = groupClient.getGroupInfo(JUNIT_GID);

            assertEquals(200, res.responseCode);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            assertTrue(false);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupInfo_GidNegative() {
        try {
            groupClient.getGroupInfo(-1);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: getGroupMembers(long gid)
     */
    @Test
    public void testGetGroupMembers() {
        try {
            ResponseWrapper res = groupClient.getGroupMembers(JUNIT_GID);

            assertEquals(200, res.responseCode);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            assertTrue(false);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupMembers_GidNegative() {
        try {
            groupClient.getGroupMembers(-1);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: getGroupListByAppkey(int start, int count)
     */
    @Test
    public void testGetGroupListByAppkey() {
        try {
            ResponseWrapper res = groupClient.getGroupListByAppkey(0, 2);

            assertEquals(200, res.responseCode);

            try {
                JsonObject obj = parser.parse(res.responseContent).getAsJsonObject();
                int count = obj.get("count").getAsInt();
                assertEquals(2, count);
            } catch (Exception e) {
                LOG.error("parse response content error.", e);
                assertTrue(false);
            }

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            assertTrue(false);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetGroupListByAppkey_StartNegative() {
        try {
            groupClient.getGroupListByAppkey(-1, 3);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupListByAppkey_CountNegative() {
        try {
            groupClient.getGroupListByAppkey(0, -1);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetGroupListByAppkey_CountMoreThan500() {
        try {
            groupClient.getGroupListByAppkey(0, 501);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: createGroup(GroupPayload payload)
     */
    @Test
    public void testCreateGroup() {
        try {
            GroupPayload payload = GroupPayload.newBuilder()
                    .setOwner(JUNIT_USER)
                    .setName("junit_test_group")
                    .setDesc("for junit test")
                    .build();

            JsonObject obj = new JsonObject();
            obj.addProperty("owner_username", JUNIT_USER);
            obj.addProperty("group_name", "junit_test_group");
            obj.addProperty("group_desc", "for junit test");

            assertEquals(obj, payload.toJSON());

            ResponseWrapper res = groupClient.createGroup(payload);
            assertEquals(201, res.responseCode);

            try {
                JsonObject resObj = parser.parse(res.responseContent).getAsJsonObject();
                long gid = resObj.get("gid").getAsLong();

                ResponseWrapper res1 = groupClient.deleteGroup(gid);
                assertEquals(204, res1.responseCode);
            } catch (Exception e) {
                LOG.error("parse response content error.", e);
                assertTrue(false);
            }

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            assertTrue(false);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateGroupNull() {
        try {
            groupClient.createGroup(null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: addOrRemoveMembers(long gid, Members add, Members remove)
     */
    @Test
    public void testAddOrRemoveMembers() {
        try {
            Members members = Members.newBuilder()
                    .addMember(JUNIT_USER1).build();

            JsonArray array = new JsonArray();

            JsonPrimitive mem = new JsonPrimitive(JUNIT_USER1);
            array.add(mem);
            assertEquals(array, members.toJSON());

            ResponseWrapper res = groupClient.addOrRemoveMembers(JUNIT_GID, members, null);
            assertEquals(204, res.responseCode);

            ResponseWrapper res1 = groupClient.addOrRemoveMembers(JUNIT_GID, null, members);
            assertEquals(204, res1.responseCode);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            assertTrue(false);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddOrRemoveMembers_GidNegative() {
        try {
            groupClient.addOrRemoveMembers(-1, Members.newBuilder().build(), Members.newBuilder().build());
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddOrRemoveMembers_AddRemoveBothNull() {
        try {
            groupClient.addOrRemoveMembers(10010, null, null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: deleteGroup(long gid)
     */
    @Test
    public void testDeleteGroup() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteGroup_GidNegative() {
        try {
            groupClient.deleteGroup(-1);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    /**
     * Method: updateGroupInfo(long gid, String groupName, String groupDesc)
     */
    @Test
    public void testUpdateGroupInfo() {
        try {
            ResponseWrapper res = groupClient.updateGroupInfo(JUNIT_GID, "junit_group_new", null);
            assertEquals(204, res.responseCode);

            ResponseWrapper res1 = groupClient.updateGroupInfo(JUNIT_GID, null, "junit group desc");
            assertEquals(204, res1.responseCode);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            assertTrue(false);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
            assertTrue(false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateGroupInfo_GidNegative() {
        try {
            groupClient.updateGroupInfo(-1, "test_group", "group desc");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateGroupInfo_NameDescBothNull() {
        try {
            groupClient.updateGroupInfo(10010, null, null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateGroupInfo_NameOverLength() {
        try {
            groupClient.updateGroupInfo(10010, MORE_THAN_64, "test desc");
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateGroupInfo_DescOverLength() {
        try {
            groupClient.updateGroupInfo(10010, "test_name", MORE_THAN_250);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
    }

} 
