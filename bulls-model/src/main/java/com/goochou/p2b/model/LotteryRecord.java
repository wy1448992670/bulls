package com.goochou.p2b.model;

import java.util.Date;

public class LotteryRecord {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.user_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.phone
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private String phone;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.gift_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private Integer giftId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.gift_name
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private String giftName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.count_type
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private Integer countType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.user_address_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private Integer userAddressId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.create_date
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private Date createDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.ip
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private String ip;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.track_type
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private Integer trackType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.track_no
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private String trackNo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.status
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private Integer status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.activity_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private Integer activityId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.version
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private Integer version;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column lottery_record.remark
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	private String remark;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.id
	 * @return  the value of lottery_record.id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.id
	 * @param id  the value for lottery_record.id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.user_id
	 * @return  the value of lottery_record.user_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.user_id
	 * @param userId  the value for lottery_record.user_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.phone
	 * @return  the value of lottery_record.phone
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.phone
	 * @param phone  the value for lottery_record.phone
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.gift_id
	 * @return  the value of lottery_record.gift_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public Integer getGiftId() {
		return giftId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.gift_id
	 * @param giftId  the value for lottery_record.gift_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.gift_name
	 * @return  the value of lottery_record.gift_name
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public String getGiftName() {
		return giftName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.gift_name
	 * @param giftName  the value for lottery_record.gift_name
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.count_type
	 * @return  the value of lottery_record.count_type
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public Integer getCountType() {
		return countType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.count_type
	 * @param countType  the value for lottery_record.count_type
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setCountType(Integer countType) {
		this.countType = countType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.user_address_id
	 * @return  the value of lottery_record.user_address_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public Integer getUserAddressId() {
		return userAddressId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.user_address_id
	 * @param userAddressId  the value for lottery_record.user_address_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setUserAddressId(Integer userAddressId) {
		this.userAddressId = userAddressId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.create_date
	 * @return  the value of lottery_record.create_date
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.create_date
	 * @param createDate  the value for lottery_record.create_date
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.ip
	 * @return  the value of lottery_record.ip
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.ip
	 * @param ip  the value for lottery_record.ip
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.track_type
	 * @return  the value of lottery_record.track_type
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public Integer getTrackType() {
		return trackType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.track_type
	 * @param trackType  the value for lottery_record.track_type
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setTrackType(Integer trackType) {
		this.trackType = trackType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.track_no
	 * @return  the value of lottery_record.track_no
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public String getTrackNo() {
		return trackNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.track_no
	 * @param trackNo  the value for lottery_record.track_no
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.status
	 * @return  the value of lottery_record.status
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.status
	 * @param status  the value for lottery_record.status
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.activity_id
	 * @return  the value of lottery_record.activity_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public Integer getActivityId() {
		return activityId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.activity_id
	 * @param activityId  the value for lottery_record.activity_id
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.version
	 * @return  the value of lottery_record.version
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.version
	 * @param version  the value for lottery_record.version
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column lottery_record.remark
	 * @return  the value of lottery_record.remark
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column lottery_record.remark
	 * @param remark  the value for lottery_record.remark
	 * @mbg.generated  Thu May 09 17:06:38 CST 2019
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}