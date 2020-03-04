package com.goochou.p2b.module.goods;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.constant.goods.ActivityTypeEnum;
import com.goochou.p2b.constant.redis.RedisConstants;
import com.goochou.p2b.model.vo.bulls.SecondKillDetailVO;
import com.goochou.p2b.service.*;
import com.goochou.p2b.service.impl.RedisService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.OpenApiApp;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.goods.GoodsOrderStatusEnum;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.api.HessianInterface;
import com.goochou.p2b.hessian.transaction.OrderResponse;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderRequest;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserAddress;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderDetail;
import com.goochou.p2b.model.idGenerator.OrderIdGenerator;
import com.goochou.p2b.utils.BigDecimalUtil;

/**
 * 商城购买订单
 */
@Service
public class SubmitGoodsOrderFace implements HessianInterface {

	private static final Logger logger = Logger.getLogger(SubmitGoodsOrderFace.class);

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private UserAddressService userAddressService;
	@Autowired
	private HongbaoService hongbaoService;
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	private OrderIdGenerator goodsOrderIdGenerator;
	@Autowired
	private UserService userService;
	@Autowired
    private GoodsShoppingCartService goodsShoppingCartService;
	@Autowired
	private GoodsOrderDetailService goodsOrderDetailService;
	@Autowired
	private MallActivityService mallActivityService;
	@Autowired
	private RedisService redisService;

