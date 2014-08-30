package cn.jpush.api.device;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.api.common.resp.BaseResult;

import com.google.gson.annotations.Expose;

public class TagListResult extends BaseResult {
    
    @Expose public List<Tag> tags = new ArrayList<Tag>();
    
    public static class Tag {
        @Expose public String tag;
    }
        
}

