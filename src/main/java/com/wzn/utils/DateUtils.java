package com.wzn.utils;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.crypto.Data;
import java.util.Date;

public class DateUtils {
    private static final String STANDER_FORMAT="yyyy-MM-dd HH:mm:ss";

    /*
    * Date-->string
    * */
    public static String dateToStr(Date date){
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(STANDER_FORMAT);
    }

    /*String-->Date*/
    public static Date strToDate(String str){
     DateTimeFormatter dateTimeFormatter=   DateTimeFormat.forPattern(STANDER_FORMAT);
     DateTime dateTime=dateTimeFormatter.parseDateTime(str);
     return dateTime.toDate();
    }
    public static Date strToDate(String str,String format){
        DateTimeFormatter dateTimeFormatter=   DateTimeFormat.forPattern(STANDER_FORMAT);
        DateTime dateTime=dateTimeFormatter.parseDateTime(str);
        return dateTime.toDate();
    }


}
