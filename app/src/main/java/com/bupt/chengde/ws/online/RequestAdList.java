package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.Utils;

public class RequestAdList extends SoapObject {

	private int adKind;
	private String adBelong;

	public RequestAdList() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	public int getAdKind() {
		return adKind;
	}

	public void setAdKind(int adKind) {
		this.adKind = adKind;
	}

	public String getAdBelong() {
		return adBelong;
	}

	public void setAdBelong(String adBelong) {
		this.adBelong = adBelong;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Object getProperty(int index) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:

			return adKind;
		case 1:
			return adBelong;

		}
		return null;
	}

	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
		case 0:
			adKind = Utils.parseStringToInt(obj.toString());
			break;
		case 1:
			adBelong = (String) obj;
			break;
		}
	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			info.name = "adKind";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = Integer.class;
			break;
		case 1:
			info.name = "adBelong";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		}
	}
}
