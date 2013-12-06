package cn.jpush.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.TrustManager;

import cn.jpush.api.CustomMessageParams;
import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.MessageParams;
import cn.jpush.api.MessageResult;
import cn.jpush.api.MsgTypeEnum;
import cn.jpush.api.NotifyMessageParams;
import cn.jpush.api.SimpleHostnameVerifier;
import cn.jpush.api.SimpleTrustManager;
import cn.jpush.api.ValidateRequestParams;

import com.google.gson.Gson;

public class HttpClient {
	private static Logger logger = Logger.getLogger("HttpClient");
	private final String CHARSET = "UTF-8";

	//设置连接超时时间
	private final int DEFAULT_CONNECTION_TIMEOUT = (20 * 1000); // milliseconds
	//设置读取超时时间
	private final int DEFAULT_SOCKET_TIMEOUT = (30 * 1000); // milliseconds

	private static Gson gson = new Gson();

	public MessageResult sendPush(final String path, final boolean enableSSL, final MessageParams messageParams) {
		MessageResult messageResult = ValidateRequestParams.vidateParams(messageParams);
		if(messageResult != null) return messageResult;

		String pushResult = sendPost(path, enableSSL, parse(messageParams),RequestTypeEnum.PUSH.value(),null);
		return gson.fromJson(pushResult, MessageResult.class);
	}

	public String sendReceived(String path, final boolean enabledSSL, String params,String authCode){	
		return sendGet(path+="?msg_ids="+params, enabledSSL, null, RequestTypeEnum.RECEIVE.value(),authCode);
	}


	private String sendGet(String path,boolean enabledSSL,String params,Integer reqeustType,String authCode){

		return sendRequest(path, enabledSSL, params, "GET", reqeustType,authCode);
	}

	private String sendPost( String path, final boolean enableSSL, String params,Integer reqeustType,String authCode){
		return sendRequest(path, enableSSL, params, "POST", reqeustType,authCode);
	}

	private String  sendRequest(String path, final boolean enableSSL, String params,String method,Integer reqeustType,String authCode){
		HttpURLConnection conn = null;
		DataOutputStream outStream = null;
		StringBuffer sb = new StringBuffer();

		try {
			if (enableSSL) {
				initSSL();
			}			

			URL url = new URL(BaseURL.getUrlForPath(path,enableSSL,reqeustType));
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
			conn.setReadTimeout(DEFAULT_SOCKET_TIMEOUT);
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", CHARSET);
			if(authCode != null && !authCode.isEmpty()){
				conn.setRequestProperty("Authorization", authCode);
			}
		
			if(method.equals("POST")){
				byte[] data = params.getBytes(CHARSET);
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", String.valueOf(data.length));
				outStream = new DataOutputStream(conn.getOutputStream());
				outStream.write(data);
				outStream.flush();
			}

			if (conn.getResponseCode() == 200) {
				logger.info("Congratulations!The request was successful. response status is 200");
				InputStream in = conn.getInputStream();
				InputStreamReader reader = new InputStreamReader(in, CHARSET);
				char[] buff = new char[1024];
				int len;
				while ((len = reader.read(buff)) > 0) {
					sb.append(buff, 0, len);
				}
			} else {		
				logger.log(Level.WARNING,"Sorry!The request was fault. response " +
							"status = "+conn.getResponseCode()+",errormsg = "+conn.getHeaderField(0));
				
				sb.append("{response_status:").append(conn.getResponseCode())
				.append(",errmsg:");
				if(reqeustType == RequestTypeEnum.RECEIVE.value()){
					String errmsg = ErrorCodeEnum.errorMsg(conn.getResponseCode());
					errmsg = errmsg == null ? conn.getHeaderField(0) : errmsg;
					sb.append("\""+errmsg+"\"");
				}else{
					sb.append("\""+conn.getHeaderField(0)+"\"");
				}
				sb.append("}");				
			}

		}catch (IOException e) {
			logger.log(Level.SEVERE,"God! the server throw exception." +
						"please check it out the error message:"+e.getMessage());
			
			StringBuilder codesb = new StringBuilder("{response_status:");
			if ( e instanceof SocketTimeoutException) {
				codesb.append(ErrorCodeEnum.CONNECTIONTIMEOUT.value())
				.append(",errmsg:").append("\""+e.getMessage().toString()+"\"")
				.append("}");
				return codesb.toString();
			}
			
			if(e instanceof ConnectException){
				codesb.append(ErrorCodeEnum.CONNECTIONREFUSED.value())
				.append(",errmsg:").append("\""+e.getMessage().toString()+"\"")
				.append("}");
				
				return codesb.toString();
			}
			
			if(e instanceof UnknownHostException){
				codesb.append(ErrorCodeEnum.UnknownHostException.value())
				.append(",errmsg:").append("\""+e.getMessage().toString()+"\"")
				.append("}");
				
				return codesb.toString();
			}

			if (conn != null) {
				try {
					codesb.append(conn.getResponseCode()).append("}");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			codesb.append("}");
			return codesb.toString();

		}catch (Exception ex) {
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
		return sb.toString();
	}



	protected void initSSL() {
		try {
			TrustManager[] tmCerts = new javax.net.ssl.TrustManager[1];
			tmCerts[0] = new SimpleTrustManager();
			javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
			sc.init(null, tmCerts, null);
			javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HostnameVerifier hv = new SimpleHostnameVerifier();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String parse(MessageParams message) { 
		String input = String.valueOf(message.getSendNo()) + message.getReceiverType().value() + message.getReceiverValue() + message.getMasterSecret();
		int msgType = 0;
		if (message instanceof NotifyMessageParams) {
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
		//nvPair.put("send_description", message.getSendDescription());
		nvPair.put("platform", message.getPlatform());
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

}
