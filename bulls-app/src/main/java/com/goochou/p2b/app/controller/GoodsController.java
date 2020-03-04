package com.goochou.p2b.app.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.goochou.p2b.model.vo.BannerIndexVO;
import com.goochou.p2b.model.vo.IconIndexVO;
import com.goochou.p2b.model.vo.SecondKillActivityView;
import com.goochou.p2b.service.*;
import com.goochou.p2b.service.impl.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.goochou.p2b.model.vo.bulls.SecondKillDetailVO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.GoodsPriceRangeEnum;
import com.goochou.p2b.constant.GoodsTypeEnum;
import com.goochou.p2b.constant.OrderDoneEnum;
import com.goochou.p2b.constant.OrderTypeEnum;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.goods.GoodsOrderStatusEnum;
import com.goochou.p2b.constant.redis.RedisConstants;
import com.goochou.p2b.hessian.Response;
import com.goochou.p2b.hessian.ServiceMessage;
import com.goochou.p2b.hessian.client.TransactionClient;
import com.goochou.p2b.hessian.transaction.OrderResponse;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderDetailRequest;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderListRequest;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderListResponse;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderRequest;
import com.goochou.p2b.hessian.transaction.goods.GoodsOrderResponse;
import com.goochou.p2b.model.Assess;
import com.goochou.p2b.model.AssessImgs;
import com.goochou.p2b.model.Bank;
import com.goochou.p2b.model.Banner;
import com.goochou.p2b.model.GoodsPicture;
import com.goochou.p2b.model.Icon;
import com.goochou.p2b.model.OrderDone;
import com.goochou.p2b.model.Recharge;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserAddress;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsCategory;
import com.goochou.p2b.model.goods.GoodsCategoryExample;
import com.goochou.p2b.model.goods.GoodsOrder;
import com.goochou.p2b.model.goods.GoodsOrderDetail;
import com.goochou.p2b.model.goods.GoodsProperty;
import com.goochou.p2b.model.goods.GoodsPropertyValue;
import com.goochou.p2b.service.AssessService;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.BankService;
import com.goochou.p2b.service.GoodsOrderService;
import com.goochou.p2b.service.GoodsPropertyValueService;
import com.goochou.p2b.service.GoodsService;
import com.goochou.p2b.service.GoodsShoppingCartService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.InvestmentBlanceService;
import com.goochou.p2b.service.OrderDoneService;
import com.goochou.p2b.service.RechargeService;
import com.goochou.p2b.service.UserAddressService;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.CommonUtils;
import com.goochou.p2b.utils.DateTimeUtil;
import com.goochou.p2b.utils.DateUtil;

@Controller
@RequestMapping("/goods")
@Api(value = "商品相关")
public class GoodsController extends BaseController {
	private static final Logger logger = Logger.getLogger(GoodsController.class);
	@Autowired
	GoodsService goodsService;
	@Autowired
	UserAddressService userAddressService;
	@Autowired
	HongbaoService hongbaoService;
	@Autowired
	AssetsService assetsService;
	@Autowired
	GoodsOrderService goodsOrderService;
    @Autowired
    private GoodsShoppingCartService goodsShoppingCartService;
    @Autowired
    private AssessService assessService;
    @Autowired
    OrderDoneService orderDoneService;
    @Autowired
    InvestmentBlanceService investmentBlanceService;
	@Resource
	private ProjectService projectService;
    @Resource
    private RechargeService rechargeService;
    @Resource
    private BankService bankService;
    @Resource
    private BannerService bannerService;
    @Resource
    private IconService iconService;
    @Resource
    private GoodsCategoryService goodsCategoryService;
    @Resource
    private GoodsClickService goodsClickService;
	@Autowired
	private MallActivityService mallActivityService;

    @Autowired
    private GoodsPropertyValueService goodsPropertyValueService;

    @Resource
    private UploadService uploadService;
    @Resource
    private RedisService redisService;
    @Resource
    private AreaService areaService;

    private final static String picUrl = ClientConstants.ALIBABA_PATH + "upload/";
    private final static DecimalFormat MONEY_FORMAT  = new  DecimalFormat("¥ ###,##0.00"); //保留整数,并且取两位小数
    /**
     * 商城首页
     * @param appVersion
     * @param client
     * @param token
     * @return
     */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "商城首页")
    public AppResult index(
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
        @ApiParam("App登陆状态") @RequestParam(required = false) String token
            ) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            //商城首页banner
            List<BannerIndexVO> banners = new ArrayList<BannerIndexVO>();
//            if (null == memcachedManager.get(Constants.BANNERS) || ((List<BannerIndexVO>) memcachedManager.get(Constants.BANNERS)).size() == 0) {
                List<Banner> listBanner = bannerService.listByStatus(0, 0, 2, null);
                for (Banner banner : listBanner) {
                    BannerIndexVO b = new BannerIndexVO();

					b.setLink(banner.getLink());

                    if("IOS".equals(client)) {
                    	if(StringUtils.isNotBlank(banner.getLink()) && (banner.getLink().contains("https") || banner.getLink().contains("http"))) {
							b.setLink("webView#url=" + banner.getLink());
						}
					}

                    b.setPictureUrl(ClientConstants.ALIBABA_PATH + "upload/" + banner.getPictureUrl());
                    banners.add(b);
                }
