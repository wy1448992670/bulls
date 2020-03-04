package com.goochou.p2b.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.dao.UserAuthenticationMapper;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserAuthentication;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.service.UserAuthenticationService;
import com.goochou.p2b.service.UserService;

/**
 * UserAuthenticationServiceImpl
 *
 * @author 刘源
 * @date 2016/5/20
 */
@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Resource
    private UserAuthenticationMapper userAuthenticationMapper;
    @Resource
    private UploadService uploadService;
    @Resource
    private UserService userService;


    @Override
    public void save(UserAuthentication record) throws Exception {
        if (record.getId() == null) {
            userAuthenticationMapper.insertSelective(record);
        } else {
            userAuthenticationMapper.updateByPrimaryKeySelective(record);
        }
        if (record.getType() == ConstantsAdmin.USER_AUTHENTICATION_TYPE_RELASE.intValue() && record.getStatus() == ConstantsAdmin.USER_STATUS_SUCCESS.intValue()) {
            User user = userService.get(record.getUserId());
            if (user != null) {
                user.setIdentityCard(null);
                user.setTrueName(null);
                userService.updateWithNull(user);
            }
        }
    }
    
    @Override
    public List<Map<String, Object>> queryAuthenticationList(String keyword, Integer type, Integer status, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("type", type);
        map.put("status", status);
        map.put("start", start);
        map.put("limit", limit);
        return userAuthenticationMapper.queryAuthenticationList(map);
    }

    @Override
    public Integer queryAuthenticationListCount(String keyword, Integer type, Integer status) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("type", type);
        map.put("status", status);
        return userAuthenticationMapper.queryAuthenticationListCount(map);
    }

    @Override
    public Map<String, Object> queryByIdWithMap(Integer id) {
        return userAuthenticationMapper.queryByIdWithMap(id);
    }

    @Override
    public UserAuthentication queryById(Integer id) {
        return userAuthenticationMapper.selectByPrimaryKey(id);
    }

	@Override
	public  boolean queryUserIsAuth(Integer userId,String realName, String idCard) {
		return userAuthenticationMapper.queryAuthByUserInfo(userId,realName, idCard);
	}
}
