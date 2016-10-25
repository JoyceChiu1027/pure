package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

public class RequestTravelDetail extends SoapObject{
	private String custId;
	private String type;
	private String detId;
	public RequestTravelDetail(){
		super("", "");
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetId() {
		return detId;
	}
	public void setDetId(String detId) {
		this.detId = detId;
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
			custId=(String)obj;
			break;
		case 1:
			type = (String)obj;
			break;
		case 2:
			detId = (String) obj;
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return custId;
		case 1:
			return type;
		case 2:
			return detId;
		}
		return null;

	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "custId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 1:
			info.name = "type";
			info.type = String.class;
			break;
		case 2:
			info.name = "detId";
			info.type = String.class;
			break;
		}
	}
}
