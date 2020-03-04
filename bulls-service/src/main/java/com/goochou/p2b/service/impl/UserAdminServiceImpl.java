package com.goochou.p2b.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goochou.p2b.constant.ConstantsAdmin;
import com.goochou.p2b.dao.AdminRoleMapper;
import com.goochou.p2b.dao.DepartmentMapper;
import com.goochou.p2b.dao.RoleMapper;
import com.goochou.p2b.dao.UploadMapper;
import com.goochou.p2b.dao.UserAdminMapper;
import com.goochou.p2b.model.AdminRole;
import com.goochou.p2b.model.AdminRoleExample;
import com.goochou.p2b.model.AllAssetsExcel;
import com.goochou.p2b.model.Department;
import com.goochou.p2b.model.DepartmentExample;
import com.goochou.p2b.model.Resources;
import com.goochou.p2b.model.Role;
import com.goochou.p2b.model.Upload;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.UserAdminExample;
import com.goochou.p2b.model.UserAdminExample.Criteria;
import com.goochou.p2b.model.vo.CapitalDetail;
import com.goochou.p2b.service.ResourcesService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.service.UserAdminService;
import com.goochou.p2b.utils.StringEncrypt;

@Service
public class UserAdminServiceImpl implements UserAdminService {
    private static Logger logger = Logger.getLogger(UserAdminServiceImpl.class);
    @Resource
    private UserAdminMapper userAdminMapper;
    @Resource
    private UploadMapper uploadMapper;
    @Resource
    private UploadService uploadService;
    @Resource
    private AdminRoleMapper adminRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private ResourcesService resourcesService;
    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public Map<String, Object> login(UserAdmin admin) throws Exception {
	UserAdminExample example = new UserAdminExample();
	Criteria c = example.createCriteria();
	c.andUsernameEqualTo(admin.getUsername());
	List<UserAdmin> list = userAdminMapper.selectByExample(example);

	Map<String, Object> map = new HashMap<String, Object>();
	if (list == null || list.isEmpty()) {
	    map.put("status", false);
	    map.put("msg", "用户不存在");
	    return map;
	}
	UserAdmin u = list.get(0);
	if (u.getStatus() == 1) {
	    map.put("status", false);
	    map.put("msg", "帐号已停用");
	    return map;
	} else if (u.getStatus() == 2) {
	    map.put("status", false);
	    map.put("msg", "帐号不存在");
	    return map;
	}

	/*
	 * admin.setId(u.getId()); if
	 * (!u.getPassword().equals(admin.getPassword()) &&
	 * !u.getPassword().equals(admin.getPassword())) { map.put("status",
	 * false); map.put("msg", "密码错误");
	 * 
	 * long now = new Date().getTime(); if (admin.getLastLoginTime() == null
	 * || (now - admin.getLastLoginTime().getTime() <= 1000 * 60 * 30)) { //
	 * admin.setErrorCount(u.getErrorCount() + 1); } else { //
	 * admin.setErrorCount(1); }
	 * 
	 * admin.setPassword(null);
	 * 
	 * userAdminMapper.updateByPrimaryKeySelective(admin); return map;
	 * }else{ admin.setErrorCount(0); if (u.getAvatarId() != null &&
	 * u.getAvatarId() > 0) { Upload upload =
	 * uploadService.get(u.getAvatarId()); if (upload != null) {
	 * u.setAvatar(upload.getPath()); } }
	 * userAdminMapper.updateByPrimaryKeySelective(admin);
	 * u.setRoleName(getRoleNameByUserId(u.getId())); map.put("status",
	 * true); map.put("msg", u); return map; }
	 */

	if (!u.getPassword().equals(admin.getPassword()) && !u.getPassword().equals(admin.getPassword())) {
	    map.put("status", false);
	    map.put("msg", "密码错误");
	    return map;
	}
	if (u.getAvatarId() != null && u.getAvatarId() > 0) {
	    Upload upload = uploadService.get(u.getAvatarId());
	    if (upload != null) {
		u.setAvatar(upload.getPath());
	    }
	}
	userAdminMapper.updateByPrimaryKeySelective(admin);
	u.setRoleName(getRoleNameByUserId(u.getId()));
	map.put("status", true);
	map.put("msg", u);
	return map;

    }

    @Override
    public void update(UserAdmin admin) throws Exception {
	userAdminMapper.updateByPrimaryKey(admin);
    }

    @Override
    public void save(UserAdmin admin) throws Exception {
	userAdminMapper.insert(admin);

    }

