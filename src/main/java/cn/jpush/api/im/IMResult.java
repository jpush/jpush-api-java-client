package cn.jpush.api.im;

import cn.jpush.api.common.resp.BaseResult;

import com.google.gson.annotations.Expose;

public class IMResult extends BaseResult {
    
    @Expose public long msg_id;
    @Expose public int sendno;
    
}

