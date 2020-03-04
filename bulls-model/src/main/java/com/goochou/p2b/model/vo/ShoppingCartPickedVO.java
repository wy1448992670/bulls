 package com.goochou.p2b.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author sxy
 * @date 2019/07/24
 */
public class ShoppingCartPickedVO implements Serializable {

    private static final long serialVersionUID = 1L;
	public static DecimalFormat MONEY_FORMAT = new DecimalFormat("¥ ###,##0.00");
    private Integer pickedGoodsCount; //选中的商品数量
    
    private BigDecimal totalAmout; //选中商品总计金额

    public Integer getPickedGoodsCount() {
        return pickedGoodsCount;
    }

    public void setPickedGoodsCount(Integer pickedGoodsCount) {
        this.pickedGoodsCount = pickedGoodsCount;
    }

    public BigDecimal getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(BigDecimal totalAmout) {
        this.totalAmout = totalAmout;
    }
    
    public String getTotalAmoutStr() {
        return  MONEY_FORMAT.format(this.getTotalAmout());
    }
}
