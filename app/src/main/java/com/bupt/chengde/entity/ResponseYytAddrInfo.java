package com.bupt.chengde.entity;

import java.io.Serializable;

/**
 * @author wyf
 * @类 :实体营业厅地址信息
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResponseYytAddrInfo implements Serializable {
	private String yytName;
	private String addr;
	private String phoneNo;
	private String addressId;

	
	
	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getYytName() {
		return yytName;
	}

	public void setYytName(String yytName) {
		this.yytName = yytName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String toString() {
		return "ResponseYytAddrInfo [yytName=" + yytName + ", addr=" + addr
				+ ", phoneNo=" + phoneNo + ", addressId=" + addressId + "]";
	}

}
