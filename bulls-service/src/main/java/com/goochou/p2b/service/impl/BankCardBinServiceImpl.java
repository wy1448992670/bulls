package com.goochou.p2b.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.BankCardBinMapper;
import com.goochou.p2b.model.BankCardBin;
import com.goochou.p2b.model.BankCardBinExample;
import com.goochou.p2b.service.BankCardBinService;

@Service
public class BankCardBinServiceImpl implements BankCardBinService {
    private final static Logger logger = Logger.getLogger(BankCardBinServiceImpl.class);

    @Resource
    private BankCardBinMapper bankCardBinMapper;
    
    @Override
    public BankCardBin findBankCardBin(String cardNo) throws Exception {
        BankCardBin cardBin = null;
        for (int i = 2; i < 11; i++) {
            String prefix = cardNo.substring(0, i);
            BankCardBinExample example = new BankCardBinExample();
            example.createCriteria().andStateEqualTo(1).andCardTypeEqualTo("借记卡").andCardBinEqualTo(prefix).andBankIdIsNotNull();
            List<BankCardBin> cards = bankCardBinMapper.selectByExample(example);
            if (cards.size()>0) {
                return cards.get(0);
            }
        }
        return cardBin;
    }
}