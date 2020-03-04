package com.goochou.p2b.service;

import com.goochou.p2b.model.AllAssetsExcel;
import com.goochou.p2b.model.Department;
import com.goochou.p2b.model.Resources;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.model.vo.CapitalDetail;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserAdminService {
    public Map<String, Object> login(UserAdmin admin) throws Exception;

    public void update(UserAdmin admin) throws Exception;

    /**
     * @param adminId 当前需要更新头像的用户ID
     * @param file    当前需要更新的头像file
     */
    public String updateAvatar(Integer adminId, MultipartFile file) throws Exception;

    public void save(UserAdmin admin) throws Exception;

    public void delete(int id) throws Exception;

    /**
     * admin用户根据关键词分页查询
     *
     * @param keyword
     * @param start
     * @param limit
     * @return 记录总数 count，结果list
     */
    public Map<String, Object> query(String keyword, Integer status, Integer roleId, int start, int limit) throws Exception;

    /**
     * 验证用户名是否存在
     *
     * @param username
     * @return true 不存在, false存在
     * @throws Exception
     */
    public boolean checkNameExists(String username) throws Exception;

    /**
     * 验证旧的密码是否正确
     *
     * @param password
     * @return
     * @throws Exception
     */
    public boolean checkOldPassword(Integer id, String password) throws Exception;

    /**
     * 通过userAdminId去查询这个管理员拥有的所有权限
     *
     * @param userAdminId
     * @return List<Resources>
     * @Author:xinjiang
     */
    public List<Resources> selectResourcesByAdminId(Integer userAdminId, Integer isMenu, Integer parentId, boolean isOrder);

    /**
     * 查找用户详细信息
     *
     * @param id
     * @return
     */
    public UserAdmin detail(Integer id) throws Exception;

    public UserAdmin get(int id) throws Exception;

    /**
     * 根据用户名查找用户信息
     */
    public UserAdmin getByUsername(String username) throws Exception;

    public String getRoleNameByUserId(Integer userId);

    /**
     * 查询所有用户资金明细
     *
     * @return
     */
    public List<Map<String, Object>> getAllUserAssets(Integer start, Integer limit);

    /**
     * 查询所有用户资金明细
     *
     * @return
     */
    public List<AllAssetsExcel> getAllUserAssetsExcel(Integer start, Integer limit);

    public Integer getAllUserAssetsCount();

    /**
     * 查询所有用户资金明细
     *
     * @return
     */
    public List<CapitalDetail> getAllCapitalDetail(Integer start, Integer limit);

    /**
     * 查询所有用户资金明细
     *
     * @return
     */
    public List<Map<String, Object>> getAllCapitalDetailForPage(Integer start, Integer limit);

    public Integer getCapitalDetailCount();

    public List<CapitalDetail> getAllCapitalDetailForMonth(Date month);

    /**
     * 根据真实姓名查找用户
     *
     * @param trueName
     * @author zxx
     * @date 2016/8/25
     */
    public UserAdmin getByTrueName(String trueName);
    
    /**
     * 根据权限查询admin用户
     * @author xueqi
     * @param map
     * @return List<UserAdmin>
     */
    public List<UserAdmin> queryUserAdminByRoleId(String roleId);
    
    List<UserAdmin> queryUserAdminByCustomerUserId(Integer userId);

	public Integer queryUserAdminByPhone(String mobilePhone);
	
	List<Department> getAllDepartment();
}
