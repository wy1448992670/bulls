package com.goochou.p2b.model.vo.bulls;

import com.goochou.p2b.model.MallActivitySecondKill;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 注释
 * </p>
 *
 * @author shuys
 * @since 2019年12月17日 11:32:00
 */
public class MallActivitySecondKillVO extends MallActivitySecondKill {
    
    private String activityName;

    private Integer goodsId;
    
    private String goodsName;

    private String goodsNo;

    private BigDecimal buyingPrice;

    private BigDecimal salingPrice;
    
    private BigDecimal memberSalingPrice;

    private Integer stock;

    private Integer sellStock;
    
    private Integer lockStock;
    
    private String littleImage;

    private String activityImage;
    
    private Date startDate;
    
    private Date stopDate;
    
    private Integer weekCount;
    
    private String enableMsg;
    
    private String isOnShelvesMsg;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getSalingPrice() {
        return salingPrice;
    }

    public void setSalingPrice(BigDecimal salingPrice) {
        this.salingPrice = salingPrice;
    }

    public BigDecimal getMemberSalingPrice() {
        return memberSalingPrice;
    }

    public void setMemberSalingPrice(BigDecimal memberSalingPrice) {
        this.memberSalingPrice = memberSalingPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSellStock() {
        return sellStock;
    }

    public void setSellStock(Integer sellStock) {
        this.sellStock = sellStock;
    }

    public Integer getLockStock() {
        return lockStock;
    }

    public void setLockStock(Integer lockStock) {
        this.lockStock = lockStock;
    }

    public String getLittleImage() {
        return littleImage;
    }

    public void setLittleImage(String littleImage) {
        this.littleImage = littleImage;
    }

    public Integer getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(Integer weekCount) {
        this.weekCount = weekCount;
    }

    public String getActivityImage() {
        return activityImage;
    }

    public void setActivityImage(String activityImage) {
        this.activityImage = activityImage;
    }

    public String getEnableMsg() {
        if (this.getEnable() == 1) {
            return "可用";
        }
        return "不可用";
    }
    

    public String getIsOnShelvesMsg() {
        if (this.getIsOnShelves() == 1) {
            return "上架";
        }
        return "下架";
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }
}
