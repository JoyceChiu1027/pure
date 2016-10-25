package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;
/**
 * @author wyf
 * @类 :关于我们
 * @version 1.0
 */
public class RequestAboutUsMemo extends SoapObject{
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String id;

	public RequestAboutUsMemo() {
		super("", "");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
		case 0:
			id = (String) obj;
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return id;
		}
		return null;

	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "id";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		}
	}
}
