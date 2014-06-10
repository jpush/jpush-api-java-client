package cn.jpush.api.tools;

import org.apache.tools.ant.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.app.AppClient;
import cn.jpush.api.app.AppCreateResult;
import cn.jpush.api.app.model.AppModel;

public class AppCreateAntTask extends Task {
    private static final Logger LOG = LoggerFactory.getLogger(AppCreateAntTask.class);
    
    private String app_name;
    private String android_package;
    private String group_name;
        
    public void setApp_name(String appName) {
        this.app_name = appName;
    }
    
    public void setAndroid_package(String androidPackage) {
        this.android_package = androidPackage;
    }
    
    public void setGroup_name(String groupName) {
        this.group_name = groupName;
    }
    
    
    public void execute() {
        String dev_key = getProject().getProperty("jpush_dev_key");
        String dev_secret = getProject().getProperty("jpush_dev_secret");
        
        AppClient appClient = new AppClient(dev_key, dev_secret);
        
        AppModel app = new AppModel(app_name, android_package, group_name);
        AppCreateResult result = appClient.create(app);
        if (!result.isResultOK()) {
            if (result.getErrorCode() > 0) {
                LOG.debug("Request Error");
            } else {
                LOG.debug("Retry Later.");
            }
            return;
        }
        
        getProject().setNewProperty("jpush_app_key", result.app_key);
        getProject().setNewProperty("jpush_android_package", result.android_package);
        getProject().setNewProperty("jpush_is_new_created", result.is_new_created + "");
    }
    
}


