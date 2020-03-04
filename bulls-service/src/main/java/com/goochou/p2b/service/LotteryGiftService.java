package com.goochou.p2b.service;

import com.goochou.p2b.model.LotteryGift;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author 刘源
 * @date 2016/4/21
 */
public interface LotteryGiftService {

    /**
     * 获取活动对应的奖品
     *
     * @param
     * @author 刘源
     * @date 2016/4/21
     */
    List<LotteryGift> getActivityGiftItems(Integer activityId);

    /**
     * 获取已获得的奖品
     *
     * @param userId
     * @param activityId
     * @return
     */
    List<Map<String, Object>> queryLotteryRecord(Integer userId, Integer activityId);

    /**
     * 获取全民理财福布斯领奖活动可领奖品列表
     *
     * @param token
     * @author 刘源
     * @date 2016/5/6
     */
    Map<String, Object> queryAwardGiftList(String token) throws ParseException;


}
