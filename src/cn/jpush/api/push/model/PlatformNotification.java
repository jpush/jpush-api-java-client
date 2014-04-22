package cn.jpush.api.push.model;

public abstract class PlatformNotification implements PushModel {
    public static final String ALERT = "alert";
    
    protected String alert;
    
    public PlatformNotification(String alert) {
        this.alert = alert;
    }
    
    public String getAlert() {
        return this.alert;
    }
    
    public void setAlert(String alert) {
        this.alert = alert;
    }

    public abstract String getPlatform();
    
}
