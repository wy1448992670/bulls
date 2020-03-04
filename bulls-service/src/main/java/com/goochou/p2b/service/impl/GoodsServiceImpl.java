package com.goochou.p2b.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import com.goochou.p2b.constant.ExemptionAreaEnum;
import com.goochou.p2b.constant.client.ClientConstants;
import com.goochou.p2b.dao.*;
import com.goochou.p2b.model.vo.SecondKillActivityView;
import com.goochou.p2b.model.vo.SecondKillActivityViewExample;
import com.goochou.p2b.service.exceptions.LockFailureException;
import com.goochou.p2b.service.memcached.MemcachedManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.model.Area;
import com.goochou.p2b.model.AreaExample;
import com.goochou.p2b.model.DispatchArea;
import com.goochou.p2b.model.DispatchAreaExample;
import com.goochou.p2b.model.DispatchTemplete;
import com.goochou.p2b.model.DispatchTempleteExample;
import com.goochou.p2b.model.GoodsPicture;
import com.goochou.p2b.model.goods.Goods;
import com.goochou.p2b.model.goods.GoodsExample;
import com.goochou.p2b.model.goods.GoodsExample.Criteria;
import com.goochou.p2b.model.goods.GoodsProperty;
import com.goochou.p2b.model.goods.GoodsPropertyExample;
import com.goochou.p2b.service.AreaService;
import com.goochou.p2b.service.GoodsPropertyValueService;
import com.goochou.p2b.service.GoodsService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.StringUtil;

import javax.annotation.Resource;

/**
 * @Auther: huangsj
 * @Date: 2019/5/9 09:26
 * @Description:
 */
@Service
public class GoodsServiceImpl implements GoodsService {
	private static final Logger logger = Logger.getLogger(GoodsServiceImpl.class);
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private UploadService uploadService;
	@Autowired
	private GoodsPictureMapper goodsPictureMapper;
	@Autowired
	GoodsPropertyMapper goodsPropertyMapper;
	@Autowired
	GoodsPropertyValueService goodsPropertyValueService;
	@Autowired
	GoodsCategoryMapper goodsCategoryMapper;
	@Autowired
	GoodsBrandMapper goodsBrandMapper;
	@Autowired
	SecondKillActivityViewMapper secondKillActivityViewMapper;
	@Resource
    protected MemcachedManager memcachedManager;
	@Resource
	private AreaMapper areaMapper;
	@Resource
	private AreaService areaService;
	@Resource
	private DispatchAreaMapper dispatchAreaMapper;
	@Resource
	private DispatchTempleteMapper dispatchTempleteMapper;
	
    public MemcachedManager getCache() {
        return memcachedManager;
    }

    public void setCache(MemcachedManager memcachedManager) {
        this.memcachedManager = memcachedManager;
    }
	@Override
	public GoodsMapper getMapper() {
		return goodsMapper;

	}

	@Override
	public List<Goods> listGoods(Integer limitStart, Integer limitEnd, String keyword, String skuCode) {
		return goodsMapper.listGoodsByPage(limitStart, limitEnd, keyword, skuCode);
	}

	@Override
	public int countGoods(String keyword, String skuCode) {
		return goodsMapper.countGoodsByPage(keyword, skuCode);
	}

	@Override
	public void saveGoods(Goods goods) {
		goodsMapper.insert(goods);
	}

	@Override
	public List<Goods> selectGoodsList(Integer status, Integer start, Integer limit) {
		GoodsExample example = new GoodsExample();
		if (start != null) {
			example.setLimitStart(start);
		}
		if (limit != null) {
			example.setLimitEnd(limit);
		}
		example.setOrderByClause("id  desc");
		return goodsMapper.selectByExample(example);
	}

	@Override
	public List<GoodsProperty> listGoodsProperty(int categoryId) {
		GoodsPropertyExample example = new GoodsPropertyExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<GoodsProperty> properties = goodsPropertyMapper.selectByExample(example);
		return properties;
	}

