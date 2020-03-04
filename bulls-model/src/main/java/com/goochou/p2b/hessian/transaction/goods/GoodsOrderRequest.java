package com.goochou.p2b.hessian.transaction.goods;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.hessian.Request;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: huangsj
 * @Date: 2019/5/8 14:17
 * @Description:
 */
public class GoodsOrderRequest extends Request {


    /**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = 3100147501813886307L;

    @FieldMeta(description = "购买订单号", have = true)
    private String orderNo;
    @FieldMeta(description = "购买用户编号", have = true)
    private Integer userId;
    @FieldMeta(description = "商品编号", have = true)
    private List<Integer> goodsIds;
    @FieldMeta(description = "购买商品数量", have = true)
    private List<Integer> counts;
    @FieldMeta(description = "收货地址编号")
    private Integer addressId;
    @FieldMeta(description = "使用的红包编号")
    private Integer hongbaoId;
    @FieldMeta(description = "账户余额支付金额")
    private BigDecimal balancePayMoney;
    @FieldMeta(description = "授信账户支付金额")
    private BigDecimal creditPayMoney;
    @FieldMeta(description = "需充值金额", have = true)
    private BigDecimal rechargeMoney;
    @FieldMeta(description = "请求ip")
    private String ipAddress;
    @FieldMeta(description = "是否秒杀")
    private Boolean isSecondKill;
    @FieldMeta(description = "秒杀活动详细编号")
    private Integer activityDetailId;



    @FieldMeta(description = "是否启用默认授信扣款", have = true)
    private boolean isAutoUseCredit;
    @FieldMeta(description = "是否启用默认余额扣款", have = true)
    private boolean isAutoUseBalance;


    @FieldMeta(description = "第三方支付渠道OutPayEnum 默认为  fuioupay_quick")
    private String payChannel;
    @FieldMeta(description = "银行卡编号")
    private Integer bankCardId;

    @FieldMeta(description = "请求来源", have = true)
    private ClientEnum clientEnum;

    public boolean isAutoUseCredit() {
        return isAutoUseCredit;
    }

    public void setAutoUseCredit(boolean autoUseCredit) {
        isAutoUseCredit = autoUseCredit;
    }

    public boolean isAutoUseBalance() {
        return isAutoUseBalance;
    }

    public void setAutoUseBalance(boolean autoUseBalance) {
        isAutoUseBalance = autoUseBalance;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getHongbaoId() {
        return hongbaoId;
    }

    public void setHongbaoId(Integer hongbaoId) {
        this.hongbaoId = hongbaoId;
    }

    public BigDecimal getBalancePayMoney() {
        return balancePayMoney;
    }

    public void setBalancePayMoney(BigDecimal balancePayMoney) {
        this.balancePayMoney = balancePayMoney;
    }

    public BigDecimal getCreditPayMoney() {
        return creditPayMoney;
    }

    public void setCreditPayMoney(BigDecimal creditPayMoney) {
        this.creditPayMoney = creditPayMoney;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public List<Integer> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(List<Integer> goodsIds) {
        this.goodsIds = goodsIds;
    }

    public List<Integer> getCounts() {
        return counts;
    }

    public void setCounts(List<Integer> counts) {
        this.counts = counts;
    }

    public ClientEnum getClientEnum() {
        return clientEnum;
    }

    public void setClientEnum(ClientEnum clientEnum) {
        this.clientEnum = clientEnum;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getBankCardId() {
        return bankCardId;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public void setBankCardId(Integer bankCardId) {
        this.bankCardId = bankCardId;
    }

    public Boolean getSecondKill() {
        return isSecondKill;
    }

    public void setSecondKill(Boolean secondKill) {
        isSecondKill = secondKill;
    }

    public Integer getActivityDetailId() {
        return activityDetailId;
    }

    public void setActivityDetailId(Integer activityDetailId) {
        this.activityDetailId = activityDetailId;
    }

    @Override
    public String toString() {
        return "GoodsOrderRequest{" +
                "orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                ", goodsIds=" + goodsIds +
                ", counts=" + counts +
                ", addressId=" + addressId +
                ", hongbaoId=" + hongbaoId +
                ", balancePayMoney=" + balancePayMoney +
                ", creditPayMoney=" + creditPayMoney +
                ", rechargeMoney=" + rechargeMoney +
                ", ipAddress='" + ipAddress + '\'' +
                ", isSecondKill=" + isSecondKill +
                ", activityDetailId=" + activityDetailId +
                ", isAutoUseCredit=" + isAutoUseCredit +
                ", isAutoUseBalance=" + isAutoUseBalance +
                ", payChannel='" + payChannel + '\'' +
                ", bankCardId=" + bankCardId +
                ", clientEnum=" + clientEnum +
                '}';
    }
}
