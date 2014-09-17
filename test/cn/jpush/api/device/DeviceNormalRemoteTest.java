package cn.jpush.api.device;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import cn.jpush.api.BaseTest;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.BooleanResult;
import cn.jpush.api.common.resp.DefaultResult;
import cn.jpush.api.utils.JUnitOrderedRunner;
import cn.jpush.api.utils.TestOrder;

@RunWith(JUnitOrderedRunner.class)
public class DeviceNormalRemoteTest extends BaseTest {
	
	// ------------------ device
	
	@Test
	@TestOrder(order = 100)
	public void testUpdateDeviceTagAlias_add_remove_tags() throws APIConnectionException, APIRequestException {
		Set<String> tagsToAdd = new HashSet<String>(); 
		tagsToAdd.add("tag_1");
		tagsToAdd.add("tag_2");
		Set<String> tagsToRemove = new HashSet<String>();
		tagsToRemove.add("tag_3");
		tagsToRemove.add("tag_4");
		DefaultResult result = jpushClient.updateDeviceTagAlias(REGISTRATION_ID1, ALIAS1, tagsToAdd, tagsToRemove);
		assertTrue(result.isResultOK());
	}
	
	@Test
	@TestOrder(order = 110)
	public void testGetDeviceTagAlias_1() throws Exception {
		TagAliasResult result = jpushClient.getDeviceTagAlias(REGISTRATION_ID1);
		
		assertTrue(result.isResultOK());
		assertEquals("alias not equals", ALIAS1, result.alias);
		
		assertTrue("tag contains", result.tags.contains("tag_1"));
		assertTrue("tag contains", result.tags.contains("tag_2"));
		assertFalse("tag not contains", result.tags.contains("tag_3"));
		assertFalse("tag not contains", result.tags.contains("tag_4"));
	}
	@Test
	@TestOrder(order = 111)
	public void testGetAliasDeviceList_1() throws APIConnectionException, APIRequestException {
		AliasDeviceListResult result = jpushClient.getAliasDeviceList(ALIAS1, null);
		assertTrue(result.registration_ids.contains(REGISTRATION_ID1));
	}
	
	@Test
	@TestOrder(order = 120)
	public void testUpdateDeviceTagAlias_clear() throws APIConnectionException, APIRequestException {
		DefaultResult result = jpushClient.updateDeviceTagAlias(REGISTRATION_ID1, true, true);
		assertTrue(result.isResultOK());
	}
	
	@Test
	@TestOrder(order = 130)
	public void testGetDeviceTagAlias_cleard() throws Exception {
		TagAliasResult result = jpushClient.getDeviceTagAlias(REGISTRATION_ID1);
		
		assertTrue(result.isResultOK());
		assertEquals("alias cleared", null, result.alias);
		assertEquals("tags cleared", 0, result.tags.size());
	}

	
	
	// ------------------ tags

	@Test
	@TestOrder(order = 203)
	public void testAddRemoveDevicesFromTag() throws APIConnectionException, APIRequestException {
		Set<String> toAddUsers  = new HashSet<String>();
		toAddUsers.add(REGISTRATION_ID1);
		Set<String> toRemoveUsers  = new HashSet<String>();
		toRemoveUsers.add(REGISTRATION_ID2);
		DefaultResult result = jpushClient.addRemoveDevicesFromTag("tag_3", toAddUsers, toRemoveUsers);
		assertTrue(result.isResultOK());
	}
	
	@Test
	@TestOrder(order = 210)
	public void testIsDeviceInTag() throws APIConnectionException, APIRequestException {
		BooleanResult result = jpushClient.isDeviceInTag("tag_3", REGISTRATION_ID1);
		assertTrue("", result.result);
		result = jpushClient.isDeviceInTag("tag_3", REGISTRATION_ID2);
		assertFalse("", result.result);
	}

	@Test
	@TestOrder(order = 211)
	public void testAddRemoveDevicesFromTagResult() throws APIConnectionException, APIRequestException {
		TagListResult result = jpushClient.getTagList();
		assertTrue("", result.tags.contains("tag_3"));
	}
	
	@Test
	@TestOrder(order = 220)
	public void testGetTagList_1() throws Exception {
		TagListResult result = jpushClient.getTagList();
		assertTrue("", result.tags.size() > 0);
	}
	
	@Test
	@TestOrder(order = 250)
	public void testDeleteTag() throws APIConnectionException, APIRequestException {
		DefaultResult result = jpushClient.deleteTag("tag_3", null);
		assertTrue(result.isResultOK());
	}

	@Test
	@TestOrder(order = 251)
	public void testDeleteResult() throws APIConnectionException, APIRequestException {
		TagListResult result = jpushClient.getTagList();
		assertFalse("", result.tags.contains("tag3"));
	}
	
	@Test
	@TestOrder(order = 260)
	public void testDeleteTag_2() throws APIConnectionException, APIRequestException {
		DefaultResult result = jpushClient.deleteTag("tag_3", null);
		assertTrue(result.isResultOK());
	}
	
	// --------------------- alias
	
	@Test
	@TestOrder(order = 230)
	public void testGetAliasDevices_1() throws Exception {
		AliasDeviceListResult result = jpushClient.getAliasDeviceList(ALIAS1, null);
		assertTrue(result.isResultOK());
	}

	@Test
	@TestOrder(order = 300)
	public void testGetAliasDeviceList() throws APIConnectionException, APIRequestException {
		AliasDeviceListResult result = jpushClient.getAliasDeviceList(ALIAS1, "android");
		assertTrue(result.isResultOK());
	}
	
	@Test
	@TestOrder(order = 310)
	public void testGetAliasDeviceList_2() throws APIConnectionException, APIRequestException {
		AliasDeviceListResult result = jpushClient.getAliasDeviceList(ALIAS1, null);
		assertTrue(result.registration_ids.size() == 0);
	}
	
	@Test
	@TestOrder(order = 320)
	public void testDeleteAlias() throws APIConnectionException, APIRequestException {
		DefaultResult result = jpushClient.deleteAlias(ALIAS2, "android");
		assertTrue(result.isResultOK());
	}
	
	
	@Test
	@TestOrder(order = 330)
	public void testDeleteAlias_2() throws APIConnectionException, APIRequestException {
		DefaultResult result = jpushClient.deleteAlias(ALIAS2, null);
		assertTrue(result.isResultOK());
	}
}
