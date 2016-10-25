package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * @author wyf
 * @类 :电视业务
 * @version 1.0
 */
public class RequestTvBusiness extends SoapObject {

	private String favType;
	private String feeType;
	private String tvType;
	private String proType;
	private String showType;

	public RequestTvBusiness() {
		super("", "");
	}

	public void setFavType(String favType) {
		this.favType = favType;
	}

	public String getFavType(String favType) {
		return this.favType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeType(String feeType) {
		return this.feeType;
	}

	public void setTvType(String tvType) {
		this.tvType = tvType;
	}

	public String getTvType(String tvType) {
		return this.tvType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getProType(String proType) {
		return this.proType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getShowType(String showType) {
		return this.showType;
	}

	public int getPropertyCount() {
		return 5;
	}

	public Object getProperty(int __index) {
		switch (__index) {
		case 0:
			return favType;
		case 1:
			return feeType;
		case 2:
			return tvType;
		case 3:
			return proType;
		case 4:
			return showType;

		}
		return null;
	}

	public void setProperty(int __index, Object __obj) {
		switch (__index) {
		case 0:
			favType = (String) __obj;
			break;
		case 1:
			feeType = (String) __obj;
			break;
		case 2:
			tvType = (String) __obj;
			break;
		case 3:
			proType = (String) __obj;
			break;
		case 4:
			showType = (String) __obj;
			break;

		}
	}

	@SuppressWarnings("rawtypes")
	public void getPropertyInfo(int __index, Hashtable __table,
			PropertyInfo __info) {
		switch (__index) {
		case 0:
			__info.name = "favType";
			__info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			__info.type = String.class;
			break;
		case 1:
			__info.name = "feeType";
			__info.type = String.class;
			break;
		case 2:
			__info.name = "tvType";
			__info.type = String.class;
			break;
		case 3:
			__info.name = "proType";
			__info.type = String.class;
			break;
		case 4:
			__info.name = "showType";
			__info.type = String.class;
			break;

		}
	}
}
