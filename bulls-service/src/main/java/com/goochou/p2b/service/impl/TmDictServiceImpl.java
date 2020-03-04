package com.goochou.p2b.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.dao.AdminLogMapper;
import com.goochou.p2b.dao.TmDictMapper;
import com.goochou.p2b.model.TmDict;
import com.goochou.p2b.model.TmDictExample;
import com.goochou.p2b.model.TmDictExample.Criteria;
import com.goochou.p2b.service.ActivityService;
import com.goochou.p2b.service.PrairieAreaService;
import com.goochou.p2b.service.PrairieAreaTacticsService;
import com.goochou.p2b.service.TmDictService;
import com.goochou.p2b.service.memcached.MemcachedManager;
import com.goochou.p2b.utils.StringUtils;

@Service
public class TmDictServiceImpl implements TmDictService {
    @Resource
    private TmDictMapper tmDictMapper;
    @Resource
    protected MemcachedManager memcachedManager;
    @Resource
    private AdminLogMapper adminLogMapper;
    @Resource
    private ActivityService activityService;
    @Resource
    private PrairieAreaTacticsService prairieAreaTacticsService;
    @Resource
    private PrairieAreaService prairieAreaService;
    
    public TmDict get(Integer id) {
        return tmDictMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TmDict> queryDictList(String keyword, Integer start, Integer limit, String name, String key) {
        TmDictExample tmDictExample = new TmDictExample();
        Criteria criteria = tmDictExample.createCriteria();
        if (StringUtils.isNotEmpty(name)) {
            criteria.andTNameLike("%"+name+"%");
        }
        if (StringUtils.isNotEmpty(key)) {
            criteria.andTKeyLike("%"+key+"%");
        }
        tmDictExample.setLimitStart(start);
        tmDictExample.setLimitEnd(limit);
        tmDictExample.setOrderByClause("id desc");
        return tmDictMapper.selectByExample(tmDictExample);
    }

    @Override
    public int queryDictListCount(String keyword, String name, String key) {
        TmDictExample tmDictExample = new TmDictExample();
        Criteria criteria = tmDictExample.createCriteria();
        if (StringUtils.isNotEmpty(name)) {
            criteria.andTNameLike("%"+name+"%");
        }
        if (StringUtils.isNotEmpty(key)) {
            criteria.andTKeyLike("%"+key+"%");
        }
        return tmDictMapper.countByExample(tmDictExample);
    }

    @Override
    public int save(TmDict tmDict) {
        return tmDictMapper.insert(tmDict);
    }

    @Override
    public int updateDict(TmDict tmDict) {
        return tmDictMapper.updateByPrimaryKeySelective(tmDict);
    }
    
    @Override
    public void doFulshCache() {
    	 try {
             Map<String, Map<String,String>> map = new LinkedHashMap<String, Map<String,String>>();
             List<Map<String, Object>> dicts = adminLogMapper.selectDicts();
             if(null != dicts && dicts.size()>0){
                 for(Map<String, Object> dict : dicts){
                     Map<String,String> value = new LinkedHashMap<String, String>();
                     if(map.get(dict.get("t_key")+"")!=null){
                         map.get(dict.get("t_key")+"").put(dict.get("t_name")+"", dict.get("t_value")+"");
                     }else{
                         value.put(dict.get("t_name")+"", dict.get("t_value")+"");
                         map.put(dict.get("t_key")+"", value);
                     }
                 }
             }
             if(!memcachedManager.addOrReplace(Constants.DICTS,map,0)) {
            	 throw new Exception("刷新memcached.server1.host "+Constants.DICTS+" 失败");
             }else {
            	 System.out.println("刷新memcached.server1.host "+Constants.DICTS+" 成功..");
             }
             
             activityService.doFlushCacheActionActivityDetailMap();
             System.out.println("刷新memcached.server1.host "+Constants.ACTIVITY_DETAIL_LIST_MAP+" 成功..");
             
             prairieAreaTacticsService.flushPrairieAreaTacticsCache();
             
             memcachedManager.delete(Constants.BANNERS);
             memcachedManager.delete(Constants.ICONS);
         }catch (Exception e){
             throw new RuntimeException("初始化缓存异常", e);
         }
    }
    
    @Override
	public List<TmDict> listTmDict(String key) {
		TmDictExample example = new TmDictExample();
		example.createCriteria().andTKeyEqualTo(key);
		example.setOrderByClause(" t_sort asc ");
		return tmDictMapper.selectByExample(example);
	}
}
