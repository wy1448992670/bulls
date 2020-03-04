package com.goochou.p2b.hessian.user;

import com.goochou.p2b.annotationin.FieldMeta;
import com.goochou.p2b.hessian.Request;

public class UserAddressRequest extends Request{
	private static final long serialVersionUID = -6817049802044051068L;
	@FieldMeta(description = "用户ID", have = true)
	private Integer userId;
	private Integer addressId;//没有id是新增
	@FieldMeta(description = "备注", have = true)
	private String remarks;
	@FieldMeta(description = "详细地址", have = true)
	private String detailAddress;
	@FieldMeta(description = "姓名", have = true)
	private String name;
	@FieldMeta(description = "手机号", have = true)
	private String phone;
	private Integer isDefault;//是否默认地址
	private Integer postcode;//邮编
	private Integer cityId;
	private Integer provinceId;
	
	private String provinceCode;
	private String cityCode;
	private String areaCode;
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	 
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	 
	public Integer getPostcode() {
		return postcode;
	}
	public void setPostcode(Integer postcode) {
		this.postcode = postcode;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
