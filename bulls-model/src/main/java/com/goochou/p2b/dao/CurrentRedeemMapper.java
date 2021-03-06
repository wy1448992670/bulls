package com.goochou.p2b.dao;

import com.goochou.p2b.model.CurrentRedeem;
import com.goochou.p2b.model.CurrentRedeemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CurrentRedeemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    int countByExample(CurrentRedeemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    int deleteByExample(CurrentRedeemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    int insert(CurrentRedeem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    int insertSelective(CurrentRedeem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    List<CurrentRedeem> selectByExample(CurrentRedeemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    CurrentRedeem selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    int updateByExampleSelective(@Param("record") CurrentRedeem record, @Param("example") CurrentRedeemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    int updateByExample(@Param("record") CurrentRedeem record, @Param("example") CurrentRedeemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    int updateByPrimaryKeySelective(CurrentRedeem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table current_redeem
     *
     * @mbggenerated Sat Oct 29 16:41:38 CST 2016
     */
    int updateByPrimaryKey(CurrentRedeem record);
}