	@Override
	public Response execute(ServiceMessage msg) {
		GoodsOrderRequest request = (GoodsOrderRequest) msg.getReq();
		OrderResponse response = new OrderResponse();
		response.setSuccess(false);

		if (null == request.getUserId() || null == request.getGoodsIds() || null == request.getCounts()) {
			response.setSuccess(false);
			response.setErrorMsg("[userId,goodsId,count]为必传参数");
			return response;
		}
		if(request.getGoodsIds().size()!=request.getCounts().size()) {
			response.setSuccess(false);
			response.setErrorMsg("商品与购买数量不匹配");
			return response;
		}
		for(Integer goodsId:request.getGoodsIds()) {
			if(goodsId==null || goodsId<=0) {
				response.setSuccess(false);
				response.setErrorMsg("购买的商品不存在");
				return response;
			}
		}
		for(Integer count:request.getCounts()) {
			if(count==null || count<=0) {
				response.setSuccess(false);
				response.setErrorMsg("购买数量错误");
				return response;
			}
		}
		if (request.getBalancePayMoney() == null) {
			request.setBalancePayMoney(BigDecimal.ZERO);
		}
		if (request.getCreditPayMoney() == null) {
			request.setCreditPayMoney(BigDecimal.ZERO);
		}
		//判断用户
		User user = userService.get(request.getUserId());
		if (null == user) {
			response.setSuccess(false);
			response.setErrorMsg("用户不存在");
			return response;
		}
		//判断收货地址是否是本人的
//        UserAddress userAddress = userAddressService.selectAddressById(request.getAddressId());
		UserAddress userAddress = userAddressService.getAddressesById(request.getAddressId());
        if (userAddress == null) {
            response.setSuccess(false);
            response.setErrorMsg("收货地址不存在");
            return response;
        }
        if (userAddress.getUserId().intValue() != user.getId().intValue()) {
            response.setSuccess(false);
            response.setErrorMsg("收货地址归属不正确");
            return response;
        }
        Hongbao hongbao = null;
        //判断红包是否可用
        if (null != request.getHongbaoId() && 0 != request.getHongbaoId()) {
            hongbao = hongbaoService.get(request.getHongbaoId());
            if (hongbao == null) {
                response.setSuccess(false);
                response.setErrorMsg("红包不存在");
                return response;
            }
            if (hongbao.getUserId().intValue() != user.getId().intValue()) {
                response.setSuccess(false);
                response.setErrorMsg("红包归属错误");
                return response;
            }
            Date currentDate = new Date();
            if (hongbao.getExpireTime().getTime() < currentDate.getTime()) {
                response.setSuccess(false);
                response.setErrorMsg("红包已过期");
                return response;
            }
            if (hongbao.getUseTime() != null) {
                response.setSuccess(false);
                response.setErrorMsg("红包已使用");
                return response;
            }
            if (hongbao.getType() != 3) {
                response.setSuccess(false);
                response.setErrorMsg("红包不支持此类订单");
                return response;
            }
        }

		Assets userAccount = assetsService.findByuserId(request.getUserId());
		if (userAccount == null) {
			response.setSuccess(false);
			response.setErrorMsg("用户账户异常");
			return response;
		}
		if(request.getBalancePayMoney().compareTo(BigDecimal.ZERO)<0) {
			response.setSuccess(false);
			response.setErrorMsg("余额支付不能小于0");
			return response;
		}
		if(request.getCreditPayMoney().compareTo(BigDecimal.ZERO)<0) {
			response.setSuccess(false);
			response.setErrorMsg("冻结余额支付不能小于0");
			return response;
		}


		SecondKillDetailVO secondKillDetailVO = null;
		//是否是秒杀进来的
		if(request.getSecondKill()){
			if(request.getGoodsIds().size()!=1){
				response.setSuccess(false);
				response.setErrorMsg("一次只能秒杀一种商品");
				return response;
			}

			if(request.getCounts().size()==1){
				if(request.getCounts().get(0).intValue()>1) {
					response.setSuccess(false);
					response.setErrorMsg("一次只能秒杀一个商品");
					return response;
				}
			}

			//查询当天此商品的秒杀数据
			//读取缓存
			List<SecondKillDetailVO> secondKillDetailVOS = mallActivityService.getTheSameDaySecondKillDetails(request.getActivityDetailId());
			if (secondKillDetailVOS == null || secondKillDetailVOS.size() == 0) {
				response.setSuccess(false);
				response.setErrorMsg("秒杀活动未开始或者已结束");
				return response;
			}

			secondKillDetailVO = secondKillDetailVOS.get(0);


			if(secondKillDetailVO.getBeginTime().compareTo(new Date())> 0 ||
					secondKillDetailVO.getEndTime().compareTo(new Date())< 0 ){
				response.setSuccess(false);
				response.setErrorMsg("秒杀活动未开始或者已结束");
				return response;
			}


			// 当天秒杀已成功卖出的数量是否超限
			int saledCount = mallActivityService.getSaledCountForActivity(secondKillDetailVO.getId());
			if( saledCount>=secondKillDetailVO.getStockCount()){
				response.setSuccess(false);
				response.setErrorMsg("抢购商品已售罄！谢谢参与！");


			 	Object isOver = redisService.get(String.format(RedisConstants.SECONDKILL_ACTIVITY_SALE_OVER,secondKillDetailVO.getId()));
			 	if(isOver==null){
					redisService.set(String.format(RedisConstants.SECONDKILL_ACTIVITY_SALE_OVER,secondKillDetailVO.getId()),"1",60*60*24);
				}

				return response;
			}

			// 查询用户当天是否超过秒杀限制订单数量
			int buyedCount = mallActivityService.getBuyedCountForUser(secondKillDetailVO.getId(),user.getId());
			if(buyedCount>=secondKillDetailVO.getLimitCount()){
				Object isUnPay	= redisService.get(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_UNPAY,secondKillDetailVO.getId(),user.getId()));
				if(isUnPay!=null) {
					response.setSuccess(false);
					response.setErrorMsg("您有抢购的订单尚未支付，请先完成支付！");
					return response;
				}
				if(buyedCount>=secondKillDetailVO.getLimitCount()) {

					redisService.set(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_CANNOT_BUY,secondKillDetailVO.getId(),user.getId()),"1",60*60*24);

					response.setSuccess(false);
					response.setErrorMsg("抢购商品每人限购"+secondKillDetailVO.getLimitCount()+"个！");
					return response;
				}
			}

