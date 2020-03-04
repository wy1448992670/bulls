package com.goochou.p2b.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.goochou.p2b.model.LotteryGift;

public class DateFormatTools {

    public final static String DATEFORMAT1 = "yyyy-MM-dd HH:mm:ss";

    public final static String DATEFORMAT2 = "yyyy-MM-dd";
    public final static String DATEFORMAT3 = "yyyy年MM月dd日 HH时mm分ss秒";

    public final static String DATEFORMAT4 = "yyyy年MM月dd日";

    public final static String DATEFORMAT5 = "yyyy.MM.dd";

    public final static String DATEFORMAT6 = "yyyy-MM-dd EEEE";

    public final static String DATEFORMAT7 = "MM月dd日 HH:mm";

    public final static String DATEFORMAT_MONTH = "yyyy-MM";

    public final static String DATEFORMAT_YEAR = "yyyy";

    public final static String DATEFORMAT_MONTHDAYS = "MMdd";
    public final static String DATEFORMAT_YEARMONTHDAYS = "YYMMdd";

    public final static String DATEFORMAT_SQLDAYS = "%Y%m%d";
    public final static String DATEFORMAT_SQLMONTH = "%Y%m";

    /**
     * 日期转化成字符串格式 yy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     * @author 刘源
     * @date 2015年11月19日
     */
    public static String dateToStr1(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT1);
        return sdf.format(date);
    }

    /**
     * 日期转化成字符串格式 yy-MM-dd
     *
     * @param date
     * @return
     * @author 刘源
     * @date 2015年11月19日
     */
    public static String dateToStr2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT2);
        return sdf.format(date);
    }

    /**
     * 日期转化成字符串格式 yyMMdd
     *
     * @param date
     * @return
     * @author 刘源
     * @date 2016年2月26日
     */
    public static String dateToStr3(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT2);
        String dateStr = sdf.format(date);
        return dateStr.replaceAll("-", "");
    }

    /**
     * 日期转化成字符串格式 MMdd
     *
     * @param date
     * @return
     * @author 刘源
     * @date 2015年11月19日
     */
    public static String dateToStr4(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_MONTHDAYS);
        return sdf.format(date);
    }

    public static String dateToStr5(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_YEARMONTHDAYS);
        return sdf.format(date);
    }

    public static String dateToStr6(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT5);
        return sdf.format(date);
    }

    public static String dateToStr7(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT6, Locale.CHINA);
        return sdf.format(date);
    }


    /**
     * 日期转化成字符串格式 yy年MM月dd日 HH:mm分
     *
     * @param date
     * @return
     */
    public static String dateToStrCN7(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT7);
        return sdf.format(date);
    }


    /**
     * 日期转化成字符串格式 yy年MM月dd日 HH时mm分ss秒
     *
     * @param date
     * @return
     * @author 刘源
     * @date 2015年11月19日
     */
    public static String dateToStrCN(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT3);
        return sdf.format(date);
    }

    /**
     * 日期转化成字符串格式 yy年MM月dd日
     *
     * @param date
     * @return
     * @author 刘源
     * @date 2015年11月19日
     */
    public static String dateToStrCN2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT4);
        return sdf.format(date);
    }

    /**
     * 日期转化成字符串格式 yy-MM
     *
     * @param date
     * @return
     * @author 刘源
     * @date 2015年11月19日
     */
    public static String dateToStrMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_MONTH);
        return sdf.format(date);
    }

    /**
     * 日期转化成字符串格式  yy
     *
     * @param date
     * @return
     * @author 刘源
     * @date 2015年11月19日
     */
    public static String dateToStrYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_YEAR);
        return sdf.format(date);
    }

    /**
     * 字符串转化成yy-MM-dd HH:mm:ss日期
     *
     * @param str
     * @return
     * @throws ParseException
     * @author 刘源
     * @date 2015年11月19日
     */
    public static Date strToDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT1);
        return sdf.parse(str);
    }

    /**
     * 字符串转化成yy-MM-dd 日期
     *
     * @param str
     * @return
     * @throws ParseException
     * @author 刘源
     * @date 2015年11月19日
     */
    public static Date strToDate2(String str) throws ParseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT2);
            return sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转化成yy年MM月dd日 HH时mm分ss秒日期
     *
     * @param str
     * @return
     * @throws ParseException
     * @author 刘源
     * @date 2015年11月19日
     */
    public static Date strToCNDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT3);
        return sdf.parse(str);
    }

    /**
     * 长时间格式转化成短时间格式
     * yyyy-MM-dd HH:mm:ss 转化成 yyyy-MM-dd 格式
     *
     * @param longDate
     * @return
     * @throws ParseException
     * @author 刘源
     * @date 2015年12月18日
     */
    public static Date dateToShortDate(Date longDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT1);
        SimpleDateFormat sdf2 = new SimpleDateFormat(DATEFORMAT2);
        String str = sdf.format(longDate);
        return sdf2.parse(str);
    }


    /**
     * 字符串转化成yy年MM月dd日 日期
     * @author 刘源
     * @date 2015年11月19日
     * @param str
     * @return
     * @throws ParseException
     */
    /*public static Date strToCNDate2(String str) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT4);
		return sdf.parse(str);
	}*/

    /**
     * 得到当前时间的前一天或者后一天
     *
     * @param date
     * @param days 负数代表往前某一天，正数代表往后某一天
     * @return
     * @throws ParseException
     * @author 王信
     * @Create Date: 2015年11月23日下午1:44:43
     */
    public static Date jumpOneDay(Date date, int days) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
            return strToDate(dateToStr1(calendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String jumpOneDayToString(Date date, int days) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
        return dateToStr2(calendar.getTime());
    }

    /**
     * @Title: DateFormatTools.java
     * @Package com.goochou.p2b.utils
     * @Description(描述):两个日期相减 返回相差日期天数
     * @author 王信
     * @date 2016年3月3日 上午11:39:23
     */
    public static long dayToDaySubtract(Date begin_date, Date end_date) {
        long day = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT2);
            String sdate = format.format(Calendar.getInstance().getTime());
            if (begin_date == null) {
                begin_date = format.parse(sdate);
            }
            if (end_date == null) {
                end_date = format.parse(sdate);
            }
            day = (end_date.getTime() - begin_date.getTime())
                    / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            System.out.println("dayToDaySubtract---->日期格式方法类报错");
            e.printStackTrace();
        }
        return day;
    }

    public static int dayToDaySubtractWithoutSeconds(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * @Description: 传入时间和当前时间做比对  是否过期    过期 返回  true   ,未过期,返回  false
     * @date 2017/3/26
     * @author 王信
     */
    public static boolean dateIsExpired(Date date) throws Exception {
        boolean flag = false;
        Date a = dateToShortDate(date);
        Date b = dateToShortDate(new Date());
        if (b.after(a)) {
            flag = true;
        }
        return flag;
    }

    public static Date date2Night(Date date) {
        if (date != null) {
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(59);
            date.setTime(date.getTime() - 500);
        }
        return date;
    }

    public static void main(String[] args) throws Exception {
        Integer a = 128;
        Integer b = 128;
        double d = 0.9;
        //System.out.println(a.equals(b) + "------------" + (a == b));
        Date d1 = strToDate2("2017-03-27");
        //Date d2 = new Date("2017-12-01");
        // System.out.print(dayToDaySubtractWithoutSeconds(d1, d2));
        List<String> l = null;
        LotteryGift l1 = new LotteryGift();
        System.out.print(dateToStrCN7(new Date()));
        //System.out.print(dayToDaySubtractWithoutSeconds(d1, d2));
        // System.out.println(new DecimalFormat("#.00").format(1));
//        System.out.println(dayToDaySubtract(strToDate2("2017-03-27"),strToDate2("2017-04-27")));

//        Date start = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        c.set(Calendar.DATE, c.get(Calendar.DATE) + 3);
//        System.out.println(dateToStr2(c.getTime()));

//
//        System.out.println(dayToDaySubtractWithoutSeconds(new Date(2017, 0, 20, 12, 30, 33), new Date(2017, 2, 20)));


//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) );
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        long cTime = calendar.getTimeInMillis();
//        long currentTime = new Date().getTime();
//        System.out.println(dateToStr1(new java.util.Date(cTime))+"---------------加了30天的当前时间");
//        System.out.println(dateToStr1(new java.util.Date(currentTime))+"---------------------当前时间");

//        Date date=DateFormatTools.dateToShortDate(new Date());
//        System.out.println(date);
//        Date hongTime=DateFormatTools.dateToShortDate(jumpOneDay(strToDate2("2016-07-03"),1));
//        System.out.println(hongTime);
//        System.out.println(date.before(hongTime)+"---------"+(date.equals(hongTime))+"-------"+date.after(hongTime));
    }
}
