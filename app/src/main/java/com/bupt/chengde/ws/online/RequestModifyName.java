package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

public class RequestModifyName extends SoapObject {
	private String custId;
	private String oldCustName;
	private String newCustName;

	public RequestModifyName() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOldCustName() {
		return oldCustName;
	}

	public void setOldCustName(String oldCustName) {
		this.oldCustName = oldCustName;
	}

	public String getNewCustName() {
		return newCustName;
	}

	public void setNewCustName(String newCustName) {
		this.newCustName = newCustName;
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
			custId = (String) obj;
			break;
		case 1:
			oldCustName = (String) obj;
			break;
		case 2:
			newCustName = (String) obj;
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
			return oldCustName;
		case 2:
			return newCustName;
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
			info.name = "oldCustName";
			info.type = String.class;
			break;
		case 2:
			info.name = "newCustName";
			info.type = String.class;
			break;
		}
	}
}

