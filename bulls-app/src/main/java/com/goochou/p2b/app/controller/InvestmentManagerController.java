package com.goochou.p2b.app.controller;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.*;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.constant.pasture.OrderPinStateEnum;
import com.goochou.p2b.constant.pasture.OrderStateEnum;
import com.goochou.p2b.constant.pasture.ProjectStatusEnum;
import com.goochou.p2b.dao.ProjectViewMapper;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.TransactionClient;
import com.goochou.p2b.hessian.transaction.OrderResponse;
import com.goochou.p2b.hessian.transaction.investment.*;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.idGenerator.OrderIdGenerator;
import com.goochou.p2b.model.vo.BannerIndexVO;
import com.goochou.p2b.model.vo.DetailsVO;
import com.goochou.p2b.model.vo.InvestmentDetailsVO;
import com.goochou.p2b.model.vo.InvestmentOrderListVO;
import com.goochou.p2b.model.vo.OrderBeforeDetailVO;
import com.goochou.p2b.model.vo.ProjectView;
import com.goochou.p2b.model.vo.ProjectViewExample;
import com.goochou.p2b.service.*;
import com.goochou.p2b.utils.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/investment")
@Api(value = "投资相关-investment")
public class InvestmentManagerController extends BaseController {
    private static final Logger logger = Logger.getLogger(InvestmentManagerController.class);
    @Resource
    private ProjectService projectService;
    @Resource
    private InvestmentService investmentService;
    @Resource
    private InterestService interestService;
    @Resource
    private BankCardService bankCardService;
    @Resource
    private AssetsService assetsService;
    @Resource
    private AppVersionService appVersionService;
    @Resource
    private UserSignedService userSignedService;
    @Resource
    private RateCouponService rateCouponService;
    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private ActivityService activityService;
    @Resource
    private MessageService messageService;
    @Resource
    private TradeRecordService tradeRecordService;
    @Resource
    private PastureOrderService pastureOrderService;
    @Resource
    private EnterpriseService enterpriseService;
    @Autowired
    private OrderIdGenerator bullsOrderIdGenerator;
    @Autowired
    private ProductService productService;
    @Resource
    private MigrationInvestmentService migrationInvestmentService;
    @Resource
    private MigrationInvestmentBillService migrationInvestmentBillService;
    @Resource
    private RechargeService rechargeService;
    

    /**
     * @param aoeType       交易记录类型 0投资1充值2提现3收益4回收本金5债权转让回收本金6认购债权7赎回8红包9复利10活期转定期（债权）
     * @param appVersion 版本号
     * @param token      用户token
     * @param source     用户投资类型   0定期  1活期
     * @param page       页码
     * @return
     * @Title: InvestmentManagerController.java
     * @Package com.goochou.p2b.app.controller
     * @Description(描述):交易记录
     * @author 王信
     * @date 2016年3月3日 下午6:41:48
     * @version V1.0
     */
    @RequestMapping(value = "/trade", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "交易记录")
    public AppResult tradeRecord(
            @ApiParam("交易记录类型") @RequestParam String aoeType,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("分页当前页") @RequestParam Integer page,
            @ApiParam("分页当前页") @RequestParam(required = false) Integer limit) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            if (limit == null || limit <= 0) {
                limit = 20;
            }
            page = page == null ? 1 : page;
            if (page < 1) {
                page = 1;
            }
            List<TradeRecord> list = tradeRecordService.findByUserIdApp(user.getId(), null, null, aoeType, (page - 1) * limit, limit);
            Integer count = tradeRecordService.findByUserIdAppCount(user.getId(), null, null, aoeType);
            int pages = calcPage(count, limit);
            if (list.size() == 0) {
                map.put("list", null);
            } else {
                map.put("list", list);
            }
            map.put("page", page);
            map.put("pages", pages);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
        return new AppResult(SUCCESS, map);
    }

    /**
     * Author:xinjiang 交记录详情
     *
     * @param token
     * @param id    交易记录ID
     * @return
     */
    @RequestMapping(value = "/trade/detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "交易记录详情")
    public AppResult tradeRecordDetail(HttpServletResponse response,
                                       @ApiParam("交易记录ID") @RequestParam Integer id,
                                       @ApiParam("App版本号") @RequestParam String appVersion,
                                       @ApiParam("用户token") @RequestParam String token) {
        Map<String, Object> map = null;
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            map = tradeRecordService.detailApp(id);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
        return new AppResult(SUCCESS, map);
    }

    /**
     * 认购物权下单
     * @param projectId 投资项目ID
     * @param token 用户token
     * @param appVersion App版本号
     * @param balanceAmount 余额支付金额
     * @param client 客户端（IOS,Android,PC,WAP）
     * @return
     */
    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "认购物权")
    public AppResult submitOrder(HttpServletRequest request,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
            //@ApiParam("订单编号") @RequestParam String orderNo,
            @ApiParam("投资项目ID") @RequestParam Integer projectId,
            @ApiParam("余额支付金额") @RequestParam Double balanceAmount,
            @ApiParam("客户端（IOS,Android,PC,WAP）") @RequestParam String client,
            @ApiParam("红包ID") @RequestParam(required=false) Integer hongbaoId,
            @ApiParam("是否启用默认余额扣款") @RequestParam(required=false) boolean isAutoUseBalance,
            @ApiParam("拼牛份数")@RequestParam(required=false) Integer point,
            @ApiParam("拼牛金额")@RequestParam(required=false) Double pinAmount) {
        long start = System.currentTimeMillis();
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }

            //判断用户是否实名
