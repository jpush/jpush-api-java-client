package cn.jpush.api.push.model;

import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @Desc
 * @date 2019-07-29.
 */
public class BatchPushPayload {

    @SerializedName("pushlist")
    private Map<String, SinglePayload> pushList = new HashMap<>();

    public Map<String, SinglePayload> getPushList() {
        return pushList;
    }

    public BatchPushPayload setPushList(Map<String, SinglePayload> pushList) {
        this.pushList.putAll(pushList);
        return this;
    }

    public BatchPushPayload setPushList(String target, SinglePayload pushList) {
        this.pushList.put(target, pushList);
        return this;
    }

}
