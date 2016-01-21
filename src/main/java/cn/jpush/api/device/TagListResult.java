package cn.jpush.api.device;

import cn.jpush.api.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class TagListResult extends BaseResult {

    private static final long serialVersionUID = -5395153728332839175L;
    @Expose public List<String> tags = new ArrayList<String>();
    
}

