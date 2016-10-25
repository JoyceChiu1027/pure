package com.bupt.chengde.entity;

public class HomeBean {
	private int id;
	private String imgUrl;
	private String title;
	private String detail;
	private String time;
	private int imgRes;
	

	public int getImgRes() {
		return imgRes;
	}

	public void setImgRes(int imgRes) {
		this.imgRes = imgRes;
	}

	// 1:首页推荐 2：热门活动 3：每日惠
	private int type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "HomeBean [imgUrl=" + imgUrl + ", title=" + title + ", detail="
				+ detail + ", time=" + time + "]";
	}

}
