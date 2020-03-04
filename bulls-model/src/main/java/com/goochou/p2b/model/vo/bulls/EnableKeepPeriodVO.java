package com.goochou.p2b.model.vo.bulls;


import java.util.List;

/**
 * @author: huangsj
 * @Date: 2019/10/31 10:35
 * @Description:
 */
public class EnableKeepPeriodVO {

    List<KeepPeriodVO> keepPeriodVOList;

    public List<KeepPeriodVO> getKeepPeriodVOList() {
        return keepPeriodVOList;
    }

    public void setKeepPeriodVOList(List<KeepPeriodVO> keepPeriodVOList) {
        this.keepPeriodVOList = keepPeriodVOList;
    }

    @Override
    public String toString() {
        return "EnableKeepPeriodVO{" +
                "keepPeriodVOList=" + keepPeriodVOList +
                '}';
    }
}
