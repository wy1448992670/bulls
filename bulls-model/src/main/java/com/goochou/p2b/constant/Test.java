package com.goochou.p2b.constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Test {

	    /**
	     * 抽奖，获取中奖奖品
	     * @param lotteryList 奖品及中奖概率列表
	     * @return 中奖商品
	     * @throws Exception 
	     */
	    public static ProbabilityBullsEnum lottery(List<ProbabilityBullsEnum> lotteryList) throws Exception {
	        if(lotteryList.isEmpty()){
	            throw new Exception("");
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

	    public static void main(String[] args) throws Exception {
	    	List<ProbabilityBullsEnum> list = ProbabilityBullsEnum.getValueByType(1);
	        Map<String,Integer> result = new HashMap<String,Integer>();
	        for(int i = 0; i < 10000; i++){
	        	ProbabilityBullsEnum award = lottery(list);
	            String title = award.getLotteryBulls().getDescription();
	            Integer count = result.get(title);
	            result.put(title, count == null ? 1 : count + 1);
	        }

	        for (Entry<String, Integer> entry : result.entrySet()) {
	            System.out.println(entry.getKey() + ", count=" + entry.getValue() +", reate="+ entry.getValue()/10000d);
	        }
	    }
}
