package com.goochou.p2b.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.ActivityExp;
import com.goochou.p2b.model.ActivityExpRecord;
import com.goochou.p2b.model.User;
import com.goochou.p2b.model.UserSignConfig;

/**
 * 签到
 * @author xueqi
 *
 */
public interface UserSignConfigService {

    List<UserSignConfig> queryUserSignConfigByDate(Date begin, Date end, Boolean isInvested);
    
    Map<String, Object> doSign(User user, UserSignConfig config, boolean isInvested);

}
