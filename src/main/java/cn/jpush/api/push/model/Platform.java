package cn.jpush.api.push.model;

import java.util.HashSet;
import java.util.Set;

import cn.jiguang.common.DeviceType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import cn.jiguang.common.utils.Preconditions;

/**
 * https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#platform
 *
 * JPush 当前支持 Android, iOS, QuickApp，Windows Phone 四个平台的推送。其关键字分别为："android", "ios", "quickapp","winphone"。
 * 使用方法，只需要在PushPayload中调用setPlatform方法。如：setPlatform(Platform.android_ios())
 * 如需要全平台推送，只需要setPlatform(Platform.all())
 */
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

    public static Platform quickapp() {
        return newBuilder().addDeviceType(DeviceType.QuickApp).build();
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

    public static Platform android_quickapp() {
        return newBuilder()
                .addDeviceType(DeviceType.Android)
                .addDeviceType(DeviceType.QuickApp)
                .build();
    }

    public static Platform ios_quickapp() {
        return newBuilder()
                .addDeviceType(DeviceType.IOS)
                .addDeviceType(DeviceType.QuickApp)
                .build();
    }

    public static Platform quickapp_winphone() {
        return newBuilder()
                .addDeviceType(DeviceType.QuickApp)
                .addDeviceType(DeviceType.WinPhone)
                .build();
    }

    public static Platform android_ios_quickapp() {
        return newBuilder()
                .addDeviceType(DeviceType.Android)
                .addDeviceType(DeviceType.IOS)
                .addDeviceType(DeviceType.QuickApp)
                .build();
    }

    public static Platform android_ios_winphone() {
        return newBuilder()
                .addDeviceType(DeviceType.Android)
                .addDeviceType(DeviceType.IOS)
                .addDeviceType(DeviceType.WinPhone)
                .build();
    }

    public static Platform android_quickapp_winphone() {
        return newBuilder()
                .addDeviceType(DeviceType.Android)
                .addDeviceType(DeviceType.QuickApp)
                .addDeviceType(DeviceType.WinPhone)
                .build();
    }

    public static Platform ios_quickapp_winphone() {
        return newBuilder()
                .addDeviceType(DeviceType.IOS)
                .addDeviceType(DeviceType.QuickApp)
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


