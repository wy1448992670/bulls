package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.goochou.p2b.constant.goods.ActivityTypeEnum;
import com.goochou.p2b.constant.redis.RedisConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.ConfigHelper;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.OrderDoneEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.assets.AccountOperateEnum;
import com.goochou.p2b.constant.goods.GoodsOrderStatusEnum;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.dao.AssessMapper;
import com.goochou.p2b.dao.GoodsOrderMapper;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.pay.AllinPayRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayResponse;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderListRequest;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.OrderDone;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderDetail;
import com.goochou.p2b.model.goods.GoodsOrderExample;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.GoodsOrderDetailService;
import com.goochou.p2b.service.GoodsOrderInvestRelationService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.GoodsService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.OrderDoneService;
import com.goochou.p2b.service.RechargeService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UserAccountService;
import com.goochou.p2b.service.exceptions.DnotMeetConditionsRedpackageException;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.Money;

/**
 * @Auther: huangsj
 * @Date: 2019/5/9 11:29
 * @Description:
 */
@Service
public class GoodsOrderServiceImpl implements GoodsOrderService {
    
    private static final Logger logger = Logger.getLogger(GoodsOrderServiceImpl.class);

	@Autowired
	private GoodsOrderMapper goodsOrderMapper;
	@Autowired
	private GoodsOrderDetailService goodsOrderDetailService;
	@Autowired
	private HongbaoService hongbaoService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private TradeRecordService tradeRecordService;
	@Autowired
	private GoodsOrderInvestRelationService relationService;
	@Autowired
	private OrderDoneService orderDoneService;
	@Autowired
	private RedisService redisService;

	@Autowired
	private AssessMapper assessMapper;
	
	@Resource
    private MemcachedManager memcachedManager;
	
	@Override
	public GoodsOrderMapper getMapper() {
		return goodsOrderMapper;
	}

	@Override
	public GoodsOrder findByOrderNum(String orderNum) {

		GoodsOrderExample example = new GoodsOrderExample();
		GoodsOrderExample.Criteria c = example.createCriteria();
		c.andOrderNoEqualTo(orderNum);

		List<GoodsOrder> orders = goodsOrderMapper.selectByExample(example);
		if (orders != null && orders.size() > 0) {
			return orders.get(0);
		} else {
			return null;
		}
	}

	@Override
	public boolean doPaySuccess(GoodsOrder goodsOrder, Assets userAccount) throws Exception {
		boolean flag = true;
		if (goodsOrder.getState() != GoodsOrderStatusEnum.PAYING.getCode()) {
    		throw new Exception("订单状态错误");
        }
		
		//现金支付
		if(goodsOrder.getRealPayMoney().compareTo(BigDecimal.ZERO) > 0) {
			if (userAccountService.modifyAccount(userAccount, goodsOrder.getRealPayMoney(), goodsOrder.getId(),
					BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_CASH_SUBTRACT) == 0) {
				flag = false;
			}
			
			// 解冻并扣除用户的余额
			if (goodsOrder.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0 ) {
				//BLANCE,FROZEN_SUBTRACT
				if (userAccountService.modifyAccount(userAccount, goodsOrder.getBalancePayMoney(), goodsOrder.getId(),
						BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_BALANCE_FROZEN_SUBTRACT) == 0) {
					flag = false;
				}
			}
			
			// 解冻并扣除用户的授信资金
			if (goodsOrder.getCreditPayMoney().compareTo(BigDecimal.ZERO) > 0 ) {
				//CREDIT,FROZEN_SUBTRACT
				if (userAccountService.modifyAccount(userAccount, goodsOrder.getCreditPayMoney(), goodsOrder.getId(),
						BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_CREDIT_FROZEN_SUBTRACT) == 0) {
					flag = false;
				}
			}
		}

		goodsOrder.setState(GoodsOrderStatusEnum.PAYED.getCode());
		goodsOrder.setUpdateDate(new Date());
		if (goodsOrderMapper.updateByPrimaryKeySelective(goodsOrder) == 0) {
			flag = false;
		}
		
		// 更新用户资金
		if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(userAccount) == 0) {
			flag = false;
		}

