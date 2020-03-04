package com.goochou.p2b.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.utils.BigDecimalUtil;

@Service
public class ScreenInfoDemoService {
	@Autowired
	TmDictService tmDictService;
	/**
	 * 牧场信息饼图假数据
	 * 
	 * @Title: demoPieData
	 * @param prairieValue
	 * @return Map<String,Object>
	 */
	public Map<String, Object> demoPieData(String prairieValue) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> allCattleMap = new HashMap<String, Object>(); // 牛总数

		Map<String, Object> bullMap = new HashMap<String, Object>();// 公牛数量
		Map<String, Object> cowMap = new HashMap<String, Object>();// 母牛数量

		Map<String, Object> expectantCowQuantityMap = new HashMap<String, Object>();// 待产牛
		Map<String, Object> expectantCowNoQuantityMap = new HashMap<String, Object>();// 非待产

		Map<String, Object> ageMap0to6 = new HashMap<String, Object>();// 月龄
		Map<String, Object> ageMap6to12 = new HashMap<String, Object>();// 月龄
		Map<String, Object> ageMap12to24 = new HashMap<String, Object>();// 月龄
		Map<String, Object> ageMap24 = new HashMap<String, Object>();// 月龄

		Map<String, Object> weightMap20to100 = new HashMap<String, Object>();
		Map<String, Object> weightMap100to300 = new HashMap<String, Object>();
		Map<String, Object> weightMap300to500 = new HashMap<String, Object>();
		Map<String, Object> weightMap500 = new HashMap<String, Object>();

