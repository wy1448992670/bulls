package com.goochou.p2b.model.vo;

public class LandlordVO {
	
	private Integer userId;
	
    private String username;
    
    private String trueName;
    
    private String phone;
    
    private String identityCard;
    
    private String cardNumber;
    
    private String bankPhone;
    
    private Integer enterpriseId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	@Override
	public String toString() {
		return "LandlordVO [userId=" + userId + ", username=" + username
				+ ", trueName=" + trueName + ", phone=" + phone
				+ ", identityCard=" + identityCard + ", cardNumber="
				+ cardNumber + ", bankPhone=" + bankPhone + ", enterpriseId="
				+ enterpriseId + "]";
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}
    
}
