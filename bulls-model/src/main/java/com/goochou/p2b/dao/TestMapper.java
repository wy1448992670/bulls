package com.goochou.p2b.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.goochou.p2b.model.Test;
import com.goochou.p2b.model.TestExample;

public interface TestMapper {
    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    int countByExample(TestExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    int deleteByExample(TestExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    int insert(Test record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    int insertSelective(Test record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    List<Test> selectByExample(TestExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    Test selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    int updateByExampleSelective(@Param("record") Test record, @Param("example") TestExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    int updateByExample(@Param("record") Test record, @Param("example") TestExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    int updateByPrimaryKeySelective(Test record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table test
     * 
     * @mbggenerated Tue Feb 03 11:41:26 CST 2015
     */
    int updateByPrimaryKey(Test record);

    int updateByPrimaryKeyVersion(Test record);

    public List<Map<String, Object>> test();

    public List<Integer> list();
}