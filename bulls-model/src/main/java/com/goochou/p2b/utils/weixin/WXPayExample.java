 package com.goochou.p2b.utils.weixin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ydp
 * @date 2019/07/13
 */
 public class WXPayExample {

     public static void main(String[] args) throws Exception {

         MyConfig config = new MyConfig();
         WXPay wxpay = new WXPay(config, false, true);

         Map<String, String> data = new HashMap<String, String>();
         data.put("body", "腾讯充值中心-QQ会员充值");
         data.put("out_trade_no", "2016090910595900000012");
         data.put("device_info", "");
         data.put("fee_type", "CNY");
         data.put("total_fee", "1");
         data.put("spbill_create_ip", "123.12.12.123");
         data.put("notify_url", "http://www.example.com/wxpay/notify");
         data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
         data.put("product_id", "12");

         try {
             Map<String, String> resp = wxpay.unifiedOrder(data);
             System.out.println(resp);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

 }
