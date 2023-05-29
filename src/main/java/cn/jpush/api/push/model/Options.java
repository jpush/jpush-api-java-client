package cn.jpush.api.push.model;


import com.google.gson.*;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.utils.Preconditions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 参考文档：https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push#options
 *
 * <p><b>Options</b></p>
 * <br>
 * <ul>
 * <li>sendno: 推送序号 </li>
 * <li>time_to_live: 离线消息保留时长 (秒) </li>
 * <li>override_msg_id: 要覆盖的消息 ID </li>
 * <li>apns_production: APNs 是否生产环境 </li>
 * <li>apns_collapse_id: 更新 iOS 通知的标识符 </li>
 * <li>big_push_duration: 定速推送时长 (分钟) </li>
 * <li>third_party_channel: 推送请求下发通道 </li>
 * <li>classification: 消息类型分类，极光不对指定的消息类型进行判断或校准，会以开发者自行指定的消息类型适配 Android 厂商通道。不填默认为 0 </li>
 * </ul>
 */
public class Options implements PushModel {

    private static final String SENDNO = "sendno";
    private static final String OVERRIDE_MSG_ID = "override_msg_id";
    private static final String TIME_TO_LIVE = "time_to_live";
    private static final String APNS_PRODUCTION = "apns_production";
    private static final String BIG_PUSH_DURATION = "big_push_duration";
    private static final String APNS_COLLAPSE_ID = "apns_collapse_id";
    private static final String THIRD_PARTH_CHANNEl = "third_party_channel";
    private static final String CLASSIFICATION = "classification";

    private static final long NONE_TIME_TO_LIVE = -1;

    private final int sendno;
    private final long overrideMsgId;
    private long timeToLive;
    private boolean apnsProduction;
    // minutes
    private int bigPushDuration;
    private String apnsCollapseId;
    private int classification;
    /**
     * 参考：https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push#third_party_channel-%E8%AF%B4%E6%98%8E
     */
    private Map<String, JsonObject> thirdPartyChannel;
    private final Map<String, JsonPrimitive> customData;

