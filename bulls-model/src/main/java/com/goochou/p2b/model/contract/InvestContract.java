package com.goochou.p2b.model.contract;


import com.goochou.p2b.model.BaseDO;

public class InvestContract extends BaseDO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String contractId;

    private String contractDownloadUrl;

    private String contractViewUrl;

    private Integer investmentId;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractDownloadUrl() {
		return contractDownloadUrl;
	}

	public void setContractDownloadUrl(String contractDownloadUrl) {
		this.contractDownloadUrl = contractDownloadUrl;
	}

	public String getContractViewUrl() {
		return contractViewUrl;
	}

	public void setContractViewUrl(String contractViewUrl) {
		this.contractViewUrl = contractViewUrl;
	}

	public Integer getInvestmentId() {
		return investmentId;
	}

	public void setInvestmentId(Integer investmentId) {
		this.investmentId = investmentId;
	}

}