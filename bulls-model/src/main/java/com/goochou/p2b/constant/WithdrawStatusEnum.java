package com.goochou.p2b.constant;

/**
 * 提现状态  0审核中1提现成功2提现失败3取消4银行处理中5挂起(状态未知）
 * @author shuys
 * @since 2019/5/30 11:35
 */
public enum WithdrawStatusEnum {

    AUDIT(0,"审核中"),
    SUCCESS(1,"提现成功"),
    FAILURE(2,"提现失败"),
    CANCEL(3,"取消"),
    BANK_PROCESS(4,"银行处理中"),
    HANG_UP(5,"挂起(状态未知）"),
    REFUSE(6,"拒绝提现"),
    ;

    private Integer code;

    private String description;

    WithdrawStatusEnum(Integer code,String description){
        this.code=code;
        this.description=description;
    }

    public static WithdrawStatusEnum getValueByCode(Integer code){
        for (WithdrawStatusEnum enums : values()) {
            if (enums.getCode()==code) {
                return enums;
            }
        }
        return null;
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
