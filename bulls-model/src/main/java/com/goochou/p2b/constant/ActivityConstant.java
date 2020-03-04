package com.goochou.p2b.constant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.goochou.p2b.utils.DateFormatTools;

/**
 * ActivityConstant
 * 活动相关常量
 *
 * @date 2016/4/15
 */
public class ActivityConstant {


    public static final String MESSAGE_TITLE = "恭喜您获得奖励";

    public static final String YAOYIYAO = "摇一摇";
    public static final String REGULAR_NEW_REGIST = "新手福利";
    public static final String REGULAR_NEW_REGIST2 = "用户第一次绑卡送35元投资定期红包";
    public static final String REGULAR_NEW_REGIST3 = "用户单出借资大于2000元赠送60,80,160投资定期红包";
    public static final String INVITE_FRIENDS_HONGBAO = "您赚1%的佣金,好友得350元红包";
    public static final String REGULAR_INVESTMENT_GIVE_EXPMONEY = "春暖花开好时光  现金收益送三天";
    public static final String REGULAR_INVESTMENT_GET_MONEY = "投资送加息券";
    public static final String REGULAR_INVESTMENT_GIVE_RATE = "推荐返现5%！我的朋友超有“财”！";
    public static final String REGULAR_INVESTMENT_GIVE_NOOB = "新人盛宴，16000体验金+0.3%加息特权";
    public static final String REGULAR_INVESTMENT_0803 = "冰爽8月，100000元京东卡享不停！";
    public static final String REGULAR_INVESTMENT_0812 = "尊贵之约，如“7”而至！投资即送iPhone7！";
    public static final String REGULAR_INVESTMENT_0422 = "定期也加息  奖励不能停";
    public static final String REGULAR_INVESTMENT_0422_85 = "0.085";
    public static final String REGULAR_INVESTMENT_0422_90 = "0.09";
    public static final String REGULAR_INVESTMENT_0422_95 = "0.095";
    public static final String REGULAR_INVESTMENT_LOTTERY = "周年庆，全民狂欢节，驾着飞机过五一";//2016-04-19  抽奖活动
    public static final String REGULAR_INVESTMENT_AWARD_GIFT = "五月理财季，四大冰爽礼";
    public static final String REGULAR_INVESTMENT_AWARD_GOLD = "投资赢壕礼,全民抢黄金";
    public static final String NEW_USER_HONGBAO_RATECOUPON = "新手壕礼，尊享350元红包+0.75%加息！";
    public static final String REGULAR_90_INVESTMENT_0903 = "apple狂欢季，90天项目专区！";
    public static final String REGULAR_180_INVESTMENT_0903 = "apple狂欢季，180天项目专区！";
    public static final String REGULAR_INVESTMENT_WEEKLY = "周末专享，50元京东卡任意拿！";
    public static final String REGULAR_INVESTMENT_JINGDONG = "50元京东卡任意拿！";
    public static final String REGULAR_INVESTMENT_HONGBAO = "红包第三弹！3125元投资红包强势来袭！";
    public static final String REGULAR_INVESTMENT_GRAB = "抢标有“礼”，标标返现无极限！";
    public static final String REGULAR_AWARD_GOLD = "黄金国庆，拿金条，赢壕礼！";
    public static final String REGULAR_AWARD_666 = "6月666 清爽好礼0元购";
    public static final String REGULAR_AWARD_CANON = "疯狂的礼物！投资即送，奖品团团赚！";
    public static final String REGULAR_AWARD_HALLOWEEN = "万圣大狂欢 聚财又送礼";
    public static final String REGULAR_AWARD_SINGLES_DAY = "11.11狂欢节，亿元现金大派送";
    public static final String REGULAR_AWARD_THANKSGING_DAY = "100倍收益感恩回馈";
    public static final String REGULAR_AWARD_DOULE_EGGS = "圣诞元旦，狂欢巨惠";
    public static final String REGULAR_AWARD_NEW_YEAR = "金鸡献瑞，喜迎新年";
    public static final String REGULAR_AWARD_VALENTINES_DAY = "以爱之名，浪漫有“礼”";
    public static final String TWO_YEARS_VALENTINES_DAY = "2周年壕礼迎新！新手即送5000元体验金+独享2%加息！";
    public static final String TWO_YEARS_THANKS_VALENTINES_DAY = "感恩2周年，加息券、体验金人人大狂欢！";
    public static final String XINJUCAI_REGIST = "鑫聚财 送鑫礼";
    public static final String XINJUCAI_REGIST_JULY = "集结10万英豪 亿元“鑫”礼从天而降";
    public static final String REGULAR_AWARD_QIXI = "鹊桥喜相送 你约会来我买单";
    public static final String REGULAR_AWARD_OCTOBER = "盛世中华 百万欢庆福利不止";
    public static final String REGULAR_AWARD_NOVERBER = "收益双11";
    public static final String REGULAR_AWARD_DECEMBER = "幸运双12  iPhone8等你来抽";
    public static final String REGULAR_AWARD_CHRISTMAS = "浪漫冬日  让爱升温";
	public static final String REGULAR_AWARD_NEWYEAR = "千万鑫礼  欢度元旦";
    public static final String HONGBAO_RAIN = "18红包雨";
    public static final String CURRENT_TRANSFER_REGULAR = "燃爆五月 疯狂返现";
    public static final String THIRD_ANNIVERSARY_ACTIVITY = "3周年 投资享好礼";
    public static final String CURRENT_TRANSFER_REGULAR_NEW = "全站限时狂欢  最高惊喜4%";
    public static final String SURPRISE_GIFT= "惊喜豪礼 玩“赚”一夏";
    public static final String AUGUST_GIFT = "缤纷八月 豪礼相伴";
    public static final String ASSISTANCE = "好友助力大比拼";
    public static final String NEW_YEAR_2020 = "春节集五牛";
    
    public static final Integer CODE_PER_PRICE = 1000;
    
    public static final Double HONGBAO_RAIN_SHARE_AWARD_AMOUNT = 18d;

    /**
     * @Description 判断是否在活动时间内
     * @author zxx
     * @date 2016/10/27
     * @params
     **/
    public static boolean isInActivity(Date startTime, Date endTime) {
        Date date = new Date();
        if (date.after(startTime) && date.before(DateFormatTools.jumpOneDay(endTime, 1))) {
            return true;
        }
        return false;
    }
}
