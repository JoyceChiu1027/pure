package com.bupt.chengde.entity;

import java.io.Serializable;

/**
 * @author wyf
 * @类 :电视业务
 * @version 1.0
 */
public class ResponseTvBusiness implements Serializable{

	private static final long serialVersionUID = -4576944266677341328L;
	private String tbTitle;
	private String tbPicUrl;
	private String tvbId;
	private String subContent;

	public String getSubContent() {
		return subContent;
	}

	public void setSubContent(String subContent) {
		this.subContent = subContent;
	}

	public String getTbTitle() {
		return tbTitle;
	}

	public void setTbTitle(String tbTitle) {
		this.tbTitle = tbTitle;
	}

	public String getTbPicUrl() {
		return tbPicUrl;
	}

	public void setTbPicUrl(String tbPicUrl) {
		this.tbPicUrl = tbPicUrl;
	}

	public String getTvbId() {
		return tvbId;
	}

	public void setTvbId(String tvbId) {
		this.tvbId = tvbId;
	}

	@Override
	public String toString() {
		return "ResponseTvBusiness [tbTitle=" + tbTitle + ", tbPicUrl="
				+ tbPicUrl + ", tvbId=" + tvbId + ", subContent=" + subContent
				+ "]";
	}

	
}
