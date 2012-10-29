package cn.jpush.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class JPushClient {
	private final String hostname = "api.jpush.cn:8800";
	private static final String CHARSET = "UTF-8";
	
	//设置连接超时时间
	private static final int DEFAULT_CONNECTION_TIMEOUT = (20 * 1000); // milliseconds
	//设置读取超时时间
	private static final int DEFAULT_SOCKET_TIMEOUT = (30 * 1000); // milliseconds

	private String username;
	private String password;
	private String callbackUrl;
	private HttpClient httpClient;

	public JPushClient(String username, String password, String callbackUrl) {
		this.username = username;
		this.password = password;
		this.callbackUrl = callbackUrl;
		
		
		setSocketTimeout(DEFAULT_SOCKET_TIMEOUT);
		
		setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
	}
	
	/*
	 * @author ace
	 * @createDate 2012/10/25
	 * @param 参数说明@see MessageParams
	 * @description 发送带IMEI的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithImei(String imei, String appKey,
					int sendNo, String sendDescription,
					String msgTitle, String msgContent,
					DeviceEnum ...devices) {
		MessageParams p = new NotifyMessageParams();
		p.addReceiverType(ReceiverTypeEnum.IMEI);
		p.addReceiverValue(imei);
		return sendMessage(p, appKey, sendNo, sendDescription, msgTitle, msgContent, devices);
	}
	
	/*
	 * @author ace
	 * @createDate 2012/10/25
	 * @param 参数说明@see MessageParams
	 * @description 发送带IMEI的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithImei(String imei, String appKey,
					int sendNo, String sendDescription,
					String msgTitle, String msgContent,
					DeviceEnum ...devices) {
		MessageParams p = new CustomMessageParams();
		p.addReceiverType(ReceiverTypeEnum.IMEI);
		p.addReceiverValue(imei);
		return sendMessage(p, appKey, sendNo, sendDescription, msgTitle, msgContent, devices);
	}
	
	/*
	 * @author ace
	 * @createDate 2012/10/25
	 * @param 参数说明@see MessageParams
	 * @description 发送带TAG的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithTag(String tag, String appKey,
					int sendNo, String sendDescription,
					String msgTitle, String msgContent,
					DeviceEnum ...devices) {
		MessageParams p = new NotifyMessageParams();
		p.addReceiverType(ReceiverTypeEnum.TAG);
		p.addReceiverValue(tag);
		return sendMessage(p, appKey, sendNo, sendDescription, msgTitle, msgContent, devices);
	}
	
	/*
	 * @author ace
	 * @createDate 2012/10/25
	 * @param 参数说明@see MessageParams
	 * @description 发送带TAG的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithTag(String tag, String appKey,
					int sendNo, String sendDescription,
					String msgTitle, String msgContent,
					DeviceEnum ...devices) {
		MessageParams p = new CustomMessageParams();
		p.addReceiverType(ReceiverTypeEnum.TAG);
		p.addReceiverValue(tag);
		return sendMessage(p, appKey, sendNo, sendDescription, msgTitle, msgContent, devices);
	}
	
	/*
	 * @author ace
	 * @createDate 2012/10/25
	 * @param 参数说明@see MessageParams
	 * @description 发送带ALIAS的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAlias(String alias, String appKey,
					int sendNo, String sendDescription,
					String msgTitle, String msgContent,
					DeviceEnum ...devices) {
		NotifyMessageParams p = new NotifyMessageParams();
		p.addReceiverType(ReceiverTypeEnum.ALIAS);
		p.addReceiverValue(alias);
		return sendMessage(p, appKey, sendNo, sendDescription, msgTitle, msgContent, devices);
	}
	
	/*
	 * @author ace
	 * @createDate 2012/10/25
	 * @param 参数说明@see MessageParams
	 * @description 发送带ALIAS的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAlias(String alias, String appKey,
					int sendNo, String sendDescription,
					String msgTitle, String msgContent,
					DeviceEnum ...devices) {
		MessageParams p = new CustomMessageParams();
		p.addReceiverType(ReceiverTypeEnum.ALIAS);
		p.addReceiverValue(alias);
		return sendMessage(p, appKey, sendNo, sendDescription, msgTitle, msgContent, devices);
	}
	
	/*
	 * @author ace
	 * @createDate 2012/10/25
	 * @param 参数说明@see MessageParams
	 * @description 发送带AppKey的通知
	 * @return MessageResult
	 */
	public MessageResult sendNotificationWithAppKey(String appKey,
					int sendNo, String sendDescription,
					String msgTitle, String msgContent,
					DeviceEnum ...devices) {
		MessageParams p = new NotifyMessageParams();
		p.addReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendMessage(p, appKey, sendNo, sendDescription, msgTitle, msgContent, devices);
	}
	
	/*
	 * @author ace
	 * @createDate 2012/10/25
	 * @param 参数说明@see MessageParams
	 * @description 发送带AppKey的自定义消息
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessageWithAppKey(String appKey,
					int sendNo, String sendDescription,
					String msgTitle, String msgContent,
					DeviceEnum ...devices) {
		MessageParams p = new CustomMessageParams();
		p.addReceiverType(ReceiverTypeEnum.APPKEYS);
		return sendMessage(p, appKey, sendNo, sendDescription, msgTitle, msgContent, devices);
	}
	
	/*
	 * @author ace
	 * @createDate 2012/10/25
	 * @param 详见方法参数
	 * @description 由于还有更多组合的情况，手动封装NotifyMessage
	 * @return MessageResult
	 */
	public MessageResult sendNotification(NotifyMessageParams p) {
		return sendMessage(p);
	}
	
	/*
	 * @author ace
	 * @createDate 2012/10/25
	 * @param 详见方法参数
	 * @description 由于还有更多组合的情况，手动封装CustomMessage
	 * @return MessageResult
	 */
	public MessageResult sendCustomMessage(CustomMessageParams p) {
		return sendMessage(p);
	}
	
	protected MessageResult sendMessage(MessageParams p) {
		return post("/sendmsg/sendmsg", p);
	}
	
	protected MessageResult sendMessage(MessageParams p, String appKey,
					int sendNo, String sendDescription,
					String msgTitle, String msgContent,
					DeviceEnum ...devices) {
		p.setAppKeys(appKey);
		p.setSendNo(sendNo);
		p.setSendDescription(sendDescription);
		p.getMsgContent().setTitle(msgTitle);
		p.getMsgContent().setMessage(msgContent);
		if (null != devices) {
			for (DeviceEnum device : devices) {
				p.addPlatform(device);
			}
		}
		return sendMessage(p);
	}

	protected MessageResult post(final String path, final MessageParams obj) {
		HttpPost post = createHttpPost(path);
		if (null != obj) {
			post.setEntity(toEntity(obj));
		}
		HttpResponse response = execute(post);
		HttpEntity responseEntity = response.getEntity();
		try {
			return fromJson(EntityUtils.toString(responseEntity));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected HttpClient getHttpClient() {
		if (null == this.httpClient) {
			this.httpClient = createHttpClient();
		}
		return this.httpClient;
	}
	
	protected HttpClient createHttpClient() {
		DefaultHttpClient client = new DefaultHttpClient();
		//...
		return client;
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
	
	protected HttpResponse execute(HttpRequestBase method) {
		try {
			method.setHeader(new BasicHeader("Accept", "application/json"));
			HttpResponse response = getHttpClient().execute(method);
			checkResponse(method, response);
			return response;
		} catch (RuntimeException rtex) {
			throw rtex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	protected void checkResponse(HttpRequest request, HttpResponse response) {
		StatusLine status = response.getStatusLine();
		int statusCode = status.getStatusCode();
		if (statusCode == 404) {
			throw new NotFoundException(status.getReasonPhrase());
		} else if ( (statusCode < 200) || (statusCode > 299) ) {
			StringBuilder msg = new StringBuilder();
			msg.append("statusCode=" + statusCode);
			msg.append("\n");
			msg.append("method=" + request.getRequestLine().getMethod());
			msg.append("\n");
			msg.append(request.getRequestLine().getUri());
			HttpEntity responseEntity = response.getEntity();
			try {
				String responseBody = EntityUtils.toString(responseEntity);
				msg.append("\n");
				msg.append("responseBody=" + responseBody);
			} catch (Exception ignored) {
				// ignored
			}
			throw new RuntimeException("unexpected response\n" + msg);
		}
	}
	
	protected MessageResult fromJson(final Object json) {
		if (null == json) {
			return null;
		}
		
		return MessageResult.fromValue(String.valueOf(json));
	}
	
	private HttpPost createHttpPost(final String path) {
		String url = getUrlForPath(path);
		HttpPost post = new HttpPost(url);
//		post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
//		post.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
//		post.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		return post;
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
	
	public CallbackMessage getCallbackMessage() {
		//unimplements
		return null;
	}
	
	public HttpEntity toEntity(MessageParams message) {
		String md5SecretKey = StringUtils.toMD5(this.getPassword()); 
		String input = this.getUsername() + message.getSendNo() + message.getReceiverType() + message.getReceiverValue() + md5SecretKey;
		int msgType = 0;
		if (message instanceof NotifyMessageParams) {
			msgType = MsgTypeEnum.NOTIFY.value();
		} else if (message instanceof CustomMessageParams)  {
			msgType = MsgTypeEnum.CUSTOM.value();
		}
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", this.getUsername()));
		nvps.add(new BasicNameValuePair("sendno", String.valueOf(message.getSendNo())));
		nvps.add(new BasicNameValuePair("appkeys", message.getAppKeys()));
		nvps.add(new BasicNameValuePair("receiver_type", message.getReceiverType()));
		if (!"".equals(message.getReceiverValue())) {
			nvps.add(new BasicNameValuePair("receiver_value", message.getReceiverValue()));
		}
		nvps.add(new BasicNameValuePair("verification_code", StringUtils.toMD5(input)));
		nvps.add(new BasicNameValuePair("msg_type", String.valueOf(msgType)));
		nvps.add(new BasicNameValuePair("msg_content", message.getMsgContent().toString()));
		nvps.add(new BasicNameValuePair("send_description", message.getSendDescription()));
		nvps.add(new BasicNameValuePair("callback_url", this.getCallbackUrl()));
		nvps.add(new BasicNameValuePair("platform", message.getPlatform()));
		
		try {
			return new UrlEncodedFormEntity(nvps, CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void shutdown() {
		if (this.getHttpClient() != null) {
			try {
				this.getHttpClient().getConnectionManager().shutdown();
			} catch (Exception ignored) {
				// ignored
			}
		}
	}
	
	public void setConnectionTimeout(int milliseconds) {
		getHttpClient().getParams().setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, milliseconds);
	}

	public void setSocketTimeout(int milliseconds) {
		getHttpClient().getParams().setIntParameter(AllClientPNames.SO_TIMEOUT, milliseconds);
	}
}
