package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.Utils;

/**
 * @author wyf
 * @类 :故障报修的请求
 * @version 1.0
 */
public class RequestBreakdown extends SoapObject {

	private String subphone;// 手机号
	private String custname;// 客户姓名
	private String idcard;// 客户身份证号
	private String addr;// 详细地址
	private String cano;// CA卡号
	private int typeid;//故障类型编号
	private String gzIntro;//故障描述

	public RequestBreakdown() {
		super("", "");
	}
	

	public String getSubphone() {
		return subphone;
	}


	public void setSubphone(String subphone) {
		this.subphone = subphone;
	}


	public String getCustname() {
		return custname;
	}


	public void setCustname(String custname) {
		this.custname = custname;
	}


	public String getIdcard() {
		return idcard;
	}


	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}


	public String getAddr() {
		return addr;
	}


	public void setAddr(String addr) {
		this.addr = addr;
	}


	public String getCano() {
		return cano;
	}


	public void setCano(String cano) {
		this.cano = cano;
	}


	public int getTypeid() {
		return typeid;
	}


	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}


	public String getGzIntro() {
		return gzIntro;
	}


	public void setGzIntro(String gzIntro) {
		this.gzIntro = gzIntro;
	}


	public int getPropertyCount() {
		return 7;
	}

	public Object getProperty(int __index) {
		switch (__index) {
		case 0:
			return subphone;
		case 1:
			return custname;
		case 2:
			return idcard;
		case 3:
			return addr;
		case 4:
			return cano;
		case 5:
			return typeid;
		case 6:
			return gzIntro;
		}
		return null;
	}

	public void setProperty(int __index, Object __obj) {
		switch (__index) {
		case 0:
			subphone = (String) __obj;
			break;
		case 1:
			custname = (String) __obj;
			break;
		case 2:
			idcard = (String) __obj;
			break;
		case 3:
			addr = (String) __obj;
			break;
		case 4:
			cano = (String) __obj;
			break;
		case 5:
			typeid = Utils.parseStringToInt(__obj.toString());
		case 6:
			gzIntro = (String) __obj;
			break;
		}
	}

	@SuppressWarnings("rawtypes")
	public void getPropertyInfo(int __index, Hashtable __table,
			PropertyInfo __info) {
		switch (__index) {
		case 0:
			__info.name = "subphone";
			__info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
			__info.type = String.class;
			break;
		case 1:
			__info.name = "custname";
			__info.type = String.class;
			break;
		case 2:
			__info.name = "idcard";
			__info.type = String.class;
			break;
		case 3:
			__info.name = "addr";
			__info.type = String.class;
			break;
		case 4:
			__info.name = "cano";
			__info.type = String.class;
			break;
		case 5:
			__info.name = "typeid";
			__info.type = Integer.class;
			break;
		case 6:
			__info.name = "gzIntro";
			__info.type = String.class;
			break;
		}
	}
}
