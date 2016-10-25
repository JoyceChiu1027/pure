package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.Utils;

public class RequestNewsById extends SoapObject {

	private String custId;
	
	private String newsId;
	private int type;
	public RequestNewsById() {
		super("", "");
		// TODO Auto-generated constructor stub
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

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
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
			custId=(String)obj;
			break;
		case 1:
			newsId = (String) obj;
			break;
		case 2:
			type = Utils.parseStringToInt(obj.toString());	
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
			return newsId;
		case 2:
			return type;
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
			info.name = "newsId";
			info.type = String.class;
			break;
		case 2:
			info.name = "type";
			info.type = Integer.class;
			break;
		}
	}
}
