package com.goochou.p2b.hessian.transaction.investment;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.constant.ClientEnum;
import com.goochou.p2b.hessian.Request;

import java.math.BigDecimal;

/**
 * @Auther: huangsj
 * @Date: 2019/5/8 14:11
 * @Description:
 */
public class InvestmentOrderRequest extends Request {

    /**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = -4841787913467220218L;

    @FieldMeta(description = "订单号", have = true)
    private String orderNo;

    @FieldMeta(description = "购买的标的编号", have = true)
    private Integer projectId;

    @FieldMeta(description = "用户编号", have = true)
    private Integer userId;

    @FieldMeta(description = "使用的红包编号", have = true)
    private Integer hongbaoId;

    @FieldMeta(description = "账户余额支付金额", have = true)
    private BigDecimal blancePayMoney;

    @FieldMeta(description = "是否启用默认余额扣款", have = true)
    private boolean isAutoUseBalance;

    @FieldMeta(description = "银行卡编号")
    private Integer bankCardId;
    @FieldMeta(description = "请求ip")
    private String ipAddress;

    @FieldMeta(description = "请求来源", have = true)
    private ClientEnum clientEnum;

    @FieldMeta(description = "拼牛份数", have = true)
    private Integer point;
    
    @FieldMeta(description = "拼牛金额", have = true)
    private BigDecimal pinAmount;
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public boolean isAutoUseBalance() {
        return isAutoUseBalance;
    }

    public void setAutoUseBalance(boolean autoUseBalance) {
        isAutoUseBalance = autoUseBalance;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHongbaoId() {
        return hongbaoId;
    }

    public void setHongbaoId(Integer hongbaoId) {
        this.hongbaoId = hongbaoId;
    }

    public Integer getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Integer bankCardId) {
        this.bankCardId = bankCardId;
    }

    public BigDecimal getBlancePayMoney() {
        return blancePayMoney;
    }

    public void setBlancePayMoney(BigDecimal blancePayMoney) {
        this.blancePayMoney = blancePayMoney;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public ClientEnum getClientEnum() {
        return clientEnum;
    }

    public void setClientEnum(ClientEnum clientEnum) {
        this.clientEnum = clientEnum;
    }

    public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public BigDecimal getPinAmount() {
		return pinAmount;
	}

	public void setPinAmount(BigDecimal pinAmount) {
		this.pinAmount = pinAmount;
	}

	@Override
    public String toString() {
        return "InvestmentOrderRequest{" +
                "orderNo='" + orderNo + '\'' +
                ", projectId=" + projectId +
                ", userId=" + userId +
                ", blancePayMoney=" + blancePayMoney +
                ", bankCardId=" + bankCardId +
                ", clientEnum=" + clientEnum +
                '}';
    }
}
