package com.goochou.p2b.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.common.base.Joiner;

/**
 * 字符串工具类
 * @author	刘源 2015-11-10
 *
 */
public class StringUtils {

	public static boolean isEmpty(String str){
		if(str == null || "".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	 /**
     * 字符串的正则匹配
     *
     * @param test
     *            正则表达试
     * @param str
     *            比较字符串
     * @return
     */
    public static boolean pattern(String test, String str) {
        Pattern p = Pattern.compile(test);
        Matcher m = p.matcher(str);
        return m.find();
    }


    /**
     * 检查字符串是否不是<code>null</code>和空字符串<code>""</code>。
     * <pre>
     * StringUtil.isEmpty(null)      = false
     * StringUtil.isEmpty("")        = false
     * StringUtil.isEmpty(" ")       = true
     * StringUtil.isEmpty("bob")     = true
     * StringUtil.isEmpty("  bob  ") = true
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果不为空, 则返回<code>true</code>
     */
    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));
    }

    /**
     * 获取字符串的长度
     *
     * @param str
     * @return
     */
    public static int strLength(String str) {
        return str.length();
    }

    /**
     * 判断是否全为数字,全为返回true,否则返回false
     * @param str
     * @return
     */
    public static boolean checkInt(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * @param str 要检查的字符串
     *
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查字符串是否不是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * StringUtil.isBlank(null)      = false
     * StringUtil.isBlank("")        = false
     * StringUtil.isBlank(" ")       = false
     * StringUtil.isBlank("bob")     = true
     * StringUtil.isBlank("  bob  ") = true
     * @param str 要检查的字符串
     *
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 比较两个字符串（大小写敏感）。
     * <pre>
     * StringUtil.equals(null, null)   = true
     * StringUtil.equals(null, "abc")  = false
     * StringUtil.equals("abc", null)  = false
     * StringUtil.equals("abc", "abc") = true
     * StringUtil.equals("abc", "ABC") = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     *
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }

    /**
     * 比较两个字符串（大小写不敏感）。
     * <pre>
     * StringUtil.equalsIgnoreCase(null, null)   = true
     * StringUtil.equalsIgnoreCase(null, "abc")  = false
     * StringUtil.equalsIgnoreCase("abc", null)  = false
     * StringUtil.equalsIgnoreCase("abc", "abc") = true
     * StringUtil.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     *
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equalsIgnoreCase(str2);
    }

    /* ============================================================================ */
    /*  大小写转换。                                                                */
    /* ============================================================================ */

    /**
     * 将字符串转换成大写。
     *
     * <p>
     * 如果字符串是<code>null</code>则返回<code>null</code>。
     * <pre>
     * StringUtil.toUpperCase(null)  = null
     * StringUtil.toUpperCase("")    = ""
     * StringUtil.toUpperCase("aBc") = "ABC"
     * </pre>
     * </p>
     *
     * @param str 要转换的字符串
     *
     * @return 大写字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String toUpperCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toUpperCase();
    }

    /**
     * 将字符串转换成小写。
     *
     * <p>
     * 如果字符串是<code>null</code>则返回<code>null</code>。
     * <pre>
     * StringUtil.toLowerCase(null)  = null
     * StringUtil.toLowerCase("")    = ""
     * StringUtil.toLowerCase("aBc") = "abc"
     * </pre>
     * </p>
     *
     * @param str 要转换的字符串
     *
     * @return 大写字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String toLowerCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toLowerCase();
    }

    /**
     * 返回card的前四后四位
     *
     * @param card
     * @return
     */
    public static String parseIdCard(String card) {
        if (card == null || "".equals(card)) {
            return null;
        }
        return card.trim().substring(0, 4) + "****" + card.trim().substring(card.length() - 4, card.length());
    }

    /**
     * 返回card的前三后四位
     *
     * @param source
     * @return
     */
    public static String parseFirstThreeAndLastFour(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        if (source.length() < 4) {
            return source;
        }
        return source.trim().substring(0, 3) + "****" + source.trim().substring(source.length() - 4, source.length());
    }

    /**
     * 返回卡号后四位
     * @param source
     * @return
     */
    public static String parseLastFourCardNo(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        if (source.length() < 4) {
            return source;
        }
        return "*" + source.substring(source.length() - 4);
    }
    /**
     * 返回姓名的首末位
     *
     * @param card
     * @return
     */
    public static String parseName(String card) {
        if (card == null || "".equals(card)) {
            return null;
        }
        return card.trim().substring(0, 1) + "***" + card.trim().substring(card.length() - 1, card.length());
    }

    public static String parseFirstName(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return source.trim().substring(0, 1) + "*";
    }


    /**
     * 处理空字符串
     *
     * @param str
     */
    public static String handleStringNull(String str) {
        return str == null || str.trim().length() == 0 || "null".equals(str) ? "" : str;
    }

    /**
     * 查询条件空处理
     *
     * @param str
     * @return
     */
    public static String handleQueryNull(String str) {
        return isBlank(str) ? null : str.trim();
    }

    /**
     * 处理HTML字符串
     *
     * @param str
     */
    public static String handleHtmlString(String str) {
        return handleStringNull(str) == "" ? "" : str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }



    /**
     * 判断是否为多音字
     */
    public static boolean isPolyphone(char t1) {
        boolean flag = false;
        if (t1 == '重' || t1 == '厦' || t1 == '长' || t1 == '柏' || t1 == '蚌') {
            flag = true;
        }
        return flag;
    }

	public static String handleFlowOperate(Integer operatorId) {
		String [] operatorName=new String[]{"移动","联通","电信"};
		return operatorName[operatorId];
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符、问号和逗号
                      注： \n 回车(\u000a)
          \t 水平制表符(\u0009)
          \s 空格(\u0008)
          \r 换行(\u000d)
	 * @return dest
	 */
	public static String handleTrim(String str){
		String dest = "";
        if (str!=null) {
        	Pattern p = Pattern.compile("[\\s]|[\t]|[\r]|[\n]|[?]|[,]");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
	}
	
	public static String join(Iterable<?> parts) {
		return StringUtils.join(parts,",");
	}
	
	public static String join(Iterable<?> parts,String separator) {
		if(parts == null) return "";
		return Joiner.on(separator == null ? "" : separator).join(parts);
	}
}
