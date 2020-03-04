package com.goochou.p2b.model.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goochou.p2b.model.HongbaoTemplate;

/**
 * HongbaoTemplateModel
 *
 * @author 刘源
 * @date 2016/7/6
 */
public class HongbaoTemplateModel {


    private Integer templateId;
    private double amount;
    private Integer limitAmount;
    private Integer minInvestDay;
    private Integer effectiveDay;
    private String descript;

    public HongbaoTemplateModel() {
    }

    public HongbaoTemplateModel(Integer templateId, double amount, Integer limitAmount, Integer minInvestDay, Integer effectiveDay, String descript) {
        this.templateId = templateId;
        this.amount = amount;
        this.limitAmount = limitAmount;
        this.minInvestDay = minInvestDay;
        this.effectiveDay = effectiveDay;
        this.descript = descript;
    }

    public HongbaoTemplate getHongbaoTemplate() {
        HongbaoTemplate record = new HongbaoTemplate();
        record.setId(this.getTemplateId());
        record.setAmount(this.getAmount());
        record.setLimitAmount(this.getLimitAmount());
        record.setLimitDay(this.getMinInvestDay());
        record.setEffectiveDays(this.getEffectiveDay());
        record.setName(this.getDescript());
//        record.setMonthType(this.getMonthType());
//        JSONObject object = JSON.parseObject(this.getMonthType());
//        StringBuilder sb = new StringBuilder();
//        if ((Boolean) object.get("check1")) {
//            sb.append("30天/");
//        }
//        if ((Boolean) object.get("check2")) {
//            sb.append("90天/");
//        }
//        if ((Boolean) object.get("check3")) {
//            sb.append("180天/");
//        }
//        if ((Boolean) object.get("check4")) {
//            sb.append("270天/");
//        }
//        if ((Boolean) object.get("check5")) {
//            sb.append("365天/");
//        }
//        if (sb.toString() != null) {
//            String result = sb.toString();
//            record.setMonthType(result.substring(0, result.lastIndexOf("/")));
//        }
        return record;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(Integer limitAmount) {
        this.limitAmount = limitAmount;
    }

    public Integer getMinInvestDay() {
        return minInvestDay;
    }

    public void setMinInvestDay(Integer minInvestDay) {
        this.minInvestDay = minInvestDay;
    }

    public Integer getEffectiveDay() {
        return effectiveDay;
    }

    public void setEffectiveDay(Integer effectiveDay) {
        this.effectiveDay = effectiveDay;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
    
}
