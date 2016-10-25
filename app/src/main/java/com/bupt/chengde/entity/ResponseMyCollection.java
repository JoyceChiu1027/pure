package com.bupt.chengde.entity;

/**
 * @author litf
 * @类 :获取我的收藏列表
 * @version 1.0
 */
public class ResponseMyCollection {
	private String busId;
	private String busName;
	private String busPicUrl;
	private String modType;

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusPicUrl() {
		return busPicUrl;
	}

	public void setBusPicUrl(String busPicUrl) {
		this.busPicUrl = busPicUrl;
	}

	public String getModType() {
		return modType;
	}

	public void setModType(String modType) {
		this.modType = modType;
	}

	@Override
	public String toString() {
		return "ResponseMyCollection [busId=" + busId + ", busName=" + busName + ", busPicUrl=" + busPicUrl
				+ ", modType=" + modType + "]";
	}


}
