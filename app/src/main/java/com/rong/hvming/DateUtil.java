package com.rong.hvming;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String SOURCEFORMAT6 = "yyyy-MM-dd";

    public static final String SOURCEFORMAT7 = "HH:mm:ss";

    public static Date parse(String source, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(source);
        } catch (Exception e) {
            try {
                return new Date(source);
            } catch (Exception e2) {
                return null;
            }
        }
    }

    public static String format(Date date, String format) {
        DateFormat sf = new SimpleDateFormat(format);
        try {
            return sf.format(date);
        } catch (Exception e) {
            return "";
        }
    }
}
