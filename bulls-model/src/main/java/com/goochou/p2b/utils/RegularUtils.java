package com.goochou.p2b.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author 刘源 2015-11-10
 */
public class RegularUtils {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证真实姓名
     */
    public static final String REGEX_TRUENAME = "[\\u4E00-\\u9FA5]{2,5}(?:·[\\u4E00-\\u9FA5]{2,5})*";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
//    public static final String REGEX_MOBILE = "^(13[0-9]|15[012356789]|17[05678]|18[0-9]|14[579]|19[1-9])[0-9]{8}$";
    public static final String REGEX_MOBILE = "^1([38][0-9]|4[5679]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";


    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式：验证银行卡
     */
    public static final String REGEX_CARD = "(\\d{16}|\\d{19})";

    /***
     * 头条广告请求参数
     */
    public static final String REGEX_PARAM_TD = "^__\\w+__$";
    
    /***
     * 验证空格
     */
    public static final String REGEX_BLANK = "\\s";
    

    /**
     * 校验字符串
     *
     * @param checkString 校验字符串
     * @param regex       正则表达式
     * @return boolean
     */
    public static boolean checkString(String checkString, String regex) {
        if (StringUtils.isEmpty(checkString) | StringUtils.isEmpty(regex)) {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(checkString);
        return m.find();
    }

    /**
     * 获取子字符串个数
     *
     * @param regs       所求个数字符串
     * @param initString 资源字符串
     * @return
     */
    public static int getSubStringCount(String regs, String initString) {
        int i = 0;
        Matcher m = Pattern.compile(regs).matcher(initString);
        while (m.find()) {
            System.out.println(m.group() + ":" + m.start());
            i++;
        }
        return i;
    }
    public static void main(String[] args) {
        System.out.println(checkString("sus23",REGEX_BLANK));
    }

}

