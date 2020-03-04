package com.goochou.p2b.service;

import java.util.List;
import java.util.Map;

import com.goochou.p2b.model.WithdrawBlack;

public interface WithdrawBlackService {
    public List<Map<String, Object>> list(String keywords, Integer start, Integer limit);

    public void add(WithdrawBlack wb);

    public Integer get(Integer userId);
}
