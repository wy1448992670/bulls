package com.goochou.p2b.utils;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期相关辅助类
 */
public class DateUtil {

	public static DateFormat dateFullTimeFormat2 = new SimpleDateFormat("yyyMMddHHmmssSSS");

	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static DateFormat dateFullTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static DateFormat simpleDotFormatter = new SimpleDateFormat("yyyy.MM");

	public static DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	public static DateFormat dmyTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	public static DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

	public static DateFormat yearMonthDayFormat = new SimpleDateFormat("yy-MM-dd");

	public static DateFormat monthDayFormat = new SimpleDateFormat("MM.dd");

	public static DateFormat dateTimeZoreFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

	public static DateFormat dateTimeMaxSMFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

	public static DateFormat dateFormatYMR = new SimpleDateFormat("yyyy年-MM月-dd日");

	public static DateFormat dateFormatYMR_2 = new SimpleDateFormat("yyyy年MM月dd日");

	public static DateFormat dateFormatYMDZoreH = new SimpleDateFormat("yyyy-MM-dd 00");
	public static DateFormat dateFormatYMDHZoreH = new SimpleDateFormat("yyyy-MM-dd HH");

	public static DateFormat dateFormatElevenFirst = new SimpleDateFormat("yyyy-MM-dd 11:00:00");

	public static DateFormat dateFormatElevenSecond = new SimpleDateFormat("yyyy-MM-dd 11:05:00");

	public static DateFormat hm = new SimpleDateFormat("HH:mm");

	public static DateFormat yearMonthDayFormatWithDot = new SimpleDateFormat("yyyy.MM.dd");

	public static DateFormat ddmmyyyy = new SimpleDateFormat("dd/MM/yyyy");

	public static DateFormat hms = new SimpleDateFormat("HH:mm:ss");

	public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

	public static DateFormat dateFormatYYYYMMDDHHMMChinese = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

	public static DateFormat mmMonthddDay = new SimpleDateFormat("MM月dd日");

	public static DateFormat mmMonthddDayHHmm = new SimpleDateFormat("MM月dd日HH:mm");

	public static DateFormat dateFormatYYYYMMDDHHMMSSChinese = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

	// 将时间戳转为rfc3339格式的字符串
	public static String getStrRfc3339Time(long cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		// 例如：cc_time=1291778220
		long lcc_time = cc_time;
		re_StrTime = sdf.format(new Date(lcc_time));

		return re_StrTime;

	}


	// 将时间戳转为rfc3339格式的字符串
	public static String getStrRfc3339Time(Date date) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		re_StrTime = sdf.format(date);

