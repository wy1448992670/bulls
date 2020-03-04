
package com.goochou.p2b.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;



/**
 * 
 * Created on 2014-8-26
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [张永效] [1055148679@qq.com]
 * @version        1.0
 */
public class DateTimeUtil
{ // 服务器的时间

	
	private Calendar calendar = null;
	/**
	 * 日历为全模板 yyyy-MM-dd HH:mm:ss
	 */
	final static public String allPattern = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日历为分钟模板 yyyy-MM-dd HH:mm
	 */
	final static public String minutePattern = "yyyy-MM-dd HH:mm";
	/**
	 * 日历为小时模板 yyyy-MM-dd HH
	 */
	final static public String hourPattern = "yyyy-MM-dd HH";
	/**
	 * 日历为天模板 yyyy-MM-dd
	 */
	final static public String dayPattern = "yyyy-MM-dd";
	/**
	 * 日历为月模板 yyyy-MM"
	 */
	final static public String monthPattern = "yyyy-MM";
	/**
	 * 日历为年模板 yyyy
	 */
	final static public String yearPattern = "yyyy";
	
	/**
	 * 时间格式模板为HH:mm:ss
	 */
	final static public String dateFHMS = "HH:mm:ss";
	/**
	 * 毫秒类型
	 */
	final static public int msType = 0;// 毫秒类型
	/**
	 * 秒类型
	 */
	final static public int scondType = 1;
	/**
	 * 分钟类型
	 */
	final static public int minuteType = 2;
	/**
	 * 小时类型
	 */
	final static public int hourType = 3;
	/**
	 * 天类型
	 */
	final static public int dayType = 4;
	/**
	 * 周类型
	 */
	final static public int weekType = 5;
	/**
	 * 月类型
	 */
	final static public int monthType = 6;
	/**
	 * 年类型
	 */
	final static public int yearType = 7;

	//格式：2007年06月07日 12时12分12秒234毫秒
	private final static String[] FORMAT_CHINA = {"年","月","日","时","分","秒","毫秒"};
	//格式：2007-06-07  12:12:12 234
	private final static String[] FORMAT_NORMAL = {"-","-","",":",":","",""};
	//格式：2007/06/07 12:12:12 234
	private final static String[] FORMAT_DATATIME = {"/","/","",":",":","",""};
	//格式：中文星期
	@SuppressWarnings("unused")
    private final static String[] FORMAT_WEEK = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	
	/**
	 * 年月日时分秒格式化
	 */
	public final static SimpleDateFormat FORMAT_YMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 年月日格式化
	 */
	public final static SimpleDateFormat FORMAT_YMD = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 构造函数，初始化时间发生器（无参数：当前时间）
	 */
	public DateTimeUtil()
	{
		calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
	}

	/**
	 * 构造函数：初始化时间发生器（带参数: 长整型时间数据：毫秒）
	 */
	public DateTimeUtil(long serverDateTime)
	{
		calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendar.setTimeInMillis(serverDateTime); // 指定时间
	}

	/**
	 * 构造函数：初始化时间发生器（带参数: 长整型时间数据）
	 */
	public DateTimeUtil(Date DateTime)
	{
		calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendar.setTimeInMillis(DateTime.getTime()); // 指定时间
	}

	/**
	 * 构造函数：初始化时间发生器（带参数:Calendar类实例）
	 */
	public DateTimeUtil(Calendar cal)
	{
		calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		calendar = cal;
	}

