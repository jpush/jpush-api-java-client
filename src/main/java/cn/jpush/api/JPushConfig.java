package cn.jpush.api;

import cn.jiguang.common.ClientConfig;

public class JPushConfig {

    private static ClientConfig clientConfig = ClientConfig.getInstance();

    private static JPushConfig instance = new JPushConfig();

    public static final String ADMIN_HOST_NAME = "api.admin.host.name";
    public static final String V1_APP_PATH = "jpush.v1.app.path";

    private JPushConfig() {
        clientConfig.put(ADMIN_HOST_NAME, "https://admin.jpush.cn");
        clientConfig.put(V1_APP_PATH, "/v1/app");
    }

    public static JPushConfig getInstance() {
        return instance;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public JPushConfig setAdminHostName(String hostName) {
        clientConfig.put(ADMIN_HOST_NAME, hostName);
        return this;
    }

    public void put(String key, Object value) {
        clientConfig.put(key, value);
    }

    public Object get(String key) {
        return clientConfig.get(key);
    }
}
