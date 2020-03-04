package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.OrderDoneEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.TradeMessageStatusEnum;
import com.goochou.p2b.constant.TradeMessageTypeEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.dao.AdminLogMapper;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.OrderDoneMapper;
import com.goochou.p2b.dao.RechargeMapper;
import com.goochou.p2b.dao.TradeMessageLogMapper;
import com.goochou.p2b.dao.TradeRecordMapper;
import com.goochou.p2b.dao.UserAdminMapper;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.pay.AllinPayRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayResponse;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouQuickPayRequest;
import com.goochou.p2b.hessian.openapi.pay.QueryFuiouQuickPayResponse;
import com.goochou.p2b.hessian.openapi.pay.QueryYeePayResponse;
import com.goochou.p2b.hessian.openapi.pay.YeePayRequest;
import com.goochou.p2b.model.AdminLog;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.OrderDone;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.RechargeExample;
import com.goochou.p2b.model.RechargeExample.Criteria;
import com.goochou.p2b.model.TradeMessageLog;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.vo.TradeMessageLogVO;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.OrderDoneService;
import com.goochou.p2b.service.PastureOrderService;
import com.goochou.p2b.service.RechargeService;
import com.goochou.p2b.service.TradeMessageLogService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UserAccountService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.StringUtils;
import com.goochou.p2b.utils.alipay.AlipayConfig;
import com.goochou.p2b.utils.weixin.MyConfig;
import com.goochou.p2b.utils.weixin.WXPay;

@Service
public class RechargeServiceImpl implements RechargeService {
	private static final Logger logger = Logger.getLogger(RechargeServiceImpl.class);
    @Resource
    private RechargeMapper rechargeMapper;
    @Resource
    private TradeRecordMapper tradeRecordMapper;
    @Resource
    private AssetsMapper assetsMapper;
    @Autowired
    private AssetsService assetsService;
    @Resource
    private UserAdminMapper userAdminMapper;
    @Resource
    private AdminLogMapper adminLogMapper;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private GoodsOrderService goodsOrderService;
    @Resource
    private TradeMessageLogMapper tradeMessageLogMapper;
    @Resource
    private OrderDoneMapper orderDoneMapper;
    @Resource
    private PastureOrderService pastureOrderService;
    @Resource
    private TradeMessageLogService tradeMessageLogService;
    @Resource
    private TradeRecordService tradeRecordService;
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private OrderDoneService orderDoneService;
    
    @Override
    public Map<String, Object> detail(Integer id) {
        return rechargeMapper.detail(id);
    }

    @Override
    public List<Map<String, Object>> query(String keyword, Integer status, String payChannel, Date startTime, Date endTime, Integer start, Integer limit, String client,Integer adminId, String code,Integer departmentId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", DateUtil.getMinInDay(startTime));
        map.put("endTime", DateUtil.getMaxInDay(endTime));
        map.put("payChannel", StringUtils.isBlank(payChannel) ? null : payChannel);
        map.put("keyword", keyword);
//        map.put("adminId", adminId);
        map.put("start", start);
        map.put("limit", limit);
        map.put("client", client);
        map.put("status", status);
        map.put("code", code);
        map.put("adminId", adminId);
        map.put("departmentId", departmentId);
        List<Map<String, Object>> list = rechargeMapper.query(map);
        return list;
    }

    @Override
    public Map<String, Object> queryCount(String keyword, Integer status, String payChannel, Date startTime, Date endTime, String client,Integer adminId, String code,Integer departmentId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", DateUtil.getMinInDay(startTime));
        map.put("endTime", DateUtil.getMaxInDay(endTime));
        map.put("payChannel", payChannel);
        map.put("adminId", adminId);
        map.put("client", client);
        map.put("keyword", keyword);
        map.put("status", status);
        map.put("code", code);
        map.put("adminId", adminId);
        map.put("departmentId", departmentId);
        return rechargeMapper.queryCount(map);
    }


