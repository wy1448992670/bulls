package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.ActivityConstant;
import com.goochou.p2b.constant.ActivityTargetTypeEnum;
import com.goochou.p2b.constant.ActivityTriggerTypeEnum;
import com.goochou.p2b.constant.BusinessTableEnum;
import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.DictConstants;
import com.goochou.p2b.constant.InvestmentTypeEnum;
import com.goochou.p2b.constant.LotteryGiftPrizeTypeEnum;
import com.goochou.p2b.constant.ProjectAnnualizedEnum;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.constant.pasture.InvestmentStateEnum;
import com.goochou.p2b.dao.ActivityDetailMapper;
import com.goochou.p2b.dao.ActivityDetailSendHongbaoMapper;
import com.goochou.p2b.dao.ActivityFakeMapper;
import com.goochou.p2b.dao.ActivityMapper;
import com.goochou.p2b.dao.ActivityPrizeMapper;
import com.goochou.p2b.dao.ActivityQualificationMapper;
import com.goochou.p2b.dao.AssistanceActivityMapper;
import com.goochou.p2b.dao.HongbaoMapper;
import com.goochou.p2b.dao.InvestmentViewMapper;
import com.goochou.p2b.dao.InviteUserAcitvityAwardLogMapper;
import com.goochou.p2b.dao.LoginRecordInviteViewMapper;
import com.goochou.p2b.dao.LotteryCountMapper;
import com.goochou.p2b.dao.LotteryGiftMapper;
import com.goochou.p2b.dao.LotteryRecordMapper;
import com.goochou.p2b.dao.SplashScreenMapper;
import com.goochou.p2b.dao.UserInviteMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.ActivityDetail;
import com.goochou.p2b.model.ActivityDetailExample;
import com.goochou.p2b.model.ActivityDetailSendHongbao;
import com.goochou.p2b.model.ActivityDetailSendHongbaoExample;
import com.goochou.p2b.model.ActivityExample;
import com.goochou.p2b.model.ActivityExample.Criteria;
import com.goochou.p2b.model.ActivityQualification;
import com.goochou.p2b.model.AssistanceActivity;
import com.goochou.p2b.model.AssistanceActivityExample;
import com.goochou.p2b.model.BaseResult;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.InvestmentExample;
import com.goochou.p2b.model.InvestmentView;
import com.goochou.p2b.model.InvestmentViewExample;
import com.goochou.p2b.model.LoginRecordExample;
import com.goochou.p2b.model.LoginRecordInviteViewExample;
import com.goochou.p2b.model.LotteryCount;
import com.goochou.p2b.model.LotteryGift;
import com.goochou.p2b.model.LotteryRecord;
import com.goochou.p2b.model.LotteryRecordExample;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserExample;
import com.goochou.p2b.model.vo.ActivityInvestAndLotteryVO;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.LotteryCountService;
import com.goochou.p2b.service.LotteryRecordService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.RateCouponService;
import com.goochou.p2b.service.ShareService;
import com.goochou.p2b.service.SmsSendService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.DateUtil;
import com.goochou.p2b.utils.RegularUtils;
import com.goochou.p2b.utils.UUIDUtil;
import com.google.common.collect.Maps;

