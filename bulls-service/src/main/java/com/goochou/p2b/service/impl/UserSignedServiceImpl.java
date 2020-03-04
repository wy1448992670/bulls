package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import com.goochou.p2b.utils.DateUtil;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.RateCouponMapper;
import com.goochou.p2b.dao.UserSignedMapper;
import com.goochou.p2b.model.UserSigned;
import com.goochou.p2b.model.UserSignedExample;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.RateCouponService;
import com.goochou.p2b.service.UserInviteService;
import com.goochou.p2b.service.UserSignedService;

@Service
public class UserSignedServiceImpl implements UserSignedService {

    @Resource
    private UserSignedMapper userSignedMapper;
    @Resource
    private RateCouponService rateCouponService;
    @Resource
    private RateCouponMapper rateCouponMapper;
    @Resource
    private AssetsService assetsService;
    @Resource
    private UserInviteService userInviteService;

    @Override
    public synchronized int saveSigned(Integer userId) {
        int ret = 0;
        Date d = new Date();
        if (checkSigned(userId, d)) {
            return ret;
        }

        // 赠送当前用户日加息券
        rateCouponService.save(0, 0, 0.005, userId, 0);
        ret = 1;
        // 首次赠送月加息券
        if (checkIfFirst(userId)) {
            rateCouponService.save(1, 0, 0.01, userId, 0);
            ret = 2;
        }
        UserSigned u = new UserSigned();
        u.setUserId(userId);
        u.setTime(d);
        userSignedMapper.insert(u);
        return ret;
    }

    @Override
    public boolean checkSigned(Integer userId, Date date) {
        int count = userSignedMapper.checkSigned(userId, date);
        return count > 0 ? true : false;
    }

    @Override
    public boolean checkIfFirst(Integer userId) {
        UserSignedExample example = new UserSignedExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return userSignedMapper.selectByExample(example).size() == 0 ? true : false;
    }

    @Override
    public Integer getMonthSignedCount(Integer userId) {
        return userSignedMapper.getMonthSignedCount(userId);
    }

    @Override
    public Integer getDailySignedCount(Date date) {
        return userSignedMapper.getDailySignedCount(date);
    }

    @Override
    public List<String> getMonthSigned(Integer userId, Date date) {
        return userSignedMapper.getMonthSigned(userId, date);
    }

    @Override
    public synchronized int saveSignedNew(Integer userId) {
        int ret = 0;
        Date d = new Date();
        if (checkSigned(userId, d)) {
            return ret;
        }

        // 赠送当前用户日加息券
        Double rate = 0.005;
        Integer count = userInviteService.getInviteCount(userId);
        if (count.equals(0)) {
            rate = 0.005;
        } else if (count.equals(1)) {
            int ran = new Random().nextInt() * 100;
            if (ran > 50) {
                rate = 0.006;
            }
        } else if (count >= 20) {
            rate = 0.01;
        } else if (count >= 10) {
            rate = 0.009;
        } else if (count >= 5) {
            rate = 0.008;
        } else if (count >= 2) {
            rate = 0.007;
        }
        rateCouponService.save(0, 0, rate, userId, 0);
        ret = 1;
        // 首次赠送月加息券
        if (checkIfFirst(userId)) {
            rateCouponService.save(1, 0, 0.01, userId, 0);
            ret = 2;
        }
        UserSigned u = new UserSigned();
        u.setUserId(userId);
        u.setTime(d);
        userSignedMapper.insert(u);
        return ret;
    }

    @Override
    public synchronized int saveSignedAgain(Integer userId) {
        int ret = userSignedMapper.checkSigned(userId, new Date());
        if (ret > 0) {
            return 5;
        }
        UserSigned u = new UserSigned();
        Date d = new Date();
        u.setUserId(userId);
        u.setTime(d);
        userSignedMapper.insert(u);
        return 3;
    }

    @Override
    public Map<String, Object> selectSignedAgainYesterday(Integer userId) {
        return userSignedMapper.selectSignedAgainYesterday(userId);
    }

    @Override
    public Map<String, Object> selectSignedAwardDetail(Integer userId) {
        return userSignedMapper.selectSignedAwardDetail(userId);
    }

    @Override
    public List<Map<String, Object>> selectSignedAwardDetailList(Integer userId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return userSignedMapper.selectSignedAwardDetailList(map);
    }

    @Override
    public List<Map<String, Object>> selectAllUserSigned(Integer page, Integer limit, String keyword, Date startDate, Date endDate, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("limit", limit);
        map.put("keyword", keyword);
        map.put("startDate", DateUtil.getMinInDay(startDate));
        map.put("endDate", DateUtil.getMaxInDay(endDate));
        map.put("adminId", adminId);
        return userSignedMapper.selectAllUserSigned(map);
    }

    @Override
    public Integer selectAllUserSignedCount(String keyword, Date startDate, Date endDate, Integer adminId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keyword", keyword);
        map.put("startDate", DateUtil.getMinInDay(startDate));
        map.put("endDate", DateUtil.getMaxInDay(endDate));
        map.put("adminId", adminId);
        return userSignedMapper.selectAllUserSignedCount(map);
    }

    @Override
    public Integer getCountByTime(Integer userId, Date startTime, Date endTime) {
        UserSignedExample example = new UserSignedExample();
        example.createCriteria().andUserIdEqualTo(userId).andTimeBetween(startTime, endTime);
        return userSignedMapper.countByExample(example);
    }

    @Override
    public Integer getAllSignedCount(Integer userId) {
        return userSignedMapper.getAllSignedCount(userId);
    }

	@Override
	public int saveSignedLog(Integer userId) {
		UserSigned u = new UserSigned();
        Date d = new Date();
        u.setUserId(userId);
        u.setTime(d);
//        u.setStatus(TaskExeStatusEnum.WEIZHIXING.getFeatureName());
        //新老版本兼容，判断老版本签过这里就不签
        if(userSignedMapper.checkSigned(userId, d)==0){
        	userSignedMapper.insertSelective(u);
        }
        return 3;
	}

}
