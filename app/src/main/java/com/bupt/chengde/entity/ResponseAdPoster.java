package com.bupt.chengde.entity;
/***************
 * @date:2016-4-15 上午9:48:53
 * @author:litf
 * @version:
 * @return:
 * @throws:
 ***************/
public class ResponseAdPoster{
	private int adId;
	private String adPicUrl;
	private String adUrl;
	
	public int getAdId() {
		return adId;
	}
	public void setAdId(int adId) {
		this.adId = adId;
	}
	public String getAdPicUrl() {
		return adPicUrl;
	}
	public void setAdPicUrl(String adPicUrl) {
		this.adPicUrl = adPicUrl;
	}
	public String getAdUrl() {
		return adUrl;
	}
	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}
	@Override
	public String toString() {
		return "ResponseAdPoster [adId=" + adId + ", adPicUrl=" + adPicUrl + ", adUrl=" + adUrl + "]";
	}
}
