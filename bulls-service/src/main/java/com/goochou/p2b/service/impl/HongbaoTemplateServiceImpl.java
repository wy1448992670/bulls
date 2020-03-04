package com.goochou.p2b.service.impl;

import com.goochou.p2b.dao.HongbaoTemplateMapper;
import com.goochou.p2b.model.HongbaoTemplate;
import com.goochou.p2b.service.HongbaoTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HongbaoTemplateServiceImpl
 *
 * @author 刘源
 * @date 2016/6/23
 */
@Service
public class HongbaoTemplateServiceImpl implements HongbaoTemplateService {

    @Resource
    private HongbaoTemplateMapper hongbaoTemplateMapper;

    @Override
    public List<Map<String, Object>> query(String keyword, Integer type, Integer status, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("type", type);
        map.put("status", status);
        map.put("start", start);
        map.put("limit", limit);
        return hongbaoTemplateMapper.query(map);
    }

    @Override
    public Integer queryCount(String keyword, Integer type, Integer status) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("type", type);
        map.put("status", status);
        return hongbaoTemplateMapper.queryCount(map);
    }

    @Override
    public Map<String, Object> queryById(Integer id) {
        return hongbaoTemplateMapper.queryById(id);
    }

    @Override
    public void save(HongbaoTemplate template) {
        if(template.getId() == null){
            hongbaoTemplateMapper.insertSelective(template);
        }else{
            hongbaoTemplateMapper.updateByPrimaryKeySelective(template);
        }
    }

    @Override
    public List<HongbaoTemplate> queryByRedeemId(Integer redeemId) {
    	List<HongbaoTemplate> hts = new ArrayList<>();
    	if(null != redeemId){
    		hts = hongbaoTemplateMapper.queryByRedeemId(redeemId);
    	}
        return hts;
    }
}
