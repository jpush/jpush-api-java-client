package cn.jpush.api.push;

import cn.jpush.api.common.BaseResult;

import com.google.gson.annotations.Expose;

public class MessageResult extends BaseResult {
    @Expose public Long msg_id;
    @Expose public int sendno;
    @Expose public int errcode = -1;
    @Expose public String errmsg;

    public MessageResult() {
    }
    
    public int getErrcode() {
        return this.errcode;
    }
    
	@Override
	public String toString() {
		return _gson.toJson(this);
	}
	
}

