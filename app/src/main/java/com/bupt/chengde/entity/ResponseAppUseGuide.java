package com.bupt.chengde.entity;

/**
 * Created by wyf on 2015/12/17. 使用指南
 */
public class ResponseAppUseGuide {

	/*
	 * private int expiainId; private String url1; private String url2; private
	 * String url3; private String url4; private String url5;
	 */
	private String guideId;
	private String guideTitle;
	private String guideContent;
	private String guideUrl;
	private String date;

	public String getGuideId() {
		return guideId;
	}

	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	public String getGuideTitle() {
		return guideTitle;
	}

	public void setGuideTitle(String guideTitle) {
		this.guideTitle = guideTitle;
	}

	public String getGuideContent() {
		return guideContent;
	}

	public void setGuideContent(String guideContent) {
		this.guideContent = guideContent;
	}

	public String getGuideUrl() {
		return guideUrl;
	}

	public void setGuideUrl(String guideUrl) {
		this.guideUrl = guideUrl;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
