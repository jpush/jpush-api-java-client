package cn.jpush.api.common;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import cn.jpush.api.utils.Base64;
import cn.jpush.api.utils.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceHelper.class);
    private final static Pattern PUSH_PATTERNS = Pattern.compile("[^a-zA-Z0-9]");
    private final static String BASIC_PREFIX = "Basic";
    
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final int MIN = 100000;
    private static final int MAX = Integer.MAX_VALUE;
    
    private static final int MAX_BADGE_NUMBER = 99999;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9][a-zA-Z_0-9.、。@,-]*");
    private static final Pattern DATE_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    static {
        DATE_FORMAT.setLenient(false);
    }

    public static boolean isValidIntBadge(int intBadge) {
        if (intBadge >= 0 && intBadge <= MAX_BADGE_NUMBER) {
            return true;
        }
        return false;
    }
    
    public static int generateSendno() {
        return RANDOM.nextInt((MAX - MIN) + 1) + MIN;
    }
    
    public static String getBasicAuthorization(String username, String password) {
        String encodeKey = username + ":" + password;
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
    
    public static JsonArray fromSet(Set<String> sets) {
        JsonArray array = new JsonArray();
        if (null != sets && sets.size() > 0) {
            for (String item : sets) {
                array.add(new JsonPrimitive(item));
            }
        }
        return array;
    }

    public static boolean checkUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidBirthday( String birthday) {
        try {
            if( ! DATE_PATTERN.matcher(birthday).matches() ) {
                return false;
            }
            DATE_FORMAT.parse(birthday);
        } catch (Exception e) {
            LOG.error("incorrect date format. " + birthday, e);
            return false;
        }
        return true;
    }

}
