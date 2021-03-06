package com.goochou.p2b.model;

import java.math.BigDecimal;
import java.util.Date;

public class InvestmentBlance {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column investment_blance.id
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column investment_blance.user_id
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column investment_blance.investment_id
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    private Integer investmentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column investment_blance.amount
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    private BigDecimal amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column investment_blance.use_amount
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    private BigDecimal useAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column investment_blance.state
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column investment_blance.version
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    private Integer version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column investment_blance.create_date
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column investment_blance.update_date
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    private Date updateDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column investment_blance.id
     *
     * @return the value of investment_blance.id
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column investment_blance.id
     *
     * @param id the value for investment_blance.id
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column investment_blance.user_id
     *
     * @return the value of investment_blance.user_id
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column investment_blance.user_id
     *
     * @param userId the value for investment_blance.user_id
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column investment_blance.investment_id
     *
     * @return the value of investment_blance.investment_id
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public Integer getInvestmentId() {
        return investmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column investment_blance.investment_id
     *
     * @param investmentId the value for investment_blance.investment_id
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column investment_blance.amount
     *
     * @return the value of investment_blance.amount
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column investment_blance.amount
     *
     * @param amount the value for investment_blance.amount
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column investment_blance.use_amount
     *
     * @return the value of investment_blance.use_amount
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public BigDecimal getUseAmount() {
        return useAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column investment_blance.use_amount
     *
     * @param useAmount the value for investment_blance.use_amount
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public void setUseAmount(BigDecimal useAmount) {
        this.useAmount = useAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column investment_blance.state
     *
     * @return the value of investment_blance.state
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column investment_blance.state
     *
     * @param state the value for investment_blance.state
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column investment_blance.version
     *
     * @return the value of investment_blance.version
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column investment_blance.version
     *
     * @param version the value for investment_blance.version
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column investment_blance.create_date
     *
     * @return the value of investment_blance.create_date
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column investment_blance.create_date
     *
     * @param createDate the value for investment_blance.create_date
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column investment_blance.update_date
     *
     * @return the value of investment_blance.update_date
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column investment_blance.update_date
     *
     * @param updateDate the value for investment_blance.update_date
     *
     * @mbggenerated Fri May 10 16:31:23 CST 2019
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}