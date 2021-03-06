package com.goochou.p2b.dao;

import com.goochou.p2b.model.goods.GoodsOrderInvestRelation;
import com.goochou.p2b.model.goods.GoodsOrderInvestRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsOrderInvestRelationMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	long countByExample(GoodsOrderInvestRelationExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	int deleteByExample(GoodsOrderInvestRelationExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	int insert(GoodsOrderInvestRelation record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	int insertSelective(GoodsOrderInvestRelation record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	List<GoodsOrderInvestRelation> selectByExample(GoodsOrderInvestRelationExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	GoodsOrderInvestRelation selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	int updateByExampleSelective(@Param("record") GoodsOrderInvestRelation record,
			@Param("example") GoodsOrderInvestRelationExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	int updateByExample(@Param("record") GoodsOrderInvestRelation record,
			@Param("example") GoodsOrderInvestRelationExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(GoodsOrderInvestRelation record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_goods_order_invest_relation
	 * @mbg.generated
	 */
	int updateByPrimaryKey(GoodsOrderInvestRelation record);
}