package cn.jpush.api.tags;

import java.util.List;

import cn.jpush.api.common.BaseResult;

import com.google.gson.annotations.Expose;

public class TagAliasResult extends BaseResult {

    @Expose public List<Tag> tag;
    @Expose public String alias;
    
    public static class Tag {
        @Expose public String tag;
    }
    
}

