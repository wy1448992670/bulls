package com.goochou.p2b.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.model.PaymentCheck;
import com.goochou.p2b.service.PaymentCheckReader;

@Service
public class PaymentCheckReaderAli implements PaymentCheckReader {
	private static final Logger logger = Logger.getLogger(PaymentCheckReaderAli.class);
	
	@Override
	public List<PaymentCheck> readFile(String filePath) throws Exception{
		logger.info("PaymentCheckReaderAli.readFile()");
		List<PaymentCheck> paymentCheckList = new ArrayList<PaymentCheck>();
		BufferedReader reader = null;
		
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
			reader = new BufferedReader(isr);
			String tempStr;
			int i = 1;
			while ((tempStr = reader.readLine()) != null) {
				if (i > 5) {
					if (tempStr.indexOf("#---") > -1) {
						break;
					}
					paymentCheckList.add(this.fileContentConvert(tempStr));
				}
				i++;
			}
			reader.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			throw new Exception("文件不存在！");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error(e1.getMessage(), e1);
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
		
		logger.info("解析字符串：" + data);
		PaymentCheck paymentCheck = new PaymentCheck();
		paymentCheck.setPayChannel(OutPayEnum.ALPAY.getFeatureName());
		paymentCheck.setStatus(0);
		try {
			String[] datas = data.split(",");
			String outOrderNo = StringUtils.trim(datas[0]); // 支付宝交易号
			String orderNo = StringUtils.trim(datas[1]); // 商户订单号
			String type = StringUtils.trim(datas[2]); // 业务类型 (交易, 退款)
//			String shopName = StringUtils.trim(datas[3]); // 商品名称
			Date createTime = DateUtil.dateFullTimeFormat.parse(StringUtils.trim(datas[4])); // 创建时间
			Date finishTime = DateUtil.dateFullTimeFormat.parse(StringUtils.trim(datas[5])); // 完成时间
			double orderAmount = Math.abs(Double.valueOf(StringUtils.trim(datas[11]))); // 订单金额
//			double merchantReceipt = Math.abs(Double.valueOf(StringUtils.trim(datas[12]))); // 商家实收
//			String refundBatchNo = StringUtils.trim(datas[21]); // 退款批次号/请求号
			double serviceFee = Math.abs(Double.valueOf(StringUtils.trim(datas[22]))); // 服务费
			String remark = StringUtils.trim(datas[24]); // 备注
			
			paymentCheck.setOutOrderNo(outOrderNo);
			paymentCheck.setOrderNo(orderNo);
			if ("交易".equals(type)) {
				paymentCheck.setType(0);
			} else if ("退款".equals(type)) {
				paymentCheck.setType(1);
				paymentCheck.setRefundOutOrderNo(outOrderNo);
				paymentCheck.setRefundOrderNo(orderNo);
			} else { // 未知
				paymentCheck.setType(2);
			}
			paymentCheck.setPayDate(createTime);
			paymentCheck.setDealDate(finishTime);
			paymentCheck.setAmount(orderAmount);
			paymentCheck.setFee(serviceFee);
			paymentCheck.setRemark(remark);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			throw new Exception("数据解析出错");
		}
		return paymentCheck;
	}
	
}
