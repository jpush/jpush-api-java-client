package cn.jpush.api.im.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseMessage {
    protected static final Logger LOG = LoggerFactory.getLogger(BaseMessage.class);
	
    protected static Gson _gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	protected MsgType msgType;
	
	protected BaseMessage(MsgType msgType) {
		this.msgType = msgType;
	}

	public String toJson() {
		return _gson.toJson(this);
	}
	
	
	public static enum MsgType {
		text, 
		voice,
		image
	}
	
}
