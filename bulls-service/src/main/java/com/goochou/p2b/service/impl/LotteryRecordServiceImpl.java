package com.goochou.p2b.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.ActivityConstant;
import com.goochou.p2b.dao.ActivityExpRecordMapper;
import com.goochou.p2b.dao.ActivityMapper;
import com.goochou.p2b.dao.InvestmentMapper;
import com.goochou.p2b.dao.LotteryAddressMapper;
import com.goochou.p2b.dao.LotteryGiftMapper;
import com.goochou.p2b.dao.LotteryRecordMapper;
import com.goochou.p2b.model.Activity;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.Hongbao;
import com.goochou.p2b.model.LotteryAddress;
import com.goochou.p2b.model.LotteryCount;
import com.goochou.p2b.model.LotteryGift;
import com.goochou.p2b.model.LotteryGiftExample;
import com.goochou.p2b.model.LotteryRecord;
import com.goochou.p2b.model.LotteryRecordExample;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.vo.ActivityInvestAndLotteryVO;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.HongbaoService;
import com.goochou.p2b.service.LotteryCountService;
import com.goochou.p2b.service.LotteryGiftService;
import com.goochou.p2b.service.LotteryRecordService;
import com.goochou.p2b.service.MessageService;
import com.goochou.p2b.service.RateCouponService;
import com.goochou.p2b.service.UserAddressService;
import com.goochou.p2b.service.UserService;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateFormatTools;
import com.goochou.p2b.utils.LotteryUtils;
import com.goochou.p2b.utils.RegularUtils;
import com.goochou.p2b.utils.StringUtils;
import com.google.common.collect.Maps;

@Service
public class LotteryRecordServiceImpl implements LotteryRecordService {
	
	private final static Logger logger = Logger.getLogger(LotteryRecordServiceImpl.class);


    //根据用户在投确定所能抽奖的奖品类型
    public final static String LV0 = "11,12,13,14";   //用户首次抽奖等级
    public final static String LV1 = "11,12,13,14,15,16,17";   //一般用户抽奖等级
    public final static String LV2 = "11,12,13,14,15,16,17,18,19,20";  //前50资产抽奖等级
    public final static int max = 1888;
    public final static int min = 888;

    @Resource
    private LotteryGiftMapper lotteryGiftMapper;
    @Resource
    private LotteryRecordMapper lotteryRecordMapper;
    @Resource
    private UserService userService;
    @Resource
    private LotteryCountService lotteryCountService;
    @Resource
    private AssetsService assetsService;
    @Resource
    private RateCouponService rateCouponService;
    @Resource
    private ActivityExpRecordMapper activityExpRecordMapper;
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private MessageService messageService;
    @Resource
    private LotteryAddressMapper lotteryAddressMapper;
    @Resource
    private InvestmentMapper investmentMapper;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private LotteryGiftService lotteryGiftService;
    @Resource
    private HongbaoService hongbaoService;
    @Resource
    private ActivityService activityService;

    @Override
    public Integer saveLottery() {
        List<LotteryGift> list = lotteryGiftMapper.selectByExample(new LotteryGiftExample());
        if (null == list || list.isEmpty()) {
            return -1;
        }
        int size = list.size();

        // 计算总概率，这样可以保证不一定总概率是1
        double sumRate = 0d;
        for (LotteryGift gift : list) {
            sumRate += gift.getRate();
        }

        // 计算每个物品在总概率的基础下的概率情况
        List<Double> sortOrignalRates = new ArrayList<Double>(size);
        Double tempSumRate = 0d;
        for (LotteryGift gift : list) {
            tempSumRate += gift.getRate();
            sortOrignalRates.add(tempSumRate / sumRate);
        }

        // 根据区块值来获取抽取到的物品索引
        double nextDouble = Math.random();
        sortOrignalRates.add(nextDouble);
        Collections.sort(sortOrignalRates);

        return sortOrignalRates.indexOf(nextDouble);
    }

    @Override
    public synchronized Integer saveLottery(LotteryRecord record) {

        return lotteryRecordMapper.insert(record);
    }

