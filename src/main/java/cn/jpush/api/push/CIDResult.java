package cn.jpush.api.push;

import cn.jiguang.common.resp.BaseResult;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class CIDResult extends BaseResult {

    @Expose public List<String> cidlist = new ArrayList<String>();
}
