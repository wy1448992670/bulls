package com.goochou.p2b.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 1、<p><p>
 * 参数类型工具转换类
 * @author mr_zou
 * @date 2017/12/5 15:08
 */
public class FormatUtil {

    /** ==============IS Base=================== */
    /**
     * 判断是否为整数(包括负数)
     */
    public static boolean isNumber(Object arg) {
        return NumberBo(0, toString(arg));
    }

    /**
     * 判断是否为小数(包括整数,包括负数)
     */
    public static boolean isDecimal(Object arg) {
        return NumberBo(1, toString(arg));
    }

    /**
     * 判断是否为空 ,为空返回true
     */
    public static boolean isEmpty(Object arg) {
        return toString(arg).trim().length() == 0 ? true : false;
    }

    /** ==============TO  Base=================== */
    /**
     * Object 转换成 Int 转换失败 返回默认值 0 <br>
     * 使用:toInt(值,默认值[选填])
     */
    public static int toInt(Object... args) {
        int def = 0;
        if (args != null) {
            String str = toStringTrim(args[0]);
            // 判断小数情况。舍弃小数位
            int stri = str.indexOf('.');
            str = stri > 0 ? str.substring(0, stri) : str;
            if (args.length > 1)
                def = Integer.parseInt(args[args.length - 1].toString());
            if (isNumber(str))
                return Integer.parseInt(str);
        }
        return def;
    }

    /**
     * Object 转换成 Long 转换失败 返回默认值 0 <br>
     * 使用:toLong(值,默认值[选填])
     */
    public static long toLong(Object... args) {
        Long def = 0L;
        if (args != null) {
            String str = toStringTrim(args[0]);
            if (args.length > 1)
                def = Long.parseLong(args[args.length - 1].toString());
            if (isNumber(str))
                return Long.parseLong(str);
        }
        return def;
    }

    /**
     * Object 转换成 Double 转换失败 返回默认值 0 <br>
     * 使用:toDouble(值,默认值[选填])
     */
    public static double toDouble(Object... args) {
        double def = 0;
        if (args != null) {
            String str = toStringTrim(args[0]);
            if (args.length > 1)
                def = Double.parseDouble(args[args.length - 1].toString());
            if (isDecimal(str))
                return Double.parseDouble(str);
        }
        return def;
    }

    public static boolean toBoolean(Object... args) {
        return toInt(args) == 1 ? true : false;
    }

    /**
     * Object 转换成 BigDecimal 转换失败 返回默认值 0
     * <br>使用:toDecimal(值,默认值[选填])
     */
    public static BigDecimal toDecimal(Object... args) {
        String val = Double.toString(toDouble(args)); // 特别注意: new  BigDecimal(Double) 会有误差，得先转String
        return new BigDecimal(val);
    }

    /**
     * Object 转换成 String 为null 返回空字符
     * <br>使用:toString(值,默认值[选填])
     */
    public static String toString(Object... args) {
        String def = "";
        if (args != null) {
            if (args.length > 1)
                def = toString(args[args.length - 1]);
            Object obj = args[0];
            if (obj == null)
                return def;
            return obj.toString();
        } else {
            return def;
        }
    }

    /**
     * Object 转换成 String[去除所以空格]; 为null 返回空字符 <br>
     * 使用:toStringTrim(值,默认值[选填])
     */
    public static String toStringTrim(Object... args) {
        String str = toString(args);
        return str.replaceAll("\\s*", "");
    }

    /**
     * String 转换成 util.Date      yyyy-MM-dd HH:mm:ss E <br>
     * G 年代标志符 <br>
     * y 年 <br>
     * M 月 <br>
     * d 日 <br>
     * h 时 在上午或下午 (1~12) <br>
     * H 时 在一天中 (0~23) <br>
     * m 分 <br>
     * s 秒 <br>
     * S 毫秒 <br>
     * E 星期 <br>
     * D 一年中的第几天 <br>
     * F 一月中第几个星期几 <br>
     * w 一年中第几个星期 <br>
     * W 一月中第几个星期 <br>
     * a 上午 / 下午 标记符 <br>
     * k 时 在一天中 (1~24) <br>
     * K 时 在上午或下午 (0~11) <br>
     * z 时区
     */
    public static Date toDate(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }

