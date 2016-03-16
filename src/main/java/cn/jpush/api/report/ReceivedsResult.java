package cn.jpush.api.report;

import cn.jpush.api.common.resp.BaseResult;
import cn.jpush.api.common.resp.ResponseWrapper;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReceivedsResult extends BaseResult {
    private static final Type RECEIVED_TYPE = new TypeToken<List<Received>>() {}.getType();
    private static final long serialVersionUID = 1761456104618847304L;

    @Expose
    public List<Received> received_list = new ArrayList<Received>();


    public static class Received {
        @Expose
        public long msg_id;
        @Expose
        public int android_received;
        @Expose
        public int ios_apns_sent;
        @Expose
        public int ios_msg_received;
        @Expose
        public int wp_mpns_sent;
    }

    static ReceivedsResult fromResponse(ResponseWrapper responseWrapper) {
        ReceivedsResult result = new ReceivedsResult();
        if (responseWrapper.isServerResponse()) {
            result.received_list = _gson.fromJson(responseWrapper.responseContent, RECEIVED_TYPE);
        }

        result.setResponseWrapper(responseWrapper);
        return result;
    }

}
