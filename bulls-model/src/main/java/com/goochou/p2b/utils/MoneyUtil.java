/**
 * 
 */
package com.goochou.p2b.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Currency;

import org.apache.commons.lang.StringUtils;

/**  
 * @Project: crm-model
 * @Title: MoneyUtil.java
 * @Package com.yuwang.crm.model.common
 * @Description: 货币类型常用方法支持
 * @author liuboen liuboen@zba.com
 * @date 2011-6-17 下午07:58:06
 * @version V1.0  
 */

public class MoneyUtil {
	public static String[] chineseDigits = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"}; 
	
	public static DecimalFormat dFormat = new DecimalFormat("#.00");  
	
	/**
	 * 获取元到分
	 * @param dollar
	 * @return
	 */
	public static Long getDollarToCent(String dollar){
		if(StringUtils.isEmpty(dollar)){
			return 0L;
		}
		 Money money =new Money(dollar);
		 return money.getCent();
	}
	/**
	 * 获取分到元
	 * @param dollar
	 * @return
	 */
	public static String getCentToDollar(long cent){
		Money m=new Money();
		m.setCent(cent);
		return m.toString();
	}
	/**
	 * 获取分到万
	 * @param dollar
	 * @return
	 */
	public static String getCentToMiriade(long cent){
		BigDecimal b1 = new BigDecimal(cent);
        BigDecimal b2 = new BigDecimal(1000000);
        return b1.divide(b2,0,BigDecimal.ROUND_HALF_EVEN).toString();
	}
	
	/**
	 * 
	 *  Created on 2014-8-13 
	 * <p>Discription:[金额正转负，负转正]</p>
	 * @author:[叶东平]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return Long .
	 */
	public static Long getPositiveAndNegative(Long amount){
	    if(null != amount){
	        if(amount<0){
	            return Math.abs(amount);
	        }else{
	            return -amount;
	        }
	    }else{
	        throw new NullPointerException();
	    }
	}


    /**
     * 金额(是分)的四舍五入,精度要分
     *
     * @param tp0
     * @return
     */
    public static BigDecimal RoundingFenUp(BigDecimal tp0) {
        BigDecimal tp1 = tp0.setScale(0, BigDecimal.ROUND_HALF_UP);
        return tp1;
    }
    
    /**
     * 金额(是分)的四舍五舍,精度要分
     *
     * @param tp0
     * @return
     */
    public static BigDecimal RoundingFenDown(BigDecimal tp0) {
        BigDecimal tp1 = tp0.setScale(0, BigDecimal.ROUND_DOWN);
        return tp1;
    }
    
    /**
     * 金额(是分)的四舍五入,精度要厘
     *
     * @param tp0
     * @return
     */
    public static BigDecimal RoundingLiUP(BigDecimal tp0) {
        BigDecimal tp1 = tp0.setScale(2, BigDecimal.ROUND_HALF_UP);
        return tp1;
    }
    
    /**
     * 金额(是分)的四舍五舍,精度要厘
     *
     * @param tp0
     * @return
     */
    public static BigDecimal RoundingLiDOWN(BigDecimal tp0) {
        BigDecimal tp1 = tp0.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return tp1;
    }
    
    /**
     *  Created on 2014-9-28 
     * <p>Discription:[全部进位]</p>
     * @author:[叶东平]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return BigDecimal .
     */
    public static BigDecimal RoundingCeiling(BigDecimal a){
        a = a.setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal tp1 = a.setScale(0, BigDecimal.ROUND_CEILING);
        return tp1;
    }
    
	
	/**
	 * 
	 *  Created on 2014年10月20日 
	 * <p>Discription:[金额格式化   1. 保留两位小数
	 * 						   2.三个单位一个逗号分割]</p>
	 * @author:[杨龙平]
	 * @update:[日期yyyy-MM-dd] [author]
	 * @return String .
	 */
	public static String insertComma(String s) {
	    if (s == null || s.length() < 1||s.equals("0.00")||s.equals("0")||s.equals("0.0")) {
	        return "0.00";
	    }
	    double num = Double.parseDouble(s);
		if (num >= 1000 || num <= -1000){
	    	DecimalFormat myformat = new DecimalFormat();
	    	myformat.applyPattern("##,###.00");
	    	return myformat.format(num);
	    }else{
	    	return s;
	    }
	}
	
