package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.Utils;

/**
 * 
 * @author wyf
 * @类 : 宽带、电视业务
 * @version 1.0
 */
public class RequestBusinessInfo extends SoapObject{
	private int type;
	private int page;
	private String typeId;
	
	public RequestBusinessInfo() {
		super("", "");
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}


	public int getPropertyCount() {
		return 3;
	}
	
	public Object getProperty(int __index) {
		switch (__index) {
		case 0:
			return type;
		case 1:
			return page;
		case 2:
			return typeId;
		}
		return null;
	}
	
	public void setProperty(int __index, Object __obj) {
		switch (__index) {
		case 0:
			type = Utils.parseStringToInt(__obj.toString());
			break;
		case 1:
			page = Utils.parseStringToInt(__obj.toString());
			break;
		case 2:
			typeId=(String) __obj;
			break;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void getPropertyInfo(int __index, Hashtable __table,
			PropertyInfo __info) {
		switch (__index) {
		case 0:
			__info.name = "type";
			__info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			__info.type = Integer.class;
			break;
			
		case 1:
			__info.name = "page";
			__info.type = Integer.class;
			break;
		case 2:
			__info.name = "typeId";
			__info.type = String.class;
			break;

		}
	}
}
