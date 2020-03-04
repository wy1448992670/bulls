package com.goochou.p2b.module.withdraw;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.WithdrawTempStatusEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.openapi.withdraw.WithdrawRequest;
import com.goochou.p2b.model.WithdrawTemp;
import com.goochou.p2b.model.WithdrawTempExample;
import com.goochou.p2b.model.WithdrawTempExample.Criteria;
import com.goochou.p2b.service.WithdrawTempService;
import com.goochou.p2b.utils.DateUtil;

/**
 * 提现业务发送报文
 * @author WuYJ
 *
 */
@Service
public class WithdrawSend implements HessianInterface {

	private final static Logger logger = Logger.getLogger(WithdrawSend.class);
	
	@Resource
    private AssetsMapper assetsMapper;
	
	
	@Resource
	private  WithdrawTempService withdrawTempService;
	
	@Autowired
	private FuiouWithdraw fuiouWithdraw;
	
	@Autowired
    private AllinPayWithdraw allinPayWithdraw;
	
	@Override
	public Response execute(ServiceMessage msg) {
		Response result = new Response();
		Date sendDate = new Date();
		//1、查询提现待发报文记录,根据状态，和发送时间查询，指定条数
//		WithdrawTempExample example = new WithdrawTempExample();
//		Criteria criteria =  example.createCriteria();
//		criteria.andPredictSendDateLessThanOrEqualTo(sendDate);
//		criteria.andStatusEqualTo(WithdrawTempStatusEnum.WEIWANCHENG.getFeatureName());
//		//T+1周六周日不处理
		Integer type = null;
//		int weekInt = DateUtil.getDateWeek(sendDate);
//		if(weekInt == 6 || weekInt == 7){
//			type = 0;
//		}
//		example.setLimitStart(0);
//		example.setLimitEnd(50);
		List<WithdrawTemp> tempList = withdrawTempService.queryWithdraw(type, sendDate);
		int success = 0;
		if (null != tempList && !tempList.isEmpty()) {
			for (WithdrawTemp withdrawTemp : tempList) {
				try {
				    Response response = null;
				    WithdrawRequest request = new WithdrawRequest();
                    request.setOrderNo(withdrawTemp.getOrderNo());
                    request.setAmount(withdrawTemp.getAmount());
                    request.setBankNo(withdrawTemp.getBankCode().trim());
                    request.setCardNo(withdrawTemp.getCardNo());
                    request.setTrueName(withdrawTemp.getTrueName());
                    request.setDate(DateUtil.yyyyMMdd.format(sendDate));
                    logger.info("payChannel="+withdrawTemp.getPayChannel());
				    if(withdrawTemp.getPayChannel().equals(OutPayEnum.FUIOU_QUICK.getFeatureName())) {
				        //2、调用富友提现接口
	                    ServiceMessage withdrawMsg = new ServiceMessage("fuiou.withdraw",request);
	                    //3、调用富友提现报文发送接口
	                    response = fuiouWithdraw.execute(withdrawMsg);
				    }else if(withdrawTemp.getPayChannel().equals(OutPayEnum.ALLINPAY.getFeatureName())) {
				        ServiceMessage withdrawMsg = new ServiceMessage("allinpay.withdraw",request);
				        response = allinPayWithdraw.execute(withdrawMsg);
				    }
					//4、修改报文发送记录状态
					if (null != response) {
						//修改状态
						withdrawTemp.setUpdateDate(sendDate);
						withdrawTemp.setStatus(WithdrawTempStatusEnum.YIWANCHENG.getFeatureName());
						if (response.isSuccess() || "1".equals(response.getErrorCode())) {
							//修改状态
							withdrawTemp.setUpdateDate(sendDate);
							withdrawTemp.setStatus(WithdrawTempStatusEnum.YIWANCHENG.getFeatureName());
							withdrawTemp.setReqSn(response.getMsg());
							int count = withdrawTempService.update(withdrawTemp);
							logger.info("widthTemp 执行数量 count="+count);
						}else {
							logger.error("提现发起业务失败，" + response.getErrorMsg());
						}
					}
					success ++;
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("提现发起业务异常，");
				}
			}
			logger.info("查询提现待发报文记录业务：处理数："+ tempList.size() + "，成功数：" + success);
		}
		result.setSuccess(true);
		return result;
	}

	@Override
	public void before(ServiceMessage msg) {
	}

	@Override
	public void after(ServiceMessage msg) {
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getDateWeek(new Date()));
	}
}
