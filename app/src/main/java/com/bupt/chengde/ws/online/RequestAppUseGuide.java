package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.Utils;

/**
 * Created by wyf on 2015/12/17. 使用指南
 */
public class RequestAppUseGuide extends SoapObject {
	private int expiainId;

	public RequestAppUseGuide() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	public int getExpiainId() {
		return expiainId;
	}

	public void setExpiainId(int expiainId) {
		this.expiainId = expiainId;
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
			expiainId = Utils.parseStringToInt(obj.toString());
			break;
		default:
			break;
		}
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return expiainId;
		}
		return null;

	}

	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		switch (index) {
		case 0:
			info.name = "expiainId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = Integer.class;
			break;
		}
	}
}
