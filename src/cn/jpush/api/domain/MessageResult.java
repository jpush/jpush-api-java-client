package cn.jpush.api.domain;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/*
 * 发送消息立即返回的状态定义
 */
public class MessageResult {
	//发送序号
	private int sendno = -1;
	//错误码，详见ErrorCodeEnum
	private int errcode;
	//错误消息
	private String errmsg;
	
	public int getSendno() {
		return sendno;
	}
	public void setSendno(int sendno) {
		this.sendno = sendno;
	}
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	public static MessageResult fromValue(String result) {
		MessageResult messageResult = null;
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = new ObjectMapper().readValue(result.toString(), Map.class);
			messageResult = new MessageResult();
			if (null != map.get("sendno")) {
				messageResult.setSendno(Integer.parseInt(String.valueOf(map.get("sendno"))));
			}
			if (null != map.get("errcode")) {
				messageResult.setErrcode(Integer.parseInt(String.valueOf(map.get("errcode"))));
			}
			if (null != map.get("errmsg")) {
				messageResult.setErrmsg(String.valueOf(map.get("errmsg")));
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messageResult;
	}
}
