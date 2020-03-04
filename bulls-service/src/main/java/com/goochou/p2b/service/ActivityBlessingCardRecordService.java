package com.goochou.p2b.service;

import com.goochou.p2b.constant.ProbabilityBullsEnum;
import com.goochou.p2b.dao.ActivityBlessingCardRecordMapper;
import java.util.List;

import com.goochou.p2b.constant.LotteryBullsTypeEnum;
import com.goochou.p2b.model.ActivityBlessingCardRecord;

public interface ActivityBlessingCardRecordService {

	ActivityBlessingCardRecordMapper getMapper();

	void updateByExampleForVersion(ActivityBlessingCardRecord activityBlessingCardRecord) throws Exception;

	void updateByExampleSelectiveForVersion(ActivityBlessingCardRecord activityBlessingCardRecord) throws Exception;

	void doCompoundGreateBulls(Integer userId) throws Exception;

	void doTransformGreateBulls(ActivityBlessingCardRecord greateBulls) throws Exception;

	List<ActivityBlessingCardRecord> selectAllUnusedGreateBulls() throws Exception;

	
	ActivityBlessingCardRecord insertCardRecord(Integer userId, ProbabilityBullsEnum hitBulls) throws Exception;
	
	List<ActivityBlessingCardRecord> listBlessingCard(Integer userId, LotteryBullsTypeEnum lotteryBullsTypeEnum, boolean isFan);
	
	int countBlessingCard(Integer userId, LotteryBullsTypeEnum lotteryBullsTypeEnum, boolean isFan);
	
	ActivityBlessingCardRecord getByUserIdAndCardNo(Integer userId, String cardNo);

	/**
	 * 参与活动人数,活动时间目前写死
	 * @author sxy
	 * @return
	 */
	int getJoinUserCount();
	
}
