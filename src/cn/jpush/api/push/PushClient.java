package cn.jpush.api.push;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.api.common.BaseHttpClient;
import cn.jpush.api.common.DeviceEnum;
import cn.jpush.api.common.ResponseResult;
import cn.jpush.api.common.ValidateRequestParams;
import cn.jpush.api.utils.StringUtils;

public class PushClient extends BaseHttpClient {
    private static final String HOST_NAME_SSL = "https://api.jpush.cn";
    private static final String HOST_NAME = "http://api.jpush.cn:8800";
    private static final String PUSH_PATH = "/v2/push";
    
    private String appKey;
    private String masterSecret;
    private long timeToLive = -1;
    private boolean enableSSL = false;
    private boolean apnsProduction = false;
    private Set<DeviceEnum> devices = new HashSet<DeviceEnum>();
    	
	public PushClient(String masterSecret, String appKey, long timeToLive, DeviceEnum device, boolean apnsProduction) {
		this.masterSecret = masterSecret;
		this.appKey = appKey;
		this.timeToLive = timeToLive;
		this.apnsProduction = apnsProduction;
        if (null != device) {
            this.devices.add(device);
        }
	}


	public MessageResult sendNotification(String notificationContent, NotificationParams params, Map<String, Object> extras) {
		if (null != extras) {
			params.getMsgContent().setExtras(extras);
		}
		return sendMessage(notificationContent, params);
	}
	
	public MessageResult sendCustomMessage(String msgTitle, String msgContent, CustomMessageParams params, Map<String, Object> extras) {
	    if (null != msgTitle) {
	        params.getMsgContent().setTitle(msgTitle);
	    }
        if (null != extras) {
            params.getMsgContent().setExtras(extras);
        }
        return sendMessage(msgContent, params);
    }

	
	private MessageResult sendMessage(String content, MessageParams params) {
        params.setApnsProduction(this.apnsProduction ? 1 : 0);
		params.setAppKey(this.getAppKey());
		params.setMasterSecret(this.masterSecret);
		if (params.getTimeToLive() == MessageParams.NO_TIME_TO_LIVE) {
		    // no specific will then use the setting in instance
		    params.setTimeToLive(this.timeToLive);
		}
		for (DeviceEnum device : this.getDevices()) {
			params.addPlatform(device);
		}
		params.getMsgContent().setMessage(content);
	    
		return sendPush(enableSSL, params);
	}

    public MessageResult sendPush(final boolean enableSSL, final MessageParams params) {
        ValidateRequestParams.checkPushParams(params);
        String url = enableSSL ? HOST_NAME_SSL : HOST_NAME;
        url += PUSH_PATH;
        
        ResponseResult result = sendPost(url, enableSSL, parse(params), null);
        MessageResult rr = null;
        if (result.responseCode == RESPONSE_OK) {
            rr = _gson.fromJson(result.responseContent, MessageResult.class);
        } else {
            rr = new MessageResult();
        }
        rr.responseResult = result;
        
        return rr;
    }
    
    protected String parse(MessageParams message) { 
        String input = String.valueOf(message.getSendNo()) + message.getReceiverType().value() + message.getReceiverValue() + message.getMasterSecret();
        int msgType = 0;
        if (message instanceof NotificationParams) {
            msgType = MsgTypeEnum.NOTIFY.value();
        } else if (message instanceof CustomMessageParams)  {
            msgType = MsgTypeEnum.CUSTOM.value();
        }

        Map<String, String> nvPair = new HashMap<String, String>();
        nvPair.put("sendno", String.valueOf(message.getSendNo()));
        nvPair.put("app_key", message.getAppKey());
        nvPair.put("receiver_type", String.valueOf(message.getReceiverType().value()));
        nvPair.put("receiver_value", message.getReceiverValue());
        nvPair.put("verification_code", StringUtils.toMD5(input));
        nvPair.put("msg_type", String.valueOf(msgType));
        nvPair.put("msg_content", message.getMsgContent().toString());
        nvPair.put("platform", message.getPlatform());
        nvPair.put("apns_production", message.getApnsProduction() + "");
        if (message.getTimeToLive() >=0) {
            nvPair.put("time_to_live", String.valueOf(message.getTimeToLive()));
        }
        if(null != message.getOverrideMsgId() && !"".equals(message.getOverrideMsgId())){
            nvPair.put("override_msg_id", message.getOverrideMsgId());
        }

        return mapWithParms(nvPair);
    }

    protected String mapWithParms(Map<String, String> nvPair){
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : nvPair.entrySet()) {
            builder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        return builder.toString();
    }

	
    public String getMasterSecret() {
        return masterSecret;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public Set<DeviceEnum> getDevices() {
        if (null == this.devices) {
            this.devices = new HashSet<DeviceEnum>();
        }
        if (this.devices.size() == 0) {
            this.devices.add(DeviceEnum.Android);
            this.devices.add(DeviceEnum.IOS);
        }
        return this.devices;
    }

    public void setEnableSSL(boolean enableSSL) {
        this.enableSSL = enableSSL;
    }

    
}


