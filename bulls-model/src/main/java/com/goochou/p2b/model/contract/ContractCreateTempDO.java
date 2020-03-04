package com.goochou.p2b.model.contract;

import com.goochou.p2b.model.BaseDO;

/**
 * 
 * @author xueqi
 *
 */
public class ContractCreateTempDO extends BaseDO
{

    /**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = -3746165043804059585L;
    
    private Integer investmentId;
    
    private Integer investmentDetailId;
    
    //'weizhixing 未执行 yicharuxiancheng 插入多线程 zhixingchenggong 执行成功'
    private String status;

	public ContractCreateTempDO(){}

	public Integer getInvestmentId() {
		return investmentId;
	}

	public void setInvestmentId(Integer investmentId) {
		this.investmentId = investmentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getInvestmentDetailId() {
		return investmentDetailId;
	}

	public void setInvestmentDetailId(Integer investmentDetailId) {
		this.investmentDetailId = investmentDetailId;
	}

	@Override
	public String toString() {
		return "ContractCreateTempDO [investmentId=" + investmentId + ", status=" + status + "]";
	}
}
