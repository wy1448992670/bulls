package com.goochou.p2b.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.AssetsMapper;
import com.goochou.p2b.model.Assets;
import com.goochou.p2b.model.AssetsExample;
import com.goochou.p2b.service.AssetsService;
import com.goochou.p2b.service.JobService;
import com.goochou.p2b.utils.BigDecimalUtil;
import com.goochou.p2b.utils.DateUtil;

@Service
public class AssetsServiceImpl implements AssetsService {

    @Resource
    private AssetsMapper assetsMapper;
    @Resource
    private JobService jobService;
    
    @Override
    public AssetsMapper getMapper(){
        return assetsMapper;
    }

    @Override
    public Assets findByuserId(Integer userId) {
        return assetsMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<Map<String, Object>> investTop10() {
        return assetsMapper.investTop10();
    }

    @Override
    public List<Map<String, Object>> getRankList(Integer adminId,Integer departmentId,Integer type) {
        return assetsMapper.getRankList(adminId,departmentId,type);
    }

    @Override
    public int updateByPrimaryKeyAndVersionSelective(Assets assets) {
       return assetsMapper.updateByPrimaryKeyAndVersionSelective(assets);
    }

    @Override
    public void insert(Assets assets) {
        assetsMapper.insert(assets);
    }

    @Override
    public List<Map<Integer, String>> getHuoInvestPie() {
        return assetsMapper.getHuoInvestPie();
    }

    @Override
    public List<Assets> listHuo() throws Exception {
        AssetsExample example = new AssetsExample();
//        example.createCriteria().andHuoInvestmentAmountGreaterThan(0d);
        return assetsMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, Object>> getByYear() {
        return assetsMapper.getByYear();
    }

    @Override
    public List<Map<String, Object>> assetsInvestment() {
        return assetsMapper.assetsInvestment();
    }

    @Override
    public Map<String, Object> selectMyAssets(Integer userid) {
        return assetsMapper.selectMyAssets(userid);
    }

    @Override
    public double selectTotalAssetsRanking() {

        return assetsMapper.selectTotalAssetsRanking();
    }
	
	@Override
    public List<Assets> listHuoOfNormalUser() throws Exception {
        return assetsMapper.listHuoOfNormalUser();
    }

	@Override
	public Double selectMyYyyAssets(Integer userId) {
		return assetsMapper.selectMyYyyAssets(userId);
	}

	@Override
	public Double selectMyYyyInterest(Integer userId) {
		
		List<Map<String,Object>> list = assetsMapper.selectMyYyyInterest(userId);
		Integer type = 0;
		double amount = 0d;
		double annualized = 0d;
		double addAnnualized = 0d;
		int limitDays = 0;
		double sum = 0;
		Date date = null;
		for (Map<String, Object> map : list) {
			type = Integer.parseInt(map.get("type").toString());
			amount = Double.parseDouble(map.get("amount").toString());
			annualized = Double.parseDouble(map.get("annualized").toString());
			addAnnualized = Double.parseDouble(map.get("addAnnualized").toString());
			limitDays = Integer.parseInt(map.get("limitDays").toString());
			date = (Date) map.get("time");
			
			int day = DateUtil.dateToDateDay(date, new Date());
			
			if (type == 1) {//授权服务期
				//计算持有天数，如果小于limitDays，则取limitDays
				limitDays = day >= limitDays ? day : limitDays;
			}
			
			sum = BigDecimalUtil.add(sum,
					BigDecimalUtil.fixed2(BigDecimalUtil.divDown(
							BigDecimalUtil.multi(
								BigDecimalUtil.multi(amount, (annualized + addAnnualized)),
								limitDays),
								365,2)));
		}
		return sum;
	}
	
	@Override
	public Map<String,Double> getAssetsTradeSum(Date beginDate,Date endDate){
		if(beginDate==null) {
			beginDate=jobService.getFIRST_RECORD_DATE();
		}else {
			beginDate=DateUtil.getDayStartDate(beginDate);
		}
		String beginTableName=jobService.getExistAssetsSnapshotTableName(beginDate);
		String endTableName="";
		if(endDate==null) {
			//非快照
			endTableName=jobService.getASSETS_NOW_TABLE();
		}else {
			//0点
			endDate=DateUtil.getDayStartDate(endDate);
			endTableName=jobService.getExistAssetsSnapshotTableName(endDate);
		}
		
		Assets beginAssetsSum=assetsMapper.sumAssetsSnapshoot(beginTableName);
		Assets endAssetsSum=assetsMapper.sumAssetsSnapshoot(endTableName);
		Map<String,Double> sumTradeRecord=assetsMapper.sumTradeRecord(beginDate,endDate);
		sumTradeRecord.put("beginBalanceAmount", beginAssetsSum.getBalanceAmount());
		sumTradeRecord.put("beginFrozenAmount", beginAssetsSum.getFrozenAmount());
		sumTradeRecord.put("beginCreditAmount", beginAssetsSum.getCreditAmount());
		sumTradeRecord.put("beginFreozenCreditAmount", beginAssetsSum.getFreozenCreditAmount());
		
		sumTradeRecord.put("endBalanceAmount", endAssetsSum.getBalanceAmount());
		sumTradeRecord.put("endFrozenAmount", endAssetsSum.getFrozenAmount());
		sumTradeRecord.put("endCreditAmount", endAssetsSum.getCreditAmount());
		sumTradeRecord.put("endFreozenCreditAmount", endAssetsSum.getFreozenCreditAmount());
		return sumTradeRecord;
	}
	
	@Override
	public Assets sumAssetsSnapshoot(String tableName){
		return assetsMapper.sumAssetsSnapshoot(tableName);
	}
	
	@Override
	public Map<String,Double> sumTradeRecord(Date beginDate,Date endDate){
		return assetsMapper.sumTradeRecord(beginDate,endDate);
	}
	
	public static void main(String[] args) {
		System.out.println(BigDecimalUtil.divDown(0.123456456, 1, 2));
	}
}
