package cn.jpush.api.schedule.model;

import cn.jpush.api.FastTests;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * TriggerPayload Tester.
 *
 * @author liucy
 * @version 1.0
 */
@Category(FastTests.class)
public class TriggerPayloadTest {

    /**
     * Method: buildSingle()
     */
    @Test
    public void testBuildSingle() {
        String time = "2015-07-30 10:12:23";
        TriggerPayload trigger = TriggerPayload.newBuilder()
                .setSingleTime(time)
                .buildSingle();
        JsonObject json = new JsonObject();
        JsonObject single = new JsonObject();
        single.addProperty("time", time);
        json.add("single", single);
        Assert.assertEquals("", json, trigger.toJSON());
    }

    /**
     * Method: buildPeriodical()
     */
    @Test
    public void testBuildPeriodical() {
        String start = "2015-07-30 10:12:23";
        String end = "2015-08-30 10:12:23";
        String time = "10:12:00";
        String[] point = {"MON", "TUE"};

        TriggerPayload trigger = TriggerPayload.newBuilder()
                .setPeriodTime(start, end, time)
                .setTimeFrequency(TriggerPayload.TimeUnit.week, 2, point)
                .buildPeriodical();

        JsonObject json = new JsonObject();
        JsonObject periodical = new JsonObject();
        periodical.addProperty("start", start);
        periodical.addProperty("end", end);
        periodical.addProperty("time", time);
        periodical.addProperty("time_unit", TriggerPayload.TimeUnit.week.name());
        periodical.addProperty("frequency", 2);
        JsonArray array = new JsonArray();
        array.add(new JsonPrimitive("MON"));
        array.add(new JsonPrimitive("TUE"));
        periodical.add("point", array);
        json.add("periodical", periodical);

        Assert.assertEquals("", json, trigger.toJSON());

    }


} 
