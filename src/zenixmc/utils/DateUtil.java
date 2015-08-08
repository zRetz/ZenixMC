/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zenixmc.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utilities class for handling Dates.
 *
 * @author james
 */
public class DateUtil {

	/**
	 * The date format.
	 */
	private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh/mm/ss");
	
	/**
	 * The valid formats.
	 */
	private static final String[] validFormats = new String[]{"second", "minute", "hour", "day", "month", "year"};
	
	/**
	 * A second.
	 */
	public static final long second = 1000L;
	
	/**
	 * A minute.
	 */
	public static final long minute = 60000L;
	
	/**
	 * A hour.
	 */
	public static final long hour = 3600000L;
	
	/**
	 * A day.
	 */
	public static final long day = 86400000L;
	
	/**
	 * A month.
	 */
	public static final long month = 2592000000L;
	
	/**
	 * A year.
	 */
	public static final long year = 31104000000L;
	
	/**
	 * Formats the given date.
	 * @param date
	 * 		The date to format.
	 * @return The formatted date.
	 */
	public static String formatDate(Date date) {
		return format.format(date);
	}
	
	/**
	 * Checks if format is a valid format.
	 * @param format
	 * 		The format to check.
	 * @return <code>true</code> If format is a valid format.
	 */
	public static boolean isFormat(String format) {
		return Arrays.asList(validFormats).contains(format);
	}
	
	/**
	 * Calculates the time through millis. An amount of millis is added on from the current time.
	 * @param millis
	 * 		The millis to add.
	 * @return The end time.
	 */
	public static long calcAddedMillisToNow(long millis) {
		
		Calendar c = new GregorianCalendar();
		c.setTimeInMillis(new Date().getTime() + millis);
		
		return c.getTimeInMillis();
	}
	
	/**
	 * Calculates the time through millis. A duration is added on from the current time.
	 * @param format
	 * 		The format to use.
	 * @param duration
	 * 		The duration to add.
	 * @return The time.
	 */
    public static long calcAddedMillisToNow(String format, long duration) {
    	return calcAddedMillisToNow(formatDuration(format, duration));
    }
    
    /**
     * Formats the given duration to the according format.
     * @param format
     * 		The format for formatting.
     * @param duration
     * 		The duration to format.
     * @return The formatted duration.
     */
    public static long formatDuration(String format, long duration) {
    	
    	if (!(DateUtil.isFormat(format))) {
			return 0;
		}
    	
    	long result = 0;
    	
    	switch(format) {
    	case "second":
    		result = duration * second;
    		break;
    	case "minute":
    		result = duration * minute;
    		break;
    	case "hour":
    		result = duration * hour;
    		break;
    	case "day":
    		result = duration * day;
    		break;
    	case "month":
    		result = duration * month;
    		break;
    	case "year":
    		result = duration * year;
    		break;
    	}
    	
    	return result;
    }
    
    /**
     * Converts milliseconds to a date.
     * @param millis
     * 		The milliseconds.
     * @return The date converted from milliseconds.
     */
    public static Date toDate(long millis) {
    	return new Date(millis);
    }
}
