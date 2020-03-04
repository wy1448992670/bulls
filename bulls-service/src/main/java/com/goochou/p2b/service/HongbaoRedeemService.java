package com.goochou.p2b.service;

import com.goochou.p2b.model.HongbaoRedeem;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.vo.HongbaoTemplateModel;
import com.goochou.p2b.service.exceptions.LockFailureException;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * liuyuan
 * 2016/6/23.
 */
public interface HongbaoRedeemService {

    /**
     * 查询红包兑换码记录List
     * @param
     * @author 刘源
     * @date 2016/6/23
     */
    List<Map<String,Object>> query(String keyword, Integer start, Integer limit);

    /**
     * 查询红包兑换码记录总数
     * @param
     * @author 刘源
     * @date 2016/6/23
     */
    Integer queryCount(String keyword);

    /**
     * 查询红包兑换记录
     * @param id
     * @author 刘源
     * @date 2016/6/23
     */
    HongbaoRedeem queryById(Integer id);

    /**
     * 添加红包兑换码
     * @param params
     * @author 刘源
     * @date 2016/6/23
     */
    void addHongbaoRedeem(Map<String, Object> params);

    /**
     * @Description(描述):查询用户是否使用过当前兑换码，当前兑换码是否可用
     * @author 王信
     * @date 2016/6/24
     * @params
     **/
    Map<String,Object> saveHongbao (HongbaoRedeem redeem,User user,String phone) throws LockFailureException;

    /**
     * @Description(描述):兑换码查询
     * @author 王信
     * @date 2016/6/24
     * @params
     **/
    HongbaoRedeem queryRedeemCode(String redeemCode);

    /**
     * @Description(描述):查询此兑换码是否是唯一兑换码
     * @author 王信
     * @date 2016/6/27
     * @params
     **/
    Integer queryByRedeemCodeUse(Integer redeemId);

    /**
     * @Description(描述):保存新生成的兑换码
     * @author 王信
     * @date 2016/6/29
     * @params
     **/
    String saveHongbaoRedeem(HongbaoRedeem hongbaoRedeem,List<HongbaoTemplateModel> list) throws  Exception;

    /**
     * @Description(描述):更新兑换码状态
     * @author 王信
     * @date 2016/6/30
     * @params
     **/
    Integer updateHongbaoRedeem (HongbaoRedeem hongbaoRedeem,List<HongbaoTemplateModel> list);

    /**
     * 保存兑换码以及其对应红包
     * @param hongbaoRedeem
     * @param list
     * @author 刘源
     * @date 2016/7/7
     */
    void save(HongbaoRedeem hongbaoRedeem, List<HongbaoTemplateModel> list) throws ParseException, LockFailureException;
}
