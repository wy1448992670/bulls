package com.goochou.p2b.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 工具类
 *
 * @author irving
 */
public class CommonUtils {
    public static String getPhone(String phone) {
    	if (phone == null) {
			return "";
		}
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
    }

    public static String getCardNoSixFour(String cardNo) {
        return cardNo.substring(0, 6) + "******" + cardNo.substring(cardNo.length() - 4, cardNo.length());
    }

    public static String getCardFour(String cardNo) {
    	 if(cardNo.length()<4){
         	return cardNo;
         }
        return cardNo.substring(cardNo.length() - 4, cardNo.length());
    }

    public static String getIDCard(String idCard) {
        if(null == idCard) {
            return "";
        }else if(idCard.length()<4){
        	return idCard;
        }else {
            return idCard.substring(0, 3) + "***********" + idCard.substring(idCard.length() - 4, idCard.length());
        }
    }
    
    public static String getTrueName(String trueName) {
    	if (trueName == null || trueName == "") {
			return "";
		}
    	
        String s = "";
        for (int i = 0; i < trueName.length() - 1; i++) {
            s += "*";
        }
        return trueName.substring(0, 1) + s;
    }
    
    public static String getTrueNameStr(String trueName) {
    	int length = trueName.length();
        String s = "";
        for (int i = 0; i < trueName.length() - 1; i++) {
            s += "*";
        }
        return trueName.substring(0, 1) + s + trueName.substring(length-2, length-1);
    }
    
    public static String getUserNameStr(String userName) {
    	int length = userName.length();
        String s = "";
        for (int i = 0; i < 4; i++) {
            s += "*";
        }
        return userName.substring(0, 1) + s + userName.substring(length-2, length-1);
    }

    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 从身份证获取出生日期
     *
     * @param cardNumber 已经校验合法的身份证号
     * @return Strig YYYY-MM-DD 出生日期
     */
    public static Calendar getBirthDateFromCard(String cardNumber) {
        String card = cardNumber.trim();
        String year;
        String month;
        String day;
        if (card.length() == 18) { // 处理18位身份证
            year = card.substring(6, 10);
            month = card.substring(10, 12);
            day = card.substring(12, 14);
        } else { // 处理非18位身份证
            year = card.substring(6, 8);
            month = card.substring(8, 10);
            day = card.substring(10, 12);
            year = "19" + year;
        }
        if (month.length() == 1) {
            month = "0" + month; // 补足两位
        }
        if (day.length() == 1) {
            day = "0" + day; // 补足两位
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(year));
        c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        c.set(Calendar.DATE, Integer.parseInt(day));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    /**
     * 从身份证获取性别
     *
     * @param cardNumber 已经校验合法的身份证号
     * @return String Sex 性别
     */
    public static int getSexFromCard(String cardNumber) {
        String inputStr = cardNumber.toString();
        int sex;
        if (inputStr.length() == 18) {
            sex = inputStr.charAt(16);
            if (sex % 2 == 0) {
                return 0;
            } else {
                return 1;
            }
        } else {
            sex = inputStr.charAt(14);
            if (sex % 2 == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * 李先生
     *
     * @param trueName
     * @param sex      0女1男2保密
     * @return
     */
    public static String getTrueNameSexStr(String trueName, Integer sex) {
        return trueName.substring(0, 1) + (sex == 0 ? "女士" : "先生");
    }
    
    /**
     *  Created on 2019年5月14日 
     * <p>Title:[提现预计到账时间]</p>
     * @author:[叶东平]
     * @update:[日期2019年5月14日] [叶东平]
     * @return String .
     */
    public static String getTime(Date date, Integer type) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.set(Calendar.HOUR_OF_DAY, c1.get(Calendar.HOUR_OF_DAY) + 2);
        if (type.equals(1)) {
            int weekend = date.getDay();
            //周六
            if (weekend == 6) {
                c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 2);
                c1.set(Calendar.HOUR_OF_DAY, 0);
                c1.set(Calendar.MINUTE, 30);
            //周五
            } else if (weekend == 5) {
                c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 3);
                c1.set(Calendar.HOUR_OF_DAY, 0);
                c1.set(Calendar.MINUTE, 30);
            } else {
            	c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
                c1.set(Calendar.MINUTE, c1.get(Calendar.MINUTE) + 30);
            }
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(c1.getTime());
    }
    
	/*
	 * 判断第18位校验码是否正确 第18位校验码的计算方式： 1. 对前17位数字本体码加权求和 公式为：S = Sum(Ai * Wi), i = 0,
	 * ... , 16 其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4 2 1 6
	 * 3 7 9 10 5 8 4 2 2. 用11对计算结果取模 Y = mod(S, 11) 3. 根据模的值得到对应的校验码 对应关系为： Y值： 0 1
	 * 2 3 4 5 6 7 8 9 10 校验码： 1 0 X 9 8 7 6 5 4 3 2
	 */
    public static boolean isVarifyIdCard(String no) {
    	// 对身份证号进行长度等简单判断 
    	if (no == null || no.length() != 18 || !no.matches("\\d{17}[0-9X]")) { return false; } // 1-17位相乘因子数组
    	int[] factor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 }; // 18位随机码数组 
    	char[] random = "10X98765432".toCharArray(); // 计算1-17位与相应因子乘积之和 
    	int total = 0; for (int i = 0; i < 17; i++) { total += Character.getNumericValue(no.charAt(i)) * factor[i]; } // 判断随机码是否相等 
    	return random[total % 11] == no.charAt(17);
     
	}
    
    public static String cutText(String str, Integer num) {
        StringBuffer sb = new StringBuffer();
        sb.append(str.substring(0, num));
        sb.append("..");
        return sb.toString();
    }
    

    public static void main(String[] args) {
    	System.out.println("1234".length());
    	System.out.println("1234".substring("1234".length() - 4, "1234".length()));
        System.out.println(getPhone("15000230763"));
        System.out.println(getCardNoSixFour("6226090216855441"));
        System.out.println(isVarifyIdCard("360121199102134617"));
        System.out.println(getTrueName("王昆终"));
        System.out.println(cutText("东乌珠穆沁旗举行旗人民政府与中亿投资有限公司建设现代化畜牧业体系",18));
    }
}
