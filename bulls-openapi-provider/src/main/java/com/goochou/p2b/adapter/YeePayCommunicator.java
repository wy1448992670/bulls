package com.goochou.p2b.adapter;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.utils.JSONAlibabaUtil;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import com.yeepay.g3.sdk.yop.client.YopRsaClient;
import com.yeepay.g3.sdk.yop.encrypt.CertTypeEnum;
import com.yeepay.g3.sdk.yop.encrypt.DigestAlgEnum;
import com.yeepay.g3.sdk.yop.encrypt.DigitalSignatureDTO;
import com.yeepay.g3.sdk.yop.utils.DigitalEnvelopeUtils;

import sun.misc.BASE64Decoder;

/**
 * 易宝支付路由
 * 
 * @author ydp
 * @date 2019/08/28
 */
public class YeePayCommunicator implements ICommunicator {

    private static final Logger logger = Logger.getLogger(YeePayCommunicator.class);

    @Override
    public Response httSend2(Request request, String method) throws CommunicateException {
        return null;
    }

    @Override
    public String httSend(String data, String method, String requestType) throws CommunicateException {
        String result = "";
        // 发送内容
        JSONObject jsonParam = JSONAlibabaUtil.parseString(data);
        switch (method) {
            case PayConstants.METHOD_CREATE_PAY:
                try {
                    String orderNo = jsonParam.getString("orderNo");
                    String amount = jsonParam.getString("amount");
                    String userId = jsonParam.getString("userId");
                    String phone = jsonParam.getString("phone");
                    String payResult = jsonParam.getString("payResult");
                    String subject = jsonParam.getString("subject");
                    String trueName = jsonParam.getString("trueName");
                    String identityCard = jsonParam.getString("identityCard");
                    Map<String,String> res=createOrder(orderNo, amount, payResult, subject, trueName, identityCard);
                    result=geturl(res.get("token"), phone);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PayConstants.METHOD_QUICK_QUERY_ORDER:
                try {
                    String orderNo = jsonParam.getString("orderNo");
                    Map<String,String> res = queryOrder(orderNo);
                    result= JSONArray.toJSONString(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PayConstants.METHOD_REFUND:
                try {
                    String orderNo = jsonParam.getString("orderNo");
                    String amount = jsonParam.getString("amount");
                    String outOrderNo = jsonParam.getString("outOrderNo");
                    Map<String,String> res = refundOrder(orderNo, amount, outOrderNo);
                    result= JSONArray.toJSONString(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        logger.info("result = " + result);
        return result;
    }

    public static Map<String, String> createOrder(String orderNo, String amount, String payResult, String subject, String trueName, String identityCard) throws IOException {
        Map<String, String> result = new HashMap<>();

        YopRequest request = new YopRequest(PayConstants.YEEPAY_APP_KEY, PayConstants.YEEPAY_PRIVATE_KEY);

        // g构造请求参数
        request.addParam("parentMerchantNo", PayConstants.YEEPAY_PARENTMERCHANTNO);
        request.addParam("merchantNo", PayConstants.YEEPAY_MERCHANTNO);
        request.addParam("orderId", orderNo);
        request.addParam("orderAmount", amount);
        // request.addParam("timeoutExpress", timeoutExpress);
        // request.addParam("requestDate", requestDate);
        request.addParam("redirectUrl", payResult);
        request.addParam("notifyUrl", PayConstants.ADVITE_URL.replace("/backRecharge", "/backRechargeByYeepay"));
        request.addParam("goodsParamExt",
            "{\"goodsName\":\"" + subject + "\",\"goodsDesc\":\"" + subject + "\"}");
        //支付扩展参数
        if(StringUtils.isNotEmpty(trueName)) {
            request.addParam("paymentParamExt", "{\"idCardNo\":\""+identityCard+"\",\"cardName\":\""+trueName+"\"}");
        }
        YopResponse response = YopRsaClient.post(PayConstants.YEEPAY_TRADEORDER_URI, request);// 发送请求
        logger.info(response.toString());// 输出返回
        // 解析返回
        if ("FAILURE".equals(response.getState())) {
            if (response.getError() != null)
                result.put("code", response.getError().getCode());
            result.put("message", response.getError().getMessage());
            return result;
        }
        if (response.getStringResult() != null) {
            result = parseResponse(response.getStringResult());
        }
        return result;
    }
    
    public static Map<String, String> queryOrder(String orderNo) throws IOException {
        Map<String, String> result = new HashMap<>();

        YopRequest request = new YopRequest(PayConstants.YEEPAY_APP_KEY, PayConstants.YEEPAY_PRIVATE_KEY);

        // g构造请求参数
        request.addParam("parentMerchantNo", PayConstants.YEEPAY_PARENTMERCHANTNO);
        request.addParam("merchantNo", PayConstants.YEEPAY_MERCHANTNO);
        request.addParam("orderId", orderNo);

        YopResponse response = YopRsaClient.post(PayConstants.YEEPAY_TRADEORDER_QUERY, request);// 发送请求
        logger.info(response.toString());// 输出返回
        // 解析返回
        if ("FAILURE".equals(response.getState())) {
            if (response.getError() != null)
                result.put("code", response.getError().getCode());
            result.put("message", response.getError().getMessage());
            return result;
        }
        if (response.getStringResult() != null) {
            result = parseResponse(response.getStringResult());
        }
        return result;
    }
    
    public static Map<String, String> refundOrder(String orderNo, String amount, String outOrderNo) throws IOException {
        Map<String, String> result = new HashMap<>();

        YopRequest request = new YopRequest(PayConstants.YEEPAY_APP_KEY, PayConstants.YEEPAY_PRIVATE_KEY);
        // g构造请求参数
        request.addParam("parentMerchantNo", PayConstants.YEEPAY_PARENTMERCHANTNO);
        request.addParam("merchantNo", PayConstants.YEEPAY_MERCHANTNO);
        request.addParam("orderId", orderNo);
        request.addParam("refundRequestId", orderNo+System.currentTimeMillis());
        request.addParam("uniqueOrderNo", outOrderNo);
        request.addParam("refundAmount", amount);

        YopResponse response = YopRsaClient.post(PayConstants.YEEPAY_TRADEORDER_REFUND, request);// 发送请求
        logger.info(response.toString());// 输出返回
        // 解析返回
        if ("FAILURE".equals(response.getState())) {
            if (response.getError() != null)
                result.put("code", response.getError().getCode());
            result.put("message", response.getError().getMessage());
            return result;
        }
        if (response.getStringResult() != null) {
            result = parseResponse(response.getStringResult());
        }
        return result;
    }

    // 将获取到的response转换成json格式
    public static Map<String, String> parseResponse(String response) {

        Map<String, String> jsonMap = new HashMap<>();
        jsonMap = JSON.parseObject(response, new TypeReference<TreeMap<String, String>>() {});

        return jsonMap;
    }

    // 拼接支付链接
    public static String geturl(String token, String phone) throws IOException {
        String url = PayConstants.YEEPAY_CASHIER;
        Map<String, String> params = new HashMap<>();
        // 系统商商编
        params.put("merchantNo", PayConstants.YEEPAY_MERCHANTNO);
        // token 调创建订单接口获取
        params.put("token", token);
        // 时间戳
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        // 直联编码
        params.put("directPayType", "");
        // 卡类型只适用于银行卡快捷支付
        params.put("cardType", "");
        // 用户标识银行卡快捷支付用于记录绑卡
        params.put("userNo", phone);
        // 用户标识类型
        params.put("userType", "PHONE");

        // 构建请求收银台签名串，注意如果传参ext，也需参与签名

        // 需要生成sign签名的参数
        String[] CASHIER = {"merchantNo", "token", "timestamp", "directPayType", "cardType", "userNo", "userType"};
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < CASHIER.length; i++) {
            String name = CASHIER[i];
            String value = params.get(name);
            if (i != 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(name + "=").append(value);
        }
        System.out.println(stringBuilder);
        String sign = getSign(stringBuilder.toString());
        System.out.println(sign);
        url = url + ("?sign=" + sign + "&" + stringBuilder);

        return url;
    }

    // 获取请求收银台的sign
    private static String getSign(String stringBuilder) {
        PrivateKey isvPrivateKey = getPrivateKey(PayConstants.YEEPAY_PRIVATE_KEY);
        DigitalSignatureDTO digitalSignatureDTO = new DigitalSignatureDTO();
        digitalSignatureDTO.setAppKey(PayConstants.YEEPAY_APP_KEY);
        digitalSignatureDTO.setCertType(CertTypeEnum.RSA2048);
        digitalSignatureDTO.setDigestAlg(DigestAlgEnum.SHA256);
        digitalSignatureDTO.setPlainText(stringBuilder);
        String sign = DigitalEnvelopeUtils.sign0(digitalSignatureDTO, isvPrivateKey);
        return sign;

    }

    // 获取私钥对象
    private static PrivateKey getPrivateKey(String priKey) {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec priPKCS8;
        try {
            priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(priKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            privateKey = keyf.generatePrivate(priPKCS8);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }
}