		return re_StrTime;

	}

	/**
	 * 对给定的字符串，转换为日期类型
	 *
	 * @param dateString
	 * @return Date (value/null)
	 */
	public static Date parseHM(String dateString) {
		Date date = null;
		try {
			date = dateTimeFormat.parse(dateString);
		} catch (ParseException e) {
		}
		return date;
	}

	public static String getDateAddYearString(int years) {
		Date date = addYear(years);
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * 计算两个时间相差多少年
	 *
	 * @param nowDate
	 *            当前的时间（被减数）
	 * @param date
	 *            以前的时间（减数）
	 */
	public static int dateToDateYear(Date nowDate, Date date) {
		if (nowDate == null)
			return 0;
		if (date == null)
			return 0;
		long livetime = nowDate.getTime() - date.getTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(livetime);
		int liveyears = calendar.get(Calendar.YEAR) - 1970;
		return liveyears;
	}

	/**
	 * 在当前时间减多少年
	 */
	public static Date addYear(int years) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.YEAR, 0 - years);
		return calendar.getTime();
	}

	/**
	 * <p>
	 * 根据{@code subType} 减去相应 {@code subNum }数
	 * </p>
	 *
	 * @param date
	 *            时间
	 * @param subType
	 *            类型（年-yy、月-MM、星期-yy、天-dd）
	 * @param subNum
	 * @return
	 */
	public static Date subDate(Date date, int type, int subNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, subNum);
		return calendar.getTime();
	}

	/**
	 * 对给定的日期以模式串pattern格式化
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 对给定的日期以模式串pattern格式化
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Object obj, String pattern) {
		if (obj == null) {
			return "";
		}
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(obj);
	}

	/**
	 * 对给定的日期字符串以pattern格式解析
	 *
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date parse(String dateString, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = df.parse(dateString);
		} catch (Throwable t) {
			date = null;
		}
		return date;
	}

	/**
	 * 计算一天的起始时间和结束时间.
	 *
	 * @param date
	 * @return
	 */
	public static Date[] getDayPeriod(Date date) {
		if (date == null) {
			return null;
		}
		Date[] dtary = new Date[2];
		dtary[0] = getDayMinTime(date);
		dtary[1] = getDayMaxTime(date);
		return dtary;
	}

	/**
	 * 获得指定日期的指定时、分、秒日期
	 *
	 * @param date
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public static Date getSpecifiedTime(Date date, int hours, int minutes, int seconds) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hours);
		c.set(Calendar.MINUTE, minutes);
		c.set(Calendar.SECOND, seconds);
		return c.getTime();
	}

	/**
	 * 获得指定日期的最小时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayMinTime(Date date) {
		if (date == null) {
			return null;
		}
		return getSpecifiedTime(date, 0, 0, 0);
	}

	/**
	 * 获得指定日期的后一天的最小时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getNextDayMinTime(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获得指定日期的最大时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayMaxTime(Date date) {
		return getSpecifiedTime(date, 23, 59, 59);
	}

	public static Date[] getWeekPeriod(Date dt) {
		if (dt == null)
			return null;
		Date[] dtary = new Date[2];

		Calendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(dt);

		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		dtary[0] = getDayMinTime(calendar.getTime());

		calendar.add(Calendar.DAY_OF_WEEK, 6);
		dtary[1] = getDayMaxTime(calendar.getTime());
		return dtary;
	}

	@SuppressWarnings("deprecation")
	public static Date parseHSDate(String dtStr) {
		if (dtStr == null || dtStr.equals("")) {
			return new Date();
		}
		int year = Integer.parseInt(dtStr.substring(0, 4)) - 1900;
		int month = Integer.parseInt(dtStr.substring(4, 6)) - 1;
		int date = Integer.parseInt(dtStr.substring(6));
		return new Date(year, month, date);
	}

	@SuppressWarnings("deprecation")
	public static Date[] getMonthPeriod(Date dt) {
		int[] days = { 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		if (dt == null)
			return null;
		Date[] dtary = new Date[2];
		dtary[0] = new Date(dt.getYear(), dt.getMonth(), 1, 0, 0, 0);
		dtary[1] = new Date(dt.getYear(), dt.getMonth(), days[dt.getMonth()], 23, 59, 59);
		if (dt.getMonth() == 1 && dt.getYear() % 4 == 0)
			dtary[1].setDate(29);

		return dtary;
	}

	/**
	 * 判断两个日期是否同一天
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean isSameDay(Date first, Date second) {
		Date range[] = getDayPeriod(first);
		return second.after(range[0]) && second.before(range[1]);
	}

	/**
	 * 判断两个日期是否同一周
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean isSameWeek(Date first, Date second) {
		Date range[] = getWeekPeriod(first);
		return (compare(second, range[0]) >= 0 && compare(second, range[1]) <= 0);
	}

	/**
	 * 判断两个日期是否同一月
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isSameMonth(Date first, Date second) {
		return first.getYear() == second.getYear() && first.getMonth() == second.getMonth();
	}

	/**
	 * 判断两个日期是否同一季度
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isSameQuarter(Date first, Date second) {
		if (first.getYear() != second.getYear())
			return false;
		else {
			if (first.getMonth() <= 2 && second.getMonth() <= 2)
				return true;
			else if (first.getMonth() <= 5 && second.getMonth() <= 5 && first.getMonth() > 2 && second.getMonth() > 2)
				return true;
			else if (first.getMonth() <= 8 && second.getMonth() <= 8 && first.getMonth() > 5 && second.getMonth() > 5)
				return true;
			else if (first.getMonth() <= 11 && second.getMonth() <= 11 && first.getMonth() > 8 && second.getMonth() > 8)
				return true;
			else
				return false;
		}
	}

	/**
	 * 取得这个周的星期五（最后交易日日期）
	 *
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getLastDayOfWeek(Date date) {
		return new Date(date.getYear(), date.getMonth(), date.getDate() + (5 - date.getDay()));
	}

	/** 获得本周一0点时间 **/
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/** 获得本周日24点时间 **/
	public static Date getFirstDateThisWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
	}

	/**
	 * 取得这个月的最后一天
	 *
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getLastDayOfMonth(Date date) {
		return new Date(date.getYear(), date.getMonth() + 1, 0);
	}

	/**
	 * 取得这个季度的第一天
	 *
	 * @param date
	 * @return 季度的第一天
	 * @deprecated replaced by <code>getFirstDayOfQuarter</code>
	 */
	@Deprecated
	public static Date getFirstDayOfQuote(Date date) {
		return new Date(date.getYear(), date.getMonth() / 3 * 3, 1);
	}

	/**
	 * 取得这个季度的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfQuarter(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) / 3 * 3);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 取得这个季度的最后一天
	 *
	 * @param date
	 * @return 季度的最后一天
	 * @deprecated replaced by <code>getLastDayOfQuarter</code>
	 */
	@Deprecated
	public static Date getLastDayOfQuote(Date date) {
		return new Date(date.getYear(), (date.getMonth() / 3 + 1) * 3, 0);
	}

	/**
	 * 取得这个季度的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfQuarter(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, (c.get(Calendar.MONTH) / 3 + 1) * 3);
		c.set(Calendar.DAY_OF_MONTH, 0);
		return c.getTime();
	}

	/**
	 * 取得这个年度的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 取得这个年度的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, Calendar.DECEMBER);
		c.set(Calendar.DAY_OF_MONTH, 31);
		return c.getTime();
	}

	@SuppressWarnings("deprecation")
	public static Date parseDate(String dateStr) {
		String part[] = dateStr.split("-");
		if (part.length != 3)
			return null;

		int year = Integer.parseInt(part[0]) - 1900;
		int month = Integer.parseInt(part[1]) - 1;
		int day = Integer.parseInt(part[2]);
		if (year < 0 || year > 8000 || month < 0 || month > 11 || day < 0 || day > 31)
			return null;

		return new Date(year, month, day);
	}

	@SuppressWarnings("deprecation")
	public static int compare(Date first, Date second) {
		if (first.getYear() > second.getYear())
			return 1;
		else if (first.getYear() < second.getYear())
			return -1;

		if (first.getMonth() > second.getMonth())
			return 1;
		else if (first.getMonth() < second.getMonth())
			return -1;

		if (first.getDate() > second.getDate())
			return 1;
		else if (first.getDate() < second.getDate())
			return -1;
		else
			return 0;
	}

	/**
	 * 获得以给定日期为基准的绝对日期（时间区间的数学运算）
	 *
	 * @param now
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date getAbsoluteDate(Date now, int field, int amount) {
	    if (now == null) {
	        return null;
        }
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(field, amount);
		return c.getTime();
	}

	/**
	 * 获得以给定日期为基准的绝对日期（指定某一时间区间值）
	 *
	 * @param now
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date setAbsoluteDate(Date now, int field, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(field, amount);
		return c.getTime();
	}

	/**
	 * <p>
	 * 得到一天的开始时间
	 * </p>
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayStartDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * <p>
	 * 得到一天的结束时间
	 * </p>
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayEndDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/** 获取当前年份 */
	public static Integer getThisYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * <p>
	 * 时间计算 返回的是一个 yyyy-MM-dd格式的字符串
	 * </p>
	 *
	 * @param item
	 *            计算的栏目 只能是 day,month或year
	 * @param value
	 *            计算值
	 * @param beginTime
	 *            基准时间 为空的话默认为当前时间
	 * @return
	 */
	public static Object dateAdd(String item, int value, Date beginTime) {

		if (beginTime == null)
			beginTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginTime);
		if (item.equals("day")) {
			int day = calendar.get(Calendar.DAY_OF_YEAR);
			calendar.set(Calendar.DAY_OF_YEAR, day + value);
			return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
		} else if (item.equals("month")) {
			int month = calendar.get(Calendar.MONTH);
			calendar.set(Calendar.MONTH, month + value);
			return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
		} else if (item.equals("year")) {
			int year = calendar.get(Calendar.YEAR);
			calendar.set(Calendar.YEAR, year + value);
			return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
		}
		return null;
	}

	/**
	 * <p>
	 * 时间计算 返回的是一个 yyyy-MM-dd格式的字符串 前几天、几月、几年、
	 * </p>
	 *
	 * @param item
	 *            计算的栏目 只能是 day,month或year
	 * @param value
	 *            计算值
	 * @param beginTime
	 *            基准时间 为空的话默认为当前时间
	 * @return
	 */
	public static Object dateSub(String item, int value, Date beginTime) {

		if (beginTime == null)
			beginTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginTime);
		if (item.equals("day")) {
			int day = calendar.get(Calendar.DAY_OF_YEAR);
			calendar.set(Calendar.DAY_OF_YEAR, day - value);
			return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
		} else if (item.equals("month")) {
			int month = calendar.get(Calendar.MONTH);
			calendar.set(Calendar.MONTH, month - value);
			return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
		} else if (item.equals("year")) {
			int year = calendar.get(Calendar.YEAR);
			calendar.set(Calendar.YEAR, year - value);
			return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
		}
		return null;
	}

	/**
	 * <p>
	 * 按51法则计算参数日期最近的一个年报发布日期返回的是一个 日期
	 * </p>
	 *
	 * @param date
	 *            基准时间 为空的话默认为当前时间
	 * @return
	 */
	public static Date getAnnalsDate(Date date) {

		if (date == null)
			date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		if (month < 5) {
			calendar.set(year - 2, 12 - 1, 31);
		} else if (month >= 5) {
			calendar.set(year - 1, 12 - 1, 31);
		}
		return calendar.getTime();
	}

	// 获得年份
	public static int getYear(Date date) {

		if (date == null)
			date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	// 获得月份
	public static int getMonth(Date date) {

		if (date == null)
			date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	// 获得日
	public static int getDay(Date date) {

		if (date == null)
			date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 得到几天前的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		if(d==null) {
			return null;
		}
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间包括小时分钟
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateTimeAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 计算两个时间相差多少分钟
	 *
	 * @param nowDate
	 *            当前的时间（被减数）
	 * @param date
	 *            以前的时间（减数）
	 */
	public static int dateToDateMinute(Date nowDate, Date date) {
		if (nowDate == null || date == null) {
			return 0;
		}
		if (nowDate.getTime() < date.getTime()) {
			return 0;
		}
		long livetime = nowDate.getTime() - date.getTime();
		int minute = (int) (livetime / (60 * 1000));
		return minute;
	}

	/**
	 * 计算两个时间相差多少秒
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static long dateDiffSecound(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数long diff;try {
        // 获得两个时间的毫秒时间差异
        long diff = 0L;
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long day = diff / nd;// 计算差多少天
        long hour = diff % nd / nh;// 计算差多少小时
        long min = diff % nd % nh / nm;// 计算差多少分钟
        long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
        return day*24*60*60 + hour*60*60 + min*60 + sec;
    }

	public static String addtime(String timeStr,String addnumber){
        String str=null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(timeStr);
            //时间累计
            Calendar gc =new GregorianCalendar();
            gc.setTime(date);
            gc.add(GregorianCalendar.MINUTE,Integer.parseInt(addnumber));
            str=  df.format(gc.getTime());
        }catch (Exception e){
        }
        return str;
    }

	/**
	 *
	 * Created on 2014年9月24日
	 * <p>
	 * Discription:[两个日期相差多少天多少分多少秒]
	 * </p>
	 *
	 * @author:[马宗飞]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return String .
	 */
	public static String dateDiff(String startTime, String endTime, String format) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数long diff;try {
		// 获得两个时间的毫秒时间差异
		long diff = 0L;
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long day = diff / nd;// 计算差多少天
		long hour = diff % nd / nh;// 计算差多少小时
		long min = diff % nd % nh / nm;// 计算差多少分钟
		long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
		return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
	}

	/**
	 *
	 * Created on 2014年9月24日
	 * <p>
	 * Discription:[获得标的结束日期字符串形式]
	 * </p>
	 *
	 * @author:[马宗飞]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return String .
	 */
	public static String getDateStr(Date dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss");
		return format.format(dateStr);
	}

	/**
	 *
	 * Created on 2014年9月24日
	 * <p>
	 * Discription:[两个日期相差秒数]
	 * </p>
	 *
	 * @author:[马宗飞]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return int .
	 */
	public static int getLastedTime(Date startDate, Date endDate) {
		long start = startDate.getTime();
		long end = endDate.getTime();
		int time = (int) ((start - end) / 1000);
		return time;
	}

	/**
	 * 计算两个时间相差多少月
	 *
	 * @param nowDate
	 *            当前的时间（被减数）
	 * @param date
	 *            以前的时间（减数）
	 */
	public static int dateToDateMouth(Date startDate, Date endDate) {
		if (startDate == null) {
			return 0;
		}
		if (endDate == null) {
			return 0;
		}
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(startDate);
		c2.setTime(endDate);
		int liveMouth = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
		return liveMouth == 0 ? 1 : Math.abs(liveMouth);
	}

	/**
	 * 计算两个时间相差多少周
	 *
	 * @param nowDate
	 *            当前的时间（被减数）
	 * @param date
	 *            以前的时间（减数）
	 */
	public static int dateToDateWeek(Date startDate, Date endDate) {
		if (startDate == null) {
			return 0;
		}
		if (endDate == null) {
			return 0;
		}
		Calendar calst = Calendar.getInstance();
		;
		Calendar caled = Calendar.getInstance();
		calst.setTime(startDate);
		caled.setTime(endDate);
		// 设置时间为0时
		calst.set(Calendar.HOUR_OF_DAY, 0);
		calst.set(Calendar.MINUTE, 0);
		calst.set(Calendar.SECOND, 0);
		caled.set(Calendar.HOUR_OF_DAY, 0);
		caled.set(Calendar.MINUTE, 0);
		caled.set(Calendar.SECOND, 0);
		// 得到两个日期相差的week
		int liveWeek = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24
				/ 7;
		return liveWeek == 0 ? 1 : liveWeek;
	}

	/**
	 * 计算两个时间相差多少天
	 *
	 * @param nowDate
	 *            当前的时间（被减数）
	 * @param date
	 *            以前的时间（减数）
	 */
	public static int dateToDateDay(Date startDate, Date endDate) {
		if (startDate == null) {
			return 0;
		}
		if (endDate == null) {
			return 0;
		}
		Calendar calst = Calendar.getInstance();
		;
		Calendar caled = Calendar.getInstance();
		calst.setTime(startDate);
		caled.setTime(endDate);
		// 设置时间为0时
		calst.set(Calendar.HOUR_OF_DAY, 0);
		calst.set(Calendar.MINUTE, 0);
		calst.set(Calendar.SECOND, 0);
		caled.set(Calendar.HOUR_OF_DAY, 0);
		caled.set(Calendar.MINUTE, 0);
		caled.set(Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int liveDay = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;
		return liveDay;
	}

	/**
	 * 计算两个时间相差多少天
	 *
	 * @param nowDate
	 *            当前的时间（被减数）
	 * @param date
	 *            以前的时间（减数）
	 */
	public static int dateToDateDay2(Date startDate, Date endDate) {
		if (startDate == null) {
			return 0;
		}
		if (endDate == null) {
			return 0;
		}
		Calendar calst = Calendar.getInstance();
		;
		Calendar caled = Calendar.getInstance();
		calst.setTime(startDate);
		caled.setTime(endDate);
		// 设置时间为0时
		calst.set(Calendar.HOUR_OF_DAY, 0);
		calst.set(Calendar.MINUTE, 0);
		calst.set(Calendar.SECOND, 0);
		caled.set(Calendar.HOUR_OF_DAY, 0);
		caled.set(Calendar.MINUTE, 0);
		caled.set(Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int liveDay = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;
		return liveDay;
	}

	/**
	 * 得到N月份后的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfterMouth(Date d, int month) {
		if (d != null) {
			Calendar now = Calendar.getInstance();
			now.setTime(d);
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
			return now.getTime();
		}
		return null;
	}

	/**
	 *
	 * Created on 2014年11月21日
	 * <p>
	 * Discription:[当月前N个月]
	 * </p>
	 *
	 * @author:[杨龙平]
	 * @update:[2014年11月21日] [杨龙平]
	 * @return Date .
	 */
	public static Date getDateBeforeMouth(Date d, int month) {
		if (d != null) {
			Calendar now = Calendar.getInstance();
			now.setTime(d);
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			now.set(Calendar.MONTH, now.get(Calendar.MONTH) - month);
			return now.getTime();
		}
		return null;
	}

	/**
	 *
	 * Created on 2014年11月21日
	 * <p>
	 * Discription:[当年N年前今天]
	 * </p>
	 *
	 * @author:[杨龙平]
	 * @update:[2014年11月21日] [杨龙平]
	 * @return Date .
	 */
	public static Date getDateBeforeYear(Date d, int year) {
		if (d != null) {
			Calendar now = Calendar.getInstance();
			now.setTime(d);
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			now.set(Calendar.YEAR, now.get(Calendar.YEAR) - year);
			return now.getTime();
		}
		return null;
	}

	/**
	 * 根据时间格式，及时间参数判断改格式是否是该时间格式
	 *
	 * @param str
	 * @param dateFormatStr
	 * @return
	 */
	public static boolean isValidDate(String str, String dateFormatStr) {
		boolean convertSuccess = true;
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(dateFormatStr)) {
			SimpleDateFormat format = new SimpleDateFormat(dateFormatStr);
			try {
				format.setLenient(false);
				format.parse(str);
			} catch (ParseException e) {
				convertSuccess = false;
			}
		}
		return convertSuccess;
	}

	/**
	 * 获取当前日期月份的第一天
	 *
	 * @return String
	 */
	public static String getCurrentMonthFirstDay() {
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = dateFormat.format((cal_1.getTime()));
		return firstDay;

	}

	/**
	 * 获取当前日期月份的第一天
	 *
	 * @return date
	 */
	public static Date getCurrentMonthFirstDate() {
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cal_1.set(Calendar.HOUR_OF_DAY, 0);
		cal_1.set(Calendar.MINUTE, 0);
		cal_1.set(Calendar.SECOND, 0);
		cal_1.set(Calendar.MILLISECOND, 0);
		return cal_1.getTime();

	}

	/**
	 * 获取当前日期月份的最后一天
	 *
	 * @return string
	 */
	public static String getCurrentMonthLastDay() {
		// 获取前月的最后一天
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = dateFormat.format(cale.getTime());
		return lastDay;
	}

	/**
	 * 获取前月的最后一天
	 *
	 * @return date
	 */
	public static Date getCurrentMonthLastDate() {
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cale.getTime();
	}

	/**
	 *
	 * Created on 2015-5-17
	 * <p>
	 * Discription:[格式化成: xx月xx日 xx:xx:xx]
	 * </p>
	 *
	 * @author:[薛祺]
	 * @return
	 * @update:[日期2015-5-17] [薛祺]
	 * @return void .
	 */
	public static String fomartDate(Date date) {
		if (date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			String month = String.valueOf(c.get(Calendar.MONTH) + 1);
			String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
			String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
			String minutes = String.valueOf(c.get(Calendar.MINUTE));
			String second = String.valueOf(c.get(Calendar.SECOND));
			if (Integer.parseInt(month) < 10)
				month = "0" + month;
			if (Integer.parseInt(day) < 10)
				day = "0" + day;
			if (Integer.parseInt(hour) < 10)
				hour = "0" + hour;
			if (Integer.parseInt(minutes) < 10)
				minutes = "0" + minutes;
			if (Integer.parseInt(second) < 10)
				second = "0" + second;

			return month + "月" + day + "日 " + hour + ":" + minutes + ":" + second;
		}
		return "";
	}

	/**
	 *
	 * Created on 2015-5-17
	 * <p>
	 * Discription:[格式化成: xx月xx日 xx:xx]
	 * </p>
	 *
	 * @author:[薛祺]
	 * @return
	 * @update:[日期2015-5-17] [薛祺]
	 * @return void .
	 */
	public static String fomartDate2YMHM(Date date) {
		if (date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			String month = String.valueOf(c.get(Calendar.MONTH) + 1);
			String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
			String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
			String minutes = String.valueOf(c.get(Calendar.MINUTE));
			if (Integer.parseInt(month) < 10)
				month = "0" + month;
			if (Integer.parseInt(day) < 10)
				day = "0" + day;
			if (Integer.parseInt(hour) < 10)
				hour = "0" + hour;
			if (Integer.parseInt(minutes) < 10)
				minutes = "0" + minutes;

			return month + "月" + day + "日 " + hour + ":" + minutes;
		}
		return "";
	}

	/**
	 *
	 * Created on 2015-5-17
	 * <p>
	 * Discription:[格式化成: xx月xx日]
	 * </p>
	 *
	 * @author:[薛祺]
	 * @return
	 * @update:[日期2015-5-17] [薛祺]
	 * @return void .
	 */
	public static String fomartDate2YM(Date date) {
		if (date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			String month = String.valueOf(c.get(Calendar.MONTH) + 1);
			String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
			if (Integer.parseInt(month) < 10)
				month = "0" + month;
			if (Integer.parseInt(day) < 10)
				day = "0" + day;

			return month + "月" + day + "日";
		}
		return "";
	}

	/**
	 * <p>
	 * 时间计算 返回的是一个 yyyy-MM-dd格式的字符串
	 * </p>
	 *
	 * @param item
	 *            计算的栏目 只能是 day,month或year
	 * @param value
	 *            计算值
	 * @param beginTime
	 *            基准时间 为空的话默认为当前时间
	 * @return
	 */
	public static Object dateAddType2(String item, int value, Date beginTime) {

		if (beginTime == null)
			beginTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginTime);
		if (item.equals("day")) {
			int day = calendar.get(Calendar.DAY_OF_YEAR);
			calendar.set(Calendar.DAY_OF_YEAR, day + value);

			int month = calendar.get(Calendar.MONTH) + 1;
			String monthStr = month < 10 ? "0" + month : String.valueOf(month);

			day = calendar.get(Calendar.DAY_OF_MONTH);
			String dayStr = day < 10 ? "0" + day : String.valueOf(day);

			return calendar.get(Calendar.YEAR) + "-" + monthStr + "-" + dayStr;
		} else if (item.equals("month")) {
			int month = calendar.get(Calendar.MONTH);
			calendar.set(Calendar.MONTH, month + value);
			return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
		} else if (item.equals("year")) {
			int year = calendar.get(Calendar.YEAR);
			calendar.set(Calendar.YEAR, year + value);
			return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
		}
		return null;
	}

	public static void main(String[] args) {
		//2017.08.15 12:06:31
		//1502768798
		//1503557039363

		Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        if(c.get(Calendar.HOUR_OF_DAY) >= 3 || c.get(Calendar.HOUR_OF_DAY) <= 11){
        	c.set(Calendar.HOUR_OF_DAY, 15);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
        }
        c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);

        System.out.println(DateUtil.dateFullTimeFormat.format(c.getTime()));


		System.out.println(getDateAfter(new Date(), 1));
		System.out.println(dateToDateDay(new Date(), getDateAfter(new Date(), 2)));
		System.out.println(System.currentTimeMillis());
		System.out.println(timestampToDateStr(1502798791000L));
		System.out.println(getYesterday());

		// String xString= DateUtil.dateDiff("2014-09-19 09:56:11", "2014-09-20
		// 10:54:12", "yyyy-MM-dd hh:mm:ss");
		// System.out.println(xString);
		try {
			// Date s = DateUtil.dateFormat.parse(DateUtil.dateFormat.format(new
			// Date()));
			// System.out.println(s);
			// System.out.println(dateToDateWeek(dateFormat.parse("2014-11-20"),dateFormat.parse("2014-12-04")));
			// System.out.println(getCurrentMonthFirstDay());
			// System.out.println(getCurrentMonthLastDay());
			// System.out.println(DateUtil.dateFormat.parse(String.valueOf(DateUtil.dateAdd("day",
			// 7, new Date()))));
			// System.out.println(getCurrentMonthFirstDay());
			// System.out.println(DateUtil.dateFormat.format(DateUtil.subDate(new
			// Date(), Calendar.DAY_OF_YEAR, -1)));
			System.out.println(DateUtil.dateFormatYMDZoreH.format(DateUtil.getDateAfter(new Date(), 1)));

			Long[] array = new Long[41];
			Long tmp = -10L;
			for (int i = 0; i <= 10; i++) {
				array[i] = tmp + i;
			}
			tmp = 0L;
			for (int i = 11; i <= 40; i++) {
				array[i] = ++tmp;
			}
			System.out.println(array.length);

			System.err.println(DateUtil.dateAddType2("day", 5, new Date()));

			/*
			 * for(int i=0;i<100;i++){ Random r = new Random();
			 * System.out.println(r.nextInt(11)); }
			 */

			System.out.println(getCurrentDayWeek());

			c = Calendar.getInstance();
        	c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, 11);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

			System.out.println(DateFormatTools.jumpOneDay(c.getTime(), 2));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取当前日期是星期几
	 */
	public static int getCurrentDayWeek() {
		Integer[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取当前日期，下午整3点时间
	 */
	public static Date getCurrentFixationFifteenDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 15);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取传入日期是星期几
	 */
	public static int getDateWeek(Date date) {
		Integer[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * Created on 2015-12-17
	 * <p>
	 * Discription:[比较日期]
	 * </p>
	 *
	 * @author:[苗媛]
	 * @update:[日期2015-12-17] [苗媛]
	 * @return int .
	 */
	public static int compareDate(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static int compareDate(Date dt1, Date dt2) {
		try {
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取某年某月的第一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFisrtDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfMonth = sdf.format(cal.getTime());

		return firstDayOfMonth;
	}

	/**
	 * 获取某年某月的第一天
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最小天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());

		return lastDayOfMonth;
	}

	/**
	 * 转话字符串日期为Date类型
	 *  Created on 2016年4月14日
	 * <p>Discription:[方法功能中文描述]</p>
	 * @author:[何俊杰]
	 * @update:[日期2016年4月14日] [何俊杰]
	 * @return Date .
	 */
	public static Date parseStrToDate(String dateStr)  {
		Date date=null;
		SimpleDateFormat simpleDateFormat=null;
		StringBuffer pattern=new StringBuffer();
	    if(StringUtils.isNotBlank(dateStr)){
	    	String[] strWithBlank=dateStr.split(" ");
	        for (int i = 0; i < strWithBlank.length; i++) {
				if(i==0){
					String[] str=strWithBlank[0].split("-");
					for (int j = 0; j < str.length; j++) {
						switch (j) {
						case 0:
							pattern.append("yyyy");
							break;
						case 1:
							pattern.append("-MM");
							break;
						case 2:
							pattern.append("-dd");
							break;
						default:
							break;
						}
					}
				}else if(i==1){
					String[] str=strWithBlank[1].split(":");
					for (int j = 0; j < str.length; j++) {
						switch (j) {
						case 0:
							pattern.append(" ");
							pattern.append("HH");
							break;
						case 1:
							pattern.append(":mm");
							break;
						case 2:
							pattern.append(":ss");
							break;
						default:
							break;
						}
					}
				}

			}

	    }else{
	    	return null;
	    }
	    simpleDateFormat = new SimpleDateFormat(pattern.toString());
		simpleDateFormat.setLenient(false);
		try{
		return simpleDateFormat.parse(dateStr);
		}catch(ParseException e){
	       throw new IllegalArgumentException("转换日期异常："+e.getMessage() , e);
		}
	}

	/**
	 * 每个月第一个星期五的日期
	 * @param date 日期
	 * @return Date 日期
	 */
	public static Date getFirstFridayOfMonth(Date date)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.DATE, 1); // 设为第一天
	    while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY)
	    {
	        cal.add(Calendar.DATE, 1);
	    }
	    return cal.getTime();
	}

	/**
     *
     *  Created on 2017年6月18日
     * <p>Discription:[ 根据下单时间,选择期数获取非农日 ]</p>
     * @author:[武勇吉]
     * @update:[日期2017年6月18日] [武勇吉]
     * @return Date .
     */
    public static Date getNonfarmDayExpDate(Date orderDate, int periodsCount){
    	Date expDate = null;
    	//判断当前日期是否已过非农日
    	//获取下单月非农日
    	Date nonfarmDay = DateUtil.getFirstFridayOfMonth(orderDate);
    	//当下单日小于当期月非农日,则包含当期周期
    	if (-1 == DateUtil.compareDate(orderDate, nonfarmDay)) {
			periodsCount -= 1;
		};
    	Date tempDate = DateUtil.subDate(orderDate, Calendar.MONTH, periodsCount);
    	expDate = DateUtil.getFirstFridayOfMonth(tempDate);
    	//下单日期,推后 用户选择期数,
    	return expDate;
    }

    /**
     *
     *  Created on 2017年6月18日
     * <p>Discription:[ 根据下单时间,选择期数获取非农日 ]</p>
     * @author:[武勇吉]
     * @update:[日期2017年6月18日] [武勇吉]
     * @return Date .
     */
    public static Date getNextNonfarmDay(Date date){
    	//判断当前日期是否已过非农日
    	//获取下单月非农日
    	Date nonfarmDay = DateUtil.getFirstFridayOfMonth(date);
    	//当下单日小于当期月非农日,则返回当前月非农日
    	if (-1 == DateUtil.compareDate(date, nonfarmDay)) {
    		return nonfarmDay;
		};
		//下月非农日
		Date tempDate = DateUtil.subDate(date, Calendar.MONTH, 1);
    	return DateUtil.getFirstFridayOfMonth(tempDate);
    }

    public static String timestampToDateStr(Long timestamp){

    	if(timestamp != null){
    		String timestampStr = timestamp.toString();
    		if(timestampStr.length() == 10){
    			timestampStr += "000";
    		}

    		timestamp = Long.parseLong(timestampStr);
    		java.sql.Timestamp ts = new java.sql.Timestamp(timestamp);
        	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	return sdf.format(ts);
    	}

    	return "";
    }

    /**
     *  Created on 2017-12-18
     * <p>Title:[获取昨天日期]</p>
     * @author:[叶东平]
     * @update:[日期2017-12-18] [叶东平]
     * @return String .
     */
    public static String getYesterday(){
    	Calendar cal=Calendar.getInstance();
    	cal.add(Calendar.DATE,-1);
    	Date time=cal.getTime();
    	return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    public static Date getStrToDate(String str){
    	try {
			if (str != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return sdf.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }

    public static String getMinInDay(Date source) {
    	if (source == null) {
    		return null;
		}
		return dateTimeZoreFormat.format(source);
	}

	public static String getMaxInDay(Date source) {
		if (source == null) {
			return null;
		}
		return dateTimeMaxSMFormat.format(source);
	}

}
