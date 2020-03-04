package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.constant.ActivityConstant;
import com.goochou.p2b.constant.RateCouponTypeEnum;
import com.goochou.p2b.constant.UseRuleEnum;
import com.goochou.p2b.dao.HongbaoMapper;
import com.goochou.p2b.dao.MessageMapper;
import com.goochou.p2b.dao.MessageReceiverMapper;
import com.goochou.p2b.dao.RateCouponMapper;
import com.goochou.p2b.dao.VoucherOriginExplainMapper;
import com.goochou.p2b.dao.YaoCountMapper;
import com.goochou.p2b.dao.YaoRecordMapper;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.LotteryGift;
import com.goochou.p2b.model.RateCoupon;
import com.goochou.p2b.model.WithdrawCoupon;
import com.goochou.p2b.model.YaoCount;
import com.goochou.p2b.model.YaoRecord;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.LotteryGiftService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.WithdrawCouponService;
import com.goochou.p2b.service.YaoCountService;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.LotteryUtils;

@Service
public class YaoCountServiceImpl implements YaoCountService {

    @Resource
    private YaoCountMapper yaoCountMapper;
    @Resource
    private YaoRecordMapper yaoRecordMapper;
    @Resource
    private HongbaoMapper hongbaoMapper;
    @Resource
    private RateCouponMapper rateCouponMapper;
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private MessageReceiverMapper messageReceiverMapper;
    @Resource
    private AssetsService assetsService;
    @Resource
    private VoucherOriginExplainMapper voucherOriginExplainMapper;
    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private WithdrawCouponService withdrawCouponService;
    @Resource
    private ActivityService activityService;
    @Resource
    private LotteryGiftService lotteryGiftService;
    @Resource
    private MessageService messageService;

    @Override

