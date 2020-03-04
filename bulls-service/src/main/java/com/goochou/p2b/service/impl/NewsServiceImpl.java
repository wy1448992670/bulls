package com.goochou.p2b.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.NewsMapper;
import com.goochou.p2b.model.News;
import com.goochou.p2b.model.NewsExample;
import com.goochou.p2b.model.NewsWithBLOBs;
import com.goochou.p2b.service.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsMapper;

    @Override
    public List<NewsWithBLOBs> query(Integer isTop, Integer status, Integer start, Integer limit, Integer type) {
        NewsExample example = new NewsExample();
        example.setLimitStart(start);
        example.setLimitEnd(limit);
        example.setOrderByClause("is_top desc,create_date desc");
        NewsExample.Criteria c = example.createCriteria();
        if (isTop != null) {
            c.andIsTopEqualTo(isTop);
        }
        if (status != null) {
            c.andStatusEqualTo(status);
        }
        if (type != null) {
            c.andTypeEqualTo(type);
        }
        return newsMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public Integer queryCount(Integer isTop, Integer status, Integer type) {
        NewsExample example = new NewsExample();
        NewsExample.Criteria c = example.createCriteria();
        if (isTop != null) {
            c.andIsTopEqualTo(isTop);
        }
        if (status != null) {
            c.andStatusEqualTo(status);
        }
        if (type != null) {
            c.andTypeEqualTo(type);
        }

        return (int) newsMapper.countByExample(example);
    }

    @Override
    public void save(NewsWithBLOBs news) {
        newsMapper.insertSelective(news);
    }

    @Override
    public void update(NewsWithBLOBs news) {
        newsMapper.updateByPrimaryKeySelective(news);
    }

    @Override
    public News detail(Integer id) {
        return newsMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Integer id) {
        newsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<NewsWithBLOBs> selectAppNewsList(Integer start, Integer limit, Integer type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("start", start);
        map.put("limit", limit);
        return newsMapper.selectAppNewsList(map);
    }

    @Override
    public Integer selectAppNewsCount() {
        return (int) newsMapper.countByExample(new NewsExample());
    }

    @Override
    public NewsMapper getMapper() {
        return newsMapper;
    }

}
