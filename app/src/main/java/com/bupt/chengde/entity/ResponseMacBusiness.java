package com.bupt.chengde.entity;

import java.io.Serializable;

/**
 * @author wyf
 * @类 :宽带业务
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResponseMacBusiness implements Serializable {

	private String mbTitle;
	private String mbId;
	private String mbPicUrl;
	private String fee;
	private String limitTime;
	private String bandWidth;
	private String level;
	private String saleFee;
	private String returnCode;
	private String returnMsg;
	private String proType;
	private String subContent;

	public String getMbTitle() {
		return mbTitle;
	}

	public void setMbTitle(String mbTitle) {
		this.mbTitle = mbTitle;
	}

	public String getMbId() {
		return mbId;
	}

	public void setMbId(String mbId) {
		this.mbId = mbId;
	}

	public String getMbPicUrl() {
		return mbPicUrl;
	}

	public void setMbPicUrl(String mbPicUrl) {
		this.mbPicUrl = mbPicUrl;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}

	public String getBandWidth() {
		return bandWidth;
	}

	public void setBandWidth(String bandWidth) {
		this.bandWidth = bandWidth;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSaleFee() {
		return saleFee;
	}

	public void setSaleFee(String saleFee) {
		this.saleFee = saleFee;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getSubContent() {
		return subContent;
	}

	public void setSubContent(String subContent) {
		this.subContent = subContent;
	}

}
