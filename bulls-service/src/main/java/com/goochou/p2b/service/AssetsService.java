package com.goochou.p2b.service;

import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.BaseResult;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 资产
 */
public interface AssetsService {


    AssetsMapper getMapper();

    public Assets findByuserId(Integer userId);

    public List<Map<String, Object>> investTop10();

    /**
     * type 0 资产排行 1 投资排行 2收益排行
     *
     * @return
     */
    public List<Map<String, Object>> getRankList(Integer adminId,Integer departmentId,Integer type);

    public int updateByPrimaryKeyAndVersionSelective(Assets assets);

    public void insert(Assets assets);

    /**
     * 投资金额人数饼状图统计
     *
     * @return
     */
    public List<Map<Integer, String>> getHuoInvestPie();

    /**
     * 查询所有活期投资用户
     *
     * @return
     */
    public List<Assets> listHuo() throws Exception;

    /**
     * 查询每个年龄段的投资情况
     *
     * @return
     */
    public List<Map<String, Object>> getByYear();

    /**
     * 查询--账户有余额资金未投资
     *
     * @return
     * @author 王信
     * @date 2015年11月6日 上午11:14:12
     */
    public List<Map<String, Object>> assetsInvestment();

    /**
     * @param userid
     * @return
     * @Title: AssetsService.java
     * @Package com.goochou.p2b.service
     * @Description(描述):查询我的资产
     * @author 王信
     * @date 2016年2月26日 下午2:21:08
     * @version V1.0
     */
    public Map<String, Object> selectMyAssets(Integer userid);
    
    /**
     * 
     * <p>月月盈本金</p> 
     * @param userId
     * @return
     * @author: lxfeng  
     * @date: Created on 2018-7-31 上午10:08:29
     */
    public Double selectMyYyyAssets(Integer userId);

    /**
     * @Description(描述):查询用户总资产前五十位的最后一位的总资产 总资产=账户余额+定期待收本金+活期再投金额
     * @author 王信
     * @date 2016/4/19
     * @params
     **/
    public double selectTotalAssetsRanking();
    
    public List<Assets> listHuoOfNormalUser() throws Exception ;
    
    public Double selectMyYyyInterest(Integer userId);

	Map<String, Double> getAssetsTradeSum(Date beginDate, Date endDate);

	Assets sumAssetsSnapshoot(String tableName);

	Map<String, Double> sumTradeRecord(Date beginDate, Date endDate);
    
}
