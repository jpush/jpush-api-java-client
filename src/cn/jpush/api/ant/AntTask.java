package cn.jpush.api.ant;

import org.apache.tools.ant.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.jpush.api.common.NativeHttpClient;
import cn.jpush.api.common.ResponseWrapper;
import cn.jpush.api.examples.PushExample;

import com.google.gson.Gson;

/**
 * the class convenient client for use in the ant script.
 * 
 * @author caiyihuang
 * 
 */
public class AntTask extends Task {
	private static final String ANT_HOST_NAME = "https://admin.jpush.cn";
	private static final String ANT_RECEIVE_PATH = "/v1/app";
	private AntTaskEntity entity;
	private NativeHttpClient _httpClient = new NativeHttpClient();;
	protected static final Logger LOG = LoggerFactory
			.getLogger(PushExample.class);

	public void execute() {

		String dev_key = getProject().getProperty("dev_key");
		String dev_secret = getProject().getProperty("dev_secret");
		String app_name = getProject().getProperty("app_name");
		String android_package = getProject().getProperty("android_package");
		String group_name = getProject().getProperty("group_name");
		entity = new AntTaskEntity(dev_key, dev_secret, app_name,
				android_package, group_name);
		getAntRespone();
	}

	public void getAntRespone() {
		String url = ANT_HOST_NAME + ANT_RECEIVE_PATH;
		Gson gson = new Gson();
		String json = gson.toJson(entity);
		ResponseWrapper respone = _httpClient.sendPost(url, json, "");
		String content = respone.responseContent;
		/*
		 * System.out.println(content); System.out.println(json);
		 */
		AntResponseEntity responseEntity = gson.fromJson(content,
				AntResponseEntity.class);
		Error error = responseEntity.getError();
		Success success = responseEntity.getSuccess();
		if (error != null) {
			LOG.error("create failed  " + error.getCode() + "   "
					+ error.getMessage());
		}
		if (success != null) {
			LOG.info("create success   " + "your appkey is"
					+ success.getApp_key() + "your android package is"
					+ success.getAndroid_package());
		}

	}

}

class AntResponseEntity {
	private Error error;
	private Success success;

	public Success getSuccess() {
		return success;
	}

	public void setSuccess(Success success) {
		this.success = success;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

}

class Success {
	private String app_key;
	private String android_package;
	private String is_new_created;

	public String getApp_key() {
		return app_key;
	}

	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}

	public String getAndroid_package() {
		return android_package;
	}

	public void setAndroid_package(String android_package) {
		this.android_package = android_package;
	}

	public String getIs_new_created() {
		return is_new_created;
	}

	public void setIs_new_created(String is_new_created) {
		this.is_new_created = is_new_created;
	}

}

class Error {
	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

class AntTaskEntity {
	private String dev_key;
	private String dev_secret;
	private String app_name;
	private String android_package;
	private String group_name;

	public AntTaskEntity(String dev_key, String dev_secret, String app_name,
			String android_package, String group_name) {
		this.dev_key = dev_key;
		this.dev_secret = dev_secret;
		this.app_name = app_name;
		this.android_package = android_package;
		this.group_name = group_name;
	}

	public String getDev_key() {
		return dev_key;
	}

	public String getDev_secret() {
		return dev_secret;
	}

	public String getApp_name() {
		return app_name;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setDev_key(String dev_key) {
		this.dev_key = dev_key;
	}

	public void setDev_secret(String dev_secret) {
		this.dev_secret = dev_secret;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getAndroid_package() {
		return android_package;
	}

	public void setAndroid_package(String android_package) {
		this.android_package = android_package;
	}
}
