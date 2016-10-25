package com.bupt.chengde.entity;

import android.util.Log;

public class ResponseCustLoginInfo extends CommonBaseInfo{
	private long custId;
	private String custName;
	private String custPhone;
	private String memo;
	private int custInt;
	private String lastModifiedTime;
	private String lastDiscussTime;
	private String url;

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getCustInt() {
		return custInt;
	}

	public void setCustInt(int custInt) {
		this.custInt = custInt;
	}
	
	public String getLastModifiedTime() {
		Log.i("!!!!!!!!!!!!!!!!!!!!", lastModifiedTime);
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
	public String getLastDiscussTime() {
		Log.i("!!!!!!!!!!!!!!!!!!!!", lastDiscussTime);
		return lastDiscussTime;
	}

	public void setLastDiscussTime(String lastDiscussTime) {
		this.lastDiscussTime = lastDiscussTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ResponseCustLoginInfo [custId=" + custId + ", custName=" + custName + ", custPhone=" + custPhone
				+ ", memo=" + memo + ", custInt=" + custInt + ", lastModifiedTime=" + lastModifiedTime
				+ ", lastDiscussTime=" + lastDiscussTime + ", url=" + url + "]";
	}
}
