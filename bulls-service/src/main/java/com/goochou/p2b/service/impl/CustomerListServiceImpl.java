package com.goochou.p2b.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.CustomerListMapper;
import com.goochou.p2b.model.CustomerList;
import com.goochou.p2b.model.CustomerListExample;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.CustomerListService;
import com.goochou.p2b.service.UserService;

/**
 * Created by irving on 2016/7/11.
 */
@Service
public class CustomerListServiceImpl implements CustomerListService {
	private static final Logger logger = Logger.getLogger(CustomerListServiceImpl.class);
    @Resource
    private CustomerListMapper customerListMapper;
    @Resource
    private UserService userService;

    @Override
    public void save(CustomerList customerList) {
        customerListMapper.insertSelective(customerList);
    }

    @Override
    public void delete(Integer id) {
        customerListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(List<String> list, UserAdmin admin) {
        for (String p : list) {
            User u = userService.getByPhone(p);
            logger.info("====================" + u);
            if (u == null) {
                continue;
            }
            CustomerList l = new CustomerList();
            l.setAdminId(admin.getId());
            l.setUserId(u.getId());
            l.setCreateTime(new Date());
            customerListMapper.insertSelective(l);
        }
    }

    @Override
    public List<Map<String, Object>> list(String keyword,Integer adminId, Integer start, Integer limit,Date startTime, Date endTime,Date investStartTime, Date investndTime,Integer type ,Integer  source,Integer investType,Date trueContactTime  ,Date trueContactTimeEnd){
        Map<String, Object> map = new HashMap<String, Object>();
        if("".equals(keyword)) {
            keyword = null;
        }
            map.put("trueContactTime",trueContactTime);
            map.put("trueContactTimeEnd",trueContactTimeEnd);
            map.put("keyword",keyword );
            map.put("start", start);
            map.put("limit", limit);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("investStartTime",investStartTime);
            map.put("investndTime",investndTime);
            map.put("type",type);
            map.put("source",source);
            map.put("investType",investType);
            map.put("adminId",adminId);
            return customerListMapper.list(map);
        }

        @Override
        public Integer listCount(String keyword,Integer adminId ,Date startTime, Date endTime,Date investStartTime, Date investndTime,Integer type ,Integer  source,Integer investType ,Date trueContactTime  ,Date trueContactTimeEnd) {
            Map<String, Object> map = new HashMap<String, Object>();
            if("".equals(keyword)){
                keyword = null;
            }
            map.put("trueContactTimeEnd",trueContactTimeEnd);
            map.put("trueContactTime",trueContactTime);
            map.put("keyword", StringUtils.trim(keyword));
            map.put("adminId",adminId);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("investStartTime",investStartTime);
            map.put("investndTime",investndTime);
            map.put("type",type);
            map.put("source",source);
            map.put("investType",investType);
            return customerListMapper.listCount(map);
        }

        @Override
        public Map<String, Object> statistics(Integer adminId, String date) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", date);
            map.put("adminId", adminId);
            return customerListMapper.statistics(map);
        }

        @Override
        public List<Map<String, Object>> statisticsList(String date, Integer id ) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", date);
            map.put("id", id);
            return customerListMapper.statisticsList(map);
        }
    @Override
    public List<Map<String, Object>> statisticsFirst(String date, Integer id ) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("id", id);
        return customerListMapper.statisticsFirst(map);
    }
    @Override
    public List<Map<String, Object>> statisticsList1(String date, Integer id ) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("id", id);
        return customerListMapper.statisticsList1(map);
    }
    @Override
    public List<Map<String, Object>> statisticsListFirst1(String date, Integer id ) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("id", id);
        return customerListMapper.statisticsListFirst1(map);
    }
        @Override
        public List<Map<String, Object>> statisticsList2(String date,Integer id) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", date);
            map.put("id", id);
            return customerListMapper.statisticsList2(map);
        }
    public void saveCus(Integer userId, Integer cusId){
        CustomerList cus = new CustomerList();
        cus.setCreateTime((new Date()));
        cus.setUserId(userId);
        cus.setAdminId(cusId);
        customerListMapper.insert(cus);
    }

    public Double sumAmount(String keyword, Integer adminId, Date startTime, Date endTime,Date investStartTime, Date investndTime,Integer type ,Integer  source,Integer investType ,Date trueContactTime  ,Date trueContactTimeEnd){
        Map<String, Object> map = new HashMap<String, Object>();
        if("".equals(keyword)){
            keyword = null;
        }
        map.put("trueContactTimeEnd",trueContactTimeEnd);
        map.put("trueContactTime",trueContactTime);
        map.put("keyword", StringUtils.trim(keyword));
        map.put("adminId",adminId);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("investStartTime",investStartTime);
        map.put("investndTime",investndTime);
        map.put("type",type);
        map.put("source",source);
        map.put("investType",investType);
        return  customerListMapper.sumAmount(map);
    }


    @Override
    public void saveBatch( List<Map<String, Object>> list){
        customerListMapper.insertBatch(list);
    }

    @Override
    public void saveBatch2( List<Map<String, Object>> list){
        customerListMapper.insertBatch2(list);
    }

    @Override
    public void deleteAll(){
        customerListMapper.deleteByExample(null);
    }

