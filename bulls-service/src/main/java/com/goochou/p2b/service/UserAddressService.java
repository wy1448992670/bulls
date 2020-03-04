package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

import com.goochou.p2b.dao.UserAddressMapper;
import com.goochou.p2b.model.LotteryAddress;
import com.goochou.p2b.model.UserAddress;

public interface UserAddressService {

    /**
     * @Description(描述):根据用户的id   查询 用户所有的常用地址
     * @author 王信
     * @date 2016/5/13
     * @params
     **/
    public List<UserAddress> selectAllAddress(Integer userId, Integer start, Integer limit);
    public Integer selectAllAddressCount(Integer userId);

    /**
     * @Description(描述):用户删除自己的地址
     * @author 王信
     * @date 2016/5/13
     * @params     id 地址id
     **/
    public void deleteAddress(Integer id);

    /**
     * @Description(描述):新增地址
     * @author 王信
     * @date 2016/5/13
     * @params
     **/
    public Integer saveAddress(UserAddress userAddress);

    /**
     * @Description(描述):查询一个单独的地址
     * @author 王信
     * @date 2016/5/13
     * @params
     **/
    public UserAddress selectKeyUserAddress(Integer id);

    /**
     * @Description(描述):保存修改的地址
     * @author 王信
     * @date 2016/5/13
     * @params
     **/
    public void updateAddress(UserAddress userAddress);


    /**
     * @Description(描述):更新其他所有地址的默认状态，只有一个默认地址
     * @author 王信
     * @date 2016/5/13
     * @params
     **/
    public void updateAllReserve(Integer userId);

    /**
     * @Description(描述):修改默认地址的接口
     * @author 王信
     * @date 2016/5/16
     * @params
     **/
    public void updateKeyReserve(Integer id,Integer userId);

    /**
     * @Description(描述):用户地址后台管理查询
     * @author 王信
     * @date 2016/5/18
     * @params
     **/
    public List<UserAddress> selectAllUserAddress(Integer page,Integer limit,String keyword);
    public Integer selectAllUserAddressCount(String keyword);

    /**
     * @Description(描述):新增，或者保存用户地址
     * @author 王信
     * @date 2016/5/18
     * @params
     **/
    public void saveUserAddress(UserAddress userAddress);

    /**
     * 是否有地址
     * @param userId
     * @author 刘源
     * @date 2016/6/30
     */
    Boolean queryHasAddress(Integer userId);
    /**
     * 查询用户的默认地址
     * @param userId
     * @author 赵星星
     * @date 2017-3-2
     */
    public UserAddress queryAddress(Integer userId);

	/**
	 * 得到mapper
	 * 
	 * @Title: getUserAddressMapper
	 * @return UserAddressMapper
	 * @author zj
	 * @date 2019-05-27 17:26
	 */
	UserAddressMapper getUserAddressMapper();
	
	/**
	 * 获取用户所有的地址 
	* @Title: listUserAddressesByUserId 
	* @param userId
	* @return List<UserAddress>
	* @author zj
	* @date 2019-05-27 17:36
	 */
	List<UserAddress> listUserAddressesByUserId(Integer userId);
    /**
     * 获取ADDRESS包括省市名称
     * @param addressId
     * @return
     */
    public UserAddress selectAddressById(Integer addressId);

    UserAddress getAddressesById(Integer id);
}
