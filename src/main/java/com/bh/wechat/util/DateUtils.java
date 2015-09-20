package com.bh.wechat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {

    private static String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";

    public static String getDatePattern() {
        return defaultDatePattern;
    }

    public static String getToday() {
        Date today = new Date();
        return format(today);
    }

    public static String format(Date date) {
        return date == null ? "" : format(date, getDatePattern());
    }

    public static String format(Date date, String pattern) {
        return date == null ? "" : new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return StringUtils.isBlank(strDate) ? null : parse(strDate, getDatePattern());
    }

    public static Date parse(String strDate, String pattern) throws ParseException {
        return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
    }

    public static String format(String strDate, String pattern) {
        if (StringUtils.isNotBlank(strDate)) {
            try {
                Date date = parse(strDate, pattern);
                strDate = format(date);
            } catch (ParseException e) {
                return "";
            }
        }

        return strDate;
    }
}
