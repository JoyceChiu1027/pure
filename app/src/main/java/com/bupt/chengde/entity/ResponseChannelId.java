package com.bupt.chengde.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResponseChannelId implements Serializable{

	private String channelId;
	private String channelName;
    private String fatherChannelId;
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getFatherChannelId() {
		return fatherChannelId;
	}

	public void setFatherChannelId(String fatherChannelId) {
		this.fatherChannelId = fatherChannelId;
	}

	@Override
	public String toString() {
		return "ResponseChannelId [channelId=" + channelId + ", channelName="
				+ channelName + ", fatherChannelId=" + fatherChannelId + "]";
	}
	
}
