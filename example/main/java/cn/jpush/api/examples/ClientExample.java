package cn.jpush.api.examples;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientExample {
	protected static final Logger LOG = LoggerFactory.getLogger(ClientExample.class);

	private static final String appKey = "dd1066407b044738b6479275";
	private static final String masterSecret = "6b135be0037a5c1e693c3dfa";
	private static final String TAG1 = "tag1";
	private static final String ALIAS1 = "alias1";
	private static final String ALIAS2 = "alias2";
	private static final String REGISTRATION_ID1 = "0900e8d85ef";
	private static final String REGISTRATION_ID2 = "0a04ad7d8b4";


	public static void main(String[] args) {
//		testDefaultClient();
//		testCustomClient();
		testCustomPushClient();
	}

	public static void testDefaultClient() {
		JPushClient client = new JPushClient(masterSecret, appKey);

	//	JPushClient client1 = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
	}

	public static void testCustomClient() {
		ClientConfig config = ClientConfig.getInstance();
		config.setMaxRetryTimes(5);
		config.setConnectionTimeout(10 * 1000);	// 10 seconds
		config.setSSLVersion("TLSv1.1");		// JPush server supports SSLv3, TLSv1, TLSv1.1, TLSv1.2

		ClientConfig.setReadTimeout(ClientConfig.getInstance(), 30 * 1000);	// 30 seconds

		JPushClient jPushClient = new JPushClient(masterSecret, appKey, null, config);
	}

	public static void testCustomPushClient() {
		ClientConfig config = ClientConfig.getInstance();

		config.setApnsProduction(false); 	// development env
		config.setTimeToLive(60 * 60 * 24); // one day

	//	config.setGlobalPushSetting(false, 60 * 60 * 24); // development env, one day

		JPushClient jPushClient = new JPushClient(masterSecret, appKey, null, config); 	// JPush client

	//	PushClient pushClient = new PushClient(masterSecret, appKey, null, config); 	// push client only

	}
	
}


