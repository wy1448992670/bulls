package com.goochou.p2b.hessian.transaction;

import java.math.BigDecimal;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Response;

public class OrderResponse extends Response{

    /**
	 * <p>Discription:[字段功能描述]</p>
	 */
	private static final long serialVersionUID = -3946900745613197059L;
	@FieldMeta(description = "订单ID", have = true)
    private Integer id;
    @FieldMeta(description = "订单号", have = true)
    private String orderNo;
	@FieldMeta(description = "需支付金额")
	private BigDecimal needPayMoney;

	@FieldMeta(description = "可用的金额")
	private BigDecimal  availableMoney;
	@FieldMeta(description = "可用的利金额")
	private BigDecimal  availableCreditMoney;
	@FieldMeta(description = "订单类型")
    private String orderType;
	@FieldMeta(description = "投资订单子类型")
	private Integer investmentType=0;
	
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

	public BigDecimal getAvailableMoney() {
		return availableMoney;
	}

	public void setAvailableMoney(BigDecimal availableMoney) {
		this.availableMoney = availableMoney;
	}

	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getAvailableCreditMoney() {
		return availableCreditMoney;
	}

	public void setAvailableCreditMoney(BigDecimal availableCreditMoney) {
		this.availableCreditMoney = availableCreditMoney;
	}

	public BigDecimal getNeedPayMoney() {
		return needPayMoney;
	}
	public void setNeedPayMoney(BigDecimal needPayMoney) {
		this.needPayMoney = needPayMoney;
	}

	public Integer getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(Integer investmentType) {
		this.investmentType = investmentType;
	}

	@Override
	public String toString() {
		return "OrderResponse{" +
				"orderNo='" + orderNo + '\'' +
				", needPayMoney='" + needPayMoney + '\'' +
				'}';
	}
}
