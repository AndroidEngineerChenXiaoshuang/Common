package com.jixingmao.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }


    public static int getCurrentDateOfInt() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return getDateOfInt(year, month, day);
    }

    public static int getDateOfInt(int year, int month, int day) {
        String m, d;

        if (month < 10) {
            m = "0" + month;
        } else {
            m = "" + month;
        }

        if (day < 10) {
            d = "0" + day;
        } else {
            d = "" + day;
        }

        String curYear = year + m + d;

        int resultValue = 0;
        try {
            resultValue = Integer.parseInt(curYear);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return resultValue;
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getCurrentDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH) + 1;
    }
}
