package com.yk.platform.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public abstract class DateUtil {
    
    /** 完整时间 yyyy-MM-dd HH:mm:ss */
    public static final String simple = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 根据自定义格式和字符串获取对应系统时间
     * @author tankaichao
     * @param format
     * @param str
     * @return
     */
    public static Date getDateFormatter(String format, String str) {
    	
    	SimpleDateFormat df = new SimpleDateFormat(format);
    	
    	Date date = null;
    	
    	try {
			date = df.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return date;
	}
    
    /**
     * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateFormatter() {
        DateFormat df = new SimpleDateFormat(simple);
        return df.format(new Date());
    }
    
    public static String getDateFormatter(Date date) {
        DateFormat df = new SimpleDateFormat(simple);
        return df.format(date);
    }
    
    public static String getDateByFormat(Date date ,String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
    
    /**
     * 当前时间
     * @return
     */
    public static String getCurrtTime(String format) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
    
    /**
     * 一个小时之间的时间
     * @return
     */
    public static String getBeforeHourTime(int hour, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        DateFormat df = new SimpleDateFormat(format);
        return df.format(calendar.getTime());
    }
    
    public static String getBeforeSecondTime(int minute, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - minute);
        DateFormat df = new SimpleDateFormat(format);
        return df.format(calendar.getTime());
    }
    
    
    
}