    /**
     * util.Date 转换成 String      yyyy-MM-dd HH:mm:ss E <br>
     * G 年代标志符 <br>
     * y 年 <br>
     * M 月 <br>
     * d 日 <br>
     * h 时 在上午或下午 (1~12) <br>
     * H 时 在一天中 (0~23) <br>
     * m 分 <br>
     * s 秒 <br>
     * S 毫秒 <br>
     * E 星期 <br>
     * D 一年中的第几天 <br>
     * F 一月中第几个星期几 <br>
     * w 一年中第几个星期 <br>
     * W 一月中第几个星期 <br>
     * a 上午 / 下午 标记符 <br>
     * k 时 在一天中 (1~24) <br>
     * K 时 在上午或下午 (0~11) <br>
     * z 时区
     */
    public static String toDate2(Date date, String format) {
        String time = "";
        try {
            time = new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }
    /** ==============Date Base=================== */

    /**
     * 获取  自定义  年月日
     *
     * @param date 你需要转换的时间 【必填】
     * @param args 年月日  [y,m,d] 【选填】
     *             比如：date="2014-04-01"	<br>
     *             调用  getCustomYMD(date,0,-1,2) 得到的结果:2014-02-03
     */
    @SuppressWarnings("static-access")
    public static String getCustomYMD(Date date, int... args) {
        int defD = 0;
        int defM = 0;
        int defY = 0;
        if (args.length >= 1) {
            defY = args[0];
            if (args.length >= 2) {
                defM = args[1];
                if (args.length >= 3)
                    defD = args[2];
            }
        }
        Calendar ca = Calendar.getInstance();//获取当前日期
        ca.setTime(date);
        if (defD != 0)
            ca.add(ca.DATE, defD);
        if (defM != 0)
            ca.add(ca.MONTH, defM);
        if (defY != 0)
            ca.add(ca.YEAR, defY);
        return baseformat.format(ca.getTime());
    }

    /**
     * 获取  自定义2  年月日
     *
     * @param date 你需要转换的时间 【必填】
     * @param args 年月日  [y,m,d] 【选填】
     *             比如：date="2014-04-01"	<br>
     *             调用  getCustomYMD(date,0,1,2) 得到的结果:2014-01-02
     */
    public static String getCustomYMD2(Date date, int... args) {
        int defD = 0;
        int defM = 0;
        int defY = 0;
        if (args.length >= 1) {
            defY = args[0];
            if (args.length >= 2) {
                defM = args[1];
                if (args.length >= 3)
                    defD = args[2];
            }
        }
        Calendar ca = Calendar.getInstance();//获取当前日期
        ca.setTime(date);
        if (defD != 0)
            ca.set(Calendar.DATE, defD);
        if (defM != 0)
            ca.set(Calendar.MONTH, defM);
        if (defY != 0)
            ca.set(Calendar.YEAR, defY);
        return baseformat.format(ca.getTime());
    }

    /**
     * 获取月份第一天  默认返回 当前月份第一天
     */
    public static String getFirstDayOfMonth(Date... dates) {
        Calendar ca = Calendar.getInstance();//获取当前日期
        if (dates.length >= 1)
            ca.setTime(dates[0]);
        ca.set(ca.DATE, 1);
        return baseformat.format(ca.getTime());
    }


    /** ==============Other Base=================== */
    /**
     * 数字左边补0 ,obj:要补0的数， length:补0后的长度
     */
    public static String leftPad(Object obj, int length) {
        return String.format("%0" + length + "d", toInt(obj));
    }

    /**
     * 小数 转 百分数
     */
    public static String toPercent(Double str) {
        StringBuffer sb = new StringBuffer(Double.toString(str * 100.0000d));
        return sb.append("%").toString();
    }

    /**
     * 百分数 转 小数
     */
    public static Double toPercent2(String str) {
        if (str.charAt(str.length() - 1) == '%')
            return Double.parseDouble(str.substring(0, str.length() - 1)) / 100.0000d;
        return 0d;
    }

    /**
     * String 转 Money
     */
    public static String toMoney(Object str, String MoneyType) {
        DecimalFormat df = new DecimalFormat(MoneyType);
        if (isDecimal(str))
            return df.format(toDecimal(str)).toString();
        return df.format(toDecimal("0.00")).toString();
    }

    /**
     * 获取字符串str 左边len位数
     */
    public static String getLeft(Object obj, int len) {
        String str = toString(obj);
        if (len <= 0)
            return "";
        if (str.length() <= len)
            return str;
        else
            return str.substring(0, len);
    }

    /**
     * 获取字符串str 右边len位数
     */
    public static String getRight(Object obj, int len) {
        String str = toString(obj);
        if (len <= 0)
            return "";
        if (str.length() <= len)
            return str;
        else
            return str.substring(str.length() - len, str.length());
    }

    /**
     * List集合去除重复值
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List delMoreList(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }

    /**
     * 两个list 连接
     */
    public static List connectList(List list1, List list2) {
        int count = list2.size();
        for (int i = 0; i < count; i++) {
            list1.add(list2.get(i));
        }
        return list1;
    }

    /**
     * ============== END  ===================
     */
    public final static class MoneyType {
        /**
         * 保留2位有效数字，整数位每3位逗号隔开 （默认）
         */
        public static final String DEF_MONEY = "#,##0.00";
        /**
         * 保留2位有效数字
         */
        public static final String DECIMAL_2 = "0.00";
        /**
         * 保留4位有效数字
         */
        public static final String DECIMAL_4 = "0.0000";
    }

    public static final SimpleDateFormat baseformat = new SimpleDateFormat("yyyy-MM-dd");

    private static boolean NumberBo(int type, Object obj) {
        if (isEmpty(obj))
            return false;
        int points = 0;
        int chr = 0;
        String str = toString(obj);
        for (int i = str.length(); --i >= 0; ) {
            chr = str.charAt(i);
            if (chr < 48 || chr > 57) { // 判断数字
                if (i == 0 && chr == 45) // 判断 - 号
                    return true;
                if (i >= 0 && chr == 46 && type == 1) { // 判断 . 号
                    ++points;
                    if (points <= 1)
                        continue;
                }
                return false;
            }
        }
        return true;
    }
}
