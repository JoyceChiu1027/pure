package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.Utils;

public class RequestDiscussList extends SoapObject {
	private String modId;
	private String delId;
	private int page;

	public RequestDiscussList() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getDelId() {
		return delId;
	}

	public void setDelId(String delId) {
		this.delId = delId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public Object getProperty(int index) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			
			return modId;
		case 1:
			return delId;
		case 2:
			return page;
		
		}
		return null;
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			modId=(String)value;
			break;
		case 1:
			delId=(String) value;
			break;
		case 2:
			page=Utils.parseStringToInt(value.toString());
			break;
		default:
			break;
		}
	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "modId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 1:
			info.name = "delId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 2:
			info.name = "page";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = Integer.class;
			break;
		}
	}
}