	@Override
	public void saveWithPicture(Goods good, List<String> pictures, String[] productPropertyIdArray,
			String[] propertyValueArray) throws Exception {
		goodsMapper.insertSelective(good);
		goodsPropertyValueService.saveGoodsPropertyValue(productPropertyIdArray, propertyValueArray, good.getId());

		if (pictures != null && pictures.size() > 0) {
			for (String s : pictures) {
				updatePicture(s, good.getId());
			}
		}
	}

	@Override
	public Goods getGood(Integer id) {
		Goods goods = goodsMapper.selectByPrimaryKey(id);
		return goods;
	}

	@Override
	public List<Map<String, Object>> listGoodsProperties(Integer id) {
		return goodsMapper.listGoodsProperties(id);
	}

	@Override
	public void update(Goods goods, String picture, String picture2, String[] productPropertyIdArray,
			String[] propertyValueArray) throws Exception {

		goods.setUpdateDate(new Date());
		goodsMapper.updateByPrimaryKeySelective(goods);
		goodsPropertyValueService.updateProjectPropertyValue(productPropertyIdArray, propertyValueArray, goods.getId());
		updatePicture(picture, goods.getId());
		updatePicture(picture2, goods.getId());
	}

	public void updatePicture(String picture, Integer goodsId) {
		if (StringUtils.isNotBlank(picture)) {
			String[] ps = picture.split( ",");
			if (ps != null && ps.length > 0) {
				for (int i = 0; i < ps.length; i++) {
					GoodsPicture p = goodsPictureMapper.selectByPrimaryKey(Integer.parseInt(ps[i]));
					if (!StringUtil.isNull(p.getGoodsId())) {
						p.setId(null);
						p.setGoodsId(goodsId);
						p.setStatus(0);
						goodsPictureMapper.insertSelective(p);
					} else {
						p.setGoodsId(goodsId);
						p.setStatus(0);
						goodsPictureMapper.updateByPrimaryKeySelective(p);
					}
				}
			}
		}
	}

	@Override
	public List<Map<String, Object>> getAppGoodsList(Map<String, Object> map) {
		List<Map<String, Object>> goods = goodsMapper.getAppGoodsList(map);
		
		return goods;

	}

	@Override
	public int getAppGoodsCount(Map<String, Object> map) {
		return goodsMapper.getAppGoodsCount(map);
	}

	@Override
	public List<Goods> getGoodsByIds(List<Integer> ids) {
		if (ids == null || ids.isEmpty()) {
			return new ArrayList<>();
		}
		GoodsExample example = new GoodsExample();
		example.createCriteria().andIdIn(ids);
		return goodsMapper.selectByExample(example);
	}

