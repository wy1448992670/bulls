package com.goochou.p2b.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.ProvinceCityMapper;
import com.goochou.p2b.model.ProvinceCity;
import com.goochou.p2b.service.ProvinceCityService;

@Service
public class ProvinceCityServiceImpl implements ProvinceCityService {
    @Resource
    private ProvinceCityMapper provinceCityMapper;

    @Override
    public List<ProvinceCity> query() throws Exception {
         return provinceCityMapper.selectByExample(null);
    }
    
}
