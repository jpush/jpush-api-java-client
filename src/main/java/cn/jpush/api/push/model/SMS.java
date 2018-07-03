package cn.jpush.api.push.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.utils.Preconditions;

public class SMS implements PushModel {

    private final String content;
    private final int delay_time;
    private final long temp_id;
    private final Map<String, String> extras;
    private final Map<String, Number> numberExtras;
    private final Map<String, Boolean> booleanExtras;
    private final Map<String, JsonObject> jsonExtras;

    private SMS(String content, int delay_time, long temp_id, 
    		Map<String, String> extras, 
    		Map<String, Number> numberExtras,
    		Map<String, Boolean> booleanExtras,
    		Map<String, JsonObject> jsonExtras) {
        this.content = content;
        this.delay_time = delay_time;
        this.temp_id = temp_id;
        this.extras = extras;
        this.numberExtras = numberExtras;
        this.booleanExtras = booleanExtras;
        this.jsonExtras = jsonExtras;
        		
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * This will be removed in the future. Please use content(long tempId, int delayTime)  this constructor.
     * Create a SMS content with a delay time.
     * JPush will send a SMS if the message doesn't received within the delay time. If the delay time is 0, the SMS will be sent immediately.
     * Please note the delay time only works on Android.
     * If you are pushing to iOS, the SMS will be sent immediately, whether or not the delay time is 0.
     *
     * @param content The SMS content.
     * @param delayTime The seconds you want to delay, should be greater than or equal to 0.
     * @return SMS payload.
     */
    @Deprecated
    public static SMS content(String content, int delayTime) {
        return new Builder()
                .setContent(content)
                .setDelayTime(delayTime)
                .build();
    }
    
    public static SMS content(long tempId, int delayTime) {
        return new Builder()
                .setTempID(tempId)
                .setDelayTime(delayTime)
                .build();
    }
    

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        
        json.addProperty("delay_time", delay_time);
        
        if (temp_id > 0) {
        	json.addProperty("temp_id", temp_id);
        }
        
        if (null != content) {
        	json.addProperty("content", content);
        }

        
        JsonObject extrasObject = null;
        if (null != extras || null != numberExtras || null != booleanExtras) {
            extrasObject = new JsonObject();
        }
        
        if (null != extras) {
            for (String key : extras.keySet()) {
                if (extras.get(key) != null) {
                    extrasObject.add(key, new JsonPrimitive(extras.get(key)));
                } else {
                    extrasObject.add(key, JsonNull.INSTANCE);
                }
            }
        }
        if (null != numberExtras) {
            for (String key : numberExtras.keySet()) {
                extrasObject.add(key, new JsonPrimitive(numberExtras.get(key)));
            }
        }
        if (null != booleanExtras) {
            for (String key : booleanExtras.keySet()) {
                extrasObject.add(key, new JsonPrimitive(booleanExtras.get(key)));
            }
        }
        if (null != jsonExtras) {
            for (String key : jsonExtras.keySet()) {
                extrasObject.add(key, jsonExtras.get(key));
            }
        }

        if (null != extras || null != numberExtras || null != booleanExtras) {
            json.add("temp_para", extrasObject);
        }
        return json;
    }

    public static class Builder {
        private String content;
        private int delay_time;
        private long temp_id;
        private Map<String, String> extrasBuilder;
        private Map<String, Number> numberExtrasBuilder;
        private Map<String, Boolean> booleanExtrasBuilder;
        protected Map<String, JsonObject> jsonExtrasBuilder;

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setDelayTime(int delayTime) {
            this.delay_time = delayTime;
            return this;
        }
        
        public Builder setTempID(long tempID) {
            this.temp_id = tempID;
            return this;
        }
        
        public Builder addPara(String key, String value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            extrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addParas(Map<String, String> extras) {
            Preconditions.checkArgument(! (null == extras), "extras should not be null.");
            if (null == extrasBuilder) {
                extrasBuilder = new HashMap<String, String>();
            }
            for (String key : extras.keySet()) {
                extrasBuilder.put(key, extras.get(key));
            }
            return this;
        }
        
        public Builder addPara(String key, Number value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == numberExtrasBuilder) {
                numberExtrasBuilder = new HashMap<String, Number>();
            }
            numberExtrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addPara(String key, Boolean value) {
            Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == booleanExtrasBuilder) {
                booleanExtrasBuilder = new HashMap<String, Boolean>();
            }
            booleanExtrasBuilder.put(key, value);
            return this;
        }
        
        public Builder addPara(String key, JsonObject value) {
        	Preconditions.checkArgument(! (null == key || null == value), "Key/Value should not be null.");
            if (null == jsonExtrasBuilder) {
            	jsonExtrasBuilder = new HashMap<String, JsonObject>();
            }
            jsonExtrasBuilder.put(key, value);
            return this;
        }

        public SMS build() {
            Preconditions.checkArgument(delay_time >= 0, "The delay time must be greater than or equal to 0");

            return new SMS(content, delay_time, temp_id,
            		extrasBuilder, numberExtrasBuilder, booleanExtrasBuilder,jsonExtrasBuilder);
        }

    }
}