//            if(null == user.getTrueName() || null == user.getIdentityCard()) {
//                return new AppResult(FAILED, "请先实名认证", "");
//            }
            if (null == balanceAmount) {
                balanceAmount = 0.00D;
            }
            long startapi = System.currentTimeMillis();
            InvestmentOrderRequest req = new InvestmentOrderRequest();
            req.setProjectId(projectId);
            //req.setOrderNo(orderNo);
            req.setClientEnum(ClientEnum.getValueByName(client));
            req.setBlancePayMoney(BigDecimalUtil.parse(balanceAmount));
            req.setUserId(user.getId());
            req.setAutoUseBalance(isAutoUseBalance);
            req.setIpAddress(getIpAddr(request));
            req.setHongbaoId(hongbaoId);
            req.setPoint(point);
            if(pinAmount!=null) {
            	req.setPinAmount(BigDecimal.valueOf(pinAmount));
            }
            
            
            ServiceMessage msg = new ServiceMessage("investorder.submit", req);
            Response response = TransactionClient.getInstance().setServiceMessage(msg).send();
            if(response instanceof OrderResponse) {
                OrderResponse orderResponse = (OrderResponse)response;
                if (orderResponse.isSuccess()) {
                    long endapi = System.currentTimeMillis();
                    logger.info((endapi-startapi)+"api ms");
                    long end = System.currentTimeMillis();
                    logger.info((end-start)+"ms");
                    Map<String, Object> map = new HashMap<>();
                    map.put("payResult", ClientConstants.H5_URL+"payResult.html?token="+token+"&appVersion="+appVersion+"&client="+client+"&orderNo="+orderResponse.getOrderNo()+"&orderType="+OrderTypeEnum.INVESTMENT.getFeatureName()+"&id="+orderResponse.getId()+"&investmentType="+orderResponse.getInvestmentType());
                    map.put("id", orderResponse.getId());
                    map.put("orderNo", orderResponse.getOrderNo());
                    map.put("needPayMoney", orderResponse.getNeedPayMoney());
                    map.put("availableMoney", orderResponse.getAvailableMoney());
                    map.put("availableCreditMoney", orderResponse.getAvailableCreditMoney());
                    map.put("orderType", orderResponse.getOrderType());
                    return new AppResult(SUCCESS, "操作成功", map);
                }else{
                    return new AppResult(FAILED, orderResponse.getErrorMsg(), orderResponse);
                }
            }else{
                return new AppResult(FAILED, "请求接口出现问题", response);
            }

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * 认购物权订单取消
     * @param token 用户token
     * @param appVersion App版本号
     * @param orderNo 投资订单编号
     * @param client 客户端（IOS,Android,PC,WAP）
     * @return
     */
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "认购物权订单取消")
    public AppResult cancelOrder(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("订单编号") @RequestParam String orderNo,
            @ApiParam("客户端（IOS,Android,PC,WAP）") @RequestParam String client) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }

            InvestmentOrderRequest req = new InvestmentOrderRequest();
            req.setOrderNo(orderNo);
            req.setUserId(user.getId());
            req.setClientEnum(ClientEnum.getValueByName(client));
            ServiceMessage msg = new ServiceMessage("investorder.cancel", req);
            Response response = TransactionClient.getInstance().setServiceMessage(msg).send();

            return getHessianResult(response,"取消认领订单成功",response.getErrorMsg());

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
    @Resource
	private ProjectViewMapper projectViewMapper;
    /**
     * @description 确认订单
     * @author shuys
     * @date 2019/5/21
     * @param appVersion
     * @param token
     * @param projectId
     * @return com.goochou.p2b.app.model.AppResult
    */
    @RequestMapping(value = "/before/submitOrder", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "确认订单") // 提交订单页面详情
    public AppResult beforeSubmitOrder(
            @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("项目ID") @RequestParam Integer projectId,
            @ApiParam("是否是续购 1是0否")@RequestParam(required = false) Integer buyAgain,
            @ApiParam("拼牛购买份数")@RequestParam(required = false) Integer point,
            @ApiParam("拼牛份数")@RequestParam(required=false) Double pinAmount) {

        User user = userService.checkLogin(token);
        if (user == null) {
            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
        }
        Integer userId = user.getId();
        OrderBeforeDetailVO orderBeforeDetail = new OrderBeforeDetailVO();
        Enterprise enterprise = enterpriseService.selectFirstRecord(); // TODO  只有一条数据，后期可能扩展
        orderBeforeDetail.setAppVersion(appVersion);
        orderBeforeDetail.setUserId(userId);
        orderBeforeDetail.setEnterpriseName(enterprise.getName());

        try {
            Project project = projectService.getProjectById(projectId);
            ProjectViewExample example=new ProjectViewExample();
            example.createCriteria().andIdEqualTo(project.getId());
            List<ProjectView> projectViewList=projectViewMapper.selectByExample(example);
            ProjectView projectView=null;
            if(projectViewList.size()==1){
            	projectView=projectViewList.get(0);
            }else {
            	throw new Exception("牛只查询错误");
            }
            BigDecimal interestAmount = PayUtil.getInterestAmount(project, false);
            if(projectView.getProjectType().equals(ProjectTypeEnum.PINNIU.getFeatureType())) {
            	int cantBuyPoint=0;
            	//可售份数
            	int canBuyPoint=projectView.getPinResiduePoint();
            	List<InvestmentView> investmentViewList=investmentService.listInvestmentViewByProject(user.getId(), projectId, Arrays.asList(0,4));
            	BigDecimal sumInvestedAmount=BigDecimal.ZERO;
            	for(InvestmentView investmentView:investmentViewList) {
            		sumInvestedAmount=sumInvestedAmount.add(BigDecimal.valueOf(investmentView.getAmount()));
            	}
            	if(sumInvestedAmount.compareTo(BigDecimal.valueOf(projectView.getInvestedAmount())) >= 0) {
            		cantBuyPoint=1;
            	}
            	canBuyPoint=projectView.getPinResiduePoint()-cantBuyPoint;
            	if(point==null) {
            		point=canBuyPoint;
            	}
            	if(point>canBuyPoint) {
            		throw new Exception("购买超过可售份数");
            	}
            	if(pinAmount == null || projectView.getPinAmountForPoint(point).doubleValue() != pinAmount) {
            		throw new Exception("拼牛金额有变");
            	}
            	orderBeforeDetail.setTotalAmount(projectView.getPinAmountForPoint(point).doubleValue());
            	orderBeforeDetail.setRaiseFee(projectView.getPinRateForPoint(point).multiply(BigDecimal.valueOf(project.getRaiseFee())).setScale(2,BigDecimal.ROUND_FLOOR).doubleValue());
                orderBeforeDetail.setManageFee(projectView.getPinRateForPoint(point).multiply(BigDecimal.valueOf(project.getManageFee())).setScale(2,BigDecimal.ROUND_FLOOR).doubleValue());
                
                projectView.setInterestAmount(interestAmount.doubleValue());
                orderBeforeDetail.setInterestAmount(projectView.getPinInterestForPoint(point));
                orderBeforeDetail.setRateStr(new DecimalFormat("#.##%").format(projectView.getPinRateForPoint(point)));
                
                
                int hongbaoCount = hongbaoService.getHongbaoInverstmentCount(userId, 4, 0, orderBeforeDetail.getTotalAmount(), project.getLimitDays()); // 有效投资红包个数
                orderBeforeDetail.setHongbaoCount(hongbaoCount);
                orderBeforeDetail.setRightLabel("拼牛");
                orderBeforeDetail.setHongbaoTypeDescription("拼牛红包");
            }else {
            	
            	orderBeforeDetail.setTotalAmount(project.getTotalAmount());
            	orderBeforeDetail.setRaiseFee(project.getRaiseFee());
                orderBeforeDetail.setManageFee(project.getManageFee());
                
                orderBeforeDetail.setInterestAmount(interestAmount);
                
                int hongbaoCount = hongbaoService.getHongbaoInverstmentCount(userId, 2, 0, project.getTotalAmount(), project.getLimitDays()); // 有效投资红包个数
                orderBeforeDetail.setHongbaoCount(hongbaoCount);
                orderBeforeDetail.setHongbaoTypeDescription("牧场红包");
            }
            logger.info("interestAmount="+interestAmount);
            orderBeforeDetail.setBullType(projectView.getProjectType());
            orderBeforeDetail.setProjectId(project.getId());
            orderBeforeDetail.setTitle(project.getTitle());
            orderBeforeDetail.setAnnualized(project.getAnnualized());
            orderBeforeDetail.setLimitDays(project.getLimitDays());
            
            orderBeforeDetail.setRepayUnit(project.getRepayUnit());
            orderBeforeDetail.setEarNumber(project.getEarNumber());
            orderBeforeDetail.setSafeNumber(project.getSafeNumber());
            orderBeforeDetail.setTag(project.getTag());
            
            //是否实名
            if(null != user.getTrueName() && null != user.getIdentityCard()) {
                orderBeforeDetail.setRealName(Constants.YES);
            }else {
                orderBeforeDetail.setRealName(Constants.NO);
            }
            //是否可以购买
            if(project.getStatus()==ProjectStatusEnum.ENABLE_SALE.getCode()) {
                orderBeforeDetail.setAllowSale(Constants.YES);
            }else {
                orderBeforeDetail.setAllowSale(Constants.NO);
            }
            orderBeforeDetail.setBuyAgreeText("《认购及托养服务协议》");
            orderBeforeDetail.setBuyAgreeUrl(ClientConstants.H5_URL+"rl.html");
            orderBeforeDetail.setBackAgreeText("");
            orderBeforeDetail.setBackAgreeUrl(ClientConstants.H5_URL+"rl.html");
            orderBeforeDetail.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
            orderBeforeDetail.setSex(project.getSex());
            orderBeforeDetail.setAnnualizedStr(BigDecimalUtil.multi(project.getAnnualized(), 100)+"%");
            if(project.getRepayUnit().equals(RepayUnitEnum.DAY.getFeatureName())) {
                orderBeforeDetail.setLimitDaysStr(project.getLimitDays()+RepayUnitEnum.DAY.getDescription());
            }else if(project.getRepayUnit().equals(RepayUnitEnum.MONTH.getFeatureName())) {
                orderBeforeDetail.setLimitDaysStr(project.getLimitDays()+RepayUnitEnum.MONTH.getDescription());
            }
            orderBeforeDetail.setNoob(project.getNoob());
            
            orderBeforeDetail.setAllow(getCacheKeyValue(DictConstants.BUY_BULLS_CHECK));
            return new AppResult(SUCCESS, orderBeforeDetail);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * @date 2019年5月20日
     * @author wangyun
     * @time 下午4:21:47
     * @Description 我的牧场
     *
     * @param appVersion
     * @param token
     * @param status
     * @param page
     * @return
     */
    @RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "我的牧场")
    public AppResult getOrderList( @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("order_status") @RequestParam Integer orderStatus,
            @ApiParam("projectType") @RequestParam(required = false) Integer projectType , // 新增养牛类型, 0养牛，1拼牛
            @ApiParam("分页当前页") @RequestParam Integer page) {
    	try {
    		User user = userService.checkLogin(token);
    		if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
	        int limit = 20;
			if (page == null) {
			    page = 1;
			}
			projectType = (projectType == null) ? 0 : projectType;
    		InvestmentOrderListRequest request = new InvestmentOrderListRequest();
    		InvestmentOrderListResponse response = new InvestmentOrderListResponse();
    		request.setLimitEnd(limit);
    		request.setLimitStart((page - 1) * limit);
    		request.setOrderStatus(orderStatus);
    		request.setUserId(user.getId());
    		request.setProjectType(projectType);
    		ServiceMessage msg = new ServiceMessage("investorder.list", request);
    		response = (InvestmentOrderListResponse) TransactionClient.getInstance().setServiceMessage(msg).send();
    		if(response.isSuccess()) {
    		    Map<String, Object> map = new HashMap<String, Object>();
    		    for(int i=0;i<response.getList().size();i++) {
    		    	InvestmentOrderListVO order  = response.getList().get(i);
    		    	
    		        if (order.getRepayUnit().equals(RepayUnitEnum.MONTH.getFeatureName())) {
    		        	order.setLimitDayStr(order.getLimitDays() + RepayUnitEnum.MONTH.getDescription());
    	            } else if (order.getRepayUnit().equals(RepayUnitEnum.DAY.getFeatureName())) {
    	                order.setLimitDayStr(order.getLimitDays() + RepayUnitEnum.DAY.getDescription());
    	            }
    		        order.setPath(ClientConstants.ALIBABA_PATH + "upload/"+ order.getPath());
    		        long lockTime = 0;

                    String createDate = null == order.getCreateDate() ? null : DateUtil.dateFullTimeFormat.format(order.getCreateDate());
                    if (null != createDate) {
                        String date = DateUtil.addtime(createDate, getCacheKeyValue(DictConstants.PAY_WAIT_TIME));
                        if(date != null) {
                        	lockTime = DateUtil.dateDiffSecound(DateUtil.format(new Date(), DateTimeUtil.allPattern), date,
                                    DateTimeUtil.allPattern);
                        }
                    }
                    order.setCreateDateStr(DateUtil.dateFormat.format(order.getCreateDate()));
                    order.setAnnualizedStr(BigDecimalUtil.multi(order.getAnnualized(), 100)+"%");
                    order.setLockTime(lockTime);
                    order.setOrderType(OrderTypeEnum.INVESTMENT.getFeatureName());
                    if(order.getOrderStatus()==OrderStateEnum.BUYED.getCode()) {
                        order.setUpdateDateStr(DateUtil.dateFormat.format(order.getDeadline()));
                    }else if(order.getOrderStatus()==OrderStateEnum.SALED.getCode()) {
                        order.setUpdateDateStr(DateUtil.dateFormat.format(order.getUpdateDate()));
                    }
                    if(order.getPayStatus() == 0 || order.getPayStatus() == 1) {//未支付的显示lable
                    	order.setSexLable("性别");
                        order.setAnnualizedLable("饲养预计利润(年化)");
                        order.setLimitDayLable("饲养期");
                    }
                    order.setAmountLable("订单金额");
                    if(order.getProjectType() == ProjectTypeEnum.PINNIU.getFeatureType()) {
                    	 ProjectViewExample example=new ProjectViewExample();
                         example.createCriteria().andIdEqualTo(order.getProjectId());
                         List<ProjectView> projectViewList=projectViewMapper.selectByExample(example);
                         ProjectView projectView = null;
                         if(projectViewList.size() == 1){
                         	projectView = projectViewList.get(0);
                         }else {
                         	throw new Exception("牛只查询错误");
                         }
                         
                         order.setPinRateStr("所占比例");
                         order.setPinRateLable(projectView.getPinRateForAmount(order.getAmount()).multiply(BigDecimal.valueOf(100)).setScale(0)+"%");
                         if(order.getOrderStatus() == OrderPinStateEnum.BUYING.getCode()) {// 待成团
                        	 order.setPinResiduePoint(projectView.getPinResiduePoint()+"份");
                        	 order.setPinResiduePointLable("当前还差<%pinResiduePoint%>成团");
                         }
                         if(order.getOrderStatus() == OrderPinStateEnum.BUYED.getCode()) {// 已成团
                        	 order.setPinBuyedAmountLable("饲养期结束可获得  牛只分成(本金)：");
                        	 order.setPinInterestAmountLable("成长分成(利息)：");
                         }
                         if(order.getOrderStatus() ==  OrderPinStateEnum.SALED.getCode()) {// 已成团
                        	 order.setPinBuyedAmountLable("牛只分成(本金)：");
                        	 order.setPinInterestAmountLable("成长分成(利息)：");
                         }
    		    	}
    		    }
    		    int pages = calcPage(response.getCount(), limit);
    		    if (response.getList().size() == 0) {
                    map.put("list", null);
                } else {
                    map.put("list", response.getList());
                }
                map.put("page", page);
                map.put("pages", pages);
                map.put("count", response.getCount());
                
                List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
                Map<String, Object> orderStateMap=new HashMap<String, Object>();
                orderStateMap.put("code", "");
                orderStateMap.put("description", "全部");
                list.add(orderStateMap);
                List<Map<String, Object>> orderState = new ArrayList<Map<String, Object>>();
                
                if(getVersion(appVersion) >= 200 && projectType == ProjectTypeEnum.PINNIU.getFeatureType()) { // 2.0.0以上版本, 拼牛
                	orderState = OrderPinStateEnum.enumParseMap(list);
                } else {
                	orderState = OrderStateEnum.enumParseMap(list);
                }
                
                map.put("orderState",orderState);
    			return new AppResult(SUCCESS,"查询成功",map);
    		} else {
    			return new AppResult(FAILED,"查询失败");
    		}

		} catch (Exception e) {
			logger.error("获取牛只订单列表出错======>"+e.getMessage(),e);
	        return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
    }

   /**
    * @date 2019年5月21日
    * @author wangyun
    * @time 下午6:10:29
    * @Description 牛只订单详情
    *
    * @param appVersion
    * @param token
    * @param investId
    * @return
    */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "牛只订单详情")
    public AppResult getOrderDetail( @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("投资Id") @RequestParam Integer investId) {
    	try {
    		User user = userService.checkLogin(token);
	        if (user == null) {
	            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
	        }
	        boolean isPinFlag = false;
	        InvestOrderDetailRequest request = new InvestOrderDetailRequest();
	        request.setInvestId(investId);
	        ServiceMessage msg = new ServiceMessage("investorder.detail", request);
	        InvestOrderDetailResponse response = (InvestOrderDetailResponse) TransactionClient.getInstance().setServiceMessage(msg).send();
	        if(response.isSuccess()) {
    	        InvestmentDetailsVO detailsVO = response.getDetails();
    	        if (detailsVO.getUserId() != null && !user.getId().equals(detailsVO.getUserId())) {
                    return new AppResult(FAILED, PARAM_ERROR);
                }
                isPinFlag = detailsVO.getPin();
                if (isPinFlag) {
                    detailsVO.setCreateDateStr(DateUtil.dateTimeFormat.format(detailsVO.getCreateDate()));
                } else {
                    detailsVO.setCreateDateStr(DateUtil.dateFormat.format(detailsVO.getCreateDate()));
                }
    	        boolean flag = false;
    	        if(detailsVO.getOrderStatus()!=OrderStateEnum.NO_BUY.getCode()) {
    	            if (isPinFlag) {
                        if(detailsVO.getOrderStatus() != InvestmentStateEnum.waiting.getCode()) {
                        	detailsVO.setDeadlineStr(DateUtil.dateFormat.format(detailsVO.getDeadline()));
                        }
    	                // 已成团
                        if (detailsVO.getOrderStatus() == InvestmentStateEnum.buyed.getCode()) {
                            detailsVO.setSellDateStr(DateUtil.dateAdd("day", detailsVO.getLimitDays(), detailsVO.getDeadline()).toString());
                        }
                        // 已结算
                        if(detailsVO.getOrderStatus() == InvestmentStateEnum.saled.getCode()) {
                            detailsVO.setSellDateStr(DateUtil.dateFormat.format(detailsVO.getUpdateDate()));
                        }
                    } else {
                        detailsVO.setDeadlineStr(DateUtil.dateFormat.format(detailsVO.getDeadline()));
                        detailsVO.setSellDateStr(DateUtil.dateAdd("day", detailsVO.getLimitDays(), detailsVO.getDeadline()).toString());
                        flag = true;
                        
                        // 未到出售时间,我要出售按钮置灰
                        Date sellDate = DateUtil.parseDate(DateUtil.dateAdd("day", detailsVO.getLimitDays(), detailsVO.getDeadline()).toString());
                        Date now = new Date();
                        if (now.before(sellDate)) { // 当前时间 < 出售时间
                        	flag =false;
                        } 
                        
                        if(detailsVO.getOrderStatus()==OrderStateEnum.SALED.getCode()) {
                            flag = false;
                            detailsVO.setSellDateStr(DateUtil.dateFormat.format(detailsVO.getUpdateDate()));
                        }
                    }
    	        }
    	        Map<String, Object> map = new HashMap<String, Object>();
        		map = BeanToMapUtil.convertBean(detailsVO);
        		//牛只大图列表
        		Project project = projectService.get(detailsVO.getProjectId());
                List<BannerIndexVO> bigPics = new ArrayList<BannerIndexVO>();
                for(ProjectPicture picture : project.getPictures()) {
                    BannerIndexVO bigPic = new BannerIndexVO();
                    bigPic.setPictureUrl(picture.getUpload().getCdnPath());
                    bigPics.add(bigPic);
                }
                map.put("bigPics", bigPics);
        		//公共
        		map.put("lable_title", "项目名称");
                map.put("lable_sex", "性别");
                map.put("ear_number_lable", "耳标号");
        		map.put("details_text", "*饲养期满后可选择出售获得收益");
        		map.put("buyAgreeText", "《认购及托养服务协议》");
                map.put("buyAgreeUrl", ClientConstants.H5_URL+"rl.html?orderNo="+detailsVO.getOrderNo());
                map.put("backAgreeText", "");
                map.put("backAgreeUrl", ClientConstants.H5_URL+"rl.html?orderNo="+detailsVO.getOrderNo());
                map.put("lable_pinRate", "所占比例");
        		//未饲养（代付款）
                map.put("statusDesc", "等待买家付款");
                map.put("statusImg", ClientConstants.ALIBABA_PATH + "images/order/status/waiting_pay.png");
                map.put("submit_date", "下单时间");
                map.put("wtlimitDays", "委托饲养期限");
        		
        		if(getVersion(appVersion) >= 200) {
        			map.put("unitPriceLable", "牛只单价");
            		map.put("raiseFeeLable", "饲养费用");
            		map.put("manageFeeLable", "管理费用");
        		}else {
        			map.put("unitPriceLable", "认购单价(元)");
            		map.put("raiseFeeLable", "饲养费用(元)");
            		map.put("manageFeeLable", "管理费用(元)");
        		}
        		map.put("balancePayMoneyLable", "余额支付");
        		map.put("redbag_deduction", Constants.REDBAG_DEDUCTION);
        		map.put("sumLable", "合计");
        		//已饲养（已成团）
        		map.put("foster_total_price", Constants.FOSTER_TOTAL_PRICE);
        		map.put("foster_date", Constants.FOSTER_DATE);
        		map.put("lable_annualized", "饲养预计利润(年化)");
        		map.put("lable_interest", "饲养预计利润(元)");
        		map.put("lable_sell_date", "可卖日期");
        		map.put("lable_limit_days", "饲养期");
        		//已出售（已结算）
        		if(detailsVO.getOrderStatus()==OrderStateEnum.SALED.getCode()) {
        		    map.put("lable_annualized", "饲养利润(年化)");
                    map.put("lable_interest", "饲养利润(元)");
        		}
                // 待成团 
                map.put("totalAmountLable", "认养总价");
                map.put("statusLabel", "状态");

                List<DetailsVO> details = new ArrayList();
                DetailsVO v = new DetailsVO();
                
                // 拼牛详情特殊数据
        		if (isPinFlag) {
        		    if (detailsVO.getOrderStatus() == InvestmentStateEnum.no_buy.getCode()) {
                        // 代付款
                        map.put("topTips", "");
                        map.put("statusDesc", "等待买家付款");
                        map.put("statusDescExtend", "");
                        
                    } else if (detailsVO.getOrderStatus() == InvestmentStateEnum.waiting.getCode()) {
                        // 待成团 
                        map.put("topTips", "");
                        map.put("statusDesc", "等待他人加入");
                        map.put("statusDescExtend", "当前还差"+detailsVO.getPinResiduePoint()+"份成团");

                    } else if (detailsVO.getOrderStatus() == InvestmentStateEnum.buyed.getCode()) {
                        // 已成团（饲养期） 
                        map.put("topTips", "*饲养期满后系统统一卖牛分配分成");
                        map.put("statusDesc", "已成团");
                        map.put("statusDescExtend", "已计算"+DateUtil.dateToDateDay(detailsVO.getCreateDate(), new Date())+"天成长利润");
                        map.put("lable_sell_date", "预计出售日期");
                        map.put("foster_date", "成团日期");
        		        
                    } else if (detailsVO.getOrderStatus() == InvestmentStateEnum.saled.getCode()) {
                        // 已结算(已卖牛)
                        map.put("topTips", "*牛只分成(本金)与饲养利润已发放至余额，请注意查收");
                        map.put("statusDesc", "已结算");
                        map.put("statusDescExtend", "");
                        map.put("lable_sell_date", "出售日期");
                        map.put("foster_date", "成团日期");

                    }
                    map.put("statusMsg", InvestmentStateEnum.getValueByCode(detailsVO.getOrderStatus()).getPinDescription());

                    v = new DetailsVO();
                    v.setTitle("行动轨迹");
                    v.setShow(1);
                    v.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/mybulls/gps_icon.png");
                    v.setLink(ClientConstants.H5_URL+"bullsGps.html?earNumber=" + detailsVO.getEarNumber()+"&isRefresh=false");
                    details.add(v);
                } else {
                    v.setKey("myBullsSell");
                    v.setTitle("我要出售");
                    if(flag) {
                        v.setShow(1);
                        v.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/sell.png");
                    }else {
                        v.setShow(0);
                        v.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/sell_no.png");
                    }
                    details.add(v);
                    v = new DetailsVO();
                    v.setKey("myReport");
                    v.setTitle("收益账单");
                    v.setShow(1);
                    v.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/report.png");
                    details.add(v);

                    v = new DetailsVO();
                    v.setTitle("牛只定位");
                    v.setShow(1);
                    v.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/mybulls/gps_icon.png");
                    v.setLink(ClientConstants.H5_URL+"bullsGps.html?earNumber=" + detailsVO.getEarNumber()+"&isRefresh=false");
                    details.add(v);

                    if(detailsVO.getIsNoob() == 0) {
                        v = new DetailsVO();
                        v.setTitle("我要续养");
                        v.setShow(0);
                        v.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/mybulls/buyAgain.png");

                        boolean buyAgaginflag = false;
                        Integer bugAgainStatus = null; //跳转待付款页面
                        if (InvestmentStateEnum.buyed.getCode() == detailsVO.getOrderStatus()) {
                            // 饲养期
                            Date sellDate = DateUtil.parseDate(DateUtil.dateAdd("day", detailsVO.getLimitDays(), detailsVO.getDeadline()).toString());
                            Date now = new Date();
                            if (now.after(sellDate)) { // 当前时间 > 出售时间
                                buyAgaginflag = true; // 跳转 卖牛并续养 页面
                                bugAgainStatus = 1;
                            }
                        } else if (InvestmentStateEnum.saled.getCode() == detailsVO.getOrderStatus()) {
                            // 已卖牛
                            // 跳转 续养 页面
                            buyAgaginflag = true;
                            bugAgainStatus = 1;

                        }

                        if(buyAgaginflag && bugAgainStatus == 1) {
                            v.setShow(1);
                            v.setLink(ClientConstants.H5_URL+"sellBullsjoin.html?token=" + token+"&investId="+ investId+"&showNav=0");
                        }
                        details.add(v);
                    }
                }
               
//                v = new DetailsVO();
//                v.setLink("http://www.baidu.com/");
//                v.setTitle("健康状态");
//                if(flag) {
//                    v.setShow(1);
//                    v.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/skill.png");
//                }else {
//                    v.setShow(0);
//                    v.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/skill_no.png");
//                }
//                details.add(v);
//                v = new DetailsVO();
//                v.setLink("http://www.baidu.com/");
//                v.setTitle("行动轨迹");
//                if(flag) {
//                    v.setShow(1);
//                    v.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/move.png");
//                }else {
//                    v.setShow(0);
//                    v.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/move_no.png");
//                }
//                details.add(v);
                map.put("lable_detail", details);
        		long lockTime = 0;
                String createDate = null == detailsVO.getCreateDate() ? null : DateUtil.dateFullTimeFormat.format(detailsVO.getCreateDate());
                if (null != createDate) {
                    String date = DateUtil.addtime(createDate, getCacheKeyValue(DictConstants.PAY_WAIT_TIME));
                    if(date != null) {
                        lockTime = DateUtil.dateDiffSecound(DateUtil.format(new Date(), DateTimeUtil.allPattern), date,
                                DateTimeUtil.allPattern);
                    }
                }

                map.put("lockTime", lockTime);
                map.put("titleLable", "我的牧场");
    			return new AppResult(SUCCESS,"查询成功",map);
    		} else {
    			return new AppResult(FAILED,response.getErrorMsg());
    		}
		} catch (Exception e) {
			logger.error(e,e);
			e.printStackTrace();
	        return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
    }

    /**
     * @date 2019年5月21日
     * @author wangyun
     * @time 下午6:10:37
     * @Description 收益账单
     * @author ydp 2019-6-29 9:42 修改
     * @param appVersion
     * @param token
     * @param investId
     * @return
     */
	@RequestMapping(value = "/getInterestDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "收益账单")
    public AppResult getInterestDetail( @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("投资Id") @RequestParam Integer investId) {
    	try {
    		User user = userService.checkLogin(token);
	        if (user == null) {
	            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
	        }
//	        InterestListRequest request = new InterestListRequest();
//	        ListResponse response = new ListResponse();
//	        request.setInvestId(investId);
//	        request.setUserId(user.getId());
//	        ServiceMessage msg = new ServiceMessage("interest.list", request);
//    		response = (ListResponse) TransactionClient.getInstance().setServiceMessage(msg).send();
//
//    		if(response.isSuccess()) {
//    			if(response.getList() != null && response.getList().size() > 0) {
//    				for (int i = 0; i < response.getList().size(); i++) {
//    					Map<String, Object> map = response.getList().get(i);
//    					int stage = Integer.parseInt(map.get("stage")+"");
//    					int limitDays = Integer.parseInt(map.get("limit_days")+"");
//    					map.put("feedingDays", (30 * stage > limitDays ? limitDays: 30 * stage));
//    					map.put("annualized", new BigDecimalUtil().multi(map.get("annualized"),100)+"%");
//					}
//    			}
//
//    			return new AppResult(SUCCESS,"查询成功",response.getList());
//    		} else {
//    			return new AppResult(SUCCESS,"查询失败");
//    		}
	        Map<String, Object> map = new HashMap<String, Object>();
	        Map<String, String> labelObject = new HashMap<String, String>();
	        labelObject.put("orderNo", "订单编号");
	        labelObject.put("amount", "认养总金额(元)");
	        labelObject.put("limitDays", "饲养期(天)");
	        labelObject.put("annualized", "饲养预计利润(年化)");
	        labelObject.put("interestStr", "饲养周期(天)");
	        labelObject.put("status", "状态");
	        labelObject.put("tips", "温馨提示：饲养利润奔富牧业已提前发放至您的账户，饲养周期未结束前仅可用于商城消费，饲养周期结束并成功出售后，您可进行提现操作。");
	        Investment investment = investmentService.get(investId);
	        Project project = projectService.get(investment.getProjectId());
	        map.put("orderNo", investment.getOrderNo());
	        map.put("amount", investment.getAmount());
            map.put("amountStr",  String.format("%.2f",investment.getAmount()));
	        map.put("limitDays", project.getLimitDays());
	        map.put("annualized", BigDecimalUtil.multi(Double.valueOf(project.getAnnualized()+""), 100)+"%");
	        map.put("interestStr", "饲养满"+project.getLimitDays()+"天获得饲养利润");
	        map.put("interestAmount", investment.getInterestAmount());
            map.put("interestAmountStr",  String.format("%.2f",investment.getInterestAmount()));
	        map.put("status", "已提前发放");
	        map.put("labelObject", labelObject);
	        return new AppResult(SUCCESS,"查询成功",map);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
	        return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
    }

    /**
     * 预支回报账单
     * @author sxy
     * @param token
     * @param appVersion
     * @param page
     * @return
     */
    @RequestMapping(value = "/prepaidBill", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "预支回报账单")
    public AppResult prepaidBill(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam(required = false) String appVersion,
            @ApiParam("分页当前页") @RequestParam Integer page) {

        Map<Object, Object> map = new HashMap<Object, Object>();
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }

            int limit = 20;
            page = page == null ? 1 : page;
            if (page < 1) {
                page = 1;
            }

            List<Map<String,Object>> list = investmentService.listPrepaidBill(user.getId(), (page - 1) * limit, limit);
            Integer count = investmentService.countPrepaidBill(user.getId());

            int pages = calcPage(count, limit);
            if (list.size() == 0) {
                map.put("list", null);
            } else {
                map.put("list", list);
            }
            map.put("page", page);
            map.put("pages", pages);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
        return new AppResult(SUCCESS, map);
    }

    /**
     * <p>
     * Title:[投资回购接口(卖牛)]
     * </p>
     *
     * @author:[张琼麒]
     * @update:[日期2019-05-17] [张琼麒]
     * @param request
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/investmentBuyBack", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "投资回购(卖牛)")
    public AppResult investmentBuyBack(HttpServletRequest request,
        @ApiParam("用户token") @RequestParam String token,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("投资主键") Integer imvestmentId,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
        logger.info("==============投资回购(卖牛)======================");

        try {
            User user = userService.updateCheckLogin(token, 1, getIpAddr(request));
            if (null == user) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            Investment investment = investmentService.getMapper().selectByPrimaryKey(imvestmentId);
            if (investment == null) {
                return new AppResult(FAILED, "不存在的投资:" + imvestmentId);
            }
            if (!investment.getUserId().equals(user.getId())) {
                return new AppResult(FAILED, "不能出售其他用户的投资");
            }

            investmentService.doInvestmentBuyBack(investment);

            // 项目产品属性及值
            List<Map<String, Object>> proList =
                projectService.getMapper().listProjectProductPropertyInfoById(investment.getProjectId());
            String value1 = "";
            for (Map<String, Object> map : proList) {
                if ("性别".equals(map.get("property_name"))) {
                    value1 = map.get("property_value") == null ? "" : map.get("property_value").toString();
                }
            }
            Map<String, Object> returnMap = new HashMap<String, Object>();

            returnMap.put("title1", "交易成功");
            returnMap.put("title2", "您已成功卖出");
            Project project = projectService.getMapper().selectByPrimaryKey(investment.getProjectId());
            List<String> lableList = new ArrayList<String>();
            List<String> valueList = new ArrayList<String>();
            lableList.add("公/母:");
            valueList.add(value1);
            lableList.add("订单编号:");
            valueList.add(investment.getOrderNo());
            lableList.add("耳标号:");
            valueList.add(project.getEarNumber());
            returnMap.put("lableList", lableList);
            returnMap.put("valueList", valueList);

            DecimalFormat myformat = new DecimalFormat();
            myformat.applyPattern("##,###.00");
            returnMap.put("buttom_lable", "成交金额");
            returnMap.put("buttom_value", myformat.format(investment.getAmount()));
            
            returnMap.put("isDialogs", true);
            returnMap.put("dialogsContent", "您已成功卖牛，现平台奖励58元牧场红包一个，请笑纳。");
            return new AppResult(SUCCESS, returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return new AppResult(FAILED, e.getMessage());
        }
    }

    /**
     * 卖牛订单信息
     *
     * @param request
     * @param projectId
     * @return
     * @author: zj
     */
    @RequestMapping(value = "/sellBullsInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "卖牛订单信息")
    public AppResult sellBullsInfo(HttpServletRequest request,
        @ApiParam("用户token") @RequestParam String token,
        @ApiParam("订单ID") Integer investId,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
        @ApiParam("是否是续购 1是0否")@RequestParam(required = false) Integer buyAgain) {
        logger.info("==============卖牛订单信息======================");
        if(null == investId) {
            return new AppResult(FAILED, "investId为必传参数");
        }
        try {
            User user = userService.updateCheckLogin(token, 1, getIpAddr(request));
            if (null == user) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            Map<String, Object> info = investmentService.getProjectOrderInfo(investId);
            info.put("titleLable", "项目名称");
            info.put("earNumber", "耳标号");
            info.put("limitDays", "已饲养日期");
            if(Integer.valueOf(info.get("p_id")+"").intValue() == 4) {
                info.put("amount", "羊只价格");
                info.put("nzxx", "羊只信息");
            }else {
                info.put("amount", "牛只价格");
                info.put("nzxx", "牛只信息");
            }
            info.put("hggs", "回购公司");
            info.put("js", "结算");
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("orderInfo", info);
            returnMap.put("lable_btn", "立即出售");
            returnMap.put("tips", "友情提示：交易结束后最终成交金额和冻结利润（已解冻）直接进入余额，可直接提现。如领养人在饲养周期结束后1个工作日内未贩卖牛只或羊只，我们将根据合同约定自动发起出售操作，出售金额自动发放至余额，该周期内不计算饲养回报。");
            returnMap.put("company", "未到最佳屠宰期的牛只或羊只由内蒙古奔富畜牧业发展有限公司回购，已到最佳屠宰期由第三方屠宰场或食品加工厂收购。");
            returnMap.put("jsTips", "总价值包含：购牛款(含饲料费+管理费)+冻结利润(已预支)");
            returnMap.put("jsTotal", "总价值");
            returnMap.put("jsFreace", "已预支回报(冻结利润)");
            returnMap.put("jsSum", "合计");
          
            if (buyAgain != null && buyAgain == 1) {
                returnMap.put("lable_btn", "卖牛并续养");
            }
            // returnMap.put("buyAgain", buyAgain);
            
            return new AppResult(SUCCESS, returnMap);
        } catch (Exception e) {
            logger.error("访问卖牛订单信息" + e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * 购买(牛)协议信息
     * @author sxy
     * @param token
     * @param orderNo
     * @param appVersion
     * @param client
     * @return
     */
    @RequestMapping(value = "/purchasePactInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "购买(牛)协议信息")
    public AppResult purchasePactInfo(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("订单编号") String orderNo,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }

            Investment investment = investmentService.findByOrderNo(orderNo);
            if(investment == null) {
                return new AppResult(FAILED, "未找到该笔订单");
            }

            List<BankCard> bankCard = bankCardService.getByUserId(user.getId());
            Project project = projectService.get(investment.getProjectId()); //项目
            Map<String, Object> projectDetailInfo = projectService.getProjectDetail(investment.getProjectId()); //项目详细信息
            Product product = productService.selectById(investment.getProductId()); //产品

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("orderNo", orderNo);
            resultMap.put("trueName", user.getTrueName());
            resultMap.put("idNumber", user.getIdentityCard());
            resultMap.put("phone", user.getPhone());
            if(null != bankCard && bankCard.size()>0) {
                resultMap.put("bankCard", bankCard.get(0).getCardNumber());
            }else {
                resultMap.put("bankCard", "");
            }
            resultMap.put("companyName", "内蒙古奔富畜牧业发展有限公司");
            resultMap.put("signDate", sdf.format(project.getDeadline())); //起始日
            resultMap.put("productName", product.getName()); //品种名
            resultMap.put("sex", "未知");
            if(project.getSex().equals("0")) {
                resultMap.put("sex", "公");
            } else if(project.getSex().equals("1")) {
                resultMap.put("sex", "母");
            }
            resultMap.put("weight", Double.valueOf(project.getWeight())+"kg"); //体重
            resultMap.put("price", projectDetailInfo.get("total_amount")); //总价
            resultMap.put("priceCap", CommonUtil.moneyToChinese((double)projectDetailInfo.get("total_amount")));
            resultMap.put("period", projectDetailInfo.get("limit_days")); //饲养天数
            resultMap.put("expDate", sdf.format(DateFormatTools.jumpOneDay(project.getDeadline(), (int)projectDetailInfo.get("limit_days")))); //到期日
            resultMap.put("raiseFeeCap", CommonUtil.moneyToChinese(project.getUnitFeedPrice())); //饲养费
            resultMap.put("manageFeeCap", CommonUtil.moneyToChinese(project.getUnitManagePrice())); //管理费
            //resultMap.put("proList", projectDetailInfo.get("proList")); //详细
            List<Map<String, Object>> proList = (List<Map<String, Object>>)projectDetailInfo.get("proList");
            for(Map<String, Object> map : proList) {
                if(map.get("property_name").equals("月龄")) {
                    resultMap.put("age", map.get("property_value"));
                }
                if(map.get("property_name").equals("健康状况")) {
                    resultMap.put("health", map.get("property_value"));
                }
            }

            return new AppResult(SUCCESS, resultMap);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    /**
     * @description 我的牧场--奔富乐园入口
     * @author shuys
     * @date 2019/9/10
     * @param token
     * @param appVersion
     * @param client
     * @return com.goochou.p2b.app.model.AppResult
     */
    @RequestMapping(value = "/paradiseEntrance", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "我的牧场--奔富乐园入口")
    public AppResult paradiseEntrance(
            @ApiParam("用户token") @RequestParam(required = false) String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
        try {
            Map<String, Object> resultMap = new HashMap<>(16);
            resultMap.put("show", getCacheKeyValue(DictConstants.PARADISE_SHOW)); // 是否显示 yes/no
            resultMap.put("bullIcon", ClientConstants.ALIBABA_PATH + "upload/myParadiseBulls.gif");
            resultMap.put("goParadiseUrl", "https://www.baidu.com");
            return new AppResult(SUCCESS, resultMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
    
    @RequestMapping(value = "/listMigrationInvestment", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "迁移用户投资列表")
    public AppResult listMigrationInvestment(HttpServletRequest request,
        @ApiParam("用户token") @RequestParam String token,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
        @ApiParam("账单状态:0回款中 1已回款") @RequestParam Integer status,
        @ApiParam("排序类型:0下期还款时间-正序 1下期还款时间-倒序  2末期还款时间-正序 3末期还款时间-倒序 4待收金额-正序 5待收金额-倒序") @RequestParam Integer orderType,
        @ApiParam("分页当前页") @RequestParam Integer page) {
        try {
            int limit = 4;
            if (page == null) {
                page = 1;
            }
            
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            
            List<MigrationInvestmentView> list = migrationInvestmentService.listMigrationInvestment(user.getId(), status, orderType, (page-1) * limit, limit);
            int count = migrationInvestmentService.countMigrationInvestment(user.getId(), status, orderType);
            
            List<MigrationInvestmentView> listNoPage = migrationInvestmentService.listMigrationInvestment(user.getId(), null, null, null, null);
            BigDecimal totalNotReceiveAmount = new BigDecimal("0.00");
            for(MigrationInvestmentView migrationInvestment : listNoPage) {
                totalNotReceiveAmount = totalNotReceiveAmount.add(migrationInvestment.getNotReceivedCorpus()).add(migrationInvestment.getNotReceivedInterest())
                    .add(migrationInvestment.getNotReceivedIncreaseInterest()); //待收总金额=待收本金+待收利息+待收加息利息
            }
            
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("list", list);
            resultMap.put("count", count);
            resultMap.put("totalNotReceiveAmount", totalNotReceiveAmount);
            resultMap.put("page", page);
            resultMap.put("pages", calcPage(count, limit));
            
            return new AppResult(SUCCESS, resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("迁移用户投资列表" + e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
       }
    }
    
    @RequestMapping(value = "/listMigrationInvestmentBill", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "迁移用户回款账单列表")
    public AppResult listMigrationInvestmentBill(HttpServletRequest request,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
        @ApiParam("迁移用户投资id") @RequestParam Integer migrationInvestmentId) {
        try {
            List<MigrationInvestmentBill> list = migrationInvestmentBillService.listMigrationInvestmentBill(migrationInvestmentId);
            MigrationInvestment migrationInvestment = migrationInvestmentService.getMapper().selectByPrimaryKey((long)migrationInvestmentId);
            
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("list", list);
            resultMap.put("migrationInvestment", migrationInvestment);
            
            return new AppResult(SUCCESS, resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("迁移用户回款账单列表" + e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
       }
    }


    @RequestMapping(value = "/gotoByAgain", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "我要续养 接口")
    public AppResult gotoByAgain(HttpServletRequest request,
             @ApiParam("token") @RequestParam String token,
             @ApiParam("App版本号") @RequestParam String appVersion,
             @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
             @ApiParam("投资订单id") @RequestParam Integer investId) {
        try {
            User user = userService.updateCheckLogin(token, 1, getIpAddr(request));
            if (null == user) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            Investment investment = investmentService.getMapper().selectByPrimaryKey(investId);
            if (investment == null) {
                return new AppResult(FAILED, "该投资订单不存在");
            }
            if (!user.getId().equals(investment.getUserId())) {
                return new AppResult(FAILED, MESSAGE_EXCEPTION);
            }
            Project project = projectService.getProjectById(investment.getProjectId());
            if (project == null) {
                return new AppResult(FAILED, "该投资项目不存在");
            }
            // 1.卖牛并续养（跳转 卖牛并续养），
            // 2.已卖牛已创建续养投资订单操作续养（跳转 牛只订单待支付），
            // 3.已卖牛操作续养（跳转 续养），
            // 4.报错："牛只已被买走，不可续购"（跳转 续养），
            // 5.报错："您已续养成功 不可再次续养“（跳转 续养），
            // 6.报错："牛只已到最佳屠宰期不可继续饲养“（跳转 续养），
            int status = -1;
            String message = "";
            Map<String, Object> resultMap = new HashMap<>();
            if (project.getStatus() == ProjectStatusEnum.BUYBACK.getCode() && !projectService.enableBuyAgain(investment)) {
                //return new AppResult(FAILED, "牛只已到最佳屠宰期不可继续饲养");
                status = 6;
                message = "牛只已到最佳屠宰期不可继续饲养。";
            } else {
                if (InvestmentStateEnum.buyed.getCode() == investment.getOrderStatus()) {
                    // 饲养期
                    Date sellDate = DateUtil.parseDate(DateUtil.dateAdd("day", project.getLimitDays(), project.getDeadline()).toString());
                    Date now = new Date();
                    if (now.after(sellDate)) { // 当前时间 > 出售时间
                        status = 1; // 跳转 卖牛并续养 页面
                    } else {
                        return new AppResult(FAILED, "该投资订单还未到期。");
                    }
                } else if (InvestmentStateEnum.saled.getCode() == investment.getOrderStatus()) {
                    // 已卖牛
                    int reshelfStatus = projectService.reshelfStatus(project.getId(), user.getId());
                    if (reshelfStatus == 1) { // 后台上架
                        status = 4;
                        message = "牛只已被买走，不可续购。";
                    } else if (reshelfStatus == 2) { // 用户操作续购
                        // 跳转到 待付款 页面
                        status = 2;
                    } else if (reshelfStatus == 3) { // 支付完成
                        status = 5;
                        message = "您已续养成功 不可再次续养。";
                    } else { // 还未生成新的投资项目
                        // 跳转 续养 页面
                        status = 3;
                    }
                } else {
                    return new AppResult(FAILED, "该投资订单状态不允许续养");
                }
            }
            resultMap.put("status", status);
            resultMap.put("message", message);
            return new AppResult(SUCCESS, resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    public static void main(String[] args) {
    	BigDecimal a=new BigDecimal("12.9955665");
    	BigDecimal b=a.setScale(2, RoundingMode.FLOOR);
    	System.out.println(a);
    	System.out.println(b);
        System.out.println(DateUtil.dateAdd("day", 30, new Date()));
    }
}
