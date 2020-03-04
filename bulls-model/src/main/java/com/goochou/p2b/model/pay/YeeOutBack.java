package com.goochou.p2b.model.pay;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.pay.BaseOutPay;
import com.goochou.p2b.constant.pay.BasePayBack;

import sun.misc.BASE64Decoder;

/**
 * 易宝支付回调
 * 
 * @author ydp
 * @date 2019/08/29
 */
public class YeeOutBack extends BasePayBack {

    public YeeOutBack() {}

    @Override
    public String getOutPayId() {
        return "uniqueOrderNo";
    }

    @Override
    public String getAmount() {
        return "payAmount";
    }

    @Override
    public String getBackInfo() {
        return "trxreserved";
    }

    @Override
    public String getStatus() {
        return "SUCCESS";
    }

    @Override
    public String getSign() {
        return "sign";
    }

    @Override
    public String getStatusName() {
        return "status";
    }

    @Override
    public String getInfoCiphertext(String outPayId, String amount, String backInfo, String status, String signKey,
        String resultDesc, String additionalInfo, String succTime, String batchNo, String sn, String currency,
        String accountNo, String mobile, String resCode, String settAmount, String orderNo) {
        return null;
    }

    @Override
    public Boolean getCent() {
        return false;
    }

    @Override
    public String getResultDesc() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAdditionalInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSuccTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getRes() {
        return "SUCCESS";
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
        return "orderId";
    }

    @Override
    public String getDataContent() {
        return null;
    }

    @Override
    public Boolean checkSign(HttpServletRequest request, BaseOutPay baseOutPay) {
        return null;
    }

    public static Map<String, String> parseResponse(String response) {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap = JSON.parseObject(response, new TypeReference<TreeMap<String, String>>() {});
        return jsonMap;
    }

    /**
     * String 转换为publickey
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * String转私钥PrivateKey
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
}
