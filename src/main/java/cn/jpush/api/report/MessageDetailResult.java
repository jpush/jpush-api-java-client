package cn.jpush.api.report;

import cn.jiguang.common.resp.BaseResult;
import cn.jiguang.common.resp.ResponseWrapper;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import javax.security.auth.Subject;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MessageDetailResult extends BaseResult {

    private static final Type RECEIVED_TYPE = new TypeToken<List<Received>>() {}.getType();
    private static final long serialVersionUID = 156439166846147394L;

    @Expose
    public List<Received> received_list = new ArrayList<Received>();

    public static class Received {
        @Expose
        public long msg_id;
        @Expose
        public JpushDetail jpush;
        @Expose
        public JsonObject android_pns;
        @Expose
        public ReceivedDetails details;
        @Expose
        public IosDetail ios;
        @Expose
        public WinphoeDetail winphone;
    }

    public static class ReceivedDetails{
        @Expose
        private Notification notification;
        @Expose
        private Message message;
        @Expose
        private Inapp inapp;

        public Notification getNotification(){
            return notification;
        }

        public Message getMessage(){
            return message;
        }

        public Inapp getInapp(){
            return inapp;
        }
    }

    public static class Notification{
        @Expose
        private int target;
        @Expose
        private int sent;
        @Expose
        private int received;
        @Expose
        private int display;
        @Expose
        private int click;
        @Expose
        private NotificationSubAndroid sub_android;
        @Expose
        private NotificationSubIos sub_ios;
        @Expose
        private NotificationSubQuickApp sub_quickapp;

        public int getTarget() {
            return target;
        }

        public int getSent() {
            return sent;
        }

        public int getReceived() {
            return received;
        }

        public int getDisplay() {
            return display;
        }

        public int getClick() {
            return click;
        }

        public NotificationSubAndroid getSub_android() {
            return sub_android;
        }

        public NotificationSubIos getSub_ios() {
            return sub_ios;
        }

        public NotificationSubQuickApp getSub_quickapp() {
            return sub_quickapp;
        }
    }

    public static class Message{
        @Expose
        private int target;
        @Expose
        private int sent;
        @Expose
        private int received;
        @Expose
        private int display;
        @Expose
        private int click;
        @Expose
        private MessageSubAndroid sub_android;
        @Expose
        private MessageSubIos sub_ios;

        public int getTarget() {
            return target;
        }

        public int getSent() {
            return sent;
        }

        public int getReceived() {
            return received;
        }

        public int getDisplay() {
            return display;
        }

        public int getClick() {
            return click;
        }

        public MessageSubAndroid getSub_android() {
            return sub_android;
        }

        public MessageSubIos getSub_ios() {
            return sub_ios;
        }
    }

    public static class Inapp{
        @Expose
        private int target;
        @Expose
        private int sent;
        @Expose
        private int received;
        @Expose
        private int display;
        @Expose
        private int click;
        @Expose
        private InappSubAndroid sub_android;
        @Expose
        private InappSubIos sub_ios;

        public int getTarget() {
            return target;
        }

        public int getSent() {
            return sent;
        }

        public int getReceived() {
            return received;
        }

        public int getDisplay() {
            return display;
        }

        public int getClick() {
            return click;
        }

        public InappSubAndroid getSub_android() {
            return sub_android;
        }

        public InappSubIos getSub_ios() {
            return sub_ios;
        }
    }

    public static class SubObject{
        @Expose
        private int target;
        @Expose
        private int sent;
        @Expose
        private int received;
        @Expose
        private int display;
        @Expose
        private int click;

        public int getTarget() {
            return target;
        }

        public int getSent() {
            return sent;
        }

        public int getReceived() {
            return received;
        }

        public int getDisplay() {
            return display;
        }

        public int getClick() {
            return click;
        }
    }

    public static class NotificationSubAndroid{
        @Expose
        private SubObject jg_android;
        @Expose
        private SubObject huawei;
        @Expose
        private SubObject xiaomi;
        @Expose
        private SubObject oppo;
        @Expose
        private SubObject vivo;
        @Expose
        private SubObject meizu;
        @Expose
        private SubObject fcm;
        @Expose
        private SubObject asus;
        @Expose
        private SubObject tuibida;
    }

    public static class NotificationSubIos{
        @Expose
        private SubObject voip;
        @Expose
        private SubObject apns;
    }

    public static class NotificationSubQuickApp{
        @Expose
        private SubObject quick_jg;
        @Expose
        private SubObject quick_huawei;
        @Expose
        private SubObject quick_xiaomi;
        @Expose
        private SubObject quick_oppo;
    }

    public static class MessageSubAndroid{
        @Expose
        private SubObject jg_android;
        @Expose
        private SubObject huawei;
        @Expose
        private SubObject xiaomi;
        @Expose
        private SubObject fcm;
    }

    public static class MessageSubIos{
        @Expose
        private SubObject jg_ios;
    }

    public static class MessageQuickApp{
        @Expose
        private SubObject quick_jg;
    }

    public static class InappSubAndroid{
        @Expose
        private SubObject jg_android;
    }

    public static class InappSubIos{
        @Expose
        private SubObject jg_ios;
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

    static MessageDetailResult fromResponse(ResponseWrapper responseWrapper) {
        MessageDetailResult result = new MessageDetailResult();
        if (responseWrapper.isServerResponse()) {
            result.received_list = _gson.fromJson(responseWrapper.responseContent, RECEIVED_TYPE);
        }

        result.setResponseWrapper(responseWrapper);
        return result;
    }

}
