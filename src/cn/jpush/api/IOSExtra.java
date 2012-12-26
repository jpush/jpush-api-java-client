package cn.jpush.api;

import java.util.HashMap;
import java.util.Map;

/*
 *  IOS 发送通知 扩展字段
 *  只针对 通知消息
 * 
 */
public class IOSExtra {
	
	public IOSExtra(int badge,String sound){
		this.badge = badge;
		this.sound = sound;
	}
	public IOSExtra(String sound){
		this.sound = sound;
	}
	public IOSExtra(int badge){
		this.badge = badge;
	}
	
	private int badge = 1;
	/*
	 * 当前软件里面的所拥有的铃声名称. 不设置，手机默认铃声
	 */
	private String sound = "happy"; 
	
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
	
	public Map<String,Object> getIOSExtraMap(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("badge", this.badge);
		params.put("sound", this.sound);
		return params;
	}
	

}
