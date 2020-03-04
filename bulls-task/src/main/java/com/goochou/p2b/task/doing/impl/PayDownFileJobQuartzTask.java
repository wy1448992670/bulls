package com.goochou.p2b.task.doing.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONArray;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.utils.alipay.AlipayConfig;
import com.goochou.p2b.utils.weixin.MyConfig;
import com.goochou.p2b.utils.weixin.WXPay;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import com.yeepay.g3.sdk.yop.client.YopRsaClient;

/**
 * 支付对账文件下载
 * @author ydp
 * @date 2019/12/02
 */
public class PayDownFileJobQuartzTask extends BaseTask {

	/**
     *
     */
    private static final long serialVersionUID = 5083194554746228718L;
    
    private static final Logger logger = Logger.getLogger(PayDownFileJobQuartzTask.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	logger.info("对账文件下载 start------");
    	//对账文件地址
    	String path = "/data"; 
    	//创建文件夹
    	Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
        String d = new SimpleDateFormat("yyyy-MM-dd").format(time);
        File dir = new File(path+File.separator+d);
        // 一、检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }
        String result = "";
        FileWriter writer = null;
    	try {
    	    logger.info("易宝对账日期："+d);
    	    YopRequest request = new YopRequest(PayConstants.YEEPAY_APP_KEY,PayConstants.YEEPAY_PRIVATE_KEY);
            //step2 配置参数
            //arg0:参数名
            //arg1:参数值
            //平台商商编
            request.addParam("parentMerchantNo",PayConstants.YEEPAY_PARENTMERCHANTNO);
            //收单子商户编号
            request.addParam("merchantNo", PayConstants.YEEPAY_MERCHANTNO);
            //本日交易次日生成对账文件，请勿用当日做参数
            request.addParam("dayString", d);
            //step3 发起请求
            //arg0:接口的uri（参见手册）
            //arg1:配置好参数的请求对象
            YopResponse response = YopRsaClient.post("/yos/v1.0/std/bill/tradedaydownload", request);
            logger.info(response.toString());
            InputStream retstream= response.getFile();
            result = new BufferedReader(new InputStreamReader(retstream)).lines().collect(Collectors.joining(System.lineSeparator()));
            logger.info(result);
            writer = new FileWriter(path+File.separator+d+File.separator+OutPayEnum.YEEPAY.getFeatureName()+".txt");
            writer.write(result);
            writer.flush();
    	}catch (Exception e) {
    	    logger.error("易宝对账文件下载异常！", e);
        } finally {
            if(null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                     e.printStackTrace();
                }
            }
        }
    	try {
            String wxDate = new SimpleDateFormat("yyyyMMdd").format(time);
            logger.info("微信对账日期："+wxDate);
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config, false, false);
            Map<String, String> reqData = new HashMap<String, String>();
            reqData.put("bill_date", wxDate);
            reqData.put("bill_type", "ALL");
            Map<String, String> map = wxpay.downloadBill(reqData);
            result = JSONArray.toJSONString(map);
            logger.info(result);
            writer = new FileWriter(path+File.separator+d+File.separator+OutPayEnum.WXPAY.getFeatureName()+".txt");
            writer.write(result);
            writer.flush();
        }catch (Exception e) {
            logger.error("微信对账文件下载异常！", e);
        } finally {
            if(null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                     e.printStackTrace();
                }
            }
        }
    	ByteArrayOutputStream bos = null;
    	InputStream inputStream = null;
    	FileOutputStream fos = null;
    	try {
            logger.info("支付宝对账日期："+d);
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签     
            CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
            certAlipayRequest.setServerUrl(AlipayConfig.URL);
            certAlipayRequest.setAppId(AlipayConfig.APPID);
            certAlipayRequest.setPrivateKey(AlipayConfig.RSA_PRIVATE_KEY);
            certAlipayRequest.setFormat(AlipayConfig.FORMAT);
            certAlipayRequest.setCharset(AlipayConfig.CHARSET);
            certAlipayRequest.setSignType(AlipayConfig.SIGNTYPE);
            certAlipayRequest.setCertPath(AlipayConfig.CERT_PATH);
            certAlipayRequest.setAlipayPublicCertPath(AlipayConfig.ALIPAY_PUBLIC_CERT_PATH);
            certAlipayRequest.setRootCertPath(AlipayConfig.ROOT_CERT_PATH);
            DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
            AlipayDataDataserviceBillDownloadurlQueryRequest alipay_request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
            AlipayDataDataserviceBillDownloadurlQueryModel model =new AlipayDataDataserviceBillDownloadurlQueryModel();
            model.setBillType("trade");
            model.setBillDate(d);
            alipay_request.setBizModel(model);
            AlipayDataDataserviceBillDownloadurlQueryResponse alipay_response = alipayClient.certificateExecute(alipay_request);
            logger.info(alipay_response.getBillDownloadUrl());
            URL url = new URL(alipay_response.getBillDownloadUrl()); 
            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
            //设置超时间为3秒
            conn.setConnectTimeout(3*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            inputStream = conn.getInputStream(); 
            //获取自己数组
            byte[] buffer = new byte[1024]; 
            int len = 0; 
            bos = new ByteArrayOutputStream(); 
            while((len = inputStream.read(buffer)) != -1) { 
                bos.write(buffer, 0, len); 
            } 
            
            byte[] getData = bos.toByteArray();
            File file = new File(path+File.separator+d+File.separator+OutPayEnum.ALPAY.getFeatureName()+".zip");   
            fos = new FileOutputStream(file);    
            fos.write(getData);
            ProcessBuilder pb = new ProcessBuilder(path+File.separator+"A.sh", d);
            pb.directory(new File(path+File.separator+d));
            int runningStatus = 0;
            String s = null;
            try {
                Process p = pb.start();
                try {
                    runningStatus = p.waitFor();
                } catch (InterruptedException e) {
                }
            } catch (IOException e) {
            }
            if (runningStatus != 0) {
                logger.info("shell 脚本执行完毕。");
            }
        }catch (Exception e) {
            logger.error("支付宝对账文件下载异常！", e);
        } finally {
            if(null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                     e.printStackTrace();
                } 
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                     e.printStackTrace();
                } 
            }
        }
        logger.info("对账文件下载 end------");
    }
    
    public static void main(String[] args) {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(time));
//        String path = "/data"; 
//        //创建文件夹
        String d = new SimpleDateFormat("yyyy-MM-dd").format(time);
//        File dir = new File(path+File.separator+d);
//        System.out.println(dir);
        String path = "d:/data";
        File dir = new File(path+File.separator+d);
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }
        try {
            FileWriter writer = new FileWriter(path+File.separator+d+File.separator+OutPayEnum.YEEPAY.getFeatureName()+".txt");
            writer.write("123455");
            writer.flush();
            writer.close();
        }catch (Exception e) {
            // TODO: handle exception
        }
    }
}
