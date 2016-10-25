package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

public class RequestPhotoDetail extends SoapObject {
	private String custId;
	private int type;//Type : 1 推荐 2 论坛 3 赏析 4 影楼 5 摄友
	private String delId;

	public RequestPhotoDetail() {
		super("", "");
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDelId() {
		return delId;
	}

	public void setDelId(String delId) {
		this.delId = delId;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
		case 0:
			custId = (String) obj;
			break;
		case 1:
			type = (Integer) obj;
			break;
		case 2:
			delId = (String) obj;
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return custId;
		case 1:
			return type;
		case 2:
			return delId;
		}
		return null;

	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "custId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 1:
			info.name = "type";
			info.type = Integer.class;
			break;
		case 2:
			info.name = "delId";
			info.type = String.class;
			break;
		}
	}

}
