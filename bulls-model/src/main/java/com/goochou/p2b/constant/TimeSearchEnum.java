package com.goochou.p2b.constant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 交易记录日期查询筛选 枚举
 * @author shuys
 * @since 2019/6/17 17:05
 */
public enum TimeSearchEnum {

    ALLS(-1, "全部"),
    THIS_WEEK(10, "本周"),
    THIS_MONTH(20, "本月"),
    THREE_MONTHS(203, "三个月"),
    SIX_MONTHS(206, "六个月"),
    ;

    private Integer code;

    private String description;

    TimeSearchEnum(Integer code,String description){
        this.code=code;
        this.description=description;
    }

    public static TimeSearchEnum getValueByCode(Integer code){
        for (TimeSearchEnum enums : values()) {
            if (enums.getCode().equals(code)) {
                return enums;
            }
        }
        return null;
    }

    /**
     * 转化成json
     * @return
     */
    public static String toJson(){
        JSONArray jsonArray = new JSONArray();
        for (TimeSearchEnum e : TimeSearchEnum.values()) {
            JSONObject object = new JSONObject();
            object.put("code", e.getCode());
            object.put("description", e.getDescription());
            jsonArray.add(object);
        }
        return jsonArray.toString();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