		switch (prairieValue) {
		case "10":
			map = new HashMap<String, Object>();

			allCattleMap.put("value", 100);// 牛总数
			allCattleMap.put("name", "牛总数量");
			map.put("all", allCattleMap);

			// 公母
			bullMap.put("value", 15);
			bullMap.put("name", "公牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(15, 100, 4)));
			cowMap.put("value", 85);
			cowMap.put("name", "母牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(15, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(bullMap);
			list.add(cowMap);
			map.put("gm", list);

			// 待产
			expectantCowQuantityMap.put("value", 40);
			expectantCowQuantityMap.put("name", "待产牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(40, 100, 4)));
			expectantCowNoQuantityMap.put("value", 45);
			expectantCowNoQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%").format(1-BigDecimalUtil.divDown(40, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(expectantCowQuantityMap);
			list.add(expectantCowNoQuantityMap);
			map.put("expectant", list);

			// 月龄
			ageMap0to6.put("value", 8);
			ageMap0to6.put("name", "0-6个月");
			ageMap6to12.put("value", 10);
			ageMap6to12.put("name", "6-12个月");
			ageMap12to24.put("value", 22);
			ageMap12to24.put("name", "12-24个月");
			ageMap24.put("value", 60);
			ageMap24.put("name", "24个月以上");
			list = new ArrayList<Map<String, Object>>();
			list.add(ageMap0to6);
			list.add(ageMap6to12);
			list.add(ageMap12to24);
			list.add(ageMap24);
			map.put("age", list);

			// 体重
			weightMap20to100.put("value", 28);
			weightMap20to100.put("name", "20KG-100KG牛崽子");
			weightMap100to300.put("value", 35);
			weightMap100to300.put("name", "100KG-300KG青年牛");
			weightMap300to500.put("value", 27);
			weightMap300to500.put("name", "300KG-500KG成年牛");
			weightMap500.put("value", 10);
			weightMap500.put("name", "500KG以上育肥牛");
			list = new ArrayList<Map<String, Object>>();
			list.add(weightMap20to100);
			list.add(weightMap100to300);
			list.add(weightMap300to500);
			list.add(weightMap500);
			map.put("weight", list);

			break;
		case "2":
			map = new HashMap<String, Object>();

			allCattleMap.put("value", 100);// 牛总数
			allCattleMap.put("name", "牛总数量");
			map.put("all", allCattleMap);

			// 公母
			bullMap.put("value", 10);
			bullMap.put("name", "公牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(10, 100, 4)));
			cowMap.put("value", 90);
			cowMap.put("name", "母牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(10, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(bullMap);
			list.add(cowMap);
			map.put("gm", list);

			// 待产
			expectantCowQuantityMap.put("value", 50);
			expectantCowQuantityMap.put("name", "待产牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(50, 90, 4)));
			expectantCowNoQuantityMap.put("value", 40);
			expectantCowNoQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%").format(1-BigDecimalUtil.divDown(50, 90, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(expectantCowQuantityMap);
			list.add(expectantCowNoQuantityMap);
			map.put("expectant", list);

			// 月龄
			ageMap0to6.put("value", 18);
			ageMap0to6.put("name", "0-6个月");
			ageMap6to12.put("value", 10);
			ageMap6to12.put("name", "6-12个月");
			ageMap12to24.put("value", 22);
			ageMap12to24.put("name", "12-24个月");
			ageMap24.put("value", 50);
			ageMap24.put("name", "24个月以上");
			list = new ArrayList<Map<String, Object>>();
			list.add(ageMap0to6);
			list.add(ageMap6to12);
			list.add(ageMap12to24);
			list.add(ageMap24);
			map.put("age", list);

			// 体重
			weightMap20to100.put("value", 23);
			weightMap20to100.put("name", "20KG-100KG牛崽子");
			weightMap100to300.put("value", 35);
			weightMap100to300.put("name", "100KG-300KG青年牛");
			weightMap300to500.put("value", 27);
			weightMap300to500.put("name", "300KG-500KG成年牛");
			weightMap500.put("value", 15);
			weightMap500.put("name", "500KG以上育肥牛");
			list = new ArrayList<Map<String, Object>>();
			list.add(weightMap20to100);
			list.add(weightMap100to300);
			list.add(weightMap300to500);
			list.add(weightMap500);
			map.put("weight", list);
			break;
		case "3":
			map = new HashMap<String, Object>();

			allCattleMap.put("value", 100);// 牛总数
			allCattleMap.put("name", "牛总数量");
			map.put("all", allCattleMap);

			// 公母
			bullMap.put("value", 25);
			bullMap.put("name", "公牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(25, 100, 4)));
			cowMap.put("value", 75);
			cowMap.put("name", "母牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(25, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(bullMap);
			list.add(cowMap);
			map.put("gm", list);

			// 待产
			expectantCowQuantityMap.put("value", 40);
			expectantCowQuantityMap.put("name", "待产牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(40, 100, 4)));
			expectantCowNoQuantityMap.put("value", 35);
			expectantCowNoQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%").format(1-BigDecimalUtil.divDown(40, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(expectantCowQuantityMap);
			list.add(expectantCowNoQuantityMap);
			map.put("expectant", list);

			// 月龄
			ageMap0to6.put("value", 18);
			ageMap0to6.put("name", "0-6个月");
			ageMap6to12.put("value", 10);
			ageMap6to12.put("name", "6-12个月");
			ageMap12to24.put("value", 22);
			ageMap12to24.put("name", "12-24个月");
			ageMap24.put("value", 50);
			ageMap24.put("name", "24个月以上");
			list = new ArrayList<Map<String, Object>>();
			list.add(ageMap0to6);
			list.add(ageMap6to12);
			list.add(ageMap12to24);
			list.add(ageMap24);
			map.put("age", list);

			// 体重
			weightMap20to100.put("value", 28);
			weightMap20to100.put("name", "20KG-100KG牛崽子");
			weightMap100to300.put("value", 32);
			weightMap100to300.put("name", "100KG-300KG青年牛");
			weightMap300to500.put("value", 13);
			weightMap300to500.put("name", "300KG-500KG成年牛");
			weightMap500.put("value", 10);
			weightMap500.put("name", "500KG以上育肥牛");
			list = new ArrayList<Map<String, Object>>();
			list.add(weightMap20to100);
			list.add(weightMap100to300);
			list.add(weightMap300to500);
			list.add(weightMap500);
			map.put("weight", list);
			break;
		case "4":
			map = new HashMap<String, Object>();

			allCattleMap.put("value", 100);// 牛总数
			allCattleMap.put("name", "牛总数量");
			map.put("all", allCattleMap);

			// 公母
			bullMap.put("value", 42);
			bullMap.put("name", "公牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(42, 100, 4)));
			cowMap.put("value", 58);
			cowMap.put("name", "母牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(42, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(bullMap);
			list.add(cowMap);
			map.put("gm", list);

			// 待产
			expectantCowQuantityMap.put("value", 20);
			expectantCowQuantityMap.put("name", "待产牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(20, 100, 4)));
			expectantCowNoQuantityMap.put("value", 38);
			expectantCowNoQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%").format(1-BigDecimalUtil.divDown(20, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(expectantCowQuantityMap);
			list.add(expectantCowNoQuantityMap);
			map.put("expectant", list);

			// 月龄
			ageMap0to6.put("value", 22);
			ageMap0to6.put("name", "0-6个月");
			ageMap6to12.put("value", 36);
			ageMap6to12.put("name", "6-12个月");
			ageMap12to24.put("value", 22);
			ageMap12to24.put("name", "12-24个月");
			ageMap24.put("value", 10);
			ageMap24.put("name", "24个月以上");
			list = new ArrayList<Map<String, Object>>();
			list.add(ageMap0to6);
			list.add(ageMap6to12);
			list.add(ageMap12to24);
			list.add(ageMap24);
			map.put("age", list);

			// 体重
			weightMap20to100.put("value", 22);
			weightMap20to100.put("name", "20KG-100KG牛崽子");
			weightMap100to300.put("value", 25);
			weightMap100to300.put("name", "100KG-300KG青年牛");
			weightMap300to500.put("value", 28);
			weightMap300to500.put("name", "300KG-500KG成年牛");
			weightMap500.put("value", 25);
			weightMap500.put("name", "500KG以上育肥牛");
			list = new ArrayList<Map<String, Object>>();
			list.add(weightMap20to100);
			list.add(weightMap100to300);
			list.add(weightMap300to500);
			list.add(weightMap500);
			map.put("weight", list);
			break;
		case "5":
			map = new HashMap<String, Object>();

			allCattleMap.put("value", 100);// 牛总数
			allCattleMap.put("name", "牛总数量");
			map.put("all", allCattleMap);

			// 公母
			bullMap.put("value", 55);
			bullMap.put("name", "公牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(55, 100, 4)));
			cowMap.put("value", 45);
			cowMap.put("name", "母牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(55, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(bullMap);
			list.add(cowMap);
			map.put("gm", list);

			// 待产
			expectantCowQuantityMap.put("value", 15);
			expectantCowQuantityMap.put("name", "待产牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(15, 100, 4)));
			expectantCowNoQuantityMap.put("value", 30);
			expectantCowNoQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%").format(1-BigDecimalUtil.divDown(15, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(expectantCowQuantityMap);
			list.add(expectantCowNoQuantityMap);
			map.put("expectant", list);

			// 月龄
			ageMap0to6.put("value", 22);
			ageMap0to6.put("name", "0-6个月");
			ageMap6to12.put("value", 26);
			ageMap6to12.put("name", "6-12个月");
			ageMap12to24.put("value", 32);
			ageMap12to24.put("name", "12-24个月");
			ageMap24.put("value", 20);
			ageMap24.put("name", "24个月以上");
			list = new ArrayList<Map<String, Object>>();
			list.add(ageMap0to6);
			list.add(ageMap6to12);
			list.add(ageMap12to24);
			list.add(ageMap24);
			map.put("age", list);

			// 体重
			weightMap20to100.put("value", 30);
			weightMap20to100.put("name", "20KG-100KG牛崽子");
			weightMap100to300.put("value", 25);
			weightMap100to300.put("name", "100KG-300KG青年牛");
			weightMap300to500.put("value", 20);
			weightMap300to500.put("name", "300KG-500KG成年牛");
			weightMap500.put("value", 25);
			weightMap500.put("name", "500KG以上育肥牛");
			list = new ArrayList<Map<String, Object>>();
			list.add(weightMap20to100);
			list.add(weightMap100to300);
			list.add(weightMap300to500);
			list.add(weightMap500);
			map.put("weight", list);
			break;
		case "6":
			map = new HashMap<String, Object>();

			allCattleMap.put("value", 100);// 牛总数
			allCattleMap.put("name", "牛总数量");
			map.put("all", allCattleMap);

			// 公母
			bullMap.put("value", 65);
			bullMap.put("name", "公牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(65, 100, 4)));
			cowMap.put("value", 35);
			cowMap.put("name", "母牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(65, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(bullMap);
			list.add(cowMap);
			map.put("gm", list);

			// 待产
			expectantCowQuantityMap.put("value", 15);
			expectantCowQuantityMap.put("name", "待产牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(15, 100, 4)));
			expectantCowNoQuantityMap.put("value", 20);
			expectantCowNoQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%").format(1-BigDecimalUtil.divDown(15, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(expectantCowQuantityMap);
			list.add(expectantCowNoQuantityMap);
			map.put("expectant", list);

			// 月龄
			ageMap0to6.put("value", 22);
			ageMap0to6.put("name", "0-6个月");
			ageMap6to12.put("value", 28);
			ageMap6to12.put("name", "6-12个月");
			ageMap12to24.put("value", 30);
			ageMap12to24.put("name", "12-24个月");
			ageMap24.put("value", 20);
			ageMap24.put("name", "24个月以上");
			list = new ArrayList<Map<String, Object>>();
			list.add(ageMap0to6);
			list.add(ageMap6to12);
			list.add(ageMap12to24);
			list.add(ageMap24);
			map.put("age", list);

			// 体重
			weightMap20to100.put("value", 30);
			weightMap20to100.put("name", "20KG-100KG牛崽子");
			weightMap100to300.put("value", 30);
			weightMap100to300.put("name", "100KG-300KG青年牛");
			weightMap300to500.put("value", 15);
			weightMap300to500.put("name", "300KG-500KG成年牛");
			weightMap500.put("value", 25);
			weightMap500.put("name", "500KG以上育肥牛");
			list = new ArrayList<Map<String, Object>>();
			list.add(weightMap20to100);
			list.add(weightMap100to300);
			list.add(weightMap300to500);
			list.add(weightMap500);
			map.put("weight", list);
			break;
		case "7":
			map = new HashMap<String, Object>();

			allCattleMap.put("value", 100);// 牛总数
			allCattleMap.put("name", "牛总数量");
			map.put("all", allCattleMap);

			// 公母
			bullMap.put("value", 35);
			bullMap.put("name", "公牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(35, 100, 4)));
			cowMap.put("value", 65);
			cowMap.put("name", "母牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(35, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(bullMap);
			list.add(cowMap);
			map.put("gm", list);

			// 待产
			expectantCowQuantityMap.put("value", 35);
			expectantCowQuantityMap.put("name", "待产牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(35, 100, 4)));
			expectantCowNoQuantityMap.put("value", 30);
			expectantCowNoQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%").format(1-BigDecimalUtil.divDown(35, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(expectantCowQuantityMap);
			list.add(expectantCowNoQuantityMap);
			map.put("expectant", list);

			// 月龄
			ageMap0to6.put("value", 22);
			ageMap0to6.put("name", "0-6个月");
			ageMap6to12.put("value", 20);
			ageMap6to12.put("name", "6-12个月");
			ageMap12to24.put("value", 38);
			ageMap12to24.put("name", "12-24个月");
			ageMap24.put("value", 20);
			ageMap24.put("name", "24个月以上");
			list = new ArrayList<Map<String, Object>>();
			list.add(ageMap0to6);
			list.add(ageMap6to12);
			list.add(ageMap12to24);
			list.add(ageMap24);
			map.put("age", list);

			// 体重
			weightMap20to100.put("value", 10);
			weightMap20to100.put("name", "20KG-100KG牛崽子");
			weightMap100to300.put("value", 30);
			weightMap100to300.put("name", "100KG-300KG青年牛");
			weightMap300to500.put("value", 15);
			weightMap300to500.put("name", "300KG-500KG成年牛");
			weightMap500.put("value", 45);
			weightMap500.put("name", "500KG以上育肥牛");
			list = new ArrayList<Map<String, Object>>();
			list.add(weightMap20to100);
			list.add(weightMap100to300);
			list.add(weightMap300to500);
			list.add(weightMap500);
			map.put("weight", list);
			break;
		case "8":
			map = new HashMap<String, Object>();

			allCattleMap.put("value", 100);// 牛总数
			allCattleMap.put("name", "牛总数量");
			map.put("all", allCattleMap);

			// 公母
			bullMap.put("value", 10);
			bullMap.put("name", "公牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(10, 100, 4)));
			cowMap.put("value", 90);
			cowMap.put("name", "母牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(10, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(bullMap);
			list.add(cowMap);
			map.put("gm", list);

			// 待产
			expectantCowQuantityMap.put("value", 35);
			expectantCowQuantityMap.put("name", "待产牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(35, 100, 4)));
			expectantCowNoQuantityMap.put("value", 55);
			expectantCowNoQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%").format(1-BigDecimalUtil.divDown(35, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(expectantCowQuantityMap);
			list.add(expectantCowNoQuantityMap);
			map.put("expectant", list);

			// 月龄
			ageMap0to6.put("value", 22);
			ageMap0to6.put("name", "0-6个月");
			ageMap6to12.put("value", 30);
			ageMap6to12.put("name", "6-12个月");
			ageMap12to24.put("value", 38);
			ageMap12to24.put("name", "12-24个月");
			ageMap24.put("value", 10);
			ageMap24.put("name", "24个月以上");
			list = new ArrayList<Map<String, Object>>();
			list.add(ageMap0to6);
			list.add(ageMap6to12);
			list.add(ageMap12to24);
			list.add(ageMap24);
			map.put("age", list);

			// 体重
			weightMap20to100.put("value", 10);
			weightMap20to100.put("name", "20KG-100KG牛崽子");
			weightMap100to300.put("value", 30);
			weightMap100to300.put("name", "100KG-300KG青年牛");
			weightMap300to500.put("value", 48);
			weightMap300to500.put("name", "300KG-500KG成年牛");
			weightMap500.put("value", 12);
			weightMap500.put("name", "500KG以上育肥牛");
			list = new ArrayList<Map<String, Object>>();
			list.add(weightMap20to100);
			list.add(weightMap100to300);
			list.add(weightMap300to500);
			list.add(weightMap500);
			map.put("weight", list);
			break;
		case "9":
			map = new HashMap<String, Object>();

			allCattleMap.put("value", 100);// 牛总数
			allCattleMap.put("name", "牛总数量");
			map.put("all", allCattleMap);

			// 公母
			bullMap.put("value", 28);
			bullMap.put("name", "公牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(28, 100, 4)));
			cowMap.put("value", 72);
			cowMap.put("name", "母牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(28, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(bullMap);
			list.add(cowMap);
			map.put("gm", list);

			// 待产
			expectantCowQuantityMap.put("value", 30);
			expectantCowQuantityMap.put("name", "待产牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(30, 100, 4)));
			expectantCowNoQuantityMap.put("value", 42);
			expectantCowNoQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%").format(1-BigDecimalUtil.divDown(30, 100, 4)));
			list = new ArrayList<Map<String, Object>>();
			list.add(expectantCowQuantityMap);
			list.add(expectantCowNoQuantityMap);
			map.put("expectant", list);

			// 月龄
			ageMap0to6.put("value", 22);
			ageMap0to6.put("name", "0-6个月");
			ageMap6to12.put("value", 38);
			ageMap6to12.put("name", "6-12个月");
			ageMap12to24.put("value", 30);
			ageMap12to24.put("name", "12-24个月");
			ageMap24.put("value", 10);
			ageMap24.put("name", "24个月以上");
			list = new ArrayList<Map<String, Object>>();
			list.add(ageMap0to6);
			list.add(ageMap6to12);
			list.add(ageMap12to24);
			list.add(ageMap24);
			map.put("age", list);

			// 体重
			weightMap20to100.put("value", 20);
			weightMap20to100.put("name", "20KG-100KG牛崽子");
			weightMap100to300.put("value", 20);
			weightMap100to300.put("name", "100KG-300KG青年牛");
			weightMap300to500.put("value", 48);
			weightMap300to500.put("name", "300KG-500KG成年牛");
			weightMap500.put("value", 12);
			weightMap500.put("name", "500KG以上育肥牛");
			list = new ArrayList<Map<String, Object>>();
			list.add(weightMap20to100);
			list.add(weightMap100to300);
			list.add(weightMap300to500);
			list.add(weightMap500);
			map.put("weight", list);
			break;
		default:
			break;
		}
		map.put("prairie", tmDictService.listTmDict("prairie"));
		return map;
	}
}
