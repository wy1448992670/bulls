package com.goochou.p2b.app.controller;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.goochou.p2b.model.vo.bulls.SecondKillDetailVO;
import com.goochou.p2b.service.*;
import com.goochou.p2b.utils.alipay.AlipayConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fuiou.util.MD5;
import com.goochou.p2b.annotationin.NeedLogin;
import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.ProjectTypeEnum;
import com.goochou.p2b.constant.TimeSearchEnum;
import com.goochou.p2b.constant.TradeMessageStatusEnum;
import com.goochou.p2b.constant.TradeMessageTypeEnum;
import com.goochou.p2b.constant.WithdrawTempStatusEnum;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.goods.GoodsOrderStatusEnum;
import com.goochou.p2b.constant.pasture.InvestPayStateEnum;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.constant.pasture.OrderStateEnum;
import com.goochou.p2b.constant.pay.BaseOutPay;
import com.goochou.p2b.constant.pay.BasePayBack;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.constant.pay.PayConstants;
import com.goochou.p2b.dao.ProjectViewMapper;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.OpenApiClient;
import com.goochou.p2b.hessian.openapi.pay.AllinPayRequest;
import com.goochou.p2b.hessian.openapi.pay.AllinPayResponse;
import com.goochou.p2b.hessian.openapi.pay.FuiouCreatePayRequest;
import com.goochou.p2b.hessian.openapi.pay.FuiouDataResponse;
import com.goochou.p2b.hessian.openapi.pay.FuiouPayRequest;
import com.goochou.p2b.hessian.openapi.pay.FuiouSendMessageRequest;
import com.goochou.p2b.hessian.openapi.pay.QueryYeePayResponse;
import com.goochou.p2b.hessian.openapi.pay.YeePayRequest;
import com.goochou.p2b.hessian.openapi.pay.YeePayResponse;
import com.goochou.p2b.hessian.transaction.PayChannelResponse;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Bank;
import com.goochou.p2b.model.BankCard;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.Project;
import com.goochou.p2b.model.ProjectExample;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.TradeMessageLog;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserAddress;
import com.goochou.p2b.model.Withdraw;
import com.goochou.p2b.model.WithdrawCoupon;
import com.goochou.p2b.model.WithdrawTemp;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.idGenerator.OrderIdGenerator;
import com.goochou.p2b.model.pay.AllinGateWayPay;
import com.goochou.p2b.model.pay.AllinPayOutPay;
import com.goochou.p2b.model.pay.YeeOutBack;
import com.goochou.p2b.model.pay.YeeOutPay;
import com.goochou.p2b.model.pay.allinpay.AIPGSignature;
import com.goochou.p2b.model.pay.allinpay.xstruct.trans.breq.Body;
import com.goochou.p2b.model.pay.fuiou.OrderRespData;
import com.goochou.p2b.model.vo.ProjectView;
import com.goochou.p2b.model.vo.ProjectViewExample;
import com.goochou.p2b.model.vo.TradeMessageLogVO;
import com.goochou.p2b.model.vo.TransactionRecordDetailVO;
import com.goochou.p2b.model.vo.TransactionRecordVO;
import com.goochou.p2b.model.vo.WithdrawRecordVO;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.CommonUtil;
import com.goochou.p2b.utils.CommonUtils;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateTimeUtil;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.Money;
import com.goochou.p2b.utils.StringUtil;
import com.goochou.p2b.utils.http.HttpsUtil;
import com.goochou.p2b.utils.weixin.MyConfig;
import com.goochou.p2b.utils.weixin.WXPay;
import com.goochou.p2b.utils.weixin.WXPayUtil;

import com.yeepay.g3.sdk.yop.encrypt.DigitalEnvelopeDTO;
import com.yeepay.g3.sdk.yop.utils.DigitalEnvelopeUtils;

import static com.goochou.p2b.constant.pay.ChannelConstants.*;


@Controller
@RequestMapping(value = "/assets")
@Api(value = "资金相关-Assets")
public class AssetsManagerController extends BaseController {
    private static final Logger logger = Logger.getLogger(AssetsManagerController.class);
    @Resource
    private UserInviteService userInviteService;
    @Resource
    private RechargeService rechargeService;
    @Resource
    private BankService bankService;
    @Resource
    private BankCardService bankCardService;
    @Resource
    private AssetsService assetsService;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private WithdrawService withdrawService;
    @Resource
    private TradeRecordService tradeRecordService;
    @Resource
    private PayTunnelService payTunnelService;
    @Resource
    private ActivityService activityService;
    @Resource
    private WithdrawBlackService withdrawBlackService;
    @Autowired
    private MallActivityService mallActivityService;
    @Resource
    private AppVersionService appVersionService;
    @Resource
    private InterestService interestService;
    @Resource
    private UserSignedService userSignedService;
    @Resource
    private RateCouponService rateCouponService;
    @Resource
    private ProductService productService;
    @Resource
    private TradeMessageLogService tradeMessageLogService;

    @Resource
    private WithdrawCouponService withdrawCouponService;
    @Resource
    private OrderIdGenerator withdrawOrderIdGenerator;
    @Resource
    private OrderIdGenerator rechargeOrderIdGenerator;

    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private GoodsOrderService goodsOrderService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private ProjectService projectService;
    @Resource
    private ProjectViewMapper projectViewMapper;
    @Resource
    private UserAddressService userAddressService;
    
