package com.bupt.chengde.entity;

/***************
 * 签到返回的实体类
 * @date:2016-4-18 下午2:47:15
 * @author:wyf
 * @version:1.0
 * @return:
 * @throws:
 ***************/
public class PayAmountInfoQiandao extends PayAmountInfo {
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
