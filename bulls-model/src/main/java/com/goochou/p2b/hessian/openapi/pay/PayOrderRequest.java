package com.goochou.p2b.hessian.openapi.pay;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.hessian.Request;

/**
 * @Auther: huangsj
 * @Date: 2019/5/21 15:00
 * @Description:
 */
public class PayOrderRequest  extends Request {

    private String orderType;
    private String orderNum;
    private Integer userId;


    @FieldMeta(description = "第三方支付渠道OutPayEnum 默认为  fuioupay_quick")
    private String payChannel;
    @FieldMeta(description = "银行卡编号")
    private Integer bankCardId;
    @FieldMeta(description = "请求ip")
    private String ipAddress;

    @FieldMeta(description = "请求来源", have = true)
    private ClientEnum clientEnum;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Integer bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public ClientEnum getClientEnum() {
        return clientEnum;
    }

    public void setClientEnum(ClientEnum clientEnum) {
        this.clientEnum = clientEnum;
    }
}
