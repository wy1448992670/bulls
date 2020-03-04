package com.goochou.p2b.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.goochou.p2b.constant.ProbabilityBullsEnum;


public class ProbabilityBullsUtil {
	/**
	 * @desc 抽奖
	 * @author wangyun
	 * @param lotteryList
	 * @return
	 * @throws Exception
	 */
    public static ProbabilityBullsEnum lottery(List<ProbabilityBullsEnum> lotteryList) throws Exception {
        if(lotteryList.isEmpty()){
            throw new Exception("概率为空");
        }
        //奖品总数
        int size = lotteryList.size();

        //计算总概率
        double sumProbability = 1d;
        
        //计算每个奖品的概率区间
        //例如奖品A概率区间0-0.1  奖品B概率区间 0.1-0.5 奖品C概率区间0.5-1
        //每个奖品的中奖率越大，所占的概率区间就越大
        List<Double> sortAwardProbabilityList = new ArrayList<Double>(size);
        Double tempSumProbability = 0d;
        
        for (ProbabilityBullsEnum lottery : lotteryList) {
            tempSumProbability += lottery.getProbability();
            sortAwardProbabilityList.add(tempSumProbability / sumProbability);
        }

        //产生0-1之间的随机数
        //随机数在哪个概率区间内，则是哪个奖品
        double randomDouble = Math.random();
        //加入到概率区间中，排序后，返回的下标则是awardList中中奖的下标
        sortAwardProbabilityList.add(randomDouble);
        Collections.sort(sortAwardProbabilityList);
        int lotteryIndex = sortAwardProbabilityList.indexOf(randomDouble);
        return lotteryList.get(lotteryIndex);
    }

}
