package com.bupt.chengde.entity;

import java.io.Serializable;

/**
 * @author wyf
 * @类 :宽带业务详情
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ResponseMacBusinessDtl implements Serializable{
	
	private String mbTitle;
	private String mbPicUrl;
	private String fee;
	private String mbId;
	private String mbContent;
	private String returnCode;
	private String returnMsg;
	

	
	
	public String getMbContent() {
		return mbContent;
	}

	public void setMbContent(String mbContent) {
		this.mbContent = mbContent;
	}

	public String getMbTitle() {
		return mbTitle;
	}

	public void setMbTitle(String mbTitle) {
		this.mbTitle = mbTitle;
	}
	
	public String getMbPicUrl() {
		return mbPicUrl;
	}

	public void setMbPicUrll(String mbPicUrl) {
		this.mbPicUrl = mbPicUrl;
	}
	
	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
	
	public String getMbIdl() {
		return mbId;
	}

	public void setMbId(String mbId) {
		this.mbId = mbId;
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
}
