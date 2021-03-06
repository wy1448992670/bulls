package com.goochou.p2b.dao;

import com.goochou.p2b.model.Resources;
import com.goochou.p2b.model.ResourcesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourcesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    int countByExample(ResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    int deleteByExample(ResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    int insert(Resources record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    int insertSelective(Resources record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    List<Resources> selectByExample(ResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    Resources selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    int updateByExampleSelective(@Param("record") Resources record, @Param("example") ResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    int updateByExample(@Param("record") Resources record, @Param("example") ResourcesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    int updateByPrimaryKeySelective(Resources record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table resources
     *
     * @mbggenerated Tue Aug 04 15:28:35 CST 2015
     */
    int updateByPrimaryKey(Resources record);
}