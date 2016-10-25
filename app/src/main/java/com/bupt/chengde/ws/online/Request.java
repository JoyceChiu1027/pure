package com.bupt.chengde.ws.online;

import org.ksoap2.serialization.SoapObject;

public  class Request {
	protected SoapObject request;
	protected String methodName;
	protected String methodNameRequest;
	protected String requestName;
	protected Class<?> clazz;
   
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodNameRequest() {
		return methodNameRequest;
	}

	public void setMethodNameRequest(String methodNameRequest) {
		this.methodNameRequest = methodNameRequest;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public SoapObject getRequest() {
		return request;
	}

	public void setRequest(SoapObject request) {
		this.request = request;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

}
