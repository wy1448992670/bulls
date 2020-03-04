package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.model.Banner;

public interface BannerService {

    /**
     * 查询banner 
     * @author ydp
     * @param status 状态
     * @param type 终端类型
     * @param source 来源（首页，发现）
     * @param page 页码
     * @return
     */
    public List<Banner> listByStatus(Integer status,Integer type,Integer source,Integer page);

    /**
     * 新增、修改  
     * @author ydp
     * @param banner
     */
    public void save(Banner banner);

    public Banner detail(Integer id);

    public void update(Banner banner);

    public Integer getCountBanner(Integer status,Integer type,Integer source);

    Banner getBannerByStatus(Integer status, Integer type, Integer source);
}
