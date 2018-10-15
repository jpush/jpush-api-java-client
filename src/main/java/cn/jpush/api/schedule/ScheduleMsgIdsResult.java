package cn.jpush.api.schedule;



import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;

public class ScheduleMsgIdsResult extends BaseResult{

    private static final long serialVersionUID = 995450157929893257L;
    
    @Expose int count;
    
    @Expose List<JsonObject> msgids;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<JsonObject> getMsgids() {
		return msgids;
	}

	public void setMsgids(List<JsonObject> msgids) {
		this.msgids = msgids;
	}


}
