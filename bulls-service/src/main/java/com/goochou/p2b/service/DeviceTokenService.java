package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.DeviceToken;

public interface DeviceTokenService {

	/* List<DeviceToken> list(); */

	List<DeviceToken> selectBytoken(String devicetoken);

	/**
	 * 查询用户的deviceToken值
	 * 
	 * @param userId
	 * @param token
	 * @author 刘源
	 * @date 2016/5/11
	 */
	DeviceToken queryByToken(Integer userId, String token);

	/**
	 * 保存deviceToken记录
	 * 
	 * @param deviceToken
	 * @author 刘源
	 * @date 2016/5/11
	 */
	void saveRecord(DeviceToken deviceToken);

	/**
	 * 查询用户deviceToken
	 * 
	 * @param userId
	 * @author 刘源
	 * @date 2016/5/11
	 */
	DeviceToken queryByUserId(Integer userId);

	/**
	 * 查询用户的deviceToken
	 * 
	 * @param ids
	 * @author 刘源
	 * @date 2016/5/12
	 */
	List<Map<String, Object>> queryByUserList(Integer[] ids);

	/**
	 * 保存或更新
	 * 
	 * @Title: saveToken
	 * @param userId
	 * @param client
	 * @param deviceToken void
	 * @author zj
	 * @throws Exception 
	 * @date 2019-08-21 14:27
	 */
	void saveToken(DeviceToken deviceToken) throws Exception;

	void destroyToken(DeviceToken deviceToken) throws Exception;
	
    DeviceToken selectByUUID(String uuid) throws Exception;

	DeviceToken selectInstallDTByToken(String devicetoken) throws Exception;

	
}
