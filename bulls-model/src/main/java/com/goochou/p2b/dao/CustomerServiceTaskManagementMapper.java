package com.goochou.p2b.dao;

import com.goochou.p2b.model.CustomerServiceTaskManagement;
import com.goochou.p2b.model.CustomerServiceTaskManagementExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CustomerServiceTaskManagementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    int countByExample(CustomerServiceTaskManagementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    int deleteByExample(CustomerServiceTaskManagementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    int insert(CustomerServiceTaskManagement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    int insertSelective(CustomerServiceTaskManagement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    List<CustomerServiceTaskManagement> selectByExample(CustomerServiceTaskManagementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    CustomerServiceTaskManagement selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    int updateByExampleSelective(@Param("record") CustomerServiceTaskManagement record, @Param("example") CustomerServiceTaskManagementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    int updateByExample(@Param("record") CustomerServiceTaskManagement record, @Param("example") CustomerServiceTaskManagementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    int updateByPrimaryKeySelective(CustomerServiceTaskManagement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table customer_service_task_management
     *
     * @mbggenerated Tue Nov 17 19:58:46 CST 2015
     */
    int updateByPrimaryKey(CustomerServiceTaskManagement record);

	public Integer checkUserTask();

	public void updateUserTask();

	public CustomerServiceTaskManagement selectConfig();

	/**
	 * 查询任务起始ID
	 * @param map
	 * @return
	 * @author
	 * @date 2015年12月10日
	 * @parameter
	 * @return
	 */
	public Integer queryTaskUserId(Map<String, Object> map);

    /**
     * 查询所有客服ID
     * @return
     */
   public List<Map<String,Object>> selectCsUserId();
}