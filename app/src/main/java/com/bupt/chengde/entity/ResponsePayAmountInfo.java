package com.bupt.chengde.entity;

/**
 * @author wyf
 * @类 :故障报修信息提交返回/用户上传 头像
 * @version 1.0
 */
public class ResponsePayAmountInfo {
	private String returnCode;
	private String returnMsg;
	private String url;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ResponsePayAmountInfo [returnCode=" + returnCode + ", returnMsg=" + returnMsg + ", url=" + url + "]";
	}

}
