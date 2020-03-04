package com.goochou.p2b.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.goochou.p2b.model.UserExperienceGold;
import com.goochou.p2b.model.UserExperienceGoldExample;

public interface UserExperienceGoldMapper {
    int countByExample(UserExperienceGoldExample example);

    int deleteByExample(UserExperienceGoldExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserExperienceGold record);

    int insertSelective(UserExperienceGold record);

    List<UserExperienceGold> selectByExample(UserExperienceGoldExample example);

    UserExperienceGold selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserExperienceGold record, @Param("example") UserExperienceGoldExample example);

    int updateByExample(@Param("record") UserExperienceGold record, @Param("example") UserExperienceGoldExample example);

    int updateByPrimaryKeySelective(UserExperienceGold record);

    int updateByPrimaryKey(UserExperienceGold record);

    List<UserExperienceGold> selectExperienceList(Map<String, Object> params);

    int updateExperienceGoldStatus(Map<String, Object> params);

    Double selectExperienceGoldAmount(Map<String, Object> params);
}