package cn.jpush.api;

public class BaseURL {
	
	private static final String HOST_NAME_SSL = "https://api.jpush.cn:443";
	private static final String HOST_NAME = "http://117.135.160.250:8800";
	protected static final String ALL_PATH = "/test/v2/views.py";       //全功能，发送地址
	protected static final String SIMPLE_CUSTOM_PATH = "/test/v2/custom_message.py"; //简易接口，自定义消息
	protected static final String SIMPLE_NOTIFICATION_PATH = "/test/v2/notification.py"; //简易接口，自定义通知
	
	
	private static String getHostname(boolean enableSSL) {
		return enableSSL? HOST_NAME_SSL :HOST_NAME;
	}

	public  static String getUrlForPath(final String path,boolean enableSSL) {
		return getHostname(enableSSL) + path;
	}
	

}
