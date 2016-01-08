/*
 * Copyright (c) 2015.
 *
 * NakedPenguin. All rights reserved.
 */

package com.medic.ragingbull.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Vamshi Molleti
 */
public class Time {

    static DateTimeFormatter format = DateTimeFormat.forPattern("dd-MMM, YYYY HH:mm");

    public static long getMillisAfterXDays(int days) {
        DateTime currentDate = new DateTime();
        currentDate.plus(Period.days(days));
        return  currentDate.getMillis();
    }

    public static long getMillisAfterXMonths(int months) {
        DateTime currentDate = new DateTime();
        currentDate.plus(Period.months(months));
        return  currentDate.getMillis();
    }

    public static String getFormattedDate(DateTime date) {
        return  date.toString(format);
    }
}
