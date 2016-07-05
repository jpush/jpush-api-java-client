package cn.jpush.api.device;

import java.util.List;

import com.google.gson.annotations.Expose;

import cn.jiguang.common.resp.BaseResult;

public class TagAliasResult extends BaseResult {

    private static final long serialVersionUID = -4765083329495728276L;
    @Expose public List<String> tags;
    @Expose public String alias;
        
}

