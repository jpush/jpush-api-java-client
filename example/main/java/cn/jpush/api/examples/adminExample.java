package cn.jpush.api.examples;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.admin.AdminClient;
import cn.jpush.api.file.model.FileType;
import cn.jpush.api.file.model.FileUploadResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class adminExample {

    protected static final Logger LOG = LoggerFactory.getLogger(DeviceExample.class);


    /**
     * Change the Dev key and Dev secret to your own account
     * base64_auth_string 生成规则是：base64(dev_key:dev_secret), dev_key 及 dev_secret 请登录官网在开发者帐号页面获取;
     */
    protected static final String DEV_KEY = "b924003f73a21f28b238fb12";
    protected static final String DEV_SECRET = "666a4a8284e62eef3b7aaf51";
    protected static final String APP_KEY = "e9fcfcf59eb3adb7e4dda56d";

    public static void main(String[] args) {
        testUploadCertificate();
    }

    public static void testUploadCertificate() {
        AdminClient adminClient = new AdminClient(DEV_KEY, DEV_SECRET);

        try {
            adminClient.uploadCertificate(APP_KEY, "test_push_cert.p12", "123456", null, null);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }
}
