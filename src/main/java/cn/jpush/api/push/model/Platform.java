package cn.jpush.api.push.model;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import cn.jiguang.commom.utils.Preconditions;

public class Platform implements PushModel {    
    private static final String ALL = "all";
    
    private final boolean all;
    private final Set<String> deviceTypes;
    
    private Platform(boolean all, Set<String> deviceTypes) {
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
        return newBuilder().addDeviceType("android").build();
    }
    
    public static Platform ios() {
        return newBuilder().addDeviceType("ios").build();
    }
    
    public static Platform winphone() {
        return newBuilder().addDeviceType("winphone").build();
    }
    
    public static Platform android_ios() {
        return newBuilder()
                .addDeviceType("android")
                .addDeviceType("ios")
                .build();
    }
    
    public static Platform android_winphone() {
        return newBuilder()
                .addDeviceType("android")
                .addDeviceType("winphone")
                .build();
    }
    
    public static Platform ios_winphone() {
        return newBuilder()
                .addDeviceType("ios")
                .addDeviceType("winphone")
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
        for (String deviceType : deviceTypes) {
            json.add(new JsonPrimitive(deviceType));
        }
        return json;
    }

    public JsonElement toSerializeJSON() {
        JsonObject json = new JsonObject();
        if (all) {
            json.add(ALL, new JsonPrimitive(true));
        } else {
            json.add(ALL, new JsonPrimitive(false));
        }
        JsonArray jsonArray = new JsonArray();
        for (String deviceType : deviceTypes) {
            jsonArray.add(new JsonPrimitive(deviceType));
        }
        json.add("deviceTypes", jsonArray);
        return json;
    }
    
    
    public static class Builder {
        private boolean all;
        private Set<String> deviceTypes;
        
        public Builder setAll(boolean all) {
            this.all = all;
            return this;
        }
        
        public Builder addDeviceType(String deviceType) {
            if (null == deviceTypes) {
                deviceTypes = new HashSet<String>();
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


