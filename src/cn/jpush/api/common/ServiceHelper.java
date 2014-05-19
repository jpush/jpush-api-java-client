package cn.jpush.api.common;

import java.util.Random;
import java.util.regex.Pattern;

import cn.jpush.api.utils.Base64;
import cn.jpush.api.utils.StringUtils;

public class ServiceHelper {

    private final static Pattern PUSH_PATTERNS = Pattern.compile("[^a-zA-Z0-9]");
    private final static String BASIC_PREFIX = "Basic";
    
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final int MIN = 100000;
    private static final int MAX = Integer.MAX_VALUE;
    
    public static int generateSendno() {
        return RANDOM.nextInt((MAX - MIN) + 1) + MIN;
    }
    
    public static String getAuthorizationBase64(String appKey, String masterSecret) {
        String encodeKey = appKey + ":" + masterSecret;
        return BASIC_PREFIX + " " + String.valueOf(Base64.encode(encodeKey.getBytes())); 
    }
    
    public static void checkBasic(String appKey, String masterSecret) {
        if (StringUtils.isEmpty(appKey)
                || StringUtils.isEmpty(masterSecret)) {
            throw new IllegalArgumentException("appKey and masterSecret are both required.");
        }
        if (appKey.length() != 24 
                || masterSecret.length() != 24
                || PUSH_PATTERNS.matcher(appKey).find()
                || PUSH_PATTERNS.matcher(masterSecret).find()) {
            throw new IllegalArgumentException("appKey and masterSecret format is incorrect. "
                    + "They should be 24 size, and be composed with alphabet and numbers. "
                    + "Please confirm that they are coming from JPush Web Portal.");
        }
    }

}
