package com.goochou.p2b.model.vo;

import com.goochou.p2b.constant.client.ClientConstants;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author shuys
 * @since 2019/5/27 17:54
 */
public class ShoppingCartVO implements Serializable {

    private final static String picUrl = ClientConstants.ALIBABA_PATH + "upload/";

    private static final long serialVersionUID = -7764201400722296145L;

//    private Integer id;

    // 用户ID
    private Integer userId;

    // 商品ID
    private Integer goodsId;

    // 商品数量
    private Integer goodsCount;

    // 创建时间
    private Date createDate = new Date();

    // 商品名称
    private String goodsName;

    // 商品编号
    private String goodsNo;

    // 销售价格（如果是会员就显示会员价）
    private BigDecimal salingPrice;

    // 会员价格
    private BigDecimal memberSalingPrice;

    // 库存
    private Integer stock = 0;

    // 商品图片地址（小图）
    private String path;

    // 是否选择 0否 1是
    private Integer isSelected = 0;

    // 商品总金额
    private BigDecimal goodsAmount;

    // 商品库存单位
    private String stockUnit;

//    public Integer getId() {
//        return id;
//    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

//    public Integer getStock() {
//        return stock;
//    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getGoodsAmount() {
        BigDecimal temp = new BigDecimal(this.goodsCount);
        return this.salingPrice.multiply(temp); // 计算单类商品总价格
    }

    public void setGoodsAmount(BigDecimal goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getPath() {
        if(StringUtils.isNotEmpty(path) && path.indexOf(picUrl) < 0) {
            return picUrl+path;
        }
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    public String getStockUnit() {
        if (StringUtils.isNotBlank(stockUnit))
            return "/" + stockUnit;
        return "";
    }

    public void setStockUnit(String stockUnit) {
        this.stockUnit = stockUnit;
    }
    

	public static DecimalFormat MONEY_FORMAT = new DecimalFormat("¥ ###,##0.00");
	public String getSalingPriceStr() {
		return MONEY_FORMAT.format(this.getSalingPrice());
	}
	
	public String getMemberSalingPriceStr() {
		return MONEY_FORMAT.format(this.getMemberSalingPrice());
	}
	
 
}
