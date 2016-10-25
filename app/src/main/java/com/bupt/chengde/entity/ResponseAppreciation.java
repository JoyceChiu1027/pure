package com.bupt.chengde.entity;
/***************
 * @date:2016-4-12 下午2:11:16
 * @author:litf
 * @version:
 * @return:
 * @throws:
 ***************/
public class ResponseAppreciation {
	private String aprePicUrl;// 赏析图url
	private String apreName;// 赏析 名称
	private String apreInto;// 赏析 文
	private String apreId;// 赏析 id

	public String getAprePicUrl() {
		return aprePicUrl;
	}

	public void setAprePicUrl(String aprePicUrl) {
		this.aprePicUrl = aprePicUrl;
	}

	public String getApreName() {
		return apreName;
	}

	public void setApreName(String apreName) {
		this.apreName = apreName;
	}

	public String getApreInto() {
		return apreInto;
	}

	public void setApreInto(String apreInto) {
		this.apreInto = apreInto;
	}

	public String getApreId() {
		return apreId;
	}

	public void setApreId(String apreId) {
		this.apreId = apreId;
	}

}