import net.sf.json.JSONObject;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final static Logger logger = Logger.getLogger(ActivityServiceImpl.class);

    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private SplashScreenMapper splashScreenMapper;
    @Resource
    private LotteryCountService lotteryCountService;
    @Resource
    private LotteryRecordService lotteryRecordService;
    @Resource
    private RateCouponService rateCouponService;
    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private MessageService messageService;
    @Resource
    private LotteryCountMapper lotteryCountMapper;
    @Resource
    private ActivityPrizeMapper activityPrizeMapper;
    @Resource
    private UserService userService;
    @Resource
    private ActivityFakeMapper activityFakeMapper;
    @Resource
    private LotteryGiftMapper lotteryGiftMapper;
    @Resource
    private ActivityQualificationMapper activityQualificationMapper;
    @Resource
    private LotteryRecordMapper lotteryRecordMapper;

    @Resource
    private HongbaoMapper hongbaoMapper;

    @Resource
    private UserInviteMapper userInviteMapper;
    @Resource
    private InviteUserAcitvityAwardLogMapper inviteUserAcitvityAwardLogMapper;
    @Resource
    private ShareService shareService;

    @Resource
    private AssistanceActivityMapper assistanceActivityMapper;

    @Resource
    private ActivityDetailMapper activityDetailMapper;

    @Resource
    private ActivityDetailSendHongbaoMapper activityDetailSendHongbaoMapper;

	@Resource
    protected MemcachedManager memcachedManager;

	@Resource
	private InvestmentViewMapper investmentViewMapper;
	
	@Resource
	private LoginRecordInviteViewMapper loginRecordInviteViewMapper;
	
	@Resource
	private SmsSendService smsSendService;
	
    @Override
    public List<Map<String, Object>> lists(Integer start, Integer limit,
                                           Integer status) {
        return activityMapper.querys(start, limit, status);
    }

    @Override
    public List<Activity> list(Integer start, Integer limit, Integer status, Integer type) {
        return activityMapper.query(start, limit, status, type);
    }

    @Override
    public Activity getByName(String name) {
        ActivityExample example = new ActivityExample();
        Criteria c = example.createCriteria();
        c.andNameEqualTo(name);
        List<Activity> list = activityMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Activity getByNameAndStatus(String name, Integer status) {
        ActivityExample example = new ActivityExample();
        Criteria c = example.createCriteria();
        c.andNameEqualTo(name);
        c.andStatusEqualTo(status);
        List<Activity> list = activityMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean checkExpired(String name) {
        ActivityExample example = new ActivityExample();
        Criteria c = example.createCriteria();
        c.andNameEqualTo(name);
        List<Activity> list = activityMapper.selectByExample(example);
        if (null != list && !list.isEmpty()) {
            Activity a = list.get(0);
            Calendar c1 = Calendar.getInstance();
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            c1.set(Calendar.MILLISECOND, 0);
            if (a.getEndTime().before(c1.getTime())) {
                return true; // 已过期
            }
        }
        return false;
    }

    @Override
    public Long queryCount(Integer status) {
        return activityMapper.queryCount(status);
    }

    @Override
    public void save(Activity ac) {
        if (ac.getUploadId() == null) {
            ac.setUploadId(1);//直接没有图片的任何东西  直接用默认图片id
        }
        activityMapper.insert(ac);
    }

    @Override
    public Activity selectNewActivity() {
        ActivityExample example = new ActivityExample();
        example.setOrderByClause("start_time desc");
        if (null == activityMapper.selectByExample(example)
                || activityMapper.selectByExample(example).size() == 0) {
            return null;
        } else {
            return activityMapper.selectByExample(example).get(0);
        }

    }

    @Override
    public Map<String, Object> selectActivityById(Integer id) {
        return activityMapper.detail(id);
    }

    @Override
    public void update(Activity ac) {
        activityMapper.updateByPrimaryKeySelective(ac);
    }

    @Override
    public List<Map<String, Object>> rateBreakList(String keyword,
                                                   Integer start, Integer limit) {
        return activityMapper.rateBreakList(keyword, start, limit);
    }

    @Override
    public Integer rateBreakListCount(String keyword) {
        return activityMapper.rateBreakListCount(keyword);
    }

    @Override
    public Activity queryByName(String name) {
        return activityMapper.queryByName(name);
    }

    @Override
    public List<Map<String, Object>> selectRegularActivityList(Integer userId) {
        return activityMapper.selectRegularActivityList(userId);
    }

    @Override
    public List<Activity> selectAll() {
        return activityMapper.selectAll();
    }

    @Override
    public void saveAwardGoldCount(User user, Investment investment) {
        String activityName = ActivityConstant.REGULAR_INVESTMENT_AWARD_GOLD;
        Activity activity = activityMapper.queryByName(activityName);
        if (activity != null) {
            Date now = new Date();
            Date startTime = activity.getStartTime();
            Date endTime = DateFormatTools.jumpOneDay(activity.getEndTime(), 1);
            if (now.before(endTime) && now.after(startTime)) {
                //查询用户是否存在抽奖次数数据
                LotteryCount lotteryCount = lotteryCountService.queryCountByUserId(user.getId());
                if (lotteryCount == null) {
                    lotteryCount = new LotteryCount();
                    lotteryCount.setUserId(user.getId());
                    lotteryCount.setTime(now);
                    lotteryCount.setSysCount(0);
                    lotteryCount.setSysTime(now);
                    lotteryCount.setActivityId(activity.getId());
                    lotteryCount.setVersion(0);
                    lotteryCountService.insertSelective(lotteryCount);
                }
                Map<String, Object> params = new HashMap<>();
                params.put("userId", user.getId());
                params.put("activityId", activity.getId());
                if (StringUtils.isEmpty(lotteryCount.getTempField())) {
                    lotteryCount.setTempField("");
                }
                int available = RegularUtils.getSubStringCount("#", lotteryCount.getTempField());
                List<Map<String, Object>> list = lotteryRecordService.queryAllAwardRecord(params);
                if (available < 3) {
                    //判断如果投资的项目少于3个月，则不算有效活动投资
                    /*Project project = investment.getProject();
                    if(project == null){
						return;
					}*/
                    /*if(project.getLimitDays() < 90){
                        return;
					}*/

                    StringBuffer buff = null;
                    String countTmep = lotteryCount.getTempField();
                    if (StringUtils.isEmpty(countTmep)) {
                        buff = new StringBuffer();
                    } else {
                        buff = new StringBuffer(countTmep);
                    }
                    if (investment.getAmount() >= 3000 && investment.getAmount() < 3500) {
                        buff.append("step3000#");
                    } else if (investment.getAmount() >= 3500 && investment.getAmount() < 3900) {
                        buff.append("step3500#");
                    } else if (investment.getAmount() >= 3900) {
                        buff.append("step3900#");
                    }
                    lotteryCount.setTempField(buff.toString());
                    lotteryCount.setActivityId(activity.getId());
                    lotteryCountService.save(lotteryCount);
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> selectInvestmentActivity(Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.selectInvestmentActivity(map);
    }

    @Override
    public Double selectAppleActivity(Integer userId, Integer limitDays, Date startDate, Date endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("limitDays", limitDays);
        map.put("startTime", startDate);
        map.put("endTime", endDate);
        return activityMapper.selectAppleActivity(map);
    }

    @Override
    public Double selectAppleActivity2(Integer userId, Integer limitDays, Date startDate, Date endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("limitDays", limitDays);
        map.put("startTime", startDate);
        map.put("endTime", endDate);
        return activityMapper.selectAppleActivity2(map);
    }

    @Override
    public Activity queryActivityById(Integer activityId) {
        return activityMapper.selectByPrimaryKey(activityId);
    }

    @Override
    public List<Map<String, Object>> selectAppleActivityUserList(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limitDays", limitDays);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("activityId", activityId);
        map.put("start", page);
        map.put("limit", limit);
        map.put("keyword", keyword);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        return activityMapper.selectAppleActivityUserList(map);
    }

    @Override
    public Integer selectAppleActivityUserCount(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limitDays", limitDays);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("activityId", activityId);
        map.put("keyword", keyword);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        return activityMapper.selectAppleActivityUserCount(map);
    }


    @Override
    public List<Map<String, Object>> selectWeekInvestmentActivity(Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.selectWeekInvestmentActivity(map);
    }


    @Override
    public List<Map<String, Object>> selectWeekInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limitDays", limitDays);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("activityId", activityId);
        map.put("start", page);
        map.put("limit", limit);
        map.put("keyword", keyword);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        return activityMapper.selectWeekInvestmentAward(map);
    }

    public Integer countWeekInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limitDays", limitDays);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("activityId", activityId);
        map.put("keyword", keyword);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        return activityMapper.countWeekInvestmentAward(map);
    }


    @Override
    public List<Map<String, Object>> selectWeekInvestmentActivity(Date startTime, Date endTime, Integer userid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("userid", userid);
        return activityMapper.selectWeekInvestmentActivityByUserId(map);
    }


    @Override
    public List<Map<String, Object>> selectHongbaoInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limitDays", limitDays);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("activityId", activityId);
        map.put("start", page);
        map.put("limit", limit);
        map.put("keyword", keyword);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        return activityMapper.selectHongbaoInvestmentAward(map);
    }

    public Integer countHongbaoInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limitDays", limitDays);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("activityId", activityId);
        map.put("keyword", keyword);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        return activityMapper.countHongbaoInvestmentAward(map);
    }


    @Override
    public List<Map<String, Object>> selectJingdongInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays, Integer page, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limitDays", limitDays);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("activityId", activityId);
        map.put("start", page);
        map.put("limit", limit);
        map.put("keyword", keyword);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        return activityMapper.selectJingdongInvestmentAward(map);
    }

    public Integer countJingdongInvestmentAward(String keyword, String keyword1, Date keyword2, Integer activityId, Date startTime, Date endTime, Integer limitDays) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limitDays", limitDays);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("activityId", activityId);
        map.put("keyword", keyword);
        map.put("keyword1", keyword1);
        map.put("keyword2", keyword2);
        return activityMapper.countJingdongInvestmentAward(map);
    }

    @Override
    public List<Map<String, Object>> selectProjectTerminator(Map<String, Object> map) {
        return activityMapper.selectProjectTerminator(map);
    }

    @Override
    public List<Map<String, Object>> projectAwardList(String keyword, Date startTime, Date endTime, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("limit", limit);
        return activityMapper.projectAwardList(map);
    }

    @Override
    public Integer projectAwardListCount(String keyword, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        return activityMapper.projectAwardListCount(map);
    }

    @Override
    public List<Map<String, Object>> selectInvestmentAwardActivity(Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.selectInvestmentAwardActivity(map);
    }

    public List<Map<String, Object>> selectInvestmentActivityFilter(Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.selectInvestmentActivityFilter(map);
    }

    @Override
    public Integer queryActivityByGift(String activityName, Integer giftId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("activityName", activityName);
        map.put("giftId", giftId);
        return activityMapper.queryActivityByGift(map);
    }

    public List<Map<String, Object>> selectActivityListByName(String activityName, String keyword, Integer activityId, Date startTime, Date endTime, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        map.put("activityId", activityId);
        map.put("start", start);
        map.put("limit", limit);
        if (activityName.equals(ActivityConstant.REGULAR_AWARD_NOVERBER)) {
            return activityMapper.selectNoverberActivityList(map);
        }
        return activityMapper.select666ActivityList(map);
    }

    public Integer selectActivityCountByName(String activityName, String keyword, Integer activityId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        map.put("activityId", activityId);
        if (activityName.equals(ActivityConstant.REGULAR_AWARD_NOVERBER)) {
            return activityMapper.selectNoverberActivityCount(map);
        }
        return activityMapper.select666ActivityCount(map);
    }

    @Override
    public List<Map<String, Object>> selecthalloweenActivity(Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.selecthalloweenActivity(map);
    }

    public List<Map<String, Object>> selectGetDoubleGiftList(String keyword, Integer activityId, Date
            startTime, Date endTime, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        map.put("activityId", activityId);
        map.put("start", start);
        map.put("limit", limit);
        return activityMapper.selectGetDoubleGiftList(map);
    }

    @Override
    public Integer selectGetDoubleGiftCount(String keyword, Integer activityId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        map.put("activityId", activityId);
        return activityMapper.selectGetDoubleGiftCount(map);
    }

    @Override
    public Map<String, Object> selectGetDoubleGiftMap(Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.selectGetDoubleGiftMap(map);
    }


    @Override
    public List<Map<String, Object>> selectSinglesDayActivity(Integer userId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.selectSinglesDayActivity(map);
    }

    @Override
    public List<Map<String, Object>> beforeSinglesDayActivity(Integer userId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.beforeSinglesDayActivity(map);
    }

    @Override
    public List<Map<String, Object>> listSelectSinglesDay(String keyword, Integer activityId, Date startTime, Date
            endTime, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("limit", limit);
        return activityMapper.listSelectSinglesDay(map);
    }


    @Override
    public List<Map<String, Object>> thanksgivingDay(String keyword, Date startTime, Date endTime, Integer
            start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("limit", limit);
        return activityMapper.thanksgivingDay(map);
    }

    @Override
    public Map<String, Object> selectValentinesDayActivity(Integer userId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("userId", userId);
        List<Map<String, Object>> list = activityMapper.selectValentinesDayActivity(map);
        if (list != null && list.size() > 0 && list.get(0) != null) {
            return list.get(0);
        } else {
            return null;
        }
    }


    public Integer countSelectSinglesDay(String keyword, Integer activityId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        return activityMapper.countSelectSinglesDay(map);
    }


    @Override
    public Double selectActivityTimeInvestmentAmount(Integer userId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.selectActivityTimeInvestmentAmount(map);
    }

    @Override
    public Map<String, Object> selectInvestmentTotal(Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.selectInvestmentTotal(map);
    }

    @Override
    public List<Map<String, Object>> selectValentinesDayActivityList(String keyword, Date startTime, Date endTime, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("limit", limit);
        return activityMapper.selectValentinesDayActivityList(map);
    }

    @Override
    public Map<String, Object> selectInvest(String keyword, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        List<Map<String, Object>> list = activityMapper.selectInvest(map);
        if (list != null && list.size() > 0 && list.get(0) != null) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> selectExpMobey(String keyword, Date startTime, Date endTime, Integer activityId) {
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        map.put("activityId", activityId);
        List<Map<String, Object>> list = activityMapper.selectExpMobey(map);
        if (list != null && list.size() > 0 && list.get(0) != null) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> selectHongbao(String keyword, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        List<Map<String, Object>> list = activityMapper.selectHongbao(map);
        if (list != null && list.size() > 0 && list.get(0) != null) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer selectValentinesDayActivityCount(String keyword, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("keyword", keyword);
        return activityMapper.selectValentinesDayActivityCount(map);
    }


    @Override
    public List<Map<String, Object>> selectActivityInvestment(Integer userId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return activityMapper.selectActivityInvestment(map);
    }

    @Override
    public List<Map<String, Object>> newAnniversary(String keyword, Date startTime, Date endTime, Date acstartTime, Date acendTime, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("keyword", keyword);
        map.put("acstartTime", acstartTime);
        if (endTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(endTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("endTime", c1.getTime());
        } else {
            map.put("endTime", null);
        }
        if (acendTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(acendTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("acendTime", c1.getTime());
        } else {
            map.put("acendTime", null);
        }
        map.put("start", start);
        map.put("limit", limit);
        return activityMapper.newAnniversary(map);
    }

    @Override
    public Integer newAnniversaryCount(String keyword, Date startTime, Date endTime, Date acstartTime, Date acendTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("acstartTime", acstartTime);
        if (endTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(endTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("endTime", c1.getTime());
        } else {
            map.put("endTime", null);
        }
        if (acendTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(acendTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("acendTime", c1.getTime());
        } else {
            map.put("acendTime", null);
        }
        map.put("keyword", keyword);
        return activityMapper.newAnniversaryCount(map);
    }

    @Override
    public List<Map<String, Object>> twoYearsThanksActivity(String keyword, Date startTime, Date endTime, Date acstartTime, Date acendTime, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        if (endTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(endTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("endTime", c1.getTime());
        } else {
            map.put("endTime", null);
        }
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("limit", limit);
        return activityMapper.twoYearsThanksActivity(map);
    }

    @Override
    public Integer twoYearsThanksActivityCount(String keyword, Date startTime, Date endTime, Date acstartTime, Date acendTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        if (endTime != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(endTime);
            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            map.put("endTime", c1.getTime());
        } else {
            map.put("endTime", null);
        }
        map.put("keyword", keyword);
        return activityMapper.twoYearsThanksActivityCount(map);
    }

    public Map<String, Object> selectRank(String name, Date startTime, Date endTime, Integer limitDays) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("limitDays", limitDays);
        map.put("start", 0);
        map.put("limit", 50);
        //年化投资排名(福利4)
        List<Map<String, Object>> yearRank = activityMapper.selectYearInvestmentRank(map);
        Map<String, Object> m = new HashMap();
        m.put("username", "g***f");
        m.put("allAmount", "331450");
        yearRank.add(0, m);
        Map<String, Object> m1 = new HashMap();
        m1.put("username", "lan***ji");
        m1.put("allAmount", "216516");
        yearRank.add(0, m1);
        Map<String, Object> m2 = new HashMap();
        m2.put("username", "王***离");
        m2.put("allAmount", "199856");
        yearRank.add(0, m2);
        for (Map<String, Object> obj : yearRank) {
            String username = obj.get("username") == null ? null : obj.get("username").toString();
            if (StringUtils.isNotBlank(username)) {
                obj.put("true_name", username.substring(0, 1) + "***" + username.substring(username.length() - 1, username.length()));
            } else {
                obj.put("true_name", "*****");
            }
            double allAmount = (double) m1.get("yearAmount");
            obj.put("allAmount", (int) allAmount);
        }
        //邀请人数排名(福利4)
        map.put("limit", 10);
        List<Map<String, Object>> inviteRank = activityMapper.selectInviteRank(map);
        Map<String, Object> m4 = new HashMap();
        m4.put("username", "P***6");
        m4.put("inviteCount", "5");
        inviteRank.add(0, m4);
        Map<String, Object> m5 = new HashMap();
        m5.put("username", "就***飞");
        m5.put("inviteCount", "4");
        inviteRank.add(1, m5);
        Map<String, Object> m6 = new HashMap();
        m6.put("username", "q***l");
        m6.put("inviteCount", "4");
        inviteRank.add(2, m6);
        Map<String, Object> m7 = new HashMap();
        m7.put("username", "梦***倪");
        m7.put("inviteCount", "3");
        inviteRank.add(3, m7);
        for (Map<String, Object> obj : inviteRank) {
            String username = obj.get("username") == null ? null : obj.get("username").toString();
            if (StringUtils.isNotBlank(username)) {
                obj.put("true_name", username.substring(0, 1) + "***" + username.substring(username.length() - 1, username.length()));
            } else {
                obj.put("true_name", "*****");
            }
        }
        Collections.sort(yearRank, new MapComparatorDesc());
        resMap.put("yearRank", yearRank.subList(0, 50));
        resMap.put("inviteRank", inviteRank.subList(0, 10));
        return resMap;
    }
    //根据年华倒序排列
    public class MapComparatorDesc implements Comparator<Map<String, Object>> {
        public int compare(Map<String, Object> m1, Map<String, Object> m2) {
            Integer v1 = (int) ((double) m1.get("yearAmount"));
            Integer v2 = (int) ((double) m2.get("yearAmount"));
            if (v2 != null) {
                return v2.compareTo(v1);
            }
            return 0;
        }
    }
    //根据时间倒序排列
    public class MapComparatorDateDesc implements Comparator<Map<String, Object>> {
        public int compare(Map<String, Object> m1, Map<String, Object> m2) {
            //System.out.println( m1.get("time"));
            String s1 = m1.get("time").toString();
            String s2 = m2.get("time").toString();
            if (s2 != null) {
                return s2.compareTo(s1);
            }
            return 0;
//        try {
//            Date d1 = format.parse(m1.get("date").toString());
//            Date d2 = format.parse(m2.get("date").toString());
//            if (d2 != null) {
//                return d1.compareTo(d2);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            logger.info("" + e);
//        }
//        return 0;
        }
    }

    public Map<String, Object> getLotteryInfo(Map<String, Object> map) {
        Map<String, Object> resMap = new HashMap<String, Object>();
//        //抽奖记录
//        List<Map<String, Object>> lotteryList = lotteryGiftService.queryLotteryRecord(map);
//        Double investmentAmount = selectInvestmentAmount(map);  //投资金额
//        int lotteryCount = selectUserLottery(map);   //抽奖次数
//        if (investmentAmount == null) {
//            investmentAmount = 0.00;
//        }
//        int leftCount = (int) (investmentAmount / 5000) - lotteryCount;//剩余抽奖次数
//        resMap.put("lottery", lotteryList);
//        resMap.put("leftCount", leftCount < 0 ? 0 : leftCount);
        return resMap;
    }

    /***
     * 获取活动
     * @param userId
     * @param activityId
     * @param startTime
     * @param endTime
     * @param limitDays
     * @return
     */
    public ActivityInvestAndLotteryVO getLotteryInfo(Integer userId, Integer activityId, Date startTime, Date endTime, Integer limitDays) {
        ActivityInvestAndLotteryVO v = new ActivityInvestAndLotteryVO();
        //活动时间小于2017-12-07 计算安心赚
        Map<String, Object> investmentMap = null;
        try {
            if (startTime.before(DateFormatTools.strToDate2("2017-11-30"))) {
                investmentMap = selectInvestmentAmount(userId, startTime, endTime, limitDays);  //投资信息
            } else {
                investmentMap = selectInvestmentAmount2(userId, startTime, endTime, limitDays);  //投资信息
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ActivityConstant.isInActivity(startTime, endTime);
        Map<String, Object> lotteryMap = selectUserLottery(userId, activityId,startTime,endTime);   //抽奖信息
        v.setUserId(userId);
        v.setInvestAmount((Double) investmentMap.get("investmentAmount"));
        v.setLotteryAmount((Double) lotteryMap.get("amount"));
        v.setLotteryCount(Integer.valueOf(lotteryMap.get("count").toString()));
        v.setYearAmount((Double) investmentMap.get("yearAmount"));
        return v;
    }

    public Map<String, Object> getNoverberMap(Activity activity, User user) {
        Map<String, Object> result = Maps.newHashMap();
        Date date = new Date();
        //专场加息
        int limitDays = 0;
        int annualize = 0;
        Double increaseAnnualized = 0.0;
        int activityDay = DateFormatTools.dayToDaySubtractWithoutSeconds(activity.getStartTime(), date);
        int a = activityDay % 3;
        switch (a) {
            case 0:
                limitDays = 30;
                annualize = ProjectAnnualizedEnum.ANNUALIZED_8.getFeatureType();
                increaseAnnualized = ProjectAnnualizedEnum.ANNUALIZED_8.getFeatureName();
                break;
            case 1:
                limitDays = 90;
                annualize = ProjectAnnualizedEnum.ANNUALIZED_9.getFeatureType();
                increaseAnnualized = ProjectAnnualizedEnum.ANNUALIZED_9.getFeatureName();
                break;
            case 2:
                limitDays = 180;
                annualize = ProjectAnnualizedEnum.ANNUALIZED_10.getFeatureType();
                increaseAnnualized = ProjectAnnualizedEnum.ANNUALIZED_10.getFeatureName();
                break;
        }
        result.put("limitDays", limitDays);   //投资期限(天)
        result.put("annualize", annualize);   //年化收益
        result.put("increaseAnnualized", increaseAnnualized);//额外加息
        result.put("doubleOne", 1.1);//双11加息

        //收益达人
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", activity.getStartTime());
        map.put("endTime", activity.getEndTime());
        map.put("limitDays", 30);
        List<Map<String, Object>> yearRank = activityMapper.selectYearInvestmentRank(map);
        double yearAmount = 0.0;
        if (null == user) {
            result.put("loginStatus", false);
        } else {
            for (Map<String, Object> m : yearRank) {
                if (user.getId() == (int) (m.get("user_id"))) {
                    yearAmount = (double) m.get("yearAmount");
                }
            }
        }
        //(假数据)查询测试账号用户的投资信息
        List<Map<String, Object>> yearTest = activityFakeMapper.queryActivityFake(activity.getId(), 1, null);
        yearRank.addAll(yearTest);
        for (Map<String, Object> m : yearRank) {
            m.put("yearAmount", BigDecimalUtil.fixed2(m.get("yearAmount")));
        }
        Collections.sort(yearRank, new MapComparatorDesc());
        if (yearRank.size() >= 10) {
            yearRank = yearRank.subList(0, 10);
        }
        result.put("yearRank", yearRank);    //年化投资总排名

        map.put("startTime", DateFormatTools.dateToStr2(date));
        map.put("endTime", DateFormatTools.dateToStr2(date));
        List<Map<String, Object>> dayRank = activityMapper.selectYearInvestmentRank(map);
        List<Map<String, Object>> dayTest = activityFakeMapper.queryActivityFake(activity.getId(), 2, date);
        dayRank.addAll(dayTest);
        for (Map<String, Object> m : dayRank) {
            m.put("yearAmount", BigDecimalUtil.fixed2(m.get("yearAmount")));
        }
        Collections.sort(dayRank, new MapComparatorDesc());
        if (dayRank.size() >= 3) {
            dayRank = dayRank.subList(0, 3);
        }
        result.put("dayRank", dayRank);      //年化投资日排名
        result.put("yearAmount", (int) yearAmount);
        return result;
    }

    /**
     * 跨年活动进行抽奖
     * @param user 用户对象
     * @param activityId 活动id
     * @return map
     */
    @Override
    public synchronized Map<String, Object>doLotteryDraw(User user, int activityId) {

        Map<String,Object> resultMap = Maps.newHashMap();

        try {
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",user.getId());
            map.put("activityId",activityId);
            ActivityQualification activityQualification = activityQualificationMapper.findByUserIdAndActivityId(map);
            if(null == activityQualification){
                resultMap.put("code","1");
                return resultMap;

            }
            if(activityQualification.getOpportunityNumber() <= 0){
                resultMap.put("code","2");
                return resultMap;
            }

            List<LotteryGift> lotteryGiftList = lotteryGiftMapper.getActivityGiftItems(activityId);
            LotteryGift lotteryGift = com.goochou.p2b.utils.LotteryUtil.startLottery(lotteryGiftList);
            if(lotteryGift.getLeftNum()<=0){
                for(LotteryGift lg:lotteryGiftList){
                	//取8元投资红包
                    if(lg.getName().indexOf("8元投资") != -1){
                        lotteryGift.setId(lg.getId());
                        lotteryGift.setName(lg.getName());
                        lotteryGift.setLeftNum(lg.getLeftNum());
                        lotteryGift.setActivityId(lg.getActivityId());
                        lotteryGift.setVersion(lg.getVersion());
                        lotteryGift.setAmount(lg.getAmount());
                        lotteryGift.setPrizeType(LotteryGiftPrizeTypeEnum.INVEST_HONGBAO.getFeatureName());
                        lotteryGift.setRule(lg.getRule());
                        break;
                    }
                }
                logger.info("获取奖励物品无剩余数量, 获取默认8元投资红包"+lotteryGift.getId());
            }

            //发奖
            String ruleStr = lotteryGift.getRule();
            JSONObject json = JSONObject.fromObject(ruleStr);
            LotteryGiftPrizeTypeEnum prizeTypeEnum = LotteryGiftPrizeTypeEnum.getValueByName(lotteryGift.getPrizeType());
            switch (prizeTypeEnum) {
			case INVEST_HONGBAO:
				if(hongbaoService.saveInvestHongBao(lotteryGift.getAmount(), user.getId(), "恭喜你获得" + lotteryGift.getAmount() + "元投资红包", 1, 2, 0, json.getInt("limitDays"), json.getInt("limitAmount"),json.getString("limitDays"), json.getInt("expireDays")) <= 0){
                    resultMap.put("code","3");
				    logger.info("跨年活动抽奖发放投资红包失败奖品id"+lotteryGift.getId());
                    throw new RuntimeException("跨年活动抽奖发放投资红包失败");
				}

				break;
			case CASH_HONGBAO:
				String description = "恭喜你获得" + lotteryGift.getAmount() + "元现金红包";
				if(hongbaoService.sendHongBaoToUser(lotteryGift.getAmount(), user.getId(), description, null, 1, 1, 0, null, json.getInt("expireDays")) <= 0){
                    resultMap.put("code","3");
                    logger.info("跨年活动抽奖发放现金红包失败奖品id"+lotteryGift.getId());
                    throw new RuntimeException("跨年活动抽奖发放现金红包失败");
				}

				break;
			case RATE_COUPON:
				if(rateCouponService.saveRateCouponToUser(3, "恭喜你获得散标加息券", json.getDouble("rate"), user.getId(), json.getInt("expireDays"), json.getInt("days"), null, null) <= 0){
                    resultMap.put("code","3");
                    logger.info("跨年活动抽奖发放加息券失败奖品id"+lotteryGift.getId());
                    throw new RuntimeException("跨年活动抽奖发放加息券失败");
				}

				break;

			default:
                resultMap.put("code","3");
                logger.info("跨年活动抽奖失败奖品id"+lotteryGift.getId());
                throw new RuntimeException("跨年活动抽奖失败");
			}

            //保存数据到抽奖记录表
            if(lotteryRecordMapper.insertSelective(formatLotteryRecord(user,lotteryGift)) <= 0){
                resultMap.put("code","3");
                logger.info("跨年活动抽奖数据插入抽奖记录失败"+lotteryGift.getId());
                throw new RuntimeException("跨年活动抽奖数据插入抽奖记录失败");
            }

            //扣减奖品数量
            LotteryGift lotteryGiftForm = new LotteryGift();
            lotteryGiftForm.setId(lotteryGift.getId());
            lotteryGiftForm.setVersion(lotteryGift.getVersion());
            lotteryGiftForm.setLeftNum(lotteryGift.getLeftNum()-1);
            if(lotteryGiftMapper.updateByPrimaryKeySelectiveAndVersion(lotteryGiftForm) <= 0){
                resultMap.put("code","3");
                logger.info("跨年活动抽奖更新奖品剩余数量失败"+lotteryGift.getId());
                throw new RuntimeException("跨年活动抽奖更新奖品剩余数量失败");
            }

            //扣减抽奖资格的抽奖次数
            if(activityQualificationMapper.updateByUserIdAndActivityIdAndVersion(
                    formatActivityQualification(user,lotteryGift,activityQualification)) <=0 ){
                resultMap.put("code","3");
                logger.info("跨年活动抽奖扣减抽奖次数失败"+lotteryGift.getId());
                throw new RuntimeException("跨年活动抽奖扣减抽奖次数失败");
            }

            resultMap.put("code","4");
            resultMap.put("prizeName",lotteryGift.getName());

        } catch (Exception e) {
        	logger.error(e);
        	resultMap.put("msg","抽奖异常");
        }
        return  resultMap;
    }

    /***
     *  查询参加活动的用户中奖次数
     * @param userId
     * @param activityId
     * @return
     */
    public Map<String, Object> selectUserLottery(Integer userId, Integer activityId,Date startTime, Date endTime) {
        return activityMapper.selectUserLottery(userId, activityId,startTime,endTime);
    }

    /***
     * 查询活动期间投资金额和年化额（包括安心赚）
     * @param userId
     * @param startTime
     * @param endTime
     * @param limitDays
     * @return
     */
    public Map<String, Object> selectInvestmentAmount(Integer userId, Date startTime, Date endTime, Integer limitDays) {
        return activityMapper.selectInvestmentAmount(userId, startTime, endTime, limitDays);
    }

    /***
     * 查询活动期间投资金额和年化额（不包括安心赚）
     * @param userId
     * @param startTime
     * @param endTime
     * @param limitDays
     * @return
     */
    public Map<String, Object> selectInvestmentAmount2(Integer userId, Date startTime, Date endTime, Integer limitDays) {
        return activityMapper.selectInvestmentAmount2(userId, startTime, endTime, limitDays);
    }

    /**
     * 根据用户id查询2017-01-01到2017-12-31的投资金额
     * @param userId 查询条件
     * @return map
     */
    @Override
    public Map<String, Object> getAvgAmount(int userId) {
        return activityMapper.getAvgAmount(userId);
    }

    /**
     * 根据条件查询跨年抽奖资格信息
     * @param userId 查询条件
     * @param activityId 查询条件
     * @return 跨年活动抽奖资格信息
     */
    @Override
    public ActivityQualification findByUserIdAndActivityId(int userId,int activityId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("activityId",activityId);
        return activityQualificationMapper.findByUserIdAndActivityId(map);
    }

    /**
     * 添加跨年抽奖资格信息
     * @param userId 添加的信息
     * @param activityId 添加的信息
     * @return int
     */
    @Override
    public int insertActivityQualification(int userId, int activityId) {
        ActivityQualification activityQualification = createActivityQualification(userId,activityId);
        return activityQualificationMapper.insertActivityQualification(activityQualification);
    }

    /**
     * 添加跨年抽奖资格信息
     * @param activityQualification 添加的信息
     * @return int
     */
    @Override
    public int updateByUserIdAndActivityIdAndVersion(ActivityQualification activityQualification) {
        return activityQualificationMapper.updateByUserIdAndActivityIdAndVersion(activityQualification);
    }

    /**
     * 查询跨年活动最近30条记录
     * @return list
     */
    @Override
    public List<Map<String, Object>> findThirtyRecord(List<Integer> activityIdList) {
        return lotteryRecordMapper.findThirtyRecord(activityIdList);
    }

    /**
     * 根据用户id查询抽奖次数
     * @param userId 用户id
     * @return int
     */
    @Override
    public int findCountByUserIdAndActivityId(int userId,List<Integer> activityIdList) {
        return lotteryRecordMapper.findCountByUserIdAndActivityId(userId,activityIdList);
    }

    /**
     * 根据用户查找跨年活动抽奖记录
     * @param userId 用户id
     * @return list
     */
    @Override
    public List<Map<String, Object>> findHappyRecord(int userId,List<Integer> activityIdList) {
        return lotteryRecordMapper.findHappyRecord(userId,activityIdList);
    }

    /**
     * 活动名称找活动id
     * @param nameList 活动名称集合
     * @return list
     */
    @Override
    public List<Activity> findActivityId(List nameList) {
        return activityMapper.findActivityId(nameList);
    }

    /**
     * 将用户信息和抽中的奖品转成抽奖记录对象
     * @param user 用户信息
     * @param lotteryGift 抽中的奖品
     * @return LotteryRecord抽奖记录
     */
    private LotteryRecord formatLotteryRecord(User user, LotteryGift lotteryGift){

        LotteryRecord lotteryRecord = new LotteryRecord();
        lotteryRecord.setUserId(user.getId());
        lotteryRecord.setPhone(user.getPhone());
        lotteryRecord.setGiftId(lotteryGift.getId());
        lotteryRecord.setCreateDate(new Date());
        lotteryRecord.setStatus(2);
        lotteryRecord.setActivityId(lotteryGift.getActivityId());
        lotteryRecord.setVersion(0);
        return lotteryRecord;
    }

    /**
     * 将用户信息和抽中的奖品转成抽奖资格表的参数对象
     * @param user 用户信息
     * @param lotteryGift 抽中的奖品
     * @return ActivityQualification
     */
    private ActivityQualification formatActivityQualification(User user, LotteryGift lotteryGift,
                                                              ActivityQualification activityQualification){

        ActivityQualification activityQualificationForm = new ActivityQualification();
        activityQualificationForm.setUserId(user.getId());
        activityQualificationForm.setActivityId(lotteryGift.getActivityId());
        activityQualificationForm.setVersion(activityQualification.getVersion());

        return activityQualificationForm;
    }

    /**
     * 将用户信息和抽中的奖品转成抽奖资格表的参数对象
     * @param userId 用户id
     * @param activityId huo
     * @return ActivityQualification
     */
    private ActivityQualification createActivityQualification(int userId, int activityId){

        ActivityQualification activityQualification = new ActivityQualification();
        activityQualification.setUserId(userId);
        activityQualification.setActivityId(activityId);
        activityQualification.setId(UUIDUtil.getUuid());
        activityQualification.setOpportunityNumber(1);
        activityQualification.setVersion(0);
        activityQualification.setIsDeleted(1);
        activityQualification.setCreateDate(new Date());
        activityQualification.setUpdateDate(new Date());
        return activityQualification;
    }

	@Override
	public Double queryHongBaoRainInviteUserCashGift(Integer userId, Date beginTime, Date endTime) {

        Double amount = 0d;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("beginTime", DateUtil.dateFullTimeFormat.format(beginTime));
        map.put("endTime", DateUtil.dateFullTimeFormat.format(endTime));

        Map<String, Object> result = userInviteMapper.sumAwardAmount(map);
        if (result != null && result.get("sum") != null) {
            amount += Double.parseDouble(result.get("sum").toString());
        }

        map.put("type", 1);
        result = hongbaoMapper.sumHongBaoAmountByOption(map);
        if (result != null && result.get("sum") != null) {
            amount += Double.parseDouble(result.get("sum").toString());
        }
        return amount;
    }

    /**
     * 查询跨年活动红包雨中奖条数
     * @return
     */
    @Override
    public int findLotteryRecordCount(Integer activityId) {
        LotteryRecordExample e = new LotteryRecordExample();
        e.createCriteria().andActivityIdEqualTo(activityId);
        return (int) lotteryRecordMapper.countByExample(e);
    }

    /**
     * 查询跨年活动红包雨记录
     * @param userId 用户id
     * @return list
     */
    @Override
    public List<Map<String, Object>> findRedRainRecord(Integer userId,Integer activityId){
        return lotteryRecordMapper.findRedRainRecord(userId,activityId);
    }

    /**
     * 我的邀请数据
     *
     * @param userId
     * @return
     */
    public Map<String, Object> getMyInvite(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> m = userInviteMapper.getInviteUserCount(userId);    // 邀请人数
        m.put("inviteAmount", userInviteMapper.getInviteUserAmount(userId));    // 总收益
        map.put("MyInvite", m);
        return map;
    }

	@Override
	public List<Map<String, Object>> getMyInviteInComePageList(Map<String, Object> searchMap) {
		return inviteUserAcitvityAwardLogMapper.getMyInviteInComePageList(searchMap);
	}

	@Override
	public List<AssistanceActivity> assistanceList(Integer userId) {
		AssistanceActivityExample example=new AssistanceActivityExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<AssistanceActivity> assistanceActivityList=assistanceActivityMapper.selectByExample(example);
		return assistanceActivityList;
	}

	@Override
	public BaseResult doAssistance(Integer activityId, Integer userId, String phone) {
		BaseResult result=new BaseResult(false);
		AssistanceActivity assistanceActivity=new AssistanceActivity();
		assistanceActivity.setFriendPhone(phone);
		assistanceActivity.setCreateDate(new Date());
		assistanceActivity.setUpdateDate(new Date());
		assistanceActivity.setUserId(userId);
		assistanceActivity.setActivityId(activityId);
		assistanceActivity.setIsDeleted(1);
		int ret=assistanceActivityMapper.insert(assistanceActivity);
		if(ret==1) {
			result.setSuccess(true);
		}
		return result;
	}

	@Override
	public boolean getAssistanceCheck(String phone,Integer activityId) {
		AssistanceActivityExample example=new AssistanceActivityExample();
		example.createCriteria().andFriendPhoneEqualTo(phone).andActivityIdEqualTo(activityId);
		List<AssistanceActivity> assistanceActivityList=assistanceActivityMapper.selectByExample(example);
		if(assistanceActivityList.size()>0) {
			return true;
		}
		return false;
	}



	@Override
	public Map<Integer,List<ActivityDetail>> getActionActivityDetailMap(){
		Date now=new Date();
		Map<Integer,List<ActivityDetail>> activityDetailListMap=new HashMap<Integer,List<ActivityDetail>>();

		for(ActivityTriggerTypeEnum atte:ActivityTriggerTypeEnum.values()) {
			activityDetailListMap.put(atte.getFeatureType(), new ArrayList<ActivityDetail>());
		}
		/*
		ActivityExample activityExample=new ActivityExample();
		//status=1 and end_time>now(),上架活动,且现在还未过期
		activityExample.createCriteria().andStatusEqualTo(1).andEndTimeGreaterThan(now);
		List<Activity> activityList= activityMapper.selectByExample(activityExample);
		*/
		ActivityDetailExample activityDetailExample=new ActivityDetailExample();
		//status=1 and (end_time>now() or end_time is null),上架活动详情,且现在还未过期
		//activityDetailExample.createCriteria().andStatusEqualTo(1).andEndTimeGreaterThan(now);
		activityDetailExample.or().andStatusEqualTo(1).andEndTimeGreaterThan(now);
		activityDetailExample.or().andStatusEqualTo(1).andEndTimeIsNull();

		List<ActivityDetail> activityDetailList= activityDetailMapper.selectByExample(activityDetailExample);
		for(ActivityDetail activityDetail:activityDetailList) {
			//存在触发器
			List<ActivityDetail> activityDetailListInMap=activityDetailListMap.get(activityDetail.getTriggerType());
			if(activityDetailListInMap!=null) {

				Activity activity= activityMapper.selectByPrimaryKey(activityDetail.getActivityId());
				//对应活动有效
				if(activity.getStatus()==1 && (activity.getEndTime() == null || activity.getEndTime().after(now))) {
					activityDetail.setActivity(activity);

					//装载赠送的红包
					ActivityDetailSendHongbaoExample activityDetailSendHongbaoExample=new ActivityDetailSendHongbaoExample();
					activityDetailSendHongbaoExample.createCriteria().andActivityDetailIdEqualTo(activityDetail.getId());
					List<ActivityDetailSendHongbao> activityDetailSendHongbaoList= activityDetailSendHongbaoMapper.selectByExample(activityDetailSendHongbaoExample);
					activityDetail.setActivityDetailSendHongbaoList(activityDetailSendHongbaoList);
					activityDetailListInMap.add(activityDetail);
				}
			}
		}

		return activityDetailListMap;
	}

	@Override
	public Map<Integer,List<ActivityDetail>> doFlushCacheActionActivityDetailMap() throws Exception{
		Map<Integer,List<ActivityDetail>>  activityDetailListMap=this.getActionActivityDetailMap();
		if(!memcachedManager.addOrReplace(Constants.ACTIVITY_DETAIL_LIST_MAP, activityDetailListMap)) {
			throw new Exception("刷新memcached.server1.host "+Constants.ACTIVITY_DETAIL_LIST_MAP+" 失败");
		}
		for(ActivityTriggerTypeEnum atte:ActivityTriggerTypeEnum.values()) {
			List<ActivityDetail> activityDetailList=activityDetailListMap.get(atte.getFeatureType());
			if(!memcachedManager.addOrReplace(Constants.ACTIVITY_DETAIL_LIST_MAP+"_"+atte.getFeatureType(), activityDetailList)) {
				throw new Exception("刷新memcached.server1.host "+Constants.ACTIVITY_DETAIL_LIST_MAP+" 失败");
			}
		}
		return activityDetailListMap;
	}

	/**
	 * 从缓存中查询活动详情,key:activityDetail.getTriggerType()|ActivityTriggerTypeEnum.getFeatureType()
	 * @author 张琼麒
	 * @version 创建时间：2019年6月19日 下午1:27:04
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<Integer,List<ActivityDetail>> doGetCacheActionActivityDetailMap() throws Exception{
		Object theObject=memcachedManager.get(Constants.ACTIVITY_DETAIL_LIST_MAP);
		if(theObject==null) {
			activityDetailMapper.lockTable();
			theObject=memcachedManager.get(Constants.ACTIVITY_DETAIL_LIST_MAP);
			if(theObject==null) {
				return this.doFlushCacheActionActivityDetailMap();
			}
		}
		@SuppressWarnings("unchecked")
		Map<Integer,List<ActivityDetail>> activityDetailListMap=(Map<Integer,List<ActivityDetail>>)theObject;
		return activityDetailListMap;
	}

	/**
	 * 从缓存中查询对应触发条件的活动详情
	 * @author 张琼麒
	 * @version 创建时间：2019年6月19日 下午1:27:04
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ActivityDetail> doGetCacheActionActivityDetailList(ActivityTriggerTypeEnum atte) throws Exception{
		Object theObject=memcachedManager.get(Constants.ACTIVITY_DETAIL_LIST_MAP+"_"+atte.getFeatureType());
		if(theObject==null) {
			return this.doGetCacheActionActivityDetailMap().get(atte.getFeatureType());
		}
		@SuppressWarnings("unchecked")
		List<ActivityDetail> activityDetailList=(List<ActivityDetail>)theObject;
		return activityDetailList;
	}

	/**
	 * 应用ActivityTriggerTypeEnum.INVESTMENT
	 * 并发送红包
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午1:04:00
	 * @param investment
	 * @throws Exception
	 */
	@Override
	public void doActivityInvestment(Investment investment) throws Exception {
		User operator=userService.get(investment.getUserId());
		if(operator.getInviteByCode()!=null) {
			operator.setInviter(userService.getByInviteCode(operator.getInviteByCode()));
		}

		List<ActivityDetail> activityDetailList=this.doGetCacheActionActivityDetailList(ActivityTriggerTypeEnum.INVESTMENT);//获得有效的投资触发的活动详情

		for(ActivityDetail activityDetail:activityDetailList) {
			int sendNum=0;
			if(activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITEE.getFeatureType()
					|| activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITER.getFeatureType()){
				if(operator.getInviter()!=null) {
					sendNum=calculateActivityForInvestment(investment,activityDetail,operator);
				}
			}else {
				sendNum=calculateActivityForInvestment(investment,activityDetail,operator);
			}

			if(sendNum>0) {
				this.sendActivity(sendNum, activityDetail,operator,investment.getId());
			}
		}
		UserExample userExample = new UserExample();
		userExample.createCriteria().andInviteByCodeEqualTo(operator.getInviteCode());
		List<User> inviteeList = userService.getUserMapper().selectByExample(userExample);
		
		//邀请首投
		List<ActivityDetail> firstInvestmentActivityDetailList=this.doGetCacheActionActivityDetailList(ActivityTriggerTypeEnum.INVITE_FIRST_INVESTMENT);//获得有效的邀请首投触发的活动详情
		for(ActivityDetail activityDetail:firstInvestmentActivityDetailList) {
			if(operator.getInviter()!=null || inviteeList.size() > 0 ) {//有邀请人 || 有被该用户邀请的用户 
				calculateActivityForFirstInvestment(investment,activityDetail,operator);
			}
		}
	}

	/**
	 * 应用ActivityTriggerTypeEnum.REGISTER
	 * 并发送红包
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午1:04:00
	 * @param investment
	 * @throws Exception
	 */
	@Override
	public void doActivityRegister(User user) throws Exception {
		User operator=user;
		if(operator.getInviteByCode()!=null) {
			operator.setInviter(userService.getByInviteCode(operator.getInviteByCode()));
		}

		List<ActivityDetail> activityDetailList=this.doGetCacheActionActivityDetailList(ActivityTriggerTypeEnum.REGISTER);

		for(ActivityDetail activityDetail:activityDetailList) {
			int sendNum=0;
			if(activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITEE.getFeatureType()
					|| activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITER.getFeatureType()){
				if(operator.getInviter()!=null) {
					sendNum=calculateActivityForRegister(activityDetail, operator);
				}
			}else {
				sendNum=calculateActivityForRegister(activityDetail, operator);
			}

			if(sendNum>0) {
				this.sendActivity(sendNum, activityDetail,operator,operator.getId());
			}

		}

	}
	
	/**
	 * 应用ActivityTriggerTypeEnum.LOGIN
	 * 并发送红包
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午1:04:00
	 * @param investment
	 * @throws Exception
	 */
	@Override
	public void doActivityLogin(User user) throws Exception {
		User operator=user;
		if(operator.getInviteByCode()!=null) {
			operator.setInviter(userService.getByInviteCode(operator.getInviteByCode()));
		}

		List<ActivityDetail> activityDetailList=this.doGetCacheActionActivityDetailList(ActivityTriggerTypeEnum.LOGIN);

		for(ActivityDetail activityDetail:activityDetailList) {
			int sendNum=0;
			if(activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITEE.getFeatureType()
					|| activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITER.getFeatureType()){
				if(operator.getInviter()!=null) {
					sendNum=calculateActivityForLogin(activityDetail, operator);
				}
			}else {
				sendNum=calculateActivityForLogin(activityDetail, operator);
			}

			if(sendNum>0) {
				this.sendActivity(sendNum, activityDetail,operator,operator.getId());
			}

		}

	}

	@Override
	public void doActivityBuyBack(Investment investment) throws Exception {
		User operator=userService.get(investment.getUserId());
		if(operator.getInviteByCode()!=null) {
			operator.setInviter(userService.getByInviteCode(operator.getInviteByCode()));
		}
		if(investment.getOrderStatus() != InvestmentStateEnum.saled.getCode()) {
			throw new Exception("牛只订单状态错误");
		}
		List<ActivityDetail> activityDetailList=this.doGetCacheActionActivityDetailList(ActivityTriggerTypeEnum.INVEST_BUY_BACK);//获得有效的投资触发的活动详情

		for(ActivityDetail activityDetail:activityDetailList) {
			int sendNum=0;
			if(activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITEE.getFeatureType()
					|| activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITER.getFeatureType()){
				if(operator.getInviter()!=null) {
					sendNum=calculateActivityForBuyBack(activityDetail,operator);
				}
			}else {
				sendNum=calculateActivityForBuyBack(activityDetail,operator);
			}

			if(sendNum>0) {
				this.sendActivity(sendNum, activityDetail,operator,investment.getId());
			}
		}
	}
	/**
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午1:29:15
	 * @param investment	当比投资
	 * @param activityDetail	活动详情
	 * @param operator		投资人
	 * @return int 活动执行数(不执行返回0,执行活动返回1,送多N次返回N)
	 * @throws Exception
	 */
	private int calculateActivityForInvestment(Investment investment,ActivityDetail activityDetail,User operator) throws Exception {
		if(!validActivityDetail(activityDetail,operator)) {
			return 0;
		}
		//当笔增加值
		BigDecimal currentAddValue=null;
		if(activityDetail.getConditionValueType()==1 //统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
				|| activityDetail.getConditionValueType()==3) {
			currentAddValue=BigDecimal.valueOf(investment.getAmount());
		}else if(activityDetail.getConditionValueType()==2) {//统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
			currentAddValue=BigDecimal.ONE;
		}else if(activityDetail.getConditionValueType()==4) {//统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
			currentAddValue=null;//是否首投必须,正对操作人查询订单数确定
		}
		//周期性预计算
		//如果是周期性活动,当期增加值正好是周期值得整倍数,不需要统计,直接返回倍数,即为用户可以享受的活动
		//(循环发放,且本次增加的值是周期的整倍数)
		if( activityDetail.getCycleVertices().compareTo(BigDecimal.ZERO)>0
				&& currentAddValue !=null
				&& currentAddValue.remainder(activityDetail.getCycleVertices()).compareTo(BigDecimal.ZERO)==0 ) {
			return currentAddValue.divide(activityDetail.getCycleVertices()).intValue();
		}

		BigDecimal totalValue=BigDecimal.ZERO;
		if(activityDetail.getConditionValueType()==1) {//统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
			totalValue=BigDecimal.valueOf(investment.getAmount());
		}else {//activityDetail.getConditionValueType()==1 不需统计累计值,2|3需要统计累计值
			//统计开始
			InvestmentViewExample example=new InvestmentViewExample();
			com.goochou.p2b.model.InvestmentViewExample.Criteria criteria=example.createCriteria();

			//统计状态
			criteria.andOrderStatusIn(new ArrayList() {{add(InvestmentStateEnum.buyed.getCode());
					add(InvestmentStateEnum.saled.getCode());}});

			//统计目标:1.操作人 2.被邀请人(有邀请人的操作人) 3.邀请人
			if(activityDetail.getConditionTarget()==ActivityTargetTypeEnum.OPERATOR.getFeatureType()
					||activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITEE.getFeatureType()) {
				criteria.andUserIdEqualTo(operator.getId());
			}else if(activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITER.getFeatureType()) {
				criteria.andInviterUserIdEqualTo(operator.getInviter().getId());
			}

			//统计时间范围
			if(activityDetail.getConditionDateType()==1) {//累计时间范围:1.全局 2.活动期间 3.活动详情期间(分段活动)
				//do nothing
			}else if (activityDetail.getConditionDateType()==2) {//累计时间范围:1.全局 2.活动期间 3.活动详情期间(分段活动)
				if(activityDetail.getActivity().getStartTime()!=null) {
					criteria.andDeadlineGreaterThanOrEqualTo(activityDetail.getActivity().getStartTime());
				}
				if(activityDetail.getActivity().getEndTime()!=null) {
					criteria.andDeadlineLessThan(activityDetail.getActivity().getEndTime());
				}
			}else if (activityDetail.getConditionDateType()==3) {//累计时间范围:1.全局 2.活动期间 3.活动详情期间(分段活动)
				if(activityDetail.getStartTime()!=null) {
					criteria.andDeadlineGreaterThanOrEqualTo(activityDetail.getStartTime());
				}
				if(activityDetail.getEndTime()!=null) {
					criteria.andDeadlineLessThan(activityDetail.getEndTime());
				}
			}

			//统计值
			if(activityDetail.getConditionValueType()==2) {//统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
				totalValue=BigDecimal.valueOf(investmentViewMapper.countByExample(example));
			}else if(activityDetail.getConditionValueType()==3){
				totalValue=BigDecimal.valueOf(investmentViewMapper.sumAmountByExample(example));
			}else if(activityDetail.getConditionValueType()==4){//4.首投
				//总下单用户数(如果[统计目标]非[邀请人],则查[操作者]是否投资,如果[统计目标]为[邀请人],则查[邀请认]邀请的[投资]用户数)
				totalValue=BigDecimal.valueOf(investmentViewMapper.countUserByExample(example));

				//统计[操作者]的[有效投资数]
				criteria.andUserIdEqualTo(operator.getId());
				currentAddValue=BigDecimal.valueOf(investmentViewMapper.countByExample(example));
				Long userOrderNum=investmentViewMapper.countByExample(example);
				if( userOrderNum==1L ) {//[操作者]的[有效投资数]是1,说明是首投
					currentAddValue=BigDecimal.ONE;
				}else {//[操作者]的[有效投资数]是1,说明不是首投
					//当前触发没有增加值
					currentAddValue=BigDecimal.ZERO;
				}
			}
		}

		return calculateSendNum(currentAddValue,totalValue,activityDetail);
	}
	
	/**
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午1:29:15
	 * @param investment	当比投资
	 * @param activityDetail	活动详情
	 * @param operator		投资人
	 * @return int 活动执行数(不执行返回0,执行活动返回1,送多N次返回N)
	 * @throws Exception
	 */
	private void calculateActivityForFirstInvestment(Investment investment,ActivityDetail activityDetail,User operator) throws Exception {
		if(!validActivityDetail(activityDetail,operator)) {//验证活动是否有效
			return;
		}
		//当笔增加值
		BigDecimal currentAddValue=null;
		if(activityDetail.getConditionValueType()!=4) {//统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
			return;
		}
		{
			//验证当前操作人的投资是首投------------------------------------------
			InvestmentViewExample example=new InvestmentViewExample();
			com.goochou.p2b.model.InvestmentViewExample.Criteria criteria=example.createCriteria();
			//统计状态
			criteria.andOrderStatusIn(new ArrayList() {{add(InvestmentStateEnum.buyed.getCode());
					add(InvestmentStateEnum.saled.getCode());}});
			criteria.andUserIdEqualTo(operator.getId());
			currentAddValue=BigDecimal.valueOf(investmentViewMapper.countByExample(example));
			if(!currentAddValue.equals(BigDecimal.ONE)) {
				return;//有效投资数不为1,说明不是首投
			}
		}
		if(operator.getInviter()!=null)	{
			//验证当前操作人的邀请人有投资------------------------------------------
			InvestmentViewExample example=new InvestmentViewExample();
			com.goochou.p2b.model.InvestmentViewExample.Criteria criteria=example.createCriteria();
			//统计状态
			criteria.andOrderStatusIn(new ArrayList() {{add(InvestmentStateEnum.buyed.getCode());
					add(InvestmentStateEnum.saled.getCode());}});
			criteria.andUserIdEqualTo(operator.getInviter().getId());
			currentAddValue=BigDecimal.valueOf(investmentViewMapper.countByExample(example));
			if(currentAddValue.compareTo(BigDecimal.ZERO)>0) {//该用户的邀请人有投资
				//双送
				this.sendActivity(1,activityDetail,operator,investment.getId());
			}
		}
		//验证当前操作人的被邀请人有首投,且首投在活动期内支付成功------------------------------------------
		//查询当前操作人的被邀请人列表
		if(operator.getInviteCode()==null) {
			return;
		}
		UserExample userExample = new UserExample();
		userExample.createCriteria().andInviteByCodeEqualTo(operator.getInviteCode());
		List<User> inviteeList = userService.getUserMapper().selectByExample(userExample);

		for(User user:inviteeList) {
			InvestmentViewExample example=new InvestmentViewExample();
			//按payTime(支付成功时间)排序被邀请认的投资单
			example.setOrderByClause("pay_time");
			com.goochou.p2b.model.InvestmentViewExample.Criteria criteria=example.createCriteria();
			//统计状态
			criteria.andOrderStatusIn(new ArrayList() {{add(InvestmentStateEnum.buyed.getCode());
					add(InvestmentStateEnum.saled.getCode());}});
			criteria.andUserIdEqualTo(user.getId());
			List<InvestmentView>  investmentViewList=investmentViewMapper.selectByExample(example);
			if(investmentViewList.size()==0) {
				continue;
			}
			//有首投,拿首投
			InvestmentView investmentView=investmentViewList.get(0);
			//首投的时间,在活动期内
			if(investmentView.getPayTime() != null 
				&& !investmentView.getPayTime().before(activityDetail.getActivity().getStartTime())
				&& investmentView.getPayTime().before(activityDetail.getActivity().getEndTime()) ){
				user.setInviter(operator);
				this.sendActivity(1,activityDetail,user,investmentView.getId());
			}
		}
	}
	
	/**
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午1:29:15
	 * @param investment	当比投资
	 * @param activityDetail	活动详情
	 * @param operator		投资人
	 * @return int 活动执行数(不执行返回0,执行活动返回1,送多N次返回N)
	 * @throws Exception
	 */
	private int calculateActivityForRegister(ActivityDetail activityDetail,User operator) throws Exception {
		if(!validActivityDetail(activityDetail,operator)) {
			return 0;
		}
		//当笔增加值
		BigDecimal currentAddValue=null;
		if(activityDetail.getConditionValueType()==1 //统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
				|| activityDetail.getConditionValueType()==3
				|| activityDetail.getConditionValueType()==2 ) {
			currentAddValue=BigDecimal.ONE;
		}else if(activityDetail.getConditionValueType()==4) {//统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
			currentAddValue=null;//是否首投必须,正对操作人查询订单数确定
		}
		//周期性预计算
		//如果是周期性活动,当期增加值正好是周期值得整倍数,不需要统计,直接返回倍数,即为用户可以享受的活动
		//(循环发放,且本次增加的值是周期的整倍数)
		if( activityDetail.getCycleVertices().compareTo(BigDecimal.ZERO)>0
				&& currentAddValue !=null
				&& currentAddValue.remainder(activityDetail.getCycleVertices()).compareTo(BigDecimal.ZERO)==0 ) {
			return currentAddValue.divide(activityDetail.getCycleVertices()).intValue();
		}

		BigDecimal totalValue=BigDecimal.ZERO;
		if(activityDetail.getConditionValueType()==1) {//统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
			totalValue=BigDecimal.ONE;
		}else {//activityDetail.getConditionValueType()==1 不需统计累计值,2|3需要统计累计值
			//统计开始
			UserExample example=new UserExample();
			com.goochou.p2b.model.UserExample.Criteria criteria=example.createCriteria();


			//统计目标:1.操作人 2.被邀请人(有邀请人的操作人) 3.邀请人
			if(activityDetail.getConditionTarget()==ActivityTargetTypeEnum.OPERATOR.getFeatureType()
					||activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITEE.getFeatureType()) {
				criteria.andIdEqualTo(operator.getId());
			}else if(activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITER.getFeatureType()) {
				criteria.andInviteByCodeEqualTo(operator.getInviter().getInviteCode());
			}

			//统计时间范围
			if(activityDetail.getConditionDateType()==1) {//累计时间范围:1.全局 2.活动期间 3.活动详情期间(分段活动)
				//do nothing
			}else if (activityDetail.getConditionDateType()==2) {//累计时间范围:1.全局 2.活动期间 3.活动详情期间(分段活动)
				if(activityDetail.getActivity().getStartTime()!=null) {
					criteria.andCreateDateGreaterThanOrEqualTo(activityDetail.getActivity().getStartTime());
				}
				if(activityDetail.getActivity().getEndTime()!=null) {
					criteria.andCreateDateLessThan(activityDetail.getActivity().getEndTime());
				}
			}else if (activityDetail.getConditionDateType()==3) {//累计时间范围:1.全局 2.活动期间 3.活动详情期间(分段活动)
				if(activityDetail.getStartTime()!=null) {
					criteria.andCreateDateGreaterThanOrEqualTo(activityDetail.getStartTime());
				}
				if(activityDetail.getEndTime()!=null) {
					criteria.andCreateDateLessThan(activityDetail.getEndTime());
				}
			}

			//统计值
			if(activityDetail.getConditionValueType()==2
					||activityDetail.getConditionValueType()==3
					||activityDetail.getConditionValueType()==4) {//统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
				totalValue=BigDecimal.valueOf(userService.getUserMapper().countByExample(example));
			}
		}

		return calculateSendNum(currentAddValue,totalValue,activityDetail);
	}
	/**
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午1:29:15
	 * @param investment	当比投资
	 * @param activityDetail	活动详情
	 * @param operator		投资人
	 * @return int 活动执行数(不执行返回0,执行活动返回1,送多N次返回N)
	 * @throws Exception
	 */
	private int calculateActivityForLogin(ActivityDetail activityDetail,User operator) throws Exception {
		if(!validActivityDetail(activityDetail,operator)) {
			return 0;
		}
		//当笔增加值
		BigDecimal currentAddValue=null;
		if( activityDetail.getConditionValueType()==1 //统计数据内容:1.当比金额 2.累计订单数量|注册数|登录数 3.累计金额 4.首投|首登
				|| activityDetail.getConditionValueType()==3
				|| activityDetail.getConditionValueType()==2 ) {
			currentAddValue=BigDecimal.ONE;
		}else if(activityDetail.getConditionValueType()==4) {//统计数据内容:1.当比金额 2.累计订单数量|注册数|登录数  3.累计金额 4.首投|首登
			currentAddValue=null;//是否首投必须,正对操作人查询订单数确定
		}
		//周期性预计算
		//如果是周期性活动,当期增加值正好是周期值得整倍数,不需要统计,直接返回倍数,即为用户可以享受的活动
		//(循环发放,且周期=阈值,且本次增加的值是周期的整倍数)
		if( activityDetail.getCycleVertices().compareTo(BigDecimal.ZERO)>0
				&& activityDetail.getThresholdValue().compareTo(activityDetail.getCycleVertices())==0
				&& currentAddValue !=null
				&& currentAddValue.remainder(activityDetail.getCycleVertices()).compareTo(BigDecimal.ZERO)==0 ) {
			return currentAddValue.divide(activityDetail.getCycleVertices()).intValue();
		}

		BigDecimal totalValue=BigDecimal.ZERO;
		if(activityDetail.getConditionValueType()==1) {//统计数据内容:1.当比金额 2.累计订单数量|注册数|登录数 3.累计金额 4.首投|首登
			totalValue=BigDecimal.ONE;
		}else {//activityDetail.getConditionValueType()==1 不需统计累计值,2|3需要统计累计值
			//统计开始
			LoginRecordInviteViewExample example=new LoginRecordInviteViewExample();
			com.goochou.p2b.model.LoginRecordInviteViewExample.Criteria criteria=example.createCriteria();


			//统计目标:1.操作人 2.被邀请人(有邀请人的操作人) 3.邀请人
			if(activityDetail.getConditionTarget()==ActivityTargetTypeEnum.OPERATOR.getFeatureType()
					||activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITEE.getFeatureType()) {
				criteria.andUserIdEqualTo(operator.getId());
			}else if(activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITER.getFeatureType()) {
				criteria.andInviterUserIdEqualTo(operator.getInviter().getId());
			}

			//统计时间范围
			if(activityDetail.getConditionDateType()==1) {//累计时间范围:1.全局 2.活动期间 3.活动详情期间(分段活动)
				//do nothing
			}else if (activityDetail.getConditionDateType()==2) {//累计时间范围:1.全局 2.活动期间 3.活动详情期间(分段活动)
				if(activityDetail.getActivity().getStartTime()!=null) {
					criteria.andCreateDateGreaterThanOrEqualTo(activityDetail.getActivity().getStartTime());
				}
				if(activityDetail.getActivity().getEndTime()!=null) {
					criteria.andCreateDateLessThan(activityDetail.getActivity().getEndTime());
				}
			}else if (activityDetail.getConditionDateType()==3) {//累计时间范围:1.全局 2.活动期间 3.活动详情期间(分段活动)
				if(activityDetail.getStartTime()!=null) {
					criteria.andCreateDateGreaterThanOrEqualTo(activityDetail.getStartTime());
				}
				if(activityDetail.getEndTime()!=null) {
					criteria.andCreateDateLessThan(activityDetail.getEndTime());
				}
			}

			//统计值
			if( activityDetail.getConditionValueType()==2
					||activityDetail.getConditionValueType()==3 ) {
				totalValue=BigDecimal.valueOf(loginRecordInviteViewMapper.countByExample(example));
			}else if( activityDetail.getConditionValueType()==4 ) {//统计数据内容:1.当比金额 2.累计订单数量|注册数|登录数 3.累计金额 4.首投|首登
				//总登录用户数(如果[统计目标]非[邀请人],则查[操作者]是否登陆过,如果[统计目标]为[邀请人],则查[邀请认]邀请的[登录]用户数)
				totalValue=BigDecimal.valueOf(loginRecordInviteViewMapper.countUserByExample(example));
				
				criteria.andUserIdEqualTo(operator.getId());
				//统计[操作者]的[登录次数]
				currentAddValue=BigDecimal.valueOf(loginRecordInviteViewMapper.countByExample(example));
				Long userOrderNum=loginRecordInviteViewMapper.countByExample(example);
				if( userOrderNum==1L ) {//[当前用户]的[登录次数]是1,说明是首登
					currentAddValue=BigDecimal.ONE;
				}else {///[当前用户]的[登录次数]不是1,则不是首登
					//当前触发没有增加值
					currentAddValue=BigDecimal.ZERO;
				}
			}
		}

		return calculateSendNum(currentAddValue,totalValue,activityDetail);
	}
	
	/**
	 * @desc 回购发送红包
	 * activityDetail.getConditionValueType()==1 
	 * @author wangyun
	 * @param activityDetail
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	private int calculateActivityForBuyBack(ActivityDetail activityDetail,User operator) throws Exception {
		if(!validActivityDetail(activityDetail,operator)) {
			return 0;
		}
		//当笔增加值
		BigDecimal currentAddValue=null;
		if(activityDetail.getConditionValueType()==1 //统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
				|| activityDetail.getConditionValueType()==3
				|| activityDetail.getConditionValueType()==2 ) {
			currentAddValue=BigDecimal.ONE;
		} else if(activityDetail.getConditionValueType()==4) {//统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
			currentAddValue=null;//是否首投必须,正对操作人查询订单数确定
		}
		//周期性预计算
		//如果是周期性活动,当期增加值正好是周期值得整倍数,不需要统计,直接返回倍数,即为用户可以享受的活动
		//(循环发放,且本次增加的值是周期的整倍数)
		if( activityDetail.getCycleVertices().compareTo(BigDecimal.ZERO)>0
				&& currentAddValue !=null
				&& currentAddValue.remainder(activityDetail.getCycleVertices()).compareTo(BigDecimal.ZERO)==0 ) {
			return currentAddValue.divide(activityDetail.getCycleVertices()).intValue();
		}

		BigDecimal totalValue=BigDecimal.ZERO;
		if(activityDetail.getConditionValueType()==1) {//统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投
			totalValue=BigDecimal.ONE;
		} else {//activityDetail.getConditionValueType()==1 不需统计累计值,2|3需要统计累计值
			throw new Exception("统计数据内容错误!");
		}

		return calculateSendNum(currentAddValue,totalValue,activityDetail);
	}
	
	
	/**
	 * 通过新增量和总量,计算当前活动的执行量
	 * @author 张琼麒
	 * @version 创建时间：2019年6月21日 下午4:10:25
	 * @param currentAddValue
	 * @param totalValue
	 * @param activityDetail
	 * @return
	 */
	private int calculateSendNum(BigDecimal currentAddValue,BigDecimal totalValue,ActivityDetail activityDetail) {
		//当期未增加值
		if(currentAddValue.compareTo(BigDecimal.ZERO)==0) {
			return 0;
		}
		//上次的累计值
		BigDecimal lastTotalValue=totalValue.subtract(currentAddValue);
		//不循环
		if(activityDetail.getCycleVertices().compareTo(BigDecimal.ZERO)==0 ) {
			if(lastTotalValue.compareTo(activityDetail.getThresholdValue())<0
					&& totalValue.compareTo(activityDetail.getThresholdValue())>=0 ) {
				return 1;
			}else {
				return 0;
			}
		}else {//循环
			//当次的累计赠送数
			int currentAccumulativeNum=totalValue.divide(activityDetail.getCycleVertices(),0,RoundingMode.DOWN).intValue();
			if(totalValue.remainder(activityDetail.getCycleVertices()).compareTo(activityDetail.getThresholdValue())>=0) {
				currentAccumulativeNum++;
			}

			//上次的累计赠送数
			int lastAccumulativeNum=lastTotalValue.divide(activityDetail.getCycleVertices(),0,RoundingMode.DOWN).intValue();
			if(lastTotalValue.remainder(activityDetail.getCycleVertices()).compareTo(activityDetail.getThresholdValue())>=0) {
				lastAccumulativeNum++;
			}
			return currentAccumulativeNum-lastAccumulativeNum;
		}
	}

	/**
	 * 验证活动有效性
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午3:00:12
	 * @param activityDetail
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	private boolean validActivityDetail(ActivityDetail activityDetail,User operator) throws Exception {
		Date now=new Date();
		//活动统计不是操作人(即被邀请认或邀请认),操作人必须要有邀请认
		if(operator.getInviter()==null && activityDetail.getConditionTarget()!=ActivityTargetTypeEnum.OPERATOR.getFeatureType() ) {
			throw new Exception("该活动是邀请活动,没有邀请人");
		}
		//活动详情失效
		if(activityDetail.getStatus()!=1) {//0不执行 1执行
			return false;
		}
		if(activityDetail.getStartTime()!=null && activityDetail.getStartTime().compareTo(now)>0) {
			return false;
		}
		if(activityDetail.getEndTime()!=null && activityDetail.getEndTime().compareTo(now)<=0) {
			return false;
		}
		//活动失效
		if(activityDetail.getActivity().getStatus()!=1) {//0草稿 1上架
			return false;
		}
		if(activityDetail.getActivity().getStartTime()!=null && activityDetail.getActivity().getStartTime().compareTo(now)>0) {
			return false;
		}
		if(activityDetail.getActivity().getEndTime()!=null &&  activityDetail.getActivity().getEndTime().compareTo(now)<=0) {
			return false;
		}
		//统计是否兼容
		//activityDetail.getConditionValueType()统计内容:1.当比金额 2.累计订单数量 3.累计金额
		//统计当比金额,时无法针对推荐人统计
		if(activityDetail.getThresholdValue().compareTo(BigDecimal.ZERO)<=0) {
			throw new Exception("活动阈值不能小于等于0");
		}
		/*
		//数据限制了unsigned
		if(activityDetail.getCycleVertices().compareTo(BigDecimal.ZERO)<=0) {
			throw new Exception("活动周期不能小于0");
		}
		*/
		//循环活动,阈值不能大于周期
		if(activityDetail.getCycleVertices().compareTo(BigDecimal.ZERO)>0 && activityDetail.getThresholdValue().compareTo(activityDetail.getCycleVertices())>0) {
			throw new Exception("活动阈值不能大于活动周期");
		}
		if(activityDetail.getConditionValueType()==1
				&& activityDetail.getConditionTarget()==ActivityTargetTypeEnum.INVITER.getFeatureType()) {
			throw new Exception("统计当比金额,时无法针对推荐人统计");
		}
		if(!isAccomplishActivityDetailType(activityDetail)) {
			throw new Exception("活动类型未实现");
		}

		return true;
	}

	/**
	 * 判断活动类型是否已实现
	 * @author 张琼麒
	 * @version 创建时间：2019年6月20日 下午2:45:11
	 * @return
	 */
	private boolean isAccomplishActivityDetailType(ActivityDetail activityDetail) {
		//注册触发已完成
		if(activityDetail.getTriggerType()==ActivityTriggerTypeEnum.REGISTER.getFeatureType() ) {
			return true;
		}
		//投资触发已完成
		if(activityDetail.getTriggerType()==ActivityTriggerTypeEnum.INVESTMENT.getFeatureType()) {
			return true;
		}
		//登录触发已完成
		if(activityDetail.getTriggerType()==ActivityTriggerTypeEnum.LOGIN.getFeatureType()) {
			return true;
		}
		//邀请首投
		if(activityDetail.getTriggerType()==ActivityTriggerTypeEnum.INVITE_FIRST_INVESTMENT.getFeatureType()) {
			return true;
		}
		//投资卖牛回购
		if(activityDetail.getTriggerType()==ActivityTriggerTypeEnum.INVEST_BUY_BACK.getFeatureType()) {
			return true;
		}
		return false;
	}

	private void sendActivity(int sendNum,ActivityDetail activityDetail,User operator,Integer otherId) throws Exception {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
	    List<ActivityDetailSendHongbao> sendHongbaoList = activityDetail.getActivityDetailSendHongbaoList();

	    for(ActivityDetailSendHongbao sendHongbao : sendHongbaoList) {
	    	User sendUser=null;
            Hongbao hongbao = new Hongbao();
            if(sendHongbao.getTargetType() == ActivityTargetTypeEnum.OPERATOR.getFeatureType()) {
            	sendUser=operator;
            } else if(sendHongbao.getTargetType() == ActivityTargetTypeEnum.INVITER.getFeatureType()) {
            	sendUser=operator.getInviter();
            } else {
            	throw new Exception("错误的红包发放对象");
            }
            hongbao.setUserId(sendUser.getId());
            hongbao.setType(sendHongbao.getType());
            //普通金额
            if(sendHongbao.getAmount().equals(sendHongbao.getRandomAmount())) {
                hongbao.setAmount(sendHongbao.getAmount().doubleValue()); //金额
            } else {//随机金额
                //hongbaoAmount=(random_amount-amount)*随机数+amount
                double hongbaoAmount = sendHongbao.getRandomAmount().subtract(sendHongbao.getAmount()).multiply(BigDecimal.valueOf(Math.random())).add(sendHongbao.getAmount()).doubleValue();
                hongbao.setAmount(hongbaoAmount);
            }
            hongbao.setLimitAmount(sendHongbao.getLimitAmount());
            hongbao.setLimitDay(sendHongbao.getLimitDay());
            hongbao.setSource(activityDetail.getActivity().getId()); //来源
            hongbao.setDescript(activityDetail.getName());
            //起止日期
            if(sendHongbao.getStartTime() != null && sendHongbao.getEndTime() != null) {
                hongbao.setSendTime(sendHongbao.getStartTime());
                hongbao.setExpireTime(sendHongbao.getEndTime());
            } else {
            	//有效期限
                hongbao.setSendTime(new Date());
                Date expTime = DateFormatTools.jumpOneDay(new Date(), sendHongbao.getEffectiveDays());
                hongbao.setExpireTime(DateFormatTools.strToDate(sdf.format(expTime)));
            }
            hongbao.setActivityDetailId(activityDetail.getId());
            hongbao.setTriggerType(activityDetail.getTriggerType());
            hongbao.setOtherId(otherId);

			for (int i = 0; i < sendNum * sendHongbao.getCnt(); i++) {
				hongbao.setId(null);
				hongbaoMapper.insert(hongbao);

				if (activityDetail.getActivity().getId().equals(111)) {
					String messageContent = DictConstants.INVITE_FIRST_INVESTMENT_SUCCEED_CODE;
					messageContent = messageContent.replace("{url}",
							ClientConstants.APP_ROOT + "/i?i=" + sendUser.getId());
					// 发送短信
					smsSendService.addSmsSend(sendUser.getPhone(), messageContent, null, new Date());
					
				} 
			}
        }
	}

	@Override
	public List<User> getFirstInvestmentWaitInviter() throws Exception {
		return userService.getUserMapper().getFirstInvestmentWaitInviter();
	}

	@Override
	public List<User> getFirstInvestmentWaitInvitee() throws Exception {
		return userService.getUserMapper().getFirstInvestmentWaitInvitee();
	}

}
