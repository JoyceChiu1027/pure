package com.bupt.chengde.entity;

/***************
 * 签到返回的实体类
 * @date:2016-7-15 
 * @author:ln
 * @version:1.0
 * @return:
 * @throws:
 ***************/
public class DiscussCountSignResponse  extends PayAmountInfo {
	private String count;//增加积分
	private String latestCount;//最新积分
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getLatestCount() {
		return latestCount;
	}
	public void setLatestCount(String latestCount) {
		this.latestCount = latestCount;
	}
}	
