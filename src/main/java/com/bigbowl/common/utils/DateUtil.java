package com.bigbowl.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * 日期处理的公用类.
 * @author Douglas Hu
 *
 */
public class DateUtil {

	public static final String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";

	private static SimpleDateFormat datePattern = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final String datePatternYYMMDD = "yyyyMMdd" ;
	public static final String datePatternHHmmss = "HHmmss" ;
	public static final String datePatternYY_MM_DD = "yyyy-MM-dd" ;
	public static final String datePatternYY_MM_DDHHMMSS ="yyyy-MM-dd HH:mm:ss";
    public static final String datePatternYY_MM_DDHHMM = "yyyy-MM-dd HH:mm";
	public static final String datePatternYYMM ="yyyyMM";
	public static final String datePatternYYYYVMMVDD="yyyy/MM/dd";
	public static final String datePatternMMDDYYYY="MM/dd/yyyy";
	public static final String datePatternMMMDDYYYYHHMMSS = "MMM d yyyy HH:mm:ss";
	public static final String datePatternMMMDDYYYY = "MMM d yyyy";
	public static final String datePatternYYYY ="yyyy";//对应年.
	public static final String datePatternyyMMdd="yyMMdd";


	/**
	 * 去掉日期的时、分、秒，如果没有指定日期，则返回当前日期
	 *
	 * @param date
	 * @return
	 *
	 */
	public static Date getSimpleDate(Date date) {
		if (date == null) {
			date = new Date();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取时分秒均为0的日期对象，如果没有给定日期，则返回null
	 * @param date
	 * @return
	 */
	public static Date getStandardEffDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取时分秒为23:59:59的日期对象，如果没有给定日期，则返回null
	 * @param date
	 * @return
	 */
	public static Date getStandardExpDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 返回一个指定日期对象
	 *
	 * @param date
	 * @return Calendar
	 */
	public static Calendar getCalendar(Date date) {
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 返回当前Calendar实例
	 *
	 * @return Calendar
	 */
	public static Calendar getCurrentCalendar() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 返回当前日期
	 *
	 * @return date
	 */
	public static Date getCurrentTime() {
		return new Date();
	}

	/**
	 * 返回默认格式的当前日期 yyyy-MM-dd HH:ss:mm
	 *
	 * @return date
	 */
	public static String getCurrentTimeByDefaultPattern() {
		return new SimpleDateFormat(defaultDatePattern).format(new Date());
	}

	/**
	 * 指定日期默认字符串格式输出
	 *
	 * @param date
	 *
	 * @return
	 */
	public static String getTimeByDefaultPattern(Date date) {
		if (date != null) {
			return new SimpleDateFormat(defaultDatePattern).format(date);
		}
		return null;
	}

	/**
	 * 返回指定日期,指定字符串格式输出
	 *
	 * @param date
	 *
	 * @return pattern格式的字符串
	 */
	public static String getTimeByCustomPattern(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 返回指定格式的当前日期字符串输出
	 *
	 * @return date
	 */
	public static String getCurrentTimeByCustomPattern(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * 返回日期是否是自然季度的末尾月
	 *
	 * @param date
	 *            日期
	 * @return
	 */
	public static boolean isEndOfSeason(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		if (month % 3 == 2) {
			return true;
		}
		return false;
	}

	/**
	 * 返回月
	 *
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回天
	 *
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 返回年
	 *
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回某月的天数
	 *
	 *
	 * @return
	 */
	public static int getMonthLength(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回该月的最大日期
	 *
	 * @param date
	 * @return
	 */
	public static Date getActualMaximumDate(Date date) {
		Calendar calendar = getCalendar(date);
		int actualMaximumDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, actualMaximumDay);
		return calendar.getTime();
	}

	/**
	 * 返回该月的最小日期
	 *
	 * @param date
	 * @return
	 */
	public static Date getActualMinimumDate(Date date) {
		Calendar calendar = getCalendar(date);
		int actualMininumDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, actualMininumDay);
		return calendar.getTime();
	}

	/**
	 * 获得当月指定的某一天
	 *
	 * @param date
	 * @param indexDay
	 * @return
	 */
	public static Date getSpecifyDate(Date date, int indexDay) {
		Calendar calendar = getCalendar(date);
		int actualMininumDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, actualMininumDay + indexDay - 1);
		return calendar.getTime();
	}

	/**
	 * 指定日期返回年月 pattern : yyyyMM
	 *
	 * @param date
	 */
	public static String getYearMonth(Date date) {
		return new SimpleDateFormat("yyyyMM").format(date);
	}

	/**
	 * 获取上个月的日期对象(上个月1号)
	 *
	 * @param date
	 */
	public static Date getPriorMonthDate(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		Calendar newCalendar = Calendar.getInstance();
		if (month == 0) {
			year--;
			month = 11;
		} else {
			month--;
		}
		newCalendar.set(year, month, 1);
		return newCalendar.getTime();
	}

	/**
	 * @param 获取传过来日期的月份的上一个季度末的日期
	 * @param date
	 * @return Date
	 */
	public static Date getpriorMonthDateByMonth(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		Calendar newCalendar = Calendar.getInstance();

		if (month == 4 || month == 5 || month == 6) {
			newCalendar.set(year, 3, 0);
			return newCalendar.getTime();
		} else if (month == 7 || month == 8 || month == 9) {
			newCalendar.set(year, 6, 0);
			return newCalendar.getTime();
		} else if (month == 10 || month == 11 || month == 12) {
			newCalendar.set(year, 9, 0);
			return newCalendar.getTime();
		} else {
			year--;
			newCalendar.set(year, 12, 0);
			return newCalendar.getTime();
		}

	}

	/**
	 * 当前月季末日期
	 *
	 * @param date
	 * @return date
	 */
	public static Date getPerforMancebonusMonthDateByMonth(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		Calendar newCalendar = Calendar.getInstance();
		if (month == 3 || month == 4 || month == 5) {
			newCalendar.set(year, 3, 0);
			return newCalendar.getTime();
		} else if (month == 6 || month == 7 || month == 8) {
			newCalendar.set(year, 6, 0);
			return newCalendar.getTime();
		} else if (month == 9 || month == 10 || month == 11) {
			newCalendar.set(year, 9, 0);
			return newCalendar.getTime();
		} else {
			year--;
			newCalendar.set(year, 12, 0);
			return newCalendar.getTime();
		}

	}

	public static Date getLastMonth(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static Date getNextMonth(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month + 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return getActualMaximumDate(calendar.getTime());
	}

	public static Date parse(String str) throws ParseException {
		return parse(str, defaultDatePattern);
	}

	public static Date parse(String str, String pattern) {
		if (str == null)
			return null;
		try {
			return new SimpleDateFormat(pattern, Locale.US).parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式转换错误", e);
		}
	}

	/**
	 * 获得前months月的最后一天
	 *
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date parse(Date date, int months) {
		String str = getTimeByCustomPattern(date, "yyyy-MM");
		Date d = parse(str, "yyyy-MM");
		Calendar calendar = getCalendar(d);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, (month - months));
		return calendar.getTime();
	}

	public static boolean isEndQuarter(Date date) {
		boolean retval = false;
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		if ((month + 1) % 3 == 0) {
			retval = true;
		}
		return retval;
	}

	public static boolean isMidYear(Date date) {
		boolean retval = false;
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		if ((month + 1) % 6 == 0) {
			retval = true;
		}
		return retval;
	}

	public static boolean isEndYear(Date date) {
		boolean retval = false;
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		if ((month + 1) % 12 == 0) {
			retval = true;
		}
		return retval;
	}

	/**
	 *
	 * @return
	 */
	public static String getCurrentTimeByFullPattern() {
		return new SimpleDateFormat(getDateTimePattern()).format(new Date());
	}

	public static String getDateTimePattern() {
		return defaultDatePattern + " HH:mm:ss.SSS";
	}

	/**
	 * Long形字符串转化日期
	 *
	 * @param exeDate
	 * @return
	 */
	public static String convertToDateString(Object exeDate) {
		String exeDateStr = "";
		if (exeDate != null && exeDate instanceof String) {
			long dataValue = Long.parseLong((String) exeDate);
			Date date = new Date(dataValue);
			exeDateStr = getTimeByCustomPattern(date, "yyyy-MM-dd");
		}
		return exeDateStr;
	}

	/**
	 * CAOJUN 2008-3-3添加
	 *
	 * @param date
	 *            指定的日期
	 * @param dateType
	 *            对指定日期的操作位置
	 * @param dateValue
	 *            对指定日期操作值
	 * @return 返回时分秒都为0的日期
	 */
	public static Date getDate(Date date, String dateType, int dateValue) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (dateType.equals("DATE")) {
			calendar.set(Calendar.DAY_OF_YEAR, calendar
					.get(Calendar.DAY_OF_YEAR)
					+ dateValue);
		} else if (dateType.equals("MONTH")) {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)
					+ dateValue);
		} else if (dateType.equals("YEAR")) {
			calendar
					.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + dateValue);
		}

		return getSimpleDate(calendar.getTime());
	}



