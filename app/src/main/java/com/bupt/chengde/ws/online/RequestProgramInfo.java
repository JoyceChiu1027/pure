package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * Created by wyf on 2016/1/8. 节目预告信息
 */
public class RequestProgramInfo extends SoapObject {

	private String channelId;
	private String broadCastDate;
	private String phoneNo;

	public RequestProgramInfo() {
		super("", "");
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getBroadCastDate() {
		return broadCastDate;
	}

	public void setBroadCastDate(String broadCastDate) {
		this.broadCastDate = broadCastDate;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getPropertyCount() {
		return 3;
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return channelId;

		case 1:
			return broadCastDate;
		case 2:
			return phoneNo;
		}
		return null;
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			channelId = (String) value;
			break;

		case 1:
			broadCastDate = (String) value;
			break;
		case 2:
			phoneNo = (String) value;
			break;
		}
	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "channelId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;

		case 1:
			info.name = "broadCastDate";
			info.type = String.class;
			break;
		case 2:
			info.name = "phoneNo";
			info.type = String.class;
			break;
		}
	}
}
