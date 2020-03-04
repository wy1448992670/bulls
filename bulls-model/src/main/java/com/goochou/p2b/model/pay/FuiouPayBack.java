package com.goochou.p2b.model.pay;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import javax.servlet.http.HttpServletRequest;

import com.fuiou.util.MD5;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.pay.BaseOutPay;
import com.goochou.p2b.constant.pay.BasePayBack;
import com.goochou.p2b.constant.pay.PayConstants;

import sun.misc.BASE64Decoder;

/**
 * Created on 2014-8-26
 * <p>Title:       上海橙旗金融线上交易平台_[子系统统名]_[模块名]/p>
 * <p>Description: [汇潮回调参数]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     上海橙旗金融线上交易平台</p>
 * <p>Department:  网贷中心开发部</p>
 * @author         [叶东平] [58294114@qq.com]
 * @version        1.0
 */
public class FuiouPayBack extends BasePayBack
{
    
    public FuiouPayBack(){}

    @Override
    public String getOutPayId()
    {
        return "fy_ssn";
    }

    @Override
    public String getAmount()
    {
        return "order_amt";
    }

    @Override
    public String getBackInfo()
    {
        return "order_pay_error";
    }

    @Override
    public String getStatus()
    {
        return "0000";
    }

    @Override
    public String getSign()
    {
        return "md5";
    }

    @Override
    public String getStatusName()
    {
        return "order_pay_code";
    }
    
    @Override
    public String getInfoCiphertext(String outPayId, String amount,
            String backInfo, String status, String signKey, String resultDesc, String additionalInfo, String succTime, 
            String batchNo, String sn, String currency, String accountNo, String mobile, String resCode, String settAmount, String orderNo)
    {
        String sign = outPayId+"&"+amount+"&"+status+"&"+signKey;
        return MD5.MD5Encode(sign).toUpperCase();
    }

    @Override
    public Boolean getCent()
    {
        return true;
    }

    @Override
    public String getResultDesc()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAdditionalInfo()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSuccTime()
    {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public String getRes() {
		return "success";
	}

	@Override
	public String getRequestType() {
		return Constants.REQUEST_POST;
	}

	@Override
	public String getBatchNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccountNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMobile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSettAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrderNo() {
		return "order_id";
	}

	@Override
	public String getDataContent() {
		return null;
	}

	@Override
	public Boolean checkSign(HttpServletRequest request, BaseOutPay baseOutPay) {
		boolean signFlag = false;
        try {
            String order_id = request.getParameter("order_id");
            String order_pay_error = request.getParameter("order_pay_error");
            String order_pay_code = request.getParameter("order_pay_code");
            String mchnt_cd = request.getParameter("mchnt_cd");
            String order_date = request.getParameter("order_date");
            String order_st = request.getParameter("order_st");
            String fy_ssn = request.getParameter("fy_ssn");
            String order_amt = request.getParameter("order_amt");
            String signPain = "";
            String sign = "";
            if ("DIRPAY".equals(request.getParameter("pay_type"))) {
                signPain = new StringBuffer().append(mchnt_cd).append("|").append(request.getParameter("user_id"))
                        .append("|").append(order_id).append("|").append(order_amt).append("|").append(order_date).append("|")
                        .append(request.getParameter("card_no")).append("|").append(fy_ssn).toString();
                sign = request.getParameter("RSA");
                signFlag = decrypt(signPain, PayConstants.FUIOU_WEB_QUICK_PUB_KEY, sign);
            } else {
                String resv1 = request.getParameter("resv1");
                sign = request.getParameter("md5");
                signPain = new StringBuffer().append(mchnt_cd).append("|").append(order_id).append("|").append(order_date)
                        .append("|").append(order_amt).append("|").append(order_st).append("|").append(order_pay_code).append("|")
                        .append(order_pay_error).append("|").append(resv1).append("|").append(fy_ssn).append("|").append(PayConstants.FUIOU_B2C_MCHNT_KEY).toString();
                signFlag = MD5.MD5Encode(signPain).equals(sign);
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        return signFlag;
	}
	
	/**
     * 富友RSA解密
     *
     * @param signDataStr 签名内容
     * @param publicKey   公钥
     * @param rspRSA      网关返回的签名
     * @return
     * @throws Exception
     */
    public static boolean decrypt(String signDataStr, String publicKey, String rspRSA) throws Exception {
        byte[] keyBytes = decryptBASE64(publicKey);   // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);   // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");   // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(pubKey);
        signature.update(signDataStr.getBytes("gbk"));   // 验证签名是否正常
        boolean ret = signature.verify(decryptBASE64(rspRSA));
        return ret;
    }
    
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }
}
