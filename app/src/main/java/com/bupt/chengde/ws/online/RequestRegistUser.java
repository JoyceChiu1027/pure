package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

public class RequestRegistUser extends SoapObject {
	private String phoneNo;
	private String userName;
	private String date;
	private String tjPhoneNo;
	private String verifyNo;
	private String verType;
	

	public RequestRegistUser() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getTjPhoneNo() {
		return tjPhoneNo;
	}

	public void setTjPhoneNo(String tjPhoneNo) {
		this.tjPhoneNo = tjPhoneNo;
	}
	
	public String getVerifyNo() {
		return verifyNo;
	}

	public void setVerifyNo(String verifyNo) {
		this.verifyNo = verifyNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVerType() {
		return verType;
	}

	public void setVerType(String verType) {
		this.verType = verType;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
		case 0:
			phoneNo = (String) obj;
			break;
		case 1:
			userName = (String) obj;
			break;
		case 2:
			date = (String) obj;
			break;
		case 3:
			tjPhoneNo=(String)obj;
			break;
		case 4:
			verifyNo=(String)obj;
			break;
		case 5:
			verType=(String)obj;
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return phoneNo;
		case 1:
			return userName;
		case 2:
			return date;
		case 3:
			return tjPhoneNo;
		case 4:
			return verifyNo;
		case 5:
			return verType;
		}
		return null;

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
			info.name = "userName";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 2:
			info.name = "date";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 3:
			info.name = "tjPhoneNo";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 4:
			info.name = "verifyNo";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 5:
			info.name = "verType";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
			
		}
	}

}
