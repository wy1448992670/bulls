package com.goochou.p2b.constant;

import com.goochou.p2b.constant.client.ClientConstants;

public enum LotteryTipsEnum {
    NOT_HIT1(1,"失败是成功的妈，下一次就能抽中了", ClientConstants.ALIBABA_PATH + "images/newyear/1.png"),
    NOT_HIT2(2,"姿势不对，换个姿势，再抽一次", ClientConstants.ALIBABA_PATH + "images/newyear/2.png"),
    NOT_HIT3(3,"听说养只牛牛，就会有好运喔~",ClientConstants.ALIBABA_PATH + "images/newyear/3.png"),
    NOT_HIT4(4,"没抽中，吃点风干牛肉压压惊",ClientConstants.ALIBABA_PATH + "images/newyear/4.png"),
    NOT_HIT5(5,"我有预感，下次就能抽中牛卡了",ClientConstants.ALIBABA_PATH + "images/newyear/5.png");
	
	
	LotteryTipsEnum(int type, String tips, String imgPath) {
		this.type = type;
		this.tips = tips;
		this.setImgPath(imgPath);
	}
	
	 public static LotteryTipsEnum getLotteryTipsByType(int type) {
		 for (LotteryTipsEnum enums : values()) {
			 if (enums.getType() == type) {
				 return enums;
			 }
		 }
		 return null;
	 }
	/**
     * 类型
     */
    private int type;
    /**
     *  提示语
     */
    private String tips;
    
    private String imgPath;
    
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
