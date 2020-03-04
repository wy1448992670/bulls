package com.goochou.p2b.app.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.goochou.p2b.constant.*;
import com.goochou.p2b.dao.ProjectPropertyViewMapper;
import com.goochou.p2b.dao.ProjectViewMapper;
import com.goochou.p2b.exception.ResponseException;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.vo.bulls.BuyAgainProjectVO;
import com.goochou.p2b.model.vo.bulls.BuyBullsDetailMoneyVO;
import com.goochou.p2b.model.vo.bulls.KeepPeriodVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.app.model.InterceptBlock;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.pasture.ProjectStatusEnum;
import com.goochou.p2b.model.vo.BannerIndexVO;
import com.goochou.p2b.model.vo.KeyValue;
import com.goochou.p2b.model.vo.PictureView;
import com.goochou.p2b.model.vo.ProjectView;
import com.goochou.p2b.model.vo.ProjectViewExample;
import com.goochou.p2b.model.vo.WeatherVO;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.AppVersionService;
import com.goochou.p2b.service.InvestmentBlanceService;
import com.goochou.p2b.service.InvestmentService;
import com.goochou.p2b.service.ProjectService;
import com.goochou.p2b.service.TradeRecordService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.CheckEmptyUtil;
import com.goochou.p2b.utils.CommonUtil;
import com.goochou.p2b.utils.DateTimeUtil;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.PayUtil;


