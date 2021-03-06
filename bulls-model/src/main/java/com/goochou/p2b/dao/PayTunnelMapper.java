package com.goochou.p2b.dao;

import com.goochou.p2b.model.PayTunnel;
import com.goochou.p2b.model.PayTunnelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayTunnelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    int countByExample(PayTunnelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    int deleteByExample(PayTunnelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    int insert(PayTunnel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    int insertSelective(PayTunnel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    List<PayTunnel> selectByExample(PayTunnelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    PayTunnel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    int updateByExampleSelective(@Param("record") PayTunnel record, @Param("example") PayTunnelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    int updateByExample(@Param("record") PayTunnel record, @Param("example") PayTunnelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    int updateByPrimaryKeySelective(PayTunnel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_tunnel
     *
     * @mbggenerated Thu May 09 18:14:29 CST 2019
     */
    int updateByPrimaryKey(PayTunnel record);
}