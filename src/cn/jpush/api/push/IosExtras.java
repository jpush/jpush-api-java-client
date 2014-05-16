package cn.jpush.api.push;

import com.google.gson.annotations.SerializedName;

/*
 * Should be set into Notification extras with key "ios"
 */
public class IosExtras {
	
    private int badge = 0;
    
    private String sound = "";
    
    @SerializedName("content-available")
    private int contentAvailable = 0;
    
	public IosExtras(int badge, String sound, boolean contentAvailableEnabled) {
		this.badge = badge;
		this.sound = sound;
		this.contentAvailable = contentAvailableEnabled ? 1 : 0;
	}
	
    public IosExtras(int badge, String sound) {
        this.badge = badge;
        this.sound = sound;
    }
    
	public IosExtras(String sound) {
		this.sound = sound;
	}
	
	public IosExtras(int badge) {
		this.badge = badge;
	}
	
	public int getBadge() {
		return badge;
	}
	
	public void setBadge(int badge) {
		this.badge = badge;
	}
	
	public String getSound() {
		return sound;
	}
	
	public void setSound(String sound) {
		this.sound = sound;
	}

    public int getContentAvailable() {
        return contentAvailable;
    }
    
    public void enableContentAvailable(boolean contentAvailableEnabled) {
        this.contentAvailable = contentAvailableEnabled ? 1 : 0;
    } 
	
	
}

