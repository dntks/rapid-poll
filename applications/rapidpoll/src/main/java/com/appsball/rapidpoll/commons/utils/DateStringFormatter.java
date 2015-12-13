package com.appsball.rapidpoll.commons.utils;

import android.content.res.Resources;

import com.appsball.rapidpoll.R;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateStringFormatter {

    private Resources resources;

    public DateStringFormatter(Resources resources) {
        this.resources = resources;
    }

    public DateStringFormatter() {
    }

    public String createDaysAgoFormatFromPublishDate(String publishDate){
        int diffInDays = getDiffInDays(publishDate, DateTime.now());
        return resources.getString(R.string.started_x_days_ago, diffInDays);
    }

    public int getDiffInDays(String publishDate, DateTime toCompare) {
        if(publishDate == null){
            return 0;
        }
        //2015-11-06 13:27:00
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime date = formatter.parseDateTime(publishDate);
        Period diff = new Period(date, toCompare);
        Days daysDiff = Days.daysBetween(date, toCompare);
        return daysDiff.getDays();
    }
}
