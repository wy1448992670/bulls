package com.goochou.p2b.dao;

import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.AssetsExample;
import com.goochou.p2b.model.vo.AssetsVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AssetsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    int countByExample(AssetsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    int deleteByExample(AssetsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    int insert(Assets record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    int insertSelective(Assets record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    List<Assets> selectByExample(AssetsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    Assets selectByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    int updateByExampleSelective(@Param("record") Assets record, @Param("example") AssetsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    int updateByExample(@Param("record") Assets record, @Param("example") AssetsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    int updateByPrimaryKeySelective(Assets record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table assets
     *
     * @mbggenerated Thu May 09 19:07:23 CST 2019
     */
    int updateByPrimaryKey(Assets record);

	List<Map<String, Object>> investTop10();

	List<Map<String, Object>> getRankList(@Param("adminId")Integer adminId,@Param("departmentId")Integer departmentId,@Param("type")Integer type);

	int updateByPrimaryKeyAndVersionSelective(Assets assets);

	List<Map<Integer, String>> getHuoInvestPie();

	List<Map<String, Object>> getByYear();

	List<Map<String, Object>> assetsInvestment();

	Map<String, Object> selectMyAssets(Integer userid);

	double selectTotalAssetsRanking();

	List<Assets> listHuoOfNormalUser();

	Double selectMyYyyAssets(Integer userId);

	List<Map<String, Object>> selectMyYyyInterest(Integer userId);
	
	/**
	 * selectByPrimaryKey 的 for update 版
	 * @param id
	 * @return
	 */
	Assets selectByPrimaryKeyForUpdate(Integer id);
	
	Assets sumAssetsSnapshoot(@Param("tableName")String tableName);
	
	Map<String,Double> sumTradeRecord(@Param("beginDate")Date beginDate,@Param("endDate")Date endDate);
	
	List<AssetsVO> listAssetsSnapshootVO(AssetsExample example);
	
	Long countAssetsSnapshootVO(AssetsExample example);
	
}