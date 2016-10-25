package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * @author litf
 * @类 :用户上传 头像
 */
public class RequestPerfect extends SoapObject {

	private String custId;
	private String fileName;
	private String file;

	public RequestPerfect() {
		super("", "");
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getPropertyCount() {
		return 3;
	}

	public Object getProperty(int __index) {
		switch (__index) {
		case 0:
			return custId;
		case 1:
			return fileName;
		case 2:
			return file;
		}
		return null;
	}

	public void setProperty(int __index, Object __obj) {
		switch (__index) {
		case 0:
			custId = (String) __obj;
			break;
		case 1:
			fileName = (String) __obj;
		case 2:
			file = (String) __obj;
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
			__info.name = "fileName";
			__info.type = String.class;
			break;
		case 2:
			__info.name = "file";
			__info.type = String.class;
			break;
		}
	}
}
