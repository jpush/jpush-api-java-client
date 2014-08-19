package cn.jpush.api.tags;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.api.common.BaseResult;

import com.google.gson.annotations.Expose;

public class TagListResult extends BaseResult {
    
    @Expose public List<TagPlatform> tags = new ArrayList<TagPlatform>();
    
    public static class TagPlatform {
        @Expose public Android android;
        @Expose public Ios ios;
    }
    
    public static class Android {
        @Expose public String tag;
    }
    
    public static class Ios {
        @Expose public String tag;
    }
    
}

