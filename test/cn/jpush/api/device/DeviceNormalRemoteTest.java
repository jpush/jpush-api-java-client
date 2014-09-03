package cn.jpush.api.device;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cn.jpush.api.BaseTest;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.BooleanResult;
import cn.jpush.api.common.resp.DefaultResult;

public class DeviceNormalRemoteTest extends BaseTest {
	
	
	@Test
	public void testGetDeviceTagAlias_1() throws Exception {
		TagAliasResult result = jpushClient.getDeviceTagAlias(REGISTRATION_ID1);
		assertTrue(result.isResultOK());
	}
	
	@Test
	public void testUpdateDeviceTagAlias_1() throws APIConnectionException, APIRequestException {
		Set<String> tagsToAdd = new HashSet<String>(); 
		tagsToAdd.add("tag2");
		tagsToAdd.add("tag2");
		Set<String> tagsToRemove = new HashSet<String>();
		tagsToRemove.add("tag4");
		tagsToRemove.add("tag3");
		DefaultResult result = jpushClient.updateDeviceTagAlias(REGISTRATION_ID1, ALIAS1, false, tagsToAdd, tagsToRemove);
		assertTrue(result.isResultOK());
	}

	@Test
	public void testUpdateDeviceTagAlias_2() throws APIConnectionException, APIRequestException {
		DefaultResult result = jpushClient.updateDeviceTagAlias(REGISTRATION_ID1, ALIAS1, true, null, null);
		assertTrue(result.isResultOK());
	}
	
	@Test
	public void testGetTagList_1() throws APIConnectionException, APIRequestException {
		TagListResult result = jpushClient.getTagList();
		assertTrue(result.isResultOK());
	}
	
	@Test
	public void testIsDeviceInTag() throws APIConnectionException, APIRequestException {
		BooleanResult result = jpushClient.isDeviceInTag(TAG1, REGISTRATION_ID1);
		assertTrue(result.isResultOK());
	}
	
	@Test
	public void testAddRemoveDevicesFromTag() throws APIConnectionException, APIRequestException {
		Set<String> toAddUsers  = new HashSet<String>();
		toAddUsers.add(REGISTRATION_ID1);
		Set<String> toRemoveUsers  = new HashSet<String>();
		toRemoveUsers.add(REGISTRATION_ID2);
		DefaultResult result = jpushClient.addRemoveDevicesFromTag(TAG1, toAddUsers, toRemoveUsers);
		assertTrue(result.isResultOK());
	}
	
	@Test
	public void testDeleteTag() throws APIConnectionException, APIRequestException {
		DefaultResult result = jpushClient.deleteTag(TAG1, "ios");
		assertTrue(result.isResultOK());
	}

	@Test
	public void testDeleteTag_2() throws APIConnectionException, APIRequestException {
		DefaultResult result = jpushClient.deleteTag(TAG1, null);
		assertTrue(result.isResultOK());
	}
	
	@Test
	public void testGetAliasDeviceList() throws APIConnectionException, APIRequestException {
		AliasDeviceListResult result = jpushClient.getAliasDeviceList(ALIAS1, "android");
		assertTrue(result.isResultOK());
	}
	
	@Test
	public void testGetAliasDeviceList_2() throws APIConnectionException, APIRequestException {
		AliasDeviceListResult result = jpushClient.getAliasDeviceList(ALIAS1, null);
		assertTrue(result.isResultOK());
	}
	
	@Test
	public void testDeleteAlias() throws APIConnectionException, APIRequestException {
		DefaultResult result = jpushClient.deleteAlias(ALIAS2, "android");
		assertTrue(result.isResultOK());
	}
	
	@Test
	public void testDeleteAlias_2() throws APIConnectionException, APIRequestException {
		DefaultResult result = jpushClient.deleteAlias(ALIAS2, null);
		assertTrue(result.isResultOK());
	}
}
