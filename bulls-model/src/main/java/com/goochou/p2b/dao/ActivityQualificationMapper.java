package com.goochou.p2b.dao;

import com.goochou.p2b.model.ActivityQualification;

import java.util.Map;

/**
 * 1、<p><p>
 *  参与抽奖资格接口层
 * @author mr_zou
 * @date 2017/12/4 18:11
 */
public interface ActivityQualificationMapper {

    /**
     * 根据条件查询跨年抽奖资格信息
     * @param map 查询抽跨年奖资格信息条件集合
     * @return 抽奖资格信息
     */
    ActivityQualification findByUserIdAndActivityId(Map<String,Object> map);

    /**
     * 添加跨年抽奖资格信息
     * @param activityQualification 添加的信息
     * @return int
     */
    int insertActivityQualification(ActivityQualification activityQualification);

    /**
     * 根据条件修改跨年抽奖机会次数
     * @param activityQualification 修改条件
     * @return int
     */
    int updateByUserIdAndActivityIdAndVersion(ActivityQualification activityQualification);
}
