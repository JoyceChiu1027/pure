package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * @author wyf
 * @类 :获取业务类型的请求体
 * @version 1.0
 */
public class RequestBusiType extends SoapObject {

	private String busiType;//业务类型  1为电视业务  2为宽带业务 3为新装业务
	
	public RequestBusiType() {
		super("", "");
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public int getPropertyCount() {
		return 1;
	}
	
	public Object getProperty(int __index) {
		switch (__index) {
		case 0:
			return busiType;
		}
		return null;
	}
	
	public void setProperty(int __index, Object __obj) {
		switch (__index) {
		case 0:
			busiType = (String) __obj;
			break;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void getPropertyInfo(int __index, Hashtable __table,
			PropertyInfo __info) {
		switch (__index) {
		case 0:
			__info.name = "busiType";
			__info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			__info.type = String.class;
			break;

		}
	}
}
