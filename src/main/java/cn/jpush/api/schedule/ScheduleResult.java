package cn.jpush.api.schedule;

import cn.jpush.api.common.resp.BaseResult;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class ScheduleResult extends BaseResult{

    private static final long serialVersionUID = 995450157929190757L;
    @Expose String schedule_id;
    @Expose String name;
    @Expose Boolean enabled;
    @Expose JsonObject trigger;
    @Expose JsonObject push;

    public String getSchedule_id() {
        return schedule_id;
    }

    public String getName() {
        return name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public JsonObject getTrigger() {
        return trigger;
    }

    public JsonObject getPush() {
        return push;
    }
}
