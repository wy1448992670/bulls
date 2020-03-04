package com.goochou.p2b.service;

import com.goochou.p2b.model.ProjectActivitySetting;
import com.goochou.p2b.model.vo.ProjectActivityRecordVO;
import com.goochou.p2b.model.vo.ProjectActivitySettingVO;

import java.util.List;
import java.util.Map;

public interface ProjectActivitySettingService  {

    void saveProjectActivity(Integer projectId, Integer period);

    List<ProjectActivitySetting> queryProjectActivitySettings();

    ProjectActivitySetting queryProjectActivitySettingByProjectId(Integer projectId);

    int insertProjectActivityRecordBatch(Map<String, Object> record);

    int delProjectActivitySetting(String period);

    /**
     * 设置奖品
     * @param settingId
     * @param luckyCode
     * @param adminId
     * @param period
     * @return
     */
    Map<String, Object> savePrize(Integer settingId, Integer luckyCode, Integer adminId, Integer period);

	/**
	 * 根据期数查找幸运号码列表
	 * @param period 期数
	 * @return List
	 */
	Map<String,Object> queryProjectActivityRecordX(Integer period);

	List<ProjectActivitySettingVO> queryProjectActivityRecordForAdmin(Integer period,Integer start, Integer limit);

	/**
	 * 根据期数查找幸运号码列表条数
	 * @param period 期数
	 * @return Integer
	 */
	Integer queryProjectActivityRecordCountForAdmin(Integer period);
	/**
	 * 设置奖品
	 */
	List<ProjectActivityRecordVO> queryProjectActivityDetail(Integer period, Integer start, Integer limit);
	
	List<Map<String,Object>> queryWinNumberDetailX(Integer period,Integer userId);
	
	Integer queryProjectActivityDetailCount(Integer period);
	
	List<ProjectActivityRecordVO> queryProjectActivityByUser(Integer period, Integer userId);
	
    /**
     * 查询活动页的详情
     * @param period
     * @return
     */
    public List<Map<String,Object>> queryProjectAndActivity(Integer period);

}
