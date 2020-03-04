package com.goochou.p2b.utils;

import com.goochou.p2b.model.LotteryGift;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 不同概率抽奖工具包
 *
 * @author Shunli
 */
public class LotteryUtils {
    /**
     * 返回抽奖的奖品
     *
     * @param list
     * @return
     * @author 王信
     * @Create Date: 2015年12月4日上午11:28:22
     */
    public static LotteryGift startLottery(List<LotteryGift> list) {
        List<Double> orignalRates = new ArrayList<Double>(list.size());
        for (LotteryGift gift : list) {
            double rate = gift.getRate();
            if (rate < 0) {
                rate = 0;
            }
            orignalRates.add(rate);
        }
        return list.get(lottery(orignalRates));
    }

    /**
     * 返回一个固定电话号码段 不同随机奖品的字符串
     *
     * @return
     * @author 王信
     * @Create Date: 2015年12月11日上午11:29:36
     */
    public static String lotteryString(List<Map<String, Object>> list) {//159,138,137,139,158,177
        String sb = "";
        for (Map<String, Object> map : list) {
            String s = map.get("phone").toString();
            String str = s.substring(0, 3) + "****" + s.substring(7, 11);
            String ss = map.get("name").toString();
            sb = sb + "恭喜\"" + str + "\"用户" + "抽中\"" + ss + "\";";
        }
        return sb;
    }

    /**
     * 抽奖,返回物品的索引
     *
     * @param orignalRates 原始的概率列表，保证顺序和实际物品对应
     * @return 物品的索引
     */
    public static int lottery(List<Double> orignalRates) {
        if (orignalRates == null || orignalRates.isEmpty()) {
            return -1;
        }

        int size = orignalRates.size();

        // 计算总概率，这样可以保证不一定总概率是1
        double sumRate = 0d;
        for (double rate : orignalRates) {
            sumRate += rate;
        }

        // 计算每个物品在总概率的基础下的概率情况
        List<Double> sortOrignalRates = new ArrayList<Double>(size);
        Double tempSumRate = 0d;
        for (double rate : orignalRates) {
            tempSumRate += rate;
            sortOrignalRates.add(tempSumRate / sumRate);
        }

        // 根据区块值来获取抽取到的物品索引
        double nextDouble = Math.random();
        sortOrignalRates.add(nextDouble);
        Collections.sort(sortOrignalRates);

        return sortOrignalRates.indexOf(nextDouble);
    }

    public static void main(String[] args) {

    }

}