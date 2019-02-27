package com.anshare.project.core.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException; 

import org.apache.commons.lang3.time.DateFormatUtils;
/**
* @ClassName DateUtil
* @Description 日期工具类
* @Author wukunfan
* @Date 2018/11/13 14:43
* @UpdateUser:
* @UpdateDate:     2018/11/13 14:43
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class DateUtil extends org.apache.commons.lang3.time.DateUtils{

	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

    /**
     * 把日期字符串格式化成日期类型
     * @param dateStr
     * @param format
     * @return
     */
    public static Date convert2Date(String dateStr, String format) {  
        SimpleDateFormat simple = new SimpleDateFormat(format); 
        try {  
            simple.setLenient(false);  
            return simple.parse(dateStr);  
        } catch (Exception e) {  
            return  null;  
        }  
     }  
    
    
    /**
     * 把日期类型格式化成字符串
     * @param date
     * @param format
     * @return
     */
    public static String convert2String(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            return formater.format(date);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 转sql的time格式
     * @param date
     * @return
     */
    public static java.sql.Timestamp convertSqlTime(Date date){
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        return timestamp;
    }
    
    /**
     * 转sql的日期格式
     * @param date
     * @return
     */
    public static java.sql.Date convertSqlDate(Date date){
        java.sql.Date Datetamp = new java.sql.Date(date.getTime());
        return Datetamp;
    }
    
    
    /**
     * 获取当前日期
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }
    
    /**
     * 获取时间戳
     * @return
     */
    public static long getTimestamp()
    {
        return System.currentTimeMillis();
    }
    
    /**
     * 获取月份的天数
     * @param year
     * @param month
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取日期的年
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * 获取日期的月
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 获取日期的日
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }
    
    /**
     * 获取日期的时
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR);
    }
    
    /**
     * 获取日期的分种
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }
    
    /**
     * 获取日期的秒
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }
    
    /**
     * 获取星期几
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek-1;
    }
    
    /**
     * 获取哪一年共有多少周
     * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekNumOfYear(c.getTime());
    }
    
    /**
     * 取得某天是一年中的多少周
     * @param date
     * @return
     */
    public static int getWeekNumOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 取得某天所在周的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
    }
    
    /**
     * 取得某天所在周的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }
    
    /**
     * 取得某年某周的第一天 对于交叉:2008-12-29到2009-01-04属于2008年的最后一周,2009-01-05为2009年第一周的第一天
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar calFirst = Calendar.getInstance();
        calFirst.set(year, 0, 7);
        Date firstDate = getFirstDayOfWeek(calFirst.getTime());

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.setTime(firstDate);

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, (week - 1) * 7);
        firstDate = getFirstDayOfWeek(cal.getTime());

        return firstDate;
    }
    
    /**
     * 取得某年某周的最后一天 对于交叉:2008-12-29到2009-01-04属于2008年的最后一周, 2009-01-04为
     * 2008年最后一周的最后一天
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar calLast = Calendar.getInstance();
        calLast.set(year, 0, 7);
        Date firstDate = getLastDayOfWeek(calLast.getTime());

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.setTime(firstDate);

        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, (week - 1) * 7);
        Date lastDate = getLastDayOfWeek(cal.getTime());

        return lastDate;
    }
    
    
    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }
    
    /*
     * 1则代表的是对年份操作， 2是对月份操作， 3是对星期操作， 5是对日期操作， 11是对小时操作， 12是对分钟操作， 13是对秒操作，
     * 14是对毫秒操作
     */

    /**
     * 增加年
     * @param date
     * @param amount
     * @return
     */
    public static Date addYears(Date date, int amount) {
        return add(date, 1, amount);
    }

    /**
     * 增加月
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, 2, amount);
    }

    /**
     * 增加周
     * @param date
     * @param amount
     * @return
     */
    public static Date addWeeks(Date date, int amount) {
        return add(date, 3, amount);
    }

    /**
     * 增加天
     * @param date
     * @param amount
     * @return
     */
    public static Date addDays(Date date, int amount) {
        return add(date, 5, amount);
    }

    /**
     * 增加时
     * @param date
     * @param amount
     * @return
     */
    public static Date addHours(Date date, int amount) {
        return add(date, 11, amount);
    }

    /**
     * 增加分
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, 12, amount);
    }

    /**
     * 增加秒
     * @param date
     * @param amount
     * @return
     */
    public static Date addSeconds(Date date, int amount) {
        return add(date, 13, amount);
    }

    /**
     * 增加毫秒
     * @param date
     * @param amount
     * @return
     */
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, 14, amount);
    }
    
    
    
   /**
    * time差
    * @param before
    * @param after
    * @return
    */
   public static long diffTimes(Date before, Date after){
       return after.getTime() - before.getTime();
   }
   
   /**
    * 秒差
    * @param before
    * @param after
    * @return
    */
   public static long diffSecond(Date before, Date after){
       return (after.getTime() - before.getTime())/1000;
   }
   
   /**
    * 分种差
    * @param before
    * @param after
    * @return
    */
   public static int diffMinute(Date before, Date after){
       return (int)(after.getTime() - before.getTime())/1000/60;
   }
   
   /**
    * 时差
    * @param before
    * @param after
    * @return
    */
   public static int diffHour(Date before, Date after){
       return (int)(after.getTime() - before.getTime())/1000/60/60;
   }
    
    /**
     * 天数差
     * @param before
     * @param after
     * @return
     */
    public static int diffDay(Date before, Date after) {
        return Integer.parseInt(String.valueOf(((after.getTime() - before.getTime()) / 86400000)));
    }
    
    /**
     * 月差
     * @param before
     * @param after
     * @return
     */
    public static int diffMonth(Date before, Date after){
        int monthAll=0;
        int yearsX = diffYear(before,after);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(before);
        c2.setTime(after);
        int monthsX = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        monthAll=yearsX*12+monthsX;
        int daysX =c2.get(Calendar.DATE) - c1.get(Calendar.DATE);
        if(daysX>0){
            monthAll=monthAll+1;
        }
        return monthAll;
    }
    
    /**
     * 年差
     * @param before
     * @param after
     * @return
     */
    public static int diffYear(Date before, Date after) {
        return getYear(after) - getYear(before);
    }
    
    /**
     * 设置23:59:59
     * @param date
     * @return
     */
    public static Date setEndDay(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.set(Calendar.HOUR_OF_DAY, 23);  
        calendar.set(Calendar.MINUTE, 59);  
        calendar.set(Calendar.SECOND, 59);  
        return calendar.getTime();  
    } 
    
    /**
     * 设置00:00:00
     * @param date
     * @return
     */
    public static Date setStartDay(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.set(Calendar.HOUR_OF_DAY, 00);  
        calendar.set(Calendar.MINUTE, 00);  
        calendar.set(Calendar.SECOND, 00);  
        return calendar.getTime();  
    }  
    
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 使用预设格式格式化日期
     * 
     * @param date 日期
     * @return 格式格式化日期
     */
    public static String format(Date date) {

        return format(date, getDatePattern());
    }
    
    /**
     * 获得默认的 date pattern
     * @return 日期格式
     */
    public static String getDatePattern() {

        return FORMAT_LONG;
    }
    
    /**
     * 使用用户格式格式化日期
     * @param date  日期
     * @param pattern 日期格式
     * @return 日期格式
     */
    public static String format(Date date, String pattern) {

        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }
    /**
     * 得到当前日期字符串 格式（yyyyMMdd）
     */
    public static String getDateH() {
        return getDate("yyyyMMdd");
    }
}
