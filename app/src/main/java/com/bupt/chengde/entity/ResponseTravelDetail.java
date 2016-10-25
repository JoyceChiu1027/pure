package com.bupt.chengde.entity;

public class ResponseTravelDetail {
	private String url;
	//private String content;
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
	
/*	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
*/
	public String getReturnMessage() {
		return returnMessage;
	}
	
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
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

	@Override
	public String toString() {
		return "ResponseTravelDetail [url=" + url + ", isLike=" + isLike
				+ ", isStore=" + isStore +", returnCode=" + returnCode +", returnMessage=" + returnMessage + "]";
	}

}
