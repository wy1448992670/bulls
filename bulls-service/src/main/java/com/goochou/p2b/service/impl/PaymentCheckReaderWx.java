package com.goochou.p2b.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.model.PaymentCheck;
import com.goochou.p2b.service.PaymentCheckReader;
import com.goochou.p2b.utils.DateUtil;

@Service
public class PaymentCheckReaderWx implements PaymentCheckReader {
	private static final Logger logger = Logger.getLogger(PaymentCheckReaderWx.class);
	private static final String SUCCESS = "SUCCESS";
	@Override
	public List<PaymentCheck> readFile(String filePath) throws Exception{
		logger.info("PaymentCheckReaderWx.readFile()");
		List<PaymentCheck> paymentCheckList=new ArrayList<PaymentCheck>();
 
		File file = new File(filePath);
		if (!file.isFile() || !file.exists()) {
	       throw new Exception("找不到微信对账单文件");
		}
		
		Long filelength = file.length(); // 获取文件长度
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContentArr = new String(filecontent);
		
        JSONObject json = JSONObject.parseObject(fileContentArr);
        String return_code = String.valueOf(json.get("return_code"));
        if(!SUCCESS.equals(return_code)) {
        	 throw new Exception("账单数据错误");
        }
        String payData = String.valueOf(json.get("data"));
        String[] payDataDetail = new String(payData).split("\r\n");
        PaymentCheck payment = null ;
        System.err.println(payDataDetail.length);
        //不读取第一行标题栏(i = 1),不读取后面统计2行(length-2)
        for (int i = 1; i < payDataDetail.length-2; i++) { 
        	String detail = payDataDetail[i];
        	String[] column = detail.split(",`",-1);
        	/**
        	 * 交易时间,公众账号ID,商户号,特约商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,应结订单金额,代金券金额,微信退款单号,商户退款单号,退款金额,充值券退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率,订单金额,申请退款金额,费率备注
        	` 0		1		2	 3		4	5		6		7	8	  9		10	 11	    12 		13		14	    15	   16 		17	  18	19	  20	21	  22  23  24     25     26	 	
        	 */
    		payment = new PaymentCheck();
    		Date dealDate = DateUtil.dateFullTimeFormat.parse(column[0].substring(1));//`2019-12-02 16:51:49 去除第一个字符
    		String outOrderNo = column[5];// 平台交易单号
    		String orderNo = column[6];// 第三方交易单号
    		String status = column[9];// 交易状态
    		String refundStatus = column[19];// 退款状态
    		String refundOrderNo = column[15];// 平台退款单号
    		String refundOutOrderNo = column[14];// 第三方退款单号
    		Double fee = Double.valueOf(column[22]) ;// 手续费
    		Double amount = Double.valueOf(column[24]);// 订单金额
    		String remark = column[26];
    		Double refundMoney = Double.valueOf(column[16]);
    		payment.setDealDate(dealDate);
    		payment.setPayChannel(OutPayEnum.WXPAY.getFeatureName());
    		if(status.equals(SUCCESS)) {
    			payment.setOutOrderNo(outOrderNo);
        		payment.setOrderNo(orderNo);
    			payment.setStatus(0);// 交易状态:0成功 1处理中 2失败 3未知
    			payment.setType(0);// 交易类型:0支付 1退款 2未知 3未知
    			payment.setAmount(amount);
    			payment.setFee(fee);// 交易取原值
    		} else if(status.equals("REFUND")) { // 交易状态退款为refund
    			if(refundStatus.equals(SUCCESS)) {
    				// 退款时orderNo记录退款单号,refundOrderNo记录原订单号
    				payment.setOrderNo(refundOrderNo);
        			payment.setOutOrderNo(refundOutOrderNo);
        			
    				payment.setRefundOrderNo(orderNo);
        			payment.setRefundOutOrderNo(outOrderNo);
        			payment.setStatus(0); 
        			payment.setType(1); 
        			payment.setAmount(refundMoney);
        			payment.setFee(-1*fee);// 退款手续费取反
    			} else {
    				payment.setStatus(3);
        			payment.setType(1);
    			}
    		} else {
    			payment.setStatus(3);
    			payment.setType(2);
    		}
    		
    		payment.setPayDate(dealDate);
    		payment.setRemark(remark);
    		logger.info(payment.toString());
    		paymentCheckList.add(payment);
		}
       
		return paymentCheckList;
	}
	
	
	public static void main(String[] args) {
		String str ="2019-12-02 16:06:16,`wx83f2f5e8f411cea9,`1550624191,`0,`,`4200000470201912024954785762,`P19120216060107718,`oe1_31HOBHoHomsv08BsLiOUl_4g,`MWEB,`SUCCESS,`CMBC_DEBIT,`CNY,`29.90,`0.00,`0,`0,`0.00,`0.00,`,`,`商城牛肉品种,`,`0.18000,`0.60%,`29.90,`0.00,`";
		String[] column = str.split(",`",-1);
		
		System.err.println(column.length);
	}
}
