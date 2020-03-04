package com.goochou.p2b.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.ActivityConstant;
import com.goochou.p2b.dao.ActivityMapper;
import com.goochou.p2b.dao.InvestmentMapper;
import com.goochou.p2b.dao.LotteryCountMapper;
import com.goochou.p2b.dao.LotteryGiftMapper;
import com.goochou.p2b.dao.LotteryRecordMapper;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.LotteryCount;
import com.goochou.p2b.model.LotteryCountExample;
import com.goochou.p2b.model.LotteryGift;
import com.goochou.p2b.model.LotteryRecord;
import com.goochou.p2b.model.User;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.LotteryCountService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.StringUtils;

@Service
public class LotteryCountServiceImpl implements LotteryCountService {
    @Resource
    private UserService userService;
    @Resource
    private AssetsService assetsService;
    @Resource
    private LotteryCountMapper lotteryCountMapper;
    @Resource
    private LotteryRecordMapper lotteryRecordMapper;
    @Resource
    private LotteryGiftMapper lotteryGiftMapper;
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private InvestmentMapper investmentMapper;

    //活动开始时间
    public final static String ACTIVITY_NAME = "年末圣诞九宫格抽奖活动";//测试时间   正式环境是需要修改为12月15号

    @Override
    public synchronized int saveOrUpdateUserLottery(Integer userId, String phone) throws ParseException {
        Activity activity = activityMapper.queryByName(ACTIVITY_NAME);
        Date now = new Date();
        if (activity == null) {
            return -1;
        } else {
            if (now.before(activity.getStartTime())) {
                return -1;
            }
            if (now.after(DateFormatTools.jumpOneDay(activity.getEndTime(), 1))) {
                return -2;
            }
        }

        int countTotal = 0;
        int count = 0;
        double us = 0;
        //用户已经抽奖的记录条数
        int qc = queryCount(userId);
        Double total = selectRechargeWithdraw(activity, userId);   //得到 充值-体现
        if (total > 0) {
            if (total >= 100000) {
                count = 100;
            } else if (total >= 50000) {
                count = 60;
            } else if (total >= 10000) {
                count = 35;
            } else if (total >= 5000) {
                count = 20;
            } else if (total >= 2000) {
                count = 10;
            } else if (total >= 1000) {
                count = 4;
            } else if (total >= 200) {
                count = 1;
            }
        }
        if (qc != 0) {//相对而言   次数表中存在的用户
            LotteryCount c = lotteryCountMapper.queryCountByUserId(userId);
            //校验是否是系统当日首次赠送
            String date = DateFormatTools.dateToStr2(c.getSysTime());
            String day = DateFormatTools.dateToStr2(now);
            if (!day.equals(date)) {
                c.setSysCount(1);
                c.setSysTime(now);
            }
            //赠送抽奖次数档位
            int co = 0;
            if (c.getTemp() != null && c.getTemp() > 0) {
                co = c.getTemp();
            }
            if (count > co) {//证明老用户活动期间充值大于提现 增加次数
                c.setTemp(count);
                count -= co;
                countTotal = c.getSysCount() + c.getExCount() + count;
                c.setExCount(c.getExCount() + count);
                c.setTime(now);
            } else {
                countTotal = c.getSysCount() + c.getExCount();
            }
            c.setPhone(phone);
            lotteryCountMapper.updateByPrimaryKeySelective(c);
        } else {//次数表中不存在的用户
            LotteryCount c = new LotteryCount();
            c.setExCount(count);
            c.setTemp(count);
            c.setSysCount(1);
            c.setSysTime(now);
            c.setTime(now);
            c.setUserId(userId);
            c.setPhone(phone);
            lotteryCountMapper.insertSelective(c);
            countTotal = 1 + count;
        }
        return countTotal;
    }

    @Override
    public LotteryCount queryCountByPhone(String phone) {
        return lotteryCountMapper.queryCountByPhone(phone);
    }

