package cn.jpush.api.jmessage;


import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.jmessage.base.connection.APIRequestException;

public class IMGroupExample {

    private static final String appkey = "242780bfdd7315dc1989fe2b";
    private static final String masterSecret = "2f5ced2bef64167950e63d13";

    public static void testCreateGroup() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);
        try {
            String res = client.createGroup("test_user", "test_gname1", "description", "test_user");
            System.out.println(res);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public static void testGetGroupInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            String res = client.getGroupInfo(10003767);
            System.out.println(res);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public static void testGetGroupMemberList() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            String res = client.getGroupMembers(10003767);
            System.out.println(res);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public static void testGetGroupListByAppkey() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            String res = client.getGroupListByAppkey(0, 30);
            System.out.println(res);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public static void testManageGroup() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            String[] addList = {"baobao148"};
            String[] removeList = {"baobao148"};
            client.addOrRemoveMembers(10003767, addList, null );
            client.addOrRemoveMembers(10003767, null, removeList);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public static void testUpdateGroupInfo() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.updateGroupInfo(10003767, "test_gname_new", "update desc");
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public static void testDeleteGroup() {
        JMessageClient client = new JMessageClient(appkey, masterSecret);

        try {
            client.deleteGroup(10003765);
        } catch (APIConnectionException e) {
            System.out.println(e.getMessage());
        } catch (APIRequestException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public static void main(String[] args) {
//        testGetGroupInfo();
//        testGetGroupListByAppkey();
//        testUpdateGroupInfo();
//        testDeleteGroup();

    }
}
