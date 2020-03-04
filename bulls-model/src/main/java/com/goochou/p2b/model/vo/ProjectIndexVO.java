 package com.goochou.p2b.model.vo;

 import com.goochou.p2b.constant.ProductTypeEnum;

 import java.math.BigDecimal;
 import java.math.RoundingMode;

 /**
 * @author ydp
 * @date 2019/06/24
 */
public class ProjectIndexVO {
    
    private String sex;
    private Integer limitDay;
    private String limitDayStr;
    private Integer noob;
    private Integer id;
    private String annualizedStr;
    private String title;
    private Double totalAmount;
    private String increaseAnnualizedStr;
    private String littleImagePath;
    private String insuranceImagePath;
    private String earNumber;
    private Integer projectType;
    private Integer productType;
    private String productTypeStr;
    // 已投份数
    private Integer investedPoint;
    // 总份数
    private Integer totalPoint;
    private String moreLabel;
    private String moreKey;
    private String investedPointLabel;
    private String buttonLabel;
    private String annualizedLabel;
    private String limitDayLabel;
    private String sexImagePath;
    private String noobImagePath;
    
    public String getIncreaseAnnualizedStr() {
        return increaseAnnualizedStr;
    }
    public void setIncreaseAnnualizedStr(String increaseAnnualizedStr) {
        this.increaseAnnualizedStr = increaseAnnualizedStr;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Integer getLimitDay() {
        return limitDay;
    }
    public void setLimitDay(Integer limitDay) {
        this.limitDay = limitDay;
    }
    public String getLimitDayStr() {
        return limitDayStr;
    }
    public void setLimitDayStr(String limitDayStr) {
        this.limitDayStr = limitDayStr;
    }
    public Integer getNoob() {
        return noob;
    }
    public void setNoob(Integer noob) {
        this.noob = noob;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAnnualizedStr() {
        return annualizedStr;
    }
    public void setAnnualizedStr(String annualizedStr) {
        this.annualizedStr = annualizedStr;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public String getLittleImagePath() {
     return littleImagePath;
    }
    public void setLittleImagePath(String littleImagePath) {
     this.littleImagePath = littleImagePath;
    }
    public String getEarNumber() {
     return earNumber;
    }
    public void setEarNumber(String earNumber) {
     this.earNumber = earNumber;
    }

    public String getMoreLabel() {
        return "查看更多>>";
    }
    
    public void setMoreLabel(String moreLabel) {
        this.moreLabel = moreLabel;
    }
    
    public String getMoreKey() {
        final String prefix = "bulls#lookLimitDay=%s";
        if (this.noob == 1) {
            return String.format(prefix, "noob");
        } else {
            return String.format(prefix, this.limitDay);
        }
    }
    
    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public String getButtonLabel() {
        if (this.projectType == 1 && totalPoint != null) {
            BigDecimal amount = BigDecimal.valueOf(totalAmount).divide(BigDecimal.valueOf(totalPoint), 0, RoundingMode.DOWN);
            return amount + "元 立即拼团";
        }
        return BigDecimal.valueOf(this.totalAmount).setScale(0, BigDecimal.ROUND_DOWN)+"元认领";
    }

    public Integer getProjectType() {
        return projectType;
    }
    
    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

     public Integer getInvestedPoint() {
         return investedPoint;
     }

     public void setInvestedPoint(Integer investedPoint) {
         this.investedPoint = investedPoint;
     }

     public Integer getTotalPoint() {
         return totalPoint;
     }

     public void setTotalPoint(Integer totalPoint) {
         this.totalPoint = totalPoint;
     }

     public String getInvestedPointLabel() {
         int num = 0;
         if (this.investedPoint != null && this.totalPoint != null) {
             // 剩余份数
             num = (int) (this.totalPoint - this.investedPoint);
             return "当前剩余" + num + "份可拼";
         }
         return null;
     }

     public String getAnnualizedLabel() {
         if (this.projectType == null) {
             return null;
         }
         if (this.projectType == 1) {
             return "饲养预计利润/(份)";
         }
         return "饲养预计利润";
     }

     public String getInsuranceImagePath() {
         return insuranceImagePath;
     }

     public void setInsuranceImagePath(String insuranceImagePath) {
         this.insuranceImagePath = insuranceImagePath;
     }

     public void setProductType(Integer productType) {
         this.productType = productType;
     }

     public String getProductTypeStr() {
         ProductTypeEnum productTypeEnum = ProductTypeEnum.getProductTypeById(this.productType);
         if (productTypeEnum != null) {
             return productTypeEnum.getTypeName() + "牛";
         }
         return productTypeStr;
     }

     public String getLimitDayLabel() {
         return "饲养期";
     }

     public String getSexImagePath() {
         return sexImagePath;
     }

     public void setSexImagePath(String sexImagePath) {
         this.sexImagePath = sexImagePath;
     }

     public String getNoobImagePath() {
         return noobImagePath;
     }

     public void setNoobImagePath(String noobImagePath) {
         this.noobImagePath = noobImagePath;
     }
 }
