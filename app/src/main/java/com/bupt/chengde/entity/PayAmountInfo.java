package com.bupt.chengde.entity;

/**
 * @author wyf
 * @类 :网络请求返回的实体类
 * @version 1.0
 */
public class PayAmountInfo {
	private String returnCode;
	private String returnMsg;
	private String transNum;

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

	public String getTransNum() {
		return transNum;
	}

	public void setTransNum(String transNum) {
		this.transNum = transNum;
	}

	@Override
	public String toString() {
		return "PayAmountInfo{" +
				"returnCode='" + returnCode + '\'' +
				", returnMsg='" + returnMsg + '\'' +
				'}';
	}
}