//                memcachedManager.addOrReplace(Constants.BANNERS, banners, 3600);
//            } else {
//                banners = (List<BannerIndexVO>) memcachedManager.get(Constants.BANNERS);
//            }
            map.put("banners", banners);
            // icons
            List<IconIndexVO> icons = new ArrayList<IconIndexVO>();
            List<Icon> iconsList = iconService.getUsingIcons(7, appVersion);
            for (Icon icon : iconsList) {
                IconIndexVO i = new IconIndexVO();
                i.setId(icon.getId());
                i.setKey(icon.getKey());
                i.setLink(icon.getLink());
                i.setPath(ClientConstants.ALIBABA_PATH + "upload/" + icon.getPath());
                i.setTitle(icon.getTitle());
                icons.add(i);
            }
            map.put("icons", icons);
            //活动
            //查询是否有秒杀活动,是否是当天的秒杀活动,是当天秒杀活动则根据状态显示.
            //若当天没有秒杀活动,则显示下一次活动时间

            // 查询未来2星期内活动
            List<SecondKillActivityView> weekActivity = goodsService.queryAppIndex2WeeksActivity();
            // 查询当天有没有活动
            List<SecondKillActivityView> currentDayActivity = goodsService.getAllSecondKillActivity(new Date(),null);

            icons = new ArrayList<IconIndexVO>();
            iconsList = iconService.getUsingIcons(8, appVersion);
            Iterator<Icon> itr = iconsList.iterator();
            Map<String, Object> secondKillInfo = new HashMap<>();
            while(itr.hasNext()) {
            	Icon icon = itr.next();
                IconIndexVO i = new IconIndexVO();
                i.setId(icon.getId());
                i.setKey(icon.getKey());
                i.setLink(icon.getLink());
                i.setPath(ClientConstants.ALIBABA_PATH + "upload/" + icon.getPath());
                i.setTitle(icon.getTitle());
                if((!currentDayActivity.isEmpty() || !weekActivity.isEmpty())  && icon.getKey().equals("defaultGoods")) {// 当天秒杀活动为空,默认商品icon移除
                	continue;
                }
                if(currentDayActivity.isEmpty() && weekActivity.isEmpty() && icon.getKey().equals("secondKillGoods")) {// 都为空,秒杀商品icon移除
                	continue;
            	}
                icons.add(i);
            }
            SecondKillActivityView activity = null;
        	if(!currentDayActivity.isEmpty()) { // 当天秒杀不为空,根据状态判断(可能秒杀活动已经结束)
        		activity = currentDayActivity.get(0);

        	} else if(!weekActivity.isEmpty()){ //当天秒杀商品为空,则显示下一次活动,活动必定未开始
        		activity = weekActivity.get(0);
        	}

        	if(activity != null) {
        		logger.info("秒杀商品不为空====>" + activity.toString());
        		Map<String, Object> goodsInfo = getActivityGoodsInfo(activity);
        		secondKillInfo.put("status", goodsInfo.get("status"));
        		secondKillInfo.put("statusStr", goodsInfo.get("statusStr"));
        		secondKillInfo.put("weekDayStr",  activity.getWeekDayStr()+"场");
        		secondKillInfo.put("preheatTime", goodsInfo.get("preheatTime"));// 预热时间
        		secondKillInfo.put("lockTime", goodsInfo.get("lockTime"));
        		secondKillInfo.put("memberPrice", activity.getMemberPrice());// 秒杀商品只显示会员价 文字￥160.0
        		secondKillInfo.put("memberPriceStr", MONEY_FORMAT.format(activity.getMemberPrice()));// 秒杀商品只显示会员价 文字￥160.0
        		secondKillInfo.put("picPath", goodsInfo.get("activityPicPath"));
        		secondKillInfo.put("beginTime", activity.getBeginTime());
        		secondKillInfo.put("endTime", activity.getEndTime());
        		secondKillInfo.put("startTime", activity.getStartTime());
        		secondKillInfo.put("activityId", activity.getId());
        		// 如果秒杀活动不为空,则替换icon的图片为秒杀商品图片
        		/*for (IconIndexVO icon : icons) { 
					if("secondKillGoods".equals(icon.getKey())) {
						icon.setPath(String.valueOf(goodsInfo.get("activityPicPath")));
					}
				}*/
        	}

            map.put("secondKillInfo", secondKillInfo);
            map.put("activity_icons", icons);

            //猜你喜欢 @sxy
            map.put("loveStr", "猜你喜欢");
            User user = userService.checkLogin(token);
            List<Map<String,Object>> listByClick=null;
            //猜你喜欢显示条目
            Integer loveCount=10;
            if(user != null) {
                listByClick = goodsService.listGoodsForLove(user.getId(), 0, loveCount);
                //用户数据不足时,使用全局数据补足
                if(listByClick.size()<loveCount) {
                	List<Map<String, Object>> list = goodsService.listGoodsByClick(0, loveCount);
                	for(Map<String, Object> goodMap:list) {
                		listByClick.add(goodMap);
                		if(listByClick.size()>=loveCount) {
                			break;
                		}
                	}
                }
            } else {
                listByClick = goodsService.listGoodsByClick(0, loveCount);
            }
            //显示lable加入
            for(Map<String, Object> loveMap : listByClick) {
                loveMap.put("buyLabel", "立即购买");
            }
            map.put("loves", listByClick);
            
            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, "您的网络好像有点问题");
        }
    }
	/**
	 * @date 2019年5月24日
	 * @author wangyun
	 * @time 下午3:24:54
	 * @Description 商品列表
	 *
	 * @param appVersion
	 * @param goodsName
	 * @param client
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "商品列表")
	public AppResult list(
	    @ApiParam("用户token") @RequestParam(required = false) String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("商品名称") @RequestParam(required = false) String goodsName,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("页码") @RequestParam Integer page,
            @ApiParam("商品类别") @RequestParam(required = false) Integer categoryId,
            @ApiParam("价格区间") @RequestParam(required = false) Integer priceRange
			) {
		try {
			if (page == null) {
	            page = 1;
	        }
			int limit = 1000;
			Map<String, Object> map = new HashMap<>();
			map.put("goodsName", goodsName);
			map.put("limitStart", (page - 1) * limit);
			map.put("limitEnd", limit);
			map.put("categoryId",categoryId == null ? 0 : categoryId);
			map.put("priceRange",priceRange);
			//商品list
			List<Map<String, Object>> goodsList = new ArrayList<Map<String,Object>>();
			long startTime = System.currentTimeMillis();
			if(getVersion(appVersion) >= 200) {
			    //根据产品需求获取相应数据 @wy
				// 栏目列表
				List<GoodsCategory> allCategory = goodsCategoryService.getMapper().selectByExample(new GoodsCategoryExample());
				Map<String, Object> categoryMap = null;
				List<Map<String, Object>> listCategory = new ArrayList<>();
			    for (GoodsCategory goodsCategory : allCategory) {
			    	categoryMap = new HashMap<>();
			    	categoryMap.put("categoryId", goodsCategory.getId());
			    	categoryMap.put("adPicPath", goodsCategory.getAdUploadPath());
			    	categoryMap.put("categoryName", goodsCategory.getCategoryName());
			    	
			    	categoryMap.put("adLink", "");
			    	categoryMap.put("key", "");
			    	categoryMap.put("isShow", 0);// 0:H5 1:原生 
			     
			    	logger.info("categoryMap: " + categoryMap);
			    	listCategory.add(categoryMap);
				}
			    map.put("listCategory", listCategory);
			}
			logger.info("查询商品分类耗时: "+ (System.currentTimeMillis() - startTime)+"ms.");
			goodsList = goodsService.getAppGoodsList(map);
			logger.info("查询商品列表耗时: "+ (System.currentTimeMillis() - startTime)+"ms.");
			for (int i = 0; i < goodsList.size(); i++) {
				Map<String, Object> goods = goodsList.get(i);
				goods.put("path", ClientConstants.ALIBABA_PATH + "upload/" + goods.get("path"));
				// add
				goods.put("sellStockStr", goods.get("sell_stock")+"人购买");
				List<Map<String, Object>> tags = new ArrayList<>();
				// 爆款商品按照sell_stock已销售排序取缓存中配置 
				boolean isHot = goodsService.isHotGoods(Integer.parseInt(goods.get("id").toString()));
				if(isHot) {
					Map<String, Object> hotTag = new HashMap<>();
					hotTag.put("tagName", "爆款");
					hotTag.put("color", "#FF4931");
					hotTag.put("imgPath", ClientConstants.ALIBABA_PATH + "images/hot.png");
					tags.add(hotTag);
				} 
				// 推荐
				boolean isRecommend = Integer.parseInt(goods.get("is_recommend")+"") == 1 ? true : false ;
				if(isRecommend) {
					Map<String, Object> recommendTag = new HashMap<>();
					recommendTag.put("tagName", "推荐");
					recommendTag.put("color", "#00CC9F");
					recommendTag.put("imgPath", ClientConstants.ALIBABA_PATH + "images/recommend.png");
					tags.add(recommendTag);
				}
				 
				goods.put("isHot", isHot);
				goods.put("isSecondKill", false);
				goods.put("isRecommend", isRecommend);
				goods.put("shoppingPath",ClientConstants.ALIBABA_PATH + "images/newyear/shopping_card_1.png");// 购物车图片 （图片还没有放服务器）
				goods.put("tags", tags);
			}
			logger.info("商品列表循环遍历处理结束耗时: "+ (System.currentTimeMillis() - startTime)+"ms.");
			int count = goodsService.getAppGoodsCount(map);
			int pages = calcPage(count, limit);
            if (goodsList.size() == 0) {
                map.put("list", null);
            } else {
                map.put("list", goodsList);
            }
            map.put("page", page);
            map.put("pages", pages);
            map.put("count", count);
            map.put("shoppingTopPath", ClientConstants.ALIBABA_PATH + "images/newyear/shopping_card_2.png");// 顶部购物车图片 （图片还没有放服务器）
//            String bullsShow = getCacheKeyValue(DictConstants.BULLS_SHOW);
//            map.put("bulls_show", bullsShow);
            User user = userService.checkLogin(token);
            if(null == user) {
                map.put("carCount", 0);
            }else {
//                map.put("carCount", goodsShoppingCartService.getShoppingCartCount(user.getId()));
				map.put("carCount", goodsShoppingCartService.getCacheShoppingCartCount(user.getId()));
            }
            
            
			// 兼容老版本不修改
			List<Map<String, Object>> filterList=new ArrayList<Map<String,Object>>();
			
			Map<String, Object> typeMap = new HashMap<String, Object>();
			typeMap.put("title", "种类");	
			typeMap.put("parameter", "categoryId");	
			typeMap.put("subTitles", GoodsTypeEnum.enumParseMap());
			filterList.add(typeMap);
			
			Map<String, Object> priceMap = new HashMap<String, Object>();
			priceMap.put("title", "价格(元)");		
			priceMap.put("parameter", "priceRange");	
			priceMap.put("subTitles", GoodsPriceRangeEnum.enumParseMap());
			filterList.add(priceMap);
			map.put("filter", filterList);

			return new AppResult(SUCCESS, map);
		} catch (Exception e) {
			e.printStackTrace();
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}

	/**
	 * @date 2019年5月24日
	 * @author wangyun
	 * @time 下午4:02:45
	 * @Description 商品详情
	 *
	 * @param appVersion
	 * @param goodsId
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "商品详情")
	public AppResult detail(@ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("商品ID") @RequestParam Integer goodsId,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("用户token") @RequestParam(required=false) String token,
							@ApiParam("活动ID") @RequestParam(required=false) Integer activityId,
			@ApiParam("安卓渠道") @RequestParam(required = false) String dataSource,
			@ApiParam("app名字") @RequestParam(required = false) String appName,
			@ApiParam("包含设备信息") @RequestParam(required = false) String deviceInfo
			) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("tips", "");
			Goods goods = goodsService.getGood(goodsId);
			if(goods == null) {
				return new AppResult(FAILED,"该商品不存在");
			}
			int count = 0;
			Boolean isAddShopingCart = false;

			map.put("amount", goods.getSalingPrice().toString());
			User user = userService.checkLogin(token);
            if(user != null) {
                //查询购物车数量
//              count = goodsShoppingCartService.queryShoppingCartCount(user.getId());
                count = goodsShoppingCartService.getCacheShoppingCartCount(user.getId());

                //查询商品是否在购物车里
                isAddShopingCart = goodsShoppingCartService.getCacheShoppingCartGoodsCount(user.getId(), goodsId) > 0 ? true : false;
                if(user.getLevel()>0) {
                    map.put("tips", "您属于我们的会员客户可以享受会员价格");
                    map.put("amount", goods.getMemberSalingPrice());
                    map.put("amountStr", MONEY_FORMAT.format(goods.getMemberSalingPrice()));
                    map.put("amountLable", "会员价");
                } else {
                     map.put("amount", goods.getSalingPrice());
                     map.put("amountStr", MONEY_FORMAT.format(goods.getSalingPrice()));
                     map.put("amountLable", "普通价");
                }
            } else {
            	map.put("amount", goods.getSalingPrice());
                map.put("amountStr", MONEY_FORMAT.format(goods.getSalingPrice()));
                map.put("amountLable", "普通价");
            }
        
			List<String> smallPics = new ArrayList<String>();//小图列表
			List<Map<String, String>> bigPics = new ArrayList<>();//大图列表
			List<GoodsPicture> picList = goods.getGoodsPictures();
			
			Iterator<GoodsPicture> iterator = picList.iterator();
	        while(iterator.hasNext()){
	        	GoodsPicture pic = iterator.next();
	        	if(pic.getType().intValue() == 14) {//小图
					smallPics.add(pic.getUpload().getCdnPath());
					iterator.remove();//原来列表移除小图
				}else if(pic.getType().intValue() == 12){
					Map<String, String> bigPic = new HashMap<>();
					bigPic.put("pictureUrl", pic.getUpload().getCdnPath());
				    bigPics.add(bigPic);
				}
	        }
			//如有多张小图只显示一张
			String smallPic = (smallPics!=null && smallPics.size() > 0) ?  smallPics.get(0) : null;
			goods.setGoodsPictures(picList);//去除原来小图,单独放smallPic里面

			//查询产品属性
			String propertyValue = "";
			List<GoodsProperty> goodsPropertises = new ArrayList<>();
			if(goods.getGoodsCategory() != null ) {
				goodsPropertises = goods.getGoodsCategory().getGoodsProperties();
				String name = "规格";

	            if(goodsPropertises != null  && goodsPropertises.size() >  0) {
	            	for (GoodsProperty goodsProperty : goodsPropertises) {
	            		if(name.equals(goodsProperty.getPropertyName())) {
	            			GoodsPropertyValue goodsPropertyValue = goodsPropertyValueService.getValueByGoodsIdAndPropertyId(goodsId, goodsProperty.getId());
	            			if(goodsPropertyValue != null) {
	            				propertyValue = goodsPropertyValue.getPropertyValue();
	            			}
	            			break;
	            		}
					}
	            }
			}

			// 是否是秒杀商品
			List<SecondKillActivityView> killGoods = goodsService.activityKillGoodsByGoodsId(goodsId);

			Map<String, Object> secondKill = new HashMap<>();
			if(!killGoods.isEmpty() && getVersion(appVersion) >= 200 && activityId != null) {	//秒杀商品2.0.0以上显示,只能从H5秒杀列表进入
				map.put("isSecondKill", true);
				SecondKillActivityView activity = killGoods.get(0);
				
				Map<String, Object> goodsInfo = getActivityGoodsInfo(activity);
				secondKill.put("status", goodsInfo.get("status"));
				secondKill.put("statusStr", goodsInfo.get("statusStr"));
				
				BigDecimal amount = new BigDecimal((user != null && user.getLevel()>0) ? activity.getMemberPrice() : activity.getPrice());
				//秒杀结束后不显示秒杀价
				if(Integer.parseInt(goodsInfo.get("status")+"") == 0) {
					secondKill.put("statusStr", "距秒杀结束还剩");
					// 秒杀商品文字改为秒杀价
					map.put("amountLable", "秒杀价");
					map.put("amount", amount);
					map.put("amountStr",  MONEY_FORMAT.format(amount));
				} else if(Integer.parseInt(goodsInfo.get("status")+"") == 1) {
					secondKill.put("statusStr", "距开始还剩");
					map.put("bottomStr", "距开始"); // app购买底部文字
					// 秒杀商品文字改为秒杀价
					map.put("amountLable", "秒杀价");
					map.put("amount",  amount);
					map.put("amountStr",  MONEY_FORMAT.format(amount));
				}
				
				secondKill.put("preheatTime", goodsInfo.get("preheatTime"));// 预热时间
				secondKill.put("lockTime", goodsInfo.get("lockTime"));
				secondKill.put("memberPrice", activity.getMemberPrice());// 秒杀商品只显示会员价
				secondKill.put("price", activity.getPrice());// 
				secondKill.put("memberPriceStr", MONEY_FORMAT.format(activity.getMemberPrice()));// 秒杀商品只显示会员价
				secondKill.put("priceStr", MONEY_FORMAT.format(activity.getPrice()));// 
				secondKill.put("picPath", goodsInfo.get("picPath"));
				secondKill.put("beginTime", activity.getBeginTime());
				secondKill.put("endTime", activity.getEndTime());
				secondKill.put("startTime", activity.getStartTime());
				secondKill.put("activityId", activity.getId());
				secondKill.put("secondKillImgPath", ClientConstants.ALIBABA_PATH + "images/newyear/miaosha_logo.png");
				// 已售罄 0能买
				Object saleOver = (user == null) ? 0 : redisService.get(String.format(RedisConstants.SECONDKILL_ACTIVITY_SALE_OVER, activity.getId(), user.getId()));
				Integer killSaleOver = saleOver == null ? 0 : Integer.parseInt(saleOver+"");
				// 限购0能买
				Object unPay = (user == null) ? 0 : redisService.get(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_UNPAY, activity.getId(), user.getId()));
				Integer killUnPay = unPay == null ? 0 : Integer.parseInt(unPay+"");
				// 限购0 能买
				Object cannotBuy = (user == null) ? 0 : redisService.get(String.format(RedisConstants.SECONDKILL_ACTIVITY_PERSON_CANNOT_BUY,activity.getId(),user.getId()));
				Integer killCannotBuy = cannotBuy == null ? 0 : Integer.parseInt(cannotBuy+"");
				String canKillSecondStr = "";
				if(killSaleOver == 0 && killUnPay == 0 && killCannotBuy == 0) { // 其中一个不为0 就返回false
					secondKill.put("canKillSecond", true);
				} else {
					secondKill.put("canKillSecond", false);
					if(killSaleOver != 0) {
						canKillSecondStr = "已售罄" ;
					} else if(killUnPay != 0) {
						canKillSecondStr = "去支付" ;
					} else if(killCannotBuy != 0) {
						canKillSecondStr = "限购商品";
					}
				}
				secondKill.put("canKillSecondStr", canKillSecondStr);

			} else {
				map.put("isSecondKill", false);
			}
			map.put("secondKill", secondKill);

			//查询1 条置顶评论
			Map<String, Object> assessMap = new HashMap<>();
			Assess assess = assessService.getTopAssessByGoodsId(goodsId);
			StringBuffer path = new StringBuffer();
			if(assess != null) {
				//置顶评论图片
				List<AssessImgs> imgs = assess.getAssessImgs();
				Iterator<AssessImgs> imgIterator = imgs.iterator();
		        while(imgIterator.hasNext()){
		        	AssessImgs img = imgIterator.next();
		        	path.append(img.getUpload() != null ? img.getUpload().getCdnPath():null);
					path.append(",");
					imgIterator.remove();
		        }

		        User assUser = userService.get(assess.getUserId());
		        // 用户昵称
		        if(assess.getIsAnonymous() == 1) {
		        	assessMap.put("userName", "匿名");
				} else {
					assessMap.put("userName", assUser.getUsername());
				}
		        
		        // 用户头像
		        if(assUser !=null && null != assUser.getAvatarId()) {
					Upload upload = uploadService.get(assUser.getAvatarId());
					assessMap.put("headIcon", ClientConstants.ALIBABA_PATH + "upload/" + upload.getPath());
				}else {
					assessMap.put("headIcon", ClientConstants.ALIBABA_PATH + "upload/login.png");
				}
		        
		        assessMap.put("content", assess.getContent());
				assessMap.put("time", DateFormatUtils.format(assess.getCreateDate(), "yyyy.MM.dd"));
			}
			int asscount = assessService.countAssessByGoodsId(goodsId);
			assessMap.put("moreH5Url", ClientConstants.H5_URL+"commentList.html?goodsId="+goodsId);
			assessMap.put("count", asscount);
			assessMap.put("userAssessLable", "用户评价("+asscount+")");
			assessMap.put("moreLable", "查看全部");
			assessMap.put("imagePath",  path.length() > 0 ? path.deleteCharAt(path.length() - 1) : null);

			map.put("stockUnit", goods.getStockUnit());
			map.put("assess", assessMap);
			map.put("introduction", goods.getIntroduction());
			map.put("goodsName", goods.getGoodsName());
			map.put("propertyValues", propertyValue);
			map.put("propertyLable", "规格");
			map.put("stock", goods.getStock());
			map.put("stockLable", "库存");
			map.put("salingPrice", goods.getSalingPrice());
			map.put("memberSalingPrice", goods.getMemberSalingPrice());
			map.put("salingPriceStr", MONEY_FORMAT.format(goods.getSalingPrice()));
			map.put("memberSalingPriceStr", MONEY_FORMAT.format(goods.getMemberSalingPrice()));
			map.put("smallPic", smallPic);
			map.put("shoppingCartCount", count);
			map.put("isAddShopingCart", isAddShopingCart);
			map.put("bigPics", bigPics);
			map.put("introductionHtml", goods.getIntroductionHtml());
			map.put("introductionUrl", ClientConstants.H5_URL+"goodsDetail.html?token="+token+"&goodsId="+goodsId+"&appVersion="+appVersion+"&client="+client+"");

			//是否屏蔽养牛信息
			boolean isShowBulls = projectService.isShowBulls(user,appName,dataSource,client);

			map.put("member", "如何成为会员？");
			if(isShowBulls) {
				map.put("memberUrl", ClientConstants.H5_URL+"member.html");
			}else{
				map.put("memberUrl", ClientConstants.H5_URL+"member2.html");
			}

			map.put("refund", "退换货须知");
            map.put("refundUrl", ClientConstants.H5_URL+"instructionsrules.html");
            map.put("skuCode", goods.getSkuCode());
            map.put("weight", goods.getWeight());

            //客服对象
            Map<String, Object> im = new HashMap<>();
            im.put("link", "");// 链接地址
            im.put("pictureUrl", ClientConstants.ALIBABA_PATH + "images/newyear/customer_service.png");// 图标图片地址
            im.put("pictureUrl2", ClientConstants.ALIBABA_PATH + "images/newyear/customer_service2.png");// 图标图片地址
            map.put("im", im);

            //插入 t_goods_click 没登录用户userId=null @sxy
            goodsClickService.addOrUpdateClick(user != null ? user.getId(): null, goods.getId());
			return new AppResult(SUCCESS, map);
		} catch (Exception e) {
			e.printStackTrace();
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}
	/**
	 * @date 2019年5月23日
	 * @author wangyun
	 * @time 下午4:26:49
	 * @Description 商城订单列表
	 *
	 * @param token
	 * @param appVersion
	 * @param status
	 * @param client
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/orderList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "商城订单列表")
	public AppResult orderList(
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("状态") @RequestParam(required = false) Integer status,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("页码") @RequestParam Integer page) {
		try {
			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
			int limit = 10;
			if (page == null) {
	            page = 1;
	        }
			Map<String, Object> map = new HashMap<>();
			GoodsOrderListResponse result = new  GoodsOrderListResponse();
	        GoodsOrderListRequest req = new GoodsOrderListRequest();
	        req.setUserId(user.getId());
	        req.setLimitEnd(limit);
	        req.setLimitStart((page - 1) * limit);
	        req.setStatus(status);
	        ServiceMessage msg = new ServiceMessage("goodsorder.list", req);
	        result = (GoodsOrderListResponse) TransactionClient.getInstance().setServiceMessage(msg).send();
	        if(result.isSuccess()) {
	        	List<GoodsOrder> orderList = result.getList();

	        	int pages = calcPage(result.getCount(), limit);
	            if (orderList.size() == 0) {
	                map.put("list", null);
	            } else {
	                List<Map<String, Object>> orderMapList = new ArrayList<>();
	                for (GoodsOrder goodsOrder : orderList) {
	                	Map<String, Object> orderMap = new HashMap<>();
	                	orderMap = getGoodsOrderInfo(goodsOrder);
	                	//订单状态 0未支付 1支付中 2已支付 3拣货中 4已发货 5订单取消 6退款完成 7交易完成  8退款中
	    				int status_ = goodsOrder.getState();
	    				if(status_ == 0 || status_ ==1) {//waiting_pay.png
	    					orderMap.put("statusStr", "待付款");
	    				}
	    				if(status_ == 3 || status_ == 2) {
	    					orderMap.put("statusStr", "待发货");
	    				}
	    				if(status_ == 4) {
	    					orderMap.put("statusStr", "待收货");
	    				}
	    				if(status_ == 5) {//交易关闭(订单取消)
	    					orderMap.put("statusStr", "交易关闭");
	    					orderMap.put("statusDesc", "订单取消");
	    				}
	    				if(status_ == 6) {//交易关闭(退款完成 )
	    					orderMap.put("statusStr", "交易关闭");
	    					orderMap.put("statusDesc", "退款完成");
	    				}
	    				if(status_ == 7) {//交易完成
	    					orderMap.put("statusStr", "交易成功");
	    					orderMap.put("statusDesc", "交易成功");
	    				}
	    				if(status_ == 8) {
	    					orderMap.put("statusStr", "交易关闭");
	    					orderMap.put("statusDesc", "退款处理中");
	    				}
	    				orderMapList.add(orderMap);
					}
	                map.put("list", orderMapList);
	            }
	            map.put("page", page);
	            map.put("pages", pages);
	            map.put("count", result.getCount());
	            map.put("isForbidComment", user.getIsForbidComment());//是否禁止评论 0否 1是
	            //状态枚举
	            List<Map<String, Object>> orderStateList=new ArrayList<Map<String,Object>>();
                Map<String, Object> orderStateMap=new HashMap<String, Object>();
                orderStateMap.put("code", "");
                orderStateMap.put("description", "全部");
                orderStateList.add(orderStateMap);
                orderStateMap = new HashMap<String, Object>();
                orderStateMap.put("code", "0");
                orderStateMap.put("description", "待付款");
                orderStateList.add(orderStateMap);
                orderStateMap = new HashMap<String, Object>();
                orderStateMap.put("code", "2");
                orderStateMap.put("description", "待发货");
                orderStateList.add(orderStateMap);
                orderStateMap = new HashMap<String, Object>();
                orderStateMap.put("code", "4");
                orderStateMap.put("description", "待收货");
                orderStateList.add(orderStateMap);

                if(getVersion(appVersion) >= 200) {
                	 orderStateMap = new HashMap<String, Object>();
                     orderStateMap.put("code", "7");// 已收货
                     orderStateMap.put("description", "待评价");
                     orderStateList.add(orderStateMap);
                }
                map.put("orderState",orderStateList);
		        return new AppResult(SUCCESS, "查询成功", map);
	        } else {
	        	return new AppResult(FAILED, "查询失败");
	        }

		} catch (Exception e) {
			e.printStackTrace();
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}

	}

    /**
     * @date 2019年5月29日
     * @author wangyun
     * @time 下午6:31:44
     * @Description 跳转提交订单
     *
     * @return
     */
    @RequestMapping(value = "/toAddOrder", method = RequestMethod.POST)
    @ApiOperation(value = "跳转提交订单")
    @ResponseBody
    public AppResult toAddOrder(
            HttpServletRequest request,
            HttpSession session,
            @ApiParam("token") @RequestParam String token,
            @ApiParam("App版本号") @RequestParam String appVersion,
			@ApiParam("秒杀商品活动Id") @RequestParam(required = false) Integer activityId,
            @ApiParam("商品ID集合") @RequestParam List<Integer> goodsIds,
            @ApiParam("对应商品数量集合") @RequestParam List<Integer> counts,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
    	Map<String, Object> map = new HashMap<>();
    	try {
    		User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, "登录失效，请重新登录");
            }
            /*//用户实名未绑卡
            if (StringUtils.isBlank(user.getTrueName()) && StringUtils.isBlank(user.getIdentityCard())) {
                return new AppResult(FAILED, "请先实名");
            }
            */

			if(goodsIds.size()!=counts.size()) {
				return new AppResult(FAILED,"参数错误");
			}

			BigDecimal orderTotalMoney = BigDecimal.ZERO;

			List<Map<String, Object>> list = new ArrayList<>();
			int totalCount = 0;
			if (goodsIds!=null) {
				// 剔除空元素（null）
				goodsIds.removeAll(Collections.singleton(null));
			}

			SecondKillDetailVO secondKillDetailVO = null;
			//参与秒杀活动
			if(activityId!=null && activityId>0) {

				if(goodsIds.size()!=1){
					return new AppResult(FAILED,"一次只能秒杀一种商品");
				}

				if(counts.get(0).intValue()>1) {
					return new AppResult(FAILED,"一次只能秒杀一个商品");
				}

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

			}
			for (int i = 0; i < goodsIds.size(); i++) {
			     //当前商品的库存
				Goods goods = goodsService.getMapper().selectByPrimaryKey(goodsIds.get(i));
				if (null == goods) {
				    return new AppResult(FAILED,"商品不存在");
				}
				if(counts.get(i) == 0) {
					return new AppResult(FAILED,"商品" + goods.getGoodsName() + "数量不能为0");
				}

				if(activityId!=null && activityId>0) {

				}else {
					if (goods.getStock() < counts.get(i) || goods.getStock() == 0) {
						return new AppResult(FAILED, "商品" + goods.getGoodsName() + "库存不足");
					}
				}

				List<GoodsPicture> smallPics = new ArrayList<>();
				List<GoodsPicture> picList = goods.getGoodsPictures();

				Iterator<GoodsPicture> iterator = picList.iterator();
		        while(iterator.hasNext()){
		        	GoodsPicture pic = iterator.next();
		        	if(pic.getType().intValue() == 14) {//小图
						smallPics.add(pic);
						iterator.remove();//原来列表移除小图
					}
		        }
				//如有多张小图只显示一张
				GoodsPicture smallPic = (smallPics!=null && smallPics.size() > 0) ?  smallPics.get(0) : null;
//				goodss.add(goods);
				goods.setGoodsPictures(picList);//去除原来小图,单独放smallPic里面
				Map<String, Object> map_ = new HashMap<>();
				map_.put("goodsName", goods.getGoodsName());
				map_.put("smallPic",smallPic!=null ? smallPic.getUpload().getCdnPath():null);
				map_.put("goodsCount", counts.get(i));
				map_.put("id", goods.getId());
				//计算金额

				boolean isVIP = user.getLevel() > 0 ? true : false;

				BigDecimal buyPrice = BigDecimal.ZERO;

				//秒杀价格
				if(activityId!=null && activityId>0) {
					buyPrice = isVIP ? secondKillDetailVO.getMemberPrice() : secondKillDetailVO.getPrice();
				}else{
					buyPrice = isVIP ? goods.getMemberSalingPrice() : goods.getSalingPrice();
				}

				orderTotalMoney = orderTotalMoney
						.add(buyPrice.multiply(BigDecimalUtil.parse(counts.get(i))));
				map_.put("price", buyPrice);
				map_.put("priceStrValue", MONEY_FORMAT.format(buyPrice));
				map_.put("stockUnit", goods.getStockUnit());
//				map_.put("stock", goods.getStockUnit());
				list.add(map_);
//				totalCount = totalCount + counts.get(i);
				totalCount += 1;
			}
			UserAddress address = userAddressService.queryAddress(user.getId());
			int hongbaoCount = hongbaoService.getHongbaoInverstmentCount(user.getId(), 3, 0, orderTotalMoney.doubleValue(), null); // 有效优惠券个数
	        map.put("hongbaoCount", hongbaoCount);
			map.put("goodses", list);
			map.put("address", address);
			map.put("userLevel", user.getLevel());
			map.put("orderTotalMoney", orderTotalMoney);
			map.put("orderTotalMoneyStrValue", MONEY_FORMAT.format(orderTotalMoney));
			map.put("orderType", OrderTypeEnum.GOODS.getFeatureName());
			map.put("totalCount", totalCount);
			map.put("distributionLable", "配送方式");