    private Options(int sendno,
                    long overrideMsgId,
                    long timeToLive,
                    boolean apnsProduction,
                    int bigPushDuration,
                    String apnsCollapseId,
                    int classification,
                    Map<String, JsonObject> thirdPartyChannel,
                    Map<String, JsonPrimitive> customData) {
        this.sendno = sendno;
        this.overrideMsgId = overrideMsgId;
        this.timeToLive = timeToLive;
        this.apnsProduction = apnsProduction;
        this.bigPushDuration = bigPushDuration;
        this.apnsCollapseId = apnsCollapseId;
        this.classification = classification;
        this.thirdPartyChannel = thirdPartyChannel;
        this.customData = customData;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Options sendno() {
        return newBuilder().setSendno(ServiceHelper.generateSendno()).build();
    }

    public static Options sendno(int sendno) {
        return newBuilder().setSendno(sendno).build();
    }

    public void setApnsProduction(boolean apnsProduction) {
        this.apnsProduction = apnsProduction;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setBigPushDuration(int bigPushDuration) {
        this.bigPushDuration = bigPushDuration;
    }

    public int getSendno() {
        return this.sendno;
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (sendno > 0) {
            json.add(SENDNO, new JsonPrimitive(sendno));
        }
        if (overrideMsgId > 0) {
            json.add(OVERRIDE_MSG_ID, new JsonPrimitive(overrideMsgId));
        }
        if (timeToLive >= 0) {
            json.add(TIME_TO_LIVE, new JsonPrimitive(timeToLive));
        }

        json.add(APNS_PRODUCTION, new JsonPrimitive(apnsProduction));

        if (bigPushDuration > 0) {
            json.add(BIG_PUSH_DURATION, new JsonPrimitive(bigPushDuration));
        }

        if (apnsCollapseId != null) {
            json.add(APNS_COLLAPSE_ID, new JsonPrimitive(apnsCollapseId));
        }

        json.add(CLASSIFICATION, new JsonPrimitive(classification));

        if (null != thirdPartyChannel && thirdPartyChannel.size() > 0) {
            JsonObject partyChannel = new JsonObject();
            for (Map.Entry<String, JsonObject> entry : thirdPartyChannel.entrySet()) {
                JsonObject channel = entry.getValue();
                partyChannel.add(entry.getKey(), channel);
            }
            json.add(THIRD_PARTH_CHANNEl, partyChannel);
        }

        if (null != customData) {
            for (Map.Entry<String, JsonPrimitive> entry : customData.entrySet()) {
                json.add(entry.getKey(), entry.getValue());
            }
        }

        return json;
    }

    public static class Builder {

        private int sendno = 0;
        private long overrideMsgId = 0;
        private long timeToLive = NONE_TIME_TO_LIVE;
        private boolean apnsProduction = false;
        private int bigPushDuration = 0;
        private String apnsCollapseId;
        private int classification;
        private Map<String, JsonObject> thirdPartyChannel;
        private Map<String, JsonPrimitive> customData;

        public Builder setSendno(int sendno) {
            this.sendno = sendno;
            return this;
        }

        public Builder setOverrideMsgId(long overrideMsgId) {
            this.overrideMsgId = overrideMsgId;
            return this;
        }

        public Builder setTimeToLive(long timeToLive) {
            this.timeToLive = timeToLive;
            return this;
        }

        public Builder setApnsProduction(boolean apnsProduction) {
            this.apnsProduction = apnsProduction;
            return this;
        }

        public Builder setApnsCollapseId(String id) {
            this.apnsCollapseId = id;
            return this;
        }

        public Builder setBigPushDuration(int bigPushDuration) {
            this.bigPushDuration = bigPushDuration;
            return this;
        }

        public Builder setClassification(int classification) {
            this.classification = classification;
            return this;
        }

        @Deprecated
        public Map<String, Map<String, String>> getThirdPartyChannel() {
            if (null != thirdPartyChannel) {
                Map<String, Map<String, String>> thirdPartyChannelRsp = new HashMap<String, Map<String, String>>();
                Set<Map.Entry<String, JsonObject>> entrySet = thirdPartyChannel.entrySet();
                for (Map.Entry<String, JsonObject> entry : entrySet) {
                    JsonObject entryValue = entry.getValue();
                    Set<Map.Entry<String, JsonElement>> valueEntrySet = entryValue.entrySet();
                    Map<String, String> valueMap = new HashMap<String, String>();
                    for (Map.Entry<String, JsonElement> valueEntry : valueEntrySet) {
                        valueMap.put(valueEntry.getKey(), null == valueEntry.getValue() ? null : valueEntry.getValue().getAsString());
                    }
                    thirdPartyChannelRsp.put(entry.getKey(), valueMap);
                }
                return thirdPartyChannelRsp;
            }
            return null;
        }

        @Deprecated
        public Builder setThirdPartyChannel(Map<String, Map<String, String>> thirdPartyChannel) {
            this.thirdPartyChannel = new HashMap<String, JsonObject>();
            if (null != thirdPartyChannel) {
                Set<Map.Entry<String, Map<String, String>>> entrySet = thirdPartyChannel.entrySet();
                for (Map.Entry<String, Map<String, String>> entry : entrySet) {
                    String key = entry.getKey();
                    Map<String, String> valueMap = entry.getValue();
                    JsonObject valueObj = new JsonObject();
                    if (null != valueMap) {
                        Set<Map.Entry<String, String>> valueEntrySet = valueMap.entrySet();
                        for (Map.Entry<String, String> valueEntry : valueEntrySet) {
                            valueObj.addProperty(valueEntry.getKey(), valueEntry.getValue());
                        }
                        this.thirdPartyChannel.put(key, valueObj);
                    }
                }

            }
            return this;
        }

        public Builder setThirdPartyChannelV2(Map<String, JsonObject> thirdPartyChannel) {
            this.thirdPartyChannel = thirdPartyChannel;
            return this;
        }

        public Builder addCustom(Map<String, String> extras) {
            if (customData == null) {
                customData = new LinkedHashMap<String, JsonPrimitive>();
            }
            for (Map.Entry<String, String> entry : extras.entrySet()) {
                customData.put(entry.getKey(), new JsonPrimitive(entry.getValue()));
            }
            return this;
        }

        public Builder addCustom(String key, Number value) {
            Preconditions.checkArgument(!(null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<String, JsonPrimitive>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }

        public Builder addCustom(String key, String value) {
            Preconditions.checkArgument(!(null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<String, JsonPrimitive>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }

        public Builder addCustom(String key, Boolean value) {
            Preconditions.checkArgument(!(null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<String, JsonPrimitive>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }

        public Options build() {
            Preconditions.checkArgument(sendno >= 0, "sendno should be greater than 0.");
            Preconditions.checkArgument(overrideMsgId >= 0, "override_msg_id should be greater than 0.");
            Preconditions.checkArgument(timeToLive >= NONE_TIME_TO_LIVE, "time_to_live should be greater than 0.");
            Preconditions.checkArgument(bigPushDuration >= 0, "bigPushDuration should be greater than 0.");

            if (sendno <= 0) {
                sendno = ServiceHelper.generateSendno();
            }

            return new Options(sendno, overrideMsgId, timeToLive, apnsProduction, bigPushDuration, apnsCollapseId, classification, thirdPartyChannel, customData);
        }
    }

}
