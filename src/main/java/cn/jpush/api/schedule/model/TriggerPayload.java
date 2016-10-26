package cn.jpush.api.schedule.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.jiguang.common.TimeUnit;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.utils.StringUtils;
import cn.jiguang.common.utils.TimeUtils;


public class TriggerPayload implements IModel {

    private static Gson gson = new Gson();

    private Type type;

    private String start;
    private String end;
    private String time;
    private TimeUnit time_unit;
    private int frequency;
    private String[] point;

    private TriggerPayload(String time) {
        this.type = Type.single;
        this.time = time;
    }

    private TriggerPayload(String start, String end, String time, TimeUnit time_unit, int frequency, String[] point) {
        this.type = Type.periodical;
        this.start = start;
        this.end = end;
        this.time = time;
        this.time_unit = time_unit;
        this.frequency = frequency;
        this.point = point;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return gson.toJson(toJSON());
    }

    @Override
    public JsonElement toJSON() {
        JsonObject json = new JsonObject();
        switch (type) {
            case single:
                JsonObject s = new JsonObject();
                s.addProperty("time", time);
                json.add(Type.single.name(), s);
                break;
            case periodical:
                JsonObject p = new JsonObject();
                p.addProperty("start", start);
                p.addProperty("end", end);
                p.addProperty("time", time);
                p.addProperty("time_unit", time_unit.name().toLowerCase());
                p.addProperty("frequency", frequency);
                if( !TimeUnit.DAY.equals(time_unit) ) {
                    JsonArray array = new JsonArray();
                    for (String aPoint : point) {
                        array.add(new JsonPrimitive(aPoint));
                    }
                    p.add("point", array);
                }
                json.add(Type.periodical.name(), p);
                break;
            default:
                // nothing
        }
        return json;
    }

    public static enum Type {
        single, periodical
    }

    public static class Builder{

        private String start;
        private String end;
        private String time;
        private TimeUnit time_unit;
        private int frequency;
        private String[] point;

        /**
         * Setup time for single trigger.
         * @param time The execute time, format yyyy-MM-dd HH:mm:ss
         * @return this Builder
         */
        public Builder setSingleTime(String time) {
            this.time = time;
            return this;
        }


        /**
         * Setup period for periodical trigger.
         * @param start The start time, format yyyy-MM-dd HH:mm:ss
         * @param end The end time, format yyyy-MM-dd HH:mm:ss
         * @param time The execute time, format HH:mm:ss
         * @return this Builder
         */
        public Builder setPeriodTime(String start, String end, String time) {
            this.start = start;
            this.end = end;
            this.time = time;
            return this;
        }

        /**
         * Setup frequency for periodical trigger.
         * @param time_unit The time unit, can be day, week or month.
         * @param frequency The frequency cooperate with time unit, must between 1 and 100.
         * @param point The time point cooperate with time unit.
         *              If time unit is day, the point should be null.
         *              If time unit is week, should be the abbreviation of the days. eg. {"MON", "TUE"}
         *              If time unit is month, should be the date of the days. eg. {"01", "03"}
         * @return this Builder
         */
        public Builder setTimeFrequency(TimeUnit time_unit, int frequency, String[] point) {
            this.time_unit = time_unit;
            this.frequency = frequency;
            this.point = point;
            return this;
        }

        public TriggerPayload buildSingle() {
            Preconditions.checkArgument(StringUtils.isNotEmpty(time), "The time must not be empty.");
            Preconditions.checkArgument(TimeUtils.isDateFormat(time), "The time format is incorrect.");
            return new TriggerPayload(time);
        }

        public TriggerPayload buildPeriodical() {
            Preconditions.checkArgument(StringUtils.isNotEmpty(start), "The start must not be empty.");
            Preconditions.checkArgument(StringUtils.isNotEmpty(end), "The end must not be empty.");
            Preconditions.checkArgument(StringUtils.isNotEmpty(time), "The time must not be empty.");

            Preconditions.checkArgument(TimeUtils.isDateFormat(start), "The start format is incorrect.");
            Preconditions.checkArgument(TimeUtils.isDateFormat(end), "The end format is incorrect.");
            Preconditions.checkArgument(TimeUtils.isTimeFormat(time), "The time format is incorrect.");

            Preconditions.checkNotNull(time_unit, "The time_unit must not be null.");
            Preconditions.checkArgument(isTimeUnitOk(time_unit), "The time unit must be DAY, WEEK or MONTH.");

            Preconditions.checkArgument(frequency > 0 && frequency < 101, "The frequency must be a int between 1 and 100.");

            return new TriggerPayload(start, end, time, time_unit, frequency, point);
        }

        private boolean isTimeUnitOk(TimeUnit timeUnit) {
            switch (timeUnit) {
                case HOUR:
                    return false;
                case DAY:
                case WEEK:
                case MONTH:
                    return true;
                default:
                    return false;
            }
        }

    }

}
