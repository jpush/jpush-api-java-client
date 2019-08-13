package cn.jpush.api.push.model;

import java.util.Map;

/**
 * @author xdx
 * @Desc
 * @date 2019-07-29.
 */
public class BatchPushPayload {

    private Map<String, PushPayload> pushlist;

    public Map<String, PushPayload> getPushlist() {
        return pushlist;
    }

    public BatchPushPayload setPushlist(Map<String, PushPayload> pushlist) {
        this.pushlist = pushlist;
        return this;
    }
}
