package cn.jpush.api.device;

import java.util.List;

import cn.jpush.api.common.resp.BaseResult;

import com.google.gson.annotations.Expose;

public class TagAliasResult extends BaseResult {

    @Expose public List<String> tags;
    @Expose public String alias;
        
}

