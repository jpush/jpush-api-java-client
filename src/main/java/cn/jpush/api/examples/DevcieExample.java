package cn.jpush.api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;

public class DevcieExample {
	protected static final Logger LOG = LoggerFactory.getLogger(DevcieExample.class);

	private static final String appKey = "dd1066407b044738b6479275";
	private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
	private static final String TAG1 = "tag1";
	private static final String ALIAS1 = "alias1";
	private static final String ALIAS2 = "alias2";
	private static final String REGISTRATION_ID1 = "0900e8d85ef";
	private static final String REGISTRATION_ID2 = "0a04ad7d8b4";

	private static JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
	
	public static void main(String[] args) {
		
	}
}
