package com.goochou.p2b.service;

import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserAuthentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * liuyuan
 * 2016/5/20.
 */
public interface UserAuthenticationService {
    /**
     * 保存认证实体
     * @param record
     * @author 刘源
     * @date 2016/5/24
     */
    void save(UserAuthentication record) throws Exception;

    /**
     * 查询用户实名记录
     * @param keyword
     * @param type
     * @param status
     * @param start
     * @param limit
     * @author 刘源
     * @date 2016/5/24
     * @return
     */
    List<Map<String,Object>> queryAuthenticationList(String keyword, Integer type, Integer status, Integer start, Integer limit);

    /**
     * 查询用户实名记录总数
     * @param keyword
     * @param type
     * @param status
     * @author 刘源
     * @date 2016/5/24
     * @return
     */
    Integer queryAuthenticationListCount(String keyword, Integer type, Integer status);

    /**
     * 查询用户实名操作记录
     * @param id
     * @author 刘源
     * @date 2016/5/24
     */
    Map<String,Object> queryByIdWithMap(Integer id);

    /**
     * 查询用户实名操作实体
     * @param id
     * @author 刘源
     * @date 2016/5/24
     */
    UserAuthentication queryById(Integer id);
    
    /**
     * @date 2019年6月20日
     * @author wangyun
     * @time 上午10:52:34
     * @Description 查询姓名和身份证是否已经实名
     * 
     * @param realName
     * @param idCard
     * @return
     */
    boolean queryUserIsAuth(Integer userId, String realName, String idCard);
}
