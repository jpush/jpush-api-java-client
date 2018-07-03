package cn.jpush.api.admin;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class CreateAppResult extends BaseResult {
    @Expose private String app_key;
    @Expose private boolean is_new_created;
    @Expose private String android_package;

    public String getApp_key() {
        return app_key;
    }

    public boolean is_new_created() {
        return is_new_created;
    }

    public String getAndroid_package() {
        return android_package;
    }
}
