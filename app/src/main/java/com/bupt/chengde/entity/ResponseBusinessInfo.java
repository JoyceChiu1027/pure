package com.bupt.chengde.entity;

import java.io.Serializable;

/**
 * 
 * @author wyf
 * @类 : 宽带、电视业务
 * @version 1.0
 */
public class ResponseBusinessInfo implements Serializable{
	/**
	 * 业务id
	 */
	private String busId;
	/**
	 * 业务名称
	 */
	private String busTitle;
	/**
	 * 业务图片地址
	 */
	private String busPicUrl;
	/**
	 * 业务期限
	 */
	private String busLimitTime;
	/**
	 * 业务费用
	 */
	private String busFee;
	/**
	 * 业务描述
	 */
	private String busContent;

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getBusTitle() {
		return busTitle;
	}

	public void setBusTitle(String busTitle) {
		this.busTitle = busTitle;
	}

	public String getBusPicUrl() {
		return busPicUrl;
	}

	public void setBusPicUrl(String busPicUrl) {
		this.busPicUrl = busPicUrl;
	}

	public String getBusLimitTime() {
		return busLimitTime;
	}

	public void setBusLimitTime(String busLimitTime) {
		this.busLimitTime = busLimitTime;
	}

	public String getBusFee() {
		return busFee;
	}

	public void setBusFee(String busFee) {
		this.busFee = busFee;
	}

	public String getBusContent() {
		return busContent;
	}

	public void setBusContent(String busContent) {
		this.busContent = busContent;
	}

	@Override
	public String toString() {
		return "ResponseBusinessInfo [busId=" + busId + ", busTitle="
				+ busTitle + ", busPicUrl=" + busPicUrl + ", busLimitTime="
				+ busLimitTime + ", busFee=" + busFee + ", busContent="
				+ busContent + "]";
	}

}
