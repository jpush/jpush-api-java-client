package cn.jpush.api.push;

import cn.jpush.api.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

public class PushResult extends BaseResult {

    private static final long serialVersionUID = 93783137655776743L;

    @Expose public long msg_id;
    @Expose public int sendno;
    
}

