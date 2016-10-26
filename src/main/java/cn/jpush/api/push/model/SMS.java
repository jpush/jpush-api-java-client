package cn.jpush.api.push.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;

public class SMS implements PushModel {

    private final String content;
    private final int delay_time;

    private SMS(String content, int delay_time) {
        this.content = content;
        this.delay_time = delay_time;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Create a SMS content with a delay time.
     * JPush will send a SMS if the message doesn't received within the delay time. If the delay time is 0, the SMS will be sent immediately.
     * Please note the delay time only works on Android.
     * If you are pushing to iOS, the SMS will be sent immediately, whether or not the delay time is 0.
     *
     * @param content The SMS content.
     * @param delayTime The seconds you want to delay, should be greater than or equal to 0.
     * @return SMS payload.
     */
    public static SMS content(String content, int delayTime) {
        return new Builder()
                .setContent(content)
                .setDelayTime(delayTime)
                .build();
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        json.addProperty("content", content);
        json.addProperty("delay_time", delay_time);
        return json;
    }

    public static class Builder {
        private String content;
        private int delay_time;

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setDelayTime(int delayTime) {
            this.delay_time = delayTime;
            return this;
        }

        public SMS build() {
            Preconditions.checkArgument(StringUtils.isNotEmpty(content), "The content must not be empty.");
            Preconditions.checkArgument(delay_time >= 0, "The delay time must be greater than or equal to 0");

            return new SMS(content, delay_time);
        }

    }
}
