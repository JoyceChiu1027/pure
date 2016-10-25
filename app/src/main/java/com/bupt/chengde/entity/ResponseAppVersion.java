package com.bupt.chengde.entity;

/**
 * @author wyf
 * @类 :版本信息
 * @version 1.0
 */
public class ResponseAppVersion extends CommonBaseInfo {
	private int versionCode;
	private String versionName;
	private String apkUrl;
	private String returnCode;
	
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	@Override
	public String toString() {
		return "ResponseAppVersion [versionCode=" + versionCode
				+ ", versionName=" + versionName + ", apkUrl=" + apkUrl
				+ ", returnCode=" + returnCode + "]";
	}

}
