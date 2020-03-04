package com.goochou.p2b.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.model.PaymentCheck;
import com.goochou.p2b.service.PaymentCheckReader;
import com.goochou.p2b.utils.DateUtil;

@Service
public class PaymentCheckReaderYeepay implements PaymentCheckReader {
	private static final Logger logger = Logger.getLogger(PaymentCheckReaderYeepay.class);
	
	@Override
	public List<PaymentCheck> readFile(String filePath) throws Exception{
		logger.info("PaymentCheckReaderYeepay.readFile()");
		List<PaymentCheck> paymentCheckList=new ArrayList<PaymentCheck>();
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            int i = 1;
            while ((tempStr = reader.readLine()) != null) {
                if (i > 5) {
                    paymentCheckList.add(this.fileContentConvert(tempStr));
                }
                i++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
		
		return paymentCheckList;
	}
	
	private PaymentCheck fileContentConvert(String data) throws Exception {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        //logger.info("易宝支付数据：" + data);
        PaymentCheck paymentCheck = new PaymentCheck();
        try {
            String[] datas = data.split(",");
            //去除数据两端的引号
            for(int i=0;i<datas.length;i++) {
                datas[i] = datas[i].replace("\"", "");
            }
            
            paymentCheck.setPayChannel(OutPayEnum.YEEPAY.getFeatureName());
            paymentCheck.setStatus(0); //交易状态:0成功 1处理中 2失败
            paymentCheck.setPayDate(DateUtil.dateFullTimeFormat.parse(StringUtils.trim(datas[0]))); //支付时间
            paymentCheck.setDealDate(DateUtil.dateFullTimeFormat.parse(StringUtils.trim(datas[1]))); //记账时间
            paymentCheck.setOrderNo(StringUtils.trim(datas[2])); //订单号
            if(StringUtils.trim(datas[3]).equals("交易")) { //交易类型
                paymentCheck.setType(0);
            } else {
                paymentCheck.setType(2);
            }
            paymentCheck.setAmount(Math.abs(Double.valueOf(StringUtils.trim(datas[4])))); //订单金额
            paymentCheck.setFee(Math.abs(Double.valueOf(StringUtils.trim(datas[5])))); //手续费
            paymentCheck.setRemark(StringUtils.trim(datas[8])); //备注
            paymentCheck.setOutOrderNo(StringUtils.trim(datas[9])); //流水号
            logger.info(paymentCheck.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw new Exception("数据解析出错");
        }
        
        return paymentCheck;
    }
	
}
