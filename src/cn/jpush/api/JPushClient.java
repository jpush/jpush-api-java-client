package cn.jpush.api;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;

public class JPushClient {
	private final String hostname = "api.jpush.cn:8800";
	private static final String CHARSET = "UTF-8";
	
	//设置连接超时时间
	private static final int DEFAULT_CONNECTION_TIMEOUT = (20 * 1000); // milliseconds
	//设置读取超时时间
	private static final int DEFAULT_SOCKET_TIMEOUT = (30 * 1000); // milliseconds

	private String username = "";
	private String password = "";
	private String appKey = "";
	private String callbackUrl = "";
	private Integer sendNo = 0;//发送的编号
	private String sendDescription = "";//发送的描述
	private Set<DeviceEnum> devices = new HashSet<DeviceEnum>();//默认发送android和ios

	public JPushClient(String username, String password, String appKey) {
		this.username = username;
		this.password = password;
		this.appKey = appKey;
	}
	
	public JPushClient(String username, String password, String appKey, DeviceEnum device) {
		this.username = username;
		this.password = password;
		this.appKey = appKey;
		devices.add(device);
	}
	
	public JPushClient(String username, String password, String appKey, String callbackUrl, DeviceEnum device) {
		this.username = username;
		this.password = password;
		this.appKey = appKey;
		this.callbackUrl = callbackUrl;
		this.devices.add(device);
	}
	
