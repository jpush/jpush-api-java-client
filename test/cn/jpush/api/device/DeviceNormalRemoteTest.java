package cn.jpush.api.device;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.jpush.api.BaseTest;

public class DeviceNormalRemoteTest extends BaseTest {
	
	
	@Test
	public void testGetDeviceTagAlias_1() throws Exception {
		TagAliasResult result = jpushClient.getDeviceTagAlias(REGISTRATION_ID1);
		assertTrue(result.isResultOK());
	}

	
}
