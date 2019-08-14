package cn.jpush.api.push.model;

import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author
 * @Desc
 * @date 2019-08-14.
 */
@Data
@Accessors(chain = true)
public class SinglePayload {

    @SerializedName("platform")
    private String platform;

    @SerializedName("notification")
    private Notification notification;

    @SerializedName("message")
    private Message message;

    @SerializedName("options")
    private Options options;

    @SerializedName("sms_message")
    private SMS sms;

    @SerializedName("target")
    private String target;

    public void resetOptionsApnsProduction(boolean apnsProduction) {
        if (null == options) {
            options = Options.newBuilder().setApnsProduction(apnsProduction).build();
        } else {
            options.setApnsProduction(apnsProduction);
        }
    }

    public void resetOptionsTimeToLive(long timeToLive) {
        if (null == options) {
            options = Options.newBuilder().setTimeToLive(timeToLive).build();
        } else {
            options.setTimeToLive(timeToLive);
        }
    }
}
