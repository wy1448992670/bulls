package com.goochou.p2b.service;

import com.goochou.p2b.model.WithdrawCoupon;

import java.util.List;

/**
 * Created by qiutianjia on 2017/8/30.
 */
public interface WithdrawCouponService {
    /**
     * 发放提现券
     *
     * @param userId
     * @param title      标题
     * @param descript   描述
     * @param source     来源
     * @param activityId 活动ID
     * @param limitDays  有效时间
     * @return
     */
    public WithdrawCoupon addToUser(Integer userId, String title, String descript, Integer source, Integer activityId, Integer limitDays);

    public void updateByPrimaryKey(WithdrawCoupon withdrawCoupon);

    public WithdrawCoupon get(Integer id);

    public List<WithdrawCoupon> getByUserId(Integer userId);

    public WithdrawCoupon getByWithdrawId(Integer withdrawId);

    /**
     * 提现券列表
     *
     * @param userId
     * @param start
     * @param limit
     * @return
     */
    public List<WithdrawCoupon> getWithdrawCouponList(Integer userId, Integer start, Integer limit);

    public Integer getWithdrawCouponCount(Integer userId);

    public int updateWithdrawCouponExpire();
}
