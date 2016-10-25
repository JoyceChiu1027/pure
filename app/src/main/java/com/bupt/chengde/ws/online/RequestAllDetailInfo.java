package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

public class RequestAllDetailInfo extends SoapObject {

	private String busiId;
	private String custId;
	private String modType;

	public RequestAllDetailInfo() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	public String getBusiId() {
		return busiId;
	}

	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getModType() {
		return modType;
	}

	public void setModType(String modType) {
		this.modType = modType;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
		case 0:
			busiId = (String) obj;
			break;
		case 1:
			custId = (String) obj;
			break;
		case 2:
			modType = (String) obj;
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return busiId;
		case 1:
			return custId;
		case 2:
			return modType;
		}
		return null;

	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "busiId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 1:
			info.name = "custId";
			info.type = String.class;
			break;
		case 2:
			info.name = "modType";
			info.type = String.class;
			break;
		}
	}
}
