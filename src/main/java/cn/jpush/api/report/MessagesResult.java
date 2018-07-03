package cn.jpush.api.report;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;

public class MessagesResult extends BaseResult {
    private static final Type MESSAGE_TYPE = new TypeToken<List<Message>>() {}.getType();
    private static final long serialVersionUID = -1582895355000647292L;

    @Expose
    public List<Message> messages = new ArrayList<Message>();

    public static class Message {
        @Expose
        public String msg_id;
        @Expose
        public Android android;
        @Expose
        public Ios ios;
        @Expose
        public Winphone winphone;
    }

    public static class Android {
        @Expose
        public int received;
        @Expose
        public int target;
        @Expose
        public int online_push;
        @Expose
        public int click;
        @Expose
        public int msg_click;
    }

    public static class Ios {
        @Expose
        public int apns_sent;
        @Expose
        public int apns_target;
        @Expose
        public int apns_received;
        @Expose
        public int click;
        @Expose
        public int target;
        @Expose
        public int received;
    }

    public static class Winphone {
        @Expose
        public int mpns_target;
        @Expose
        public int mpns_sent;
        @Expose
        public int click;
    }

    static MessagesResult fromResponse(ResponseWrapper responseWrapper) {
        MessagesResult result = new MessagesResult();
        if (responseWrapper.isServerResponse()) {
            result.messages = _gson.fromJson(responseWrapper.responseContent, MESSAGE_TYPE);
        }

        result.setResponseWrapper(responseWrapper);
        return result;
    }

}
