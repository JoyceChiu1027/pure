package com.bupt.chengde.entity;

public class ResponseSerNewsDtl {
	private String url;
	private int isLike;
	private int isStore;
	private int returnCode;
	private String returnMessage;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIsLike() {
		return isLike;
	}
	public void setIsLike(int isLike) {
		this.isLike = isLike;
	}
	public int getIsStore() {
		return isStore;
	}
	public void setIsStore(int isStore) {
		this.isStore = isStore;
	}
	
	public int getReturnCode() {
		return returnCode;
	}
	
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	
	public String getReturnMessage() {
		return returnMessage;
	}
	
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

}
