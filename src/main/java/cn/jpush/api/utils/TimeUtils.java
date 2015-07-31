package cn.jpush.api.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat TIME_ONLY_FORMAT = new SimpleDateFormat("HH:mm:ss");

    static {
        DATE_FORMAT.setLenient(false);
        TIME_ONLY_FORMAT.setLenient(false);
    }

    public static boolean isDateFormat(String time) {
        try {
            DATE_FORMAT.parse(time);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isTimeFormat(String time) {
        try{
            TIME_ONLY_FORMAT.parse(time);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

}
