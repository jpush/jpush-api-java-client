package cn.jpush.api.admin;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class AppResult extends BaseResult {

    @Expose private String success;

    public String getSuccess() {
        return success;
    }
}
