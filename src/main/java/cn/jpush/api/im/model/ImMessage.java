package cn.jpush.api.im.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public abstract class ImMessage {
    protected static final Logger LOG = LoggerFactory.getLogger(ImMessage.class);

	private static final String KEY_MSG_TYPE = "msg_type";
	private static final int CURRENT_VERSION = 1;
	
	protected static Gson _gson = new Gson();
	protected static JsonParser _jsonParser = new JsonParser();

	protected int version;
	protected String targetType;
	protected String targetId;
	protected String targetName;
	protected String fromType;
	protected String fromId;
	protected String fromName;
	protected int createTime;
	protected String extras;
	protected MsgType msgType;
	
	
	protected ImMessage(String targetType, String targetId, String targetName,
			String fromType, String fromId, String fromName,
			MsgType msgType, String extras) {
		
		this.version = CURRENT_VERSION;
		this.createTime = (int) (System.currentTimeMillis() / 1000);
		
		this.targetType = targetType;
		this.targetId = targetId;
		this.targetName = targetName;
		
		this.fromType = fromType;
		this.fromId = fromId;
		this.fromName = fromName;
		
		this.msgType = msgType;
		this.extras = extras;
	}

	public String toJson() {
		return _gson.toJson(this);
	}
	
	
	public static ImMessage fromJson(String json) throws Exception {
		MsgType type = null;
		JsonElement root = null;
		try {
			root = _jsonParser.parse(json);
			if (!root.isJsonObject()) {
				throw new Exception("The msg json root should be a JsonObject.");
			}
			JsonObject rootOjbect = root.getAsJsonObject();
			Object typeObject = rootOjbect.get(KEY_MSG_TYPE);
			if (null == typeObject) {
				throw new Exception("Invalid IM msg json - msg_type is required.");
			}
			
			String typeString = rootOjbect.get(KEY_MSG_TYPE).getAsString();
			type = MsgType.valueOf(typeString);
			if (null == type) {
				throw new Exception("Invalid IM message - unknown msg_type - " + typeString);
			}
			
		} catch (JsonSyntaxException e) {
			throw new Exception("Invalid json");
		}
		
		switch (type) {
			case text:
				return _gson.fromJson(root, TextMessage.class);
			case voice:
				return _gson.fromJson(root, VoiceMessage.class);
			case image:
				return _gson.fromJson(root, ImageMessage.class);
			default:
				new Exception("Unknown IM message type.");
				return null;
		}
	}
	
	
	protected abstract static class Builder<T extends ImMessage, B extends Builder<T, B>> {
		private B theBuilder;
		
		protected String targetType;
		protected String targetId;
		protected String targetName;
		protected String fromType;
		protected String fromId;
		protected String fromName;
		protected String extras;
		protected MsgType msgType;
		
		public Builder() {
			this.theBuilder = getThis();
		}
		
		public B setTarget(String type, String id, String name) {
			this.targetType = type;
			this.targetId = id;
			this.targetName = name;
			return theBuilder;
		}
		
		public B setFrom(String type, String id, String name) {
			this.fromType = type;
			this.fromName = name;
			this.fromId = id;
			return theBuilder;
		}
		
		public B setExtras(String extras) {
			this.extras = extras;
			return theBuilder;
		}		
		
		public abstract T build();
		protected abstract B getThis();
	}
	
	
	

	
	public enum MsgType {
		text, 
		voice,
		image
	}
	
}
