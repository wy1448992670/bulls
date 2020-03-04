package com.goochou.p2b.model.vo;

import com.goochou.p2b.utils.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by irving on 2017/4/20.
 */
public class InvestmentSearchCondition {
    private String keyword = null;
    private Date startTime;
    private Date endTime;
    private Integer type;
    private Integer seq;
    private Integer start = 0;
    private Integer limit = 20;
    private Integer investType;
    private Integer source;
    private String codes = null;
    private Integer adminId;

    public InvestmentSearchCondition(String keyword, Date startTime, Date endTime, Integer type, Integer seq, Integer start, Integer limit, Integer investType, Integer source, String codes, Integer adminId) {
        if (StringUtils.isBlank(keyword)) {
            this.keyword = null;
        } else {
            this.keyword = keyword;
        }
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.seq = seq;
        this.start = start;
        this.limit = limit;
        this.investType = investType;
        this.source = source;
        if (StringUtils.isBlank(codes)) {
            this.codes = null;
        } else {
            this.codes = codes;
        }
        this.adminId = adminId;
    }

    public InvestmentSearchCondition() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            this.keyword = null;
        } else {
            this.keyword = keyword;
        }
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(endTime);
        c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
        this.endTime = c1.getTime();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getInvestType() {
        return investType;
    }

    public void setInvestType(Integer investType) {
        this.investType = investType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        if (StringUtils.isBlank(codes)) {
            this.codes = null;
        } else {
            this.codes = codes;
        }
    }

    public Integer adminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}
