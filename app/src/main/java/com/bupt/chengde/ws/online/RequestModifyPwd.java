package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

public class RequestModifyPwd extends SoapObject {
	private String custId;
	private String oldPwd;
	private String newPwd;

	public RequestModifyPwd() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
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
			oldPwd = (String) obj;
			break;
		case 2:
			newPwd = (String) obj;
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
			return oldPwd;
		case 2:
			return newPwd;
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
			info.name = "oldPwd";
			info.type = String.class;
			break;
		case 2:
			info.name = "newPwd";
			info.type = String.class;
			break;
		}
	}
}
