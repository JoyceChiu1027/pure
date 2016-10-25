package com.bupt.chengde.entity;
public class ResponseBenPeoById {
	private int benPeoId;
	private String benPeoAddr;
	private String benPeoPhone;
	private int isLike;
	private int isStore;
	private String detailUrl;
	private int returnCode;
	private String returnMessage;

	public int getBenPeoId() {
		return benPeoId;
	}

	public void setBenPeoId(int benPeoId) {
		this.benPeoId = benPeoId;
	}

	public String getBenPeoAddr() {
		return benPeoAddr;
	}

	public void setBenPeoAddr(String benPeoAddr) {
		this.benPeoAddr = benPeoAddr;
	}

	public String getBenPeoPhone() {
		return benPeoPhone;
	}

	public void setBenPeoPhone(String benPeoPhone) {
		this.benPeoPhone = benPeoPhone;
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

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
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