    @Override
    public void delete(int id) throws Exception {
	userAdminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> query(String keyword, Integer status, Integer roleId, int start, int limit) throws Exception {
	try {

	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("keyword", keyword);
	    map.put("userStatus", status);
		map.put("roleId", roleId);
	    map.put("start", start);
	    map.put("limit", limit);

	    List<UserAdmin> list = userAdminMapper.selectUserAdminList(map);
	    int count = userAdminMapper.countUserAdminList(map);

	    if (list != null) {
		for (UserAdmin ua : list) {
		    AdminRoleExample arexample = new AdminRoleExample();
		    com.goochou.p2b.model.AdminRoleExample.Criteria arc = arexample.createCriteria();
		    arc.andAdminIdEqualTo(ua.getId());
		    List<AdminRole> roleList = adminRoleMapper.selectByExample(arexample);
		    if (roleList != null && roleList.size() > 0) {
			for (AdminRole ar : roleList) {
			    Role role = roleMapper.selectByPrimaryKey(ar.getRoleId());
			    if (role != null) {// 获取该用户的角色名
				ar.setRoleName(role.getDescription());
			    }
			}
			ua.setRoleList(roleList);
		    }
		}
	    }
	    map.put("count", count);
	    map.put("list", list);
	    return map;
	} catch (Exception e) {
	    logger.equals(e);
	    throw new Exception(e);
	}
    }

    @Override
    public UserAdmin get(int id) throws Exception {
	return userAdminMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean checkNameExists(String username) throws Exception {
	UserAdminExample example = new UserAdminExample();
	Criteria c = example.createCriteria();
	c.andUsernameEqualTo(StringUtils.trim(username));
	List<UserAdmin> list = userAdminMapper.selectByExample(example);
	return list.size() > 0 ? false : true;
    }

    @Override
    public boolean checkOldPassword(Integer id, String password) throws Exception {
	UserAdmin admin = get(id);
	if (admin.getPassword().equals(StringEncrypt.Encrypt(password))) {
	    return true;
	}
	return false;
    }

    @Override
    public UserAdmin detail(Integer id) throws Exception {
	UserAdmin admin = userAdminMapper.selectByPrimaryKey(id);
	if (admin != null) {
	    if (admin.getAvatarId() != null) {
		Upload u = uploadMapper.selectByPrimaryKey(admin.getAvatarId());
		admin.setAvatar(u.getPath());
	    }

	    if (admin.getUpdateUser() != null) {
		UserAdmin a = userAdminMapper.selectByPrimaryKey(admin.getUpdateUser());
		admin.setUpdateUserName(a.getUsername());
	    }
	}
	return admin;
    }

    @Override
    public String updateAvatar(Integer adminId, MultipartFile file) throws Exception {
	if (file != null) {
	    Map<String, Object> map = uploadService.save(file, 0, adminId);
	    if (map.get(ConstantsAdmin.STATUS).equals(ConstantsAdmin.SUCCESS)) {
		UserAdmin admin = userAdminMapper.selectByPrimaryKey(adminId);
		Integer avatarId = (Integer) map.get(ConstantsAdmin.ID);

		// 删除记录同时删除原来的图片
		Integer oldAvatar = admin.getAvatarId();
		if (oldAvatar != null) {
		    Upload upload = uploadMapper.selectByPrimaryKey(oldAvatar);
		    String picPath = (String) map.get(ConstantsAdmin.PICTURE_PATH);
		    File f = new File(picPath + "/" + upload.getPath());
		    if (f.exists()) {
			f.delete();
		    }
		    uploadMapper.deleteByPrimaryKey(oldAvatar);
		}
		admin.setAvatarId(avatarId);
		userAdminMapper.updateByPrimaryKey(admin);
		return (String) map.get(ConstantsAdmin.PATH);
	    }
	}
	return null;
    }

    @Override
    public UserAdmin getByUsername(String username) throws Exception {
	UserAdminExample example = new UserAdminExample();
	example.createCriteria().andUsernameEqualTo(username);
	List<UserAdmin> list = userAdminMapper.selectByExample(example);
	if (null != list && !list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<Resources> selectResourcesByAdminId(Integer userAdminId, Integer isMenu, Integer parentId,
	    boolean isOrder) {
	return userAdminMapper.selectResourcesByAdminId(userAdminId, isMenu, parentId, isOrder);
    }

    @Override
    public String getRoleNameByUserId(Integer userId) {
	return userAdminMapper.getRoleNameByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> getAllUserAssets(Integer start, Integer limit) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("start", start);
	map.put("limit", limit);
	return userAdminMapper.getAllUserAssets(map);
    }

    @Override
    public Integer getAllUserAssetsCount() {
	return userAdminMapper.getAllUserAssetsCount();
    }

    @Override
    public List<AllAssetsExcel> getAllUserAssetsExcel(Integer start, Integer limit) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("start", start);
	map.put("limit", limit);
	return userAdminMapper.getAllUserAssetsExcel(map);
    }

    @Override
    public List<CapitalDetail> getAllCapitalDetail(Integer start, Integer limit) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("start", start);
	map.put("limit", limit);
	return userAdminMapper.getAllCapitalDetail(map);
    }

    @Override
    public List<Map<String, Object>> getAllCapitalDetailForPage(Integer start, Integer limit) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("start", start);
	map.put("limit", limit);
	return userAdminMapper.getAllCapitalDetailForPage(map);
    }

    @Override
    public Integer getCapitalDetailCount() {
	return userAdminMapper.getCapitalDetailCount();
    }

    @Override
    public List<CapitalDetail> getAllCapitalDetailForMonth(Date month) {
	return userAdminMapper.getAllCapitalDetailForMonth(month);
    }

    @Override
    public UserAdmin getByTrueName(String trueName) {
	UserAdminExample example = new UserAdminExample();
	Criteria c = example.createCriteria();
	c.andTrueNameEqualTo(trueName);
	List<UserAdmin> list = userAdminMapper.selectByExample(example);
	if (list != null && list.size() > 0) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<UserAdmin> queryUserAdminByRoleId(String roleId) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("roleId", roleId);
	List<UserAdmin> list = userAdminMapper.queryUserAdminByRoleId(map);
	return list;
    }

    @Override
    public List<UserAdmin> queryUserAdminByCustomerUserId(Integer userId) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("userId", userId);
	return userAdminMapper.queryUserAdminByCustomerUserId(map);
    }

    @Override
    public Integer queryUserAdminByPhone(String mobilePhone) {

	return userAdminMapper.queryUserAdminByPhone(mobilePhone);
    }

    @Override
    public List<Department> getAllDepartment() {
        DepartmentExample example = new DepartmentExample();
        return departmentMapper.selectByExample(example);
    }
    
    
}
