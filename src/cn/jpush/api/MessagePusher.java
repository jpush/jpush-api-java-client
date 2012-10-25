package cn.jpush.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cn.jpush.api.domain.CustomMessageParams;
import cn.jpush.api.domain.BaseMessageParams;
import cn.jpush.api.domain.MessageResult;
import cn.jpush.api.domain.MsgTypeEnum;
import cn.jpush.api.domain.NotificationParams;
import cn.jpush.api.domain.ReceiverTypeEnum;
import cn.jpush.api.util.StringUtils;

public class MessagePusher {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private String apiUrl;
	private String username;
	private String md5Password;
	private String callbackUrl;
	
	public MessagePusher(String apiUrl, String username, String password) {
		this.apiUrl = apiUrl;
		this.username = username;
		md5Password = StringUtils.toMD5(password); 
	}
	
	public MessagePusher(String apiUrl, String username, String password, String callbackUrl) {
		this(apiUrl, username, password);
		this.callbackUrl = callbackUrl;
	}
	
	public MessageResult sendNotificationWithImei(String imei, 
			String appKey, String msgTitle, String msgContent, 
			int sendNo, String sendDescription) {
		NotificationParams notificationParams = new NotificationParams();
		notificationParams.addReceiverType(ReceiverTypeEnum.IMEI.value());//接收者类型
		notificationParams.addReceiverValue(imei);		//需要和接收者类型对应
		
		notificationParams.setSendNo(0);
		notificationParams.setSendDescription("send description");
		notificationParams.addPlatform("android");	//android 需要是 enum
		
		notificationParams.addAppKeys(appKey);		// 只支持一个 appKey，需要转
		notificationParams.getMsgContent().setTitle(msgTitle);
		notificationParams.getMsgContent().setContent(msgContent);
		return sendMessage(convertMessage(notificationParams));
	}
	
	public MessageResult sendCustomMessageWithImei(String imei, 
			String appKey, String msgTitle, String msgContent, 
			int sendNo, String sendDescription) {
		CustomMessageParams customMessageParams = new CustomMessageParams();
		customMessageParams.addReceiverType(ReceiverTypeEnum.IMEI.value());//接收者类型
		customMessageParams.addReceiverValue(imei);		//需要和接收者类型对应
		
		customMessageParams.setSendNo(0);
		customMessageParams.setSendDescription("send description");
		customMessageParams.addPlatform("android");	//android 需要是 enum
		
		customMessageParams.addAppKeys(appKey);		// 只支持一个 appKey，需要转
		customMessageParams.getMsgContent().setTitle(msgTitle);
		customMessageParams.getMsgContent().setMessage(msgContent);
		return sendMessage(convertMessage(customMessageParams));
	}


	/*
	 * 简单模拟Post发送信息
	 */
	public MessageResult sendMessage(Map<String, Object> paramMap) {
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader in = null;
		OutputStream out = null;
		StringBuffer result = new StringBuffer();
		try {
			url = new URL(this.apiUrl);
			conn = (HttpURLConnection)url.openConnection();
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setConnectTimeout(20 * 1000);
			conn.setRequestMethod("POST");
			if (null == paramMap) {
				paramMap = new HashMap<String, Object>();
			}
			String params = parseMap(paramMap);
			byte[] bytes = params.getBytes();
			out = conn.getOutputStream();
			out.write(bytes, 0, bytes.length);
			out.flush();
			out.close();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while (null != (line = in.readLine())) {
				result.append(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != conn) {
					conn.disconnect();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		MessageResult messageResult = MessageResult.fromValue(result.toString());
		return messageResult;
	}
	
	/*
	 * 填充剩余数据并且转换成相应的json格式
	 * 返回包装后的Map对象，可以直接用于发送消息接口
	 */
	public Map<String, Object> convertMessage(BaseMessageParams message) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("username", this.username);
		params.put("sendno", message.getSendNo());
		params.put("appkeys", message.getAppKeys());
		params.put("receiver_type", message.getReceiverType());
		params.put("receiver_value", message.getReceiverValue());
		
		String input = this.username + message.getSendNo()
				+ message.getReceiverType() + message.getReceiverValue()
				+ md5Password;
		params.put("verification_code", StringUtils.toMD5(input));
		
		int msgType = -1;
		if (message instanceof NotificationParams) {
			msgType = MsgTypeEnum.Notification.value();
		} else if (message instanceof CustomMessageParams)  {
			msgType = MsgTypeEnum.Custom.value();
		}
		params.put("msg_type", msgType);
		params.put("msg_content", message.getMsgContent().toString());
		params.put("send_description", message.getSendDescription());
		params.put("callback_url", this.callbackUrl);
		params.put("platform", message.getPlatform());
		
		return params;
	}
	
	private String parseMap(Map<String, Object> map) {
		StringBuffer params = new StringBuffer();
		Object value = null;
		for (String key : map.keySet()) {
			value = map.get(key);
			if (value instanceof Map) {
				try {
					params.append("&" + key + "=" + objectMapper.writeValueAsString(value));
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				params.append("&" + key + "=" + value);
			}
		}
		return params.toString().substring(1);
	}
	
}

