package com.goochou.p2b.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.*;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.pay.OutPayEnum;
import com.goochou.p2b.hessian.transaction.PayChannelResponse;
import com.goochou.p2b.model.*;
import com.goochou.p2b.model.vo.BannerIndexVO;
import com.goochou.p2b.model.vo.IconIndexVO;
import com.goochou.p2b.model.vo.NoticeIndexVO;
import com.goochou.p2b.model.vo.ProjectIndexVO;
import com.goochou.p2b.service.*;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.PayUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.goochou.p2b.constant.pay.ChannelConstants.*;

@Controller
@Api(value = "公共模块")
public class CommonController extends BaseController {
	private static final Logger logger = Logger.getLogger(BaseController.class);
	@Resource
	private BannerService bannerService;
	@Resource
	private AppVersionService appVersionService;
	@Resource
	private IconService iconService;
	@Resource
	private AppNoticeService appNoticeService;
	@Resource
	private DeviceTokenService deviceTokenService;
	@Resource
	private InvestmentService investmentService;
	@Resource
	private ProjectService projectService;
	@Resource
	private PayTunnelService payTunnelService;
	@Resource
	private BankCardService bankCardService;
	@Resource
	private GoodsService goodsService;

	/**
	 * APP首页
	 * @param request
	 * @param response
	 * @param deviceToken
	 * @param appVersion
	 * @param client
	 * @param token
	 * @param dataSource
	 * 阿里分发市场 2
                应用宝 3
                华为应用市场 4
        vivo应用商店 5
        oppo 6
                魅族 7
                联通wo 8
                联想 9
                百度 10
                小米 11
        360手机助手 12
	 * @param appName
	 * @param deviceInfo
	 * 苹果：iOS版本号，手机型号
               安卓：安卓版本号，第三方UI版本号，手机型号，厂商
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "首页")
	public AppResult home(HttpServletRequest request, HttpServletResponse response,
						  @ApiParam("设备唯一身份码") @RequestParam(required = false) String deviceToken, @ApiParam("App版本号") @RequestParam String appVersion,
						  @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client, @ApiParam("App登陆状态") @RequestParam(required = false) String token,
						  @ApiParam("安卓渠道") @RequestParam(required = false) String dataSource,
						  @ApiParam("app名字") @RequestParam(required = false) String appName,
						  @ApiParam("包含设备信息") @RequestParam(required = false) String deviceInfo) {
		try {

			Map<String, Object> map = new HashMap<String, Object>();

			// 是否显示牛
			Boolean bullsShow = false;
			if (client.equals(ClientEnum.IOS.getFeatureName())) {
				if (Constants.YES.equals(getCacheKeyValue(DictConstants.BULLS_SHOW_IOS))) {
					bullsShow = true;
				}
			} else if (client.equals(ClientEnum.ANDROID.getFeatureName())) {
				if (Constants.YES.equals(getCacheKeyValue(DictConstants.BULLS_SHOW_ANDROID))) {
					bullsShow = true;
				}
			} else if (client.equals(ClientEnum.WAP.getFeatureName())) {
				if (Constants.YES.equals(getCacheKeyValue(DictConstants.BULLS_SHOW_WAP))) {
					bullsShow = true;
				}
			}

			User user = userService.checkLogin(token);

			if("不显示牛".equals(appName)) {
			    bullsShow = false;
			}

			//华为上线时暂时关闭
			if (projectService.isHidden(user, dataSource)) {
				bullsShow = false;
			}

			boolean isHiddenBalance =false;
			if(projectService.isHidden(user, dataSource,true)){
				isHiddenBalance=true;
				if(user!=null && user.getLevel()>0){
					isHiddenBalance=false;
				}
			}
			map.put("isHiddenBalance", isHiddenBalance?"1":"0");

			//会员用户可查看
			boolean isMemberShow=false;
			if(bullsShow==false){
				if(user!=null && user.getLevel()>0){
					bullsShow = true;
					isMemberShow=true;
				}
			}



			// 新手标数
            int buyNoobCount = 0;
            // 判断是否是新手标，只能购买一次
            if (user != null && user.getLevel() > 0) {
                ProjectExample example = new ProjectExample();
                example.createCriteria().andNoobEqualTo(1).andUserIdEqualTo(user.getId()).andStatusIn(Arrays.asList(2, 3, 4));//// 0待上架1上架2待付款3已出售4已回购
                buyNoobCount = projectService.getMapper().countByExample(example);
                //判断如果IOS在审核牛关闭，判断用户是否有买牛，有的话依然显示养牛
                //bullsShow = true;
            }
			// 检查是否需要更新
			Map<String, String> updateMap = new HashMap<String, String>();
			String update = checkVersion(client, appVersion);
			if (UPDATE.equals(update)) {
				AppVersionContentWithBLOBs blobs = appVersionService.getAppVersionContentWithBLOBs(client);
				if (blobs != null) {
					updateMap.put("version", blobs.getVersion());
					updateMap.put("noticeTitle", blobs.getTitle());
					updateMap.put("noticeText", blobs.getTextContent());
					updateMap.put("noticeHtml", blobs.getHtmlContent());
					updateMap.put("url", blobs.getUrl());
				} else {
					update = NOUPDATE;
				}
			} else {
				updateMap.put("version", "");
				updateMap.put("noticeTitle", "不需要更新");
				updateMap.put("noticeText", "不需要更新");
				updateMap.put("noticeHtml", "不需要更新");
				updateMap.put("url", "");
			}
			map.put("update", update);
			map.put("updateDetails", updateMap);

			// icons
			List<IconIndexVO> icons = new ArrayList<IconIndexVO>();
			List<BannerIndexVO> banners = new ArrayList<BannerIndexVO>();

//			if("牛牛牧场".equals(appName)) {
			if(!bullsShow || isHiddenBalance){
			    IconIndexVO i = new IconIndexVO();
			    i.setLink("https://wap.bfmuchang.com/mc.html");
                i.setPath("https://www.bfmuchang.com/static/upload/icons/203906bb-f2c7-48c8-b86e-2df2a5e095a7.png");
                i.setTitle("牧场介绍");
                icons.add(i);

				if(getVersion(appVersion)>=200) {
					i = new IconIndexVO();
					i.setLink("https://wap.bfmuchang.com/zfList.html");
					i.setPath("https://www.bfmuchang.com/static/upload/icons/7ad2cd9a-b7a1-4d14-b82f-0ba3f667e8d4.png");
					i.setTitle("政企合作");
					icons.add(i);
				}

                i = new IconIndexVO();
                i.setLink("https://wap.bfmuchang.com/20191125.html");
                i.setPath("https://www.bfmuchang.com/static/upload/icons/d6c33e33-8baf-49e8-9d9d-5ef80c97b9ab.png");
                i.setTitle("热销商品");
                icons.add(i);
                i = new IconIndexVO();
                i.setLink("https://wap.bfmuchang.com/live2.html");
                i.setPath("https://www.bfmuchang.com/static/upload/icons/76d3d5a5-c183-41c9-a8fe-1121bea6d5f0.png");
                i.setTitle("精选视频");
                icons.add(i);
                i = new IconIndexVO();
                i.setLink("https://wap.bfmuchang.com/FAQ2.html");
                i.setPath("https://www.bfmuchang.com/static/upload/icons/eb779abf-3bf0-456c-a512-4305f3f87fbb.png");
                i.setTitle("常见问题");
                icons.add(i);

				BannerIndexVO banner = new BannerIndexVO();
				banner.setPictureUrl("https://www.bfmuchang.com/static/upload/banner/274b070b-6322-4001-99f4-b6fa24b4b9c4.png");
				banner.setLink("https://wap.bfmuchang.com/20191211.html");
				banners.add(banner);
//				banner = new BannerIndexVO();
//				banner.setPictureUrl("https://www.bfmuchang.com/static/upload/banner/4ea86c2f-4480-47cb-bef7-d7e4f30a35c8.png");
//				banner.setLink("https://wap.bfmuchang.com/20191212.html");
//				banners.add(banner);
				banner = new BannerIndexVO();
				banner.setPictureUrl("https://www.bfmuchang.com/static/upload/banner/d8bd6aaa-cc42-47c6-bbb6-2ac9524d1a05.png");
				banner.setLink("https://wap.bfmuchang.com/20191112b.html");
				banners.add(banner);
				banner = new BannerIndexVO();
				banner.setPictureUrl("https://www.bfmuchang.com/static/upload/banner/4165ce45-6521-4b2e-84ad-b17f5dd7db6f.png");
				banner.setLink("https://wap.bfmuchang.com/live2.html");
				banners.add(banner);
			} else {
//			if (null == memcachedManager.get(Constants.ICONS) || ((List<IconIndexVO>) memcachedManager.get(Constants.ICONS)).size() == 0) {
				List<Icon> iconsList = iconService.getUsingIcons(0, appVersion);
				for (Icon icon : iconsList) {
					IconIndexVO i = new IconIndexVO();
					i.setId(icon.getId());
					i.setKey(icon.getKey());
					i.setLink(icon.getLink());
					if(icon.getTitle().equals("邀请好友")) {
                        if(bullsShow && isMemberShow) {
							if (TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)) {
								i.setLink("http://101.80.207.189:3082/invite20.html?shareId=5");
							}else {
								i.setLink("https://wap.bfmuchang.com/invite20.html?shareId=1");
							}
                        }
                    }
                    i.setPath(ClientConstants.ALIBABA_PATH + "upload/" + icon.getPath());
                    i.setTitle(icon.getTitle());
					icons.add(i);
				}
//				memcachedManager.addOrReplace(Constants.ICONS, icons, 3600);
//			} else {
//				icons = (List<IconIndexVO>) memcachedManager.get(Constants.ICONS);
//			}

//				if (null == memcachedManager.get(Constants.BANNERS) || ((List<BannerIndexVO>) memcachedManager.get(Constants.BANNERS)).size() == 0) {
					List<Banner> list = bannerService.listByStatus(0, 0, 0, null);
					for (Banner banner : list) {
						BannerIndexVO b = new BannerIndexVO();
						b.setLink(banner.getLink());
						b.setPictureUrl(ClientConstants.ALIBABA_PATH + "upload/" + banner.getPictureUrl());
						banners.add(b);
					}
//					memcachedManager.addOrReplace(Constants.BANNERS, banners, 3600);
//				} else {
//					banners = (List<BannerIndexVO>) memcachedManager.get(Constants.BANNERS);
//				}
			}

			map.put("icons", icons);
			map.put("banners", banners);

			icons = new ArrayList<IconIndexVO>();
			List<Icon> tabicons = iconService.getUsingIcons(2, appVersion);
			List<Icon> tabiconsGray = iconService.getUsingIcons(3, appVersion);
			for (int i = 0; i < tabicons.size(); i++) {
				IconIndexVO icon = new IconIndexVO();
				icon.setId(tabicons.get(i).getId());
				icon.setKey(tabicons.get(i).getKey());
				icon.setLink(tabicons.get(i).getLink());
				icon.setPath(ClientConstants.ALIBABA_PATH + "upload/" + tabicons.get(i).getPath());
				icon.setPathGray(ClientConstants.ALIBABA_PATH + "upload/" + tabiconsGray.get(i).getPath());
				icon.setTitle(tabicons.get(i).getTitle());
				icons.add(icon);
			}
			// 不显示牛,隐藏牛icon
			if (!bullsShow) {
				icons.remove(1);
			}
			map.put("tabicons", icons);
			map.put("active_banner", null);

			List<Banner> list=null;
			if(bullsShow) {
				list = bannerService.listByStatus(0, 0, 1, null);
				if (null != list && list.size() > 0) {
					Banner banner = list.get(0);
					BannerIndexVO b = new BannerIndexVO();
					b.setLink(banner.getLink());
					b.setPictureUrl(ClientConstants.ALIBABA_PATH + "upload/" + banner.getPictureUrl());
					map.put("active_banner", b);

					//TODO 因IOS低版本不兼容视频直播
					if (client.equals(ClientEnum.IOS.getFeatureName()) && getVersion(appVersion) < 110) {
						b.setLink("https://wap.bfmuchang.com/mc.html");
						b.setPictureUrl(ClientConstants.ALIBABA_PATH + "upload/banner/bcc8b5f3-ba2a-4a9a-975c-0268cb2fd27d.png");
						map.put("active_banner", b);
					}
				}
			}


			//开机广告（每天展示一次）
			BannerIndexVO b1 = new BannerIndexVO();
			if (user == null) {
				b1.setKey("registerApp");
				b1.setPictureUrl(ClientConstants.ALIBABA_PATH + "images/newyear/index_registe_tip.png");
			}else {
				list = bannerService.listByStatus(0, 0, 3, null);
				if (null != list && list.size() > 0) {
					Banner banner = list.get(0);
					b1 = new BannerIndexVO();

					b1.setLink(banner.getLink());
					b1.setPictureUrl(ClientConstants.ALIBABA_PATH + "upload/" + banner.getPictureUrl());
				}
			}

			map.put("bootup_banner", b1);
			//是否点击后永不再展示,0：每次都展示，1：点击后就不展示
			map.put("bootup_banner_show", "1");

			AppNoticeWithBLOBs notice = appNoticeService.getShowNotice();
			if (null != notice) {
				NoticeIndexVO n = new NoticeIndexVO();
				n.setLink(notice.getLink());
				n.setTitle(notice.getTitle());
				map.put("notice", n);
			}

			map.put("project", null);
			map.put("goods", null);
			map.put("xia_banner", null);

			if(getVersion(appVersion)>=200) {
				// 显示牛
				if (bullsShow) {
					//根据产品规则获取projects @sys
					JSONObject bullPlate = new JSONObject(); // 养牛板块
					bullPlate.put("sort", "1");
					bullPlate.put("title", "养牛专区");
					// tab页数据
					List<JSONObject> bullPlateData = new ArrayList<>();
					// 养牛专区
					JSONObject purchase = new JSONObject();
					purchase.put("title", "养牛专区");
					purchase.put("bulls_type", "0");
					// 养牛专区列表数据
					List<ProjectIndexVO> purchaseData = projectService.listGroupByLimitDays(1);
					if (buyNoobCount>0) {
						purchaseData = purchaseData.stream().filter(item -> 1 != item.getNoob()).collect(Collectors.toList());
					}
					purchase.put("data", purchaseData);
					bullPlateData.add(purchase);

					// 拼牛专区
					JSONObject assemble = new JSONObject();
					assemble.put("title", "拼牛专区");
					assemble.put("bulls_type", "1");
					assemble.put("topPicture", ClientConstants.ALIBABA_PATH + "images/newyear/ping_logo.png");
					// 拼牛专区列表数据
					int showPingniuNum = Integer.valueOf(getCacheKeyValue(DictConstants.PINGNIU_PROJECT_SHOW_NUM));
					if (showPingniuNum > 0) {
						List<ProjectIndexVO> assembleData = projectService.listPingniu(showPingniuNum);
						assemble.put("data", assembleData);
						if (!assembleData.isEmpty()) { // 如果拼牛有数据就显示拼牛板块，否则就不显示
							bullPlateData.add(assemble);
							bullPlate.put("title", "牧场主");
						}
					}
					bullPlate.put("list", bullPlateData);
					map.put("project", bullPlate);
				}

				boolean goodsShow_v2_0 = false;
				if (Constants.YES.equals(getCacheKeyValue(DictConstants.SHOP_SHOW_RECOMMEND_V2_0))) {
					goodsShow_v2_0 = true;
				}

				// 显示商城
				if (goodsShow_v2_0) {
					//根据产品规则获取goods @sys
					JSONObject goodsPlate = new JSONObject(); // 商城板块
					goodsPlate.put("title", "精选推荐");
					goodsPlate.put("sort", "3");
					List<Map<String, Object>> goodsPlateData = new ArrayList<>();

					// 精选推荐
					JSONObject recommend = new JSONObject();
					recommend.put("title","精选推荐");
					// 精选推荐数据
					List<Map<String, Object>> recommendData = goodsService.listGoodsByClick(0, 4);
					recommend.put("data",recommendData);
					goodsPlateData.add(recommend);
					goodsPlate.put("list", goodsPlateData);
					map.put("goods", goodsPlate);
				}
				
				//首页广告下区
				JSONObject bannerPlate = new JSONObject(); // banner板块
				bannerPlate.put("title", "");
				bannerPlate.put("sort", "2");
				list = bannerService.listByStatus(0, 0, 4, null);
				List<BannerIndexVO> xias = new ArrayList<BannerIndexVO>();
				if (null != list && list.size() > 0) {
					for(Banner banner : list) {
						BannerIndexVO b = new BannerIndexVO();
						b.setLink(banner.getLink());
						b.setPictureUrl(ClientConstants.ALIBABA_PATH + "upload/" + banner.getPictureUrl());
						xias.add(b);
					}
					bannerPlate.put("data", xias);
					map.put("xia_banner", bannerPlate);
				}
			} else {
				// 显示牛
				if (bullsShow) {
					List<Project> projects = null;
					// 新手
					if (buyNoobCount == 0) {
						map.put("lable_class", "新手专区");
						projects = projectService.queryIndex(1, 0, 1, 1);
						// 没有新手就取精选
						if (projects.size() == 0) {
							map.put("lable_class", "精选专区");
							projects = projectService.queryIndex(1, 0, 1, 0);
						}
					} else {
						map.put("lable_class", "精选专区");
						projects = projectService.queryIndex(1, 0, 1, 0);
					}
					ProjectIndexVO p = null;
					if (null != projects && projects.size() > 0) {
						Project project = projects.get(0);
						p = projectService.convertProjectIndexVO(project, true);
					}
					map.put("project", p);
					map.put("goods", null);
				} else {// ,显示商品
					// 商城产品
					Map<String, Object> goodsMap = new HashMap<String, Object>();
					goodsMap.put("limitStart", 0);
					goodsMap.put("limitEnd", 2);
					List<Map<String, Object>> goods = goodsService.getAppGoodsList(goodsMap);
					for (Map<String, Object> gds : goods) {
						gds.put("path", ClientConstants.ALIBABA_PATH + "upload/" + gds.get("path"));
					}
					map.put("goods", goods);
					map.put("lable_class", "精选专区");
					map.put("project", null);
				}
			}

			// 养牛是否显示
			map.put("bulls_show", bullsShow);

			map.put("lable_more", "更多>>");
			map.put("lable_anz", "饲养预计利润");
			map.put("lable_day", "饲养期");
			map.put("lable_foot", "智慧养牛，美好生活");
			// 注册协议
			map.put("register_agree_text", "《用户注册协议》");
			map.put("register_agree_url", ClientConstants.H5_URL + "register.html");
			map.put("register_assure", "*手机号仅用于注册登录，平台保护您的账户信息安全，平台不会向任何人泄漏你的手机号。");
			// 首页广告轮播秒数
			map.put("banner_second", getCacheKeyValue(DictConstants.SPLASH_SHOW_TIME));
			map.put("auth_assure", "*请务必填写真实信息，以免给您带来不必要的损失；");//如填些错误信息，认领合同将无法生效");
			map.put("pwd_assure", "*密码为数字加字母组合不小于6位，不超过12位");
			map.put("pay_pwd_assure", "*交易密码为6为数字");
			map.put("bind_card_lable", "银行限额");
			map.put("bind_card_url", ClientConstants.H5_URL + "userbankCard.html");
			// 滚动公告
			List<Map<String, Object>> listWithRoll = appNoticeService.listWithRoll(1, 1, null, null);
			List<NoticeIndexVO> noticesRoll = new ArrayList<NoticeIndexVO>();
			if (listWithRoll.size() != 0) {
				for (Map<String, Object> noticeMap : listWithRoll) {
					NoticeIndexVO n = new NoticeIndexVO();
					n.setLink((String) noticeMap.get("link"));
					n.setTitle((String) noticeMap.get("title"));
					n.setTitleColor((String) noticeMap.get("title_color"));
					noticesRoll.add(n);
				}
			}
			map.put("noticesRoll", noticesRoll);
			// 首页公告滚动秒数
			map.put("notice_second", getCacheKeyValue(DictConstants.NOTICE_SHOW_TIME));
			// 公告图标
			map.put("notice_icon", ClientConstants.ALIBABA_PATH + "images/home_notice.png");
			//安卓关闭养牛渠道
			map.put("data_source", getCacheKeyValue(DictConstants.DATA_SOURCE));
			return new AppResult(SUCCESS, map);
		} catch (Exception e) {
			logger.error(e,e);
			e.printStackTrace();
			return new AppResult(FAILED, "您的网络好像有点问题");
		}

	}

	/**
	 * 保存设备token
	 * 
	 * @author ydp
	 * @param token
	 * @param devicetoken
	 * @param appVersion
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "/save/devicetoken", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "保存设备token（每次启动每次都调用）")
	public AppResult devicetoken(
	        @ApiParam("token") @RequestParam(required = false) String token,
			@ApiParam("设备Token") @RequestParam(required = false) String devicetoken,
			@ApiParam("App版本号") @RequestParam String appVersion,
			@ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
			@ApiParam("唯一识别码") @RequestParam(required = false) String uuid,
			@ApiParam("手机版本") @RequestParam(required = false) String mobileVersion,
			@ApiParam("机型") @RequestParam(required = false) String mobileModel,
			@ApiParam("渠道") @RequestParam(required = false) String dataSource,
            @ApiParam("app名字") @RequestParam(required = false) String appName) {
		try {
			logger.info("========== 进入保存设备token ===========");
			if("Android".equals(client)) {
				if(StringUtils.isEmpty(dataSource)) {
					dataSource="-1";
				}else if("其他".equals(dataSource)) {
					dataSource="0";
				}
			}else {
				dataSource="0";
			}
			
			if(StringUtils.isBlank(devicetoken)) {
				devicetoken=null;
			}
			
			DeviceToken dt = new DeviceToken();
			if(StringUtils.isNotEmpty(token)) {
				User user = userService.checkLogin(token);
				if (null != user) {
	                logger.info("========== 用户已登录 ===========");
	                dt.setUserId(user.getId());
	            }
			}
			
			if (StringUtils.isEmpty(devicetoken) && StringUtils.isEmpty(uuid) && dt.getUserId()==null ) {
				return new AppResult(FAILED, MESSAGE_EXCEPTION);
			}

			dt.setToken(devicetoken);
			dt.setVersion(appVersion);
			dt.setClient(client);
			dt.setAppName(appName);
			dt.setDataSource(dataSource);
			dt.setMobileModel(mobileModel);
			dt.setMobileVersion(mobileVersion);
			dt.setUuid(uuid);
			dt.setIsUninstall(false);
			deviceTokenService.saveToken(dt);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, "您的网络好像有点问题");
		}
		return new AppResult(SUCCESS);
	}

	/**
	 * 关于我们
	 * 
	 * @author ydp
	 * @param request
	 * @param client
	 * @param appVersion
	 * @return
	 */
	@RequestMapping(value = "/aboutus", method = RequestMethod.GET)
	@ApiOperation(value = "关于我们")
	@ResponseBody
	public AppResult aboutus(HttpServletRequest request, @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
			@ApiParam("app版本") @RequestParam String appVersion) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Icon> icons = iconService.getUsingIcons(5, appVersion);
			for (Icon icon : icons) {
				icon.setPath(ClientConstants.ALIBABA_PATH + "upload/" + icon.getPath());
				if (icon.getSeq() == 2) {
					icon.setSubtitle("牧场管家");
				} else if (icon.getSeq() == 3) {
					icon.setSubtitle("feedback@bfmuchang.com");
				} else if (icon.getSeq() == 4) {
					icon.setSubtitle("store");
				}
			}
			map.put("lable_tel", "客服电话：");
			map.put("lable_tel_value", "400-179-8099");
			map.put("lable_time", "客服服务时间：");
			map.put("lable_time_value", "周一至周五9：00-17：30");
			map.put("icons", icons);
			return new AppResult(SUCCESS, map);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @description 用户支付方式
	 * @author shuys
	 * @date 2019/5/22
	 * @param appVersion
	 * @param token
	 * @return com.goochou.p2b.app.model.AppResult
	 */
	@RequestMapping(value = "/userPayChannel", method = RequestMethod.GET)
	@ApiOperation(value = "用户支付方式")
	@ResponseBody
	public AppResult getUserPayChannel(@ApiParam("app版本号") @RequestParam String appVersion, @ApiParam("用户token") @RequestParam String token) {
		try {
			User user = userService.checkLogin(token);
			if (user == null) {
				return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
			}
			PayChannelResponse payChannelResponse = bankCardService.getUserPayChannel(user.getId());
			if(getVersion(appVersion)>110) {
			    //添加收银台
	            Map<String, String> last = new HashMap<>();
	            last.put(CHANNEL_URL, ClientConstants.ALIBABA_PATH + "images/tai.png");
	            last.put(CHANNEL_KEY, OutPayEnum.YEEPAY.getFeatureName());
	            last.put(CHANNEL_NAME, "收银台");
	            payChannelResponse.getChannel().add(0, last);
			}
			return new AppResult(SUCCESS, payChannelResponse);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new AppResult(FAILED, MESSAGE_EXCEPTION);
		}
	}
	
	
	
