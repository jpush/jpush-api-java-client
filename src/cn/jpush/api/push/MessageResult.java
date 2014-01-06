package cn.jpush.api.push;

import cn.jpush.api.common.BaseResult;

import com.google.gson.annotations.Expose;

public class MessageResult extends BaseResult {
    @Expose public Long msg_id;
    @Expose public int sendno;
    @Expose public int errcode = ERROR_CODE_NONE;
    @Expose public String errmsg;

    public MessageResult() {
    }
    
    public long getMessageId() {
        return this.msg_id;
    }
    
    public int getSendNo() {
        return this.sendno;
    }
    
    public int getErrorCode() {
        return this.errcode;
    }
    
    public String getErrorMessage() {
        return this.errmsg;
    }
    
    public boolean isResultOK() {
        if (responseResult.responseCode == RESPONSE_OK && this.errcode == ERROR_CODE_OK) return true;
        return false;
    }
    
	@Override
	public String toString() {
		return _gson.toJson(this);
	}
	
}

