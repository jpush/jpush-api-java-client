package cn.jpush.api;

public class BaseURL {
	
	public static String HOST_NAME_SSL = "https://api.jpush.cn:443";
	public static String HOST_NAME = "http://api.jpush.cn:8800";
	
	protected static final String ALL_PATH = "/v2/push";       //全功能接口
	protected static final String SIMPLE_CUSTOM_PATH = "/v2/custom_message"; //简易接口，自定义消息
	protected static final String SIMPLE_NOTIFICATION_PATH = "/v2/notification"; //简易接口，自定义通知
	
	private static String getHostname(boolean enableSSL) {
		return enableSSL? HOST_NAME_SSL :HOST_NAME;
	}

	public  static String getUrlForPath(final String path,boolean enableSSL) {
		return getHostname(enableSSL) + path;
	}
	
}
