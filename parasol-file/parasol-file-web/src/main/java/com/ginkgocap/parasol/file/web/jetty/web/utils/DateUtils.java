
package com.ginkgocap.parasol.file.web.jetty.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;


/**
 * 
 * 常用日期操作.
 * @author henry.yu
 * @version 
 * @since 1.5
 * 
 */
public class DateUtils {

	private static Logger logger = Logger.getLogger(DateUtils.class);

	public static String dateToString(Date date, String formatStr) {
		SimpleDateFormat formatDate = new SimpleDateFormat(formatStr);
		String str = formatDate.format(date);
		return str;
	}
	
	/**最近一天start*/
	/**
	 * 获取当日0时0分0点0秒
	 * 获取当前时间戳的String格式 [yyyy-MM-dd HH:mm:ss].
	 * 
	 * @return String [yyyy-MM-dd HH:mm:ss]格式的当前时间
	 */
	public static String getTodayStart() {
		       Calendar calendar = Calendar.getInstance();   
		        //如果没有这种设定的话回去系统的当期的时间   
		        calendar.set(Calendar.HOUR_OF_DAY, 0);   
		        calendar.set(Calendar.MINUTE, 0);   
		        calendar.set(Calendar.SECOND, 0);   
		        calendar.set(Calendar.MILLISECOND, 0);   
		        Date date = new Date(calendar.getTimeInMillis());   
  
		return DateFormater.formatDate(date, DateFormatPattern.DEFAULT_FULL_TIMESTAMP);
	}
	
	
	/**最近一天start*/
	/**
	 * 获取当日0时0分0点0秒
	 * 获取当前时间戳的String格式 [yyyy-MM-dd HH:mm:ss].
	 * 
	 * @return long
	 */
	public static long getLastDayStart() {
		       Calendar calendar = Calendar.getInstance();   
		        //如果没有这种设定的话回去系统的当期的时间   
		        calendar.set(Calendar.HOUR_OF_DAY, 0);   
		        calendar.set(Calendar.MINUTE, 0);   
		        calendar.set(Calendar.SECOND, 0);   
		        calendar.set(Calendar.MILLISECOND, 0);   
		        Date date = new Date(calendar.getTimeInMillis());   
  
		return date.getTime();
	}
	 /**  
	     * 取当天23点59分59秒  
     */  
	public static String getTodayEnd () {   
       Calendar calendar = Calendar.getInstance();   
	       calendar.set(Calendar.HOUR_OF_DAY, 23);    
	       calendar.set(Calendar.MINUTE, 59);   
	       calendar.set(Calendar.SECOND, 59);   
	       Date date = new Date(calendar.getTimeInMillis());   
	       return DateFormater.formatDate(date,DateFormatPattern.DEFAULT_FULL_TIMESTAMP);   
	    }      
	
	
	 /**  
     * 取当天23点59分59秒  
 */  
public static long getLastDayEnd () {   
   Calendar calendar = Calendar.getInstance();   
       calendar.set(Calendar.HOUR_OF_DAY, 23);    
       calendar.set(Calendar.MINUTE, 59);   
       calendar.set(Calendar.SECOND, 59);   
       Date date = new Date(calendar.getTimeInMillis());   
       return date.getTime();   
    }      
	/**  
     * 取得此时此刻
    */  
 public static String getCurrentTime () {   
   
       return DateFormater.formatDate(new Date(),DateFormatPattern.DEFAULT_FULL_TIMESTAMP);   
    } 
 /**
  * 获取当前时间 
  * @return [yyyy年MM月dd日HH:mm]
  */
 public static String getCurDate() {
	  return DateFormater.formatDate(new Date(),DateFormatPattern.DEFAULT_TIMESTAMP);  
}
 /**
  * 获取当前时间 
  * @return [yyyy年MM月dd日HH:mm]
  */
 public static String getCurDate(String time) {
	 SimpleDateFormat dateFormat = new SimpleDateFormat(
             "yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
	 try {
		Date date = dateFormat.parse(time);
		return DateFormater.formatDate(date,DateFormatPattern.DEFAULT_TIMESTAMP);
	} catch (ParseException e) {
		e.printStackTrace();
		return DateFormater.formatDate(new Date(),DateFormatPattern.DEFAULT_TIMESTAMP);  
	}
}
 /**  
  * 取得此时此刻
 */  
public static long getCurentTimes() {   

    return new Date().getTime();   
 }    
 
	/**最近一天end*/
/***最近两天start*/
public static String getTwoDaysStart() {
       Calendar calendar = Calendar.getInstance();   
        //如果没有这种设定的话回去系统的当期的时间   
       calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);   
        calendar.set(Calendar.MINUTE, 0);   
        calendar.set(Calendar.SECOND, 0);   
        calendar.set(Calendar.MILLISECOND, 0);   
        Date date = new Date(calendar.getTimeInMillis());   

return DateFormater.formatDate(date, DateFormatPattern.DEFAULT_FULL_TIMESTAMP);
}

/***最近三天end*/
	/***最近三天start*/
	public static String getThreeDaysStart() {
	       Calendar calendar = Calendar.getInstance();   
	        //如果没有这种设定的话回去系统的当期的时间   
	       calendar.add(Calendar.DAY_OF_MONTH, -2);
	        calendar.set(Calendar.HOUR_OF_DAY, 0);   
	        calendar.set(Calendar.MINUTE, 0);   
	        calendar.set(Calendar.SECOND, 0);   
	        calendar.set(Calendar.MILLISECOND, 0);   
	        Date date = new Date(calendar.getTimeInMillis());   

	return DateFormater.formatDate(date, DateFormatPattern.DEFAULT_FULL_TIMESTAMP);
}
	
