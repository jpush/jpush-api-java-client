package cn.jpush.api.push.model;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.api.common.DeviceType;
import cn.jpush.api.utils.Preconditions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class Platform implements PushModel {    
    private static final String ALL = "all";
    
    private final boolean all;
    private final Set<DeviceType> deviceTypes;
    
    private Platform(boolean all, Set<DeviceType> deviceTypes) {
        this.all = all;
        this.deviceTypes = deviceTypes;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static Platform all() {
        return newBuilder().setAll(true).build();
    }
    
    public static Platform android() {
        return newBuilder().addDeviceType(DeviceType.Android).build();
    }
    
    public static Platform ios() {
        return newBuilder().addDeviceType(DeviceType.IOS).build();
    }
    
    public static Platform winphone() {
        return newBuilder().addDeviceType(DeviceType.WinPhone).build();
    }
    
    public static Platform android_ios() {
        return newBuilder()
                .addDeviceType(DeviceType.Android)
                .addDeviceType(DeviceType.IOS)
                .build();
    }
    
    public static Platform android_winphone() {
        return newBuilder()
                .addDeviceType(DeviceType.Android)
                .addDeviceType(DeviceType.WinPhone)
                .build();
    }
    
    public static Platform ios_winphone() {
        return newBuilder()
                .addDeviceType(DeviceType.IOS)
                .addDeviceType(DeviceType.WinPhone)
                .build();
    }
    
    public boolean isAll() {
        return all;
    }
    
    @Override
    public JsonElement toJSON() {
        if (all) {
            return new JsonPrimitive(ALL);
        }
        
        JsonArray json = new JsonArray();
        for (DeviceType deviceType : deviceTypes) {
            json.add(new JsonPrimitive(deviceType.value()));
        }
        return json;
    }
    
    
    public static class Builder {
        private boolean all;
        private Set<DeviceType> deviceTypes;
        
        public Builder setAll(boolean all) {
            this.all = all;
            return this;
        }
        
        public Builder addDeviceType(DeviceType deviceType) {
            if (null == deviceTypes) {
                deviceTypes = new HashSet<DeviceType>();
            }
            deviceTypes.add(deviceType);
            return this;
        }
        
        public Platform build() {
            Preconditions.checkArgument(! (all && null != deviceTypes), "Since all is enabled, any platform should not be set.");
            Preconditions.checkArgument(! (!all && null == deviceTypes), "No any deviceType is set.");
            return new Platform(all, deviceTypes);
        }
    }
    
}


