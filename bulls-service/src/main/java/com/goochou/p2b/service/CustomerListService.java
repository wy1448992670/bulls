package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.CustomerList;
import com.goochou.p2b.model.UserAdmin;

/**
 * Created by irving on 2016/7/11.
 */
public interface CustomerListService {

    public void save(CustomerList customerList);

    public void delete(Integer id);

    public void save(List<String> list, UserAdmin admin);

    public List<Map<String, Object>> list(String keyword, Integer adminId, Integer start, Integer limit, Date startTime, Date endTime, Date investStartTime, Date investndTime, Integer type , Integer  source, Integer investType,Date trueContactTime,Date trueContactTimeEnd) ;

    public Integer listCount(String keyword, Integer adminId, Date startTime, Date endTime,Date investStartTime, Date investndTime,Integer type ,Integer  source,Integer investType ,Date trueContactTime ,Date trueContactTimeEnd) ;

    public Map<String, Object> statistics(Integer adminId, String date);
    public List<Map<String, Object>> statisticsFirst(String date, Integer id );

    public List<Map<String, Object>> statisticsList(String date, Integer id);
    public List<Map<String, Object>> statisticsListFirst1(String date, Integer id );
    public List<Map<String, Object>> statisticsList2(String date, Integer id);

    public void saveCus(Integer userId, Integer cusId);

    public Double sumAmount(String keyword, Integer adminId, Date startTime, Date endTime,Date investStartTime, Date investndTime,Integer type ,Integer  source,Integer investType,Date trueContactTime ,Date trueContactTimeEnd) ;

    public void saveBatch( List<Map<String, Object>> list);

    public void deleteAll();

    public void saveBatch2( List<Map<String, Object>> list);
    public List<Map<String, Object>> statisticsList1(String date, Integer id );
    public List<Map<String, Object>> listDetail(String date, Integer id,Integer start,Integer limit ) ;
    public Integer listDetailCount(String date, Integer id);

    public String selectTrueNameByUserId(Integer userId);

    List<Map<String, Object>> customerUser();
    
    
    public List<Map<String, Object>> statisticsListByDate(String date, Integer adminId);
    public List<Map<String, Object>> statisticsListByDate1(String date, Integer adminId );
    public List<Map<String, Object>> statisticsFirstByDate(String date, Integer adminId );
    public List<Map<String, Object>> statisticsListFirstByDate1(String date, Integer adminId );

	public List<Map<String, Object>> queryAchievement(Map<String, Object> map);

	public int saveCustomerUser(int uid,String name,int adminId, String userPhone);

	public int queryMyCustomer(Integer userId, Integer adminId);

	public List<Map<String, Object>> statisticsYyyList(String date, Integer adminId);

	public List<Map<String, Object>> statisticsYyyFirst(String date, Integer adminId);

	public List<Map<String, Object>> statisticsNewOld(String date, Integer adminId);
}
