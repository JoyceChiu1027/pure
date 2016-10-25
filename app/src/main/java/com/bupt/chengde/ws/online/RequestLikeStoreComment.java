package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.Utils;

public class RequestLikeStoreComment extends SoapObject {
	private String custId;
	private String custName;
	private int type;
	private String busiId;
	private String modId;
	private String content;

	public RequestLikeStoreComment() {
		super("", "");
		// TODO Auto-generated constructor stub
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBusiId() {
		return busiId;
	}

	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public Object getProperty(int index) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			
			return custName;
		case 1:
			return custId;
		case 2:
			return type;
		case 3:
			return modId; 
		case 4:
			return busiId;
		case 5:
			return content;
		}
		return null;
	}
	@Override
	public void setProperty(int index, Object obj) {
		switch (index) {
		case 0:
			custName=(String) obj;
			break;
		case 1:
			custId=(String) obj;
			break;
		case 2:
			type = Utils.parseStringToInt(obj.toString());
			break;
		case 3:
			modId=(String) obj;
			break;
		case 4:
			busiId=(String) obj;
			break;
		case 5:
			content=(String) obj;
			break;
		}
	}
	@Override
	public void getPropertyInfo(int index, PropertyInfo info) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			info.name = "custName";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 1:
			info.name = "custId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 2:
			info.name = "type";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = Integer.class;
			break;
		case 3:
			info.name = "modId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 4:
			info.name = "busId";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		case 5:
			info.name = "content";
			info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			info.type = String.class;
			break;
		}
	}
}
