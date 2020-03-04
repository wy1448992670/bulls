package com.goochou.p2b.service;

import com.goochou.p2b.dao.VipDividendMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年10月08日 18:02:00
 */
@Service
public class VipDividendServiceImpl implements VipDividendService {

    private final static Logger logger = Logger.getLogger(VipDividendServiceImpl.class);

    @Resource
    private VipDividendMapper vipDividendMapper;

    @Override
    public VipDividendMapper getVipDividendMapper() {
        return this.vipDividendMapper;
    }

}
