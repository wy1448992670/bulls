package com.goochou.p2b.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.goochou.p2b.dao.BankMapper;
import com.goochou.p2b.model.Bank;
import com.goochou.p2b.model.BankExample;
import com.goochou.p2b.model.BankExample.Criteria;
import com.goochou.p2b.service.BankService;
import com.goochou.p2b.service.UploadService;
import com.goochou.p2b.utils.BigDecimalUtil;

@Service
public class BankServiceImpl implements BankService {

    @Resource
    private BankMapper bankMapper;
    @Resource
    private UploadService uploadService;

    @Override
    public List<Bank> list() {
        BankExample example = new BankExample();
        return bankMapper.selectByExample(example);
    }

    @Override
    public Bank getByCode(String code, Integer type) {
        BankExample example = new BankExample();
        Criteria c = example.createCriteria();
        if (type.equals(4)) {
            //c.andFuiouCodeEqualTo(code);
            c.andFuiouCodeLike(code + "%");
        }else if(type.equals(3)) {
            c.andCodeEqualTo(code);
        }
        List<Bank> list = bankMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Bank getByName(String name) {
        BankExample example = new BankExample();
        Criteria c = example.createCriteria();
        c.andNameEqualTo(name);
        List<Bank> list = bankMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Bank getBankByBankCardId(Integer cardId) {
        return bankMapper.getBankByBankCardId(cardId);
    }

    @Override
    public Bank get(Integer id) {
        return bankMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Bank> getBankList(String keyword) {
        return bankMapper.getBankList(keyword);
    }

    @Override
    public List<Bank> query(String keyword, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("start", start);
        map.put("limit", limit);
        return bankMapper.query(map);
    }

    @Override
    public Integer queryCount(String keyword) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        return bankMapper.queryCount(map);
    }

    @Override
    public Bank queryById(Integer id) {
        return bankMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Bank bank) {
        if (bank.getId() == null) {
            bankMapper.insertSelective(bank);
        } else {
            bankMapper.updateByPrimaryKeySelective(bank);
        }
    }

    @Override
    public List<Map<String, Object>> queryAvailableBankList(Integer id) {
        List<Map<String, Object>> list = bankMapper.queryAvailableBankList(id);
        DecimalFormat df = new DecimalFormat("######0.00");
        DecimalFormat dfPC = new DecimalFormat("#######");
        for (Map<String, Object> map : list) {

            Object firstMax = map.get("firstMax");
            Object dailyMax = map.get("dailyMax");
            Object singleMax = map.get("singleMax");
            Object singleMin = map.get("singleMin");
            Object monthMaxAmount = map.get("monthMaxAmount");

            map.put("firstMax", "首笔" + df.format(firstMax));
            map.put("dailyMax", "单日" + df.format(dailyMax));
            map.put("singleMax", "单笔" + df.format(singleMax));
            map.put("singleMin", "单笔" + df.format(singleMin));
            map.put("monthMaxAmount", "单月" + df.format(monthMaxAmount));

            map.put("firstMaxPC", Long.parseLong(dfPC.format(firstMax)) / 10000 + "万");
            map.put("dailyMaxPC", Long.parseLong(dfPC.format(dailyMax)) / 10000 + "万");
            map.put("singleMaxPC", Long.parseLong(dfPC.format(singleMax)) / 10000 + "万");
            map.put("singleMinPC", Long.parseLong(dfPC.format(singleMin)) / 10000 + "万");
            map.put("monthMaxAmountPC", Long.parseLong(dfPC.format(monthMaxAmount)) / 10000 + "万");
        }
        return list;
    }

    @Override
    public List<Bank> getBankListByExample(BankExample example) {
        List<Bank> list = bankMapper.selectByExample(example);
        for (Bank bank : list) {
            //Double firstMax = bank.getFirstBindMaxAmount();
            Double bindSingleMaxAmount = bank.getBindSingleMaxAmount();
            Double bindDailyMaxAmount = bank.getBindDailyMaxAmount();
            Double monthMaxAmount = bank.getMonthMaxAmount();

            String bindSingleMaxAmountStr;
            String bindDailyMaxAmountStr;
            String monthMaxAmountStr;
            
            if(bindSingleMaxAmount < 10000){
            	bindSingleMaxAmountStr = (int)(double)bindSingleMaxAmount + "";
            }else {
            	bindSingleMaxAmountStr = (int)BigDecimalUtil.div(bindSingleMaxAmount, 10000, 0) + "万";
            }
            if(bindDailyMaxAmount < 10000){
            	bindDailyMaxAmountStr = (int)(double)bindDailyMaxAmount + "";
            }else {
            	bindDailyMaxAmountStr = (int)BigDecimalUtil.div(bindDailyMaxAmount, 10000, 0) + "万";
            }
            if (monthMaxAmount < 10000) {
                monthMaxAmountStr = (int) (double) monthMaxAmount + "";
            } else {
                monthMaxAmountStr = (int) BigDecimalUtil.div(monthMaxAmount, 10000, 0) + "万";
            }
            bank.setNote("限额: " + bindSingleMaxAmountStr + "/笔，" + bindDailyMaxAmountStr + "/日，" + monthMaxAmountStr + "/月");
        }
        return list;
    }

}
