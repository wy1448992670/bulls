package com.goochou.p2b.service;

import com.goochou.p2b.model.ProjectPackage;

import java.util.List;

public interface ProjectPackageService {


    /**
     * @Description: admin 资产包列表
     * @date  2017/2/15
     * @author 王信
     */
    List<ProjectPackage> selectProjectPackageList(Integer status, Integer  product,Integer start, Integer limit);
    Integer selectProjectPackageCount(Integer status ,Integer  product);

    public void savePackage (Integer product, Integer[] projectId) throws Exception;

    /**
     * @Description: 根据资产包ID 查询实体
     * @date  2017/2/20
     * @author 王信
     */
    ProjectPackage selectByIdProjectPackage(Integer packageId);

    /**
     * @Description: 更新资产包
     * @date  2017/2/21
     * @author 王信
     */
    void updatePackage(Integer packageId,Integer status,Integer[] projectId) throws Exception;


    /**
     * @Description: 自动打包
     * @date  2017/2/21
     * @author 王信
     */
    public Integer saveNewPackage(Integer productId);


    /**
     * @Description: 根据产品ID 查询当前包的个数
     * @date  2017/2/22
     * @author 王信
     */
    Integer selectByProductIdCount(Integer productId);
    /**
     * @Description: 根据产品ID 投资中查询当前包的个数
     * @date  2017/3/15
     * @author zxx
     */
    public Integer selectByProductIdandStatusCount(Integer productId);
}