    /**
     * 充值接口第一步
     * @param session
     * @param request
     * @param token
     * @param appVersion
     * @param pwd
     * @param isOk
     * @param orderType
     * @param orderNo
     * @param client
     * @return
     */
    @RequestMapping(value = "/toRecharge", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "富有支付-创建支付单")
    public AppResult toRecharge(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                @ApiParam("用户token") @RequestParam String token,
                                @ApiParam("App版本号") @RequestParam String appVersion,
                                @ApiParam("支付密码") @RequestParam(required = false) String pwd,
                                @ApiParam("手势密码是否正确") @RequestParam(required = false) String isOk,
                                @ApiParam("OrderTypeEnum") @RequestParam String orderType,
                                @ApiParam("订单号") @RequestParam(required = false) String orderNo,
                                @ApiParam("充值金额") @RequestParam(required = false) Double rechargeAmount,
                                @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam String client,
                                @ApiParam("银行卡ID") @RequestParam String bankCardId) {
        long start = System.currentTimeMillis();
        
        if(!orderType.equals(OrderTypeEnum.RECHARGE.getFeatureName())) {
        	if(StringUtils.isBlank(orderNo)) {
        		return new AppResult(FAILED, "订单不存在");
        	}
        	if(rechargeAmount!=null) {
        		return new AppResult(FAILED, "金额错误");
        	}
        }
        if(orderType.equals(OrderTypeEnum.RECHARGE.getFeatureName())) {
        	if(!StringUtils.isBlank(orderNo)) {
        		return new AppResult(FAILED, "订单不存在");
        	}
        	if(rechargeAmount==null) {
        		return new AppResult(FAILED, "金额错误");
        	}
        }
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        //查询订单
        Integer otherId = null;
        double amount = 0d;
        boolean isPaying=true;//是否支付中
        boolean needPay=false;//是否需要支付
        Integer investmentType=0;
        String createDate = null;
        String subject = "";
        if (orderType.equals(OrderTypeEnum.INVESTMENT.getFeatureName())) {
            Investment investment = investmentService.findByOrderNo(orderNo);
            if (null == investment) {
                return new AppResult(FAILED, "订单不存在");
            }
            if (!investment.getUserId().equals(user.getId()) ) {
                return new AppResult(FAILED, "订单不存在");
            }
            otherId = investment.getId();
            amount = investment.getRemainAmount().doubleValue();
            isPaying=investment.getPayStatus()==InvestPayStateEnum.PAYING.getCode();
            needPay=investment.getPayStatus()==InvestPayStateEnum.NO_PAY.getCode() && investment.getOrderStatus()==InvestmentStateEnum.no_buy.getCode();
            investmentType=investment.getType();
            createDate = DateUtil.dateFullTimeFormat.format(investment.getCreateDate());
            Project project = projectService.get(investment.getProjectId());
            subject = project.getTitle();
        } else if (orderType.equals(OrderTypeEnum.GOODS.getFeatureName())) {
            GoodsOrder goodsOrder = goodsOrderService.findByOrderNum(orderNo);
            if (null == goodsOrder) {
                return new AppResult(FAILED, "订单不存在");
            }
            if (!goodsOrder.getUserId().equals(user.getId())) {
                return new AppResult(FAILED, "订单不存在");
            }
            otherId = goodsOrder.getId();
            amount = goodsOrder.getRealPayMoney().doubleValue();
            isPaying=goodsOrder.getState()==GoodsOrderStatusEnum.PAYING.getCode();
            needPay=goodsOrder.getState()==GoodsOrderStatusEnum.NO_PAY.getCode();
            createDate = DateUtil.dateFullTimeFormat.format(goodsOrder.getCreateDate());
            subject = "商城牛肉品种";
        } else if (orderType.equals(OrderTypeEnum.RECHARGE.getFeatureName())) {

        	otherId=null;
            amount = rechargeAmount;
            isPaying=false;
            needPay=true;
            createDate = DateUtil.dateFullTimeFormat.format(new Date());
            subject = "商城牛肉品种";
        } else {
            return new AppResult(FAILED, "订单类型异常");
        }
        try {
            long lockTime = 0;
            if (null != createDate) {
                String date = DateUtil.addtime(createDate, getCacheKeyValue(DictConstants.PAY_WAIT_TIME));
                lockTime = DateUtil.dateDiffSecound(DateUtil.format(new Date(), DateTimeUtil.allPattern), date,
                    DateTimeUtil.allPattern);
            }
            if(lockTime <= 0) {
                return new AppResult(FAILED, "订单超时，已无法发起支付！");
            }
            logger.info("==============" + user.getUsername() + "/////" + user.getTrueName() + "进入支付================");
            Recharge recharge = new Recharge();
            final String orderno = rechargeOrderIdGenerator.next(); //生成订单号
            // 保存充值信息
            Date d = new Date();
            recharge.setUserId(user.getId());
            recharge.setAmount(amount);
            recharge.setClient(client);
            recharge.setOrderNo(orderno);
            recharge.setCreateDate(d);
            recharge.setUpdateDate(d);
            recharge.setStatus(1); // 默认处理中
            recharge.setOrderType(orderType);
            recharge.setOtherId(otherId);
            Map<String, Object> resultMap = new HashMap<>();
          //判断是否是二次支付，二次支付需要查询上一笔是否支付成功，如果上笔在支付中则不允许再次发起支付
            if(isPaying) {
            	List<Recharge> rechargeList = rechargeService.getPayingRechargeByOrderTypeAndNo(orderType, otherId);
				if (rechargeList.size() != 1) {
					return new AppResult(FAILED, "otherId="+otherId+";"+rechargeList.size()+"笔支付中数据异常，不允许操作");
				}else {
					//尝试完成支付
					rechargeService.doTryCompletePayingRecharge(rechargeList.get(0));
					//如果尝试完成过支付,重新加载investment/goodsOrder
					if (orderType.equals(OrderTypeEnum.INVESTMENT.getFeatureName())) {
			        	Investment investment = investmentService.findByOrderNo(orderNo);
			            needPay=investment.getPayStatus()==InvestPayStateEnum.NO_PAY.getCode() && investment.getOrderStatus()==InvestmentStateEnum.no_buy.getCode();
			        } else if (orderType.equals(OrderTypeEnum.GOODS.getFeatureName())) {
			        	GoodsOrder goodsOrder = goodsOrderService.findByOrderNum(orderNo);
			            needPay=goodsOrder.getState()==GoodsOrderStatusEnum.NO_PAY.getCode();
			        }
				}
            }
            if(!needPay) {
            	//不开乐观锁,因为更新investment或goodsOrder时,附带条件CAS更新
            	return new AppResult(FAILED, "otherId="+otherId+"; 不可支付，不允许操作");
            }
            
            
            String payResult = ClientConstants.H5_URL+"payResult.html?token="+token+"&appVersion="+appVersion+"&client="+client+"&orderNo="+recharge.getOrderNo()
            				+"&orderType="+recharge.getOrderType()+"&id="+recharge.getOtherId()+"&investmentType="+investmentType;

            //这里因为配置的时候code weixin，alipay，bankCardId在同一级
            if(bankCardId.equals(OutPayEnum.ALPAY.getFeatureName())) {
                logger.info("进入支付宝支付..");
                                recharge.setPayChannel(OutPayEnum.ALPAY.getFeatureName());
                rechargeService.save(recharge);
                resultMap.put("nolink", "1");
                resultMap.put("payResult", payResult);
                resultMap.put("url", "https://app.bfmuchang.com/user/alipayJump?orderNo="+recharge.getOrderNo()+"&payResult="+URLEncoder.encode(payResult)+"&subject="+URLEncoder.encode(subject)+"");
                return new AppResult(SUCCESS, resultMap);
            }else if(bankCardId.equals(OutPayEnum.WXPAY.getFeatureName())) {
                logger.info("进入微信支付..");
                                recharge.setPayChannel(OutPayEnum.WXPAY.getFeatureName());
                rechargeService.save(recharge);
                MyConfig config = new MyConfig();
                logger.info(config.getAppID()+";"+config.getKey()+";"+config.getMchID());
                WXPay wxpay = new WXPay(config, false, false);
                Map<String, String> data = new HashMap<String, String>();
                data.put("body", subject);
                data.put("out_trade_no", recharge.getOrderNo());
                data.put("device_info", "");
                data.put("fee_type", "CNY");
                data.put("total_fee", String.valueOf(new Money(recharge.getAmount()).getCent()));
                data.put("spbill_create_ip", getIpAddr(request));
                data.put("notify_url", PayConstants.ADVITE_URL.replace("/backRecharge", "/wxpay/notify"));
                data.put("trade_type", "MWEB");  // 此处指定为扫码支付
                data.put("product_id", recharge.getOtherId()+"");
                data.put("scene_info", "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"https://wap.bfmuchang.com\",\"wap_name\": \"奔富牧业精选商城\"}} ");
                logger.info(JSONArray.toJSONString(data));
                try {
                    Map<String, String> resp = wxpay.unifiedOrder(data);
                    logger.info("resp>>>>"+resp);
                    if(resp.get("return_code").equals("SUCCESS") && resp.get("result_code").equals("SUCCESS")) {
                        if(client.equals(ClientEnum.WAP.getFeatureName())) {
                            resultMap.put("url", resp.get("mweb_url")+"&redirect_url="+URLEncoder.encode(payResult));
                        }else {
                            //resultMap.put("url", resp.get("mweb_url"));
                            logger.info("wx开始=====");
                            String wxUrl = HttpsUtil.doWx302JumpUrl(resp.get("mweb_url"));
                            logger.info("wxUrl=========="+wxUrl);
                            resultMap.put("url", "https://wap.bfmuchang.com/wxLink.html?url="+URLEncoder.encode(wxUrl+"&redirect_url="+payResult));
//                            resultMap.put("url", "https://"+ClientConstants.APP_ROOT+"/wxJump?url="+URLEncoder.encode(resp.get("mweb_url")));
                        }
                        resultMap.put("nolink", "1");
                        resultMap.put("payResult", payResult);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new AppResult(SUCCESS, resultMap);
            }else if(bankCardId.equals(OutPayEnum.YEEPAY.getFeatureName())) {
                logger.info("进入易宝支付..");
                                recharge.setPayChannel(OutPayEnum.YEEPAY.getFeatureName());
                rechargeService.save(recharge);
                //生成URL
                long startapi = System.currentTimeMillis();
                YeePayResponse result = new YeePayResponse();
                YeePayRequest req = new YeePayRequest(
                    recharge.getOrderNo(),
                    String.valueOf(recharge.getAmount()),
                    recharge.getUserId() + "",
                    payResult,
                    subject,
                    user.getPhone(),
                    user.getTrueName(),
                    user.getIdentityCard());
                ServiceMessage msg = new ServiceMessage("yeepay.create.pay", req);
                result = (YeePayResponse) OpenApiClient.getInstance().setServiceMessage(msg).send();
                if (result != null && result.isSuccess()) {
                    long endapi = System.currentTimeMillis();
                    logger.info((endapi-startapi)+"api ms");
                    rechargeService.update(recharge,1);
                    if(client.equals(ClientEnum.WAP.getFeatureName())) {
                        resultMap.put("url", HttpsUtil.do302JumpUrl(result.getUrl()));
                    }else {
                        resultMap.put("url", result.getUrl());
                    }
                    resultMap.put("nolink", "1");
                    resultMap.put("payResult", payResult);
                    long end = System.currentTimeMillis();
                    logger.info((end-start)+"ms");
                    return new AppResult(SUCCESS, resultMap);
                }
                return new AppResult(SUCCESS, resultMap);
            }else {
                logger.info("进入快捷支付..");
                BankCard bc = bankCardService.get(Integer.valueOf(bankCardId));
                if (null == bc || bc.getUserId()==null || !bc.getUserId().equals(user.getId()) ) {
                    return new AppResult(FAILED, "请先绑卡");
                }
                double dailyAmount = rechargeService.getRechargeAmountByExpress(user.getId(), DateFormatTools.DATEFORMAT_SQLDAYS, bc.getCardNumber());    //当天充值金额
                double monthAmount = rechargeService.getRechargeAmountByExpress(user.getId(), DateFormatTools.DATEFORMAT_SQLMONTH, bc.getCardNumber());  //当月充值金额
                double singleMaxAmount = bc.getBank().getBindSingleMaxAmount();  //单笔最大额度
                double dailyMaxAmount = BigDecimalUtil.sub(bc.getBank().getBindDailyMaxAmount(), dailyAmount); //每日最大额度
                double monthMaxAmount = BigDecimalUtil.sub(bc.getBank().getMonthMaxAmount(), monthAmount); //每月最大额度
                double singleMinAmount = bc.getBank().getBindSingleMinAmount();
                double minAmount = singleMaxAmount;
                if (singleMaxAmount > dailyMaxAmount) {
                    minAmount = dailyMaxAmount;
                }
                if (minAmount > monthMaxAmount) {
                    minAmount = monthMaxAmount;
                }
                if (minAmount < 0) {
                    minAmount = 0;
                }
                if (amount > singleMaxAmount) {
                    return new AppResult(FAILED, "单笔支付金额超限，本次最多支付" + minAmount + "元。请选择收银台(大额)支付");
                }
                if (amount > dailyMaxAmount) {
                    return new AppResult(FAILED, "单日支付金额超限，本次最多支付" + minAmount + "元。请选择收银台(大额)支付");
                }
                if (amount > monthMaxAmount) {
                    return new AppResult(FAILED, "单月支付金额超限，本次最多支付" + minAmount + "元。请选择收银台(大额)支付");
                }
                if(amount < singleMinAmount ) {
                	return new AppResult(FAILED, "最少支付" + singleMinAmount + "元");
                }
                recharge.setBankId(bc.getBank().getId());
                recharge.setCardNo(bc.getCardNumber());
             // 获取可用的充值通道,权重降序排列
//                List<PayTunnel> list = payTunnelService.listUsable(0);
//                String tunnelName = list.get(0).getName();

                if (bc.getBank().getPayChannel().equals(OutPayEnum.FUIOU_QUICK.getFeatureName())) {
                    recharge.setPayChannel(OutPayEnum.FUIOU_QUICK.getFeatureName()); // TODO sq 枚举
                    rechargeService.save(recharge);
                    if(recharge.getOrderType().equals(OrderTypeEnum.RECHARGE.getFeatureName())) {
                    	recharge.setOtherId(recharge.getId());
                    	rechargeService.update(recharge);
                    }
//                    OrderRespData data = FuyouUtil.toRecharge(recharge, user, bc, getIpAddr(request));
//                    if (FuiouConstants.RESP_CODE_SUCCESS.equals(data.getResponsecode())) {
                    long startapi = System.currentTimeMillis();
                    FuiouDataResponse result = new FuiouDataResponse();
                    FuiouCreatePayRequest req = new FuiouCreatePayRequest(
                            recharge.getUserId() + "",
                            new Money(recharge.getAmount()).getCent(),
                            recharge.getCardNo(),
                            user.getTrueName(),
                            user.getIdentityCard(),
                            bc.getPhone(),
                            getIpAddr(request),
                            recharge.getOrderNo());
                    ServiceMessage msg = new ServiceMessage("fuiou.create.pay", req);
                    result = (FuiouDataResponse) OpenApiClient.getInstance().setServiceMessage(msg).send();
                    if (result != null && result.isSuccess()) {
                        long endapi = System.currentTimeMillis();
                        logger.info((endapi-startapi)+"api ms");
                        recharge.setOutOrderNo(result.getOrderRespData().getOrderid()); //更新富有订单号
                        rechargeService.update(recharge,1);
                        session.setAttribute("rechargeData", result.getOrderRespData());
                        session.setAttribute("recharge", recharge);
                        session.setAttribute("lastSendTime_pay", new Date());
                        resultMap.put("path", ClientConstants.ALIBABA_PATH + "upload/images/bank-icon/bank"+bc.getBankId()+"@2x.png");
                        resultMap.put("mobile", CommonUtils.getPhone(bc.getPhone()));
                        resultMap.put("orderNo", orderno);
                        long end = System.currentTimeMillis();
                        logger.info((end-start)+"ms");
                        return new AppResult(SUCCESS, resultMap);
                    }
                    return new AppResult(FAILED, result.getErrorMsg());
                }else if(bc.getBank().getPayChannel().equals(OutPayEnum.ALLINPAY.getFeatureName())) {
                    recharge.setPayChannel(OutPayEnum.ALLINPAY.getFeatureName()); // TODO sq 枚举
                    rechargeService.save(recharge);
                    if(recharge.getOrderType().equals(OrderTypeEnum.RECHARGE.getFeatureName())) {
                    	recharge.setOtherId(recharge.getId());
                    	rechargeService.update(recharge);
                    }
                    long startapi = System.currentTimeMillis();
                    AllinPayRequest pay = new AllinPayRequest();
                    pay.setAgreeId(bc.getProtoColNo());
                    pay.setAmount(new Money(recharge.getAmount()).getCent());
                    pay.setOrderNo(recharge.getOrderNo());
                    pay.setSubject(subject);
                    ServiceMessage msg = new ServiceMessage("allinpay.create.pay", pay);
                    AllinPayResponse result = (AllinPayResponse) OpenApiClient.getInstance()
                            .setServiceMessage(msg).send();
                    if (result != null && result.isSuccess()) {
                        long endapi = System.currentTimeMillis();
                        logger.info((endapi-startapi)+"api ms");
                        rechargeService.update(recharge,1);
                        session.setAttribute("thpInfo", result.getThpInfo());
                        session.setAttribute("recharge", recharge);
                        session.setAttribute("lastSendTime_pay", new Date());
                        resultMap.put("path", ClientConstants.ALIBABA_PATH + "upload/images/bank-icon/bank"+bc.getBankId()+"@2x.png");
                        resultMap.put("mobile", CommonUtils.getPhone(bc.getPhone()));
                        resultMap.put("orderNo", orderno);
                        long end = System.currentTimeMillis();
                        logger.info((end-start)+"ms");
                        return new AppResult(SUCCESS, resultMap);
                    }
                    return new AppResult(FAILED, result.getErrorMsg());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);

            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
        return null;
    }

    /**
     * 富友充值
     *
     * @param session
     * @param request
     * @param token
     * @param appVersion
     * @param pwd
     * @param isOk
     * @param amount
     * @param client
     * @param verifyCode
     * @return
     */
    @RequestMapping(value = "/recharge/fuiou", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "富有支付-短信确认")
    public AppResult recharge(HttpSession session, HttpServletRequest request,
                              @ApiParam("用户token") @RequestParam String token,
                              @ApiParam("App版本号") @RequestParam String appVersion,
                              @ApiParam("支付密码") @RequestParam(required = false) String pwd,
                              @ApiParam("手势密码是否正确") @RequestParam(required = false) String isOk,
                              @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam String client,
                              @ApiParam("验证码") @RequestParam String verifyCode,
                              @ApiParam("跳转页面Key值") @RequestParam(required = false) String pageKey) {
        long start = System.currentTimeMillis();
        OrderRespData data = (OrderRespData) session.getAttribute("rechargeData");
        String thpInfo = (String)session.getAttribute("thpInfo");
        Recharge recharge = (Recharge) session.getAttribute("recharge");
        if (recharge == null) {
            return new AppResult(FAILED, "验证码失效，请重新获取验证码");
        }
        if (StringUtils.isBlank(verifyCode)) {
            return new AppResult(FAILED, "请先获取验证码");
        }

        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        try {
            BankCard bc = null;
            /*List<BankCard> bankCards = bankCardService.getByUserId(user.getId());
            if (bankCards.size() > 0) {
                bc = bankCards.get(0);
            }*/
            
            List<BankCard> bankCards = bankCardService.getByCardnumber(recharge.getCardNo());
            if (bankCards.size() > 0) {
                bc = bankCards.get(0);
            }
            if (null == bc) {
                return new AppResult(FAILED, "请先绑卡");
            }
            logger.info("支付BankId: " + bc.getCardNumber());;
            //TODO是否需要标记recharge已提交请求
            TradeMessageLog tradeMessageLog = tradeMessageLogService.getTradeMessageLog(recharge.getOutOrderNo(), TradeMessageTypeEnum.SEND.getFeatureName());
            if (null == tradeMessageLog) {
                //发送请求前保存充值单，增加报文日志
                tradeMessageLog = new TradeMessageLog();
                TradeMessageLog.voluationTradeMessageLog(tradeMessageLog, PayConstants.FUIOU_API + PayConstants.METHOD_PAY,
                        recharge.getPayChannel(), recharge.getPayChannel(),
                        TradeMessageTypeEnum.SEND.getFeatureName(), String.valueOf(recharge.getUserId()),
                        recharge.getOrderNo(), recharge.getOutOrderNo(), null, null, "true",
                        TradeMessageStatusEnum.YIWANCHENG.getFeatureName());
                //记录发送充值报文
                if (!tradeMessageLogService.saveTradeMessageLog(tradeMessageLog)) {
                    return new AppResult(FAILED, "交易记录创建失败！");
                }
            }
           
            Integer investmentType=0;
            if (recharge.getOrderType().equals(OrderTypeEnum.INVESTMENT.getFeatureName())) {
                Investment investment = investmentService.get(recharge.getOtherId());
                if (null == investment) {
                    return new AppResult(FAILED, "订单不存在");
                }
                if (!investment.getUserId().equals(user.getId()) ) {
                    return new AppResult(FAILED, "订单不存在");
                }
                investmentType=investment.getType();
            }
            String payResult = ClientConstants.H5_URL+"payResult.html?token="+token+"&appVersion="+appVersion+"&client="+client+"&orderNo="+recharge.getOrderNo()
			+"&orderType="+recharge.getOrderType()+"&id="+recharge.getOtherId()+"&investmentType="+investmentType;
            
            long startapi = System.currentTimeMillis();
            if(recharge.getPayChannel().equals(OutPayEnum.FUIOU_QUICK.getFeatureName())) {
                Response result = new Response();
                FuiouPayRequest req = new FuiouPayRequest(
                        recharge.getCardNo(),
                        recharge.getOrderNo(),
                        data.getOrderid(),
                        data.getSignpay(),
                        data.getUserid(),
                        verifyCode,
                        getIpAddr(request),
                        bc.getPhone());
                ServiceMessage msg = new ServiceMessage("fuiou.pay", req);
                result = OpenApiClient.getInstance().setServiceMessage(msg).send();
                if (result.isSuccess()) {
                    long endapi = System.currentTimeMillis();
                    logger.info((endapi-startapi)+"api ms");
                    session.removeAttribute("rechargeData");
                    session.removeAttribute("recharge");
                    Map<String, Object> map = new HashMap<>();
                    long end = System.currentTimeMillis();
                    logger.info((end-start)+"ms");
                    map.put("payResult", payResult);
                    return new AppResult(SUCCESS, map);
                }
            }else if(recharge.getPayChannel().equals(OutPayEnum.ALLINPAY.getFeatureName())) {
                AllinPayRequest pay = new AllinPayRequest();
                System.out.println("协议号: " + bc.getProtoColNo());
                pay.setAgreeId(bc.getProtoColNo());
                pay.setCode(verifyCode);
                pay.setOrderNo(recharge.getOrderNo());
                if(null != thpInfo) {
                    pay.setThpInfo(thpInfo);
                }
                ServiceMessage msg = new ServiceMessage("allinpay.pay", pay);
                AllinPayResponse result = (AllinPayResponse) OpenApiClient.getInstance()
                        .setServiceMessage(msg).send();
                if (result.isSuccess()) {
                    long endapi = System.currentTimeMillis();
                    logger.info((endapi-startapi)+"api ms");
                    session.removeAttribute("thpInfo");
                    session.removeAttribute("recharge");
                    Map<String, Object> map = new HashMap<>();
                    long end = System.currentTimeMillis();
                    logger.info((end-start)+"ms");
                    map.put("payResult", payResult);
                    logger.info("result.getTrxStatus()="+result.getTrxStatus());
                    if(result.getTrxStatus().equals(PayConstants.RESP_CODE_SUCCESS) || result.getTrxStatus().equals("2000") ) {
                        return new AppResult(SUCCESS, map);
                    }else{
                        return new AppResult(FAILED, result.getErrorMsg());
                    }
                }else {
                    return new AppResult(FAILED, result.getErrorMsg());
                }
            }
            return new AppResult(FAILED, "支付失败请稍后再试！");
        } catch (Exception e) {
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * 富友重新发送短信验证码
     *
     * @param session
     * @param request
     * @param token
     * @param appVersion
     * @param client
     * @return
     */
    @RequestMapping(value = "/recharge/fuiou/resend", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户充值--富有充值--重新发送短信验证码")
    public AppResult recharge(HttpSession session, HttpServletRequest request,
                              @ApiParam("用户token") @RequestParam String token,
                              @ApiParam("App版本号") @RequestParam String appVersion,
                              @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam String client) {
        Recharge recharge = (Recharge) session.getAttribute("recharge");
        if (recharge == null) {
            return new AppResult(FAILED, "支付信息失效，请重新下单");
        }

        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        if(null != session.getAttribute("lastSendTime_pay")) {
            long diffTime = System.currentTimeMillis() - ((Date) session.getAttribute("lastSendTime_pay")).getTime();
            if (diffTime <= Constants.ALLOW_SECOUND * 1000) {
                int secound = Constants.ALLOW_SECOUND-Integer.valueOf((diffTime/1000)+"");
                return new AppResult(FAILED, "请一分钟后再发送验证码", secound);
            }
        }
        try {
            BankCard bc = null;
            /*List<BankCard> bankCards = bankCardService.getByUserId(user.getId());
            if (bankCards.size() > 0) {
                bc = bankCards.get(0);
            }*/
            List<BankCard> bankCards = bankCardService.getByCardnumber(recharge.getCardNo());
            if (bankCards.size() > 0) {
                bc = bankCards.get(0);
            }
            if (null == bc) {
                return new AppResult(FAILED, "请先绑卡");
            }
            System.out.println("卡信息: " + bc.toString());
            if(recharge.getPayChannel().equals(OutPayEnum.FUIOU_QUICK.getFeatureName())) {
                FuiouDataResponse result = new FuiouDataResponse();
                FuiouSendMessageRequest req = new FuiouSendMessageRequest(
                        recharge.getUserId() + "",
                        new Money(recharge.getAmount()).getCent(),
                        recharge.getCardNo(),
                        user.getTrueName(),
                        user.getIdentityCard(),
                        bc.getPhone(),
                        getIpAddr(request),
                        recharge.getOutOrderNo());
                ServiceMessage msg = new ServiceMessage("fuiou.send", req);
                result = (FuiouDataResponse) OpenApiClient.getInstance().setServiceMessage(msg).send();
                if (result.isSuccess()) {
                    JSONObject json = new JSONObject();
                    return new AppResult(SUCCESS,json);
                }
            }else if(recharge.getPayChannel().equals(OutPayEnum.ALLINPAY.getFeatureName())) {
                String thpInfo = (String)session.getAttribute("thpInfo");
                System.out.println("协议号: " + bc.getProtoColNo());
                AllinPayRequest pay = new AllinPayRequest();
                pay.setOrderNo(recharge.getOrderNo());
                pay.setAgreeId(bc.getProtoColNo());
                pay.setThpInfo(thpInfo);
                ServiceMessage msg = new ServiceMessage("allinpay.send", pay);
                AllinPayResponse result = (AllinPayResponse) OpenApiClient.getInstance()
                        .setServiceMessage(msg).send();
                if (result.isSuccess()) {
                    JSONObject json = new JSONObject();
                    return new AppResult(SUCCESS,json);
                }
            }
            return new AppResult(FAILED, "重发短信失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * 富有支付：接受提现通知
     */
    @RequestMapping(value = "/withdraw/fuiou/notify", method = RequestMethod.POST)
    public void withdrawFuiouNotify(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        logger.info("==============富有提现通知接收开始==================");
        try {
            out = response.getWriter();
            String orderNo = request.getParameter("orderNo");
            String merdt = request.getParameter("merdt");
            String accntno = request.getParameter("accntno");
            String amt = request.getParameter("amt");
            String mac = request.getParameter("mac");
            String state = request.getParameter("state");
            String signPain = new StringBuffer().append(PayConstants.WITHDRAW_MCHNT_CD).append("|").append(PayConstants.WITHDRAW_MCHNT_KEY).append("|")
                    .append(orderNo).append("|").append(merdt).append("|").append(accntno).append("|").append(amt).toString();
            if (MD5.MD5Encode(signPain).equals(mac)) {
                Withdraw withdraw = withdrawService.getByOrderNo(orderNo);
                if (withdraw == null) {
                    logger.info("============找不到该笔提现订单=============");
                    out.print("-1");
                    return;
                }
                if (withdraw.getStatus().equals(1)) {
                    logger.info("==============富有通知该次提现已经成功，无需通知==============");
                    out.print("-1");
                    return;
                }
                //提现成功
                if (state.equals("1")) {
                    withdraw.setStatus(1);
                    withdraw.setTechRemark("success");
                } else {
                    withdraw.setStatus(2);
                    withdraw.setTechRemark("failed");
                    //返还提现券
                    WithdrawCoupon wc = withdrawCouponService.getByWithdrawId(withdraw.getId());
                    if (wc != null) {
                        withdrawCouponService.addToUser(wc.getUserId(), wc.getTitle(), wc.getDescript(), wc.getSource(), null, DateFormatTools.dayToDaySubtractWithoutSeconds(wc.getUseTime(), wc.getExpireTime()));
                    }
                }
                try {
                    withdrawService.audit(withdraw, 1, false);// 灵活宝1表示
                } catch (LockFailureException e) {
                    logger.error(e);
                }
                out.print("1"); //1成功
            } else {
                out.print("-1");
            }
        } catch (Exception e) {
            logger.error("=================富有提现通知接受发生异常=================", e);
            e.printStackTrace();
        }
        logger.info("==============富有提现通知接收结束==================");
    }

    /**
     * 富有支付：接受提现通知--退票通知
     */
    @RequestMapping(value = "/withdraw/fuiou/notify/failed", method = RequestMethod.POST)
    public void withdrawFuiouNotifyFailed(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        logger.info("==============富有提现通知【退票】接收开始==================");
        try {
            out = response.getWriter();
            String orderNo = request.getParameter("orderNo");
            String merdt = request.getParameter("merdt");
            String accntno = request.getParameter("accntno");
            String amt = request.getParameter("amt");
            String mac = request.getParameter("mac");
            String state = request.getParameter("state");
            String signPain = new StringBuffer().append(PayConstants.WITHDRAW_MCHNT_CD).append("|").append(PayConstants.WITHDRAW_MCHNT_KEY).append("|")
                    .append(orderNo).append("|").append(merdt).append("|").append(accntno).append("|").append(amt).toString();
            if (MD5.MD5Encode(signPain).equals(mac)) {
                Withdraw withdraw = withdrawService.getByOrderNo(orderNo);
                if (withdraw == null) {
                    logger.info("============【退票】找不到该笔提现订单=============");
                    out.print("-1");
                    return;
                }
                if (withdraw.getStatus().equals(1) || withdraw.getStatus().equals(2)) {
                    logger.info("==============富有通知【退票】该次提现已经失败或者成功，无需通知==============");
                    out.print("-1");
                    return;
                }
                //退票 提现失败
                withdraw.setStatus(2);
                withdraw.setTechRemark("failed");
                try {
                    withdrawService.audit(withdraw, 1, false);// 灵活宝1表示
                } catch (LockFailureException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
                out.print("1"); //1成功
            } else {
                out.print("-1");
            }
        } catch (Exception e) {
            logger.error("=================富有提现通知【退票】接受发生异常=================", e);
            e.printStackTrace();
        }
        logger.info("==============富有提现通知【退票】接收结束==================");
    }
    
    private static Provider prvd = null;
    
    static{
        prvd = new BouncyCastleProvider();
    }

    /**
     * 通联支付：接受提现通知（成功、失败、退票）
     */
    @RequestMapping(value = "/withdraw/allinpay/notify", method = RequestMethod.GET)
    public void withdrawAllinPayNotify(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        logger.info("==============通联提现通知接收开始==================");
        try {
            out = response.getWriter();
            String RETCODE = request.getParameter("RETCODE");
            String RETMSG = request.getParameter("RETMSG");
            String ACCOUNT_NO = request.getParameter("ACCOUNT_NO");
            String MOBILE = null==request.getParameter("MOBILE")?"":request.getParameter("MOBILE");
            String AMOUNT = request.getParameter("AMOUNT");//分
            String SETTDAY = request.getParameter("SETTDAY");//清算日期
            String FINTIME = request.getParameter("FINTIME");
            String SUBMITTIME = request.getParameter("SUBMITTIME");
            String BATCHID = request.getParameter("BATCHID");
            String SN = null==request.getParameter("SN")?"":request.getParameter("SN");
            String POUNDAGE = null==request.getParameter("POUNDAGE")?"":request.getParameter("POUNDAGE");
            String USERCODE = request.getParameter("USERCODE");
            String MERID = request.getParameter("MERID");
            String TRXTYPE = request.getParameter("TRXTYPE");
            String TRXCODE = request.getParameter("TRXCODE");
            String SIGN = request.getParameter("SIGN");
            String signPain = new StringBuffer().append(ACCOUNT_NO).append("|").append(MOBILE).append("|")
                    .append(AMOUNT).append("|").append(BATCHID).append("|").append(SN).append("|").append(POUNDAGE).toString();
            AIPGSignature signature = new AIPGSignature(prvd);
            if (signature.verifyMsg(SIGN, signPain, PayConstants.ALLINPAY_PROVIDED_PUB_PATH)) {
                WithdrawTemp withdrawTemp = withdrawService.getBySn(BATCHID);
                if (withdrawTemp == null) {
                    logger.info("============找不到该笔提现提现交易流水=============");
                    out.print("-1");
                    return;
                }
                Withdraw withdraw = withdrawService.getByOrderNo(withdrawTemp.getOrderNo());
                if (withdraw == null) {
                    logger.info("============找不到该笔提现订单=============");
                    out.print("-1");
                    return;
                }
                if (withdraw.getStatus().equals(1)) {
                    logger.info("==============通联通知该次提现已经成功，无需通知==============");
                    out.print("-1");
                    return;
                }
                //提现成功
                if (RETCODE.equals("0000")) {
                    withdraw.setStatus(1);
                    withdraw.setTechRemark("success");
                } else if(RETCODE.equals("3056")) {
                    logger.info("退票什么都不做，退票订单号："+BATCHID);
                    out.print(PayConstants.ERROR);
                    return;
                } else {
                    withdraw.setStatus(2);
                    withdraw.setTechRemark("failed");
                    //返还提现券
                    WithdrawCoupon wc = withdrawCouponService.getByWithdrawId(withdraw.getId());
                    if (wc != null) {
                        withdrawCouponService.addToUser(wc.getUserId(), wc.getTitle(), wc.getDescript(), wc.getSource(), null, DateFormatTools.dayToDaySubtractWithoutSeconds(wc.getUseTime(), wc.getExpireTime()));
                    }
                }
                try {
                    withdrawService.audit(withdraw, 1, false);// 灵活宝1表示
                } catch (LockFailureException e) {
                    logger.error(e);
                }
                out.print(PayConstants.SUCCESS); //1成功
            } else {
                out.print(PayConstants.ERROR);
            }
        } catch (Exception e) {
            logger.error("=================通联提现通知接受发生异常=================", e);
            e.printStackTrace();
        } finally {
            if(null != out) {
                out.close();
            }
        }
        logger.info("==============通联提现通知接收结束==================");
    }

    /**
     * 用户提现
     *
     * @param amount       提现金额
     * @param token
     * @param pwd          支付密码
     * @param isOk         指纹密码是否正确
     * @param cardId       提现卡的ID
     * @param provinceCode 省份编码
     * @param cityCode     城市编码
     * @param bankBranch   分行名称
     * @return
     */
    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户提现")
    public AppResult withdraw(HttpServletRequest request,
                              @ApiParam("提现金额") @RequestParam Double amount,
                              @ApiParam("用户token") @RequestParam String token,
                              @ApiParam("App版本号") @RequestParam String appVersion,
                              @ApiParam("支付密码") @RequestParam String pwd,
                              @ApiParam("指纹密码是否验证成功") @RequestParam(required = false) String isOk,
                              @ApiParam("提现银行卡ID") @RequestParam(required = false) Integer cardId,
                              @ApiParam("省份编码") @RequestParam(required = false) String provinceCode,
                              @ApiParam("城市编码") @RequestParam(required = false) String cityCode,
                              @ApiParam("分行名称") @RequestParam(required = false) String bankBranch,
                              @ApiParam("类型(0 T+0,1 t+1)") @RequestParam Integer type,
                              @ApiParam("提现券id") @RequestParam(required = false) Integer withdrawCouponId,
                              @ApiParam("确认提现提示") @RequestParam(required = false) Boolean confirm,
                              @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam String client) throws Exception {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.MONTH, 9);
        c1.set(Calendar.DATE, 1);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.MONTH, 9);
        c2.set(Calendar.DATE, 8);
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);

        Date current = new Date();
        if (current.getTime() >= c1.getTimeInMillis() && current.getTime() < c2.getTimeInMillis()) {
            return new AppResult(FAILED, com.goochou.p2b.constant.Constants.HOLIDAY_WITHDRAW_ALERT);
        }

        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        if (amount <= 0) {
            return new AppResult(FAILED, "提现金额必须大于0");
        }
        if (amount < 100) {
            return new AppResult(FAILED, "提现金额必须大于100元");
        }
        if (amount > 50000) {
            return new AppResult(FAILED, "单次提现金额不能超过5万元");
        }
//        if (StringUtils.isBlank(user.getPayPassword())) {
//            return new AppResult(FAILED, "请先设置支付密码");
//        }
//        if (StringUtils.isBlank(pwd) || !user.getPayPassword().equalsIgnoreCase(MD5.MD5Encode(pwd + Constants.PASSWORD_FEX))) {
//            return new AppResult(ALERT, "支付密码错误");
//        }
        if (StringUtils.isNotBlank(isOk) && isOk.equals("0")) {
            return new AppResult(FAILED, "指纹错误"); // 支付密码或者指纹密码错误
        }

        // 提现黑名单
        if (withdrawBlackService.get(user.getId()) > 0) {
            return new AppResult(FAILED, "您的账户存在风险，请联系客服");
        }
        WithdrawCoupon wc = withdrawCouponService.get(withdrawCouponId);
        if (wc != null && wc.getStatus() == 1) {
            return new AppResult(FAILED, "提现券已失效");
        }
        if (wc != null && !wc.getUserId().equals(user.getId())) {
            return new AppResult(FAILED, "不能使用非本人的提现券");
        }

        BankCard bc = null;
       /* List<BankCard> bankCards = bankCardService.getByUserId(user.getId());
        if (bankCards.size() > 0) {
            bc = bankCards.get(0);
        }*/
        if(cardId != null) {
            bc = bankCardService.get(cardId);
        } else {
            List<BankCard> bankCards = bankCardService.getByUserId(user.getId());
            if (bankCards.size() > 0) {
                bc = bankCards.get(0);
            }
        }
        /*if (bankCards.size() > 0) {
            bc = bankCards.get(0);
        }*/
        if (bc == null) {
            return new AppResult(FAILED, "提现卡不存在"); // 提现卡不存在
        }
        logger.info("===================================【" + user.getTrueName() + "】进入提现流程=====================================");

        Assets a = assetsService.findByuserId(user.getId());
        Double preAvaAmount = a.getBalanceAmount();
        if (amount > preAvaAmount) {
            return new AppResult(FAILED, "对不起，您的可用余额不足");
        }

        //手续费
        // 当天提现3笔以上,收取Constants.WITHDRAW_FEE元手续费
        Double fee = 0.0;
        //查询当天提现记录
        List<WithdrawRecordVO> withdrawList = withdrawService.queryProcessWithdrawRecordByUser(user.getId(), current, current,null, null);
        if(withdrawList!= null && withdrawList.size() >= 3) {//已经有3条提现记录
            fee = Constants.WITHDRAW_FEE.doubleValue();
        }
        // 提现金额 = 提现金额 - 手续费
        Double withdrawAmount = BigDecimalUtil.sub(amount, fee);
        //平台已将提现手续费垫付，今天您还  剩2次免费提现机会。
        if (null != confirm && !confirm) {
            Map<String, Object> map = new HashMap<>();
            if (fee > 0) {
                String title = "实际到账"+ withdrawAmount + "元";
                String content ="您的当天免费提现次数已用完，此次提现将扣除"+Constants.WITHDRAW_FEE+"元手续费";
                map.put("title", title);
                map.put("content", content);
                return new AppResult(CONFIRM,  map);

            } else {
                int num = withdrawList == null ? 3 : (3-withdrawList.size());
                String content = "平台已将提现手续费垫付，今天您还剩"+num+"次免费提现机会。";
                map.put("content", content);
                return new AppResult(CONFIRM, map);
            }
        }

        String orderNo = withdrawOrderIdGenerator.next();
        // 创建提现,保存，冻结金额
        Date createTime = new Date();
        Withdraw w = new Withdraw();
        w.setAmount(amount);
        w.setRealAmount(withdrawAmount);
        w.setCardNo(bc.getCardNumber());
        w.setBankId(bc.getBankId());
        w.setCreateDate(createTime);
        w.setUserId(user.getId());
        w.setStatus(0);
        w.setPayChannel(bc.getBank().getPayChannel());
        w.setType(type);
        w.setOrderNo(orderNo);
        w.setClient(client);
        WithdrawTemp wt = new WithdrawTemp();
        wt.setClient(client);
        if(w.getPayChannel().equals(OutPayEnum.FUIOU_QUICK.getFeatureName())) {
            wt.setBankCode(bc.getBank().getFuiouWithdrawCode());
        }else if(w.getPayChannel().equals(OutPayEnum.ALLINPAY.getFeatureName())) {
            wt.setBankCode(new AllinPayOutPay().getWithdrawBankMap().get(bc.getBank().getCode()));
        }
        wt.setAmount(w.getRealAmount());
        wt.setPayChannel(w.getPayChannel());
        wt.setOrderNo(w.getOrderNo());
        wt.setCardNo(w.getCardNo());
        wt.setTrueName(user.getTrueName());
        wt.setStatus(WithdrawTempStatusEnum.WEIWANCHENG.getFeatureName());
        wt.setType(type);
        if (type == 1) {
            int addDays = 1;
            int week = DateUtil.getCurrentDayWeek();

            Calendar c = Calendar.getInstance();
            c.setTime(current);
            if (week == 5 || week == 6) {
                c.set(Calendar.HOUR_OF_DAY, 11);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
            }
            if (DateUtil.getCurrentDayWeek() == 6) {
                addDays = 2;
            } else if (DateUtil.getCurrentDayWeek() == 5) {
                addDays = 3;
            }

            wt.setPredictSendDate(DateFormatTools.jumpOneDay(c.getTime(), addDays));
        } else if (type == 0) {
            wt.setPredictSendDate(current);
        }
        wt.setCreateDate(current);
        //保存提现信息
        try {
            withdrawService.saveForAppOne510(w, a, wt);//0PC1安卓2IOS3WAP
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, "提现失败，请重试！");
        }
        if (wc != null) {
            wc.setStatus(1);
            wc.setWithdrawId(w.getId());
            wc.setUseTime(new Date());
            withdrawCouponService.updateByPrimaryKey(wc);
        }
        Map<String, Object> returnMap = new HashMap<>();
        // app 提现成功之后返回的数据
        String tailNum = bc.getCardNumber().substring(bc.getCardNumber().length() - 4, bc.getCardNumber().length());
        returnMap.put("withdrawLable", "您有一笔提现到" + bc.getBank().getName() + "(" + tailNum + ")" + BigDecimalUtil.sub(amount, fee) + "元");
        returnMap.put("createDate", DateFormatTools.dateToStr1(w.getCreateDate()));
        returnMap.put("operateDate", DateFormatTools.dateToStr1(w.getCreateDate()));
        //returnMap.put("link", "43#" + ProjectTabEnum.DINGQI.getFeatureName());
        //returnMap.put("tunnel", 4);
        return new AppResult(SUCCESS, "提现申请成功", returnMap);
    }

    /**
     * 用户提现列表
     *
     * @param token
     * @param page  分页当前页
     * @return
     */
    @RequestMapping(value = "/withdraw/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "提现进度列表")
    @Deprecated
    public AppResult getWithdrawList(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam(required = false) String appVersion,
            @ApiParam("分页当前页") @RequestParam Integer page,
            @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam String client,
            @ApiParam("交易记录时间 TimeSearchEnum") @RequestParam(required = false) Integer timeCode
            ) {
        if (page == null || page < 1) {
            page = 1;
        }
        int limit = 10;
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }

        Date startDate = this.getStartDate(timeCode);
        Date endDate = null;
        if (startDate != null) {
            endDate = new Date();
        }

        Integer userId = user.getId();

        // 提现首先按照提现是否成功分类
        // 再按照提现时间进行排序
        List<WithdrawRecordVO> list = withdrawService.queryWithdrawRecordByUser(userId, startDate, endDate, (page - 1) * limit, limit);
        int count = withdrawService.queryWithdrawRecordCountByUser(userId, startDate, endDate);

        Double totalAmount = withdrawService.queryWithdrawTotalAmount(userId, startDate, endDate);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("timeSearch", TimeSearchEnum.toJson());
        map.put("startDate", startDate != null ? DateFormatTools.dateToStr1(startDate) : "");
        map.put("endDate", endDate != null ? DateFormatTools.dateToStr1(endDate) : "");
        map.put("list", list);
        map.put("totalAmount", totalAmount);
        map.put("page", page);
        map.put("pages", calcPage(count, limit));
        return new AppResult(SUCCESS, map);
    }

    /**
     * @param token
     * @param appVersion
     * @param type
     * @return
     * @Title: AssetsManagerController.java
     * @Package com.goochou.p2b.app.controller
     * @Description(描述):我的资产
     * @author 王信
     * @date 2016年2月26日 上午10:01:43
     * @version V1.0
     */
    @RequestMapping(value = "/userAssets", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "我的资产")
    public AppResult userAssets(@ApiParam("用户token") @RequestParam String token,
                                @ApiParam("App版本号") @RequestParam(required = false) String appVersion,
                                @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam String client) {
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        String update = checkVersion(client, appVersion);//检查是否需要更新
        Map<String, Object> map = assetsService.selectMyAssets(user.getId());
        Double uncollectInterest = interestService.getUncollectInterestAmountByUserId(user.getId());
        if (uncollectInterest == null) {
            uncollectInterest = 0d;
        }
        map.put("uncollectInterest", uncollectInterest);
        //map.put("totalAmount", BigDecimalUtil.add(map.get("totalAmount"), uncollectInterest));
        Double yesDouble = interestService.getUserDynamicInterest(user.getId(), 0);//昨日 灵活宝 收益

        //为派息定期本金
        Double noDividendPayout = interestService.getNoDividendPayout(user.getId());
        map.put("regularAmount", noDividendPayout);//覆盖掉SQL里的查询

        //月月盈在投本金
        Double yyyCapital = assetsService.selectMyYyyAssets(user.getId());
        map.put("yyyCapital", yyyCapital);
        //月月盈待收收益
        Double yyyIncome = assetsService.selectMyYyyInterest(user.getId());
        map.put("yyyIncome", yyyIncome);


        map.put("totalAmount", BigDecimalUtil.add(map.get("totalAmount"), uncollectInterest, yyyIncome));

        Map<String, Object> signed = userSignedService.selectSignedAgainYesterday(user.getId());//昨日签到收益
        Double amount = signed.get("amount") == null ? 0 : BigDecimalUtil.fixed2(signed.get("amount").toString());
        Double yesDoubles = (yesDouble == null ? 0 : yesDouble) + amount;
        map.put("yesterdayAmount", yesDoubles);
        map.put("update", update);//是否需要更新的标记
        map.put("btn", "赚取更多收益");
        map.put("btnUrl", "43#dingqi");
        return new AppResult(SUCCESS, map);
    }

    /**
     * @Description: 累计收益
     * @date 2016/11/29
     * @author 王信
     */
    @RequestMapping(value = "/accumulatedIncome", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "累计收益")
    @NeedLogin
    public AppResult accumulatedIncome(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam String client) {
        try {
            User user = userService.checkLogin(token);
            Map<String, Object> map = tradeRecordService.selectAccumulatedIncome(user.getId());

            double regularAmount = (Double) map.get("regularAmount");
            double yyyIncome = (Double) map.get("yyyIncome");
            double signAmount = (Double) map.get("signAmount");
            double hongbaoAmount = (Double) map.get("hongbaoAmount");
            double rateCouponAmount = (Double) map.get("rateCouponAmount");
            Double sumIncome = BigDecimalUtil.add(regularAmount, yyyIncome, signAmount, hongbaoAmount, rateCouponAmount);

            map.put("sumIncome", sumIncome);
            map.put("btn", "赚取更多收益");
            map.put("btnUrl", "43#dingqi");
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * @param token
     * @param page
     * @return com.goochou.p2b.app.model.AppResult
     * @description 交易记录
     * @author shuys
     * @date 2019/5/21
     */
    @RequestMapping(value = "/trade/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "交易记录")
    @NeedLogin
    public AppResult tradeRecode(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("分页当前页") @RequestParam Integer page,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam(required = false) String client,
            @ApiParam("账户类型（0.现金，1.余额）") @RequestParam(required = false) Integer accountType,
            @ApiParam("交易记录时间 TimeSearchEnum") @RequestParam(required = false) Integer timeCode
    ) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            if (page == null || page < 1) {
                page = 1;
            }
            int limit = 10;
            if (accountType != null && accountType != 0 && accountType != 1) {
                return new AppResult(FAILED, PARAM_ERROR);
            }

            Date startDate = this.getStartDate(timeCode);
            Date endDate = null;
            if (startDate != null) {
                endDate = new Date();
            }

            Integer userId = user.getId();

            List<TransactionRecordVO> list = tradeRecordService.queryTradeRecordFromApp(userId, accountType, startDate, endDate, (page - 1) * limit, limit);
            int count = tradeRecordService.queryTradeRecordCountFromApp(userId, accountType, startDate, endDate);

            double income = tradeRecordService.queryUserTradeSumAmount(userId, accountType, startDate, endDate, true);
            double expenditure = tradeRecordService.queryUserTradeSumAmount(userId, accountType, startDate, endDate, false);

            Map<String, Object> result = new HashMap<String, Object>();
            result.put("list", list);
            result.put("page", page);
            result.put("pages", calcPage(count, limit));
            result.put("count", count);
            result.put("startDate", startDate != null ? DateFormatTools.dateToStr1(startDate) : "");
            result.put("endDate", endDate != null ? DateFormatTools.dateToStr1(endDate) : "");
            result.put("accountType", accountType);
            result.put("income", income); // 收入
            result.put("expenditure", expenditure); // 支出

            result.put("timeSearch", TimeSearchEnum.toJson());
//            result.put("accountType", AccountTypeEnum.toJson());
//            result.put("accountOperateType", AccountOperateTypeEnum.toJson());
            return new AppResult(SUCCESS, result);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    private Date getStartDate(Integer timeCode) {
        if (timeCode == null || TimeSearchEnum.ALLS.getCode().equals(timeCode)) { // 参数为 空/全部 时
            return null;
        }
        TimeSearchEnum timeSearchEnum = TimeSearchEnum.getValueByCode(timeCode);
        if (timeSearchEnum != null) {
            if (timeSearchEnum.equals(TimeSearchEnum.THIS_WEEK)) { // 本周
                return DateUtil.getTimesWeekmorning(); // 获得本周一0点时间
            }
            if (timeSearchEnum.equals(TimeSearchEnum.THIS_MONTH)) { // 本月
                return DateUtil.getDayStartDate(DateUtil.getCurrentMonthFirstDate()); //  获取当前日期月份的第一天
//            return DateUtil.getDateBeforeMouth(new Date(), 1);
            }
            if (timeSearchEnum.equals(TimeSearchEnum.THREE_MONTHS)) { // 三个月
                return DateUtil.getDateBeforeMouth(new Date(), 3);
            }
            if (timeSearchEnum.equals(TimeSearchEnum.SIX_MONTHS)) { // 六个月
                return DateUtil.getDateBeforeMouth(new Date(), 6);
            }
        }
        return null;
    }


    @RequestMapping(value = "/trade/detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "交易记录详情")
    @NeedLogin
    public AppResult tradeRecodeDetail(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam String client,
            @ApiParam("id") @RequestParam Integer id
    ) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            TransactionRecordDetailVO detail = tradeRecordService.queryTradeRecordDetailById(id);
            return new AppResult(SUCCESS, detail);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * 获取支付渠道
     * @author ydp
     * @param token
     * @param appVersion
     * @param client
     * @return
     */
    @RequestMapping(value = "/getPayChannel", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取支付渠道")
    @NeedLogin
    public AppResult getPayChannel(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("项目类型OrderTypeEnum") @RequestParam String orderType,
            @ApiParam("秒杀商品活动Id") @RequestParam(required = false) Integer activityId,
            @ApiParam("项目/商品ID集合") @RequestParam(required = false) List<Integer> otherIds,
            @ApiParam("对应项目/商品数量集合") @RequestParam(required = false) List<Integer> nums,
            @ApiParam("红包ID") @RequestParam(required = false) Integer hongbaoId,
            @ApiParam("项目/商品订单号") @RequestParam(required = false) String orderNo,
            @ApiParam("app名字") @RequestParam(required = false) String appName,
            @ApiParam("拼牛份数") @RequestParam(required = false) Integer point,
            @ApiParam("拼牛金额") @RequestParam(required = false) Double pinAmount,
            @ApiParam("收货地址ID") @RequestParam(required = false) Integer addressId
    ) {

        return getPayChannelAndMoney(token,appVersion,client,orderType,activityId,otherIds,nums,hongbaoId,orderNo,appName,
                true,true,true,point,pinAmount, addressId);
    }

    /**
     * 根据支付方式自动计算资金
     * @author ydp
     * @param token
     * @param appVersion
     * @param client
     * @return
     */
    @RequestMapping(value = "/calculatePayMoney", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据支付方式自动计算资金")
    @NeedLogin
    public AppResult calculatePayMoney(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("项目类型OrderTypeEnum") @RequestParam String orderType,
            @ApiParam("秒杀商品活动Id") @RequestParam(required = false) Integer activityId,
            @ApiParam("项目/商品ID集合") @RequestParam(required = false) List<Integer> otherIds,
            @ApiParam("对应项目/商品数量集合") @RequestParam(required = false) List<Integer> nums,
            @ApiParam("红包ID") @RequestParam(required = false) Integer hongbaoId,
            @ApiParam("项目/商品订单号") @RequestParam(required = false) String orderNo,
            @ApiParam("app名字") @RequestParam(required = false) String appName,
            @ApiParam("是否启用默认余额扣款") @RequestParam(required = false) boolean isAutoUseBalance,
            @ApiParam("是否启用默认授信扣款") @RequestParam(required = false) boolean isAutoUseCredit,
            @ApiParam("拼牛份数") @RequestParam(required = false) Integer point,
            @ApiParam("拼牛金额") @RequestParam(required = false) Double pinAmount,
            @ApiParam("收货地址ID") @RequestParam(required = false) Integer addressId
            
    ) {
    	System.out.println("isAutoUseBalance"+isAutoUseBalance);
        return getPayChannelAndMoney(token,appVersion,client,orderType,activityId,otherIds,nums,hongbaoId,orderNo,appName,
        		isAutoUseBalance,isAutoUseCredit,false,point,pinAmount,addressId);
    }


    private AppResult getPayChannelAndMoney(String token,String appVersion,String client,String orderType,Integer activityId,List<Integer> otherIds,List<Integer> nums,Integer hongbaoId,
                                            String orderNo,String appName,boolean isAutoUseBalance,boolean isAutoUseCredit, boolean isGetChannel,Integer point,Double pinAmount,Integer addressId){

        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            boolean isVIP = user.getLevel() > 0 ? true : false;

            PayChannelResponse result=null;

            if(isGetChannel) {
                result = bankCardService.getUserPayChannel(user.getId());
            }else{
                result = new PayChannelResponse();
            }

            Project project2=null;
            boolean isFirstBuy=false;

            //后期可根据喜好自动选择支付方式CHOOSE
            result.setNeedPayMoney(0D);

            //账户可用余额
            result.setAvailableMoney(user.getAssets().getBalanceAmount());
            result.setAvailableCreditMoney(user.getAssets().getCreditAmount());

            result.setCreditPayMoney(0D);
            result.setBalancePayMoney(0D);

            if(null == orderNo || "".equals(orderNo)) {

                Hongbao hongbao = null;
                //判断红包是否可用
                if (null != hongbaoId && 0 != hongbaoId) {
                    hongbao = hongbaoService.get(hongbaoId);
                    if (hongbao == null) {
                        return new AppResult(FAILED, "红包不存在");
                    }
                    if (hongbao.getUserId().intValue() != user.getId().intValue()) {
                        return new AppResult(FAILED, "红包归属错误");
                    }
                    Date currentDate = new Date();
                    if (hongbao.getExpireTime().getTime() < currentDate.getTime()) {
                        return new AppResult(FAILED, "红包已过期");
                    }
                    if (hongbao.getUseTime() != null) {
                        return new AppResult(FAILED, "红包已使用");
                    }
                }
                //获取项目需要支付金额
                if(OrderTypeEnum.INVESTMENT.getFeatureName().equals(orderType)) {
                    //参数非空判断
                    if (null == otherIds || otherIds.size()==0) {
                        return new AppResult(FAILED, "项目参数异常");
                    }
                    //判断用户是否实名
                    if(null == user.getTrueName() || null == user.getIdentityCard()) {
                        return new AppResult(FAILED, "请先实名认证", "");
                    }
                    //项目是否可购买
                    Project project = projectService.getProjectById(otherIds.get(0));
                    if(project ==null){
                        return new AppResult(FAILED, "参数错误");
                    }

                    // 判断是否是新手标，只能购买一次
                    if (project.getNoob() == 1) {
                        ProjectExample example=new ProjectExample();
                        example.createCriteria().andNoobEqualTo(1).andUserIdEqualTo(user.getId()).andStatusIn(Arrays.asList(2,3,4));//0待上架1上架2待付款3已出售4已回购
                        int noobCnt=projectService.getMapper().countByExample(example);
                        if(noobCnt>0) {
                            return new AppResult(FAILED, "新手项目用户只能购买一次", "");
                        }else {
                            isFirstBuy=true;
                        }
                    }
//                    if (null == project) {
//                        return new AppResult(FAILED, "项目不存在");
//                    }
//                    if (project.getStatus() != ProjectStatusEnum.ENABLE_SALE.getCode()) {
//                        return new AppResult(FAILED, "项目不在出售状态");
//                    }
                    
                    
                    if(project.getProjectType().equals(ProjectTypeEnum.YANGNIU.getFeatureType())) {
                    	result.setNeedPayMoney(project.getTotalAmount());
                    }else if(project.getProjectType().equals(ProjectTypeEnum.PINNIU.getFeatureType())) {
                    	ProjectViewExample pvExample=new ProjectViewExample();
                		pvExample.createCriteria().andIdEqualTo(project.getId());
                        List<ProjectView> projectViewList=projectViewMapper.selectByExample(pvExample);
                        ProjectView projectView=null;
                        if(projectViewList.size()==1){
                        	projectView=projectViewList.get(0);
                        }else {
                			return new AppResult(FAILED, "项目不存在", "");
                        }
                    	if(point == null || point<=0) {
            				return new AppResult(FAILED, "拼牛份数有误", "");
                    	}
                    	if(point>projectView.getPinResiduePoint()) {
                    		return new AppResult(FAILED, "拼牛份数不足", "");
                    	}
                    	if(pinAmount == null  || pinAmount<0) {
                    		return new AppResult(FAILED, "拼牛金额有误", "");
                    	}
                    	//当牛只不能被份数整除或份数调整时,会出现最后一份金额变化
                    	if(projectView.getPinAmountForPoint(point).compareTo(BigDecimal.valueOf(pinAmount))!=0  ) {
                    		return new AppResult(FAILED, "拼牛金额有变", "");
                    	}
                    	result.setNeedPayMoney(pinAmount);
                    }
                    
                    if(null != hongbao) {
                    	if(project.getProjectType().equals(ProjectTypeEnum.PINNIU.getFeatureType())) {
            				if (hongbao.getType() != 4) {
            					return new AppResult(FAILED, "红包不支持此类订单", "");
            				}
            			}else if(project.getProjectType().equals(ProjectTypeEnum.YANGNIU.getFeatureType())) {
            				if (hongbao.getType() != 2) {
            					return new AppResult(FAILED, "红包不支持此类订单", "");
            				}
            			}else {
            				return new AppResult(FAILED, "红包不支持此类订单", "");
            			}
                        if (hongbao.getLimitAmount() > project.getTotalAmount()) {
                            return new AppResult(FAILED, "未满足红包限制金额");
                        }
                        if (hongbao.getLimitDay() > project.getLimitDays()) {
                            return new AppResult(FAILED, "未满足红包使用期限");
                        }
                    }
                }else if(OrderTypeEnum.GOODS.getFeatureName().equals(orderType)) {
                    Integer userId = user.getId();
                    if (null == userId || null == otherIds || null == nums) {
                        return new AppResult(FAILED, "项目参数异常");
                    }
                    if (otherIds.size() < 1 || nums.size() < 1
                            || otherIds.size() != nums.size()) {
                        return new AppResult(FAILED, "参数错误");
                    }

                    SecondKillDetailVO secondKillDetailVO=null;

                    if(activityId!=null && activityId!=0){

                        //查询当天此商品的秒杀数据
                        //读取缓存
                        List<SecondKillDetailVO> secondKillDetailVOS = mallActivityService.getTheSameDaySecondKillDetails(activityId);
                        if (secondKillDetailVOS == null || secondKillDetailVOS.size() == 0) {
                            return new AppResult(FAILED,"秒杀活动未开始或者已结束");
                        }

                        secondKillDetailVO = secondKillDetailVOS.get(0);

                        if(secondKillDetailVO.getBeginTime().compareTo(new Date())> 0 ||
                                secondKillDetailVO.getEndTime().compareTo(new Date())< 0 ){
                            return new AppResult(FAILED,"秒杀活动未开始或者已结束");
                        }

                        if(otherIds.size()!=1){
                            return new AppResult(FAILED,"一次只能秒杀一种商品");
                        }
                        if(nums.get(0).intValue()>1) {
                            return new AppResult(FAILED,"一次只能秒杀一个商品");
                        }
                    }
                    double weight = 0d;
                    for(int i=0;i<otherIds.size();i++) {
                        Goods goods = goodsService.getGood(otherIds.get(i));

                        BigDecimal buyPrice= BigDecimal.ZERO;
                        if(activityId!=null && activityId>0) {
                            buyPrice = isVIP ? secondKillDetailVO.getMemberPrice() : secondKillDetailVO.getPrice();
                        }else{
                            buyPrice = isVIP ? goods.getMemberSalingPrice() : goods.getSalingPrice();
                        }
                        weight += goods.getWeight().doubleValue() * nums.get(i);
                        result.setNeedPayMoney(BigDecimalUtil.add(result.getNeedPayMoney(), BigDecimalUtil.multi(buyPrice, nums.get(i))));
                    }
                    if(null != hongbao) {
                        if (hongbao.getType() != 3) {
                            return new AppResult(FAILED, "红包不支持此类订单");
                        }
                        if (Double.valueOf(hongbao.getLimitAmount()).longValue() > result.getNeedPayMoney().longValue()) {
                            return new AppResult(FAILED, "未满足红包限制金额");
                        }
                    }
                    
                    //使用红包前判断是否包邮及邮费
                   
                    if(getVersion(appVersion) >= 200) {
                    	if(addressId == null) {
                    		return new AppResult(FAILED, "请选择收货地址");
                    	}
                    	Map<String, Object> expressMap = new HashMap<String, Object>();
//                    	UserAddress address = userAddressService.selectAddressById(addressId);
                        UserAddress address = userAddressService.getAddressesById(addressId);

                        // 老版本使用市code，新版本使用区县code
                        String areaId = StringUtils.isBlank(address.getaId()) ? address.getcId() : address.getaId();
                        
                    	expressMap = goodsService.calculateExpressFee(weight, BigDecimal.valueOf(result.getNeedPayMoney()), areaId);
                    	BigDecimal expressFee = new BigDecimal(expressMap.get("expressFee")+"");
                    	  //BigDecimal realExpressFee = new BigDecimal(expressMap.get("realExpressFee")+"");
                        result.setNeedPayMoney(BigDecimalUtil.add(result.getNeedPayMoney(), expressFee.doubleValue()));
                    }
                   
                  
                    
                    //这里不需要判断库存
//                    for (int i = 0; i < otherIds.size(); i++) {
//                        //当前商品的库存
//                        Goods goods = goodsService.getMapper().selectByPrimaryKey(otherIds.get(i));
//                        if (null == goods) {
//                            return new AppResult(FAILED, "商品不存在");
//                        }
//                        if (goods.getStock() < nums.get(i)) {
//                            return new AppResult(FAILED, "商品" + goods.getGoodsName() + "库存不足");
//                        }
//                    }
                }else if(OrderTypeEnum.RECHARGE.getFeatureName().equals(orderType)) {
                    if(otherIds!=null && otherIds.size()>0) {
                        return new AppResult(FAILED, "参数错误");
                    }
                    if(nums!=null && nums.size()>0) {
                        return new AppResult(FAILED, "参数错误");
                    }
                    if(hongbao!=null) {
                        return new AppResult(FAILED, "充值不能使用红包");
                    }
                }
                //扣除红包金额
                if(null != hongbao) {
                    result.setNeedPayMoney(BigDecimalUtil.parse(result.getNeedPayMoney()).subtract(BigDecimalUtil.parse(hongbao.getAmount())).doubleValue());
                }

                result.setBankPayMoney(result.getNeedPayMoney());

                if(getVersion(appVersion)>=130) {
                    if (OrderTypeEnum.GOODS.getFeatureName().equals(orderType)) {

                        if (isAutoUseCredit) {
                            if (result.getBankPayMoney() > result.getAvailableCreditMoney()) {
                                result.setCreditPayMoney(result.getAvailableCreditMoney());
                                result.setBankPayMoney(BigDecimalUtil.parse(result.getBankPayMoney()).subtract(BigDecimalUtil.parse(result.getAvailableCreditMoney())).doubleValue());
                            } else {
                                result.setCreditPayMoney(result.getBankPayMoney());
                                result.setBankPayMoney(0D);
                            }
                        }
                    }

                    if (OrderTypeEnum.INVESTMENT.getFeatureName().equals(orderType)
                            || OrderTypeEnum.GOODS.getFeatureName().equals(orderType)) {

                        if (isAutoUseBalance) {
                            if (result.getBankPayMoney() > result.getAvailableMoney()) {
                                result.setBalancePayMoney(result.getAvailableMoney());
                                result.setBankPayMoney(BigDecimalUtil.parse(result.getBankPayMoney()).subtract(BigDecimalUtil.parse(result.getAvailableMoney())).doubleValue());
                            } else {
                                result.setBalancePayMoney(result.getBankPayMoney());
                                result.setBankPayMoney(0D);
                            }
                        }
                    }
                }
            }else {
                if(OrderTypeEnum.INVESTMENT.getFeatureName().equals(orderType)) {
                    Investment investment = investmentService.findByOrderNo(orderNo);
                    project2 = projectService.get(investment.getProjectId());
                    result.setNeedPayMoney(investment.getRemainAmount().doubleValue());
                    result.setBankPayMoney(result.getNeedPayMoney());

                }else if(OrderTypeEnum.GOODS.getFeatureName().equals(orderType)) {
                    GoodsOrder goodsOrder = goodsOrderService.findByOrderNum(orderNo);
                    result.setNeedPayMoney(goodsOrder.getRealPayMoney().doubleValue());
                    result.setBankPayMoney(result.getNeedPayMoney());

                }else {
                    return new AppResult(FAILED, "订单类型异常");
                }
            }


            if(isGetChannel) {
                //加载支付渠道
                if (OrderTypeEnum.GOODS.getFeatureName().equals(orderType)) {
                    //变更渠道选择选项
                    if ("不显示牛".equals(appName)) {
                        logger.info("appName=" + appName);
                    } else {
                        logger.info("===变更渠道选择选项====");
                        result.getChannel().clear();
                        result.getChannel().add(result.getPaychannelByEnum(OutPayEnum.WXPAY, false));
                        if (getVersion(appVersion) > 120) {
                            result.getChannel().add(result.getPaychannelByEnum(OutPayEnum.ALPAY, true));
                        }
                    }
                } else {
//                    if (getVersion(appVersion) > 110) {
//                        //添加收银台
//                        Map<String, String> last = new HashMap<>();
//                        last.put(CHANNEL_URL, ClientConstants.ALIBABA_PATH + "images/tai.png");
//                        last.put(CHANNEL_KEY, OutPayEnum.YEEPAY.getFeatureName());
//                        last.put(CHANNEL_NAME, "收银台(大额)");
//
//                        if (result.getNeedPayMoney() > 0) {
//                            if (isFirstBuy || (project2 != null && project2.getNoob() == 1)) {
//                                last.put(CHANNEL_CHOOSE, "0");
//                            } else {
//                                last.put(CHANNEL_CHOOSE, "1");
//                            }
//                        } else {
//                            last.put(CHANNEL_CHOOSE, "0");
//                        }
//
//                        result.getChannel().add(0, last);
//                    }

                    //result.getChannel().add(0, result.getPaychannelByEnum(OutPayEnum.WXPAY, isFirstBuy));
                    if (getVersion(appVersion) > 120) {
                        result.getChannel().add(0, result.getPaychannelByEnum(OutPayEnum.ALPAY, true));
                    }

//                    if (isFirstBuy || (project2 != null && project2.getNoob() == 1)) {
//                    if (getVersion(appVersion) > 120) {
                        //新用户第一次购买新手显示微信支付
                        result.getChannel().add(0, result.getPaychannelByEnum(OutPayEnum.WXPAY, false));
//                    }
                }
            }

            result.setAutoUse(true);

            return new AppResult(SUCCESS, result);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    /**
     * 支付回调地址
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/backRecharge")
    public void backRecharge(HttpServletRequest request, HttpServletResponse response, Model model){

        logger.info("进入");
        PrintWriter pw = null;
        try {
            request.setCharacterEncoding(Constants.UTF8);
            BaseOutPay baseOutPay = null;
            for(OutPayEnum payEnum : OutPayEnum.values()){
            	if(payEnum.getFeatureType()==null) {
            		continue;
            	}
                Class<?> onwClass = Class.forName(payEnum.getFeatureType());
                //反射生成一个子类的空实例
                baseOutPay = (BaseOutPay) onwClass.newInstance();
                Class<?> payBackClass = Class.forName(baseOutPay.getPayBackClassName());
                BasePayBack payBack = (BasePayBack) payBackClass.newInstance();
                if(payBack.getRequestType().equals(Constants.REQUEST_POST)){
                    //支付订单号
                    final String outPayId = request.getParameter(payBack.getOutPayId());
                    if(null != outPayId && !"".equals(outPayId)){
                        logger.info("outPayId="+outPayId+";from "+payEnum.getFeatureName()+" callback");
                         //验证签名
                        Boolean signCheckSuccess = payBack.checkSign(request, baseOutPay);
                        if(!signCheckSuccess){
                            logger.error("签名异常");
                            break;
                        }
                        //返回状态信息描述
                        final String backInfo = request.getParameter(payBack.getBackInfo());
                        //返回状态code
                        String status = request.getParameter(payBack.getStatusName());
                        //成功code
                        String successStatusCode = payBack.getStatus();
                        boolean messageStatus = status.equals(successStatusCode) ? true : false;

                        //查询充值单
                        Recharge recharge = rechargeService.getByOrder(request.getParameter(payBack.getOrderNo()));
                        if (recharge == null) {
                            logger.info("============找不到该笔充值订单=============");
                            response.getWriter().print(payBack.getRes());
                            return;
                        }
                        if (messageStatus && recharge.getStatus()==0 ) {
                            logger.info("==============富有通知该次充值已经成功，无需通知==============");
                            response.getWriter().print(payBack.getRes());
                            return;
                        }
                        if (!messageStatus && recharge.getStatus()==2) {
                            logger.info("==============富有通知该次充值已经失败，无需通知==============");
                            response.getWriter().print(payBack.getRes());
                            return;
                        }
                        if (recharge.getStatus()!=1) {
                            logger.info("==============充值单状态不是处理中,不能执行完成支付中支付单方法==============");
                            return;
                        }
                        //保存回调记录
                        //发送请求前保存充值单，增加报文日志
                        //判断该笔记录是否已经成功充值
                        TradeMessageLog tradeMessageLog = tradeMessageLogService.getTradeMessageLog(outPayId, TradeMessageTypeEnum.BACK.getFeatureName());
                        if(tradeMessageLog == null){
                            tradeMessageLog = TradeMessageLog.createTradeMessageLogFactory(CommonUtil.getIpAddr(request), String.valueOf(recharge.getPayChannel()),
                                    String.valueOf(recharge.getPayChannel()), TradeMessageTypeEnum.BACK.getFeatureName(), String.valueOf(recharge.getUserId()),
                                    recharge.getOrderNo(), outPayId, null, backInfo,  String.valueOf(messageStatus), TradeMessageStatusEnum.WEIWANCHENG.getFeatureName());
                            //添加临时表
                            tradeMessageLogService.saveTradeMessageLog(tradeMessageLog);
                        }else{
                            tradeMessageLog.setMessageInfo(backInfo);
                            tradeMessageLog.setMessageStatus(String.valueOf(messageStatus));
                            TradeMessageLogVO tradeMessageLogVO = new TradeMessageLogVO();
                            tradeMessageLogVO.setMessageInfo(backInfo);
                            tradeMessageLogVO.setMessageStatus(String.valueOf(messageStatus));
                            tradeMessageLogVO.setEditStatus(TradeMessageStatusEnum.WEIWANCHENG.getFeatureName());
                            tradeMessageLogVO.setInOrderId(recharge.getOrderNo());
                            tradeMessageLogVO.setMessageType(TradeMessageTypeEnum.BACK.getFeatureName());
                            tradeMessageLogService.updateTradeMessageLogStatus(tradeMessageLogVO);
                        }
                        //充值成功
                        if(payBack.getCent()){
                            String amount = request.getParameter(payBack.getAmount());
                            recharge.setAmount(new Money(amount).divide(100).getAmount().doubleValue());
                        }
                        recharge.setRemark(backInfo);
                        recharge.setOutOrderNo(outPayId);
                        Assets assets = assetsService.findByuserId(recharge.getUserId()); // 获取他的资产，取得可用余额
                        if(messageStatus){
                            recharge.setStatus(0);
                            if(rechargeService.updateRecord(recharge, assets, null)){
                                //通知银行处理成功
                                pw = response.getWriter();
                                //成功后的返回信息
                                pw.print(payBack.getRes());
                            }
                        } else {
                            recharge.setStatus(2);
                            //echargeService.updateBalance(recharge, assets);
                            rechargeService.updateRecord(recharge, assets, null);
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if(null != pw){
                pw.close();
            }
        }
    }

    /**
     * 提现页面
     * @author sxy
     * @param token
     * @param type
     * @param appVersion
     * @param client
     * @return
     */
    @RequestMapping(value = "/withdrawHome", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "提现页面")
    @NeedLogin
    public AppResult withdrawHome(@ApiParam("用户token") @RequestParam String token,
                                  @ApiParam("类型(0 T+0,1 t+1)") @RequestParam Integer type,
                                  @ApiParam("App版本号") @RequestParam String appVersion,
                                  @ApiParam("终端来源(IOS,Android,PC,WAP)") @RequestParam(required = false) String client) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }

            Assets assets = assetsService.findByuserId(user.getId());
            List<BankCard> bankCard = bankCardService.getByUserId(user.getId());
            Date current = new Date();
            String withdrawFee = null;
            if (type.equals(0)) {
                withdrawFee = getCacheKeyValue(Constants.T0_FEE);
            } else {
                withdrawFee = getCacheKeyValue(Constants.T1_FEE);
            }

            String content = "提现说明：\n" +
                    "1.工作日15点前申请的提现，当日审核处理，15点以后申请的提现，将在下个工作日审核处理。\n" +
                    "2.提现到账时间为审核处理后的T+1天到账。如遇周末或节假日顺延至下个工作日到账。\n" +
                    "3.客户提现单日最高金额为100万元。\n" +
                    "4.一个自然日内前3次提现不收取任何手续费。超过3次，每次收取"+Constants.WITHDRAW_FEE+"元提现手续费。";

            List<WithdrawRecordVO> withdrawList = withdrawService.queryProcessWithdrawRecordByUser(user.getId(), current, current,null, null);
            boolean isAlert = false;
            if(withdrawList!= null && withdrawList.size() >= 3) {//已经有三次提现
                withdrawFee = "-"+Constants.WITHDRAW_FEE+"元";
                isAlert = true;
            } else {
                withdrawFee = "0元";
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("balanceAmount",assets.getBalanceAmount());
            map.put("bankIcon","");
            map.put("bankTxt", "");
            if(null != bankCard && bankCard.size()>0) {
                map.put("bankIcon",ClientConstants.ALIBABA_PATH + "images/bank-icon/bank" + bankCard.get(0).getBankId() + "@2x.png");
                map.put("bankTxt", bankCard.get(0).getBank().getName()+"("+CommonUtils.getCardFour(bankCard.get(0).getCardNumber())+")");
                map.put("id", bankCard.get(0).getId());
            }
            //目前不收手续费
//            withdrawFee = "0元";
            map.put("withdrawFee",withdrawFee);
            map.put("isAlert", isAlert);

            map.put("content",content);
            map.put("lableBankCard","提现银行卡");
            map.put("recordUrl",ClientConstants.H5_URL+"withdrawList.html");
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
    
    @RequestMapping(value = "/getPayStatus", method = RequestMethod.GET)
    @ApiOperation(value = "获取支付状态")
    @ResponseBody
    public AppResult getStatusByOrder(@ApiParam("用户token") @RequestParam String token,
                                      @ApiParam("订单号") @RequestParam String orderNo,
                                      @ApiParam("App版本号") @RequestParam String appVersion,
                                      @ApiParam("终端来源(IOS,Android,PC,WAP)") String client,
                                      @ApiParam("订单类型") @RequestParam String orderType,
                                      @ApiParam("订单ID") @RequestParam Integer id) {//recharge时没有other_id,没有入参id
        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        if (StringUtils.isBlank(orderNo)) {
            return new AppResult(NO_LOGIN, "无效订单号");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Recharge recharge = rechargeService.getByOrder(orderNo);
        if(null != recharge) {
            if (!recharge.getUserId().equals(user.getId())) {
                return new AppResult(NO_LOGIN, "无法查询非本人订单号");
            }
        }else {//没有支付信息可能是余额或授信支付
            if(orderType.equals(OrderTypeEnum.INVESTMENT.getFeatureName())) {
                Investment investment = investmentService.get(id);
                if(null != investment && investment.getPayStatus().intValue() == InvestPayStateEnum.PAYED.getCode()) {
                    map.put("status", 0);
                    map.put("remark", "支付成功");
                    map.put("id", id);
                    map.put("orderNo", investment.getOrderNo());
                }else {
                    map.put("status", 2);
                    map.put("remark", "支付失败");
                    map.put("id", id);
                    map.put("orderNo", investment.getOrderNo());
                }
            }else if(orderType.equals(OrderTypeEnum.GOODS.getFeatureName())) {
                GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKey(id);
                if(null != goodsOrder && goodsOrder.getState().intValue() == GoodsOrderStatusEnum.PAYED.getCode()) {
                    map.put("status", 0);
                    map.put("remark", "支付成功");
                    map.put("id", id);
                    map.put("orderNo", goodsOrder.getOrderNo());
                }else {
                    map.put("status", 2);
                    map.put("remark", "支付失败");
                    map.put("id", id);
                    map.put("orderNo", goodsOrder.getOrderNo());
                }
            }else if(orderType.equals(OrderTypeEnum.RECHARGE.getFeatureName())) {
                Recharge rechargeInner = rechargeService.get(id);
                if(null != rechargeInner && rechargeInner.getStatus() == 0) {
                    map.put("status", 0);
                    map.put("remark", "支付成功");
                    map.put("id", id);
                    map.put("orderNo", rechargeInner.getOrderNo());
                }else {
                    map.put("status", 2);
                    map.put("remark", "支付失败");
                    map.put("id", id);
                    map.put("orderNo", rechargeInner.getOrderNo());
                }
            }
        }
        
        //查询支付订单是否成功
        if(null != recharge) {
            if(recharge.getOrderType().equals(OrderTypeEnum.INVESTMENT.getFeatureName())) {
                Investment investment = investmentService.get(id);
                map.put("id", investment.getId());
                map.put("orderNo", investment.getOrderNo());
            }else if(recharge.getOrderType().equals(OrderTypeEnum.GOODS.getFeatureName())) {
                GoodsOrder goodsOrder = goodsOrderService.getMapper().selectByPrimaryKey(id);
                map.put("id", goodsOrder.getId());
                map.put("orderNo", goodsOrder.getOrderNo());
            }else if(recharge.getOrderType().equals(OrderTypeEnum.RECHARGE.getFeatureName())) {
                map.put("id", recharge.getId());
                map.put("orderNo", recharge.getOrderNo());
            }
            if(recharge.getPayChannel().equals(OutPayEnum.ALLINPAY.getFeatureName())) {
                AllinPayRequest qpay = new AllinPayRequest();
                qpay.setOrderNo(recharge.getOrderNo());
                ServiceMessage msg = new ServiceMessage("allinpay.order.query", qpay);
                AllinPayResponse qresult = (AllinPayResponse) OpenApiClient.getInstance()
                        .setServiceMessage(msg).send();
                if(PayConstants.RESP_CODE_SUCCESS.equals(qresult.getTrxStatus())) {
                    map.put("status", 0);
                    map.put("remark", "支付成功");
                }else if("2000".equals(qresult.getTrxStatus())){
                    map.put("status", 1);
                    map.put("remark", "处理中");
                }else {
                    map.put("status", 2);
                    map.put("remark", qresult.getErrorMsg());
                }
            }else if(recharge.getPayChannel().equals(OutPayEnum.YEEPAY.getFeatureName())){
                YeePayRequest qpay = new YeePayRequest();
                qpay.setOrderNo(recharge.getOrderNo());
                ServiceMessage msg = new ServiceMessage("yeepay.order.query", qpay);
                QueryYeePayResponse qresult = (QueryYeePayResponse) OpenApiClient.getInstance()
                        .setServiceMessage(msg).send();
                if("SUCCESSED".equals(qresult.getStatus())) {
                    map.put("status", 0);
                    map.put("remark", "支付成功");
                }else if("PROCESSING".equals(qresult.getStatus())) {
                    map.put("status", 1);
                    map.put("remark", "处理中");
                }else {
                    map.put("status", 2);
                    map.put("remark", qresult.getErrorMsg());
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
                            return new AppResult(FAILED, "微信签名校验失败");
                        }
                        if(qresult.get("return_code").equals("SUCCESS") && qresult.get("result_code").equals("SUCCESS")) {
                            if("SUCCESS".equals(qresult.get("trade_state"))) {
                                map.put("status", 0);
                                map.put("remark", "支付成功");
                            }else if("USERPAYING".equals(qresult.get("trade_state"))) {
                                map.put("status", 1);
                                map.put("remark", "处理中");
                            }else {
                                map.put("status", 2);
                                map.put("remark", qresult.get("trade_state_desc"));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    map.put("status", 1);
                    map.put("remark", "处理中");
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
                                map.put("status", 0);
                                map.put("remark", "支付成功");
                            }else if("WAIT_BUYER_PAY".equals(alipay_response.getTradeStatus())) {
                                map.put("status", 1);
                                map.put("remark", "处理中");
                            }else {
                                map.put("status", 2);
                                map.put("remark", alipay_response.getMsg());
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
        }
        return new AppResult(SUCCESS, map);
    }
    
    @RequestMapping(value = "/recharge/allinpay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "通联网关充值")
    public AppResult rechargeSina(@ApiParam("金额") Double amount, @ApiParam("session") HttpSession
            session, @ApiParam("response") HttpServletResponse response, @ApiParam("request")HttpServletRequest request,
            @ApiParam("capImgCode") @RequestParam String capImgCode) {
        User user = (User) session.getAttribute("user");      
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        try {
            Map<String, Object> m = checkCapImgCode(Constants.CAP_IMG_CODE + user.getUsername(), capImgCode, request);
            if (!m.get(STATUS).equals(Status.OK)) {
                Map<String,String> map = new HashMap<String, String>();
                map.put("error", Constants.CAP_IMG_CODE);
                return new AppResult(FAILED, m.get("msg").toString(), map);
            }
//            if (amount <= 0 || amount < 5) {
//                return new AppResult(FAILED, "充值金额必须大于5元");
//            }
            
            if (amount <= 0 ) {
                return new AppResult(FAILED, "充值金额必须大于0元");
            }
            
            logger.info("==============" + user.getUsername() + "/////" + user.getTrueName() + "进入充值================");
            // 保存充值信息
            Recharge recharge = new Recharge();
            final String orderno = rechargeOrderIdGenerator.next(); //生成订单号
            // 保存充值信息
            Date d = new Date();
            recharge.setUserId(user.getId());
            recharge.setAmount(amount);
            recharge.setClient(ClientEnum.PC.getFeatureName());
            recharge.setOrderNo(orderno);
            recharge.setCreateDate(d);
            recharge.setUpdateDate(d);
            recharge.setStatus(1); // 默认处理中
            recharge.setOrderType(OrderTypeEnum.RECHARGE.getFeatureName());
            recharge.setOtherId(null);
            recharge.setPayChannel(OutPayEnum.ALLINPAY_GATEWAY.getFeatureName());
            rechargeService.save(recharge);
            if(recharge.getOrderType().equals(OrderTypeEnum.RECHARGE.getFeatureName())) {
            	recharge.setOtherId(recharge.getId());
            	rechargeService.update(recharge);
            }
            
            //记录发送日志报文
            TradeMessageLog tradeMessageLog = TradeMessageLog.createTradeMessageLogFactory(PayConstants.FUIOU_SUBMIT, recharge.getPayChannel(),
                recharge.getPayChannel(), TradeMessageTypeEnum.SEND.getFeatureName(), String.valueOf(recharge.getUserId()),
                    recharge.getOrderNo(), null, null, null, "true", TradeMessageStatusEnum.YIWANCHENG.getFeatureName());
            //记录发送充值报文
            if (!tradeMessageLogService.saveTradeMessageLog(tradeMessageLog)) {
                logger.info("交易报文日志创建失败！");
                return null;
            }
            PrintWriter pw = null;
            try {
                BaseOutPay baseOutPay = new AllinGateWayPay();
                baseOutPay = baseOutPay.setSubOutPay(orderno, amount, null);
                pw = response.getWriter();
                response.setCharacterEncoding("utf-8");
                StringBuffer sb = new StringBuffer();
                sb.append(
                        "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>");
                sb.append(baseOutPay.getJumpParams());
                sb.append("</body></html>");
                pw.print(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
            } finally {
                if (null != pw) {
                    pw.close();
                }
            }
            return null;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }
    
    /**
     * 易宝支付回调地址
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/backRechargeByYeepay")
    public void backRechargeByYeepay(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("易宝回调进入");
        PrintWriter pw = null;
        String responseMsg = request.getParameter("response");
        DigitalEnvelopeDTO dto = new DigitalEnvelopeDTO();
        dto.setCipherText(responseMsg);
        try {
            request.setCharacterEncoding(Constants.UTF8);
            //设置商户私钥
            PrivateKey privateKey = YeeOutBack.getPrivateKey(PayConstants.YEEPAY_PRIVATE_KEY);
            //设置易宝公钥
            PublicKey publicKey = YeeOutBack.getPublicKey(PayConstants.YEEPAY_PUBLIC_KEY);
            //解密验签
            dto = DigitalEnvelopeUtils.decrypt(dto, privateKey, publicKey);
            //打印回调数据
            logger.info(dto.getPlainText());
            Map<String,String> jsonMap  = new HashMap<>();
            jsonMap = YeeOutBack.parseResponse(dto.getPlainText());
            //支付订单号
            final String outPayId = jsonMap.get("uniqueOrderNo");
            if(null != outPayId && !"".equals(outPayId)){
                logger.info("outPayId="+outPayId+";from "+OutPayEnum.YEEPAY.getFeatureName()+" callback");
                //返回状态信息描述
                final String backInfo = jsonMap.get("bankTrxId");
                //返回状态code
                String status = jsonMap.get("status");
                //成功code
                String successStatusCode = "SUCCESS";
                boolean messageStatus = status.equals(successStatusCode) ? true : false;

                //查询充值单
                Recharge recharge = rechargeService.getByOrder(jsonMap.get("orderId"));
                if (recharge == null) {
                    logger.info("============找不到该笔充值订单=============");
                    response.getWriter().print(successStatusCode);
                    return;
                }
                if (messageStatus && recharge.getStatus()==0 ) {
                    logger.info("==============易宝通知该次充值已经成功，无需通知==============");
                    response.getWriter().print(successStatusCode);
                    return;
                }
                if (!messageStatus && recharge.getStatus()==2) {
                    logger.info("==============易宝通知该次充值已经失败，无需通知==============");
                    response.getWriter().print(successStatusCode);
                    return;
                }
                if (recharge.getStatus()!=1) {
                    logger.info("==============充值单状态不是处理中,不能执行完成支付中支付单方法==============");
                    return;
                }
                //保存回调记录
                //发送请求前保存充值单，增加报文日志
                //判断该笔记录是否已经成功充值
                TradeMessageLog tradeMessageLog = tradeMessageLogService.getTradeMessageLog(outPayId, TradeMessageTypeEnum.BACK.getFeatureName());
                if(tradeMessageLog == null){
                    tradeMessageLog = TradeMessageLog.createTradeMessageLogFactory(CommonUtil.getIpAddr(request), String.valueOf(recharge.getPayChannel()),
                            String.valueOf(recharge.getPayChannel()), TradeMessageTypeEnum.BACK.getFeatureName(), String.valueOf(recharge.getUserId()),
                            recharge.getOrderNo(), outPayId, null, backInfo,  String.valueOf(messageStatus), TradeMessageStatusEnum.WEIWANCHENG.getFeatureName());
                    //添加临时表
                    tradeMessageLogService.saveTradeMessageLog(tradeMessageLog);
                }else{
                    tradeMessageLog.setMessageInfo(backInfo);
                    tradeMessageLog.setMessageStatus(String.valueOf(messageStatus));
                    TradeMessageLogVO tradeMessageLogVO = new TradeMessageLogVO();
                    tradeMessageLogVO.setMessageInfo(backInfo);
                    tradeMessageLogVO.setMessageStatus(String.valueOf(messageStatus));
                    tradeMessageLogVO.setEditStatus(TradeMessageStatusEnum.WEIWANCHENG.getFeatureName());
                    tradeMessageLogVO.setInOrderId(recharge.getOrderNo());
                    tradeMessageLogVO.setMessageType(TradeMessageTypeEnum.BACK.getFeatureName());
                    tradeMessageLogService.updateTradeMessageLogStatus(tradeMessageLogVO);
                }
                recharge.setAmount(new Money(jsonMap.get("payAmount")).getAmount().doubleValue());
                recharge.setRemark(backInfo);
                recharge.setOutOrderNo(outPayId);
                //增加银行关联
                String bankCode = jsonMap.get("bankId");
                if(StringUtils.isNotEmpty(bankCode)) {
                    logger.info("bankCode="+bankCode);
                    logger.info("bankCode change = "+YeeOutPay.getCxkBank(bankCode));
                    Bank bank = bankService.getByCode(YeeOutPay.getCxkBank(bankCode), 3);
                    if(null != bank) {
                        recharge.setBankId(bank.getId());
                    }
                }
                Assets assets = assetsService.findByuserId(recharge.getUserId()); // 获取他的资产，取得可用余额
                if(messageStatus){
                    recharge.setStatus(0);
                    if(rechargeService.updateRecord(recharge, assets, null)){
                        //通知银行处理成功
                        pw = response.getWriter();
                        //成功后的返回信息
                        pw.print(successStatusCode);
                    }
                } else {
                    recharge.setStatus(2);
                    rechargeService.updateRecord(recharge, assets, null);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if(null != pw){
                pw.close();
            }
        }
    }
    
    @RequestMapping(value = "/wxpay/notify")
    public void wxpayNotify(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("微信回调进入");
        PrintWriter pw = null;
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            request.setCharacterEncoding(Constants.UTF8);
            InputStream inStream = request.getInputStream();
            int _buffer_size = 1024;
            if (inStream != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] tempBytes = new byte[_buffer_size];
                int count = -1;
                while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
                    outStream.write(tempBytes, 0, count);
                }
                tempBytes = null;
                outStream.flush();
                //将流转换成字符串
                String strXML = new String(outStream.toByteArray(), "UTF-8");
                logger.info("strXML="+strXML);
                //将字符串解析成XML
                resultMap = WXPayUtil.xmlToMap(strXML);
            }
            //验证签名
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config, false, false);
            if (!wxpay.isResponseSignatureValid(resultMap)) {
                logger.info("==============微信签名校验失败==============");
                return;
            }
            //支付订单号
            final String outPayId = resultMap.get("transaction_id");
            if(null != outPayId && !"".equals(outPayId)){
                logger.info("outPayId="+outPayId+";from "+OutPayEnum.WXPAY.getFeatureName()+" callback");
                //返回状态信息描述
                final String backInfo = resultMap.get("return_msg");
                //返回状态code
                String status = resultMap.get("result_code");
                //成功code
                String successStatusCode = "SUCCESS";
                boolean messageStatus = status.equals(successStatusCode) ? true : false;

                //查询充值单
                Recharge recharge = rechargeService.getByOrder(resultMap.get("out_trade_no"));
                if (recharge == null) {
                    logger.info("============找不到该笔充值订单=============");
                    response.getWriter().print(successStatusCode);
                    return;
                }
                if (messageStatus && recharge.getStatus()==0 ) {
                    logger.info("==============微信通知该次充值已经成功，无需通知==============");
                    response.getWriter().print(successStatusCode);
                    return;
                }
                if (!messageStatus && recharge.getStatus()==2) {
                    logger.info("==============微信通知该次充值已经失败，无需通知==============");
                    response.getWriter().print(successStatusCode);
                    return;
                }
                if (recharge.getStatus()!=1) {
                    logger.info("==============充值单状态不是处理中,不能执行完成支付中支付单方法==============");
                    return;
                }
                //保存回调记录
                //发送请求前保存充值单，增加报文日志
                //判断该笔记录是否已经成功充值
                TradeMessageLog tradeMessageLog = tradeMessageLogService.getTradeMessageLog(outPayId, TradeMessageTypeEnum.BACK.getFeatureName());
                if(tradeMessageLog == null){
                    tradeMessageLog = TradeMessageLog.createTradeMessageLogFactory(CommonUtil.getIpAddr(request), String.valueOf(recharge.getPayChannel()),
                            String.valueOf(recharge.getPayChannel()), TradeMessageTypeEnum.BACK.getFeatureName(), String.valueOf(recharge.getUserId()),
                            recharge.getOrderNo(), outPayId, null, backInfo,  String.valueOf(messageStatus), TradeMessageStatusEnum.WEIWANCHENG.getFeatureName());
                    //添加临时表
                    tradeMessageLogService.saveTradeMessageLog(tradeMessageLog);
                }else{
                    tradeMessageLog.setMessageInfo(backInfo);
                    tradeMessageLog.setMessageStatus(String.valueOf(messageStatus));
                    TradeMessageLogVO tradeMessageLogVO = new TradeMessageLogVO();
                    tradeMessageLogVO.setMessageInfo(backInfo);
                    tradeMessageLogVO.setMessageStatus(String.valueOf(messageStatus));
                    tradeMessageLogVO.setEditStatus(TradeMessageStatusEnum.WEIWANCHENG.getFeatureName());
                    tradeMessageLogVO.setInOrderId(recharge.getOrderNo());
                    tradeMessageLogVO.setMessageType(TradeMessageTypeEnum.BACK.getFeatureName());
                    tradeMessageLogService.updateTradeMessageLogStatus(tradeMessageLogVO);
                }
                recharge.setAmount(new Money(resultMap.get("cash_fee")).divide(100).getAmount().doubleValue());
                recharge.setRemark(backInfo);
                recharge.setOutOrderNo(outPayId);
                Assets assets = assetsService.findByuserId(recharge.getUserId()); // 获取他的资产，取得可用余额
                if(messageStatus){
                    recharge.setStatus(0);
                    if(rechargeService.updateRecord(recharge, assets, null)){
                        //通知银行处理成功
                        pw = response.getWriter();
                        //成功后的返回信息
                        pw.print(successStatusCode);
                    }
                } else {
                    recharge.setStatus(2);
                    rechargeService.updateRecord(recharge, assets, null);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if(null != pw){
                pw.close();
            }
        }
    }

    @RequestMapping(value = "/alipay/notify")
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("支付宝回调进入");
        PrintWriter pw = null;
        try {
          //获取支付宝POST过来反馈信息
            Map<String,String> params = new HashMap<String,String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            //支付宝交易号
            String outPayId = request.getParameter("trade_no");
            //交易状态
            String trade_status = request.getParameter("trade_status");
            //交易金额
            String total_amount = request.getParameter("total_amount");

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            //计算得出通知验证结果
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
            if(!verify_result){//验证成功
                logger.info("==============支付宝签名校验失败==============");
                return;
            }

            if(null != outPayId && !"".equals(outPayId)){
                logger.info("outPayId="+outPayId+";from "+OutPayEnum.ALPAY.getFeatureName()+" callback");
                //返回状态信息描述
                final String backInfo = trade_status;
                //成功code
                String successStatusCode = "TRADE_SUCCESS";
                boolean messageStatus = trade_status.equals(successStatusCode) ? true : false;

                //查询充值单
                Recharge recharge = rechargeService.getByOrder(out_trade_no);
                if (recharge == null) {
                    logger.info("alipay callback:============找不到该笔充值订单=============,num:"+recharge.getOrderNo());
                    response.getWriter().print(successStatusCode);
                    return;
                }
                if (messageStatus && recharge.getStatus()==0 ) {
                    logger.info("alipay callback:==============通知该次充值已经成功，无需通知==============,num:"+recharge.getOrderNo());
                    response.getWriter().print(successStatusCode);
                    return;
                }
                if (!messageStatus && recharge.getStatus()==2) {
                    logger.info("alipay callback:==============通知该次充值已经失败，无需通知==============,num:"+recharge.getOrderNo());
                    response.getWriter().print(successStatusCode);
                    return;
                }
                if (recharge.getStatus()!=1) {
                    logger.info("alipay callback:==============充值单状态不是处理中,不能执行完成支付中支付单方法==============,num:"+recharge.getOrderNo());
                    return;
                }
                if(BigDecimalUtil.parse(recharge.getAmount()).compareTo(BigDecimalUtil.parse(total_amount))!=0){
                    logger.info("alipay callback:==============接口返回的支付金额与订单充值的金额不致,num:"+recharge.getOrderNo());
                    return;
                }

                //保存回调记录
                //发送请求前保存充值单，增加报文日志
                //判断该笔记录是否已经成功充值
                TradeMessageLog tradeMessageLog = tradeMessageLogService.getTradeMessageLog(outPayId, TradeMessageTypeEnum.BACK.getFeatureName());
                if(tradeMessageLog == null){
                    tradeMessageLog = TradeMessageLog.createTradeMessageLogFactory(CommonUtil.getIpAddr(request), String.valueOf(recharge.getPayChannel()),
                            String.valueOf(recharge.getPayChannel()), TradeMessageTypeEnum.BACK.getFeatureName(), String.valueOf(recharge.getUserId()),
                            recharge.getOrderNo(), outPayId, null, backInfo,  String.valueOf(messageStatus), TradeMessageStatusEnum.WEIWANCHENG.getFeatureName());
                    //添加临时表
                    tradeMessageLogService.saveTradeMessageLog(tradeMessageLog);
                }else{
                    tradeMessageLog.setMessageInfo(backInfo);
                    tradeMessageLog.setMessageStatus(String.valueOf(messageStatus));
                    TradeMessageLogVO tradeMessageLogVO = new TradeMessageLogVO();
                    tradeMessageLogVO.setMessageInfo(backInfo);
                    tradeMessageLogVO.setMessageStatus(String.valueOf(messageStatus));
                    tradeMessageLogVO.setEditStatus(TradeMessageStatusEnum.WEIWANCHENG.getFeatureName());
                    tradeMessageLogVO.setInOrderId(recharge.getOrderNo());
                    tradeMessageLogVO.setMessageType(TradeMessageTypeEnum.BACK.getFeatureName());
                    tradeMessageLogService.updateTradeMessageLogStatus(tradeMessageLogVO);
                }
//                recharge.setAmount(Double.valueOf(total_amount));
                recharge.setRemark(backInfo);
                recharge.setOutOrderNo(outPayId);
                Assets assets = assetsService.findByuserId(recharge.getUserId()); // 获取他的资产，取得可用余额
                if(messageStatus){
                    recharge.setStatus(0);
                    if(rechargeService.updateRecord(recharge, assets, null)){
                        //通知银行处理成功
                        pw = response.getWriter();
                        //成功后的返回信息
                        pw.print(successStatusCode);
                    }
                } else {
                    recharge.setStatus(2);
                    rechargeService.updateRecord(recharge, assets, null);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if(null != pw){
                pw.close();
            }
        }
    }

    public static void main(String[] args) {
        String str = "<form name=\"punchout_form\" method=\"post\" action=\"https://openapi.alipay.com/gateway.do?charset=UTF-8&method=alipay.trade.wap.pay&sign=mPxZLq4j%2BMY%2Bsk7A7XfNq7ZdX7Nj6oNCimAJOCXzJcViaUuMc0Gk9vsp68mYEyh8QDWLEmdc46d9sq7exxw78jZSrV1s4peuLmZJupIjUiXnifKzX9gokxoA%2F1N9fEE2CDjZTi1mde9qHB9m5bKXp6AjbDyAZTmI%2Bzz%2BHcVUw10BiX580Y7pBIBjQ685A3b9V1hY0ceoHN2Na2ir6HuCHscWXMC1GKDjaLgWd%2FkyXzMcAPMp7MBhPY43q4wLWyGTwf561WwhrC120uvqBAxS6O%2FIHTYRjhLFQkmlFVlflFTHscxUN70BmuFZDfPaSxK7e8SneYrc1%2Fm6yyef4Kabsw%3D%3D&return_url=https%3A%2F%2Fapp.bfmuchang.com%2Fassets%2Falipay%2Fnotify&notify_url=https%3A%2F%2Fwap.bfmuchang.com%2FpayResult.html%3Ftoken%3D883e5496a30c30b180ef70446a0681a2f2d86545f8de0fc88c14b5a2db1022dd%26appVersion%3D1.2.0%26client%3DAndroid%26orderNo%3DP19112709010574436%26orderType%3Dgoods%26id%3D456&version=1.0&app_id=2019112169317311&sign_type=RSA2&timestamp=2019-11-27+09%3A01%3A57&alipay_sdk=alipay-sdk-java-3.3.0&format=json\">\n" +
            "<input type=\"hidden\" name=\"biz_content\" value=\"{&quot;body&quot;:&quot;商城牛肉品种&quot;,&quot;out_trade_no&quot;:&quot;P19112709010574436&quot;,&quot;product_code&quot;:&quot;QUICK_WAP_WAY&quot;,&quot;subject&quot;:&quot;商城牛肉品种&quot;,&quot;timeout_express&quot;:&quot;2m&quot;,&quot;total_amount&quot;:&quot;29.9&quot;}\">\n" +
            "<input type=\"submit\" value=\"立即支付\" style=\"display:none\" >\n" +
            "</form>\n" +
            "<script>document.forms[0].submit();</script>";
        System.out.println(URLDecoder.decode(str));
        System.out.println("<form name=\"punchout_form\" method=\"post\" action=\"https://openapi.alipay.com/gateway.do?charset=UTF-8&method=alipay.trade.wap.pay&sign=n3IPC5lR7MCvcS5e1mZv%2FC%2B8MuR2vl08ZlQX9sILuUuoOEnYUgAwHCssbSQG5u%2B5eAnC35YwlPS2vRVxndNHu%2FAyZ%2FYh1dM3mD1H11sCjZUoPIeEG8UFfmZ%2FfHuu%2BMK1lBmEYlwdgSYkWPSQPqkRHIZuUQ1V7JjxhdvbRe2RGc8XCa8RSvIkvVJFv%2FHeEHG6NJP5kp4Xk1O9IC22g3naeo19sLI%2FPSroawnF14wpmTxs0J1fhJaSG4dfleIwnY9ABmi%2F5OKoIrxfPTAg4XL%2BsrlEB5DIK6jZvAQ4AgtD6p70u54YNSQA5i%2Bx9fpcm1SnOZIx0dFCysvS3VKa5x6BUA%3D%3D&return_url=https%3A%2F%2Fapp.bfmuchang.com%2Fassets%2Falipay%2Fnotify&notify_url=https%3A%2F%2Fwap.bfmuchang.com%2FpayResult.html%3Ftoken%3D883e5496a30c30b180ef70446a0681a2f2d86545f8de0fc88c14b5a2db1022dd%26appVersion%3D1.2.0%26client%3DAndroid%26orderNo%3DP19112709180430877%26orderType%3Dgoods%26id%3D458&version=1.0&app_id=2019112169317311&sign_type=RSA2&timestamp=2019-11-27+09%3A18%3A43&alipay_sdk=alipay-sdk-java-3.3.0&format=json\">\n" +
            "<input type=\"hidden\" name=\"biz_content\" value=\"{&quot;body&quot;:&quot;商城牛肉品种&quot;,&quot;out_trade_no&quot;:&quot;P19112709180430877&quot;,&quot;product_code&quot;:&quot;QUICK_WAP_WAY&quot;,&quot;subject&quot;:&quot;商城牛肉品种&quot;,&quot;timeout_express&quot;:&quot;2m&quot;,&quot;total_amount&quot;:&quot;29.9&quot;}\">\n" +
            "<input type=\"submit\" value=\"立即支付\" style=\"display:none\" >\n" +
            "</form>\n" +
            "<script>document.forms[0].submit();</script>");
    }
}
