package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

public class RequestModifyPhone extends SoapObject {
	private String custId;
//	private String custName;
	private String phone;

	public RequestModifyPhone() {
		super("", "");
		// TODO Auto-generated constructor stub
	}


	public String getCustId() {
		return custId;
	}


	public void setCustId(String custId) {
		this.custId = custId;
	}


//	public String getCustName() {
//		return custName;
//	}
//
//	public void setCustName(String custName) {
//		this.custName = custName;
//	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
		case 0:
			custId = (String) obj;
			break;
//		case 1:
//			custName = (String) obj;
//			break;
		case 1:
			phone = (String) obj;
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
//		case 1:
//			return custName;
		case 1:
			return phone;
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
//		case 1:
//			info.name = "custLName";
//			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
//			info.type = String.class;
//			break;
		case 1:
			info.name = "phone";
			info.type = String.class;
			break;
		}
	}

}