	public BigDecimal calculateExpressFee(Double weight, Integer cityId) throws Exception{
		// cityId 查询非默认快递首重续重费用 dispatch_templete.isdefault = 0
		// 若未配置指定城市的快递,则查询该城市默认快递
		// 根据dispatch_templete模板重量是否超重,价格 =(总重量-首重)/续重单位*续重费用 + 首重费用
		
		// 查询城市信息
		Area area = areaService.getAreaByCode(String.valueOf(cityId));
	
		// 城市地区配送信息
		DispatchAreaExample dispatchAreaExample = new DispatchAreaExample();
		dispatchAreaExample.createCriteria().andCityIdEqualTo(cityId);
		List<DispatchArea> listDispatchArea = dispatchAreaMapper.selectByExample(dispatchAreaExample);
		
		if(listDispatchArea.isEmpty()) {
			String cityCode = String.valueOf(cityId).substring(0, 4).concat("00");//配置整个市
			dispatchAreaExample = new DispatchAreaExample();
			dispatchAreaExample.createCriteria().andCityIdEqualTo(Integer.parseInt(cityCode));
			listDispatchArea = dispatchAreaMapper.selectByExample(dispatchAreaExample);
		}
		
		if(listDispatchArea.isEmpty()) {
			String cityCode = String.valueOf(cityId).substring(0, 2).concat("0000");//配置整个省份
			dispatchAreaExample = new DispatchAreaExample();
			dispatchAreaExample.createCriteria().andCityIdEqualTo(Integer.parseInt(cityCode));
			listDispatchArea = dispatchAreaMapper.selectByExample(dispatchAreaExample);
		}
		// 根据地区配送信息查询模板
		DispatchTemplete template = new DispatchTemplete(); 
		if(listDispatchArea.isEmpty()) {// 省市区都未配置
			logger.info("未配置"+area.getName()+"配送,查询默认配送模板===========>");
			// 取默认配送模板
			DispatchTempleteExample templeteExample = new DispatchTempleteExample();
			templeteExample.createCriteria().andIsDefaultEqualTo(true);// dispatch_templete.isdefault = 1
			List<DispatchTemplete> listTemplate = dispatchTempleteMapper.selectByExample(templeteExample);
			if(!listTemplate.isEmpty() && listTemplate.size() > 1) {
				throw new Exception("多个默认配送模板");
			}
			template = listTemplate.get(0);
		} else {
			if(!listDispatchArea.isEmpty() && listDispatchArea.size() > 1) {
				throw new Exception("城市code:"+cityId+"配置多个模板");
			}
			DispatchArea dispatchArea = listDispatchArea.get(0);
			template = dispatchTempleteMapper.selectByPrimaryKey(dispatchArea.getTempleteId());
		}
		//模板重量是否超重,价格 =(总重量-首重)/续重单位*续重费用 + 首重费用
		BigDecimal totalDispatchAmount = BigDecimal.ZERO;
		if(weight.compareTo(template.getFirstHeavy().doubleValue()) > 0) {
			BigDecimal overaWeight = BigDecimal.valueOf(weight).subtract(template.getFirstHeavy());
			totalDispatchAmount = overaWeight.divide(template.getAddHeavy()).setScale(2).multiply(template.getAddHeavyPrice()).add(template.getFirstHeavyPrice());
		
		} else {// 不超重,快递费用取首重费用
			logger.info("不超重,快递费用取首重费用=======");
			totalDispatchAmount = template.getFirstHeavyPrice();
		}
		
		return totalDispatchAmount.setScale(2);
	}