	/**
	 * 构造函数：初始化时间发生器（带参数:DateTime） 参数格式：yyyy-MM-dd HH:mm:ss or yyyy-MM-dd
	 */
	public DateTimeUtil(String DateTime)
	{
		calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		SimpleDateFormat s = null;
		Date date = null;
		try
		{
			if (DateTime.length() > 12)
			{
				s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			else
			{
				s = new SimpleDateFormat("yyyy-MM-dd");
			}
			date = new Date();
			try
			{
				date = s.parse(DateTime);
			}
			catch (ParseException e)
			{
				System.out.println("e:" + e.getMessage());
			}
		}
		catch (Exception ee)
		{
			System.out.println("ee:" + ee.getMessage());
		}
		calendar.setTime(date);
	}

	/**
	 * 构造函数：初始化时间发生器（带参数:DateTime） 参数格式：yyyy-MM-dd HH:mm:ss or yyyy-MM-dd
	 */
	public DateTimeUtil(String DateTime, String DateFormat)
	{
		calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		SimpleDateFormat s = null;
		Date date = null;
		try
		{
			s = new SimpleDateFormat(DateFormat);
			date = new Date();
			try
			{
				date = s.parse(DateTime);
			}
			catch (ParseException e)
			{
				System.out.println("e:" + e.getMessage());
			}
		}
		catch (Exception ee)
		{
			System.out.println("ee:" + ee.getMessage());
		}
		calendar.setTime(date);
	}

	/**
	 * 取日期-时间 yyyy-mm-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public String getLongDate()
	{
		return this.getDate() + " " + this.getTime();
	}

	/**
	 * 取日期-时间 yyyymmddHHmmss
	 * 
	 * @return String
	 */
	public String getLongDateChar()
	{
		return this.getShortDate() + this.getShortTime();
	}

	/**
	 * 取时间型日期
	 * 
	 * @return Date
	 */
	public Date getDateTime()
	{
		return calendar.getTime();
	}

	/**
	 * 取日期 yyyy-mm-dd
	 * 
	 * @return String
	 */
	public String getDate()
	{
		String months , days , years;
		years = String.valueOf(this.getYear());
		if (this.getMonth() < 10)
		{
			months = "0" + this.getMonth();
		}
		else
		{
			months = String.valueOf(this.getMonth());
		}
		if (this.getDay() < 10)
		{
			days = "0" + this.getDay();
		}
		else
		{
			days = String.valueOf(this.getDay());
		}
		return years + "-" + months + "-" + days;
	}

	/**
	 * 以指定日期格式展示出来日期字符串
	 * 
	 * @param pattern
	 * @return
	 */
	public String getDatePatternStr(String pattern)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(this.getDateTime());
	}

	/**
	 * 取日期 yyyy-m-d add by benyp
	 * 
	 * @return String
	 */
	public String getDate2()
	{
		String months , days , years;
		years = String.valueOf(this.getYear());
		months = String.valueOf(this.getMonth());
		days = String.valueOf(this.getDay());
		return years + "-" + months + "-" + days;
	}

	/**
	 * 取日期 yyyymmdd
	 * 
	 * @return String
	 */
	public String getShortDate()
	{
		String months , days , years;
		years = String.valueOf(this.getYear());
		if (this.getMonth() < 10)
		{
			months = "0" + this.getMonth();
		}
		else
		{
			months = String.valueOf(this.getMonth());
		}
		if (this.getDay() < 10)
		{
			days = "0" + this.getDay();
		}
		else
		{
			days = String.valueOf(this.getDay());
		}
		return years + "" + months + "" + days;
	}

	/**
	 * 取时间 hh:mm:ss
	 * 
	 * @return String
	 */
	public String getTime()
	{
		String hours , minutes , seconds;
		if (this.getHour() < 10)
		{
			hours = "0" + this.getHour();
		}
		else
		{
			hours = String.valueOf(this.getHour());
		}
		if (this.getMinute() < 10)
		{
			minutes = "0" + this.getMinute();
		}
		else
		{
			minutes = String.valueOf(this.getMinute());
		}
		if (this.getSecond() < 10)
		{
			seconds = "0" + this.getSecond();
		}
		else
		{
			seconds = String.valueOf(this.getSecond());
		}
		return hours + ":" + minutes + ":" + seconds;
	}

	/**
	 * 取时间 hhmmss
	 * 
	 * @return String
	 */
	public String getShortTime()
	{
		String hours , minutes , seconds;
		if (this.getHour() < 10)
		{
			hours = "0" + this.getHour();
		}
		else
		{
			hours = String.valueOf(this.getHour());
		}
		if (this.getMinute() < 10)
		{
			minutes = "0" + this.getMinute();
		}
		else
		{
			minutes = String.valueOf(this.getMinute());
		}
		if (this.getSecond() < 10)
		{
			seconds = "0" + this.getSecond();
		}
		else
		{
			seconds = String.valueOf(this.getSecond());
		}
		return hours + minutes + seconds;
	}