		//若是秒杀活动，删除用户未支付的缓存
		List<GoodsOrderDetail> details =  goodsOrderDetailService.getDetailsByOrderId(goodsOrder.getId());
		if(details!=null && details.size()==1 && details.get(0).getActivityType() != null  && details.get(0).getActivityType()==ActivityTypeEnum.SECOND_KILL.getCode()){
			redisService.del(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_UNPAY,details.get(0).getActivityDetailId(),goodsOrder.getUserId()));
		}

		// 增加节点
		OrderDone orderDone = new OrderDone();
		orderDone.setOrderNo(goodsOrder.getOrderNo());
		orderDone.setOrderStatus(OrderDoneEnum.PAY.getFeatureName());
		orderDone.setUpdateDate(new Date());
		orderDone.setCreateDate(new Date());
		orderDone.setOrderType(OrderTypeEnum.GOODS.getFeatureName());
		if (orderDoneService.insert(orderDone) != 1) {
			flag = false;
		}
		if (!flag) {
			throw new LockFailureException();
		}
		return flag;
	}

	@Override
	public boolean cancelOrder(GoodsOrder goodsOrder, Assets assets) throws Exception {

		// todo: 查看此订单有没有支付订单，如果有查找第三方支付接口，看此订单的支付情况，确认失败或不存在则再走下面的业务
		recoverGoodsStock(goodsOrder, assets);
		return true;
	}

	@Override
	public boolean doRefundOrder(GoodsOrder goodsOrder, Assets assets) throws Exception {
		recoverGoodsStock(goodsOrder, assets);
		
		return true;
	}

	@Override
	public boolean addOrder(User user, GoodsOrder goodsOrder, List<Goods> goodss, List<Integer> nums, Hongbao hongbao)
			throws Exception {
		boolean flag = true;

		List<GoodsOrderDetail> details = new ArrayList<>();

		BigDecimal orderTotalMoney = BigDecimal.ZERO;

		for (int i = 0; i < goodss.size(); i++) {
			Goods goods = goodss.get(i);

			// 修改商品的库存
			goods.setStock(goods.getStock() - nums.get(i));
			if (goodsService.getMapper().updateByPrimaryKeyAndVersionSelective(goods) == 0) {
				flag = false;
				break;
			}

			boolean isMember = user.getLevel() > 0 ? true : false;
			BigDecimal buyPrice = isMember ? goods.getMemberSalingPrice() : goods.getSalingPrice();

			// 生成订单详情数据
			GoodsOrderDetail detail = goodsOrderDetailService.initDetailNoSave(goods.getId(), nums.get(i),
					goods.getSalingPrice(), buyPrice);
			details.add(detail);

			orderTotalMoney = orderTotalMoney
					.add(detail.getBuyPrice().multiply(BigDecimalUtil.parse(detail.getCount())));
		}

		goodsOrder.setTotalMoney(orderTotalMoney);

		if (hongbao != null) {
			if (hongbao.getLimitAmount() > goodsOrder.getTotalMoney().intValue()) {
				throw new DnotMeetConditionsRedpackageException();
			}

			goodsOrder.setHongbaoId(hongbao.getId());
			if (BigDecimalUtil.parse(hongbao.getAmount()).compareTo(goodsOrder.getTotalMoney()) > 0) {
				goodsOrder.setHongbaoMoney(goodsOrder.getTotalMoney());
			} else {
				goodsOrder.setHongbaoMoney(BigDecimalUtil.parse(hongbao.getAmount()));
			}

			// 修改红包的使用情况
			hongbao.setUseTime(new Date());
			if (hongbaoService.getMapper().update(hongbao) == 0) {
				flag = false;
			}
		} else {
			goodsOrder.setHongbaoMoney(BigDecimal.ZERO);
		}

		if (goodsOrderMapper.insertSelective(goodsOrder) == 0) {
			flag = false;
		}

		for (GoodsOrderDetail detail : details) {
			detail.setOrderId(goodsOrder.getId());
			if (goodsOrderDetailService.getMapper().insertSelective(detail) == 0) {
				flag = false;
				break;
			}
		}

		// 增加节点
		OrderDone orderDone = new OrderDone();
		orderDone.setOrderNo(goodsOrder.getOrderNo());
		orderDone.setOrderStatus(OrderDoneEnum.SUBMIT.getFeatureName());
		orderDone.setUpdateDate(new Date());
		orderDone.setCreateDate(new Date());
		orderDone.setOrderType(OrderTypeEnum.GOODS.getFeatureName());
		if (orderDoneService.insert(orderDone) != 1) {
			flag = false;
		}

		return flag;
	}

	@Override
	@Deprecated
	public boolean submitOrder(GoodsOrder goodsOrder, Assets userAccount) throws Exception {
		boolean flag = true;

		// 可用资金变动纪录，授信资金变动纪录
		if (goodsOrder.getRealPayMoney().compareTo(BigDecimal.ZERO) == 0) {
			if (goodsOrder.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 扣除使用的余额
				//BLANCE,SUBTRACT
				userAccountService.modifyAccount(userAccount,goodsOrder.getBalancePayMoney(), 
						goodsOrder.getId(),BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_BALANCE_SUBTRACT);
				
			}
			if (goodsOrder.getCreditPayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 扣除使用的锁定余额
				//CREDIT,SUBTRACT
				userAccountService.modifyAccount(userAccount,goodsOrder.getCreditPayMoney(),
						goodsOrder.getId(),	BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_CREDIT_SUBTRACT);
			}
		} else {
			if (goodsOrder.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 冻结支付的余额
				//BLANCE,FREZEE
				userAccountService.modifyAccount(userAccount, goodsOrder.getBalancePayMoney(),
						goodsOrder.getId(), BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_BALANCE_FREEZE);
			}
			if (goodsOrder.getCreditPayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 冻结支付的锁定余额
				//CREDIT,FREZEE
				userAccountService.modifyAccount(userAccount, goodsOrder.getCreditPayMoney(),
						goodsOrder.getId(), BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_CREDIT_FREEZE);
			}
		}

		if (goodsOrderMapper.updateByPrimaryKeySelective(goodsOrder) == 0) {
			flag = false;
		}

		// 无第三方支付
		if (goodsOrder.getRealPayMoney().compareTo(BigDecimal.ZERO) == 0) {
			if (!doPaySuccess(goodsOrder, userAccount)) {
				flag = false;
			}
		} else {
			// 更新用户资金
			if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(userAccount) == 0) {
				flag = false;
			}
		}

		if (!flag) {
			throw new LockFailureException();
		}

		return flag;

	}

	@Override
	public boolean addSubmitOrder(User user, GoodsOrder goodsOrder,  List<GoodsOrderDetail> goodsOrderDetails, Hongbao hongbao, Assets userAccount)
			throws Exception {

		List<GoodsOrderDetail> goodsOrderDetailsCopy=new ArrayList<GoodsOrderDetail>(goodsOrderDetails);
		//goodsOrderDetailsCopy对goods.id正序排序,防止更新库存时锁库
		Collections.sort(goodsOrderDetailsCopy, new Comparator<GoodsOrderDetail>(){
			@Override
			public int compare(GoodsOrderDetail orderDetail1, GoodsOrderDetail orderDetail2) {
				if (orderDetail1.getGoodsId() > orderDetail2.getGoodsId()) {
					return 1;
				} else if (orderDetail1.getGoodsId() < orderDetail2.getGoodsId()) {
					return -1;
				}
				return 0; // 相等为0
			}
		});

		//先对用户账户上锁
		userAccount=assetsService.getMapper().selectByPrimaryKeyForUpdate(userAccount.getUserId());
		//订单
		if (goodsOrderMapper.insertSelective(goodsOrder) == 0) {
			throw new LockFailureException();
		}
		
		//红包
		if (hongbao != null) {
			//CAS更新where user_id,use_time=null
			if (hongbaoService.getMapper().update(hongbao) == 0) {
				throw new LockFailureException();
			}
		}
		
		//订单详情
		for (GoodsOrderDetail goodOrderDetail : goodsOrderDetails) {
			goodOrderDetail.setOrderId(goodsOrder.getId());
			if (goodsOrderDetailService.getMapper().insertSelective(goodOrderDetail) == 0) {
				throw new LockFailureException();
			}
		}
		
		//库存
		for(GoodsOrderDetail goodOrderDetail:goodsOrderDetailsCopy) {

			if(goodOrderDetail.getActivityType()!=null && goodOrderDetail.getActivityType() == ActivityTypeEnum.SECOND_KILL.getCode()
					&& goodOrderDetail.getActivityDetailId()!=null) {
				//秒杀活动库存在清算时结算,此处不处理

			}else{
				Goods goods = goodOrderDetail.getGoods();
				//减库存
				if (goodsService.getMapper().sell(goodOrderDetail) == 0) {
					throw new Exception(goods.getGoodsName() + "库存不足");
				}
			}
		}

		// 增加节点
		OrderDone orderDone = new OrderDone();
		orderDone.setOrderNo(goodsOrder.getOrderNo());
		orderDone.setOrderStatus(OrderDoneEnum.SUBMIT.getFeatureName());
		orderDone.setUpdateDate(new Date());
		orderDone.setCreateDate(new Date());
		orderDone.setOrderType(OrderTypeEnum.GOODS.getFeatureName());
		if (orderDoneService.insert(orderDone) != 1) {
			throw new LockFailureException();
		}
		
		// 可用资金变动纪录，授信资金变动纪录
		if (goodsOrder.getRealPayMoney().compareTo(BigDecimal.ZERO) == 0) {
			if (goodsOrder.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 扣除使用的余额
				//BLANCE,SUBTRACT
				userAccountService.modifyAccount(userAccount,goodsOrder.getBalancePayMoney(), 
						goodsOrder.getId(),BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_BALANCE_SUBTRACT);
				
			}
			if (goodsOrder.getCreditPayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 扣除使用的锁定余额
				//CREDIT,SUBTRACT
				userAccountService.modifyAccount(userAccount,goodsOrder.getCreditPayMoney(),
						goodsOrder.getId(),	BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_CREDIT_SUBTRACT);
			}
		} else {
			if (goodsOrder.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 冻结支付的余额
				//BLANCE,FREZEE
				userAccountService.modifyAccount(userAccount, goodsOrder.getBalancePayMoney(),
						goodsOrder.getId(), BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_BALANCE_FREEZE);
			}
			if (goodsOrder.getCreditPayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 冻结支付的锁定余额
				//CREDIT,FREZEE
				userAccountService.modifyAccount(userAccount, goodsOrder.getCreditPayMoney(),
						goodsOrder.getId(), BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_CREDIT_FREEZE);
			}
		}
		
		// 无第三方支付
		if (goodsOrder.getRealPayMoney().compareTo(BigDecimal.ZERO) == 0) {
			goodsOrder=this.getMapper().selectByPrimaryKey(goodsOrder.getId());
			goodsOrder.setState(GoodsOrderStatusEnum.PAYING.getCode());
			if (!doPaySuccess(goodsOrder, userAccount)) {
				throw new LockFailureException();
			}
		} else {
			// 更新用户资金
			if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(userAccount) == 0) {
				throw new LockFailureException();
			}
		}
		return true;
	}



	/**
	 * 因订单取消、退款，恢复商品库存业务
	 *
	 * @param goodsOrder
	 * @throws Exception
	 */
	private void recoverGoodsStock(GoodsOrder goodsOrder, Assets assets) throws Exception {
		// 先对用户账户上锁
		assets=assetsService.getMapper().selectByPrimaryKeyForUpdate(assets.getUserId());
		// 对订单上锁
		goodsOrder=goodsOrderMapper.selectByPrimaryKeyForUpdate(goodsOrder.getId());
		
		if (goodsOrder.getState() != GoodsOrderStatusEnum.NO_PAY.getCode()
				&& goodsOrder.getState() != GoodsOrderStatusEnum.REFUNDING.getCode()) {
			throw new Exception("订单当前状态不可取消");
		}
		
		// 返还已使用的红包
		if (goodsOrder.getHongbaoMoney().compareTo(BigDecimal.ZERO) > 0) {
			Hongbao hongbao = hongbaoService.getMapper().selectByPrimaryKey(goodsOrder.getHongbaoId());
			hongbao.setUseTime(null);
			if (hongbaoService.getMapper().updateByPrimaryKey(hongbao) == 0) {
				throw new LockFailureException();
			}
		}

		// 增加库存
		List<GoodsOrderDetail> goodsOrderDetails = goodsOrderDetailService.getDetailsByOrderId(goodsOrder.getId());
		//goodsOrderDetails对goods.id正序排序,防止更新库存时锁库
		Collections.sort(goodsOrderDetails, new Comparator<GoodsOrderDetail>(){
			@Override
			public int compare(GoodsOrderDetail orderDetail1, GoodsOrderDetail orderDetail2) {
				if (orderDetail1.getGoodsId() > orderDetail2.getGoodsId()) {
					return 1;
				} else if (orderDetail1.getGoodsId() < orderDetail2.getGoodsId()) {
					return -1;
				}
				return 0; // 相等为0
			}
		});
		for (GoodsOrderDetail goodsOrderDetail : goodsOrderDetails) {
			if(goodsOrderDetail.getActivityType() != null && goodsOrderDetail.getActivityType() == ActivityTypeEnum.SECOND_KILL.getCode()
					&& goodsOrderDetail.getActivityDetailId()!=null) {
				//秒杀活动库存在清算时结算,此处不处理

			}else {
				if (goodsService.getMapper().add(goodsOrderDetail) == 0) {
					throw new Exception(goodsOrderDetail.getGoods().getGoodsName() + "增加库存失败");
				}
			}
		}

		if (goodsOrder.getState() == GoodsOrderStatusEnum.NO_PAY.getCode()) {
			if (goodsOrder.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 冻结支付的余额
				//BLANCE,UNFREZEE
				userAccountService.modifyAccount(assets, goodsOrder.getBalancePayMoney(),
						goodsOrder.getId(), BusinessTableEnum.t_goods_order,AccountOperateEnum.GOODSORDER_CLOSE_BALANCE_UNFREEZE);
			}
			if (goodsOrder.getCreditPayMoney().compareTo(BigDecimal.ZERO) > 0) {
				// 冻结支付的锁定余额
				//CREDIT,UNFREZEE
				userAccountService.modifyAccount(assets, goodsOrder.getCreditPayMoney(),
						goodsOrder.getId(), BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_CLOSE_CREDIT_UNFREEZE);
				// 授信使用的关系处理，恢复授信使用金额
				relationService.recoverCreditBalance(goodsOrder, assets);
			}
		} else if (goodsOrder.getState() == GoodsOrderStatusEnum.REFUNDING.getCode()) {
			// 退回已使用的余额
			if (goodsOrder.getBalancePayMoney().compareTo(BigDecimal.ZERO) > 0) {
				//BLANCE,ADD
				if (userAccountService.modifyAccount(assets, goodsOrder.getBalancePayMoney() ,goodsOrder.getId(),
						BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_REFUND_BALANCE_ADD) == 0) {
					throw new LockFailureException();
				}
			}
			// 退回已使用的授信金额
			if (goodsOrder.getCreditPayMoney().compareTo(BigDecimal.ZERO) > 0) {
				//CREDIT,ADD
				if (userAccountService.modifyAccount(assets, goodsOrder.getCreditPayMoney(), goodsOrder.getId(),
						BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_REFUND_CREDIT_ADD) == 0) {
					throw new LockFailureException();
				}
				// 授信使用的关系处理，恢复授信使用金额
				relationService.recoverCreditBalance(goodsOrder, assets);
			}
			//退回已使用的现金
			if(goodsOrder.getRealPayMoney().compareTo(BigDecimal.ZERO) > 0 ) {
				//CASH,ADD
				if (userAccountService.modifyAccount(assets, goodsOrder.getRealPayMoney(), goodsOrder.getId(),
						BusinessTableEnum.t_goods_order, AccountOperateEnum.GOODSORDER_REFUND_CASH_ADD) == 0) {
					throw new LockFailureException();
				}
			}
		} else {
			throw new Exception("订单当前状态不可取消");
		}

		if (assetsService.getMapper().updateByPrimaryKeyAndVersionSelective(assets) == 0) {
			throw new LockFailureException();
		}

		// 修改订单状态
		if(goodsOrder.getState()==GoodsOrderStatusEnum.NO_PAY.getCode()) {
			goodsOrder.setState(GoodsOrderStatusEnum.CANCEL.getCode());
		}else {
			goodsOrder.setState(GoodsOrderStatusEnum.REFUND.getCode());
		}
		goodsOrder.setUpdateDate(new Date());
		if (goodsOrderMapper.updateByPrimaryKeySelective(goodsOrder) == 0) {
			throw new LockFailureException();
		}

		// 增加节点
		OrderDone orderDone = new OrderDone();
		orderDone.setOrderNo(goodsOrder.getOrderNo());
		if(goodsOrder.getState()==GoodsOrderStatusEnum.CANCEL.getCode()) {
			orderDone.setOrderStatus(OrderDoneEnum.CANNEL.getFeatureName());
		}else {
			//orderDone.setOrderStatus(OrderDoneEnum.REFUND_APPLY.getFeatureName());
			orderDone.setOrderStatus(OrderDoneEnum.REFUND_FINISH.getFeatureName());
		}
		orderDone.setUpdateDate(new Date());
		orderDone.setCreateDate(new Date());
		orderDone.setOrderType(OrderTypeEnum.GOODS.getFeatureName());
		if (orderDoneService.insert(orderDone) != 1) {
			throw new LockFailureException();
		}

        if(goodsOrder.getState()==GoodsOrderStatusEnum.CANCEL.getCode()) {
            //若是秒杀活动，删除用户未支付的缓存
            List<GoodsOrderDetail> details = goodsOrderDetailService.getDetailsByOrderId(goodsOrder.getId());
            if (details != null
                    && details.size() == 1
                    &&  details.get(0).getActivityType() != null
                    && details.get(0).getActivityType().intValue() == ActivityTypeEnum.SECOND_KILL.getCode()) {
                redisService.del(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_UNPAY, details.get(0).getActivityDetailId(), goodsOrder.getUserId()));

                redisService.decr(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_BUYED_COUNT, details.get(0).getActivityDetailId(), goodsOrder.getUserId()), 1);
                redisService.decr(String.format(RedisConstants.SECONDKILL_ACTIVITY_SALED_COUNT, details.get(0).getActivityDetailId()), 1);
            }
        }
	}

	@Override
	public List<GoodsOrder> findByIds(List<Integer> ids) {
		if (ids == null || ids.isEmpty())
			return new ArrayList<>();
		GoodsOrderExample example = new GoodsOrderExample();
		GoodsOrderExample.Criteria c = example.createCriteria();
		c.andIdIn(ids);
		return goodsOrderMapper.selectByExample(example);
	}

	@Override
	public List<Map<String, Object>> listGoodsOrder(String trueName, Integer goodsId, String keyword, List<Integer> status,
			Date startTime, Date endTime, Integer limitStart, Integer limitEnd, Integer userId, Integer expressWay,
			Date payStartTime,Date payEndTime, Date refundFinishStartTime, Date refundFinishEndTime, Integer adminId,
			Integer departmentId, String payChannel) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("trueName", trueName);
		map.put("goodsId", goodsId);
		map.put("keyword", keyword);
		if(status!=null && status.size()>0) {
			map.put("status", status);
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("userId", userId);
		map.put("payStartTime", payStartTime);
		map.put("payEndTime", payEndTime);
		map.put("refundFinishStartTime", refundFinishStartTime);
		map.put("refundFinishEndTime", refundFinishEndTime);
		map.put("limitStart", limitStart);
		map.put("limitEnd", limitEnd);
		map.put("expressWay", expressWay);
		map.put("adminId", adminId);
		map.put("departmentId", departmentId);
		map.put("payChannel", payChannel);
		
		return goodsOrderMapper.listGoodsOrder(map);
	}

	@Override
	public Integer countOrder(String trueName, Integer goodsId, String keyword, List<Integer> status, Date startTime,
			Date endTime, Integer userId, Integer expressWay,
			Date payStartTime,Date payEndTime, Date refundFinishStartTime, Date refundFinishEndTime, Integer adminId,
			Integer departmentId, String payChannel) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("trueName", trueName);
		map.put("goodsId", goodsId);
		map.put("keyword", keyword);
		if(status!=null && status.size()>0) {
			map.put("status", status);
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("userId", userId);
		map.put("payStartTime", payStartTime);
		map.put("payEndTime", payEndTime);
		map.put("refundFinishStartTime", refundFinishStartTime);
		map.put("refundFinishEndTime", refundFinishEndTime);
		map.put("expressWay", expressWay);
		map.put("adminId", adminId);
		map.put("departmentId", departmentId);
		map.put("payChannel", payChannel);
		
		return goodsOrderMapper.countOrder(map);
	}

	@Override
	public Double getAmountCount(Integer adminId,Integer departmentId) {
		return goodsOrderMapper.getAmountCount(adminId,departmentId);
	}

	@Override
	public List<GoodsOrder> listGoodsOrder(GoodsOrderListRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", request.getStatus());
		map.put("limitStart", request.getLimitStart());
		map.put("limitEnd", request.getLimitEnd());
		map.put("userId", request.getUserId());

		return goodsOrderMapper.listGoodsOrderApp(map);
	}

	@Override
	public Integer countOrder(GoodsOrderListRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", request.getGoodsId());
		map.put("status", request.getStatus());
		map.put("userId", request.getUserId());
		return goodsOrderMapper.countOrderApp(map);
	}

	@Override
	public GoodsOrder queryGoodsOrderDetail(String orderNo) {
		return goodsOrderMapper.queryGoodsOrderDetail(orderNo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.goochou.p2b.service.GoodsOrderService#update(java.lang.Integer,
	 * com.goochou.p2b.model.goods.GoodsOrder, int)
	 */
	@Override
	public int update(GoodsOrder goodsOrder, int needStatus) {
		GoodsOrderExample example = new GoodsOrderExample();
		GoodsOrderExample.Criteria c = example.createCriteria();
		c.andStateEqualTo(needStatus).andIdEqualTo(goodsOrder.getId());
		return goodsOrderMapper.updateByExampleSelective(goodsOrder, example);
	}

	@Override
	public List<Map<String, Object>> listBuyGoodsRecord(Integer goodsId, Integer limitStart, Integer limitEnd) {
		return goodsOrderMapper.listBuyGoodsRecord(goodsId, limitStart, limitEnd);
	}
	@Override
	public Integer countBuyGoodsRecord(Integer goodsId, Integer limitStart, Integer limitEnd) {
		return goodsOrderMapper.countBuyGoodsRecord(goodsId, limitStart, limitEnd);
	}


	@Override
	public GoodsOrder queryGoodsOrderByNumber(String orderNo) {
		GoodsOrderExample example = new GoodsOrderExample();
		example.setLimitStart(0);
		example.setLimitEnd(1);
		example.setOrderByClause(" create_date desc");
		example.createCriteria().andOrderNoEqualTo(orderNo);
		List<GoodsOrder> goodsOrder = getMapper().selectByExample(example);
		return (goodsOrder != null && !goodsOrder.isEmpty()) ? goodsOrder.get(0) : null;
	}

	private int updateGoodsOrder(Integer id, GoodsOrder goodsOrder) {
		goodsOrder.setId(id);
		return getMapper().updateByPrimaryKeySelective(goodsOrder);
	}


	@Override
	public int updateOrderState(Integer id, Integer state) {
		GoodsOrder goodsOrder = new GoodsOrder();
		goodsOrder.setState(state);
		return this.updateGoodsOrder(id, goodsOrder);
	}

	@Override
	public int updateAddressDetail(Integer id, String addressDetail) {
		GoodsOrder goodsOrder = new GoodsOrder();
		goodsOrder.setAddresseeDetail(addressDetail);
		return this.updateGoodsOrder(id, goodsOrder);
	}

	@Override
	public int updateIsHiddenById(Integer id) {
		GoodsOrder goodsOrder = new GoodsOrder();
		goodsOrder.setIsHidden(1);
		return this.updateGoodsOrder(id, goodsOrder);
	}

    /**
	 * 获得过期订单(未完成购买的,包括未购买和购买中)
	 * 2019-05-24 张琼麒
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<GoodsOrder> getExceedTimeLimitOrder() throws Exception {
		GoodsOrderExample example=new GoodsOrderExample();
		//最早的 有效的 创建时间
		GregorianCalendar earliestValidTime=new GregorianCalendar();
		
		//有效期(分钟)
		String validity=memcachedManager.getCacheKeySingleValue(DictConstants.PAY_WAIT_TIME);
		System.out.println("validity2:"+validity);
		
		if(StringUtils.isBlank(validity) || !com.goochou.p2b.utils.StringUtils.checkInt(validity)) {
			validity="30";//默认30分钟
		}
		//当前时间 - 有效期=当前存在的最早的 有效的 创建时间
		earliestValidTime.add(Calendar.MINUTE, - Integer.parseInt(validity));
		
		//earliestValidTime 以前,且未支付的pay_status=0,且未饲养的投资订单order_status=0
		GoodsOrderExample.Criteria c = example.createCriteria();
		c.andStateIn(new ArrayList<Integer>() {{add(GoodsOrderStatusEnum.NO_PAY.getCode());add(GoodsOrderStatusEnum.PAYING.getCode());}});
		c.andCreateDateLessThan(earliestValidTime.getTime());

		return this.getMapper().selectByExample(example);
	}


	// 计算商品总价格
	public BigDecimal getTotalAmoutByOrders(boolean isVIP, List<Integer> goodsIds, List<Integer> counts){
		//商品总金额
		BigDecimal totalAmout = BigDecimal.ZERO;
		if (goodsIds == null || counts == null) {
			return totalAmout;
		}
		//TODO此处需要对商品id排序,防止更新库存时死锁
		for (int i = 0; i < goodsIds.size(); i++) {
			//当前商品的库存
			Goods goods = goodsService.getMapper().selectByPrimaryKey(goodsIds.get(i));
			BigDecimal buyPrice = isVIP ? goods.getMemberSalingPrice() : goods.getSalingPrice();
			//商品总金额
			totalAmout = totalAmout.add(buyPrice.multiply(BigDecimalUtil.parse(counts.get(i))));
		}
		return totalAmout;
	}

	@Override
	public List<Map<String, Object>> listGoodsAssessPage(Integer goodsId, Integer limitStart, Integer limitEnd) {
		return assessMapper.listGoodsAssessPage(goodsId, limitStart, limitEnd);
	}
	@Override
	public int countGoodsAssess(Integer goodsId) {
		return assessMapper.countGoodsAssess(goodsId);
	}

	@Override
	public List<GoodsOrder> selectSecondKillOrder(Integer activityDetailId, Integer userId) {
		return goodsOrderMapper.selectSecondKillOrder(activityDetailId,userId);
	}

	@Override
	public void updateOrderExpress(GoodsOrder goodsOrder) throws Exception {
	    GoodsOrder goodsOrderNew = this.getMapper().selectByPrimaryKeyForUpdate(goodsOrder.getId());
	    
	    if(goodsOrderNew.getState() == GoodsOrderStatusEnum.PAYED.getCode()) {
	        goodsOrderNew.setExpress(goodsOrder.getExpress());
	        goodsOrderNew.setExpressNum(goodsOrder.getExpressNum());
	        goodsOrderNew.setState(GoodsOrderStatusEnum.SENDED.getCode());
	        goodsOrderNew.setDeliveryDate(new Date());
	        goodsOrderNew.setUpdateDate(new Date());
	        this.getMapper().updateByPrimaryKeySelective(goodsOrderNew);
	    } else {
	        throw new Exception("订单状态有误!");
	    }
	    
	    //增加节点
        OrderDone orderDone = new OrderDone();
        orderDone.setOrderNo(goodsOrderNew.getOrderNo());
        orderDone.setOrderStatus(OrderDoneEnum.SHIPPED.getFeatureName());
        orderDone.setUpdateDate(new Date());
        orderDone.setCreateDate(new Date());
        orderDone.setOrderType(OrderTypeEnum.GOODS.getFeatureName());
        if (orderDoneService.insert(orderDone) != 1) {
            throw new LockFailureException();
        }
	}
	
	@Override
    public void doFlashOrder() throws Exception {
	    List<GoodsOrder> goodsOrderList=this.getMapper().selectByExample(new GoodsOrderExample());
	    for(GoodsOrder goodsOrder:goodsOrderList) {
	        List<GoodsOrderDetail> goodsOrderDetailList = goodsOrderDetailService.getDetailsByOrderId(goodsOrder.getId());
	        BigDecimal balanceGoodsAmount = BigDecimal.ZERO;
            BigDecimal cashGoodsAmount = BigDecimal.ZERO;
            BigDecimal creditGoodsAmount = BigDecimal.ZERO;
            BigDecimal hongbaoGoodsAmount = BigDecimal.ZERO;
            for(GoodsOrderDetail orderDetail:goodsOrderDetailList) {

                //详情余额支付=(商品单价*商品数量+各商品总价之和/订单总金额)*订单总余额支付-(各商品总价之和/订单总金额)*订单总余额支付
                //上述公式均取余之后相减 以达到各详情支付金额之和等于订单总支付金额之和的目的
                orderDetail.setBalancePayMoney(
                    balanceGoodsAmount.add(orderDetail.getSalePrice().multiply(BigDecimal.valueOf(orderDetail.getCount())))
                    .multiply(goodsOrder.getBalancePayMoney()).divide(goodsOrder.getTotalMoney(),2,RoundingMode.UP)
                    .subtract(
                        balanceGoodsAmount.multiply(goodsOrder.getBalancePayMoney()).divide(goodsOrder.getTotalMoney(),2,RoundingMode.UP)
                        )
                    );
                balanceGoodsAmount = balanceGoodsAmount.add(orderDetail.getSalePrice().multiply(BigDecimal.valueOf(orderDetail.getCount())));
                //详情现金支付=(商品单价*商品数量+各商品总价之和/订单总金额)*订单总现金支付-(各商品总价之和/订单总金额)*订单总现金支付
                //上述公式均取余之后相减 以达到各详情支付金额之和等于订单总支付金额之和的目的
                orderDetail.setCashPayMoney(
                    cashGoodsAmount.add(orderDetail.getSalePrice().multiply(BigDecimal.valueOf(orderDetail.getCount())))
                    .multiply(goodsOrder.getRealPayMoney()).divide(goodsOrder.getTotalMoney(),2,RoundingMode.UP)
                    .subtract(
                        cashGoodsAmount.multiply(goodsOrder.getRealPayMoney()).divide(goodsOrder.getTotalMoney(),2,RoundingMode.UP)
                        )
                    );
                cashGoodsAmount = cashGoodsAmount.add(orderDetail.getSalePrice().multiply(BigDecimal.valueOf(orderDetail.getCount())));
                //详情授信支付=(商品单价*商品数量+各商品总价之和/订单总金额)*订单总授信支付-(各商品总价之和/订单总金额)*订单总授信支付
                //上述公式均取余之后相减 以达到各详情支付金额之和等于订单总支付金额之和的目的
                orderDetail.setCreditPayMoney(
                    creditGoodsAmount.add(orderDetail.getSalePrice().multiply(BigDecimal.valueOf(orderDetail.getCount())))
                    .multiply(goodsOrder.getCreditPayMoney()).divide(goodsOrder.getTotalMoney(),2,RoundingMode.UP)
                    .subtract(
                        creditGoodsAmount.multiply(goodsOrder.getCreditPayMoney()).divide(goodsOrder.getTotalMoney(),2,RoundingMode.UP)
                        )
                    );
                creditGoodsAmount = creditGoodsAmount.add(orderDetail.getSalePrice().multiply(BigDecimal.valueOf(orderDetail.getCount())));
                //详情红包支付=(商品单价*商品数量+各商品总价之和/订单总金额)*订单总红包支付-(各商品总价之和/订单总金额)*订单总红包支付
                //上述公式均取余之后相减 以达到各详情支付金额之和等于订单总支付金额之和的目的
                orderDetail.setHongbaoMoney(
                    hongbaoGoodsAmount.add(orderDetail.getSalePrice().multiply(BigDecimal.valueOf(orderDetail.getCount())))
                    .multiply(goodsOrder.getHongbaoMoney()).divide(goodsOrder.getTotalMoney(),2,RoundingMode.UP)
                    .subtract(
                        hongbaoGoodsAmount.multiply(goodsOrder.getHongbaoMoney()).divide(goodsOrder.getTotalMoney(),2,RoundingMode.UP)
                        )
                    );
                hongbaoGoodsAmount = hongbaoGoodsAmount.add(orderDetail.getSalePrice().multiply(BigDecimal.valueOf(orderDetail.getCount())));
                
                goodsOrderDetailService.getMapper().updateByPrimaryKey(orderDetail);
            }
           
	    }
    }

    @Override
    public List<Map<String, Object>> listCarriage(String orderNo, String skuCode, Integer orderStatus,
        Date payStartTime, Date payEndTime, Integer limitStart, Integer limitEnd) {
        
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("skuCode", skuCode);
        map.put("orderStatus", orderStatus);
        map.put("payStartTime", payStartTime);
        if (payEndTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(payEndTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("payEndTime", c1.getTime());
        } else {
            map.put("payEndTime", null);
        }
        map.put("limitStart", limitStart);
        map.put("limitEnd", limitEnd);
        
        return goodsOrderMapper.listCarriage(map);
    }

    @Override
    public int countCarriage(String orderNo, String skuCode, Integer orderStatus, Date payStartTime, Date payEndTime) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("skuCode", skuCode);
        map.put("orderStatus", orderStatus);
        map.put("payStartTime", payStartTime);
        if (payEndTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(payEndTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("payEndTime", c1.getTime());
        } else {
            map.put("payEndTime", null);
        }
        
        return goodsOrderMapper.countCarriage(map);
    }
 
}
