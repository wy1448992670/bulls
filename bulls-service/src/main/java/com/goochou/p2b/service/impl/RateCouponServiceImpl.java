package com.goochou.p2b.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.RateCouponTypeEnum;
import com.goochou.p2b.constant.UseRuleEnum;
import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.dao.CouponTemplateMapper;
import com.goochou.p2b.dao.InterestMapper;
import com.goochou.p2b.dao.InvestmentMapper;
import com.goochou.p2b.dao.MessageMapper;
import com.goochou.p2b.dao.ProjectMapper;
import com.goochou.p2b.dao.RateCouponAuditMapper;
import com.goochou.p2b.dao.RateCouponMapper;
import com.goochou.p2b.dao.TradeRecordMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.BaseResult;
import com.goochou.p2b.model.CouponTemplate;
import com.goochou.p2b.model.CouponTemplateExample;
import com.goochou.p2b.model.Investment;
import com.goochou.p2b.model.Message;
import com.goochou.p2b.model.MessageReceiver;
import com.goochou.p2b.model.RateCoupon;
import com.goochou.p2b.model.RateCouponAudit;
import com.goochou.p2b.model.RateCouponAuditExample;
import com.goochou.p2b.model.RateCouponExample;
import com.goochou.p2b.model.RateCouponExample.Criteria;
import com.goochou.p2b.model.TradeRecord;
import com.goochou.p2b.model.TradeRecordExample;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.RateCouponService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateFormatTools;


@Service
public class RateCouponServiceImpl implements RateCouponService {

    @Resource
    private RateCouponMapper rateCouponMapper;
    @Resource
    private ActivityService activityService;
    @Resource
    private RateCouponAuditMapper rateCouponAuditMapper;
    @Resource
    private InvestmentMapper investmentMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private InterestMapper interestMapper;
    @Resource
    private AssetsService assetsService;
    @Resource
    private TradeRecordMapper tradeRecordMapper;
    @Resource
    private MessageService messageService;
    @Resource
    private UserService userService;
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private AssetsMapper assetsMapper;
    @Resource
    private CouponTemplateMapper couponTemplateMapper;

    @Override
    public synchronized int useCoupon(Integer id, Integer userId) {
        return 0;
    }


