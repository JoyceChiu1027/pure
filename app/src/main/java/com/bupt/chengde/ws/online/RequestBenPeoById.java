package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.Utils;

public class RequestBenPeoById extends SoapObject {
	private int type;
	private String modType;
	private int benPeoId;
	private int page;
	
	public RequestBenPeoById() {
		super("", "");
		// TODO Auto-generated constructor stub
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
	
	public String getModType() {
		return modType;
	}
	
	public void setModType(String modType) {
		this.modType = modType;
	}

	public int getBenPeoId() {
		return benPeoId;
	}

	public void setBenPeoId(int benPeoId) {
		this.benPeoId = benPeoId;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
		case 0:
			type = Utils.parseStringToInt(obj.toString());
			break;
		case 1:
			benPeoId = Utils.parseStringToInt(obj.toString());
			break;
		case 2:
			page = Utils.parseStringToInt(obj.toString());
			break;
		case 3:
			modType = (String)obj;
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return type;
		case 1:
			return benPeoId;
		case 2: 
			return page;
		case 3: 
			return modType;
		}
		return null;

	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "type";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = Integer.class;
			break;
		case 1:
			info.name = "benPeoId";
			info.type = Integer.class;
			break;
		case 2:
			info.name = "page";
			info.type = Integer.class;
			break;
		case 3:
			info.name = "modType";
			info.type = String.class;
			break;
		}
	}

}
