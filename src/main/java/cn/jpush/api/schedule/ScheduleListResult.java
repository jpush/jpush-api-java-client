package cn.jpush.api.schedule;


import cn.jpush.api.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.List;

public class ScheduleListResult extends BaseResult{

    @Expose int total_count;
    @Expose int total_pages;
    @Expose int page;
    @Expose List<ScheduleResult> schedules;

    public int getTotal_count() {
        return total_count;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getPage() {
        return page;
    }

    public List<ScheduleResult> getSchedules() {
        return schedules;
    }
}
