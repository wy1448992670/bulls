package com.goochou.p2b.dao;

import com.goochou.p2b.model.ActivityTaskUser;
import com.goochou.p2b.model.ActivityTaskUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityTaskUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    int countByExample(ActivityTaskUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    int deleteByExample(ActivityTaskUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    int insert(ActivityTaskUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    int insertSelective(ActivityTaskUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    List<ActivityTaskUser> selectByExample(ActivityTaskUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    ActivityTaskUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    int updateByExampleSelective(@Param("record") ActivityTaskUser record, @Param("example") ActivityTaskUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    int updateByExample(@Param("record") ActivityTaskUser record, @Param("example") ActivityTaskUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    int updateByPrimaryKeySelective(ActivityTaskUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_task_user
     *
     * @mbggenerated Mon May 23 17:24:11 CST 2016
     */
    int updateByPrimaryKey(ActivityTaskUser record);
}