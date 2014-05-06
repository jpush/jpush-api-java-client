package cn.jpush.api.report;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.api.common.BaseResult;

import com.google.gson.annotations.Expose;

public class ReceivedsResult extends BaseResult {
    @Expose public List<Received> receivedList = new ArrayList<Received>();
	
	public static class Received {
	    @Expose public long msg_id;
	    @Expose public int android_received;
	    @Expose public int ios_apns_sent;
	}
	
	public List<Received> getReceivedList() {
	    return this.receivedList;
	}
	
	
}