			redisService.set(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_UNPAY,secondKillDetailVO.getId(),user.getId()),"1",60*60*24);

			//修改此次的缓存数据
			redisService.incr(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_BUYED_COUNT,secondKillDetailVO.getId(),user.getId()),1);
			redisService.incr(String.format(RedisConstants.SECONDKILL_ACTIVITY_SALED_COUNT,secondKillDetailVO.getId()),1);

			redisService.expire(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_BUYED_COUNT,secondKillDetailVO.getId(),user.getId()),60*60*24);
			redisService.expire(String.format(RedisConstants.SECONDKILL_ACTIVITY_SALED_COUNT,secondKillDetailVO.getId()),60*60*24);
		}

		//初始化订单
		GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setOrderNo(goodsOrderIdGenerator.next());
        goodsOrder.setCreateDate(new Date());
        goodsOrder.setUserId(userAccount.getUserId());
        goodsOrder.setCount(request.getCounts().size());
        goodsOrder.setAddresseeName(userAddress.getName());
        goodsOrder.setAddresseePhone(userAddress.getPhone());
        goodsOrder.setAddresseeDetail(userAddress.getProvinceName()+userAddress.getCityName()+userAddress.getDetail());
        goodsOrder.setClient(request.getClientEnum().getFeatureName());
        Double weight = 0d;
		try {
		    //订单详情
		    List<GoodsOrderDetail> goodsOrderDetails = new ArrayList<>();
            //商品总金额
            BigDecimal totalAmout = BigDecimal.ZERO;
            //使用红包冲抵
            BigDecimal hongbaoAmout = BigDecimal.ZERO;
            //TODO此处需要对商品id排序,防止更新库存时死锁
            for (int i = 0; i < request.getGoodsIds().size(); i++) {
                //当前商品的库存
                Goods goods = goodsService.getMapper().selectByPrimaryKey(request.getGoodsIds().get(i));
                if (null == goods) {
                    response.setSuccess(false);
                    response.setErrorMsg("商品不存在");
                    return response;
                }

                if(!request.getSecondKill()){
                	//判断商品是否在上架状态
					if(goods.getUpDown() != 1){
						response.setSuccess(false);
						response.setErrorMsg("商品" + goods.getGoodsName() + "已下架");
						return response;
					}

					if (goods.getStock() < request.getCounts().get(i)) {
						response.setSuccess(false);
						response.setErrorMsg("商品" + goods.getGoodsName() + "库存不足");
						return response;
					}
				}

                boolean isVIP = user.getLevel() > 0 ? true : false;

				BigDecimal buyPrice = BigDecimal.ZERO;

				//秒杀价格
				if(request.getSecondKill()) {
					buyPrice = isVIP ? secondKillDetailVO.getMemberPrice() : secondKillDetailVO.getPrice();
				}else{
					buyPrice = isVIP ? goods.getMemberSalingPrice() : goods.getSalingPrice();
				}

             // 组装订单详情
    			GoodsOrderDetail orderDetail = goodsOrderDetailService.initDetailNoSave(goods.getId(), request.getCounts().get(i),
    					buyPrice, buyPrice);

				//纪录下商品参与的活动
				if(request.getSecondKill()) {
					orderDetail.setActivityType(ActivityTypeEnum.SECOND_KILL.getCode());
					orderDetail.setActivityDetailId(request.getActivityDetailId());
				}

    			orderDetail.setGoods(goods);
    			//放入集合
    			goodsOrderDetails.add(orderDetail);
    			//商品总金额
                totalAmout = totalAmout.add(buyPrice.multiply(BigDecimalUtil.parse(orderDetail.getCount())));
                weight += goods.getWeight().doubleValue() * request.getCounts().get(i);
            }
            logger.info("<============订单运费开始==============>");
			// 老版本使用市code，新版本使用区县code
			String areaId = StringUtils.isBlank(userAddress.getaId()) ? userAddress.getcId() : userAddress.getaId();
            //使用红包前判断是否包邮及邮费
            Map<String, Object> expressMap = goodsService.calculateExpressFee(weight, totalAmout, areaId);
            BigDecimal expressFee = new BigDecimal(expressMap.get("expressFee")+"");
            BigDecimal realExpressFee = new BigDecimal(expressMap.get("realExpressFee")+"");
            logger.info("<============订单运费结束==============>");
            //红包
            if(hongbao!=null) {
            	if(BigDecimal.valueOf(hongbao.getLimitAmount()).compareTo(totalAmout)>0) {
                	response.setSuccess(false);
        			response.setErrorMsg("红包不满足使用条件");
        			return response;
                }
            	hongbaoAmout=BigDecimal.valueOf(hongbao.getAmount());
            	if(hongbaoAmout.compareTo(totalAmout)>0) {
                	hongbaoAmout=totalAmout;
                }
            	hongbao.setUseTime(new Date());
            	goodsOrder.setHongbaoId(hongbao.getId());
            }
            logger.info("<============totalAmout添加运费前"+totalAmout +"==============>");
            totalAmout = totalAmout.add(expressFee);
            logger.info("<============totalAmout添加运费后"+totalAmout +"==============>");
            goodsOrder.setHongbaoMoney(hongbaoAmout);
            goodsOrder.setTotalMoney(totalAmout);
            goodsOrder.setRealPayMoney(goodsOrder.getTotalMoney().subtract(goodsOrder.getHongbaoMoney()));
            //添加实际运费
            goodsOrder.setRealExpressFee(realExpressFee);
            goodsOrder.setExpressFee(expressFee);
            //自动使用预收利息
			if (request.isAutoUseCredit()) {
				if(goodsOrder.getRealPayMoney().compareTo(BigDecimalUtil.parse(userAccount.getCreditAmount()))>0){
					goodsOrder.setCreditPayMoney(BigDecimalUtil.parse(userAccount.getCreditAmount()));
					goodsOrder.setRealPayMoney(goodsOrder.getRealPayMoney().subtract(goodsOrder.getCreditPayMoney()));
				}else{
					goodsOrder.setCreditPayMoney(goodsOrder.getRealPayMoney());
					goodsOrder.setRealPayMoney(BigDecimal.ZERO);
				}
			}else{
				goodsOrder.setCreditPayMoney(request.getCreditPayMoney());
				goodsOrder.setRealPayMoney(goodsOrder.getRealPayMoney().subtract(goodsOrder.getCreditPayMoney()));

				if(goodsOrder.getRealPayMoney().compareTo(BigDecimal.ZERO)<0){
					response.setSuccess(false);
					response.setErrorMsg("参数有误");
					return response;
				}
			}

			//自动使用余额
			if (request.isAutoUseBalance()) {
				if(goodsOrder.getRealPayMoney().compareTo(BigDecimalUtil.parse(userAccount.getBalanceAmount()))>0){
					goodsOrder.setBalancePayMoney(BigDecimalUtil.parse(userAccount.getBalanceAmount()));
					goodsOrder.setRealPayMoney(goodsOrder.getRealPayMoney().subtract(goodsOrder.getBalancePayMoney()));
				}else{
					goodsOrder.setBalancePayMoney(goodsOrder.getRealPayMoney());
					goodsOrder.setRealPayMoney(BigDecimal.ZERO);
				}
			}else {
				goodsOrder.setBalancePayMoney(request.getBalancePayMoney());
				goodsOrder.setRealPayMoney(goodsOrder.getRealPayMoney().subtract(goodsOrder.getBalancePayMoney()));

				if(goodsOrder.getRealPayMoney().compareTo(BigDecimal.ZERO)<0){
					response.setSuccess(false);
					response.setErrorMsg("参数有误");
					return response;
				}
			}

			//判断余额是否充足
			if (BigDecimalUtil.parse(userAccount.getBalanceAmount()).compareTo(goodsOrder.getBalancePayMoney()) < 0) {
				response.setSuccess(false);
				response.setErrorMsg("可用余额不足");
				return response;
			}
			//判断授信余额是否充足
			if (BigDecimalUtil.parse(userAccount.getCreditAmount()).compareTo(goodsOrder.getCreditPayMoney()) < 0) {
				response.setSuccess(false);
				response.setErrorMsg("可用冻结余额不足");
				return response;
			}


			//余额支付+信用额度支付+红包支付>商品总金额
            if(goodsOrder.getBalancePayMoney().add(goodsOrder.getCreditPayMoney()).add(goodsOrder.getHongbaoMoney()).compareTo(goodsOrder.getTotalMoney())>0) {
            	response.setSuccess(false);
    			response.setErrorMsg("支付的金额超过了订单总价");
    			return response;
            }
            //需付现金=商品总金额-余额支付-信用额度支付-红包支付
            goodsOrder.setState(GoodsOrderStatusEnum.NO_PAY.getCode());

            BigDecimal balanceGoodsAmount = BigDecimal.ZERO;
            BigDecimal cashGoodsAmount = BigDecimal.ZERO;
            BigDecimal creditGoodsAmount = BigDecimal.ZERO;
            BigDecimal hongbaoGoodsAmount = BigDecimal.ZERO;
            for(GoodsOrderDetail orderDetail:goodsOrderDetails) {
            	//商品用户实际支付=商品单价*用户支付/商品总金额
            	orderDetail.setBuyPrice(
            			orderDetail.getSalePrice()
            			.multiply( goodsOrder.getRealPayMoney().add(goodsOrder.getBalancePayMoney()).add(goodsOrder.getCreditPayMoney()) )
            			.divide(goodsOrder.getTotalMoney(),2,RoundingMode.UP) );
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
            }

            //添加订单
            if (goodsOrderService.addSubmitOrder(user, goodsOrder, goodsOrderDetails, hongbao ,userAccount)) {
                //删除购物车对应的商品 TODO 单个购买不删除购物车
//                goodsShoppingCartService.delShoppingCartGoods(user.getId(), request.getGoodsIds().toArray(new Integer[0]));
				goodsShoppingCartService.deleteCacheShoppingCartByUserAndGoods(user.getId(), request.getGoodsIds());
            }


			//是否是秒杀进来的
			if(request.getSecondKill()) {
				if (goodsOrder.getRealPayMoney().compareTo(BigDecimal.ZERO) == 0) {
					redisService.del(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_UNPAY,secondKillDetailVO.getId(),goodsOrder.getUserId()));
				}
			}

            response.setId(goodsOrder.getId());
			response.setOrderNo(goodsOrder.getOrderNo());
			response.setNeedPayMoney(goodsOrder.getRealPayMoney());
            response.setOrderType(OrderTypeEnum.GOODS.getFeatureName());
			response.setSuccess(true);

		} catch (Exception e) {

			if(request.getSecondKill()) {

				redisService.del(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_UNPAY,secondKillDetailVO.getId(),goodsOrder.getUserId()));

				redisService.decr(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_BUYED_COUNT, secondKillDetailVO.getId(), user.getId()), 1);
				redisService.decr(String.format(RedisConstants.SECONDKILL_ACTIVITY_SALED_COUNT, secondKillDetailVO.getId()), 1);
			}

			e.printStackTrace();
			OpenApiApp.EXCEPTION.exception(msg, e);
			response.setErrorMsg(e.getMessage());
		}
		return response;
	}

	@Override
	public void before(ServiceMessage msg) {
	}

	@Override
	public void after(ServiceMessage msg) {
	}
}
