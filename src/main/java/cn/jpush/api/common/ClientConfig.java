package cn.jpush.api.common;

import java.util.HashMap;
import java.util.Map;

public class ClientConfig extends HashMap<String, Object> {

    private static ClientConfig instance = new ClientConfig();

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

    private ClientConfig() {
        super(12);
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
    }

    public static ClientConfig getInstance() {
        return instance;
    }

    public static void setDeviceHostName(Map conf, String hostName) {
        conf.put(DEVICE_HOST_NAME, hostName);
    }

    /**
     * Setup custom device api host name, if using the JPush privacy cloud.
     * @param hostName the custom api host name, default is JPush domain name
     */
    public void setDeviceHostName(String hostName) {
        setDeviceHostName(this, hostName);
    }

    public static void setPushHostName(Map conf, String hostName) {
        conf.put(PUSH_HOST_NAME, hostName);
    }

    /**
     * Setup custom push api host name, if using the JPush privacy cloud.
     * @param hostName the custom api host name, default is JPush domain name
     */
    public void setPushHostName(String hostName) {
        setPushHostName(this, hostName);
    }

    public static void setReportHostName(Map conf, String hostName) {
        conf.put(REPORT_HOST_NAME, hostName);
    }

    /**
     * Setup custom report api host name, if using the JPush privacy cloud.
     * @param hostName the custom api host name, default is JPush domain name
     */
    public void setReportHostName(String hostName) {
        setReportHostName(this, hostName);
    }

    public static void setScheduleHostName(Map conf, String hostName) {
        conf.put(SCHEDULE_HOST_NAME, hostName);
    }

    public void setScheduleHostName(String hostName) {
        setScheduleHostName(this, hostName);
    }

}
