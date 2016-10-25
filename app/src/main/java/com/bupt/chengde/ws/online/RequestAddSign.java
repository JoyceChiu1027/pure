package com.bupt.chengde.ws.online;

import java.util.Hashtable;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.bupt.chengde.control.CodeConstants;

/**
 * 用户签到接口
 * @author wyf
 *
 */
public class RequestAddSign extends SoapObject{

	private Integer custId;
	private String signDate;
	
	public RequestAddSign() {
		super("", "");
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	 public int getPropertyCount() {
	        return 2;
	    }

	    public Object getProperty(int __index) {
	        switch (__index) {
	            case 0:
	                return custId;
	            case 1:
	                return signDate;
	        }
	        return null;
	    }

	    public void setProperty(int __index, Object __obj) {
	        switch (__index) {
	            case 0:
	            	custId = (Integer) __obj;
	                break;
	            case 1:
	            	signDate = (String) __obj;
	                break;
	        }
	    }

	    @SuppressWarnings("rawtypes")
	    public void getPropertyInfo(int __index, Hashtable __table, PropertyInfo __info) {
	        switch (__index) {
	            case 0:
	                __info.name = "custId";
	                __info.namespace = CodeConstants.NAMESPACE_IN_GENERATED_REQUEST;
	                __info.type = Integer.class;
	                break;
	            case 1:
	                __info.name = "signDate";
	                __info.type = String.class;
	                break;


	        }
	    }
}
