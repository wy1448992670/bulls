package com.goochou.p2b.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.TestEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.dao.PaymentCheckMapper;
import com.goochou.p2b.dao.PaymentCheckViewMapper;
import com.goochou.p2b.model.PaymentCheck;
import com.goochou.p2b.model.PaymentCheckExample;
import com.goochou.p2b.model.PaymentCheckView;
import com.goochou.p2b.model.PaymentCheckViewExample;
import com.goochou.p2b.model.PaymentCheckViewExample.Criteria;
import com.goochou.p2b.service.PaymentCheckReader;
import com.goochou.p2b.service.PaymentCheckService;
import com.goochou.p2b.utils.DateUtil;

@Service
public class PaymentCheckServiceImpl implements PaymentCheckService {
	private static final Logger logger = Logger.getLogger(PaymentCheckServiceImpl.class);
	
	@Resource
	private PaymentCheckMapper paymentCheckMapper;
	@Resource
	private PaymentCheckReader paymentCheckReaderAli;
	@Resource
	private PaymentCheckReader paymentCheckReaderWx;
	@Resource
	private PaymentCheckReader paymentCheckReaderYeepay;
	@Resource
	private PaymentCheckViewMapper paymentCheckViewMapper;

	private final String localPath = "D:/data";
	private final String serverPath = "/data";

	@Override
	public void doLoadFileByDate(Date date) throws Exception {
		Date now=new Date();
		String filePath = null;
		if (TestEnum.RELEASE.getFeatureName().equals(com.goochou.p2b.constant.Constants.TEST_SWITCH)) {
			filePath = serverPath;
		} else {
			filePath = localPath;
		}
		filePath=filePath + "/" + DateUtil.format(date, "yyyy-MM-dd")+ "/";
		
		List<PaymentCheck> paymentCheckList=new ArrayList<PaymentCheck>();
		try {
			paymentCheckList.addAll(paymentCheckReaderAli.readFile(filePath+"alpay.txt"));
		} catch (Exception e) {
			logger.error(e,e);
			throw e;
		}
		try {
			paymentCheckList.addAll(paymentCheckReaderWx.readFile(filePath+"wxpay.txt"));
		} catch (Exception e) {
			logger.error(e,e);
			throw e;
		}
		try {
			paymentCheckList.addAll(paymentCheckReaderYeepay.readFile(filePath+"yeepay.txt"));
		} catch (Exception e) {
			logger.error(e,e);
			throw e;
		}
		for(PaymentCheck paymentCheck:paymentCheckList) {
			PaymentCheckExample example=new PaymentCheckExample();
			try {
				example.createCriteria().andOrderNoEqualTo(paymentCheck.getOrderNo()).andTypeEqualTo(paymentCheck.getType());
				if(paymentCheckMapper.countByExample(example)==0L) {
					paymentCheck.setCreateDate(now);
					paymentCheckMapper.insert(paymentCheck);
				}
			} catch (Exception e) {
				logger.error(e,e);
				throw e;
			}
			
		}
	}

    @Override
    public List<PaymentCheckView> listPaymentCheckView(String orderNo, Date startDate, Date endDate, String channel,
        Integer warningType, Integer limitStart, Integer limitEnd) {
        PaymentCheckViewExample example = new PaymentCheckViewExample();
        example.setOrderByClause(" create_date desc ");
        example.setLimitStart(limitStart);
        example.setLimitEnd(limitEnd);
        Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(orderNo)) {
            criteria.andOrderNoLike("%"+orderNo+"%");
        }
        if(startDate != null) {
            criteria.andUpdateDateGreaterThanOrEqualTo(startDate);
        }
        if(endDate != null) {
            criteria.andUpdateDateLessThan(endDate);
        }
        if(StringUtils.isNotBlank(channel)) {
            criteria.andPayChannelEqualTo(channel);
        }
        if(warningType != null) { //数据是否有误 1有误 0无误
            if(warningType == 1) {
                criteria.andWarningTagEqualTo((long)1);
            } else if (warningType == 0) {
                criteria.andWarningTagEqualTo((long)0);
            }
        }
        
        List<PaymentCheckView> list = paymentCheckViewMapper.selectByExample(example);
        for(PaymentCheckView pcv : list) {
            if(pcv.getRechargePayChannel() != null) {
                pcv.setRechargePayChannel(OutPayEnum.getValueByName(pcv.getRechargePayChannel()).getDescription());
            }
            if(pcv.getCheckPayChannel() != null) {
                pcv.setCheckPayChannel(OutPayEnum.getValueByName(pcv.getCheckPayChannel()).getDescription());
            }
            if(pcv.getPayChannel() != null) {
                pcv.setPayChannel(OutPayEnum.getValueByName(pcv.getPayChannel()).getDescription());
            }
        }
        
        return list;
    }

    @Override
    public Integer countPaymentCheckView(String orderNo, Date startDate, Date endDate, String channel,
        Integer warningType) {
        PaymentCheckViewExample example = new PaymentCheckViewExample();
        Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(orderNo)) {
            criteria.andOrderNoLike("%"+orderNo+"%");
        }
        if(startDate != null) {
            criteria.andUpdateDateGreaterThanOrEqualTo(startDate);
        }
        if(endDate != null) {
            criteria.andUpdateDateLessThan(endDate);
        }
        if(StringUtils.isNotBlank(channel)) {
            criteria.andPayChannelEqualTo(channel);
        }
        if(warningType != null) { //数据是否有误 1有误 0无误
            if(warningType == 1) {
                criteria.andWarningTagEqualTo((long)1);
            } else if (warningType == 0) {
                criteria.andWarningTagEqualTo((long)0);
            }
        }
        
        return (int)paymentCheckViewMapper.countByExample(example);
    }

	@Override
	public PaymentCheckMapper getPaymentCheckMapper() {
		return paymentCheckMapper;
	}
 
}
