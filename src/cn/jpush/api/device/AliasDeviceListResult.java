package cn.jpush.api.device;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.api.common.resp.BaseResult;

import com.google.gson.annotations.Expose;

public class AliasDeviceListResult extends BaseResult {
    @Expose public List<RegistrationId> registration_ids = new ArrayList<RegistrationId>();
    
    public static class RegistrationId {
        @Expose public String id;
    }
    
}

