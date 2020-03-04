package com.goochou.p2b.model.vo;

import java.io.Serializable;

/**
 * @author shuys
 * @Description 不同期限中牛只数量统计
 * @since 2019年08月19日 10:30:00
 */
public class InvestementCountGroupLimitDay implements Serializable {

    private static final long serialVersionUID = 4993488150399949706L;

    private String title;

    private String limitDaysType;

    private Integer count;

    public InvestementCountGroupLimitDay(){}

    public InvestementCountGroupLimitDay(String limitDaysType){
        this.limitDaysType = limitDaysType;
        this.count = 0;
    }

    public String getLimitDaysType() {
        return limitDaysType;
    }

    public void setLimitDaysType(String limitDaysType) {
        this.limitDaysType = limitDaysType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTitle() {
        if (title == null && limitDaysType != null) {
            String title = "";
            if ("noob".equals(limitDaysType)) {
                title = "新手标";
            } else {
                title = limitDaysType + "天";
            }
            return title;
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
