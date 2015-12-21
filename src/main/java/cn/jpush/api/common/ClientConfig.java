package cn.jpush.api.common;

import java.util.HashMap;

public class ClientConfig extends HashMap<String, Object> {

    public static final String DEVICE_HOST_NAME = "device.host.name";
    public static final Object DEVICE_HOST_NAME_SCHEMA = String.class;

    public static final String DEVICES_PATH = "devices.path";
    public static final Object DEVICES_PATH_SCHEMA = String.class;

    public static final String TAGS_PATH = "tags.path";
    public static final Object TAGS_PATH_SCHEMA = String.class;

    public static final String ALIASES_PATH = "aliases.path";
    public static final Object ALIASES_PATH_SCHEMA = String.class;

    public static final String PUSH_HOST_NAME = "push.host.name";
    public static final Object PUSH_HOST_NAME_SCHEMA = String.class;

    public static final String PUSH_PATH = "push.path";
    public static final Object PUSH_PATH_SCHEMA = String.class;

    public static final String PUSH_VALIDATE_PATH = "push.validate.path";
    public static final Object PUSH_VALIDATE_PATH_SCHMEA = String.class;

    public static final String REPORT_HOST_NAME = "report.host.name";
    public static final Object REPORT_HOST_NAME_SCHEMA = String.class;

    public static final String REPORT_RECEIVE_PATH = "report.receive.path";
    public static final Object REPORT_RECEIVE_PATH_SCHEMA = String.class;

    public static final String REPORT_USER_PATH = "report.user.path";
    public static final Object REPORT_USER_PATH_SCHEMA = String.class;

    public static final String REPORT_MESSAGE_PATH = "report.message.path";
    public static final Object REPORT_MESSAGE_PATH_SCHEMA = String.class;

    public static final String SCHEDULE_HOST_NAME = "schedule.host.name";
    public static final Object SCHEDULE_HOST_NAME_SCHEMA = String.class;

    public static final String SCHEDULE_PATH = "schedule.path";
    public static final Object SCHEDULE_PATH_SCHEMA = String.class;

    public static final String SSL_VERSION = "ssl.version";
    public static final Object SSL_VERSION_SCHEMA = String.class;
    public static final String DEFAULT_SSL_VERSION = "TLS";

    public static final String MAX_RETRY_TIMES = "max.retry.times";
    public static final Object MAX_RETRY_TIMES_SCHEMA = Integer.class;
    public static final int DEFULT_MAX_RETRY_TIMES = 3;

    public static final String READ_TIMEOUT = "read.timeout";
    public static final Object READ_TIMEOUT_SCHEMA = Integer.class;
    public static final int DEFAULT_READ_TIMEOUT = 30 * 1000;

    public static final String CONNECTION_TIMEOUT = "connection.timeout";
    public static final Object CONNECTION_TIMEOUT_SCHEMA = Integer.class;
    public static final int DEFAULT_CONNECTION_TIMEOUT = 5 * 1000;

    /**
     * Global APNs environment setting.
     * Setting to -1, if you want to use PushPayload Options{@link cn.jpush.api.push.model.Options#apnsProduction}.
     * Default value is -1.
     * Setting to 0, if you want to use global setting as development environment.
     * Setting to 1, if you want to use global setting as production environment.
     *
     */
    public static final String APNS_PRODUCTION = "apns.production";
    public static final Object APNS_PRODUCTION_SCHEMA = Integer.class;
    public static final int DEFAULT_APNS_PRODUCTION = -1;

    /**
     * Global time_to_live setting. Time unit is second.
     * Setting to -1, if you want to use PushPayload Options{@link cn.jpush.api.push.model.Options#timeToLive}.
     * Default value is -1.
     * It will override PushPayload Options, while it is a positive integer value.
     */
    public static final String TIME_TO_LIVE = "time.to.live";
    public static final Object TIME_TO_LIVE_SCHEMA = Long.class;
    public static final long DEFAULT_TIME_TO_LIVE = -1;

    private static ClientConfig instance = new ClientConfig();

    private ClientConfig() {
        super(32);
        this.put(DEVICE_HOST_NAME, "https://device.jpush.cn");
        this.put(DEVICES_PATH, "/v3/devices");
        this.put(TAGS_PATH, "/v3/tags");
        this.put(ALIASES_PATH, "/v3/aliases");

        this.put(PUSH_HOST_NAME, "https://api.jpush.cn");
        this.put(PUSH_PATH, "/v3/push");
        this.put(PUSH_VALIDATE_PATH, "/v3/push/validate");

        this.put(REPORT_HOST_NAME, "https://report.jpush.cn");
        this.put(REPORT_RECEIVE_PATH, "/v3/received");
        this.put(REPORT_USER_PATH, "/v3/users");
        this.put(REPORT_MESSAGE_PATH, "/v3/messages");

        this.put(SCHEDULE_HOST_NAME, "https://api.jpush.cn");
        this.put(SCHEDULE_PATH, "/v3/schedules");

        this.put(SSL_VERSION, DEFAULT_SSL_VERSION);
        this.put(MAX_RETRY_TIMES, DEFULT_MAX_RETRY_TIMES);
        this.put(READ_TIMEOUT, DEFAULT_READ_TIMEOUT);
        this.put(CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);

        this.put(APNS_PRODUCTION, DEFAULT_APNS_PRODUCTION);
        this.put(TIME_TO_LIVE, DEFAULT_TIME_TO_LIVE);

    }

    public static ClientConfig getInstance() {
        return instance;
    }

    /**
     * Setup custom device api host name, if using the JPush privacy cloud.
     * @param hostName the custom api host name, default is JPush domain name
     */
    public void setDeviceHostName(String hostName) {
        this.put(DEVICE_HOST_NAME, hostName);
    }

    /**
     * Setup custom push api host name, if using the JPush privacy cloud.
     * @param hostName the custom api host name, default is JPush domain name
     */
    public void setPushHostName(String hostName) {
        this.put(PUSH_HOST_NAME, hostName);
    }

    /**
     * Setup custom report api host name, if using the JPush privacy cloud.
     * @param hostName the custom api host name, default is JPush domain name
     */
    public void setReportHostName(String hostName) {
        this.put(REPORT_HOST_NAME, hostName);
    }

    public void setScheduleHostName(String hostName) {
        this.put(SCHEDULE_HOST_NAME, hostName);
    }

    public void setSSLVersion(String sslVer) {
        this.put(SSL_VERSION, sslVer);
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.put(MAX_RETRY_TIMES, maxRetryTimes);
    }

    public void setReadTimeout(int readTimeout) {
        this.put(READ_TIMEOUT, readTimeout);
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.put(CONNECTION_TIMEOUT, connectionTimeout);
    }

    public String getSSLVersion() {
        return (String) this.get(SSL_VERSION);
    }

    public Integer getMaxRetryTimes() {
        return (Integer) this.get(MAX_RETRY_TIMES);
    }

    public Integer getReadTimeout() {
        return (Integer) this.get(READ_TIMEOUT);
    }

    public Integer getConnectionTimeout() {
        return (Integer) this.get(CONNECTION_TIMEOUT);
    }

    public void setApnsProduction(boolean production) {
        if(production) {
            this.put(APNS_PRODUCTION, 1);
        } else {
            this.put(APNS_PRODUCTION, 0);
        }
    }

    public void setTimeToLive(long timeToLive) {
        this.put(TIME_TO_LIVE, timeToLive);
    }

    public void setGlobalPushSetting(boolean apnsProduction, long timeToLive) {
        setApnsProduction(apnsProduction);
        setTimeToLive(timeToLive);
    }
}
