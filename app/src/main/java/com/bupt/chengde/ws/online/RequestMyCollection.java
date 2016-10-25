package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * @author litf
 * @类 :获取我的收藏列表的请求
 * @version 1.0
 */
public class RequestMyCollection extends SoapObject {

	private String custId;// 客户ID
	private String bigModId;// 大模块编号：便民 ben 摄影 photo 旅游 travel 主页 main 新闻 news

	public RequestMyCollection() {
		super("", "");
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getBigModId() {
		return bigModId;
	}

	public void setBigModId(String bigModId) {
		this.bigModId = bigModId;
	}

	public int getPropertyCount() {
		return 2;
	}

	public Object getProperty(int __index) {
		switch (__index) {
		case 0:
			return custId;
		case 1:
			return bigModId;
		}
		return null;
	}

	public void setProperty(int __index, Object __obj) {
		switch (__index) {
		case 0:
			custId = (String) __obj;
			break;
		case 1:
			bigModId = (String) __obj;
			break;
		}
	}

	@SuppressWarnings("rawtypes")
	public void getPropertyInfo(int __index, Hashtable __table, PropertyInfo __info) {
		switch (__index) {
		case 0:
			__info.name = "custId";
			__info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			__info.type = String.class;
			break;
		case 1:
			__info.name = "bigModId";
			__info.type = String.class;
			break;
		}
	}
}
