package com.goochou.p2b.model.vo.bulls;

/**
 * @author: huangsj
 * @Date: 2019/10/31 10:51
 * @Description:
 */
public class KeepPeriodVO {
    private int days;
    private String daysName;

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getDaysName() {
        return daysName;
    }

    public void setDaysName(String daysName) {
        this.daysName = daysName;
    }

    @Override
    public String toString() {
        return "KeepPeriodVO{" +
                "days=" + days +
                ", daysName='" + daysName + '\'' +
                '}';
    }
}
