package com.goochou.p2b.model;

import java.util.Date;

public class UserOperationRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_operation_record.id
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_operation_record.user_id
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_operation_record.bank_card_no
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    private String bankCardNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_operation_record.message
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    private String message;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_operation_record.bank_name
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    private String bankName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_operation_record.create_time
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_operation_record.type
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_operation_record.status
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_operation_record.note
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    private String note;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_operation_record.id
     *
     * @return the value of user_operation_record.id
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_operation_record.id
     *
     * @param id the value for user_operation_record.id
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_operation_record.user_id
     *
     * @return the value of user_operation_record.user_id
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_operation_record.user_id
     *
     * @param userId the value for user_operation_record.user_id
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_operation_record.bank_card_no
     *
     * @return the value of user_operation_record.bank_card_no
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public String getBankCardNo() {
        return bankCardNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_operation_record.bank_card_no
     *
     * @param bankCardNo the value for user_operation_record.bank_card_no
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_operation_record.message
     *
     * @return the value of user_operation_record.message
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_operation_record.message
     *
     * @param message the value for user_operation_record.message
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_operation_record.bank_name
     *
     * @return the value of user_operation_record.bank_name
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_operation_record.bank_name
     *
     * @param bankName the value for user_operation_record.bank_name
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_operation_record.create_time
     *
     * @return the value of user_operation_record.create_time
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_operation_record.create_time
     *
     * @param createTime the value for user_operation_record.create_time
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_operation_record.type
     *
     * @return the value of user_operation_record.type
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_operation_record.type
     *
     * @param type the value for user_operation_record.type
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_operation_record.status
     *
     * @return the value of user_operation_record.status
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_operation_record.status
     *
     * @param status the value for user_operation_record.status
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_operation_record.note
     *
     * @return the value of user_operation_record.note
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_operation_record.note
     *
     * @param note the value for user_operation_record.note
     *
     * @mbggenerated Wed Jun 22 16:42:51 CST 2016
     */
    public void setNote(String note) {
        this.note = note;
    }
}