    @Override
    public LotteryRecord queryByPrimaryKey(Integer id) {
        return lotteryRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateLotteryRecord(LotteryRecord record, LotteryAddress address) {
        if (address != null && address.getId() != null) {
            lotteryAddressMapper.updateByPrimaryKeySelective(address);
        } else {
            lotteryAddressMapper.insertSelective(address);
        }
        record.setUserAddressId(address.getId());
        lotteryRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void updateLotteryRecord(LotteryRecord lottery) {
        lotteryRecordMapper.updateByPrimaryKeySelective(lottery);
    }

    @Override
    public Map<String, Object> queryDetailByPrimaryKey(Integer orderId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", orderId);
        return lotteryRecordMapper.queryDetailByPrimaryKey(map);
    }

    @Override
    public List<Map<String, Object>> queryListByUserId(Integer userId) {
        return lotteryRecordMapper.queryListByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> queryAll(String status, Date date, Integer orderId, Integer activityId, Integer giftId, String account, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(status)) {
            map.put("status", status);
        }
        if (!StringUtils.isEmpty(account)) {
            map.put("account", account);
        }
        map.put("date", date);
        map.put("orderId", orderId);
        map.put("activityId", activityId);
        map.put("giftId", giftId);
        map.put("start", start);
        map.put("limit", limit);
        return lotteryRecordMapper.queryAll(map);
    }

    @Override
    public Integer queryCount(String status, Date date, Integer orderId, Integer activityId, Integer giftId, String account) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("date", date);
        map.put("orderId", orderId);
        map.put("giftId", giftId);
        map.put("activityId", activityId);
        map.put("account", account);
        return lotteryRecordMapper.queryCount(map);
    }

    @Override
    public List<Map<String, Object>> selectString(String order, Integer start, Integer limit, Integer activityId) {//不需要去除重复,从零开始,二十条中奖记录,按时间降序取得
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("order", order);
        map.put("start", start);
        map.put("limit", limit);
        map.put("activityId", activityId);
        return lotteryRecordMapper.selectString(map);
    }


    @Override
    public synchronized Map<String, Object> saveLotteryRecord(User user) throws Exception {//2016-04-19  抽奖活动
        Map<String, Object> result = new HashMap<String, Object>();
        Activity activity = activityMapper.queryByName(ActivityConstant.REGULAR_INVESTMENT_LOTTERY);
        Date date = new Date();
        if (activity == null) {
            result.put("error", "无效活动，请联系客服");
            return result;
        } else {
            if (date.before(activity.getStartTime())) {
                result.put("error", "活动未开始");
                return result;
            }
            if (date.after(DateFormatTools.jumpOneDay(activity.getEndTime(), 1))) {
                result.put("error", "活动已结束");
                return result;
            }
        }
        LotteryCount lotteryCount = lotteryCountService.queryCountByUserId(user.getId());
        Integer count = 0;
        if (lotteryCount != null) {
            Integer exCount = lotteryCount.getExCount() == null ? 0 : lotteryCount.getExCount();
            Integer sysCount = lotteryCount.getSysCount() == null ? 0 : lotteryCount.getSysCount();
            count = exCount + sysCount;
        } else {
            result.put("noLottery", "没有抽奖次数！");
            return result;
        }
        if (count > 0) {//count>0  最少有一次
            Map<String, Object> map = new HashMap<String, Object>();
            //根据用户在投确定他抽奖的奖品种类
            Assets a = assetsService.findByuserId(user.getId());// 得到用户当前的投资详情
//            double amount = BigDecimalUtil.add(a.getAvailableBalance(), a.getHuoInvestmentAmount(), a.getUncollectCapital());//总资产
            String lv = LV0;
            //查询数据库中总资产前五十的的最后一位总资产
            double Ranking = assetsService.selectTotalAssetsRanking();
            //此处需要添加一个判断用户第一次抽奖时100%中奖
            int cs = lotteryCountService.queryRecordCount(user.getId(), activity.getId());
            if (cs != 0) {
                lv = LV1;
//                if (amount >= Ranking) {
//                    lv = LV2;
//                }
            }
            List<LotteryGift> list = lotteryCountService.listLotteryGift(lv);//返回奖品的集合  输入1不显示奖品抽奖概率   输入0  显示中奖概率
            LotteryGift gift = null;
            LotteryGift g = null;
            int s = 0;
            int cnt = 0;
            do {//每个用户中单个实物只能有一个
                if (cnt >= 5) {
                    g = lotteryGiftMapper.selectByPrimaryKey(14);
                    gift = g;
                    break;
                }
                gift = LotteryUtils.startLottery(list);//随机抽奖
                if (gift != null) {
                    //查询奖品库存
                    g = lotteryCountService.selectLotteryGift(gift.getId());
                    if (g.getLeftNum() <= 0) {//如果奖品抽完了  继续抽
                        list.remove(gift);
                        continue;
                    }
                    if (g.getId() == 13 || g.getId() == 14) {
                        break;
                    }
                    s = lotteryCountService.onlyKind(gift.getType(), gift.getId(), user.getId(), activity.getId());
                    if (s > 0) {
                        cnt++;
                    } else {
                        break;
                    }
                }
                cnt++;
            } while (true);
            map.put("prizeName", gift.getName());//只返回奖品的名称
            map.put("id", gift.getId());//奖品id
            LotteryRecord record = new LotteryRecord();//中奖记录
            record.setGiftId(gift.getId());
            record.setIp(user.getLastLoginIp());
            record.setUserId(user.getId());
            record.setCreateDate(new Date());
            record.setActivityId(activity.getId());
            int lock = lotteryCountService.updateCount(user.getId());
            if (lock == 0) {
                throw new LockFailureException();
            }
            double sx = 0d;
            if (gift.getType() == 1) {// 奖品等于虚拟物品 立即派发
                int pan = gift.getId();
                if (pan == 11) {// 月加息券0.2% 默认为三天 有效时间 加息时间0一个月1三天2七天
                    rateCouponService.save(1, 9, 0.002, user.getId(), 0);
                } else if (pan == 12) {// 月加息券0.1% 默认为三天 加息时间0一个月1三天2七天
                    rateCouponService.save(1, 9, 0.001, user.getId(), 0);
                } else if (pan == 13) {// 日加息券0.5% 默认为三天 加息时间0一个月1三天2七天
                    rateCouponService.save(0, 9, 0.005, user.getId(), 0);
                } else if (pan == 14) {//随机体验金
                    Random random = new Random();
                    sx = random.nextInt(max) % (max - min + 1) + min;
//                    expMoneyService.save(user.getId(), sx, 8, null, 1);
                   // record.setExpAmount((int) sx);
                    map.put("expAmount", sx);
                }
                record.setStatus(2);//奖品已发放
            } else {
                record.setStatus(1);//奖品未发放
            }
            if (gift.getId() != null) {//中奖信息
                lotteryRecordMapper.insert(record);//保存中奖信息排除谢谢参与
                String title = "恭喜您抽中奖品！";
                String descript = "";
                switch (gift.getId()) {
                    case 11:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖励已派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 12:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖励已派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 13:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖励已派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 14:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到 体验金" + sx + "元！，奖励已派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 15:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 16:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 17:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 18:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 19:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 20:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    default:
                        break;
                }
                messageService.save(title, descript, user.getId());
            }
            // 抽奖完成后减少奖品表中的数量
            if (g.getLeftNum() > 0) {
                g.setLeftNum(g.getLeftNum() - 1);
                int locks = lotteryCountService.updateLotteryNum(g);
                if (locks == 0) {
                    throw new LockFailureException();
                }
            }
            map.put("type", gift.getType());//奖品类型  1是虚拟物品  已经立即派发,2是实物    奖品id=10  是谢谢参与
            result.put("success", map);
            result.put("status", 1);
        } else {
            result.put("noLottery", "没有抽奖次数！");
        }
        return result;
    }

    @Override
    public synchronized Map<String, Object> saveLotteryRecord(Activity activity, Integer userId, Integer giftId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //查询所有的已领取奖品
        List<LotteryGift> listGift = lotteryGiftService.getActivityGiftItems(activity.getId());
        int i = 0;
        map.put("flag", 0);
        for (LotteryGift lotteryGift : listGift) {
            if (lotteryGift.getId().equals(giftId)) {
                //Double totalIntegral = investmentService.selectActivityAndTime(userId, activity.getId(), activity.getStartTime(), activity.getEndTime());//剩余可用积分
            	Double totalIntegral = 0d;
            	if (lotteryGift.getAmount() > totalIntegral) {
                    map.put("flag", 1);
                    return map;//不能领取改奖励
                }
                map.put("type", lotteryGift.getType());
                map.put("name", lotteryGift.getName());
                map.put("url", "https://qmlc.oss-cn-hangzhou.aliyuncs.com/images/activity/201610/gold101/popupImg/prize@" + i + ".jpg");
            }
            i++;
        }
        String descript = "恭喜您在“疯狂的礼物！投资即送，奖品团团赚！”活动中成功领取" + map.get("name") + "一件！“排行榜”与“赢壕礼”奖品不重复领取，以实际获得的最高档位为准！奖品将于活动结束后15个工作日内为您派发！请您完善收货地址！";
        LotteryRecord lotteryRocord = new LotteryRecord();
        lotteryRocord.setGiftId(giftId);
        lotteryRocord.setCreateDate(new Date());
        lotteryRocord.setUserId(userId);
        lotteryRocord.setVersion(0);
        lotteryRocord.setActivityId(activity.getId());
        this.saveLottery(lotteryRocord);
        messageService.save("恭喜您获得奖励！", descript, userId);
        return map;
    }

    public synchronized Map<String, Object> saveLotteryRecord666(Integer userId, Integer giftId, String phone) throws LockFailureException {
        Map<String, Object> result = new HashMap<>();
        Activity activity = activityService.getByName(ActivityConstant.REGULAR_AWARD_666);
        Date now = new Date();
        Date endTime = DateFormatTools.jumpOneDay(activity.getEndTime(), 1);
        if (activity == null) {
            result.put("code", "0");
            result.put("msg", "活动不存在");
            return result;
        } else {
            if (now.before(endTime) && now.after(activity.getStartTime())) {
                //查询获奖奖品id
                LotteryGift gift = lotteryGiftMapper.selectByPrimaryKey(giftId);
                if (gift == null) {
                    result.put("code", "0");
                    result.put("msg", "您想领的奖品，不存在的亲！如有疑问请联系客服");
                    return result;
                }
                if (gift.getLeftNum() <= 0) {
                    result.put("code", "0");
                    result.put("msg", "[" + gift.getName() + "]奖品已经被领完，请选择其他奖品");
                    return result;
                }
                Map<String, Object> params = new HashMap<>();
                params.put("userId", userId);
                params.put("activityId", activity.getId());
                params.put("startTime", activity.getStartTime());
                params.put("endTime", activity.getEndTime());
                //获取活动期间的投资总额
                Double totalIntegral = investmentMapper.selectActivityYearAmount(params);
                if (gift.getAmount() > totalIntegral) {
                    result.put("code", "0");
                    result.put("msg", "奖品积分不够");
                    return result;
                }
                LotteryRecord lotteryRocord = new LotteryRecord();
                lotteryRocord.setGiftId(giftId);
                lotteryRocord.setCreateDate(new Date());
                lotteryRocord.setUserId(userId);
                lotteryRocord.setVersion(0);
                lotteryRocord.setActivityId(activity.getId());
                gift.setLeftNum(gift.getLeftNum() - 1);
                int ret = lotteryGiftMapper.updateByPrimaryKeySelectiveAndVersion(gift);
                if (ret != 1) {
                    throw new LockFailureException();
                }
                this.saveLottery(lotteryRocord);
                String descript = "";
                if (ActivityConstant.REGULAR_AWARD_666.equals(activity.getName())) { //666 0元购活动
                    if (gift.getType() == 1) {
                        descript = "亲爱的鑫聚财用户，恭喜您获得了6月666  " + gift.getName() + "一份，已发送到您的鑫聚财账户，在【我的福利】- 投资红包中显示，快去领取使用吧。";
                        String amount = gift.getName().substring(0, gift.getName().indexOf("元"));
                        Integer money = "36".equals(amount) ? 1666 : 2666;
                        hongbaoService.addToUser3(Double.parseDouble(amount), userId, "投资红包", 0, 2, 0, 30, money, "30", 2, 1, 15);
                    } else {
                        descript = "亲爱的鑫聚财用户，恭喜您获得了6月666  " + gift.getName() + "礼品一份，活动结束后会由客服人员与您联系，统一将于活动结束后10个工作日内送出，请保持电话畅通。";
                    }
                    try {
                        String content = descript + "【鑫聚财】";
                        //SendMessageUtils.send(content, phone);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
                messageService.save("恭喜您获得奖励！", descript, userId);
                result.put("code", "1");
                result.put("msg", "您已经成功领取奖品[" + gift.getName() + "]");
            } else {
                result.put("code", "0");
                result.put("msg", "不在活动期间内，请留意全民理财活动公告");
                return result;
            }
        }
        return result;
    }


    /**
     * 已注册用户抽奖
     *
     * @param user
     * @return
     * @throws ParseException
     * @author 王信
     * @Create Date: 2015年12月14日下午4:16:36
     */
    private synchronized Map<String, Object> saveLotteryRecordByUser(User user, Integer activityId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        LotteryCount lotteryCount = lotteryCountService.queryCountByUserId(user.getId());
        Integer count = 0;
        if (lotteryCount != null) {
            Integer exCount = lotteryCount.getExCount() == null ? 0 : lotteryCount.getExCount();
            Integer sysCount = lotteryCount.getSysCount() == null ? 0 : lotteryCount.getSysCount();
            count = exCount + sysCount;
        }
        if (count > 0) {//count>0  最少有一次
            Map<String, Object> map = new HashMap<String, Object>();
            //根据用户在投确定他抽奖的奖品种类
            Assets a = assetsService.findByuserId(user.getId());// 得到用户当前的投资详情
//            double amount = BigDecimalUtil.add(a.getAvailableBalance(), a.getHuoInvestmentAmount(), a.getUncollectCapital());//总资产
            String lv = LV0;
            //查询数据库中总资产前五十的的最后一位总资产
            double Ranking = assetsService.selectTotalAssetsRanking();
            //此处需要添加一个判断用户第一次抽奖时100%中奖
            int cs = lotteryCountService.queryRecordCount(user.getId(), activityId);
            if (cs != 0) {
                lv = LV1;
//                if (amount >= Ranking) {
//                    lv = LV2;
//                }
            }
            List<LotteryGift> list = lotteryCountService.listLotteryGift(lv);//返回奖品的集合  输入1不显示奖品抽奖概率   输入0  显示中奖概率
            LotteryGift gift = null;
            LotteryGift g = null;
            int s = 0;
            int cnt = 0;
            do {//每个用户中单个实物只能有一个
                if (cnt >= 5) {
                    g = lotteryGiftMapper.selectByPrimaryKey(14);
                    gift = g;
                    break;
                }
                gift = LotteryUtils.startLottery(list);//随机抽奖
                if (gift != null) {
                    //查询奖品库存
                    g = lotteryCountService.selectLotteryGift(gift.getId());
                    if (g.getLeftNum() <= 0) {//如果奖品抽完了  继续抽
                        list.remove(gift);
                        continue;
                    }
                    s = lotteryCountService.onlyKind(gift.getType(), gift.getId(), user.getId(), activityId);
                    if (s > 0) {
                        cnt++;
                    } else {
                        break;
                    }
                }
                cnt++;
            } while (true);
            map.put("prizeName", gift.getName());//只返回奖品的名称
            map.put("id", gift.getId());//奖品id
            LotteryRecord record = new LotteryRecord();//中奖记录
            record.setGiftId(gift.getId());
            record.setIp(user.getLastLoginIp());
            record.setUserId(user.getId());
            record.setCreateDate(new Date());
            record.setActivityId(activityId);
            lotteryCountService.updateCount(user.getId());
            double sx = 0d;
            if (gift.getType() == 1) {// 奖品等于虚拟物品 立即派发
                int pan = gift.getId();
                if (pan == 11) {// 月加息券0.2% 默认为三天 有效时间 加息时间0一个月1三天2七天
                    rateCouponService.save(1, 9, 0.002, user.getId(), 0);
                } else if (pan == 12) {// 月加息券0.1% 默认为三天 加息时间0一个月1三天2七天
                    rateCouponService.save(1, 9, 0.001, user.getId(), 0);
                } else if (pan == 13) {// 日加息券0.5% 默认为三天 加息时间0一个月1三天2七天
                    rateCouponService.save(0, 9, 0.005, user.getId(), 0);
                } else if (pan == 14) {//随机体验金
                    Random random = new Random();
                    sx = random.nextInt(max) % (max - min + 1) + min;
                    //expMoneyService.save(user.getId(), sx, 8, null, 1);
                   // record.setExpAmount((int) sx);
                    map.put("expAmount", sx);
                }
                record.setStatus(2);//奖品已发放
            } else {
                record.setStatus(1);//奖品未发放
            }
            if (gift.getId() != null) {//中奖信息
                lotteryRecordMapper.insert(record);//保存中奖信息排除谢谢参与
                String title = "恭喜您抽中奖品！";
                String descript = "";
                switch (gift.getId()) {
                    case 11:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖励已派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 12:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖励已派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 13:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖励已派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 14:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到 体验金" + sx + "元！，奖励已派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 15:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 16:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 17:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 18:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 19:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    case 20:
                        descript = "恭喜您在“全民理财周年庆”活动中抽到" + g.getName() + "，奖品将于活动结束后派发！抽奖次数越多，中奖几率越大！";
                        break;
                    default:
                        break;
                }
                messageService.save(title, descript, user.getId());
            }


            // 抽奖完成后减少奖品表中的数量
            if (g.getLeftNum() > 0) {
                g.setLeftNum(g.getLeftNum() - 1);
                lotteryCountService.updateLotteryNum(g);
            }
            map.put("type", gift.getType());//奖品类型  1是虚拟物品  已经立即派发,2是实物    奖品id=10  是谢谢参与
            result.put("success", map);
            result.put("status", 1);
        } else {
            result.put("noLottery", "没有抽奖次数！");
        }
        return result;
    }

    @Override
    public Map<String, Object> selectLotteryRecordByPhone(String phone) {
        return lotteryRecordMapper.selectLotteryRecordByPhone(phone);
    }

    @Override
    public List<Map<String, Object>> queryExportDetails(String status, Date date, Integer orderId, Integer activityId, Integer giftId, String account) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(status)) {
            map.put("status", status);
        }
        if (!StringUtils.isEmpty(account)) {
            map.put("account", account);
        }
        map.put("date", date);
        map.put("orderId", orderId);
        map.put("activityId", activityId);
        map.put("giftId", giftId);
        return lotteryRecordMapper.queryExportDetails(map);
    }

    @Override
    public synchronized Map<String, Object> saveAwardGift(String token, Integer giftId, String ip) throws ParseException {
        Map<String, Object> result = new HashMap<>();
        User user = userService.checkLogin(token);
        if (user == null) {
            result.put("code", "2");
            result.put("msg", "您未登录");
            return result;
        }
        String activityName = ActivityConstant.REGULAR_INVESTMENT_AWARD_GIFT;
        Activity activity = activityMapper.queryByName(activityName);
        Date now = new Date();
        Date endTime = DateFormatTools.jumpOneDay(activity.getEndTime(), 1);
        if (activity == null) {
            result.put("code", "0");
            result.put("msg", "活动不存在");
            return result;
        } else {
            if (now.before(endTime) && now.after(activity.getStartTime())) {
                //查询获奖奖品id
                LotteryGift gift = lotteryGiftMapper.selectByPrimaryKey(giftId);
                if (gift == null) {
                    result.put("code", "0");
                    result.put("msg", "您想领的奖品，不存在的亲！如有疑问请联系客服");
                    return result;
                }
                if (gift.getLeftNum() <= 0) {
                    result.put("code", "0");
                    result.put("msg", "[" + gift.getName() + "]奖品已经被领完，请选择其他奖品");
                    return result;
                }
                Map<String, Object> params = new HashMap<>();
                params.put("userId", user.getId());
//                params.put("giftId", giftId);
                params.put("grade", gift.getDescript());
                params.put("activityId", activity.getId());
                params.put("startTime", activity.getStartTime());
                params.put("endTime", endTime);
                //获取活动期间的投资总额和投资笔数
                Map<String, Object> investmentMap = investmentMapper.querySumRegularInvestmentOnActivityDay(params);
                Double sumAmount = Double.valueOf(investmentMap.get("sumAmount").toString());
                Set<String> gradeSet = new HashSet<>();
                final String NORMAL_GRADE = "normalGrade";
                final String HIGH_GRADE = "highGrade";
                final String FORBES_GRADE = "forbesGrade";
                //判断在指定投资区间内，能够获取奖品的
                if (sumAmount >= 3000 && sumAmount < 30000) {
                    gradeSet.add(NORMAL_GRADE);
                } else if (sumAmount >= 30000 && sumAmount < 60000) {
                    gradeSet.add(NORMAL_GRADE);
                    gradeSet.add(HIGH_GRADE);
                } else if (sumAmount >= 60000) {
                    gradeSet.add(NORMAL_GRADE);
                    gradeSet.add(HIGH_GRADE);
                    gradeSet.add(FORBES_GRADE);
                }
                if (!gradeSet.contains(gift.getDescript())) {
                    result.put("code", "0");
                    result.put("msg", "您还没有领取[" + gift.getName() + "]奖品的资格哦，请查看活动说明");
                    return result;
                }
                Boolean isAwarded = lotteryRecordMapper.queryAwardRecord(params);
                if (isAwarded) {
                    result.put("code", "0");
                    result.put("msg", "亲，您已经领过该阶段奖品，不能给您再多啦");
                    return result;
                } else {
                    LotteryRecord record = new LotteryRecord();
                    LotteryAddress lotteryAddress = lotteryAddressMapper.queryByUserId(user.getId());
                    if (lotteryAddress == null) {
                        result.put("hasAddress", false);
                    } else {
                        result.put("hasAddress", true);
                        record.setUserAddressId(lotteryAddress.getId());
                    }
                    record.setUserId(user.getId());
                    record.setActivityId(activity.getId());
                    record.setGiftId(giftId);
                    record.setCreateDate(now);
                    record.setCountType(1);
                    record.setStatus(1);
                    record.setIp(ip);
                    lotteryRecordMapper.insertSelective(record);
                    gift.setLeftNum(gift.getLeftNum() - 1);
                    lotteryGiftMapper.updateByPrimaryKeySelective(gift);
                    messageService.save("恭喜您获得奖品！", "恭喜您在全民理财“盛夏理财季”活动中获得" + gift.getName() + "，奖品将于活动结束后15个工作日内派发！请保持通讯畅通！", user.getId());
                    result.put("code", "1");
                    result.put("msg", "您已经成功领取奖品[" + gift.getName() + "]");
                    result.put("orderId", record.getId());
                }
            } else {
                result.put("code", "0");
                result.put("msg", "不在活动期间内，请留意全民理财活动公告");
                return result;
            }
        }

        return result;
    }

    @Override
    public synchronized Map<String, Object> saveAwadSoldActivity(String token, Integer giftId, String ip) throws LockFailureException {
        Map<String, Object> result = new HashMap<>();
        User user = userService.checkLogin(token);
        if (user == null) {
            result.put("code", "4");
            result.put("msg", "还没登录哦");
            return result;
        }
        String activityName = ActivityConstant.REGULAR_INVESTMENT_AWARD_GOLD;
        Activity activity = activityMapper.queryByName(activityName);
        Date now = new Date();
        Date endTime = DateFormatTools.jumpOneDay(activity.getEndTime(), 1);
        if (activity == null) {
            result.put("code", "0");
            result.put("msg", "活动不存在");
            return result;
        } else {
            if (now.before(endTime) && now.after(activity.getStartTime())) {
                //查询获奖奖品id
                LotteryGift gift = lotteryGiftMapper.selectByPrimaryKey(giftId);
                if (gift == null) {
                    result.put("code", "0");
                    result.put("msg", "您想领的奖品，不存在的亲！如有疑问请联系客服");
                    return result;
                }
                if (gift.getLeftNum() <= 0) {
                    result.put("code", "0");
                    result.put("msg", "[" + gift.getName() + "]奖品已经被领完，请选择其他奖品");
                    return result;
                }
                LotteryCount lotteryCount = lotteryCountService.queryCountByUserId(user.getId());
                if (lotteryCount == null) {
                    lotteryCount = new LotteryCount();
                    lotteryCount.setUserId(user.getId());
                    lotteryCount.setSysCount(0);
                    lotteryCount.setTime(now);
                    lotteryCount.setSysTime(now);
                    lotteryCount.setActivityId(activity.getId());
                    lotteryCountService.insertSelective(lotteryCount);
                    result.put("code", "3");
                    result.put("msg", "sorry，您不满足领取条件");
                    return result;
                }
                Map<String, Object> params = new HashMap<>();
                params.put("userId", user.getId());
                params.put("activityId", activity.getId());
                List<Map<String, Object>> list = lotteryRecordMapper.queryAllAwardRecord(params);
                //判断是否已经领取了3次机会
                if (list != null && list.size() >= 3) {
                    result.put("code", "2");
                    result.put("msg", "领奖次数用完啦！快去抢金条吧！");
                    return result;
                } else {
                    String awardQualifications = lotteryCount.getTempField();
                    String stepType = gift.getDescript();
                    if (StringUtils.isEmpty(awardQualifications) || !awardQualifications.contains("step")) {
                        result.put("code", "3");
                        result.put("msg", "sorry，您不满足领取条件");
                        return result;
                    }
                    int availableCount = RegularUtils.getSubStringCount("#", awardQualifications);
                    if (availableCount == 0) {
                        result.put("code", "3");
                        result.put("msg", "sorry，您不满足领取条件");
                        return result;
                    }
                    if (awardQualifications.contains(stepType)) {
                        awardQualifications = awardQualifications.replaceFirst(stepType, "");
                    } else {
                        String min = "";
                        String[] steps = awardQualifications.split("#");
                        //系统默认使用的机会为范围最小的机会 ：例如 如果有高、中档领取机会，我要领低档，实际使用的机会为领取中档机会
                        for (String step : steps) {
                            if (StringUtils.isEmpty(step)) {
                                continue;
                            }
                            if (step.compareTo(stepType) < 0) {
                                continue;
                            }
                            if (StringUtils.isEmpty(min)) {
                                min = step;
                                continue;
                            }
                            if (step.compareTo(min) <= 0) {
                                min = step;
                            }
                        }
                        awardQualifications = awardQualifications.replaceFirst(min, "");
                    }
                    if (lotteryCount.getTempField().equals(awardQualifications)) {
                        result.put("code", "3");
                        result.put("msg", "sorry，您不满足领取条件");
                        return result;
                    }
                    lotteryCount.setTempField(awardQualifications);
                    int lock = lotteryCountService.updateByPrimaryKeySelective(lotteryCount);
                    if (lock == 0) {
                        throw new LockFailureException();
                    }
                    LotteryRecord record = new LotteryRecord();
                    record.setGiftId(giftId);
                    record.setUserId(user.getId());
                    record.setCreateDate(now);
                    record.setIp(ip);
                    record.setCountType(1);
                    record.setActivityId(activity.getId());
                    lotteryRecordMapper.insertSelective(record);
                    gift.setLeftNum(gift.getLeftNum() - 1);
                    lotteryGiftMapper.updateByPrimaryKeySelective(gift);
                    Boolean hasAddress = userAddressService.queryHasAddress(user.getId());

                    messageService.save(
                            "恭喜您领取成功",
                            "恭喜您成功领取" + gift.getName() + "！参与奖和黄金榜奖不重复派发，即获得黄金榜奖项的用户不再派发参与奖。" +
                                    "请至“我”界面—设置—收货地址，完成收件信息填写。奖品将于活动结束后15个工作日内统一发放，请保持联系畅通。",
                            user.getId());
                    result.put("hasAddress", hasAddress);
                    result.put("msg", "恭喜您领取成功！还有" + (availableCount - list.size() - 1) + "次领奖机会！");
                    result.put("code", "1");
                }
            } else {
                result.put("code", "0");
                result.put("msg", "不在活动期间内，请留意全民理财活动公告");
                return result;
            }
        }
        return result;
    }

    public Map<String, Object> queryAwardRecord(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> awardList = lotteryRecordMapper.queryAllAwardRecord(map);
        Map<String, Object> userSumInvestment = investmentMapper.queryUserSumInvestment(map);
        LotteryCount lotteryCount = lotteryCountService.queryCountByUserId((Integer) map.get("userId"));
        int availableCount = 0;
        if (!(lotteryCount == null || StringUtils.isEmpty(lotteryCount.getTempField()))) {
            availableCount = RegularUtils.getSubStringCount("#", lotteryCount.getTempField());
        }
        if (availableCount == 0) {
            result.put("available", 0);
        } else {
            if (awardList != null && awardList.size() >= 3) {
                result.put("available", 0);
            } else {
                result.put("available", availableCount - awardList.size());
            }
        }
        result.put("awardRecordList", awardList);
        result.put("totalInvestment", userSumInvestment);
        return result;
    }


    public Map<String, Object> queryAwardRecordFilter(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> awardList = lotteryRecordMapper.queryLotteryRecord((Integer) map.get("userId"), (Integer) map.get("activityId"));
        //个人总投资和年化额
        List<Map<String, Object>> userSumInvestment = activityMapper.selectInvestmentActivityFilter(map);
        for (Map<String, Object> obj : userSumInvestment) {
            Integer userId = (Integer) obj.get("user_id");
            if (map.get("userId") != null) {
                if (userId.equals((Integer) map.get("userId"))) {
                    result.put("totalInvestment", obj.get("totalAmount"));
                    double yearAmount = Double.valueOf(obj.get("yearAmount").toString());
                    result.put("yearAmount", (int) yearAmount);
                }
            }
        }
        //可兑换额度
        double totalIntegral = investmentMapper.selectActivityYearAmount(map);//剩余可用积分(年化额)
        result.put("totalIntegral", totalIntegral <= 0 ? 0 : (int) totalIntegral);
        result.put("awardRecordList", awardList);
        return result;
    }

    @Override
    public List<Map<String, Object>> queryAllAwardRecord(Map<String, Object> map) {
        return lotteryRecordMapper.queryAllAwardRecord(map);
    }

    @Override
    public LotteryRecord queryUserIdByActivityId(Integer userId, Integer activityId) {
        LotteryRecordExample example = new LotteryRecordExample();
        LotteryRecordExample.Criteria c = example.createCriteria();
        c.andActivityIdEqualTo(activityId);
        c.andUserIdEqualTo(userId);
        List<LotteryRecord> list = lotteryRecordMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }


    @Override
    public void updateUserIdByActivityId(Integer userId, Integer activityId, String remark, String ip, Integer giftId) {
        LotteryRecord lotteryRecord = queryUserIdByActivityId(userId, activityId);
        if (giftId == null) {
            if (lotteryRecord == null) { //新增
                lotteryRecord = new LotteryRecord();
                lotteryRecord.setActivityId(activityId);
                lotteryRecord.setUserId(userId);
                lotteryRecord.setRemark(remark);
                lotteryRecord.setCreateDate(new Date());
                lotteryRecord.setIp(ip);
                lotteryRecordMapper.insertSelective(lotteryRecord);
            } else {//更新
                lotteryRecord.setRemark(remark);
                lotteryRecord.setIp(ip);
                lotteryRecord.setCreateDate(new Date());
                lotteryRecordMapper.updateByPrimaryKeySelective(lotteryRecord);
            }
        } else {
            if (lotteryRecord == null) { //新增
                lotteryRecord = new LotteryRecord();
                lotteryRecord.setActivityId(activityId);
                lotteryRecord.setUserId(userId);
                lotteryRecord.setCreateDate(new Date());
                lotteryRecord.setIp(ip);
                lotteryRecord.setGiftId(giftId);
                lotteryRecordMapper.insertSelective(lotteryRecord);
            } else {//更新
                if (lotteryRecord.getGiftId() == null || !lotteryRecord.getGiftId().equals(giftId) ) {
                    lotteryRecord.setGiftId(giftId);
                    lotteryRecord.setIp(ip);
                    lotteryRecord.setCreateDate(new Date());
                    lotteryRecordMapper.updateByPrimaryKeySelective(lotteryRecord);
                }
            }
        }

    }

    @Override
    public void deleteAllRecord(Integer userId, Integer activityid) {
        LotteryCount lotteryCount = lotteryCountService.saveOrQueryUserAndActivityCount(userId, activityid);
        int count = lotteryCount.getExCount() - 1;
        if (count < 0) {
            return;
        }
        lotteryCount.setExCount(count);
        lotteryCountService.updateByPrimaryKeySelective(lotteryCount);
        LotteryRecordExample lotteryRecordExample = new LotteryRecordExample();
        LotteryRecordExample.Criteria c = lotteryRecordExample.createCriteria();
        c.andActivityIdEqualTo(activityid);
        c.andUserIdEqualTo(userId);
        lotteryRecordMapper.deleteByExample(lotteryRecordExample);
    }

    public Map<String, Object> saveLotteryRecordRegist(Activity activity, Integer userId, Integer type) {

        return null;
    }

    public synchronized Map<String, Object> saveAllLotteryRecord(Integer userId, Activity activity) {
        Map result = new HashMap();
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            boolean b1 = rateCouponService.getCountBySource(userId, 72);
            boolean b2 = hongbaoService.getCountBySource(userId, 72);
            if (b1 && b2) {
                result.put("code", "0");
                result.put("msg", "您已经领取过了哦，下次活动再来吧...");
                return result;
            }
            if (!b1) {
                Date time = DateFormatTools.jumpOneDay(new Date(), 15);
                rateCouponService.save2(3, 72, "全民鑫礼包-加息券", 0.008d, userId, time, null);
                String descriptQuan = "亲爱的鑫聚财用户，恭喜您获得了“全民鑫礼包”0.8%加息券一份，已发送到您的鑫聚财账户，在【我的福利】-【加息券】中显示，快去领取使用吧。";
                messageService.save("恭喜您获得奖励！", descriptQuan, userId);
            }
            if (!b2) {
                hongbaoService.addToUser3(18.00, userId, "全民鑫礼包-投资红包", 72, 2, 0, 15, 6000, "90", 2, 1, 15);
                String descriptHong = "亲爱的鑫聚财用户，恭喜您获得了“全民鑫礼包”18元投资红包一份，已发送到您的鑫聚财账户，在【我的福利】-【投资红包】中显示，快去领取使用吧。";
                messageService.save("恭喜您获得奖励！", descriptHong, userId);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            lock.unlock();
        }
        result.put("code", "1");
        result.put("msg", "您已经成功领取奖品");
        result.put("name", "");
        return result;
    }

    public synchronized Map<String, Object> saveLotteryRecordJuly(Integer userId, Activity activity, String phone) throws LockFailureException {
//        Map result = Maps.newHashMap();
//        //获取活动期间的投资总额
//        Map<String, Object> amountMap = activityMapper.selectInvestmentAmount(userId, activity.getStartTime(), activity.getEndTime(), 15);  //投资金额
//        Double investmentAmount = (Double) amountMap.get("investmentAmount");
//        int lotteryCount = activityMapper.selectUserLottery(userId, activity.getId());   //抽奖次数
//        if (investmentAmount == null) {
//            investmentAmount = 0.00;
//        }
//        int leftCount = (int) (investmentAmount / 5000) - lotteryCount;//剩余抽奖次数
//        if (leftCount < 1) {
//            result.put("code", "0");
//            result.put("msg", "暂无抽奖次数");
//            return result;
//        }
//        List<LotteryGift> list = lotteryGiftMapper.getActivityLeftGiftItems(activity.getId(), null); //奖品列表
//        if (list == null || list.size() == 0) {
//            result.put("code", "0");
//            result.put("msg", "很抱歉奖品已经领完");
//            return result;
//        }
//        LotteryGift g = null;
//        LotteryGift gift = null;
//        do {
//            g = LotteryUtils.startLottery(list);//随机抽奖
//            if (g != null) {
//                //查询奖品库存
//                gift = lotteryCountService.selectLotteryGift(g.getId());
//                if (gift.getLeftNum() <= 0) {//如果奖品抽完了  继续抽
//                    list.remove(g);
//                    continue;
//                } else {
//                    System.out.println(g.getName() + ":" + g.getLeftNum());
//                    break;
//                }
//            }
//        } while (true);
//        if (list.size() == 0) {
//            result.put("code", "0");
//            result.put("msg", "很抱歉奖品已经领完");
//            return result;
//        }
//        g.setLeftNum(g.getLeftNum() - 1);
//        g.setVersion(g.getVersion());
//        int ret = lotteryGiftMapper.updateByPrimaryKeySelectiveAndVersion(g);
//        if (ret != 1) {
//            throw new LockFailureException();
//        }
//        LotteryRecord record = new LotteryRecord();//中奖记录
//        record.setGiftId(g.getId());
//        record.setTime(new Date());
//        record.setPhone(phone);
//        record.setStatus(2);//未注册用户默认为发送，注册完成同时，立即发送
//        record.setUserId(userId);
//        record.setVersion(0);
//        record.setActivityId(activity.getId());
//        this.saveLottery(record);
//        String amount = g.getDescript();
//        String descript = "";
//        if (g.getName().contains("投资红包")) {
//            descript = "亲爱的鑫聚财用户，恭喜您获得了“亿元鑫礼”" + g.getName() + "一份，已发送到您的鑫聚财账户，在【我的福利】-【投资红包】中显示，快去领取使用吧。";
//            Hongbao hongbao = null;
//            if (amount.equals("5")) {
//                hongbao = hongbaoService.addToUser3(5.00, userId, "亿元鑫礼-投资红包", 3, 2, 0, 15, 6000, "30", 2, 1, 15);
//            } else if (amount.equals("7")) {
//                hongbao = hongbaoService.addToUser3(7.00, userId, "亿元鑫礼-投资红包", 3, 2, 0, 15, 3000, "90", 2, 1, 15);
//            } else if (amount.equals("9")) {
//                hongbao = hongbaoService.addToUser3(9.00, userId, "亿元鑫礼-投资红包", 3, 2, 0, 15, 9000, "30", 2, 1, 15);
//            } else if (amount.equals("18")) {
//                hongbao = hongbaoService.addToUser3(18.00, userId, "亿元鑫礼-投资红包", 3, 2, 0, 15, 6000, "90", 2, 1, 15);
//            } else if (amount.equals("88")) {
//                hongbao = hongbaoService.addToUser3(88.00, userId, "亿元鑫礼-投资红包", 3, 2, 0, 15, 15000, "180", 2, 1, 15);
//            } else if (amount.equals("188")) {
//                hongbao = hongbaoService.addToUser3(188.00, userId, "亿元鑫礼-投资红包", 3, 2, 0, 15, 30000, "180", 2, 1, 15);
//            }
//        } else if (g.getName().contains("加息券")) {
//            descript = "亲爱的鑫聚财用户，恭喜您获得了“亿元鑫礼”" + g.getName() + "一份，已发送到您的鑫聚财账户，在【我的福利】-【加息券】中显示，快去领取使用吧。";
//            rateCouponService.save2(3, 73, "亿元鑫礼-加息券", Double.parseDouble(g.getDescript()), userId, DateFormatTools.jumpOneDay(new Date(), 15), null);
//        }
//        //保存记录
//        messageService.save("恭喜您获得奖励！", descript, userId);
//        result.put("code", "1");
//        result.put("msg", "您已经成功领取奖品");
//        result.put("name", g.getName());
//        return result;
        return null;
    }

    public synchronized Map<String, Object> saveLotteryRecordQixi(Integer userId, Activity activity, String phone) throws LockFailureException {
        Map result = Maps.newHashMap();
        LotteryGift g = null;
        LotteryGift gift = null;
        ActivityInvestAndLotteryVO v = activityService.getLotteryInfo(userId, activity.getId(), activity.getStartTime(), activity.getEndTime(), 30);
        int leftCount = (int) v.getYearAmount() / 7000;
        if (leftCount < 1) {
            result.put("code", "0");
            result.put("msg", "暂无抽奖次数");
            return result;
        }
        Double yearAmount = v.getYearAmount();
        List<LotteryGift> list = lotteryGiftMapper.getActivityLeftGiftItems(activity.getId(), 1); //奖品列表
        if (list == null || list.size() == 0) {
            result.put("code", "0");
            result.put("msg", "很抱歉奖品已经领完");
            return result;
        }
        int s = 0;
        int cnt = 1;
        do {    //门槛达到先抽先得
            if (cnt < 4 && yearAmount > 20000) {
                if (yearAmount >= 200000 && cnt == 1) {
                    g = lotteryCountService.selectLotteryGift(90);
                } else if (yearAmount >= 30000 && cnt <= 2) {
                    g = lotteryCountService.selectLotteryGift(89);
                } else if (yearAmount >= 20000 && cnt <= 3) {
                    g = lotteryCountService.selectLotteryGift(88);
                }
                //判断是否抽中实物
                s = lotteryCountService.onlyKind(g.getType(), g.getId(), userId, activity.getId());
                if (s > 0 || g.getLeftNum() <= 0) {
                    s = 1;
                    cnt++;
                    continue;
                } else {
                    break;
                }
            }
            g = LotteryUtils.startLottery(list);//随机抽奖
            if (g != null) {
                //查询奖品库存
                gift = lotteryCountService.selectLotteryGift(g.getId());
                if (gift.getLeftNum() <= 0) {//如果奖品抽完了  继续抽
                    list.remove(g);
                    s = 1;
                } else {
                    s = 0;
                    System.out.println(g.getName() + ":" + g.getLeftNum());
                }
            }
        } while (s != 0);
        g.setLeftNum(g.getLeftNum() - 1);
        g.setVersion(g.getVersion());
        int ret = lotteryGiftMapper.updateByPrimaryKeySelectiveAndVersion(g);
        if (ret != 1) {
            throw new LockFailureException();
        }
        LotteryRecord record = new LotteryRecord();//中奖记录
        record.setGiftId(g.getId());
        record.setCreateDate(new Date());
        record.setPhone(phone);
        record.setStatus(2);//未注册用户默认为发送，注册完成同时，立即发送
        record.setUserId(userId);
        record.setVersion(0);
        record.setActivityId(activity.getId());
        this.saveLottery(record);
        Double amount = g.getAmount();
        String descript = "";
        Hongbao hongbao = null;
        if (g.getDescript().equals("立即到账")) {
            if (g.getName().contains("投资红包")) {
                //descript = SmsUtils.getName("virtual").replace("name", g.getName()).replace("descript", "投资红包");
                if (amount == 3d) {
                    hongbao = hongbaoService.addToUser3(amount, userId, "鹊桥喜相送-投资红包", 3, 2, 0, 15, 5000, "30", 2, 1, 15);
                } else if (amount == 7d) {
                    hongbao = hongbaoService.addToUser3(amount, userId, "鹊桥喜相送-投资红包", 3, 2, 0, 15, 4000, "90", 2, 1, 15);
                } else if (amount == 19d) {
                    hongbao = hongbaoService.addToUser3(amount, userId, "鹊桥喜相送-投资红包", 3, 2, 0, 15, 5000, "180", 2, 1, 15);
                } else if (amount == 57d) {
                    hongbao = hongbaoService.addToUser3(amount, userId, "鹊桥喜相送-投资红包", 3, 2, 0, 15, 10000, "270", 2, 1, 15);
                }
            } else if (g.getName().contains("现金红包")) {
                //descript = SmsUtils.getName("virtual").replace("name", g.getName()).replace("descript", "现金红包");
                if (amount == 2d) {
                    hongbao = hongbaoService.addToUser(2.0, userId, descript, "鹊桥喜相送-现金红包", 1, 1, 1, null, 15);
                } else if (amount == 3d) {
                    hongbao = hongbaoService.addToUser(3.0, userId, descript, "鹊桥喜相送-现金红包", 1, 1, 1, null, 15);
                } else if (amount == 5d) {
                    hongbao = hongbaoService.addToUser(5.0, userId, descript, "鹊桥喜相送-现金红包", 1, 1, 1, null, 15);
                }
            } else if (g.getName().contains("加息券")) {
                //descript = SmsUtils.getName("virtual").replace("name", g.getName()).replace("descript", "加息券");
                rateCouponService.save2(3, 2, "鹊桥喜相送-加息券", g.getAmount() / 100, userId, DateFormatTools.jumpOneDay(new Date(), 15), null);
            }
        } else {
            //descript = SmsUtils.getName("real").replace("name", g.getName());
            System.out.println(descript);
        }
        messageService.save("恭喜您获得" + g.getName() + "奖励！", descript, userId);
        try {
            //SendMessageUtils.send(descript + "【鑫聚财】", phone);
        } catch (Exception e) {
            logger.info(ActivityConstant.REGULAR_AWARD_QIXI + phone + "发送短信异常" + e);
        }
        result.put("code", "1");
        result.put("msg", "您已经成功领取奖品");
        result.put("name", g.getName());
        result.put("id", g.getId());
        return result;
    }

    public synchronized Map<String, Object> saveLotteryRecordOctober1(Integer userId, String phone, Integer activityId) throws LockFailureException {
        Map<String, Object> result = Maps.newHashMap();
        LotteryGift gift = null;
        // 根据user_id 查询出用户抽奖次数记录 如果记录表中没有这个数据那么就是活动期间新增用户
        int count = lotteryRecordMapper.saveOrselectActivityLotteryCount(activityId, userId);
        if (count > 0) {
            result.put("code", "0");
            result.put("msg", "您已经领过啦！");
            return result;
        }
        int s = 1;
        List<LotteryGift> list = lotteryGiftMapper.getActivityLeftGiftItems(activityId, 1);
        LotteryGift g = null;
        do {
            g = LotteryUtils.startLottery(list);
            if (g != null) {
                //查询奖品库存
                gift = lotteryCountService.selectLotteryGift(g.getId());
                if (gift.getLeftNum() <= 0) {//如果奖品抽完了  继续抽
                    list.remove(g);
                    s = 1;
                } else {
                    s = 0;
                    System.out.println(g.getName() + ":" + g.getLeftNum());
                }
            }
        } while (s != 0);
        g.setLeftNum(g.getLeftNum() - 1);
        g.setVersion(g.getVersion());
        int ret = lotteryGiftMapper.updateByPrimaryKeySelectiveAndVersion(g);
        if (ret != 1) {
            throw new LockFailureException();
        }
        LotteryRecord lotteryRocord = new LotteryRecord();
        lotteryRocord.setGiftId(g.getId());
        lotteryRocord.setCreateDate(new Date());
        lotteryRocord.setUserId(userId);
        lotteryRocord.setVersion(0);
        lotteryRocord.setActivityId(activityId);
        lotteryRocord.setCountType(0);
        this.saveLottery(lotteryRocord);
        String descript = "";
        if (g.getName().contains("现金")) {
            //descript = SmsUtils.getName("virtual1").replace("activityName", "盛世中华").replace("name", g.getName()).replace("descript", "现金红包");
            hongbaoService.addToUser(Double.valueOf(g.getDescript()), userId, "盛世中华-现金红包", "盛世中华-现金红包", 1, 1, 1, null, 15);
        } else if (g.getName().contains("红包")) {
            //descript = SmsUtils.getName("virtual1").replace("activityName", "盛世中华").replace("name", g.getName()).replace("descript", "投资红包");
            if (g.getDescript().contains("2")) {
                hongbaoService.addToUser3(2.0, userId, "盛世中华-投资红包", 3, 2, 0, 15, 3000, "30", 2, 1, 15);
            } else if (g.getDescript().contains("5")) {
                hongbaoService.addToUser3(5.0, userId, "盛世中华-投资红包", 3, 2, 0, 15, 6000, "30", 2, 1, 15);
            } else if (g.getDescript().contains("10")) {
                hongbaoService.addToUser3(10.0, userId, "盛世中华-投资红包", 3, 2, 0, 15, 3500, "90", 2, 1, 15);
            } else if (g.getDescript().contains("18")) {
                hongbaoService.addToUser3(18.0, userId, "盛世中华-投资红包", 3, 2, 0, 15, 7000, "90", 2, 1, 15);
            }
        } else if (g.getName().contains("加息券")) {
            //descript = SmsUtils.getName("virtual1").replace("activityName", "盛世中华").replace("name", g.getName()).replace("descript", "加息券");
            rateCouponService.save2(3, 2, "盛世中华-加息券", Double.valueOf(g.getDescript()) / 100, userId, DateFormatTools.jumpOneDay(new Date(), 15), null);
        }
        try {
            String content = descript + "回TD退订【鑫聚财】";
            //SendMessageUtils.send(content, phone);
        } catch (Exception e) {
            logger.error(e);
        }
        messageService.save("恭喜您获得奖励！", descript, userId);
        result.put("code", "1");
        result.put("msg", g.getName());
        return result;
    }


    public synchronized Map<String, Object> saveLotteryRecordOctober2(Integer userId, String phone, Activity
            activity, Integer giftId) throws LockFailureException {
        Date date = new Date();
        Map<String, Object> result = Maps.newHashMap();
        //查询获奖奖品id
        LotteryGift gift = lotteryGiftMapper.selectByPrimaryKey(giftId);
        if (gift == null) {
            result.put("code", "0");
            result.put("msg", "您想领的奖品，不存在的亲！如有疑问请联系客服");
            return result;
        }
        if (gift.getLeftNum() <= 0) {
            result.put("code", "0");
            result.put("msg", "[" + gift.getName() + "]奖品已经被领完，请选择其他奖品");
            return result;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("activityId", activity.getId());
        params.put("startTime", activity.getStartTime());
        params.put("endTime", activity.getEndTime());
        //获取活动期间的投资总额
        Map<String, Object> m = activityMapper.selectInvestmentAmount2(userId, date, date, 30);

        ActivityInvestAndLotteryVO v = activityService.getLotteryInfo(userId, activity.getId(), activity.getStartTime(), activity.getEndTime(), 30);
        double leftYearAmount = (int) (v.getYearAmount() - v.getLotteryAmount());
        if (gift.getAmount() > leftYearAmount) {
            result.put("code", "0");
            result.put("msg", "您的累计年化额不够");
            return result;
        }
        LotteryRecord lotteryRocord = new LotteryRecord();
        lotteryRocord.setGiftId(giftId);
        lotteryRocord.setCreateDate(new Date());
        lotteryRocord.setUserId(userId);
        lotteryRocord.setVersion(0);
        lotteryRocord.setActivityId(activity.getId());
        gift.setLeftNum(gift.getLeftNum() - 1);
        int ret = lotteryGiftMapper.updateByPrimaryKeySelectiveAndVersion(gift);
        if (ret != 1) {
            throw new LockFailureException();
        }
        this.saveLottery(lotteryRocord);
        String descript = "";
        if (gift.getName().contains("现金红包")) {
            //descript = SmsUtils.getName("virtual1").replace("activityName", "盛世中华").replace("name", gift.getName()).replace("descript", "现金红包");
            hongbaoService.addToUser(100.0, userId, descript, "盛世中华-现金红包", 1, 1, 1, null, 15);
        } else if (gift.getName().contains("投资红包")) {
            //descript = SmsUtils.getName("virtual1").replace("activityName", "盛世中华").replace("name", gift.getName()).replace("descript", "投资红包");
            hongbaoService.addToUser3(100.0, userId, "盛世中华-投资红包", 72, 2, 0, 15, 20000, "90", 2, 1, 15);
        } else {
            //descript = SmsUtils.getName("real1").replace("activityName", "盛世中华").replace("name", gift.getName());
        }
        try {
            String content = descript + "【鑫聚财】";
            //SendMessageUtils.send(content, phone);
        } catch (Exception e) {
            logger.error(e);
        }
        messageService.save("恭喜您获得奖励！", descript, userId);
        result.put("code", "1");
        result.put("msg", gift.getName());
        return result;
    }

    public synchronized Map<String, Object> saveLotteryDecember(Integer userId, Activity activity, String phone) throws LockFailureException {
        Map result = Maps.newHashMap();
        Date date = new Date();
        LotteryGift gift;
        LotteryGift g = null;
        ActivityInvestAndLotteryVO v = activityService.getLotteryInfo(userId, activity.getId(), date, date, 30);
        int leftCount = (int) (v.getInvestAmount() / 8000) - v.getLotteryCount();//剩余抽奖次数
        if (leftCount < 1) {
            result.put("code", "0");
            result.put("msg", "暂无抽奖次数");
            return result;
        }
        Map<String, Object> m = activityMapper.selectInvestmentAmount2(userId, activity.getStartTime(), activity.getEndTime(), 30);
        Double yearAmount = (double) m.get("yearAmount");
        List<LotteryGift> list = lotteryGiftMapper.getActivityLeftGiftItems(activity.getId(), 1); //奖品列表
        int s = 0;
        LotteryRecord lr = null;
        //开始抽奖,门槛达到先抽先得
        if (yearAmount > 500000) {
            //查询用户是否抽过奖
            lr = queryByActivityGiftId(userId, activity.getId(), 130);
            if (lr == null) {
                g = lotteryGiftMapper.selectByPrimaryKey(130);
            }
        }
        if ((g == null || g.getLeftNum() <= 0) && yearAmount > 50000) {
            lr = queryByActivityGiftId(userId, activity.getId(), 129);
            if (lr == null) {
                g = lotteryGiftMapper.selectByPrimaryKey(129);
            }
        }
        if ((g == null || g.getLeftNum() <= 0) && yearAmount > 30000) {
            lr = queryByActivityGiftId(userId, activity.getId(), 128);
            if (lr == null) {
                g = lotteryGiftMapper.selectByPrimaryKey(128);
            }
        }
        if (g == null || g.getLeftNum() <= 0) {
            do {
                g = LotteryUtils.startLottery(list);//随机抽奖
                //查询奖品库存
                gift = lotteryCountService.selectLotteryGift(g.getId());
                System.out.println(gift.getName() + "==================" + gift.getLeftNum());
                if (gift.getLeftNum() <= 0) {//如果奖品库存为0或者已经抽过奖了
                    list.remove(g);
                    s = 0;
                } else {
                    s = 1;
                }
            } while (s == 0);
        }
        g.setLeftNum(g.getLeftNum() - 1);
        g.setVersion(g.getVersion());
        int ret = lotteryGiftMapper.updateByPrimaryKeySelectiveAndVersion(g);
        if (ret != 1) {
            throw new LockFailureException();
        }
        LotteryRecord record = new LotteryRecord();//中奖记录
        record.setGiftId(g.getId());
        record.setCreateDate(new Date());
        record.setPhone(phone);
        record.setStatus(2);//未注册用户默认为发送，注册完成同时，立即发送
        record.setUserId(userId);
        record.setVersion(0);
        record.setActivityId(activity.getId());
        record.setGiftName(g.getName());
        this.saveLottery(record);
        Double amount = g.getAmount();
        String descript = "";
        Hongbao hongbao = null;
        if (g.getDescript().equals("立即到账")) {
            if (g.getName().contains("投资红包")) {
                //descript = SmsUtils.getName("virtual1").replace("activityName", "幸运双12").replace("name", g.getName()).replace("descript", "投资红包");
                if (amount == 6d) {
                    hongbao = hongbaoService.addToUser3(amount, userId, "幸运双12-投资红包", 3, 2, 0, 15, 7000, "30", 2, 1, 15);
                } else if (amount == 12d) {
                    hongbao = hongbaoService.addToUser3(amount, userId, "幸运双12-投资红包", 3, 2, 0, 15, 5000, "90", 2, 1, 15);
                } else if (amount == 21d) {
                    hongbao = hongbaoService.addToUser3(amount, userId, "幸运双12-投资红包", 3, 2, 0, 15, 8000, "90", 2, 1, 15);
                } else if (amount == 42d) {
                    hongbao = hongbaoService.addToUser3(amount, userId, "幸运双12-投资红包", 3, 2, 0, 15, 8000, "180", 2, 1, 15);
                }
            } else if (g.getName().contains("现金红包")) {
                //descript = SmsUtils.getName("virtual1").replace("activityName", "幸运双12").replace("name", g.getName()).replace("descript", "现金红包");
                hongbao = hongbaoService.addToUser(amount, userId, descript, "幸运双12-现金红包", 1, 1, 1, null, 15);
            } else if (g.getName().contains("加息券")) {
                //descript = SmsUtils.getName("virtual1").replace("activityName", "幸运双12").replace("name", g.getName()).replace("descript", "加息券");
                rateCouponService.save2(3, 2, "幸运双12-加息券", g.getAmount() / 100, userId, DateFormatTools.jumpOneDay(new Date(), 15), null);
            }
        } else {
            //descript = SmsUtils.getName("real1").replace("activityName", "幸运双12").replace("name", g.getName());
            System.out.println(descript);
        }
        messageService.save("恭喜您获得" + g.getName() + "奖励！", descript, userId);
//        try {
//            SendMessageUtils.send(descript + "回TD退订【鑫聚财】", phone);
//        } catch (Exception e) {
//            LogUtil.infoLogs(ActivityConstant.REGULAR_AWARD_QIXI + phone + "发送短信异常" + e);
//        }
        result.put("code", "1");
        result.put("msg", "您已经成功领取奖品");
        result.put("name", g.getName());
        result.put("id", g.getId());
        return result;
    }

    /**
     *  根据用户id和活动id查询抽奖记录
     * @param userId 用户id
     * @param activityId 活动id
     * @return int
     */
    @Override
    public int findCountByUserIdAndActivityId(int userId,List<Integer>activityId) {

        return lotteryRecordMapper.findCountByUserIdAndActivityId(userId,activityId);
    }

    public LotteryRecord queryByActivityGiftId(Integer userId, Integer activityId, Integer giftId) {
        LotteryRecordExample example = new LotteryRecordExample();
        LotteryRecordExample.Criteria c = example.createCriteria();
        c.andActivityIdEqualTo(activityId);
        c.andUserIdEqualTo(userId);
        c.andGiftIdEqualTo(giftId);
        List<LotteryRecord> list = lotteryRecordMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

}