    @Override
    public Integer querySum(String keyword, Integer status, String payChannel, Date startTime, Date endTime, String client,Integer adminId, String code,Integer departmentId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", DateUtil.getMinInDay(startTime));
        map.put("endTime", DateUtil.getMaxInDay(endTime));
        map.put("payChannel", payChannel);
        map.put("adminId", adminId);
        map.put("client", client);
        map.put("keyword", keyword);
        map.put("status", status);
        map.put("code", code);
        map.put("departmentId", departmentId);
        return rechargeMapper.querySum(map);
    }

    @Override
    public List<Recharge> findByUserId(Integer userId, int start, int limit) {
        RechargeExample example = new RechargeExample();
        Criteria c = example.createCriteria();
        if (userId > 0) {
            c.andUserIdEqualTo(userId);
        }
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        return rechargeMapper.selectByExample(example);
    }

    @Override
    public void save(Recharge recharge) throws Exception {
        boolean flag = true;
        try {
            if(rechargeMapper.insertSelective(recharge)==0) {
                flag = false;
            }
            if(recharge.getOrderType()!=null) {
                //变更支付中，防止取消并发
                if(recharge.getOrderType().equals(OrderTypeEnum.GOODS.getFeatureName())) {
                    GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKey(recharge.getOtherId());
                    goodsOrder.setState(1);
                    if(goodsOrderService.update(goodsOrder, 0) == 0) {
                        flag = false;
                    }
                }else if(recharge.getOrderType().equals(OrderTypeEnum.INVESTMENT.getFeatureName())) {
                    Investment investment = investmentService.getMapper().selectByPrimaryKey(recharge.getOtherId());
                    investment.setPayStatus(1);
                    if(investmentService.update(investment,0, 0) == 0) {
                        flag = false;
                    }
                }
            }

            if (!flag) {
                throw new Exception("发起支付异常，事务回滚");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public Recharge getByOrder(String orderNo) {
        RechargeExample example = new RechargeExample();
        Criteria c = example.createCriteria();
        c.andOrderNoEqualTo(orderNo);
        List<Recharge> list = rechargeMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Map<Integer, String>> getAIRechargePie() {
        return rechargeMapper.getAIRechargePie();
    }

    @Override
    public List<Map<String, Object>> getExportByStatus(Integer status) {
        return rechargeMapper.getExportByStatus(status);
    }

    @Override
    public Date getFirstHuoRechargeTime(Integer userId) {
        RechargeExample example = new RechargeExample();
        List<String> client = new ArrayList<>(); // TODO sq 枚举
        client.add(String.valueOf(1));
        client.add(String.valueOf(2));
        example.createCriteria().andClientIn(client).andUserIdEqualTo(userId).andStatusEqualTo(0);
        example.setOrderByClause("time asc");
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<Recharge> list = rechargeMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            return list.get(0).getUpdateDate();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getRechargeAmountByMonthDay(Integer adminId, Integer departmentId) {

        return rechargeMapper.getRechargeAmountByMonthDay(adminId, departmentId);
    }

    @Override
    public Boolean checkOrderNo(Integer id, String orderNo) {
        return null == rechargeMapper.checkOrderNo(id, orderNo) ? false : true;
    }

    @Override
    public void update(Recharge recharge) {
        rechargeMapper.updateByPrimaryKeySelective(recharge);
    }
    @Override
    public void update(Recharge recharge,int status) {
    	RechargeExample example = new RechargeExample();
    	RechargeExample.Criteria c = example.createCriteria();
        c.andStatusEqualTo(status).andIdEqualTo(recharge.getId());
        rechargeMapper.updateByExampleSelective(recharge,example);
    }

    // 线下补单
    @Override
    public void updateFixed(Recharge recharge, UserAdmin admin) throws Exception {
//        Recharge recharge = rechargeMapper.selectByPrimaryKey(id);
        if (recharge.getStatus().equals(0)) {
            return;
        }
        if(!recharge.getPayChannel().equals(OutPayEnum.OFFLINE.getFeatureName())) {
        	throw new Exception("非线下充值单");
        }
        recharge.setStatus(0);
        Assets assets = assetsMapper.selectByPrimaryKey(recharge.getUserId()); // 获取他的资产，取得可用余额
        
        this.updateRecord(recharge, assets, null);

        AdminLog adminLog = new AdminLog();
        adminLog.setAdminId(admin.getId());
        adminLog.setAdminUserName(admin.getTrueName());
        adminLog.setOperateTime(new Date());
        adminLog.setRemark("充值补单");
        adminLogMapper.insertSelective(adminLog);
    }

    @Override
    public Boolean checkIfFirstSinaPay(Integer userId) {
        RechargeExample example = new RechargeExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(0).andPayChannelEqualTo(String.valueOf(3)); // TODO 枚举
        Integer count = rechargeMapper.countByExample(example);
        if (count > 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> selectRechargeWithdrawDay(String month) {
        return rechargeMapper.selectRechargeWithdrawDay(month);
    }

    @Override
    public List<Map<String, Object>> selectRechargeWithdrawMonth(String year) {
        return rechargeMapper.selectRechargeWithdrawMonth(year);
    }

    @Override
    public List<Map<Integer, String>> selectRechargeWithdrawYear(String year) {
        return rechargeMapper.selectRechargeWithdrawYear(year);
    }

    public List<Map<String, Object>> selectUsersAssetsDetailsDay(String days) {
        return rechargeMapper.selectUsersAssetsDetailsDay(days);
    }

    @Override
    public Recharge get(Integer id) {
        return rechargeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Recharge> queryAllChargeInfoToday() {
        RechargeExample example = new RechargeExample();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        //查询成功和失败的充值记录
  		Integer []status = new Integer[]{0, 2};
  		List<Integer> statusList = Arrays.asList(status);

        example.createCriteria().andStatusIn(statusList).andUpdateDateGreaterThanOrEqualTo(c.getTime());
        return rechargeMapper.selectByExample(example);
    }

	@Override
	public List<Recharge> queryChargeAmountByDate(Date date, Date endDate) {

		RechargeExample example = new RechargeExample();
		example.createCriteria().andStatusEqualTo(0).andUpdateDateGreaterThanOrEqualTo(date).andUpdateDateLessThanOrEqualTo(endDate);
		return rechargeMapper.selectByExample(example);
	}

    public double getRechargeAmountByExpress(Integer userId, String dateExpress,String cardNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("dateExpress", dateExpress);
        map.put("cardNo", cardNo);
        return rechargeMapper.getRechargeAmountByExpress(map);
    }

    @Override
    public List<Map<String, Object>> queryAllChargeInfo(Integer userId, Date startTime, Date endTime, int start, int limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("start", start);
        map.put("limit", limit);
        return rechargeMapper.queryAllChargeInfo(map);
    }

    public Integer queryAllChargeInfoCount(Integer userId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return rechargeMapper.queryAllChargeInfoCount(map);
    }

    @Override
    public boolean updateRecord(Recharge recharge, Assets assets, TradeMessageLog tradeMessageLog) throws Exception {
    	boolean flag = true;
    	try {
    		logger.info("otherId="+recharge.getOtherId());
    		//CAS更新,因为支付单只有一个方法可以更新,所以没有并发问题,如果以后增加支付单得更新方法,需要加锁
            if(this.updateCAS(recharge,1) != 1) {
            	flag = false;
            	logger.error("修改充值单信息失败，"+ recharge.toString());
            }
            // 只有充值成功的状态才会插入交易记录表里
            String editStatus = TradeMessageStatusEnum.SHIBAI.getFeatureName();
            if (recharge.getStatus() == 0) {
            	editStatus = TradeMessageStatusEnum.YIWANCHENG.getFeatureName();
                //更新订单状态（investment, goods）
                if(recharge.getOrderType().equals(OrderTypeEnum.INVESTMENT.getFeatureName())) {
                	Investment investment = investmentService.getMapper().selectByPrimaryKey(recharge.getOtherId());
                	logger.info(recharge.getOrderType()+" orderNo="+investment.getOrderNo());
                	if(!pastureOrderService.doPaySuccess(investment, assets)) {
                		flag = false;
                	}
                }else if(recharge.getOrderType().equals(OrderTypeEnum.GOODS.getFeatureName())) {
                	GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKeyForUpdate(recharge.getOtherId());
                	logger.info(recharge.getOrderType()+" orderNo="+goodsOrder.getOrderNo());
                	goodsOrderService.doPaySuccess(goodsOrder, assets);
                }else if(recharge.getOrderType().equals(OrderTypeEnum.RECHARGE.getFeatureName())) {
                	logger.info(recharge.getOrderType()+" orderNo="+recharge.getOrderNo());
                	if(!this.doPaySuccess(recharge, assets)) {
                		flag = false;
                	}
                } else{
                	flag = false;
                	logger.error("更新订单失败，无效订单类型");
                }
                //报文请求记录
                if(!recharge.getPayChannel().equals(OutPayEnum.OFFLINE.getFeatureName())) {
                	if (tradeMessageLog != null) {
                    	if (tradeMessageLogMapper.insertTradeMessageLog(tradeMessageLog) != 1) {
                    		flag = false;
        	            	logger.error("添加交易回调信息状态失败，" + tradeMessageLog.toString());
        				}
        			}else {
        				// 处理日志状态
        	            TradeMessageLogVO tradeMessageLogVO = new TradeMessageLogVO();
        	            tradeMessageLogVO.setInOrderId(recharge.getOrderNo());
        	            tradeMessageLogVO.setMessageType(TradeMessageTypeEnum.BACK.getFeatureName());
        	            tradeMessageLogVO.setEditStatus(editStatus);
        	            if (tradeMessageLogMapper.updateTradeMessageLogStatus(tradeMessageLogVO) != 1) {
        	            	flag = false;
        	            	logger.error("修改交易回调信息状态失败，" + tradeMessageLogVO.toString());
        	            }
        			}
                }
            }else {
                //变更支付中，防止取消并发
                if(recharge.getOrderType().equals(OrderTypeEnum.GOODS.getFeatureName())) {
                    GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKey(recharge.getOtherId());
                    goodsOrder.setState(0);
                    if(goodsOrderService.update(goodsOrder, 1) == 0) {
                    	logger.error("修改交易回调信息状态失败，" + goodsOrder.getId());
                        flag = false;
                    }
                }else if(recharge.getOrderType().equals(OrderTypeEnum.INVESTMENT.getFeatureName())) {
                    Investment investment = investmentService.getMapper().selectByPrimaryKey(recharge.getOtherId());
                    investment.setPayStatus(0);
                    if(investmentService.update(investment, 0,1) == 0) {
                    	logger.error("修改交易回调信息状态失败，" + investment.getId());
                        flag = false;
                    }
                }else if(recharge.getOrderType().equals(OrderTypeEnum.RECHARGE.getFeatureName())) {
                    //donothing
                }
            }

            if (!flag) {
            	throw new Exception("更新充值交易信息异常，事务回滚");
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
    	return flag;
    }

    @Override
	public List<Recharge> selectRechargeException(String payChannel, Integer status, Integer limit, Date startDate) {
		Map<String, Object> param = new HashMap<>();
		param.put("payChannel", payChannel);
		param.put("status", status);
		param.put("limit", limit);
		return rechargeMapper.selectRechargeException(param);
	}

    @Override
	public int rechargeCancel(Date startDate) {
		return rechargeMapper.rechargeCancel(startDate);
	}

	/**
	 * 通过订单类型和订单号查询支付记录 2019-05-15 张琼麒
	 *
	 * @param orderType
	 * @param orderNo
	 * @return
	 */
	@Override
	public List<Recharge> getByOrderTypeAndNo(String orderType, String orderNo) {
		RechargeExample example = new RechargeExample();

		Criteria c = example.createCriteria();
		c.andOrderTypeEqualTo(orderType);
		c.andOrderNoEqualTo(orderNo);
		return rechargeMapper.selectByExample(example);
	}

	/**
	 * 通过订单类型和订单号查询订单的支付状态
	 * 充值状态:0成功，1处理中，2失败 2019-05-15 张琼麒
	 *
	 * @param orderType
	 * @param orderNo
	 * @return
	 */
	@Override
	public int getRechargeStatusByOrderTypeAndNo(String orderType, String orderNo) {
		List<Recharge> rechargeList = this.getByOrderTypeAndNo(orderType, orderNo);
		for (Recharge recharge : rechargeList) {
			if (recharge.getStatus() == 0) {
				return recharge.getStatus();
			}else if(recharge.getStatus() == 1) {
				return this.getRechargeStatusConnectorAPI(recharge);
			}
		}
		return 2;
	}

    /**
     * 调用接口,查询充值是否成功
     * 充值状态:0成功，1处理中，2失败 2019-05-15 张琼麒
     *
     * @param orderType
     * @param orderNo
     * @return
     */
    @Override
    public int getRechargeStatusConnectorAPI(Recharge recharge) {
        if (recharge.getStatus() == 0) {
            return recharge.getStatus();
        }else if(recharge.getStatus() == 2) {
            return recharge.getStatus();
        }

		try {
			//TODO 支付查询接口
			if (recharge.getStatus() == 0) {
				return recharge.getStatus();
			}else if(recharge.getStatus() == 2) {
				return recharge.getStatus();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return 1;
	}

	/**
	 * 按orderType,orderNo获得支付中的支付单
	 * @author 张琼麒
	 * @version 创建时间：2019年5月23日 下午7:20:48
	 * @param orderType
	 * @param orderNo
	 * @return
	 */
	@Override
	public List<Recharge> getPayingRechargeByOrderTypeAndNo(String orderType, Integer otherId) {
		RechargeExample example = new RechargeExample();

		Criteria c = example.createCriteria();
		c.andOrderTypeEqualTo(orderType);
		c.andOtherIdEqualTo(otherId);
		c.andStatusEqualTo(1);
		return rechargeMapper.selectByExample(example);
	}

	/**
	 * 完成支付
	 * 确认支付单状态后,传入支付单,完成支付中的支付单状态更新
	 * 请先确认支付单状态:
	 * @see RechargeService#doTryCompletePayingRecharge(Recharge)
	 *
	 * @author 张琼麒
	 * @version 创建时间：2019年5月23日 下午6:14:55
	 * @param recharge
	 * @throws Exception
	 */
	//@Override
	private void doCompletePayingRecharge(Recharge recharge) throws Exception {
		Assets assets = assetsMapper.selectByPrimaryKey(recharge.getUserId()); // 获取他的资产，取得可用余额
		// 查询是否有回调记录
		TradeMessageLog tradeMessageLog = tradeMessageLogService.getTradeMessageLog(recharge.getOutOrderNo(),
				TradeMessageTypeEnum.BACK.getFeatureName());
		if (null == tradeMessageLog) {
			tradeMessageLog = TradeMessageLog.createTradeMessageLogFactory(null,
					String.valueOf(recharge.getPayChannel()), String.valueOf(recharge.getPayChannel()),
					TradeMessageTypeEnum.BACK.getFeatureName(), String.valueOf(recharge.getUserId()),
					recharge.getOrderNo(), recharge.getOutOrderNo(), null, recharge.getRemark(), String.valueOf(true),
					TradeMessageStatusEnum.YIWANCHENG.getFeatureName());
		} else {
			tradeMessageLog = null;
		}
		try {
			this.updateRecord(recharge, assets, tradeMessageLog);
		} catch (Exception e) {
			logger.error("充值操作业务异常，" + recharge.toString());
			e.printStackTrace();
			throw e;
		}
	}


	/**
	 * 尝试完成支付
	 * 传入支付中的支付单,查询支付单状态,完成支付中的支付单状态更新
	 * @author 张琼麒
	 * @version 创建时间：2019年5月23日 下午6:14:55
	 * @param recharge
	 * @param remark
	 * @throws Exception
	 */
	@Override
	public void doTryCompletePayingRecharge(Recharge recharge) throws Exception {
		logger.info("完成支付中的支付单completePayingRecharge:"+recharge.getId());
		// 是否执行操作
		boolean isOperate = true;
		// 调用认证支付查询接口
		recharge=rechargeMapper.selectByPrimaryKey(recharge.getId());
		if(recharge.getStatus()!=1) {//充值状态0成功，1处理中，2失败
			throw new Exception("充值单状态不是处理中,不能执行完成支付中支付单方法");
		}
		if(recharge.getPayChannel().equals(OutPayEnum.FUIOU_QUICK.getFeatureName())) {
		    QueryFuiouQuickPayRequest request = new QueryFuiouQuickPayRequest();
	        request.setOutOrderNo(recharge.getOutOrderNo());
	        ServiceMessage msg = new ServiceMessage("fuiou.quick.order.query", request);
	        QueryFuiouQuickPayResponse payResult = (QueryFuiouQuickPayResponse) OpenApiClient.getInstance()
	                .setServiceMessage(msg).send();
	        String resultStatus = payResult.getStatus();
	        String remark = payResult.getErrorMsg();
	        switch (resultStatus) {
	        case "1":// 成功
	            recharge.setStatus(0);
	            recharge.setRemark("成功（已查询）");
	            break;
	        case "2":// 失败
	            recharge.setStatus(2);
	            recharge.setRemark(remark);
	            break;
	        case "0":// 失败
	            recharge.setStatus(2);
	            recharge.setRemark("用户取消支付");
	            break;
	        default:// 不处理
	            isOperate = false;
	            break;
	        }
		}else if(recharge.getPayChannel().equals(OutPayEnum.ALLINPAY.getFeatureName())) {
		    AllinPayRequest pay = new AllinPayRequest();
	        pay.setOrderNo(recharge.getOrderNo());
	        ServiceMessage msg = new ServiceMessage("allinpay.order.query", pay);
	        AllinPayResponse result = (AllinPayResponse) OpenApiClient.getInstance()
	                .setServiceMessage(msg).send();
	        if(PayConstants.RESP_CODE_SUCCESS.equals(result.getTrxStatus())) {
	            recharge.setStatus(0);
                recharge.setRemark("成功（已查询）");
	        }else if("1999".equals(result.getTrxStatus()) || "2008".equals(result.getTrxStatus())  || "2000".equals(result.getTrxStatus())) {
	        	//1999: 短信验证码已发送,请继续调用支付确认接口完成支付
                //2008/2000:交易处理中,请查询交易
	        	isOperate = false;
	        }else if ("1001".equals(result.getTrxStatus())){//交易不存在说明没付款可以直接取消
	            recharge.setStatus(2);
                recharge.setRemark("用户取消支付");
	        }else {
	        	recharge.setStatus(2);
	            recharge.setRemark(result.getErrorMsg());
	        }
		}else if(recharge.getPayChannel().equals(OutPayEnum.YEEPAY.getFeatureName())) {
		    YeePayRequest pay = new YeePayRequest();
            pay.setOrderNo(recharge.getOrderNo());
            ServiceMessage msg = new ServiceMessage("yeepay.order.query", pay);
            QueryYeePayResponse result = (QueryYeePayResponse) OpenApiClient.getInstance()
                    .setServiceMessage(msg).send();
            if("SUCCESSED".equals(result.getStatus())) {
                recharge.setStatus(0);
                recharge.setRemark("成功（已查询）");
            }else if("PROCESSING".equals(result.getStatus())) {
                //1999: 短信验证码已发送,请继续调用支付确认接口完成支付
                //2008/2000:交易处理中,请查询交易
                isOperate = false;
            }else if ("FAILED".equals(result.getStatus()) || result.getErrorMsg().equals("订单不存在")){//交易不存在说明没付款可以直接取消
                recharge.setStatus(2);
                recharge.setRemark("用户取消支付");
            }else {
                isOperate = false;
            }
		}else if(recharge.getPayChannel().equals(OutPayEnum.WXPAY.getFeatureName())){
		    if(StringUtils.isNotEmpty(recharge.getOutOrderNo())) {
                Map<String, String> data = new HashMap<String, String>();
                data.put("transaction_id", recharge.getOutOrderNo());
                logger.info(JSONArray.toJSONString(data));
                Map<String, String> qresult = new HashMap<String, String>();
                try {
                    MyConfig config = new MyConfig();
                    WXPay wxpay = new WXPay(config, false, false);
                    qresult = wxpay.orderQuery(data);
                    logger.info("qresult="+JSONArray.toJSONString(qresult));
                    if (!wxpay.isResponseSignatureValid(qresult)) {
                        logger.info("==============微信签名校验失败==============");
                        isOperate = false;
                    }
                    if(qresult.get("return_code").equals("SUCCESS") && qresult.get("result_code").equals("SUCCESS")) {
                        if("SUCCESS".equals(qresult.get("trade_state"))) {
                            recharge.setStatus(0);
                            recharge.setRemark("成功（已查询）");
                        }else if("USERPAYING".equals(qresult.get("trade_state"))) {
                            isOperate = false;
                        }else {
                            recharge.setStatus(2);
                            recharge.setRemark("用户取消支付");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
		    }else {
		        recharge.setStatus(2);
                recharge.setRemark("用户取消支付");
		    }
		}else if(recharge.getPayChannel().equals(OutPayEnum.ALPAY.getFeatureName())){
		    if(StringUtils.isNotEmpty(recharge.getOutOrderNo())) {
                try {
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
                    AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();
                    AlipayTradeQueryModel model=new AlipayTradeQueryModel();
                    model.setOutTradeNo(recharge.getOrderNo());
                    model.setTradeNo(recharge.getOutOrderNo());
                    alipay_request.setBizModel(model);
                    AlipayTradeQueryResponse alipay_response =alipayClient.certificateExecute(alipay_request);
                    logger.info("qresult="+JSONArray.toJSONString(alipay_response));
                    if(alipay_response.getCode().equals("10000")) {
                        if("TRADE_SUCCESS".equals(alipay_response.getTradeStatus())) {
                            recharge.setStatus(0);
                            recharge.setRemark("成功（已查询）");
                        }else if("WAIT_BUYER_PAY".equals(alipay_response.getTradeStatus())) {
                            isOperate = false;
                        }else {
                            recharge.setStatus(2);
                            recharge.setRemark("用户取消支付");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                recharge.setStatus(2);
                recharge.setRemark("用户取消支付");
            }
		}
		if (isOperate) {
			doCompletePayingRecharge(recharge);
		}
	}

	/**
     * 支付单 支付状态修改
     * @param investment
     * @param needStatus
     * @return
     */
    @Override
    public int updateCAS(Recharge recharge, int status) {
        RechargeExample example = new RechargeExample();
        RechargeExample.Criteria c = example.createCriteria();
        c.andStatusEqualTo(status).andIdEqualTo(recharge.getId());
        recharge.setUpdateDate(new Date());
        return rechargeMapper.updateByExampleSelective(recharge, example);
    }

    /**
     * 根据otherId和orderType获取支付成功的数据
     */
    @Override
    public Recharge getPaySuccessRechargeByOrderTypeAndId(String orderType, Integer otherId) {
        RechargeExample example = new RechargeExample();
        Criteria c = example.createCriteria();
        c.andOrderTypeEqualTo(orderType);
        c.andOtherIdEqualTo(otherId);
        c.andStatusEqualTo(0);
        List<Recharge> recharge = rechargeMapper.selectByExample(example);
        if(null != recharge && recharge.size()>0) {
            return recharge.get(0);
        }
        return null;
    }


    // 线上补单
    @Override
    public void updateFixedByOnLine(Integer id, UserAdmin admin) throws Exception {
        Recharge recharge = rechargeMapper.selectByPrimaryKey(id);
        if (!recharge.getStatus().equals(1)) {
            return;
        }
//        Assets assets = assetsMapper.selectByPrimaryKey(recharge.getUserId()); // 获取他的资产，取得可用余额
        this.doTryCompletePayingRecharge(recharge);

        AdminLog adminLog = new AdminLog();
        adminLog.setAdminId(admin.getId());
        adminLog.setAdminUserName(admin.getTrueName());
        adminLog.setOperateTime(new Date());
        adminLog.setRemark("充值补单");
        adminLogMapper.insertSelective(adminLog);
    }

    @Override
    public RechargeMapper getMapper() {
    	return rechargeMapper;
    }

    @Override
    public boolean updateBalance(Recharge recharge, Assets assets) throws Exception {
        boolean flag = true;
        try {
            logger.info("otherId="+recharge.getOtherId());
            //CAS更新,因为支付单只有一个方法可以更新,所以没有并发问题,如果以后增加支付单得更新方法,需要加锁
            if(this.updateCAS(recharge,1) != 1) {
                flag = false;
                logger.error("修改充值单信息失败，"+ recharge.toString());
            }
            // 只有充值成功的状态才会插入交易记录表里
            if (recharge.getStatus() == 0) {
                // 处理日志状态
                TradeMessageLogVO tradeMessageLogVO = new TradeMessageLogVO();
                tradeMessageLogVO.setInOrderId(recharge.getOrderNo());
                tradeMessageLogVO.setMessageType(TradeMessageTypeEnum.BACK.getFeatureName());
                tradeMessageLogVO.setEditStatus(TradeMessageStatusEnum.YIWANCHENG.getFeatureName());
                if (tradeMessageLogMapper.updateTradeMessageLogStatus(tradeMessageLogVO) != 1) {
                    flag = false;
                    logger.error("修改交易回调信息状态失败，" + tradeMessageLogVO.toString());
                }
                //增加交易日志
                if(tradeRecordService.addRecord(assets, BigDecimal.valueOf(recharge.getAmount()), null, BusinessTableEnum.recharge, AccountOperateEnum.RECHARGE_CASH_SUBTRACT) == 0) {
                    flag = false;
                    logger.error("更新交易记录失败，" + assets.getUserId());
                }
                //增加资金
                if(userAccountService.modifyAccount(assets, BigDecimal.valueOf(recharge.getAmount()), null,
                    BusinessTableEnum.recharge, AccountOperateEnum.RECHARGE_BALANCE_ADD) == 0) {
                    flag = false;
                    logger.error("更新余额失败，" + assets.getUserId());
                }

                // 更新用户资金
                if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(assets) == 0) {
                    flag = false;
                }
            }

            if (!flag) {
                throw new Exception("更新充值交易信息异常，事务回滚");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return flag;
    }
    
    private boolean doPaySuccess(Recharge recharge, Assets assets) throws Exception {
    	boolean flag=true;
        //增加现金交易记录
        if(userAccountService.modifyAccount(assets, BigDecimal.valueOf(recharge.getAmount()), recharge.getId(),
            BusinessTableEnum.recharge, AccountOperateEnum.RECHARGE_CASH_SUBTRACT) == 0) {
            flag = false;
            logger.error("增加现金交易记录，" + assets.getUserId());
        }
        //增加资金
        if(userAccountService.modifyAccount(assets, BigDecimal.valueOf(recharge.getAmount()), recharge.getId(),
            BusinessTableEnum.recharge, AccountOperateEnum.RECHARGE_BALANCE_ADD) == 0) {
            flag = false;
            logger.error("更新余额失败，" + assets.getUserId());
        }
        assets.setUpdateDate(new Date());
        // 更新用户资金
        if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(assets) == 0) {
            flag = false;
        }
      //增加节点
  		OrderDone orderDone = new OrderDone();
  		orderDone.setOrderNo(recharge.getOrderNo());
  		orderDone.setOrderStatus(OrderDoneEnum.PAY.getFeatureName());
  		orderDone.setUpdateDate(new Date());
  		orderDone.setCreateDate(new Date());
  		orderDone.setOrderType(OrderTypeEnum.RECHARGE.getFeatureName());
  		if (orderDoneService.insert(orderDone) != 1) {
  			flag = false;
  		}
  		//TODO 充值活动
  		//activityService.doActivityInvestment(order);
        if(!flag) {
        	throw new LockFailureException();
        }
        return flag;
    }

    @Override
    public double querySumAmount(String keyword, String payChannel, Date startTime, Date endTime, String client,Integer adminId, String code,Integer departmentId) {
        Map<String, Object> map = new HashMap<String, Object>();
        String startDateStr = startTime == null ? null : DateUtil.dateTimeZoreFormat.format(startTime);
        String endDateStr = endTime == null ? null : DateUtil.dateTimeMaxSMFormat.format(endTime);
        map.put("startTime", startDateStr);
        map.put("endTime", endDateStr);
        map.put("payChannel", payChannel);
        map.put("adminId", adminId);
        map.put("client", client);
        map.put("keyword", keyword);
//        map.put("status", status);
        map.put("code", code);
        map.put("departmentId", departmentId);
        return rechargeMapper.querySumAmount(map);
    }

}