	/**
	 * 安卓初始化时调用友盟,拿到deviceToken后,先调用saveDeviceToken
	 * 再调用activation
	 * 两个是异步调用,两次调用方法中都会生成uuid.可能会生成两个不同的uuid.
	 * 因为已经调了saveDeviceToken了,已经满足所有的数据需要.
	 * 又为了不产生错误的数据.关闭安卓的activation接口.
	 * 
	 * ios初始化时先生成uuid,调用activation.
	 * 之后申请推送权限.
	 * 获得推送权限调用saveDeviceToken.
	 */
	@RequestMapping(value = "/activation", method = RequestMethod.POST)
    @ApiOperation(value = "APP激活（生命周期内只请求一次）")
    @ResponseBody
    public AppResult activation(
        HttpServletRequest request, HttpServletResponse response,
        @ApiParam("设备唯一身份码") @RequestParam String uuid,
        @ApiParam("App版本号") @RequestParam String appVersion,
        @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client) {
        try {
        	if(client.equals("IOS")){
        		 DeviceToken dt = new DeviceToken();
        		 if(StringUtils.isEmpty(uuid)) {
        			 return new AppResult(FAILED, MESSAGE_EXCEPTION);
        		 }
                 dt.setUuid(uuid);
                 dt.setDataSource("0");
                 dt.setClient(client);
                 dt.setVersion(appVersion);
                 dt.setIsUninstall(false);
                 deviceTokenService.saveToken(dt);
        	}
            return new AppResult(SUCCESS, SUCCESS_MSG);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }

	public static void main(String[] args) {
		System.out.println("u110kEAIykWnnKxN_CDSp08ASZMwJo-_lobwboa2pd".length());
	}
}
