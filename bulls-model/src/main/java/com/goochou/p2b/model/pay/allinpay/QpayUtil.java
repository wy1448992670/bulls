package com.goochou.p2b.model.pay.allinpay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.model.pay.allinpay.xstruct.common.InfoReq;

import net.sf.json.JSONObject;

public class QpayUtil {

	public static String sign(Map<String,String> params,String appkey) throws Exception{
		if(params.containsKey("sign"))//签名明文组装不包含sign字段
			params.remove("sign");
		params.put("key", appkey);
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry:params.entrySet()){
			if(entry.getValue()!=null&&entry.getValue().length()>0){
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		String sign = md5(sb.toString().getBytes("UTF-8"));//记得是md5编码的加签
		System.out.println("sign:"+sign+":"+sb.toString());
		params.remove("key");
		return sign;
	}
	
	 public static boolean validSign(TreeMap<String,String> param,String appkey) throws Exception{
		 if(param!=null&&!param.isEmpty()){
			 if(!param.containsKey("sign"))
	    			return false;
			 String sign = param.get("sign").toString();
			 String mysign = sign(param, appkey);
			 return sign.toLowerCase().equals(mysign.toLowerCase());
		 }
		 return false;
	 }
	 
	 
	 public static Map<String, String> dorequest(String url,Map<String, String> params,String appkey) throws Exception{
		 params.put("sign", sign(params,appkey));
		 HttpConnectionUtil http = new HttpConnectionUtil(url);
		 http.init();
		 System.out.println("post params: " + params);
		 byte[] bys = http.postParams(params, true);
		 String result = new String(bys,"UTF-8");
		 Map<String,String> map = handleResult(result,appkey);
		 return map;
	 }
	 
	private static Map<String,String> handleResult(String result,String appkey) throws Exception{
		System.out.println("返回:"+result+"");
		Map map = json2Obj(result, Map.class);
		if(map == null){
			throw new Exception("返回数据错误");
		}
		if("SUCCESS".equals(map.get("retcode"))){
			TreeMap tmap = new TreeMap();
			tmap.putAll(map);
			String sign = tmap.remove("sign").toString();
			String sign1 = "";
			sign1 = sign(tmap,appkey);
			if(sign1.toLowerCase().equals(sign.toLowerCase())){
				return map;
			}else{
				throw new Exception("验证签名失败");
			}
			
		}else{
			return map;
		}
	}
	
	public static <T> T json2Obj(String jsonstr,Class<T> cls){
    	JSONObject jo =JSONObject.fromObject(jsonstr);
		T obj = (T)JSONObject.toBean(jo, cls);
		return obj;
    }
	
	public static String md5(byte[] b) {
        try {
        	MessageDigest md = MessageDigest.getInstance("MD5");
        	 md.reset();
             md.update(b);
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new String(b);
        }
    }
	
	private static Provider prvd = null;
    
    static{
        prvd = new BouncyCastleProvider();
    }
    
    public static InfoReq makeReq(String trxcod){
        InfoReq info=new InfoReq();
        info.setTRX_CODE(trxcod);
        info.setREQ_SN(PayConstants.ALLINPAY_PROVIDED_MCHNT_CD + String.format("-%016d",System.currentTimeMillis()));
        /*info.setREQ_SN("PW20181229001");*/
        info.setUSER_NAME(PayConstants.ALLINPAY_PROVIDED_USER);
        info.setUSER_PASS(PayConstants.ALLINPAY_PROVIDED_PWD);
        info.setLEVEL("5");
        info.setDATA_TYPE("2");
        info.setVERSION("04");
        info.setMERCHANT_ID(PayConstants.ALLINPAY_PROVIDED_MCHNT_CD);
        return info;
    }
    
    public static String getNow(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }
    
    /**
     * @param xmlMsg   待签名报文
     * @param pathPfx   
     * @param pass
     * @return  签名后的报文
     * @throws AIPGException
     */
    public static String buildSignedXml(String xmlMsg) throws AIPGException{
        if(xmlMsg == null){
            throw new AIPGException("传入的加签报文为空");
        }
        String IDD_STR="<SIGNED_MSG></SIGNED_MSG>";
        if(xmlMsg.indexOf(IDD_STR) == -1){
            throw new AIPGException("找不到签名信息字段");
        }
        String strMsg = xmlMsg.replaceAll(IDD_STR, "");
        AIPGSignature signature = new AIPGSignature(prvd);
        System.out.println("加签内容"+strMsg);
        String signedStr = signature.signMsg(strMsg, PayConstants.ALLINPAY_PROVIDED_PFX_PATH, PayConstants.ALLINPAY_PROVIDED_PFX_PWD);
        String strRnt = xmlMsg.replaceAll(IDD_STR, "<SIGNED_MSG>" + signedStr + "</SIGNED_MSG>");
        return strRnt;
    }
    
    /**
     * @param xmlMsg  返回报文
     * @param pathCer  通联公钥
     * @return
     * @throws AIPGException
     */
    public static boolean verifyXml(String xmlMsg) throws AIPGException{
        if(xmlMsg == null){
            throw new AIPGException("传入的验签报文为空");
        }
        int pre = xmlMsg.indexOf("<SIGNED_MSG>");
        int suf = xmlMsg.indexOf("</SIGNED_MSG>");
        if(pre == - 1 || suf == -1 || pre >= suf){
            throw new AIPGException("找不到签名信息");
        }
        String signedStr = xmlMsg.substring(pre + 12, suf);
        String msgStr = xmlMsg.substring(0, pre) + xmlMsg.substring(suf + 13);
        AIPGSignature signature = new AIPGSignature(prvd);
        return signature.verifyMsg(signedStr, msgStr, PayConstants.ALLINPAY_PROVIDED_PUB_PATH);
    }
}