//    @Override
//    public List<Map<String, Object>> statisticsList(String date, Integer id ) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("date", date);
//        map.put("id", id);
//        return customerListMapper.statisticsList(map);
//    }

    @Override
    public List<Map<String, Object>> listDetail(String date, Integer id,Integer start,Integer limit ) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("id", id);
        map.put("start", start);
        map.put("limit", limit);
        return customerListMapper.listDetail(map);
    }
    @Override
    public Integer listDetailCount(String date, Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("id", id);
        return customerListMapper.listDetailCount(map);
    }
    @Override
    public String selectTrueNameByUserId(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return customerListMapper.selectTrueNameByUserId(map);
    }

    public List<Map<String, Object>> customerUser(){
        return customerListMapper.customerUser();
    }

    
    
    
    
	@Override
	public List<Map<String, Object>> statisticsListByDate(
			String date, Integer adminId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("adminId", adminId);
        return customerListMapper.statisticsListByDate(map);
	}

	@Override
	public List<Map<String, Object>> statisticsListByDate1(
			String date, Integer adminId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("adminId", adminId);
        return customerListMapper.statisticsListByDate1(map);
	}

	@Override
	public List<Map<String, Object>> statisticsFirstByDate(
			String date, Integer adminId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("adminId", adminId);
        return customerListMapper.statisticsFirstByDate(map);
	}

	@Override
	public List<Map<String, Object>> statisticsListFirstByDate1(
			String date, Integer adminId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("adminId", adminId);
        return customerListMapper.statisticsListFirstByDate1(map);
	}

	@Override
	public List<Map<String, Object>> queryAchievement(Map<String, Object> map) {
		return customerListMapper.queryAchievement(map);
	}

	@Override
	public int saveCustomerUser(int uid, String name,int adminId,String userPhone) {
		int flag=0;
		CustomerListExample example=new CustomerListExample();
		example.createCriteria().andUserIdEqualTo(uid);
		List<CustomerList> clist=customerListMapper.selectByExample(example);
		if(clist.size()>0){
			CustomerList cus=clist.get(0);
			cus.setTag(name);
			cus.setAdminId(adminId);
			cus.setUpdateTime(new Date());
			cus.setAdminName(name);
			cus.setMobile(userPhone);
			flag=customerListMapper.updateByPrimaryKeySelective(cus);
		}else{
			CustomerList record=new CustomerList();
			record.setUserId(uid);
			record.setTag(name);
			record.setAdminId(adminId);
			record.setAdminName(name);
			record.setCreateTime(new Date());
			record.setMobile(userPhone);
			flag=customerListMapper.insert(record);
		}
		return flag;
		
	}

	@Override
	public int queryMyCustomer(Integer userId, Integer adminId) {
		CustomerListExample example=new CustomerListExample();
		example.createCriteria().andUserIdEqualTo(userId).andAdminIdEqualTo(adminId);
		List<CustomerList> list = new ArrayList<>();
		list = customerListMapper.selectByExample(example);
		return list.size();
	}

	@Override
	public List<Map<String, Object>> statisticsYyyList(String date,
			Integer adminId) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("adminId", adminId);
        return customerListMapper.statisticsYyyList(map);
	}

	@Override
	public List<Map<String, Object>> statisticsYyyFirst(String date,
			Integer adminId) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("adminId", adminId);
        return customerListMapper.statisticsYyyFirst(map);
	}

	@Override
	public List<Map<String, Object>> statisticsNewOld(String date,
			Integer adminId) {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", date);
        map.put("adminId", adminId);
        return customerListMapper.statisticsNewOld(map);
	}
}