	/***最近三天end*/
	/***最近三天start*/
	public static long getLastThreeDays() {
	       Calendar calendar = Calendar.getInstance();   
	        //如果没有这种设定的话回去系统的当期的时间   
	       calendar.add(Calendar.DAY_OF_MONTH, -2);
	        calendar.set(Calendar.HOUR_OF_DAY, 0);   
	        calendar.set(Calendar.MINUTE, 0);   
	        calendar.set(Calendar.SECOND, 0);   
	        calendar.set(Calendar.MILLISECOND, 0);   
	        Date date = new Date(calendar.getTimeInMillis());   

	return date.getTime();
}
	
	/***最近七天start*/
	public static String getSevenDaysStart() {
	       Calendar calendar = Calendar.getInstance();   
	        //如果没有这种设定的话回去系统的当期的时间   
	       calendar.add(Calendar.DAY_OF_MONTH, -6);
	        calendar.set(Calendar.HOUR_OF_DAY, 0);   
	        calendar.set(Calendar.MINUTE, 0);   
	        calendar.set(Calendar.SECOND, 0);   
	        calendar.set(Calendar.MILLISECOND, 0);   
	        Date date = new Date(calendar.getTimeInMillis());   

	return DateFormater.formatDate(date, DateFormatPattern.DEFAULT_FULL_TIMESTAMP);
}
	
	/***最近七天end*/
	

	/***最近七天start*/
	public static long getLastSevenDays() {
	       Calendar calendar = Calendar.getInstance();   
	        //如果没有这种设定的话回去系统的当期的时间   
	       calendar.add(Calendar.DAY_OF_MONTH, -6);
	        calendar.set(Calendar.HOUR_OF_DAY, 0);   
	        calendar.set(Calendar.MINUTE, 0);   
	        calendar.set(Calendar.SECOND, 0);   
	        calendar.set(Calendar.MILLISECOND, 0);   
	        Date date = new Date(calendar.getTimeInMillis());   

	return date.getTime();
}
	
	/***最近七天end*/
	
