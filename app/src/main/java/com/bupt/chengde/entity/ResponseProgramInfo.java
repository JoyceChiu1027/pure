package com.bupt.chengde.entity;

/**
 * Created by wyf on 2016/1/8.
 * 节目预告信息
 */
public class ResponseProgramInfo {
    private String prodName;
    private String channel;
    private String broadCastTime;
    private String prevueId;
    private int status;

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }


    public String getBroadCastTime() {
        return broadCastTime;
    }

    public void setBroadCastTime(String broadCastTime) {
        this.broadCastTime = broadCastTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPrevueId() {
		return prevueId;
	}

	public void setPrevueId(String prevueId) {
		this.prevueId = prevueId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
    public String toString() {
        return "ResponseProgramInfo{" +
                "prodName='" + prodName + '\'' +
                ", channel='" + channel + '\'' +
                ", broadCastTime='" + broadCastTime + '\'' +
                '}';
    }
}
