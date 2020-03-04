package com.goochou.p2b.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.CodeLimitMapper;
import com.goochou.p2b.model.CodeLimit;
import com.goochou.p2b.service.CodeLimitService;

@Service
public class CodeLimitServiceImpl implements CodeLimitService {

    @Resource
    private CodeLimitMapper codeLimitMapper;

    @Override
    public Integer listCountByPhone(String phone) {
        return codeLimitMapper.listCountByPhone(phone);
    }

    @Override
    public void save(CodeLimit limit) {
        codeLimitMapper.insert(limit);
    }

}
