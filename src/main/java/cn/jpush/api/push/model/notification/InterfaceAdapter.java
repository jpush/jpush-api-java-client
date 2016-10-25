package cn.jpush.api.push.model.notification;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Ken on 2016/10/25.
 */
public class InterfaceAdapter<T> implements JsonSerializer, JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement jsonElement, Type type,
                              JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObj = jsonElement.getAsJsonObject();
        String className = jsonObj.get("type").getAsString();
        try {
            Class<?> clz = Class.forName(className);
            return jsonDeserializationContext.deserialize(jsonElement, clz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public JsonElement serialize(Object object, Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        JsonElement jsonEle = jsonSerializationContext.serialize(object, object.getClass());
        jsonEle.getAsJsonObject().addProperty("type",
                object.getClass().getCanonicalName());
        return jsonEle;
    }
}