	@Override
	public int updateForVersion(Goods goods) throws Exception {
		if (goods.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (goods.getVersion() == null) {
			throw new Exception("版本号不能为空");
		}
		GoodsExample example = new GoodsExample();
		example.createCriteria()
				.andIdEqualTo(goods.getId())
				.andVersionEqualTo(goods.getVersion());
		goods.setVersion(goods.getVersion() + 1);
		if(1 != goodsMapper.updateByExampleSelective(goods, example)) {
			throw new LockFailureException();
		}
		return 1;
	}
	@Override
	public boolean isHotGoods(Integer goodsId) throws Exception{
		boolean flag = false;
		List<Goods> goodslist = this.getHotGoods();
		if(!goodslist.isEmpty()) {
			for (Goods goods : goodslist) {
				if(goods.getId().intValue() == goodsId.intValue()) {
					flag = true;
					return flag;
				}
			}
		}
		return flag;
	}

	private List<Goods> getHotGoods() {
		Integer number = Integer.valueOf(memcachedManager.getCacheKeyValue("HOT_GOODS"));// 爆款商品数量
		if (number == null || number < 1) {
			logger.info("=====缓存中爆款商品数量为空=========");
			return new ArrayList<>();
		}
		GoodsExample example = new GoodsExample();
		GoodsExample.Criteria cri = example.createCriteria();
		cri.andStockGreaterThan(0); //库存>0
		example.setOrderByClause("sell_stock desc");
		example.setLimitStart(0);
		example.setLimitEnd(number);
		List<Goods> goodslist = goodsMapper.selectByExample(example);
		return goodslist;
	}

	/**
	 * @desc 是否是秒杀商品（不包括已过期）
	 * @author wangyun
	 * @param goodsId
	 * @return
	 */
	public List<SecondKillActivityView> activityKillGoodsByGoodsId(Integer goodsId) {
		SecondKillActivityViewExample example = new SecondKillActivityViewExample();
		com.goochou.p2b.model.vo.SecondKillActivityViewExample.Criteria  cri = example.createCriteria();
		cri.andGoodIdEqualTo(goodsId);
		// beginTime.before(new Date()) && endTime.before(new Date())
//		cri.andBeginTimeLessThan(new Date());
		cri.andEndTimeGreaterThan(new Date());
		example.setOrderByClause(" date ");
		List<SecondKillActivityView> activityView = secondKillActivityViewMapper.selectByExample(example);
		return activityView;
	}

    @Override
    public List<Goods> listGoodsBySales(Integer limitStart, Integer limitEnd) {
        GoodsExample example = new GoodsExample();
        example.setLimitStart(limitStart);
        example.setLimitEnd(limitEnd);
        example.setOrderByClause(" sell_stock desc ");
        
        Criteria criteria = example.createCriteria();
        criteria.andUpDownEqualTo(1);

        return this.getMapper().selectByExample(example);
    }
    public static DecimalFormat MONEY_FORMAT = new DecimalFormat("¥ ###,##0.00");
    @Override
    public List<Map<String, Object>> listGoodsByClick(Integer limitStart, Integer limitEnd) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("limitStart", limitStart);
        map.put("limitEnd", limitEnd);
        List<Map<String,Object>> list = this.getMapper().listGoodsByClick(map);
        // 爆款商品
		List<Goods> hotGoods = this.getHotGoods();

		Map<String, Object> temp = null;
		for(Map<String,Object> m : list) {
            m.put("path", ClientConstants.ALIBABA_PATH + "upload/" + m.get("path"));
            m.put("buying_price", m.get("buying_price")+"");
            m.put("saling_price", m.get("saling_price")+"");
            m.put("member_saling_price", m.get("member_saling_price")+"");
            
            m.put("buying_price_str", MONEY_FORMAT.format(new BigDecimal(m.get("buying_price")+"")));
            m.put("saling_price_str", MONEY_FORMAT.format(new BigDecimal(m.get("saling_price")+"")));
            m.put("member_saling_price_str", MONEY_FORMAT.format(new BigDecimal(m.get("member_saling_price")+"")));
            
            m.put("sellStockStr", m.get("sell_stock")+"人购买");

			List<Map<String, Object>> tags = new ArrayList<>();
			// 爆款商品按照sell_stock已销售排序取缓存中配置
			boolean isHot = false;
			Integer goodsId = Integer.parseInt(m.get("id")+"");
			for (Goods hg : hotGoods) {
				if (hg.getId().equals(goodsId)) {
					isHot = true;
					break;
				}
			}
			if(isHot) {
				temp = new HashMap<>();
				temp.put("tagName", "爆款");
				temp.put("color", "#FF4931");
				temp.put("imgPath", ClientConstants.ALIBABA_PATH + "upload/hot.png");
				tags.add(temp);
			}
			// 推荐
			boolean isRecommend = Integer.parseInt(m.get("is_recommend")+"") == 1 ? true : false ;
			m.put("isRecommend", m.get("is_recommend"));
			if(isRecommend) {
				temp = new HashMap<>();
				temp.put("tagName", "推荐");
				temp.put("color", "#00CC9F");
				temp.put("imgPath", ClientConstants.ALIBABA_PATH + "upload/recommend.png");
				tags.add(temp);
			}
			m.put("tags", tags);
        }

        return list;
    }

	/**
	 * @desc 查询所有秒杀活动 
	 * status -1已结束 0进行中 1未开始
	 * @author wangyun
	 * @return
	 */
    @Override
	public List<SecondKillActivityView> getAllSecondKillActivity(Date date, Integer status){
    	SecondKillActivityViewExample example = new SecondKillActivityViewExample();
    	com.goochou.p2b.model.vo.SecondKillActivityViewExample.Criteria cri = example.createCriteria();
    	if(date != null) {
    		cri.andDateEqualTo(date);
    	}
    	if(status != null) {
    		if(status == -1) { //已结束
    			cri.andEndTimeLessThan(new Date());
    		} else if(status == 0) {// 进行中
    			cri.andBeginTimeLessThanOrEqualTo(new Date());
    			cri.andEndTimeGreaterThanOrEqualTo(new Date());
    		} else if(status == 1){// 未开始
    			cri.andBeginTimeGreaterThan(new Date());
    		}
    	}
    	example.setOrderByClause(" date ");
		return secondKillActivityViewMapper.selectByExample(example);
	}

