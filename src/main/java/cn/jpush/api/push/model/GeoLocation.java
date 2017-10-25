package cn.jpush.api.push.model;

import cn.jiguang.common.utils.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GeoLocation implements PushModel {

    private final String TYPE = "type";
    private final String COORDINATES = "coordinates";
    private final String LT = "lt"; // left top
    private final String RB = "rb"; // right bottom
    private final String LAT = "lat";
    private final String LON = "lon";

    private String type;
    private String ltLat;
    private String ltLon;
    private String rbLat;
    private String rbLon;

    public GeoLocation(String type, String ltLat, String ltLon, String rbLat, String rbLon) {
        this.type = type;
        this.ltLat = ltLat;
        this.ltLon = ltLon;
        this.rbLat = rbLat;
        this.rbLon = rbLon;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String type;
        private String ltLat;
        private String ltLon;
        private String rbLat;
        private String rbLon;

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setLeftTopCoordinate(String latitude, String longitude) {
            this.ltLat = latitude;
            this.ltLon = longitude;
            return this;
        }

        public Builder setRightBottomCoordinate(String latitude, String longitude) {
            this.rbLat = latitude;
            this.rbLon = longitude;
            return this;
        }


        public GeoLocation build() {
            return new GeoLocation(type, ltLat, ltLon, rbLat, rbLon);
        }
    }

    @Override
    public JsonElement toJSON() {
        JsonObject jsonObject = new JsonObject();
        if (null != type) {
            jsonObject.addProperty(TYPE, type);
        }

        JsonObject coordinate = new JsonObject();
        JsonObject lt = new JsonObject();
        if (null != ltLat && null != ltLon) {
            lt.addProperty(LAT, ltLat);
            lt.addProperty(LON, ltLon);
        }
        coordinate.add(LT, lt);

        JsonObject rb = new JsonObject();
        if (null != rbLat && null != rbLon) {
            rb.addProperty(LAT, rbLat);
            rb.addProperty(LON, rbLon);
        }
        coordinate.add(RB, rb);
        jsonObject.add(COORDINATES, coordinate);
        return jsonObject;
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }
}
