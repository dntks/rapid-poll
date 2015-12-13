package com.appsball.rapidpoll.commons.utils;

import junit.framework.Assert;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

public class DateStringFormatterTest {

    private DateStringFormatter dateStringFormatter;

    @Before
    public void setUp(){
        dateStringFormatter = new DateStringFormatter();
    }

    @Test
    public void test3DaysAgo(){
        String toCompare ="2015-12-13 20:27:00";
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        int diffInDays = dateStringFormatter.getDiffInDays("2015-11-06 13:27:00",
                                                           formatter.parseDateTime(toCompare));
        Assert.assertEquals(diffInDays, 37);
    }
}
