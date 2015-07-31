package cn.jpush.api.schedule;

import cn.jpush.api.common.resp.BaseResult;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class ScheduleResult extends BaseResult{

    @Expose String schedule_id;
    @Expose String name;
    @Expose Boolean enabled;
    @Expose JsonObject trigger;
    @Expose JsonObject push;
}
