package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

public class RequestCustLoginInfo extends SoapObject {

	private String custlName;
	private String custlpwd;

	public RequestCustLoginInfo() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	public String getCustlName() {
		return custlName;
	}

	public void setCustlName(String custlName) {
		this.custlName = custlName;
	}

	public String getCustlpwd() {
		return custlpwd;
	}

	public void setCustlpwd(String custlpwd) {
		this.custlpwd = custlpwd;
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
			custlName = (String) obj;
			break;
		case 1:
			custlpwd = (String) obj;
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return custlName;
		case 1:
			return custlpwd;
		}
		return null;

	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "custlName";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 1:
			info.name = "custlpwd";
			info.type = String.class;
			break;
		}
	}
}
