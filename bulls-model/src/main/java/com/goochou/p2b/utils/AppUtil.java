package com.goochou.p2b.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 
 * Created on 2015-5-5
 * <p>Title:       中投融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [APP专用工具类]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     中投融线上交易平台</p>
 * <p>Department:  研发中心</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class AppUtil{
    /**
     * 
     *  Created on 2015-5-5 
     * <p>Discription:[str空判断]</p>
     * @author:[叶东平]
     * @update:[日期2015-5-5] [叶东平]
     * @return boolean .
     */
    public static boolean isnull(String str) {
        if (null == str || str.equalsIgnoreCase("null") || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
    *  
    *  Created on 2015-5-5 
    * <p>生成待签名串</p>
    * @author:[叶东平]
    * @update:[日期2015-5-5] [叶东平]
    * @return String .
    */
    public static String genSignData(Map map) {
    	JSONObject jsonObject=JSONObject.fromObject(map);
        StringBuffer content = new StringBuffer();
        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(jsonObject.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = jsonObject.getString(key);
            // 空串不参与签名
            if (isnull(value)) {
                continue;
            }
            //为list不参与签名 （"[{},{}]"）
            if(value.startsWith("[")){
            	continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&")) {
            signSrc = signSrc.replaceFirst("&", "");
        }
        return signSrc;
    }

    /**
     * 
     *  Created on 2015-5-5 
     * <p>Discription:[加签]</p>
     * @author:[叶东平]
     * @update:[日期2015-5-5] [叶东平]
     * @return String .
     */
    public static String addSign(String sign_src, String md5_key) {
        if (sign_src == null) {
            return "";
        }
        return addSignMD5(sign_src, md5_key);
    }

    /**
     *  
     *  Created on 2015-5-5 
     * <p>Discription:[签名验证]</p>
     * @author:[叶东平]
     * @update:[日期2015-5-5] [叶东平]
     * @return boolean .
     */
    public static boolean checkSign(String sign, Map map, String md5_key) {
        if (map == null) {
            return false;
        }
        return checkSignMD5(sign, map, md5_key);
    }

    /**
     * 国槐签名验证
     * @param sign
     * @param map
     * @param md5_key
     * @return
     */
    public static boolean checkSign_GH(String sign, Map map, String md5_key) {
        if (map == null) {
            return false;
        }
        return checkSignMD5_GH(sign, map, md5_key);
    }
    /**
     * 
     *  Created on 2015-5-5 
     * <p>Discription:[MD5签名验证]</p>
     * @author:[叶东平]
     * @update:[日期2015-5-5] [叶东平]
     * @return boolean .
     */
    private static boolean checkSignMD5(String sign, Map map, String md5_key) {
        if (map == null) {
            return false;
        }
        // 生成待签名串
        String sign_src = genSignData(map);
        System.out.println("待签名原串" + sign_src);
        System.out.println("签名串" + sign);
        sign_src += "&key=" + md5_key;
        try {
        	String signReal = Md5Algorithm.getInstance().md5Digest(
                    sign_src.getBytes("utf-8"));
        	System.out.println("signReal="+signReal);
            if (sign.equals(signReal)) {
                System.out.println("MD5签名验证通过");
                return true;
            } else {
                System.out.println("MD5签名验证未通过");
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("MD5签名验证异常" + e.getMessage());
            return false;
        }
    }
    /**
     * 国槐签名方式
     * @param sign
     * @param map
     * @param md5_key
     * @return
     */
    private static boolean checkSignMD5_GH(String sign, Map map, String md5_key) {
        if (map == null) {
            return false;
        }
        // 生成待签名串
        String sign_src = genSignData(map);
        System.out.println("待签名原串" + sign_src);
        System.out.println("签名串" + sign);
        sign_src += md5_key;
        try {
            if (sign.equals(Md5Algorithm.getInstance().md5Digest(
                    sign_src.getBytes("utf-8")))) {
                System.out.println("MD5签名验证通过");
                return true;
            } else {
                System.out.println("MD5签名验证未通过");
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("MD5签名验证异常" + e.getMessage());
            return false;
        }
    }
    /**
     * 
     *  Created on 2015-5-5 
     * <p>Discription:[MD5加签名]</p>
     * @author:[叶东平]
     * @update:[日期2015-5-5] [叶东平]
     * @return String .
     */
    private static String addSignMD5(String sign_src, String md5_key) {
        if (sign_src == null) {
            return "";
        }
        // 生成待签名串
        System.out.println("加签原串" + sign_src);
        sign_src += "&key=" + md5_key;
        try {
            return Md5Algorithm.getInstance().md5Digest(sign_src.getBytes("utf-8"));
        } catch (Exception e) {
            System.out.println("MD5加签名异常" + e.getMessage());
            return "";
        }
    }
}
