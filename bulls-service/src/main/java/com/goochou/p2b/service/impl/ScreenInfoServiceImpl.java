package com.goochou.p2b.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goochou.p2b.constant.Constants;
import com.goochou.p2b.constant.project.ProjectWeightEnum;
import com.goochou.p2b.dao.ProjectMapper;
import com.goochou.p2b.model.vo.ScreenInfo;
import com.goochou.p2b.service.ScreenInfoDemoService;
import com.goochou.p2b.service.ScreenInfoService;
import com.goochou.p2b.service.TmDictService;
import com.goochou.p2b.utils.BigDecimalUtil;

@Service
public class ScreenInfoServiceImpl implements ScreenInfoService {
	@Autowired
	ProjectMapper projectMapper;
	@Autowired
	TmDictService tmDictService;

	@Autowired
	ScreenInfoDemoService screenInfoDemoService;

	@Override
	public Map<String, Object> getScreenInfo(String prairieValue) {

		if (!"1".equals(prairieValue)) {
			return screenInfoDemoService.demoPieData(prairieValue);//假数据
		} else {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			int allCattle = projectMapper.countAllCattle(prairieValue);
			Map<String, Object> allCattleMap = new HashMap<String, Object>();
			allCattleMap.put("value", allCattle);
			allCattleMap.put("name", "牛总数量");

			int bull = projectMapper.countBull(prairieValue);
			int cow = projectMapper.countCow(prairieValue);

			Map<String, Object> calfMap = new HashMap<String, Object>();
			calfMap.put("value", projectMapper.countCalf(prairieValue));
			calfMap.put("name", "牛犊");

			list = new ArrayList<Map<String, Object>>();

			if (allCattle != 0) {

				// 公母牛比例
				Map<String, Object> bullMap = new HashMap<String, Object>();
				bullMap.put("value", bull);
				bullMap.put("name", "公牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(bull, allCattle, 4)));

				Map<String, Object> cowMap = new HashMap<String, Object>();
				cowMap.put("value", cow);
				cowMap.put("name", "母牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(bull, allCattle, 4)));
				list = new ArrayList<Map<String, Object>>();
				list.add(bullMap);
				list.add(cowMap);
				map.put("gm", list);

				if ("1".equals(prairieValue)) {
					Map<String, Object> oneMap = new HashMap<String, Object>();
					list = new ArrayList<Map<String, Object>>();
					oneMap.put("value", ProjectWeightEnum.FARM1.getCount20to100());
					oneMap.put("name", ProjectWeightEnum.FARM1.getDesc20to100());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM1.getCount100to300());
					oneMap.put("name", ProjectWeightEnum.FARM1.getDesc100to300());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM1.getCount300to500());
					oneMap.put("name", ProjectWeightEnum.FARM1.getDesc300to500());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM1.getCount500());
					oneMap.put("name", ProjectWeightEnum.FARM1.getDesc500());
					list.add(oneMap);
					map.put("weight", list);

					// 待产牛比例
					list = new ArrayList<Map<String, Object>>();
					Map<String, Object> expectantCowQuantityMap = new HashMap<String, Object>();
					expectantCowQuantityMap.put("value", Constants.EXPECTANTCOW_QUANTITY);
					expectantCowQuantityMap.put("name",
							"待产牛 " + new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(Constants.EXPECTANTCOW_QUANTITY, allCattle, 4)));

					list.add(expectantCowQuantityMap);
					expectantCowQuantityMap = new HashMap<String, Object>();
					expectantCowQuantityMap.put("value", allCattle - Constants.EXPECTANTCOW_QUANTITY);
					expectantCowQuantityMap.put("name",
							"非待产牛 " + new DecimalFormat("#.##%").format(1 - BigDecimalUtil.divDown(Constants.EXPECTANTCOW_QUANTITY, allCattle, 4)));

					list.add(expectantCowQuantityMap);
					map.put("expectant", list);

				}
				if ("2".equals(prairieValue)) {
					Map<String, Object> oneMap = new HashMap<String, Object>();
					list = new ArrayList<Map<String, Object>>();
					oneMap.put("value", ProjectWeightEnum.FARM2.getCount20to100());
					oneMap.put("name", ProjectWeightEnum.FARM2.getDesc20to100());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM2.getCount100to300());
					oneMap.put("name", ProjectWeightEnum.FARM2.getDesc100to300());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM2.getCount300to500());
					oneMap.put("name", ProjectWeightEnum.FARM2.getDesc300to500());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM2.getCount500());
					oneMap.put("name", ProjectWeightEnum.FARM2.getDesc500());
					list.add(oneMap);
					map.put("weight", list);

					// 待产牛比例
					list = new ArrayList<Map<String, Object>>();
					Map<String, Object> expectantCowQuantityMap = new HashMap<String, Object>();
					expectantCowQuantityMap.put("value", Constants.EXPECTANTCOW_QUANTITY2);
					expectantCowQuantityMap.put("name", "待产牛 "
							+ new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(Constants.EXPECTANTCOW_QUANTITY2, Constants.FARM2COUNT, 4)));

					list.add(expectantCowQuantityMap);
					expectantCowQuantityMap = new HashMap<String, Object>();
					expectantCowQuantityMap.put("value", Constants.FARM2COUNT - Constants.EXPECTANTCOW_QUANTITY2);
					expectantCowQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%")
							.format(1 - BigDecimalUtil.divDown(Constants.EXPECTANTCOW_QUANTITY2, Constants.FARM2COUNT, 4)));

					list.add(expectantCowQuantityMap);
					map.put("expectant", list);
				}
				if ("3".equals(prairieValue)) {
					Map<String, Object> oneMap = new HashMap<String, Object>();
					list = new ArrayList<Map<String, Object>>();
					oneMap.put("value", ProjectWeightEnum.FARM3.getCount20to100());
					oneMap.put("name", ProjectWeightEnum.FARM3.getDesc20to100());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM3.getCount100to300());
					oneMap.put("name", ProjectWeightEnum.FARM3.getDesc100to300());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM3.getCount300to500());
					oneMap.put("name", ProjectWeightEnum.FARM3.getDesc300to500());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM3.getCount500());
					oneMap.put("name", ProjectWeightEnum.FARM3.getDesc500());
					list.add(oneMap);
					map.put("weight", list);
					// 待产牛比例
					list = new ArrayList<Map<String, Object>>();
					Map<String, Object> expectantCowQuantityMap = new HashMap<String, Object>();
					expectantCowQuantityMap.put("value", Constants.EXPECTANTCOW_QUANTITY3);
					expectantCowQuantityMap.put("name", "待产牛 "
							+ new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(Constants.EXPECTANTCOW_QUANTITY3, Constants.FARM3COUNT, 4)));

					list.add(expectantCowQuantityMap);
					expectantCowQuantityMap = new HashMap<String, Object>();
					expectantCowQuantityMap.put("value", Constants.FARM3COUNT - Constants.EXPECTANTCOW_QUANTITY3);
					expectantCowQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%")
							.format(1 - BigDecimalUtil.divDown(Constants.EXPECTANTCOW_QUANTITY3, Constants.FARM3COUNT, 4)));

					list.add(expectantCowQuantityMap);
					map.put("expectant", list);

				}
				if ("4".equals(prairieValue)) {
					Map<String, Object> oneMap = new HashMap<String, Object>();
					list = new ArrayList<Map<String, Object>>();
					oneMap.put("value", ProjectWeightEnum.FARM4.getCount20to100());
					oneMap.put("name", ProjectWeightEnum.FARM4.getDesc20to100());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM4.getCount100to300());
					oneMap.put("name", ProjectWeightEnum.FARM4.getDesc100to300());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM4.getCount300to500());
					oneMap.put("name", ProjectWeightEnum.FARM4.getDesc300to500());
					list.add(oneMap);
					oneMap = new HashMap<String, Object>();
					oneMap.put("value", ProjectWeightEnum.FARM4.getCount500());
					oneMap.put("name", ProjectWeightEnum.FARM4.getDesc500());
					list.add(oneMap);
					map.put("weight", list);
					// 待产牛比例
					list = new ArrayList<Map<String, Object>>();
					Map<String, Object> expectantCowQuantityMap = new HashMap<String, Object>();
					expectantCowQuantityMap.put("value", Constants.EXPECTANTCOW_QUANTITY4);
					expectantCowQuantityMap.put("name", "待产牛 "
							+ new DecimalFormat("#.##%").format(BigDecimalUtil.divDown(Constants.EXPECTANTCOW_QUANTITY4, Constants.FARM4COUNT, 4)));

					list.add(expectantCowQuantityMap);
					expectantCowQuantityMap = new HashMap<String, Object>();
					expectantCowQuantityMap.put("value", Constants.FARM4COUNT - Constants.EXPECTANTCOW_QUANTITY4);
					expectantCowQuantityMap.put("name", "非待产牛 " + new DecimalFormat("#.##%")
							.format(1 - BigDecimalUtil.divDown(Constants.EXPECTANTCOW_QUANTITY4, Constants.FARM4COUNT, 4)));

					list.add(expectantCowQuantityMap);
					map.put("expectant", list);
				}
			} else {// 如果牧场没有牛

				// 待产牛比例
				list = new ArrayList<Map<String, Object>>();
				Map<String, Object> expectantCowQuantityMap = new HashMap<String, Object>();
				expectantCowQuantityMap.put("value", 0);
				expectantCowQuantityMap.put("name", "待产牛 " + "50%");

				list.add(expectantCowQuantityMap);
				expectantCowQuantityMap = new HashMap<String, Object>();
				expectantCowQuantityMap.put("value", 0);
				expectantCowQuantityMap.put("name", "非待产牛 " + "50%");

				list.add(expectantCowQuantityMap);
				map.put("expectant", list);

				// 公母牛比例
				Map<String, Object> bullMap = new HashMap<String, Object>();
				bullMap.put("value", bull);
				bullMap.put("name", "公牛 " + "50%");

				Map<String, Object> cowMap = new HashMap<String, Object>();
				cowMap.put("value", cow);
				cowMap.put("name", "母牛 " + "50%");
				list = new ArrayList<Map<String, Object>>();
				list.add(bullMap);
				list.add(cowMap);
				map.put("gm", list);

				list = new ArrayList<Map<String, Object>>();
				Map<String, Object> oneMap = new HashMap<String, Object>();
				oneMap.put("value", 0);
				oneMap.put("name", ProjectWeightEnum.FARM4.getDesc20to100());
				list.add(oneMap);
				oneMap = new HashMap<String, Object>();
				oneMap.put("value", 0);
				oneMap.put("name", ProjectWeightEnum.FARM4.getDesc100to300());
				list.add(oneMap);
				oneMap = new HashMap<String, Object>();
				oneMap.put("value", 0);
				oneMap.put("name", ProjectWeightEnum.FARM4.getDesc300to500());
				list.add(oneMap);
				oneMap = new HashMap<String, Object>();
				oneMap.put("value", 0);
				oneMap.put("name", ProjectWeightEnum.FARM4.getDesc500());
				list.add(oneMap);
				map.put("weight", list);
			}

			map.put("all", allCattleMap);

//		list = new ArrayList<Map<String, Object>>();
//		list.add(calfMap);
//		map.put("calf", list);

			// 封装月龄范围
			list = new ArrayList<Map<String, Object>>();
			Map<String, Object> ageMap = new HashMap<String, Object>();
			List<ScreenInfo> infos = projectMapper.countAge(prairieValue);
			for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				ScreenInfo screenInfo = (ScreenInfo) iterator.next();
				ageMap = new HashMap<String, Object>();
				ageMap.put("value", screenInfo.getAgeQuantity());
				ageMap.put("name", screenInfo.getAgeDesc());
				// 月龄
				list.add(ageMap);
			}
			map.put("age", list);

			map.put("prairie", tmDictService.listTmDict("prairie"));

			return map;
		}
	}
}
