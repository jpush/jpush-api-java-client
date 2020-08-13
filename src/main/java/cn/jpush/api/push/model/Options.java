package cn.jpush.api.push.model;


import com.google.gson.*;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.utils.Preconditions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Options implements PushModel {

    private static final String SENDNO = "sendno";
    private static final String OVERRIDE_MSG_ID = "override_msg_id";
    private static final String TIME_TO_LIVE = "time_to_live";
    private static final String APNS_PRODUCTION = "apns_production";
    private static final String BIG_PUSH_DURATION = "big_push_duration";
    private static final String APNS_COLLAPSE_ID = "apns_collapse_id";
    private static final String THIRD_PARTH_CHANNEl = "third_party_channel";

    private static final long NONE_TIME_TO_LIVE = -1;

    private final int sendno;
    private final long overrideMsgId;
    private long timeToLive;
    private boolean apnsProduction;
    // minutes
    private int bigPushDuration;
    private String apnsCollapseId;
    private final Map<String, JsonPrimitive> customData;


    /**
     * {
     *     "third_party_channel":{
     *         "xiaomi":{
     *             "distribution":"ospush",
     *             "channel_id":"*******"
     *         },
     *         "huawei":{
     *             "distribution":"jpush"
     *         },
     *         "meizu":{
     *             "distribution":"jpush"
     *         },
     *         "fcm":{
     *             "distribution":"ospush"
     *         },
     *         "oppo":{
     *             "distribution":"ospush",
     *             "channel_id":"*******"
     *         },
     *         "vivo":{
     *             "distribution":"ospush",
     *             "classification":0 // 2020/06 新增，和vivo官方字段含义一致 0 代表运营消息，1 代表系统消息，不填vivo官方默认为0
     *                                // 使用此字段时，需使用setThirdPartyChannelV2方法，因为此值只能为整数形式
     *         }
     *     }
     * }
     */
    private Map<String, JsonObject> thirdPartyChannel;

    private Options(int sendno,
                    long overrideMsgId,
                    long timeToLive,
                    boolean apnsProduction,
                    int bigPushDuration,
                    String apnsCollapseId,
                    Map<String, JsonObject> thirdPartyChannel,
                    Map<String, JsonPrimitive> customData) {
        this.sendno = sendno;
        this.overrideMsgId = overrideMsgId;
        this.timeToLive = timeToLive;
        this.apnsProduction = apnsProduction;
        this.bigPushDuration = bigPushDuration;
        this.apnsCollapseId = apnsCollapseId;
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

        @Deprecated
        public Map<String, Map<String, String>> getThirdPartyChannel() {
            if (null != thirdPartyChannel) {
                Map<String, Map<String, String>> thirdPartyChannelRsp = new HashMap<>();
                Set<Map.Entry<String, JsonObject>> entrySet = thirdPartyChannel.entrySet();
                for (Map.Entry<String, JsonObject> entry : entrySet) {
                    JsonObject entryValue = entry.getValue();
                    Set<Map.Entry<String, JsonElement>> valueEntrySet = entryValue.entrySet();
                    Map<String, String> valueMap = new HashMap<>();
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
            this.thirdPartyChannel = new HashMap<>();
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
                customData = new LinkedHashMap<>();
            }
            for (Map.Entry<String, String> entry : extras.entrySet()) {
                customData.put(entry.getKey(), new JsonPrimitive(entry.getValue()));
            }
            return this;
        }

        public Builder addCustom(String key, Number value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }

        public Builder addCustom(String key, String value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<>();
            }
            customData.put(key, new JsonPrimitive(value));
            return this;
        }

        public Builder addCustom(String key, Boolean value) {
            Preconditions.checkArgument(! (null == key), "Key should not be null.");
            if (customData == null) {
                customData = new LinkedHashMap<>();
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

            return new Options(sendno, overrideMsgId, timeToLive, apnsProduction, bigPushDuration, apnsCollapseId, thirdPartyChannel, customData);
        }
    }

}
