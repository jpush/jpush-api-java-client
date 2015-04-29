package cn.jpush.api.jmessage.base;


import com.google.gson.JsonParser;

public class BaseTest {

    protected static final String APP_KEY ="242780bfdd7315dc1989fe2b";
    protected static final String MASTER_SECRET = "2f5ced2bef64167950e63d13";

    protected static final String MORE_THAN_64 = "a0123456789012345678901234567890123456789012345678901234567890123";

    protected static final String MORE_THAN_128 =  "a012345678901234567890123456789012345678901234567890123456789" +
            "01234567890123456789012345678901234567890123456789012345678901234567";

    protected static final String MORE_THAN_250 =
            "a0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
            "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
            "01234567890123456789012345678901234567890123456789" ;

    protected static JsonParser parser = new JsonParser();

    protected static final String JUNIT_USER = "junit_no_delete";

    protected static final String JUNIT_USER1 = "junit_no_delete1";

    protected static final long JUNIT_GID = 10003718;

}
