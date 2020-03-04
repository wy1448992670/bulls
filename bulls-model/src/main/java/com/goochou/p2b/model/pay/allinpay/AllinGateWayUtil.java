 package com.goochou.p2b.model.pay.allinpay;

import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

import com.goochou.p2b.constant.pay.PayConstants;

import net.sf.json.JSONObject;

/**
 * @author ydp
 * @date 2019/08/01
 */
public class AllinGateWayUtil {
    public static <T> T json2Obj(String jsonstr,Class<T> cls){
        JSONObject jo =JSONObject.fromObject(jsonstr);
        T obj = (T)JSONObject.toBean(jo, cls);
        return obj;
    }
    
     public static String md5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
             md.reset();
             md.update(data.getBytes("utf-8"));
             byte[] hash = md.digest();
             StringBuffer outStrBuf = new StringBuffer(32);
             for (int i = 0; i < hash.length; i++) {
                 int v = hash[i] & 0xFF;
                 if (v < 16) {
                    outStrBuf.append('0');
                 }
                 outStrBuf.append(Integer.toString(v, 16).toLowerCase());
             }
             return outStrBuf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
           
    }
    
    public static String sign(TreeMap<String,String> params,String key){
        params.put("key", key);
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> entry:params.entrySet()){
            if(entry.getValue()!=null&&entry.getValue().length()>0){
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        if(sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
        }
        System.out.println("明文:"+sb.toString());
        String sign = md5(sb.toString()).toUpperCase();
        System.out.println("密文:"+sign);
        params.remove("key");
        return sign;
    }
    
    public static String sign(String appid,String cusid,String charset,String trxamt,String orderid,
            String paytype,String gateid,String randomstr,String goodsinf,String goodsid){
        String validtime = "30";
        TreeMap<String,String> params = new TreeMap<String, String>();
        params.put("appid", appid);
        params.put("cusid", cusid);
        params.put("charset", charset);
        params.put("trxamt", trxamt);
        params.put("orderid", orderid);
        params.put("paytype", paytype);
        params.put("gateid", gateid);
        params.put("randomstr", randomstr);
        params.put("goodsinf", goodsinf);
        params.put("goodsid", goodsid);
        params.put("validtime", validtime);
        params.put("returl", PayConstants.RETURN_URL+"?orderNo="+orderid);
        params.put("notifyurl", PayConstants.ADVITE_URL);
        
        return sign(params, PayConstants.ALLINPAY_MCHNT_KEY);
        
    }
    public static boolean validSign(TreeMap<String,String> param,String appkey) throws Exception{
        if(param!=null&&!param.isEmpty()){
            if(!param.containsKey("sign")){
                return false;
            }
            param.put("key", appkey);//将分配的appkey加入排序
            StringBuilder sb = new StringBuilder();
            String sign = param.get("sign").toString();
            param.remove("sign");
            for(String key:param.keySet()){
                String value = param.get(key);
                sb.append(key).append("=").append(value).append("&");
            }
            if(sb.length()>0){
                sb.deleteCharAt(sb.length()-1);
            }
            String blank = sb.toString();
            System.out.println(blank+";"+sign);
            return sign.toLowerCase().equals(md5(blank));
        }
        return false;
    }
}
