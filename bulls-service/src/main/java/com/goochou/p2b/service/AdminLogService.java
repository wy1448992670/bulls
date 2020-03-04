package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;

import com.goochou.p2b.model.AdminLog;

public interface AdminLogService {
    /**
     * 插入日志，根据remark修改日志内容
     *
     * @param log
     */
    public void save(AdminLog log);

    /**
     * 根据条件查询Admin日志
     * @author sxy
     * @param log
     */
    public List<AdminLog> query(String keyWord, Date startTime, Date endTime, Integer lvl, Integer i, Integer j);

    /**
     * 根据条件查询AdminCount
     *
     * @param log
     */
    public Long queryCount(String keyWord, Date startTime, Date endTime, Integer lvl);

}
