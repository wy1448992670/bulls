package com.goochou.p2b.service;

import com.goochou.p2b.model.LoginRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LoginRecordService {

    /**
     * @Description(描述):查询用户所有的登录记录
     * @author 王信
     * @date 2016/5/6
     * @params
     **/
    public List<Map<String, Object>> selectAllLoginRecord(Integer start, Integer limit, String keyword, Date startDate, Date endDate);

    public Integer selectAllLoginRecordCount(String keyword, Date startDate, Date endDate);

    /**
     * @Description(描述):保存设备信息
     * @author 王信
     * @date 2016/5/6
     * @params
     **/
    public void saveLoginRecord(LoginRecord record);

    public LoginRecord getFirstLogin(Integer userId);


}
