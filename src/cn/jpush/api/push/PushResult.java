package cn.jpush.api.push;

import cn.jpush.api.common.BaseResult;

import com.google.gson.annotations.Expose;

public class PushResult extends BaseResult {
        
    public static class Push {
        @Expose public long msg_id;
        @Expose public int sendno;
    }
    
}

