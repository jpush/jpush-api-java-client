package cn.jpush.api.push;

/*
 * Should be set into Notification extras with key "ios"
 */
public class IosExtras {
	
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
	
	/*
	 * Badge Notification,默认是(0)
	 */
	private int badge = 0;
	
	/*
	 *  当前软件里面的所拥有的铃声名称（如：message.wav)。
	 *  不设置，手机默认通知铃声
	 */
	private String sound = ""; 
	
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
	
}
