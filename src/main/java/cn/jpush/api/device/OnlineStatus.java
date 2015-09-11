package cn.jpush.api.device;

public class OnlineStatus {

    Boolean online;
    String last_online_time;

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getLast_online_time() {
        return last_online_time;
    }

    public void setLast_online_time(String last_online_time) {
        this.last_online_time = last_online_time;
    }

    @Override
    public String toString() {
        if(null == last_online_time) {
            return "status: " + online;
        }
        return "status: " + online + " , last_online_time: " + last_online_time;
    }
}