	/*
	 * @description 发送带IMEI的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithImei(String imei, String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.IMEI);
		p.setReceiverValue(imei);
		return sendNotification(p, msgTitle, msgContent, 0);
	}
	
	/*
	 * @params builderId通知栏样式
	 * @description 发送带IMEI的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithImei(String imei, String msgTitle, String msgContent, int builderId) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.IMEI);
		p.setReceiverValue(imei);
		return sendNotification(p, msgTitle, msgContent, builderId);
	}
	
	/*
	 * @description 发送带IMEI的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithImei(String imei, String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.IMEI);
		p.setReceiverValue(imei);
		return sendCustomMessage(p, msgTitle, msgContent, null, null);
	}
	
	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发送带IMEI的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithImei(String imei, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.IMEI);
		p.setReceiverValue(imei);
		return sendCustomMessage(p, msgTitle, msgContent, msgContentType, extra);
	}
	
	/*
	 * @description 发送带TAG的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithTag(String tag, String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendNotification(p, msgTitle, msgContent, 0);
	}
	
	/*
	 * @params builderId通知栏样式
	 * @description 发送带TAG的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithTag(String tag, String msgTitle, String msgContent, int builderId) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendNotification(p, msgTitle, msgContent, builderId);
	}
	
	/*
	 * @description 发送带TAG的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithTag(String tag, String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendCustomMessage(p, msgTitle, msgContent, null, null);
	}
	
	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发送带TAG的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithTag(String tag, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.TAG);
		p.setReceiverValue(tag);
		return sendCustomMessage(p, msgTitle, msgContent, msgContentType, extra);
	}
	
	/*
	 * @description 发送带ALIAS的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAlias(String alias, String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendNotification(p, msgTitle, msgContent, 0);
	}

	/*
	 * @params builderId通知栏样式
	 * @description 发送带ALIAS的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAlias(String alias, String msgTitle, String msgContent, int builderId) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendNotification(p, msgTitle, msgContent, builderId);
	}

	/*
	 * @description 发送带ALIAS的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAlias(String alias, String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendCustomMessage(p, msgTitle, msgContent, null, null);
	}
	
	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发送带ALIAS的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAlias(String alias, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.ALIAS);
		p.setReceiverValue(alias);
		return sendCustomMessage(p, msgTitle, msgContent, msgContentType, extra);
	}
	
	/*
	 * @description 发送带AppKey的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAppKey(String msgTitle, String msgContent) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendNotification(p, msgTitle, msgContent, 0);
	}
	
	/*
	 * @params builderId通知栏样式
	 * @description 发送带AppKey的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAppKey(String msgTitle, String msgContent, int builderId) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendNotification(p, msgTitle, msgContent, builderId);
	}
	
	/*
	 * @description 发送带AppKey的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAppKey(String msgTitle, String msgContent) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendCustomMessage(p, msgTitle, msgContent, null, null);
	}
	
	/*
	 * @params msgContentType消息的类型，extra附属JSON信息
	 * @description 发送带AppKey的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAppKey(String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		CustomMessageParams p = new CustomMessageParams();
		p.setReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendCustomMessage(p, msgTitle, msgContent, msgContentType, extra);
	}
	
	
	protected MessageResult sendCustomMessage(CustomMessageParams p, String msgTitle, String msgContent, String msgContentType, Map<String, Object> extra) {
		if (null != msgContentType) {
			p.getMsgContent().setContentType(msgContentType);
		}
		if (null != extra) {
			p.getMsgContent().setExtra(extra);
		}
		return sendMessage(p, msgTitle, msgContent);
	}
	
	protected MessageResult sendNotification(NotifyMessageParams p, String msgTitle, String msgContent, int builderId) {
		p.getMsgContent().setBuilderId(builderId);
		return sendMessage(p, msgTitle, msgContent);
	}
	
	protected MessageResult sendMessage(MessageParams p,String msgTitle, String msgContent) {
		p.setAppKeys(this.getAppKey());
		p.setSendNo(this.getSendNo());
		p.setSendDescription(this.getSendDescription());
		for (DeviceEnum device : this.getDevices()) {
			p.addPlatform(device);
		}
		p.getMsgContent().setTitle(msgTitle);
		p.getMsgContent().setMessage(msgContent);
		return sendMessage(p);
	}
	
	protected MessageResult sendMessage(MessageParams p) {
		return post("/sendmsg/sendmsg", p);
	}
	
	protected MessageResult post(final String path, final MessageParams messageParams) {
		HttpURLConnection conn = null;
		DataOutputStream outStream = null;
		MessageResult messageResult = null;
		try {
			URL url = new URL(getUrlForPath(path));
			byte[] data = parse(messageParams).getBytes(CHARSET);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
			conn.setReadTimeout(DEFAULT_SOCKET_TIMEOUT);
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));
			outStream = new DataOutputStream(conn.getOutputStream());
			outStream.write(data);
			outStream.flush();
			
			if (conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();
				StringBuffer sb = new StringBuffer();
				InputStreamReader reader = new InputStreamReader(in, CHARSET);
				char[] buff = new char[1024];
				int len;
				while ((len = reader.read(buff)) > 0) {
					sb.append(buff, 0, len);
				}
				if(!"".equals(sb.toString())){
					messageResult = new Gson().fromJson(sb.toString(), MessageResult.class);
				}
			} else {
				throw new Exception("ResponseCode=" + conn.getResponseCode());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (null != outStream) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != conn) {
				conn.disconnect();
			}
		}
		return messageResult;
	}
	
	protected String getUrlForPath(final String path) {
		if (path.startsWith("http://") || path.startsWith("https://")) {
			return path;
		} else {
			return "http://"
					+ getHostname()
					+ path;
		}
	}
	
	protected String getHostname() {
		return this.hostname;
	}
	
	protected String parse(MessageParams message) {
		String md5SecretKey = StringUtils.toMD5(this.getPassword()); 
		String input = this.getUsername() + message.getSendNo() + message.getReceiverType().value() + message.getReceiverValue() + md5SecretKey;
		int msgType = 0;
		if (message instanceof NotifyMessageParams) {
			msgType = MsgTypeEnum.NOTIFY.value();
		} else if (message instanceof CustomMessageParams)  {
			msgType = MsgTypeEnum.CUSTOM.value();
		}
		
		Map<String, String> nvPair = new HashMap<String, String>();
		nvPair.put("username", this.getUsername());
		nvPair.put("sendno", String.valueOf(message.getSendNo()));
		nvPair.put("appkeys", message.getAppKeys());
		nvPair.put("receiver_type", String.valueOf(message.getReceiverType().value()));
		nvPair.put("receiver_value", message.getReceiverValue());
		nvPair.put("verification_code", StringUtils.toMD5(input));
		nvPair.put("msg_type", String.valueOf(msgType));
		nvPair.put("msg_content", message.getMsgContent().toString());
		nvPair.put("send_description", message.getSendDescription());
		nvPair.put("callback_url", this.getCallbackUrl());
		nvPair.put("platform", message.getPlatform());
		
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, String> entry : nvPair.entrySet()) {
			builder.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		return builder.toString();
	}
	
	protected String getUsername() {
		return this.username;
	}
	
	protected String getPassword() {
		return this.password;
	}
	
	protected String getCallbackUrl() {
		return this.callbackUrl;
	}
	
	protected String getAppKey() {
		return this.appKey;
	}
	
	protected Integer getSendNo() {
		return ++this.sendNo;
	}

	protected String getSendDescription() {
		return this.sendDescription;
	}

	protected Set<DeviceEnum> getDevices() {
		if (null == this.devices) {
			this.devices = new HashSet<DeviceEnum>();
		}
		if (this.devices.size() == 0) {
			this.devices.add(DeviceEnum.Android);
			this.devices.add(DeviceEnum.IOS);
		}
		return this.devices;
	}

	protected MessageResult fromJson(final Object json) {
		if (null == json) {
			return null;
		}
		return MessageResult.fromValue(String.valueOf(json));
	}
	
}
