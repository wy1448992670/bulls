package com.goochou.p2b.constant;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * 页面常用查询规则
 * </p>
 *
 * @author shuys
 * @since 2019年11月15日 09:38:00
 */
public enum SearchRuleEnum {
    
    EQ("=", "等于"),
    NOT_EQ("<>", "不等于"),
    LIKE("like", "全模糊"),
    GTE(">=", "大于等于"),
    LTE("<=", "小于等于"),
    GT(">", "大于"),
    LT("<", "小于"),
    IN("in", "在其中"),
    NOT_IN("not in", "不在其中"),
    OR("or", "或者"),
    ;
    
    private String symbol;
    
    private String descript;
    
    SearchRuleEnum (String symbol, String descript) {
        this.symbol = symbol;
        this.descript = descript;
    }
    
    public SearchRuleEnum getSearchRuleEnum(String enumName) {
        if (StringUtils.isNotBlank(enumName)) {
            SearchRuleEnum[] searchRuleEnums = SearchRuleEnum.values();
            for (SearchRuleEnum e : searchRuleEnums) {
                if (e.name().equalsIgnoreCase(enumName)) {
                    return e;
                }
            }
        }
        return null;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescript() {
        return descript;
    }
}
