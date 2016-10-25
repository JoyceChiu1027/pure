package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

public class RequestCheckScore  extends SoapObject {

	private int custId;
	
	public RequestCheckScore() {
		super("","");
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getPropertyCount() {
		return 1;
	}
	
	public Object getProperty(int __index) {
		switch (__index) {
		case 0:
			return custId;
		}
		return null;
	}
	
	public void setProperty(int __index, Object __obj) {
		switch (__index) {
		case 0:
			custId = (Integer) __obj;
			break;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void getPropertyInfo(int __index, Hashtable __table,
			PropertyInfo __info) {
		switch (__index) {
		case 0:
			__info.name = "custId";
			__info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			__info.type = Integer.class;
			break;

		}
	}
}