	public static String insertCommaStr(String s) {
	    if (s == null || s.length() < 1||s.equals("0.00")||s.equals("0")||s.equals("0.0")) {
	        return "0";
	    }
	    double num = Double.parseDouble(s);
		if (num >= 1000 || num <= -1000){
	    	DecimalFormat myformat = new DecimalFormat();
	    	myformat.applyPattern("##,###");
	    	return myformat.format(num);
	    }else{
	    	return s;
	    }
	}
	
	
	/**
	 * 
	 *  Created on 2014年10月20日 
	 * <p>Discription:[金额格式化   1. 保留两位小数
	 * 						   2.三个单位一个逗号分割]</p>
	 * @author:[杨龙平]
	 * @update:[日期yyyy-MM-dd] [author]
	 * @return String .
	 */
	public static String insertCommaNotDecimal(String s) {
	    if (s == null || s.length() < 1||s.equals("0.00")||s.equals("0")||s.equals("0.0")) {
	        return "0";
	    }
	    double num = Double.parseDouble(s);
		if (num >= 1000 || num <= -1000){
	    	DecimalFormat myformat = new DecimalFormat();
	    	myformat.applyPattern("##,###");
	    	return myformat.format(num);
	    }else{
	    	return s;
	    }
	}
	
	/**
	 * 
	 *  Created on 2014年10月21日 
	 * <p>Discription:[
	 * 分到元再格式化金额
	 * 例：5000000 --> 50,000.00
	 * ]</p>
	 * @author:[谭燊]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return String .
	 */
	public static String getCommaMoney(Long money) {
		return insertComma(getCentToDollar(money));
	}
	
	/**
	 * 
	 *  Created on 2015-4-27 
	 * <p>Discription:[金额去掉“,”
	 *  			s 金额
	 *  			去掉“,”后的金额]</p>
	 * @author:[杨龙平]
	 * @update:[日期2015-4-27] [杨龙平]
	 * @return String .
	 */
	public static String delComma(String s) {
	    String formatString = "";
	    if (s != null && s.length() >= 1) {
	        formatString = s.replaceAll(",", "");
	    }
	 
	    return formatString;
	}
	  
    /**
     *   
     *  Created on 2015-4-27 
     * <p>Discription:[把金额转换为汉字表示的数量，小数点后四舍五入保留两位 ]</p>
     * @author:[杨龙平]
     * @update:[日期2015-4-27] [杨龙平]
     * @return String .
     */
    public static String amountToChinese(double amount) {  
  
        if(amount > 99999999999999.99 || amount < -99999999999999.99)  
            throw new IllegalArgumentException("参数值超出允许范围 (-99999999999999.99 ～ 99999999999999.99)！");  
  
        boolean negative = false;  
        if(amount < 0) {  
            negative = true;  
            amount = amount * (-1);  
        }  
        long temp = Math.round(amount * 100);  
        int numFen = (int)(temp % 10); // 分  
        temp = temp / 10;  
        int numJiao = (int)(temp % 10); //角  
        temp = temp / 10;  
        //temp 目前是金额的整数部分  
  
        int[] parts = new int[20]; // 其中的元素是把原来金额整数部分分割为值在 0~9999 之间的数的各个部分  
        int numParts = 0; // 记录把原来金额整数部分分割为了几个部分（每部分都在 0~9999 之间）  
        for(int i=0; ; i++) {  
            if(temp ==0)  
                break;  
            int part = (int)(temp % 10000);  
            parts[i] = part;  
            numParts ++;  
            temp = temp / 10000;  
        }  
        boolean beforeWanIsZero = true; // 标志“万”下面一级是不是 0  
        String chineseStr = "";  
        for(int i=0; i<numParts; i++) {  
            String partChinese = partTranslate(parts[i]);  
            if(i % 2 == 0) {  
                if("".equals(partChinese))  
                    beforeWanIsZero = true;  
                else  
                    beforeWanIsZero = false;  
            }  
            if(i != 0) {  
                if(i % 2 == 0)  
                    chineseStr = "亿" + chineseStr;  
                else {  
                    if("".equals(partChinese) && !beforeWanIsZero)   // 如果“万”对应的 part 为 0，而“万”下面一级不为 0，则不加“万”，而加“零”  
                        chineseStr = "零" + chineseStr;  
                    else {  
                        if(parts[i-1] < 1000 && parts[i-1] > 0) // 如果"万"的部分不为 0, 而"万"前面的部分小于 1000 大于 0， 则万后面应该跟“零”  
                            chineseStr = "零" + chineseStr;  
                        chineseStr = "万" + chineseStr;  
                    }  
                }  
            }  
            chineseStr = partChinese + chineseStr;  
        }  
        if("".equals(chineseStr))  // 整数部分为 0, 则表达为"零元"  
            chineseStr = chineseDigits[0];  
        else if(negative) // 整数部分不为 0, 并且原金额为负数  
            chineseStr = "负" + chineseStr;  
        chineseStr = chineseStr + "元";  
        if(numFen == 0 && numJiao == 0) {  
            chineseStr = chineseStr + "整";  
        }  
        else if(numFen == 0) { // 0 分，角数不为 0  
            chineseStr = chineseStr + chineseDigits[numJiao] + "角";  
        }  
        else { // “分”数不为 0  
            if(numJiao == 0)  
                chineseStr = chineseStr + "零" + chineseDigits[numFen] + "分";  
            else  
                chineseStr = chineseStr + chineseDigits[numJiao] + "角" + chineseDigits[numFen] + "分";  
        }  
        return chineseStr;  
    }  
  
  
    /**
     *   
     *  Created on 2015-4-27 
     * <p>Discription:[把一个 0~9999 之间的整数转换为汉字的字符串，如果是 0 则返回 "" ]</p>
     * @author:[杨龙平]
     * @update:[日期2015-4-27] [杨龙平]
     * @return String .
     */
    private static String partTranslate(int amountPart) {  
  
        if(amountPart < 0 || amountPart > 10000) {  
            throw new IllegalArgumentException("参数必须是大于等于 0，小于 10000 的整数！");  
        }  
        String[] units = new String[] {"", "拾", "佰", "仟"};  
        int temp = amountPart;  
        String amountStr = new Integer(amountPart).toString();  
        int amountStrLength = amountStr.length();  
        boolean lastIsZero = true; //在从低位往高位循环时，记录上一位数字是不是 0  
        String chineseStr = "";  
        for(int i=0; i<amountStrLength; i++) {  
            if(temp == 0)  // 高位已无数据  
                break;  
            int digit = temp % 10;  
            if(digit == 0) { // 取到的数字为 0  
                if(!lastIsZero)  //前一个数字不是 0，则在当前汉字串前加“零”字;  
                    chineseStr = "零" + chineseStr;  
                lastIsZero = true;  
            }  
            else { // 取到的数字不是 0  
                chineseStr = chineseDigits[digit] + units[i] + chineseStr;  
                lastIsZero = false;  
            }  
            temp = temp / 10;  
        }  
        return chineseStr;  
    }  
    
