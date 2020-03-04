package com.goochou.p2b.app.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.redis.RedisConstants;
import com.goochou.p2b.service.impl.RedisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.goochou.p2b.app.model.AppResult;
import com.goochou.p2b.constant.ActivityConstant;
import com.goochou.p2b.constant.LotteryBullsTypeEnum;
import com.goochou.p2b.constant.LotteryTipsEnum;
import com.goochou.p2b.constant.ProbabilityBullsEnum;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.ActivityBlessingCardRecord;
import com.goochou.p2b.model.ActivityBlessingCardRecordExample;
import com.goochou.p2b.model.ActivityBlessingChanceRecordExample;
import com.goochou.p2b.model.Share;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.ActivityBlessingCardRecordService;
import com.goochou.p2b.service.ActivityBlessingChanceRecordService;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.AppVersionService;
import com.goochou.p2b.service.ShareService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.BeanToMapUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.ProbabilityBullsUtil;


@Controller
@RequestMapping(value = "activity")
@Api(value = "活动中心")
public class ActivityController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(ActivityController.class);
    @Resource
    private ActivityService activityService;
    @Resource
    private AppVersionService appVersionService;
    @Resource
    private UserService userService;
    @Resource
    private ActivityBlessingChanceRecordService activityBlessingChanceRecordService;
    @Resource
    private ActivityBlessingCardRecordService activityBlessingCardRecordService;
    @Resource
    private ShareService shareService;
    @Resource
    private UploadService uploadService;

    @Autowired
    private RedisService redisService;


    /**
     * 活动中心列表
     * @param request
     * @param page
     * @param status
     * @param client
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "活动中心列表")
    public AppResult cardmanage(HttpServletRequest request,
                                @ApiParam("分页当前页") @RequestParam Integer page,
                                @ApiParam("活动有效标记3.0.0  1新推出0已结束") @RequestParam Integer status,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                @ApiParam("App版本号") @RequestParam String appVersion) {
        if (page < 1) {
            page = 1;
        }
        String update = checkVersion(client, appVersion);//检查是否需要更新
        try {
            List<Activity> list = activityService.list((page - 1) * 5, 5, 1, status);
            Long count = activityService.queryCount(1);
            List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
            for (Activity activity : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", activity.getId());
                map.put("title", activity.getName());
                map.put("descript", activity.getDescript());
                map.put("startTime", DateUtil.dateFormat.format(activity.getStartTime()));
                map.put("endTime", DateUtil.dateFormat.format(activity.getEndTime()));
                map.put("path", ClientConstants.ALIBABA_PATH + "upload/" + activity.getPath());
                map.put("link", activity.getLink());
                Date limitTime = DateFormatTools.dateToShortDate(activity.getEndTime());
                Date now = DateFormatTools.dateToShortDate(new Date());
                Integer flag = 0;
                if ( ! limitTime.before(now) ) {
                    flag = 1;
                }
                map.put("status", flag);
                if (status != null) {
                    list1.add(map);//1新推出0已结束  根据前台传过来的参数  分类活动。
                } else {
                    list1.add(map);
                }
            }
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("list", list1);
            returnMap.put("page", page);
            returnMap.put("pages", calcPage(count.intValue(), 5));
            returnMap.put("update", update);
            return new AppResult(SUCCESS, returnMap);
        } catch (Exception e) {
            logger.error(e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
    
    @RequestMapping(value = "/lottery", method = RequestMethod.POST) 
    @ResponseBody
    @ApiOperation(value = "抽奖")
    public AppResult lottery(HttpServletRequest request,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("App版本号") @RequestParam String appVersion) {
    	try {
    		Map<String, Object> resultMap = new HashMap<String, Object>();
    		User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            
            //抽卡开始返回抽到什么牛
            logger.info(user.getId()+"开始抽卡===============");
            Date now = new Date();
            //2020年1月10日00：00 至1月16日23：59
            //2020年1月17日00：00至1月20日23：59
            //2020年1月21日00：00至1月24日21:59:59
            List<ProbabilityBullsEnum> list = new ArrayList<>();
            ProbabilityBullsEnum hitBull = null;
            if(/*now.after(DateUtil.dateFullTimeFormat.parse("2020-01-10 00:00:00")) 
            		&& */now.before(DateUtil.dateFullTimeFormat.parse("2020-01-16 23:59:59"))) {
            	list = ProbabilityBullsEnum.getValueByType(1);
            	
            } else if(now.after(DateUtil.dateFullTimeFormat.parse("2020-01-17 00:00:00")) 
            		&& now.before(DateUtil.dateFullTimeFormat.parse("2020-01-20 23:59:59"))) {
            	list = ProbabilityBullsEnum.getValueByType(2);
            	
            } else if(now.after(DateUtil.dateFullTimeFormat.parse("2020-01-21 00:00:00")) 
            		&& now.before(DateUtil.dateFullTimeFormat.parse("2020-01-24 21:59:59"))) {
            	list = ProbabilityBullsEnum.getValueByType(3);
            	
            } else {
            	return new AppResult(FAILED, "不在活动时间范围内");
            }
           
            hitBull = ProbabilityBullsUtil.lottery(list);
            LotteryBullsTypeEnum lotteryBulls = hitBull.getLotteryBulls();
            // 未命中提示里随机一个
            if(hitBull.getLotteryBulls() == LotteryBullsTypeEnum.NOTHING) {
            	int random = (int)(Math.random() * 5) + 1;
            	LotteryTipsEnum notHitTips = LotteryTipsEnum.getLotteryTipsByType(random);
            	
            	resultMap.put("notHitTips", BeanToMapUtil.convertBean(notHitTips));
            }
            resultMap.put("hitBull", BeanToMapUtil.convertBean(lotteryBulls));
        	// 最优先获得的卡牌先用,生成用卡记录
            ActivityBlessingCardRecord cardRecord = activityBlessingCardRecordService.insertCardRecord(user.getId(), hitBull);
            resultMap.put("cardNo", cardRecord.getCardNo());
    		return new AppResult(SUCCESS, resultMap);
		} catch (Exception e) {
			logger.error(e,e);
			e.printStackTrace();
	        return new AppResult(FAILED, e.getMessage());
		}
    }
    
    /**
     * 福卡首页
     * @author sxy
     * @param request
     * @param token
     * @param client
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/blessingCard/index", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "福卡首页")
    public AppResult cardmanage(HttpServletRequest request,
                                @ApiParam("用户token") @RequestParam String token,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                @ApiParam("App版本号") @RequestParam String appVersion) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            Map<String, Object> resultMap = new HashMap<String, Object>();
            Activity activity = activityService.getByName(ActivityConstant.NEW_YEAR_2020);
            Date now = new Date();
            Date startTime = activity.getStartTime(); //活动开始时间
            Date endTime = activity.getEndTime(); //活动截止时间
            
            //参与人数*人,稀有奔富牛*张
            int joinUserCount = activityBlessingCardRecordService.getJoinUserCount();
            ActivityBlessingCardRecordExample example = new ActivityBlessingCardRecordExample();
            example.createCriteria().andTypeEqualTo(LotteryBullsTypeEnum.BENFU_BULLS.getType()).andIsTransferEqualTo(false).andIsUnfinishedEqualTo(false);
            long haveBenfuCount = activityBlessingCardRecordService.getMapper().countByExample(example);
            //可抽卡次数
            int chanceCount = activityBlessingChanceRecordService.getCanUseChanceCount(user.getId());
            //新卡(未翻牌)
            List<ActivityBlessingCardRecord> newCards = activityBlessingCardRecordService.listBlessingCard(user.getId(), null, false);
            resultMap.put("newCards", newCards);
            //更新翻牌状态
            if(newCards != null && !newCards.isEmpty()) {
                for(ActivityBlessingCardRecord card : newCards) {
                    card.setIsFan(true);
                    activityBlessingCardRecordService.updateByExampleSelectiveForVersion(card);
                }
            }
            //健康牛
            List<ActivityBlessingCardRecord> healthBulls = activityBlessingCardRecordService.listBlessingCard(user.getId(), LotteryBullsTypeEnum.HEALTH_BULLS, true);
            int healthCount = activityBlessingCardRecordService.countBlessingCard(user.getId(), LotteryBullsTypeEnum.HEALTH_BULLS, true);
            //快乐牛
            List<ActivityBlessingCardRecord> happyBulls = activityBlessingCardRecordService.listBlessingCard(user.getId(), LotteryBullsTypeEnum.HAPPY_BULLS, true);
            int happyCount = activityBlessingCardRecordService.countBlessingCard(user.getId(), LotteryBullsTypeEnum.HAPPY_BULLS, true);
            //平安牛
            List<ActivityBlessingCardRecord> safetyBulls = activityBlessingCardRecordService.listBlessingCard(user.getId(), LotteryBullsTypeEnum.SAFETY_BULLS, true);
            int safetyCount = activityBlessingCardRecordService.countBlessingCard(user.getId(), LotteryBullsTypeEnum.SAFETY_BULLS, true);
            //幸运牛
            List<ActivityBlessingCardRecord> luckyBulls = activityBlessingCardRecordService.listBlessingCard(user.getId(), LotteryBullsTypeEnum.LUCKY_BULLS, true);
            int luckyCount = activityBlessingCardRecordService.countBlessingCard(user.getId(), LotteryBullsTypeEnum.LUCKY_BULLS, true);
            //奔富牛
            List<ActivityBlessingCardRecord> benfuBulls = activityBlessingCardRecordService.listBlessingCard(user.getId(), LotteryBullsTypeEnum.BENFU_BULLS, true);
            int benfuCount = activityBlessingCardRecordService.countBlessingCard(user.getId(), LotteryBullsTypeEnum.BENFU_BULLS, true);
            //牛气冲天
            List<ActivityBlessingCardRecord> greateBulls = activityBlessingCardRecordService.listBlessingCard(user.getId(), LotteryBullsTypeEnum.GREATE_BULLS, true);
            int greateCount = activityBlessingCardRecordService.countBlessingCard(user.getId(), LotteryBullsTypeEnum.GREATE_BULLS, true);
            
            //share
            List<Share> shareList = new ArrayList<Share>();
            Share activityShare = shareService.queryByClickWord("分享活动");
            Upload upload1 = uploadService.get(activityShare.getUploadId());
            if(upload1 != null) {
                activityShare.setSharePic(upload1.getCdnPath());
            }
            Share friendShare = shareService.queryByClickWord("送给朋友");
            Upload upload2 = uploadService.get(friendShare.getUploadId());
            if(upload2 != null) {
                friendShare.setSharePic(upload2.getCdnPath());
            }
            shareList.add(activityShare);
            shareList.add(friendShare);
            
            resultMap.put("joinUserCount", joinUserCount);
            resultMap.put("haveBenfuCount", haveBenfuCount);
            resultMap.put("chanceCount", chanceCount);
            resultMap.put("healthBulls", healthBulls);
            resultMap.put("healthCount", healthCount);
            resultMap.put("happyBulls", happyBulls);
            resultMap.put("happyCount", happyCount);
            resultMap.put("safetyBulls", safetyBulls);
            resultMap.put("safetyCount", safetyCount);
            resultMap.put("luckyBulls", luckyBulls);
            resultMap.put("luckyCount", luckyCount);
            resultMap.put("benfuBulls", benfuBulls);
            resultMap.put("benfuCount", benfuCount);
            resultMap.put("greateBulls", greateBulls);
            resultMap.put("greateCount", greateCount);
            resultMap.put("shareList", shareList);
            resultMap.put("inviteCode", user.getInviteCode()); //invite_code
            
            if(now.after(startTime) && now.before(endTime)) {
                resultMap.put("isInActivity", true); //活动期间
            } else if(now.after(endTime)){
                //拜年红包
                resultMap.put("isInActivity", false); //活动结束
                resultMap.put("isLottery", false); //未中奖
                if(greateBulls != null && !greateBulls.isEmpty()) {
                    ActivityBlessingCardRecord greateBull = greateBulls.get(0);
                    if(greateBull.getIsUsed()) {
                        resultMap.put("isLottery", true); //中奖
                        resultMap.put("multiple", "2"); //倍数
                        resultMap.put("amount", greateBull.getAmount());
                        resultMap.put("vipAmount", "");
                        if(greateBull.getIsDouble()) {
                            resultMap.put("amount", greateBull.getAmount()/2);
                            resultMap.put("vipAmount", greateBull.getAmount());
                        }
                    } else {
                        resultMap.put("isInActivity", true); //活动期间
                    }
                }
            } else {
                return new AppResult(FAILED, "未在活动期间内");
            }
            
            return new AppResult(SUCCESS, resultMap);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
    
    @RequestMapping(value = "/compound", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "合卡")
    public AppResult compound(HttpServletRequest request,
            @ApiParam("用户token") @RequestParam String token,
            @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
            @ApiParam("App版本号") @RequestParam String appVersion) {
    	try {
    		 User user = userService.checkLogin(token);
             if (user == null) {
                 return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
             }
             
    		 activityBlessingCardRecordService.doCompoundGreateBulls(user.getId());
    		 return new AppResult(SUCCESS);
		} catch (Exception e) {
			logger.error(e, e);
	        return new AppResult(FAILED, e.getMessage());
		}
    	
    }
    
    @RequestMapping(value = "/blessingCard/share", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "分享活动")
    public AppResult cardShare(HttpServletRequest request,
                                @ApiParam("用户token") @RequestParam String token,
                                @ApiParam("终端来源 IOS,Android,PC,WAP") @RequestParam String client,
                                @ApiParam("App版本号") @RequestParam String appVersion) {
        try {
            User user = userService.checkLogin(token);
            if (user == null) {
                return new AppResult(NO_LOGIN, MESSAGE_NO_LOGIN);
            }
            
            //分享获得次数,每天最多3次
            Date now = new Date();
            ActivityBlessingChanceRecordExample example = new ActivityBlessingChanceRecordExample();
            example.createCriteria().andUserIdEqualTo(user.getId()).andTypeEqualTo(2)
                    .andCreateDateGreaterThan(DateUtil.getDayMinTime(now))
                    .andCreateDateLessThanOrEqualTo(DateUtil.getDayMaxTime(now));
            long count = activityBlessingChanceRecordService.getMapper().countByExample(example);
            if (count < 3) {
                activityBlessingChanceRecordService.addToUser(user.getId(), 1, 2, null);
            }
            
            return new AppResult(SUCCESS);
        } catch (Exception e) {
            logger.error(e);
            return new AppResult(FAILED, MESSAGE_EXCEPTION);
        }
    }
    
}