    @Override
    public RateCoupon save(Integer type, Integer source, Double rate, Integer userId, Integer time) {
        RateCoupon rc = new RateCoupon();
        Date d = new Date();
        rc.setType(type);
        rc.setCreateTime(d);
        rc.setUserId(userId);
        rc.setSource(source);
        String descript = null;
        if (source == 0) {
            descript = "签到获取加息券";
        } else if (source == 1) {
            descript = "全民理财师奖励加息券";
        } else if (source == 2) {
            descript = "活动赠送加息券";
        } else if (source == 3) {
            descript = "抗战活动赠送加息券";
        } else if (source == 4) {
            descript = "分享邀请码赠送加息券";
        } else if (source == 999) {
            descript = "系统赠送";
        } else if (source == 5) {
            descript = "首次投资赠送";
        } else if (source == 6) {
            descript = "邀请好友投资赠送";
        } else if (source == 7) {
            descript = "11月福利赠送";
        } else if (source == 10) {
            descript = "新手任务赠送";
        } else if (source == 11) {
            descript = "首次绑卡赠送";
        }
        rc.setDescript(descript);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        if (time.equals(0)) {
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        } else if (time.equals(1)) {
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 3);
        } else if (time.equals(2)) {
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 7);
        } else if (time.equals(3)) {
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
        }
        rc.setRate(rate);
        rc.setExpireTime(c.getTime()); // 有效期1个月
        rc.setStatus(1);
        rc.setHasDividended(0);
        rateCouponMapper.insert(rc);
        return rc;
    }

    @Override
    public RateCoupon save(Integer type, Integer source, Double rate, Integer userId, Date time, Integer days) {
        RateCoupon rc = new RateCoupon();
        Date d = new Date();
        rc.setType(type);
        rc.setCreateTime(d);
        rc.setUserId(userId);
        rc.setSource(source);
        String descript = null;
        if (source == 0) {
            descript = "签到获取加息券";
        } else if (source == 1) {
            descript = "全民理财师奖励加息券";
        } else if (source == 2) {
            descript = "活动赠送加息券";
        } else if (source == 3) {
            descript = "抗战活动赠送加息券";
        } else if (source == 4) {
            descript = "分享邀请码赠送加息券";
        } else if (source == 999) {
            descript = "系统赠送";
        } else if (source == 5) {
            descript = "首次投资赠送";
        } else if (source == 6) {
            descript = "邀请好友投资赠送";
        } else if (source == 7) {
            descript = "11月福利赠送";
        } else if (source == 10) {
            descript = "新手任务赠送";
        } else if (source == 11) {
            descript = "首次绑卡赠送";
        }
        rc.setDescript(descript);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 30);
        rc.setRate(rate);
        rc.setDays(days);
        if (time != null) {
            rc.setExpireTime(time);
        } else {
            rc.setExpireTime(c.getTime()); // 有效期1个月
        }
        rc.setStatus(1);
        rateCouponMapper.insertAndDays(rc);
        return rc;
    }


    @Override
    public RateCoupon save(Integer type, String descript, Double rate, Integer userId, Date time, Integer days, Integer adminId, String rateCouponType) {
        RateCoupon rc = new RateCoupon();
        Date d = new Date();
        rc.setType(type);
        rc.setCreateTime(d);
        rc.setUserId(userId);
        rc.setSource(999);
        rc.setDescript(descript);
        rc.setStatus(1);
        rc.setHasDividended(0);
        rc.setRateCouponType(rateCouponType);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 30);
        rc.setRate(rate);
        rc.setDays(days);
        if (time != null) {
            rc.setExpireTime(time);
        } else {
            rc.setExpireTime(c.getTime()); // 有效期1个月
        }
        rc.setAdminId(adminId);
        rateCouponMapper.insertAndDays(rc);
        return rc;
    }

    @Override
    public RateCoupon save2(Integer type, Integer source, String descript, Double rate, Integer userId, Date time, Integer days) {
        RateCoupon rc = new RateCoupon();
        Date d = new Date();
        rc.setType(type);
        rc.setCreateTime(d);
        rc.setUserId(userId);
        rc.setSource(source);
        if (source == 0) {
            descript = "签到获取加息券";
        } else if (source == 1) {
            descript = "全民理财师奖励加息券";
        } else if (source == 2) {
            descript = "活动赠送加息券";
        } else if (source == 3) {
            descript = "抗战活动赠送加息券";
        } else if (source == 4) {
            descript = "分享邀请码赠送加息券";
        } else if (source == 999) {
            descript = "系统赠送";
        } else if (source == 5) {
            descript = "首次投资赠送";
        } else if (source == 6) {
            descript = "邀请好友投资赠送";
        } else if (source == 7) {
            descript = "11月福利赠送";
        } else if (source == 10) {
            descript = "新手任务赠送";
        } else if (source == 11) {
            descript = "首次绑卡赠送";
        }
        rc.setDescript(descript);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 30);
        rc.setRate(rate);
        rc.setDays(days);
        if (time != null) {
            rc.setExpireTime(time);
        } else {
            rc.setExpireTime(c.getTime()); // 有效期1个月
        }
        rc.setDays(days);
        rc.setStatus(1);
        rc.setHasDividended(0);
        rateCouponMapper.insert(rc);
        return rc;
    }


    @Override
    public List<RateCoupon> rateCouponList(Integer id) {
        RateCouponExample example = new RateCouponExample();
        Criteria c = example.createCriteria();
        c.andExpireTimeLessThan(new Date());
        c.andUserIdEqualTo(id);
        example.setOrderByClause("create_time,type desc");
        return rateCouponMapper.selectByExample(example);
    }

    @Override
    public List<RateCoupon> rateCouponNoUseList(Integer id, Integer page) {
        RateCouponExample example = new RateCouponExample();
        Criteria c = example.createCriteria();
        c.andUseTimeIsNull();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        c1.set(Calendar.DATE, c1.get(Calendar.DATE));
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        c.andExpireTimeGreaterThanOrEqualTo(c1.getTime());
        c.andUserIdEqualTo(id);
        example.setLimitStart((page - 1) * 5);
        example.setLimitEnd(5);
        example.setOrderByClause("expire_time,create_time asc");
        return rateCouponMapper.selectByExample(example);
    }

    @Override
    public RateCoupon getNoUseMonthRateCoupon(Integer id) {
        return rateCouponMapper.getNoUseMonthRateCoupon(id);
    }

    @Override
    public int countRateCouponNoUse(Integer id) {
        RateCouponExample example = new RateCouponExample();
        Criteria c = example.createCriteria();
        c.andUseTimeIsNull();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        c1.set(Calendar.DATE, c1.get(Calendar.DATE));
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        c.andExpireTimeGreaterThanOrEqualTo(c1.getTime());
        c.andUserIdEqualTo(id);
        return rateCouponMapper.countByExample(example);
    }

    @Override
    public List<RateCoupon> rateCouponUsingList(Integer id, Integer page) {
        return rateCouponMapper.rateCouponUsingList(id, (page - 1) * 5, 5);
    }

    @Override
    public int countRateCouponUsed(Integer id) {
        RateCouponExample example = new RateCouponExample();
        Criteria c = example.createCriteria();
        c.andUseTimeIsNotNull();
        c.andUserIdEqualTo(id);
        return rateCouponMapper.countByExample(example);
    }

    @Override
    public List<RateCoupon> rateCouponExpiredList(Integer id, Integer page) {
        RateCouponExample example = new RateCouponExample();
        Criteria c = example.createCriteria();
        c.andUseTimeIsNull();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        c1.set(Calendar.DATE, c1.get(Calendar.DATE));
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        c.andExpireTimeLessThan(c1.getTime());
        c.andUserIdEqualTo(id);
        example.setLimitStart((page - 1) * 5);
        example.setLimitEnd(5);
        example.setOrderByClause("create_time,type desc");
        return rateCouponMapper.selectByExample(example);
    }

    @Override
    public int countRateCouponExpired(Integer id) {
        RateCouponExample example = new RateCouponExample();
        Criteria c = example.createCriteria();
        c.andUseTimeIsNull();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        c1.set(Calendar.DATE, c1.get(Calendar.DATE));
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        c.andExpireTimeLessThan(c1.getTime());
        c.andUserIdEqualTo(id);
        return rateCouponMapper.countByExample(example);
    }

    @Override
    public RateCoupon saveRateCoupon(Date date, Double rate, Integer id) {
        RateCoupon rc = new RateCoupon();
        rc.setType(0);
        rc.setSource(2);
        rc.setDescript("摇一摇获取加息券");
        rc.setCreateTime(date);
        rc.setUserId(id);
        rc.setRate(rate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        rc.setExpireTime(c.getTime());
        rc.setHasDividended(0);
        rateCouponMapper.insert(rc);
        return rc;
    }

    @Override
    public RateCoupon getMonthSend(Integer userId, Integer source) {
        return rateCouponMapper.getMonthSend(userId, source);
    }

    @Override
    public boolean getCountBySource(Integer userId, Integer source) {
        RateCouponExample example = new RateCouponExample();
        Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId);
        c.andSourceEqualTo(source);
        Integer count = rateCouponMapper.countByExample(example);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int countUsingRateCoupon(Integer id) {
        List<Long> list = rateCouponMapper.countUsingRateCoupon(id);
        Long count = 0l;
        if (list != null && list.size() > 0) {
            for (Long aa : list) {
                count = count + aa;
            }
        }
        return count.intValue();
    }

    @Override
    public List<Map<String, Object>> query(String keyword, Integer source, Integer type, Date startTime, Date endTime, Integer start, Integer limit, Integer days, Integer adminId) {
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
        map.put("source", source);
        map.put("type", type);
        map.put("days", days);
        map.put("start", start);
        map.put("limit", limit);
        map.put("adminId", adminId);
        return rateCouponMapper.query(map);
    }

    @Override
    public Integer queryCount(String keyword, Integer source, Integer type, Date startTime, Date endTime, Integer days, Integer adminId) {
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
        map.put("days", days);
        map.put("source", source);
        map.put("type", type);
        map.put("adminId", adminId);
        return rateCouponMapper.queryCount(map);
    }

    @Override
    public Integer querySum(String keyword, Integer source, Integer type, Date startTime, Date endTime, Integer days, Integer adminId) {
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
        map.put("days", days);
        map.put("source", source);
        map.put("type", type);
        map.put("adminId", adminId);
        return rateCouponMapper.querySum(map);
    }

    @Override
    public int getDailyCountBySource(Integer userId, Integer source) {
        return rateCouponMapper.getDailyCountBySource(userId, source);
    }

    @Override
    public List<String> addBirthdayRateCoupon() throws URISyntaxException, ParseException, IOException {
        rateCouponMapper.addBirthdayRateCoupon();
        List<Map<String, Object>> list = rateCouponMapper.queryDayBirthdayUser();
        List<String> phoneList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String phone = map.get("phone").toString();
            phoneList.add(phone);
        }
        return phoneList;
    }

    @Override
    public Integer checkBirthdayRateCoupons() {
        return rateCouponMapper.checkBirthdayRateCoupons();
    }

    public static void main(String[] args) {
        try {
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    
    public List<RateCoupon> rateCouponUsingAndNoUse(Integer id, Integer start, Integer limit) {
        return rateCouponMapper.rateCouponUsingAndNoUse(id, start, limit);
    }

    public int countRateCouponUsingAndNoUse(Integer id) {
        return rateCouponMapper.countrateCouponUsingAndNoUse(id);
    }

    @Override
    public List<RateCoupon> rateCoupon(Integer id) {
        RateCouponExample example = new RateCouponExample();
        Criteria c = example.createCriteria();
        c.andExpireTimeGreaterThanOrEqualTo(new Date());
        c.andUseTimeIsNotNull();
        c.andUserIdEqualTo(id);
        c.andTypeBetween(0, 1);
        return rateCouponMapper.selectByExample(example);
    }

    @Override
    public List<RateCoupon> rateCouponNewList(Integer userId, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("start", start);
        map.put("limit", limit);
        return rateCouponMapper.rateCouponNewList(map);
    }

    @Override
    public Integer rateCouponNewCount(Integer userId) {
        return rateCouponMapper.rateCouponNewCount(userId);
    }

    @Override
    public RateCoupon selectByIdRateCoupon(Integer couponId) {

        return rateCouponMapper.selectByPrimaryKey(couponId);
    }

    @Override
    public List<RateCoupon> selectByUserIdRateCouponList(Integer type, Integer userId, Integer start, Integer limit) {
        RateCouponExample example = new RateCouponExample();
        example.createCriteria().andTypeEqualTo(type).andUserIdEqualTo(userId).andUseTimeIsNull().andStatusEqualTo(1).andExpireTimeGreaterThanOrEqualTo(new Date());
        if (null != start) {
            example.setLimitStart(start);
        }
        if (null != limit) {
            example.setLimitEnd(limit);
        }
        example.setOrderByClause("expire_time");
        return rateCouponMapper.selectByExample(example);
    }

    @Override
    public Integer selectByUserIdRateCouponCount(Integer type, Integer userId) {
        RateCouponExample example = new RateCouponExample();
        example.createCriteria().andTypeEqualTo(type).andUserIdEqualTo(userId).andUseTimeIsNull().andStatusEqualTo(1).andExpireTimeGreaterThanOrEqualTo(new Date());
        return rateCouponMapper.countByExample(example);
    }

    public List<RateCoupon> getByUserIdAndSourceAndType(Integer userId, Integer source, Integer type) {
        RateCouponExample example = new RateCouponExample();
        Criteria c = example.createCriteria();
        c.andUserIdEqualTo(userId).andUseTimeIsNull().andExpireTimeGreaterThanOrEqualTo(new Date());
        if (source != null) {
            c.andSourceEqualTo(source);
        }
        if (type != null) {
            c.andTypeEqualTo(type);
        }
        return rateCouponMapper.selectByExample(example);

    }

    @Override
    public void updateRateCoupon(RateCoupon rateCoupon) {
        rateCouponMapper.updateByPrimaryKeySelective(rateCoupon);
    }

    @Override
    public List<RateCoupon> selectRateCouponList(Integer userId, Integer status, Integer start, Integer limit) {
        RateCouponExample example = new RateCouponExample();
        example.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(status).andStatusEqualTo(1);// 查询未使用的加息券
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        example.setOrderByClause("status");
        return rateCouponMapper.selectByExample(example);
    }

    @Override
    public int selectRateCouponCount(Integer userId, Integer status) {
        RateCouponExample example = new RateCouponExample();
        example.createCriteria().andUserIdEqualTo(userId).andTypeEqualTo(status).andStatusEqualTo(1);
        return rateCouponMapper.countByExample(example);
    }


    @Override
    public void addRateCouponAudit(RateCouponAudit rateCouponAudit) {
        rateCouponAuditMapper.insertSelective(rateCouponAudit);
    }

    @Override
    public RateCouponAudit getByInvestmentId(Integer investmentId) {
        RateCouponAuditExample example = new RateCouponAuditExample();
        example.createCriteria().andInvestmentIdEqualTo(investmentId);
        List<RateCouponAudit> list = rateCouponAuditMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<RateCouponAudit> getByStatus(Integer status) {
        RateCouponAuditExample example = new RateCouponAuditExample();
        example.createCriteria().andStatusEqualTo(status);
        return rateCouponAuditMapper.selectByExample(example);
    }

    @Override
    public RateCoupon get(Integer id) {
        return rateCouponMapper.selectByPrimaryKey(id);
    }

    @Override
    public RateCouponAudit getAudit(Integer auditId) {
        return rateCouponAuditMapper.selectByPrimaryKey(auditId);
    }


    @Override
    public void saveCouponInterestTask() throws Exception {//散标加息券  派息定时器
        //查询当日所有需要派发的 散标加息券
        List<RateCoupon> list = rateCouponMapper.selectCouponInterestTaskList();
        if (list.size() > 0) {
            for (RateCoupon rateCoupon : list) {
                Assets assets = assetsService.findByuserId(rateCoupon.getUserId());
//                Double banlce = BigDecimalUtil.add(assets.getAvailableBalance(), rateCoupon.getIncome());
//                assets.setAvailableBalance(banlce);
//                assets.setTotalIncome(BigDecimalUtil.add(assets.getTotalIncome(), rateCoupon.getIncome()));
                //  assets.setUncollectInterest(BigDecimalUtil.sub(assets.getUncollectInterest(), rateCoupon.getIncome()));
                int ret = assetsMapper.updateByPrimaryKeyAndVersionSelective(assets);
                if (ret != 1) {
                    throw new LockFailureException();
                }
                rateCoupon.setStatus(2);
                rateCoupon.setHasDividended(1);
                rateCouponMapper.updateByPrimaryKey(rateCoupon);


                TradeRecordExample example = new TradeRecordExample();
                example.createCriteria()//.andTypeEqualTo(3)
                        .andOtherIdEqualTo(rateCoupon.getId()).andTableNameEqualTo("rate_coupon");
                List<TradeRecord> tradeRecordList = tradeRecordMapper.selectByExample(example);
                if (tradeRecordList.size() > 0) {
                    TradeRecord tradeRecord = tradeRecordList.get(0);
                    tradeRecord.setAmount(BigDecimalUtil.add(tradeRecord.getAmount(), rateCoupon.getIncome()));
//                    tradeRecord.setTime(new Date());
//                    tradeRecord.setBalance(assets.getAvailableBalance());
                    tradeRecordMapper.updateByPrimaryKeySelective(tradeRecord);
                } else {
                    TradeRecord tradeRecord = new TradeRecord();
//                    tradeRecord.setTime(new Date());
//                    tradeRecord.setBalance(assets.getAvailableBalance());
                    tradeRecord.setAmount(rateCoupon.getIncome());
                    tradeRecord.setUserId(rateCoupon.getUserId());
//                    tradeRecord.setSource(7);
                    tradeRecord.setTableName("rate_coupon");
                    tradeRecord.setOtherId(rateCoupon.getId());
//                    tradeRecord.setType(3);
                    tradeRecordMapper.insertSelective(tradeRecord);
                }
                Investment investment = investmentMapper.selectByPrimaryKey(rateCoupon.getInvestmentId());
                String title = "";
                if (investment != null) {
                    title = investment.getProductId() == null ? "散标" : "安鑫赚";
                } else {
                    title = "散标";
                }
                messageService.save("您的加息收益已派发！",
                        "您在鑫聚财“2周年庆”活动中投资" + title + "项目使用了加息券，目前加息券已使用完成，收益已派发至您的账户余额！感谢您对鑫聚财的信任和支持！",
                        rateCoupon.getUserId());

            }
        }
    }

    @Override
    public List<RateCoupon> selectUseRateCouponList(Integer projectId) {
        return rateCouponMapper.selectUseRateCouponList(projectId);
    }

    @Override
    public void saveCouponStatusTask() throws Exception {
        RateCouponExample example = new RateCouponExample();
        example.createCriteria().andStatusEqualTo(1);
        List<RateCoupon> list = rateCouponMapper.selectByExample(example);//散标加息券
        for (RateCoupon rateCoupon : list) {
            if (DateFormatTools.dateIsExpired(rateCoupon.getExpireTime())) {
                rateCoupon.setStatus(3);
                rateCouponMapper.updateByPrimaryKeySelective(rateCoupon);
            }
        }
        RateCouponExample example1 = new RateCouponExample();
        example1.createCriteria().andStatusEqualTo(0).andTypeEqualTo(2);
        List<RateCoupon> list2 = rateCouponMapper.selectByExample(example1);//活期加息券
        for (RateCoupon rateCoupon : list2) {
            if (DateFormatTools.dateIsExpired(rateCoupon.getEndTime())) {
                rateCoupon.setStatus(2);
                rateCouponMapper.updateByPrimaryKeySelective(rateCoupon);
            }
        }
    }

    @Override
    public void saveBatchCoupon(String couponTitle, MultipartFile file, String descript, Integer type, String userId, String template, String title, Integer adminId, String rateCouponType) throws Exception {
        if (StringUtils.isBlank(couponTitle)) {
            if (type.equals(2)) {
                couponTitle = "灵活宝加息券";
            } else if (type.equals(3)) {
                couponTitle = "散标加息券";
            }
        }
        JSONArray jsonArray = JSONArray.parseArray(template);
        if (StringUtils.isBlank(userId)) {
            if (!file.isEmpty()) {
                InputStreamReader isr = new InputStreamReader(file.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                String str = "";
                Date d = new Date();
                Message message = new Message();
                message.setTitle(title);
                message.setContent(descript);
                message.setCreateTime(d);
                Integer id = messageService.saveMessage(title, descript);
                List<RateCoupon> couponList = new LinkedList<>();
                List<MessageReceiver> msgList = new LinkedList<>();
                long s1 = System.currentTimeMillis();
                while ((str = reader.readLine()) != null) {
                    System.out.println("---" + str);
                    Integer uId = Integer.parseInt(str);
                    if (uId == null) {
                        continue;
                    }
                    for (int k = 0; k < jsonArray.size(); k++) {
                        Map<String, Object> map = (Map<String, Object>) jsonArray.get(k);
                        Date entime = new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("en"));

                        RateCoupon rc = new RateCoupon();
                        rc.setType(type);
                        rc.setCreateTime(d);
                        rc.setUserId(uId);
                        rc.setSource(999);
                        rc.setDescript(descript);
                        rc.setStatus(1);
                        rc.setHasDividended(0);
                        Calendar c = Calendar.getInstance();
                        c.setTime(d);
                        c.add(Calendar.DATE, 3);
                        rc.setRate(Double.parseDouble((String) map.get("rate")));
                        //    rc.setDays((Integer) map.get("days"));
                        if (entime != null) {
                            rc.setExpireTime(entime);
                        } else {
                            rc.setExpireTime(c.getTime());
                        }
                        rc.setAdminId(adminId);
                        rc.setRateCouponType(rateCouponType);
                        couponList.add(rc);

                        //   save(type, couponTitle, rate1, user.getId(), (entime), Integer.parseInt(dayss));
                    }
                    if (!StringUtils.isBlank(title) && !StringUtils.isBlank(descript)) {
                        MessageReceiver r = new MessageReceiver();
                        r.setMessageId(id);
                        r.setReceiveTime(d);
                        r.setReceiverId(uId);
                        msgList.add(r);
                    }
                }
                long s2 = System.currentTimeMillis();
                System.out.println("=查询添加耗时==" + (s2 - s1));

                long start = System.currentTimeMillis();
                rateCouponMapper.insertBatch(couponList);
                messageMapper.insertBatch(msgList);
                long end = System.currentTimeMillis();
                System.out.println("插入耗时=-==" + (end - start));
            } else {
//                List<User> user = userService.list();
//                for (User user2 : user) {
//                    sf.append(user2.getTrueName() + "加息券利率：");
//                    for (int k = 0; k < jsonArray.size(); k++) {
//                        Map<String, Object> map = (Map<String, Object>) jsonArray.get(k);
//                        Double rate1 = Double.parseDouble((String) map.get("rate"));
//                        sf.append(rate1 + "__");
//                        String entime = (String) map.get("en");
//                        String dayss = (String) map.get("days");
//                        save(type, couponTitle, rate1, user2.getId(), new SimpleDateFormat("yyyy-MM-dd").parse(entime), Integer.parseInt(dayss));
//                    }
//                    if (!StringUtils.isBlank(title) && !StringUtils.isBlank(descript)) {
//                        messageService.save(title, descript, user2.getId());
//                    }
//                }
            }
        } else {
            String[] users = userId.split(",");
            for (int i = 0; i < users.length; i++) {
                for (int k = 0; k < jsonArray.size(); k++) {
                    Map<String, Object> map = (Map<String, Object>) jsonArray.get(k);
                    Double rate1 = Double.parseDouble((String) map.get("rate"));
                    String entime = (String) map.get("en");
                    String dayss = (String) map.get("days");
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(entime);

                    Integer couponDays = dayss == null ? null : Integer.parseInt(dayss);
                    save(type, couponTitle, rate1, Integer.parseInt(users[i]), date, couponDays, adminId, rateCouponType);
                }
                if (!StringUtils.isBlank(title) && !StringUtils.isBlank(descript)) {
                    messageService.save(title, descript, Integer.parseInt(users[i]));
                }
            }
        }
    }


    @Override
    public List<RateCoupon> queryRateCouponUseInfoByDate(Date date, Date endDate) {

        RateCouponExample rateCouponExample = new RateCouponExample();
        rateCouponExample.createCriteria().andUseTimeGreaterThanOrEqualTo(date).andUseTimeLessThanOrEqualTo(endDate);
        return rateCouponMapper.selectByExample(rateCouponExample);
    }

    @Override
    public Integer saveRateCouponToUser(Integer type, String descript, Double rate, Integer userId, Integer expireDays, Integer days, Integer adminId, String rateCouponType) {
        RateCoupon rc = new RateCoupon();
        Date d = new Date();
        rc.setType(type);
        rc.setCreateTime(d);
        rc.setUserId(userId);
        rc.setSource(999);
        rc.setDescript(descript);
        rc.setStatus(1);
        rc.setHasDividended(0);
        rc.setRate(rate);
        rc.setDays(days);
        expireDays = expireDays == null ? 30 : expireDays;
        rc.setExpireTime(DateFormatTools.jumpOneDay(new Date(), expireDays));
        rc.setAdminId(adminId);
        rc.setRateCouponType(rateCouponType == null ? RateCouponTypeEnum.UN_LIMITED.getFeatureName() : rateCouponType);
        return rateCouponMapper.insertAndDays(rc);
    }

    public Integer saveNew(Integer userId, Integer type, Integer source, String title, String descript, Double rate, Integer expireDays, String couponId, Integer minDays, Double minAmount) {
        RateCoupon rc = new RateCoupon();
        rc.setUserId(userId);
        rc.setType(type);
        rc.setTitle(title);
        rc.setDescript(descript);
        rc.setRate(rate);
        rc.setSource(source);
        Date now = new Date();
        rc.setCreateTime(now);
        rc.setExpireTime(DateFormatTools.jumpOneDay(now, expireDays));
        rc.setHasDividended(0);
        rc.setStatus(1);
        rc.setRateCouponType(RateCouponTypeEnum.UN_LIMITED.getFeatureName());
        Map<String, Object> useRuleMap = new HashMap<>();
        useRuleMap.put(UseRuleEnum.PROJECTVALIDMINDAY.getKey(), minDays);
        useRuleMap.put(UseRuleEnum.PROJECTVALIDMINAMOUNT.getKey(), minAmount);
        rc.setUseRule(JSONObject.toJSONString(useRuleMap));
        rc.setCouponId(couponId);
        int i= rateCouponMapper.insertAndDays(rc);
        // 发送消息
        messageService.save(title, descript, userId);
        return i;
    }

    /**
     * 按照加息后产生的利息最多的对加息券排序
     */
    public class MapComparatorInterestDesc implements Comparator<Map<String, Object>> {
        public int compare(Map<String, Object> m1, Map<String, Object> m2) {
            return ((Double) m2.get("maxRate")).compareTo((Double) m1.get("maxRate"));
        }
    }

	@Override
	public List<Map<String,Object>> queryExpireRateCouponAfterSomeDay(
			Map<String, Object> map) {
		return rateCouponMapper.queryExpireRateCouponAfterSomeDay(map);
	}


	@Override
	public BaseResult couponTemplateList(Integer type,
			Integer stockBalance, Integer minDays, Long minAmount,
			String keyword, Integer start, Integer limit) {
		BaseResult result = new BaseResult();
		try {
			Map<String,Object> map = new HashMap<>();
			map.put("type", type);
			map.put("stockBalance", stockBalance);
			map.put("minDays", minDays);
			map.put("minAmount", minAmount);
			map.put("keyword", keyword);
			map.put("start", start);
			map.put("limit", limit);
			
			List<CouponTemplate> list = couponTemplateMapper.couponTemplateList(map);
			Integer count = couponTemplateMapper.couponTemplateCount(map);
			
			map.clear();
			map.put("list", list);
			map.put("count", count);
			result.setMap(map);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}


	@Override
	public boolean addCouponTemplate(CouponTemplate couponTemplate) {
		return couponTemplateMapper.insert(couponTemplate) == 1 ? true : false;
	}


	@Override
	public CouponTemplate getCouponTemplate(String templateId) {
		CouponTemplateExample example = new CouponTemplateExample();
		example.createCriteria().andTemplateIdEqualTo(templateId);
		List<CouponTemplate> list = couponTemplateMapper.selectByExample(example);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}


	@Override
	public boolean updateCouponTemplate(CouponTemplate couponTemplate) {
        return couponTemplateMapper.updateByPrimaryKeySelective(couponTemplate) == 1 ? true : false;
    }

	@Override
	public boolean updateCouponTemplate(String templateId) {
		CouponTemplate c = new CouponTemplate();
		c.setTemplateId(templateId);
		c.setIsDeleted(0);
		return couponTemplateMapper.updateByPrimaryKeySelective(c) == 1 ? true : false;
	}
}
