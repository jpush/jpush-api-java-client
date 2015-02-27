package cn.jpush.api.im.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.im.model.BaseMessage.MsgType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Message container
 *
 */
public class ImMessage {
    protected static final Logger LOG = LoggerFactory.getLogger(ImMessage.class);

	private static final int CURRENT_VERSION = 1;
	
	private static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	@Expose
	private int version;
	
	@Expose
	@SerializedName("target_type")
	private String targetType;
	
	@Expose
	@SerializedName("target_id")
	private String targetId;
	
	@Expose
	@SerializedName("target_name")
	private String targetName;
	
	@Expose 
	@SerializedName("from_type")
	private String fromType;
	
	@Expose
	@SerializedName("from_id")
	private String fromId;
	
	@Expose
	@SerializedName("from_name")
	private String fromName;
	
	@Expose
	@SerializedName("create_time")
	private int createTime;
	
	@Expose 
	private String extras;
	
	@Expose
	@SerializedName("msg_type")
	private MsgType msgType;
	
	@Expose
	@SerializedName("msg_body")
	private JsonElement msgBody;
	
	private BaseMessage baseMessage;
	
	protected ImMessage(String targetType, String targetId, String targetName,
			String fromType, String fromId, String fromName,
			String extras, BaseMessage message) {
		
		this.version = CURRENT_VERSION;
		this.createTime = (int) (System.currentTimeMillis() / 1000);
		
		this.targetType = targetType;
		this.targetId = targetId;
		this.targetName = targetName;
		
		this.fromType = fromType;
		this.fromId = fromId;
		this.fromName = fromName;
		
		this.extras = extras;
		
		this.msgType = message.msgType;
		this.baseMessage = message;
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}

	public String toJson() {
		this.msgBody = _gson.toJsonTree(baseMessage);
		return _gson.toJson(this);
	}
	
	
	public static ImMessage fromJson(String json) throws Exception {
		ImMessage imMessage = null;
		try {
			imMessage = _gson.fromJson(json, ImMessage.class);
		} catch (JsonSyntaxException e) {
			throw new Exception("Not a valid json.");
		}
		
		MsgType type = imMessage.msgType;
		JsonElement msgBody = imMessage.msgBody;
		
		if (null == type || null == msgBody) {
			throw new Exception("msgType and msgBody should not be null.");
		}
		
		// maybe add more param check
		
		BaseMessage baseMessage = null;
		
		switch (type) {
			case text:
				baseMessage = _gson.fromJson(msgBody, TextMessage.class);
				break;
				
			case voice:
				baseMessage = _gson.fromJson(msgBody, VoiceMessage.class);
				break;
				
			case image:
				baseMessage = _gson.fromJson(msgBody, ImageMessage.class);
				break;
				
			default:
				new Exception("Unknown IM message type.");
		}
		
		imMessage.baseMessage = baseMessage;
		return imMessage;
	}
	
	
	public static class Builder {
		
		private String targetType;
		private String targetId;
		private String targetName;
		private String fromType;
		private String fromId;
		private String fromName;
		private String extras;
		
		private MsgType msgType;
		private BaseMessage baseMessage;

		public Builder setTarget(String type, String id, String name) {
			this.targetType = type;
			this.targetId = id;
			this.targetName = name;
			return this;
		}
		
		public Builder setFrom(String type, String id, String name) {
			this.fromType = type;
			this.fromId = id;
			this.fromName = name;
			return this;
		}
		
		public Builder setExtras(String extras) {
			this.extras = extras;
			return this;
		}
		
		public Builder setMessage(BaseMessage message) {
			this.baseMessage = message;
			return this;
		}
		
		public ImMessage build() {
			return new ImMessage(targetType, targetId, targetName, 
					fromType, fromId, fromName, 
					extras, baseMessage);
		}
	}
	
}