    /**
     * 
     *  Created on 2015-7-29 
     * <p>Discription:[去除小数点以后的值]</p>
     * @author:[武勇吉]
     * @update:[日期2015-7-29] [武勇吉]
     * @return String .
     */
    public static String removeDecimalPoint(String amount){
    	if (amount.indexOf(".") > 0) {
			amount = amount.substring(0, amount.indexOf("."));
		}
		return amount;
    }

	/**
	 * 分转元
	 * 
	 *  Created on 2015年10月23日
	 * <p>Discription:[amtFormat]</p>
	 * @author:[李旭东]
	 * @update:[日期2015年10月23日] [李旭东]
	 * @return Double .
	 */
	public static Double amtFormat(Long amt){
		if (amt == null) return null;
		return amt / 100.0;
	}
	/**
	 * 分转元
	 * 
	 *  Created on 2015年10月23日
	 * <p>Discription:[amtFormat]</p>
	 * @author:[李旭东]
	 * @update:[日期2015年10月23日] [李旭东]
	 * @return Double .
	 */
	public static Double amtFormat(Integer amt){
		if (amt == null) return null;
		return amt / 100.0;
	}
	
	public static Double getDollerToRmbByRate(Double doller, Double rate, RoundingMode roundingMode){
		BigDecimal rmb = new BigDecimal(doller.toString()).multiply(new BigDecimal(rate).setScale(2, RoundingMode.HALF_UP)).setScale(2, roundingMode);
		return rmb.doubleValue();
	}
	
	public static Double getRmbToDollerByRate(Long rmb, Double rate){
		BigDecimal dollar = new BigDecimal(rmb).divide(new BigDecimal(rate), RoundingMode.HALF_DOWN).setScale(0, RoundingMode.HALF_DOWN).divide(new BigDecimal(100.0));
		return dollar.doubleValue();
	}
	
