package cn.jpush.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import cn.jpush.http.Base64;


public class StringUtils {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String toMD5(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString;
	}
	
	public static String encodeParam(String param) {
		String encodeParam = null;
		try {
			encodeParam = URLEncoder.encode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeParam;
	}
	
	public static String arrayToString(String[] vlaues){
		StringBuffer buffer = new StringBuffer(vlaues.length);
		if(vlaues != null){
			for(int i = 0; i<vlaues.length; i++){
				buffer.append(vlaues[i]).append(",");
			}
		}
		if(buffer.length() > 0)
			return buffer.toString().substring(0,buffer.length()-1);
		return "";
	}

	// Authorization base64 code for receive api
	public static String getAuthorizationBase64(String appKey,String  masterSecret){
		String encodeKey = appKey +":"+masterSecret;
		return String.valueOf(Base64.encode(encodeKey.getBytes())); 
	}
}
