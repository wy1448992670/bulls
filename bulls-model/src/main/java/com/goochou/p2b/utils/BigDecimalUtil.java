package com.goochou.p2b.utils;

import java.math.BigDecimal;

/**
 * 提供精确的加减法计算
 *
 * @author irving
 */
public class BigDecimalUtil {

    public static BigDecimal parse(int d){
        return new BigDecimal(String.valueOf(d));
    }

    public static BigDecimal parse(Double d){
        return new BigDecimal(Double.toString(d));
    }

    public static BigDecimal parse(String s){
        return new BigDecimal(s);
    }

    public static Double sub(Object a, Object b) {
        return new BigDecimal(a.toString()).subtract(new BigDecimal(b.toString())).doubleValue();
    }

    public static Double add(Object a, Object b) {
        return new BigDecimal(a.toString()).add(new BigDecimal(b.toString())).doubleValue();
    }

    public static Double sub(Object... obj) {//重写 累减方法    默认取第一个为被减数
        BigDecimal r1 = new BigDecimal("0");
        int i = 0;
        for (Object object : obj) {
            if (object == null) {
                object = 0;
            }
            if (i == 0) {
                r1 = new BigDecimal(object.toString());
                i++;
            } else {
                r1 = r1.subtract(new BigDecimal(object.toString()));
            }
        }
        return r1.doubleValue();
    }

    public static Double multi(Object o1, Object o2) {
        return new BigDecimal(o1.toString()).multiply(new BigDecimal(o2.toString())).doubleValue();
    }

    /**
     * N个参数相加
     *
     * @param obj
     * @return
     */
    public static Double add(Object... obj) {
        BigDecimal r1 = new BigDecimal("0");
        for (Object object : obj) {
            r1 = r1.add(new BigDecimal(object.toString()));
        }
        return r1.doubleValue();
    }

    /**
     * 精确保留两位小数，不做四舍五入操作
     *
     * @param a
     * @return
     */
    public static Double fixed2(Object a) {
        return Double.parseDouble(String.format("%.2f", new BigDecimal(a.toString()).setScale(2, BigDecimal.ROUND_FLOOR).doubleValue()));
    }

    public static Double fixedDecimal(Object a, int decimal) {
        return Double.parseDouble(String.format("%." + decimal + "f", new BigDecimal(a.toString()).setScale(decimal, BigDecimal.ROUND_FLOOR).doubleValue()));
    }
    
    /**
     * 精确保留两位小数，四舍五入操作
     */
    public static Double cfixed2Up(Object a){
    	return Double.parseDouble(String.format("%.2f", new BigDecimal(a.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
    }
    
    /**
     * 精确保留两位小数，四舍五入操作
     */
    public static Double cfixed2UpDecimal(Object a, int decimal){
    	return Double.parseDouble(String.format("%." + decimal + "f", new BigDecimal(a.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
    }

    /**
     * @Description(描述):除法, 不做四舍五入
     * @date 2016/5/18
     * @params
     **/
    public static double divfixedWithLen(double d1, double d2, int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_FLOOR).doubleValue();
    }
    
    /**
     * @Description(描述):精确计算除法
     * @date 2016/5/18
     * @params
     **/
    public static double div(double d1, double d2, int len) {// 进行除法运算  len 精确度
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    public static double divDown(double d1, double d2, int len) {// 进行除法运算  len 精确度
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static double multiplyUp(double a,double b ,int len) {
    	BigDecimal a1=new BigDecimal(a);
    	BigDecimal b1=new BigDecimal(b);
    	return a1.multiply(b1.setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue();
    }
    
    public static void main(String[] args) {
//        System.out.println(multi(2, 2));
//        System.out.println(sub(100.1, 2.22222, 3, 4, 5, 6));
//        System.out.println(BigDecimalUtil.add(1, 2, 3));
    	
    	
//    	Double bondManagementRate = 0d;
//    	Double amount = 5000d;
//        Double exceptAmount = BigDecimalUtil.multi(amount, BigDecimalUtil.sub(0.139f, 0.009f));
//        exceptAmount = BigDecimalUtil.multi(exceptAmount, 303);
//        exceptAmount = BigDecimalUtil.div(exceptAmount, 365, 8);
//        exceptAmount = BigDecimalUtil.add(exceptAmount, BigDecimalUtil.multi(amount, bondManagementRate));
//
//        Double exceptRate = 0d;
//        exceptRate = BigDecimalUtil.div(exceptAmount, amount, 8);
//        exceptRate = BigDecimalUtil.div(exceptRate, 303, 8);
//        exceptRate = BigDecimalUtil.multi(exceptRate, 365);
//        exceptRate = BigDecimalUtil.cfixed2UpDecimal(BigDecimalUtil.multi(exceptRate, 100), 1);
//    	
//    	System.out.println(BigDecimalUtil.sub(exceptRate));
//    	
//        System.out.println(BigDecimalUtil.fixed2(BigDecimalUtil.multi(0.138f, 100)));
//        System.out.println(BigDecimalUtil.fixed2(0.138f * 100));
//        
//        System.out.println(BigDecimalUtil.fixed2(BigDecimalUtil.multi(0.011, 100)));
    	System.out.println(div(7d,100d,2));
    }
}
