package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * 
 * 得到二级频道列表
 * 
 * @author wyf
 *
 */
public class RequestTwoChannel extends SoapObject {

	public RequestTwoChannel() {
		super("","");
		// TODO Auto-generated constructor stub
	}
	
	private String fatherChannelId;

	public void setFatherChannelId(String fatherChannelId) {
		this.fatherChannelId = fatherChannelId;
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
			fatherChannelId = (String) obj;
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return fatherChannelId;
		}
		return null;

	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "fatherChannelId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		}
	}
	
}