    @Override
    public LotteryCount queryCountByUserId(Integer userId) {
        return lotteryCountMapper.queryCountByUserId(userId);
    }


    /**
     * 传id代表查询是否是新用户，如果什么都不传 或者传0 那么就是查所有用户
     */
    @Override
    public Integer queryCount(Integer userId) {
        return lotteryCountMapper.queryCount(userId);
    }

    @Override
    public List<LotteryGift> listLotteryGift(String ids) {//返回奖品的集合  index输入1不显示奖品抽奖概率   输入0  显示中奖概率
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", ids);
        List<Map<String, Object>> listm = lotteryCountMapper.listLotteryGift(map);
        List<LotteryGift> list = new ArrayList<LotteryGift>();
        if (listm != null) {
            for (Map<String, Object> m : listm) {
                LotteryGift g = new LotteryGift();
                g.setId((Integer) m.get("id"));
                g.setRate(m.get("rate") == null ? 0 : (Double) m.get("rate"));
                g.setType(m.get("type") == null ? 0 : (Integer) m.get("type"));
                g.setName(m.get("name").toString());
                list.add(g);
            }
        }
        return list;
    }

    @Override
    public void addLotteryCountInit() {
        lotteryCountMapper.initLotteryCount();
    }

    @Override
    public Integer queryCountByDate(Date now) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", now);
        return lotteryCountMapper.queryCountByDate(map);
    }

    @Override
    public void updateLotteryCount() {
        lotteryCountMapper.updateLotteryCount();
    }

    @Override
    public int insertSelective(LotteryCount record) {
        return lotteryCountMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(LotteryCount record) {
        return lotteryCountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public LotteryCount queryByPrimaryKey(Integer userId) {
        return lotteryCountMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void insertFromAssetsById(Integer userId) {
        lotteryCountMapper.insertFromAssetsById(userId);
    }

    @Override
    public String setLotteryCount(User user) {
        String msg = null;
        if (StringUtils.isEmpty(user.getPhone())) {
            return "请您绑定手机";
        }
        // step1 判断用户是否第一次登录,如果第一次登录需插入增抽奖记录，并直接使用一次系统赠送抽奖次数，否则更新
        LotteryCount lottery = lotteryCountMapper.selectByPrimaryKey(user.getId());
        if (lottery == null) {
            // 根据资金表插入数据
            lotteryCountMapper.insertFromAssetsById(user.getId());

        } else {
            // lotteryCountService.updateLotteryCount();
        }
        // lotteryCountService.queryCount(user.getId());
        return msg;
    }

    @Override
    public int saveLotteryRecord(LotteryRecord record) {
        return lotteryRecordMapper.insert(record);
    }

    @Override
    public int updateCount(Integer userId) {
        Map<String, Object> userLottery = lotteryCountMapper.queryUserLottery(userId);// 查询用户的抽奖记录数，和历史在投金额
        // 判断是否有系统赠送抽奖,有优先减去
        int sysCount = userLottery.get("sysCount") == null ? 0 : Integer.valueOf(userLottery.get("sysCount").toString());
        int exCount = userLottery.get("exCount") == null ? 0 : Integer.valueOf(userLottery.get("exCount").toString());
        int id = userLottery.get("id").toString() == null ? 0 : Integer.valueOf(userLottery.get("id").toString());
        int version = userLottery.get("version").toString() == null ? 0 : Integer.valueOf(userLottery.get("version").toString());
        if (sysCount > 0) {
            sysCount -= 1;
        } else {
            exCount -= 1;
        }
        LotteryCount c = new LotteryCount();
        c.setId(id);
        c.setVersion(version);
        c.setUserId(userId);
        c.setAmount(userLottery.get("amount") == null ? 0 : Double.valueOf(userLottery.get("amount").toString()));
        c.setExCount(exCount);
        c.setSysCount(sysCount);
        c.setTime(new Date());
        c.setTemp(Integer.valueOf(userLottery.get("temp" +
                "").toString()));
        return lotteryCountMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public Integer queryRecordCount(Integer userId, Integer activityId) {

        return lotteryCountMapper.queryRecordCount(userId, activityId);
    }

    @Override
    public int onlyKind(Integer type, Integer giftId, Integer userId, Integer activityId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("giftId", giftId);
        map.put("userId", userId);
        map.put("activityId", activityId);
        return lotteryCountMapper.onlyKind(map);
    }

    @Override
    public int updateLotteryNum(LotteryGift gift) {
        return lotteryGiftMapper.updateByPrimaryKey(gift);
    }

    @Override
    public LotteryGift selectLotteryGift(Integer id) {
        // TODO Auto-generated method stub
        return lotteryGiftMapper.selectByPrimaryKey(id);
    }

    @Override
    public double selectRechargeWithdraw(Activity activity, Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", activity.getStartTime());
        map.put("endTime", activity.getEndTime());
        map.put("userId", userId);
        System.out.println(activity.getStartTime());
        Map<String, Object> r = lotteryCountMapper.selectRechargeWithdraw(map);
        System.out.println(r.get("total"));
        Double dd = r.get("total") == null ? 0d : Double.valueOf(r.get("total").toString());
        return dd;
    }

    @Override
    public LotteryCount insertSysCount(String phone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insertSysCount(LotteryCount ct, User user) {
        ct.setUserId(user.getId());
        ct.setPhone(user.getPhone());
        ct.setSysCount(1);
        ct.setSysTime(new Date());
        ct.setTemp(0);
        ct.setExCount(0);
        lotteryCountMapper.insertSelective(ct);
    }

    @Override
    public void updateLotteryCount(LotteryCount record) {
        record.setSysCount(1);
        record.setSysTime(new Date());
        lotteryCountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public LotteryCount selectLotteryCountByPhone(String phone) {
        return lotteryCountMapper.queryCountByPhone(phone);
    }

    @Override
    public int queryRecordCountByPhone(String phone) {
        return lotteryCountMapper.queryRecordCountByPhone(phone);
    }

    @Override
    public Map<String, Object> selectChrismasLotteryCount(String phone) {
        return lotteryCountMapper.selectChrismasLotteryCount(phone);
    }

    @Override
    public int doGetLotteryCount(LotteryCount ct, User user) {

        Activity activity = activityMapper.queryByName(ActivityConstant.REGULAR_INVESTMENT_LOTTERY);
        Date now = new Date();
        if (now.after(activity.getStartTime()) && now.before(activity.getEndTime())) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getId());
            map.put("startTime", activity.getStartTime());
            map.put("endTime", activity.getEndTime());
            int maxCount = ct.getTemp();
            Map<String, Object> result = investmentMapper.querySumRegularInvestmentOnActivityDay(map);
//            int count = Integer.valueOf(result.get("count").toString());
            double sumAmount = Double.valueOf(result.get("sumAmount").toString());
            int temp = 0;
            if (sumAmount >= 1000 && sumAmount < 5000) {
                temp = 2;
            } else if (sumAmount >= 5000 && sumAmount < 10000) {
                temp = 2 + 5;
            } else if (sumAmount >= 10000 && sumAmount < 50000) {
                temp = 2 + 5 + 10;
            } else if (sumAmount >= 50000 && sumAmount < 100000) {
                temp = 2 + 5 + 10 + 15;
            } else if (sumAmount >= 100000) {
                temp = 2 + 5 + 10 + 15 + 20;
            }
            if (maxCount < temp) {
                ct.setExCount(temp - maxCount + ct.getExCount());
                ct.setTemp(temp);
//                ct.setTime(now);
                ct.setAmount(sumAmount);
                lotteryCountMapper.updateByPrimaryKeySelective(ct);
            }
            return ct.getSysCount() + ct.getExCount();
        } else {
            return -1;
        }
    }

    @Override
    public void save(LotteryCount record) {
        if (record.getId() == null) {
            lotteryCountMapper.insertSelective(record);
        } else {
            lotteryCountMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public LotteryCount saveOrQueryUserAndActivityCount(Integer userId, Integer activityId) {
        LotteryCountExample lotteryCountExample = new LotteryCountExample();
        LotteryCountExample.Criteria c = lotteryCountExample.createCriteria();
        c.andActivityIdEqualTo(activityId);
        c.andUserIdEqualTo(userId);
        List<LotteryCount> lotteryCount = lotteryCountMapper.selectByExample(lotteryCountExample);
        if (lotteryCount.isEmpty() || lotteryCount == null) {
            LotteryCountExample lo = new LotteryCountExample();
            lo.createCriteria().andUserIdEqualTo(userId);
            List<LotteryCount> lottery = lotteryCountMapper.selectByExample(lo);
            LotteryCount count = new LotteryCount();
            count.setActivityId(activityId);
            count.setUserId(userId);
            count.setTime(new Date());
            count.setExCount(3);
            count.setSysCount(0);
            count.setSysTime(new Date());
            count.setAmount(0d);
            count.setTemp(null);
            count.setVersion(0);
            count.setTempField(null);
            if (lottery.size() > 0 && !lottery.isEmpty()) {
                count.setId(lottery.get(0).getId());
                count.setVersion(lottery.get(0).getVersion());
                lotteryCountMapper.updateByPrimaryKeySelective(count);
            } else {
                lotteryCountMapper.insertSelective(count);
            }
            return count;
        }
        return lotteryCount.get(0);
    }

    @Override
    public Integer saveOrselectActivityCount(Integer activityId, Integer userId) {
        LotteryCountExample example = new LotteryCountExample();
        example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(activityId);
        List<LotteryCount> list = lotteryCountMapper.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0).getExCount();
        }
        Date date = new Date();
        LotteryCount lotteryCount = new LotteryCount();
        lotteryCount.setUserId(userId);
        lotteryCount.setActivityId(activityId);
        lotteryCount.setExCount(0);
        lotteryCount.setSysCount(0);
        lotteryCount.setTime(date);
        lotteryCount.setVersion(0);
        lotteryCount.setSysTime(date);
        lotteryCountMapper.insertSelective(lotteryCount);
        return 0;
    }

    @Override
    public LotteryCount selectLotteryCounts(Integer activityId, Integer userId) {
        LotteryCountExample example = new LotteryCountExample();
        example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(activityId);
        List<LotteryCount> list = lotteryCountMapper.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        }
        Date date = new Date();
        LotteryCount lotteryCount = new LotteryCount();
        lotteryCount.setUserId(userId);
        lotteryCount.setActivityId(activityId);
        lotteryCount.setExCount(0);
        lotteryCount.setSysCount(0);
        lotteryCount.setTime(date);
        lotteryCount.setVersion(0);
        lotteryCount.setSysTime(date);
        lotteryCountMapper.insertSelective(lotteryCount);
        return lotteryCount;
    }

    public Integer saveOrselectActivitySysCount(Integer activityId, Integer userId) {
        LotteryCountExample example = new LotteryCountExample();
        example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(activityId).andSysTimeEqualTo(new Date());
        List<LotteryCount> list = lotteryCountMapper.selectByExample(example);
        if (list.size() > 0) {
            return 1;
        }
        Date date = new Date();
        LotteryCount lotteryCount = new LotteryCount();
        lotteryCount.setUserId(userId);
        lotteryCount.setActivityId(activityId);
        lotteryCount.setExCount(0);
        lotteryCount.setSysCount(0);
        lotteryCount.setTime(date);
        lotteryCount.setVersion(0);
        lotteryCount.setSysTime(date);
        lotteryCountMapper.insertSelective(lotteryCount);
        return 0;
    }


}
