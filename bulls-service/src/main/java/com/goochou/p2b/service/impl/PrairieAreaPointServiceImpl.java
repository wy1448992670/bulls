package com.goochou.p2b.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.PrairieAreaPointMapper;
import com.goochou.p2b.model.PrairieAreaPoint;
import com.goochou.p2b.model.PrairieAreaPointExample;
import com.goochou.p2b.service.PrairieAreaPointService;

@Service
public class PrairieAreaPointServiceImpl implements PrairieAreaPointService{
	
	
	@Resource
	PrairieAreaPointMapper prairieAreaPointMapper;
	
	@Override
    public List<PrairieAreaPoint> getByPrairieAreaId(Long prairieAreaId){
		PrairieAreaPointExample example=new PrairieAreaPointExample();
		example.createCriteria().andPrairieAreaIdEqualTo(prairieAreaId);
		return prairieAreaPointMapper.selectByExample(example);
    }
}