    @Override
    public List<Map<String, Object>> listGoodsForLove(Integer userId, Integer limitStart, Integer limitEnd) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("limitStart", limitStart);
        map.put("limitEnd", limitEnd);
        List<Map<String,Object>> list = this.getMapper().listGoodsForLove(map);
        // 爆款商品
        List<Goods> hotGoods = this.getHotGoods();

        Map<String, Object> temp = null;
        for(Map<String,Object> m : list) {
            m.put("path", ClientConstants.ALIBABA_PATH + "upload/" + m.get("path"));
            m.put("buying_price", m.get("buying_price")+"");
            m.put("saling_price", m.get("saling_price")+"");
            m.put("member_saling_price", m.get("member_saling_price")+"");
            m.put("sellStockStr", m.get("sell_stock")+"人购买");

            List<Map<String, Object>> tags = new ArrayList<>();
            // 爆款商品按照sell_stock已销售排序取缓存中配置
            boolean isHot = false;
            Integer goodsId = Integer.parseInt(m.get("id")+"");
            for (Goods hg : hotGoods) {
                if (hg.getId().equals(goodsId)) {
                    isHot = true;
                    break;
                }
            }
            if(isHot) {
                temp = new HashMap<>();
                temp.put("tagName", "爆款");
                temp.put("color", "#FF4931");
                temp.put("imgPath", ClientConstants.ALIBABA_PATH + "upload/hot.png");
                tags.add(temp);
            }
            // 推荐
            boolean isRecommend = Integer.parseInt(m.get("is_recommend")+"") == 1 ? true : false ;
            m.put("isRecommend", m.get("is_recommend"));
            if(isRecommend) {
                temp = new HashMap<>();
                temp.put("tagName", "推荐");
                temp.put("color", "#00CC9F");
                temp.put("imgPath", ClientConstants.ALIBABA_PATH + "upload/recommend.png");
                tags.add(temp);
            }
            m.put("tags", tags);
        }

        return list;
    }

    @Override
    public Goods queryGoodsDetailById(Integer goodsId) {
    	return goodsMapper.queryGoodsDetailById(goodsId);
    }


    @Override
    public List<SecondKillActivityView> queryAppIndex2WeeksActivity(){
    	return secondKillActivityViewMapper.queryAppIndex2WeeksActivity();
    }
    
    
    /**
	 * @desc 邮费
	 * @author wangyun
	 * @param weight
	 * @param amount
	 * @param cityId
	 * @return
	 * @throws Exception
	 */
    @Override
	public Map<String, Object> calculateExpressFee(Double weight, BigDecimal amount, String cityCode) throws Exception{
		Map<String, Object> map = new HashMap<>();
		BigDecimal expressFee = null;
		if(StringUtils.isEmpty(cityCode)) {
			throw new Exception("地区码不能为空");
		}
		// 实际邮费
		BigDecimal realExpressFee = calculateExpressFee(weight, Integer.parseInt(cityCode));
		//满200全国包邮
		if(amount.compareTo(BigDecimal.valueOf(200)) >= 0) { //在满200包邮省市区
			expressFee = BigDecimal.ZERO;
		}else {
			expressFee = realExpressFee;
		}
		
		/*
		//满200江浙沪包邮,满300全国包邮
		if(amount.compareTo(BigDecimal.valueOf(300)) < 0) {
			//  包邮只配置省份ID
			String proviceCode = cityCode.substring(0,2).concat("0000");
			ExemptionAreaEnum  exemption = ExemptionAreaEnum.getExemptionByType(1, Integer.parseInt(proviceCode));//满200 
			if(exemption != null && amount.compareTo(BigDecimal.valueOf(200)) >= 0) { //在满200包邮省市区
				expressFee = BigDecimal.ZERO;
			} else {
				expressFee = realExpressFee;
			}
		} else { //  满300全部包邮(显示给用户)
			expressFee = BigDecimal.ZERO;
		}
		*/
		map.put("expressFee", expressFee);
		map.put("realExpressFee", realExpressFee);
		logger.info("weight: "+weight+",amount: "+amount+",cityCode:"+cityCode+",运费: " + map);
		return map;
	}
    
    public static void main(String[] args) {
		System.err.println("342623".substring(0, 2).concat("0000"));
		 
	}
}
