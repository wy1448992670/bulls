package com.goochou.p2b.model.vo.bulls;

import com.goochou.p2b.model.MallActivity;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年12月12日 17:19:00
 */
public class MallActivityVO extends MallActivity {
    
    private String typeMsg;
    
    private String enableMsg;
    
    private String userName;
    
    private Integer count;

    private Integer weekCount;
    
    public String getTypeMsg() {
        if (this.getType() == 1) {
            return "秒杀";
        }
        return typeMsg;
    }

    public String getEnableMsg() {
        if (this.getEnable() == 1) {
            return "可用";
        }
        return "不可用";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(Integer weekCount) {
        this.weekCount = weekCount;
    }
}
