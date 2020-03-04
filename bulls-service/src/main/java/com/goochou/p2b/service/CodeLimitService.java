package com.goochou.p2b.service;

import com.goochou.p2b.model.CodeLimit;

public interface CodeLimitService {

    public Integer listCountByPhone(String phone);

    public void save(CodeLimit limit);

}
