package cn.jpush.api.device;

import cn.jpush.api.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.List;

public class TagAliasResult extends BaseResult {

    private static final long serialVersionUID = -4765083329495728276L;
    @Expose public List<String> tags;
    @Expose public String alias;
        
}