//			if(orderTotalMoney.compareTo(BigDecimal.valueOf(200))== -1) {
			map.put("distribution",  "快递");
//			}
			/*if(address != null) {
				Map<String, Object> expressMap = goodsService.calculateExpressFee(weight, orderTotalMoney, address.getaId());
				map.put("distribution",  "快递"+expressMap.get("expressFee"));
				
			} else {
				if(orderTotalMoney.compareTo(BigDecimal.valueOf(200))== -1) {
				    map.put("distribution",  "快递（邮费到付）");
				}
			}
			*/
			
			/*if(orderTotalMoney.compareTo(BigDecimal.valueOf(200))== -1) {
			    map.put("distribution",  "快递（邮费到付）");
			}*/

			//读取用户的红包数据，取最大的可用的红包

			
			return new AppResult(SUCCESS,map);
		} catch (Exception e) {
			 logger.error("提交订单异常");
	         e.printStackTrace();
	         return new AppResult(ERROR, "提交订单异常");
		}
    }


    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "下单")
    public AppResult submitOrder(
        @ApiParam("用户token") @RequestParam String token,
        @ApiParam("App版本号") @RequestParam String appVersion,
//        @ApiParam("订单编号") @RequestParam String orderNo,
        @ApiParam("余额支付金额") @RequestParam(required = false) Double balanceAmount,
        @ApiParam("授信支付金额") @RequestParam(required = false) Double creditAmount,
        @ApiParam("客户端（IOS,Android,PC,WAP）") @RequestParam String client,
        @ApiParam("地址") @RequestParam Integer addressId,
        @ApiParam("商品集合") @RequestParam List<Integer> goodsIds,
        @ApiParam("商品数量集合") @RequestParam List<Integer> counts,
        @ApiParam("红包ID") @RequestParam(required = false) Integer hongbaoId,
		@ApiParam("秒杀活动id") @RequestParam(required = false) Integer activityId,
		@ApiParam("是否启用默认授信扣款") @RequestParam(required=false) boolean isAutoUseCredit,
        @ApiParam("是否启用默认余额扣款") @RequestParam(required=false) boolean isAutoUseBalance) {
        long start = System.currentTimeMillis();
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            Integer userId = user.getId();
            if (null == userId || null == addressId
                || null == goodsIds || null == counts) {
                return new AppResult(FAILED, "[userId,goodsIds,counts,addressId]为必传参数");
            }
//            if (goodsIds.size() < 1 || counts.size() < 1
//                    || goodsIds.size() != counts.size()) {
//                return new AppResult(FAILED, "参数错误");
//            }
//            //判断收货地址是否是本人的
//            UserAddress userAddress = userAddressService.selectKeyUserAddress(addressId);
//            if (userAddress == null) {
//                return new AppResult(FAILED, "收货地址不存在");
//            }
//            if (userAddress.getUserId().intValue() != userId.intValue()) {
//                return new AppResult(FAILED, "收货地址归属不正确");
//            }
//
//            Hongbao hongbao = null;
//            //判断红包是否可用
//            if (null != hongbaoId && 0 != hongbaoId) {
//                hongbao = hongbaoService.get(hongbaoId);
//                if (hongbao == null) {
//                    return new AppResult(FAILED, "红包不存在");
//                }
//                if (hongbao.getUserId().intValue() != userId.intValue()) {
//                    return new AppResult(FAILED, "红包归属错误");
//                }
//                Date currentDate = new Date();
//                if (hongbao.getExpireTime().getTime() < currentDate.getTime()) {
//                    return new AppResult(FAILED, "红包已过期");
//                }
//                if (hongbao.getUseTime() != null) {
//                    return new AppResult(FAILED, "红包已使用");
//                }
//                if (hongbao.getType() != 3) {
//                    return new AppResult(FAILED, "红包不支持此类订单");
//                }
//            }

            long startapi = System.currentTimeMillis();
            GoodsOrderRequest req = new GoodsOrderRequest();
//            req.setOrderNo(orderNo);
            req.setClientEnum(ClientEnum.getValueByName(client));
            if(creditAmount==null) {
            	creditAmount=0d;
            }
            req.setCreditPayMoney(BigDecimalUtil.parse(creditAmount));
            if(balanceAmount==null) {
            	balanceAmount=0d;
            }
            req.setBalancePayMoney(BigDecimalUtil.parse(balanceAmount));
            req.setUserId(user.getId());
            req.setAddressId(addressId);
            req.setHongbaoId(hongbaoId);
            req.setGoodsIds(goodsIds);
			req.setAutoUseCredit(isAutoUseCredit);
            req.setAutoUseBalance(isAutoUseBalance);
            req.setCounts(counts);

			req.setSecondKill(false);

            //参与秒杀活动
            if(activityId!=null && activityId>0) {
				req.setSecondKill(true);
				req.setActivityDetailId(activityId);
			}

            ServiceMessage msg = new ServiceMessage("goodsorder.submit", req);
            OrderResponse response = (OrderResponse) TransactionClient.getInstance().setServiceMessage(msg).send();
            if(response instanceof OrderResponse) {
                OrderResponse orderResponse = (OrderResponse)response;
                if (orderResponse.isSuccess()) {
                    long endapi = System.currentTimeMillis();
                    logger.info((endapi-startapi)+"api ms");
                    long end = System.currentTimeMillis();
                    logger.info((end-start)+"ms");
                    Map<String, Object> map = new HashMap<>();
                    map.put("payResult", ClientConstants.H5_URL+"payResult.html?token="+token+"&appVersion="+appVersion+"&client="+client+"&orderNo="+orderResponse.getOrderNo()+"&orderType="+OrderTypeEnum.GOODS.getFeatureName()+"&id="+orderResponse.getId()+"");
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

        }catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }


    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "取消订单")
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

            GoodsOrderRequest req = new GoodsOrderRequest();
            req.setUserId(user.getId());
            req.setOrderNo(orderNo);
            req.setClientEnum(ClientEnum.getValueByName(client));
            ServiceMessage msg = new ServiceMessage("goodsorder.cancel", req);
            Response result = TransactionClient.getInstance().setServiceMessage(msg).send();

            if(result.isSuccess()){
                return new AppResult(SUCCESS, "订单取消成功", "");
            }else{
                return new AppResult(FAILED, "订单取消失败", result.getErrorMsg());
            }
        }catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

    @RequestMapping(value = "/getGoodsOrderDtail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "商城订单详情")
    public AppResult getGoodsOrderDetail( @ApiParam("app版本号") @RequestParam String appVersion,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("订单号") @RequestParam String orderNo) {
    	try {
    		User user = userService.checkLogin(token);
	        if (user == null) {
	            return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
	        }

	        GoodsOrderDetailRequest request = new GoodsOrderDetailRequest();
	        GoodsOrderResponse response = new GoodsOrderResponse();
	        request.setOrderNo(orderNo);
	        request.setUserId(user.getId());
	        ServiceMessage msg = new ServiceMessage("goodsorder.detail", request);
    		response = (GoodsOrderResponse) TransactionClient.getInstance().setServiceMessage(msg).send();

    		Map<String, Object> map = new HashMap<>();
    		if(response.isSuccess()) {
    			GoodsOrder order = response.getOrder();
    			String statusImgUrl = ClientConstants.ALIBABA_PATH + "images/order/status/";
    			if(order != null) {
    				map = getGoodsOrderInfo(order);
    				//订单状态 0未支付 1支付中 2已支付 3拣货中 4已发货 5订单取消 6退款完成 7交易完成  8退款中
    				int status = order.getState();
    				map.put("topTips", "");
    				map.put("titleLable", "商城订单");
    				map.put("balancePayMoneyLable", "余额支付");
    				map.put("creditPayMoneyLable", "冻结利润支付");
    				map.put("realPayMoneyLable", "实际支付");
    				map.put("hongbaoMoneyLable", "红包抵扣");
    				
    				if(status == 0 || status ==1) {//waiting_pay.png
    					map.put("statusImg", statusImgUrl + "waiting_pay.png");
    					map.put("statusStr", "等待买家付款");
//    					map.put("remainTimeDesc", "剩余："+ map.get("lockTime")+"秒后自动关闭");
    				}
    				if(status == 3 || status == 2) {
    					map.put("statusImg", statusImgUrl + "payed.png");
    					map.put("statusStr", "买家已付款");
    					map.put("refundTotalAmountLable", "需退款总金额（含邮费￥0.00）");
    					refundMapInit(map, order);
    					
    				}
    				if(status == 4) {
    					map.put("statusImg", statusImgUrl + "sendedout.png");
    					map.put("statusStr", "平台已发货");

    				}
    				if(status == 5) {//交易关闭(订单取消)
    					map.put("statusImg", statusImgUrl + "closed.png");
    					map.put("statusStr", "交易已关闭");
    					map.put("statusDesc", "订单取消");
    				}
    				if(status == 6) {//交易关闭(退款完成 )
    					map.put("statusImg", statusImgUrl + "closed.png");
    					map.put("statusStr", "交易已关闭");
    					map.put("statusDesc", "退款完成");
    					refundMapInit(map, order);
    				}
    				if(status == 7) {//交易完成
    					map.put("statusImg", statusImgUrl + "success.png");
    					map.put("statusStr", "交易成功");
    					
    				}
    				if(status == 8) {
    					map.put("statusImg", statusImgUrl + "auding.png");
    					map.put("statusStr", "等待平台处理");
    					map.put("topTips", "平台工作人员如超时未处理，系统将自动退款至您的支付渠道平 台，如您使用了红包，红包将一并返还。 ");
    					refundMapInit(map, order);
                    
    				}
    				
    				map.put("assess", null);
    				map.put("distributionLable", "配送方式");
    				map.put("distribution",  "快递");
    				
    			}

    			return new AppResult(SUCCESS,"查询成功",map);
    		} else {
    			return new AppResult(FAILED,"查询失败");
    		}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
	        return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
    }

    public void refundMapInit(Map<String, Object> map, GoodsOrder order) {
    	// 退款lable
    	String expressFeeStr = "（含邮费"+order.getExpressFeeStr()+"）";
    	if(order.getExpressFee() == null || order.getExpressFee().compareTo(BigDecimal.ZERO) == 0) {
    		expressFeeStr = "";
    	}
    	map.put("refundTotalAmountLable", "需退款总金额"+expressFeeStr);
        map.put("refundFrozenLable", "需退款至冻结利润");
        map.put("refundBlanceLable", "需退款至余额");
        map.put("refundHongbaoLable", "需红包退回");
        map.put("refundMoneyLable", "需退款至银行");
    	// 退款字段
		map.put("refundTotalAmount", order.getTotalMoney());
        map.put("refundFrozen", order.getCreditPayMoney());
        map.put("refundBlance",order.getBalancePayMoney());
        map.put("refundHongbao", order.getHongbaoMoney());
        map.put("refundMoney", order.getRealPayMoney());
        // 2.0修改金额返回字符串
        map.put("refundTotalAmountStr", order.getTotalMoneyStr()+"");
        map.put("refundFrozenStr", order.getCreditPayMoneyStr());
        map.put("refundBlanceStr",order.getBalancePayMoneyStr());
        map.put("refundHongbaoStr", order.getHongbaoMoneyStr());
        map.put("refundMoneyStr", order.getRealPayMoneyStr());
    }
    
	/**
	 * @description 确认收货
	 * @author shuys
	 * @date 2019/6/3
	 * @param appVersion
	 * @param token
	 * @param orderNo
	 * @return com.goochou.p2b.app.model.AppResult
	*/
	@RequestMapping(value = "/confirmReceipt", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "确认收货")
	public AppResult confirmReceipt(
			@ApiParam("app版本号") @RequestParam String appVersion,
			@ApiParam("用户token") @RequestParam String token,
			@ApiParam("订单号") @RequestParam String orderNo
	) {
		try {
			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
			GoodsOrder goodsOrder = goodsOrderService.queryGoodsOrderByNumber(orderNo);
			if (goodsOrder == null) {
				return new AppResult(FAILED, MESSAGE_EXCEPTION);
			}
			if (goodsOrder.getState()!=GoodsOrderStatusEnum.SENDED.getCode()) { // 订单状态不为已发货
				return new AppResult(FAILED, "错误的订单类型");
			}
			// 更新订单状态为交易完成
			goodsOrderService.updateOrderState(goodsOrder.getId(), GoodsOrderStatusEnum.COMPLETED.getCode());
			// 增加节点
	        OrderDone orderDone = new OrderDone();
	        orderDone.setOrderNo(goodsOrder.getOrderNo());
	        orderDone.setOrderStatus(OrderDoneEnum.SUCCESS.getFeatureName());
	        orderDone.setUpdateDate(new Date());
	        orderDone.setCreateDate(new Date());
	        orderDone.setOrderType(OrderTypeEnum.GOODS.getFeatureName());
	        if (orderDoneService.insert(orderDone) != 1) {
	            return new AppResult(FAILED,"确认收货失败");
	        }else {
	            return new AppResult(SUCCESS,"确认收货成功");
	        }
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}


	/**
	 * @description 删除订单
	 * @author shuys
	 * @date 2019/6/4
	 * @param appVersion
	 * @param token
	 * @param orderNo
	 * @return com.goochou.p2b.app.model.AppResult
	 */
	@RequestMapping(value = "/delOrder", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "删除订单")
	public AppResult delOrder(
			@ApiParam("app版本号") @RequestParam String appVersion,
			@ApiParam("用户token") @RequestParam String token,
			@ApiParam("订单号") @RequestParam String orderNo
	) {
		try {
			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
			GoodsOrder goodsOrder = goodsOrderService.queryGoodsOrderByNumber(orderNo);
			if (goodsOrder == null) {
				return new AppResult(FAILED, MESSAGE_EXCEPTION);
			}
			if (goodsOrder.getState() != GoodsOrderStatusEnum.COMPLETED.getCode() &&  // 交易完成
				goodsOrder.getState() != GoodsOrderStatusEnum.CANCEL.getCode() && // 取消订单
					goodsOrder.getState() != GoodsOrderStatusEnum.REFUND.getCode()) {  // 订单退款
				return new AppResult(FAILED, "错误的订单类型");
			}
			// 修改订单为隐藏状态(删除订单)
			goodsOrderService.updateIsHiddenById(goodsOrder.getId());
			return new AppResult(SUCCESS,"删除订单成功");

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}


	@SuppressWarnings("deprecation")
	public Map<String, Object> getGoodsOrderInfo(GoodsOrder order){

		Map<String, Object> map = new HashMap<>();
		if(order != null) {
			map.put("createTime", DateUtil.dateFullTimeFormat.format(order.getCreateDate()));
			map.put("addressDetail", order.getAddresseeDetail());
			map.put("addresseeName", order.getAddresseeName());
			map.put("addresseePhone", order.getAddresseePhone());
			map.put("orderNo", order.getOrderNo());
			map.put("orderNoLable", "订单编号");
			map.put("id", order.getId());
			
			Integer userId = order.getUserId();
			User user = userService.get(userId);
			//筛选图片
			List<GoodsOrderDetail> orderDetails = order.getGoodsOrderDetail();
			List<Map<String, Object>> goodsInfoList = new ArrayList<>();
			if(!orderDetails.isEmpty()) {
				for (GoodsOrderDetail goodsOrderDetail : orderDetails) {
					Map<String, Object> goodsInfo = new HashMap<>();
					Goods goods = goodsOrderDetail.getGoods();
					List<GoodsPicture> smallPics = new ArrayList<>();
					List<GoodsPicture> picList = goods.getGoodsPictures(); //goods不能为空！
					Iterator<GoodsPicture> iterator = picList.iterator();
			        while(iterator.hasNext()){
			        	GoodsPicture pic = iterator.next();
			        	if(null != pic && pic.getType().intValue() == 14) {//小图
							smallPics.add(pic);
	//						iterator.remove();//原来列表移除小图
						}
			        }
			        //如有多张小图只显示一张
					GoodsPicture smallPic = (smallPics!=null && smallPics.size() > 0) ?  smallPics.get(0) : null;
					goodsInfo.put("smallPic", smallPic!=null ? smallPic.getUpload().getCdnPath():null);
					goodsInfo.put("goodId", goodsOrderDetail.getGoodsId());
					goodsInfo.put("goodsName", goods.getGoodsName());
	
					//查看商品属性
					String propertyValue = "";
					List<GoodsProperty> goodsPropertises = new ArrayList<>();
					if(goods.getGoodsCategory() != null ) {
						goodsPropertises = goods.getGoodsCategory().getGoodsProperties();
						String name = "净含量";
	
	    	            if(goodsPropertises != null  && goodsPropertises.size() >  0) {
	    	            	for (GoodsProperty goodsProperty : goodsPropertises) {
	    	            		if(name.equals(goodsProperty.getPropertyName())) {
	    	            			GoodsPropertyValue goodsPropertyValue = goodsPropertyValueService.getValueByGoodsIdAndPropertyId(goods.getId(), goodsProperty.getId());
	    	            			if(goodsPropertyValue != null) {
	    	            				propertyValue = goodsPropertyValue.getPropertyValue();
	    	            			}
	    	            			break;
	    	            		}
	    					}
	    	            }
					}
	
					// 商品是否显示评价按钮
					boolean isShowEvaluate = false;
					List<Assess> asslist = assessService.getAssessByGoodsIdAndUserId(goodsOrderDetail.getGoodsId(), order.getUserId(), order.getId());
					if(asslist.isEmpty() && order.getState() == 7) { //已完成的订单,并且未评价才显示评价按钮
						isShowEvaluate = true;
					}
					
					goodsInfo.put("isShowEvaluate", isShowEvaluate);
					 
					goodsInfo.put("addEvaluateUrl", ClientConstants.H5_URL+"addComment.html?noRefreshInViewWillAppear=1"+"&goodsId="+goods.getId()+"&goodsUrl="+goodsInfo.get("smallPic")
							+"&orderNo="+order.getOrderNo()+"&goodsName="+URLEncoder.encode(goods.getGoodsName())+"&token="+user.getToken());
					goodsInfo.put("stockUnit", goods.getStockUnit());
					goodsInfo.put("propertyValue", propertyValue);
					goodsInfo.put("pirce", goodsOrderDetail.getSalePrice());
					goodsInfo.put("count", goodsOrderDetail.getCount());
					// 2.0新增
					goodsInfo.put("pirceStr", goodsOrderDetail.getSalePriceStr());
					goodsInfoList.add(goodsInfo);
				}
			}
			map.put("goodsInfoList", goodsInfoList);
			map.put("status", order.getState());
			map.put("totalMoneyLable", "商品总价");
			map.put("totalMoney", order.getTotalMoney());
			map.put("expressFeeLable", "运费（快递）");
			map.put("expressNum", order.getExpressNum());
			
			map.put("expressFee", order.getExpressFee());
			map.put("realPayMoney", order.getRealPayMoney());
			map.put("hongbaoMoney", order.getHongbaoMoney());
			map.put("creditPayMoney", order.getCreditPayMoney());
			map.put("balancePayMoney", order.getBalancePayMoney());
			
			// 2.0邮费字段,为0显示包邮,不为0显示邮费价格,金额字段返回字符串
			map.put("totalMoneyStr", order.getTotalMoneyStr());
			map.put("expressFeeStr", order.getExpressFeeStr());
			map.put("realPayMoneyStr", order.getRealPayMoneyStr());
			map.put("hongbaoMoneyStr", "-"+order.getHongbaoMoneyStr());
			map.put("creditPayMoneyStr", order.getCreditPayMoneyStr());
			map.put("balancePayMoneyStr", order.getBalancePayMoneyStr());
			map.put("shouldPayMoneyStr", order.getShouldPayMoneyStr());
			
			map.put("expUrl", "https://m.kuaidi100.com/result.jsp?nu=" + order.getExpressNum());
			map.put("shouldPayMoneyLable", "实际支付金额:");
			map.put("shouldPayMoney", order.getTotalMoney()
					.subtract(order.getHongbaoMoney() != null ? order.getHongbaoMoney() :BigDecimal.ZERO)
					.add(order.getExpressFee() != null ? order.getExpressFee() :BigDecimal.ZERO));//总计

			long lockTime = 0;
            String createDate = null == order.getCreateDate() ? null : DateUtil.dateFullTimeFormat.format(order.getCreateDate());
            if (null != createDate) {
                String date = DateUtil.addtime(createDate, getCacheKeyValue(DictConstants.PAY_WAIT_TIME));
                if(date != null) {
                	 lockTime = DateUtil.dateDiffSecound(DateUtil.format(new Date(), DateTimeUtil.allPattern), date,
                             DateTimeUtil.allPattern);
                }
            }
            map.put("lockTime", lockTime);

			List<OrderDone> done = orderDoneService.queryOrderDoneListByOrderNo(order.getOrderNo());
			if (done != null && done.size() > 0) {
				for (OrderDone orderDone : done) {
					if(orderDone.getOrderStatus().equals(OrderDoneEnum.SUBMIT.getFeatureName())) {//下单时间
						map.put("submitTimeLable", "下单时间");
						map.put("submitTime", DateUtil.dateFullTimeFormat.format(orderDone.getCreateDate()));
					}
					if(orderDone.getOrderStatus().equals(OrderDoneEnum.PAY.getFeatureName())) {//支付时间
						map.put("payTimeLable", "付款时间");
						map.put("payTime", DateUtil.dateFullTimeFormat.format(orderDone.getCreateDate()));
					}
					if(orderDone.getOrderStatus().equals(OrderDoneEnum.CANNEL.getFeatureName())) {//取消时间
						map.put("cancelTimeLable", "取消时间");
						map.put("cancelTime", DateUtil.dateFullTimeFormat.format(orderDone.getCreateDate()));
					}
					if(orderDone.getOrderStatus().equals(OrderDoneEnum.SUCCESS.getFeatureName())) {//订单完成时间
						map.put("successTimeLable", "完成时间");
						map.put("successTime", DateUtil.dateFullTimeFormat.format(orderDone.getCreateDate()));
					}
					if(orderDone.getOrderStatus().equals(OrderDoneEnum.SHIPPED.getFeatureName())) {//发货时间
						map.put("shippedTimeLable", "发货时间");
						map.put("shippedTime", DateUtil.dateFullTimeFormat.format(orderDone.getCreateDate()));
					}
					if(orderDone.getOrderStatus().equals(OrderDoneEnum.REFUND_APPLY.getFeatureName())) {//退款申请时间
						map.put("refundApplyTimeLable", "申请退款时间");
						map.put("refundApplyTime", DateUtil.dateFullTimeFormat.format(orderDone.getCreateDate()));
					}
					if(orderDone.getOrderStatus().equals(OrderDoneEnum.REFUND_FINISH.getFeatureName())) {//退款完成时间
						//退款完成添加银行支付信息
						List<Recharge> rechargeList = rechargeService.getPayingRechargeByOrderTypeAndNo(OrderTypeEnum.GOODS.getFeatureName(), order.getId());
						if(rechargeList != null && rechargeList.size() != 0) {
							Recharge recharge = rechargeList.get(0);
							Integer bankId = recharge.getBankId();
							Bank bank = bankService.get(bankId);
							map.put("bankName", bank.getName());
							String cardNo = recharge.getCardNo();
							map.put("bankNameLable", "退回至"+bank.getName()+"(尾号"+StringUtils.substring(cardNo, cardNo.length()-4)+")");
						}
						map.put("refundFinishTimeLable", "退款成功时间 ");
						map.put("refundFinishTime", DateUtil.dateFullTimeFormat.format(orderDone.getCreateDate()));
					}
					if(orderDone.getOrderStatus().equals(OrderDoneEnum.REFUND_REJECT.getFeatureName())) {//退款驳回时间
						map.put("refundRejectTimeLable", "拒绝退款时间");
						map.put("refundRejectTime", DateUtil.dateFullTimeFormat.format(orderDone.getCreateDate()));
					}
				}
			}

		}
		return map;
	}

	/**
	 * 商城各种排行
	 * @param appVersion
	 * @param client
	 * @param token
	 * @param type
	 * @return
	 */
    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "商城各种排行")
    public AppResult sort(
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
        @ApiParam("App登陆状态") @RequestParam(required = false) String token,
        @ApiParam("排序条件 1销量 2浏览量") @RequestParam(required = false) Integer type
            ) {
        Map<String, Object> map = new HashMap<>();
        try {
            if(type != null) {
                if(type == 1) {
                    //商品销量排行t_goods(sell_stock) @sxy
                    List<Goods> listBySales = goodsService.listGoodsBySales(0, 10);
                    for(Goods good : listBySales) {
                        List<GoodsPicture> pictures = good.getGoodsPictures();
                        for(GoodsPicture pic : pictures) {
                            if(pic.getType() == 14) { //商品小图
                                good.setSmallPicPath(pic.getUpload().getCdnPath());
                                break;
                            }
                        }
                    }
                    map.put("list", listBySales);
                } else if(type == 2) {
                    //商品推荐排行-全平台浏览次数t_goods_click(sum)
                    List<Map<String, Object>> listByClick = goodsService.listGoodsByClick(0, 10);
                    map.put("list", listByClick);
                } else {
                    return new AppResult(ERROR, "排序类型有误");
                }
            } else {
                return new AppResult(ERROR, "排序类型为空");
            }

            return new AppResult(SUCCESS, map);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, "您的网络好像有点问题");
        }
    }


	@RequestMapping(value = "/listSecondKill", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "秒杀列表")
    public AppResult listSecondKill(@ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("App登陆状态") @RequestParam(required = false) String token) {
//    	Map<String, Object> map = new HashMap<>();
        try {
        	Map resultData = new HashMap();
        	String title = "";
            // 查询所有的秒杀活动
        	List<Map<String, Object>> secondKillList = new ArrayList<>();
        	List<SecondKillActivityView> list = goodsService.getAllSecondKillActivity(null, null);
        	Boolean isSetDefault = false;
        	for(int i = 0 ; i < list.size(); i++) {
        		Boolean isDefault = false;
        		Map<String, Object> currentDaySecondKill = new HashMap<>();
        		// 如果1天多个活动，活动列表
        		/*List<SecondKillActivityView> currentDayActivity = goodsService.getAllSecondKillActivity(list.get(i).getWeekDay());
        		if(currentDayActivity.isEmpty()) {
        			continue;
        		}*/
        		
        		List<Map<String, Object>> listCurrentDayActivity = new ArrayList<>();
        		SecondKillActivityView activityDetail = list.get(i);// { // 秒杀商品详情信息
				if (StringUtils.isBlank(title)) {
					title = activityDetail.getName();
				}
    			Map<String, Object> goodsInfo = new HashMap<>();
    			goodsInfo = getActivityGoodsInfo(activityDetail);
    			 
    			if((Integer.parseInt(goodsInfo.get("status")+"") == 0 ||
    				(Integer.parseInt(goodsInfo.get("status")+"") == 1))
    					&& !isSetDefault) {// 默认显示进行中
    				isDefault = true;
    				isSetDefault = true;
        		} 
    			goodsInfo.put("activityDetail", activityDetail);
    			listCurrentDayActivity.add(goodsInfo);
//        		}
    			currentDaySecondKill.put("isDefault", isDefault);
        		currentDaySecondKill.put("weekDay", activityDetail.getWeekDay());
        		currentDaySecondKill.put("date", activityDetail.getDate());
        		currentDaySecondKill.put("currentDayActivity", listCurrentDayActivity);
        		secondKillList.add(currentDaySecondKill);
			}
			resultData.put("title", title);
			resultData.put("secondKillList", secondKillList);
        	
            return new AppResult(SUCCESS, resultData);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, "您的网络好像有点问题");
        }
    }



	public Map<String, Object> getActivityGoodsInfo(SecondKillActivityView activityDetail){
		Map<String, Object> goodsInfo = new HashMap<>();
		Integer goodsId = activityDetail.getGoodId();
		Goods goods = goodsService.queryGoodsDetailById(goodsId);
		goodsInfo.put("activityPicPath",  ClientConstants.ALIBABA_PATH + "upload/" + goods.getActivityPicPath());
		goodsInfo.put("picPath",  ClientConstants.ALIBABA_PATH + "upload/" + goods.getSmallPicPath());
		goodsInfo.put("goodsName", goods.getGoodsName());
		goodsInfo.put("sellStock", activityDetail.getStockCount());
		goodsInfo.put("memberPrice",  activityDetail.getMemberPrice());
		goodsInfo.put("price",  activityDetail.getPrice());
		goodsInfo.put("salingPrice", activityDetail.getPrice());
		goodsInfo.put("activityId", activityDetail.getId());

		Date beginTime = activityDetail.getBeginTime();
		Date endTime = activityDetail.getEndTime();
		Date startTime = activityDetail.getStartTime();
		if(beginTime.before(new Date()) && endTime.before(new Date())) {// 活动已过期
			goodsInfo.put("status", -1);// 已过期状态
			goodsInfo.put("statusStr", "已结束");
		} else if(beginTime.before(new Date()) && endTime.after(new Date())) {// 活动进行中
			Long lockTime = DateUtil.dateDiffSecound(DateUtil.format(new Date(), DateTimeUtil.allPattern),DateUtil.format(endTime, DateTimeUtil.allPattern) ,
                     DateTimeUtil.allPattern);
			goodsInfo.put("status", 0);// 进行中
			goodsInfo.put("statusStr", "进行中");
			goodsInfo.put("lockTime", lockTime); //距结束时间

		} else if(beginTime.after(new Date())) {// 活动未开始
			goodsInfo.put("status", 1);// 未开始
			goodsInfo.put("statusStr", DateUtil.format(startTime, DateTimeUtil.dateFHMS)+"点开始");
        	Long lockTime = DateUtil.dateDiffSecound(DateUtil.format(new Date(), DateTimeUtil.allPattern),DateUtil.format(beginTime, DateTimeUtil.allPattern) ,
                     DateTimeUtil.allPattern);
        	//  活动预热时间距离开始时间分钟
        	String min = memcachedManager.getCacheKeyValue("ACTIVITY_KILL_TIME");
        	// 预热时间（判断是否显示倒计时） 开始时间 - 当前时间 < 预热时间
            Long preheatTime = Long.parseLong(min == null ? "0" : min) * 60 ;
            goodsInfo.put("preheatTime", preheatTime); // lockTime < preheatTime 显示倒计时
            goodsInfo.put("lockTime", lockTime);
		}

		return goodsInfo;
	}

	@RequestMapping(value = "/caculateExpressFee", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "计算运费")
    public AppResult caculateExpressFee(@ApiParam("App版本号") @RequestParam String appVersion,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("App登陆状态") @RequestParam String token,
            @ApiParam("收货地址ID") @RequestParam Integer addressId,
            @ApiParam("商品ID集合") @RequestParam List<Integer> goodsIds,
            @ApiParam("对应商品数量集合") @RequestParam List<Integer> counts,
            @ApiParam("活动ID")@RequestParam(required = false)Integer activityId) {
		try {
			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
//			UserAddress userAddress = userAddressService.selectAddressById(addressId);
			UserAddress userAddress = userAddressService.getAddressesById(addressId);
	        if (userAddress == null) {
	        	return new AppResult(FAILED, "收货地址不正确");
	        }
	        if (userAddress.getUserId().intValue() != user.getId().intValue()) {
	        	return new AppResult(FAILED, "收货地址归属不正确");
	        }
	        
	        double weight = 0d;
	        BigDecimal totalMoney = BigDecimal.ZERO;
	        boolean isVIP = user.getLevel() > 0 ? true : false;
	        SecondKillDetailVO secondKillDetailVO = null;
			//参与秒杀活动
			if(activityId!=null && activityId>0) {
				if(goodsIds.size()!=1){
					return new AppResult(FAILED,"一次只能秒杀一种商品");
				}

				if(counts.get(0).intValue()>1) {
					return new AppResult(FAILED,"一次只能秒杀一个商品");
				}
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

			}
			for (int i = 0; i < goodsIds.size(); i++) {
			     //当前商品的库存
				Goods goods = goodsService.getMapper().selectByPrimaryKey(goodsIds.get(i));
				if (null == goods) {
				    return new AppResult(FAILED,"商品不存在");
				}
				if(counts.get(i) == 0) {
					return new AppResult(FAILED,"商品" + goods.getGoodsName() + "数量不能为0");
				}               
				
				BigDecimal buyPrice = BigDecimal.ZERO;
				//秒杀价格
				if(activityId!=null && activityId>0) {
					buyPrice = isVIP ? secondKillDetailVO.getMemberPrice() : secondKillDetailVO.getPrice();
				}else{
					buyPrice = isVIP ? goods.getMemberSalingPrice() : goods.getSalingPrice();
				}
				totalMoney = totalMoney
						.add(buyPrice.multiply(BigDecimalUtil.parse(counts.get(i))));
				weight += goods.getWeight().doubleValue() * counts.get(i);
			}
			
			// 老版本使用市code，新版本使用区县code
			String areaId = StringUtils.isBlank(userAddress.getaId()) ? userAddress.getcId() : userAddress.getaId();
	        //使用红包前判断是否包邮及邮费
            Map<String, Object> expressMap = goodsService.calculateExpressFee(weight, totalMoney, areaId);
           /* BigDecimal expressFee = new BigDecimal(expressMap.get("expressFee")+"");
            BigDecimal realExpressFee = new BigDecimal(expressMap.get("realExpressFee")+"");
            */
            expressMap.put("distribution", "快递费：");
            BigDecimal expressFee = (BigDecimal) expressMap.get("expressFee");
            expressMap.put("expressFeeStr", expressFee.compareTo(BigDecimal.ZERO) == 0 ? "免邮" :MONEY_FORMAT.format(expressMap.get("expressFee")));
            
			return new AppResult(SUCCESS, expressMap);
		} catch (Exception e) {
			logger.error(e, e);
            e.printStackTrace();
            return new AppResult(FAILED, "您的网络好像有点问题");
		}
	}
}
