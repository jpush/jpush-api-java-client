package cn.jpush.api.push;

import java.util.Map;

/**
 * @author tp
 * @since 2020/8/6
 */
public class GroupPushResult {

    private Map<String, PushResult> appResultMap;

    private String groupMsgId;

    public GroupPushResult() {
    }

    public GroupPushResult(String groupMsgId, Map<String, PushResult> appResultMap) {
        this.groupMsgId = groupMsgId;
        this.appResultMap = appResultMap;
    }

    public Map<String, PushResult> getAppResultMap() {
        return appResultMap;
    }

    public void setAppResultMap(Map<String, PushResult> appResultMap) {
        this.appResultMap = appResultMap;
    }

    public String getGroupMsgId() {
        return groupMsgId;
    }

    public void setGroupMsgId(String groupMsgId) {
        this.groupMsgId = groupMsgId;
    }
}
