package com.goochou.p2b.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.UserAddressMapper;
import com.goochou.p2b.model.LotteryAddress;
import com.goochou.p2b.model.UserAddress;
import com.goochou.p2b.model.UserAddressExample;
import com.goochou.p2b.service.UserAddressService;
import com.goochou.p2b.service.UserService;

/**
 * Created by swj on 2016/5/13.
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

	@Resource
	private UserAddressMapper userAddressMapper;
	@Resource
	private UserService userService;

	@Override
	public List<UserAddress> selectAllAddress(Integer userId, Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("start", start);
		map.put("limit", limit);
		return userAddressMapper.selectAllAddress(map);
	}

	@Override
	public Integer selectAllAddressCount(Integer userId) {
		return userAddressMapper.selectAllAddressCount(userId);
	}

	@Override
	public void deleteAddress(Integer id) {

		userAddressMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Integer saveAddress(UserAddress userAddress) {

		return userAddressMapper.insertSelective(userAddress);
	}

	@Override
	public UserAddress selectKeyUserAddress(Integer id) {
		return userAddressMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateAddress(UserAddress userAddress) {
		userAddressMapper.updateByPrimaryKeySelective(userAddress);
	}

	@Override
	public void updateAllReserve(Integer userId) {
		userAddressMapper.updateAllReserve(userId);
	}

	@Override
	public void updateKeyReserve(Integer id, Integer userId) {
		userAddressMapper.updateAllReserve(userId);
		UserAddress userAddress = userAddressMapper.selectByPrimaryKey(id);
		userAddress.setReserve(1);
		userAddress.setUpdateDate(new Date());
		userAddressMapper.updateByPrimaryKeySelective(userAddress);
	}

	@Override
	public List<UserAddress> selectAllUserAddress(Integer page, Integer limit, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page);
		map.put("limit", limit);
		map.put("keyword", keyword);
		return userAddressMapper.queryAllUserAddress(map);
		//return userAddressMapper.queryAllUserAddress(keyword,page,limit);
	}

	@Override
	public Integer selectAllUserAddressCount(String keyword) {
		return userAddressMapper.queryAllUserAddressCount(keyword);
	}

	@Override
	public void saveUserAddress(UserAddress userAddress) {
		if (userAddress.getReserve() == 1) {
			Integer userId = userAddress.getUserId();
			userAddressMapper.updateAllReserve(userId);
		}
		if (userAddress.getId() == null) {
			userAddressMapper.insertSelective(userAddress);
		} else {
			userAddressMapper.updateByPrimaryKeySelective(userAddress);
		}
	}

	@Override
	public Boolean queryHasAddress(Integer userId) {
		return userAddressMapper.queryHasAddress(userId);
	}

	@Override
	public UserAddress queryAddress(Integer userId) {
		UserAddressExample example = new UserAddressExample();
		example.createCriteria().andUserIdEqualTo(userId).andReserveEqualTo(1);
		List<UserAddress> list = userAddressMapper.listUserAddress(example);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;

	}

	@Override
	public UserAddressMapper getUserAddressMapper() {
		return userAddressMapper;
	}

	@Override
	public List<UserAddress> listUserAddressesByUserId(Integer userId) {
		UserAddressExample example = new UserAddressExample();
		example.createCriteria().andUserIdEqualTo(userId).andReserveNotEqualTo(-2);
		return userAddressMapper.listUserAddress(example);
	}

    /* (non-Javadoc)
     * @see com.goochou.p2b.service.UserAddressService#selectAddressById(java.lang.Integer)
     * 2.0版本弃用
     */
    @Override
	@Deprecated
    public UserAddress selectAddressById(Integer addressId) {
//         return userAddressMapper.selectAddressById(addressId);
		return this.getAddressesById(addressId);
    }

	@Override
	public UserAddress getAddressesById(Integer id) {
		UserAddressExample example = new UserAddressExample();
		example.createCriteria().andIdEqualTo(id);
		List<UserAddress> list = userAddressMapper.listUserAddress(example);
		return list.isEmpty() ? null : list.get(0);
	}

}