	/**
	 * 返回日期的第yearNum年后的日期
	 *
	 * @param date
	 * @return
	 */
	public static Date getNextYear(Date date,int yearNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + yearNum);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
		return calendar.getTime();
	}

	/**
	 * 返回日期 date的第monthNum个月.
	 * @param date
	 * @return
	 */
	public static Date getNextDateByMonth(Date date,int monthNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + monthNum);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
		return calendar.getTime();
	}


	/***
	 * 返回对应日期的第dateNum天.
	 * @param date
	 * @param dateNum
	 * @return
	 */
	public static Date getNextDay(Date date,int dayNum){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR,
				calendar.get(Calendar.DAY_OF_YEAR) + dayNum);
		return calendar.getTime();
	}

	/**
	 * 获得当前日期的第n个月的,第n天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfLastMonth(Date date,int monthNum,int dayNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) +monthNum);
		calendar.set(Calendar.DAY_OF_MONTH, dayNum);
		return calendar.getTime();
	}

	/**
	 * 返回当月的最后一天
	 *
	 * @param strDate
	 *
	 * @return
	 */
	public static Date getLastDayOfThisMonth(String strDate) throws Exception {

		try {
			Date date = new SimpleDateFormat("yyyy-MM").parse(strDate.trim());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) + 1));
			calendar.set(Calendar.DAY_OF_YEAR, (calendar
					.get(Calendar.DAY_OF_YEAR) - 1));
			return calendar.getTime();
		} catch (ParseException e) {
			throw new Exception("日期格式错误！");
		}

	}

	/**
	 * 对帐期的开始时间和结束时间做对比,开始时间不能大于结束时间,开始时间和结束时间应该在一个月内.
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */

	public static Object checkPlanTermDate(Date startDate, Date endDate) {
		Calendar calenderStart = Calendar.getInstance();
		calenderStart.setTime(getSimpleDate(startDate));
		Calendar calenderEnd = Calendar.getInstance();
		calenderEnd.setTime(getSimpleDate(endDate));
		StringBuffer sm = new StringBuffer();
		if (calenderStart.after(calenderEnd)
				|| calenderStart.equals(calenderEnd))
			sm.append("开始日期必须小于结束日期!");
		if (calenderStart.get(Calendar.MONTH) != calenderEnd
				.get(Calendar.MONTH))
			sm.append("开始日期和结束日期必须处于同一个月!");
		if (sm.length() != 0) {
			return sm;
		} else {
			return null;
		}
	}

	/**
	 * 对开始日期和结束日期做判断
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int compareDate(Date startDate, Date endDate) {
		Calendar calenderStart = Calendar.getInstance();
		calenderStart.setTime(getSimpleDate(startDate));
		Calendar calenderEnd = Calendar.getInstance();
		calenderEnd.setTime(getSimpleDate(endDate));
		return calenderStart.compareTo(calenderEnd);
	}

	/***************************************************************************
	 * 将string类型的数据转换为日期型.
	 *
	 * @param str
	 * @return
	 */
	public static Date transStringToDate(String str) {
		try {
			Date dateTemp = datePattern.parse(datePattern.format(str));
			return dateTemp;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 返回指定日期指定格式的字符串输出
	 * @param date 指定日期
	 * @return
	 */
	public static String getTimeByPattern(Date date, String formatType) {
		return new SimpleDateFormat(formatType).format(date);
	}

	/**
	 * 返回指定日期指定格式以及指定时区的字符串输出
	 * @param date 指定日期
	 * @return
	 */
	public static String getTimeByPattern(Date date, String formatType, Locale locale) {
		// 如果没有给定时区则指定为默认时区 - Locale.CHINA
		if(locale==null) {
			locale = Locale.getDefault();
		}
		if(date ==null){
			date = new Date();
		}
		return new SimpleDateFormat(formatType, locale).format(date);
	}

	/**
	 * 对新增汇率的开始时间和结束时间做对比,开始时间必须小于结束时间.
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */

	public static Object checkCurrencyDate(Date startDate, Date endDate) {
		Calendar calenderStart = Calendar.getInstance();
		calenderStart.setTime(getSimpleDate(startDate));
		Calendar calenderEnd = Calendar.getInstance();
		calenderEnd.setTime(getSimpleDate(endDate));
		StringBuffer da = new StringBuffer();
		if (calenderStart.after(calenderEnd)
				|| calenderStart.equals(calenderEnd))
			da.append("开始日期必须小于结束日期!");
		if (da.length() != 0) {
			return da;
		} else {
			return null;
		}
	}

	/***
	 * 根据strFormate 形式得到对应的日期.
	 * @param strDate
	 * @param strFormater
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByString(String strDate,String strFormate) throws ParseException{
		SimpleDateFormat simpleFormat = new SimpleDateFormat(strFormate);
		return simpleFormat.parse(strDate);
	}


	/**
	 * 通过日期/时间 和格式返回string格式的日期
	 * @author likey
	 * @param date
	 * @param format
	 * @return
	 */
	public static String returnDateFormatToString(Date date ,String format){
		SimpleDateFormat creatFormatter = new SimpleDateFormat(format);
		return creatFormatter.format(date);
	}


	/**
	 *
	 * @Description (两个不含年月日的时间进行比较)
	 * @param time_o 如 11:12:13
	 * @param time_t 如  08:08:08
	 * @return  time_o >  time_o  true  time_o <=  time_o  false
	 * @author: Gaoxueyong
	 * Create at: 2017年7月18日 下午5:59:18
	 */
	@SuppressWarnings("finally")
	public static boolean compareHHmmss(String time_o,String time_t){
		boolean returnFlag = false;
		if(StringUtils.isEmpty(time_o) || StringUtils.isEmpty(time_t)){
			return false;
		}
		String nowStr = returnDateFormatToString(new Date(), datePatternYY_MM_DD);

		try {
			Date date_o  = parse(nowStr+" "+time_o);
			Date date_t = parse(nowStr+" "+time_t);
			if(date_o.after(date_t)){
				returnFlag =  true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return returnFlag;
		}

	}

	/**
	 * 比较两个日期相差的天数(不算时分秒)
	 * @param startDate
	 * @param endDate
	 * @return 相差天数
	 */
	public static int compareDateDay(Date startDate, Date endDate) {
		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(startDate);

		int startDay = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(endDate);

		int endDay = aCalendar.get(Calendar.DAY_OF_YEAR);

		return endDay - startDay;
	}

	/**
	 * 格式化当前时间  返回格式yyyyMMdd
	 * @return
	 */
	public static String getCurrentDateYYMMDD() {
		SimpleDateFormat formatter = new SimpleDateFormat(datePatternyyMMdd);
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}

	/**
	 * 格式化当前时分秒  返回格式yyyyMMdd
	 * @return
	 */
	public static String getCurrentDateHHmmss() {
		SimpleDateFormat formatter = new SimpleDateFormat(datePatternHHmmss);
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}


	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args){
		try {
			//System.out.println(DateUtil.getDateByString("2008/2/31", DateUtil.datePatternYYYYVMMVDD));
			System.out.println(DateUtil.getCurrentDateYYMMDD()+".."+DateUtil.getCurrentDateHHmmss());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
