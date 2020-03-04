package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.ActivityConstant;
import com.goochou.p2b.dao.ActivityMapper;
import com.goochou.p2b.dao.InvestmentMapper;
import com.goochou.p2b.dao.LotteryGiftMapper;
import com.goochou.p2b.dao.LotteryRecordMapper;
import com.goochou.p2b.dao.UserMapper;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.LotteryGift;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.LotteryGiftService;
import com.goochou.p2b.utils.DateFormatTools;

/**
 * LotteryGiftServiceImpl
 *
 * @author 刘源
 * @date 2016/4/21
 */
@Service
public class LotteryGiftServiceImpl implements LotteryGiftService {
    @Resource
    private LotteryGiftMapper lotteryGiftMapper;
    @Resource
    private InvestmentMapper investmentMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private LotteryRecordMapper lotteryRecordMapper;

    @Override
    public List<LotteryGift> getActivityGiftItems(Integer activityId) {
        return lotteryGiftMapper.getActivityGiftItems(activityId);
    }

    @Override
    public List<Map<String, Object>> queryLotteryRecord(Integer userId, Integer activityId) {
        return lotteryRecordMapper.queryLotteryRecord(userId, activityId);
    }

    @Override
    public Map<String, Object> queryAwardGiftList(String token) {
        Map<String, Object> result = new HashMap<>();
        String activityName = ActivityConstant.REGULAR_INVESTMENT_AWARD_GIFT;
        Activity activity = activityMapper.queryByName(activityName);
        Date now = new Date();
        User user = userMapper.checkLogin(token);
        Date endTime = DateFormatTools.jumpOneDay(activity.getEndTime(), 1);
        if (user == null) {
            result.put("code", "2");
            result.put("msg", "您未登录");
            result.put("list", null);
            return result;
        }
        if (activity == null) {
            result.put("code", "0");
            result.put("msg", "活动不存在");
            return result;
        } else {
            if (now.before(endTime) && now.after(activity.getStartTime())) {
                Map<String, Object> params = new HashMap<>();
                params.put("userId", user.getId());
                params.put("startTime", activity.getStartTime());
                params.put("endTime", endTime);
                Map<String, Object> investmentMap = investmentMapper.querySumRegularInvestmentOnActivityDay(params);
                Double sumAmount = Double.valueOf(investmentMap.get("sumAmount").toString());
                final String NORMAL_GRADE = "normalGrade";
                final String HIGH_GRADE = "highGrade";
                final String FORBES_GRADE = "forbesGrade";
                if (sumAmount >= 3000 && sumAmount < 30000) {
                    params.put("grade", NORMAL_GRADE);
                    List<LotteryGift> normalList = lotteryGiftMapper.queryGiftOnActivity(params);
                    result.put(NORMAL_GRADE, normalList);

                    result.put(HIGH_GRADE, null);
                    result.put(FORBES_GRADE, null);
                } else if (sumAmount >= 30000 && sumAmount < 60000) {
                    params.put("grade", NORMAL_GRADE);
                    List<LotteryGift> normalList = lotteryGiftMapper.queryGiftOnActivity(params);
                    result.put(NORMAL_GRADE, normalList);

                    params.put("grade", HIGH_GRADE);
                    List<LotteryGift> highList = lotteryGiftMapper.queryGiftOnActivity(params);
                    result.put(HIGH_GRADE, highList);

                    result.put("forbesGrade", null);
                } else if (sumAmount >= 60000) {
                    params.put("grade", NORMAL_GRADE);
                    List<LotteryGift> normalList = lotteryGiftMapper.queryGiftOnActivity(params);
                    result.put(NORMAL_GRADE, normalList);

                    params.put("grade", HIGH_GRADE);
                    List<LotteryGift> highList = lotteryGiftMapper.queryGiftOnActivity(params);
                    result.put(HIGH_GRADE, highList);

                    params.put("grade", FORBES_GRADE);
                    List<LotteryGift> forbesList = lotteryGiftMapper.queryGiftOnActivity(params);
                    result.put(FORBES_GRADE, forbesList);
                } else {
                    result.put(NORMAL_GRADE, null);
                    result.put(HIGH_GRADE, null);
                    result.put(FORBES_GRADE, null);
                }
                params.put("amount", sumAmount);
            } else {
                result.put("code", "0");
                result.put("msg", "不在活动期间内，请留意全民理财活动公告");
                return result;
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", activity.getStartTime());
        params.put("endTime", endTime);
        //该处只有定期
        params.put("type", 0);
        /*
        List<Map<String, Object>> rankList = investmentMapper.queryInvestmentRankOnActivity(params);
        for(Map<String, Object> rank : rankList){
            String username = rank.get("username").toString();
            username = username.substring(0,1)+"***"+username.substring(username.length()-1);
            rank.put("username",username);
        }*/
        params.put("userId", user.getId());
        Map<String, Object> userRank = investmentMapper.queryUserRankOnActivity(params);
        result.put("code", "1");
        result.put("msg", "获取领奖奖品列表成功");
//        result.put("rank", rankList);
        result.put("userRank", userRank);
//        result.put("allList",allList);
        return result;
    }

}
