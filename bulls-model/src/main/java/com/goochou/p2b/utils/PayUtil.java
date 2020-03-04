package com.goochou.p2b.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.goochou.p2b.model.Project;

/**
 * 回潮支付
 *
 * @author irving
 *
 */
public class PayUtil {

    /**
     * 银行code列表
     *
     * @return
     */
    public static String[] getBanks() {
        return new String[] { "ICBC", "ABC", "BOCSH", "CCB", "CMB", "SPDB", "GDB", "BOCOM", "PSBC", "CNCB", "CMBC", "CEB", "HXB", "CIB", "BOS", "SRCB", "PAB", "BCCB" };
    }

    /**
     * @Title: getNewHttpClient
     * @Description: Methods Description
     * @param @return
     * @return HttpClient
     * @throws
     */

    private static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory sf = new SSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    params, registry);
            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    /**
     *
     * 生成订单号
     *
     * @param userId
     * @return
     */
    public static String getOrderNo(Integer userId) {
        long itmp1 = Math.round(Math.random() * 9);
        long itmp2 = Math.round(Math.random() * 9);
        Calendar currenttime = Calendar.getInstance();
        String orderinfo = "" + (currenttime.get(Calendar.YEAR) + "").substring(2) + ""
                + (currenttime.get(Calendar.MONTH) + 1) + ""
                + currenttime.get(Calendar.DAY_OF_MONTH) + ""
                + currenttime.get(Calendar.HOUR_OF_DAY) + ""
                + currenttime.get(Calendar.MINUTE) + ""
                + currenttime.get(Calendar.SECOND) +
                +currenttime.get(Calendar.MILLISECOND) + ""
                + String.valueOf(itmp1) + ""
                + String.valueOf(itmp2);
        return userId + orderinfo;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(PayUtil.getOrderNo(1));
        System.out.println();
    }
    
    public static BigDecimal getInterestAmount(Project project, boolean flag) {
        int scale = 2;
        if(flag) {
            scale = 0;
        }
        BigDecimal principal=BigDecimal.valueOf(project.getWeight()).multiply(BigDecimal.valueOf(project.getUnitZoomPrice())).add(BigDecimal.valueOf(project.getUnitFeedPrice()).multiply(BigDecimal.valueOf(project.getLimitDays()))).add(BigDecimal.valueOf(project.getUnitManagePrice()).multiply(BigDecimal.valueOf(project.getLimitDays())));
        BigDecimal interestAmount = principal.multiply(BigDecimal.valueOf(project.getAnnualized())).multiply(BigDecimal.valueOf(project.getLimitDays())).divide(BigDecimal.valueOf(360), scale, RoundingMode.DOWN);
        return interestAmount;
    }


    public static BigDecimal getPinniuInterestAmount(Project project, Integer totalPoint, boolean flag) {
        BigDecimal interestAmount = getInterestAmount(project, flag);
        int scale = 2;
        if(flag) {
            scale = 0;
        }
        return interestAmount.divide(BigDecimal.valueOf(totalPoint), scale, RoundingMode.DOWN);
    }
}
