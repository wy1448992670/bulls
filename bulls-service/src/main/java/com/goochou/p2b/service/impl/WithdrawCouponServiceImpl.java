package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.WithdrawCouponMapper;
import com.goochou.p2b.model.WithdrawCoupon;
import com.goochou.p2b.model.WithdrawCouponExample;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.WithdrawCouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by qiutianjia on 2017/8/30.
 */
@Service
public class WithdrawCouponServiceImpl implements WithdrawCouponService {
    @Resource
    private WithdrawCouponMapper withdrawCouponMapper;
    @Resource
    private MessageService messageService;

    public WithdrawCoupon addToUser(Integer userId, String title, String descript, Integer source, Integer activityId, Integer limitDays) {
        Date now = new Date();
        WithdrawCoupon wc = new WithdrawCoupon();
        wc.setUserId(userId);
        wc.setTitle(title);
        wc.setDescript(descript);
        wc.setSource(source);
        wc.setActivityId(activityId);
        wc.setCreateDate(now);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.get(Calendar.DATE) + (limitDays == null ? 30 : limitDays));
        wc.setExpireTime(c.getTime());
        wc.setStatus(0);
        int ret = withdrawCouponMapper.insert(wc);
        if (ret == 1) {
            // 发送消息
            String title1 = "恭喜您获得" + title;
            messageService.save(title1, descript, userId);
        }
        return wc;
    }

    @Override
    public WithdrawCoupon get(Integer id) {
        return withdrawCouponMapper.selectByPrimaryKey(id);
    }

    public List<WithdrawCoupon> getByUserId(Integer userId) {
        WithdrawCouponExample example = new WithdrawCouponExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(0);
        List<WithdrawCoupon> list = withdrawCouponMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list;
        }
        return null;
    }

    public WithdrawCoupon getByWithdrawId(Integer withdrawId) {
        WithdrawCouponExample example = new WithdrawCouponExample();
        example.createCriteria().andWithdrawIdEqualTo(withdrawId);
        List<WithdrawCoupon> list = withdrawCouponMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<WithdrawCoupon> getWithdrawCouponList(Integer userId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return withdrawCouponMapper.getWithdrawCouponList(map);
    }

    public Integer getWithdrawCouponCount(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return withdrawCouponMapper.getWithdrawCouponCount(map);
    }

    @Override
    public int updateWithdrawCouponExpire() {
        return withdrawCouponMapper.updateWithdrawCouponExpire();
    }

    @Override
    public void updateByPrimaryKey(WithdrawCoupon withdrawCoupon) {
        withdrawCouponMapper.updateByPrimaryKey(withdrawCoupon);
    }
}
