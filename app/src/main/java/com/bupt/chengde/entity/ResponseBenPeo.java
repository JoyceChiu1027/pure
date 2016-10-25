package com.bupt.chengde.entity;

public class ResponseBenPeo {
	private int benPeoId;
	private String benPeoName;
	private String benPeoUrl;
	private String benPeoPhone;
	private String benPeoAddr;

	public int getBenPeoId() {
		return benPeoId;
	}

	public void setBenPeoId(int benPeoId) {
		this.benPeoId = benPeoId;
	}

	public String getBenPeoName() {
		return benPeoName;
	}

	public void setBenPeoName(String benPeoName) {
		this.benPeoName = benPeoName;
	}

	public String getBenPeoUrl() {
		return benPeoUrl;
	}

	public void setBenPeoUrl(String benPeoUrl) {
		this.benPeoUrl = benPeoUrl;
	}

	public String getBenPeoPhone() {
		return benPeoPhone;
	}

	public void setBenPeoPhone(String benPeoPhone) {
		this.benPeoPhone = benPeoPhone;
	}

	public String getBenPeoAddr() {
		return benPeoAddr;
	}

	public void setBenPeoAddr(String benPeoAddr) {
		this.benPeoAddr = benPeoAddr;
	}

	@Override
	public String toString() {
		return "ResponseBenPeo [benPeoId=" + benPeoId + ", benPeoName="
				+ benPeoName + ", benPeoUrl=" + benPeoUrl + ", benPeoPhone="
				+ benPeoPhone + ", benPeoAddr=" + benPeoAddr + "]";
	}

}