/**
 * 产品订单
 *
 * @ClassName ProjectController
 * @author zj
 * @Date 2019年5月20日 下午4:27:11
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "project")
@Api(value = "项目")
public class ProjectController extends BaseController {

	private static final Logger logger = Logger.getLogger(ProjectController.class);
	@Resource
	private ActivityService activityService;
	@Resource
	private AppVersionService appVersionService;
	@Resource
	private UserService userService;
	@Autowired
	private ProjectService projectService;
	@Resource
	private InvestmentService investmentService;
	@Resource
	private InvestmentBlanceService investmentBlanceService;
	@Autowired
	private TradeRecordService tradeRecordService;
	@Autowired
	private ProjectPropertyViewMapper projectPropertyViewMapper;
	@Resource
	private ProjectViewMapper projectViewMapper;
	/**
     * 项目详细信息
     *
     * @param request
     * @param projectId
     * @return
     * @author: zj
     */
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "项目详细信息")
    public AppResult getProjectDetail(HttpServletRequest request,
        @ApiParam("用户token") @RequestParam(required=false) String token,
        @ApiParam("项目主键") Integer projectId,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
        logger.info("==============项目详细信息======================");
        try {
            Map<String, Object> projectDetailInfo = projectService.getProjectDetail(projectId);//?
            Project project = projectService.get(projectId);
            ProjectViewExample example=new ProjectViewExample();
            example.createCriteria().andIdEqualTo(project.getId());
            List<ProjectView> projectViewList=projectViewMapper.selectByExample(example);
            ProjectView projectView=null;
            if(projectViewList.size()==1){
            	projectView=projectViewList.get(0);
            }else {
            	throw new Exception("牛只查询错误");
            }
            // User user = userService.updateCheckLogin(token, 1, getIpAddr(request));
            // if (null == user) {
            // return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            // }
            
            User user = userService.checkLogin(token);
            
            String buyFlag=Constants.YES;
            int noob=(Integer)projectDetailInfo.get("noob");
			if (user != null) {//登录
				if (noob==1&&!projectService.isNoob(user.getId())) {
					buyFlag=Constants.NO;//不能购买
				}
			}

            Map<String, Object> returnMap = new HashMap<String, Object>(64);
            InterceptBlock interceptBlock=new InterceptBlock();
            returnMap.put("interceptBlock", interceptBlock);
//          projectDetailInfo.put("annualizedStr", BigDecimalUtil.multi(projectDetailInfo.get("annualized"), 100) + "%");
            
            projectDetailInfo.put("increaseAnnualizedStr",
                BigDecimalUtil.multi(projectDetailInfo.get("increase_annualized"), 100) + "%");
            if (projectDetailInfo.get("repay_unit").equals(RepayUnitEnum.MONTH.getFeatureName())) {
                projectDetailInfo.put("limitDayStr",
                    projectDetailInfo.get("limit_days") + RepayUnitEnum.MONTH.getDescription());
            } else if (projectDetailInfo.get("repay_unit").equals(RepayUnitEnum.DAY.getFeatureName())) {
                projectDetailInfo.put("limitDayStr", projectDetailInfo.get("limit_days") + RepayUnitEnum.DAY.getDescription());
            }
            projectDetailInfo.put("ear_path", "https://wap.bfmuchang.com/bullsGps.html?earNumber="+projectDetailInfo.get("ear_number").toString());
            projectDetailInfo.put("safe_path", ClientConstants.H5_URL+"warranty.html?zoom=1");
            //以后可能会有羊、猪
            if(Integer.valueOf(projectDetailInfo.get("product_id")+"").intValue() == 4) {
                projectDetailInfo.put("lable_data", "羊只信息");
                projectDetailInfo.put("lable_safe", "羊只保单");
            }else {
                projectDetailInfo.put("lable_data", "牛只信息");
                projectDetailInfo.put("lable_safe", "牛只保单");
            }
            projectDetailInfo.put("lable_safe_query", "牛只定位");
            

            //----------------2.0begin
            if(getVersion(appVersion)>=200) {//TODO 可改造
            	//保单icon
            	projectDetailInfo.put("baodan_img", ClientConstants.ALIBABA_PATH + "images/newyear/insurance_bill.png");
            	//定位icon
                projectDetailInfo.put("dingwei_img", ClientConstants.ALIBABA_PATH + "images/newyear/fixed_position.png");
                //承保图片
                projectDetailInfo.put("chengbao_img", ClientConstants.ALIBABA_PATH + "images/newyear/insurance.png");
                //牛只类型
                projectDetailInfo.put("bull_type", project.getProjectType());
                
            	returnMap.put("innerDetailBulls", ClientConstants.H5_URL+"detailBulls.html?projectId="+projectId+"#");
            	returnMap.put("returnImg", "https://dss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/aladdin/img/new_weath/icon/1.png");
            }
            
            //预算总利润
            BigDecimal interestAmount = PayUtil.getInterestAmount(project, true);
        	projectView.setInterestAmount(interestAmount.doubleValue());
            if(projectView.getProjectType().equals(ProjectTypeEnum.PINNIU.getFeatureType())) {//拼牛

            	projectDetailInfo.put("ping_icon","https://dss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/aladdin/img/new_weath/icon/1.png");
            	//每份利润
                projectDetailInfo.put("annualizedStr", projectView.getPinInterestPerPoint().setScale(0, RoundingMode.DOWN)+"元");
            	//份数
            	projectDetailInfo.put("residuePointStr", projectView.getPinResiduePoint()+"份");//TODO
            	projectDetailInfo.put("nhxxpp", "当前剩余<%projectDetailInfo.residuePointStr%>可拼");
            	
            	projectDetailInfo.put("button_text", projectView.getPinAmountPerPoint().setScale(0, BigDecimal.ROUND_DOWN)+"元认养");
            	returnMap.put("nhxx", "饲养预计利润/份");
            	
            	int cantBuyPoint=0;
            	Integer userId=null;
            	if(user != null) {
            		userId=user.getId();
            		
            		//用户有未付款的某拼牛,不能继续拼该牛
                	//0：未饲养，1：饲养期，2：已卖牛 3 已取消, 4 待成交
                	List<InvestmentView> investmentViewList=investmentService.listInvestmentViewByProject(user.getId(), projectId, Arrays.asList(0));
                	//自己有未付款的拼牛
                	
                	if(investmentViewList.size()>0) {
                		interceptBlock.setIntercept(true);
                		interceptBlock.setTitle("友情提示");
                		interceptBlock.setMsg("您当前有此牛只的未付款订单,请先前往处理后再次购买");
                		interceptBlock.setKey("orderPinCattleDetail#investId="+investmentViewList.get(0).getId());
                	}
                }
            	
            	//一个用户不能把一头牛全拼了,如果一头牛的投资全
            	//查询非当前用户购买的该拼牛
            	//0：未饲养，1：饲养期，2：已卖牛 3 已取消, 4 待成交
            	List<InvestmentView> investmentViewList=investmentService.listInvestmentViewByProjectUnuserId(userId, projectId, Arrays.asList(0,4));
            	//没有其他人买过该拼牛,必须少买一份
            	if(investmentViewList.size()<=0) {
            		cantBuyPoint=1;
            	}
            	
            	//可售份数
            	projectDetailInfo.put("canBuyPoint", projectView.getPinResiduePoint()-cantBuyPoint+"");
            	
            	//可售状态
            	if (projectView.getStatus() == ProjectStatusEnum.ENABLE_SALE.getCode()) {// 已上架
					returnMap.put("allowSale", Constants.YES);
				}else if (projectView.getStatus() == ProjectStatusEnum.PAYING.getCode() ) {// 购买中
					if( projectView.getPinResiduePoint()-cantBuyPoint>0) {
						returnMap.put("allowSale", Constants.YES);
					}else {
						returnMap.put("allowSale", Constants.NO);
						if( cantBuyPoint>0 ) {
							returnMap.put("noBuyMsg", "给其他人留几份吧!");
						}else{
							returnMap.put("noBuyMsg", "拼牛被抢完了!");
						}
					}
				}else {
					returnMap.put("allowSale", Constants.NO);
					returnMap.put("noBuyMsg", "当前拼牛不能被购买");
				}
            	
            }else {
            	//利润
                projectDetailInfo.put("annualizedStr", interestAmount+"元");
                projectDetailInfo.put("button_text", BigDecimal.valueOf(projectView.getTotalAmount()).setScale(0, BigDecimal.ROUND_DOWN)+"元认养");
            	returnMap.put("nhxx", "饲养预计利润");
            	
            	//可售状态
            	if (projectView.getStatus() == ProjectStatusEnum.ENABLE_SALE.getCode()) {// 已上架
    				if (buyFlag.equals(Constants.YES)) {// 可以购买
    					returnMap.put("allowSale", Constants.YES);
    				}else {
    					returnMap.put("allowSale", Constants.NO);
    					returnMap.put("noBuyMsg", "您已购买过此类新手产品,不能再次购买！");
    				}
    			} else {
                    returnMap.put("allowSale", Constants.NO);
                    returnMap.put("noBuyMsg", "");
                    
                    long lockTime = 0;
                    
                    if (projectView.getStatus() == ProjectStatusEnum.PAYING.getCode()) {
                    	//对应领养订单下单时间,可以计算锁定库存的时间
                    	//增加拼牛时,改为领养中的牛的update_date
                        String createDate = null == project.getUpdateDate() ? null
                            : DateUtil.dateFullTimeFormat.format(project.getUpdateDate());
                        if (null != createDate) {
                            String date = DateUtil.addtime(createDate, getCacheKeyValue(DictConstants.PAY_WAIT_TIME));
                            lockTime = DateUtil.dateDiffSecound(DateUtil.format(new Date(), DateTimeUtil.allPattern), date,
                                DateTimeUtil.allPattern);
                        }
                    }
                    returnMap.put("lockTime", lockTime);
                }
            }
            
            //----------------2.0end
            
            returnMap.put("enterprise_info_path", ClientConstants.H5_URL+CheckEmptyUtil.ifnull(projectDetailInfo.get("memo")));
            returnMap.put("syhb_lable", "饲养利润");
            returnMap.put("syhb", Constants.SYHB);
            returnMap.put("fxts", Constants.FXTS);
            returnMap.put("projectDetailInfo", projectDetailInfo);
            returnMap.put("lable_deal", "认养当日即计算成长利润");
            returnMap.put("lable_sian", "安心认养 放心成长");
            returnMap.put("lable_limit_days", "饲养期");
            //returnMap.put("nhxx", "饲养预计利润");
            returnMap.put("safe_lable", "安全保障");
            returnMap.put("safe_img", ClientConstants.ALIBABA_PATH + "images/safe.png");
            returnMap.put("tran_lable", "交易规则");
            returnMap.put("tran_img", ClientConstants.ALIBABA_PATH + "images/tran.png");
            if(Integer.valueOf(projectDetailInfo.get("product_id")+"").intValue()== 4) {
                returnMap.put("tran_url", ClientConstants.H5_URL+"ruleSheep.html");
            }else {
                if(projectDetailInfo.get("sex").equals("0")) {
                    returnMap.put("tran_url", ClientConstants.H5_URL+"ruleBull.html");
                }else if(projectDetailInfo.get("sex").equals("1")) {
                    returnMap.put("tran_url", ClientConstants.H5_URL+"ruleCow.html");
                }
            }
            returnMap.put("pastureIntroduce", "牧场介绍");
            //大图
            List<BannerIndexVO> bigPics = new ArrayList<BannerIndexVO>();
            for(ProjectPicture picture : project.getPictures()) {
                BannerIndexVO bigPic = new BannerIndexVO();
                bigPic.setPictureUrl(picture.getUpload().getCdnPath());
                bigPics.add(bigPic);
            }
            returnMap.put("bigPics", bigPics);
            return new AppResult(SUCCESS, returnMap);
        } catch (Exception e) {
            logger.error("访问项目详细信息" + e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    @RequestMapping(value = "/pinPrice", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "项目详细信息")
    public AppResult getPinniuPrice(HttpServletRequest request,
        @ApiParam("用户token") @RequestParam(required=false) String token,
        @ApiParam("项目主键") Integer projectId,
        @ApiParam("拼牛份数") Integer point,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
    	try {
    		User user = userService.checkLogin(token);
        	
        	ProjectViewExample example=new ProjectViewExample();
            example.createCriteria().andIdEqualTo(projectId);
            List<ProjectView> projectViewList=projectViewMapper.selectByExample(example);
            ProjectView projectView=null;
            if(projectViewList.size()==1){
            	projectView=projectViewList.get(0);
            }else {
            	throw new Exception("牛只查询错误");
            }
            if(!projectView.getProjectType().equals(ProjectTypeEnum.PINNIU.getFeatureType())) {
            	throw new Exception("该牛只不参与拼牛");
            }
            if(point==null || point<=0) {
            	throw new Exception("购买份数异常");
            }
            if(point>projectView.getPinResiduePoint()) {
            	throw new Exception("购买超过可售份数");
            }

        	int cantBuyPoint=0;
        	//可售份数
        	int canBuyPoint=projectView.getPinResiduePoint();
            if(user!=null) {
            	//0：未饲养，1：饲养期，2：已卖牛 3 已取消, 4 待成交
            	List<InvestmentView> investmentViewList=investmentService.listInvestmentViewByProject(user.getId(), projectId, Arrays.asList(0,4));
            	BigDecimal sumInvestedAmount=BigDecimal.ZERO;
            	for(InvestmentView investmentView:investmentViewList) {
            		sumInvestedAmount=sumInvestedAmount.add(BigDecimal.valueOf(investmentView.getAmount()));
            	}
            	if(sumInvestedAmount.compareTo(BigDecimal.valueOf(projectView.getInvestedAmount())) >= 0) {
            		cantBuyPoint=1;
            	}
            }
            
            canBuyPoint=projectView.getPinResiduePoint()-cantBuyPoint;
            if(point>canBuyPoint) {
            	throw new Exception("购买超过可售份数");
            }
            
            Map<String, Object> returnMap = new HashMap<String, Object>(64);
            
            returnMap.put("button_text", new DecimalFormat("0.##").format(projectView.getPinAmountForPoint(point).setScale(0, BigDecimal.ROUND_DOWN))+"元认养");
            returnMap.put("pinAmountForPoint", projectView.getPinAmountForPoint(point)+"");
            returnMap.put("pinInterestForPoint", projectView.getPinInterestForPoint(point)+"");
            //可买份数
            returnMap.put("canBuyPoint", canBuyPoint+"");
            returnMap.put("point", point+"");
            return new AppResult(SUCCESS, returnMap);
		} catch (Exception e) {
			if(e.getMessage().equals("牛只查询错误") || e.getMessage().equals("该牛只不参与拼牛") 
					|| e.getMessage().equals("购买份数异常")|| e.getMessage().equals("购买超过可售份数") ) {
				logger.info("访问项目详细信息" + e.getMessage(), e);
			}else {
				logger.error("访问项目详细信息" + e.getMessage(), e);
			}
			return new AppResult(FAILED, e.getMessage());
		}
    	
    }
    
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "项目列表")
	public AppResult listProjectIndexInfo(HttpServletRequest request, @ApiParam("用户token") @RequestParam(required = false) String token,
			@ApiParam("当前页号") Integer page, @ApiParam("App版本号") @RequestParam String appVersion,
			@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client, @ApiParam("标投资期限及类型") @RequestParam(required = false) Integer limitDays,
			@RequestParam(required = false) String productId
			) {
		logger.info("==============项目列表======================");
		try {
			User user = userService.checkLogin(token);
			
			int limit = 6;
			if (page == null) {
				page = 1;
			}
			Map<String, Object> parMap = new HashMap<String, Object>();
		//	User user = userService.checkLogin(token);
			if (!StringUtils.isEmpty(limitDays)) {
				if (limitDays == 1) {
					parMap.put("noob", 1);
				} else {
					parMap.put("limitDays", limitDays);
				}
			}
			boolean isNoobUser = true;
			if (null != user) {
				// 查询用户是否可以查看新手标
				ProjectExample example = new ProjectExample();
				example.createCriteria().andUserIdEqualTo(user.getId()).andNoobEqualTo(1).andStatusGreaterThan(2);
				List<Project> projects = projectService.selectByExample(example);
				if (projects.size() > 0) {
					isNoobUser = false;
					parMap.put("noob", 0);
					if (!StringUtils.isEmpty(limitDays) && limitDays == 1) {
						limit = 0;
					}
				}
			}
			parMap.put("limitStart", (page - 1) * limit);
			parMap.put("limitEnd", limit);
			parMap.put("status", 1);
			parMap.put("limitDays", limitDays);// 投资期限
			parMap.put("productId", productId);//畜牧品种
			List<Map<String, Object>> projectDetailInfo = new ArrayList<Map<String,Object>>();
			if(getVersion(appVersion)>=200) {
			    //根据产品需求获取相应数据 @sys
			    //projectDetailInfo = [{title:"新手牛专区（30天）",data:[]},{title:"90天牛只专区",data:[]},{title:"180天牛只专区",data:[]},{title:"360天牛只专区",data:[]}]
				projectDetailInfo = projectService.listGroupByLimitDays(8, null);
				if (!isNoobUser) {
					// 如果用户已买过牛，则过滤新手牛信息
					projectDetailInfo = projectDetailInfo.stream().filter(item -> !"noob".equals(item.get("key"))).collect(Collectors.toList());
				}
			}else {
			    projectDetailInfo = projectService.listProjectInfo(parMap);
				for (int i = 0; i < projectDetailInfo.size(); i++) {
//                projectDetailInfo.get(i).put("annualizedStr", BigDecimalUtil.multi(projectDetailInfo.get(i).get("annualized"), 100) + "%");
					Project p = projectService.get(Integer.valueOf(projectDetailInfo.get(i).get("id") + ""));
					BigDecimal interestAmount = PayUtil.getInterestAmount(p, true);
					projectDetailInfo.get(i).put("annualizedStr", interestAmount + "元");
					projectDetailInfo.get(i).put("increaseAnnualizedStr",
							BigDecimalUtil.multi(projectDetailInfo.get(i).get("increase_annualized"), 100) + "%");
					if (projectDetailInfo.get(i).get("repay_unit").equals(RepayUnitEnum.MONTH.getFeatureName())) {
						projectDetailInfo.get(i).put("limitDayStr", projectDetailInfo.get(i).get("limit_days") + RepayUnitEnum.MONTH.getDescription());
					} else if (projectDetailInfo.get(i).get("repay_unit").equals(RepayUnitEnum.DAY.getFeatureName())) {
						projectDetailInfo.get(i).put("limitDayStr", projectDetailInfo.get(i).get("limit_days") + RepayUnitEnum.DAY.getDescription());
					}
					projectDetailInfo.get(i).put("noob", projectDetailInfo.get(i).get("noob"));// 是否新手标 0正常1新手标
				}
			}
			Integer count = projectService.getMapper().countProjectDetailInfoByPage(parMap);
			int pages = 1;
			if (limit != 0) {
				pages = calcPage(count, limit);
			}

			// 每种期限的牛只默认显示数量
			final int defaultShowCount = 4;
			Map<String, Object> returnMap = new HashMap<String, Object>(16); 
			returnMap.put("projectDetailInfo", projectDetailInfo);
			returnMap.put("defaultShowCount", defaultShowCount);
			returnMap.put("pages", pages);
			returnMap.put("count", count);
			returnMap.put("page", page);

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			Map<String, Object> allMap = new HashMap<String, Object>();
			allMap.put("days", "");
			allMap.put("daysName", "全部期限");
			list.add(allMap);
			if(user == null || isNoobUser) { // 未登录和新用户显示新手标
				Map<String, Object> newMap = new HashMap<String, Object>();
				newMap.put("days", "1");
				newMap.put("daysName", "新手");
				list.add(newMap);
			}
			List<Map<String, Object>> projectDays = ProjectDaysEnum.enumParseMap(list);

//            List<Map<String, Object>> projectDays=ProjectDaysEnum.enumParseMap();
//            Map<String, Object> map=new HashMap<String, Object>();
//            map.put("days", "1");
//            map.put("daysName", "新手");
//            projectDays.add(map);
			
			
			
			
			
		//==============================新菜单============================	
			List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
			Map<String, Object> allMap1 = new HashMap<String, Object>();
			allMap1.put("id", "");
			allMap1.put("subTitle", "全部期限");
			list1.add(allMap1);
			if(user == null || isNoobUser) { // 未登录和新用户显示新手标
				Map<String, Object> newMap2 = new HashMap<String, Object>();
				newMap2.put("id", "1");
				newMap2.put("subTitle", "新手");
				list1.add(newMap2);
			}

			List<Map<String, Object>> projectDays1 = ProjectDaysEnum.enumParseMapNew(list1);
			
			List<Map<String, Object>> filterList=new ArrayList<Map<String,Object>>();
			
			Map<String, Object> filterMap=new HashMap<String, Object>();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("title", "期限");
			map.put("parameter", "limitDays");
			map.put("subTitles", projectDays1);
			filterList.add(map);
			
			map=new HashMap<String, Object>();
			map.put("title", "品种");
			map.put("parameter", "productId");
			map.put("subTitles", ProductTypeEnum.enumParseMap());
			filterList.add(map);

			
			returnMap.put("filter", filterList);
//            String bullsShow = getCacheKeyValue(DictConstants.BULLS_SHOW);
//            returnMap.put("bulls_show", bullsShow);
			
			returnMap.put("limitDays", projectDays);//兼容老版本 菜单  保留
			//领养快讯
			returnMap.put("horseImage", ClientConstants.ALIBABA_PATH + "images/lykx.png");
			List<Map<String, Object>> horseList = new ArrayList<Map<String,Object>>();
			if (Constants.YES.equals(getCacheKeyValue(DictConstants.LY_SHOW))) {
    			horseList = investmentService.getMapper().getHorseList();
			}
			returnMap.put("horseList", horseList);
			return new AppResult(SUCCESS, returnMap);//新版菜单使用
		} catch (Exception e) {
			logger.error("访问项目列表出错====>" + e.getMessage(), e);
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}

	/**
	 * 获取预支回报账单列表
	 * 
	 * @Title: listAdvanceBill
	 * @param appVersion
	 * @param token
	 * @param page
	 * @return AppResult
	 * @author zj
	 * @date 2019-06-18 14:26
	 */
	@RequestMapping(value = "/listAdvanceBill", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "预支回报账单列表")
	public AppResult listAdvanceBill(@ApiParam("app版本号") @RequestParam String appVersion, @ApiParam("用户token") @RequestParam String token,
			@ApiParam("页码") @RequestParam Integer page, @ApiParam("订单收益状态") @RequestParam Integer state) {
		try {

			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
			if (page == null) {
				page = 1;
			}
			int limit = 1000;// 暂时不做分页
			Map<String, Object> map = new HashMap<>();

			AppResult result = new AppResult(SUCCESS, "获取成功");

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("countInvestmentBlance", investmentBlanceService.countInvestmentBlance(user.getId(), state));
			data.put("listInvestmentBlance", investmentBlanceService.listInvestmentBlanceByPage(user.getId(), (page - 1) * limit, limit, state));
			data.put("advanceDeclare", Constants.ADVANCE_DECLARE);
			data.put("advanceNotice", Constants.ADVANCE_NOTICE);
			data.put("remainingAvailableProfit", Constants.REMAINING_AVAILABLE_PROFIT);
			data.put("remainingAvailableIncome", Constants.REMAINING_AVAILABLE_INCOME);
			data.put("orderNoName", Constants.ORDER_NO_NAME);
			data.put("unfreezeDescription", Constants.UNFREEZE_DESCRIPTION);
			data.put("usableFreezeProfit", Constants.USABLE_FREEZE_PROFIT);
			data.put("unfreezedProfit", Constants.UNFREEZED_PROFIT);

			int count = investmentBlanceService.countListInvestmentBlance(user.getId(), state);
			int totalPages = calcPage(count, limit);
			map.put("count", count);
			map.put("currentPage", page);
			map.put("totalPages", totalPages);

			data.put("pageInfo", map);

			result.setData(data);

			return result;
		} catch (Exception e) {
			logger.error("获取预支回报账单出错===========>" + e.getMessage(), e);
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}

	/**
	 * 预支回报明细列表
	 * 
	 * @Title: listReturnDetail
	 * @param appVersion
	 * @param token
	 * @param page
	 * @return AppResult
	 * @author zj
	 * @date 2019-06-18 14:25
	 */
	@RequestMapping(value = "/listReturnDetail", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "预支回报明细列表")
	public AppResult listReturnDetail(@ApiParam("app版本号") @RequestParam String appVersion, @ApiParam("用户token") @RequestParam String token,
			@ApiParam("页码") @RequestParam Integer page, @ApiParam("订单号") String orderNo) {
		try {

			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
			if (page == null) {
				page = 1;
			}
			int limit = 1000;// 第一期暂无分页
			Map<String, Object> map = new HashMap<>();

			AppResult result = new AppResult(SUCCESS, "获取成功");

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("listReturnDetail", tradeRecordService.listReturnDetailByPage(orderNo, (page - 1) * limit, limit));

			int count = tradeRecordService.countReturnDetail(orderNo);
			int totalPages = calcPage(count, limit);
			map.put("count", count);
			map.put("currentPage", page);
			map.put("totalPages", totalPages);

			data.put("pageInfo", map);

			result.setData(data);

			return result;
		} catch (Exception e) {
			logger.error("获取预支回报明细出错===========>" + e.getMessage(), e);
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}

	/**
	 * 项目详细信息 通过设备号查询
	 *
	 * @param request
	 * @param projectId
	 * @return
	 * @author: zj
	 */
	@RequestMapping(value = "/detailByGpsNumber", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "项目详细信息")
	public AppResult detailByGpsNumber(HttpServletRequest request, @ApiParam("用户token") @RequestParam(required = false) String token,
			@ApiParam("GPS设备号码") String gpsNumber, @ApiParam("App版本号") @RequestParam String appVersion,
			@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
		logger.info("==============项目详细信息======================");
		try {
			Map<String, Object> projectDetailInfo = projectService.getProjectDetailByEarNumber(gpsNumber);
			// User user = userService.updateCheckLogin(token, 1, getIpAddr(request));
			// if (null == user) {
			// return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			// }

			Map<String, Object> returnMap = new HashMap<String, Object>(16);
			// 是否可以购买
			int status = Integer.valueOf(projectDetailInfo.get("status") + "");
			if (status == ProjectStatusEnum.ENABLE_SALE.getCode()) {
				returnMap.put("allowSale", Constants.YES);
			} else {
				returnMap.put("allowSale", Constants.NO);
				long lockTime = 0;
				String createDate = null == projectDetailInfo.get("create_date") ? null
						: DateUtil.dateFullTimeFormat.format(projectDetailInfo.get("create_date"));

				if (null != createDate) {
					String date = DateUtil.addtime(createDate, getCacheKeyValue(DictConstants.PAY_WAIT_TIME));
					lockTime = DateUtil.dateDiffSecound(DateUtil.format(new Date(), DateTimeUtil.allPattern), date, DateTimeUtil.allPattern);
				}
				returnMap.put("lockTime", lockTime);
			}
			projectDetailInfo.put("annualizedStr", BigDecimalUtil.multi(projectDetailInfo.get("annualized"), 100) + "%");
			projectDetailInfo.put("increaseAnnualizedStr", BigDecimalUtil.multi(projectDetailInfo.get("increase_annualized"), 100) + "%");
			if (projectDetailInfo.get("repay_unit").equals(RepayUnitEnum.MONTH.getFeatureName())) {
				projectDetailInfo.put("limitDayStr", projectDetailInfo.get("limit_days") + RepayUnitEnum.MONTH.getDescription());
			} else if (projectDetailInfo.get("repay_unit").equals(RepayUnitEnum.DAY.getFeatureName())) {
				projectDetailInfo.put("limitDayStr", projectDetailInfo.get("limit_days") + RepayUnitEnum.DAY.getDescription());
			}
			projectDetailInfo.put("ear_path", "");
			projectDetailInfo.put("safe_path", ClientConstants.H5_URL + "warranty.html");
			// 以后可能会有羊、猪
			if (Integer.valueOf(projectDetailInfo.get("product_id") + "").intValue() == 4) {
				projectDetailInfo.put("lable_data", "羊只信息");
				projectDetailInfo.put("lable_safe", "羊只保险单号");
			} else {
				projectDetailInfo.put("lable_data", "牛只信息");
				projectDetailInfo.put("lable_safe", "牛只保险单号");
			}
			projectDetailInfo.put("lable_safe_query", "耳标号");
			returnMap.put("enterprise_info_path", ClientConstants.H5_URL + "mc.html");
			returnMap.put("syhb_lable", "饲养利润");
			returnMap.put("syhb", Constants.SYHB);
			returnMap.put("fxts", Constants.FXTS);
			returnMap.put("projectDetailInfo", projectDetailInfo);
			returnMap.put("lable_deal", "认养当日即计算成长利润");
			returnMap.put("lable_sian", "安心认养 放心成长");
			returnMap.put("lable_limit_days", "饲养期");
			returnMap.put("nhxx", "饲养预计利润(年化)");
			returnMap.put("safe_lable", "安全保障");
			returnMap.put("safe_img", ClientConstants.ALIBABA_PATH + "images/safe.png");
			returnMap.put("tran_lable", "交易规则");
			returnMap.put("tran_img", ClientConstants.ALIBABA_PATH + "images/tran.png");
			if (Integer.valueOf(projectDetailInfo.get("product_id") + "").intValue() == 4) {
				returnMap.put("tran_url", ClientConstants.H5_URL + "ruleSheep.html");
			} else {
				if (projectDetailInfo.get("sex").equals("0")) {
					returnMap.put("tran_url", ClientConstants.H5_URL + "ruleBull.html");
				} else if (projectDetailInfo.get("sex").equals("1")) {
					returnMap.put("tran_url", ClientConstants.H5_URL + "ruleCow.html");
				}
			}
			returnMap.put("pastureIntroduce", "牧场介绍");

			return new AppResult(SUCCESS, returnMap);
		} catch (Exception e) {
			logger.error("访问项目详细信息" + e.getMessage(), e);
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}

	/**
	 * 牛只相册列表
	 * @author sxy
	 * @param request
	 * @param token
	 * @param appVersion
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "/listProjectPicture", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "牛只相册列表")
    public AppResult getProjectPicture(HttpServletRequest request,
        @ApiParam("用户名") @RequestParam String userName,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
        @ApiParam("分页当前页") @RequestParam Integer page) {
	    try {
	        int limit = 8;
            if (page == null) {
                page = 1;
            }
            
            User user = userService.queryByUserName(userName);
            if(user == null) {
                return new AppResult(ERROR, "未找到该用户");
            }
            
            List<Map<String,Object>> listProjectPicture = projectService.listProjectPicture(user.getId(), (page - 1) * limit, limit);
            Integer count = projectService.countProjectPicture(user.getId());
            
            List<VideoAlbum> listVideoAlbum = projectService.listVideoAlbum(1, 0, 1); //精选视频
            
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            
            for(Map<String, Object> picMap : listProjectPicture) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", picMap.get("id"));
                map.put("earNumber", picMap.get("ear_number"));
                map.put("orderNo", picMap.get("order_no"));
                map.put("createDate", new SimpleDateFormat("yyyy-MM-dd").format(picMap.get("create_date")));
                String smallImagePath = projectService.getProjectsmallImagePath("1", (int)picMap.get("id"), true);
                map.put("smallImagePath", smallImagePath);
                map.put("pictureCount", picMap.get("pic_count"));
                map.put("newPicCount", picMap.get("new_count"));
                
                list.add(map);
            }
            
            resultMap.put("list", list);
            resultMap.put("count", count);
            resultMap.put("page", page);
            resultMap.put("pages", calcPage(count, limit));
            resultMap.put("content", "友情提示：尊敬的用户您好，这里是您有领养的牛只照片，我们会不定期更新照片，以便于您更好的查看您的牛只。");
            if(listVideoAlbum.size() != 0) {
                resultMap.put("videoTitle", listVideoAlbum.get(0).getTitle());
                resultMap.put("videoTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(listVideoAlbum.get(0).getShowTime()));
                resultMap.put("videoPageUrl", listVideoAlbum.get(0).getVideoPageUrl()); //视频封面地址
                resultMap.put("videoUrl", listVideoAlbum.get(0).getVideoUrl()); //视频地址
            } else {
                resultMap.put("videoTitle", null);
                resultMap.put("videoTime", null);
                resultMap.put("videoPageUrl", null); //视频封面地址
                resultMap.put("videoUrl", null); //视频地址
            }
            
            return new AppResult(SUCCESS, resultMap);
        } catch (Exception e) {
             e.printStackTrace();
             logger.error("访问牛只相册" + e.getMessage(), e);
             return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
	}
	
	@RequestMapping(value = "/projectPictureDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "牛只相册详情")
    public AppResult getProjectPictureDetail(HttpServletRequest request,
        @ApiParam("用户token") @RequestParam(required=false) String token,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("项目ID") Integer projectId,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
        @ApiParam("分页当前页") @RequestParam Integer page) {
        try {
            int limit = 8;
            if (page == null) {
                page = 1;
            }
            
//            User user = userService.checkLogin(token);
//            if (user == null) {
//                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
//            }
            
            Map<String, Object> projectDetailInfo = projectService.getProjectDetail(projectId);
            String earNum = (String)projectDetailInfo.get("ear_number"); //获取耳标号
            
            List<Map<String,Object>> pictureList = projectService.getProjectPictureDetail(null, earNum, (page - 1) * limit, limit);
            Integer count = projectService.countProjectPictureDetail(null, earNum);
            
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            
            for(Map<String, Object> picMap : pictureList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", picMap.get("id"));
                map.put("earNumber", picMap.get("ear_number"));
                map.put("createUser", picMap.get("create_user"));
                map.put("createDate", new SimpleDateFormat("yyyy-MM-dd").format(picMap.get("create_date")));
                map.put("path", ClientConstants.ALIBABA_PATH + "upload/" + picMap.get("path"));
                map.put("isRead", picMap.get("is_read"));
                
                list.add(map);
            }
            
            resultMap.put("projectDetailInfo", projectDetailInfo);
            resultMap.put("list", list);
            resultMap.put("count", count);
            resultMap.put("page", page);
            resultMap.put("pages", calcPage(count, limit));
            
            return new AppResult(SUCCESS, resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("访问牛只相册详情" + e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
       }
   }


	/**
	 * 续养牛只时的项目详情（可续养的周期选项）
	 * @param appVersion
	 * @param token
	 * @param investId
	 * @return
	 */
	@RequestMapping(value = "/buyAgainProjectInfo", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "续养牛只时的项目详情（可续养的周期选项）")
	public AppResult buyAgainProjectInfo(@ApiParam("app版本号") @RequestParam String appVersion,
										 @ApiParam("用户token") @RequestParam String token,
										 @ApiParam("投资订单号") @RequestParam Integer investId) {
		try {
			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}

			Integer userId = user.getId();

			Investment investment = investmentService.get(investId);

			if (investment == null || investment.getUserId().intValue() != userId) {
				//参数错误
				return new AppResult(FAILED,"参数错误");
			}


			Project project = projectService.getMapper().selectByPrimaryKey(investment.getProjectId());

			ProjectPropertyViewExample example = new ProjectPropertyViewExample();
			ProjectPropertyViewExample.Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(project.getId());
			ProjectPropertyView projectPropertyView = projectPropertyViewMapper.selectByExample(example).get(0);


			BuyAgainProjectVO buyAgainProjectVO = new BuyAgainProjectVO();

			buyAgainProjectVO.setTip("");

			buyAgainProjectVO.setProjectId(project.getId());


			//获取项目图片
			String path = projectService.getProjectsmallImagePath("1", investment.getProjectId(), true);
			buyAgainProjectVO.setPictureUrl(path);


			buyAgainProjectVO.setOrderNum(investment.getOrderNo());
			buyAgainProjectVO.setProjectName(project.getTitle());
			buyAgainProjectVO.setEarNum(project.getEarNumber());
			buyAgainProjectVO.setSex(project.getSex().equals("0") ? "公" : "母");

			buyAgainProjectVO.setBuyedAgreeUrl(ClientConstants.H5_URL+"rl.html?orderNo="+investment.getOrderNo());

			buyAgainProjectVO.setBuyAgreeUrl(ClientConstants.H5_URL + "rl.html");


			buyAgainProjectVO.setSelectedBackgroundPicUrl(ClientConstants.ALIBABA_PATH + "images/selected_bg_pic.png");

			List<KeepPeriodVO> keepPeriodVOList = projectService.caculateBuyAgainCase(project.getSex(),
					Integer.parseInt(projectPropertyView.getYueLing()) * 30,
					project.getLimitDays());
			buyAgainProjectVO.setKeepPeriodVOList(keepPeriodVOList);


			buyAgainProjectVO.setAllow(getCacheKeyValue(DictConstants.BUY_BULLS_CHECK));

			return new AppResult(SUCCESS, buyAgainProjectVO);

		} catch (Exception e) {
			logger.error("续养牛只时的项目详情出现异常" + e.getMessage(), e);
			return new AppResult(FAILED,"接口出现异常");
		}

	}


	/**
	 * 根据饲养周期，计算饲养牛只的总金额及各项费用
	 *
	 * @param appVersion
	 * @param token
	 * @param days
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/caculateBuyMoney", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据饲养周期，计算饲养牛只的总金额及各项费用")
	public AppResult caculateBuyMoney(@ApiParam("app版本号") @RequestParam String appVersion,
										 @ApiParam("用户token") @RequestParam String token,
										 @ApiParam("饲养周期") @RequestParam Integer days,
										 @ApiParam("项目编号") @RequestParam Integer projectId) {
		try {

			ProjectDaysEnum projectDaysEnum = ProjectDaysEnum.getValueByType(days);


			//查询之前牛只信息及饲养周期，来计算牛只初始重量
			Project p = projectService.getMapper().selectByPrimaryKey(projectId);

			if(projectDaysEnum == null || p == null){
				return new AppResult(FAILED,"参数错误");
			}

			BuyBullsDetailMoneyVO buyBullsDetailMoneyVO = projectService.caculateBuyMoney(p, projectDaysEnum);

			return new AppResult(SUCCESS, buyBullsDetailMoneyVO);

		} catch (Exception e) {
			logger.error("计算饲养牛只的总金额及各项费用出现异常" + e.getMessage(), e);
			return new AppResult(FAILED,"接口出现异常");
		}
	}




	/**
	 * 续养前，根据之前的认养订单生成新的牛只数据，如果已生成，则更新牛只数据（根据所选的续养周期）
	 *
	 * @param appVersion
	 * @param token
	 * @param days
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/newProjectWhenReFeed", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "续养时，新生成牛只数据")
	public AppResult newProjectWhenReFeed(@ApiParam("app版本号") @RequestParam String appVersion,
										 @ApiParam("用户token") @RequestParam String token,
										 @ApiParam("饲养周期") @RequestParam Integer days,
										 @ApiParam("项目编号") @RequestParam Integer projectId) {

		try {
			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}

			ProjectDaysEnum projectDaysEnum = ProjectDaysEnum.getValueByType(days);
			//牛只状态是否在已卖牛
			Project p = projectService.getMapper().selectByPrimaryKey(projectId);

			if(projectDaysEnum==null || p==null){
				return new AppResult(FAILED,"参数错误");
			}

			if(p.getStatus()!=ProjectStatusEnum.BUYBACK.getCode()){
				return new AppResult(FAILED,"此牛只不能续购");
			}

			if(p.getNoob().intValue()==1){
				return new AppResult(FAILED,"此牛只不能续购");
			}

			Map<String, Object> resultMap = new HashMap<String, Object>();

			try {
				int pid =  projectService.doNewProjectWhenReFeed(p,projectDaysEnum);
				resultMap.put("projectId",pid);
				return new AppResult(SUCCESS, resultMap);
			}catch (ResponseException re){
				resultMap.put("errormsg",re.getResponse().getErrorMsg());
			}catch (Exception e){
				resultMap.put("errormsg",e.getMessage());
			}
			return new AppResult(FAILED, resultMap);

		} catch (Exception e) {
			logger.error("生成续养标的出现异常：" + e.getMessage(), e);
			return new AppResult(FAILED,"接口出现异常");
		}

	}

	
	@RequestMapping(value = "/updatePictureDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "更新牛只相册详情")
    public AppResult updateProjectPictureDetail(HttpServletRequest request,
        @ApiParam("用户token") @RequestParam(required=false) String token,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("生活照ID") Integer pictureId,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
	    try {
//	        User user = userService.checkLogin(token);
//            if (user == null) {
//                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
//            }
            
            ProjectLifePicture lifePicture = projectService.queryLifePictureById(pictureId);
            if(lifePicture == null) {
                return new AppResult(ERROR, "耳标号有误！");
            }
            
            lifePicture.setIsRead(1); //牛只照片 已读
            projectService.updateProjectLifePicture(lifePicture);
	        
	        return new AppResult(SUCCESS, "更新成功!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新牛只相册详情" + e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
       }
	}

	@RequestMapping(value = "/featuredVideosList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "精选视频列表")
    public AppResult featuredVideos(HttpServletRequest request,
                                    @ApiParam("用户token") @RequestParam(required = false) String token,
                                    @ApiParam("App版本号") @RequestParam String appVersion,
                                    @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                    @ApiParam("当前页号") @RequestParam Integer page
                                    ) {
        try {
            int limit = 15;
            if (page == null) {
                page = 1;
            }
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            List<VideoAlbum> videoAlbums = projectService.listVideoAlbum(null, (page - 1) * limit, limit);
            long count = projectService.countVideoAlbum(null);
            int pages = 1;
            if (limit != 0) {
                pages = calcPage(Integer.parseInt(String.valueOf(count)), limit);
            }
            returnMap.put("list", videoAlbums);
            returnMap.put("pages", pages);
            returnMap.put("count", count);
            returnMap.put("page", page);
            return new AppResult(SUCCESS, returnMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
	
	@RequestMapping(value = "/detailExtra", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "详情附加")
    public AppResult weather(HttpServletRequest request,
                                    @ApiParam("用户token") @RequestParam(required = false) String token,
                                    @ApiParam("App版本号") @RequestParam String appVersion,
                                    @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                    @ApiParam("项目主键") Integer projectId,
							 		@ApiParam("app名字") @RequestParam(required = false) String appName,
                                    @ApiParam("安卓渠道") @RequestParam(required = false) String dataSource
                                    ) {
	    try {
			User user = userService.checkLogin(token);

	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        //天气信息
	        WeatherVO weatherInfo = projectService.getWeatherInfo();
	        resultMap.put("weatherInfo", weatherInfo);
	        //牛只信息
	        Project project = projectService.get(projectId);
	        if(project == null) {
	            return new AppResult(FAILED, "牛只查询有误");
	        }
	        //视频封面图、地址
	        resultMap.put("videoPicUrl", ClientConstants.ALIBABA_PATH + "images/newyear/vidio_default.png");
	        resultMap.put("videoUrl", "https://wap.bfmuchang.com/H5image/20191213/video.mp4");
	        //是否拼牛
	        Integer projectType = project.getProjectType();
	        if(projectType.equals(ProjectTypeEnum.PINNIU.getFeatureType())) {
	            resultMap.put("isPinniu", true);
	        } else {
	            resultMap.put("isPinniu", false);
	        }
	        //饲养期限
	        resultMap.put("limitDays", project.getLimitDays());
	        //家畜性别0 公  1母
	        resultMap.put("sex", project.getSex());

			Map<String, Object> infoMap = projectService.getMapper().getProjectDetailInfo(projectId);
			//牧场编号   1基地  2 1号牧场   3 2号牧场
			resultMap.put("areaId", infoMap.get("areaId"));

//	        //9个牧场图片地址
//	        String prairiePicUrl = "";
//	        String prairieValue = project.getPrairieValue();
//	        if(prairieValue != null) {
//	            switch (prairieValue) {
//	                case "1":
//	                    prairiePicUrl = "p1";
//	                    break;
//	                case "2":
//                        prairiePicUrl = "p2";
//                        break;
//	                case "3":
//                        prairiePicUrl = "p3";
//                        break;
//	                case "4":
//                        prairiePicUrl = "p4";
//                        break;
//	                case "5":
//                        prairiePicUrl = "p5";
//                        break;
//	                case "6":
//                        prairiePicUrl = "p6";
//                        break;
//	                case "7":
//                        prairiePicUrl = "p7";
//                        break;
//	                case "8":
//                        prairiePicUrl = "p8";
//                        break;
//	                case "9":
//                        prairiePicUrl = "p9";
//                        break;
//	            }
//	        }
//	        resultMap.put("prairiePicUrl", prairiePicUrl);
	        
	        List<String> prairiePicUrls = new ArrayList<String>();
	        prairiePicUrls.add(ClientConstants.ALIBABA_PATH + "images/newyear/weather1.png");
	        prairiePicUrls.add(ClientConstants.ALIBABA_PATH + "images/newyear/weather2.png");
	        prairiePicUrls.add(ClientConstants.ALIBABA_PATH + "images/newyear/weather3.png");
	        prairiePicUrls.add(ClientConstants.ALIBABA_PATH + "images/newyear/weather4.png");
	        prairiePicUrls.add(ClientConstants.ALIBABA_PATH + "images/newyear/weather5.png");
	        resultMap.put("prairiePicUrls", prairiePicUrls);
			//是否屏蔽养牛信息
			boolean isShowBulls = projectService.isShowBulls(user,appName,dataSource,client);

	        resultMap.put("isShow", isShowBulls);
	        
	        return new AppResult(SUCCESS, resultMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
	}
	
	public static void main(String[] args) {
		// 2019-05-27 18:41:08
		try {
			System.out.println(new DecimalFormat("0.##").format(BigDecimal.valueOf(1.0052)));
			String date = DateUtil.addtime("2019-05-28 11:26:39", "30");
			long lockTime = DateUtil.dateDiffSecound(DateUtil.format(new Date(), DateTimeUtil.allPattern), date, DateTimeUtil.allPattern);
			System.out.println(lockTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
