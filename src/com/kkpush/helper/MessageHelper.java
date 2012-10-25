package com.kkpush.helper;

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
import com.kkpush.helper.domain.MessageParam;
import com.kkpush.helper.domain.CustomMessageParam;
import com.kkpush.helper.domain.MessageResult;
import com.kkpush.helper.domain.NotifyMessageParam;
import com.kkpush.helper.enums.MsgTypeEnum;
import com.kkpush.helper.enums.ReceiverTypeEnum;
import com.kkpush.helper.util.StringUtils;

public class MessageHelper {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String []args) {
		String urlString = "http://api.jpush.cn:8800/sendmsg/sendmsg";
		NotifyMessageParam notifyMessage = new NotifyMessageParam();
		notifyMessage.setUserName("admin");
		notifyMessage.setPassword("1234");
		notifyMessage.setSendNo(2244);
		notifyMessage.addReceiverType(ReceiverTypeEnum.APPKEYS.value());
		notifyMessage.addReceiverValue("4");
		notifyMessage.setCallbackUrl("http://dev.jpush.cn/pushMsg/callbck");
		notifyMessage.setSendDescription("kktalk");
		notifyMessage.addAppKeys("10cb7756722efdd37a2417e9");
		notifyMessage.addPlatform("android");
		notifyMessage.addPlatform("ios");
		notifyMessage.getMsgContent().setTitle("");
		notifyMessage.getMsgContent().setBuilderId("0");
		notifyMessage.getMsgContent().setContent("_content_");
		MessageResult result = sendMessage(urlString, convertMessage(notifyMessage));
		System.out.println("notifyMessage: " + result);
		
		CustomMessageParam customMessage = new CustomMessageParam();
		customMessage.setUserName("admin");
		customMessage.setPassword("1234");
		customMessage.setSendNo(2244);
		customMessage.addReceiverType(ReceiverTypeEnum.APPKEYS.value());
		customMessage.addReceiverValue("4");
		customMessage.setCallbackUrl("http://dev.jpush.cn/pushMsg/callbck");
		customMessage.setSendDescription("kktalk");
		customMessage.addAppKeys("10cb7756722efdd37a2417e9");
		customMessage.addPlatform("android");
		customMessage.getMsgContent().setMessage("");
		customMessage.getMsgContent().setContentType("");
		customMessage.getMsgContent().setTitle("_title_");
		customMessage.getMsgContent().setExtra(new HashMap<String, Object>());
		result = sendMessage(urlString, convertMessage(customMessage));
		System.out.println("customMessage:" + result);
	}
	
	/*
	 * 简单模拟Post发送信息
	 */
	public static MessageResult sendMessage(String urlString, Map<String, Object> paramMap) {
		URL url = null;
		HttpURLConnection conn = null;
		BufferedReader in = null;
		OutputStream out = null;
		StringBuffer result = new StringBuffer();
		try {
			url = new URL(urlString);
			conn = (HttpURLConnection)url.openConnection();
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setConnectTimeout(20 * 1000);
			conn.setRequestMethod("POST");
			if (null == paramMap) {
				paramMap = new HashMap<String, Object>();
			}
			String params = parseMap(paramMap);
			System.out.println("post request param: "  + params);
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
		System.out.println(result.toString());
		MessageResult messageResult = MessageResult.fromValue(result.toString());
		return messageResult;
	}
	
	/*
	 * 填充剩余数据并且转换成相应的json格式
	 * 返回包装后的Map对象，可以直接用于发送消息接口
	 */
	public static Map<String, Object> convertMessage(MessageParam message) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("username", message.getUserName());
		params.put("sendno", message.getSendNo());
		params.put("appkeys", message.getAppKeys());
		params.put("receiver_type", message.getReceiverType());
		params.put("receiver_value", message.getReceiverValue());
		
		String md5SecretKey = StringUtils.toMD5(message.getPassword()); 
		String input = message.getUserName() + message.getSendNo() + message.getReceiverType() + message.getReceiverValue() + md5SecretKey;
		params.put("verification_code", StringUtils.toMD5(input));
		int msgType = -1;
		if (message instanceof NotifyMessageParam) {
			msgType = MsgTypeEnum.NOTIFY.value();
		} else if (message instanceof CustomMessageParam)  {
			msgType = MsgTypeEnum.CUSTOM.value();
		}
		params.put("msg_type", msgType);
		params.put("msg_content", message.getMsgContent().toString());
		params.put("send_description", message.getSendDescription());
		params.put("callback_url", message.getCallbackUrl());
		params.put("platform", message.getPlatform());
		
		return params;
	}
	
	private static String parseMap(Map<String, Object> map) {
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
