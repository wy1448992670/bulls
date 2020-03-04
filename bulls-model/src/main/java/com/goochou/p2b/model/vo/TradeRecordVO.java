package com.goochou.p2b.model.vo;

public class TradeRecordVO {
    private String username;
    private String trueName;
    private String type;
    private String source;
    private String rate;
    private String amount;
    private String time;
    private String balance;
    private String wav;
    private String userId;
    
    public TradeRecordVO(){
    	
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWav() {
		return wav;
	}

	public void setWav(String wav) {
		this.wav = wav;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

	public TradeRecordVO(String username, String trueName, String type,
			String source, String rate, String amount, String time,
			String balance, String wav, String userId) {
		this.username = username;
		this.trueName = trueName;
		this.type = type;
		this.source = source;
		this.rate = rate;
		this.amount = amount;
		this.time = time;
		this.balance = balance;
		this.wav = wav;
		this.userId = userId;
	}

}
