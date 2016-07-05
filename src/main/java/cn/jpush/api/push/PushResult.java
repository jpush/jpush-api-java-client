package cn.jpush.api.push;

import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;

public class PushResult extends BaseResult {

    private static final long serialVersionUID = 93783137655776743L;

    @Expose public long msg_id;
    @Expose public int sendno;
    
}

