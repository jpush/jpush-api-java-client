package cn.jpush.api.app.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class AppModel {
    private static final String APP_NAME = "app_name";
    private static final String ANDROID_PACKAGE = "android_package";
    private static final String GROUP_NAME = "group_name";
    
    private final String appName;
    private final String androidPackage;
    private final String groupName;
    
    private static Gson _gson = new Gson();
    
    public AppModel(String appName, String androidPackage) {
        this(appName, androidPackage, null);
    }
    
    public AppModel(String appName, String androidPackage, String groupName) {
        this.appName = appName;
        this.androidPackage = androidPackage;
        this.groupName = groupName;
    }
    
    @Override
    public String toString() {
        JsonObject json = new JsonObject();
        if (null != appName) {
            json.add(APP_NAME, new JsonPrimitive(appName));
        }
        if (null != androidPackage) {
            json.add(ANDROID_PACKAGE, new JsonPrimitive(androidPackage));
        }
        if (null != groupName) {
            json.add(GROUP_NAME, new JsonPrimitive(groupName));
        }
        return _gson.toJson(json);
    }
}
