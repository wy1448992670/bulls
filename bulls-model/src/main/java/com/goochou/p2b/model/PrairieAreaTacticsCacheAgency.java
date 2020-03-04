package com.goochou.p2b.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 缓存数据代理.
 * 读取一次memcache缓存,存放在缓存代理中,可以一直访问,不用每次访问数据都读取memcache缓存,减小带宽压力
 * @author admin
 */
/**
 * @author 张琼麒
 * @version 创建时间：2019年9月9日 下午6:03:58
 * 类说明
 */
public class PrairieAreaTacticsCacheAgency implements Serializable{
 
	private Map<Long,List<PrairieArea>> prairieIdKPrairieAreaListVMap=null;
	private Map<Long,PrairieArea> prairieAreaIdKPrairieAreaVMap=null;
	private Map<Long, List<PrairieAreaTactics>> prairieIdKPrairieAreaTacticsListVMap = null;
	
	public Map<Long, List<PrairieArea>> getPrairieIdKPrairieAreaListVMap() {
		return prairieIdKPrairieAreaListVMap;
	}
	public void setPrairieIdKPrairieAreaListVMap(Map<Long, List<PrairieArea>> prairieIdKPrairieAreaListVMap) {
		this.prairieIdKPrairieAreaListVMap = prairieIdKPrairieAreaListVMap;
	}
	public Map<Long, PrairieArea> getPrairieAreaIdKPrairieAreaVMap() {
		return prairieAreaIdKPrairieAreaVMap;
	}
	public void setPrairieAreaIdKPrairieAreaVMap(Map<Long, PrairieArea> prairieAreaIdKPrairieAreaVMap) {
		this.prairieAreaIdKPrairieAreaVMap = prairieAreaIdKPrairieAreaVMap;
	}
	public Map<Long, List<PrairieAreaTactics>> getPrairieIdKPrairieAreaTacticsListVMap() {
		return prairieIdKPrairieAreaTacticsListVMap;
	}
	public void setPrairieIdKPrairieAreaTacticsListVMap(
			Map<Long, List<PrairieAreaTactics>> prairieIdKPrairieAreaTacticsListVMap) {
		this.prairieIdKPrairieAreaTacticsListVMap = prairieIdKPrairieAreaTacticsListVMap;
	}
	
	public List<PrairieArea> listPrairieAreaByPrairieId(Long prairie_id){
    	return prairieIdKPrairieAreaListVMap.get(prairie_id);
    }
    public PrairieArea getPrairieAreaById(Long id){
    	return prairieAreaIdKPrairieAreaVMap.get(id);
    }
	public List<PrairieAreaTactics> listPrairieAreaTacticsByPrairieId(Long prairie_id) {
		return prairieIdKPrairieAreaTacticsListVMap.get(prairie_id);
	}
	
}