    public YaoCount getById(Integer id) {
        return yaoCountMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(YaoCount yc) {
        yaoCountMapper.updateByPrimaryKey(yc);
    }

    @Override
    public void save(YaoCount yc) {
        yaoCountMapper.insert(yc);
    }


    /**
     * 随机发送红包方法
     */
    private Hongbao sendHongbao(Integer type, Integer userId) {
        Integer hbs[][] = {{30, 1, 2000}, {30, 5, 5000}, {30, 15, 10000}, {90, 10, 3000}
                , {90, 20, 5000}, {90, 50, 10000}, {180, 25, 5000}, {180, 50, 8000}
                , {180, 80, 10000}};
        Integer randomIndex = new Random().nextInt(100) % hbs.length;
        //获取随机的红包数据
        Integer days = hbs[randomIndex][0];
        Integer amount = hbs[randomIndex][1];
        Integer limitAmount = hbs[randomIndex][2];
        String limitMonth = null;
        if (days.equals(30)) {
            limitMonth = "30";
        } else if (days.equals(90)) {
            limitMonth = "90";
        } else if (days.equals(180)) {
            limitMonth = "180";
        }
        return hongbaoService.addToUser2(amount.doubleValue(), userId, "恭喜你摇到" + amount + "元投资红包", 1, 2, 0, 30, limitAmount, limitMonth);
    }

    /**
     * 随机发送加息券方法
     */
    private RateCoupon sendRateCoupon(Integer userId) {
        int random = new Random().nextInt(100) % 3;
        Double rate = 0d;
        Map<String, Object> useRuleMap = new HashMap<>();
        if (random == 0) {
            rate = 0.005;
            useRuleMap.put(UseRuleEnum.PROJECTVALIDDAY.getKey(), "30");
        } else if (random == 1) {
            rate = 0.006;
            useRuleMap.put(UseRuleEnum.PROJECTVALIDDAY.getKey(), "90");
        } else if (random == 2) {
            rate = 0.008;
            useRuleMap.put(UseRuleEnum.PROJECTVALIDDAY.getKey(), "180");
        }
        RateCoupon rc = new RateCoupon();
        rc.setType(3);
        rc.setSource(2);
        Date now = new Date();
        rc.setCreateTime(now);
        rc.setUserId(userId);
        rc.setRate(rate);
        rc.setStatus(1);
        rc.setHasDividended(0);
        rc.setExpireTime(DateFormatTools.jumpOneDay(now, 3));
        rc.setUseRule(JSONObject.toJSONString(useRuleMap));
        rc.setTitle("散标加息券");
        rc.setDescript("摇一摇获得散标加息券");
        rc.setRateCouponType(RateCouponTypeEnum.UN_LIMITED.getFeatureName());
        rateCouponMapper.insertAndDays(rc);
        // 发送消息
        String title = "恭喜您获得" + rate*100 + "%加息券奖励";
        String descript = "恭喜您摇到" + rate*100 + "%加息券";
        messageService.save(title, descript, userId);
        return rc;
    }

    private WithdrawCoupon sendWithdrawCoupon(Integer userId) {
        return withdrawCouponService.addToUser(userId, "摇一摇提现券", "摇一摇提现券", 0, null, 30);
    }


    @Override

    public YaoCount updateGetByUserId(Integer userId) {
        return yaoCountMapper.updateGetByUserId(userId);
    }

    @Override
    public Map<String, String> updateYaoYao(Integer userId, String client) {
        Map<String, String> map = new HashMap<String, String>();
        YaoCount yc = yaoCountMapper.updateGetByUserId(userId);
        if (yc.getCount() + yc.getTodayCount() <= 0) {
            map.put("type", "3");
            map.put("msg", "您没有摇奖次数哦");
            return map;
        }
        if (yc.getTodayCount() != 0) {
            yc.setTodayCount(0);
        } else {
            yc.setCount(yc.getCount() - 1);
        }
        yaoCountMapper.updateByPrimaryKey(yc);

        Random random = new Random();
        YaoRecord yr = new YaoRecord();
        yr.setUserId(userId);
        yr.setCreateDate(new Date());
        yr.setUpdateDate(new Date());
        yr.setClient(client);

        map.put("count", String.valueOf(yc.getCount() + yc.getTodayCount()));
        // 开始摇一摇
        int s = random.nextInt(100);
        Integer count = yaoRecordMapper.selectYaoRecordCount(userId);
        if (count == 0) {
            s = 1;
        }
        Hongbao hb = new Hongbao();
        RateCoupon coupon = new RateCoupon();
        WithdrawCoupon wc = new WithdrawCoupon();
        Activity activity = activityService.getByName(ActivityConstant.YAOYIYAO);
        List<LotteryGift> list = lotteryGiftService.getActivityGiftItems(activity.getId());
        LotteryGift lotteryGift = LotteryUtils.startLottery(list);
        if ("投资红包".equals(lotteryGift.getName())) {
            //25%概率送投资红包
            hb = sendHongbao(1, userId);
        } else if ("加息券".equals(lotteryGift.getName())) {
            //概率散标加息券
            coupon = sendRateCoupon(userId);
        } else if ("提现券".equals(lotteryGift.getName())) {
            wc = sendWithdrawCoupon(userId);
        }
        //0没抽中1现金红包2活期加息券3积分4体验金5散标加息券6投资红包7提现券
        if (hb.getId() != null) {
            //投资红包
            yr.setType(6);
            yr.setOtherId(hb.getId());
            yaoRecordMapper.insert(yr);
            map.put("msg", String.valueOf(hb.getAmount()));
            map.put("type", "6");
            return map;
        } else if (coupon.getId() != null) {
            //散标加息券
            yr.setType(5);
            yr.setOtherId(coupon.getId());
            yaoRecordMapper.insert(yr);
            map.put("msg", BigDecimalUtil.multi(coupon.getRate(), 100).toString());
            map.put("type", "5");
            return map;
        } else if (wc.getId() != null) {
            //提现券
            yr.setType(7);
            yr.setOtherId(wc.getId());
            yaoRecordMapper.insert(yr);
            map.put("msg", "提现券");
            map.put("type", "7");
            return map;
        } else {
            //随机文字内容，摇到谢谢参与
            String thks[] = {"安心理财", "稳健平台", "乐享收益", "步步为赢", "快乐投资", "乐趣生财"};
            String word = thks[new Random().nextInt(100) % thks.length];
            yr.setType(0);
            yr.setWords(word);
            yaoRecordMapper.insert(yr);
            map.put("msg", word);
            map.put("type", "0");
            return map;
        }
    }

    @Override
    public void updateCount(Integer userId, Double investAmount) {
        int addCount = (int) (investAmount / 2000);
        if (addCount > 0) {
            YaoCount yc = yaoCountMapper.selectByPrimaryKey(userId);
            if (yc != null) {
                yc.setUserId(userId);
                yc.setCount(yc.getCount() + addCount);
                yc.setTime(new Date());
                yaoCountMapper.updateByPrimaryKey(yc);
            }
        }
    }

    @Override
    public void updateCount(Integer userId) {
        YaoCount yc = yaoCountMapper.selectByPrimaryKey(userId);
        if (yc != null) {
            yc.setTodayCount(1);
            yc.setTime(new Date());
            yaoCountMapper.updateByPrimaryKey(yc);
        }
    }


    @Override
    public List<Integer> selectAllIncomeUser() {
        return yaoCountMapper.selectAllIncomeUser();
    }
}