/**
 * 
 */
package com.goochou.p2b.constant;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.goochou.p2b.utils.BeanToMapUtil;

import static com.goochou.p2b.constant.LotteryBullsTypeEnum.*;
/**
 * 
 * @author wangyun
 *
 */
public enum ProbabilityBullsEnum {
	/**
	 * 	HEALTH_BULLS
	HAPPY_BULLS	
	SAFETY_BULLS
	LUCKY_BULLS	
	BENFU_BULLS	
	GREATE_BULLS

	 */
	// 前期 2020年1月10日00：00 至1月16日23：59
	HEALTH_BULLS_BEFORE(1, 0.2, HEALTH_BULLS),
	HAPPY_BULLS_BEFORE(1, 0.2, HAPPY_BULLS),
	SAFETY_BULLS_BEFORE(1, 0.15, SAFETY_BULLS),
	LUCKY_BULLS_BEFORE(1, 0.05, LUCKY_BULLS),
	BENFU_BULLS_BEFORE(1, 0.03, BENFU_BULLS),
	NOTHING_BEFORE(1, 0.37, NOTHING),
	
	// 中期 2020年1月17日00：00至1月20日23：59
	HEALTH_BULLS_MID(2, 0.2, HEALTH_BULLS),
	HAPPY_BULLS_MID(2, 0.2, HAPPY_BULLS),
	SAFETY_BULLS_MID(2, 0.15, SAFETY_BULLS),
	LUCKY_BULLS_MID(2, 0.1, LUCKY_BULLS),
	BENFU_BULLS_MID(2, 0.06, BENFU_BULLS),
	NOTHING_MID(2, 0.29, NOTHING),
	
	// 后期 2020年1月21日00：00至活动结束
	HEALTH_BULLS_LATER(3, 0.2, HEALTH_BULLS),
	HAPPY_BULLS_LATER(3, 0.2, HAPPY_BULLS),
	SAFETY_BULLS_LATER(3, 0.15, SAFETY_BULLS),
	LUCKY_BULLS_LATER(3, 0.15, LUCKY_BULLS),
	BENFU_BULLS_LATER(3, 0.08, BENFU_BULLS),
	NOTHING_LATER(3, 0.22, NOTHING);
	
	/**
     * 类型
     */
    private int type;
    /**
     *  概率
     */
    private Double probability;
    /**
     *  名称
     */
    private LotteryBullsTypeEnum lotteryBulls;
     
	 
    /**
     * 
     * @param type
     * @param probability
     * @param name
     * @param description
     */
    ProbabilityBullsEnum(int type,double probability, LotteryBullsTypeEnum LotteryBulls){
        this.type = type;
        this.probability = probability;
        this.lotteryBulls = LotteryBulls; 
    }
    
    public static List<ProbabilityBullsEnum> getValueByType(int type){
    	List<ProbabilityBullsEnum> list = new ArrayList<ProbabilityBullsEnum>();
        for (ProbabilityBullsEnum enums : values()) {
            if (enums.getType() == type) {
            	list.add(enums);
            }
        }
        return list;
    }
    
     
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	 
	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

	
	public LotteryBullsTypeEnum getLotteryBulls() {
		return lotteryBulls;
	}

	public void setLotteryBulls(LotteryBullsTypeEnum lotteryBulls) {
		this.lotteryBulls = lotteryBulls;
	}

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		System.err.println(BeanToMapUtil.convertBean(LUCKY_BULLS_LATER));
	}
}
