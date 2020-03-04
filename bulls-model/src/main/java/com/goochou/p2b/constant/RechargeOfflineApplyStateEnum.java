package com.goochou.p2b.constant;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年11月22日 14:16:00
 */
public enum RechargeOfflineApplyStateEnum {
    APPLING(0, "申请中"),
    PASS(1, "通过"),
    NO_PASS(-1, "不通过"),
    ;

    private int code;
    
    private String descript;
    
    RechargeOfflineApplyStateEnum (int code, String descript) {
        this.code = code;
        this.descript = descript;
    }
    
    public static RechargeOfflineApplyStateEnum getByCode(int code) {
        for (RechargeOfflineApplyStateEnum e : RechargeOfflineApplyStateEnum.values()) {
            if (code == e.getCode()) {
                return e;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDescript() {
        return descript;
    }
}
