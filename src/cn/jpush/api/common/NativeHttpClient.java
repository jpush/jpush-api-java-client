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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NativeHttpClient implements IHttpClient {
    private static final Logger LOG = LoggerFactory.getLogger(NativeHttpClient.class);
    
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


    public ResponseWrapper sendGet(String url, String params, String authCode) {
		return sendRequest(url, params, METHOD_GET, authCode);
	}
    
    public ResponseWrapper sendPost(String url, String content, String authCode) {
		return sendRequest(url, content, METHOD_POST, authCode);
	}
    
    public ResponseWrapper sendRequest(String url, String content, String method, String authCode) {
        LOG.debug("Send request to - " + url + ", with content - " + content);
		HttpURLConnection conn = null;
		OutputStream out = null;
		StringBuffer sb = new StringBuffer();
		ResponseWrapper wrapper = new ResponseWrapper();
		
		try {
		    initSSL();
			
			URL aUrl = new URL(url);
			conn = (HttpURLConnection) aUrl.openConnection();
			conn.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
			conn.setReadTimeout(DEFAULT_SOCKET_TIMEOUT);
			conn.setUseCaches(false);
			conn.setRequestMethod(method);
			conn.setRequestProperty("User-Agent", JPUSH_USER_AGENT);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset", CHARSET);
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("Authorization", authCode);
            
            if (METHOD_POST.equals(method)) {
                conn.setDoOutput(true);     //POST Request
				conn.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
                byte[] data = content.getBytes(CHARSET);
				conn.setRequestProperty("Content-Length", String.valueOf(data.length));
	            out = conn.getOutputStream();
				out.write(data);
	            out.flush();
			} else {
	            conn.setDoOutput(false);
			}
            
            int status = conn.getResponseCode();
            InputStream in = null;
            if (status == 200) {
                in = conn.getInputStream();
            } else {
                in = conn.getErrorStream();
            }
            InputStreamReader reader = new InputStreamReader(in, CHARSET);
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) > 0) {
                sb.append(buff, 0, len);
            }
            
            String responseContent = sb.toString();
            wrapper.responseCode = status;
            wrapper.responseContent = responseContent;
            
            String quota = conn.getHeaderField(RATE_LIMIT_QUOTA);
            String remaining = conn.getHeaderField(RATE_LIMIT_Remaining);
            String reset = conn.getHeaderField(RATE_LIMIT_Reset);
            wrapper.setRateLimit(quota, remaining, reset);
            
            if (status == 200) {
				LOG.debug("Succeed to get response - 200 OK");
				LOG.debug(responseContent);
				
			} else {
			    LOG.info("Got error response - responseCode:" + status + ", responseContent:" + responseContent);
			    
			    switch (status) {
			    case 400:
			        LOG.error("Your request params is invalid. Please check them according to error message.");
	                wrapper.setErrorObject();
			        break;
			    case 403:
			        LOG.error("Request is forbidden! Maybe your appkey is listed in blacklist?");
	                wrapper.setErrorObject();
			        break;
			    case 401:
			        LOG.error("Authentication failed! Please check authentication params according to docs.");
	                wrapper.setErrorObject();
			        break;
			    case 429:
			        LOG.error("Too many requests! Please review your appkey's request quota.");
	                wrapper.setErrorObject();
			        break;
			    case 500:
			        LOG.error("Seems encountered server error. Please retry later.");
			        break;
			    default:
                    LOG.error("Unexpected response.");
			    }
				return wrapper;
			}

		} catch (SocketTimeoutException e) {
		    wrapper.exceptionString = e.getMessage();
		    LOG.error("Request timeout. Retry later.", e);
		} catch (ConnectException e) {
		    wrapper.exceptionString = e.getMessage();
		    LOG.error("Connnect error. Retry later.", e);
		} catch (UnknownHostException e) {
		    wrapper.exceptionString = e.getMessage();
		    LOG.error("Unknown host. Please check the DNS configuration of your server.", e);
        } catch (IOException e) {
            wrapper.exceptionString = e.getMessage();
            LOG.error("IO error. Retry later.", e);
		} catch (Exception e) {
		    wrapper.exceptionString = e.getMessage();
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
		
		return wrapper;
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


	public static class SimpleHostnameVerifier implements HostnameVerifier {

	    @Override
	    public boolean verify(String hostname, SSLSession session) {
	        return true;
	    }

	}

	public static class SimpleTrustManager implements TrustManager, X509TrustManager {

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
