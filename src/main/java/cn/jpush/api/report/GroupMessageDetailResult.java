package cn.jpush.api.report;

import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GroupMessageDetailResult extends BaseResult {

    // what is this?
    private static final Type JSON_OBJECT_TYPE = new TypeToken<List<JsonObject>>() {}.getType();
    private static final Type RECEIVED_TYPE = new TypeToken<List<Received>>() {}.getType();

    @Expose
    public List<Received> received_list = new ArrayList<Received>();

    public static class Received {
        @Expose
        public String group_msgids;
        @Expose
        public JpushDetail jpush;
        @Expose
        public JsonObject android_pns;
        @Expose
        public IosDetail ios;
        @Expose
        public WinphoeDetail winphone;
    }

    public static class JpushDetail {
        @Expose
        public long target;
        @Expose
        public int online_push;
        @Expose
        public int received;
        @Expose
        public int click;
        @Expose
        public int msg_click;
    }

    public static class WinphoeDetail {
        @Expose
        public long mpns_target;
        @Expose
        public int mpns_sent;
        @Expose
        public int click;
    }

    public static class IosDetail {
        @Expose
        public long apns_target;
        @Expose
        public int apns_sent;
        @Expose
        public int apns_received;
        @Expose
        public int apns_click;
        @Expose
        public int msg_target;
        @Expose
        public int msg_received;
    }


    static GroupMessageDetailResult fromResponse(ResponseWrapper responseWrapper) {
        GroupMessageDetailResult result = new GroupMessageDetailResult();
        if (responseWrapper.isServerResponse()) {
            List<JsonObject> tempList = _gson.fromJson(responseWrapper.responseContent, JSON_OBJECT_TYPE);
            for (JsonObject jsonObject : tempList) {
                if (jsonObject.isJsonNull() || jsonObject.get("android_pns").isJsonNull()) {
                    continue;
                }
                Received received = _gson.fromJson(jsonObject, Received.class);
                result.received_list.add(received);
            }
        }

        result.setResponseWrapper(responseWrapper);
        return result;
    }
}
