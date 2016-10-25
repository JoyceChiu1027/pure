package com.bupt.chengde.entity;

import java.util.List;

public class ResponseMainPage extends ResponseJson {
	private List<ResponseMainMenu> rsmm;
	private List<ResponseAdPoster> rsap;
	private List<ResponseNews> rsns;
	private List<ResponseHots> rshs;
	private List<ResponseSales> rsss;

	public List<ResponseMainMenu> getRsmm() {
		return rsmm;
	}

	public void setRsmm(List<ResponseMainMenu> rsmm) {
		this.rsmm = rsmm;
	}

	public List<ResponseAdPoster> getRsap() {
		return rsap;
	}

	public void setRsap(List<ResponseAdPoster> rsap) {
		this.rsap = rsap;
	}

	public List<ResponseNews> getRsns() {
		return rsns;
	}

	public void setRsns(List<ResponseNews> rsns) {
		this.rsns = rsns;
	}

	public List<ResponseHots> getRshs() {
		return rshs;
	}

	public void setRshs(List<ResponseHots> rshs) {
		this.rshs = rshs;
	}

	public List<ResponseSales> getRsss() {
		return rsss;
	}

	public void setRsss(List<ResponseSales> rsss) {
		this.rsss = rsss;
	}

}
