package cn.jpush.api.report;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.api.common.ResponseResult;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ReceivedsResult {
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public ResponseResult responseResult;
    @Expose public List<Received> receivedList = new ArrayList<Received>();
	
	public static class Received {
	    @Expose public long msg_id;
	    @Expose public int android_received;
	    @Expose public int ios_apns_sent;
	}
	
	public List<Received> getReceivedList() {
	    return this.receivedList;
	}
	
	@Override
	public String toString() {
		return _gson.toJson(this);
	}
}
