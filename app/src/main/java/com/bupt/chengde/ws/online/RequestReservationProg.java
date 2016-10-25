package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

public class RequestReservationProg extends SoapObject {

	private String phoneNo;
	private String status;
	private String prevueId;

	public RequestReservationProg() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrevueId() {
		return prevueId;
	}

	public void setPrevueId(String prevueId) {
		this.prevueId = prevueId;
	}

	public int getPropertyCount() {
        return 3;
    }
	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return phoneNo;

		case 1:
			return status;
		case 2:
			return prevueId;
		}
		return null;
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			phoneNo = (String) value;
			break;

		case 1:
			status = (String) value;
			break;
		case 2:
			prevueId = (String) value;
			break;
		}
	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "phoneNo";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;

		case 1:
			info.name = "status";
			info.type = String.class;
			break;
		case 2:
			info.name = "prevueId";
			info.type = String.class;
			break;
		}
	}
}
