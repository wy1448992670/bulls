package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;


/**
 * 签到
 * @author xueqi
 *
 */
public interface UserSignAwardService {

    Double queryUserSignAwardAmount(Integer userId);
    
    List<Map<String, Object>> queryUserSignAwardList(Integer userId, Integer limit, Integer offset);
    
    Integer queryUserSignAwardCount(Integer userId);

}