	/***最近一个月start*/
	public static String getLastMonthStart() {
	       Calendar calendar = Calendar.getInstance();   
	        //如果没有这种设定的话回去系统的当期的时间   
	       //Calendar.MONTH)
	        calendar.add(Calendar.MONTH, -1);
	        calendar.set(Calendar.HOUR_OF_DAY, 0);   
	        calendar.set(Calendar.MINUTE, 0);   
	        calendar.set(Calendar.SECOND, 0);   
	        calendar.set(Calendar.MILLISECOND, 0);   
	        Date date = new Date(calendar.getTimeInMillis());   

	return DateFormater.formatDate(date, DateFormatPattern.DEFAULT_FULL_TIMESTAMP);
}
	
	/***最近一个月end*/
	
	/***最近一个月start*/
	public static long getLastMonth() {
	       Calendar calendar = Calendar.getInstance();   
	        //如果没有这种设定的话回去系统的当期的时间   
	       //Calendar.MONTH)
	        calendar.add(Calendar.MONTH, -1);
	        calendar.set(Calendar.HOUR_OF_DAY, 0);   
	        calendar.set(Calendar.MINUTE, 0);   
	        calendar.set(Calendar.SECOND, 0);   
	        calendar.set(Calendar.MILLISECOND, 0);   
	        Date date = new Date(calendar.getTimeInMillis());   

	return date.getTime();
}
	
	/***最近一个月end*/
	/***最近三个月start*/
	public static String getLastThreeMonthStart() {
	       Calendar calendar = Calendar.getInstance();   
	        //如果没有这种设定的话回去系统的当期的时间   
	        calendar.add(Calendar.MONTH, -3);
	        calendar.set(Calendar.HOUR_OF_DAY, 0);   
	        calendar.set(Calendar.MINUTE, 0);   
	        calendar.set(Calendar.SECOND, 0);   
	        calendar.set(Calendar.MILLISECOND, 0);   
	        Date date = new Date(calendar.getTimeInMillis());   

	return DateFormater.formatDate(date, DateFormatPattern.DEFAULT_FULL_TIMESTAMP);
}
	
	/***最近三个月end*/
	/***最近三个月start*/
	public static long getLastThreeMonth() {
	       Calendar calendar = Calendar.getInstance();   
	        //如果没有这种设定的话回去系统的当期的时间   
	        calendar.add(Calendar.MONTH, -3);
	        calendar.set(Calendar.HOUR_OF_DAY, 0);   
	        calendar.set(Calendar.MINUTE, 0);   
	        calendar.set(Calendar.SECOND, 0);   
	        calendar.set(Calendar.MILLISECOND, 0);   
	        Date date = new Date(calendar.getTimeInMillis());   

	return date.getTime();
}
	
	public static String getStartTime(String dateRange){
		String startctime = "";
		if (dateRange != null && dateRange.length() > 0) {
			if (dateRange.equals("1")) {
				startctime = DateUtils.getTodayStart(); //yyyy-MM-dd 00:00:00
			} else if (dateRange.equals("2")) {
				startctime = DateUtils.getThreeDaysStart();
			}else if (dateRange.equals("3")) {
				startctime = DateUtils.getSevenDaysStart();
			}else if (dateRange.equals("4")) {
				startctime = DateUtils.getLastMonthStart();
			}else if (dateRange.equals("5")) {
				startctime = DateUtils.getLastThreeMonthStart();
			}
		}
		return startctime;
	}
	
	/***最近三个月end*/
    public static void main(String[] args) {
        /**
         * 获取当前时间戳的String格式 [yyyy-MM-dd HH:mm:ss].
         */
        System.out.println("toStringCurrentTimestamp() = " + getTodayStart());

        /**
         * 获取当前时间戳的String格式 [yyyyMMddHHmmss].
         */
        System.out.println("toStringCurrentTimestampNoPartition() = " + getTodayEnd());

     
    }
}
