package cn.jpush.api.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.utils.Base64;
import cn.jpush.api.utils.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseHttpClient {
    private static final Logger LOG = LoggerFactory.getLogger(BaseHttpClient.class);
    
	private static final String CHARSET = "UTF-8";
	private static final String RATE_LIMIT_QUOTA = "X-Rate-Limit-Limit";
	private static final String RATE_LIMIT_Remaining = "X-Rate-Limit-Remaining";
	private static final String RATE_LIMIT_Reset = "X-Rate-Limit-Reset";
	
	protected static final int RESPONSE_OK = 200;
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	//设置连接超时时间
	private final int DEFAULT_CONNECTION_TIMEOUT = (20 * 1000); // milliseconds
	
	//设置读取超时时间
	private final int DEFAULT_SOCKET_TIMEOUT = (30 * 1000); // milliseconds


    protected ResponseResult sendGet(String url, String params, String authCode) {
		return sendRequest(url, params, "GET", authCode);
	}
    
    protected ResponseResult sendPost(String url, String content, String authCode) {
		return sendRequest(url, content, "POST", authCode);
	}
    
    protected ResponseResult sendRequest(String url, String content, String method, String authCode) {
        LOG.debug("Send request to - " + url + ", with content - " + content);
		HttpURLConnection conn = null;
		OutputStream out = null;
		StringBuffer sb = new StringBuffer();
		ResponseResult result = new ResponseResult();
		
		try {
		    initSSL();
			
			URL aUrl = new URL(url);
			conn = (HttpURLConnection) aUrl.openConnection();
			conn.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
			conn.setReadTimeout(DEFAULT_SOCKET_TIMEOUT);
			conn.setUseCaches(false);
			conn.setRequestMethod(method);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset", CHARSET);
			conn.setRequestProperty("Charset", CHARSET);
            if (!StringUtils.isEmpty(authCode)) {
				conn.setRequestProperty("Authorization", authCode);
			}
            
            if (method.equals("POST")) {
                conn.setDoOutput(true);     //POST Request
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                byte[] data = content.getBytes(CHARSET);
				conn.setRequestProperty("Content-Length", String.valueOf(data.length));
	            out = conn.getOutputStream();
				out.write(data);
	            out.flush();
			} else {
	            conn.setDoOutput(false);
			}
            
            InputStream in = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, CHARSET);
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) > 0) {
                sb.append(buff, 0, len);
            }
            
            int status = conn.getResponseCode();
            String responseContent = sb.toString();
            result.responseCode = status;
            result.responseContent = responseContent;
            
            String quota = conn.getHeaderField(RATE_LIMIT_QUOTA);
            String remaining = conn.getHeaderField(RATE_LIMIT_Remaining);
            String reset = conn.getHeaderField(RATE_LIMIT_Reset);
            result.setRateLimit(quota, remaining, reset);
            
            if (status == 200) {
				LOG.debug("Succeed to get response - 200 OK");
				
			} else {
			    LOG.warn("Got error response - responseCode:" + status + ", responseContent:" + responseContent);
			    
			    switch (status) {
			    case 400:
			        LOG.warn("Your request params is invalid. Please check them according to docs.");
	                result.setErrorObject();
			        break;
			    case 403:
			        LOG.warn("Request is forbidden! Maybe your appkey is listed in blacklist?");
	                result.setErrorObject();
			        break;
			    case 401:
			        LOG.warn("Authentication failed! Please check authentication params according to docs.");
	                result.setErrorObject();
			        break;
			    case 429:
			        LOG.warn("Too many requests! Please review your appkey's request quota.");
	                result.setErrorObject();
			        break;
			    case 500:
			        LOG.warn("Seems encountered server error. Please retry later.");
			        break;
			    default:
			    }
				return result;
			}

		} catch (SocketTimeoutException e) {
		    result.exceptionString = e.getMessage();
		    LOG.error("Request timeout. Retry later.", e);
		} catch (ConnectException e) {
		    result.exceptionString = e.getMessage();
		    LOG.error("Connnect error. ", e);
		} catch (UnknownHostException e) {
		    result.exceptionString = e.getMessage();
		    LOG.error("Unknown host. Please check the DNS configuration of your server.", e);
        } catch (IOException e) {
            result.exceptionString = e.getMessage();
            LOG.error("IO error. ", e);
		} catch (Exception e) {
		    result.exceptionString = e.getMessage();
		    LOG.error("Unknown exception. ", e);
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					LOG.error("Failed to close stream.", e);
				}
			}
			if (null != conn) {
				conn.disconnect();
			}
		}
		
		return result;
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
			LOG.error("", e);
		}
	}
	
    public static String getAuthorizationBase64(String appKey, String masterSecret) {
        String encodeKey = appKey + ":" + masterSecret;
        return String.valueOf(Base64.encode(encodeKey.getBytes())); 
    }
    
    private final static Pattern PUSH_PATTERNS = Pattern.compile("[^a-zA-Z0-9]");
    
    public static void checkBasic(String appKey, String masterSecret) {
        if (StringUtils.isEmpty(appKey)
                || StringUtils.isEmpty(masterSecret)) {
            throw new IllegalArgumentException("appKey and masterSecret are both required.");
        }
        if (appKey.length() != 24 
                || masterSecret.length() != 24
                || PUSH_PATTERNS.matcher(appKey).find()
                || PUSH_PATTERNS.matcher(masterSecret).find()) {
            throw new IllegalArgumentException("appKey and masterSecret format is incorrect. "
                    + "They should be 24 size, and be composed with alphabet and numbers. "
                    + "Please confirm that they are coming from JPush Web Portal.");
        }
    }


	public class SimpleHostnameVerifier implements HostnameVerifier {

	    @Override
	    public boolean verify(String hostname, SSLSession session) {
	        return true;
	    }

	}

	public class SimpleTrustManager implements TrustManager, X509TrustManager {

	    @Override
	    public void checkClientTrusted(X509Certificate[] chain, String authType)
	            throws CertificateException {
	        return;
	    }

	    @Override
	    public void checkServerTrusted(X509Certificate[] chain, String authType)
	            throws CertificateException {
	        return;
	    }

	    @Override
	    public X509Certificate[] getAcceptedIssuers() {
	        return null;
	    }
	}

}
