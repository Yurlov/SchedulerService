package com.viktor.yurlov.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static Date futureTime(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }
}
