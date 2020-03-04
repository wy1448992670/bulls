package com.goochou.p2b.service;

import java.util.List;

import com.goochou.p2b.dao.NewsMapper;
import com.goochou.p2b.model.News;
import com.goochou.p2b.model.NewsWithBLOBs;

public interface NewsService {

    /**
     * 查询新闻管理列表
     * @author sxy
     * @param isTop
     * @param status
     * @param start
     * @param limit
     * @param type
     * @return
     */
    public List<NewsWithBLOBs> query(Integer isTop, Integer status, Integer start, Integer limit, Integer type);
    public Integer queryCount(Integer isTop, Integer status, Integer type);

    public void save(NewsWithBLOBs news);

    public void update(NewsWithBLOBs news);

    public News detail(Integer id);

    public void delete(Integer id);

    /**
     * @Description: APP  媒体报道
     * @date 2016/10/14
     * @author 王信
     */
    List<NewsWithBLOBs> selectAppNewsList(Integer start, Integer limit, Integer type);

    Integer selectAppNewsCount();

    /**
     * @author shuys
     * @date 2019/6/14
     * @param
     * @return com.goochou.p2b.dao.NewsMapper
    */
    NewsMapper getMapper();
}
