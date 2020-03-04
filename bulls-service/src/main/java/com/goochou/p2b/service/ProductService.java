package com.goochou.p2b.service;

import com.goochou.p2b.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {


    /**
     * @Description: admin 产品列表
     * @date 2017/2/15
     * @author 王信
     */
    List<Product> selectProductList(Integer status, Integer start, Integer limit);

    Integer selectProductCount(Integer status);

    /**
     * @Description: 新增产品
     * @date 2017/2/15
     * @author 王信
     */
    void saveProduct(Product product);

    /**
     * @Description: 根据ID 查询对应的产品
     * @date 2017/2/15
     * @author 王信
     */
    Product selectById(Integer id);

    /**
     * @Description: 依次展示投资期限最短的周期产品和年化收益率最高的周期产品。
     * @date 2017/2/24
     * @author 王信
     */
    Product selectMinOutDaysProduct();

    Product selectMaxAnnualizedProduct();

    /**
     * @Description: 查询 安鑫赚 年化区间,锁定区间,起投金额
     * @date 2017/2/24
     * @author 王信
     */
    Map<String, Object> selectProductLimit();

    /**
     * @Description: 安鑫赚投资列表
     * @date 2017/2/24
     * @author 王信
     */
    List<Product> queryProductList(Integer start, Integer limit);

    Integer queryProductCount();


    /**
     * @Description: 根据用户ID 和 投资产品的状态  查询列表
     * @date 2017/2/27
     * @author 王信
     */
    List<Map<String, Object>> selectByuserIdAndProductStatusList(Integer status, Integer userId, Integer start, Integer limit);

    Integer selectByuserIdAndProductStatusCount(Integer status, Integer userId);

    /**
     * @Description: 根据用户投资id  查询用户投资详情.
     * @date 2017/2/27
     * @author 王信
     */
    Map<String, Object> selectInvestmentIdByProductDetail(Integer investmentId, Integer userId);

    List<Map<String, Object>> selectByInvestmentIdProjectList(Integer investmentId, Integer userId, Integer start, Integer limit);

    Integer selectByInvestmentIdProjectCount(Integer investmentId, Integer userId);

    /**
     * @Description: 单笔订单投资  收益明细
     * @date 2017/2/28
     * @author 王信
     */
    List<Map<String, Object>> selectInvestDetailIvestList(Integer investmentId, Integer status, Integer userId, Integer start, Integer limit);

    Integer selectInvestDetailIvestCount(Integer investmentId, Integer status, Integer userId);

    /**
     * @Description: 标的组成
     * @date 2017/3/15
     * @author zxx
     */
    public List<Map<String, Object>> projectFormIvestList(Integer packageId, Integer start, Integer limit);

    public Integer projectFormIvestListCount(Integer packageId);

    public List<Map<String, Object>> projectFormIvestList1(Integer packageId, Integer start, Integer limit);

    public Integer projectFormIvestListCount1(Integer packageId);


    public List<Map<String, Object>> list(Integer packageId, Integer start, Integer limit);

    public Integer listCount(Integer packageId);

    /**
     * @Description: 新产品派息复投 定时器
     */
    public void saveProductInterest() throws Exception;

    public void saveInterestCompound(Map<String, Object> map) throws Exception;

    /**
     * @Description: 新产品福利投资 定时器
     * @date 2017/3/13
     */
    public void saveProductFailedCompund() throws Exception;

    /**
     * @Description: 查询用户昨日的产品收益     interest_product表
     * @date 2017/3/17
     * @author 王信
     */
    Double selectYesterdayAmount(Integer userId);

    /**
     * @Description: 查询用户持有安鑫赚总额
     * @date 2017/3/21
     * @author 王信
     */
    Double selectProductTotalAmount(Integer userId);

}


