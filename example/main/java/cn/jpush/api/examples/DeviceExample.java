package cn.jpush.api.examples;

import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.OnlineStatus;
import cn.jpush.api.device.TagAliasResult;

public class DeviceExample {
	protected static final Logger LOG = LoggerFactory.getLogger(DeviceExample.class);

	private static final String appKey = "7b4b94cca0d185d611e53cca";
	private static final String masterSecret = "860803cf613ed54aa3b941a8";
	private static final String TAG1 = "tag1";
	private static final String ALIAS1 = "alias1";
	private static final String ALIAS2 = "alias2";
	private static final String REGISTRATION_ID1 = "160a3797c856951a397";
	private static final String REGISTRATION_ID2 = "0a04ad7d8b4";

	private static JPushClient jpushClient = new JPushClient(masterSecret, appKey);

	public static void main(String[] args) {
//		testGetDeviceTagAlias();
//		testGetUserOnlineStatus();
		testUpdateDeviceTagAlias();
	}
	
	public static void testUpdateDeviceTagAlias() {
		HashSet<String> tagSet = new HashSet<String>();
		tagSet.add("hhh");
		try {
			DefaultResult result = jpushClient.updateDeviceTagAlias(REGISTRATION_ID1, null, tagSet, null);
			LOG.info("Got result " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
			
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
		}
	}
	
	public static void testGetDeviceTagAlias() {
		try {
			TagAliasResult result = jpushClient.getDeviceTagAlias(REGISTRATION_ID1);
			
			LOG.info(result.alias);
			LOG.info(result.tags.toString());
			
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
			
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
		}
	}

	public static void testGetUserOnlineStatus() {
		try {
			Map<String, OnlineStatus> result =  jpushClient.getUserOnlineStatus(REGISTRATION_ID1, REGISTRATION_ID2);

			LOG.info(result.get(REGISTRATION_ID1).toString());
			LOG.info(result.get(REGISTRATION_ID2).toString());
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}
	}

	public static void testBindMobile() {
		try {
			DefaultResult result =  jpushClient.bindMobile(REGISTRATION_ID1, "13000000000");
			LOG.info("Got result " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}
	}
	
}


