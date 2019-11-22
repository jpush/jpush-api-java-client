package cn.jpush.api.push.model;

import cn.jiguang.common.utils.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 回执功能支持
 */
public class Callback implements PushModel {
    /**
     * 回执地址
     */
    private String url;
    /**
     * 回执数据类型: 1-送达回执, 2-点击回执，3-送达和点击回执
     */
    private int type;
    /**
     * 自定义参数
     */
    private Map<String, String> params;

    private Callback(String url, int type, Map<String, String> params) {
        this.url = url;
        this.type = type;
        this.params = params;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        if (type > 0) {
            json.add("type", new JsonPrimitive(type));
        }
        if (null != url) {
            json.add("url", new JsonPrimitive(url));
        }

        if (null != params && params.size() > 0) {
            JsonObject paramObj = new JsonObject();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramObj.add(entry.getKey(), new JsonPrimitive(entry.getValue()));
            }
            json.add("params", paramObj);
        }

        return json;
    }


    public static class Builder {
        private String url;
        private int type;
        private Map<String, String> params;

        public Callback.Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Callback.Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Callback.Builder addParam(String key, String value) {
            Preconditions.checkArgument(!(null == key), "Key should not be null.");
            if (null == params) {
                params = new LinkedHashMap<>();
            }
            this.params.put(key, value);
            return this;
        }

        public Callback.Builder addParams(Map<String, String> params) {
            if (null == this.params) {
                this.params = new LinkedHashMap<>();
            }
            this.params.putAll(params);
            return this;
        }

        public Callback build() {
            Preconditions.checkArgument(type >= 0, "type should be greater than 0.");
            return new Callback(url, type, params);
        }
    }
}