	/**
	 * 取年 yyyy
	 * 
	 * @return int
	 */
	public int getYear()
	{
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 取月 mm
	 * 
	 * @return int
	 */
	public int getMonth()
	{
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 取日 dd
	 * 
	 * @return int
	 */
	public int getDay()
	{
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取小时 hh
	 * 
	 * @return int
	 */
	public int getHour()
	{
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 取分钟 mm
	 * 
	 * @return int
	 */
	public int getMinute()
	{
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 取秒钟 ss
	 * 
	 * @return int
	 */
	public int getSecond()
	{
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 取从1970年1月1日以来的时间（秒）
	 * 
	 * @return long
	 */
	public long getLongTime()
	{
		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 取从当前日期的偏移日期:年份
	 * 
	 * @param years
	 * @return int
	 */
	public int getNextYear(int years)
	{
		calendar.add(Calendar.YEAR, years);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 取从当前日期的偏移日期:月份
	 * 
	 * @param months
	 * @return int
	 */
	public int getNextMonth(int months)
	{
		calendar.add(Calendar.MONTH, months);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回选定日期的周
	 * 
	 * @param week
	 * @return
	 */
	public int getNextWeek(int week)
	{
		calendar.add(Calendar.WEEK_OF_YEAR, week);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 取从当前日期的偏移日期:日期
	 * 
	 * @param dates
	 * @return int Calendar.DAY_OF_MONTH
	 */
	public int getNextDate(int dates)
	{
		calendar.add(Calendar.DATE, dates);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取从当前日期偏移到的日期:具体时间值字符串
	 * 
	 * @param dateType
	 * @param timeNumber
	 * @return getLongDate()
	 */
	public String getNextDateTime(String dateType, int timeNumber)
	{
		if (dateType.equals("year"))
		{
			calendar.add(Calendar.YEAR, timeNumber);
		}
		else if (dateType.equals("month"))
		{
			calendar.add(Calendar.MONTH, timeNumber);
		}
		else if (dateType.equals("day"))
		{
			calendar.add(Calendar.DATE, timeNumber);
		}
		else if (dateType.equals("hour"))
		{
			calendar.add(Calendar.HOUR, timeNumber);
		}
		else if (dateType.equals("minute"))
		{
			calendar.add(Calendar.MINUTE, timeNumber);
		}
		else if (dateType.equals("second"))
		{
			calendar.add(Calendar.SECOND, timeNumber);
		}
		return this.getLongDate();
	}

	/**
	 * 取从当前日期偏移到的日期:具体时间值字符串 add by benyp(5260)：返回的时间不含0 如:2008-3-1 10:1:1
	 * 
	 * @param dateType
	 * @param timeNumber
	 * @return getLongDate()
	 */
	public String getNextDateTimeWithoutZero(String dateType, int timeNumber)
	{
		if (dateType.equals("year"))
		{
			calendar.add(Calendar.YEAR, timeNumber);
		}
		else if (dateType.equals("month"))
		{
			calendar.add(Calendar.MONTH, timeNumber);
		}
		else if (dateType.equals("day"))
		{
			calendar.add(Calendar.DATE, timeNumber);
		}
		else if (dateType.equals("hour"))
		{
			calendar.add(Calendar.HOUR, timeNumber);
		}
		else if (dateType.equals("minute"))
		{
			calendar.add(Calendar.MINUTE, timeNumber);
		}
		else if (dateType.equals("second"))
		{
			calendar.add(Calendar.SECOND, timeNumber);
		}
		return (this.getDate2() + " " + this.getHour() + ":" + this.getMinute() + ":" + this
				.getSecond());
	}

	/**
	 * 取从当前日期偏移到的日期:具体时间值字符串
	 * 
	 * @param dateType
	 * @param timeNumber
	 * @return getDate
	 */
	public String getNextDate(String dateType, int timeNumber)
	{
		if (dateType.equals("year"))
		{
			calendar.add(Calendar.YEAR, timeNumber);
		}
		else if (dateType.equals("month"))
		{
			calendar.add(Calendar.MONTH, timeNumber);
		}
		else if (dateType.equals("day"))
		{
			calendar.add(Calendar.DATE, timeNumber);
		}
		else if (dateType.equals("hour"))
		{
			calendar.add(Calendar.HOUR, timeNumber);
		}
		else if (dateType.equals("minute"))
		{
			calendar.add(Calendar.MINUTE, timeNumber);
		}
		else if (dateType.equals("second"))
		{
			calendar.add(Calendar.SECOND, timeNumber);
		}
		return this.getDate();
	}

	/**
	 * 获得特定日期是当年的第几天
	 * 
	 * @return int
	 */
	public int getNoDayOfYear()
	{
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 计算两个日期相隔的天数
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return int
	 */
	public static int nDaysBetweenTwoDate(Date firstDate, Date secondDate)
	{
		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	/**
	 * 计算两个日期相隔的天数
	 * 
	 * @param firstString
	 * @param secondString
	 * @return int
	 */
	public static int nDaysBetweenTwoDate(String firstString, String secondString)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDate = null;
		Date secondDate = null;
		try
		{
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		}
		catch (Exception e)
		{
			// 日期型字符串格式错误
		}
		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	/**
	 * 计算两个日期相隔的天数
	 * 
	 * @param firstString
	 * @param secondString
	 * @return int
	 */
	public static int nWeeksBetweenTwoDate(String firstString, String secondString)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDate = null;
		Date secondDate = null;
		try
		{
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		}
		catch (Exception e)
		{
			// 日期型字符串格式错误
		}
		int nWeek = (int) ((secondDate.getTime() - firstDate.getTime()) / (7 * 24 * 60 * 60 * 1000));
		return nWeek;
	}

	/**
	 * 返回特定日期处于一年中的第几周 周的定义：星期一到星期日
	 * 
	 * @return int
	 */
	public int getNoWeekOfYear()
	{
		calendar.add(Calendar.DATE, -1);
		int weeks = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.add(Calendar.DATE, 1);
		return weeks;
	}

	/**
	 * 返回特定日期处于一年中的第几周 周的定义：星期日到星期六
	 * 
	 * @return
	 */
	public int getWeekOfYear()
	{
		int weeks = calendar.get(Calendar.WEEK_OF_YEAR);
		Calendar cal = (Calendar) calendar.clone();
		boolean isError = false;// 默认没有异常
		while (weeks == 1 && this.getMonth() == 12)
		{
			isError = true;
			cal.add(Calendar.DATE, -1);
			weeks = cal.get(Calendar.WEEK_OF_YEAR);
		}
		if (isError)
		{
			weeks++;
		}
		return weeks;
	}

	/**
	 * 返回特定日期处于一周中的第几天<br>
	 * 中国周一为一周第一天：CN<br>
	 * 国外周日为一周第一天：US
	 * 
	 * @return int
	 */
	public int getNoDayOfWeek(String country)
	{
		return calendar.get(Calendar.DAY_OF_WEEK)
				- Integer.parseInt(country.equals("CN") ? "1" : "0");
	}

	/**
	 * 东方是周一为一周的第一天：CN<br>
	 * 西方是周日为一周的第一天：US<br>
	 * 返回特定日期所处这一周的周一所处的日期
	 * 
	 * @return String
	 */
	public String getFirstDayOfWeek(String country)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 西方是周日为一周的第一天
		// 东方是周一为一周的第一天
		Calendar calendarTmp = Calendar.getInstance();
		calendarTmp.setTime(calendar.getTime());
		if ("CN".equals(country))
		{
			calendarTmp.setFirstDayOfWeek(Calendar.MONDAY);
			calendarTmp.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		else
		{
			calendarTmp.setFirstDayOfWeek(Calendar.SUNDAY);
			calendarTmp.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}
		String time = formatter.format(calendarTmp.getTime());
		calendarTmp.clear();
		return time;
	}

	/**
	 * 东方是周一为一周的第一天：CN<br>
	 * 西方是周日为一周的第一天：US<br>
	 * 返回特定日期所处这一周的周末所处的日期
	 * 
	 * @return String
	 */
	public String getLastDayOfWeek(String country)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 西方是周日为一周的第一天
		// 东方是周一为一周的第一天
		Calendar calendarTmp = Calendar.getInstance();
		calendarTmp.setTime(calendar.getTime());
		if ("CN".equals(country))
		{
			calendarTmp.setFirstDayOfWeek(Calendar.MONDAY);
			calendarTmp.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		else
		{
			calendarTmp.setFirstDayOfWeek(Calendar.SUNDAY);
			calendarTmp.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}
		calendarTmp.add(Calendar.DAY_OF_WEEK, 6);
		String time = formatter.format(calendarTmp.getTime());
		calendarTmp.clear();
		return time;
	}

	/**
	 * 获得特定日期是当年的第几天 返回：int
	 */
	public int getNOdays()
	{
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获得指定日期所在月的最后一天的日期
	 * 
	 * @return String
	 */
	public String getLastDayOfMonth()
	{
		calendar.set(this.getYear(), this.getMonth(), 1);
		this.getNextDate(-1);
		return this.getDate();
	}

	/**
	 * 通过秒数得到最大天数 20081029移植于Stringutils类
	 * 
	 * @param timeMills
	 * @return
	 */
	public static int getMaxDaysOfMonth(long timeMills)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeMills);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 设置成整分钟时间
	 * 
	 * @param c
	 *            日历对象
	 * @return 被设置成整分钟的日期对象
	 */
	public static Calendar setMinuteTime(Calendar c)
	{
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	/**
	 * 设置成整时
	 * 
	 * @param c
	 * @return Calendar 被设置成整时的日期对象
	 */
	public static Calendar setTimingHour(Calendar c)
	{
		c.set(Calendar.MINUTE, 0);
		setMinuteTime(c);
		return c;
	}

	/**
	 * 设置成零点
	 * 
	 * @param c
	 *            要设置的对象
	 * @return Calendar 被设置成零点的日期对象
	 */
	public static Calendar setZeroTime(Calendar c)
	{
		c = setTimingHour(c);
		c.set(Calendar.HOUR_OF_DAY, 0);
		return c;
	}

	/**
	 * 设置成月初的零点
	 * 
	 * @param c
	 * @return Calendar 被设置成月起始的日期对象
	 */
	public static Calendar setMonthStart(Calendar c)
	{
		c = setZeroTime(c);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c;
	}

	/**
	 * 设置成月末的24点（实际是下个月初的零点）
	 * 
	 * @param c
	 * @return Calendar 被设置成月结束的日期对象
	 */
	public static Calendar setMonthEnd(Calendar c)
	{
		c = setZeroTime(c);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c;
	}

	/**
	 * 表单日期专用转换方法,获取datepicker组件的毫秒值
	 * 
	 * @param formdate
	 *            传入格式化的日期字符串
	 * @param pattern
	 *            日期模板，请使用此类的公共常量,例如：DateTimeUtils.allPattern
	 * @return 返回毫秒数
	 * @throws Exception
	 *             解析异常
	 */
	public static long getMillisecondForForm(String formdate, String pattern)
			throws Exception
	{
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		try
		{
			return f.parse(formdate).getTime();
		}
		catch (Exception e)
		{
			throw new Exception("格式化表单的日期出错，传入的字符串" + formdate + ",使用的模板为:" + allPattern);
		}
	}

	/**
	 * 表单日期专用转换方法,获取datepicker组件的秒值
	 * 
	 * @param formdate
	 *            传入格式化的日期字符串
	 * @param pattern
	 *            日期模板，请使用此类的公共常量,例如：DateTimeUtils.allPattern
	 * @return 返回秒数
	 * @throws Exception
	 *             解析异常
	 */
	public static long getSecondForForm(String formdate, String pattern) throws Exception
	{
		return getMillisecondForForm(formdate, pattern) / 1000L;
	}

	/**
	 * 返回给定时间点的起始时间和结束时间的毫秒数
	 * 
	 * @param formDate
	 *            日历对象的字符串，该字符串识别从前台日历标签传回来的格式
	 * @param type
	 *            获取的类型，请使用 DateTimeUtils的常量类，例如 DateTimeUtils.dayType
	 *            则获取给定时间点位于这天的起始时间点和结束时间点的毫秒数，默认为DateTimeUtils.dayType
	 * @return long[2] 0位：起始时间 1位：结束时间
	 */
	public static long[] getstAndEdMilliscondByPointTime(String formDate, int type)
			throws Exception
	{
		long[] res = new long[2];
		Calendar c = Calendar.getInstance();
		SimpleDateFormat f;
		switch (type)
		{
			case DateTimeUtil.msType:
				throw new Exception("不支持毫秒级别类型DateTimeUtils.msType");
			case DateTimeUtil.minuteType:
				f = new SimpleDateFormat(DateTimeUtil.minutePattern);
				c.setTime(f.parse(formDate));
				res[0] = c.getTimeInMillis();
				c.add(Calendar.MINUTE, 1);
				res[1] = c.getTimeInMillis();
				break;
			case DateTimeUtil.hourType:
				f = new SimpleDateFormat(DateTimeUtil.hourPattern);
				c.setTime(f.parse(formDate));
				res[0] = c.getTimeInMillis();
				c.add(Calendar.HOUR_OF_DAY, 1);
				res[1] = c.getTimeInMillis();
				break;
			case DateTimeUtil.dayType:
				f = new SimpleDateFormat(DateTimeUtil.dayPattern);
				c.setTime(f.parse(formDate));
				res[0] = c.getTimeInMillis();
				c.add(Calendar.DAY_OF_YEAR, 1);
				res[1] = c.getTimeInMillis();
				break;
			case DateTimeUtil.weekType:
				throw new Exception("不支持毫秒级别类型DateTimeUtils.weekType");
			case DateTimeUtil.monthType:
				f = new SimpleDateFormat(DateTimeUtil.monthPattern);
				c.setTime(f.parse(formDate));
				res[0] = c.getTimeInMillis();
				c.add(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				res[1] = c.getTimeInMillis();
				break;
			case DateTimeUtil.yearType:
				f = new SimpleDateFormat(DateTimeUtil.yearPattern);
				c.setTime(f.parse(formDate));
				res[0] = c.getTimeInMillis();
				c.add(Calendar.YEAR, 1);
				res[1] = c.getTimeInMillis();
				break;
			default:
				throw new Exception("不支持级别类型");
		}
		return res;
	}

	/**
	 * 返回给定时间点的起始时间和结束时间的秒数 s
	 * 
	 * @param formDate
	 *            日历对象的字符串，该字符串识别从前台日历标签传回来的格式
	 * @param type
	 *            获取的类型，请使用 DateTimeUtils的常量类，例如 DateTimeUtils.dayType
	 *            则获取给定时间点位于这天的起始时间点和结束时间点的秒数，默认为DateTimeUtils.dayType
	 * @return long[2] 0位：起始时间 1位：结束时间
	 */
	public static long[] getstAndEdScondByPointTime(String formDate, int type)
			throws Exception
	{
		long[] msres = getstAndEdMilliscondByPointTime(formDate, type);
		long[] res = new long[2];
		res[0] = msres[0] / 1000L;
		res[1] = msres[1] / 1000L;
		return res;
	}
	/**
	 * 默认 yyyy-MM-dd HH:mm:ss
	 * @param dateTime
	 * @return
	 */
	public static String getThisDate(Date dateTime,String pattern){
		if(StringUtils.isEmpty(pattern)){
			return DateFormatUtils.format(dateTime, "yyyy-MM-dd HH:mm:ss");
		}
		return DateFormatUtils.format(dateTime, pattern);
	}
	/**
	 * 获取今日年份  
	 * @return yyyy
	 */
	public static String getTodayYear(){
		return DateFormatUtils.format(new Date(), "yyyy");	
	}
	
	/**
	 * 获取今日月份
	 * @return MM
	 */
	public static String getTodayMonth(){
		return DateFormatUtils.format(new Date(), "MM");		
	}
	
	/**
	 * 获取今日日期
	 * @return dd
	 */
	public static String getTodayDay(){
		return DateFormatUtils.format(new Date(), "dd");	
	}
	
	/**
	 * 获取短日月
	 * @return MMdd
	 */
	public static String getTodayChar4(){
		return DateFormatUtils.format(new Date(), "MMdd");
	}
	
	/**
	 * 返回年月
	 * @return yyyyMM
	 */
	public static String getTodayChar6(){
		return DateFormatUtils.format(new Date(), "yyyyMM");
	}
	
	/**
	 * 返回年月日
	 * @return yyyyMMdd
	 */
	public static String getTodayChar8(){
		return DateFormatUtils.format(new Date(), "yyyyMMdd");
	}
	
	/**
	 * 返回 年月日小时分
	 * @return yyyyMMddHHmm
	 */
	public static String getTodayChar12() {
		return DateFormatUtils.format(new Date(),"yyyyMMddHHmm");
	}	
	
	/**
	 * 返回 年月日小时分秒
	 * @return yyyyMMddHHmmss
	 */
	public static String getTodayChar14(){
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
	}
	
	/**
	 * 返回 年月日小时分秒 毫秒
	 * @return yyyyMMddHHmmssSSS
	 */
	public static String getTodayChar17(){
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");		
	}
	
	/**
	 * 返回 年月日小时分秒 毫秒
	 * @return yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static String getTodayChar23(){
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");		
	}
	
	
	/**
	 * 返回中文日期格式 支持4、6、8、12、14、17位转换
	 * @param charDateTime 长整型 CHAR
	 * @return 2007年12月12日 12时12分12秒 234毫秒
	 */
	public static String getFormatChina(String charDateTime){
		return getFormatDateTime(charDateTime,"FORMAT_CHINA");
	}
	
	/**
	 * 返回标准日期格式 支持4、6、8、12、14、17位转换
	 * @param charDateTime 长整型 CHAR
	 * @return  2007-12-12 12:12:12 234
	 */
	public static String getFormatNormal(String charDateTime){
		return getFormatDateTime(charDateTime,"FORMAT_NORMAL");
	}
	
	
	/**
	 * 返回标准日期格式 支持4、6、8、12、14、17位转换
	 * @param charDateTime 长整型 CHAR
	 * @return  2007/12/12 12:12:12 234
	 */
	public static String getFormatDateTime(String charDateTime){
		return getFormatDateTime(charDateTime,"FORMAT_DATATIME");
	}
	
	/**
	 * 对日期进行转换
	 * @param charDateTime
	 * @param formatType
	 * @return
	 */
	private static String getFormatDateTime(String charDateTime,String formatType){
		String strResult="";
		if( null == charDateTime ){
			return strResult="";
		}

		if(("".equals(charDateTime.trim()))){
			return strResult="";
		}
		
		String[] FORMAT_CHAR=null;
		if(formatType.equals("FORMAT_CHINA")){
			FORMAT_CHAR = FORMAT_CHINA;
		}else if(formatType.equals("FORMAT_NORMAL")){
			FORMAT_CHAR = FORMAT_NORMAL;
		}else if(formatType.equals("FORMAT_DATATIME")){
			FORMAT_CHAR = FORMAT_DATATIME;
		}else{
			return strResult=charDateTime;
		}

		//去掉空格
		charDateTime = charDateTime.trim();
		
		if(charDateTime.length()== 4 ){
			//MMdd 转换 MM月dd日
			strResult=charDateTime.substring(0, 2) + FORMAT_CHAR[1] 
		   		    + charDateTime.substring(2, 4) + FORMAT_CHAR[2];
		}else if (charDateTime.length()== 6 ){
			//yyyyMM 转换 yyyy年MM月
			strResult=charDateTime.substring(0, 4) + FORMAT_CHAR[0]
		   		    + charDateTime.substring(4, 6) + FORMAT_CHAR[1];
		}else if (charDateTime.length()== 8 ){
			//yyyyMMdd
			strResult=charDateTime.substring(0, 4) + FORMAT_CHAR[0]
			        + charDateTime.substring(4, 6) + FORMAT_CHAR[1]
			        + charDateTime.substring(6, 8) + FORMAT_CHAR[2];
		}else if (charDateTime.length()== 12 ){
			//yyyyMMddHHmm
			strResult=charDateTime.substring(0, 4) + FORMAT_CHAR[0]
			        + charDateTime.substring(4, 6) + FORMAT_CHAR[1]
			        + charDateTime.substring(6, 8) + FORMAT_CHAR[2]
			        + " "
			        + charDateTime.substring(8, 10) + FORMAT_CHAR[3]
			        + charDateTime.substring(10, 12) + FORMAT_CHAR[4]; 
		}else if (charDateTime.length()== 14 ){
			//yyyyMMddHHmmss
			strResult=charDateTime.substring(0, 4) + FORMAT_CHAR[0]
			        + charDateTime.substring(4, 6) + FORMAT_CHAR[1]
			        + charDateTime.substring(6, 8) + FORMAT_CHAR[2]
			        + " "
			        + charDateTime.substring(8, 10) + FORMAT_CHAR[3]
			        + charDateTime.substring(10, 12) + FORMAT_CHAR[4]
			        + charDateTime.substring(12, 14) + FORMAT_CHAR[5]; 
		}else if (charDateTime.length()== 17 ){
			//yyyyMMddHHmmssS
			strResult=charDateTime.substring(0, 4) + FORMAT_CHAR[0]
			        + charDateTime.substring(4, 6) + FORMAT_CHAR[1]
			        + charDateTime.substring(6, 8) + FORMAT_CHAR[2]
			        + " "
			        + charDateTime.substring(8, 10) + FORMAT_CHAR[3]
			        + charDateTime.substring(10, 12) + FORMAT_CHAR[4]
			        + charDateTime.substring(12, 14) + FORMAT_CHAR[5]
					+ " "
			        + charDateTime.substring(14, 17) + FORMAT_CHAR[6];	
		}else{
			strResult = charDateTime;
		}

		return strResult;
	}
	
	/**
	 * 判断字符串是否为对应的时间格式
	 *  Created on 2015年1月21日 
	 * <p>Discription:[方法功能中文描述]</p>
	 * @author:[杨龙平]
	 * @update:[日期yyyy-MM-dd] [杨龙平]
	 * @return boolean .
	 */
	public static boolean isValidDate(String dateFormatStr, String dateStr) {
        boolean convertSuccess=true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
         SimpleDateFormat format = new SimpleDateFormat(dateFormatStr);
        try {
	    	// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
	    	format.setLenient(false);
	        format.parse(dateStr);
         } catch (ParseException e) {
	    	 // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
	    	 convertSuccess=false;
        } 
        	return convertSuccess;
	}
	
	public static void main(String[] args) {
		System.out.println(isValidDate(dateFHMS,"9:00:00"));
		System.out.println(new Date().getTime());
	}
	
	/**
     * 秒转换为指定格式的日期
     * @param second
     * @param patten
     * @return
     */
    public static String secondToDate(long second,String patten) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(second * 1000);//转换为毫秒
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(patten);
        String dateString = format.format(date);
        return dateString;
    }
	
	/**
	 * 
	 *  Created on 2015年12月7日 
	 * <p>Discription:[方法功能中文描述]</p>
	 * @author:[李旭东]
	 * @update:[日期2015年12月7日] [李旭东]
	 * @return Date .
	 */
	public static Date parseTime(String time){
		
		if (StringUtils.isBlank(time)) return null;
		
		time = time.replaceAll("[-/: ]", "");
		Date date = null;
		
		try {
			switch (time.length()) {
			case 8:
				date = FORMAT_YMD.parse(time);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				
				date = cal.getTime();
				break;
			case 14:
				date = FORMAT_YMDHMS.parse(time);
				break;
			default:
			}
		} catch (ParseException e) {
		}
		
		return date;
	}
	
	/**
     * Java将Unix时间戳转换成指定格式日期字符串
     * @param timestampString 时间戳 如："1473048265";
     * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     *
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (null == formats)
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }
    
    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}