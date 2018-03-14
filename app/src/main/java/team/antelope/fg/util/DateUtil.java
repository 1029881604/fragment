package team.antelope.fg.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author：hwc
 * @Date：2017/12/5 16:06
 * @Desc: 格式化日期的工具类
 */

public class DateUtil {
    /*
    * 日期时间*/
    private static final SimpleDateFormat DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /*
    * 日期*/
    private static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat TIME = new SimpleDateFormat("HH:mm:ss");
    /*
    * 时间*/
    public static String formatDataTime(long date) {
        return DATETIME.format(new Date(date));
    }
    public static String formatDataTime2(Date date) {
        return DATETIME.format(date);
    }

    public static String formatDate(long date) {
        return DATE.format(new Date(date));
    }

    public static String formatTime(long date) {
        return TIME.format(new Date(date));
    }
    /**
     * @Description 字符串转为日期
     * @date 2017/12/5
     */
    public static Date string2Date(String s, String style) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        Date date = null;
        if (s == null || s.length() < 6) {
            return null;
        }
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
