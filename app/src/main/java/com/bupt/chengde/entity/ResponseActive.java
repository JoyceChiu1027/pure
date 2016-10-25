package com.bupt.chengde.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResponseActive implements Serializable{
	private String activeId;
	private String activeTitle;
	private String activeContent;
	private String activeDate;
	//private String activeLimittime;
	private String activePrice;
	private String activeStatus;
	private String activePosterUrl;
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getActiveTitle() {
		return activeTitle;
	}
	public void setActiveTitle(String activeTitle) {
		this.activeTitle = activeTitle;
	}
	public String getActiveContent() {
		return activeContent;
	}
	public void setActiveContent(String activeContent) {
		this.activeContent = activeContent;
	}
	public String getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}
	
	public String getActivePrice() {
		return activePrice;
	}
	public void setActivePrice(String activePrice) {
		this.activePrice = activePrice;
	}
	/*public String getActiveLimittime() {
		return activeLimittime;
	}
	public void setActiveLimittime(String activeLimittime) {
		this.activeLimittime = activeLimittime;
	}*/
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getActivePosterUrl() {
		return activePosterUrl;
	}
	public void setActivePosterUrl(String activePosterUrl) {
		this.activePosterUrl = activePosterUrl;
	}
	@Override
	public String toString() {
		return "ResponseActive [activeId=" + activeId + ", activeTitle="
				+ activeTitle + ", activeContent=" + activeContent
				+ ", activeDate=" + activeDate + ", activeLimittime="
				+ activePrice + ", activeStatus=" + activeStatus
				+ ", activePosterUrl=" + activePosterUrl + "]";
	}
	
	
}
