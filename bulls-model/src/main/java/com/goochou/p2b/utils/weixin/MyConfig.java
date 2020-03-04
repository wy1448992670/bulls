 package com.goochou.p2b.utils.weixin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author ydp
 * @date 2019/07/13
 */
 public class MyConfig extends WXPayConfig {
     private byte[] certData;
     public MyConfig() throws Exception {
         String certPath = "/usr/local/cert/wxpay/apiclient_cert.p12";
         File file = new File(certPath);
         InputStream certStream = new FileInputStream(file);
         this.certData = new byte[(int) file.length()];
         certStream.read(this.certData);
         certStream.close();
     }
     public String getAppID() {
         return "wx83f2f5e8f411cea9";
     }
     public String getMchID() {
         return "1550624191";
     }
     public String getKey() {
         return "cecb57b90db9639bed90ffe0c240a923";
         //return "4c8dd282a38199a671956a5ff6bc07a5";
     }
     public InputStream getCertStream() {
         ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
         return certBis;
     }
     public int getHttpConnectTimeoutMs() {
         return 8000;
     }
     public int getHttpReadTimeoutMs() {
         return 10000;
     }
    @Override
    IWXPayDomain getWXPayDomain() {
     // 这个方法需要这样实现, 否则无法正常初始化WXPay
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
    
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
        
            }
    
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }
 }
