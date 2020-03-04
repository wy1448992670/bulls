package com.goochou.p2b.service;

import com.goochou.p2b.dao.ShareMapper;
import com.goochou.p2b.model.Share;

import java.util.List;
import java.util.Map;

public interface ShareService {


    /**
     * 查询所有推广链接
     * @return
     * @author zxx
     * @date 2016年9月5日
     * @parameter
     */
    public List<Share> queryAll(Integer start ,Integer limit);
    public Integer queryCount();


    /**
     *
     * @return
     * @author zxx
     * @date 2016年9月5日
     * @parameter
     */
    public Share queryByKey(Integer id);

    public void saveOrUpdate(Share share);

    ShareMapper getMapper();

    /**
     * @description 删除
     * @author shuys
     * @date 2019/7/3
     * @param id
     * @param status
     * @return void
    */
    void delShare(Integer id, Integer status) throws Exception;

    /**
     * @description 分享列表
     * @author shuys
     * @date 2019/7/3
     * @param start
     * @param limit
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    List<Map<String, Object>> queryShareList(Integer start, Integer limit);
    
    Share queryByClickWord(String clickWord);
}
