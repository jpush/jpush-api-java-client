package cn.jpush.http;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.api.DeviceEnum;

public class BaseClient {
	public String masterSecret = "";
	public String appKey = "";
	public String sendDescription = "";                  //发送的描述
	public long timeToLive = -1;                        //保存离线的时长。秒为单位。默认为保存1天的离线消息
	public boolean enableSSL = false;
	public Set<DeviceEnum> devices = new HashSet<DeviceEnum>();   //默认发送android和ios

	public String getMasterSecret() {
		return masterSecret;
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public void setSendDescription(String description) {
		this.sendDescription = description;
	}

	public String getSendDescription() {
		return this.sendDescription;
	}

	public Set<DeviceEnum> getDevices() {
		if (null == this.devices) {
			this.devices = new HashSet<DeviceEnum>();
		}
		if (this.devices.size() == 0) {
			this.devices.add(DeviceEnum.Android);
			this.devices.add(DeviceEnum.IOS);
		}
		return this.devices;
	}

	/*
	 * @description 是否使用ssl安全连接
	 */
	public void setEnableSSL(boolean enableSSL) {
		this.enableSSL = enableSSL;
	}

}
