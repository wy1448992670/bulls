package com.goochou.p2b.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.PrairieAreaMapper;
import com.goochou.p2b.model.PrairieArea;
import com.goochou.p2b.model.PrairieAreaExample;
import com.goochou.p2b.service.PrairieAreaPointService;
import com.goochou.p2b.service.PrairieAreaService;

@Service
public class PrairieAreaServiceImpl implements PrairieAreaService{
	private final static Logger logger = Logger.getLogger(PrairieAreaServiceImpl.class);
	
	@Resource
	PrairieAreaMapper prairieAreaMapper;
	
	@Resource
	PrairieAreaPointService prairieAreaPointService;
    
    /**
     * 按PrairieId组装 有序的策略组map
     * @author 张琼麒
     * @version 创建时间：2019年8月28日 下午3:17:09
     * @return
     */
    @Override
	public Map<Long,List<PrairieArea>> getPrairieIdKPrairieAreaListVMap(){
    	Map<Long,List<PrairieArea>> prairieIdKPrairieAreaListVMap=new HashMap<Long,List<PrairieArea>>(1 << 8);
    	
    	PrairieAreaExample example=new PrairieAreaExample();
    	example.createCriteria().andPrairieIdIsNotNull();
    	example.setOrderByClause("sequence asc");
    	for(PrairieArea prairieArea:prairieAreaMapper.selectByExample(example)) {
    		List<PrairieArea> prairieAreaList=null;
    		prairieAreaList=prairieIdKPrairieAreaListVMap.get(prairieArea.getPrairieId());
    		if(prairieAreaList==null) {
    			prairieAreaList=new ArrayList<PrairieArea>();
    			prairieIdKPrairieAreaListVMap.put(prairieArea.getPrairieId(), prairieAreaList);
    		}
    		prairieAreaList.add(prairieArea);
    		prairieArea.setPrairieAreaPointList(prairieAreaPointService.getByPrairieAreaId(prairieArea.getId()));
    	}
    	return prairieIdKPrairieAreaListVMap;
    }
    
}