	/*public static void main(String[] args) {
		
		
		System.out.println(getRmbToDollerByRate(6740000L, 6.74));
		
		Double rate = 6.78;
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.UP));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.DOWN));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.CEILING));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.HALF_UP));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.HALF_DOWN));
		System.out.println("--------------------------------");
		
		rate = 6.77;
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.UP));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.DOWN));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.CEILING));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.HALF_UP));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.HALF_DOWN));
		System.out.println("--------------------------------");
		
		
		rate = 6.70;
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.UP));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.DOWN));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.CEILING));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.HALF_UP));
		System.out.println(new BigDecimal(rate).setScale(2, RoundingMode.HALF_DOWN));
		System.out.println("--------------------------------");
		
		System.out.println(getDollerToRmbByRate(0.01, 6.8, RoundingMode.UP));
		System.out.println(getRmbToDollerByRate(7L, 6.8));
		
		System.out.println(insertCommaNotDecimal("12345678"));
		String amount = "1000.00";
		if (amount.indexOf(".") > 0) {
			amount = amount.substring(0, amount.indexOf("."));
		}
		System.out.println(amount);
			
		if(args.length == 0) {  
            System.out.println("转换演示：");  
            System.out.println("-------------------------");  
            System.out.println("25000000000005.999: " + amountToChinese(25000000000005.99));  
            System.out.println("45689263.626: " + amountToChinese(45689263.62));  
            System.out.println("0.69457: " + amountToChinese(0.69));  
            System.out.println("253.0: " + amountToChinese(253.0));  
            System.out.println("0: " + amountToChinese(0));  
            System.out.println("-------------------------");  
  
            System.out.println("999: " + amountToChinese(999));  
              
            //System.out.println(Long.MAX_VALUE);  
            //System.out.println(Long.MIN_VALUE);  
        }  
        else {  
            System.out.println("转换结果：");  
            System.out.println(args[0] + ": " + amountToChinese(Double.parseDouble(args[0])));  
        }  
  
		System.out.println(insertComma("-1000.99"));
		System.out.println(delComma("1000.00"));
		
		System.out.println(Math.round(((double)60 / 100)));
		
		double test = (double)60 / 100;
		System.out.println(test);
		String test2 = String.valueOf(test);
		if(test2.indexOf(".") != -1){
			test2 = test2.substring(0, test2.indexOf(".")) + ".00";
		}
		
		
		System.out.println(test2);
		
		System.out.println(String.valueOf(1230.098098).substring(0, "1230".indexOf(".")));
	}*/
	
	public static String insertCommaWithOutZero(String s) {
	    if (s == null || s.length() < 1||s.equals("0.00")||s.equals("0")||s.equals("0.0")) {
	        return "0.00";
	    }
	    double num = Double.parseDouble(s);
		if (num >= 1000 || num <= -1000){
	    	DecimalFormat myformat = new DecimalFormat();
	    	myformat.applyPattern("##,###");
	    	return myformat.format(num);
	    }else{
	    	return s;
	    }
	}
	
	
	/**
	 * 
	 * <p>进行加法运算--建议使用参数为String类型的方法</p> 
	 * @param d1
	 * @param d2
	 * @return
	 * @author: lxfeng  
	 * @date: Created on 2018-3-15 下午4:53:07
	 */
	public static double add(double d1, double d2) { 
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.add(b2).doubleValue();
	}
	
	public static double add(Object d1, Object d2) { 
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 
	 * <p>进行减法运算--建议使用参数为object类型的方法</p> 
	 * @param d1
	 * @param d2
	 * @return
	 * @author: lxfeng  
	 * @date: Created on 2018-3-15 下午4:53:18
	 */
	public static double sub(double d1, double d2) { 
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.subtract(b2).doubleValue();
	}
	
	public static double sub(Object d1, Object d2) { 
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 
	 * <p>进行乘法运算--建议使用参数为object类型的方法</p> 
	 * @param d1
	 * @param d2
	 * @return
	 * @author: lxfeng  
	 * @date: Created on 2018-3-15 下午4:53:32
	 */
	/*public static double mul(double d1, double d2) { 
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.multiply(b2).doubleValue();
	}*/
	
	public static double mul(Object d1, Object d2) { 
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 
	 * <p>进行除法运算--建议使用参数为object类型的方法</p> 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 * @author: lxfeng  
	 * @date: Created on 2018-3-15 下午4:53:47
	 */
	public static double div(double d1, double d2, int len) {
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.divide(b2, len, BigDecimal.ROUND_DOWN).doubleValue();
	}
	
	public static double div(Object d1, Object d2, int len) {
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
		BigDecimal b2 = new BigDecimal(String.valueOf(d2));
		return b1.divide(b2, len, BigDecimal.ROUND_DOWN).doubleValue();
	}
	
	/**
	 * 保留两位小数
	 * @param obj
	 * @return
	 */
	public static String format(Object obj){
		return dFormat.format(obj);
		
	}
	
	/**
	 * 
	 * <p>元转万,保留两位小数</p> 
	 * @param d
	 * @return
	 * @author: lxfeng  
	 * @date: Created on 2018-6-8 下午1:33:55
	 */
	public static String yuanToTenThousand(Double d){
		BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(10000);
        return b1.divide(b2,2,BigDecimal.ROUND_DOWN).toString();
	}
	
	public static void main(String[] args) {
		System.out.println(div(100, 3, 0));
		System.out.println(yuanToTenThousand(1234567d));
	}
}
