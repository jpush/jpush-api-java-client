package cn.jpush.api.examples;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.BooleanResult;
import cn.jpush.api.common.resp.DefaultResult;
import cn.jpush.api.device.AliasDeviceListResult;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.device.TagListResult;

public class DevcieExample {
	protected static final Logger LOG = LoggerFactory.getLogger(DevcieExample.class);

	private static final String appKey = "dd1066407b044738b6479275";
	private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
	private static JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
	private static final String TAG1 = "tag1";
	private static final String ALIAS1 = "alias1";
	private static final String ALIAS2 = "alias2";
	private static final String REGISTRATION_ID1 = "0900e8d85ef";
	private static final String REGISTRATION_ID2 = "0a04ad7d8b4";

	
	public static void main(String[] args) throws Exception {
		testGetDeviceTagAlias();
		testUpdateDeviceTagAlias();
		testGetTagList();
		testIsDeviceInTag();
		testAddRemoveDevicesFromTag();
		testDeleteTag();
		testGetAliasDeviceList();
		testDeleteAlias();
	}
	public static void testGetDeviceTagAlias() throws Exception {
		TagAliasResult result = jpushClient.getDeviceTagAlias(REGISTRATION_ID1);
		assertTrue(result.isResultOK());
	}

	public static void testUpdateDeviceTagAlias()
			throws APIConnectionException, APIRequestException {
		Set<String> tagsToAdd = new HashSet<String>();
		tagsToAdd.add("tag2");
		tagsToAdd.add("tag2");
		Set<String> tagsToRemove = new HashSet<String>();
		tagsToRemove.add("tag4");
		tagsToRemove.add("tag3");
		DefaultResult result = jpushClient.updateDeviceTagAlias(
				REGISTRATION_ID1, ALIAS1, false, tagsToAdd, tagsToRemove);
		assertTrue(result.isResultOK());
	}

	public static void testGetTagList() throws APIConnectionException,
			APIRequestException {
		TagListResult result = jpushClient.getTagList();
		assertTrue(result.isResultOK());
	}

	public static void testIsDeviceInTag() throws APIConnectionException,
			APIRequestException {
		BooleanResult result = jpushClient
				.isDeviceInTag(TAG1, REGISTRATION_ID1);
		assertTrue(result.isResultOK());
	}

	public static void testAddRemoveDevicesFromTag()
			throws APIConnectionException, APIRequestException {
		Set<String> toAddUsers = new HashSet<String>();
		toAddUsers.add(REGISTRATION_ID1);
		Set<String> toRemoveUsers = new HashSet<String>();
		toRemoveUsers.add(REGISTRATION_ID2);
		DefaultResult result = jpushClient.addRemoveDevicesFromTag(TAG1,
				toAddUsers, toRemoveUsers);
		assertTrue(result.isResultOK());
	}

	public static void testDeleteTag() throws APIConnectionException,
			APIRequestException {
		DefaultResult result = jpushClient.deleteTag(TAG1, null);
		assertTrue(result.isResultOK());
	}

	public static void testGetAliasDeviceList() throws APIConnectionException,
			APIRequestException {
		AliasDeviceListResult result = jpushClient.getAliasDeviceList(ALIAS1,
				null);
		assertTrue(result.isResultOK());
	}

	public static void testDeleteAlias() throws APIConnectionException,
			APIRequestException {
		DefaultResult result = jpushClient.deleteAlias(ALIAS2, null);
		assertTrue(result.isResultOK());
	}
}
