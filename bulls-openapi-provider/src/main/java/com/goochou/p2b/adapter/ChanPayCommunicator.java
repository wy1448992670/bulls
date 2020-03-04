package com.goochou.p2b.adapter;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.hessian.Request;
import com.goochou.p2b.hessian.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.model.pay.chan.HttpProtocolHandler;
import com.goochou.p2b.model.pay.chan.HttpRequest;
import com.goochou.p2b.model.pay.chan.HttpResponse;
import com.goochou.p2b.model.pay.chan.HttpResultType;
import com.goochou.p2b.model.pay.chan.MD5;
import com.goochou.p2b.model.pay.chan.RSA;
import com.goochou.p2b.utils.JSONAlibabaUtil;

/**
 * 畅捷支付路由
 * @author ydp
 * @date 2019/06/26
 */
public class ChanPayCommunicator implements ICommunicator {
    
    private static final Logger logger = Logger.getLogger(ChanzorCommunicator.class);
    /**
     * 编码类型
     */
    private static String charset = "UTF-8";
    
    /**
     * 第三方支付接口入口
     */
    @Override
    public String httSend(String data, String method, String requestType)
        throws CommunicateException {
        String result = "";
        // 发送内容
        JSONObject jsonParam = JSONAlibabaUtil.parseString(data);
        switch(method){
        case PayConstants.METHOD_MESSAGE:
            try {
                Map<String, String> origMap = new HashMap<String, String>();
                // 2.1 基本参数
                origMap = setCommonMap(origMap);
                // 2.2 业务参数
                origMap.put("Service", "nmg_api_quick_payment_resend");
                origMap.put("TrxId", "2017030915102022");// 订单号
                origMap.put("OriTrxId", "20170309131120");// 原业务请求订单号
                origMap.put("TradeType", "auth_order");// 原业务订单类型
                result = this.gatewayPost(origMap, charset, PayConstants.CHAN_MERCHANT_PRIVATE_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case PayConstants.METHOD_PAY:
            try {
                Map<String, String> origMap = new HashMap<String, String>();
                // 2.1 基本参数
                origMap = setCommonMap(origMap);
                origMap.put("Service", "nmg_api_quick_payment_smsconfirm");// 请求的接口名称
                // 2.2 业务参数
                String trxId = Long.toString(System.currentTimeMillis());       
                origMap.put("TrxId", trxId);// 订单号

                //origMap.put("TrxId", "101149785980144593760");// 订单号
                origMap.put("OriPayTrxId", "1501123506844");// 原有支付请求订单号
                origMap.put("SmsCode", "695535");// 短信验证码
                result = this.gatewayPost(origMap, charset, PayConstants.CHAN_MERCHANT_PRIVATE_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case PayConstants.METHOD_CREATE_PAY:
            try {
                Map<String, String> origMap = new HashMap<String, String>();
                // 2.1 基本参数
                origMap = setCommonMap(origMap);
                origMap.put("Service", "nmg_biz_api_quick_payment");// 支付的接口名
                // 2.2 业务参数
                String trxId = Long.toString(System.currentTimeMillis());
                
                origMap.put("TrxId", trxId);// 订单号

                //origMap.put("TrxId", "201703131045234230112");// 订单号
                origMap.put("OrdrName", "畅捷支付");// 商品名称
                origMap.put("MerUserId", "0000001");// 用户标识（测试时需要替换一个新的meruserid）
                origMap.put("SellerId", PayConstants.CHAN_QUICK_PARTNERID);// 子账户号
                origMap.put("SubMerchantNo", "");// 子商户号
                origMap.put("ExpiredTime", "30m");// 订单有效期
                origMap.put("CardBegin", "430000");// 卡号前6位
                origMap.put("CardEnd", "7200");// 卡号后4位
                origMap.put("TrxAmt", "0.01");// 交易金额
                origMap.put("TradeType", "11");// 交易类型
                origMap.put("SmsFlag", "1");
                result = this.gatewayPost(origMap, charset, PayConstants.CHAN_MERCHANT_PRIVATE_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case PayConstants.METHOD_QUICK_QUERY_ORDER:
            try {
                Map<String, String> origMap = new HashMap<String, String>();
                // 2.1 基本参数
                origMap = setCommonMap(origMap);
                origMap.put("Service", "nmg_api_query_trade");// 请求的接口名
                // 2.2 业务参数
                origMap.put("TrxId", "2017030915202027");// 订单号
                origMap.put("OrderTrxId", "2017030915302088");// 原业务请求订单号
                origMap.put("TradeType", "refund_order");// 原业务订单类型
                result = this.gatewayPost(origMap, charset, PayConstants.CHAN_MERCHANT_PRIVATE_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case PayConstants.METHOD_REFUND:
            try {
                Map<String, String> origMap = new HashMap<String, String>();
                // 2.1 基本参数
                origMap = setCommonMap(origMap);
                // 2.2 业务参数
                origMap.put("Service", "nmg_api_refund");
                origMap.put("TrxId", "2017030915102022");// 订单号
                origMap.put("OriTrxId", "20170309131120");// 原业务请求订单号
                origMap.put("TrxAmt", "0.01");
                origMap.put("NotifyUrl", "");
                result = this.gatewayPost(origMap, charset, PayConstants.CHAN_MERCHANT_PRIVATE_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case PayConstants.METHOD_BIND_CARD:
            try {
                Map<String, String> origMap = new HashMap<String, String>();
                origMap = setCommonMap(origMap);
                // 2.1 鉴权绑卡 api 业务参数
                origMap.put("Service", "nmg_biz_api_auth_req");// 鉴权绑卡的接口名(商户采集方式)
                String trxId = Long.toString(System.currentTimeMillis());   
                System.out.println(trxId);
                origMap.put("TrxId", trxId);// 订单号
                origMap.put("ExpiredTime", "90m");// 订单有效期
                origMap.put("MerUserId", "9527840815");// 用户标识（测试时需要替换一个新的meruserid）
                origMap.put("BkAcctTp", "01");// 卡类型（00 – 银行贷记卡;01 – 银行借记卡;）
                origMap.put("BkAcctNo", this.encrypt("6214850210049661", PayConstants.CHAN_MERCHANT_PUBLIC_KEY, charset));// 卡号
                //System.out.println(this.encrypt("621483011*******", MERCHANT_PUBLIC_KEY, charset));
                origMap.put("IDTp", "01");// 证件类型 （目前只支持身份证 01：身份证）
                origMap.put("IDNo", this.encrypt("350781198408154015", PayConstants.CHAN_MERCHANT_PUBLIC_KEY, charset));// 证件号
                //System.out.println(this.encrypt("13010*********", MERCHANT_PUBLIC_KEY, charset));
                origMap.put("CstmrNm", this.encrypt("叶东平", PayConstants.CHAN_MERCHANT_PUBLIC_KEY, charset));// 持卡人姓名
                origMap.put("MobNo", this.encrypt("15001824049", PayConstants.CHAN_MERCHANT_PUBLIC_KEY, charset));// 银行预留手机号
                //信用卡
//              origMap.put("CardCvn2", "004");// cvv2码
//              origMap.put("CardExprDt", "09/21");// 有效期
                origMap.put("NotifyUrl", "http://dev.chanpay.com/receive.php");// 异步通知url
                origMap.put("SmsFlag", "1");
                origMap.put("Extension", "");
                result = this.gatewayPost(origMap, charset, PayConstants.CHAN_MERCHANT_PRIVATE_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case PayConstants.METHOD_BIND_CARD_CONFIRM:
            try {
                Map<String, String> origMap = new HashMap<String, String>();
                // 2.1 基本参数
                origMap = setCommonMap(origMap);
                origMap.put("Service", "nmg_api_auth_sms");// 鉴权绑卡确认的接口名
                // 2.1 鉴权绑卡  业务参数
                String trxId = "1561528002698";
                origMap.put("TrxId", trxId);// 订单号
                origMap.put("OriAuthTrxId", trxId);// 原鉴权绑卡订单号
                origMap.put("SmsCode", "938611");// 鉴权短信验证码
                origMap.put("NotifyUrl", "http://dev.chanpay.com/receive.php");// 异步通知地址
                result = this.gatewayPost(origMap, charset, PayConstants.CHAN_MERCHANT_PRIVATE_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case PayConstants.METHOD_WITHDRAW:
            try {
                Map<String, String> origMap = new HashMap<String, String>();
                origMap = setCommonMapCjt(origMap);
                origMap.put("Service", "cjt_dsf");// 接口名
                origMap.put("TransCode", "T10000"); // 交易码
                origMap.put("OutTradeNo", System.currentTimeMillis()+""); // 商户网站唯一订单号
                origMap.put("CorpAcctNo", "1223332343");  //可空
                origMap.put("BusinessType", "0"); // 业务类型：0对私 1对公
                origMap.put("BankCommonName", "招商银行"); // 通用银行名称
                origMap.put("BankCode", "CMB");//对公必填
                origMap.put("AccountType", "00"); // 账户类型
                origMap.put("AcctNo", encrypt("6214850210049661", PayConstants.CHAN_MERCHANT_PUBLIC_KEY, charset)); // 对手人账号(此处需要用真实的账号信息)
                origMap.put("AcctName", encrypt("叶东平", PayConstants.CHAN_MERCHANT_PUBLIC_KEY, charset)); // 对手人账户名称
                origMap.put("TransAmt", "0.01");
                
                //************** 以下信息可空  *******************
//              map.put("Province", "甘肃省"); // 省份信息
//              map.put("City", "兰州市"); // 城市信息
//              map.put("BranchBankName", "中国建设银行股份有限公司兰州新港城支行"); // 对手行行名
//              map.put("BranchBankCode", "105821005604");
//              map.put("DrctBankCode", "105821005604");
//              map.put("Currency", "CNY");
//              map.put("LiceneceType", "01");
//              map.put("LiceneceNo", ChanPayUtil.encrypt("622225199209190017", BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
//              map.put("Phone", ChanPayUtil.encrypt("17001090000", BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
//              map.put("AcctExp", "exp");
//              map.put("AcctCvv2", ChanPayUtil.encrypt("cvv", BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
//              map.put("CorpCheckNo", "201703061413");
//              map.put("Summary", "");
                
                origMap.put("CorpPushUrl", "http://172.20.11.16");      
                origMap.put("PostScript", "用途");
                result = this.gatewayPost(origMap, charset, PayConstants.CHAN_MERCHANT_PRIVATE_KEY);
            }catch (Exception e) {
                e.printStackTrace();
            }
            break;
        default:
            break;
        }
        logger.info("result = "+result);
        return result;
    }

    @Override
    public Response httSend2(Request request, String method) throws CommunicateException {
        return null;
    }

    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果
     * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
     * "",sParaTemp)
     *
     * @param strParaFileName
     *            文件类型的参数名
     * @param strFilePath
     *            文件路径
     * @param sParaTemp
     *            请求参数数组
     * @return 钱包处理结果
     * @throws Exception
     */
    public static String buildRequest(Map<String, String> sParaTemp, String signType, String key, String inputCharset,
            String gatewayUrl) throws Exception {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key, inputCharset);
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(inputCharset);
        request.setMethod(HttpRequest.METHOD_POST);
        request.setParameters(generatNameValuePair(createLinkRequestParas(sPara), inputCharset));
        request.setUrl(gatewayUrl);
        System.out.println(gatewayUrl+""+httpProtocolHandler.toString(generatNameValuePair(createLinkRequestParas(sPara), inputCharset)));
        if(sParaTemp.get("Service").equalsIgnoreCase("nmg_quick_onekeypay")||sParaTemp.get("Service").equalsIgnoreCase("nmg_nquick_onekeypay")){
            return null;
        }
        
        // 返回结果处理
        HttpResponse response = httpProtocolHandler.execute(request, null, null);
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        
        return strResult;
    }
    
    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果
     * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
     * "",sParaTemp)
     *
     * @param strParaFileName
     *            文件类型的参数名
     * @param strFilePath
     *            文件路径
     * @param sParaTemp
     *            请求参数数组
     * @return 钱包处理结果
     * @throws Exception
     */
    public static Object buildRequests(Map<String, String> sParaTemp,
            String signType, String key, String inputCharset, String gatewayUrl)
            throws Exception {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key,
                inputCharset);
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
                .getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(inputCharset);
        request.setMethod(HttpRequest.METHOD_POST);
        request.setParameters(generatNameValuePair(
                createLinkRequestParas(sPara), inputCharset));
        request.setUrl(gatewayUrl);
        HttpResponse response = httpProtocolHandler
                .execute(request, null, null);
        if (response == null) {
            return null;
        }

        byte[] byteResult = response.getByteResult();
        if (byteResult.length > 1024) {
            return byteResult;
        } else {
            return response.getStringResult();
        }

    }

    @SuppressWarnings("unused")
    private static Map convertMap(Map<?, ?> parameters, String InputCharset)
            throws ArrayIndexOutOfBoundsException, UnsupportedEncodingException, IllegalArgumentException {
        Map<String, String> formattedParameters = new HashMap<String, String>(parameters.size());
        for (Map.Entry<?, ?> entry : parameters.entrySet()) {
            if (entry.getValue() == null || Array.getLength(entry.getValue()) == 0) {
                formattedParameters.put((String) entry.getKey(), null);
            } else {
                String value = new String(((String) Array.get(entry.getValue(), 0)).getBytes(InputCharset), charset);

            }
        }
        return formattedParameters;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties
     *            MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties, String charset)
            throws Exception {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // nameValuePair[i++] = new NameValuePair(entry.getKey(),
            // URLEncoder.encode(entry.getValue(),charset));
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }

    /**
     * 生成要请求参数数组
     * 
     * @param sParaTemp
     *            请求前的参数数组
     * @param signType
     *            RSA
     * @param key
     *            商户自己生成的商户私钥
     * @param inputCharset
     *            UTF-8
     * @return 要请求的参数数组
     * @throws Exception
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String signType, String key,
            String inputCharset) throws Exception {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = "";
        if ("MD5".equalsIgnoreCase(signType)) {
            mysign = buildRequestByMD5(sPara, key, inputCharset);
        } else if ("RSA".equalsIgnoreCase(signType)) {
            mysign = buildRequestByRSA(sPara, key, inputCharset);
        }
        // 签名结果与签名方式加入请求提交参数组中
        System.out.println("Sign:" + mysign);
        sPara.put("Sign", mysign);
        sPara.put("SignType", signType);

        return sPara;
    }

    /**
     * 生成MD5签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara, String key, String inputCharset)
            throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }

    /**
     * 生成RSA签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(Map<String, String> sPara, String privateKey, String inputCharset)
            throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = RSA.sign(prestr, privateKey, inputCharset);
        return mysign;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode
     *            是否需要urlEncode
     * @return 拼接后字符串
     */
    public static Map<String, String> createLinkRequestParas(Map<String, String> params) {
        Map<String, String> encodeParamsValueMap = new HashMap<String, String>();
        List<String> keys = new ArrayList<String>(params.keySet());
        String charset = params.get("InputCharset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value;
            try {
                value = URLEncoder.encode(params.get(key), charset);
                encodeParamsValueMap.put(key, value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return encodeParamsValueMap;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode
     *            是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {

        params = paraFilter(params);

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get("InputCharset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("Sign") || key.equalsIgnoreCase("SignType") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 向测试服务器发送post请求
     * 
     * @param origMap
     *            参数map
     * @param charset
     *            编码字符集
     * @param MERCHANT_PRIVATE_KEY
     *            私钥
     * @throws Exception 
     */
    public String gatewayPost(Map<String, String> origMap, String charset, String MERCHANT_PRIVATE_KEY)  {
        String result="";
        try {
        String urlStr = PayConstants.CHAN_GATEWAY_URL+"?";// 测试环境地址，上生产后需要替换该地址
        Map<String, String> sPara = buildRequestPara(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset);
            result = buildRequest(origMap, "RSA", PayConstants.CHAN_MERCHANT_PRIVATE_KEY, charset,
                    urlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 向测试服务器发送post请求
     * 
     * @param origMap
     *            参数map
     * @param charset
     *            编码字符集
     * @param MERCHANT_PRIVATE_KEY
     *            私钥
     */
    public Object gatewayPosts(Map<String, String> origMap, String charset,
            String MERCHANT_PRIVATE_KEY) {
        try {
            String urlStr = PayConstants.CHAN_GATEWAY_URL+"?";
            // String urlStr =
            // "https://cpay.chanpay.com/mag-unify/gateway/receiveOrder.do?";
            Map<String, String> sPara = buildRequestPara(origMap, "RSA",
                    MERCHANT_PRIVATE_KEY, charset);
            System.out.println(urlStr + createLinkString(sPara, true));
            Object obj = buildRequests(origMap, "RSA", MERCHANT_PRIVATE_KEY,
                    charset, urlStr);
            System.out.println(obj);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 加密，部分接口，有参数需要加密
     * 
     * @param src
     *            原值
     * @param publicKey
     *            畅捷支付发送的平台公钥
     * @param charset
     *            UTF-8
     * @return RSA加密后的密文
     */
    private String encrypt(String src, String publicKey, String charset) {
        try {
            byte[] bytes = RSA.encryptByPublicKey(src.getBytes(charset), publicKey);
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步通知验签仅供参考
     */
    public void notifyVerify() {
    
        String sign = "kkqC/K3COMiQpugQj1Dtqw7568Y7UDsDTY9aihmurROXSpy+BaFyfz5o8PHyUM1opW3rMXCTIH21spjnsadH1Qsl5IrymJnG1M79nQ4cGgVsOsqoAe714RNHqXIDrGw49vpXuFbxFi7Ey4Yhd2gQDmuBYWaj0hecLWlWtd93slM=";
        
        Map<String, String> paramMap = new HashMap<String, String>();
        //新网银

        paramMap.put("notify_id", "5958170d60b14bfe9dd06a51af6b5532");
        paramMap.put("notify_type", "trade_status_sync"); //代收付与此不同
        paramMap.put("notify_time", "20170901142633");
        paramMap.put("_input_charset", "UTF-8");
        paramMap.put("version", "1.0");
        paramMap.put("outer_trade_no", "20170901001");
        paramMap.put("inner_trade_no", "101150424305443607560");
        paramMap.put("trade_status", "TRADE_SUCCESS");
        paramMap.put("trade_amount", "1.00");
        paramMap.put("gmt_create", "20170901142633");
        paramMap.put("gmt_payment", "20170901142633");
        paramMap.put("extension", "{}");

            
        //扫码、快捷
//      paramMap.put("notify_id", "87e8e322cc9b4dba8fc2f0e8010a1382");
//      paramMap.put("notify_type", "trade_status_sync"); //代收付与此不同
//      paramMap.put("notify_time", "20170807233141");
//      paramMap.put("_input_charset", "UTF-8");
//      paramMap.put("version", "1.0");
//      paramMap.put("outer_trade_no", "17080720052853968889");
//      paramMap.put("inner_trade_no", "101150210752881582798");
//      paramMap.put("trade_status", "TRADE_SUCCESS");
//      paramMap.put("trade_amount", "0.01");
//      paramMap.put("gmt_create", "20170807200534");
//      paramMap.put("gmt_payment", "20170807200534");
//      paramMap.put("extension", "{\"BANK_RET_DATA\":\"{'bank_type':'CFT','fee_type':'CNY','is_subscribe':'N','openid':'oMJGHs2wAz41X5GjYp4bPbcuB-EU','out_trade_no':'SG102946308070000040358','out_transaction_id':'4001502001201708075023175339','pay_result':'0','result_code':'0','status':'0','sub_appid':'wxfa2f613ed691411f','sub_is_subscribe':'Y','sub_openid':'os7Olwggpu_x6urLCdMh6uJseiUI','time_end':'20170807200533','total_fee':'1','transaction_id':'299540006994201708072263952157'}\"}");

        
        //代付
//      paramMap.put("uid", "");
//      paramMap.put("notify_time", "20170802175829");
//      paramMap.put("notify_id", "6c923350499747eeb0012c967e5325a6");
//      paramMap.put("notify_type", "withdrawal_status_sync"); 
//      paramMap.put("_input_charset", "UTF-8");
//      paramMap.put("version", "1.0");     
//      paramMap.put("outer_trade_no", "Y5372126986139229223");
//      paramMap.put("inner_trade_no", "102150166789473238683");
//      paramMap.put("withdrawal_status", "WITHDRAWAL_SUCCESS");
//      paramMap.put("withdrawal_amount", "0.01");
//      paramMap.put("gmt_withdrawal", "20170802175821");
//      paramMap.put("return_code", "S0001");
//      paramMap.put("fail_reason", "交易成功");    

        
        //代扣
//      paramMap.put("notify_time", "20170803000841");
//      paramMap.put("notify_id", "64ac64bed1444554a195b52ea105cefa");
//      paramMap.put("notify_type", "trade_status_sync"); 
//      paramMap.put("_input_charset", "UTF-8");
//      paramMap.put("version", "1.0"); 
//      paramMap.put("outer_trade_no", "15016560129750|359469");
//      paramMap.put("inner_trade_no", "101150165603996435603");
//
//      paramMap.put("trade_status", "TRADE_SUCCESS");
//      paramMap.put("trade_amount", "100.00");
//      paramMap.put("gmt_create", "20170802144046");
//      paramMap.put("gmt_payment", "20170802144046");
//      paramMap.put("extension", "{\"apiResultMsg\":\"交易成功\",\"apiResultcode\":\"S\",\"channelTransTime\":\"20170802\",\"instPayNo\":\"DI111353501080200743557\",\"paymentSeqNo\":\"20170802FI031263767\",\"unityResultCode\":\"S0001\",\"unityResultMessage\":\"交易成功\"}");
//      
        
        String text = createLinkString(paramMap, false);
        System.out.println("ori_text:" + text);
        try {
            System.out.println(RSA.verify(text, sign, PayConstants.CHAN_MERCHANT_PUBLIC_KEY,
                    charset));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 公共请求参数设置
     */
    public Map<String, String> setCommonMap(Map<String, String> origMap) {
        // 2.1 基本参数
        origMap.put("Version", "1.0");
        origMap.put("PartnerId", PayConstants.CHAN_QUICK_PARTNERID);//生产环境测试商户号
        origMap.put("InputCharset", charset);// 字符集
        origMap.put("TradeDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));// 商户请求时间
        origMap.put("TradeTime", new SimpleDateFormat("HHmmss").format(new Date()));// 商户请求时间
        origMap.put("Memo", null);
        return origMap;
    }
    
    /**
     * 公共请求参数设置
     */
    public Map<String, String> setCommonMapCjt(Map<String, String> origMap) {
        // 2.1 基本参数
        origMap.put("Version", "1.0");
        origMap.put("PartnerId", PayConstants.CHAN_CJT_PARTNERID);//生产环境测试商户号
        origMap.put("InputCharset", charset);// 字符集
        origMap.put("TradeDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));// 商户请求时间
        origMap.put("TradeTime", new SimpleDateFormat("HHmmss").format(new Date()));// 商户请求时间
        origMap.put("Memo", null);
        return origMap;
    }
}