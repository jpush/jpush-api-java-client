package cn.jpush.api.push.model.live_activity;

public enum LiveActivityEvent {

    UPDATE("update", "更新"),
    END("end", "结束，dismissal-date为结束展示时间");

    private String value;
    private String describe;

    LiveActivityEvent(String value, String describe) {
        this.value = value;
        this.describe = describe;
    }

    public String getValue() {
        return this.value;
    }

    public String getDescribe() {
        return this.describe;
    }

}
