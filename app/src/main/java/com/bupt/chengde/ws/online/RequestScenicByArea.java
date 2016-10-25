package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.Utils;

public class RequestScenicByArea extends SoapObject {
	private Integer areaID;
	// reaID = 1 承德市 2 承德县 3 宽城县 4 兴隆县 5 平泉县 6 滦平县 7 隆化县 8 丰宁县 9 围场县
	private Integer page;

	public RequestScenicByArea() {
		super("", "");
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
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
			areaID = Utils.parseStringToInt(obj.toString());
			break;
		case 1:
			page = Utils.parseStringToInt(obj.toString());
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return areaID;
		case 1:
			return page;
		}
		return null;

	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "areaID";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = Integer.class;
			break;
		case 1:
			info.name = "page";
			info.type = Integer.class;
			break;
		}
	}

}
