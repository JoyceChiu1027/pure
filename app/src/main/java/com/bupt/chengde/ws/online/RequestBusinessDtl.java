package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * @author wyf
 * @类 :电视业务详情
 * @version 1.0
 */
public class RequestBusinessDtl extends SoapObject {

	private String tvbId;

	public RequestBusinessDtl() {
		super("", "");
	}

	public void setTvbId(String tvbId) {
		this.tvbId = tvbId;
	}

	public String getTvbId(String tvbId) {
		return this.tvbId;
	}

	public int getPropertyCount() {
		return 1;
	}

	public Object getProperty(int __index) {
		switch (__index) {
		case 0:
			return tvbId;
		}
		return null;
	}

	public void setProperty(int __index, Object __obj) {
		switch (__index) {
		case 0:
			tvbId = (String) __obj;
			break;
		}
	}

	@SuppressWarnings("rawtypes")
	public void getPropertyInfo(int __index, Hashtable __table,
			PropertyInfo __info) {
		switch (__index) {
		case 0:
			__info.name = "tvbId";
			__info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			__info.type = String.class;
			break;

		}
	}
}
