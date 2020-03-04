package com.goochou.p2b.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

public interface JobMapper {

	/**
	 * 用户账户日快照
	 * @author 张琼麒
	 * @version 创建时间：2019年7月30日 上午11:04:37
	 * @param priorPeriodTable		上期数据表
	 * @param currentPeriodTable	当期数据表,既当期数据将要存储的表名
	 * @param currentPeriodStart	当期统计开始时间
	 * @param currentPeriodEnd		当期统计结束时间
	 * @return
	 */
    int assetsSnapshot(@Param("priorPeriodTable") String priorPeriodTable,@Param("currentPeriodTable") String currentPeriodTable,
    		@Param("currentPeriodStart") Date currentPeriodStart,@Param("currentPeriodEnd") Date currentPeriodEnd);
    
    /**
     * 创建用户账户日快照表
     * @author 张琼麒
     * @version 创建时间：2019年8月14日 下午12:00:00
     * @param currentPeriodTable
     * @return
     */
    int createAssetsSnapshotTable(@Param("currentPeriodTable") String currentPeriodTable);
    
    /**
	 * 查看表是否存在
	 * @author 张琼麒
	 * @version 创建时间：2019年7月30日 下午2:22:07
	 * @param tableName
	 * @return
	 */
    int countTableName(@Param("tableName") String tableName);
    
}